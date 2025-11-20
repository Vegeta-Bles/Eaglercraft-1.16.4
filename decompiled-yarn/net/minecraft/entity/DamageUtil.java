package net.minecraft.entity;

import net.minecraft.util.math.MathHelper;

public class DamageUtil {
   public static float getDamageLeft(float damage, float armor, float armorToughness) {
      float _snowman = 2.0F + armorToughness / 4.0F;
      float _snowmanx = MathHelper.clamp(armor - damage / _snowman, armor * 0.2F, 20.0F);
      return damage * (1.0F - _snowmanx / 25.0F);
   }

   public static float getInflictedDamage(float damageDealt, float protection) {
      float _snowman = MathHelper.clamp(protection, 0.0F, 20.0F);
      return damageDealt * (1.0F - _snowman / 25.0F);
   }
}
