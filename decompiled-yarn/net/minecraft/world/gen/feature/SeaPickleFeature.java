package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SeaPickleFeature extends Feature<CountConfig> {
   public SeaPickleFeature(Codec<CountConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, CountConfig _snowman) {
      int _snowmanxxxxx = 0;
      int _snowmanxxxxxx = _snowman.getCount().getValue(_snowman);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
         int _snowmanxxxxxxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
         int _snowmanxxxxxxxxx = _snowman.nextInt(8) - _snowman.nextInt(8);
         int _snowmanxxxxxxxxxx = _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR, _snowman.getX() + _snowmanxxxxxxxx, _snowman.getZ() + _snowmanxxxxxxxxx);
         BlockPos _snowmanxxxxxxxxxxx = new BlockPos(_snowman.getX() + _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowman.getZ() + _snowmanxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxx = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(_snowman.nextInt(4) + 1));
         if (_snowman.getBlockState(_snowmanxxxxxxxxxxx).isOf(Blocks.WATER) && _snowmanxxxxxxxxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxxxxxx)) {
            _snowman.setBlockState(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 2);
            _snowmanxxxxx++;
         }
      }

      return _snowmanxxxxx > 0;
   }
}
