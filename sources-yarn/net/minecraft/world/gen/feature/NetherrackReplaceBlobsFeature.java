package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherrackReplaceBlobsFeature extends Feature<NetherrackReplaceBlobsFeatureConfig> {
   public NetherrackReplaceBlobsFeature(Codec<NetherrackReplaceBlobsFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, NetherrackReplaceBlobsFeatureConfig arg4) {
      Block lv = arg4.target.getBlock();
      BlockPos lv2 = method_27107(arg, arg3.mutableCopy().clamp(Direction.Axis.Y, 1, arg.getHeight() - 1), lv);
      if (lv2 == null) {
         return false;
      } else {
         int i = arg4.getRadius().getValue(random);
         boolean bl = false;

         for (BlockPos lv3 : BlockPos.iterateOutwards(lv2, i, i, i)) {
            if (lv3.getManhattanDistance(lv2) > i) {
               break;
            }

            BlockState lv4 = arg.getBlockState(lv3);
            if (lv4.isOf(lv)) {
               this.setBlockState(arg, lv3, arg4.state);
               bl = true;
            }
         }

         return bl;
      }
   }

   @Nullable
   private static BlockPos method_27107(WorldAccess arg, BlockPos.Mutable arg2, Block arg3) {
      while (arg2.getY() > 1) {
         BlockState lv = arg.getBlockState(arg2);
         if (lv.isOf(arg3)) {
            return arg2;
         }

         arg2.move(Direction.DOWN);
      }

      return null;
   }
}
