package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;

public class VillageFeature extends JigsawFeature {
   public VillageFeature(Codec<StructurePoolFeatureConfig> _snowman) {
      super(_snowman, 0, true, true);
   }
}
