package net.minecraft.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.dimension.DimensionType;

public interface LunarWorldView extends WorldView {
   long getLunarTime();

   default float getMoonSize() {
      return DimensionType.MOON_SIZES[this.getDimension().getMoonPhase(this.getLunarTime())];
   }

   default float getSkyAngle(float tickDelta) {
      return this.getDimension().getSkyAngle(this.getLunarTime());
   }

   @Environment(EnvType.CLIENT)
   default int getMoonPhase() {
      return this.getDimension().getMoonPhase(this.getLunarTime());
   }
}
