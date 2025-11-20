package net.minecraft.world.biome.source;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.ChunkRandom;

public class TheEndBiomeSource extends BiomeSource {
   public static final Codec<TheEndBiomeSource> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(_snowmanx -> _snowmanx.biomeRegistry), Codec.LONG.fieldOf("seed").stable().forGetter(_snowmanx -> _snowmanx.seed))
            .apply(_snowman, _snowman.stable(TheEndBiomeSource::new))
   );
   private final SimplexNoiseSampler noise;
   private final Registry<Biome> biomeRegistry;
   private final long seed;
   private final Biome centerBiome;
   private final Biome highlandsBiome;
   private final Biome midlandsBiome;
   private final Biome smallIslandsBiome;
   private final Biome barrensBiome;

   public TheEndBiomeSource(Registry<Biome> biomeRegistry, long seed) {
      this(
         biomeRegistry,
         seed,
         biomeRegistry.getOrThrow(BiomeKeys.THE_END),
         biomeRegistry.getOrThrow(BiomeKeys.END_HIGHLANDS),
         biomeRegistry.getOrThrow(BiomeKeys.END_MIDLANDS),
         biomeRegistry.getOrThrow(BiomeKeys.SMALL_END_ISLANDS),
         biomeRegistry.getOrThrow(BiomeKeys.END_BARRENS)
      );
   }

   private TheEndBiomeSource(
      Registry<Biome> biomeRegistry, long seed, Biome centerBiome, Biome highlandsBiome, Biome midlandsBiome, Biome smallIslandsBiome, Biome barrensBiome
   ) {
      super(ImmutableList.of(centerBiome, highlandsBiome, midlandsBiome, smallIslandsBiome, barrensBiome));
      this.biomeRegistry = biomeRegistry;
      this.seed = seed;
      this.centerBiome = centerBiome;
      this.highlandsBiome = highlandsBiome;
      this.midlandsBiome = midlandsBiome;
      this.smallIslandsBiome = smallIslandsBiome;
      this.barrensBiome = barrensBiome;
      ChunkRandom _snowman = new ChunkRandom(seed);
      _snowman.consume(17292);
      this.noise = new SimplexNoiseSampler(_snowman);
   }

   @Override
   protected Codec<? extends BiomeSource> getCodec() {
      return CODEC;
   }

   @Override
   public BiomeSource withSeed(long seed) {
      return new TheEndBiomeSource(
         this.biomeRegistry, seed, this.centerBiome, this.highlandsBiome, this.midlandsBiome, this.smallIslandsBiome, this.barrensBiome
      );
   }

   @Override
   public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
      int _snowman = biomeX >> 2;
      int _snowmanx = biomeZ >> 2;
      if ((long)_snowman * (long)_snowman + (long)_snowmanx * (long)_snowmanx <= 4096L) {
         return this.centerBiome;
      } else {
         float _snowmanxx = getNoiseAt(this.noise, _snowman * 2 + 1, _snowmanx * 2 + 1);
         if (_snowmanxx > 40.0F) {
            return this.highlandsBiome;
         } else if (_snowmanxx >= 0.0F) {
            return this.midlandsBiome;
         } else {
            return _snowmanxx < -20.0F ? this.smallIslandsBiome : this.barrensBiome;
         }
      }
   }

   public boolean matches(long seed) {
      return this.seed == seed;
   }

   public static float getNoiseAt(SimplexNoiseSampler _snowman, int _snowman, int _snowman) {
      int _snowmanxxx = _snowman / 2;
      int _snowmanxxxx = _snowman / 2;
      int _snowmanxxxxx = _snowman % 2;
      int _snowmanxxxxxx = _snowman % 2;
      float _snowmanxxxxxxx = 100.0F - MathHelper.sqrt((float)(_snowman * _snowman + _snowman * _snowman)) * 8.0F;
      _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxxx, -100.0F, 80.0F);

      for (int _snowmanxxxxxxxx = -12; _snowmanxxxxxxxx <= 12; _snowmanxxxxxxxx++) {
         for (int _snowmanxxxxxxxxx = -12; _snowmanxxxxxxxxx <= 12; _snowmanxxxxxxxxx++) {
            long _snowmanxxxxxxxxxx = (long)(_snowmanxxx + _snowmanxxxxxxxx);
            long _snowmanxxxxxxxxxxx = (long)(_snowmanxxxx + _snowmanxxxxxxxxx);
            if (_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx > 4096L && _snowman.sample((double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx) < -0.9F) {
               float _snowmanxxxxxxxxxxxx = (MathHelper.abs((float)_snowmanxxxxxxxxxx) * 3439.0F + MathHelper.abs((float)_snowmanxxxxxxxxxxx) * 147.0F) % 13.0F + 9.0F;
               float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxx - _snowmanxxxxxxxx * 2);
               float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxx - _snowmanxxxxxxxxx * 2);
               float _snowmanxxxxxxxxxxxxxxx = 100.0F - MathHelper.sqrt(_snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxx = MathHelper.clamp(_snowmanxxxxxxxxxxxxxxx, -100.0F, 80.0F);
               _snowmanxxxxxxx = Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
            }
         }
      }

      return _snowmanxxxxxxx;
   }
}
