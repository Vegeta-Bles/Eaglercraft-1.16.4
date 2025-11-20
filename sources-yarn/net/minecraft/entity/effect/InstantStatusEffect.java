package net.minecraft.entity.effect;

public class InstantStatusEffect extends StatusEffect {
   public InstantStatusEffect(StatusEffectType arg, int i) {
      super(arg, i);
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
