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

   public WeepingVinesFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      if (!_snowman.isAir(_snowman)) {
         return false;
      } else {
         BlockState _snowmanxxxxx = _snowman.getBlockState(_snowman.up());
         if (!_snowmanxxxxx.isOf(Blocks.NETHERRACK) && !_snowmanxxxxx.isOf(Blocks.NETHER_WART_BLOCK)) {
            return false;
         } else {
            this.generateNetherWartBlocksInArea(_snowman, _snowman, _snowman);
            this.generateVinesInArea(_snowman, _snowman, _snowman);
            return true;
         }
      }
   }

   private void generateNetherWartBlocksInArea(WorldAccess world, Random random, BlockPos pos) {
      world.setBlockState(pos, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (int _snowmanxx = 0; _snowmanxx < 200; _snowmanxx++) {
         _snowman.set(pos, random.nextInt(6) - random.nextInt(6), random.nextInt(2) - random.nextInt(5), random.nextInt(6) - random.nextInt(6));
         if (world.isAir(_snowman)) {
            int _snowmanxxx = 0;

            for (Direction _snowmanxxxx : DIRECTIONS) {
               BlockState _snowmanxxxxx = world.getBlockState(_snowmanx.set(_snowman, _snowmanxxxx));
               if (_snowmanxxxxx.isOf(Blocks.NETHERRACK) || _snowmanxxxxx.isOf(Blocks.NETHER_WART_BLOCK)) {
                  _snowmanxxx++;
               }

               if (_snowmanxxx > 1) {
                  break;
               }
            }

            if (_snowmanxxx == 1) {
               world.setBlockState(_snowman, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
            }
         }
      }
   }

   private void generateVinesInArea(WorldAccess world, Random random, BlockPos pos) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();

      for (int _snowmanx = 0; _snowmanx < 100; _snowmanx++) {
         _snowman.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
         if (world.isAir(_snowman)) {
            BlockState _snowmanxx = world.getBlockState(_snowman.up());
            if (_snowmanxx.isOf(Blocks.NETHERRACK) || _snowmanxx.isOf(Blocks.NETHER_WART_BLOCK)) {
               int _snowmanxxx = MathHelper.nextInt(random, 1, 8);
               if (random.nextInt(6) == 0) {
                  _snowmanxxx *= 2;
               }

               if (random.nextInt(5) == 0) {
                  _snowmanxxx = 1;
               }

               int _snowmanxxxx = 17;
               int _snowmanxxxxx = 25;
               generateVineColumn(world, random, _snowman, _snowmanxxx, 17, 25);
            }
         }
      }
   }

   public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int length, int minAge, int maxAge) {
      for (int _snowman = 0; _snowman <= length; _snowman++) {
         if (world.isAir(pos)) {
            if (_snowman == length || !world.isAir(pos.down())) {
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
