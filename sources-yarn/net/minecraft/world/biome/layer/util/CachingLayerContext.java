package net.minecraft.world.biome.layer.util;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.Random;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.source.SeedMixer;

public class CachingLayerContext implements LayerSampleContext<CachingLayerSampler> {
   private final Long2IntLinkedOpenHashMap cache;
   private final int cacheCapacity;
   private final PerlinNoiseSampler noiseSampler;
   private final long worldSeed;
   private long localSeed;

   public CachingLayerContext(int cacheCapacity, long seed, long salt) {
      this.worldSeed = addSalt(seed, salt);
      this.noiseSampler = new PerlinNoiseSampler(new Random(seed));
      this.cache = new Long2IntLinkedOpenHashMap(16, 0.25F);
      this.cache.defaultReturnValue(Integer.MIN_VALUE);
      this.cacheCapacity = cacheCapacity;
   }

   public CachingLayerSampler createSampler(LayerOperator arg) {
      return new CachingLayerSampler(this.cache, this.cacheCapacity, arg);
   }

   public CachingLayerSampler createSampler(LayerOperator arg, CachingLayerSampler arg2) {
      return new CachingLayerSampler(this.cache, Math.min(1024, arg2.getCapacity() * 4), arg);
   }

   public CachingLayerSampler createSampler(LayerOperator arg, CachingLayerSampler arg2, CachingLayerSampler arg3) {
      return new CachingLayerSampler(this.cache, Math.min(1024, Math.max(arg2.getCapacity(), arg3.getCapacity()) * 4), arg);
   }

   @Override
   public void initSeed(long x, long y) {
      long n = this.worldSeed;
      n = SeedMixer.mixSeed(n, x);
      n = SeedMixer.mixSeed(n, y);
      n = SeedMixer.mixSeed(n, x);
      n = SeedMixer.mixSeed(n, y);
      this.localSeed = n;
   }

   @Override
   public int nextInt(int bound) {
      int j = (int)Math.floorMod(this.localSeed >> 24, (long)bound);
      this.localSeed = SeedMixer.mixSeed(this.localSeed, this.worldSeed);
      return j;
   }

   @Override
   public PerlinNoiseSampler getNoiseSampler() {
      return this.noiseSampler;
   }

   private static long addSalt(long seed, long salt) {
      long n = SeedMixer.mixSeed(salt, salt);
      n = SeedMixer.mixSeed(n, salt);
      n = SeedMixer.mixSeed(n, salt);
      long o = SeedMixer.mixSeed(seed, n);
      o = SeedMixer.mixSeed(o, n);
      return SeedMixer.mixSeed(o, n);
   }
}
