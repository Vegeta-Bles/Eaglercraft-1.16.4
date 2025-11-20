package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.WoodlandMansionGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class WoodlandMansionFeature extends StructureFeature<DefaultFeatureConfig> {
   public WoodlandMansionFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean isUniformDistribution() {
      return false;
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, DefaultFeatureConfig _snowman) {
      for (Biome _snowmanxxxxxxxxx : _snowman.getBiomesInArea(_snowman * 16 + 9, _snowman.getSeaLevel(), _snowman * 16 + 9, 32)) {
         if (!_snowmanxxxxxxxxx.getGenerationSettings().hasStructureFeature(this)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return WoodlandMansionFeature.Start::new;
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         BlockRotation _snowmanxxxxxxx = BlockRotation.random(this.random);
         int _snowmanxxxxxxxx = 5;
         int _snowmanxxxxxxxxx = 5;
         if (_snowmanxxxxxxx == BlockRotation.CLOCKWISE_90) {
            _snowmanxxxxxxxx = -5;
         } else if (_snowmanxxxxxxx == BlockRotation.CLOCKWISE_180) {
            _snowmanxxxxxxxx = -5;
            _snowmanxxxxxxxxx = -5;
         } else if (_snowmanxxxxxxx == BlockRotation.COUNTERCLOCKWISE_90) {
            _snowmanxxxxxxxxx = -5;
         }

         int _snowmanxxxxxxxxxx = (_snowman << 4) + 7;
         int _snowmanxxxxxxxxxxx = (_snowman << 4) + 7;
         int _snowmanxxxxxxxxxxxx = _snowman.getHeightInGround(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
         int _snowmanxxxxxxxxxxxxx = _snowman.getHeightInGround(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
         int _snowmanxxxxxxxxxxxxxx = _snowman.getHeightInGround(_snowmanxxxxxxxxxx + _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
         int _snowmanxxxxxxxxxxxxxxx = _snowman.getHeightInGround(_snowmanxxxxxxxxxx + _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx, Heightmap.Type.WORLD_SURFACE_WG);
         int _snowmanxxxxxxxxxxxxxxxx = Math.min(Math.min(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx), Math.min(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         if (_snowmanxxxxxxxxxxxxxxxx >= 60) {
            BlockPos _snowmanxxxxxxxxxxxxxxxxx = new BlockPos(_snowman * 16 + 8, _snowmanxxxxxxxxxxxxxxxx + 1, _snowman * 16 + 8);
            List<WoodlandMansionGenerator.Piece> _snowmanxxxxxxxxxxxxxxxxxx = Lists.newLinkedList();
            WoodlandMansionGenerator.addPieces(_snowman, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, this.random);
            this.children.addAll(_snowmanxxxxxxxxxxxxxxxxxx);
            this.setBoundingBoxFromChildren();
         }
      }

      @Override
      public void generateStructure(
         StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos
      ) {
         super.generateStructure(world, structureAccessor, chunkGenerator, random, box, chunkPos);
         int _snowman = this.boundingBox.minY;

         for (int _snowmanx = box.minX; _snowmanx <= box.maxX; _snowmanx++) {
            for (int _snowmanxx = box.minZ; _snowmanxx <= box.maxZ; _snowmanxx++) {
               BlockPos _snowmanxxx = new BlockPos(_snowmanx, _snowman, _snowmanxx);
               if (!world.isAir(_snowmanxxx) && this.boundingBox.contains(_snowmanxxx)) {
                  boolean _snowmanxxxx = false;

                  for (StructurePiece _snowmanxxxxx : this.children) {
                     if (_snowmanxxxxx.getBoundingBox().contains(_snowmanxxx)) {
                        _snowmanxxxx = true;
                        break;
                     }
                  }

                  if (_snowmanxxxx) {
                     for (int _snowmanxxxxxx = _snowman - 1; _snowmanxxxxxx > 1; _snowmanxxxxxx--) {
                        BlockPos _snowmanxxxxxxx = new BlockPos(_snowmanx, _snowmanxxxxxx, _snowmanxx);
                        if (!world.isAir(_snowmanxxxxxxx) && !world.getBlockState(_snowmanxxxxxxx).getMaterial().isLiquid()) {
                           break;
                        }

                        world.setBlockState(_snowmanxxxxxxx, Blocks.COBBLESTONE.getDefaultState(), 2);
                     }
                  }
               }
            }
         }
      }
   }
}
