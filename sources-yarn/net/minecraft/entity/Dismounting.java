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
      Direction lv = movementDirection.rotateYClockwise();
      Direction lv2 = lv.getOpposite();
      Direction lv3 = movementDirection.getOpposite();
      return new int[][]{
         {lv.getOffsetX(), lv.getOffsetZ()},
         {lv2.getOffsetX(), lv2.getOffsetZ()},
         {lv3.getOffsetX() + lv.getOffsetX(), lv3.getOffsetZ() + lv.getOffsetZ()},
         {lv3.getOffsetX() + lv2.getOffsetX(), lv3.getOffsetZ() + lv2.getOffsetZ()},
         {movementDirection.getOffsetX() + lv.getOffsetX(), movementDirection.getOffsetZ() + lv.getOffsetZ()},
         {movementDirection.getOffsetX() + lv2.getOffsetX(), movementDirection.getOffsetZ() + lv2.getOffsetZ()},
         {lv3.getOffsetX(), lv3.getOffsetZ()},
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
         Vec3d lv = new Vec3d(x, height, z);
         if (canPlaceEntityAt(world, entity, entity.getBoundingBox(pose).offset(lv))) {
            return lv;
         }
      }

      return null;
   }

   public static VoxelShape getCollisionShape(BlockView world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos);
      return !lv.isIn(BlockTags.CLIMBABLE) && (!(lv.getBlock() instanceof TrapdoorBlock) || !lv.get(TrapdoorBlock.OPEN))
         ? lv.getCollisionShape(world, pos)
         : VoxelShapes.empty();
   }

   public static double getCeilingHeight(BlockPos pos, int maxDistance, Function<BlockPos, VoxelShape> collisionShapeGetter) {
      BlockPos.Mutable lv = pos.mutableCopy();
      int j = 0;

      while (j < maxDistance) {
         VoxelShape lv2 = collisionShapeGetter.apply(lv);
         if (!lv2.isEmpty()) {
            return (double)(pos.getY() + j) + lv2.getMin(Direction.Axis.Y);
         }

         j++;
         lv.move(Direction.UP);
      }

      return Double.POSITIVE_INFINITY;
   }

   @Nullable
   public static Vec3d method_30769(EntityType<?> arg, CollisionView arg2, BlockPos arg3, boolean bl) {
      if (bl && arg.isInvalidSpawn(arg2.getBlockState(arg3))) {
         return null;
      } else {
         double d = arg2.getDismountHeight(getCollisionShape(arg2, arg3), () -> getCollisionShape(arg2, arg3.down()));
         if (!canDismountInBlock(d)) {
            return null;
         } else if (bl && d <= 0.0 && arg.isInvalidSpawn(arg2.getBlockState(arg3.down()))) {
            return null;
         } else {
            Vec3d lv = Vec3d.ofCenter(arg3, d);
            return arg2.getBlockCollisions(null, arg.getDimensions().method_30757(lv)).allMatch(VoxelShape::isEmpty) ? lv : null;
         }
      }
   }
}
