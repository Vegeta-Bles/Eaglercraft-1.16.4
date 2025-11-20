package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class KelpFeature extends Feature<DefaultFeatureConfig> {
   public KelpFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      int _snowmanxxxxx = 0;
      int _snowmanxxxxxx = _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR, _snowman.getX(), _snowman.getZ());
      BlockPos _snowmanxxxxxxx = new BlockPos(_snowman.getX(), _snowmanxxxxxx, _snowman.getZ());
      if (_snowman.getBlockState(_snowmanxxxxxxx).isOf(Blocks.WATER)) {
         BlockState _snowmanxxxxxxxx = Blocks.KELP.getDefaultState();
         BlockState _snowmanxxxxxxxxx = Blocks.KELP_PLANT.getDefaultState();
         int _snowmanxxxxxxxxxx = 1 + _snowman.nextInt(10);

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
            if (_snowman.getBlockState(_snowmanxxxxxxx).isOf(Blocks.WATER) && _snowman.getBlockState(_snowmanxxxxxxx.up()).isOf(Blocks.WATER) && _snowmanxxxxxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxx)) {
               if (_snowmanxxxxxxxxxxx == _snowmanxxxxxxxxxx) {
                  _snowman.setBlockState(_snowmanxxxxxxx, _snowmanxxxxxxxx.with(KelpBlock.AGE, Integer.valueOf(_snowman.nextInt(4) + 20)), 2);
                  _snowmanxxxxx++;
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxx, _snowmanxxxxxxxxx, 2);
               }
            } else if (_snowmanxxxxxxxxxxx > 0) {
               BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.down();
               if (_snowmanxxxxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxxxxxxx) && !_snowman.getBlockState(_snowmanxxxxxxxxxxxx.down()).isOf(Blocks.KELP)) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx.with(KelpBlock.AGE, Integer.valueOf(_snowman.nextInt(4) + 20)), 2);
                  _snowmanxxxxx++;
               }
               break;
            }

            _snowmanxxxxxxx = _snowmanxxxxxxx.up();
         }
      }

      return _snowmanxxxxx > 0;
   }
}
