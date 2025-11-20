package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BasaltColumnsFeature extends Feature<BasaltColumnsFeatureConfig> {
   private static final ImmutableList<Block> field_24132 = ImmutableList.of(
      Blocks.LAVA,
      Blocks.BEDROCK,
      Blocks.MAGMA_BLOCK,
      Blocks.SOUL_SAND,
      Blocks.NETHER_BRICKS,
      Blocks.NETHER_BRICK_FENCE,
      Blocks.NETHER_BRICK_STAIRS,
      Blocks.NETHER_WART,
      Blocks.CHEST,
      Blocks.SPAWNER
   );

   public BasaltColumnsFeature(Codec<BasaltColumnsFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, BasaltColumnsFeatureConfig _snowman) {
      int _snowmanxxxxx = _snowman.getSeaLevel();
      if (!method_30379(_snowman, _snowmanxxxxx, _snowman.mutableCopy())) {
         return false;
      } else {
         int _snowmanxxxxxx = _snowman.getHeight().getValue(_snowman);
         boolean _snowmanxxxxxxx = _snowman.nextFloat() < 0.9F;
         int _snowmanxxxxxxxx = Math.min(_snowmanxxxxxx, _snowmanxxxxxxx ? 5 : 8);
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx ? 50 : 15;
         boolean _snowmanxxxxxxxxxx = false;

         for (BlockPos _snowmanxxxxxxxxxxx : BlockPos.iterateRandomly(
            _snowman, _snowmanxxxxxxxxx, _snowman.getX() - _snowmanxxxxxxxx, _snowman.getY(), _snowman.getZ() - _snowmanxxxxxxxx, _snowman.getX() + _snowmanxxxxxxxx, _snowman.getY(), _snowman.getZ() + _snowmanxxxxxxxx
         )) {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx - _snowmanxxxxxxxxxxx.getManhattanDistance(_snowman);
            if (_snowmanxxxxxxxxxxxx >= 0) {
               _snowmanxxxxxxxxxx |= this.method_27096(_snowman, _snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman.getReach().getValue(_snowman));
            }
         }

         return _snowmanxxxxxxxxxx;
      }
   }

   private boolean method_27096(WorldAccess _snowman, int _snowman, BlockPos _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = false;

      for (BlockPos _snowmanxxxxxx : BlockPos.iterate(_snowman.getX() - _snowman, _snowman.getY(), _snowman.getZ() - _snowman, _snowman.getX() + _snowman, _snowman.getY(), _snowman.getZ() + _snowman)) {
         int _snowmanxxxxxxx = _snowmanxxxxxx.getManhattanDistance(_snowman);
         BlockPos _snowmanxxxxxxxx = method_27095(_snowman, _snowman, _snowmanxxxxxx)
            ? method_27094(_snowman, _snowman, _snowmanxxxxxx.mutableCopy(), _snowmanxxxxxxx)
            : method_27098(_snowman, _snowmanxxxxxx.mutableCopy(), _snowmanxxxxxxx);
         if (_snowmanxxxxxxxx != null) {
            int _snowmanxxxxxxxxx = _snowman - _snowmanxxxxxxx / 2;

            for (BlockPos.Mutable _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.mutableCopy(); _snowmanxxxxxxxxx >= 0; _snowmanxxxxxxxxx--) {
               if (method_27095(_snowman, _snowman, _snowmanxxxxxxxxxx)) {
                  this.setBlockState(_snowman, _snowmanxxxxxxxxxx, Blocks.BASALT.getDefaultState());
                  _snowmanxxxxxxxxxx.move(Direction.UP);
                  _snowmanxxxxx = true;
               } else {
                  if (!_snowman.getBlockState(_snowmanxxxxxxxxxx).isOf(Blocks.BASALT)) {
                     break;
                  }

                  _snowmanxxxxxxxxxx.move(Direction.UP);
               }
            }
         }
      }

      return _snowmanxxxxx;
   }

   @Nullable
   private static BlockPos method_27094(WorldAccess _snowman, int _snowman, BlockPos.Mutable _snowman, int _snowman) {
      while (_snowman.getY() > 1 && _snowman > 0) {
         _snowman--;
         if (method_30379(_snowman, _snowman, _snowman)) {
            return _snowman;
         }

         _snowman.move(Direction.DOWN);
      }

      return null;
   }

   private static boolean method_30379(WorldAccess _snowman, int _snowman, BlockPos.Mutable _snowman) {
      if (!method_27095(_snowman, _snowman, _snowman)) {
         return false;
      } else {
         BlockState _snowmanxxx = _snowman.getBlockState(_snowman.move(Direction.DOWN));
         _snowman.move(Direction.UP);
         return !_snowmanxxx.isAir() && !field_24132.contains(_snowmanxxx.getBlock());
      }
   }

   @Nullable
   private static BlockPos method_27098(WorldAccess _snowman, BlockPos.Mutable _snowman, int _snowman) {
      while (_snowman.getY() < _snowman.getHeight() && _snowman > 0) {
         _snowman--;
         BlockState _snowmanxxx = _snowman.getBlockState(_snowman);
         if (field_24132.contains(_snowmanxxx.getBlock())) {
            return null;
         }

         if (_snowmanxxx.isAir()) {
            return _snowman;
         }

         _snowman.move(Direction.UP);
      }

      return null;
   }

   private static boolean method_27095(WorldAccess _snowman, int _snowman, BlockPos _snowman) {
      BlockState _snowmanxxx = _snowman.getBlockState(_snowman);
      return _snowmanxxx.isAir() || _snowmanxxx.isOf(Blocks.LAVA) && _snowman.getY() <= _snowman;
   }
}
