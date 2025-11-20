package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EmeraldOreFeature extends Feature<EmeraldOreFeatureConfig> {
   public EmeraldOreFeature(Codec<EmeraldOreFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, EmeraldOreFeatureConfig arg4) {
      if (arg.getBlockState(arg3).isOf(arg4.target.getBlock())) {
         arg.setBlockState(arg3, arg4.state, 2);
      }

      return true;
   }
}
