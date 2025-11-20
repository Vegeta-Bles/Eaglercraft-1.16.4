package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class VoidStartPlatformFeature extends Feature<DefaultFeatureConfig> {
   private static final BlockPos START_BLOCK = new BlockPos(8, 3, 8);
   private static final ChunkPos START_CHUNK = new ChunkPos(START_BLOCK);

   public VoidStartPlatformFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   private static int getDistance(int x1, int z1, int x2, int z2) {
      return Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      ChunkPos _snowmanxxxxx = new ChunkPos(_snowman);
      if (getDistance(_snowmanxxxxx.x, _snowmanxxxxx.z, START_CHUNK.x, START_CHUNK.z) > 1) {
         return true;
      } else {
         BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxxx = _snowmanxxxxx.getStartZ(); _snowmanxxxxxxx <= _snowmanxxxxx.getEndZ(); _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = _snowmanxxxxx.getStartX(); _snowmanxxxxxxxx <= _snowmanxxxxx.getEndX(); _snowmanxxxxxxxx++) {
               if (getDistance(START_BLOCK.getX(), START_BLOCK.getZ(), _snowmanxxxxxxxx, _snowmanxxxxxxx) <= 16) {
                  _snowmanxxxxxx.set(_snowmanxxxxxxxx, START_BLOCK.getY(), _snowmanxxxxxxx);
                  if (_snowmanxxxxxx.equals(START_BLOCK)) {
                     _snowman.setBlockState(_snowmanxxxxxx, Blocks.COBBLESTONE.getDefaultState(), 2);
                  } else {
                     _snowman.setBlockState(_snowmanxxxxxx, Blocks.STONE.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
