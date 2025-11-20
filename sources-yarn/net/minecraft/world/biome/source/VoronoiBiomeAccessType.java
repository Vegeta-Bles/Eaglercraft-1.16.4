package net.minecraft.world.biome.source;

import net.minecraft.world.biome.Biome;

public enum VoronoiBiomeAccessType implements BiomeAccessType {
   INSTANCE;

   private VoronoiBiomeAccessType() {
   }

   @Override
   public Biome getBiome(long seed, int x, int y, int z, BiomeAccess.Storage storage) {
      int m = x - 2;
      int n = y - 2;
      int o = z - 2;
      int p = m >> 2;
      int q = n >> 2;
      int r = o >> 2;
      double d = (double)(m & 3) / 4.0;
      double e = (double)(n & 3) / 4.0;
      double f = (double)(o & 3) / 4.0;
      double[] ds = new double[8];

      for (int s = 0; s < 8; s++) {
         boolean bl = (s & 4) == 0;
         boolean bl2 = (s & 2) == 0;
         boolean bl3 = (s & 1) == 0;
         int t = bl ? p : p + 1;
         int u = bl2 ? q : q + 1;
         int v = bl3 ? r : r + 1;
         double g = bl ? d : d - 1.0;
         double h = bl2 ? e : e - 1.0;
         double w = bl3 ? f : f - 1.0;
         ds[s] = calcSquaredDistance(seed, t, u, v, g, h, w);
      }

      int xx = 0;
      double yx = ds[0];

      for (int zx = 1; zx < 8; zx++) {
         if (yx > ds[zx]) {
            xx = zx;
            yx = ds[zx];
         }
      }

      int aa = (xx & 4) == 0 ? p : p + 1;
      int ab = (xx & 2) == 0 ? q : q + 1;
      int ac = (xx & 1) == 0 ? r : r + 1;
      return storage.getBiomeForNoiseGen(aa, ab, ac);
   }

   private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
      long m = SeedMixer.mixSeed(seed, (long)x);
      m = SeedMixer.mixSeed(m, (long)y);
      m = SeedMixer.mixSeed(m, (long)z);
      m = SeedMixer.mixSeed(m, (long)x);
      m = SeedMixer.mixSeed(m, (long)y);
      m = SeedMixer.mixSeed(m, (long)z);
      double g = distribute(m);
      m = SeedMixer.mixSeed(m, seed);
      double h = distribute(m);
      m = SeedMixer.mixSeed(m, seed);
      double n = distribute(m);
      return square(zFraction + n) + square(yFraction + h) + square(xFraction + g);
   }

   private static double distribute(long seed) {
      double d = (double)((int)Math.floorMod(seed >> 24, 1024L)) / 1024.0;
      return (d - 0.5) * 0.9;
   }

   private static double square(double d) {
      return d * d;
   }
}
