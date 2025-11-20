package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ChaseBoatGoal extends Goal {
   private int updateCountdownTicks;
   private final PathAwareEntity mob;
   private PlayerEntity passenger;
   private ChaseBoatState state;

   public ChaseBoatGoal(PathAwareEntity mob) {
      this.mob = mob;
   }

   @Override
   public boolean canStart() {
      List<BoatEntity> _snowman = this.mob.world.getNonSpectatingEntities(BoatEntity.class, this.mob.getBoundingBox().expand(5.0));
      boolean _snowmanx = false;

      for (BoatEntity _snowmanxx : _snowman) {
         Entity _snowmanxxx = _snowmanxx.getPrimaryPassenger();
         if (_snowmanxxx instanceof PlayerEntity
            && (MathHelper.abs(((PlayerEntity)_snowmanxxx).sidewaysSpeed) > 0.0F || MathHelper.abs(((PlayerEntity)_snowmanxxx).forwardSpeed) > 0.0F)) {
            _snowmanx = true;
            break;
         }
      }

      return this.passenger != null && (MathHelper.abs(this.passenger.sidewaysSpeed) > 0.0F || MathHelper.abs(this.passenger.forwardSpeed) > 0.0F) || _snowmanx;
   }

   @Override
   public boolean canStop() {
      return true;
   }

   @Override
   public boolean shouldContinue() {
      return this.passenger != null
         && this.passenger.hasVehicle()
         && (MathHelper.abs(this.passenger.sidewaysSpeed) > 0.0F || MathHelper.abs(this.passenger.forwardSpeed) > 0.0F);
   }

   @Override
   public void start() {
      for (BoatEntity _snowman : this.mob.world.getNonSpectatingEntities(BoatEntity.class, this.mob.getBoundingBox().expand(5.0))) {
         if (_snowman.getPrimaryPassenger() != null && _snowman.getPrimaryPassenger() instanceof PlayerEntity) {
            this.passenger = (PlayerEntity)_snowman.getPrimaryPassenger();
            break;
         }
      }

      this.updateCountdownTicks = 0;
      this.state = ChaseBoatState.GO_TO_BOAT;
   }

   @Override
   public void stop() {
      this.passenger = null;
   }

   @Override
   public void tick() {
      boolean _snowman = MathHelper.abs(this.passenger.sidewaysSpeed) > 0.0F || MathHelper.abs(this.passenger.forwardSpeed) > 0.0F;
      float _snowmanx = this.state == ChaseBoatState.GO_IN_BOAT_DIRECTION ? (_snowman ? 0.01F : 0.0F) : 0.015F;
      this.mob.updateVelocity(_snowmanx, new Vec3d((double)this.mob.sidewaysSpeed, (double)this.mob.upwardSpeed, (double)this.mob.forwardSpeed));
      this.mob.move(MovementType.SELF, this.mob.getVelocity());
      if (--this.updateCountdownTicks <= 0) {
         this.updateCountdownTicks = 10;
         if (this.state == ChaseBoatState.GO_TO_BOAT) {
            BlockPos _snowmanxx = this.passenger.getBlockPos().offset(this.passenger.getHorizontalFacing().getOpposite());
            _snowmanxx = _snowmanxx.add(0, -1, 0);
            this.mob.getNavigation().startMovingTo((double)_snowmanxx.getX(), (double)_snowmanxx.getY(), (double)_snowmanxx.getZ(), 1.0);
            if (this.mob.distanceTo(this.passenger) < 4.0F) {
               this.updateCountdownTicks = 0;
               this.state = ChaseBoatState.GO_IN_BOAT_DIRECTION;
            }
         } else if (this.state == ChaseBoatState.GO_IN_BOAT_DIRECTION) {
            Direction _snowmanxx = this.passenger.getMovementDirection();
            BlockPos _snowmanxxx = this.passenger.getBlockPos().offset(_snowmanxx, 10);
            this.mob.getNavigation().startMovingTo((double)_snowmanxxx.getX(), (double)(_snowmanxxx.getY() - 1), (double)_snowmanxxx.getZ(), 1.0);
            if (this.mob.distanceTo(this.passenger) > 12.0F) {
               this.updateCountdownTicks = 0;
               this.state = ChaseBoatState.GO_TO_BOAT;
            }
         }
      }
   }
}
