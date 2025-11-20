package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;

@Immutable
public class LocalDifficulty {
   private final Difficulty globalDifficulty;
   private final float localDifficulty;

   public LocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
      this.globalDifficulty = difficulty;
      this.localDifficulty = this.setLocalDifficulty(difficulty, timeOfDay, inhabitedTime, moonSize);
   }

   public Difficulty getGlobalDifficulty() {
      return this.globalDifficulty;
   }

   public float getLocalDifficulty() {
      return this.localDifficulty;
   }

   public boolean isHarderThan(float difficulty) {
      return this.localDifficulty > difficulty;
   }

   public float getClampedLocalDifficulty() {
      if (this.localDifficulty < 2.0F) {
         return 0.0F;
      } else {
         return this.localDifficulty > 4.0F ? 1.0F : (this.localDifficulty - 2.0F) / 2.0F;
      }
   }

   private float setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
      if (difficulty == Difficulty.PEACEFUL) {
         return 0.0F;
      } else {
         boolean _snowman = difficulty == Difficulty.HARD;
         float _snowmanx = 0.75F;
         float _snowmanxx = MathHelper.clamp(((float)timeOfDay + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         _snowmanx += _snowmanxx;
         float _snowmanxxx = 0.0F;
         _snowmanxxx += MathHelper.clamp((float)inhabitedTime / 3600000.0F, 0.0F, 1.0F) * (_snowman ? 1.0F : 0.75F);
         _snowmanxxx += MathHelper.clamp(moonSize * 0.25F, 0.0F, _snowmanxx);
         if (difficulty == Difficulty.EASY) {
            _snowmanxxx *= 0.5F;
         }

         _snowmanx += _snowmanxxx;
         return (float)difficulty.getId() * _snowmanx;
      }
   }
}
