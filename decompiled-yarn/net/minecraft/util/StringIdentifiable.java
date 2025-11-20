package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface StringIdentifiable {
   String asString();

   static <E extends Enum<E> & StringIdentifiable> Codec<E> createCodec(Supplier<E[]> enumValues, Function<? super String, ? extends E> fromString) {
      E[] _snowman = (E[])enumValues.get();
      return createCodec(Enum::ordinal, ordinal -> _snowman[ordinal], fromString);
   }

   static <E extends StringIdentifiable> Codec<E> createCodec(
      ToIntFunction<E> compressedEncoder, IntFunction<E> compressedDecoder, Function<? super String, ? extends E> decoder
   ) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E _snowman, DynamicOps<T> _snowman, T _snowman) {
            return _snowman.compressMaps() ? _snowman.mergeToPrimitive(_snowman, _snowman.createInt(compressedEncoder.applyAsInt(_snowman))) : _snowman.mergeToPrimitive(_snowman, _snowman.createString(_snowman.asString()));
         }

         public <T> DataResult<com.mojang.datafixers.util.Pair<E, T>> decode(DynamicOps<T> _snowman, T _snowman) {
            return _snowman.compressMaps()
               ? _snowman.getNumberValue(_snowman)
                  .flatMap(
                     _snowmanxxx -> Optional.ofNullable(compressedDecoder.apply(_snowmanxxx.intValue()))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element id: " + _snowmanxxx))
                  )
                  .map(_snowmanxxx -> com.mojang.datafixers.util.Pair.of(_snowmanxxx, _snowman.empty()))
               : _snowman.getStringValue(_snowman)
                  .flatMap(
                     _snowmanxxx -> Optional.ofNullable(decoder.apply(_snowmanxxx))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element name: " + _snowmanxxx))
                  )
                  .map(_snowmanxxx -> com.mojang.datafixers.util.Pair.of(_snowmanxxx, _snowman.empty()));
         }

         @Override
         public String toString() {
            return "StringRepresentable[" + compressedEncoder + "]";
         }
      };
   }

   static Keyable method_28142(StringIdentifiable[] _snowman) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> _snowman) {
            return _snowman.compressMaps()
               ? IntStream.range(0, _snowman.length).mapToObj(_snowman::createInt)
               : Arrays.stream(_snowman).map(StringIdentifiable::asString).map(_snowman::createString);
         }
      };
   }
}
