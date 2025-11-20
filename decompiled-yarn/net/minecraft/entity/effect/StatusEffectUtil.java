package net.minecraft.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.math.MathHelper;

public final class StatusEffectUtil {
   public static String durationToString(StatusEffectInstance effect, float multiplier) {
      if (effect.isPermanent()) {
         return "**:**";
      } else {
         int _snowman = MathHelper.floor((float)effect.getDuration() * multiplier);
         return ChatUtil.ticksToString(_snowman);
      }
   }

   public static boolean hasHaste(LivingEntity entity) {
      return entity.hasStatusEffect(StatusEffects.HASTE) || entity.hasStatusEffect(StatusEffects.CONDUIT_POWER);
   }

   public static int getHasteAmplifier(LivingEntity entity) {
      int _snowman = 0;
      int _snowmanx = 0;
      if (entity.hasStatusEffect(StatusEffects.HASTE)) {
         _snowman = entity.getStatusEffect(StatusEffects.HASTE).getAmplifier();
      }

      if (entity.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
         _snowmanx = entity.getStatusEffect(StatusEffects.CONDUIT_POWER).getAmplifier();
      }

      return Math.max(_snowman, _snowmanx);
   }

   public static boolean hasWaterBreathing(LivingEntity entity) {
      return entity.hasStatusEffect(StatusEffects.WATER_BREATHING) || entity.hasStatusEffect(StatusEffects.CONDUIT_POWER);
   }
}
