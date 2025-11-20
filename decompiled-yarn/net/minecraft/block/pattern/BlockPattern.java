package net.minecraft.block.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldView;

public class BlockPattern {
   private final Predicate<CachedBlockPosition>[][][] pattern;
   private final int depth;
   private final int height;
   private final int width;

   public BlockPattern(Predicate<CachedBlockPosition>[][][] pattern) {
      this.pattern = pattern;
      this.depth = pattern.length;
      if (this.depth > 0) {
         this.height = pattern[0].length;
         if (this.height > 0) {
            this.width = pattern[0][0].length;
         } else {
            this.width = 0;
         }
      } else {
         this.height = 0;
         this.width = 0;
      }
   }

   public int getDepth() {
      return this.depth;
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   @Nullable
   private BlockPattern.Result testTransform(BlockPos frontTopLeft, Direction forwards, Direction up, LoadingCache<BlockPos, CachedBlockPosition> cache) {
      for (int _snowman = 0; _snowman < this.width; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < this.height; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < this.depth; _snowmanxx++) {
               if (!this.pattern[_snowmanxx][_snowmanx][_snowman].test((CachedBlockPosition)cache.getUnchecked(translate(frontTopLeft, forwards, up, _snowman, _snowmanx, _snowmanxx)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.Result(frontTopLeft, forwards, up, cache, this.width, this.height, this.depth);
   }

   @Nullable
   public BlockPattern.Result searchAround(WorldView world, BlockPos pos) {
      LoadingCache<BlockPos, CachedBlockPosition> _snowman = makeCache(world, false);
      int _snowmanx = Math.max(Math.max(this.width, this.height), this.depth);

      for (BlockPos _snowmanxx : BlockPos.iterate(pos, pos.add(_snowmanx - 1, _snowmanx - 1, _snowmanx - 1))) {
         for (Direction _snowmanxxx : Direction.values()) {
            for (Direction _snowmanxxxx : Direction.values()) {
               if (_snowmanxxxx != _snowmanxxx && _snowmanxxxx != _snowmanxxx.getOpposite()) {
                  BlockPattern.Result _snowmanxxxxx = this.testTransform(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman);
                  if (_snowmanxxxxx != null) {
                     return _snowmanxxxxx;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, CachedBlockPosition> makeCache(WorldView world, boolean forceLoad) {
      return CacheBuilder.newBuilder().build(new BlockPattern.BlockStateCacheLoader(world, forceLoad));
   }

   protected static BlockPos translate(BlockPos pos, Direction forwards, Direction up, int offsetLeft, int offsetDown, int offsetForwards) {
      if (forwards != up && forwards != up.getOpposite()) {
         Vec3i _snowman = new Vec3i(forwards.getOffsetX(), forwards.getOffsetY(), forwards.getOffsetZ());
         Vec3i _snowmanx = new Vec3i(up.getOffsetX(), up.getOffsetY(), up.getOffsetZ());
         Vec3i _snowmanxx = _snowman.crossProduct(_snowmanx);
         return pos.add(
            _snowmanx.getX() * -offsetDown + _snowmanxx.getX() * offsetLeft + _snowman.getX() * offsetForwards,
            _snowmanx.getY() * -offsetDown + _snowmanxx.getY() * offsetLeft + _snowman.getY() * offsetForwards,
            _snowmanx.getZ() * -offsetDown + _snowmanxx.getZ() * offsetLeft + _snowman.getZ() * offsetForwards
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class BlockStateCacheLoader extends CacheLoader<BlockPos, CachedBlockPosition> {
      private final WorldView world;
      private final boolean forceLoad;

      public BlockStateCacheLoader(WorldView world, boolean forceLoad) {
         this.world = world;
         this.forceLoad = forceLoad;
      }

      public CachedBlockPosition load(BlockPos _snowman) throws Exception {
         return new CachedBlockPosition(this.world, _snowman, this.forceLoad);
      }
   }

   public static class Result {
      private final BlockPos frontTopLeft;
      private final Direction forwards;
      private final Direction up;
      private final LoadingCache<BlockPos, CachedBlockPosition> cache;
      private final int width;
      private final int height;
      private final int depth;

      public Result(
         BlockPos frontTopLeft, Direction forwards, Direction up, LoadingCache<BlockPos, CachedBlockPosition> cache, int width, int height, int depth
      ) {
         this.frontTopLeft = frontTopLeft;
         this.forwards = forwards;
         this.up = up;
         this.cache = cache;
         this.width = width;
         this.height = height;
         this.depth = depth;
      }

      public BlockPos getFrontTopLeft() {
         return this.frontTopLeft;
      }

      public Direction getForwards() {
         return this.forwards;
      }

      public Direction getUp() {
         return this.up;
      }

      public CachedBlockPosition translate(int _snowman, int _snowman, int _snowman) {
         return (CachedBlockPosition)this.cache.getUnchecked(BlockPattern.translate(this.frontTopLeft, this.getForwards(), this.getUp(), _snowman, _snowman, _snowman));
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.up).add("forwards", this.forwards).add("frontTopLeft", this.frontTopLeft).toString();
      }
   }
}
