package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.OptionalInt;

public abstract class MutableRegistry<T> extends Registry<T> {
   public MutableRegistry(RegistryKey<? extends Registry<T>> registryRef, Lifecycle lifecycle) {
      super(registryRef, lifecycle);
   }

   public abstract <V extends T> V set(int rawId, RegistryKey<T> key, V entry, Lifecycle var4);

   public abstract <V extends T> V add(RegistryKey<T> key, V entry, Lifecycle lifecycle);

   public abstract <V extends T> V replace(OptionalInt rawId, RegistryKey<T> key, V newEntry, Lifecycle var4);
}
