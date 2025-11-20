package net.minecraft.entity.ai.pathing;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;

public class WaterPathNodeMaker extends PathNodeMaker {
   private final boolean canJumpOutOfWater;

   public WaterPathNodeMaker(boolean canJumpOutOfWater) {
      this.canJumpOutOfWater = canJumpOutOfWater;
   }

   @Override
   public PathNode getStart() {
      return super.getNode(
         MathHelper.floor(this.entity.getBoundingBox().minX),
         MathHelper.floor(this.entity.getBoundingBox().minY + 0.5),
         MathHelper.floor(this.entity.getBoundingBox().minZ)
      );
   }

   @Override
   public TargetPathNode getNode(double x, double y, double z) {
      return new TargetPathNode(
         super.getNode(
            MathHelper.floor(x - (double)(this.entity.getWidth() / 2.0F)),
            MathHelper.floor(y + 0.5),
            MathHelper.floor(z - (double)(this.entity.getWidth() / 2.0F))
         )
      );
   }

   @Override
   public int getSuccessors(PathNode[] successors, PathNode node) {
      int _snowman = 0;

      for (Direction _snowmanx : Direction.values()) {
         PathNode _snowmanxx = this.getPathNodeInWater(node.x + _snowmanx.getOffsetX(), node.y + _snowmanx.getOffsetY(), node.z + _snowmanx.getOffsetZ());
         if (_snowmanxx != null && !_snowmanxx.visited) {
            successors[_snowman++] = _snowmanxx;
         }
      }

      return _snowman;
   }

   @Override
   public PathNodeType getNodeType(
      BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors
   ) {
      return this.getDefaultNodeType(world, x, y, z);
   }

   @Override
   public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
      BlockPos _snowman = new BlockPos(x, y, z);
      FluidState _snowmanx = world.getFluidState(_snowman);
      BlockState _snowmanxx = world.getBlockState(_snowman);
      if (_snowmanx.isEmpty() && _snowmanxx.canPathfindThrough(world, _snowman.down(), NavigationType.WATER) && _snowmanxx.isAir()) {
         return PathNodeType.BREACH;
      } else {
         return _snowmanx.isIn(FluidTags.WATER) && _snowmanxx.canPathfindThrough(world, _snowman, NavigationType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
      }
   }

   @Nullable
   private PathNode getPathNodeInWater(int x, int y, int z) {
      PathNodeType _snowman = this.getNodeType(x, y, z);
      return (!this.canJumpOutOfWater || _snowman != PathNodeType.BREACH) && _snowman != PathNodeType.WATER ? null : this.getNode(x, y, z);
   }

   @Nullable
   @Override
   protected PathNode getNode(int x, int y, int z) {
      PathNode _snowman = null;
      PathNodeType _snowmanx = this.getDefaultNodeType(this.entity.world, x, y, z);
      float _snowmanxx = this.entity.getPathfindingPenalty(_snowmanx);
      if (_snowmanxx >= 0.0F) {
         _snowman = super.getNode(x, y, z);
         _snowman.type = _snowmanx;
         _snowman.penalty = Math.max(_snowman.penalty, _snowmanxx);
         if (this.cachedWorld.getFluidState(new BlockPos(x, y, z)).isEmpty()) {
            _snowman.penalty += 8.0F;
         }
      }

      return _snowmanx == PathNodeType.OPEN ? _snowman : _snowman;
   }

   private PathNodeType getNodeType(int x, int y, int z) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();

      for (int _snowmanx = x; _snowmanx < x + this.entityBlockXSize; _snowmanx++) {
         for (int _snowmanxx = y; _snowmanxx < y + this.entityBlockYSize; _snowmanxx++) {
            for (int _snowmanxxx = z; _snowmanxxx < z + this.entityBlockZSize; _snowmanxxx++) {
               FluidState _snowmanxxxx = this.cachedWorld.getFluidState(_snowman.set(_snowmanx, _snowmanxx, _snowmanxxx));
               BlockState _snowmanxxxxx = this.cachedWorld.getBlockState(_snowman.set(_snowmanx, _snowmanxx, _snowmanxxx));
               if (_snowmanxxxx.isEmpty() && _snowmanxxxxx.canPathfindThrough(this.cachedWorld, _snowman.down(), NavigationType.WATER) && _snowmanxxxxx.isAir()) {
                  return PathNodeType.BREACH;
               }

               if (!_snowmanxxxx.isIn(FluidTags.WATER)) {
                  return PathNodeType.BLOCKED;
               }
            }
         }
      }

      BlockState _snowmanx = this.cachedWorld.getBlockState(_snowman);
      return _snowmanx.canPathfindThrough(this.cachedWorld, _snowman, NavigationType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
   }
}
