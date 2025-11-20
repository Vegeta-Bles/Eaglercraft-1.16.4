package net.minecraft.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

public abstract class AbstractNetherSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   private long seed;
   private ImmutableMap<BlockState, OctavePerlinNoiseSampler> surfaceNoises = ImmutableMap.of();
   private ImmutableMap<BlockState, OctavePerlinNoiseSampler> underLavaNoises = ImmutableMap.of();
   private OctavePerlinNoiseSampler shoreNoise;

   public AbstractNetherSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      int _snowmanxxxxxxxxxxxx = _snowman + 1;
      int _snowmanxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxx = _snowman & 15;
      int _snowmanxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      int _snowmanxxxxxxxxxxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      double _snowmanxxxxxxxxxxxxxxxxx = 0.03125;
      boolean _snowmanxxxxxxxxxxxxxxxxxx = this.shoreNoise.sample((double)_snowman * 0.03125, 109.0, (double)_snowman * 0.03125) * 75.0 + _snowman.nextDouble() > 0.0;
      BlockState _snowmanxxxxxxxxxxxxxxxxxxx = (BlockState)this.underLavaNoises
         .entrySet()
         .stream()
         .max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample((double)_snowman, (double)_snowman, (double)_snowman)))
         .get()
         .getKey();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxx = (BlockState)this.surfaceNoises
         .entrySet()
         .stream()
         .max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample((double)_snowman, (double)_snowman, (double)_snowman)))
         .get()
         .getKey();
      BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();
      BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, 128, _snowmanxxxxxxxxxxxxxx));

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx--) {
         _snowmanxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock()) && (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.isAir() || _snowmanxxxxxxxxxxxxxxxxxxxxxxxx == _snowman)) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               _snowmanxxxxxxxxxxxxxxxxxxxxx.move(Direction.UP);
               if (!_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx).isOf(_snowman.getBlock())) {
                  break;
               }

               _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, false);
            }

            _snowmanxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         }

         if ((_snowmanxxxxxxxxxxxxxxxxxxxxxx.isAir() || _snowmanxxxxxxxxxxxxxxxxxxxxxx == _snowman) && _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.isOf(_snowman.getBlock())) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx && _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx).isOf(_snowman.getBlock());
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (_snowmanxxxxxxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxx - 4 && _snowmanxxxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx + 1) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, this.getLavaShoreState(), false);
               } else {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, false);
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxx.move(Direction.DOWN);
            }
         }

         _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      }
   }

   @Override
   public void initSeed(long seed) {
      if (this.seed != seed || this.shoreNoise == null || this.surfaceNoises.isEmpty() || this.underLavaNoises.isEmpty()) {
         this.surfaceNoises = createNoisesForStates(this.getSurfaceStates(), seed);
         this.underLavaNoises = createNoisesForStates(this.getUnderLavaStates(), seed + (long)this.surfaceNoises.size());
         this.shoreNoise = new OctavePerlinNoiseSampler(
            new ChunkRandom(seed + (long)this.surfaceNoises.size() + (long)this.underLavaNoises.size()), ImmutableList.of(0)
         );
      }

      this.seed = seed;
   }

   private static ImmutableMap<BlockState, OctavePerlinNoiseSampler> createNoisesForStates(ImmutableList<BlockState> states, long seed) {
      Builder<BlockState, OctavePerlinNoiseSampler> _snowman = new Builder();
      UnmodifiableIterator var4 = states.iterator();

      while (var4.hasNext()) {
         BlockState _snowmanx = (BlockState)var4.next();
         _snowman.put(_snowmanx, new OctavePerlinNoiseSampler(new ChunkRandom(seed), ImmutableList.of(-4)));
         seed++;
      }

      return _snowman.build();
   }

   protected abstract ImmutableList<BlockState> getSurfaceStates();

   protected abstract ImmutableList<BlockState> getUnderLavaStates();

   protected abstract BlockState getLavaShoreState();
}
