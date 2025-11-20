package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.GenerationStep;

public class CarvingMaskDecoratorConfig implements DecoratorConfig {
   public static final Codec<CarvingMaskDecoratorConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(GenerationStep.Carver.CODEC.fieldOf("step").forGetter(_snowmanx -> _snowmanx.step), Codec.FLOAT.fieldOf("probability").forGetter(_snowmanx -> _snowmanx.probability))
            .apply(_snowman, CarvingMaskDecoratorConfig::new)
   );
   protected final GenerationStep.Carver step;
   protected final float probability;

   public CarvingMaskDecoratorConfig(GenerationStep.Carver step, float probability) {
      this.step = step;
      this.probability = probability;
   }
}
