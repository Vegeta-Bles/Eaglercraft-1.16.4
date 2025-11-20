package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherForestVegetationFeature extends Feature<BlockPileFeatureConfig> {
   public NetherForestVegetationFeature(Codec<BlockPileFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, BlockPileFeatureConfig _snowman) {
      return generate(_snowman, _snowman, _snowman, _snowman, 8, 4);
   }

   public static boolean generate(WorldAccess world, Random random, BlockPos pos, BlockPileFeatureConfig config, int _snowman, int _snowman) {
      Block _snowmanxx = world.getBlockState(pos.down()).getBlock();
      if (!_snowmanxx.isIn(BlockTags.NYLIUM)) {
         return false;
      } else {
         int _snowmanxxx = pos.getY();
         if (_snowmanxxx >= 1 && _snowmanxxx + 1 < 256) {
            int _snowmanxxxx = 0;

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman * _snowman; _snowmanxxxxx++) {
               BlockPos _snowmanxxxxxx = pos.add(random.nextInt(_snowman) - random.nextInt(_snowman), random.nextInt(_snowman) - random.nextInt(_snowman), random.nextInt(_snowman) - random.nextInt(_snowman));
               BlockState _snowmanxxxxxxx = config.stateProvider.getBlockState(random, _snowmanxxxxxx);
               if (world.isAir(_snowmanxxxxxx) && _snowmanxxxxxx.getY() > 0 && _snowmanxxxxxxx.canPlaceAt(world, _snowmanxxxxxx)) {
                  world.setBlockState(_snowmanxxxxxx, _snowmanxxxxxxx, 2);
                  _snowmanxxxx++;
               }
            }

            return _snowmanxxxx > 0;
         } else {
            return false;
         }
      }
   }
}
