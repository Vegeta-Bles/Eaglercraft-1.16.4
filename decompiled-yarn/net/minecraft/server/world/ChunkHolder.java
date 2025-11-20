package net.minecraft.server.world;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;

public class ChunkHolder {
   public static final Either<Chunk, ChunkHolder.Unloaded> UNLOADED_CHUNK = Either.right(ChunkHolder.Unloaded.INSTANCE);
   public static final CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_CHUNK);
   public static final Either<WorldChunk, ChunkHolder.Unloaded> UNLOADED_WORLD_CHUNK = Either.right(ChunkHolder.Unloaded.INSTANCE);
   private static final CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> UNLOADED_WORLD_CHUNK_FUTURE = CompletableFuture.completedFuture(
      UNLOADED_WORLD_CHUNK
   );
   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.createOrderedList();
   private static final ChunkHolder.LevelType[] LEVEL_TYPES = ChunkHolder.LevelType.values();
   private final AtomicReferenceArray<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>> futuresByStatus = new AtomicReferenceArray<>(
      CHUNK_STATUSES.size()
   );
   private volatile CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> accessibleFuture = UNLOADED_WORLD_CHUNK_FUTURE;
   private volatile CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> tickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
   private volatile CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> entityTickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
   private CompletableFuture<Chunk> savingFuture = CompletableFuture.completedFuture(null);
   private int lastTickLevel;
   private int level;
   private int completedLevel;
   private final ChunkPos pos;
   private boolean pendingBlockUpdates;
   private final ShortSet[] blockUpdatesBySection = new ShortSet[16];
   private int blockLightUpdateBits;
   private int skyLightUpdateBits;
   private final LightingProvider lightingProvider;
   private final ChunkHolder.LevelUpdateListener levelUpdateListener;
   private final ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider;
   private boolean accessible;
   private boolean field_26744;

   public ChunkHolder(
      ChunkPos pos,
      int level,
      LightingProvider lightingProvider,
      ChunkHolder.LevelUpdateListener levelUpdateListener,
      ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider
   ) {
      this.pos = pos;
      this.lightingProvider = lightingProvider;
      this.levelUpdateListener = levelUpdateListener;
      this.playersWatchingChunkProvider = playersWatchingChunkProvider;
      this.lastTickLevel = ThreadedAnvilChunkStorage.MAX_LEVEL + 1;
      this.level = this.lastTickLevel;
      this.completedLevel = this.lastTickLevel;
      this.setLevel(level);
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getFutureFor(ChunkStatus leastStatus) {
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowman = this.futuresByStatus.get(leastStatus.getIndex());
      return _snowman == null ? UNLOADED_CHUNK_FUTURE : _snowman;
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getValidFutureFor(ChunkStatus leastStatus) {
      return getTargetStatusForLevel(this.level).isAtLeast(leastStatus) ? this.getFutureFor(leastStatus) : UNLOADED_CHUNK_FUTURE;
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> getTickingFuture() {
      return this.tickingFuture;
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> getEntityTickingFuture() {
      return this.entityTickingFuture;
   }

   public CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> getAccessibleFuture() {
      return this.accessibleFuture;
   }

   @Nullable
   public WorldChunk getWorldChunk() {
      CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowman = this.getTickingFuture();
      Either<WorldChunk, ChunkHolder.Unloaded> _snowmanx = _snowman.getNow(null);
      return _snowmanx == null ? null : (WorldChunk)_snowmanx.left().orElse(null);
   }

   @Nullable
   public ChunkStatus getCurrentStatus() {
      for (int _snowman = CHUNK_STATUSES.size() - 1; _snowman >= 0; _snowman--) {
         ChunkStatus _snowmanx = CHUNK_STATUSES.get(_snowman);
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxx = this.getFutureFor(_snowmanx);
         if (_snowmanxx.getNow(UNLOADED_CHUNK).left().isPresent()) {
            return _snowmanx;
         }
      }

      return null;
   }

   @Nullable
   public Chunk getCurrentChunk() {
      for (int _snowman = CHUNK_STATUSES.size() - 1; _snowman >= 0; _snowman--) {
         ChunkStatus _snowmanx = CHUNK_STATUSES.get(_snowman);
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxx = this.getFutureFor(_snowmanx);
         if (!_snowmanxx.isCompletedExceptionally()) {
            Optional<Chunk> _snowmanxxx = _snowmanxx.getNow(UNLOADED_CHUNK).left();
            if (_snowmanxxx.isPresent()) {
               return _snowmanxxx.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<Chunk> getSavingFuture() {
      return this.savingFuture;
   }

   public void markForBlockUpdate(BlockPos _snowman) {
      WorldChunk _snowmanx = this.getWorldChunk();
      if (_snowmanx != null) {
         byte _snowmanxx = (byte)ChunkSectionPos.getSectionCoord(_snowman.getY());
         if (this.blockUpdatesBySection[_snowmanxx] == null) {
            this.pendingBlockUpdates = true;
            this.blockUpdatesBySection[_snowmanxx] = new ShortArraySet();
         }

         this.blockUpdatesBySection[_snowmanxx].add(ChunkSectionPos.packLocal(_snowman));
      }
   }

   public void markForLightUpdate(LightType type, int y) {
      WorldChunk _snowman = this.getWorldChunk();
      if (_snowman != null) {
         _snowman.setShouldSave(true);
         if (type == LightType.SKY) {
            this.skyLightUpdateBits |= 1 << y - -1;
         } else {
            this.blockLightUpdateBits |= 1 << y - -1;
         }
      }
   }

   public void flushUpdates(WorldChunk chunk) {
      if (this.pendingBlockUpdates || this.skyLightUpdateBits != 0 || this.blockLightUpdateBits != 0) {
         World _snowman = chunk.getWorld();
         int _snowmanx = 0;

         for (int _snowmanxx = 0; _snowmanxx < this.blockUpdatesBySection.length; _snowmanxx++) {
            _snowmanx += this.blockUpdatesBySection[_snowmanxx] != null ? this.blockUpdatesBySection[_snowmanxx].size() : 0;
         }

         this.field_26744 |= _snowmanx >= 64;
         if (this.skyLightUpdateBits != 0 || this.blockLightUpdateBits != 0) {
            this.sendPacketToPlayersWatching(
               new LightUpdateS2CPacket(chunk.getPos(), this.lightingProvider, this.skyLightUpdateBits, this.blockLightUpdateBits, true), !this.field_26744
            );
            this.skyLightUpdateBits = 0;
            this.blockLightUpdateBits = 0;
         }

         for (int _snowmanxx = 0; _snowmanxx < this.blockUpdatesBySection.length; _snowmanxx++) {
            ShortSet _snowmanxxx = this.blockUpdatesBySection[_snowmanxx];
            if (_snowmanxxx != null) {
               ChunkSectionPos _snowmanxxxx = ChunkSectionPos.from(chunk.getPos(), _snowmanxx);
               if (_snowmanxxx.size() == 1) {
                  BlockPos _snowmanxxxxx = _snowmanxxxx.unpackBlockPos(_snowmanxxx.iterator().nextShort());
                  BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
                  this.sendPacketToPlayersWatching(new BlockUpdateS2CPacket(_snowmanxxxxx, _snowmanxxxxxx), false);
                  this.tryUpdateBlockEntityAt(_snowman, _snowmanxxxxx, _snowmanxxxxxx);
               } else {
                  ChunkSection _snowmanxxxxx = chunk.getSectionArray()[_snowmanxxxx.getY()];
                  ChunkDeltaUpdateS2CPacket _snowmanxxxxxx = new ChunkDeltaUpdateS2CPacket(_snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.field_26744);
                  this.sendPacketToPlayersWatching(_snowmanxxxxxx, false);
                  _snowmanxxxxxx.visitUpdates((_snowmanxxxxxxx, _snowmanxxxxxxxx) -> this.tryUpdateBlockEntityAt(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx));
               }

               this.blockUpdatesBySection[_snowmanxx] = null;
            }
         }

         this.pendingBlockUpdates = false;
      }
   }

   private void tryUpdateBlockEntityAt(World _snowman, BlockPos _snowman, BlockState _snowman) {
      if (_snowman.getBlock().hasBlockEntity()) {
         this.sendBlockEntityUpdatePacket(_snowman, _snowman);
      }
   }

   private void sendBlockEntityUpdatePacket(World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman != null) {
         BlockEntityUpdateS2CPacket _snowmanx = _snowman.toUpdatePacket();
         if (_snowmanx != null) {
            this.sendPacketToPlayersWatching(_snowmanx, false);
         }
      }
   }

   private void sendPacketToPlayersWatching(Packet<?> _snowman, boolean onlyOnWatchDistanceEdge) {
      this.playersWatchingChunkProvider.getPlayersWatchingChunk(this.pos, onlyOnWatchDistanceEdge).forEach(_snowmanxx -> _snowmanxx.networkHandler.sendPacket(_snowman));
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkAt(ChunkStatus targetStatus, ThreadedAnvilChunkStorage chunkStorage) {
      int _snowman = targetStatus.getIndex();
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanx = this.futuresByStatus.get(_snowman);
      if (_snowmanx != null) {
         Either<Chunk, ChunkHolder.Unloaded> _snowmanxx = _snowmanx.getNow(null);
         if (_snowmanxx == null || _snowmanxx.left().isPresent()) {
            return _snowmanx;
         }
      }

      if (getTargetStatusForLevel(this.level).isAtLeast(targetStatus)) {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxx = chunkStorage.getChunk(this, targetStatus);
         this.combineSavingFuture(_snowmanxx);
         this.futuresByStatus.set(_snowman, _snowmanxx);
         return _snowmanxx;
      } else {
         return _snowmanx == null ? UNLOADED_CHUNK_FUTURE : _snowmanx;
      }
   }

   private void combineSavingFuture(CompletableFuture<? extends Either<? extends Chunk, ChunkHolder.Unloaded>> then) {
      this.savingFuture = this.savingFuture.thenCombine(then, (_snowman, _snowmanx) -> (Chunk)_snowmanx.map(_snowmanxx -> _snowmanxx, _snowmanxxx -> _snowman));
   }

   public ChunkHolder.LevelType getLevelType() {
      return getLevelType(this.level);
   }

   public ChunkPos getPos() {
      return this.pos;
   }

   public int getLevel() {
      return this.level;
   }

   public int getCompletedLevel() {
      return this.completedLevel;
   }

   private void setCompletedLevel(int level) {
      this.completedLevel = level;
   }

   public void setLevel(int level) {
      this.level = level;
   }

   protected void tick(ThreadedAnvilChunkStorage chunkStorage) {
      ChunkStatus _snowman = getTargetStatusForLevel(this.lastTickLevel);
      ChunkStatus _snowmanx = getTargetStatusForLevel(this.level);
      boolean _snowmanxx = this.lastTickLevel <= ThreadedAnvilChunkStorage.MAX_LEVEL;
      boolean _snowmanxxx = this.level <= ThreadedAnvilChunkStorage.MAX_LEVEL;
      ChunkHolder.LevelType _snowmanxxxx = getLevelType(this.lastTickLevel);
      ChunkHolder.LevelType _snowmanxxxxx = getLevelType(this.level);
      if (_snowmanxx) {
         Either<Chunk, ChunkHolder.Unloaded> _snowmanxxxxxx = Either.right(new ChunkHolder.Unloaded() {
            @Override
            public String toString() {
               return "Unloaded ticket level " + ChunkHolder.this.pos.toString();
            }
         });

         for (int _snowmanxxxxxxx = _snowmanxxx ? _snowmanx.getIndex() + 1 : 0; _snowmanxxxxxxx <= _snowman.getIndex(); _snowmanxxxxxxx++) {
            CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxxxxxxxx = this.futuresByStatus.get(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.complete(_snowmanxxxxxx);
            } else {
               this.futuresByStatus.set(_snowmanxxxxxxx, CompletableFuture.completedFuture(_snowmanxxxxxx));
            }
         }
      }

      boolean _snowmanxxxxxx = _snowmanxxxx.isAfter(ChunkHolder.LevelType.BORDER);
      boolean _snowmanxxxxxxxx = _snowmanxxxxx.isAfter(ChunkHolder.LevelType.BORDER);
      this.accessible |= _snowmanxxxxxxxx;
      if (!_snowmanxxxxxx && _snowmanxxxxxxxx) {
         this.accessibleFuture = chunkStorage.makeChunkAccessible(this);
         this.combineSavingFuture(this.accessibleFuture);
      }

      if (_snowmanxxxxxx && !_snowmanxxxxxxxx) {
         CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> _snowmanxxxxxxxxx = this.accessibleFuture;
         this.accessibleFuture = UNLOADED_WORLD_CHUNK_FUTURE;
         this.combineSavingFuture(_snowmanxxxxxxxxx.thenApply(_snowmanxxxxxxxxxx -> _snowmanxxxxxxxxxx.ifLeft(chunkStorage::enableTickSchedulers)));
      }

      boolean _snowmanxxxxxxxxx = _snowmanxxxx.isAfter(ChunkHolder.LevelType.TICKING);
      boolean _snowmanxxxxxxxxxx = _snowmanxxxxx.isAfter(ChunkHolder.LevelType.TICKING);
      if (!_snowmanxxxxxxxxx && _snowmanxxxxxxxxxx) {
         this.tickingFuture = chunkStorage.makeChunkTickable(this);
         this.combineSavingFuture(this.tickingFuture);
      }

      if (_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxx) {
         this.tickingFuture.complete(UNLOADED_WORLD_CHUNK);
         this.tickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
      }

      boolean _snowmanxxxxxxxxxxx = _snowmanxxxx.isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxx.isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
      if (!_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         if (this.entityTickingFuture != UNLOADED_WORLD_CHUNK_FUTURE) {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException());
         }

         this.entityTickingFuture = chunkStorage.makeChunkEntitiesTickable(this.pos);
         this.combineSavingFuture(this.entityTickingFuture);
      }

      if (_snowmanxxxxxxxxxxx && !_snowmanxxxxxxxxxxxx) {
         this.entityTickingFuture.complete(UNLOADED_WORLD_CHUNK);
         this.entityTickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
      }

      this.levelUpdateListener.updateLevel(this.pos, this::getCompletedLevel, this.level, this::setCompletedLevel);
      this.lastTickLevel = this.level;
   }

   public static ChunkStatus getTargetStatusForLevel(int level) {
      return level < 33 ? ChunkStatus.FULL : ChunkStatus.byDistanceFromFull(level - 33);
   }

   public static ChunkHolder.LevelType getLevelType(int distance) {
      return LEVEL_TYPES[MathHelper.clamp(33 - distance + 1, 0, LEVEL_TYPES.length - 1)];
   }

   public boolean isAccessible() {
      return this.accessible;
   }

   public void updateAccessibleStatus() {
      this.accessible = getLevelType(this.level).isAfter(ChunkHolder.LevelType.BORDER);
   }

   public void setCompletedChunk(ReadOnlyChunk _snowman) {
      for (int _snowmanx = 0; _snowmanx < this.futuresByStatus.length(); _snowmanx++) {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> _snowmanxx = this.futuresByStatus.get(_snowmanx);
         if (_snowmanxx != null) {
            Optional<Chunk> _snowmanxxx = _snowmanxx.getNow(UNLOADED_CHUNK).left();
            if (_snowmanxxx.isPresent() && _snowmanxxx.get() instanceof ProtoChunk) {
               this.futuresByStatus.set(_snowmanx, CompletableFuture.completedFuture(Either.left(_snowman)));
            }
         }
      }

      this.combineSavingFuture(CompletableFuture.completedFuture(Either.left(_snowman.getWrappedChunk())));
   }

   public static enum LevelType {
      INACCESSIBLE,
      BORDER,
      TICKING,
      ENTITY_TICKING;

      private LevelType() {
      }

      public boolean isAfter(ChunkHolder.LevelType levelType) {
         return this.ordinal() >= levelType.ordinal();
      }
   }

   public interface LevelUpdateListener {
      void updateLevel(ChunkPos pos, IntSupplier levelGetter, int targetLevel, IntConsumer levelSetter);
   }

   public interface PlayersWatchingChunkProvider {
      Stream<ServerPlayerEntity> getPlayersWatchingChunk(ChunkPos chunkPos, boolean onlyOnWatchDistanceEdge);
   }

   public interface Unloaded {
      ChunkHolder.Unloaded INSTANCE = new ChunkHolder.Unloaded() {
         @Override
         public String toString() {
            return "UNLOADED";
         }
      };
   }
}
