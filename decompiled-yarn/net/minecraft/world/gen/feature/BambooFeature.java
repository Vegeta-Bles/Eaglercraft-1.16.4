package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BambooFeature extends Feature<ProbabilityConfig> {
   private static final BlockState BAMBOO = Blocks.BAMBOO
      .getDefaultState()
      .with(BambooBlock.AGE, Integer.valueOf(1))
      .with(BambooBlock.LEAVES, BambooLeaves.NONE)
      .with(BambooBlock.STAGE, Integer.valueOf(0));
   private static final BlockState BAMBOO_TOP_1 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE).with(BambooBlock.STAGE, Integer.valueOf(1));
   private static final BlockState BAMBOO_TOP_2 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE);
   private static final BlockState BAMBOO_TOP_3 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.SMALL);

   public BambooFeature(Codec<ProbabilityConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, ProbabilityConfig _snowman) {
      int _snowmanxxxxx = 0;
      BlockPos.Mutable _snowmanxxxxxx = _snowman.mutableCopy();
      BlockPos.Mutable _snowmanxxxxxxx = _snowman.mutableCopy();
      if (_snowman.isAir(_snowmanxxxxxx)) {
         if (Blocks.BAMBOO.getDefaultState().canPlaceAt(_snowman, _snowmanxxxxxx)) {
            int _snowmanxxxxxxxx = _snowman.nextInt(12) + 5;
            if (_snowman.nextFloat() < _snowman.probability) {
               int _snowmanxxxxxxxxx = _snowman.nextInt(4) + 1;

               for (int _snowmanxxxxxxxxxx = _snowman.getX() - _snowmanxxxxxxxxx; _snowmanxxxxxxxxxx <= _snowman.getX() + _snowmanxxxxxxxxx; _snowmanxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxx = _snowman.getZ() - _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxx <= _snowman.getZ() + _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - _snowman.getX();
                     int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - _snowman.getZ();
                     if (_snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx <= _snowmanxxxxxxxxx * _snowmanxxxxxxxxx) {
                        _snowmanxxxxxxx.set(_snowmanxxxxxxxxxx, _snowman.getTopY(Heightmap.Type.WORLD_SURFACE, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx) - 1, _snowmanxxxxxxxxxxx);
                        if (isSoil(_snowman.getBlockState(_snowmanxxxxxxx).getBlock())) {
                           _snowman.setBlockState(_snowmanxxxxxxx, Blocks.PODZOL.getDefaultState(), 2);
                        }
                     }
                  }
               }
            }

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx && _snowman.isAir(_snowmanxxxxxx); _snowmanxxxxxxxxx++) {
               _snowman.setBlockState(_snowmanxxxxxx, BAMBOO, 2);
               _snowmanxxxxxx.move(Direction.UP, 1);
            }

            if (_snowmanxxxxxx.getY() - _snowman.getY() >= 3) {
               _snowman.setBlockState(_snowmanxxxxxx, BAMBOO_TOP_1, 2);
               _snowman.setBlockState(_snowmanxxxxxx.move(Direction.DOWN, 1), BAMBOO_TOP_2, 2);
               _snowman.setBlockState(_snowmanxxxxxx.move(Direction.DOWN, 1), BAMBOO_TOP_3, 2);
            }
         }

         _snowmanxxxxx++;
      }

      return _snowmanxxxxx > 0;
   }
}
