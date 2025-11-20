package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public interface CrossbowUser extends RangedAttackMob {
   void setCharging(boolean charging);

   void shoot(LivingEntity target, ItemStack crossbow, ProjectileEntity projectile, float multiShotSpray);

   @Nullable
   LivingEntity getTarget();

   void postShoot();

   default void shoot(LivingEntity entity, float speed) {
      Hand lv = ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW);
      ItemStack lv2 = entity.getStackInHand(lv);
      if (entity.isHolding(Items.CROSSBOW)) {
         CrossbowItem.shootAll(entity.world, entity, lv, lv2, speed, (float)(14 - entity.world.getDifficulty().getId() * 4));
      }

      this.postShoot();
   }

   default void shoot(LivingEntity entity, LivingEntity target, ProjectileEntity projectile, float multishotSpray, float speed) {
      double d = target.getX() - entity.getX();
      double e = target.getZ() - entity.getZ();
      double h = (double)MathHelper.sqrt(d * d + e * e);
      double i = target.getBodyY(0.3333333333333333) - projectile.getY() + h * 0.2F;
      Vector3f lv2 = this.getProjectileLaunchVelocity(entity, new Vec3d(d, i, e), multishotSpray);
      projectile.setVelocity((double)lv2.getX(), (double)lv2.getY(), (double)lv2.getZ(), speed, (float)(14 - entity.world.getDifficulty().getId() * 4));
      entity.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
   }

   default Vector3f getProjectileLaunchVelocity(LivingEntity entity, Vec3d positionDelta, float multishotSpray) {
      Vec3d lv = positionDelta.normalize();
      Vec3d lv2 = lv.crossProduct(new Vec3d(0.0, 1.0, 0.0));
      if (lv2.lengthSquared() <= 1.0E-7) {
         lv2 = lv.crossProduct(entity.getOppositeRotationVector(1.0F));
      }

      Quaternion lv3 = new Quaternion(new Vector3f(lv2), 90.0F, true);
      Vector3f lv4 = new Vector3f(lv);
      lv4.rotate(lv3);
      Quaternion lv5 = new Quaternion(lv4, multishotSpray, true);
      Vector3f lv6 = new Vector3f(lv);
      lv6.rotate(lv5);
      return lv6;
   }
}
