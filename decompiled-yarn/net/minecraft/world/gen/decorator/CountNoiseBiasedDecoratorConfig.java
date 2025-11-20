package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CountNoiseBiasedDecoratorConfig implements DecoratorConfig {
   public static final Codec<CountNoiseBiasedDecoratorConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.INT.fieldOf("noise_to_count_ratio").forGetter(_snowmanx -> _snowmanx.noiseToCountRatio),
               Codec.DOUBLE.fieldOf("noise_factor").forGetter(_snowmanx -> _snowmanx.noiseFactor),
               Codec.DOUBLE.fieldOf("noise_offset").orElse(0.0).forGetter(_snowmanx -> _snowmanx.noiseOffset)
            )
            .apply(_snowman, CountNoiseBiasedDecoratorConfig::new)
   );
   public final int noiseToCountRatio;
   public final double noiseFactor;
   public final double noiseOffset;

   public CountNoiseBiasedDecoratorConfig(int noiseToCountRatio, double noiseFactor, double noiseOffset) {
      this.noiseToCountRatio = noiseToCountRatio;
      this.noiseFactor = noiseFactor;
      this.noiseOffset = noiseOffset;
   }
}
