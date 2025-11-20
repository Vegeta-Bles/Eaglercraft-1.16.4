package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class FreezeTopLayerFeature extends Feature<DefaultFeatureConfig> {
   public FreezeTopLayerFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
            int _snowmanxxxxxxxxx = _snowman.getX() + _snowmanxxxxxxx;
            int _snowmanxxxxxxxxxx = _snowman.getZ() + _snowmanxxxxxxxx;
            int _snowmanxxxxxxxxxxx = _snowman.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            _snowmanxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx);
            _snowmanxxxxxx.set(_snowmanxxxxx).move(Direction.DOWN, 1);
            Biome _snowmanxxxxxxxxxxxx = _snowman.getBiome(_snowmanxxxxx);
            if (_snowmanxxxxxxxxxxxx.canSetIce(_snowman, _snowmanxxxxxx, false)) {
               _snowman.setBlockState(_snowmanxxxxxx, Blocks.ICE.getDefaultState(), 2);
            }

            if (_snowmanxxxxxxxxxxxx.canSetSnow(_snowman, _snowmanxxxxx)) {
               _snowman.setBlockState(_snowmanxxxxx, Blocks.SNOW.getDefaultState(), 2);
               BlockState _snowmanxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxx);
               if (_snowmanxxxxxxxxxxxxx.contains(SnowyBlock.SNOWY)) {
                  _snowman.setBlockState(_snowmanxxxxxx, _snowmanxxxxxxxxxxxxx.with(SnowyBlock.SNOWY, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
