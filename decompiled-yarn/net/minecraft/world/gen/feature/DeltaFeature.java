package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class DeltaFeature extends Feature<DeltaFeatureConfig> {
   private static final ImmutableList<Block> field_24133 = ImmutableList.of(
      Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER
   );
   private static final Direction[] DIRECTIONS = Direction.values();

   public DeltaFeature(Codec<DeltaFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DeltaFeatureConfig _snowman) {
      boolean _snowmanxxxxx = false;
      boolean _snowmanxxxxxx = _snowman.nextDouble() < 0.9;
      int _snowmanxxxxxxx = _snowmanxxxxxx ? _snowman.getRimSize().getValue(_snowman) : 0;
      int _snowmanxxxxxxxx = _snowmanxxxxxx ? _snowman.getRimSize().getValue(_snowman) : 0;
      boolean _snowmanxxxxxxxxx = _snowmanxxxxxx && _snowmanxxxxxxx != 0 && _snowmanxxxxxxxx != 0;
      int _snowmanxxxxxxxxxx = _snowman.getSize().getValue(_snowman);
      int _snowmanxxxxxxxxxxx = _snowman.getSize().getValue(_snowman);
      int _snowmanxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);

      for (BlockPos _snowmanxxxxxxxxxxxxx : BlockPos.iterateOutwards(_snowman, _snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx)) {
         if (_snowmanxxxxxxxxxxxxx.getManhattanDistance(_snowman) > _snowmanxxxxxxxxxxxx) {
            break;
         }

         if (method_27103(_snowman, _snowmanxxxxxxxxxxxxx, _snowman)) {
            if (_snowmanxxxxxxxxx) {
               _snowmanxxxxx = true;
               this.setBlockState(_snowman, _snowmanxxxxxxxxxxxxx, _snowman.getRim());
            }

            BlockPos _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.add(_snowmanxxxxxxx, 0, _snowmanxxxxxxxx);
            if (method_27103(_snowman, _snowmanxxxxxxxxxxxxxx, _snowman)) {
               _snowmanxxxxx = true;
               this.setBlockState(_snowman, _snowmanxxxxxxxxxxxxxx, _snowman.getContents());
            }
         }
      }

      return _snowmanxxxxx;
   }

   private static boolean method_27103(WorldAccess _snowman, BlockPos _snowman, DeltaFeatureConfig _snowman) {
      BlockState _snowmanxxx = _snowman.getBlockState(_snowman);
      if (_snowmanxxx.isOf(_snowman.getContents().getBlock())) {
         return false;
      } else if (field_24133.contains(_snowmanxxx.getBlock())) {
         return false;
      } else {
         for (Direction _snowmanxxxx : DIRECTIONS) {
            boolean _snowmanxxxxx = _snowman.getBlockState(_snowman.offset(_snowmanxxxx)).isAir();
            if (_snowmanxxxxx && _snowmanxxxx != Direction.UP || !_snowmanxxxxx && _snowmanxxxx == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }
}
