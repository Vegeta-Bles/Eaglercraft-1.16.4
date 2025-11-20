package net.minecraft.world.biome.source;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.biome.Biome;

public class CheckerboardBiomeSource extends BiomeSource {
   public static final Codec<CheckerboardBiomeSource> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Biome.field_26750.fieldOf("biomes").forGetter(_snowmanx -> _snowmanx.biomeArray), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter(_snowmanx -> _snowmanx.scale)
            )
            .apply(_snowman, CheckerboardBiomeSource::new)
   );
   private final List<Supplier<Biome>> biomeArray;
   private final int gridSize;
   private final int scale;

   public CheckerboardBiomeSource(List<Supplier<Biome>> biomeArray, int size) {
      super(biomeArray.stream());
      this.biomeArray = biomeArray;
      this.gridSize = size + 2;
      this.scale = size;
   }

   @Override
   protected Codec<? extends BiomeSource> getCodec() {
      return CODEC;
   }

   @Override
   public BiomeSource withSeed(long seed) {
      return this;
   }

   @Override
   public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
      return this.biomeArray.get(Math.floorMod((biomeX >> this.gridSize) + (biomeZ >> this.gridSize), this.biomeArray.size())).get();
   }
}
