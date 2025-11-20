package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DeltaFeature extends Feature<DeltaFeatureConfig> {
   private static final ImmutableList<Block> field_24133 = ImmutableList.of(
      Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER
   );
   private static final Direction[] DIRECTIONS = Direction.values();

   public DeltaFeature(Codec<DeltaFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DeltaFeatureConfig arg4) {
      boolean bl = false;
      boolean bl2 = random.nextDouble() < 0.9;
      int i = bl2 ? arg4.getRimSize().getValue(random) : 0;
      int j = bl2 ? arg4.getRimSize().getValue(random) : 0;
      boolean bl3 = bl2 && i != 0 && j != 0;
      int k = arg4.getSize().getValue(random);
      int l = arg4.getSize().getValue(random);
      int m = Math.max(k, l);

      for (BlockPos lv : BlockPos.iterateOutwards(arg3, k, 0, l)) {
         if (lv.getManhattanDistance(arg3) > m) {
            break;
         }

         if (method_27103(arg, lv, arg4)) {
            if (bl3) {
               bl = true;
               this.setBlockState(arg, lv, arg4.getRim());
            }

            BlockPos lv2 = lv.add(i, 0, j);
            if (method_27103(arg, lv2, arg4)) {
               bl = true;
               this.setBlockState(arg, lv2, arg4.getContents());
            }
         }
      }

      return bl;
   }

   private static boolean method_27103(WorldAccess arg, BlockPos arg2, DeltaFeatureConfig arg3) {
      BlockState lv = arg.getBlockState(arg2);
      if (lv.isOf(arg3.getContents().getBlock())) {
         return false;
      } else if (field_24133.contains(lv.getBlock())) {
         return false;
      } else {
         for (Direction lv2 : DIRECTIONS) {
            boolean bl = arg.getBlockState(arg2.offset(lv2)).isAir();
            if (bl && lv2 != Direction.UP || !bl && lv2 == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }
}
