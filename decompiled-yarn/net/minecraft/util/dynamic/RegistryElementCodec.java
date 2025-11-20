package net.minecraft.util.dynamic;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public final class RegistryElementCodec<E> implements Codec<Supplier<E>> {
   private final RegistryKey<? extends Registry<E>> registryRef;
   private final Codec<E> elementCodec;
   private final boolean field_26758;

   public static <E> RegistryElementCodec<E> of(RegistryKey<? extends Registry<E>> registryRef, Codec<E> codec) {
      return method_31192(registryRef, codec, true);
   }

   public static <E> Codec<List<Supplier<E>>> method_31194(RegistryKey<? extends Registry<E>> _snowman, Codec<E> _snowman) {
      return Codec.either(method_31192(_snowman, _snowman, false).listOf(), _snowman.xmap(_snowmanxx -> () -> _snowmanxx, Supplier::get).listOf())
         .xmap(_snowmanxx -> (List)_snowmanxx.map(_snowmanxxx -> _snowmanxxx, _snowmanxxx -> _snowmanxxx), Either::left);
   }

   private static <E> RegistryElementCodec<E> method_31192(RegistryKey<? extends Registry<E>> _snowman, Codec<E> _snowman, boolean _snowman) {
      return new RegistryElementCodec<>(_snowman, _snowman, _snowman);
   }

   private RegistryElementCodec(RegistryKey<? extends Registry<E>> registryRef, Codec<E> _snowman, boolean _snowman) {
      this.registryRef = registryRef;
      this.elementCodec = _snowman;
      this.field_26758 = _snowman;
   }

   public <T> DataResult<T> encode(Supplier<E> _snowman, DynamicOps<T> _snowman, T _snowman) {
      return _snowman instanceof RegistryReadingOps
         ? ((RegistryReadingOps)_snowman).encodeOrId(_snowman.get(), _snowman, this.registryRef, this.elementCodec)
         : this.elementCodec.encode(_snowman.get(), _snowman, _snowman);
   }

   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> ops, T input) {
      return ops instanceof RegistryOps
         ? ((RegistryOps)ops).decodeOrId(input, this.registryRef, this.elementCodec, this.field_26758)
         : this.elementCodec.decode(ops, input).map(_snowman -> _snowman.mapFirst(_snowmanx -> () -> _snowmanx));
   }

   @Override
   public String toString() {
      return "RegistryFileCodec[" + this.registryRef + " " + this.elementCodec + "]";
   }
}
