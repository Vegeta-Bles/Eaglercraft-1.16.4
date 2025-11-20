package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryCodec;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRegistry<T> extends MutableRegistry<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final ObjectList<T> rawIdToEntry = new ObjectArrayList(256);
   private final Object2IntMap<T> entryToRawId = new Object2IntOpenCustomHashMap(Util.identityHashStrategy());
   private final BiMap<Identifier, T> idToEntry;
   private final BiMap<RegistryKey<T>, T> keyToEntry;
   private final Map<T, Lifecycle> entryToLifecycle;
   private Lifecycle lifecycle;
   protected Object[] randomEntries;
   private int nextId;

   public SimpleRegistry(RegistryKey<? extends Registry<T>> arg, Lifecycle lifecycle) {
      super(arg, lifecycle);
      this.entryToRawId.defaultReturnValue(-1);
      this.idToEntry = HashBiMap.create();
      this.keyToEntry = HashBiMap.create();
      this.entryToLifecycle = Maps.newIdentityHashMap();
      this.lifecycle = lifecycle;
   }

   public static <T> MapCodec<SimpleRegistry.RegistryManagerEntry<T>> createRegistryManagerEntryCodec(
      RegistryKey<? extends Registry<T>> arg, MapCodec<T> entryCodec
   ) {
      return RecordCodecBuilder.mapCodec(
         instance -> instance.group(
                  Identifier.CODEC.xmap(RegistryKey.createKeyFactory(arg), RegistryKey::getValue).fieldOf("name").forGetter(argxx -> argxx.key),
                  Codec.INT.fieldOf("id").forGetter(argxx -> argxx.rawId),
                  entryCodec.forGetter(argxx -> argxx.entry)
               )
               .apply(instance, SimpleRegistry.RegistryManagerEntry::new)
      );
   }

   @Override
   public <V extends T> V set(int rawId, RegistryKey<T> key, V entry, Lifecycle lifecycle) {
      return this.set(rawId, key, entry, lifecycle, true);
   }

   private <V extends T> V set(int rawId, RegistryKey<T> key, V entry, Lifecycle lifecycle, boolean checkDuplicateKeys) {
      Validate.notNull(key);
      Validate.notNull(entry);
      this.rawIdToEntry.size(Math.max(this.rawIdToEntry.size(), rawId + 1));
      this.rawIdToEntry.set(rawId, entry);
      this.entryToRawId.put(entry, rawId);
      this.randomEntries = null;
      if (checkDuplicateKeys && this.keyToEntry.containsKey(key)) {
         LOGGER.debug("Adding duplicate key '{}' to registry", key);
      }

      if (this.idToEntry.containsValue(entry)) {
         LOGGER.error("Adding duplicate value '{}' to registry", entry);
      }

      this.idToEntry.put(key.getValue(), entry);
      this.keyToEntry.put(key, entry);
      this.entryToLifecycle.put((T)entry, lifecycle);
      this.lifecycle = this.lifecycle.add(lifecycle);
      if (this.nextId <= rawId) {
         this.nextId = rawId + 1;
      }

      return entry;
   }

   @Override
   public <V extends T> V add(RegistryKey<T> key, V entry, Lifecycle lifecycle) {
      return this.set(this.nextId, key, entry, lifecycle);
   }

   @Override
   public <V extends T> V replace(OptionalInt rawId, RegistryKey<T> key, V newEntry, Lifecycle lifecycle) {
      Validate.notNull(key);
      Validate.notNull(newEntry);
      T object2 = (T)this.keyToEntry.get(key);
      int i;
      if (object2 == null) {
         i = rawId.isPresent() ? rawId.getAsInt() : this.nextId;
      } else {
         i = this.entryToRawId.getInt(object2);
         if (rawId.isPresent() && rawId.getAsInt() != i) {
            throw new IllegalStateException("ID mismatch");
         }

         this.entryToRawId.removeInt(object2);
         this.entryToLifecycle.remove(object2);
      }

      return this.set(i, key, newEntry, lifecycle, false);
   }

   @Nullable
   @Override
   public Identifier getId(T entry) {
      return (Identifier)this.idToEntry.inverse().get(entry);
   }

   @Override
   public Optional<RegistryKey<T>> getKey(T entry) {
      return Optional.ofNullable((RegistryKey<T>)this.keyToEntry.inverse().get(entry));
   }

   @Override
   public int getRawId(@Nullable T entry) {
      return this.entryToRawId.getInt(entry);
   }

   @Nullable
   @Override
   public T get(@Nullable RegistryKey<T> key) {
      return (T)this.keyToEntry.get(key);
   }

   @Nullable
   @Override
   public T get(int index) {
      return (T)(index >= 0 && index < this.rawIdToEntry.size() ? this.rawIdToEntry.get(index) : null);
   }

   @Override
   public Lifecycle getEntryLifecycle(T object) {
      return this.entryToLifecycle.get(object);
   }

   @Override
   public Lifecycle getLifecycle() {
      return this.lifecycle;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.filter(this.rawIdToEntry.iterator(), Objects::nonNull);
   }

   @Nullable
   @Override
   public T get(@Nullable Identifier id) {
      return (T)this.idToEntry.get(id);
   }

   @Override
   public Set<Identifier> getIds() {
      return Collections.unmodifiableSet(this.idToEntry.keySet());
   }

   @Override
   public Set<Entry<RegistryKey<T>, T>> getEntries() {
      return Collections.<RegistryKey<T>, T>unmodifiableMap(this.keyToEntry).entrySet();
   }

   @Nullable
   public T getRandom(Random random) {
      if (this.randomEntries == null) {
         Collection<?> collection = this.idToEntry.values();
         if (collection.isEmpty()) {
            return null;
         }

         this.randomEntries = collection.toArray(new Object[collection.size()]);
      }

      return Util.getRandom((T[])this.randomEntries, random);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean containsId(Identifier id) {
      return this.idToEntry.containsKey(id);
   }

   public static <T> Codec<SimpleRegistry<T>> createRegistryManagerCodec(RegistryKey<? extends Registry<T>> arg, Lifecycle lifecycle, Codec<T> entryCodec) {
      return createRegistryManagerEntryCodec(arg, entryCodec.fieldOf("element")).codec().listOf().xmap(list -> {
         SimpleRegistry<T> lv = new SimpleRegistry<>(arg, lifecycle);

         for (SimpleRegistry.RegistryManagerEntry<T> lv2 : list) {
            lv.set(lv2.rawId, lv2.key, lv2.entry, lifecycle);
         }

         return lv;
      }, argx -> {
         Builder<SimpleRegistry.RegistryManagerEntry<T>> builder = ImmutableList.builder();

         for (T object : argx) {
            builder.add(new SimpleRegistry.RegistryManagerEntry((RegistryKey<T>)argx.getKey(object).get(), argx.getRawId(object), object));
         }

         return builder.build();
      });
   }

   public static <T> Codec<SimpleRegistry<T>> createRegistryCodec(RegistryKey<? extends Registry<T>> registryRef, Lifecycle lifecycle, Codec<T> entryCodec) {
      return RegistryCodec.of(registryRef, lifecycle, entryCodec);
   }

   public static <T> Codec<SimpleRegistry<T>> createCodec(RegistryKey<? extends Registry<T>> arg, Lifecycle lifecycle, Codec<T> entryCodec) {
      return Codec.unboundedMap(Identifier.CODEC.xmap(RegistryKey.createKeyFactory(arg), RegistryKey::getValue), entryCodec).xmap(map -> {
         SimpleRegistry<T> lv = new SimpleRegistry<>(arg, lifecycle);
         map.forEach((arg2, object) -> lv.add((RegistryKey<T>)arg2, object, lifecycle));
         return lv;
      }, argx -> ImmutableMap.copyOf(argx.keyToEntry));
   }

   public static class RegistryManagerEntry<T> {
      public final RegistryKey<T> key;
      public final int rawId;
      public final T entry;

      public RegistryManagerEntry(RegistryKey<T> key, int rawId, T entry) {
         this.key = key;
         this.rawId = rawId;
         this.entry = entry;
      }
   }
}
