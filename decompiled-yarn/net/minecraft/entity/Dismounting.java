package net.minecraft.entity;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;

public class Dismounting {
   public static int[][] getDismountOffsets(Direction movementDirection) {
      Direction _snowman = movementDirection.rotateYClockwise();
      Direction _snowmanx = _snowman.getOpposite();
      Direction _snowmanxx = movementDirection.getOpposite();
      return new int[][]{
         {_snowman.getOffsetX(), _snowman.getOffsetZ()},
         {_snowmanx.getOffsetX(), _snowmanx.getOffsetZ()},
         {_snowmanxx.getOffsetX() + _snowman.getOffsetX(), _snowmanxx.getOffsetZ() + _snowman.getOffsetZ()},
         {_snowmanxx.getOffsetX() + _snowmanx.getOffsetX(), _snowmanxx.getOffsetZ() + _snowmanx.getOffsetZ()},
         {movementDirection.getOffsetX() + _snowman.getOffsetX(), movementDirection.getOffsetZ() + _snowman.getOffsetZ()},
         {movementDirection.getOffsetX() + _snowmanx.getOffsetX(), movementDirection.getOffsetZ() + _snowmanx.getOffsetZ()},
         {_snowmanxx.getOffsetX(), _snowmanxx.getOffsetZ()},
         {movementDirection.getOffsetX(), movementDirection.getOffsetZ()}
      };
   }

   public static boolean canDismountInBlock(double height) {
      return !Double.isInfinite(height) && height < 1.0;
   }

   public static boolean canPlaceEntityAt(CollisionView world, LivingEntity entity, Box targetBox) {
      return world.getBlockCollisions(entity, targetBox).allMatch(VoxelShape::isEmpty);
   }

   @Nullable
   public static Vec3d findDismountPos(CollisionView world, double x, double height, double z, LivingEntity entity, EntityPose pose) {
      if (canDismountInBlock(height)) {
         Vec3d _snowman = new Vec3d(x, height, z);
         if (canPlaceEntityAt(world, entity, entity.getBoundingBox(pose).offset(_snowman))) {
            return _snowman;
         }
      }

      return null;
   }

   public static VoxelShape getCollisionShape(BlockView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      return !_snowman.isIn(BlockTags.CLIMBABLE) && (!(_snowman.getBlock() instanceof TrapdoorBlock) || !_snowman.get(TrapdoorBlock.OPEN))
         ? _snowman.getCollisionShape(world, pos)
         : VoxelShapes.empty();
   }

   public static double getCeilingHeight(BlockPos pos, int maxDistance, Function<BlockPos, VoxelShape> collisionShapeGetter) {
      BlockPos.Mutable _snowman = pos.mutableCopy();
      int _snowmanx = 0;

      while (_snowmanx < maxDistance) {
         VoxelShape _snowmanxx = collisionShapeGetter.apply(_snowman);
         if (!_snowmanxx.isEmpty()) {
            return (double)(pos.getY() + _snowmanx) + _snowmanxx.getMin(Direction.Axis.Y);
         }

         _snowmanx++;
         _snowman.move(Direction.UP);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static Vec3d method_30769(EntityType<?> _snowman, CollisionView _snowman, BlockPos _snowman, boolean _snowman) {
      if (_snowman && _snowman.isInvalidSpawn(_snowman.getBlockState(_snowman))) {
         return null;
      } else {
         double _snowmanxxxx = _snowman.getDismountHeight(getCollisionShape(_snowman, _snowman), () -> getCollisionShape(_snowman, _snowman.down()));
         if (!canDismountInBlock(_snowmanxxxx)) {
            return null;
         } else if (_snowman && _snowmanxxxx <= 0.0 && _snowman.isInvalidSpawn(_snowman.getBlockState(_snowman.down()))) {
            return null;
         } else {
            Vec3d _snowmanxxxxx = Vec3d.ofCenter(_snowman, _snowmanxxxx);
            return _snowman.getBlockCollisions(null, _snowman.getDimensions().method_30757(_snowmanxxxxx)).allMatch(VoxelShape::isEmpty) ? _snowmanxxxxx : null;
         }
      }
   }
}
