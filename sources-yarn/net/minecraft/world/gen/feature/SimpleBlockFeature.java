package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SimpleBlockFeature extends Feature<SimpleBlockFeatureConfig> {
   public SimpleBlockFeature(Codec<SimpleBlockFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, SimpleBlockFeatureConfig arg4) {
      if (arg4.placeOn.contains(arg.getBlockState(arg3.down()))
         && arg4.placeIn.contains(arg.getBlockState(arg3))
         && arg4.placeUnder.contains(arg.getBlockState(arg3.up()))) {
         arg.setBlockState(arg3, arg4.toPlace, 2);
         return true;
      } else {
         return false;
      }
   }
}
