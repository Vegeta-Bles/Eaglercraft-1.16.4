/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
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
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTaskPrioritySystem;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.LevelPrioritizedQueue;
import net.minecraft.server.world.PlayerChunkWatchingManager;
import net.minecraft.server.world.ServerLightingProvider;
import net.minecraft.server.world.ServerWorld;
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

public class ThreadedAnvilChunkStorage
extends VersionedChunkStorage
implements ChunkHolder.PlayersWatchingChunkProvider {
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
    private final TicketManager ticketManager;
    private final AtomicInteger totalChunksLoadedCount = new AtomicInteger();
    private final StructureManager structureManager;
    private final File saveDir;
    private final PlayerChunkWatchingManager playerChunkWatchingManager = new PlayerChunkWatchingManager();
    private final Int2ObjectMap<EntityTracker> entityTrackers = new Int2ObjectOpenHashMap();
    private final Long2ByteMap chunkToType = new Long2ByteOpenHashMap();
    private final Queue<Runnable> unloadTaskQueue = Queues.newConcurrentLinkedQueue();
    private int watchDistance;

    public ThreadedAnvilChunkStorage(ServerWorld serverWorld, LevelStorage.Session session, DataFixer dataFixer, StructureManager structureManager, Executor workerExecutor, ThreadExecutor<Runnable> mainThreadExecutor, ChunkProvider chunkProvider, ChunkGenerator chunkGenerator, WorldGenerationProgressListener worldGenerationProgressListener, Supplier<PersistentStateManager> supplier, int n, boolean bl) {
        super(new File(session.getWorldDirectory(serverWorld.getRegistryKey()), "region"), dataFixer, bl);
        this.structureManager = structureManager;
        this.saveDir = session.getWorldDirectory(serverWorld.getRegistryKey());
        this.world = serverWorld;
        this.chunkGenerator = chunkGenerator;
        this.mainThreadExecutor = mainThreadExecutor;
        TaskExecutor<Runnable> taskExecutor = TaskExecutor.create(workerExecutor, "worldgen");
        MessageListener<Runnable> _snowman2 = MessageListener.create("main", mainThreadExecutor::send);
        this.worldGenerationProgressListener = worldGenerationProgressListener;
        _snowman = TaskExecutor.create(workerExecutor, "light");
        this.chunkTaskPrioritySystem = new ChunkTaskPrioritySystem((List<MessageListener<?>>)ImmutableList.of(taskExecutor, _snowman2, _snowman), workerExecutor, Integer.MAX_VALUE);
        this.worldGenExecutor = this.chunkTaskPrioritySystem.createExecutor(taskExecutor, false);
        this.mainExecutor = this.chunkTaskPrioritySystem.createExecutor(_snowman2, false);
        this.serverLightingProvider = new ServerLightingProvider(chunkProvider, this, this.world.getDimension().hasSkyLight(), _snowman, this.chunkTaskPrioritySystem.createExecutor(_snowman, false));
        this.ticketManager = new TicketManager(workerExecutor, mainThreadExecutor);
        this.persistentStateManagerFactory = supplier;
        this.pointOfInterestStorage = new PointOfInterestStorage(new File(this.saveDir, "poi"), dataFixer, bl);
        this.setViewDistance(n);
    }

    private static double getSquaredDistance(ChunkPos pos, Entity entity) {
        double d = pos.x * 16 + 8;
        _snowman = pos.z * 16 + 8;
        _snowman = d - entity.getX();
        _snowman = _snowman - entity.getZ();
        return _snowman * _snowman + _snowman * _snowman;
    }

    private static int getChebyshevDistance(ChunkPos pos, ServerPlayerEntity player, boolean useCameraPosition) {
        int _snowman3;
        int _snowman2;
        if (useCameraPosition) {
            ChunkSectionPos chunkSectionPos = player.getCameraPosition();
            _snowman2 = chunkSectionPos.getSectionX();
            _snowman3 = chunkSectionPos.getSectionZ();
        } else {
            _snowman2 = MathHelper.floor(player.getX() / 16.0);
            _snowman3 = MathHelper.floor(player.getZ() / 16.0);
        }
        return ThreadedAnvilChunkStorage.getChebyshevDistance(pos, _snowman2, _snowman3);
    }

    private static int getChebyshevDistance(ChunkPos pos, int x, int z) {
        int n = pos.x - x;
        _snowman = pos.z - z;
        return Math.max(Math.abs(n), Math.abs(_snowman));
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
            ChunkHolder chunkHolder = this.getChunkHolder(pos);
            if (chunkHolder == null) {
                return LevelPrioritizedQueue.LEVEL_COUNT - 1;
            }
            return Math.min(chunkHolder.getCompletedLevel(), LevelPrioritizedQueue.LEVEL_COUNT - 1);
        };
    }

    public String getChunkLoadingDebugInfo(ChunkPos chunkPos) {
        ChunkHolder chunkHolder = this.getChunkHolder(chunkPos.toLong());
        if (chunkHolder == null) {
            return "null";
        }
        String _snowman2 = chunkHolder.getLevel() + "\n";
        ChunkStatus _snowman3 = chunkHolder.getCurrentStatus();
        Chunk _snowman4 = chunkHolder.getCurrentChunk();
        if (_snowman3 != null) {
            _snowman2 = _snowman2 + "St: \u00a7" + _snowman3.getIndex() + _snowman3 + '\u00a7' + "r\n";
        }
        if (_snowman4 != null) {
            _snowman2 = _snowman2 + "Ch: \u00a7" + _snowman4.getStatus().getIndex() + _snowman4.getStatus() + '\u00a7' + "r\n";
        }
        ChunkHolder.LevelType _snowman5 = chunkHolder.getLevelType();
        _snowman2 = _snowman2 + "\u00a7" + _snowman5.ordinal() + (Object)((Object)_snowman5);
        return _snowman2 + '\u00a7' + "r";
    }

    private CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> getRegion(ChunkPos centerChunk, int margin, IntFunction<ChunkStatus> distanceToStatus) {
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = centerChunk.x;
        int _snowman3 = centerChunk.z;
        for (int i = -margin; i <= margin; ++i) {
            for (_snowman = -margin; _snowman <= margin; ++_snowman) {
                _snowman = Math.max(Math.abs(_snowman), Math.abs(i));
                ChunkPos chunkPos = new ChunkPos(_snowman2 + _snowman, _snowman3 + i);
                long _snowman4 = chunkPos.toLong();
                ChunkHolder _snowman5 = this.getCurrentChunkHolder(_snowman4);
                if (_snowman5 == null) {
                    return CompletableFuture.completedFuture(Either.right((Object)new ChunkHolder.Unloaded(this, chunkPos){
                        final /* synthetic */ ChunkPos field_17231;
                        final /* synthetic */ ThreadedAnvilChunkStorage field_17232;
                        {
                            this.field_17232 = threadedAnvilChunkStorage;
                            this.field_17231 = chunkPos;
                        }

                        public String toString() {
                            return "Unloaded " + this.field_17231.toString();
                        }
                    }));
                }
                ChunkStatus _snowman6 = distanceToStatus.apply(_snowman);
                CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowman7 = _snowman5.getChunkAt(_snowman6, this);
                arrayList.add(_snowman7);
            }
        }
        CompletableFuture completableFuture = Util.combine(arrayList);
        return completableFuture.thenApply(list -> {
            ArrayList arrayList = Lists.newArrayList();
            int _snowman2 = 0;
            for (Either either : list) {
                Optional optional = either.left();
                if (!optional.isPresent()) {
                    int n4 = _snowman2;
                    return Either.right((Object)new ChunkHolder.Unloaded(this, _snowman2, n4, margin, _snowman3, either){
                        final /* synthetic */ int field_17233;
                        final /* synthetic */ int field_17234;
                        final /* synthetic */ int field_17235;
                        final /* synthetic */ int field_17236;
                        final /* synthetic */ Either field_17237;
                        final /* synthetic */ ThreadedAnvilChunkStorage field_17238;
                        {
                            this.field_17238 = threadedAnvilChunkStorage;
                            this.field_17233 = n;
                            this.field_17234 = n2;
                            this.field_17235 = n3;
                            this.field_17236 = n4;
                            this.field_17237 = either;
                        }

                        public String toString() {
                            return "Unloaded " + new ChunkPos(this.field_17233 + this.field_17234 % (this.field_17235 * 2 + 1), this.field_17236 + this.field_17234 / (this.field_17235 * 2 + 1)) + " " + ((ChunkHolder.Unloaded)this.field_17237.right().get()).toString();
                        }
                    });
                }
                arrayList.add(optional.get());
                ++_snowman2;
            }
            return Either.left((Object)arrayList);
        });
    }

    public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkEntitiesTickable(ChunkPos pos) {
        return this.getRegion(pos, 2, n -> ChunkStatus.FULL).thenApplyAsync(either -> either.mapLeft(list -> (WorldChunk)list.get(list.size() / 2)), (Executor)this.mainThreadExecutor);
    }

    @Nullable
    private ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int n) {
        if (n > MAX_LEVEL && level > MAX_LEVEL) {
            return holder;
        }
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
            this.currentChunkHolders.put(pos, (Object)holder);
            this.chunkHolderListDirty = true;
        }
        return holder;
    }

    @Override
    public void close() throws IOException {
        try {
            this.chunkTaskPrioritySystem.close();
            this.pointOfInterestStorage.close();
        }
        finally {
            super.close();
        }
    }

    protected void save(boolean flush) {
        if (flush) {
            List list = this.chunkHolders.values().stream().filter(ChunkHolder::isAccessible).peek(ChunkHolder::updateAccessibleStatus).collect(Collectors.toList());
            MutableBoolean _snowman2 = new MutableBoolean();
            do {
                _snowman2.setFalse();
                list.stream().map(chunkHolder -> {
                    CompletableFuture<Chunk> completableFuture;
                    do {
                        completableFuture = chunkHolder.getSavingFuture();
                        this.mainThreadExecutor.runTasks(completableFuture::isDone);
                    } while (completableFuture != chunkHolder.getSavingFuture());
                    return completableFuture.join();
                }).filter(chunk -> chunk instanceof ReadOnlyChunk || chunk instanceof WorldChunk).filter(this::save).forEach(chunk -> _snowman2.setTrue());
            } while (_snowman2.isTrue());
            this.unloadChunks(() -> true);
            this.completeAll();
            LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)this.saveDir.getName());
        } else {
            this.chunkHolders.values().stream().filter(ChunkHolder::isAccessible).forEach(chunkHolder -> {
                Chunk chunk = chunkHolder.getSavingFuture().getNow(null);
                if (chunk instanceof ReadOnlyChunk || chunk instanceof WorldChunk) {
                    this.save(chunk);
                    chunkHolder.updateAccessibleStatus();
                }
            });
        }
    }

    protected void tick(BooleanSupplier shouldKeepTicking) {
        Profiler profiler = this.world.getProfiler();
        profiler.push("poi");
        this.pointOfInterestStorage.tick(shouldKeepTicking);
        profiler.swap("chunk_unload");
        if (!this.world.isSavingDisabled()) {
            this.unloadChunks(shouldKeepTicking);
        }
        profiler.pop();
    }

    private void unloadChunks(BooleanSupplier shouldKeepTicking) {
        LongIterator longIterator = this.unloadedChunks.iterator();
        int _snowman2 = 0;
        while (longIterator.hasNext() && (shouldKeepTicking.getAsBoolean() || _snowman2 < 200 || this.unloadedChunks.size() > 2000)) {
            long l = longIterator.nextLong();
            ChunkHolder _snowman3 = (ChunkHolder)this.currentChunkHolders.remove(l);
            if (_snowman3 != null) {
                this.chunksToUnload.put(l, (Object)_snowman3);
                this.chunkHolderListDirty = true;
                ++_snowman2;
                this.tryUnloadChunk(l, _snowman3);
            }
            longIterator.remove();
        }
        while ((shouldKeepTicking.getAsBoolean() || this.unloadTaskQueue.size() > 2000) && (_snowman = this.unloadTaskQueue.poll()) != null) {
            _snowman.run();
        }
    }

    private void tryUnloadChunk(long pos, ChunkHolder chunkHolder) {
        CompletableFuture<Chunk> completableFuture = chunkHolder.getSavingFuture();
        ((CompletableFuture)completableFuture.thenAcceptAsync(chunk2 -> {
            CompletableFuture<Chunk> completableFuture2 = chunkHolder.getSavingFuture();
            if (completableFuture2 != completableFuture) {
                this.tryUnloadChunk(pos, chunkHolder);
                return;
            }
            if (this.chunksToUnload.remove(pos, (Object)chunkHolder) && chunk2 != null) {
                Chunk chunk2;
                if (chunk2 instanceof WorldChunk) {
                    ((WorldChunk)chunk2).setLoadedToWorld(false);
                }
                this.save((Chunk)chunk2);
                if (this.loadedChunks.remove(pos) && chunk2 instanceof WorldChunk) {
                    WorldChunk worldChunk = (WorldChunk)chunk2;
                    this.world.unloadEntities(worldChunk);
                }
                this.serverLightingProvider.updateChunkStatus(chunk2.getPos());
                this.serverLightingProvider.tick();
                this.worldGenerationProgressListener.setChunkStatus(chunk2.getPos(), null);
            }
        }, this.unloadTaskQueue::add)).whenComplete((void_, throwable) -> {
            if (throwable != null) {
                LOGGER.error("Failed to save chunk " + chunkHolder.getPos(), throwable);
            }
        });
    }

    protected boolean updateHolderMap() {
        if (!this.chunkHolderListDirty) {
            return false;
        }
        this.chunkHolders = this.currentChunkHolders.clone();
        this.chunkHolderListDirty = false;
        return true;
    }

    public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunk(ChunkHolder holder, ChunkStatus requiredStatus) {
        ChunkPos chunkPos = holder.getPos();
        if (requiredStatus == ChunkStatus.EMPTY) {
            return this.loadChunk(chunkPos);
        }
        CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowman2 = holder.getChunkAt(requiredStatus.getPrevious(), this);
        return _snowman2.thenComposeAsync(either -> {
            ChunkHolder chunkHolder2;
            Optional optional = either.left();
            if (!optional.isPresent()) {
                return CompletableFuture.completedFuture(either);
            }
            if (requiredStatus == ChunkStatus.LIGHT) {
                this.ticketManager.addTicketWithLevel(ChunkTicketType.LIGHT, chunkPos, 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FEATURES), chunkPos);
            }
            if ((_snowman = (Chunk)optional.get()).getStatus().isAtLeast(requiredStatus)) {
                CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = requiredStatus == ChunkStatus.LIGHT ? this.upgradeChunk(holder, requiredStatus) : requiredStatus.runLoadTask(this.world, this.structureManager, this.serverLightingProvider, chunk -> this.convertToFullChunk(holder), _snowman);
                this.worldGenerationProgressListener.setChunkStatus(chunkPos, requiredStatus);
                return completableFuture;
            }
            return this.upgradeChunk(holder, requiredStatus);
        }, (Executor)this.mainThreadExecutor);
    }

    private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> loadChunk(ChunkPos pos) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                this.world.getProfiler().visit("chunkLoad");
                CompoundTag compoundTag = this.getUpdatedChunkTag(pos);
                if (compoundTag != null) {
                    ChunkPos chunkPos2;
                    boolean bl = _snowman = compoundTag.contains("Level", 10) && compoundTag.getCompound("Level").contains("Status", 8);
                    if (_snowman) {
                        ProtoChunk protoChunk = ChunkSerializer.deserialize(this.world, this.structureManager, this.pointOfInterestStorage, pos, compoundTag);
                        protoChunk.setLastSaveTime(this.world.getTime());
                        this.method_27053(pos, protoChunk.getStatus().getChunkType());
                        return Either.left((Object)protoChunk);
                    }
                    LOGGER.error("Chunk file at {} is missing level data, skipping", (Object)pos);
                }
            }
            catch (CrashException crashException) {
                Throwable throwable = crashException.getCause();
                if (throwable instanceof IOException) {
                    LOGGER.error("Couldn't load chunk {}", (Object)pos, (Object)throwable);
                }
                this.method_27054(pos);
                throw crashException;
            }
            catch (Exception exception) {
                LOGGER.error("Couldn't load chunk {}", (Object)pos, (Object)exception);
            }
            this.method_27054(pos);
            return Either.left((Object)new ProtoChunk(pos, UpgradeData.NO_UPGRADE_DATA));
        }, this.mainThreadExecutor);
    }

    private void method_27054(ChunkPos chunkPos) {
        this.chunkToType.put(chunkPos.toLong(), (byte)-1);
    }

    private byte method_27053(ChunkPos chunkPos, ChunkStatus.ChunkType chunkType) {
        return this.chunkToType.put(chunkPos.toLong(), chunkType == ChunkStatus.ChunkType.field_12808 ? (byte)-1 : 1);
    }

    private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> upgradeChunk(ChunkHolder holder, ChunkStatus requiredStatus) {
        ChunkPos chunkPos = holder.getPos();
        CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> _snowman2 = this.getRegion(chunkPos, requiredStatus.getTaskMargin(), n -> this.getRequiredStatusForGeneration(requiredStatus, n));
        this.world.getProfiler().visit(() -> "chunkGenerate " + requiredStatus.getId());
        return _snowman2.thenComposeAsync(either -> (CompletableFuture)either.map(list -> {
            try {
                CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = requiredStatus.runGenerationTask(this.world, this.chunkGenerator, this.structureManager, this.serverLightingProvider, chunk -> this.convertToFullChunk(holder), (List<Chunk>)list);
                this.worldGenerationProgressListener.setChunkStatus(chunkPos, requiredStatus);
                return completableFuture;
            }
            catch (Exception exception) {
                CrashReport crashReport = CrashReport.create(exception, "Exception generating new chunk");
                CrashReportSection _snowman2 = crashReport.addElement("Chunk to be generated");
                _snowman2.add("Location", String.format("%d,%d", chunkPos.x, chunkPos.z));
                _snowman2.add("Position hash", ChunkPos.toLong(chunkPos.x, chunkPos.z));
                _snowman2.add("Generator", this.chunkGenerator);
                throw new CrashException(crashReport);
            }
        }, unloaded -> {
            this.releaseLightTicket(chunkPos);
            return CompletableFuture.completedFuture(Either.right((Object)unloaded));
        }), runnable -> this.worldGenExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
    }

    protected void releaseLightTicket(ChunkPos pos) {
        this.mainThreadExecutor.send(Util.debugRunnable(() -> this.ticketManager.removeTicketWithLevel(ChunkTicketType.LIGHT, pos, 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FEATURES), pos), () -> "release light ticket " + pos));
    }

    private ChunkStatus getRequiredStatusForGeneration(ChunkStatus centerChunkTargetStatus, int distance) {
        ChunkStatus chunkStatus = distance == 0 ? centerChunkTargetStatus.getPrevious() : ChunkStatus.byDistanceFromFull(ChunkStatus.getDistanceFromFull(centerChunkTargetStatus) + distance);
        return chunkStatus;
    }

    private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> convertToFullChunk(ChunkHolder chunkHolder) {
        CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = chunkHolder.getFutureFor(ChunkStatus.FULL.getPrevious());
        return completableFuture.thenApplyAsync(either -> {
            ChunkStatus chunkStatus = ChunkHolder.getTargetStatusForLevel(chunkHolder.getLevel());
            if (!chunkStatus.isAtLeast(ChunkStatus.FULL)) {
                return ChunkHolder.UNLOADED_CHUNK;
            }
            return either.mapLeft(chunk2 -> {
                WorldChunk _snowman2;
                ChunkPos chunkPos = chunkHolder.getPos();
                if (chunk2 instanceof ReadOnlyChunk) {
                    _snowman2 = ((ReadOnlyChunk)chunk2).getWrappedChunk();
                } else {
                    Chunk chunk2;
                    _snowman2 = new WorldChunk(this.world, (ProtoChunk)chunk2);
                    chunkHolder.setCompletedChunk(new ReadOnlyChunk(_snowman2));
                }
                _snowman2.setLevelTypeProvider(() -> ChunkHolder.getLevelType(chunkHolder.getLevel()));
                _snowman2.loadToWorld();
                if (this.loadedChunks.add(chunkPos.toLong())) {
                    _snowman2.setLoadedToWorld(true);
                    this.world.addBlockEntities(_snowman2.getBlockEntities().values());
                    Iterable _snowman3 = null;
                    for (TypeFilterableList<Entity> typeFilterableList : _snowman2.getEntitySectionArray()) {
                        for (Entity entity : typeFilterableList) {
                            if (entity instanceof PlayerEntity || this.world.loadEntity(entity)) continue;
                            if (_snowman3 == null) {
                                _snowman3 = Lists.newArrayList((Object[])new Entity[]{entity});
                                continue;
                            }
                            _snowman3.add(entity);
                        }
                    }
                    if (_snowman3 != null) {
                        _snowman3.forEach(_snowman2::remove);
                    }
                }
                return _snowman2;
            });
        }, runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(runnable, chunkHolder.getPos().toLong(), chunkHolder::getLevel)));
    }

    public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkTickable(ChunkHolder holder) {
        ChunkPos chunkPos = holder.getPos();
        CompletableFuture<Either<List<Chunk>, ChunkHolder.Unloaded>> _snowman2 = this.getRegion(chunkPos, 1, n -> ChunkStatus.FULL);
        CompletionStage _snowman3 = _snowman2.thenApplyAsync(either -> either.flatMap(list -> {
            WorldChunk worldChunk = (WorldChunk)list.get(list.size() / 2);
            worldChunk.runPostProcessing();
            return Either.left((Object)worldChunk);
        }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
        ((CompletableFuture)_snowman3).thenAcceptAsync(either -> either.mapLeft(worldChunk -> {
            this.totalChunksLoadedCount.getAndIncrement();
            Packet[] packetArray = new Packet[2];
            this.getPlayersWatchingChunk(chunkPos, false).forEach(serverPlayerEntity -> this.sendChunkDataPackets((ServerPlayerEntity)serverPlayerEntity, packetArray, (WorldChunk)worldChunk));
            return Either.left((Object)worldChunk);
        }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
        return _snowman3;
    }

    public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> makeChunkAccessible(ChunkHolder holder) {
        return holder.getChunkAt(ChunkStatus.FULL, this).thenApplyAsync(either -> either.mapLeft(chunk -> {
            WorldChunk worldChunk = (WorldChunk)chunk;
            worldChunk.disableTickSchedulers();
            return worldChunk;
        }), runnable -> this.mainExecutor.send(ChunkTaskPrioritySystem.createMessage(holder, runnable)));
    }

    public int getTotalChunksLoadedCount() {
        return this.totalChunksLoadedCount.get();
    }

    private boolean save(Chunk chunk) {
        this.pointOfInterestStorage.method_20436(chunk.getPos());
        if (!chunk.needsSaving()) {
            return false;
        }
        chunk.setLastSaveTime(this.world.getTime());
        chunk.setShouldSave(false);
        ChunkPos chunkPos = chunk.getPos();
        try {
            ChunkStatus chunkStatus = chunk.getStatus();
            if (chunkStatus.getChunkType() != ChunkStatus.ChunkType.field_12807) {
                if (this.method_27055(chunkPos)) {
                    return false;
                }
                if (chunkStatus == ChunkStatus.EMPTY && chunk.getStructureStarts().values().stream().noneMatch(StructureStart::hasChildren)) {
                    return false;
                }
            }
            this.world.getProfiler().visit("chunkSave");
            CompoundTag _snowman2 = ChunkSerializer.serialize(this.world, chunk);
            this.setTagAt(chunkPos, _snowman2);
            this.method_27053(chunkPos, chunkStatus.getChunkType());
            return true;
        }
        catch (Exception exception) {
            LOGGER.error("Failed to save chunk {},{}", (Object)chunkPos.x, (Object)chunkPos.z, (Object)exception);
            return false;
        }
    }

    private boolean method_27055(ChunkPos chunkPos) {
        byte by = this.chunkToType.get(chunkPos.toLong());
        if (by != 0) {
            return by == 1;
        }
        try {
            CompoundTag compoundTag = this.getUpdatedChunkTag(chunkPos);
            if (compoundTag == null) {
                this.method_27054(chunkPos);
                return false;
            }
        }
        catch (Exception exception) {
            LOGGER.error("Failed to read chunk {}", (Object)chunkPos, (Object)exception);
            this.method_27054(chunkPos);
            return false;
        }
        ChunkStatus.ChunkType chunkType = ChunkSerializer.getChunkType(compoundTag);
        return this.method_27053(chunkPos, chunkType) == 1;
    }

    protected void setViewDistance(int watchDistance) {
        int n = MathHelper.clamp(watchDistance + 1, 3, 33);
        if (n != this.watchDistance) {
            _snowman = this.watchDistance;
            this.watchDistance = n;
            this.ticketManager.setWatchDistance(this.watchDistance);
            for (ChunkHolder chunkHolder : this.currentChunkHolders.values()) {
                ChunkPos chunkPos = chunkHolder.getPos();
                Packet[] _snowman2 = new Packet[2];
                this.getPlayersWatchingChunk(chunkPos, false).forEach(serverPlayerEntity -> {
                    int n2 = ThreadedAnvilChunkStorage.getChebyshevDistance(chunkPos, serverPlayerEntity, true);
                    boolean _snowman2 = n2 <= _snowman;
                    boolean _snowman3 = n2 <= this.watchDistance;
                    this.sendWatchPackets((ServerPlayerEntity)serverPlayerEntity, chunkPos, _snowman2, _snowman2, _snowman3);
                });
            }
        }
    }

    protected void sendWatchPackets(ServerPlayerEntity player, ChunkPos pos, Packet<?>[] packets, boolean withinMaxWatchDistance, boolean withinViewDistance) {
        ChunkHolder chunkHolder;
        if (player.world != this.world) {
            return;
        }
        if (withinViewDistance && !withinMaxWatchDistance && (chunkHolder = this.getChunkHolder(pos.toLong())) != null) {
            WorldChunk worldChunk = chunkHolder.getWorldChunk();
            if (worldChunk != null) {
                this.sendChunkDataPackets(player, packets, worldChunk);
            }
            DebugInfoSender.sendChunkWatchingChange(this.world, pos);
        }
        if (!withinViewDistance && withinMaxWatchDistance) {
            player.sendUnloadChunkPacket(pos);
        }
    }

    public int getLoadedChunkCount() {
        return this.chunkHolders.size();
    }

    protected TicketManager getTicketManager() {
        return this.ticketManager;
    }

    protected Iterable<ChunkHolder> entryIterator() {
        return Iterables.unmodifiableIterable((Iterable)this.chunkHolders.values());
    }

    void dump(Writer writer) throws IOException {
        CsvWriter csvWriter = CsvWriter.makeHeader().addColumn("x").addColumn("z").addColumn("level").addColumn("in_memory").addColumn("status").addColumn("full_status").addColumn("accessible_ready").addColumn("ticking_ready").addColumn("entity_ticking_ready").addColumn("ticket").addColumn("spawning").addColumn("entity_count").addColumn("block_entity_count").startBody(writer);
        for (Long2ObjectMap.Entry entry : this.chunkHolders.long2ObjectEntrySet()) {
            ChunkPos chunkPos = new ChunkPos(entry.getLongKey());
            ChunkHolder _snowman2 = (ChunkHolder)entry.getValue();
            Optional<Chunk> _snowman3 = Optional.ofNullable(_snowman2.getCurrentChunk());
            Optional<Object> _snowman4 = _snowman3.flatMap(chunk -> chunk instanceof WorldChunk ? Optional.of((WorldChunk)chunk) : Optional.empty());
            csvWriter.printRow(chunkPos.x, chunkPos.z, _snowman2.getLevel(), _snowman3.isPresent(), _snowman3.map(Chunk::getStatus).orElse(null), _snowman4.map(WorldChunk::getLevelType).orElse(null), ThreadedAnvilChunkStorage.getFutureStatus(_snowman2.getAccessibleFuture()), ThreadedAnvilChunkStorage.getFutureStatus(_snowman2.getTickingFuture()), ThreadedAnvilChunkStorage.getFutureStatus(_snowman2.getEntityTickingFuture()), this.ticketManager.getTicket(entry.getLongKey()), !this.isTooFarFromPlayersToSpawnMobs(chunkPos), _snowman4.map(worldChunk -> Stream.of(worldChunk.getEntitySectionArray()).mapToInt(TypeFilterableList::size).sum()).orElse(0), _snowman4.map(worldChunk -> worldChunk.getBlockEntities().size()).orElse(0));
        }
    }

    private static String getFutureStatus(CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> completableFuture) {
        try {
            Either either = completableFuture.getNow(null);
            if (either != null) {
                return (String)either.map(worldChunk -> "done", unloaded -> "unloaded");
            }
            return "not completed";
        }
        catch (CompletionException completionException) {
            return "failed " + completionException.getCause().getMessage();
        }
        catch (CancellationException cancellationException) {
            return "cancelled";
        }
    }

    @Nullable
    private CompoundTag getUpdatedChunkTag(ChunkPos pos) throws IOException {
        CompoundTag compoundTag = this.getNbt(pos);
        if (compoundTag == null) {
            return null;
        }
        return this.updateChunkTag(this.world.getRegistryKey(), this.persistentStateManagerFactory, compoundTag);
    }

    boolean isTooFarFromPlayersToSpawnMobs(ChunkPos chunkPos) {
        long l = chunkPos.toLong();
        if (!this.ticketManager.method_20800(l)) {
            return true;
        }
        return this.playerChunkWatchingManager.getPlayersWatchingChunk(l).noneMatch(serverPlayerEntity -> !serverPlayerEntity.isSpectator() && ThreadedAnvilChunkStorage.getSquaredDistance(chunkPos, serverPlayerEntity) < 16384.0);
    }

    private boolean doesNotGenerateChunks(ServerPlayerEntity player) {
        return player.isSpectator() && !this.world.getGameRules().getBoolean(GameRules.SPECTATORS_GENERATE_CHUNKS);
    }

    void handlePlayerAddedOrRemoved(ServerPlayerEntity player, boolean added) {
        boolean bl = this.doesNotGenerateChunks(player);
        _snowman = this.playerChunkWatchingManager.method_21715(player);
        int _snowman2 = MathHelper.floor(player.getX()) >> 4;
        int _snowman3 = MathHelper.floor(player.getZ()) >> 4;
        if (added) {
            this.playerChunkWatchingManager.add(ChunkPos.toLong(_snowman2, _snowman3), player, bl);
            this.method_20726(player);
            if (!bl) {
                this.ticketManager.handleChunkEnter(ChunkSectionPos.from(player), player);
            }
        } else {
            ChunkSectionPos chunkSectionPos = player.getCameraPosition();
            this.playerChunkWatchingManager.remove(chunkSectionPos.toChunkPos().toLong(), player);
            if (!_snowman) {
                this.ticketManager.handleChunkLeave(chunkSectionPos, player);
            }
        }
        for (int i = _snowman2 - this.watchDistance; i <= _snowman2 + this.watchDistance; ++i) {
            for (_snowman = _snowman3 - this.watchDistance; _snowman <= _snowman3 + this.watchDistance; ++_snowman) {
                ChunkPos chunkPos = new ChunkPos(i, _snowman);
                this.sendWatchPackets(player, chunkPos, new Packet[2], !added, added);
            }
        }
    }

    private ChunkSectionPos method_20726(ServerPlayerEntity serverPlayerEntity) {
        ChunkSectionPos chunkSectionPos = ChunkSectionPos.from(serverPlayerEntity);
        serverPlayerEntity.setCameraPosition(chunkSectionPos);
        serverPlayerEntity.networkHandler.sendPacket(new ChunkRenderDistanceCenterS2CPacket(chunkSectionPos.getSectionX(), chunkSectionPos.getSectionZ()));
        return chunkSectionPos;
    }

    public void updateCameraPosition(ServerPlayerEntity player) {
        for (EntityTracker entityTracker : this.entityTrackers.values()) {
            if (entityTracker.entity == player) {
                entityTracker.updateCameraPosition(this.world.getPlayers());
                continue;
            }
            entityTracker.updateCameraPosition(player);
        }
        int n = MathHelper.floor(player.getX()) >> 4;
        _snowman = MathHelper.floor(player.getZ()) >> 4;
        ChunkSectionPos _snowman2 = player.getCameraPosition();
        ChunkSectionPos _snowman3 = ChunkSectionPos.from(player);
        long _snowman4 = _snowman2.toChunkPos().toLong();
        long _snowman5 = _snowman3.toChunkPos().toLong();
        boolean _snowman6 = this.playerChunkWatchingManager.isWatchDisabled(player);
        boolean _snowman7 = this.doesNotGenerateChunks(player);
        boolean bl = _snowman = _snowman2.asLong() != _snowman3.asLong();
        if (_snowman || _snowman6 != _snowman7) {
            this.method_20726(player);
            if (!_snowman6) {
                this.ticketManager.handleChunkLeave(_snowman2, player);
            }
            if (!_snowman7) {
                this.ticketManager.handleChunkEnter(_snowman3, player);
            }
            if (!_snowman6 && _snowman7) {
                this.playerChunkWatchingManager.disableWatch(player);
            }
            if (_snowman6 && !_snowman7) {
                this.playerChunkWatchingManager.enableWatch(player);
            }
            if (_snowman4 != _snowman5) {
                this.playerChunkWatchingManager.movePlayer(_snowman4, _snowman5, player);
            }
        }
        _snowman = _snowman2.getSectionX();
        _snowman = _snowman2.getSectionZ();
        if (Math.abs(_snowman - n) <= this.watchDistance * 2 && Math.abs(_snowman - _snowman) <= this.watchDistance * 2) {
            _snowman = Math.min(n, _snowman) - this.watchDistance;
            _snowman = Math.min(_snowman, _snowman) - this.watchDistance;
            _snowman = Math.max(n, _snowman) + this.watchDistance;
            _snowman = Math.max(_snowman, _snowman) + this.watchDistance;
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                    ChunkPos chunkPos = new ChunkPos(_snowman, _snowman);
                    boolean _snowman8 = ThreadedAnvilChunkStorage.getChebyshevDistance(chunkPos, _snowman, _snowman) <= this.watchDistance;
                    boolean _snowman9 = ThreadedAnvilChunkStorage.getChebyshevDistance(chunkPos, n, _snowman) <= this.watchDistance;
                    this.sendWatchPackets(player, chunkPos, new Packet[2], _snowman8, _snowman9);
                }
            }
        } else {
            boolean _snowman11;
            boolean _snowman10;
            ChunkPos chunkPos;
            int n2;
            for (n2 = _snowman - this.watchDistance; n2 <= _snowman + this.watchDistance; ++n2) {
                for (_snowman = _snowman - this.watchDistance; _snowman <= _snowman + this.watchDistance; ++_snowman) {
                    chunkPos = new ChunkPos(n2, _snowman);
                    _snowman10 = true;
                    _snowman11 = false;
                    this.sendWatchPackets(player, chunkPos, new Packet[2], true, false);
                }
            }
            for (n2 = n - this.watchDistance; n2 <= n + this.watchDistance; ++n2) {
                for (_snowman = _snowman - this.watchDistance; _snowman <= _snowman + this.watchDistance; ++_snowman) {
                    chunkPos = new ChunkPos(n2, _snowman);
                    _snowman10 = false;
                    _snowman11 = true;
                    this.sendWatchPackets(player, chunkPos, new Packet[2], false, true);
                }
            }
        }
    }

    @Override
    public Stream<ServerPlayerEntity> getPlayersWatchingChunk(ChunkPos chunkPos, boolean onlyOnWatchDistanceEdge) {
        return this.playerChunkWatchingManager.getPlayersWatchingChunk(chunkPos.toLong()).filter(serverPlayerEntity -> {
            int n = ThreadedAnvilChunkStorage.getChebyshevDistance(chunkPos, serverPlayerEntity, true);
            if (n > this.watchDistance) {
                return false;
            }
            return !onlyOnWatchDistanceEdge || n == this.watchDistance;
        });
    }

    protected void loadEntity(Entity entity) {
        if (entity instanceof EnderDragonPart) {
            return;
        }
        EntityType<?> entityType = entity.getType();
        int _snowman2 = entityType.getMaxTrackDistance() * 16;
        int _snowman3 = entityType.getTrackTickInterval();
        if (this.entityTrackers.containsKey(entity.getEntityId())) {
            throw Util.throwOrPause(new IllegalStateException("Entity is already tracked!"));
        }
        EntityTracker _snowman4 = new EntityTracker(entity, _snowman2, _snowman3, entityType.alwaysUpdateVelocity());
        this.entityTrackers.put(entity.getEntityId(), (Object)_snowman4);
        _snowman4.updateCameraPosition(this.world.getPlayers());
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            this.handlePlayerAddedOrRemoved(serverPlayerEntity, true);
            for (EntityTracker entityTracker : this.entityTrackers.values()) {
                if (entityTracker.entity == serverPlayerEntity) continue;
                entityTracker.updateCameraPosition(serverPlayerEntity);
            }
        }
    }

    protected void unloadEntity(Entity entity) {
        Object object;
        if (entity instanceof ServerPlayerEntity) {
            object = (ServerPlayerEntity)entity;
            this.handlePlayerAddedOrRemoved((ServerPlayerEntity)object, false);
            for (EntityTracker entityTracker : this.entityTrackers.values()) {
                entityTracker.stopTracking((ServerPlayerEntity)object);
            }
        }
        if ((object = (EntityTracker)this.entityTrackers.remove(entity.getEntityId())) != null) {
            ((EntityTracker)object).stopTracking();
        }
    }

    protected void tickPlayerMovement() {
        ArrayList arrayList = Lists.newArrayList();
        List<ServerPlayerEntity> _snowman2 = this.world.getPlayers();
        for (EntityTracker entityTracker2 : this.entityTrackers.values()) {
            EntityTracker entityTracker2;
            ChunkSectionPos chunkSectionPos = entityTracker2.lastCameraPosition;
            if (!Objects.equals(chunkSectionPos, _snowman = ChunkSectionPos.from(entityTracker2.entity))) {
                entityTracker2.updateCameraPosition(_snowman2);
                Entity entity = entityTracker2.entity;
                if (entity instanceof ServerPlayerEntity) {
                    arrayList.add((ServerPlayerEntity)entity);
                }
                entityTracker2.lastCameraPosition = _snowman;
            }
            entityTracker2.entry.tick();
        }
        if (!arrayList.isEmpty()) {
            for (EntityTracker entityTracker2 : this.entityTrackers.values()) {
                entityTracker2.updateCameraPosition(arrayList);
            }
        }
    }

    protected void sendToOtherNearbyPlayers(Entity entity, Packet<?> packet) {
        EntityTracker entityTracker = (EntityTracker)this.entityTrackers.get(entity.getEntityId());
        if (entityTracker != null) {
            entityTracker.sendToOtherNearbyPlayers(packet);
        }
    }

    protected void sendToNearbyPlayers(Entity entity, Packet<?> packet) {
        EntityTracker entityTracker = (EntityTracker)this.entityTrackers.get(entity.getEntityId());
        if (entityTracker != null) {
            entityTracker.sendToNearbyPlayers(packet);
        }
    }

    private void sendChunkDataPackets(ServerPlayerEntity player, Packet<?>[] packets, WorldChunk chunk) {
        ArrayList arrayList;
        if (packets[0] == null) {
            packets[0] = new ChunkDataS2CPacket(chunk, 65535);
            packets[1] = new LightUpdateS2CPacket(chunk.getPos(), this.serverLightingProvider, true);
        }
        player.sendInitialChunkPackets(chunk.getPos(), packets[0], packets[1]);
        DebugInfoSender.sendChunkWatchingChange(this.world, chunk.getPos());
        ArrayList arrayList2 = Lists.newArrayList();
        arrayList = Lists.newArrayList();
        for (Object object : this.entityTrackers.values()) {
            Entity entity = ((EntityTracker)object).entity;
            if (entity == player || entity.chunkX != chunk.getPos().x || entity.chunkZ != chunk.getPos().z) continue;
            ((EntityTracker)object).updateCameraPosition(player);
            if (entity instanceof MobEntity && ((MobEntity)entity).getHoldingEntity() != null) {
                arrayList2.add(entity);
            }
            if (entity.getPassengerList().isEmpty()) continue;
            arrayList.add(entity);
        }
        if (!arrayList2.isEmpty()) {
            for (Object object : arrayList2) {
                player.networkHandler.sendPacket(new EntityAttachS2CPacket((Entity)object, ((MobEntity)object).getHoldingEntity()));
            }
        }
        if (!arrayList.isEmpty()) {
            for (Object object : arrayList) {
                player.networkHandler.sendPacket(new EntityPassengersSetS2CPacket((Entity)object));
            }
        }
    }

    protected PointOfInterestStorage getPointOfInterestStorage() {
        return this.pointOfInterestStorage;
    }

    public CompletableFuture<Void> enableTickSchedulers(WorldChunk worldChunk) {
        return this.mainThreadExecutor.submit(() -> worldChunk.enableTickSchedulers(this.world));
    }

    class EntityTracker {
        private final EntityTrackerEntry entry;
        private final Entity entity;
        private final int maxDistance;
        private ChunkSectionPos lastCameraPosition;
        private final Set<ServerPlayerEntity> playersTracking = Sets.newHashSet();

        public EntityTracker(Entity maxDistance, int tickInterval, int n, boolean bl) {
            this.entry = new EntityTrackerEntry(ThreadedAnvilChunkStorage.this.world, maxDistance, n, bl, this::sendToOtherNearbyPlayers);
            this.entity = maxDistance;
            this.maxDistance = tickInterval;
            this.lastCameraPosition = ChunkSectionPos.from(maxDistance);
        }

        public boolean equals(Object o) {
            if (o instanceof EntityTracker) {
                return ((EntityTracker)o).entity.getEntityId() == this.entity.getEntityId();
            }
            return false;
        }

        public int hashCode() {
            return this.entity.getEntityId();
        }

        public void sendToOtherNearbyPlayers(Packet<?> packet) {
            for (ServerPlayerEntity serverPlayerEntity : this.playersTracking) {
                serverPlayerEntity.networkHandler.sendPacket(packet);
            }
        }

        public void sendToNearbyPlayers(Packet<?> packet) {
            this.sendToOtherNearbyPlayers(packet);
            if (this.entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)this.entity).networkHandler.sendPacket(packet);
            }
        }

        public void stopTracking() {
            for (ServerPlayerEntity serverPlayerEntity : this.playersTracking) {
                this.entry.stopTracking(serverPlayerEntity);
            }
        }

        public void stopTracking(ServerPlayerEntity serverPlayerEntity) {
            if (this.playersTracking.remove(serverPlayerEntity)) {
                this.entry.stopTracking(serverPlayerEntity);
            }
        }

        public void updateCameraPosition(ServerPlayerEntity player) {
            if (player == this.entity) {
                return;
            }
            Vec3d vec3d = player.getPos().subtract(this.entry.getLastPos());
            int _snowman2 = Math.min(this.getMaxTrackDistance(), (ThreadedAnvilChunkStorage.this.watchDistance - 1) * 16);
            boolean bl = _snowman = vec3d.x >= (double)(-_snowman2) && vec3d.x <= (double)_snowman2 && vec3d.z >= (double)(-_snowman2) && vec3d.z <= (double)_snowman2 && this.entity.canBeSpectated(player);
            if (_snowman) {
                boolean bl2 = this.entity.teleporting;
                if (!bl2 && (_snowman = ThreadedAnvilChunkStorage.this.getChunkHolder((_snowman = new ChunkPos(this.entity.chunkX, this.entity.chunkZ)).toLong())) != null && _snowman.getWorldChunk() != null) {
                    boolean bl3 = bl2 = ThreadedAnvilChunkStorage.getChebyshevDistance(_snowman, player, false) <= ThreadedAnvilChunkStorage.this.watchDistance;
                }
                if (bl2 && this.playersTracking.add(player)) {
                    this.entry.startTracking(player);
                }
            } else if (this.playersTracking.remove(player)) {
                this.entry.stopTracking(player);
            }
        }

        private int adjustTrackingDistance(int initialDistance) {
            return ThreadedAnvilChunkStorage.this.world.getServer().adjustTrackingDistance(initialDistance);
        }

        private int getMaxTrackDistance() {
            Collection<Entity> collection = this.entity.getPassengersDeep();
            int _snowman2 = this.maxDistance;
            for (Entity entity : collection) {
                int n = entity.getType().getMaxTrackDistance() * 16;
                if (n <= _snowman2) continue;
                _snowman2 = n;
            }
            return this.adjustTrackingDistance(_snowman2);
        }

        public void updateCameraPosition(List<ServerPlayerEntity> players) {
            for (ServerPlayerEntity serverPlayerEntity : players) {
                this.updateCameraPosition(serverPlayerEntity);
            }
        }
    }

    class TicketManager
    extends ChunkTicketManager {
        protected TicketManager(Executor mainThreadExecutor, Executor executor) {
            super(mainThreadExecutor, executor);
        }

        @Override
        protected boolean isUnloaded(long pos) {
            return ThreadedAnvilChunkStorage.this.unloadedChunks.contains(pos);
        }

        @Override
        @Nullable
        protected ChunkHolder getChunkHolder(long pos) {
            return ThreadedAnvilChunkStorage.this.getCurrentChunkHolder(pos);
        }

        @Override
        @Nullable
        protected ChunkHolder setLevel(long pos, int level, @Nullable ChunkHolder holder, int n) {
            return ThreadedAnvilChunkStorage.this.setLevel(pos, level, holder, n);
        }
    }
}

