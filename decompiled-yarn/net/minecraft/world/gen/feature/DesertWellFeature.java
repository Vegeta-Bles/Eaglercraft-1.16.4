package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DesertWellFeature extends Feature<DefaultFeatureConfig> {
   private static final BlockStatePredicate CAN_GENERATE = BlockStatePredicate.forBlock(Blocks.SAND);
   private final BlockState slab = Blocks.SANDSTONE_SLAB.getDefaultState();
   private final BlockState wall = Blocks.SANDSTONE.getDefaultState();
   private final BlockState fluidInside = Blocks.WATER.getDefaultState();

   public DesertWellFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      _snowman = _snowman.up();

      while (_snowman.isAir(_snowman) && _snowman.getY() > 2) {
         _snowman = _snowman.down();
      }

      if (!CAN_GENERATE.test(_snowman.getBlockState(_snowman))) {
         return false;
      } else {
         for (int _snowmanxxxxx = -2; _snowmanxxxxx <= 2; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = -2; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
               if (_snowman.isAir(_snowman.add(_snowmanxxxxx, -1, _snowmanxxxxxx)) && _snowman.isAir(_snowman.add(_snowmanxxxxx, -2, _snowmanxxxxxx))) {
                  return false;
               }
            }
         }

         for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 0; _snowmanxxxxx++) {
            for (int _snowmanxxxxxxx = -2; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = -2; _snowmanxxxxxxxx <= 2; _snowmanxxxxxxxx++) {
                  _snowman.setBlockState(_snowman.add(_snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx), this.wall, 2);
               }
            }
         }

         _snowman.setBlockState(_snowman, this.fluidInside, 2);

         for (Direction _snowmanxxxxx : Direction.Type.HORIZONTAL) {
            _snowman.setBlockState(_snowman.offset(_snowmanxxxxx), this.fluidInside, 2);
         }

         for (int _snowmanxxxxx = -2; _snowmanxxxxx <= 2; _snowmanxxxxx++) {
            for (int _snowmanxxxxxxx = -2; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
               if (_snowmanxxxxx == -2 || _snowmanxxxxx == 2 || _snowmanxxxxxxx == -2 || _snowmanxxxxxxx == 2) {
                  _snowman.setBlockState(_snowman.add(_snowmanxxxxx, 1, _snowmanxxxxxxx), this.wall, 2);
               }
            }
         }

         _snowman.setBlockState(_snowman.add(2, 1, 0), this.slab, 2);
         _snowman.setBlockState(_snowman.add(-2, 1, 0), this.slab, 2);
         _snowman.setBlockState(_snowman.add(0, 1, 2), this.slab, 2);
         _snowman.setBlockState(_snowman.add(0, 1, -2), this.slab, 2);

         for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
            for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx <= 1; _snowmanxxxxxxxx++) {
               if (_snowmanxxxxx == 0 && _snowmanxxxxxxxx == 0) {
                  _snowman.setBlockState(_snowman.add(_snowmanxxxxx, 4, _snowmanxxxxxxxx), this.wall, 2);
               } else {
                  _snowman.setBlockState(_snowman.add(_snowmanxxxxx, 4, _snowmanxxxxxxxx), this.slab, 2);
               }
            }
         }

         for (int _snowmanxxxxx = 1; _snowmanxxxxx <= 3; _snowmanxxxxx++) {
            _snowman.setBlockState(_snowman.add(-1, _snowmanxxxxx, -1), this.wall, 2);
            _snowman.setBlockState(_snowman.add(-1, _snowmanxxxxx, 1), this.wall, 2);
            _snowman.setBlockState(_snowman.add(1, _snowmanxxxxx, -1), this.wall, 2);
            _snowman.setBlockState(_snowman.add(1, _snowmanxxxxx, 1), this.wall, 2);
         }

         return true;
      }
   }
}
