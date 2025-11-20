package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class AbstractPileFeature extends Feature<BlockPileFeatureConfig> {
   public AbstractPileFeature(Codec<BlockPileFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, BlockPileFeatureConfig _snowman) {
      if (_snowman.getY() < 5) {
         return false;
      } else {
         int _snowmanxxxxx = 2 + _snowman.nextInt(2);
         int _snowmanxxxxxx = 2 + _snowman.nextInt(2);

         for (BlockPos _snowmanxxxxxxx : BlockPos.iterate(_snowman.add(-_snowmanxxxxx, 0, -_snowmanxxxxxx), _snowman.add(_snowmanxxxxx, 1, _snowmanxxxxxx))) {
            int _snowmanxxxxxxxx = _snowman.getX() - _snowmanxxxxxxx.getX();
            int _snowmanxxxxxxxxx = _snowman.getZ() - _snowmanxxxxxxx.getZ();
            if ((float)(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx) <= _snowman.nextFloat() * 10.0F - _snowman.nextFloat() * 6.0F) {
               this.addPileBlock(_snowman, _snowmanxxxxxxx, _snowman, _snowman);
            } else if ((double)_snowman.nextFloat() < 0.031) {
               this.addPileBlock(_snowman, _snowmanxxxxxxx, _snowman, _snowman);
            }
         }

         return true;
      }
   }

   private boolean canPlacePileBlock(WorldAccess world, BlockPos pos, Random random) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      return _snowmanx.isOf(Blocks.GRASS_PATH) ? random.nextBoolean() : _snowmanx.isSideSolidFullSquare(world, _snowman, Direction.UP);
   }

   private void addPileBlock(WorldAccess world, BlockPos pos, Random random, BlockPileFeatureConfig config) {
      if (world.isAir(pos) && this.canPlacePileBlock(world, pos, random)) {
         world.setBlockState(pos, config.stateProvider.getBlockState(random, pos), 4);
      }
   }
}
