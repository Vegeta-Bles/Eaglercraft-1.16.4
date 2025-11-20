package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CountNoiseDecoratorConfig implements DecoratorConfig {
   public static final Codec<CountNoiseDecoratorConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.DOUBLE.fieldOf("noise_level").forGetter(_snowmanx -> _snowmanx.noiseLevel),
               Codec.INT.fieldOf("below_noise").forGetter(_snowmanx -> _snowmanx.belowNoise),
               Codec.INT.fieldOf("above_noise").forGetter(_snowmanx -> _snowmanx.aboveNoise)
            )
            .apply(_snowman, CountNoiseDecoratorConfig::new)
   );
   public final double noiseLevel;
   public final int belowNoise;
   public final int aboveNoise;

   public CountNoiseDecoratorConfig(double noiseLevel, int belowNoise, int aboveNoise) {
      this.noiseLevel = noiseLevel;
      this.belowNoise = belowNoise;
      this.aboveNoise = aboveNoise;
   }
}
