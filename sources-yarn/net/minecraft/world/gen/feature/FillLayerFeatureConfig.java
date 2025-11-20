package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;

public class FillLayerFeatureConfig implements FeatureConfig {
   public static final Codec<FillLayerFeatureConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               Codec.intRange(0, 255).fieldOf("height").forGetter(arg -> arg.height), BlockState.CODEC.fieldOf("state").forGetter(arg -> arg.state)
            )
            .apply(instance, FillLayerFeatureConfig::new)
   );
   public final int height;
   public final BlockState state;

   public FillLayerFeatureConfig(int height, BlockState state) {
      this.height = height;
      this.state = state;
   }
}
