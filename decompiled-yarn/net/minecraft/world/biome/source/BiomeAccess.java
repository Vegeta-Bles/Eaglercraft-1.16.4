package net.minecraft.world.biome.source;

import com.google.common.hash.Hashing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class BiomeAccess {
   private final BiomeAccess.Storage storage;
   private final long seed;
   private final BiomeAccessType type;

   public BiomeAccess(BiomeAccess.Storage storage, long seed, BiomeAccessType type) {
      this.storage = storage;
      this.seed = seed;
      this.type = type;
   }

   public static long hashSeed(long seed) {
      return Hashing.sha256().hashLong(seed).asLong();
   }

   public BiomeAccess withSource(BiomeSource source) {
      return new BiomeAccess(source, this.seed, this.type);
   }

   public Biome getBiome(BlockPos pos) {
      return this.type.getBiome(this.seed, pos.getX(), pos.getY(), pos.getZ(), this.storage);
   }

   public Biome getBiome(double x, double y, double z) {
      int _snowman = MathHelper.floor(x) >> 2;
      int _snowmanx = MathHelper.floor(y) >> 2;
      int _snowmanxx = MathHelper.floor(z) >> 2;
      return this.getBiomeForNoiseGen(_snowman, _snowmanx, _snowmanxx);
   }

   public Biome method_27344(BlockPos pos) {
      int _snowman = pos.getX() >> 2;
      int _snowmanx = pos.getY() >> 2;
      int _snowmanxx = pos.getZ() >> 2;
      return this.getBiomeForNoiseGen(_snowman, _snowmanx, _snowmanxx);
   }

   public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
      return this.storage.getBiomeForNoiseGen(biomeX, biomeY, biomeZ);
   }

   public interface Storage {
      Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ);
   }
}
