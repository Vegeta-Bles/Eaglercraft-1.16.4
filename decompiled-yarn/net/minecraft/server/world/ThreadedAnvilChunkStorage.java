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
      ServerWorld _snowman,
      LevelStorage.Session _snowman,
      DataFixer dataFixer,
      StructureManager _snowman,
      Executor workerExecutor,
      ThreadExecutor<Runnable> mainThreadExecutor,
      ChunkProvider chunkProvider,
      ChunkGenerator chunkGenerator,
      WorldGenerationProgressListener worldGenerationProgressListener,
      Supplier<PersistentStateManager> _snowman,
      int _snowman,
      boolean _snowman
   ) {
      super(new File(_snowman.getWorldDirectory(_snowman.getRegistryKey()), "region"), dataFixer, _snowman);
      this.structureManager = _snowman;
      this.saveDir = _snowman.getWorldDirectory(_snowman.getRegistryKey());
      this.world = _snowman;
      this.chunkGenerator = chunkGenerator;
      this.mainThreadExecutor = mainThreadExecutor;
      TaskExecutor<Runnable> _snowmanxxxxxx = TaskExecutor.create(workerExecutor, "worldgen");
      MessageListener<Runnable> _snowmanxxxxxxx = MessageListener.create("main", mainThreadExecutor::send);
      this.worldGenerationProgressListener = worldGenerationProgressListener;
      TaskExecutor<Runnable> _snowmanxxxxxxxx = TaskExecutor.create(workerExecutor, "light");
      this.chunkTaskPrioritySystem = new ChunkTaskPrioritySystem(ImmutableList.of(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx), workerExecutor, Integer.MAX_VALUE);
      this.worldGenExecutor = this.chunkTaskPrioritySystem.createExecutor(_snowmanxxxxxx, false);
      this.mainExecutor = this.chunkTaskPrioritySystem.createExecutor(_snowmanxxxxxxx, false);
      this.serverLightingProvider = new ServerLightingProvider(
         chunkProvider, this, this.world.getDimension().hasSkyLight(), _snowmanxxxxxxxx, this.chunkTaskPrioritySystem.createExecutor(_snowmanxxxxxxxx, false)
      );
      this.ticketManager = new ThreadedAnvilChunkStorage.TicketManager(workerExecutor, mainThreadExecutor);
      this.persistentStateManagerFactory = _snowman;
      this.pointOfInterestStorage = new PointOfInterestStorage(new File(this.saveDir, "poi"), dataFixer, _snowman);
      this.setViewDistance(_snowman);
   }

   private static double getSquaredDistance(ChunkPos pos, Entity entity) {
      double _snowman = (double)(pos.x * 16 + 8);
      double _snowmanx = (double)(pos.z * 16 + 8);
      double _snowmanxx = _snowman - entity.getX();
      double _snowmanxxx = _snowmanx - entity.getZ();
      return _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
   }

   private static int getChebyshevDistance(ChunkPos pos, ServerPlayerEntity player, boolean useCameraPosition) {
      int _snowman;
      int _snowmanx;
      if (useCameraPosition) {
         ChunkSectionPos _snowmanxx = player.getCameraPosition();
         _snowman = _snowmanxx.getSectionX();
         _snowmanx = _snowmanxx.getSectionZ();
      } else {
         _snowman = MathHelper.floor(player.getX() / 16.0);
         _snowmanx = MathHelper.floor(player.getZ() / 16.0);
      }

      return getChebyshevDistance(pos, _snowman, _snowmanx);
   }

   private static int getChebyshevDistance(ChunkPos pos, int x, int z) {
      int _snowman = pos.x - x;
      int _snowmanx = pos.z - z;
      return Math.max(Math.abs(_snowman), Math.abs(_snowmanx));
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
         ChunkHolder _snowmanx = this.getChunkHolder(pos);
         return _snowmanx == null ? LevelPrioritizedQueue.LEVEL_COUNT - 1 : Math.min(_snowmanx.getCompletedLevel(), LevelPrioritizedQueue.LEVEL_COUNT - 1);
      };
   }

   public String getChunkLoadingDebugInfo(ChunkPos _snowman) {
      ChunkHolder _snowmanx = this.getChunkHolder(_snowman.toLong());
      if (_snowmanx == null) {
         return "null";
      } else {
         String _snowmanxx = _snowmanx.getLevel() + "\n";
         ChunkStatus _snowmanxxx = _snowmanx.getCurrentStatus();
         Chunk _snowmanxxxx = _snowmanx.getCurrentChunk();
         if (_snowmanxxx != null) {
            _snowmanxx = _snowmanxx + "St: §" + _snowmanxxx.getIndex() + _snowmanxxx + '§' + "r\n";
         }

         if (_snowmanxxxx != null) {
            _snowmanxx = _snowmanxx + "Ch: §" + _snowmanxxxx.getStatus().getIndex() + _snowmanxxxx.getStatus() + '§' + "r\n";
         }

         ChunkHolder.LevelType _snowmanxxxxx = _snowmanx.getLevelType();
         _snowmanxx = _snowmanxx + "§" + _snowmanxxxxx.ordinal() + _snowmanxxxxx;
         return _snowmanxx + '§' + "r";
      }
   }

   private CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> getRegion(ChunkPos centerChunk, int margin, IntFunction<ChunkStatus> distanceToStatus) {
      List<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>> _snowman = Lists.newArrayList();
      int _snowmanx = centerChunk.x;
      int _snowmanxx = centerChunk.z;

      for (int _snowmanxxx = -margin; _snowmanxxx <= margin; _snowmanxxx++) {
         for (int _snowmanxxxx = -margin; _snowmanxxxx <= margin; _snowmanxxxx++) {
            int _snowmanxxxxx = Math.max(Math.abs(_snowmanxxxx), Math.abs(_snowmanxxx));
            final ChunkPos _snowmanxxxxxx = new ChunkPos(_snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxx);
            long _snowmanxxxxxxx = _snowmanxxxxxx.toLong();
            ChunkHolder _snowmanxxxxxxxx = this.getCurrentChunkHolder(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx == null) {
               return CompletableFuture.completedFuture(Either.right(new ChunkHolder.Unloaded() {
                  @Override
                  public String toString() {
                     return "Unloaded " + _snowman.toString();
                  }
               }));
            }

            ChunkStatus _snowmanxxxxxxxxx = distanceToStatus.apply(_snowmanxxxxx);
            CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getChunkAt(_snowmanxxxxxxxxx, this);
            _snowman.add(_snowmanxxxxxxxxxx);
         }
      }

      CompletableFuture<List<Either<Chunk, ChunkHolder.Unloaded>>> _snowmanxxx = Util.combine(_snowman);
      return _snowmanxxx.thenApply(_snowmanxxxx -> {
         List<Chunk> _snowmanxxxxx = Lists.newArrayList();
         int _snowmanx = 0;

         for (final Either<Chunk, ChunkHolder.Unloaded> _snowmanxx : _snowmanxxxx) {
            Optional<Chunk> _snowmanxxxx = _snowmanxx.left();
            if (!_snowmanxxxx.isPresent()) {
               final int _snowmanxxxxx = _snowmanx;
               return Either.right(new ChunkHolder.Unloaded() {
                  @Override
                  public String toString() {
                     return "Unloaded " + new ChunkPos(_snowman + _snowman % (_snowman * 2 + 1), _snowman + _snowman / (_snowman * 2 + 1)) + " " + ((ChunkHolder.Unloaded)_snowman.right().get()).toString();
                  }
               });
            }

            _snowmanxxxxx.add(_snowmanxxxx.get());
            _snowmanx++;
         }

         return Either.left(_snowmanxxxxx);
      });
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkEntitiesTickable(ChunkPos pos) {
      return this.getRegion(pos, 2, _snowman -> ChunkStatus.FULL).thenApplyAsync(_snowman -> _snowman.mapLeft(_snowmanx -> (WorldChunk)_snowmanx.get(_snowmanx.size() / 2)), this.mainThreadExecutor);
   }

   @Nullable
   private ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int _snowman) {
      if (_snowman > MAX_LEVEL && level > MAX_LEVEL) {
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
         List<ChunkHolder> _snowman = this.chunkHolders
            .values()
            .stream()
            .filter(ChunkHolder::isAccessible)
            .peek(ChunkHolder::updateAccessibleStatus)
            .collect(Collectors.toList());
         MutableBoolean _snowmanx = new MutableBoolean();

         do {
            _snowmanx.setFalse();
            _snowman.stream().map(_snowmanxx -> {
               CompletableFuture<Chunk> _snowmanx;
               do {
                  _snowmanx = _snowmanxx.getSavingFuture();
                  this.mainThreadExecutor.runTasks(_snowmanx::isDone);
               } while (_snowmanx != _snowmanxx.getSavingFuture());

               return _snowmanx.join();
            }).filter(_snowmanxx -> _snowmanxx instanceof ReadOnlyChunk || _snowmanxx instanceof WorldChunk).filter(this::save).forEach(_snowmanxx -> _snowman.setTrue());
         } while (_snowmanx.isTrue());

         this.unloadChunks(() -> true);
         this.completeAll();
         LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.saveDir.getName());
      } else {
         this.chunkHolders.values().stream().filter(ChunkHolder::isAccessible).forEach(_snowman -> {
            Chunk _snowmanx = _snowman.getSavingFuture().getNow(null);
            if (_snowmanx instanceof ReadOnlyChunk || _snowmanx instanceof WorldChunk) {
               this.save(_snowmanx);
               _snowman.updateAccessibleStatus();
            }
         });
      }
   }

   protected void tick(BooleanSupplier shouldKeepTicking) {
      Profiler _snowman = this.world.getProfiler();
      _snowman.push("poi");
      this.pointOfInterestStorage.tick(shouldKeepTicking);
      _snowman.swap("chunk_unload");
      if (!this.world.isSavingDisabled()) {
         this.unloadChunks(shouldKeepTicking);
      }

      _snowman.pop();
   }

   private void unloadChunks(BooleanSupplier shouldKeepTicking) {
      LongIterator _snowman = this.unloadedChunks.iterator();

      for (int _snowmanx = 0; _snowman.hasNext() && (shouldKeepTicking.getAsBoolean() || _snowmanx < 200 || this.unloadedChunks.size() > 2000); _snowman.remove()) {
         long _snowmanxx = _snowman.nextLong();
         ChunkHolder _snowmanxxx = (ChunkHolder)this.currentChunkHolders.remove(_snowmanxx);
         if (_snowmanxxx != null) {
            this.chunksToUnload.put(_snowmanxx, _snowmanxxx);
            this.chunkHolderListDirty = true;
            _snowmanx++;
            this.tryUnloadChunk(_snowmanxx, _snowmanxxx);
         }
      }

      Runnable _snowmanxx;
      while ((shouldKeepTicking.getAsBoolean() || this.unloadTaskQueue.size() > 2000) && (_snowmanxx = this.unloadTaskQueue.poll()) != null) {
         _snowmanxx.run();
      }
   }

   private void tryUnloadChunk(long pos, ChunkHolder _snowman) {
      CompletableFuture<Chunk> _snowmanx = _snowman.getSavingFuture();
      _snowmanx.thenAcceptAsync(_snowmanxxx -> {
         CompletableFuture<Chunk> _snowmanxxxx = _snowman.getSavingFuture();
         if (_snowmanxxxx != _snowman) {
            this.tryUnloadChunk(pos, _snowman);
         } else {
            if (this.chunksToUnload.remove(pos, _snowman) && _snowmanxxx != null) {
               if (_snowmanxxx instanceof WorldChunk) {
                  ((WorldChunk)_snowmanxxx).setLoadedToWorld(false);
               }

               this.save(_snowmanxxx);
               if (this.loadedChunks.remove(pos) && _snowmanxxx instanceof WorldChunk) {
                  WorldChunk _snowmanxxx = (WorldChunk)_snowmanxxx;
                  this.world.unloadEntities(_snowmanxxx);
               }

               this.serverLightingProvider.updateChunkStatus(_snowmanxxx.getPos());
               this.serverLightingProvider.tick();
               this.worldGenerationProgressListener.setChunkStatus(_snowmanxxx.getPos(), null);
            }
         }
      }, this.unloadTaskQueue::add).whenComplete((_snowmanxxx, _snowmanxx) -> {
         if (_snowmanxx != null) {
            LOGGER.error("Failed to save chunk " + _snowman.getPos(), _snowmanxx);
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
      ChunkPos _snowman = holder.getPos();
      if (requiredStatus == ChunkStatus.EMPTY) {
         return this.loadChunk(_snowman);
      } else {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanx = holder.getChunkAt(requiredStatus.getPrevious(), this);
         return _snowmanx.thenComposeAsync(
            _snowmanxxx -> {
               Optional<Chunk> _snowmanxx = _snowmanxxx.left();
               if (!_snowmanxx.isPresent()) {
                  return CompletableFuture.completedFuture((Either<Chunk, ChunkHolder.Unloaded>)_snowmanxxx);
               } else {
                  if (requiredStatus == ChunkStatus.LIGHT) {
                     this.ticketManager.addTicketWithLevel(ChunkTicketType.LIGHT, _snowman, 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FEATURES), _snowman);
                  }

                  Chunk _snowmanx = _snowmanxx.get();
                  if (_snowmanx.getStatus().isAtLeast(requiredStatus)) {
                     CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxx;
                     if (requiredStatus == ChunkStatus.LIGHT) {
                        _snowmanxx = this.upgradeChunk(holder, requiredStatus);
                     } else {
                        _snowmanxx = requiredStatus.runLoadTask(
                           this.world, this.structureManager, this.serverLightingProvider, _snowmanxxxxx -> this.convertToFullChunk(holder), _snowmanx
                        );
                     }

                     this.worldGenerationProgressListener.setChunkStatus(_snowman, requiredStatus);
                     return _snowmanxx;
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
            CompoundTag _snowmanx = this.getUpdatedChunkTag(pos);
            if (_snowmanx != null) {
               boolean _snowmanx = _snowmanx.contains("Level", 10) && _snowmanx.getCompound("Level").contains("Status", 8);
               if (_snowmanx) {
                  Chunk _snowmanxx = ChunkSerializer.deserialize(this.world, this.structureManager, this.pointOfInterestStorage, pos, _snowmanx);
                  _snowmanxx.setLastSaveTime(this.world.getTime());
                  this.method_27053(pos, _snowmanxx.getStatus().getChunkType());
                  return Either.left(_snowmanxx);
               }

               LOGGER.error("Chunk file at {} is missing level data, skipping", pos);
            }
         } catch (CrashException var5) {
            Throwable _snowmanx = var5.getCause();
            if (!(_snowmanx instanceof IOException)) {
               this.method_27054(pos);
               throw var5;
            }

            LOGGER.error("Couldn't load chunk {}", pos, _snowmanx);
         } catch (Exception var6) {
            LOGGER.error("Couldn't load chunk {}", pos, var6);
         }

         this.method_27054(pos);
         return Either.left(new ProtoChunk(pos, UpgradeData.NO_UPGRADE_DATA));
      }, this.mainThreadExecutor);
   }

   private void method_27054(ChunkPos _snowman) {
      this.chunkToType.put(_snowman.toLong(), (byte)-1);
   }

   private byte method_27053(ChunkPos _snowman, ChunkStatus.ChunkType _snowman) {
      return this.chunkToType.put(_snowman.toLong(), (byte)(_snowman == ChunkStatus.ChunkType.field_12808 ? -1 : 1));
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> upgradeChunk(ChunkHolder holder, ChunkStatus requiredStatus) {
      ChunkPos _snowman = holder.getPos();
      CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> _snowmanx = this.getRegion(
         _snowman, requiredStatus.getTaskMargin(), _snowmanxx -> this.getRequiredStatusForGeneration(requiredStatus, _snowmanxx)
      );
      this.world.getProfiler().visit(() -> "chunkGenerate " + requiredStatus.getId());
      return _snowmanx.thenComposeAsync(
         _snowmanxxx -> (CompletableFuture)_snowmanxxx.map(
               _snowmanxxxxxxx -> {
                  try {
                     CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxxxx = requiredStatus.runGenerationTask(
                        this.world,
                        this.chunkGenerator,
                        this.structureManager,
                        this.serverLightingProvider,
                        _snowmanxxxxxxxxxxx -> this.convertToFullChunk(holder),
                        _snowmanxxxxxxx
                     );
                     this.worldGenerationProgressListener.setChunkStatus(_snowman, requiredStatus);
                     return _snowmanxxxx;
                  } catch (Exception var8) {
                     CrashReport _snowmanxxxxx = CrashReport.create(var8, "Exception generating new chunk");
                     CrashReportSection _snowmanxxxxxx = _snowmanxxxxx.addElement("Chunk to be generated");
                     _snowmanxxxxxx.add("Location", String.format("%d,%d", _snowman.x, _snowman.z));
                     _snowmanxxxxxx.add("Position hash", ChunkPos.toLong(_snowman.x, _snowman.z));
                     _snowmanxxxxxx.add("Generator", this.chunkGenerator);
                     throw new CrashException(_snowmanxxxxx);
                  }
               },
               _snowmanxxxx -> {
                  this.releaseLightTicket(_snowman);
                  return CompletableFuture.completedFuture(Either.right(_snowmanxxxx));
               }
            ),
         _snowmanxx -> this.worldGenExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, _snowmanxx))
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
      ChunkStatus _snowman;
      if (distance == 0) {
         _snowman = centerChunkTargetStatus.getPrevious();
      } else {
         _snowman = ChunkStatus.byDistanceFromFull(ChunkStatus.getDistanceFromFull(centerChunkTargetStatus) + distance);
      }

      return _snowman;
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> convertToFullChunk(ChunkHolder _snowman) {
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanx = _snowman.getFutureFor(ChunkStatus.FULL.getPrevious());
      return _snowmanx.thenApplyAsync(_snowmanxx -> {
         ChunkStatus _snowmanxx = ChunkHolder.getTargetStatusForLevel(_snowman.getLevel());
         return !_snowmanxx.isAtLeast(ChunkStatus.FULL) ? ChunkHolder.UNLOADED_CHUNK : _snowmanxx.mapLeft(_snowmanxxxxxxxx -> {
            ChunkPos _snowmanxx = _snowman.getPos();
            WorldChunk _snowmanxxx;
            if (_snowmanxxxxxxxx instanceof ReadOnlyChunk) {
               _snowmanxxx = ((ReadOnlyChunk)_snowmanxxxxxxxx).getWrappedChunk();
            } else {
               _snowmanxxx = new WorldChunk(this.world, (ProtoChunk)_snowmanxxxxxxxx);
               _snowman.setCompletedChunk(new ReadOnlyChunk(_snowmanxxx));
            }

            _snowmanxxx.setLevelTypeProvider(() -> ChunkHolder.getLevelType(_snowman.getLevel()));
            _snowmanxxx.loadToWorld();
            if (this.loadedChunks.add(_snowmanxx.toLong())) {
               _snowmanxxx.setLoadedToWorld(true);
               this.world.addBlockEntities(_snowmanxxx.getBlockEntities().values());
               List<Entity> _snowmanxxxx = null;
               TypeFilterableList[] var6 = _snowmanxxx.getEntitySectionArray();
               int var7 = var6.length;

               for (int var8 = 0; var8 < var7; var8++) {
                  for (Entity _snowmanxxxxx : var6[var8]) {
                     if (!(_snowmanxxxxx instanceof PlayerEntity) && !this.world.loadEntity(_snowmanxxxxx)) {
                        if (_snowmanxxxx == null) {
                           _snowmanxxxx = Lists.newArrayList(new Entity[]{_snowmanxxxxx});
                        } else {
                           _snowmanxxxx.add(_snowmanxxxxx);
                        }
                     }
                  }
               }

               if (_snowmanxxxx != null) {
                  _snowmanxxxx.forEach(_snowmanxxx::remove);
               }
            }

            return _snowmanxxx;
         });
      }, _snowmanxx -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(_snowmanxx, _snowman.getPos().toLong(), _snowman::getLevel)));
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkTickable(ChunkHolder holder) {
      ChunkPos _snowman = holder.getPos();
      CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> _snowmanx = this.getRegion(_snowman, 1, _snowmanxx -> ChunkStatus.FULL);
      CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowmanxx = _snowmanx.thenApplyAsync(_snowmanxxx -> _snowmanxxx.flatMap(_snowmanxxxx -> {
            WorldChunk _snowmanxxxxx = (WorldChunk)_snowmanxxxx.get(_snowmanxxxx.size() / 2);
            _snowmanxxxxx.runPostProcessing();
            return Either.left(_snowmanxxxxx);
         }), _snowmanxxx -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, _snowmanxxx)));
      _snowmanxx.thenAcceptAsync(_snowmanxxx -> _snowmanxxx.mapLeft(_snowmanxxxx -> {
            this.totalChunksLoadedCount.getAndIncrement();
            Packet<?>[] _snowmanxxxxx = new Packet[2];
            this.getPlayersWatchingChunk(_snowman, false).forEach(_snowmanxxxxxx -> this.sendChunkDataPackets(_snowmanxxxxxx, _snowmanxx, _snowmanxxx));
            return Either.left(_snowmanxxxx);
         }), _snowmanxxx -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, _snowmanxxx)));
      return _snowmanxx;
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkAccessible(ChunkHolder holder) {
      return holder.getChunkAt(ChunkStatus.FULL, this).thenApplyAsync(_snowman -> _snowman.mapLeft(_snowmanx -> {
            WorldChunk _snowmanx = (WorldChunk)_snowmanx;
            _snowmanx.disableTickSchedulers();
            return _snowmanx;
         }), _snowmanx -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, _snowmanx)));
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
         ChunkPos _snowman = chunk.getPos();

         try {
            ChunkStatus _snowmanx = chunk.getStatus();
            if (_snowmanx.getChunkType() != ChunkStatus.ChunkType.field_12807) {
               if (this.method_27055(_snowman)) {
                  return false;
               }

               if (_snowmanx == ChunkStatus.EMPTY && chunk.getStructureStarts().values().stream().noneMatch(StructureStart::hasChildren)) {
                  return false;
               }
            }

            this.world.getProfiler().visit("chunkSave");
            CompoundTag _snowmanxx = ChunkSerializer.serialize(this.world, chunk);
            this.setTagAt(_snowman, _snowmanxx);
            this.method_27053(_snowman, _snowmanx.getChunkType());
            return true;
         } catch (Exception var5) {
            LOGGER.error("Failed to save chunk {},{}", _snowman.x, _snowman.z, var5);
            return false;
         }
      }
   }

   private boolean method_27055(ChunkPos _snowman) {
      byte _snowmanx = this.chunkToType.get(_snowman.toLong());
      if (_snowmanx != 0) {
         return _snowmanx == 1;
      } else {
         CompoundTag _snowmanxx;
         try {
            _snowmanxx = this.getUpdatedChunkTag(_snowman);
            if (_snowmanxx == null) {
               this.method_27054(_snowman);
               return false;
            }
         } catch (Exception var5) {
            LOGGER.error("Failed to read chunk {}", _snowman, var5);
            this.method_27054(_snowman);
            return false;
         }

         ChunkStatus.ChunkType _snowmanxxx = ChunkSerializer.getChunkType(_snowmanxx);
         return this.method_27053(_snowman, _snowmanxxx) == 1;
      }
   }

   protected void setViewDistance(int watchDistance) {
      int _snowman = MathHelper.clamp(watchDistance + 1, 3, 33);
      if (_snowman != this.watchDistance) {
         int _snowmanx = this.watchDistance;
         this.watchDistance = _snowman;
         this.ticketManager.setWatchDistance(this.watchDistance);
         ObjectIterator var4 = this.currentChunkHolders.values().iterator();

         while (var4.hasNext()) {
            ChunkHolder _snowmanxx = (ChunkHolder)var4.next();
            ChunkPos _snowmanxxx = _snowmanxx.getPos();
            Packet<?>[] _snowmanxxxx = new Packet[2];
            this.getPlayersWatchingChunk(_snowmanxxx, false).forEach(_snowmanxxxxx -> {
               int _snowmanxxxxxx = getChebyshevDistance(_snowman, _snowmanxxxxx, true);
               boolean _snowmanx = _snowmanxxxxxx <= _snowman;
               boolean _snowmanxx = _snowmanxxxxxx <= this.watchDistance;
               this.sendWatchPackets(_snowmanxxxxx, _snowman, _snowman, _snowmanx, _snowmanxx);
            });
         }
      }
   }

   protected void sendWatchPackets(ServerPlayerEntity player, ChunkPos pos, Packet<?>[] packets, boolean withinMaxWatchDistance, boolean withinViewDistance) {
      if (player.world == this.world) {
         if (withinViewDistance && !withinMaxWatchDistance) {
            ChunkHolder _snowman = this.getChunkHolder(pos.toLong());
            if (_snowman != null) {
               WorldChunk _snowmanx = _snowman.getWorldChunk();
               if (_snowmanx != null) {
                  this.sendChunkDataPackets(player, packets, _snowmanx);
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

   void dump(Writer _snowman) throws IOException {
      CsvWriter _snowmanx = CsvWriter.makeHeader()
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
         .startBody(_snowman);
      ObjectBidirectionalIterator var3 = this.chunkHolders.long2ObjectEntrySet().iterator();

      while (var3.hasNext()) {
         Entry<ChunkHolder> _snowmanxx = (Entry<ChunkHolder>)var3.next();
         ChunkPos _snowmanxxx = new ChunkPos(_snowmanxx.getLongKey());
         ChunkHolder _snowmanxxxx = (ChunkHolder)_snowmanxx.getValue();
         Optional<Chunk> _snowmanxxxxx = Optional.ofNullable(_snowmanxxxx.getCurrentChunk());
         Optional<WorldChunk> _snowmanxxxxxx = _snowmanxxxxx.flatMap(_snowmanxxxxxxx -> _snowmanxxxxxxx instanceof WorldChunk ? Optional.of((WorldChunk)_snowmanxxxxxxx) : Optional.empty());
         _snowmanx.printRow(
            _snowmanxxx.x,
            _snowmanxxx.z,
            _snowmanxxxx.getLevel(),
            _snowmanxxxxx.isPresent(),
            _snowmanxxxxx.map(Chunk::getStatus).orElse(null),
            _snowmanxxxxxx.map(WorldChunk::getLevelType).orElse(null),
            getFutureStatus(_snowmanxxxx.getAccessibleFuture()),
            getFutureStatus(_snowmanxxxx.getTickingFuture()),
            getFutureStatus(_snowmanxxxx.getEntityTickingFuture()),
            this.ticketManager.getTicket(_snowmanxx.getLongKey()),
            !this.isTooFarFromPlayersToSpawnMobs(_snowmanxxx),
            _snowmanxxxxxx.<Integer>map(_snowmanxxxxxxx -> Stream.of(_snowmanxxxxxxx.getEntitySectionArray()).mapToInt(TypeFilterableList::size).sum()).orElse(0),
            _snowmanxxxxxx.<Integer>map(_snowmanxxxxxxx -> _snowmanxxxxxxx.getBlockEntities().size()).orElse(0)
         );
      }
   }

   private static String getFutureStatus(CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowman) {
      try {
         Either<WorldChunk, ChunkHolder.Unloaded> _snowmanx = _snowman.getNow(null);
         return _snowmanx != null ? (String)_snowmanx.map(_snowmanxx -> "done", _snowmanxx -> "unloaded") : "not completed";
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   @Nullable
   private CompoundTag getUpdatedChunkTag(ChunkPos pos) throws IOException {
      CompoundTag _snowman = this.getNbt(pos);
      return _snowman == null ? null : this.updateChunkTag(this.world.getRegistryKey(), this.persistentStateManagerFactory, _snowman);
   }

   boolean isTooFarFromPlayersToSpawnMobs(ChunkPos _snowman) {
      long _snowmanx = _snowman.toLong();
      return !this.ticketManager.method_20800(_snowmanx)
         ? true
         : this.playerChunkWatchingManager.getPlayersWatchingChunk(_snowmanx).noneMatch(_snowmanxx -> !_snowmanxx.isSpectator() && getSquaredDistance(_snowman, _snowmanxx) < 16384.0);
   }

   private boolean doesNotGenerateChunks(ServerPlayerEntity player) {
      return player.isSpectator() && !this.world.getGameRules().getBoolean(GameRules.SPECTATORS_GENERATE_CHUNKS);
   }

   void handlePlayerAddedOrRemoved(ServerPlayerEntity player, boolean added) {
      boolean _snowman = this.doesNotGenerateChunks(player);
      boolean _snowmanx = this.playerChunkWatchingManager.method_21715(player);
      int _snowmanxx = MathHelper.floor(player.getX()) >> 4;
      int _snowmanxxx = MathHelper.floor(player.getZ()) >> 4;
      if (added) {
         this.playerChunkWatchingManager.add(ChunkPos.toLong(_snowmanxx, _snowmanxxx), player, _snowman);
         this.method_20726(player);
         if (!_snowman) {
            this.ticketManager.handleChunkEnter(ChunkSectionPos.from(player), player);
         }
      } else {
         ChunkSectionPos _snowmanxxxx = player.getCameraPosition();
         this.playerChunkWatchingManager.remove(_snowmanxxxx.toChunkPos().toLong(), player);
         if (!_snowmanx) {
            this.ticketManager.handleChunkLeave(_snowmanxxxx, player);
         }
      }

      for (int _snowmanxxxxx = _snowmanxx - this.watchDistance; _snowmanxxxxx <= _snowmanxx + this.watchDistance; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowmanxxx - this.watchDistance; _snowmanxxxxxx <= _snowmanxxx + this.watchDistance; _snowmanxxxxxx++) {
            ChunkPos _snowmanxxxxxxx = new ChunkPos(_snowmanxxxxx, _snowmanxxxxxx);
            this.sendWatchPackets(player, _snowmanxxxxxxx, new Packet[2], !added, added);
         }
      }
   }

   private ChunkSectionPos method_20726(ServerPlayerEntity _snowman) {
      ChunkSectionPos _snowmanx = ChunkSectionPos.from(_snowman);
      _snowman.setCameraPosition(_snowmanx);
      _snowman.networkHandler.sendPacket(new ChunkRenderDistanceCenterS2CPacket(_snowmanx.getSectionX(), _snowmanx.getSectionZ()));
      return _snowmanx;
   }

   public void updateCameraPosition(ServerPlayerEntity player) {
      ObjectIterator var2 = this.entityTrackers.values().iterator();

      while (var2.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker _snowman = (ThreadedAnvilChunkStorage.EntityTracker)var2.next();
         if (_snowman.entity == player) {
            _snowman.updateCameraPosition(this.world.getPlayers());
         } else {
            _snowman.updateCameraPosition(player);
         }
      }

      int _snowman = MathHelper.floor(player.getX()) >> 4;
      int _snowmanx = MathHelper.floor(player.getZ()) >> 4;
      ChunkSectionPos _snowmanxx = player.getCameraPosition();
      ChunkSectionPos _snowmanxxx = ChunkSectionPos.from(player);
      long _snowmanxxxx = _snowmanxx.toChunkPos().toLong();
      long _snowmanxxxxx = _snowmanxxx.toChunkPos().toLong();
      boolean _snowmanxxxxxx = this.playerChunkWatchingManager.isWatchDisabled(player);
      boolean _snowmanxxxxxxx = this.doesNotGenerateChunks(player);
      boolean _snowmanxxxxxxxx = _snowmanxx.asLong() != _snowmanxxx.asLong();
      if (_snowmanxxxxxxxx || _snowmanxxxxxx != _snowmanxxxxxxx) {
         this.method_20726(player);
         if (!_snowmanxxxxxx) {
            this.ticketManager.handleChunkLeave(_snowmanxx, player);
         }

         if (!_snowmanxxxxxxx) {
            this.ticketManager.handleChunkEnter(_snowmanxxx, player);
         }

         if (!_snowmanxxxxxx && _snowmanxxxxxxx) {
            this.playerChunkWatchingManager.disableWatch(player);
         }

         if (_snowmanxxxxxx && !_snowmanxxxxxxx) {
            this.playerChunkWatchingManager.enableWatch(player);
         }

         if (_snowmanxxxx != _snowmanxxxxx) {
            this.playerChunkWatchingManager.movePlayer(_snowmanxxxx, _snowmanxxxxx, player);
         }
      }

      int _snowmanxxxxxxxxx = _snowmanxx.getSectionX();
      int _snowmanxxxxxxxxxx = _snowmanxx.getSectionZ();
      if (Math.abs(_snowmanxxxxxxxxx - _snowman) <= this.watchDistance * 2 && Math.abs(_snowmanxxxxxxxxxx - _snowmanx) <= this.watchDistance * 2) {
         int _snowmanxxxxxxxxxxx = Math.min(_snowman, _snowmanxxxxxxxxx) - this.watchDistance;
         int _snowmanxxxxxxxxxxxx = Math.min(_snowmanx, _snowmanxxxxxxxxxx) - this.watchDistance;
         int _snowmanxxxxxxxxxxxxx = Math.max(_snowman, _snowmanxxxxxxxxx) + this.watchDistance;
         int _snowmanxxxxxxxxxxxxxx = Math.max(_snowmanx, _snowmanxxxxxxxxxx) + this.watchDistance;

         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxxxxxx = getChebyshevDistance(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx) <= this.watchDistance;
               boolean _snowmanxxxxxxxxxxxxxxxxxxx = getChebyshevDistance(_snowmanxxxxxxxxxxxxxxxxx, _snowman, _snowmanx) <= this.watchDistance;
               this.sendWatchPackets(player, _snowmanxxxxxxxxxxxxxxxxx, new Packet[2], _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
            }
         }
      } else {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - this.watchDistance; _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxx + this.watchDistance; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - this.watchDistance; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxxxx + this.watchDistance; _snowmanxxxxxxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxx = true;
               boolean _snowmanxxxxxxxxxxxxxxx = false;
               this.sendWatchPackets(player, _snowmanxxxxxxxxxxxxx, new Packet[2], true, false);
            }
         }

         for (int _snowmanxxxxxxxxxxx = _snowman - this.watchDistance; _snowmanxxxxxxxxxxx <= _snowman + this.watchDistance; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanx - this.watchDistance; _snowmanxxxxxxxxxxxx <= _snowmanx + this.watchDistance; _snowmanxxxxxxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxx = false;
               boolean _snowmanxxxxxxxxxxxxxxx = true;
               this.sendWatchPackets(player, _snowmanxxxxxxxxxxxxx, new Packet[2], false, true);
            }
         }
      }
   }

   @Override
   public Stream<ServerPlayerEntity> getPlayersWatchingChunk(ChunkPos chunkPos, boolean onlyOnWatchDistanceEdge) {
      return this.playerChunkWatchingManager.getPlayersWatchingChunk(chunkPos.toLong()).filter(_snowmanxx -> {
         int _snowmanxxx = getChebyshevDistance(chunkPos, _snowmanxx, true);
         return _snowmanxxx > this.watchDistance ? false : !onlyOnWatchDistanceEdge || _snowmanxxx == this.watchDistance;
      });
   }

   protected void loadEntity(Entity _snowman) {
      if (!(_snowman instanceof EnderDragonPart)) {
         EntityType<?> _snowmanx = _snowman.getType();
         int _snowmanxx = _snowmanx.getMaxTrackDistance() * 16;
         int _snowmanxxx = _snowmanx.getTrackTickInterval();
         if (this.entityTrackers.containsKey(_snowman.getEntityId())) {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Entity is already tracked!"));
         } else {
            ThreadedAnvilChunkStorage.EntityTracker _snowmanxxxx = new ThreadedAnvilChunkStorage.EntityTracker(_snowman, _snowmanxx, _snowmanxxx, _snowmanx.alwaysUpdateVelocity());
            this.entityTrackers.put(_snowman.getEntityId(), _snowmanxxxx);
            _snowmanxxxx.updateCameraPosition(this.world.getPlayers());
            if (_snowman instanceof ServerPlayerEntity) {
               ServerPlayerEntity _snowmanxxxxx = (ServerPlayerEntity)_snowman;
               this.handlePlayerAddedOrRemoved(_snowmanxxxxx, true);
               ObjectIterator var7 = this.entityTrackers.values().iterator();

               while (var7.hasNext()) {
                  ThreadedAnvilChunkStorage.EntityTracker _snowmanxxxxxx = (ThreadedAnvilChunkStorage.EntityTracker)var7.next();
                  if (_snowmanxxxxxx.entity != _snowmanxxxxx) {
                     _snowmanxxxxxx.updateCameraPosition(_snowmanxxxxx);
                  }
               }
            }
         }
      }
   }

   protected void unloadEntity(Entity _snowman) {
      if (_snowman instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowmanx = (ServerPlayerEntity)_snowman;
         this.handlePlayerAddedOrRemoved(_snowmanx, false);
         ObjectIterator var3 = this.entityTrackers.values().iterator();

         while (var3.hasNext()) {
            ThreadedAnvilChunkStorage.EntityTracker _snowmanxx = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
            _snowmanxx.stopTracking(_snowmanx);
         }
      }

      ThreadedAnvilChunkStorage.EntityTracker _snowmanx = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.remove(_snowman.getEntityId());
      if (_snowmanx != null) {
         _snowmanx.stopTracking();
      }
   }

   protected void tickPlayerMovement() {
      List<ServerPlayerEntity> _snowman = Lists.newArrayList();
      List<ServerPlayerEntity> _snowmanx = this.world.getPlayers();
      ObjectIterator var3 = this.entityTrackers.values().iterator();

      while (var3.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker _snowmanxx = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
         ChunkSectionPos _snowmanxxx = _snowmanxx.lastCameraPosition;
         ChunkSectionPos _snowmanxxxx = ChunkSectionPos.from(_snowmanxx.entity);
         if (!Objects.equals(_snowmanxxx, _snowmanxxxx)) {
            _snowmanxx.updateCameraPosition(_snowmanx);
            Entity _snowmanxxxxx = _snowmanxx.entity;
            if (_snowmanxxxxx instanceof ServerPlayerEntity) {
               _snowman.add((ServerPlayerEntity)_snowmanxxxxx);
            }

            _snowmanxx.lastCameraPosition = _snowmanxxxx;
         }

         _snowmanxx.entry.tick();
      }

      if (!_snowman.isEmpty()) {
         var3 = this.entityTrackers.values().iterator();

         while (var3.hasNext()) {
            ThreadedAnvilChunkStorage.EntityTracker _snowmanxx = (ThreadedAnvilChunkStorage.EntityTracker)var3.next();
            _snowmanxx.updateCameraPosition(_snowman);
         }
      }
   }

   protected void sendToOtherNearbyPlayers(Entity entity, Packet<?> packet) {
      ThreadedAnvilChunkStorage.EntityTracker _snowman = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.get(entity.getEntityId());
      if (_snowman != null) {
         _snowman.sendToOtherNearbyPlayers(packet);
      }
   }

   protected void sendToNearbyPlayers(Entity entity, Packet<?> packet) {
      ThreadedAnvilChunkStorage.EntityTracker _snowman = (ThreadedAnvilChunkStorage.EntityTracker)this.entityTrackers.get(entity.getEntityId());
      if (_snowman != null) {
         _snowman.sendToNearbyPlayers(packet);
      }
   }

   private void sendChunkDataPackets(ServerPlayerEntity player, Packet<?>[] packets, WorldChunk chunk) {
      if (packets[0] == null) {
         packets[0] = new ChunkDataS2CPacket(chunk, 65535);
         packets[1] = new LightUpdateS2CPacket(chunk.getPos(), this.serverLightingProvider, true);
      }

      player.sendInitialChunkPackets(chunk.getPos(), packets[0], packets[1]);
      DebugInfoSender.sendChunkWatchingChange(this.world, chunk.getPos());
      List<Entity> _snowman = Lists.newArrayList();
      List<Entity> _snowmanx = Lists.newArrayList();
      ObjectIterator var6 = this.entityTrackers.values().iterator();

      while (var6.hasNext()) {
         ThreadedAnvilChunkStorage.EntityTracker _snowmanxx = (ThreadedAnvilChunkStorage.EntityTracker)var6.next();
         Entity _snowmanxxx = _snowmanxx.entity;
         if (_snowmanxxx != player && _snowmanxxx.chunkX == chunk.getPos().x && _snowmanxxx.chunkZ == chunk.getPos().z) {
            _snowmanxx.updateCameraPosition(player);
            if (_snowmanxxx instanceof MobEntity && ((MobEntity)_snowmanxxx).getHoldingEntity() != null) {
               _snowman.add(_snowmanxxx);
            }

            if (!_snowmanxxx.getPassengerList().isEmpty()) {
               _snowmanx.add(_snowmanxxx);
            }
         }
      }

      if (!_snowman.isEmpty()) {
         for (Entity _snowmanxx : _snowman) {
            player.networkHandler.sendPacket(new EntityAttachS2CPacket(_snowmanxx, ((MobEntity)_snowmanxx).getHoldingEntity()));
         }
      }

      if (!_snowmanx.isEmpty()) {
         for (Entity _snowmanxx : _snowmanx) {
            player.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(_snowmanxx));
         }
      }
   }

   protected PointOfInterestStorage getPointOfInterestStorage() {
      return this.pointOfInterestStorage;
   }

   public CompletableFuture<Void> enableTickSchedulers(WorldChunk _snowman) {
      return this.mainThreadExecutor.submit(() -> _snowman.enableTickSchedulers(this.world));
   }

   class EntityTracker {
      private final EntityTrackerEntry entry;
      private final Entity entity;
      private final int maxDistance;
      private ChunkSectionPos lastCameraPosition;
      private final Set<ServerPlayerEntity> playersTracking = Sets.newHashSet();

      public EntityTracker(Entity maxDistance, int tickInterval, int var4, boolean var5) {
         this.entry = new EntityTrackerEntry(ThreadedAnvilChunkStorage.this.world, maxDistance, _snowman, _snowman, this::sendToOtherNearbyPlayers);
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
         for (ServerPlayerEntity _snowman : this.playersTracking) {
            _snowman.networkHandler.sendPacket(packet);
         }
      }

      public void sendToNearbyPlayers(Packet<?> packet) {
         this.sendToOtherNearbyPlayers(packet);
         if (this.entity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)this.entity).networkHandler.sendPacket(packet);
         }
      }

      public void stopTracking() {
         for (ServerPlayerEntity _snowman : this.playersTracking) {
            this.entry.stopTracking(_snowman);
         }
      }

      public void stopTracking(ServerPlayerEntity _snowman) {
         if (this.playersTracking.remove(_snowman)) {
            this.entry.stopTracking(_snowman);
         }
      }

      public void updateCameraPosition(ServerPlayerEntity player) {
         if (player != this.entity) {
            Vec3d _snowman = player.getPos().subtract(this.entry.getLastPos());
            int _snowmanx = Math.min(this.getMaxTrackDistance(), (ThreadedAnvilChunkStorage.this.watchDistance - 1) * 16);
            boolean _snowmanxx = _snowman.x >= (double)(-_snowmanx) && _snowman.x <= (double)_snowmanx && _snowman.z >= (double)(-_snowmanx) && _snowman.z <= (double)_snowmanx && this.entity.canBeSpectated(player);
            if (_snowmanxx) {
               boolean _snowmanxxx = this.entity.teleporting;
               if (!_snowmanxxx) {
                  ChunkPos _snowmanxxxx = new ChunkPos(this.entity.chunkX, this.entity.chunkZ);
                  ChunkHolder _snowmanxxxxx = ThreadedAnvilChunkStorage.this.getChunkHolder(_snowmanxxxx.toLong());
                  if (_snowmanxxxxx != null && _snowmanxxxxx.getWorldChunk() != null) {
                     _snowmanxxx = ThreadedAnvilChunkStorage.getChebyshevDistance(_snowmanxxxx, player, false) <= ThreadedAnvilChunkStorage.this.watchDistance;
                  }
               }

               if (_snowmanxxx && this.playersTracking.add(player)) {
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
         Collection<Entity> _snowman = this.entity.getPassengersDeep();
         int _snowmanx = this.maxDistance;

         for (Entity _snowmanxx : _snowman) {
            int _snowmanxxx = _snowmanxx.getType().getMaxTrackDistance() * 16;
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }

         return this.adjustTrackingDistance(_snowmanx);
      }

      public void updateCameraPosition(List<ServerPlayerEntity> players) {
         for (ServerPlayerEntity _snowman : players) {
            this.updateCameraPosition(_snowman);
         }
      }
   }

   class TicketManager extends ChunkTicketManager {
      protected TicketManager(Executor mainThreadExecutor, Executor _snowman) {
         super(mainThreadExecutor, _snowman);
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
      protected ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int _snowman) {
         return ThreadedAnvilChunkStorage.this.setLevel(pos, level, holder, _snowman);
      }
   }
}
