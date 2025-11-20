package net.minecraft.entity.effect;

public class InstantStatusEffect extends StatusEffect {
   public InstantStatusEffect(StatusEffectType _snowman, int _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean isInstant() {
      return true;
   }

   @Override
   public boolean canApplyUpdateEffect(int duration, int amplifier) {
      return duration >= 1;
   }
}
