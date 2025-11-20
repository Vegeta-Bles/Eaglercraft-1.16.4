package net.minecraft.server.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkRenderDistanceCenterS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Util;
import net.minecraft.util.collection.TypeFilterableList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.CsvWriter;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadedAnvilChunkStorage extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int MAX_LEVEL = 33 + ChunkStatus.getMaxDistanceFromFull();
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> currentChunkHolders = new Long2ObjectLinkedOpenHashMap();
   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> chunkHolders = this.currentChunkHolders.clone();
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> chunksToUnload = new Long2ObjectLinkedOpenHashMap();
   private final LongSet loadedChunks = new LongOpenHashSet();
   private final ServerWorld world;
   private final ServerLightingProvider serverLightingProvider;
   private final ThreadExecutor<Runnable> mainThreadExecutor;
   private final ChunkGenerator chunkGenerator;
   private final Supplier<PersistentStateManager> persistentStateManagerFactory;
   private final PointOfInterestStorage pointOfInterestStorage;
   private final LongSet unloadedChunks = new LongOpenHashSet();
   private boolean chunkHolderListDirty;
   private final ChunkTaskPrioritySystem chunkTaskPrioritySystem;
   private final MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> worldGenExecutor;
   private final MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> mainExecutor;
   private final WorldGenerationProgressListener worldGenerationProgressListener;
   private final ThreadedAnvilChunkStorage.TicketManager ticketManager;
   private final AtomicInteger totalChunksLoadedCount = new AtomicInteger();
   private final StructureManager structureManager;
   private final File saveDir;
   private final PlayerChunkWatchingManager playerChunkWatchingManager = new PlayerChunkWatchingManager();
   private final Int2ObjectMap<ThreadedAnvilChunkStorage.EntityTracker> entityTrackers = new Int2ObjectOpenHashMap();
   private final Long2ByteMap chunkToType = new Long2ByteOpenHashMap();
   private final Queue<Runnable> unloadTaskQueue = Queues.newConcurrentLinkedQueue();
   private int watchDistance;

   public ThreadedAnvilChunkStorage(
      ServerWorld arg,
      LevelStorage.Session arg2,
      DataFixer dataFixer,
      StructureManager arg3,
      Executor workerExecutor,
      ThreadExecutor<Runnable> mainThreadExecutor,
      ChunkProvider chunkProvider,
      ChunkGenerator chunkGenerator,
      WorldGenerationProgressListener worldGenerationProgressListener,
      Supplier<PersistentStateManager> supplier,
      int i,
      boolean bl
   ) {
      super(new File(arg2.getWorldDirectory(arg.getRegistryKey()), "region"), dataFixer, bl);
      this.structureManager = arg3;
      this.saveDir = arg2.getWorldDirectory(arg.getRegistryKey());
      this.world = arg;
      this.chunkGenerator = chunkGenerator;
      this.mainThreadExecutor = mainThreadExecutor;
      TaskExecutor<Runnable> lv = TaskExecutor.create(workerExecutor, "worldgen");
      MessageListener<Runnable> lv2 = MessageListener.create("main", mainThreadExecutor::send);
      this.worldGenerationProgressListener = worldGenerationProgressListener;
      TaskExecutor<Runnable> lv3 = TaskExecutor.create(workerExecutor, "light");
      this.chunkTaskPrioritySystem = new ChunkTaskPrioritySystem(ImmutableList.of(lv, lv2, lv3), workerExecutor, Integer.MAX_VALUE);
      this.worldGenExecutor = this.chunkTaskPrioritySystem.createExecutor(lv, false);
      this.mainExecutor = this.chunkTaskPrioritySystem.createExecutor(lv2, false);
      this.serverLightingProvider = new ServerLightingProvider(
         chunkProvider, this, this.world.getDimension().hasSkyLight(), lv3, this.chunkTaskPrioritySystem.createExecutor(lv3, false)
      );
      this.ticketManager = new ThreadedAnvilChunkStorage.TicketManager(workerExecutor, mainThreadExecutor);
      this.persistentStateManagerFactory = supplier;
      this.pointOfInterestStorage = new PointOfInterestStorage(new File(this.saveDir, "poi"), dataFixer, bl);
      this.setViewDistance(i);
   }

   private static double getSquaredDistance(ChunkPos pos, Entity entity) {
      double d = (double)(pos.x * 16 + 8);
      double e = (double)(pos.z * 16 + 8);
      double f = d - entity.getX();
      double g = e - entity.getZ();
      return f * f + g * g;
   }

   private static int getChebyshevDistance(ChunkPos pos, ServerPlayerEntity player, boolean useCameraPosition) {
      int i;
      int j;
      if (useCameraPosition) {
         ChunkSectionPos lv = player.getCameraPosition();
         i = lv.getSectionX();
         j = lv.getSectionZ();
      } else {
         i = MathHelper.floor(player.getX() / 16.0);
         j = MathHelper.floor(player.getZ() / 16.0);
      }

      return getChebyshevDistance(pos, i, j);
   }

   private static int getChebyshevDistance(ChunkPos pos, int x, int z) {
      int k = pos.x - x;
      int l = pos.z - z;
      return Math.max(Math.abs(k), Math.abs(l));
   }

   protected ServerLightingProvider getLightProvider() {
      return this.serverLightingProvider;
   }

   @Nullable
   protected ChunkHolder getCurrentChunkHolder(long pos) {
      return (ChunkHolder)this.currentChunkHolders.get(pos);
   }

   @Nullable
   protected ChunkHolder getChunkHolder(long pos) {
      return (ChunkHolder)this.chunkHolders.get(pos);
   }

   protected IntSupplier getCompletedLevelSupplier(long pos) {
      return () -> {
         ChunkHolder lv = this.getChunkHolder(pos);
         return lv == null ? LevelPrioritizedQueue.LEVEL_COUNT - 1 : Math.min(lv.getCompletedLevel(), LevelPrioritizedQueue.LEVEL_COUNT - 1);
      };
   }

   @Environment(EnvType.CLIENT)
   public String getChunkLoadingDebugInfo(ChunkPos arg) {
      ChunkHolder lv = this.getChunkHolder(arg.toLong());
      if (lv == null) {
         return "null";
      } else {
         String string = lv.getLevel() + "\n";
         ChunkStatus lv2 = lv.getCurrentStatus();
         Chunk lv3 = lv.getCurrentChunk();
         if (lv2 != null) {
            string = string + "St: §" + lv2.getIndex() + lv2 + '§' + "r\n";
         }

         if (lv3 != null) {
            string = string + "Ch: §" + lv3.getStatus().getIndex() + lv3.getStatus() + '§' + "r\n";
         }

         ChunkHolder.LevelType lv4 = lv.getLevelType();
         string = string + "§" + lv4.ordinal() + lv4;
         return string + '§' + "r";
      }
   }

   private CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> getRegion(ChunkPos centerChunk, int margin, IntFunction<ChunkStatus> distanceToStatus) {
      List<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>> list = Lists.newArrayList();
      int j = centerChunk.x;
      int k = centerChunk.z;

      for (int l = -margin; l <= margin; l++) {
         for (int m = -margin; m <= margin; m++) {
            int n = Math.max(Math.abs(m), Math.abs(l));
            final ChunkPos lv = new ChunkPos(j + m, k + l);
            long o = lv.toLong();
            ChunkHolder lv2 = this.getCurrentChunkHolder(o);
            if (lv2 == null) {
               return CompletableFuture.completedFuture(Either.right(new ChunkHolder.Unloaded() {
                  @Override
                  public String toString() {
                     return "Unloaded " + lv.toString();
                  }
               }));
            }

            ChunkStatus lv3 = distanceToStatus.apply(n);
            CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = lv2.getChunkAt(lv3, this);
            list.add(completableFuture);
         }
      }

      CompletableFuture<List<Either<Chunk, ChunkHolder.Unloaded>>> completableFuture2 = Util.combine(list);
      return completableFuture2.thenApply(
         listx -> {
            List<Chunk> list2 = Lists.newArrayList();
            int l = 0;

            for (final Either<Chunk, ChunkHolder.Unloaded> either : listx) {
               Optional<Chunk> optional = either.left();
               if (!optional.isPresent()) {
                  final int mx = l;
                  return Either.right(
                     new ChunkHolder.Unloaded() {
                        @Override
                        public String toString() {
                           return "Unloaded "
                              + new ChunkPos(j + mx % (margin * 2 + 1), k + mx / (margin * 2 + 1))
                              + " "
                              + ((ChunkHolder.Unloaded)either.right().get()).toString();
                        }
                     }
                  );
               }

               list2.add(optional.get());
               l++;
            }

            return Either.left(list2);
         }
      );
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkEntitiesTickable(ChunkPos pos) {
      return this.getRegion(pos, 2, i -> ChunkStatus.FULL)
         .thenApplyAsync(either -> either.mapLeft(list -> (WorldChunk)list.get(list.size() / 2)), this.mainThreadExecutor);
   }

   @Nullable
   private ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int j) {
      if (j > MAX_LEVEL && level > MAX_LEVEL) {
         return holder;
      } else {
         if (holder != null) {
            holder.setLevel(level);
         }

         if (holder != null) {
            if (level > MAX_LEVEL) {
               this.unloadedChunks.add(pos);
            } else {
               this.unloadedChunks.remove(pos);
            }
         }

         if (level <= MAX_LEVEL && holder == null) {
            holder = (ChunkHolder)this.chunksToUnload.remove(pos);
            if (holder != null) {
               holder.setLevel(level);
            } else {
               holder = new ChunkHolder(new ChunkPos(pos), level, this.serverLightingProvider, this.chunkTaskPrioritySystem, this);
            }

            this.currentChunkHolders.put(pos, holder);
            this.chunkHolderListDirty = true;
         }

         return holder;
      }
   }

   @Override
   public void close() throws IOException {
      try {
         this.chunkTaskPrioritySystem.close();
         this.pointOfInterestStorage.close();
      } finally {
         super.close();
      }
   }

   protected void save(boolean flush) {
      if (flush) {
         List<ChunkHolder> list = this.chunkHolders
            .values()
            .stream()
            .filter(ChunkHolder::isAccessible)
            .peek(ChunkHolder::updateAccessibleStatus)
            .collect(Collectors.toList());
         MutableBoolean mutableBoolean = new MutableBoolean();

         do {
            mutableBoolean.setFalse();
            list.stream().map(arg -> {
               CompletableFuture<Chunk> completableFuture;
               do {
                  completableFuture = arg.getSavingFuture();
                  this.mainThreadExecutor.runTasks(completableFuture::isDone);
               } while (completableFuture != arg.getSavingFuture());

               return completableFuture.join();
            }).filter(arg -> arg instanceof ReadOnlyChunk || arg instanceof WorldChunk).filter(this::save).forEach(arg -> mutableBoolean.setTrue());
         } while (mutableBoolean.isTrue());

         this.unloadChunks(() -> true);
         this.completeAll();
         LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.saveDir.getName());
      } else {
         this.chunkHolders.values().stream().filter(ChunkHolder::isAccessible).forEach(arg -> {
            Chunk lv = arg.getSavingFuture().getNow(null);
            if (lv instanceof ReadOnlyChunk || lv instanceof WorldChunk) {
               this.save(lv);
               arg.updateAccessibleStatus();
            }
         });
      }
   }

   protected void tick(BooleanSupplier shouldKeepTicking) {
      Profiler lv = this.world.getProfiler();
      lv.push("poi");
      this.pointOfInterestStorage.tick(shouldKeepTicking);
      lv.swap("chunk_unload");
      if (!this.world.isSavingDisabled()) {
         this.unloadChunks(shouldKeepTicking);
      }

      lv.pop();
   }

   private void unloadChunks(BooleanSupplier shouldKeepTicking) {
      LongIterator longIterator = this.unloadedChunks.iterator();

      for (int i = 0; longIterator.hasNext() && (shouldKeepTicking.getAsBoolean() || i < 200 || this.unloadedChunks.size() > 2000); longIterator.remove()) {
         long l = longIterator.nextLong();
         ChunkHolder lv = (ChunkHolder)this.currentChunkHolders.remove(l);
         if (lv != null) {
            this.chunksToUnload.put(l, lv);
            this.chunkHolderListDirty = true;
            i++;
            this.tryUnloadChunk(l, lv);
         }
      }

      Runnable runnable;
      while ((shouldKeepTicking.getAsBoolean() || this.unloadTaskQueue.size() > 2000) && (runnable = this.unloadTaskQueue.poll()) != null) {
         runnable.run();
      }
   }

   private void tryUnloadChunk(long pos, ChunkHolder arg) {
      CompletableFuture<Chunk> completableFuture = arg.getSavingFuture();
      completableFuture.thenAcceptAsync(arg2 -> {
         CompletableFuture<Chunk> completableFuture2 = arg.getSavingFuture();
         if (completableFuture2 != completableFuture) {
            this.tryUnloadChunk(pos, arg);
         } else {
            if (this.chunksToUnload.remove(pos, arg) && arg2 != null) {
               if (arg2 instanceof WorldChunk) {
                  ((WorldChunk)arg2).setLoadedToWorld(false);
               }

               this.save(arg2);
               if (this.loadedChunks.remove(pos) && arg2 instanceof WorldChunk) {
                  WorldChunk lv = (WorldChunk)arg2;
                  this.world.unloadEntities(lv);
               }

               this.serverLightingProvider.updateChunkStatus(arg2.getPos());
               this.serverLightingProvider.tick();
               this.worldGenerationProgressListener.setChunkStatus(arg2.getPos(), null);
            }
         }
      }, this.unloadTaskQueue::add).whenComplete((void_, throwable) -> {
         if (throwable != null) {
            LOGGER.error("Failed to save chunk " + arg.getPos(), throwable);
         }
      });
   }

   protected boolean updateHolderMap() {
      if (!this.chunkHolderListDirty) {
         return false;
      } else {
         this.chunkHolders = this.currentChunkHolders.clone();
         this.chunkHolderListDirty = false;
         return true;
      }
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunk(ChunkHolder holder, ChunkStatus requiredStatus) {
      ChunkPos lv = holder.getPos();
      if (requiredStatus == ChunkStatus.EMPTY) {
         return this.loadChunk(lv);
      } else {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = holder.getChunkAt(requiredStatus.getPrevious(), this);
         return completableFuture.thenComposeAsync(
            either -> {
               Optional<Chunk> optional = either.left();
               if (!optional.isPresent()) {
                  return CompletableFuture.completedFuture((Either<Chunk, ChunkHolder.Unloaded>)either);
               } else {
                  if (requiredStatus == ChunkStatus.LIGHT) {
                     this.ticketManager.addTicketWithLevel(ChunkTicketType.LIGHT, lv, 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FEATURES), lv);
                  }

                  Chunk lvx = optional.get();
                  if (lvx.getStatus().isAtLeast(requiredStatus)) {
                     CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuturex;
                     if (requiredStatus == ChunkStatus.LIGHT) {
                        completableFuturex = this.upgradeChunk(holder, requiredStatus);
                     } else {
                        completableFuturex = requiredStatus.runLoadTask(
                           this.world, this.structureManager, this.serverLightingProvider, arg2 -> this.convertToFullChunk(holder), lvx
                        );
                     }

                     this.worldGenerationProgressListener.setChunkStatus(lv, requiredStatus);
                     return completableFuturex;
                  } else {
                     return this.upgradeChunk(holder, requiredStatus);
                  }
               }
            },
            this.mainThreadExecutor
         );
      }
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> loadChunk(ChunkPos pos) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            this.world.getProfiler().visit("chunkLoad");
            CompoundTag lv = this.getUpdatedChunkTag(pos);
            if (lv != null) {
               boolean bl = lv.contains("Level", 10) && lv.getCompound("Level").contains("Status", 8);
               if (bl) {
                  Chunk lv2 = ChunkSerializer.deserialize(this.world, this.structureManager, this.pointOfInterestStorage, pos, lv);
                  lv2.setLastSaveTime(this.world.getTime());
                  this.method_27053(pos, lv2.getStatus().getChunkType());
                  return Either.left(lv2);
               }

               LOGGER.error("Chunk file at {} is missing level data, skipping", pos);
            }
         } catch (CrashException var5) {
            Throwable throwable = var5.getCause();
            if (!(throwable instanceof IOException)) {
               this.method_27054(pos);
               throw var5;
            }

            LOGGER.error("Couldn't load chunk {}", pos, throwable);
         } catch (Exception var6) {
            LOGGER.error("Couldn't load chunk {}", pos, var6);
         }

         this.method_27054(pos);
         return Either.left(new ProtoChunk(pos, UpgradeData.NO_UPGRADE_DATA));
      }, this.mainThreadExecutor);
   }

   private void method_27054(ChunkPos arg) {
      this.chunkToType.put(arg.toLong(), (byte)-1);
   }

   private byte method_27053(ChunkPos arg, ChunkStatus.ChunkType arg2) {
      return this.chunkToType.put(arg.toLong(), (byte)(arg2 == ChunkStatus.ChunkType.field_12808 ? -1 : 1));
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> upgradeChunk(ChunkHolder holder, ChunkStatus requiredStatus) {
      ChunkPos lv = holder.getPos();
      CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> completableFuture = this.getRegion(
         lv, requiredStatus.getTaskMargin(), i -> this.getRequiredStatusForGeneration(requiredStatus, i)
      );
      this.world.getProfiler().visit(() -> "chunkGenerate " + requiredStatus.getId());
      return completableFuture.thenComposeAsync(
         either -> (CompletableFuture)either.map(
               list -> {
                  try {
                     CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuturex = requiredStatus.runGenerationTask(
                        this.world, this.chunkGenerator, this.structureManager, this.serverLightingProvider, arg2 -> this.convertToFullChunk(holder), list
                     );
                     this.worldGenerationProgressListener.setChunkStatus(lv, requiredStatus);
                     return completableFuturex;
                  } catch (Exception var8) {
                     CrashReport lvx = CrashReport.create(var8, "Exception generating new chunk");
                     CrashReportSection lv2 = lvx.addElement("Chunk to be generated");
                     lv2.add("Location", String.format("%d,%d", lv.x, lv.z));
                     lv2.add("Position hash", ChunkPos.toLong(lv.x, lv.z));
                     lv2.add("Generator", this.chunkGenerator);
                     throw new CrashException(lvx);
                  }
               },
               arg2 -> {
                  this.releaseLightTicket(lv);
                  return CompletableFuture.completedFuture(Either.right(arg2));
               }
            ),
         runnable -> this.worldGenExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable))
      );
   }

   protected void releaseLightTicket(ChunkPos pos) {
      this.mainThreadExecutor
         .send(
            Util.debugRunnable(
               () -> this.ticketManager.removeTicketWithLevel(ChunkTicketType.LIGHT, pos, 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FEATURES), pos),
               () -> "release light ticket " + pos
            )
         );
   }

   private ChunkStatus getRequiredStatusForGeneration(ChunkStatus centerChunkTargetStatus, int distance) {
      ChunkStatus lv;
      if (distance == 0) {
         lv = centerChunkTargetStatus.getPrevious();
      } else {
         lv = ChunkStatus.byDistanceFromFull(ChunkStatus.getDistanceFromFull(centerChunkTargetStatus) + distance);
      }

      return lv;
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> convertToFullChunk(ChunkHolder arg) {
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = arg.getFutureFor(ChunkStatus.FULL.getPrevious());
      return completableFuture.thenApplyAsync(either -> {
         ChunkStatus lv = ChunkHolder.getTargetStatusForLevel(arg.getLevel());
         return !lv.isAtLeast(ChunkStatus.FULL) ? ChunkHolder.UNLOADED_CHUNK : either.mapLeft(arg2 -> {
            ChunkPos lvx = arg.getPos();
            WorldChunk lv2;
            if (arg2 instanceof ReadOnlyChunk) {
               lv2 = ((ReadOnlyChunk)arg2).getWrappedChunk();
            } else {
               lv2 = new WorldChunk(this.world, (ProtoChunk)arg2);
               arg.setCompletedChunk(new ReadOnlyChunk(lv2));
            }

            lv2.setLevelTypeProvider(() -> ChunkHolder.getLevelType(arg.getLevel()));
            lv2.loadToWorld();
            if (this.loadedChunks.add(lvx.toLong())) {
               lv2.setLoadedToWorld(true);
               this.world.addBlockEntities(lv2.getBlockEntities().values());
               List<Entity> list = null;
               TypeFilterableList<Entity>[] sections = lv2.getEntitySectionArray();

               for (TypeFilterableList<Entity> section : sections) {
                  for (Entity lv5 : section) {
                     if (!(lv5 instanceof PlayerEntity) && !this.world.loadEntity(lv5)) {
                        if (list == null) {
                           list = Lists.newArrayList(new Entity[]{lv5});
                        } else {
                           list.add(lv5);
                        }
                     }
                  }
               }

               if (list != null) {
                  list.forEach(lv2::remove);
               }
            }

            return lv2;
         });
      }, runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(runnable, arg.getPos().toLong(), arg::getLevel)));
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkTickable(ChunkHolder holder) {
      ChunkPos lv = holder.getPos();
      CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> completableFuture = this.getRegion(lv, 1, i -> ChunkStatus.FULL);
      CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> completableFuture2 = completableFuture.thenApplyAsync(either -> either.flatMap(list -> {
            WorldChunk lvx = (WorldChunk)list.get(list.size() / 2);
            lvx.runPostProcessing();
            return Either.left(lvx);
         }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
      completableFuture2.thenAcceptAsync(either -> either.mapLeft(arg2 -> {
            this.totalChunksLoadedCount.getAndIncrement();
            Packet<?>[] lvs = new Packet[2];
            this.getPlayersWatchingChunk(lv, false).forEach(arg2x -> this.sendChunkDataPackets(arg2x, lvs, arg2));
            return Either.left(arg2);
         }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
      return completableFuture2;
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkAccessible(ChunkHolder holder) {
      return holder.getChunkAt(ChunkStatus.FULL, this).thenApplyAsync(either -> either.mapLeft(arg -> {
            WorldChunk lv = (WorldChunk)arg;
            lv.disableTickSchedulers();
            return lv;
         }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
   }

   public int getTotalChunksLoadedCount() {
      return this.totalChunksLoadedCount.get();
   }

   private boolean save(Chunk chunk) {
      this.pointOfInterestStorage.method_20436(chunk.getPos());
      if (!chunk.needsSaving()) {
         return false;
      } else {
         chunk.setLastSaveTime(this.world.getTime());
         chunk.setShouldSave(false);
         ChunkPos lv = chunk.getPos();

         try {
            ChunkStatus lv2 = chunk.getStatus();
            if (lv2.getChunkType() != ChunkStatus.ChunkType.field_12807) {
               if (this.method_27055(lv)) {
                  return false;
               }

               if (lv2 == ChunkStatus.EMPTY && chunk.getStructureStarts().values().stream().noneMatch(StructureStart::hasChildren)) {
                  return false;
               }
            }

            this.world.getProfiler().visit("chunkSave");
            CompoundTag lv3 = ChunkSerializer.serialize(this.world, chunk);
            this.setTagAt(lv, lv3);
            this.method_27053(lv, lv2.getChunkType());
            return true;
         } catch (Exception var5) {
            LOGGER.error("Failed to save chunk {},{}", lv.x, lv.z, var5);
            return false;
         }
      }
   }

   private boolean method_27055(ChunkPos arg) {
      byte b = this.chunkToType.get(arg.toLong());
      if (b != 0) {
         return b == 1;
      } else {
         CompoundTag lv;
         try {
            lv = this.getUpdatedChunkTag(arg);
            if (lv == null) {
               this.method_27054(arg);
               return false;
            }
         } catch (Exception var5) {
            LOGGER.error("Failed to read chunk {}", arg, var5);
            this.method_27054(arg);
            return false;
         }

         ChunkStatus.ChunkType lv3 = ChunkSerializer.getChunkType(lv);
         return this.method_27053(arg, lv3) == 1;
      }
   }

   protected void setViewDistance(int watchDistance) {
      int j = MathHelper.clamp(watchDistance + 1, 3, 33);
      if (j != this.watchDistance) {
         int k = this.watchDistance;
         this.watchDistance = j;
         this.ticketManager.setWatchDistance(this.watchDistance);
         ObjectIterator var4 = this.currentChunkHolders.values().iterator();

         while (var4.hasNext()) {
            ChunkHolder lv = (ChunkHolder)var4.next();
            ChunkPos lv2 = lv.getPos();
            Packet<?>[] lvs = new Packet[2];
            this.getPlayersWatchingChunk(lv2, false).forEach(arg2 -> {
               int jx = getChebyshevDistance(lv2, arg2, true);
               boolean bl = jx <= k;
               boolean bl2 = jx <= this.watchDistance;
               this.sendWatchPackets(arg2, lv2, lvs, bl, bl2);
            });
         }
      }
   }

   protected void sendWatchPackets(ServerPlayerEntity player, ChunkPos pos, Packet<?>[] packets, boolean withinMaxWatchDistance, boolean withinViewDistance) {
      if (player.world == this.world) {
         if (withinViewDistance && !withinMaxWatchDistance) {
            ChunkHolder lv = this.getChunkHolder(pos.toLong());
            if (lv != null) {
               WorldChunk lv2 = lv.getWorldChunk();
               if (lv2 != null) {
                  this.sendChunkDataPackets(player, packets, lv2);
               }

               DebugInfoSender.sendChunkWatchingChange(this.world, pos);
            }
         }

         if (!withinViewDistance && withinMaxWatchDistance) {
            player.sendUnloadChunkPacket(pos);
         }
      }
   }

   public int getLoadedChunkCount() {
      return this.chunkHolders.size();
   }

   protected ThreadedAnvilChunkStorage.TicketManager getTicketManager() {
      return this.ticketManager;
   }

   protected Iterable<ChunkHolder> entryIterator() {
      return Iterables.unmodifiableIterable(this.chunkHolders.values());
   }

   void dump(Writer writer) throws IOException {
      CsvWriter lv = CsvWriter.makeHeader()
         .addColumn("x")
         .addColumn("z")
         .addColumn("level")
         .addColumn("in_memory")
         .addColumn("status")
         .addColumn("full_status")
         .addColumn("accessible_ready")
         .addColumn("ticking_ready")
         .addColumn("entity_ticking_ready")
         .addColumn("ticket")
         .addColumn("spawning")
         .addColumn("entity_count")
         .addColumn("block_entity_count")
         .startBody(writer);
      ObjectBidirectionalIterator var3 = this.chunkHolders.long2ObjectEntrySet().iterator();

      while (var3.hasNext()) {
         Entry<ChunkHolder> entry = (Entry<ChunkHolder>)var3.next();
         ChunkPos lv2 = new ChunkPos(entry.getLongKey());
         ChunkHolder lv3 = (ChunkHolder)entry.getValue();
         Optional<Chunk> optional = Optional.ofNullable(lv3.getCurrentChunk());
         Optional<WorldChunk> optional2 = optional.flatMap(arg -> arg instanceof WorldChunk ? Optional.of((WorldChunk)arg) : Optional.empty());
         lv.printRow(
            lv2.x,
            lv2.z,
            lv3.getLevel(),
            optional.isPresent(),
            optional.map(Chunk::getStatus).orElse(null),
            optional2.map(WorldChunk::getLevelType).orElse(null),
            getFutureStatus(lv3.getAccessibleFuture()),
            getFutureStatus(lv3.getTickingFuture()),
            getFutureStatus(lv3.getEntityTickingFuture()),
            this.ticketManager.getTicket(entry.getLongKey()),
            !this.isTooFarFromPlayersToSpawnMobs(lv2),
            optional2.<Integer>map(arg -> Stream.of(arg.getEntitySectionArray()).mapToInt(TypeFilterableList::size).sum()).orElse(0),
            optional2.<Integer>map(arg -> arg.getBlockEntities().size()).orElse(0)
         );
      }
   }

   private static String getFutureStatus(CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> completableFuture) {
      try {
         Either<WorldChunk, ChunkHolder.Unloaded> either = completableFuture.getNow(null);
         return either != null ? (String)either.map(arg -> "done", arg -> "unloaded") : "not completed";
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   @Nullable
   private CompoundTag getUpdatedChunkTag(ChunkPos pos) throws IOException {
      CompoundTag lv = this.getNbt(pos);
      return lv == null ? null : this.updateChunkTag(this.world.getRegistryKey(), this.persistentStateManagerFactory, lv);
   }

   boolean isTooFarFromPlayersToSpawnMobs(ChunkPos arg) {
      long l = arg.toLong();
      return !this.ticketManager.method_20800(l)
         ? true
         : this.playerChunkWatchingManager.getPlayersWatchingChunk(l).noneMatch(arg2 -> !arg2.isSpectator() && getSquaredDistance(arg, arg2) < 16384.0);
   }

   private boolean doesNotGenerateChunks(ServerPlayerEntity player) {
      return player.isSpectator() && !this.world.getGameRules().getBoolean(GameRules.SPECTATORS_GENERATE_CHUNKS);
   }

   void handlePlayerAddedOrRemoved(ServerPlayerEntity player, boolean added) {
      boolean bl2 = this.doesNotGenerateChunks(player);
      boolean bl3 = this.playerChunkWatchingManager.method_21715(player);
      int i = MathHelper.floor(player.getX()) >> 4;
      int j = MathHelper.floor(player.getZ()) >> 4;
      if (added) {
         this.playerChunkWatchingManager.add(ChunkPos.toLong(i, j), player, bl2);
         this.method_20726(player);
         if (!bl2) {
            this.ticketManager.handleChunkEnter(ChunkSectionPos.from(player), player);
         }
      } else {
         ChunkSectionPos lv = player.getCameraPosition();
         this.playerChunkWatchingManager.remove(lv.toChunkPos().toLong(), player);
         if (!bl3) {
            this.ticketManager.handleChunkLeave(lv, player);
         }
      }

      for (int k = i - this.watchDistance; k <= i + this.watchDistance; k++) {
         for (int l = j - this.watchDistance; l <= j + this.watchDistance; l++) {
            ChunkPos lv2 = new ChunkPos(k, l);
            this.sendWatchPackets(player, lv2, new Packet[2], !added, added);
         }
      }
   }

   private ChunkSectionPos method_20726(ServerPlayerEntity arg) {
      ChunkSectionPos lv = ChunkSectionPos.from(arg);
      arg.setCameraPosition(lv);
      arg.networkHandler.sendPacket(new ChunkRenderDistanceCenterS2CPacket(lv.getSectionX(), lv.getSectionZ()));
      return lv;
   }

   public void updateCameraPosition(ServerPlayerEntity player) {
      ObjectIterator i = this.entityTrackers.values().iterator();

      while (i.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker lv = (ThreadedAnvilChunkStorage.EntityTracker)i.next();
         if (lv.entity == player) {
            lv.updateCameraPosition(this.world.getPlayers());
         } else {
            lv.updateCameraPosition(player);
         }
      }

      int ix = MathHelper.floor(player.getX()) >> 4;
      int j = MathHelper.floor(player.getZ()) >> 4;
      ChunkSectionPos lv2 = player.getCameraPosition();
      ChunkSectionPos lv3 = ChunkSectionPos.from(player);
      long l = lv2.toChunkPos().toLong();
      long m = lv3.toChunkPos().toLong();
      boolean bl = this.playerChunkWatchingManager.isWatchDisabled(player);
      boolean bl2 = this.doesNotGenerateChunks(player);
      boolean bl3 = lv2.asLong() != lv3.asLong();
      if (bl3 || bl != bl2) {
         this.method_20726(player);
         if (!bl) {
            this.ticketManager.handleChunkLeave(lv2, player);
         }

         if (!bl2) {
            this.ticketManager.handleChunkEnter(lv3, player);
         }

         if (!bl && bl2) {
            this.playerChunkWatchingManager.disableWatch(player);
         }

         if (bl && !bl2) {
            this.playerChunkWatchingManager.enableWatch(player);
         }

         if (l != m) {
            this.playerChunkWatchingManager.movePlayer(l, m, player);
         }
      }

      int k = lv2.getSectionX();
      int n = lv2.getSectionZ();
      if (Math.abs(k - ix) <= this.watchDistance * 2 && Math.abs(n - j) <= this.watchDistance * 2) {
         int o = Math.min(ix, k) - this.watchDistance;
         int p = Math.min(j, n) - this.watchDistance;
         int q = Math.max(ix, k) + this.watchDistance;
         int r = Math.max(j, n) + this.watchDistance;

         for (int s = o; s <= q; s++) {
            for (int t = p; t <= r; t++) {
               ChunkPos lv4 = new ChunkPos(s, t);
               boolean bl4 = getChebyshevDistance(lv4, k, n) <= this.watchDistance;
               boolean bl5 = getChebyshevDistance(lv4, ix, j) <= this.watchDistance;
               this.sendWatchPackets(player, lv4, new Packet[2], bl4, bl5);
            }
         }
      } else {
         for (int u = k - this.watchDistance; u <= k + this.watchDistance; u++) {
            for (int v = n - this.watchDistance; v <= n + this.watchDistance; v++) {
               ChunkPos lv5 = new ChunkPos(u, v);
               boolean bl6 = true;
               boolean bl7 = false;
               this.sendWatchPackets(player, lv5, new Packet[2], true, false);
            }
         }

         for (int w = ix - this.watchDistance; w <= ix + this.watchDistance; w++) {
            for (int x = j - this.watchDistance; x <= j + this.watchDistance; x++) {
               ChunkPos lv6 = new ChunkPos(w, x);
               boolean bl8 = false;
               boolean bl9 = true;
               this.sendWatchPackets(player, lv6, new Packet[2], false, true);
            }
         }
      }
   }

   @Override
   public Stream<ServerPlayerEntity> getPlayersWatchingChunk(ChunkPos chunkPos, boolean onlyOnWatchDistanceEdge) {
      return this.playerChunkWatchingManager.getPlayersWatchingChunk(chunkPos.toLong()).filter(arg2 -> {
         int i = getChebyshevDistance(chunkPos, arg2, true);
         return i > this.watchDistance ? false : !onlyOnWatchDistanceEdge || i == this.watchDistance;
      });
   }

   protected void loadEntity(Entity arg) {
      if (!(arg instanceof EnderDragonPart)) {
         EntityType<?> lv = arg.getType();
         int i = lv.getMaxTrackDistance() * 16;
         int j = lv.getTrackTickInterval();
         if (this.entityTrackers.containsKey(arg.getEntityId())) {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Entity is already tracked!"));
         } else {
            ThreadedAnvilChunkStorage.EntityTracker lv2 = new ThreadedAnvilChunkStorage.EntityTracker(arg, i, j, lv.alwaysUpdateVelocity());
            this.entityTrackers.put(arg.getEntityId(), lv2);
            lv2.updateCameraPosition(this.world.getPlayers());
            if (arg instanceof ServerPlayerEntity) {
               ServerPlayerEntity lv3 = (ServerPlayerEntity)arg;
               this.handlePlayerAddedOrRemoved(lv3, true);
               ObjectIterator var7 = this.entityTrackers.values().iterator();

               while (var7.hasNext()) {
                  ThreadedAnvilChunkStorage.EntityTracker lv4 = (ThreadedAnvilChunkStorage.EntityTracker)var7.next();
                  if (lv4.entity != lv3) {
                     lv4.updateCameraPosition(lv3);
                  }
               }
            }
         }
      }
   }

   protected void unloadEntity(Entity arg) {
      if (arg instanceof ServerPlayerEntity) {
         ServerPlayerEntity lv = (ServerPlayerEntity)arg;
         this.handlePlayerAddedOrRemoved(lv, false);
         ObjectIterator var3 = this.entityTrackers.values().iterator();

         while (var3.hasNext()) {
            ThreadedAnvilChunkStorage.EntityTracker lv2 = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
            lv2.stopTracking(lv);
         }
      }

      ThreadedAnvilChunkStorage.EntityTracker lv3 = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.remove(arg.getEntityId());
      if (lv3 != null) {
         lv3.stopTracking();
      }
   }

   protected void tickPlayerMovement() {
      List<ServerPlayerEntity> list = Lists.newArrayList();
      List<ServerPlayerEntity> list2 = this.world.getPlayers();
      ObjectIterator var3 = this.entityTrackers.values().iterator();

      while (var3.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker lv = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
         ChunkSectionPos lv2 = lv.lastCameraPosition;
         ChunkSectionPos lv3 = ChunkSectionPos.from(lv.entity);
         if (!Objects.equals(lv2, lv3)) {
            lv.updateCameraPosition(list2);
            Entity lv4 = lv.entity;
            if (lv4 instanceof ServerPlayerEntity) {
               list.add((ServerPlayerEntity)lv4);
            }

            lv.lastCameraPosition = lv3;
         }

         lv.entry.tick();
      }

      if (!list.isEmpty()) {
         var3 = this.entityTrackers.values().iterator();

         while (var3.hasNext()) {
            ThreadedAnvilChunkStorage.EntityTracker lv5 = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
            lv5.updateCameraPosition(list);
         }
      }
   }

   protected void sendToOtherNearbyPlayers(Entity entity, Packet<?> packet) {
      ThreadedAnvilChunkStorage.EntityTracker lv = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.get(entity.getEntityId());
      if (lv != null) {
         lv.sendToOtherNearbyPlayers(packet);
      }
   }

   protected void sendToNearbyPlayers(Entity entity, Packet<?> packet) {
      ThreadedAnvilChunkStorage.EntityTracker lv = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.get(entity.getEntityId());
      if (lv != null) {
         lv.sendToNearbyPlayers(packet);
      }
   }

   private void sendChunkDataPackets(ServerPlayerEntity player, Packet<?>[] packets, WorldChunk chunk) {
      if (packets[0] == null) {
         packets[0] = new ChunkDataS2CPacket(chunk, 65535);
         packets[1] = new LightUpdateS2CPacket(chunk.getPos(), this.serverLightingProvider, true);
      }

      player.sendInitialChunkPackets(chunk.getPos(), packets[0], packets[1]);
      DebugInfoSender.sendChunkWatchingChange(this.world, chunk.getPos());
      List<Entity> list = Lists.newArrayList();
      List<Entity> list2 = Lists.newArrayList();
      ObjectIterator var6 = this.entityTrackers.values().iterator();

      while (var6.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker lv = (ThreadedAnvilChunkStorage.EntityTracker)var6.next();
         Entity lv2 = lv.entity;
         if (lv2 != player && lv2.chunkX == chunk.getPos().x && lv2.chunkZ == chunk.getPos().z) {
            lv.updateCameraPosition(player);
            if (lv2 instanceof MobEntity && ((MobEntity)lv2).getHoldingEntity() != null) {
               list.add(lv2);
            }

            if (!lv2.getPassengerList().isEmpty()) {
               list2.add(lv2);
            }
         }
      }

      if (!list.isEmpty()) {
         for (Entity lv3 : list) {
            player.networkHandler.sendPacket(new EntityAttachS2CPacket(lv3, ((MobEntity)lv3).getHoldingEntity()));
         }
      }

      if (!list2.isEmpty()) {
         for (Entity lv4 : list2) {
            player.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(lv4));
         }
      }
   }

   protected PointOfInterestStorage getPointOfInterestStorage() {
      return this.pointOfInterestStorage;
   }

   public CompletableFuture<Void> enableTickSchedulers(WorldChunk arg) {
      return this.mainThreadExecutor.submit(() -> arg.enableTickSchedulers(this.world));
   }

   class EntityTracker {
      private final EntityTrackerEntry entry;
      private final Entity entity;
      private final int maxDistance;
      private ChunkSectionPos lastCameraPosition;
      private final Set<ServerPlayerEntity> playersTracking = Sets.newHashSet();

      public EntityTracker(Entity maxDistance, int tickInterval, int j, boolean bl) {
         this.entry = new EntityTrackerEntry(ThreadedAnvilChunkStorage.this.world, maxDistance, j, bl, this::sendToOtherNearbyPlayers);
         this.entity = maxDistance;
         this.maxDistance = tickInterval;
         this.lastCameraPosition = ChunkSectionPos.from(maxDistance);
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof ThreadedAnvilChunkStorage.EntityTracker
            ? ((ThreadedAnvilChunkStorage.EntityTracker)o).entity.getEntityId() == this.entity.getEntityId()
            : false;
      }

      @Override
      public int hashCode() {
         return this.entity.getEntityId();
      }

      public void sendToOtherNearbyPlayers(Packet<?> packet) {
         for (ServerPlayerEntity lv : this.playersTracking) {
            lv.networkHandler.sendPacket(packet);
         }
      }

      public void sendToNearbyPlayers(Packet<?> packet) {
         this.sendToOtherNearbyPlayers(packet);
         if (this.entity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)this.entity).networkHandler.sendPacket(packet);
         }
      }

      public void stopTracking() {
         for (ServerPlayerEntity lv : this.playersTracking) {
            this.entry.stopTracking(lv);
         }
      }

      public void stopTracking(ServerPlayerEntity arg) {
         if (this.playersTracking.remove(arg)) {
            this.entry.stopTracking(arg);
         }
      }

      public void updateCameraPosition(ServerPlayerEntity player) {
         if (player != this.entity) {
            Vec3d lv = player.getPos().subtract(this.entry.getLastPos());
            int i = Math.min(this.getMaxTrackDistance(), (ThreadedAnvilChunkStorage.this.watchDistance - 1) * 16);
            boolean bl = lv.x >= (double)(-i) && lv.x <= (double)i && lv.z >= (double)(-i) && lv.z <= (double)i && this.entity.canBeSpectated(player);
            if (bl) {
               boolean bl2 = this.entity.teleporting;
               if (!bl2) {
                  ChunkPos lv2 = new ChunkPos(this.entity.chunkX, this.entity.chunkZ);
                  ChunkHolder lv3 = ThreadedAnvilChunkStorage.this.getChunkHolder(lv2.toLong());
                  if (lv3 != null && lv3.getWorldChunk() != null) {
                     bl2 = ThreadedAnvilChunkStorage.getChebyshevDistance(lv2, player, false) <= ThreadedAnvilChunkStorage.this.watchDistance;
                  }
               }

               if (bl2 && this.playersTracking.add(player)) {
                  this.entry.startTracking(player);
               }
            } else if (this.playersTracking.remove(player)) {
               this.entry.stopTracking(player);
            }
         }
      }

      private int adjustTrackingDistance(int initialDistance) {
         return ThreadedAnvilChunkStorage.this.world.getServer().adjustTrackingDistance(initialDistance);
      }

      private int getMaxTrackDistance() {
         Collection<Entity> collection = this.entity.getPassengersDeep();
         int i = this.maxDistance;

         for (Entity lv : collection) {
            int j = lv.getType().getMaxTrackDistance() * 16;
            if (j > i) {
               i = j;
            }
         }

         return this.adjustTrackingDistance(i);
      }

      public void updateCameraPosition(List<ServerPlayerEntity> players) {
         for (ServerPlayerEntity lv : players) {
            this.updateCameraPosition(lv);
         }
      }
   }

   class TicketManager extends ChunkTicketManager {
      protected TicketManager(Executor mainThreadExecutor, Executor executor2) {
         super(mainThreadExecutor, executor2);
      }

      @Override
      protected boolean isUnloaded(long pos) {
         return ThreadedAnvilChunkStorage.this.unloadedChunks.contains(pos);
      }

      @Nullable
      @Override
      protected ChunkHolder getChunkHolder(long pos) {
         return ThreadedAnvilChunkStorage.this.getCurrentChunkHolder(pos);
      }

      @Nullable
      @Override
      protected ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int j) {
         return ThreadedAnvilChunkStorage.this.setLevel(pos, level, holder, j);
      }
   }
}
