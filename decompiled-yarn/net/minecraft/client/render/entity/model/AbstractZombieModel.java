package net.minecraft.client.render.entity.model;

import net.minecraft.entity.mob.HostileEntity;

public abstract class AbstractZombieModel<T extends HostileEntity> extends BipedEntityModel<T> {
   protected AbstractZombieModel(float _snowman, float _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      CrossbowPosing.method_29352(this.leftArm, this.rightArm, this.isAttacking(_snowman), this.handSwingProgress, _snowman);
   }

   public abstract boolean isAttacking(T var1);
}
