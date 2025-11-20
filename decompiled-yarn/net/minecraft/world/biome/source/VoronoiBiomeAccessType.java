package net.minecraft.world.biome.source;

import net.minecraft.world.biome.Biome;

public enum VoronoiBiomeAccessType implements BiomeAccessType {
   INSTANCE;

   private VoronoiBiomeAccessType() {
   }

   @Override
   public Biome getBiome(long seed, int x, int y, int z, BiomeAccess.Storage storage) {
      int _snowman = x - 2;
      int _snowmanx = y - 2;
      int _snowmanxx = z - 2;
      int _snowmanxxx = _snowman >> 2;
      int _snowmanxxxx = _snowmanx >> 2;
      int _snowmanxxxxx = _snowmanxx >> 2;
      double _snowmanxxxxxx = (double)(_snowman & 3) / 4.0;
      double _snowmanxxxxxxx = (double)(_snowmanx & 3) / 4.0;
      double _snowmanxxxxxxxx = (double)(_snowmanxx & 3) / 4.0;
      double[] _snowmanxxxxxxxxx = new double[8];

      for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 8; _snowmanxxxxxxxxxx++) {
         boolean _snowmanxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 4) == 0;
         boolean _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 2) == 0;
         boolean _snowmanxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 1) == 0;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? _snowmanxxx : _snowmanxxx + 1;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx ? _snowmanxxxx : _snowmanxxxx + 1;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? _snowmanxxxxx : _snowmanxxxxx + 1;
         double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? _snowmanxxxxxx : _snowmanxxxxxx - 1.0;
         double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxxx - 1.0;
         double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? _snowmanxxxxxxxx : _snowmanxxxxxxxx - 1.0;
         _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx] = calcSquaredDistance(
            seed, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx
         );
      }

      int _snowmanxxxxxxxxxx = 0;
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx[0];

      for (int _snowmanxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxx++) {
         if (_snowmanxxxxxxxxxxx > _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxx]) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxx];
         }
      }

      int _snowmanxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 4) == 0 ? _snowmanxxx : _snowmanxxx + 1;
      int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 2) == 0 ? _snowmanxxxx : _snowmanxxxx + 1;
      int _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 1) == 0 ? _snowmanxxxxx : _snowmanxxxxx + 1;
      return storage.getBiomeForNoiseGen(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
   }

   private static double calcSquaredDistance(long seed, int x, int y, int z, double xFraction, double yFraction, double zFraction) {
      long var11 = SeedMixer.mixSeed(seed, (long)x);
      var11 = SeedMixer.mixSeed(var11, (long)y);
      var11 = SeedMixer.mixSeed(var11, (long)z);
      var11 = SeedMixer.mixSeed(var11, (long)x);
      var11 = SeedMixer.mixSeed(var11, (long)y);
      var11 = SeedMixer.mixSeed(var11, (long)z);
      double _snowman = distribute(var11);
      var11 = SeedMixer.mixSeed(var11, seed);
      double _snowmanx = distribute(var11);
      var11 = SeedMixer.mixSeed(var11, seed);
      double _snowmanxx = distribute(var11);
      return square(zFraction + _snowmanxx) + square(yFraction + _snowmanx) + square(xFraction + _snowman);
   }

   private static double distribute(long seed) {
      double _snowman = (double)((int)Math.floorMod(seed >> 24, 1024L)) / 1024.0;
      return (_snowman - 0.5) * 0.9;
   }

   private static double square(double d) {
      return d * d;
   }
}
