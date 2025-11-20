package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FlyOntoTreeGoal extends WanderAroundFarGoal {
   public FlyOntoTreeGoal(PathAwareEntity arg, double d) {
      super(arg, d);
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      Vec3d lv = null;
      if (this.mob.isTouchingWater()) {
         lv = TargetFinder.findGroundTarget(this.mob, 15, 15);
      }

      if (this.mob.getRandom().nextFloat() >= this.probability) {
         lv = this.getTreeTarget();
      }

      return lv == null ? super.getWanderTarget() : lv;
   }

   @Nullable
   private Vec3d getTreeTarget() {
      BlockPos lv = this.mob.getBlockPos();
      BlockPos.Mutable lv2 = new BlockPos.Mutable();
      BlockPos.Mutable lv3 = new BlockPos.Mutable();

      for (BlockPos lv4 : BlockPos.iterate(
         MathHelper.floor(this.mob.getX() - 3.0),
         MathHelper.floor(this.mob.getY() - 6.0),
         MathHelper.floor(this.mob.getZ() - 3.0),
         MathHelper.floor(this.mob.getX() + 3.0),
         MathHelper.floor(this.mob.getY() + 6.0),
         MathHelper.floor(this.mob.getZ() + 3.0)
      )) {
         if (!lv.equals(lv4)) {
            Block lv5 = this.mob.world.getBlockState(lv3.set(lv4, Direction.DOWN)).getBlock();
            boolean bl = lv5 instanceof LeavesBlock || lv5.isIn(BlockTags.LOGS);
            if (bl && this.mob.world.isAir(lv4) && this.mob.world.isAir(lv2.set(lv4, Direction.UP))) {
               return Vec3d.ofBottomCenter(lv4);
            }
         }
      }

      return null;
   }
}
