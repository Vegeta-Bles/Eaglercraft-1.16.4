package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
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
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.poi.PointOfInterestStorage;

public class ServerChunkManager extends ChunkManager {
   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.createOrderedList();
   private final ChunkTicketManager ticketManager;
   private final ChunkGenerator chunkGenerator;
   private final ServerWorld world;
   private final Thread serverThread;
   private final ServerLightingProvider lightProvider;
   private final ServerChunkManager.MainThreadExecutor mainThreadExecutor;
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

   public ServerChunkManager(
      ServerWorld _snowman,
      LevelStorage.Session _snowman,
      DataFixer dataFixer,
      StructureManager structureManager,
      Executor workerExecutor,
      ChunkGenerator chunkGenerator,
      int viewDistance,
      boolean _snowman,
      WorldGenerationProgressListener _snowman,
      Supplier<PersistentStateManager> _snowman
   ) {
      this.world = _snowman;
      this.mainThreadExecutor = new ServerChunkManager.MainThreadExecutor(_snowman);
      this.chunkGenerator = chunkGenerator;
      this.serverThread = Thread.currentThread();
      File _snowmanxxxxx = _snowman.getWorldDirectory(_snowman.getRegistryKey());
      File _snowmanxxxxxx = new File(_snowmanxxxxx, "data");
      _snowmanxxxxxx.mkdirs();
      this.persistentStateManager = new PersistentStateManager(_snowmanxxxxxx, dataFixer);
      this.threadedAnvilChunkStorage = new ThreadedAnvilChunkStorage(
         _snowman, _snowman, dataFixer, structureManager, workerExecutor, this.mainThreadExecutor, this, this.getChunkGenerator(), _snowman, _snowman, viewDistance, _snowman
      );
      this.lightProvider = this.threadedAnvilChunkStorage.getLightProvider();
      this.ticketManager = this.threadedAnvilChunkStorage.getTicketManager();
      this.initChunkCaches();
   }

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
      for (int _snowman = 3; _snowman > 0; _snowman--) {
         this.chunkPosCache[_snowman] = this.chunkPosCache[_snowman - 1];
         this.chunkStatusCache[_snowman] = this.chunkStatusCache[_snowman - 1];
         this.chunkCache[_snowman] = this.chunkCache[_snowman - 1];
      }

      this.chunkPosCache[0] = pos;
      this.chunkStatusCache[0] = status;
      this.chunkCache[0] = chunk;
   }

   @Nullable
   @Override
   public Chunk getChunk(int x, int z, ChunkStatus leastStatus, boolean create) {
      if (Thread.currentThread() != this.serverThread) {
         return CompletableFuture.<Chunk>supplyAsync(() -> this.getChunk(x, z, leastStatus, create), this.mainThreadExecutor).join();
      } else {
         Profiler _snowman = this.world.getProfiler();
         _snowman.visit("getChunk");
         long _snowmanx = ChunkPos.toLong(x, z);

         for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
            if (_snowmanx == this.chunkPosCache[_snowmanxx] && leastStatus == this.chunkStatusCache[_snowmanxx]) {
               Chunk _snowmanxxx = this.chunkCache[_snowmanxx];
               if (_snowmanxxx != null || !create) {
                  return _snowmanxxx;
               }
            }
         }

         _snowman.visit("getChunkCacheMiss");
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxxx = this.getChunkFuture(x, z, leastStatus, create);
         this.mainThreadExecutor.runTasks(_snowmanxxx::isDone);
         Chunk _snowmanxxxx = (Chunk)_snowmanxxx.join().map(_snowmanxxxxx -> _snowmanxxxxx, _snowmanxxxxx -> {
            if (create) {
               throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Chunk not there when requested: " + _snowmanxxxxx));
            } else {
               return null;
            }
         });
         this.putInCache(_snowmanx, _snowmanxxxx, leastStatus);
         return _snowmanxxxx;
      }
   }

   @Nullable
   @Override
   public WorldChunk getWorldChunk(int chunkX, int chunkZ) {
      if (Thread.currentThread() != this.serverThread) {
         return null;
      } else {
         this.world.getProfiler().visit("getChunkNow");
         long _snowman = ChunkPos.toLong(chunkX, chunkZ);

         for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
            if (_snowman == this.chunkPosCache[_snowmanx] && this.chunkStatusCache[_snowmanx] == ChunkStatus.FULL) {
               Chunk _snowmanxx = this.chunkCache[_snowmanx];
               return _snowmanxx instanceof WorldChunk ? (WorldChunk)_snowmanxx : null;
            }
         }

         ChunkHolder _snowmanxx = this.getChunkHolder(_snowman);
         if (_snowmanxx == null) {
            return null;
         } else {
            Either<Chunk, ChunkHolder.Unloaded> _snowmanxxx = _snowmanxx.getValidFutureFor(ChunkStatus.FULL).getNow(null);
            if (_snowmanxxx == null) {
               return null;
            } else {
               Chunk _snowmanxxxx = (Chunk)_snowmanxxx.left().orElse(null);
               if (_snowmanxxxx != null) {
                  this.putInCache(_snowman, _snowmanxxxx, ChunkStatus.FULL);
                  if (_snowmanxxxx instanceof WorldChunk) {
                     return (WorldChunk)_snowmanxxxx;
                  }
               }

               return null;
            }
         }
      }
   }

   private void initChunkCaches() {
      Arrays.fill(this.chunkPosCache, ChunkPos.MARKER);
      Arrays.fill(this.chunkStatusCache, null);
      Arrays.fill(this.chunkCache, null);
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkFutureSyncOnMainThread(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
      boolean _snowman = Thread.currentThread() == this.serverThread;
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanx;
      if (_snowman) {
         _snowmanx = this.getChunkFuture(chunkX, chunkZ, leastStatus, create);
         this.mainThreadExecutor.runTasks(_snowmanx::isDone);
      } else {
         _snowmanx = CompletableFuture.<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>>supplyAsync(
               () -> this.getChunkFuture(chunkX, chunkZ, leastStatus, create), this.mainThreadExecutor
            )
            .thenCompose(_snowmanxx -> (CompletionStage<Either<Chunk, ChunkHolder.Unloaded>>)_snowmanxx);
      }

      return _snowmanx;
   }

   private CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkFuture(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
      ChunkPos _snowman = new ChunkPos(chunkX, chunkZ);
      long _snowmanx = _snowman.toLong();
      int _snowmanxx = 33 + ChunkStatus.getDistanceFromFull(leastStatus);
      ChunkHolder _snowmanxxx = this.getChunkHolder(_snowmanx);
      if (create) {
         this.ticketManager.addTicketWithLevel(ChunkTicketType.UNKNOWN, _snowman, _snowmanxx, _snowman);
         if (this.isMissingForLevel(_snowmanxxx, _snowmanxx)) {
            Profiler _snowmanxxxx = this.world.getProfiler();
            _snowmanxxxx.push("chunkLoad");
            this.tick();
            _snowmanxxx = this.getChunkHolder(_snowmanx);
            _snowmanxxxx.pop();
            if (this.isMissingForLevel(_snowmanxxx, _snowmanxx)) {
               throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("No chunk holder after ticket has been added"));
            }
         }
      }

      return this.isMissingForLevel(_snowmanxxx, _snowmanxx) ? ChunkHolder.UNLOADED_CHUNK_FUTURE : _snowmanxxx.getChunkAt(leastStatus, this.threadedAnvilChunkStorage);
   }

   private boolean isMissingForLevel(@Nullable ChunkHolder holder, int maxLevel) {
      return holder == null || holder.getLevel() > maxLevel;
   }

   @Override
   public boolean isChunkLoaded(int x, int z) {
      ChunkHolder _snowman = this.getChunkHolder(new ChunkPos(x, z).toLong());
      int _snowmanx = 33 + ChunkStatus.getDistanceFromFull(ChunkStatus.FULL);
      return !this.isMissingForLevel(_snowman, _snowmanx);
   }

   @Override
   public BlockView getChunk(int chunkX, int chunkZ) {
      long _snowman = ChunkPos.toLong(chunkX, chunkZ);
      ChunkHolder _snowmanx = this.getChunkHolder(_snowman);
      if (_snowmanx == null) {
         return null;
      } else {
         int _snowmanxx = CHUNK_STATUSES.size() - 1;

         while (true) {
            ChunkStatus _snowmanxxx = CHUNK_STATUSES.get(_snowmanxx);
            Optional<Chunk> _snowmanxxxx = _snowmanx.getFutureFor(_snowmanxxx).getNow(ChunkHolder.UNLOADED_CHUNK).left();
            if (_snowmanxxxx.isPresent()) {
               return _snowmanxxxx.get();
            }

            if (_snowmanxxx == ChunkStatus.LIGHT.getPrevious()) {
               return null;
            }

            _snowmanxx--;
         }
      }
   }

   public World getWorld() {
      return this.world;
   }

   public boolean executeQueuedTasks() {
      return this.mainThreadExecutor.runTask();
   }

   private boolean tick() {
      boolean _snowman = this.ticketManager.tick(this.threadedAnvilChunkStorage);
      boolean _snowmanx = this.threadedAnvilChunkStorage.updateHolderMap();
      if (!_snowman && !_snowmanx) {
         return false;
      } else {
         this.initChunkCaches();
         return true;
      }
   }

   @Override
   public boolean shouldTickEntity(Entity entity) {
      long _snowman = ChunkPos.toLong(MathHelper.floor(entity.getX()) >> 4, MathHelper.floor(entity.getZ()) >> 4);
      return this.isFutureReady(_snowman, ChunkHolder::getEntityTickingFuture);
   }

   @Override
   public boolean shouldTickChunk(ChunkPos pos) {
      return this.isFutureReady(pos.toLong(), ChunkHolder::getEntityTickingFuture);
   }

   @Override
   public boolean shouldTickBlock(BlockPos pos) {
      long _snowman = ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4);
      return this.isFutureReady(_snowman, ChunkHolder::getTickingFuture);
   }

   private boolean isFutureReady(long pos, Function<ChunkHolder, CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>>> futureFunction) {
      ChunkHolder _snowman = this.getChunkHolder(pos);
      if (_snowman == null) {
         return false;
      } else {
         Either<WorldChunk, ChunkHolder.Unloaded> _snowmanx = futureFunction.apply(_snowman).getNow(ChunkHolder.UNLOADED_WORLD_CHUNK);
         return _snowmanx.left().isPresent();
      }
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
      long _snowman = this.world.getTime();
      long _snowmanx = _snowman - this.lastMobSpawningTime;
      this.lastMobSpawningTime = _snowman;
      WorldProperties _snowmanxx = this.world.getLevelProperties();
      boolean _snowmanxxx = this.world.isDebugWorld();
      boolean _snowmanxxxx = this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING);
      if (!_snowmanxxx) {
         this.world.getProfiler().push("pollingChunks");
         int _snowmanxxxxx = this.world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
         boolean _snowmanxxxxxx = _snowmanxx.getTime() % 400L == 0L;
         this.world.getProfiler().push("naturalSpawnCount");
         int _snowmanxxxxxxx = this.ticketManager.getSpawningChunkCount();
         SpawnHelper.Info _snowmanxxxxxxxx = SpawnHelper.setupSpawn(_snowmanxxxxxxx, this.world.iterateEntities(), this::ifChunkLoaded);
         this.spawnEntry = _snowmanxxxxxxxx;
         this.world.getProfiler().pop();
         List<ChunkHolder> _snowmanxxxxxxxxx = Lists.newArrayList(this.threadedAnvilChunkStorage.entryIterator());
         Collections.shuffle(_snowmanxxxxxxxxx);
         _snowmanxxxxxxxxx.forEach(_snowmanxxxxxxxxxx -> {
            Optional<WorldChunk> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getTickingFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left();
            if (_snowmanxxxxxxxxxxx.isPresent()) {
               this.world.getProfiler().push("broadcast");
               _snowmanxxxxxxxxxx.flushUpdates(_snowmanxxxxxxxxxxx.get());
               this.world.getProfiler().pop();
               Optional<WorldChunk> _snowmanx = _snowmanxxxxxxxxxx.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left();
               if (_snowmanx.isPresent()) {
                  WorldChunk _snowmanxx = _snowmanx.get();
                  ChunkPos _snowmanxxx = _snowmanxxxxxxxxxx.getPos();
                  if (!this.threadedAnvilChunkStorage.isTooFarFromPlayersToSpawnMobs(_snowmanxxx)) {
                     _snowmanxx.setInhabitedTime(_snowmanxx.getInhabitedTime() + _snowman);
                     if (_snowman && (this.spawnMonsters || this.spawnAnimals) && this.world.getWorldBorder().contains(_snowmanxx.getPos())) {
                        SpawnHelper.spawn(this.world, _snowmanxx, _snowman, this.spawnAnimals, this.spawnMonsters, _snowman);
                     }

                     this.world.tickChunk(_snowmanxx, _snowman);
                  }
               }
            }
         });
         this.world.getProfiler().push("customSpawners");
         if (_snowmanxxxx) {
            this.world.tickSpawners(this.spawnMonsters, this.spawnAnimals);
         }

         this.world.getProfiler().pop();
         this.world.getProfiler().pop();
      }

      this.threadedAnvilChunkStorage.tickPlayerMovement();
   }

   private void ifChunkLoaded(long pos, Consumer<WorldChunk> chunkConsumer) {
      ChunkHolder _snowman = this.getChunkHolder(pos);
      if (_snowman != null) {
         _snowman.getAccessibleFuture().getNow(ChunkHolder.UNLOADED_WORLD_CHUNK).left().ifPresent(chunkConsumer);
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
      int _snowman = pos.getX() >> 4;
      int _snowmanx = pos.getZ() >> 4;
      ChunkHolder _snowmanxx = this.getChunkHolder(ChunkPos.toLong(_snowman, _snowmanx));
      if (_snowmanxx != null) {
         _snowmanxx.markForBlockUpdate(pos);
      }
   }

   @Override
   public void onLightUpdate(LightType type, ChunkSectionPos pos) {
      this.mainThreadExecutor.execute(() -> {
         ChunkHolder _snowmanxx = this.getChunkHolder(pos.toChunkPos().toLong());
         if (_snowmanxx != null) {
            _snowmanxx.markForLightUpdate(type, pos.getSectionY());
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

   public void unloadEntity(Entity _snowman) {
      this.threadedAnvilChunkStorage.unloadEntity(_snowman);
   }

   public void loadEntity(Entity _snowman) {
      this.threadedAnvilChunkStorage.loadEntity(_snowman);
   }

   public void sendToNearbyPlayers(Entity entity, Packet<?> packet) {
      this.threadedAnvilChunkStorage.sendToNearbyPlayers(entity, packet);
   }

   public void sendToOtherNearbyPlayers(Entity _snowman, Packet<?> _snowman) {
      this.threadedAnvilChunkStorage.sendToOtherNearbyPlayers(_snowman, _snowman);
   }

   public void applyViewDistance(int watchDistance) {
      this.threadedAnvilChunkStorage.setViewDistance(watchDistance);
   }

   @Override
   public void setMobSpawnOptions(boolean spawnMonsters, boolean spawnAnimals) {
      this.spawnMonsters = spawnMonsters;
      this.spawnAnimals = spawnAnimals;
   }

   public String getChunkLoadingDebugInfo(ChunkPos _snowman) {
      return this.threadedAnvilChunkStorage.getChunkLoadingDebugInfo(_snowman);
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

   final class MainThreadExecutor extends ThreadExecutor<Runnable> {
      private MainThreadExecutor(World var2) {
         super("Chunk source main thread executor for " + _snowman.getRegistryKey().getValue());
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
         } else {
            ServerChunkManager.this.lightProvider.tick();
            return super.runTask();
         }
      }
   }
}
