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

   public AbstractNetherSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   public void generate(
      Random random, Chunk arg, Biome arg2, int i, int j, int k, double d, BlockState arg3, BlockState arg4, int l, long m, TernarySurfaceConfig arg5
   ) {
      int n = l + 1;
      int o = i & 15;
      int p = j & 15;
      int q = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
      int r = (int)(d / 3.0 + 3.0 + random.nextDouble() * 0.25);
      double e = 0.03125;
      boolean bl = this.shoreNoise.sample((double)i * 0.03125, 109.0, (double)j * 0.03125) * 75.0 + random.nextDouble() > 0.0;
      BlockState lv = (BlockState)this.underLavaNoises
         .entrySet()
         .stream()
         .max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample((double)i, (double)l, (double)j)))
         .get()
         .getKey();
      BlockState lv2 = (BlockState)this.surfaceNoises
         .entrySet()
         .stream()
         .max(Comparator.comparing(entry -> ((OctavePerlinNoiseSampler)entry.getValue()).sample((double)i, (double)l, (double)j)))
         .get()
         .getKey();
      BlockPos.Mutable lv3 = new BlockPos.Mutable();
      BlockState lv4 = arg.getBlockState(lv3.set(o, 128, p));

      for (int s = 127; s >= 0; s--) {
         lv3.set(o, s, p);
         BlockState lv5 = arg.getBlockState(lv3);
         if (lv4.isOf(arg3.getBlock()) && (lv5.isAir() || lv5 == arg4)) {
            for (int t = 0; t < q; t++) {
               lv3.move(Direction.UP);
               if (!arg.getBlockState(lv3).isOf(arg3.getBlock())) {
                  break;
               }

               arg.setBlockState(lv3, lv, false);
            }

            lv3.set(o, s, p);
         }

         if ((lv4.isAir() || lv4 == arg4) && lv5.isOf(arg3.getBlock())) {
            for (int u = 0; u < r && arg.getBlockState(lv3).isOf(arg3.getBlock()); u++) {
               if (bl && s >= n - 4 && s <= n + 1) {
                  arg.setBlockState(lv3, this.getLavaShoreState(), false);
               } else {
                  arg.setBlockState(lv3, lv2, false);
               }

               lv3.move(Direction.DOWN);
            }
         }

         lv4 = lv5;
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
      Builder<BlockState, OctavePerlinNoiseSampler> builder = new Builder();
      UnmodifiableIterator var4 = states.iterator();

      while (var4.hasNext()) {
         BlockState lv = (BlockState)var4.next();
         builder.put(lv, new OctavePerlinNoiseSampler(new ChunkRandom(seed), ImmutableList.of(-4)));
         seed++;
      }

      return builder.build();
   }

   protected abstract ImmutableList<BlockState> getSurfaceStates();

   protected abstract ImmutableList<BlockState> getUnderLavaStates();

   protected abstract BlockState getLavaShoreState();
}
