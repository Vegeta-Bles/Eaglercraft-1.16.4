package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BastionRemnantFeature extends JigsawFeature {
   public BastionRemnantFeature(Codec<StructurePoolFeatureConfig> _snowman) {
      super(_snowman, 33, false, false);
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, StructurePoolFeatureConfig _snowman) {
      return _snowman.nextInt(5) >= 2;
   }
}
