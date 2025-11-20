package net.minecraft.util.dynamic;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

public final class RegistryCodec<E> implements Codec<SimpleRegistry<E>> {
   private final Codec<SimpleRegistry<E>> delegate;
   private final RegistryKey<? extends Registry<E>> registryRef;
   private final Codec<E> elementCodec;

   public static <E> RegistryCodec<E> of(RegistryKey<? extends Registry<E>> registryRef, Lifecycle lifecycle, Codec<E> codec) {
      return new RegistryCodec<>(registryRef, lifecycle, codec);
   }

   private RegistryCodec(RegistryKey<? extends Registry<E>> registryRef, Lifecycle lifecycle, Codec<E> codec) {
      this.delegate = SimpleRegistry.createCodec(registryRef, lifecycle, codec);
      this.registryRef = registryRef;
      this.elementCodec = codec;
   }

   public <T> DataResult<T> encode(SimpleRegistry<E> _snowman, DynamicOps<T> _snowman, T _snowman) {
      return this.delegate.encode(_snowman, _snowman, _snowman);
   }

   public <T> DataResult<Pair<SimpleRegistry<E>, T>> decode(DynamicOps<T> ops, T input) {
      DataResult<Pair<SimpleRegistry<E>, T>> _snowman = this.delegate.decode(ops, input);
      return ops instanceof RegistryOps
         ? _snowman.flatMap(
            _snowmanx -> ((RegistryOps)ops)
                  .loadToRegistry((SimpleRegistry<E>)_snowmanx.getFirst(), this.registryRef, this.elementCodec)
                  .map(_snowmanxxx -> Pair.of(_snowmanxxx, _snowmanx.getSecond()))
         )
         : _snowman;
   }

   @Override
   public String toString() {
      return "RegistryDataPackCodec[" + this.delegate + " " + this.registryRef + " " + this.elementCodec + "]";
   }
}
