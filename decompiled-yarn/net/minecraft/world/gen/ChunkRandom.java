package net.minecraft.world.gen;

import java.util.Random;

public class ChunkRandom extends Random {
   private int sampleCount;

   public ChunkRandom() {
   }

   public ChunkRandom(long seed) {
      super(seed);
   }

   public void consume(int count) {
      for (int _snowman = 0; _snowman < count; _snowman++) {
         this.next(1);
      }
   }

   @Override
   protected int next(int bound) {
      this.sampleCount++;
      return super.next(bound);
   }

   public long setTerrainSeed(int chunkX, int chunkZ) {
      long _snowman = (long)chunkX * 341873128712L + (long)chunkZ * 132897987541L;
      this.setSeed(_snowman);
      return _snowman;
   }

   public long setPopulationSeed(long worldSeed, int blockX, int blockZ) {
      this.setSeed(worldSeed);
      long _snowman = this.nextLong() | 1L;
      long _snowmanx = this.nextLong() | 1L;
      long _snowmanxx = (long)blockX * _snowman + (long)blockZ * _snowmanx ^ worldSeed;
      this.setSeed(_snowmanxx);
      return _snowmanxx;
   }

   public long setDecoratorSeed(long populationSeed, int index, int step) {
      long _snowman = populationSeed + (long)index + (long)(10000 * step);
      this.setSeed(_snowman);
      return _snowman;
   }

   public long setCarverSeed(long worldSeed, int chunkX, int chunkZ) {
      this.setSeed(worldSeed);
      long _snowman = this.nextLong();
      long _snowmanx = this.nextLong();
      long _snowmanxx = (long)chunkX * _snowman ^ (long)chunkZ * _snowmanx ^ worldSeed;
      this.setSeed(_snowmanxx);
      return _snowmanxx;
   }

   public long setRegionSeed(long worldSeed, int regionX, int regionZ, int salt) {
      long _snowman = (long)regionX * 341873128712L + (long)regionZ * 132897987541L + worldSeed + (long)salt;
      this.setSeed(_snowman);
      return _snowman;
   }

   public static Random getSlimeRandom(int chunkX, int chunkZ, long worldSeed, long scrambler) {
      return new Random(
         worldSeed + (long)(chunkX * chunkX * 4987142) + (long)(chunkX * 5947611) + (long)(chunkZ * chunkZ) * 4392871L + (long)(chunkZ * 389711) ^ scrambler
      );
   }
}
