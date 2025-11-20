package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BlueIceFeature extends Feature<DefaultFeatureConfig> {
   public BlueIceFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      if (arg3.getY() > arg.getSeaLevel() - 1) {
         return false;
      } else if (!arg.getBlockState(arg3).isOf(Blocks.WATER) && !arg.getBlockState(arg3.down()).isOf(Blocks.WATER)) {
         return false;
      } else {
         boolean bl = false;

         for (Direction lv : Direction.values()) {
            if (lv != Direction.DOWN && arg.getBlockState(arg3.offset(lv)).isOf(Blocks.PACKED_ICE)) {
               bl = true;
               break;
            }
         }

         if (!bl) {
            return false;
         } else {
            arg.setBlockState(arg3, Blocks.BLUE_ICE.getDefaultState(), 2);

            for (int i = 0; i < 200; i++) {
               int j = random.nextInt(5) - random.nextInt(6);
               int k = 3;
               if (j < 2) {
                  k += j / 2;
               }

               if (k >= 1) {
                  BlockPos lv2 = arg3.add(random.nextInt(k) - random.nextInt(k), j, random.nextInt(k) - random.nextInt(k));
                  BlockState lv3 = arg.getBlockState(lv2);
                  if (lv3.getMaterial() == Material.AIR || lv3.isOf(Blocks.WATER) || lv3.isOf(Blocks.PACKED_ICE) || lv3.isOf(Blocks.ICE)) {
                     for (Direction lv4 : Direction.values()) {
                        BlockState lv5 = arg.getBlockState(lv2.offset(lv4));
                        if (lv5.isOf(Blocks.BLUE_ICE)) {
                           arg.setBlockState(lv2, Blocks.BLUE_ICE.getDefaultState(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
