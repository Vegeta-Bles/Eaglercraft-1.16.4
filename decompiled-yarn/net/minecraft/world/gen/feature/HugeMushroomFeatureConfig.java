package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class HugeMushroomFeatureConfig implements FeatureConfig {
   public static final Codec<HugeMushroomFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter(_snowmanx -> _snowmanx.capProvider),
               BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter(_snowmanx -> _snowmanx.stemProvider),
               Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter(_snowmanx -> _snowmanx.foliageRadius)
            )
            .apply(_snowman, HugeMushroomFeatureConfig::new)
   );
   public final BlockStateProvider capProvider;
   public final BlockStateProvider stemProvider;
   public final int foliageRadius;

   public HugeMushroomFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius) {
      this.capProvider = capProvider;
      this.stemProvider = stemProvider;
      this.foliageRadius = foliageRadius;
   }
}
