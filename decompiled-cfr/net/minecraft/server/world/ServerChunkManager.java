/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerLightingProvider;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.poi.PointOfInterestStorage;

public class ServerChunkManager
extends ChunkManager {
    private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.createOrderedList();
    private final ChunkTicketManager ticketManager;
    private final ChunkGenerator chunkGenerator;
    private final ServerWorld world;
    private final Thread serverThread;
    private final ServerLightingProvider lightProvider;
    private final MainThreadExecutor mainThreadExecutor;
    public final ThreadedAnvilChunkStorage threadedAnvilChunkStorage;
    private final PersistentStateManager persistentStateManager;
    private long lastMobSpawningTime;
    private boolean spawnMonsters = true;
    private boolean spawnAnimals = true;
    private final long[] chunkPosCache = new long[4];
    private final ChunkStatus[] chunkStatusCache = new ChunkStatus[4];
    private final Chunk[] chunkCache = new Chunk[4];
    @Nullable
    private SpawnHelper.Info spawnEntry;

    public ServerChunkManager(ServerWorld serverWorld, LevelStorage.Session session, DataFixer dataFixer, StructureManager structureManager, Executor workerExecutor, ChunkGenerator chunkGenerator, int viewDistance, boolean bl, WorldGenerationProgressListener worldGenerationProgressListener, Supplier<PersistentStateManager> supplier) {
        this.world = serverWorld;
        this.mainThreadExecutor = new MainThreadExecutor(serverWorld);
        this.chunkGenerator = chunkGenerator;
        this.serverThread = Thread.currentThread();
        File file = session.getWorldDirectory(serverWorld.getRegistryKey());
        _snowman = new File(file, "data");
        _snowman.mkdirs();
        this.persistentStateManager = new PersistentStateManager(_snowman, dataFixer);
        this.threadedAnvilChunkStorage = new ThreadedAnvilChunkStorage(serverWorld, session, dataFixer, structureManager, workerExecutor, this.mainThreadExecutor, this, this.getChunkGenerator(), worldGenerationProgressListener, supplier, viewDistance, bl);
        this.lightProvider = this.threadedAnvilChunkStorage.getLightProvider();
        this.ticketManager = this.threadedAnvilChunkStorage.getTicketManager();
        this.initChunkCaches();
    }

    @Override
    public ServerLightingProvider getLightingProvider() {
        return this.lightProvider;
    }

    @Nullable
    private ChunkHolder getChunkHolder(long pos) {
        return this.threadedAnvilChunkStorage.getChunkHolder(pos);
    }

    public int getTotalChunksLoadedCount() {
        return this.threadedAnvilChunkStorage.getTotalChunksLoadedCount();
    }

    private void putInCache(long pos, Chunk chunk, ChunkStatus status) {
        for (int i = 3; i > 0; --i) {
            this.chunkPosCache[i] = this.chunkPosCache[i - 1];
            this.chunkStatusCache[i] = this.chunkStatusCache[i - 1];
            this.chunkCache[i] = this.chunkCache[i - 1];
        }
        this.chunkPosCache[0] = pos;
        this.chunkStatusCache[0] = status;
        this.chunkCache[0] = chunk;
    }

    @Override
    @Nullable
    public Chunk getChunk(int x, int z, ChunkStatus leastStatus, boolean create) {
        if (Thread.currentThread() != this.serverThread) {
            return CompletableFuture.supplyAsync(() -> this.getChunk(x, z, leastStatus, create), this.mainThreadExecutor).join();
        }
        Profiler profiler = this.world.getProfiler();
        profiler.visit("getChunk");
        long _snowman2 = ChunkPos.toLong(x, z);
        for (int i = 0; i < 4; ++i) {
            if (_snowman2 != this.chunkPosCache[i] || leastStatus != this.chunkStatusCache[i] || (_snowman4 = this.chunkCache[i]) == null && create) continue;
            return _snowman4;
        }
        profiler.visit("getChunkCacheMiss");
        CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowman3 = this.getChunkFuture(x, z, leastStatus, create);
        this.mainThreadExecutor.runTasks(_snowman3::isDone);
        Chunk _snowman4 = (Chunk)_snowman3.join().map(chunk -> chunk, unloaded -> {
            if (create) {
                throw Util.throwOrPause(new IllegalStateException("Chunk not there when requested: " + unloaded));
            }
            return null;
        });
        this.putInCache(_snowman2, _snowman4, leastStatus);
        return _snowman4;
    }

    @Override
    @Nullable
    public WorldChunk getWorldChunk(int chunkX, int chunkZ) {
        if (Thread.currentThread() != this.serverThread) {
            return null;
        }
        this.world.getProfiler().visit("getChunkNow");
        long l = ChunkPos.toLong(chunkX, chunkZ);
        for (int i = 0; i < 4; ++i) {
            if (l != this.chunkPosCache[i] || this.chunkStatusCache[i] != ChunkStatus.FULL) continue;
            Chunk chunk = this.chunkCache[i];
            return chunk instanceof WorldChunk ? (WorldChunk)chunk : null;
        }
        ChunkHolder _snowman2 = this.getChunkHolder(l);
        if (_snowman2 == null) {
            return null;
        }
        Either _snowman3 = _snowman2.getValidFutureFor(ChunkStatus.FULL).getNow(null);
        if (_snowman3 == null) {
            return null;
        }
        Chunk _snowman4 = _snowman3.left().orElse(null);
        if (_snowman4 != null) {
            this.putInCache(l, _snowman4, ChunkStatus.FULL);
            if (_snowman4 instanceof WorldChunk) {
                return (WorldChunk)_snowman4;
            }
        }
        return null;
    }

    private void initChunkCaches() {
        Arrays.fill(this.chunkPosCache, ChunkPos.MARKER);
        Arrays.fill(this.chunkStatusCache, null);
        Arrays.fill(this.chunkCache, null);
    }

    public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkFutureSyncOnMainThread(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
        CompletionStage<Object> completionStage;
        boolean bl;
        boolean bl2 = bl = Thread.currentThread() == this.serverThread;
        if (bl) {
            completionStage = this.getChunkFuture(chunkX, chunkZ, leastStatus, create);
            this.mainThreadExecutor.runTasks(() -> completionStage.isDone());
        } else {
            completionStage = CompletableFuture.supplyAsync(() -> this.getChunkFuture(chunkX, chunkZ, leastStatus, create), this.mainThreadExecutor).thenCompose(completableFuture -> completableFuture);
        }
        return completionStage;
    }

    private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkFuture(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        long _snowman2 = chunkPos.toLong();
        int _snowman3 = 33 + ChunkStatus.getDistanceFromFull(leastStatus);
        ChunkHolder _snowman4 = this.getChunkHolder(_snowman2);
        if (create) {
            this.ticketManager.addTicketWithLevel(ChunkTicketType.UNKNOWN, chunkPos, _snowman3, chunkPos);
            if (this.isMissingForLevel(_snowman4, _snowman3)) {
                Profiler profiler = this.world.getProfiler();
                profiler.push("chunkLoad");
                this.tick();
                _snowman4 = this.getChunkHolder(_snowman2);
                profiler.pop();
                if (this.isMissingForLevel(_snowman4, _snowman3)) {
                    throw Util.throwOrPause(new IllegalStateException("No chunk holder after ticket has been added"));
                }
            }
        }
        if (this.isMissingForLevel(_snowman4, _snowman3)) {
            return ChunkHolder.UNLOADED_CHUNK_FUTURE;
        }
        return _snowman4.getChunkAt(leastStatus, this.threadedAnvilChunkStorage);
    }

    private boolean isMissingForLevel(@Nullable ChunkHolder holder, int maxLevel) {
        return holder == null || holder.getLevel() > maxLevel;
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        ChunkHolder chunkHolder = this.getChunkHolder(new ChunkPos(x, z).toLong());
        return !this.isMissingForLevel(chunkHolder, _snowman = 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FULL));
    }

    @Override
    public BlockView getChunk(int chunkX, int chunkZ) {
        long l = ChunkPos.toLong(chunkX, chunkZ);
        ChunkHolder _snowman2 = this.getChunkHolder(l);
        if (_snowman2 == null) {
            return null;
        }
        int _snowman3 = CHUNK_STATUSES.size() - 1;
        while (true) {
            if ((_snowman = _snowman2.getFutureFor(_snowman = CHUNK_STATUSES.get(_snowman3)).getNow(ChunkHolder.UNLOADED_CHUNK).left()).isPresent()) {
                return (BlockView)_snowman.get();
            }
            if (_snowman == ChunkStatus.LIGHT.getPrevious()) break;
            --_snowman3;
        }
        return null;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public boolean executeQueuedTasks() {
        return this.mainThreadExecutor.runTask();
    }

    private boolean tick() {
        boolean bl = this.ticketManager.tick(this.threadedAnvilChunkStorage);
        _snowman = this.threadedAnvilChunkStorage.updateHolderMap();
        if (bl || _snowman) {
            this.initChunkCaches();
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldTickEntity(Entity entity) {
        long l = ChunkPos.toLong(MathHelper.floor(entity.getX()) >> 4, MathHelper.floor(entity.getZ()) >> 4);
        return this.isFutureReady(l, ChunkHolder::getEntityTickingFuture);
    }

    @Override
    public boolean shouldTickChunk(ChunkPos pos) {
        return this.isFutureReady(pos.toLong(), ChunkHolder::getEntityTickingFuture);
    }

    @Override
    public boolean shouldTickBlock(BlockPos pos) {
        long l = ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4);
        return this.isFutureReady(l, ChunkHolder::getTickingFuture);
    }

    private boolean isFutureReady(long pos, Function<ChunkHolder, CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>>> futureFunction) {
        ChunkHolder chunkHolder = this.getChunkHolder(pos);
        if (chunkHolder == null) {
            return false;
        }
        Either<WorldChunk, ChunkHolder.Unloaded> _snowman2 = futureFunction.apply(chunkHolder).getNow(ChunkHolder.UNLOADED_WORLD_CHUNK);
        return _snowman2.left().isPresent();
    }

    public void save(boolean flush) {
        this.tick();
        this.threadedAnvilChunkStorage.save(flush);
    }

    @Override
    public void close() throws IOException {
        this.save(true);
        this.lightProvider.close();
        this.threadedAnvilChunkStorage.close();
    }

    public void tick(BooleanSupplier shouldKeepTicking) {
        this.world.getProfiler().push("purge");
        this.ticketManager.purge();
        this.tick();
        this.world.getProfiler().swap("chunks");
        this.tickChunks();
        this.world.getProfiler().swap("unload");
        this.threadedAnvilChunkStorage.tick(shouldKeepTicking);
        this.world.getProfiler().pop();
        this.initChunkCaches();
    }

    private void tickChunks() {
        long l = this.world.getTime();
        _snowman = l - this.lastMobSpawningTime;
        this.lastMobSpawningTime = l;
        WorldProperties _snowman2 = this.world.getLevelProperties();
        boolean _snowman3 = this.world.isDebugWorld();
        boolean _snowman4 = this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING);
        if (!_snowman3) {
            this.world.getProfiler().push("pollingChunks");
            int n = this.world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
            boolean _snowman5 = _snowman2.getTime() % 400L == 0L;
            this.world.getProfiler().push("naturalSpawnCount");
            _snowman = this.ticketManager.getSpawningChunkCount();
            this.spawnEntry = _snowman = SpawnHelper.setupSpawn(_snowman, this.world.iterateEntities(), this::ifChunkLoaded);
            this.world.getProfiler().pop();
            ArrayList _snowman6 = Lists.newArrayList(this.threadedAnvilChunkStorage.entryIterator());
            Collections.shuffle(_snowman6);
            _snowman6.forEach(chunkHolder -> {
                Optional optional = chunkHolder.getTickingFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left();
                if (!optional.isPresent()) {
                    return;
                }
                this.world.getProfiler().push("broadcast");
                chunkHolder.flushUpdates((WorldChunk)optional.get());
                this.world.getProfiler().pop();
                _snowman = chunkHolder.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left();
                if (!_snowman.isPresent()) {
                    return;
                }
                WorldChunk _snowman2 = (WorldChunk)_snowman.get();
                ChunkPos _snowman3 = chunkHolder.getPos();
                if (this.threadedAnvilChunkStorage.isTooFarFromPlayersToSpawnMobs(_snowman3)) {
                    return;
                }
                _snowman2.setInhabitedTime(_snowman2.getInhabitedTime() + _snowman);
                if (_snowman4 && (this.spawnMonsters || this.spawnAnimals) && this.world.getWorldBorder().contains(_snowman2.getPos())) {
                    SpawnHelper.spawn(this.world, _snowman2, _snowman, this.spawnAnimals, this.spawnMonsters, _snowman5);
                }
                this.world.tickChunk(_snowman2, n);
            });
            this.world.getProfiler().push("customSpawners");
            if (_snowman4) {
                this.world.tickSpawners(this.spawnMonsters, this.spawnAnimals);
            }
            this.world.getProfiler().pop();
            this.world.getProfiler().pop();
        }
        this.threadedAnvilChunkStorage.tickPlayerMovement();
    }

    private void ifChunkLoaded(long pos, Consumer<WorldChunk> chunkConsumer) {
        ChunkHolder chunkHolder = this.getChunkHolder(pos);
        if (chunkHolder != null) {
            chunkHolder.getAccessibleFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left().ifPresent(chunkConsumer);
        }
    }

    @Override
    public String getDebugString() {
        return "ServerChunkCache: " + this.getLoadedChunkCount();
    }

    @VisibleForTesting
    public int getPendingTasks() {
        return this.mainThreadExecutor.getTaskCount();
    }

    public ChunkGenerator getChunkGenerator() {
        return this.chunkGenerator;
    }

    public int getLoadedChunkCount() {
        return this.threadedAnvilChunkStorage.getLoadedChunkCount();
    }

    public void markForUpdate(BlockPos pos) {
        int n = pos.getX() >> 4;
        ChunkHolder _snowman2 = this.getChunkHolder(ChunkPos.toLong(n, _snowman = pos.getZ() >> 4));
        if (_snowman2 != null) {
            _snowman2.markForBlockUpdate(pos);
        }
    }

    @Override
    public void onLightUpdate(LightType type, ChunkSectionPos pos) {
        this.mainThreadExecutor.execute(() -> {
            ChunkHolder chunkHolder = this.getChunkHolder(pos.toChunkPos().toLong());
            if (chunkHolder != null) {
                chunkHolder.markForLightUpdate(type, pos.getSectionY());
            }
        });
    }

    public <T> void addTicket(ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument) {
        this.ticketManager.addTicket(ticketType, pos, radius, argument);
    }

    public <T> void removeTicket(ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument) {
        this.ticketManager.removeTicket(ticketType, pos, radius, argument);
    }

    @Override
    public void setChunkForced(ChunkPos pos, boolean forced) {
        this.ticketManager.setChunkForced(pos, forced);
    }

    public void updateCameraPosition(ServerPlayerEntity player) {
        this.threadedAnvilChunkStorage.updateCameraPosition(player);
    }

    public void unloadEntity(Entity entity) {
        this.threadedAnvilChunkStorage.unloadEntity(entity);
    }

    public void loadEntity(Entity entity) {
        this.threadedAnvilChunkStorage.loadEntity(entity);
    }

    public void sendToNearbyPlayers(Entity entity, Packet<?> packet) {
        this.threadedAnvilChunkStorage.sendToNearbyPlayers(entity, packet);
    }

    public void sendToOtherNearbyPlayers(Entity entity, Packet<?> packet) {
        this.threadedAnvilChunkStorage.sendToOtherNearbyPlayers(entity, packet);
    }

    public void applyViewDistance(int watchDistance) {
        this.threadedAnvilChunkStorage.setViewDistance(watchDistance);
    }

    @Override
    public void setMobSpawnOptions(boolean spawnMonsters, boolean spawnAnimals) {
        this.spawnMonsters = spawnMonsters;
        this.spawnAnimals = spawnAnimals;
    }

    public String getChunkLoadingDebugInfo(ChunkPos chunkPos) {
        return this.threadedAnvilChunkStorage.getChunkLoadingDebugInfo(chunkPos);
    }

    public PersistentStateManager getPersistentStateManager() {
        return this.persistentStateManager;
    }

    public PointOfInterestStorage getPointOfInterestStorage() {
        return this.threadedAnvilChunkStorage.getPointOfInterestStorage();
    }

    @Nullable
    public SpawnHelper.Info getSpawnInfo() {
        return this.spawnEntry;
    }

    @Override
    public /* synthetic */ LightingProvider getLightingProvider() {
        return this.getLightingProvider();
    }

    @Override
    public /* synthetic */ BlockView getWorld() {
        return this.getWorld();
    }

    final class MainThreadExecutor
    extends ThreadExecutor<Runnable> {
        private MainThreadExecutor(World world) {
            super("Chunk source main thread executor for " + world.getRegistryKey().getValue());
        }

        @Override
        protected Runnable createTask(Runnable runnable) {
            return runnable;
        }

        @Override
        protected boolean canExecute(Runnable task) {
            return true;
        }

        @Override
        protected boolean shouldExecuteAsync() {
            return true;
        }

        @Override
        protected Thread getThread() {
            return ServerChunkManager.this.serverThread;
        }

        @Override
        protected void executeTask(Runnable task) {
            ServerChunkManager.this.world.getProfiler().visit("runTask");
            super.executeTask(task);
        }

        @Override
        protected boolean runTask() {
            if (ServerChunkManager.this.tick()) {
                return true;
            }
            ServerChunkManager.this.lightProvider.tick();
            return super.runTask();
        }
    }
}

