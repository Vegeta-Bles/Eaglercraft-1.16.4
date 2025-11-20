package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class MineshaftFeatureConfig implements FeatureConfig {
   public static final Codec<MineshaftFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(_snowmanx -> _snowmanx.probability),
               MineshaftFeature.Type.CODEC.fieldOf("type").forGetter(_snowmanx -> _snowmanx.type)
            )
            .apply(_snowman, MineshaftFeatureConfig::new)
   );
   public final float probability;
   public final MineshaftFeature.Type type;

   public MineshaftFeatureConfig(float probability, MineshaftFeature.Type type) {
      this.probability = probability;
      this.type = type;
   }
}
