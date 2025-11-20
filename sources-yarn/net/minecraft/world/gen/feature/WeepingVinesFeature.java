package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class WeepingVinesFeature extends Feature<DefaultFeatureConfig> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public WeepingVinesFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, DefaultFeatureConfig arg4) {
      if (!arg.isAir(arg3)) {
         return false;
      } else {
         BlockState lv = arg.getBlockState(arg3.up());
         if (!lv.isOf(Blocks.NETHERRACK) && !lv.isOf(Blocks.NETHER_WART_BLOCK)) {
            return false;
         } else {
            this.generateNetherWartBlocksInArea(arg, random, arg3);
            this.generateVinesInArea(arg, random, arg3);
            return true;
         }
      }
   }

   private void generateNetherWartBlocksInArea(WorldAccess world, Random random, BlockPos pos) {
      world.setBlockState(pos, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
      BlockPos.Mutable lv = new BlockPos.Mutable();
      BlockPos.Mutable lv2 = new BlockPos.Mutable();

      for (int i = 0; i < 200; i++) {
         lv.set(pos, random.nextInt(6) - random.nextInt(6), random.nextInt(2) - random.nextInt(5), random.nextInt(6) - random.nextInt(6));
         if (world.isAir(lv)) {
            int j = 0;

            for (Direction lv3 : DIRECTIONS) {
               BlockState lv4 = world.getBlockState(lv2.set(lv, lv3));
               if (lv4.isOf(Blocks.NETHERRACK) || lv4.isOf(Blocks.NETHER_WART_BLOCK)) {
                  j++;
               }

               if (j > 1) {
                  break;
               }
            }

            if (j == 1) {
               world.setBlockState(lv, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
            }
         }
      }
   }

   private void generateVinesInArea(WorldAccess world, Random random, BlockPos pos) {
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int i = 0; i < 100; i++) {
         lv.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
         if (world.isAir(lv)) {
            BlockState lv2 = world.getBlockState(lv.up());
            if (lv2.isOf(Blocks.NETHERRACK) || lv2.isOf(Blocks.NETHER_WART_BLOCK)) {
               int j = MathHelper.nextInt(random, 1, 8);
               if (random.nextInt(6) == 0) {
                  j *= 2;
               }

               if (random.nextInt(5) == 0) {
                  j = 1;
               }

               int k = 17;
               int l = 25;
               generateVineColumn(world, random, lv, j, 17, 25);
            }
         }
      }
   }

   public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int length, int minAge, int maxAge) {
      for (int l = 0; l <= length; l++) {
         if (world.isAir(pos)) {
            if (l == length || !world.isAir(pos.down())) {
               world.setBlockState(
                  pos, Blocks.WEEPING_VINES.getDefaultState().with(AbstractPlantStemBlock.AGE, Integer.valueOf(MathHelper.nextInt(random, minAge, maxAge))), 2
               );
               break;
            }

            world.setBlockState(pos, Blocks.WEEPING_VINES_PLANT.getDefaultState(), 2);
         }

         pos.move(Direction.DOWN);
      }
   }
}
