package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;

public class DefaultedRegistry<T> extends SimpleRegistry<T> {
   private final Identifier defaultId;
   private T defaultValue;

   public DefaultedRegistry(String defaultId, RegistryKey<? extends Registry<T>> registryRef, Lifecycle lifecycle) {
      super(registryRef, lifecycle);
      this.defaultId = new Identifier(defaultId);
   }

   @Override
   public <V extends T> V set(int rawId, RegistryKey<T> key, V entry, Lifecycle lifecycle) {
      if (this.defaultId.equals(key.getValue())) {
         this.defaultValue = (T)entry;
      }

      return super.set(rawId, key, entry, lifecycle);
   }

   @Override
   public int getRawId(@Nullable T entry) {
      int _snowman = super.getRawId(entry);
      return _snowman == -1 ? super.getRawId(this.defaultValue) : _snowman;
   }

   @Nonnull
   @Override
   public Identifier getId(T entry) {
      Identifier _snowman = super.getId(entry);
      return _snowman == null ? this.defaultId : _snowman;
   }

   @Nonnull
   @Override
   public T get(@Nullable Identifier id) {
      T _snowman = super.get(id);
      return _snowman == null ? this.defaultValue : _snowman;
   }

   @Override
   public Optional<T> getOrEmpty(@Nullable Identifier id) {
      return Optional.ofNullable(super.get(id));
   }

   @Nonnull
   @Override
   public T get(int index) {
      T _snowman = super.get(index);
      return _snowman == null ? this.defaultValue : _snowman;
   }

   @Nonnull
   @Override
   public T getRandom(Random _snowman) {
      T _snowmanx = super.getRandom(_snowman);
      return _snowmanx == null ? this.defaultValue : _snowmanx;
   }

   public Identifier getDefaultId() {
      return this.defaultId;
   }
}
