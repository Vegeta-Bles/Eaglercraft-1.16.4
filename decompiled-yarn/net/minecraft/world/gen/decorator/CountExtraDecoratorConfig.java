package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CountExtraDecoratorConfig implements DecoratorConfig {
   public static final Codec<CountExtraDecoratorConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Codec.INT.fieldOf("count").forGetter(_snowmanx -> _snowmanx.count),
               Codec.FLOAT.fieldOf("extra_chance").forGetter(_snowmanx -> _snowmanx.extraChance),
               Codec.INT.fieldOf("extra_count").forGetter(_snowmanx -> _snowmanx.extraCount)
            )
            .apply(_snowman, CountExtraDecoratorConfig::new)
   );
   public final int count;
   public final float extraChance;
   public final int extraCount;

   public CountExtraDecoratorConfig(int count, float extraChance, int extraCount) {
      this.count = count;
      this.extraChance = extraChance;
      this.extraCount = extraCount;
   }
}
