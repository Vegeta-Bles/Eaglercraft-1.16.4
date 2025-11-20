package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BuriedTreasureFeature extends StructureFeature<ProbabilityConfig> {
   public BuriedTreasureFeature(Codec<ProbabilityConfig> _snowman) {
      super(_snowman);
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, ProbabilityConfig _snowman) {
      _snowman.setRegionSeed(_snowman, _snowman, _snowman, 10387320);
      return _snowman.nextFloat() < _snowman.probability;
   }

   @Override
   public StructureFeature.StructureStartFactory<ProbabilityConfig> getStructureStartFactory() {
      return BuriedTreasureFeature.Start::new;
   }

   public static class Start extends StructureStart<ProbabilityConfig> {
      public Start(StructureFeature<ProbabilityConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, ProbabilityConfig _snowman) {
         int _snowmanxxxxxxx = _snowman * 16;
         int _snowmanxxxxxxxx = _snowman * 16;
         BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowmanxxxxxxx + 9, 90, _snowmanxxxxxxxx + 9);
         this.children.add(new BuriedTreasureGenerator.Piece(_snowmanxxxxxxxxx));
         this.setBoundingBoxFromChildren();
      }

      @Override
      public BlockPos getPos() {
         return new BlockPos((this.getChunkX() << 4) + 9, 0, (this.getChunkZ() << 4) + 9);
      }
   }
}
