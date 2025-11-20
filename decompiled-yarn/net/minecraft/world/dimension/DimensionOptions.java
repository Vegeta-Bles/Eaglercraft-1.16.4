package net.minecraft.world.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public final class DimensionOptions {
   public static final Codec<DimensionOptions> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               DimensionType.REGISTRY_CODEC.fieldOf("type").forGetter(DimensionOptions::getDimensionTypeSupplier),
               ChunkGenerator.CODEC.fieldOf("generator").forGetter(DimensionOptions::getChunkGenerator)
            )
            .apply(_snowman, _snowman.stable(DimensionOptions::new))
   );
   public static final RegistryKey<DimensionOptions> OVERWORLD = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("overworld"));
   public static final RegistryKey<DimensionOptions> NETHER = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("the_nether"));
   public static final RegistryKey<DimensionOptions> END = RegistryKey.of(Registry.DIMENSION_OPTIONS, new Identifier("the_end"));
   private static final LinkedHashSet<RegistryKey<DimensionOptions>> BASE_DIMENSIONS = Sets.newLinkedHashSet(ImmutableList.of(OVERWORLD, NETHER, END));
   private final Supplier<DimensionType> dimensionTypeSupplier;
   private final ChunkGenerator chunkGenerator;

   public DimensionOptions(Supplier<DimensionType> typeSupplier, ChunkGenerator _snowman) {
      this.dimensionTypeSupplier = typeSupplier;
      this.chunkGenerator = _snowman;
   }

   public Supplier<DimensionType> getDimensionTypeSupplier() {
      return this.dimensionTypeSupplier;
   }

   public DimensionType getDimensionType() {
      return this.dimensionTypeSupplier.get();
   }

   public ChunkGenerator getChunkGenerator() {
      return this.chunkGenerator;
   }

   public static SimpleRegistry<DimensionOptions> method_29569(SimpleRegistry<DimensionOptions> _snowman) {
      SimpleRegistry<DimensionOptions> _snowmanx = new SimpleRegistry<>(Registry.DIMENSION_OPTIONS, Lifecycle.experimental());

      for (RegistryKey<DimensionOptions> _snowmanxx : BASE_DIMENSIONS) {
         DimensionOptions _snowmanxxx = _snowman.get(_snowmanxx);
         if (_snowmanxxx != null) {
            _snowmanx.add(_snowmanxx, _snowmanxxx, _snowman.getEntryLifecycle(_snowmanxxx));
         }
      }

      for (Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxx : _snowman.getEntries()) {
         RegistryKey<DimensionOptions> _snowmanxxxx = _snowmanxxx.getKey();
         if (!BASE_DIMENSIONS.contains(_snowmanxxxx)) {
            _snowmanx.add(_snowmanxxxx, _snowmanxxx.getValue(), _snowman.getEntryLifecycle(_snowmanxxx.getValue()));
         }
      }

      return _snowmanx;
   }

   public static boolean method_29567(long seed, SimpleRegistry<DimensionOptions> _snowman) {
      List<Entry<RegistryKey<DimensionOptions>, DimensionOptions>> _snowmanx = Lists.newArrayList(_snowman.getEntries());
      if (_snowmanx.size() != BASE_DIMENSIONS.size()) {
         return false;
      } else {
         Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxx = _snowmanx.get(0);
         Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxx = _snowmanx.get(1);
         Entry<RegistryKey<DimensionOptions>, DimensionOptions> _snowmanxxxx = _snowmanx.get(2);
         if (_snowmanxx.getKey() != OVERWORLD || _snowmanxxx.getKey() != NETHER || _snowmanxxxx.getKey() != END) {
            return false;
         } else if (!_snowmanxx.getValue().getDimensionType().equals(DimensionType.OVERWORLD) && _snowmanxx.getValue().getDimensionType() != DimensionType.OVERWORLD_CAVES) {
            return false;
         } else if (!_snowmanxxx.getValue().getDimensionType().equals(DimensionType.THE_NETHER)) {
            return false;
         } else if (!_snowmanxxxx.getValue().getDimensionType().equals(DimensionType.THE_END)) {
            return false;
         } else if (_snowmanxxx.getValue().getChunkGenerator() instanceof NoiseChunkGenerator && _snowmanxxxx.getValue().getChunkGenerator() instanceof NoiseChunkGenerator) {
            NoiseChunkGenerator _snowmanxxxxx = (NoiseChunkGenerator)_snowmanxxx.getValue().getChunkGenerator();
            NoiseChunkGenerator _snowmanxxxxxx = (NoiseChunkGenerator)_snowmanxxxx.getValue().getChunkGenerator();
            if (!_snowmanxxxxx.matchesSettings(seed, ChunkGeneratorSettings.NETHER)) {
               return false;
            } else if (!_snowmanxxxxxx.matchesSettings(seed, ChunkGeneratorSettings.END)) {
               return false;
            } else if (!(_snowmanxxxxx.getBiomeSource() instanceof MultiNoiseBiomeSource)) {
               return false;
            } else {
               MultiNoiseBiomeSource _snowmanxxxxxxx = (MultiNoiseBiomeSource)_snowmanxxxxx.getBiomeSource();
               if (!_snowmanxxxxxxx.matchesInstance(seed)) {
                  return false;
               } else if (!(_snowmanxxxxxx.getBiomeSource() instanceof TheEndBiomeSource)) {
                  return false;
               } else {
                  TheEndBiomeSource _snowmanxxxxxxxx = (TheEndBiomeSource)_snowmanxxxxxx.getBiomeSource();
                  return _snowmanxxxxxxxx.matches(seed);
               }
            }
         } else {
            return false;
         }
      }
   }
}
