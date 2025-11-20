package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class DepthAverageDecoratorConfig implements DecoratorConfig {
   public static final Codec<DepthAverageDecoratorConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(Codec.INT.fieldOf("baseline").forGetter(_snowmanx -> _snowmanx.baseline), Codec.INT.fieldOf("spread").forGetter(_snowmanx -> _snowmanx.spread))
            .apply(_snowman, DepthAverageDecoratorConfig::new)
   );
   public final int baseline;
   public final int spread;

   public DepthAverageDecoratorConfig(int baseline, int spread) {
      this.baseline = baseline;
      this.spread = spread;
   }
}
