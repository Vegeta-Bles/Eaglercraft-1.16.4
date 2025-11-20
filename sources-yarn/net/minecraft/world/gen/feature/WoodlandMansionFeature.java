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
   public WoodlandMansionFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   @Override
   protected boolean isUniformDistribution() {
      return false;
   }

   protected boolean shouldStartAt(
      ChunkGenerator arg, BiomeSource arg2, long l, ChunkRandom arg3, int i, int j, Biome arg4, ChunkPos arg5, DefaultFeatureConfig arg6
   ) {
      for (Biome lv : arg2.getBiomesInArea(i * 16 + 9, arg.getSeaLevel(), j * 16 + 9, 32)) {
         if (!lv.getGenerationSettings().hasStructureFeature(this)) {
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
      public Start(StructureFeature<DefaultFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, DefaultFeatureConfig arg5) {
         BlockRotation lv = BlockRotation.random(this.random);
         int k = 5;
         int l = 5;
         if (lv == BlockRotation.CLOCKWISE_90) {
            k = -5;
         } else if (lv == BlockRotation.CLOCKWISE_180) {
            k = -5;
            l = -5;
         } else if (lv == BlockRotation.COUNTERCLOCKWISE_90) {
            l = -5;
         }

         int m = (i << 4) + 7;
         int n = (j << 4) + 7;
         int o = arg2.getHeightInGround(m, n, Heightmap.Type.WORLD_SURFACE_WG);
         int p = arg2.getHeightInGround(m, n + l, Heightmap.Type.WORLD_SURFACE_WG);
         int q = arg2.getHeightInGround(m + k, n, Heightmap.Type.WORLD_SURFACE_WG);
         int r = arg2.getHeightInGround(m + k, n + l, Heightmap.Type.WORLD_SURFACE_WG);
         int s = Math.min(Math.min(o, p), Math.min(q, r));
         if (s >= 60) {
            BlockPos lv2 = new BlockPos(i * 16 + 8, s + 1, j * 16 + 8);
            List<WoodlandMansionGenerator.Piece> list = Lists.newLinkedList();
            WoodlandMansionGenerator.addPieces(arg3, lv2, lv, list, this.random);
            this.children.addAll(list);
            this.setBoundingBoxFromChildren();
         }
      }

      @Override
      public void generateStructure(
         StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos
      ) {
         super.generateStructure(world, structureAccessor, chunkGenerator, random, box, chunkPos);
         int i = this.boundingBox.minY;

         for (int j = box.minX; j <= box.maxX; j++) {
            for (int k = box.minZ; k <= box.maxZ; k++) {
               BlockPos lv = new BlockPos(j, i, k);
               if (!world.isAir(lv) && this.boundingBox.contains(lv)) {
                  boolean bl = false;

                  for (StructurePiece lv2 : this.children) {
                     if (lv2.getBoundingBox().contains(lv)) {
                        bl = true;
                        break;
                     }
                  }

                  if (bl) {
                     for (int l = i - 1; l > 1; l--) {
                        BlockPos lv3 = new BlockPos(j, l, k);
                        if (!world.isAir(lv3) && !world.getBlockState(lv3).getMaterial().isLiquid()) {
                           break;
                        }

                        world.setBlockState(lv3, Blocks.COBBLESTONE.getDefaultState(), 2);
                     }
                  }
               }
            }
         }
      }
   }
}
