package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SeagrassFeature extends Feature<ProbabilityConfig> {
   public SeagrassFeature(Codec<ProbabilityConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, ProbabilityConfig _snowman) {
      boolean _snowmanxxxxx = false;
      int _snowmanxxxxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
      int _snowmanxxxxxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
      int _snowmanxxxxxxxx = _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR, _snowman.getX() + _snowmanxxxxxx, _snowman.getZ() + _snowmanxxxxxxx);
      BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowman.getX() + _snowmanxxxxxx, _snowmanxxxxxxxx, _snowman.getZ() + _snowmanxxxxxxx);
      if (_snowman.getBlockState(_snowmanxxxxxxxxx).isOf(Blocks.WATER)) {
         boolean _snowmanxxxxxxxxxx = _snowman.nextDouble() < (double)_snowman.probability;
         BlockState _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
         if (_snowmanxxxxxxxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxxxx)) {
            if (_snowmanxxxxxxxxxx) {
               BlockState _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
               BlockPos _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.up();
               if (_snowman.getBlockState(_snowmanxxxxxxxxxxxxx).isOf(Blocks.WATER)) {
                  _snowman.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, 2);
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 2);
               }
            } else {
               _snowman.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, 2);
            }

            _snowmanxxxxx = true;
         }
      }

      return _snowmanxxxxx;
   }
}
