package net.minecraft.entity.mob;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class FlyingEntity extends MobEntity {
   protected FlyingEntity(EntityType<? extends FlyingEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.isTouchingWater()) {
         this.updateVelocity(0.02F, movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply(0.8F));
      } else if (this.isInLava()) {
         this.updateVelocity(0.02F, movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply(0.5));
      } else {
         float _snowman = 0.91F;
         if (this.onGround) {
            _snowman = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getSlipperiness() * 0.91F;
         }

         float _snowmanx = 0.16277137F / (_snowman * _snowman * _snowman);
         _snowman = 0.91F;
         if (this.onGround) {
            _snowman = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getSlipperiness() * 0.91F;
         }

         this.updateVelocity(this.onGround ? 0.1F * _snowmanx : 0.02F, movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply((double)_snowman));
      }

      this.method_29242(this, false);
   }

   @Override
   public boolean isClimbing() {
      return false;
   }
}
