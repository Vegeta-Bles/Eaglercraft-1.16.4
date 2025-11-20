package net.minecraft.entity.mob;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;

public interface Hoglin {
   int getMovementCooldownTicks();

   static boolean tryAttack(LivingEntity attacker, LivingEntity target) {
      float _snowman = (float)attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
      float _snowmanx;
      if (!attacker.isBaby() && (int)_snowman > 0) {
         _snowmanx = _snowman / 2.0F + (float)attacker.world.random.nextInt((int)_snowman);
      } else {
         _snowmanx = _snowman;
      }

      boolean _snowmanxx = target.damage(DamageSource.mob(attacker), _snowmanx);
      if (_snowmanxx) {
         attacker.dealDamage(attacker, target);
         if (!attacker.isBaby()) {
            knockback(attacker, target);
         }
      }

      return _snowmanxx;
   }

   static void knockback(LivingEntity attacker, LivingEntity target) {
      double _snowman = attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
      double _snowmanx = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
      double _snowmanxx = _snowman - _snowmanx;
      if (!(_snowmanxx <= 0.0)) {
         double _snowmanxxx = target.getX() - attacker.getX();
         double _snowmanxxxx = target.getZ() - attacker.getZ();
         float _snowmanxxxxx = (float)(attacker.world.random.nextInt(21) - 10);
         double _snowmanxxxxxx = _snowmanxx * (double)(attacker.world.random.nextFloat() * 0.5F + 0.2F);
         Vec3d _snowmanxxxxxxx = new Vec3d(_snowmanxxx, 0.0, _snowmanxxxx).normalize().multiply(_snowmanxxxxxx).rotateY(_snowmanxxxxx);
         double _snowmanxxxxxxxx = _snowmanxx * (double)attacker.world.random.nextFloat() * 0.5;
         target.addVelocity(_snowmanxxxxxxx.x, _snowmanxxxxxxxx, _snowmanxxxxxxx.z);
         target.velocityModified = true;
      }
   }
}
