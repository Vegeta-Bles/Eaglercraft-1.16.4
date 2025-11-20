package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherrackReplaceBlobsFeature extends Feature<NetherrackReplaceBlobsFeatureConfig> {
   public NetherrackReplaceBlobsFeature(Codec<NetherrackReplaceBlobsFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, NetherrackReplaceBlobsFeatureConfig _snowman) {
      Block _snowmanxxxxx = _snowman.target.getBlock();
      BlockPos _snowmanxxxxxx = method_27107(_snowman, _snowman.mutableCopy().clamp(Direction.Axis.Y, 1, _snowman.getHeight() - 1), _snowmanxxxxx);
      if (_snowmanxxxxxx == null) {
         return false;
      } else {
         int _snowmanxxxxxxx = _snowman.getRadius().getValue(_snowman);
         boolean _snowmanxxxxxxxx = false;

         for (BlockPos _snowmanxxxxxxxxx : BlockPos.iterateOutwards(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxx)) {
            if (_snowmanxxxxxxxxx.getManhattanDistance(_snowmanxxxxxx) > _snowmanxxxxxxx) {
               break;
            }

            BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxx);
            if (_snowmanxxxxxxxxxx.isOf(_snowmanxxxxx)) {
               this.setBlockState(_snowman, _snowmanxxxxxxxxx, _snowman.state);
               _snowmanxxxxxxxx = true;
            }
         }

         return _snowmanxxxxxxxx;
      }
   }

   @Nullable
   private static BlockPos method_27107(WorldAccess _snowman, BlockPos.Mutable _snowman, Block _snowman) {
      while (_snowman.getY() > 1) {
         BlockState _snowmanxxx = _snowman.getBlockState(_snowman);
         if (_snowmanxxx.isOf(_snowman)) {
            return _snowman;
         }

         _snowman.move(Direction.DOWN);
      }

      return null;
   }
}
