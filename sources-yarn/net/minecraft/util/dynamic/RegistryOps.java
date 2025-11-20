package net.minecraft.util.dynamic;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryOps<T> extends ForwardingDynamicOps<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RegistryOps.EntryLoader entryLoader;
   private final DynamicRegistryManager.Impl registryManager;
   private final Map<RegistryKey<? extends Registry<?>>, RegistryOps.ValueHolder<?>> valueHolders;
   private final RegistryOps<JsonElement> entryOps;

   public static <T> RegistryOps<T> of(DynamicOps<T> delegate, ResourceManager resourceManager, DynamicRegistryManager.Impl arg2) {
      return of(delegate, RegistryOps.EntryLoader.resourceBacked(resourceManager), arg2);
   }

   public static <T> RegistryOps<T> of(DynamicOps<T> dynamicOps, RegistryOps.EntryLoader arg, DynamicRegistryManager.Impl arg2) {
      RegistryOps<T> lv = new RegistryOps<>(dynamicOps, arg, arg2, Maps.newIdentityHashMap());
      DynamicRegistryManager.load(arg2, lv);
      return lv;
   }

   private RegistryOps(
      DynamicOps<T> delegate,
      RegistryOps.EntryLoader arg,
      DynamicRegistryManager.Impl arg2,
      IdentityHashMap<RegistryKey<? extends Registry<?>>, RegistryOps.ValueHolder<?>> identityHashMap
   ) {
      super(delegate);
      this.entryLoader = arg;
      this.registryManager = arg2;
      this.valueHolders = identityHashMap;
      if (delegate == JsonOps.INSTANCE) {
         this.entryOps = (RegistryOps<JsonElement>)this;
      } else {
         this.entryOps = new RegistryOps<>(JsonOps.INSTANCE, arg, arg2, identityHashMap);
      }
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> decodeOrId(T object, RegistryKey<? extends Registry<E>> arg, Codec<E> codec, boolean allowInlineDefinitions) {
      Optional<MutableRegistry<E>> optional = this.registryManager.getOptional(arg);
      if (!optional.isPresent()) {
         return DataResult.error("Unknown registry: " + arg);
      } else {
         MutableRegistry<E> lv = optional.get();
         DataResult<Pair<Identifier, T>> dataResult = Identifier.CODEC.decode(this.delegate, object);
         if (!dataResult.result().isPresent()) {
            return !allowInlineDefinitions
               ? DataResult.error("Inline definitions not allowed here")
               : codec.decode(this, object).map(pairx -> pairx.mapFirst(objectx -> () -> objectx));
         } else {
            Pair<Identifier, T> pair = (Pair<Identifier, T>)dataResult.result().get();
            Identifier lv2 = (Identifier)pair.getFirst();
            return this.readSupplier(arg, lv, codec, lv2).map(supplier -> Pair.of(supplier, pair.getSecond()));
         }
      }
   }

   public <E> DataResult<SimpleRegistry<E>> loadToRegistry(SimpleRegistry<E> registry, RegistryKey<? extends Registry<E>> arg2, Codec<E> codec) {
      Collection<Identifier> collection = this.entryLoader.getKnownEntryPaths(arg2);
      DataResult<SimpleRegistry<E>> dataResult = DataResult.success(registry, Lifecycle.stable());
      String string = arg2.getValue().getPath() + "/";

      for (Identifier lv : collection) {
         String string2 = lv.getPath();
         if (!string2.endsWith(".json")) {
            LOGGER.warn("Skipping resource {} since it is not a json file", lv);
         } else if (!string2.startsWith(string)) {
            LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", lv);
         } else {
            String string3 = string2.substring(string.length(), string2.length() - ".json".length());
            Identifier lv2 = new Identifier(lv.getNamespace(), string3);
            dataResult = dataResult.flatMap(arg3 -> this.readSupplier(arg2, arg3, codec, lv2).map(supplier -> arg3));
         }
      }

      return dataResult.setPartial(registry);
   }

   private <E> DataResult<Supplier<E>> readSupplier(RegistryKey<? extends Registry<E>> arg, MutableRegistry<E> arg2, Codec<E> codec, Identifier elementId) {
      RegistryKey<E> lv = RegistryKey.of(arg, elementId);
      RegistryOps.ValueHolder<E> lv2 = this.getValueHolder(arg);
      DataResult<Supplier<E>> dataResult = lv2.values.get(lv);
      if (dataResult != null) {
         return dataResult;
      } else {
         Supplier<E> supplier = Suppliers.memoize(() -> {
            E object = arg2.get(lv);
            if (object == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + lv);
            } else {
               return object;
            }
         });
         lv2.values.put(lv, DataResult.success(supplier));
         DataResult<Pair<E, OptionalInt>> dataResult2 = this.entryLoader.load(this.entryOps, arg, lv, codec);
         Optional<Pair<E, OptionalInt>> optional = dataResult2.result();
         if (optional.isPresent()) {
            Pair<E, OptionalInt> pair = optional.get();
            arg2.replace((OptionalInt)pair.getSecond(), lv, pair.getFirst(), dataResult2.lifecycle());
         }

         DataResult<Supplier<E>> dataResult3;
        if (!optional.isPresent() && arg2.get(lv) != null) {
            dataResult3 = DataResult.success((Supplier<E>)(() -> arg2.get(lv)), Lifecycle.stable());
         } else {
            dataResult3 = dataResult2.map(pair -> () -> arg2.get(lv));
         }

         lv2.values.put(lv, dataResult3);
         return dataResult3;
      }
   }

   private <E> RegistryOps.ValueHolder<E> getValueHolder(RegistryKey<? extends Registry<E>> registryRef) {
      return (RegistryOps.ValueHolder<E>)this.valueHolders.computeIfAbsent(registryRef, arg -> new RegistryOps.ValueHolder());
   }

   public <E> DataResult<Registry<E>> method_31152(RegistryKey<? extends Registry<E>> arg) {
      return this.registryManager
         .getOptional(arg)
         .map(argx -> DataResult.success((Registry<E>)argx, argx.getLifecycle()))
         .orElseGet(() -> DataResult.error("Unknown registry: " + arg));
   }

   public interface EntryLoader {
      Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> arg);

      <E> DataResult<Pair<E, OptionalInt>> load(
         DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> decoder
      );

      static RegistryOps.EntryLoader resourceBacked(final ResourceManager arg) {
         return new RegistryOps.EntryLoader() {
            @Override
            public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> argx) {
               return arg.findResources(argx.getValue().getPath(), string -> string.endsWith(".json"));
            }

            @Override
            public <E> DataResult<Pair<E, OptionalInt>> load(
               DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> decoder
            ) {
               Identifier lv = entryId.getValue();
               Identifier lv2 = new Identifier(lv.getNamespace(), registryId.getValue().getPath() + "/" + lv.getPath() + ".json");

               try (
                  Resource lv3 = arg.getResource(lv2);
                  Reader reader = new InputStreamReader(lv3.getInputStream(), StandardCharsets.UTF_8);
               ) {
                  JsonParser jsonParser = new JsonParser();
                  JsonElement jsonElement = jsonParser.parse(reader);
                  return decoder.parse(dynamicOps, jsonElement).map(object -> Pair.of(object, OptionalInt.empty()));
               } catch (JsonIOException | JsonSyntaxException | IOException var42) {
                  return DataResult.error("Failed to parse " + lv2 + " file: " + var42.getMessage());
               }
            }

            @Override
            public String toString() {
               return "ResourceAccess[" + arg + "]";
            }
         };
      }

      public static final class Impl implements RegistryOps.EntryLoader {
         private final Map<RegistryKey<?>, JsonElement> values = Maps.newIdentityHashMap();
         private final Object2IntMap<RegistryKey<?>> entryToRawId = new Object2IntOpenCustomHashMap(Util.identityHashStrategy());
         private final Map<RegistryKey<?>, Lifecycle> entryToLifecycle = Maps.newIdentityHashMap();

         public Impl() {
         }

         public <E> void add(DynamicRegistryManager.Impl arg, RegistryKey<E> arg2, Encoder<E> encoder, int rawId, E object, Lifecycle lifecycle) {
            DataResult<JsonElement> dataResult = encoder.encodeStart(RegistryReadingOps.of(JsonOps.INSTANCE, arg), object);
            Optional<PartialResult<JsonElement>> optional = dataResult.error();
            if (optional.isPresent()) {
               RegistryOps.LOGGER.error("Error adding element: {}", optional.get().message());
            } else {
               this.values.put(arg2, (JsonElement)dataResult.result().get());
               this.entryToRawId.put(arg2, rawId);
               this.entryToLifecycle.put(arg2, lifecycle);
            }
         }

         @Override
         public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> arg) {
            return this.values
               .keySet()
               .stream()
               .filter(arg2 -> arg2.isOf(arg))
               .map(arg2 -> new Identifier(arg2.getValue().getNamespace(), arg.getValue().getPath() + "/" + arg2.getValue().getPath() + ".json"))
               .collect(Collectors.toList());
         }

         @Override
         public <E> DataResult<Pair<E, OptionalInt>> load(
            DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> decoder
         ) {
            JsonElement jsonElement = this.values.get(entryId);
            return jsonElement == null
               ? DataResult.error("Unknown element: " + entryId)
               : decoder.parse(dynamicOps, jsonElement)
                  .setLifecycle(this.entryToLifecycle.get(entryId))
                  .map(object -> Pair.of(object, OptionalInt.of(this.entryToRawId.getInt(entryId))));
         }
      }
   }

   static final class ValueHolder<E> {
      private final Map<RegistryKey<E>, DataResult<Supplier<E>>> values = Maps.newIdentityHashMap();

      private ValueHolder() {
      }
   }
}
