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
   public FlyOntoTreeGoal(PathAwareEntity _snowman, double _snowman) {
      super(_snowman, _snowman);
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      Vec3d _snowman = null;
      if (this.mob.isTouchingWater()) {
         _snowman = TargetFinder.findGroundTarget(this.mob, 15, 15);
      }

      if (this.mob.getRandom().nextFloat() >= this.probability) {
         _snowman = this.getTreeTarget();
      }

      return _snowman == null ? super.getWanderTarget() : _snowman;
   }

   @Nullable
   private Vec3d getTreeTarget() {
      BlockPos _snowman = this.mob.getBlockPos();
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

      for (BlockPos _snowmanxxx : BlockPos.iterate(
         MathHelper.floor(this.mob.getX() - 3.0),
         MathHelper.floor(this.mob.getY() - 6.0),
         MathHelper.floor(this.mob.getZ() - 3.0),
         MathHelper.floor(this.mob.getX() + 3.0),
         MathHelper.floor(this.mob.getY() + 6.0),
         MathHelper.floor(this.mob.getZ() + 3.0)
      )) {
         if (!_snowman.equals(_snowmanxxx)) {
            Block _snowmanxxxx = this.mob.world.getBlockState(_snowmanxx.set(_snowmanxxx, Direction.DOWN)).getBlock();
            boolean _snowmanxxxxx = _snowmanxxxx instanceof LeavesBlock || _snowmanxxxx.isIn(BlockTags.LOGS);
            if (_snowmanxxxxx && this.mob.world.isAir(_snowmanxxx) && this.mob.world.isAir(_snowmanx.set(_snowmanxxx, Direction.UP))) {
               return Vec3d.ofBottomCenter(_snowmanxxx);
            }
         }
      }

      return null;
   }
}
