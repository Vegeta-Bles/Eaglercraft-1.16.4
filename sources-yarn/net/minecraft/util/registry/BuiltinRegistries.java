package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuiltinRegistries {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Identifier, Supplier<?>> DEFAULT_VALUE_SUPPLIERS = Maps.newLinkedHashMap();
   private static final MutableRegistry<MutableRegistry<?>> ROOT = new SimpleRegistry<>(
      RegistryKey.ofRegistry(new Identifier("root")), Lifecycle.experimental()
   );
   public static final Registry<? extends Registry<?>> REGISTRIES = ROOT;
   public static final Registry<ConfiguredSurfaceBuilder<?>> CONFIGURED_SURFACE_BUILDER = addRegistry(
      Registry.CONFIGURED_SURFACE_BUILDER_WORLDGEN, () -> ConfiguredSurfaceBuilders.NOPE
   );
   public static final Registry<ConfiguredCarver<?>> CONFIGURED_CARVER = addRegistry(Registry.CONFIGURED_CARVER_WORLDGEN, () -> ConfiguredCarvers.CAVE);
   public static final Registry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = addRegistry(Registry.CONFIGURED_FEATURE_WORLDGEN, () -> ConfiguredFeatures.OAK);
   public static final Registry<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURE_FEATURE = addRegistry(
      Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, () -> ConfiguredStructureFeatures.MINESHAFT
   );
   public static final Registry<StructureProcessorList> STRUCTURE_PROCESSOR_LIST = addRegistry(
      Registry.PROCESSOR_LIST_WORLDGEN, () -> StructureProcessorLists.ZOMBIE_PLAINS
   );
   public static final Registry<StructurePool> STRUCTURE_POOL = addRegistry(Registry.TEMPLATE_POOL_WORLDGEN, StructurePools::initDefaultPools);
   public static final Registry<Biome> BIOME = addRegistry(Registry.BIOME_KEY, () -> BuiltinBiomes.PLAINS);
   public static final Registry<ChunkGeneratorSettings> CHUNK_GENERATOR_SETTINGS = addRegistry(
      Registry.NOISE_SETTINGS_WORLDGEN, ChunkGeneratorSettings::getInstance
   );

   private static <T> Registry<T> addRegistry(RegistryKey<? extends Registry<T>> registryRef, Supplier<T> defaultValueSupplier) {
      return addRegistry(registryRef, Lifecycle.stable(), defaultValueSupplier);
   }

   private static <T> Registry<T> addRegistry(RegistryKey<? extends Registry<T>> registryRef, Lifecycle lifecycle, Supplier<T> defaultValueSupplier) {
      return addRegistry(registryRef, new SimpleRegistry<>(registryRef, lifecycle), defaultValueSupplier, lifecycle);
   }

   private static <T, R extends MutableRegistry<T>> R addRegistry(
      RegistryKey<? extends Registry<T>> registryRef, R registry, Supplier<T> defaultValueSupplier, Lifecycle lifecycle
   ) {
      Identifier lv = registryRef.getValue();
      DEFAULT_VALUE_SUPPLIERS.put(lv, defaultValueSupplier);
      @SuppressWarnings("unchecked")
      MutableRegistry<R> lv2 = (MutableRegistry<R>)(MutableRegistry<?>)ROOT;
      return lv2.add((RegistryKey<R>)(RegistryKey<?>)registryRef, registry, lifecycle);
   }

   public static <T> T add(Registry<? super T> registry, String id, T object) {
      return add(registry, new Identifier(id), object);
   }

   public static <V, T extends V> T add(Registry<V> registry, Identifier id, T object) {
      @SuppressWarnings("unchecked")
      MutableRegistry<V> mutable = (MutableRegistry<V>)registry;
      return mutable.add(RegistryKey.of(registry.getKey(), id), object, Lifecycle.stable());
   }

   public static <V, T extends V> T set(Registry<V> arg, int rawId, RegistryKey<V> arg2, T object) {
      @SuppressWarnings("unchecked")
      MutableRegistry<V> mutable = (MutableRegistry<V>)arg;
      return mutable.set(rawId, arg2, object, Lifecycle.stable());
   }

   public static void init() {
   }

   static {
      DEFAULT_VALUE_SUPPLIERS.forEach((arg, supplier) -> {
         if (supplier.get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", arg);
         }
      });
      Registry.validate(ROOT);
   }
}
