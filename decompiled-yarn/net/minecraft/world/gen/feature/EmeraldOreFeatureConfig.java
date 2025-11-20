package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;

public class EmeraldOreFeatureConfig implements FeatureConfig {
   public static final Codec<EmeraldOreFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(BlockState.CODEC.fieldOf("target").forGetter(_snowmanx -> _snowmanx.target), BlockState.CODEC.fieldOf("state").forGetter(_snowmanx -> _snowmanx.state))
            .apply(_snowman, EmeraldOreFeatureConfig::new)
   );
   public final BlockState target;
   public final BlockState state;

   public EmeraldOreFeatureConfig(BlockState target, BlockState state) {
      this.target = target;
      this.state = state;
   }
}
