package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IcePatchFeature extends DiskFeature {
   public IcePatchFeature(Codec<DiskFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DiskFeatureConfig arg4) {
      while (arg.isAir(arg3) && arg3.getY() > 2) {
         arg3 = arg3.down();
      }

      return !arg.getBlockState(arg3).isOf(Blocks.SNOW_BLOCK) ? false : super.generate(arg, arg2, random, arg3, arg4);
   }
}
