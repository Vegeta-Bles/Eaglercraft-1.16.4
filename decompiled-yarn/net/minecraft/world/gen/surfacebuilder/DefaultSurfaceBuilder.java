package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class DefaultSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public DefaultSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      this.generate(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.getTopMaterial(), _snowman.getUnderMaterial(), _snowman.getUnderwaterMaterial(), _snowman);
   }

   protected void generate(
      Random random,
      Chunk chunk,
      Biome biome,
      int x,
      int z,
      int height,
      double noise,
      BlockState defaultBlock,
      BlockState fluidBlock,
      BlockState topBlock,
      BlockState underBlock,
      BlockState underwaterBlock,
      int seaLevel
   ) {
      BlockState _snowman = topBlock;
      BlockState _snowmanx = underBlock;
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();
      int _snowmanxxx = -1;
      int _snowmanxxxx = (int)(noise / 3.0 + 3.0 + random.nextDouble() * 0.25);
      int _snowmanxxxxx = x & 15;
      int _snowmanxxxxxx = z & 15;

      for (int _snowmanxxxxxxx = height; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
         _snowmanxx.set(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
         BlockState _snowmanxxxxxxxx = chunk.getBlockState(_snowmanxx);
         if (_snowmanxxxxxxxx.isAir()) {
            _snowmanxxx = -1;
         } else if (_snowmanxxxxxxxx.isOf(defaultBlock.getBlock())) {
            if (_snowmanxxx == -1) {
               if (_snowmanxxxx <= 0) {
                  _snowman = Blocks.AIR.getDefaultState();
                  _snowmanx = defaultBlock;
               } else if (_snowmanxxxxxxx >= seaLevel - 4 && _snowmanxxxxxxx <= seaLevel + 1) {
                  _snowman = topBlock;
                  _snowmanx = underBlock;
               }

               if (_snowmanxxxxxxx < seaLevel && (_snowman == null || _snowman.isAir())) {
                  if (biome.getTemperature(_snowmanxx.set(x, _snowmanxxxxxxx, z)) < 0.15F) {
                     _snowman = Blocks.ICE.getDefaultState();
                  } else {
                     _snowman = fluidBlock;
                  }

                  _snowmanxx.set(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
               }

               _snowmanxxx = _snowmanxxxx;
               if (_snowmanxxxxxxx >= seaLevel - 1) {
                  chunk.setBlockState(_snowmanxx, _snowman, false);
               } else if (_snowmanxxxxxxx < seaLevel - 7 - _snowmanxxxx) {
                  _snowman = Blocks.AIR.getDefaultState();
                  _snowmanx = defaultBlock;
                  chunk.setBlockState(_snowmanxx, underwaterBlock, false);
               } else {
                  chunk.setBlockState(_snowmanxx, _snowmanx, false);
               }
            } else if (_snowmanxxx > 0) {
               _snowmanxxx--;
               chunk.setBlockState(_snowmanxx, _snowmanx, false);
               if (_snowmanxxx == 0 && _snowmanx.isOf(Blocks.SAND) && _snowmanxxxx > 1) {
                  _snowmanxxx = random.nextInt(4) + Math.max(0, _snowmanxxxxxxx - 63);
                  _snowmanx = _snowmanx.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
               }
            }
         }
      }
   }
}
