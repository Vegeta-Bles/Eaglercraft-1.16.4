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
      Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder = ImmutableMap.builder();
      register(builder, Registry.DIMENSION_TYPE_KEY, DimensionType.CODEC, DimensionType.CODEC);
      register(builder, Registry.BIOME_KEY, Biome.CODEC, Biome.field_26633);
      register(builder, Registry.CONFIGURED_SURFACE_BUILDER_WORLDGEN, ConfiguredSurfaceBuilder.CODEC);
      register(builder, Registry.CONFIGURED_CARVER_WORLDGEN, ConfiguredCarver.CODEC);
      register(builder, Registry.CONFIGURED_FEATURE_WORLDGEN, ConfiguredFeature.CODEC);
      register(builder, Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, ConfiguredStructureFeature.CODEC);
      register(builder, Registry.PROCESSOR_LIST_WORLDGEN, StructureProcessorType.field_25876);
      register(builder, Registry.TEMPLATE_POOL_WORLDGEN, StructurePool.CODEC);
      register(builder, Registry.NOISE_SETTINGS_WORLDGEN, ChunkGeneratorSettings.CODEC);
      return builder.build();
   });
   private static final DynamicRegistryManager.Impl BUILTIN = Util.make(
      () -> {
         DynamicRegistryManager.Impl lv = new DynamicRegistryManager.Impl();
         DimensionType.addRegistryDefaults(lv);
         INFOS.keySet()
            .stream()
            .filter(arg -> !arg.equals(Registry.DIMENSION_TYPE_KEY))
            .forEach(arg2 -> copyFromBuiltin(lv, (RegistryKey<? extends Registry<?>>)arg2));
         return lv;
      }
   );

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
      DynamicRegistryManager.Impl lv = new DynamicRegistryManager.Impl();
      RegistryOps.EntryLoader.Impl lv2 = new RegistryOps.EntryLoader.Impl();

      for (DynamicRegistryManager.Info<?> lv3 : INFOS.values()) {
         method_31141(lv, lv2, lv3);
      }

      RegistryOps.of(JsonOps.INSTANCE, lv2, lv);
      return lv;
   }

   private static <E> void method_31141(DynamicRegistryManager.Impl arg, RegistryOps.EntryLoader.Impl arg2, DynamicRegistryManager.Info<E> arg3) {
      RegistryKey<? extends Registry<E>> lv = arg3.getRegistry();
      boolean bl = !lv.equals(Registry.NOISE_SETTINGS_WORLDGEN) && !lv.equals(Registry.DIMENSION_TYPE_KEY);
      Registry<E> lv2 = BUILTIN.get(lv);
      MutableRegistry<E> lv3 = arg.get(lv);

      for (Entry<RegistryKey<E>, E> entry : lv2.getEntries()) {
         E object = entry.getValue();
         if (bl) {
            arg2.add(BUILTIN, entry.getKey(), arg3.getEntryCodec(), lv2.getRawId(object), object, lv2.getEntryLifecycle(object));
         } else {
            lv3.set(lv2.getRawId(object), entry.getKey(), object, lv2.getEntryLifecycle(object));
         }
      }
   }

   private static <R extends Registry<?>> void copyFromBuiltin(DynamicRegistryManager.Impl manager, RegistryKey<R> registryRef) {
      Registry<R> lv = (Registry<R>)BuiltinRegistries.REGISTRIES;
      Registry<?> lv2 = lv.get(registryRef);
      if (lv2 == null) {
         throw new IllegalStateException("Missing builtin registry: " + registryRef);
      } else {
         addBuiltinEntries(manager, lv2);
      }
   }

   private static <E> void addBuiltinEntries(DynamicRegistryManager.Impl manager, Registry<E> registry) {
      MutableRegistry<E> lv = manager.getOptional(registry.getKey()).orElseThrow(() -> new IllegalStateException("Missing registry: " + registry.getKey()));

      for (Entry<RegistryKey<E>, E> entry : registry.getEntries()) {
         E object = entry.getValue();
         lv.set(registry.getRawId(object), entry.getKey(), object, registry.getEntryLifecycle(object));
      }
   }

   public static void load(DynamicRegistryManager.Impl arg, RegistryOps<?> arg2) {
      for (DynamicRegistryManager.Info<?> lv : INFOS.values()) {
         load(arg2, arg, lv);
      }
   }

   private static <E> void load(RegistryOps<?> ops, DynamicRegistryManager.Impl manager, DynamicRegistryManager.Info<E> info) {
      RegistryKey<? extends Registry<E>> lv = info.getRegistry();
      SimpleRegistry<E> lv2 = Optional.ofNullable(manager.registries.get(lv))
         .map(arg -> (SimpleRegistry<E>)arg)
         .orElseThrow(() -> new IllegalStateException("Missing registry: " + lv));
      DataResult<SimpleRegistry<E>> dataResult = ops.loadToRegistry(lv2, info.getRegistry(), info.getEntryCodec());
      dataResult.error().ifPresent(partialResult -> LOGGER.error("Error loading registry data: {}", partialResult.message()));
   }

   public static final class Impl extends DynamicRegistryManager {
      public static final Codec<DynamicRegistryManager.Impl> CODEC = setupCodec();
      private final Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> registries;

      private static <E> Codec<DynamicRegistryManager.Impl> setupCodec() {
         Codec<RegistryKey<? extends Registry<E>>> codec = Identifier.CODEC.xmap(RegistryKey::ofRegistry, RegistryKey::getValue);
         Codec<SimpleRegistry<E>> codec2 = codec.partialDispatch(
            "type",
            arg -> DataResult.success(arg.getKey()),
            arg -> getDataResultForCodec(arg).map(codecx -> SimpleRegistry.createRegistryManagerCodec(arg, Lifecycle.experimental(), codecx))
         );
         UnboundedMapCodec<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> unboundedMapCodec = Codec.unboundedMap(codec, codec2);
         return fromRegistryCodecs(unboundedMapCodec);
      }

      private static <K extends RegistryKey<? extends Registry<?>>, V extends SimpleRegistry<?>> Codec<DynamicRegistryManager.Impl> fromRegistryCodecs(
         UnboundedMapCodec<K, V> unboundedMapCodec
      ) {
         return unboundedMapCodec.xmap(
            DynamicRegistryManager.Impl::new,
            arg -> (ImmutableMap)arg.registries
                  .entrySet()
                  .stream()
                  .filter(entry -> DynamicRegistryManager.INFOS.get(entry.getKey()).isSynced())
                  .collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue))
         );
      }

      @SuppressWarnings("unchecked")
      private static <E> DataResult<? extends Codec<E>> getDataResultForCodec(RegistryKey<? extends Registry<E>> registryRef) {
         DynamicRegistryManager.Info<?> info = DynamicRegistryManager.INFOS.get(registryRef);
         if (info == null) {
            return DataResult.error("Unknown or not serializable registry: " + registryRef);
         } else {
            Codec<?> codec = info.getNetworkEntryCodec();
            return codec == null
               ? DataResult.error("Unknown or not serializable registry: " + registryRef)
               : DataResult.success((Codec<E>)codec);
         }
      }

      public Impl() {
         this(DynamicRegistryManager.INFOS.keySet().stream().collect(Collectors.toMap(Function.identity(), DynamicRegistryManager.Impl::createRegistry)));
      }

      private Impl(Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> registries) {
         this.registries = registries;
      }

      @SuppressWarnings("unchecked")
      private static SimpleRegistry<?> createRegistry(RegistryKey<? extends Registry<?>> registryRef) {
         return new SimpleRegistry<>((RegistryKey<? extends Registry<Object>>)registryRef, Lifecycle.stable());
      }

      @Override
      public <E> Optional<MutableRegistry<E>> getOptional(RegistryKey<? extends Registry<E>> key) {
         return Optional.ofNullable(this.registries.get(key)).map(arg -> (MutableRegistry<E>)arg);
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
