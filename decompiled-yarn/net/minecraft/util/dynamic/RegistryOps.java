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

   public static <T> RegistryOps<T> of(DynamicOps<T> delegate, ResourceManager resourceManager, DynamicRegistryManager.Impl _snowman) {
      return of(delegate, RegistryOps.EntryLoader.resourceBacked(resourceManager), _snowman);
   }

   public static <T> RegistryOps<T> of(DynamicOps<T> _snowman, RegistryOps.EntryLoader _snowman, DynamicRegistryManager.Impl _snowman) {
      RegistryOps<T> _snowmanxxx = new RegistryOps<>(_snowman, _snowman, _snowman, Maps.newIdentityHashMap());
      DynamicRegistryManager.load(_snowman, _snowmanxxx);
      return _snowmanxxx;
   }

   private RegistryOps(
      DynamicOps<T> delegate,
      RegistryOps.EntryLoader _snowman,
      DynamicRegistryManager.Impl _snowman,
      IdentityHashMap<RegistryKey<? extends Registry<?>>, RegistryOps.ValueHolder<?>> _snowman
   ) {
      super(delegate);
      this.entryLoader = _snowman;
      this.registryManager = _snowman;
      this.valueHolders = _snowman;
      this.entryOps = delegate == JsonOps.INSTANCE ? this : new RegistryOps<>(JsonOps.INSTANCE, _snowman, _snowman, _snowman);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> decodeOrId(T _snowman, RegistryKey<? extends Registry<E>> _snowman, Codec<E> _snowman, boolean allowInlineDefinitions) {
      Optional<MutableRegistry<E>> _snowmanxxx = this.registryManager.getOptional(_snowman);
      if (!_snowmanxxx.isPresent()) {
         return DataResult.error("Unknown registry: " + _snowman);
      } else {
         MutableRegistry<E> _snowmanxxxx = _snowmanxxx.get();
         DataResult<Pair<Identifier, T>> _snowmanxxxxx = Identifier.CODEC.decode(this.delegate, _snowman);
         if (!_snowmanxxxxx.result().isPresent()) {
            return !allowInlineDefinitions
               ? DataResult.error("Inline definitions not allowed here")
               : _snowman.decode(this, _snowman).map(_snowmanxxxxxx -> _snowmanxxxxxx.mapFirst(_snowmanxxxxxxx -> () -> _snowmanxxxx));
         } else {
            Pair<Identifier, T> _snowmanxxxxxx = (Pair<Identifier, T>)_snowmanxxxxx.result().get();
            Identifier _snowmanxxxxxxx = (Identifier)_snowmanxxxxxx.getFirst();
            return this.readSupplier(_snowman, _snowmanxxxx, _snowman, _snowmanxxxxxxx).map(_snowmanxxxxxxxx -> Pair.of(_snowmanxxxxxxxx, _snowman.getSecond()));
         }
      }
   }

   public <E> DataResult<SimpleRegistry<E>> loadToRegistry(SimpleRegistry<E> registry, RegistryKey<? extends Registry<E>> _snowman, Codec<E> _snowman) {
      Collection<Identifier> _snowmanxx = this.entryLoader.getKnownEntryPaths(_snowman);
      DataResult<SimpleRegistry<E>> _snowmanxxx = DataResult.success(registry, Lifecycle.stable());
      String _snowmanxxxx = _snowman.getValue().getPath() + "/";

      for (Identifier _snowmanxxxxx : _snowmanxx) {
         String _snowmanxxxxxx = _snowmanxxxxx.getPath();
         if (!_snowmanxxxxxx.endsWith(".json")) {
            LOGGER.warn("Skipping resource {} since it is not a json file", _snowmanxxxxx);
         } else if (!_snowmanxxxxxx.startsWith(_snowmanxxxx)) {
            LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", _snowmanxxxxx);
         } else {
            String _snowmanxxxxxxx = _snowmanxxxxxx.substring(_snowmanxxxx.length(), _snowmanxxxxxx.length() - ".json".length());
            Identifier _snowmanxxxxxxxx = new Identifier(_snowmanxxxxx.getNamespace(), _snowmanxxxxxxx);
            _snowmanxxx = _snowmanxxx.flatMap(_snowmanxxxxxxxxx -> this.readSupplier(_snowman, _snowmanxxxxxxxxx, _snowman, _snowman).map(_snowmanxxxxxxxxxx -> _snowmanxx));
         }
      }

      return _snowmanxxx.setPartial(registry);
   }

   private <E> DataResult<Supplier<E>> readSupplier(RegistryKey<? extends Registry<E>> _snowman, MutableRegistry<E> _snowman, Codec<E> _snowman, Identifier elementId) {
      RegistryKey<E> _snowmanxxx = RegistryKey.of(_snowman, elementId);
      RegistryOps.ValueHolder<E> _snowmanxxxx = this.getValueHolder(_snowman);
      DataResult<Supplier<E>> _snowmanxxxxx = _snowmanxxxx.values.get(_snowmanxxx);
      if (_snowmanxxxxx != null) {
         return _snowmanxxxxx;
      } else {
         Supplier<E> _snowmanxxxxxx = Suppliers.memoize(() -> {
            E _snowmanxxxxxxx = _snowman.get(_snowman);
            if (_snowmanxxxxxxx == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + _snowman);
            } else {
               return _snowmanxxxxxxx;
            }
         });
         _snowmanxxxx.values.put(_snowmanxxx, DataResult.success(_snowmanxxxxxx));
         DataResult<Pair<E, OptionalInt>> _snowmanxxxxxxx = this.entryLoader.load(this.entryOps, _snowman, _snowmanxxx, _snowman);
         Optional<Pair<E, OptionalInt>> _snowmanxxxxxxxx = _snowmanxxxxxxx.result();
         if (_snowmanxxxxxxxx.isPresent()) {
            Pair<E, OptionalInt> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.get();
            _snowman.replace((OptionalInt)_snowmanxxxxxxxxx.getSecond(), _snowmanxxx, _snowmanxxxxxxxxx.getFirst(), _snowmanxxxxxxx.lifecycle());
         }

         DataResult<Supplier<E>> _snowmanxxxxxxxxx;
         if (!_snowmanxxxxxxxx.isPresent() && _snowman.get(_snowmanxxx) != null) {
            _snowmanxxxxxxxxx = DataResult.success((Supplier<E>)(() -> _snowman.get(_snowman)), Lifecycle.stable());
         } else {
            _snowmanxxxxxxxxx = _snowmanxxxxxxx.map(_snowmanxxxxxxxxxx -> () -> _snowman.get(_snowman));
         }

         _snowmanxxxx.values.put(_snowmanxxx, _snowmanxxxxxxxxx);
         return _snowmanxxxxxxxxx;
      }
   }

   private <E> RegistryOps.ValueHolder<E> getValueHolder(RegistryKey<? extends Registry<E>> registryRef) {
      return (RegistryOps.ValueHolder<E>)this.valueHolders.computeIfAbsent(registryRef, _snowman -> new RegistryOps.ValueHolder());
   }

   protected <E> DataResult<Registry<E>> method_31152(RegistryKey<? extends Registry<E>> _snowman) {
      return this.registryManager
         .getOptional(_snowman)
         .map(_snowmanx -> DataResult.success(_snowmanx, _snowmanx.getLifecycle()))
         .orElseGet(() -> DataResult.error("Unknown registry: " + _snowman));
   }

   public interface EntryLoader {
      Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> var1);

      <E> DataResult<Pair<E, OptionalInt>> load(
         DynamicOps<JsonElement> var1, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> var4
      );

      static RegistryOps.EntryLoader resourceBacked(ResourceManager _snowman) {
         return new RegistryOps.EntryLoader() {
            @Override
            public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> _snowman) {
               return _snowman.findResources(_snowman.getValue().getPath(), _snowmanxx -> _snowmanxx.endsWith(".json"));
            }

            @Override
            public <E> DataResult<Pair<E, OptionalInt>> load(
               DynamicOps<JsonElement> _snowman, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> _snowman
            ) {
               Identifier _snowmanxx = entryId.getValue();
               Identifier _snowmanxxx = new Identifier(_snowmanxx.getNamespace(), registryId.getValue().getPath() + "/" + _snowmanxx.getPath() + ".json");

               try (
                  Resource _snowmanxxxx = _snowman.getResource(_snowmanxxx);
                  Reader _snowmanxxxxx = new InputStreamReader(_snowmanxxxx.getInputStream(), StandardCharsets.UTF_8);
               ) {
                  JsonParser _snowmanxxxxxx = new JsonParser();
                  JsonElement _snowmanxxxxxxx = _snowmanxxxxxx.parse(_snowmanxxxxx);
                  return _snowman.parse(_snowman, _snowmanxxxxxxx).map(_snowmanxxxxxxxx -> Pair.of(_snowmanxxxxxxxx, OptionalInt.empty()));
               } catch (JsonIOException | JsonSyntaxException | IOException var42) {
                  return DataResult.error("Failed to parse " + _snowmanxxx + " file: " + var42.getMessage());
               }
            }

            @Override
            public String toString() {
               return "ResourceAccess[" + _snowman + "]";
            }
         };
      }

      public static final class Impl implements RegistryOps.EntryLoader {
         private final Map<RegistryKey<?>, JsonElement> values = Maps.newIdentityHashMap();
         private final Object2IntMap<RegistryKey<?>> entryToRawId = new Object2IntOpenCustomHashMap(Util.identityHashStrategy());
         private final Map<RegistryKey<?>, Lifecycle> entryToLifecycle = Maps.newIdentityHashMap();

         public Impl() {
         }

         public <E> void add(DynamicRegistryManager.Impl _snowman, RegistryKey<E> _snowman, Encoder<E> _snowman, int rawId, E _snowman, Lifecycle _snowman) {
            DataResult<JsonElement> _snowmanxxxxx = _snowman.encodeStart(RegistryReadingOps.of(JsonOps.INSTANCE, _snowman), _snowman);
            Optional<PartialResult<JsonElement>> _snowmanxxxxxx = _snowmanxxxxx.error();
            if (_snowmanxxxxxx.isPresent()) {
               RegistryOps.LOGGER.error("Error adding element: {}", _snowmanxxxxxx.get().message());
            } else {
               this.values.put(_snowman, (JsonElement)_snowmanxxxxx.result().get());
               this.entryToRawId.put(_snowman, rawId);
               this.entryToLifecycle.put(_snowman, _snowman);
            }
         }

         @Override
         public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> _snowman) {
            return this.values
               .keySet()
               .stream()
               .filter(_snowmanxx -> _snowmanxx.isOf(_snowman))
               .map(_snowmanxx -> new Identifier(_snowmanxx.getValue().getNamespace(), _snowman.getValue().getPath() + "/" + _snowmanxx.getValue().getPath() + ".json"))
               .collect(Collectors.toList());
         }

         @Override
         public <E> DataResult<Pair<E, OptionalInt>> load(
            DynamicOps<JsonElement> _snowman, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> _snowman
         ) {
            JsonElement _snowmanxx = this.values.get(entryId);
            return _snowmanxx == null
               ? DataResult.error("Unknown element: " + entryId)
               : _snowman.parse(_snowman, _snowmanxx).setLifecycle(this.entryToLifecycle.get(entryId)).map(_snowmanxxx -> Pair.of(_snowmanxxx, OptionalInt.of(this.entryToRawId.getInt(entryId))));
         }
      }
   }

   static final class ValueHolder<E> {
      private final Map<RegistryKey<E>, DataResult<Supplier<E>>> values = Maps.newIdentityHashMap();

      private ValueHolder() {
      }
   }
}
