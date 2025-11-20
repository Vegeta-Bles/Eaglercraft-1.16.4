package net.minecraft.util.registry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DynamicRegistryManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> INFOS = Util.make(() -> {
      Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> _snowman = ImmutableMap.builder();
      register(_snowman, Registry.DIMENSION_TYPE_KEY, DimensionType.CODEC, DimensionType.CODEC);
      register(_snowman, Registry.BIOME_KEY, Biome.CODEC, Biome.field_26633);
      register(_snowman, Registry.CONFIGURED_SURFACE_BUILDER_WORLDGEN, ConfiguredSurfaceBuilder.CODEC);
      register(_snowman, Registry.CONFIGURED_CARVER_WORLDGEN, ConfiguredCarver.CODEC);
      register(_snowman, Registry.CONFIGURED_FEATURE_WORLDGEN, ConfiguredFeature.CODEC);
      register(_snowman, Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, ConfiguredStructureFeature.CODEC);
      register(_snowman, Registry.PROCESSOR_LIST_WORLDGEN, StructureProcessorType.field_25876);
      register(_snowman, Registry.TEMPLATE_POOL_WORLDGEN, StructurePool.CODEC);
      register(_snowman, Registry.NOISE_SETTINGS_WORLDGEN, ChunkGeneratorSettings.CODEC);
      return _snowman.build();
   });
   private static final DynamicRegistryManager.Impl BUILTIN = Util.make(() -> {
      DynamicRegistryManager.Impl _snowman = new DynamicRegistryManager.Impl();
      DimensionType.addRegistryDefaults(_snowman);
      INFOS.keySet().stream().filter(_snowmanx -> !_snowmanx.equals(Registry.DIMENSION_TYPE_KEY)).forEach(_snowmanx -> copyFromBuiltin(_snowman, (RegistryKey<? extends Registry<?>>)_snowmanx));
      return _snowman;
   });

   public DynamicRegistryManager() {
   }

   public abstract <E> Optional<MutableRegistry<E>> getOptional(RegistryKey<? extends Registry<E>> key);

   public <E> MutableRegistry<E> get(RegistryKey<? extends Registry<E>> key) {
      return this.getOptional(key).orElseThrow(() -> new IllegalStateException("Missing registry: " + key));
   }

   public Registry<DimensionType> getDimensionTypes() {
      return this.get(Registry.DIMENSION_TYPE_KEY);
   }

   private static <E> void register(
      Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> infosBuilder,
      RegistryKey<? extends Registry<E>> registryRef,
      Codec<E> entryCodec
   ) {
      infosBuilder.put(registryRef, new DynamicRegistryManager.Info<>(registryRef, entryCodec, null));
   }

   private static <E> void register(
      Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> infosBuilder,
      RegistryKey<? extends Registry<E>> registryRef,
      Codec<E> entryCodec,
      Codec<E> networkEntryCodec
   ) {
      infosBuilder.put(registryRef, new DynamicRegistryManager.Info<>(registryRef, entryCodec, networkEntryCodec));
   }

   public static DynamicRegistryManager.Impl create() {
      DynamicRegistryManager.Impl _snowman = new DynamicRegistryManager.Impl();
      RegistryOps.EntryLoader.Impl _snowmanx = new RegistryOps.EntryLoader.Impl();

      for (DynamicRegistryManager.Info<?> _snowmanxx : INFOS.values()) {
         method_31141(_snowman, _snowmanx, _snowmanxx);
      }

      RegistryOps.of(JsonOps.INSTANCE, _snowmanx, _snowman);
      return _snowman;
   }

   private static <E> void method_31141(DynamicRegistryManager.Impl _snowman, RegistryOps.EntryLoader.Impl _snowman, DynamicRegistryManager.Info<E> _snowman) {
      RegistryKey<? extends Registry<E>> _snowmanxxx = _snowman.getRegistry();
      boolean _snowmanxxxx = !_snowmanxxx.equals(Registry.NOISE_SETTINGS_WORLDGEN) && !_snowmanxxx.equals(Registry.DIMENSION_TYPE_KEY);
      Registry<E> _snowmanxxxxx = BUILTIN.get(_snowmanxxx);
      MutableRegistry<E> _snowmanxxxxxx = _snowman.get(_snowmanxxx);

      for (Entry<RegistryKey<E>, E> _snowmanxxxxxxx : _snowmanxxxxx.getEntries()) {
         E _snowmanxxxxxxxx = _snowmanxxxxxxx.getValue();
         if (_snowmanxxxx) {
            _snowman.add(BUILTIN, _snowmanxxxxxxx.getKey(), _snowman.getEntryCodec(), _snowmanxxxxx.getRawId(_snowmanxxxxxxxx), _snowmanxxxxxxxx, _snowmanxxxxx.getEntryLifecycle(_snowmanxxxxxxxx));
         } else {
            _snowmanxxxxxx.set(_snowmanxxxxx.getRawId(_snowmanxxxxxxxx), _snowmanxxxxxxx.getKey(), _snowmanxxxxxxxx, _snowmanxxxxx.getEntryLifecycle(_snowmanxxxxxxxx));
         }
      }
   }

   private static <R extends Registry<?>> void copyFromBuiltin(DynamicRegistryManager.Impl manager, RegistryKey<R> registryRef) {
      Registry<R> _snowman = (Registry<R>)BuiltinRegistries.REGISTRIES;
      Registry<?> _snowmanx = _snowman.get(registryRef);
      if (_snowmanx == null) {
         throw new IllegalStateException("Missing builtin registry: " + registryRef);
      } else {
         addBuiltinEntries(manager, _snowmanx);
      }
   }

   private static <E> void addBuiltinEntries(DynamicRegistryManager.Impl manager, Registry<E> registry) {
      MutableRegistry<E> _snowman = manager.getOptional(registry.getKey()).orElseThrow(() -> new IllegalStateException("Missing registry: " + registry.getKey()));

      for (Entry<RegistryKey<E>, E> _snowmanx : registry.getEntries()) {
         E _snowmanxx = _snowmanx.getValue();
         _snowman.set(registry.getRawId(_snowmanxx), _snowmanx.getKey(), _snowmanxx, registry.getEntryLifecycle(_snowmanxx));
      }
   }

   public static void load(DynamicRegistryManager.Impl _snowman, RegistryOps<?> _snowman) {
      for (DynamicRegistryManager.Info<?> _snowmanxx : INFOS.values()) {
         load(_snowman, _snowman, _snowmanxx);
      }
   }

   private static <E> void load(RegistryOps<?> ops, DynamicRegistryManager.Impl manager, DynamicRegistryManager.Info<E> info) {
      RegistryKey<? extends Registry<E>> _snowman = info.getRegistry();
      SimpleRegistry<E> _snowmanx = Optional.ofNullable(manager.registries.get(_snowman))
         .map(_snowmanxx -> (SimpleRegistry<E>)_snowmanxx)
         .orElseThrow(() -> new IllegalStateException("Missing registry: " + _snowman));
      DataResult<SimpleRegistry<E>> _snowmanxx = ops.loadToRegistry(_snowmanx, info.getRegistry(), info.getEntryCodec());
      _snowmanxx.error().ifPresent(_snowmanxxx -> LOGGER.error("Error loading registry data: {}", _snowmanxxx.message()));
   }

   public static final class Impl extends DynamicRegistryManager {
      public static final Codec<DynamicRegistryManager.Impl> CODEC = setupCodec();
      private final Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> registries;

      private static <E> Codec<DynamicRegistryManager.Impl> setupCodec() {
         Codec<RegistryKey<? extends Registry<E>>> _snowman = Identifier.CODEC.xmap(RegistryKey::ofRegistry, RegistryKey::getValue);
         Codec<SimpleRegistry<E>> _snowmanx = _snowman.partialDispatch(
            "type",
            _snowmanxx -> DataResult.success(_snowmanxx.getKey()),
            _snowmanxx -> getDataResultForCodec(_snowmanxx).map(_snowmanxxx -> SimpleRegistry.createRegistryManagerCodec(_snowman, Lifecycle.experimental(), _snowmanxxx))
         );
         UnboundedMapCodec<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> _snowmanxx = Codec.unboundedMap(_snowman, _snowmanx);
         return fromRegistryCodecs(_snowmanxx);
      }

      private static <K extends RegistryKey<? extends Registry<?>>, V extends SimpleRegistry<?>> Codec<DynamicRegistryManager.Impl> fromRegistryCodecs(
         UnboundedMapCodec<K, V> _snowman
      ) {
         return _snowman.xmap(
            DynamicRegistryManager.Impl::new,
            _snowmanx -> (ImmutableMap)_snowmanx.registries
                  .entrySet()
                  .stream()
                  .filter(_snowmanxx -> DynamicRegistryManager.INFOS.get(_snowmanxx.getKey()).isSynced())
                  .collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue))
         );
      }

      private static <E> DataResult<? extends Codec<E>> getDataResultForCodec(RegistryKey<? extends Registry<E>> registryRef) {
         return Optional.ofNullable(DynamicRegistryManager.INFOS.get(registryRef))
            .map(_snowman -> _snowman.getNetworkEntryCodec())
            .<DataResult<? extends Codec<E>>>map(DataResult::success)
            .orElseGet(() -> DataResult.error("Unknown or not serializable registry: " + registryRef));
      }

      public Impl() {
         this(DynamicRegistryManager.INFOS.keySet().stream().collect(Collectors.toMap(Function.identity(), DynamicRegistryManager.Impl::createRegistry)));
      }

      private Impl(Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> registries) {
         this.registries = registries;
      }

      private static <E> SimpleRegistry<?> createRegistry(RegistryKey<? extends Registry<?>> registryRef) {
         return new SimpleRegistry<>(registryRef, Lifecycle.stable());
      }

      @Override
      public <E> Optional<MutableRegistry<E>> getOptional(RegistryKey<? extends Registry<E>> key) {
         return Optional.ofNullable(this.registries.get(key)).map(_snowman -> (MutableRegistry<E>)_snowman);
      }
   }

   static final class Info<E> {
      private final RegistryKey<? extends Registry<E>> registry;
      private final Codec<E> entryCodec;
      @Nullable
      private final Codec<E> networkEntryCodec;

      public Info(RegistryKey<? extends Registry<E>> registry, Codec<E> entryCodec, @Nullable Codec<E> networkEntryCodec) {
         this.registry = registry;
         this.entryCodec = entryCodec;
         this.networkEntryCodec = networkEntryCodec;
      }

      public RegistryKey<? extends Registry<E>> getRegistry() {
         return this.registry;
      }

      public Codec<E> getEntryCodec() {
         return this.entryCodec;
      }

      @Nullable
      public Codec<E> getNetworkEntryCodec() {
         return this.networkEntryCodec;
      }

      public boolean isSynced() {
         return this.networkEntryCodec != null;
      }
   }
}
