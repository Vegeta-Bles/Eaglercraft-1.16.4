package net.minecraft.world.biome.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class BiomeSource implements BiomeAccess.Storage {
   public static final Codec<BiomeSource> CODEC = Registry.BIOME_SOURCE.dispatchStable(BiomeSource::getCodec, Function.identity());
   protected final Map<StructureFeature<?>, Boolean> structureFeatures = Maps.newHashMap();
   protected final Set<BlockState> topMaterials = Sets.newHashSet();
   protected final List<Biome> biomes;

   protected BiomeSource(Stream<Supplier<Biome>> _snowman) {
      this(_snowman.map(Supplier::get).collect(ImmutableList.toImmutableList()));
   }

   protected BiomeSource(List<Biome> biomes) {
      this.biomes = biomes;
   }

   protected abstract Codec<? extends BiomeSource> getCodec();

   public abstract BiomeSource withSeed(long seed);

   public List<Biome> getBiomes() {
      return this.biomes;
   }

   public Set<Biome> getBiomesInArea(int x, int y, int z, int radius) {
      int _snowman = x - radius >> 2;
      int _snowmanx = y - radius >> 2;
      int _snowmanxx = z - radius >> 2;
      int _snowmanxxx = x + radius >> 2;
      int _snowmanxxxx = y + radius >> 2;
      int _snowmanxxxxx = z + radius >> 2;
      int _snowmanxxxxxx = _snowmanxxx - _snowman + 1;
      int _snowmanxxxxxxx = _snowmanxxxx - _snowmanx + 1;
      int _snowmanxxxxxxxx = _snowmanxxxxx - _snowmanxx + 1;
      Set<Biome> _snowmanxxxxxxxxx = Sets.newHashSet();

      for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxx = _snowman + _snowmanxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxx = _snowmanx + _snowmanxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxx + _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxx.add(this.getBiomeForNoiseGen(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
            }
         }
      }

      return _snowmanxxxxxxxxx;
   }

   @Nullable
   public BlockPos locateBiome(int x, int y, int z, int radius, Predicate<Biome> predicate, Random random) {
      return this.locateBiome(x, y, z, radius, 1, predicate, random, false);
   }

   @Nullable
    public BlockPos locateBiome(int x, int y, int z, int radius, int step, Predicate<Biome> predicate, Random random, boolean requireFullArea) {
      int centerX = x >> 2;
      int centerZ = z >> 2;
      int searchRadius = radius >> 2;
      int scaledY = y >> 2;
      BlockPos foundPos = null;
      int matches = 0;
      int start = requireFullArea ? 0 : searchRadius;
      int currentRadius = start;

      while (currentRadius <= searchRadius) {
         for (int offsetX = -currentRadius; offsetX <= currentRadius; offsetX += step) {
            boolean onEdgeX = Math.abs(offsetX) == currentRadius;

            for (int offsetZ = -currentRadius; offsetZ <= currentRadius; offsetZ += step) {
               if (requireFullArea) {
                  boolean onEdgeZ = Math.abs(offsetZ) == currentRadius;
                  if (!onEdgeZ && !onEdgeX) {
                     continue;
                  }
               }

               int biomeX = centerX + offsetZ;
               int biomeZ = centerZ + offsetX;
               if (predicate.test(this.getBiomeForNoiseGen(biomeX, scaledY, biomeZ))) {
                  if (foundPos == null || random.nextInt(matches + 1) == 0) {
                     foundPos = new BlockPos(biomeX << 2, y, biomeZ << 2);
                     if (requireFullArea) {
                        return foundPos;
                     }
                  }

                  matches++;
               }
            }
         }

         currentRadius += step;
      }

      return foundPos;
   }

   public boolean hasStructureFeature(StructureFeature<?> feature) {
      return this.structureFeatures.computeIfAbsent(feature, structure -> this.biomes.stream().anyMatch(biome -> biome.getGenerationSettings().hasStructureFeature(structure)));
   }

   public Set<BlockState> getTopMaterials() {
      if (this.topMaterials.isEmpty()) {
         for (Biome biome : this.biomes) {
            this.topMaterials.add(biome.getGenerationSettings().getSurfaceConfig().getTopMaterial());
         }
      }

      return this.topMaterials;
   }

   static {
      Registry.register(Registry.BIOME_SOURCE, "fixed", FixedBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "multi_noise", MultiNoiseBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "checkerboard", CheckerboardBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "vanilla_layered", VanillaLayeredBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "the_end", TheEndBiomeSource.CODEC);
   }
}
