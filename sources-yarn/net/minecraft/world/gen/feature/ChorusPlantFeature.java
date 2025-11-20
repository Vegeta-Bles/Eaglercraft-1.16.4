package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ChorusPlantFeature extends Feature<DefaultFeatureConfig> {
   public ChorusPlantFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      if (arg.isAir(arg3) && arg.getBlockState(arg3.down()).isOf(Blocks.END_STONE)) {
         ChorusFlowerBlock.generate(arg, arg3, random, 8);
         return true;
      } else {
         return false;
      }
   }
}
