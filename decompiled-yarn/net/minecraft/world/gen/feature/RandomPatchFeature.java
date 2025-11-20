package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomPatchFeature extends Feature<RandomPatchFeatureConfig> {
   public RandomPatchFeature(Codec<RandomPatchFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, RandomPatchFeatureConfig _snowman) {
      BlockState _snowmanxxxxx = _snowman.stateProvider.getBlockState(_snowman, _snowman);
      BlockPos _snowmanxxxxxx;
      if (_snowman.project) {
         _snowmanxxxxxx = _snowman.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, _snowman);
      } else {
         _snowmanxxxxxx = _snowman;
      }

      int _snowmanxxxxxxx = 0;
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowman.tries; _snowmanxxxxxxxxx++) {
         _snowmanxxxxxxxx.set(
            _snowmanxxxxxx,
            _snowman.nextInt(_snowman.spreadX + 1) - _snowman.nextInt(_snowman.spreadX + 1),
            _snowman.nextInt(_snowman.spreadY + 1) - _snowman.nextInt(_snowman.spreadY + 1),
            _snowman.nextInt(_snowman.spreadZ + 1) - _snowman.nextInt(_snowman.spreadZ + 1)
         );
         BlockPos _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.down();
         BlockState _snowmanxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxx);
         if ((_snowman.isAir(_snowmanxxxxxxxx) || _snowman.canReplace && _snowman.getBlockState(_snowmanxxxxxxxx).getMaterial().isReplaceable())
            && _snowmanxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxxx)
            && (_snowman.whitelist.isEmpty() || _snowman.whitelist.contains(_snowmanxxxxxxxxxxx.getBlock()))
            && !_snowman.blacklist.contains(_snowmanxxxxxxxxxxx)
            && (
               !_snowman.needsWater
                  || _snowman.getFluidState(_snowmanxxxxxxxxxx.west()).isIn(FluidTags.WATER)
                  || _snowman.getFluidState(_snowmanxxxxxxxxxx.east()).isIn(FluidTags.WATER)
                  || _snowman.getFluidState(_snowmanxxxxxxxxxx.north()).isIn(FluidTags.WATER)
                  || _snowman.getFluidState(_snowmanxxxxxxxxxx.south()).isIn(FluidTags.WATER)
            )) {
            _snowman.blockPlacer.generate(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx, _snowman);
            _snowmanxxxxxxx++;
         }
      }

      return _snowmanxxxxxxx > 0;
   }
}
