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

   public CachingLayerSampler createSampler(LayerOperator _snowman) {
      return new CachingLayerSampler(this.cache, this.cacheCapacity, _snowman);
   }

   public CachingLayerSampler createSampler(LayerOperator _snowman, CachingLayerSampler _snowman) {
      return new CachingLayerSampler(this.cache, Math.min(1024, _snowman.getCapacity() * 4), _snowman);
   }

   public CachingLayerSampler createSampler(LayerOperator _snowman, CachingLayerSampler _snowman, CachingLayerSampler _snowman) {
      return new CachingLayerSampler(this.cache, Math.min(1024, Math.max(_snowman.getCapacity(), _snowman.getCapacity()) * 4), _snowman);
   }

   @Override
   public void initSeed(long x, long y) {
      long _snowman = this.worldSeed;
      _snowman = SeedMixer.mixSeed(_snowman, x);
      _snowman = SeedMixer.mixSeed(_snowman, y);
      _snowman = SeedMixer.mixSeed(_snowman, x);
      _snowman = SeedMixer.mixSeed(_snowman, y);
      this.localSeed = _snowman;
   }

   @Override
   public int nextInt(int bound) {
      int _snowman = (int)Math.floorMod(this.localSeed >> 24, (long)bound);
      this.localSeed = SeedMixer.mixSeed(this.localSeed, this.worldSeed);
      return _snowman;
   }

   @Override
   public PerlinNoiseSampler getNoiseSampler() {
      return this.noiseSampler;
   }

   private static long addSalt(long seed, long salt) {
      long var4 = SeedMixer.mixSeed(salt, salt);
      var4 = SeedMixer.mixSeed(var4, salt);
      var4 = SeedMixer.mixSeed(var4, salt);
      long var6 = SeedMixer.mixSeed(seed, var4);
      var6 = SeedMixer.mixSeed(var6, var4);
      return SeedMixer.mixSeed(var6, var4);
   }
}
