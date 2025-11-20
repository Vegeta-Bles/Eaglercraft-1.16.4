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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.futuresByStatus.get(leastStatus.getIndex());
      return completableFuture == null ? UNLOADED_CHUNK_FUTURE : completableFuture;
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
      CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> completableFuture = this.getTickingFuture();
      Either<WorldChunk, ChunkHolder.Unloaded> either = completableFuture.getNow(null);
      return either == null ? null : (WorldChunk)either.left().orElse(null);
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public ChunkStatus getCurrentStatus() {
      for (int i = CHUNK_STATUSES.size() - 1; i >= 0; i--) {
         ChunkStatus lv = CHUNK_STATUSES.get(i);
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.getFutureFor(lv);
         if (completableFuture.getNow(UNLOADED_CHUNK).left().isPresent()) {
            return lv;
         }
      }

      return null;
   }

   @Nullable
   public Chunk getCurrentChunk() {
      for (int i = CHUNK_STATUSES.size() - 1; i >= 0; i--) {
         ChunkStatus lv = CHUNK_STATUSES.get(i);
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.getFutureFor(lv);
         if (!completableFuture.isCompletedExceptionally()) {
            Optional<Chunk> optional = completableFuture.getNow(UNLOADED_CHUNK).left();
            if (optional.isPresent()) {
               return optional.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<Chunk> getSavingFuture() {
      return this.savingFuture;
   }

   public void markForBlockUpdate(BlockPos arg) {
      WorldChunk lv = this.getWorldChunk();
      if (lv != null) {
         byte b = (byte)ChunkSectionPos.getSectionCoord(arg.getY());
         if (this.blockUpdatesBySection[b] == null) {
            this.pendingBlockUpdates = true;
            this.blockUpdatesBySection[b] = new ShortArraySet();
         }

         this.blockUpdatesBySection[b].add(ChunkSectionPos.packLocal(arg));
      }
   }

   public void markForLightUpdate(LightType type, int y) {
      WorldChunk lv = this.getWorldChunk();
      if (lv != null) {
         lv.setShouldSave(true);
         if (type == LightType.SKY) {
            this.skyLightUpdateBits |= 1 << y - -1;
         } else {
            this.blockLightUpdateBits |= 1 << y - -1;
         }
      }
   }

   public void flushUpdates(WorldChunk chunk) {
      if (this.pendingBlockUpdates || this.skyLightUpdateBits != 0 || this.blockLightUpdateBits != 0) {
         World lv = chunk.getWorld();
         int i = 0;

         for (int j = 0; j < this.blockUpdatesBySection.length; j++) {
            i += this.blockUpdatesBySection[j] != null ? this.blockUpdatesBySection[j].size() : 0;
         }

         this.field_26744 |= i >= 64;
         if (this.skyLightUpdateBits != 0 || this.blockLightUpdateBits != 0) {
            this.sendPacketToPlayersWatching(
               new LightUpdateS2CPacket(chunk.getPos(), this.lightingProvider, this.skyLightUpdateBits, this.blockLightUpdateBits, true), !this.field_26744
            );
            this.skyLightUpdateBits = 0;
            this.blockLightUpdateBits = 0;
         }

         for (int k = 0; k < this.blockUpdatesBySection.length; k++) {
            ShortSet shortSet = this.blockUpdatesBySection[k];
            if (shortSet != null) {
               ChunkSectionPos lv2 = ChunkSectionPos.from(chunk.getPos(), k);
               if (shortSet.size() == 1) {
                  BlockPos lv3 = lv2.unpackBlockPos(shortSet.iterator().nextShort());
                  BlockState lv4 = lv.getBlockState(lv3);
                  this.sendPacketToPlayersWatching(new BlockUpdateS2CPacket(lv3, lv4), false);
                  this.tryUpdateBlockEntityAt(lv, lv3, lv4);
               } else {
                  ChunkSection lv5 = chunk.getSectionArray()[lv2.getY()];
                  ChunkDeltaUpdateS2CPacket lv6 = new ChunkDeltaUpdateS2CPacket(lv2, shortSet, lv5, this.field_26744);
                  this.sendPacketToPlayersWatching(lv6, false);
                  lv6.visitUpdates((arg2, arg3) -> this.tryUpdateBlockEntityAt(lv, arg2, arg3));
               }

               this.blockUpdatesBySection[k] = null;
            }
         }

         this.pendingBlockUpdates = false;
      }
   }

   private void tryUpdateBlockEntityAt(World arg, BlockPos arg2, BlockState arg3) {
      if (arg3.getBlock().hasBlockEntity()) {
         this.sendBlockEntityUpdatePacket(arg, arg2);
      }
   }

   private void sendBlockEntityUpdatePacket(World world, BlockPos pos) {
      BlockEntity lv = world.getBlockEntity(pos);
      if (lv != null) {
         BlockEntityUpdateS2CPacket lv2 = lv.toUpdatePacket();
         if (lv2 != null) {
            this.sendPacketToPlayersWatching(lv2, false);
         }
      }
   }

   private void sendPacketToPlayersWatching(Packet<?> arg, boolean onlyOnWatchDistanceEdge) {
      this.playersWatchingChunkProvider.getPlayersWatchingChunk(this.pos, onlyOnWatchDistanceEdge).forEach(arg2 -> arg2.networkHandler.sendPacket(arg));
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> getChunkAt(ChunkStatus targetStatus, ThreadedAnvilChunkStorage chunkStorage) {
      int i = targetStatus.getIndex();
      CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.futuresByStatus.get(i);
      if (completableFuture != null) {
         Either<Chunk, ChunkHolder.Unloaded> either = completableFuture.getNow(null);
         if (either == null || either.left().isPresent()) {
            return completableFuture;
         }
      }

      if (getTargetStatusForLevel(this.level).isAtLeast(targetStatus)) {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture2 = chunkStorage.getChunk(this, targetStatus);
         this.combineSavingFuture(completableFuture2);
         this.futuresByStatus.set(i, completableFuture2);
         return completableFuture2;
      } else {
         return completableFuture == null ? UNLOADED_CHUNK_FUTURE : completableFuture;
      }
   }

   private void combineSavingFuture(CompletableFuture<? extends Either<? extends Chunk, ChunkHolder.Unloaded>> then) {
      this.savingFuture = this.savingFuture.thenCombine(then, (arg, either) -> (Chunk)either.map(argx -> argx, arg2 -> arg));
   }

   @Environment(EnvType.CLIENT)
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
      ChunkStatus lv = getTargetStatusForLevel(this.lastTickLevel);
      ChunkStatus lv2 = getTargetStatusForLevel(this.level);
      boolean bl = this.lastTickLevel <= ThreadedAnvilChunkStorage.MAX_LEVEL;
      boolean bl2 = this.level <= ThreadedAnvilChunkStorage.MAX_LEVEL;
      ChunkHolder.LevelType lv3 = getLevelType(this.lastTickLevel);
      ChunkHolder.LevelType lv4 = getLevelType(this.level);
      if (bl) {
         Either<Chunk, ChunkHolder.Unloaded> either = Either.right(new ChunkHolder.Unloaded() {
            @Override
            public String toString() {
               return "Unloaded ticket level " + ChunkHolder.this.pos.toString();
            }
         });

         for (int i = bl2 ? lv2.getIndex() + 1 : 0; i <= lv.getIndex(); i++) {
            CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.futuresByStatus.get(i);
            if (completableFuture != null) {
               completableFuture.complete(either);
            } else {
               this.futuresByStatus.set(i, CompletableFuture.completedFuture(either));
            }
         }
      }

      boolean bl3 = lv3.isAfter(ChunkHolder.LevelType.BORDER);
      boolean bl4 = lv4.isAfter(ChunkHolder.LevelType.BORDER);
      this.accessible |= bl4;
      if (!bl3 && bl4) {
         this.accessibleFuture = chunkStorage.makeChunkAccessible(this);
         this.combineSavingFuture(this.accessibleFuture);
      }

      if (bl3 && !bl4) {
         CompletableFuture<Either<WorldChunk, ChunkHolder.Unloaded>> completableFuture2 = this.accessibleFuture;
         this.accessibleFuture = UNLOADED_WORLD_CHUNK_FUTURE;
         this.combineSavingFuture(completableFuture2.thenApply(either -> either.ifLeft(chunkStorage::enableTickSchedulers)));
      }

      boolean bl5 = lv3.isAfter(ChunkHolder.LevelType.TICKING);
      boolean bl6 = lv4.isAfter(ChunkHolder.LevelType.TICKING);
      if (!bl5 && bl6) {
         this.tickingFuture = chunkStorage.makeChunkTickable(this);
         this.combineSavingFuture(this.tickingFuture);
      }

      if (bl5 && !bl6) {
         this.tickingFuture.complete(UNLOADED_WORLD_CHUNK);
         this.tickingFuture = UNLOADED_WORLD_CHUNK_FUTURE;
      }

      boolean bl7 = lv3.isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
      boolean bl8 = lv4.isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
      if (!bl7 && bl8) {
         if (this.entityTickingFuture != UNLOADED_WORLD_CHUNK_FUTURE) {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException());
         }

         this.entityTickingFuture = chunkStorage.makeChunkEntitiesTickable(this.pos);
         this.combineSavingFuture(this.entityTickingFuture);
      }

      if (bl7 && !bl8) {
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

   public void setCompletedChunk(ReadOnlyChunk arg) {
      for (int i = 0; i < this.futuresByStatus.length(); i++) {
         CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> completableFuture = this.futuresByStatus.get(i);
         if (completableFuture != null) {
            Optional<Chunk> optional = completableFuture.getNow(UNLOADED_CHUNK).left();
            if (optional.isPresent() && optional.get() instanceof ProtoChunk) {
               this.futuresByStatus.set(i, CompletableFuture.completedFuture(Either.left(arg)));
            }
         }
      }

      this.combineSavingFuture(CompletableFuture.completedFuture(Either.left(arg.getWrappedChunk())));
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
