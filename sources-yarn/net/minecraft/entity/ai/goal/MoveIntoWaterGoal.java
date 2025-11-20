package net.minecraft.entity.ai.goal;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MoveIntoWaterGoal extends Goal {
   private final PathAwareEntity mob;

   public MoveIntoWaterGoal(PathAwareEntity mob) {
      this.mob = mob;
   }

   @Override
   public boolean canStart() {
      return this.mob.isOnGround() && !this.mob.world.getFluidState(this.mob.getBlockPos()).isIn(FluidTags.WATER);
   }

   @Override
   public void start() {
      BlockPos lv = null;

      for (BlockPos lv2 : BlockPos.iterate(
         MathHelper.floor(this.mob.getX() - 2.0),
         MathHelper.floor(this.mob.getY() - 2.0),
         MathHelper.floor(this.mob.getZ() - 2.0),
         MathHelper.floor(this.mob.getX() + 2.0),
         MathHelper.floor(this.mob.getY()),
         MathHelper.floor(this.mob.getZ() + 2.0)
      )) {
         if (this.mob.world.getFluidState(lv2).isIn(FluidTags.WATER)) {
            lv = lv2;
            break;
         }
      }

      if (lv != null) {
         this.mob.getMoveControl().moveTo((double)lv.getX(), (double)lv.getY(), (double)lv.getZ(), 1.0);
      }
   }
}
