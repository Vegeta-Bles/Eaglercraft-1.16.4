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
   public BlockPos locateBiome(int x, int y, int z, int radius, Predicate<Biome> _snowman, Random random) {
      return this.locateBiome(x, y, z, radius, 1, _snowman, random, false);
   }

   @Nullable
   public BlockPos locateBiome(int x, int y, int z, int radius, int _snowman, Predicate<Biome> _snowman, Random random, boolean _snowman) {
      int _snowmanxxx = x >> 2;
      int _snowmanxxxx = z >> 2;
      int _snowmanxxxxx = radius >> 2;
      int _snowmanxxxxxx = y >> 2;
      BlockPos _snowmanxxxxxxx = null;
      int _snowmanxxxxxxxx = 0;
      int _snowmanxxxxxxxxx = _snowman ? 0 : _snowmanxxxxx;
      int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx;

      while (_snowmanxxxxxxxxxx <= _snowmanxxxxx) {
         for (int _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxx += _snowman) {
            boolean _snowmanxxxxxxxxxxxx = Math.abs(_snowmanxxxxxxxxxxx) == _snowmanxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxx = -_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxx <= _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxx += _snowman) {
               if (_snowman) {
                  boolean _snowmanxxxxxxxxxxxxxx = Math.abs(_snowmanxxxxxxxxxxxxx) == _snowmanxxxxxxxxxx;
                  if (!_snowmanxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxx) {
                     continue;
                  }
               }

               int _snowmanxxxxxxxxxxxxxx = _snowmanxxx + _snowmanxxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx + _snowmanxxxxxxxxxxx;
               if (_snowman.test(this.getBiomeForNoiseGen(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxx))) {
                  if (_snowmanxxxxxxx == null || random.nextInt(_snowmanxxxxxxxx + 1) == 0) {
                     _snowmanxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxxxx << 2, y, _snowmanxxxxxxxxxxxxxxx << 2);
                     if (_snowman) {
                        return _snowmanxxxxxxx;
                     }
                  }

                  _snowmanxxxxxxxx++;
               }
            }
         }

         _snowmanxxxxxxxxxx += _snowman;
      }

      return _snowmanxxxxxxx;
   }

   public boolean hasStructureFeature(StructureFeature<?> feature) {
      return this.structureFeatures.computeIfAbsent(feature, _snowman -> this.biomes.stream().anyMatch(_snowmanxx -> _snowmanxx.getGenerationSettings().hasStructureFeature(_snowman)));
   }

   public Set<BlockState> getTopMaterials() {
      if (this.topMaterials.isEmpty()) {
         for (Biome _snowman : this.biomes) {
            this.topMaterials.add(_snowman.getGenerationSettings().getSurfaceConfig().getTopMaterial());
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
