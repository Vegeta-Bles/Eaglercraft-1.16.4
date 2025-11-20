package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.UniformIntDistribution;

public class NetherrackReplaceBlobsFeatureConfig implements FeatureConfig {
   public static final Codec<NetherrackReplaceBlobsFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BlockState.CODEC.fieldOf("target").forGetter(_snowmanx -> _snowmanx.target),
               BlockState.CODEC.fieldOf("state").forGetter(_snowmanx -> _snowmanx.state),
               UniformIntDistribution.CODEC.fieldOf("radius").forGetter(_snowmanx -> _snowmanx.radius)
            )
            .apply(_snowman, NetherrackReplaceBlobsFeatureConfig::new)
   );
   public final BlockState target;
   public final BlockState state;
   private final UniformIntDistribution radius;

   public NetherrackReplaceBlobsFeatureConfig(BlockState target, BlockState state, UniformIntDistribution radius) {
      this.target = target;
      this.state = state;
      this.radius = radius;
   }

   public UniformIntDistribution getRadius() {
      return this.radius;
   }
}
