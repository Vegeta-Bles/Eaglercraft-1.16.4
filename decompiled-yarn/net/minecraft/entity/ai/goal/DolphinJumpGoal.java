package net.minecraft.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DolphinJumpGoal extends DiveJumpingGoal {
   private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
   private final DolphinEntity dolphin;
   private final int chance;
   private boolean inWater;

   public DolphinJumpGoal(DolphinEntity dolphin, int chance) {
      this.dolphin = dolphin;
      this.chance = chance;
   }

   @Override
   public boolean canStart() {
      if (this.dolphin.getRandom().nextInt(this.chance) != 0) {
         return false;
      } else {
         Direction _snowman = this.dolphin.getMovementDirection();
         int _snowmanx = _snowman.getOffsetX();
         int _snowmanxx = _snowman.getOffsetZ();
         BlockPos _snowmanxxx = this.dolphin.getBlockPos();

         for (int _snowmanxxxx : OFFSET_MULTIPLIERS) {
            if (!this.isWater(_snowmanxxx, _snowmanx, _snowmanxx, _snowmanxxxx) || !this.isAirAbove(_snowmanxxx, _snowmanx, _snowmanxx, _snowmanxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isWater(BlockPos pos, int xOffset, int zOffset, int multiplier) {
      BlockPos _snowman = pos.add(xOffset * multiplier, 0, zOffset * multiplier);
      return this.dolphin.world.getFluidState(_snowman).isIn(FluidTags.WATER) && !this.dolphin.world.getBlockState(_snowman).getMaterial().blocksMovement();
   }

   private boolean isAirAbove(BlockPos pos, int xOffset, int zOffset, int multiplier) {
      return this.dolphin.world.getBlockState(pos.add(xOffset * multiplier, 1, zOffset * multiplier)).isAir()
         && this.dolphin.world.getBlockState(pos.add(xOffset * multiplier, 2, zOffset * multiplier)).isAir();
   }

   @Override
   public boolean shouldContinue() {
      double _snowman = this.dolphin.getVelocity().y;
      return (!(_snowman * _snowman < 0.03F) || this.dolphin.pitch == 0.0F || !(Math.abs(this.dolphin.pitch) < 10.0F) || !this.dolphin.isTouchingWater())
         && !this.dolphin.isOnGround();
   }

   @Override
   public boolean canStop() {
      return false;
   }

   @Override
   public void start() {
      Direction _snowman = this.dolphin.getMovementDirection();
      this.dolphin.setVelocity(this.dolphin.getVelocity().add((double)_snowman.getOffsetX() * 0.6, 0.7, (double)_snowman.getOffsetZ() * 0.6));
      this.dolphin.getNavigation().stop();
   }

   @Override
   public void stop() {
      this.dolphin.pitch = 0.0F;
   }

   @Override
   public void tick() {
      boolean _snowman = this.inWater;
      if (!_snowman) {
         FluidState _snowmanx = this.dolphin.world.getFluidState(this.dolphin.getBlockPos());
         this.inWater = _snowmanx.isIn(FluidTags.WATER);
      }

      if (this.inWater && !_snowman) {
         this.dolphin.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
      }

      Vec3d _snowmanx = this.dolphin.getVelocity();
      if (_snowmanx.y * _snowmanx.y < 0.03F && this.dolphin.pitch != 0.0F) {
         this.dolphin.pitch = MathHelper.lerpAngle(this.dolphin.pitch, 0.0F, 0.2F);
      } else {
         double _snowmanxx = Math.sqrt(Entity.squaredHorizontalLength(_snowmanx));
         double _snowmanxxx = Math.signum(-_snowmanx.y) * Math.acos(_snowmanxx / _snowmanx.length()) * 180.0F / (float)Math.PI;
         this.dolphin.pitch = (float)_snowmanxxx;
      }
   }
}
