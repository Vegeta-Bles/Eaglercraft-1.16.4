package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class UnderwaterDiskFeature extends DiskFeature {
   public UnderwaterDiskFeature(Codec<DiskFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DiskFeatureConfig arg4) {
      return !arg.getFluidState(arg3).isIn(FluidTags.WATER) ? false : super.generate(arg, arg2, random, arg3, arg4);
   }
}
