package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndPortalFeature extends Feature<DefaultFeatureConfig> {
   public static final BlockPos ORIGIN = BlockPos.ORIGIN;
   private final boolean open;

   public EndPortalFeature(boolean open) {
      super(DefaultFeatureConfig.CODEC);
      this.open = open;
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      for (BlockPos _snowmanxxxxx : BlockPos.iterate(new BlockPos(_snowman.getX() - 4, _snowman.getY() - 1, _snowman.getZ() - 4), new BlockPos(_snowman.getX() + 4, _snowman.getY() + 32, _snowman.getZ() + 4))) {
         boolean _snowmanxxxxxx = _snowmanxxxxx.isWithinDistance(_snowman, 2.5);
         if (_snowmanxxxxxx || _snowmanxxxxx.isWithinDistance(_snowman, 3.5)) {
            if (_snowmanxxxxx.getY() < _snowman.getY()) {
               if (_snowmanxxxxxx) {
                  this.setBlockState(_snowman, _snowmanxxxxx, Blocks.BEDROCK.getDefaultState());
               } else if (_snowmanxxxxx.getY() < _snowman.getY()) {
                  this.setBlockState(_snowman, _snowmanxxxxx, Blocks.END_STONE.getDefaultState());
               }
            } else if (_snowmanxxxxx.getY() > _snowman.getY()) {
               this.setBlockState(_snowman, _snowmanxxxxx, Blocks.AIR.getDefaultState());
            } else if (!_snowmanxxxxxx) {
               this.setBlockState(_snowman, _snowmanxxxxx, Blocks.BEDROCK.getDefaultState());
            } else if (this.open) {
               this.setBlockState(_snowman, new BlockPos(_snowmanxxxxx), Blocks.END_PORTAL.getDefaultState());
            } else {
               this.setBlockState(_snowman, new BlockPos(_snowmanxxxxx), Blocks.AIR.getDefaultState());
            }
         }
      }

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
         this.setBlockState(_snowman, _snowman.up(_snowmanxxxxxx), Blocks.BEDROCK.getDefaultState());
      }

      BlockPos _snowmanxxxxxx = _snowman.up(2);

      for (Direction _snowmanxxxxxxx : Direction.Type.HORIZONTAL) {
         this.setBlockState(_snowman, _snowmanxxxxxx.offset(_snowmanxxxxxxx), Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, _snowmanxxxxxxx));
      }

      return true;
   }
}
