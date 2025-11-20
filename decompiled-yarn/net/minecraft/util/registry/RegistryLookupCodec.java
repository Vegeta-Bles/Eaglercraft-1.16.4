package net.minecraft.util.registry;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.stream.Stream;
import net.minecraft.util.dynamic.RegistryOps;

public final class RegistryLookupCodec<E> extends MapCodec<Registry<E>> {
   private final RegistryKey<? extends Registry<E>> registryKey;

   public static <E> RegistryLookupCodec<E> of(RegistryKey<? extends Registry<E>> registryKey) {
      return new RegistryLookupCodec<>(registryKey);
   }

   private RegistryLookupCodec(RegistryKey<? extends Registry<E>> registryKey) {
      this.registryKey = registryKey;
   }

   public <T> RecordBuilder<T> encode(Registry<E> _snowman, DynamicOps<T> _snowman, RecordBuilder<T> _snowman) {
      return _snowman;
   }

   public <T> DataResult<Registry<E>> decode(DynamicOps<T> _snowman, MapLike<T> _snowman) {
      return _snowman instanceof RegistryOps ? ((RegistryOps)_snowman).method_31152(this.registryKey) : DataResult.error("Not a registry ops");
   }

   public String toString() {
      return "RegistryLookupCodec[" + this.registryKey + "]";
   }

   public <T> Stream<T> keys(DynamicOps<T> _snowman) {
      return Stream.empty();
   }
}
