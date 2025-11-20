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
      Hand _snowman = ProjectileUtil.getHandPossiblyHolding(entity, Items.CROSSBOW);
      ItemStack _snowmanx = entity.getStackInHand(_snowman);
      if (entity.isHolding(Items.CROSSBOW)) {
         CrossbowItem.shootAll(entity.world, entity, _snowman, _snowmanx, speed, (float)(14 - entity.world.getDifficulty().getId() * 4));
      }

      this.postShoot();
   }

   default void shoot(LivingEntity entity, LivingEntity target, ProjectileEntity projectile, float multishotSpray, float speed) {
      double _snowman = target.getX() - entity.getX();
      double _snowmanx = target.getZ() - entity.getZ();
      double _snowmanxx = (double)MathHelper.sqrt(_snowman * _snowman + _snowmanx * _snowmanx);
      double _snowmanxxx = target.getBodyY(0.3333333333333333) - projectile.getY() + _snowmanxx * 0.2F;
      Vector3f _snowmanxxxx = this.getProjectileLaunchVelocity(entity, new Vec3d(_snowman, _snowmanxxx, _snowmanx), multishotSpray);
      projectile.setVelocity((double)_snowmanxxxx.getX(), (double)_snowmanxxxx.getY(), (double)_snowmanxxxx.getZ(), speed, (float)(14 - entity.world.getDifficulty().getId() * 4));
      entity.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
   }

   default Vector3f getProjectileLaunchVelocity(LivingEntity entity, Vec3d positionDelta, float multishotSpray) {
      Vec3d _snowman = positionDelta.normalize();
      Vec3d _snowmanx = _snowman.crossProduct(new Vec3d(0.0, 1.0, 0.0));
      if (_snowmanx.lengthSquared() <= 1.0E-7) {
         _snowmanx = _snowman.crossProduct(entity.getOppositeRotationVector(1.0F));
      }

      Quaternion _snowmanxx = new Quaternion(new Vector3f(_snowmanx), 90.0F, true);
      Vector3f _snowmanxxx = new Vector3f(_snowman);
      _snowmanxxx.rotate(_snowmanxx);
      Quaternion _snowmanxxxx = new Quaternion(_snowmanxxx, multishotSpray, true);
      Vector3f _snowmanxxxxx = new Vector3f(_snowman);
      _snowmanxxxxx.rotate(_snowmanxxxx);
      return _snowmanxxxxx;
   }
}
