package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndGatewayFeature extends Feature<EndGatewayFeatureConfig> {
   public EndGatewayFeature(Codec<EndGatewayFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, EndGatewayFeatureConfig _snowman) {
      for (BlockPos _snowmanxxxxx : BlockPos.iterate(_snowman.add(-1, -2, -1), _snowman.add(1, 2, 1))) {
         boolean _snowmanxxxxxx = _snowmanxxxxx.getX() == _snowman.getX();
         boolean _snowmanxxxxxxx = _snowmanxxxxx.getY() == _snowman.getY();
         boolean _snowmanxxxxxxxx = _snowmanxxxxx.getZ() == _snowman.getZ();
         boolean _snowmanxxxxxxxxx = Math.abs(_snowmanxxxxx.getY() - _snowman.getY()) == 2;
         if (_snowmanxxxxxx && _snowmanxxxxxxx && _snowmanxxxxxxxx) {
            BlockPos _snowmanxxxxxxxxxx = _snowmanxxxxx.toImmutable();
            this.setBlockState(_snowman, _snowmanxxxxxxxxxx, Blocks.END_GATEWAY.getDefaultState());
            _snowman.getExitPos().ifPresent(_snowmanxxxxxxxxxxx -> {
               BlockEntity _snowmanxxxxxxxxxxxx = _snowman.getBlockEntity(_snowman);
               if (_snowmanxxxxxxxxxxxx instanceof EndGatewayBlockEntity) {
                  EndGatewayBlockEntity _snowmanxxxxxxxxxxxxx = (EndGatewayBlockEntity)_snowmanxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxx.setExitPortalPos(_snowmanxxxxxxxxxxx, _snowman.isExact());
                  _snowmanxxxxxxxxxxxx.markDirty();
               }
            });
         } else if (_snowmanxxxxxxx) {
            this.setBlockState(_snowman, _snowmanxxxxx, Blocks.AIR.getDefaultState());
         } else if (_snowmanxxxxxxxxx && _snowmanxxxxxx && _snowmanxxxxxxxx) {
            this.setBlockState(_snowman, _snowmanxxxxx, Blocks.BEDROCK.getDefaultState());
         } else if ((_snowmanxxxxxx || _snowmanxxxxxxxx) && !_snowmanxxxxxxxxx) {
            this.setBlockState(_snowman, _snowmanxxxxx, Blocks.BEDROCK.getDefaultState());
         } else {
            this.setBlockState(_snowman, _snowmanxxxxx, Blocks.AIR.getDefaultState());
         }
      }

      return true;
   }
}
