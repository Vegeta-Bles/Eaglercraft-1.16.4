package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.UniformIntDistribution;

public class DeltaFeatureConfig implements FeatureConfig {
   public static final Codec<DeltaFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BlockState.CODEC.fieldOf("contents").forGetter(_snowmanx -> _snowmanx.contents),
               BlockState.CODEC.fieldOf("rim").forGetter(_snowmanx -> _snowmanx.rim),
               UniformIntDistribution.createValidatedCodec(0, 8, 8).fieldOf("size").forGetter(_snowmanx -> _snowmanx.size),
               UniformIntDistribution.createValidatedCodec(0, 8, 8).fieldOf("rim_size").forGetter(_snowmanx -> _snowmanx.rimSize)
            )
            .apply(_snowman, DeltaFeatureConfig::new)
   );
   private final BlockState contents;
   private final BlockState rim;
   private final UniformIntDistribution size;
   private final UniformIntDistribution rimSize;

   public DeltaFeatureConfig(BlockState contents, BlockState rim, UniformIntDistribution size, UniformIntDistribution rimSize) {
      this.contents = contents;
      this.rim = rim;
      this.size = size;
      this.rimSize = rimSize;
   }

   public BlockState getContents() {
      return this.contents;
   }

   public BlockState getRim() {
      return this.rim;
   }

   public UniformIntDistribution getSize() {
      return this.size;
   }

   public UniformIntDistribution getRimSize() {
      return this.rimSize;
   }
}
