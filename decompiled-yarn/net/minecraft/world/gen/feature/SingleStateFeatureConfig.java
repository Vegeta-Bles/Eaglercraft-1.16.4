package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;

public class SingleStateFeatureConfig implements FeatureConfig {
   public static final Codec<SingleStateFeatureConfig> CODEC = BlockState.CODEC.fieldOf("state").xmap(SingleStateFeatureConfig::new, _snowman -> _snowman.state).codec();
   public final BlockState state;

   public SingleStateFeatureConfig(BlockState state) {
      this.state = state;
   }
}
