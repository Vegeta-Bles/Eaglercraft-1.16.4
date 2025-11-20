package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.structure.EndCityGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndCityFeature extends StructureFeature<DefaultFeatureConfig> {
   public EndCityFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean isUniformDistribution() {
      return false;
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, DefaultFeatureConfig _snowman) {
      return getGenerationHeight(_snowman, _snowman, _snowman) >= 60;
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return EndCityFeature.Start::new;
   }

   private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
      Random _snowman = new Random((long)(chunkX + chunkZ * 10387313));
      BlockRotation _snowmanx = BlockRotation.random(_snowman);
      int _snowmanxx = 5;
      int _snowmanxxx = 5;
      if (_snowmanx == BlockRotation.CLOCKWISE_90) {
         _snowmanxx = -5;
      } else if (_snowmanx == BlockRotation.CLOCKWISE_180) {
         _snowmanxx = -5;
         _snowmanxxx = -5;
      } else if (_snowmanx == BlockRotation.COUNTERCLOCKWISE_90) {
         _snowmanxxx = -5;
      }

      int _snowmanxxxx = (chunkX << 4) + 7;
      int _snowmanxxxxx = (chunkZ << 4) + 7;
      int _snowmanxxxxxx = chunkGenerator.getHeightInGround(_snowmanxxxx, _snowmanxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
      int _snowmanxxxxxxx = chunkGenerator.getHeightInGround(_snowmanxxxx, _snowmanxxxxx + _snowmanxxx, Heightmap.Type.WORLD_SURFACE_WG);
      int _snowmanxxxxxxxx = chunkGenerator.getHeightInGround(_snowmanxxxx + _snowmanxx, _snowmanxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
      int _snowmanxxxxxxxxx = chunkGenerator.getHeightInGround(_snowmanxxxx + _snowmanxx, _snowmanxxxxx + _snowmanxxx, Heightmap.Type.WORLD_SURFACE_WG);
      return Math.min(Math.min(_snowmanxxxxxx, _snowmanxxxxxxx), Math.min(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         BlockRotation _snowmanxxxxxxx = BlockRotation.random(this.random);
         int _snowmanxxxxxxxx = EndCityFeature.getGenerationHeight(_snowman, _snowman, _snowman);
         if (_snowmanxxxxxxxx >= 60) {
            BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowman * 16 + 8, _snowmanxxxxxxxx, _snowman * 16 + 8);
            EndCityGenerator.addPieces(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxx, this.children, this.random);
            this.setBoundingBoxFromChildren();
         }
      }
   }
}
