package net.minecraft.entity.ai.goal;

import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class SitOnOwnerShoulderGoal extends Goal {
   private final TameableShoulderEntity tameable;
   private ServerPlayerEntity owner;
   private boolean mounted;

   public SitOnOwnerShoulderGoal(TameableShoulderEntity tameable) {
      this.tameable = tameable;
   }

   @Override
   public boolean canStart() {
      ServerPlayerEntity _snowman = (ServerPlayerEntity)this.tameable.getOwner();
      boolean _snowmanx = _snowman != null && !_snowman.isSpectator() && !_snowman.abilities.flying && !_snowman.isTouchingWater();
      return !this.tameable.isSitting() && _snowmanx && this.tameable.isReadyToSitOnPlayer();
   }

   @Override
   public boolean canStop() {
      return !this.mounted;
   }

   @Override
   public void start() {
      this.owner = (ServerPlayerEntity)this.tameable.getOwner();
      this.mounted = false;
   }

   @Override
   public void tick() {
      if (!this.mounted && !this.tameable.isInSittingPose() && !this.tameable.isLeashed()) {
         if (this.tameable.getBoundingBox().intersects(this.owner.getBoundingBox())) {
            this.mounted = this.tameable.mountOnto(this.owner);
         }
      }
   }
}
