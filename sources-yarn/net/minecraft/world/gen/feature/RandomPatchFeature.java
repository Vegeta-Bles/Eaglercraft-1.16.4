package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomPatchFeature extends Feature<RandomPatchFeatureConfig> {
   public RandomPatchFeature(Codec<RandomPatchFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, RandomPatchFeatureConfig arg4) {
      BlockState lv = arg4.stateProvider.getBlockState(random, arg3);
      BlockPos lv2;
      if (arg4.project) {
         lv2 = arg.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, arg3);
      } else {
         lv2 = arg3;
      }

      int i = 0;
      BlockPos.Mutable lv4 = new BlockPos.Mutable();

      for (int j = 0; j < arg4.tries; j++) {
         lv4.set(
            lv2,
            random.nextInt(arg4.spreadX + 1) - random.nextInt(arg4.spreadX + 1),
            random.nextInt(arg4.spreadY + 1) - random.nextInt(arg4.spreadY + 1),
            random.nextInt(arg4.spreadZ + 1) - random.nextInt(arg4.spreadZ + 1)
         );
         BlockPos lv5 = lv4.down();
         BlockState lv6 = arg.getBlockState(lv5);
         if ((arg.isAir(lv4) || arg4.canReplace && arg.getBlockState(lv4).getMaterial().isReplaceable())
            && lv.canPlaceAt(arg, lv4)
            && (arg4.whitelist.isEmpty() || arg4.whitelist.contains(lv6.getBlock()))
            && !arg4.blacklist.contains(lv6)
            && (
               !arg4.needsWater
                  || arg.getFluidState(lv5.west()).isIn(FluidTags.WATER)
                  || arg.getFluidState(lv5.east()).isIn(FluidTags.WATER)
                  || arg.getFluidState(lv5.north()).isIn(FluidTags.WATER)
                  || arg.getFluidState(lv5.south()).isIn(FluidTags.WATER)
            )) {
            arg4.blockPlacer.generate(arg, lv4, lv, random);
            i++;
         }
      }

      return i > 0;
   }
}
