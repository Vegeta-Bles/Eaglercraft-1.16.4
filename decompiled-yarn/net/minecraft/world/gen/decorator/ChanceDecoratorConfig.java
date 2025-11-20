package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;

public class ChanceDecoratorConfig implements DecoratorConfig {
   public static final Codec<ChanceDecoratorConfig> CODEC = Codec.INT.fieldOf("chance").xmap(ChanceDecoratorConfig::new, _snowman -> _snowman.chance).codec();
   public final int chance;

   public ChanceDecoratorConfig(int chance) {
      this.chance = chance;
   }
}
