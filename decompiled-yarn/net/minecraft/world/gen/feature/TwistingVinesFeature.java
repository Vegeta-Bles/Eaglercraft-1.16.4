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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class TwistingVinesFeature extends Feature<DefaultFeatureConfig> {
   public TwistingVinesFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      return method_26265(_snowman, _snowman, _snowman, 8, 4, 8);
   }

   public static boolean method_26265(WorldAccess _snowman, Random _snowman, BlockPos _snowman, int _snowman, int _snowman, int _snowman) {
      if (isNotSuitable(_snowman, _snowman)) {
         return false;
      } else {
         generateVinesInArea(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         return true;
      }
   }

   private static void generateVinesInArea(WorldAccess _snowman, Random _snowman, BlockPos _snowman, int _snowman, int _snowman, int _snowman) {
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman * _snowman; _snowmanxxxxxxx++) {
         _snowmanxxxxxx.set(_snowman).move(MathHelper.nextInt(_snowman, -_snowman, _snowman), MathHelper.nextInt(_snowman, -_snowman, _snowman), MathHelper.nextInt(_snowman, -_snowman, _snowman));
         if (method_27220(_snowman, _snowmanxxxxxx) && !isNotSuitable(_snowman, _snowmanxxxxxx)) {
            int _snowmanxxxxxxxx = MathHelper.nextInt(_snowman, 1, _snowman);
            if (_snowman.nextInt(6) == 0) {
               _snowmanxxxxxxxx *= 2;
            }

            if (_snowman.nextInt(5) == 0) {
               _snowmanxxxxxxxx = 1;
            }

            int _snowmanxxxxxxxxx = 17;
            int _snowmanxxxxxxxxxx = 25;
            generateVineColumn(_snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxx, 17, 25);
         }
      }
   }

   private static boolean method_27220(WorldAccess _snowman, BlockPos.Mutable _snowman) {
      do {
         _snowman.move(0, -1, 0);
         if (World.isOutOfBuildLimitVertically(_snowman)) {
            return false;
         }
      } while (_snowman.getBlockState(_snowman).isAir());

      _snowman.move(0, 1, 0);
      return true;
   }

   public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int maxLength, int minAge, int maxAge) {
      for (int _snowman = 1; _snowman <= maxLength; _snowman++) {
         if (world.isAir(pos)) {
            if (_snowman == maxLength || !world.isAir(pos.up())) {
               world.setBlockState(
                  pos, Blocks.TWISTING_VINES.getDefaultState().with(AbstractPlantStemBlock.AGE, Integer.valueOf(MathHelper.nextInt(random, minAge, maxAge))), 2
               );
               break;
            }

            world.setBlockState(pos, Blocks.TWISTING_VINES_PLANT.getDefaultState(), 2);
         }

         pos.move(Direction.UP);
      }
   }

   private static boolean isNotSuitable(WorldAccess _snowman, BlockPos _snowman) {
      if (!_snowman.isAir(_snowman)) {
         return true;
      } else {
         BlockState _snowmanxx = _snowman.getBlockState(_snowman.down());
         return !_snowmanxx.isOf(Blocks.NETHERRACK) && !_snowmanxx.isOf(Blocks.WARPED_NYLIUM) && !_snowmanxx.isOf(Blocks.WARPED_WART_BLOCK);
      }
   }
}
