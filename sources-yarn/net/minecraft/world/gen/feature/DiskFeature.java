package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DiskFeature extends Feature<DiskFeatureConfig> {
   public DiskFeature(Codec<DiskFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DiskFeatureConfig arg4) {
      boolean bl = false;
      int i = arg4.radius.getValue(random);

      for (int j = arg3.getX() - i; j <= arg3.getX() + i; j++) {
         for (int k = arg3.getZ() - i; k <= arg3.getZ() + i; k++) {
            int l = j - arg3.getX();
            int m = k - arg3.getZ();
            if (l * l + m * m <= i * i) {
               for (int n = arg3.getY() - arg4.halfHeight; n <= arg3.getY() + arg4.halfHeight; n++) {
                  BlockPos lv = new BlockPos(j, n, k);
                  Block lv2 = arg.getBlockState(lv).getBlock();

                  for (BlockState lv3 : arg4.targets) {
                     if (lv3.isOf(lv2)) {
                        arg.setBlockState(lv, arg4.state, 2);
                        bl = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return bl;
   }
}
