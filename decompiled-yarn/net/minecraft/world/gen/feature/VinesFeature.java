package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class VinesFeature extends Feature<DefaultFeatureConfig> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public VinesFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      BlockPos.Mutable _snowmanxxxxx = _snowman.mutableCopy();

      for (int _snowmanxxxxxx = 64; _snowmanxxxxxx < 256; _snowmanxxxxxx++) {
         _snowmanxxxxx.set(_snowman);
         _snowmanxxxxx.move(_snowman.nextInt(4) - _snowman.nextInt(4), 0, _snowman.nextInt(4) - _snowman.nextInt(4));
         _snowmanxxxxx.setY(_snowmanxxxxxx);
         if (_snowman.isAir(_snowmanxxxxx)) {
            for (Direction _snowmanxxxxxxx : DIRECTIONS) {
               if (_snowmanxxxxxxx != Direction.DOWN && VineBlock.shouldConnectTo(_snowman, _snowmanxxxxx, _snowmanxxxxxxx)) {
                  _snowman.setBlockState(_snowmanxxxxx, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(_snowmanxxxxxxx), Boolean.valueOf(true)), 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}
