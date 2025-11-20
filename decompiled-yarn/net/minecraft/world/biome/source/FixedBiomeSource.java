package net.minecraft.world.biome.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class FixedBiomeSource extends BiomeSource {
   public static final Codec<FixedBiomeSource> CODEC = Biome.REGISTRY_CODEC.fieldOf("biome").xmap(FixedBiomeSource::new, _snowman -> _snowman.biome).stable().codec();
   private final Supplier<Biome> biome;

   public FixedBiomeSource(Biome biome) {
      this(() -> biome);
   }

   public FixedBiomeSource(Supplier<Biome> biome) {
      super(ImmutableList.of(biome.get()));
      this.biome = biome;
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
      return this.biome.get();
   }

   @Nullable
   @Override
   public BlockPos locateBiome(int x, int y, int z, int radius, int _snowman, Predicate<Biome> _snowman, Random random, boolean _snowman) {
      if (_snowman.test(this.biome.get())) {
         return _snowman ? new BlockPos(x, y, z) : new BlockPos(x - radius + random.nextInt(radius * 2 + 1), y, z - radius + random.nextInt(radius * 2 + 1));
      } else {
         return null;
      }
   }

   @Override
   public Set<Biome> getBiomesInArea(int x, int y, int z, int radius) {
      return Sets.newHashSet(new Biome[]{this.biome.get()});
   }
}
