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
      E[] enums = (E[])enumValues.get();
      return createCodec(Enum::ordinal, ordinal -> enums[ordinal], fromString);
   }

   static <E extends StringIdentifiable> Codec<E> createCodec(
      final ToIntFunction<E> compressedEncoder, final IntFunction<E> compressedDecoder, final Function<? super String, ? extends E> decoder
   ) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E arg, DynamicOps<T> dynamicOps, T object) {
            return dynamicOps.compressMaps()
               ? dynamicOps.mergeToPrimitive(object, dynamicOps.createInt(compressedEncoder.applyAsInt(arg)))
               : dynamicOps.mergeToPrimitive(object, dynamicOps.createString(arg.asString()));
         }

         public <T> DataResult<com.mojang.datafixers.util.Pair<E, T>> decode(DynamicOps<T> dynamicOps, T object) {
            return dynamicOps.compressMaps()
               ? dynamicOps.getNumberValue(object)
                  .flatMap(
                     number -> Optional.ofNullable(compressedDecoder.apply(number.intValue()))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element id: " + number))
                  )
                  .map(arg -> com.mojang.datafixers.util.Pair.of(arg, dynamicOps.empty()))
               : dynamicOps.getStringValue(object)
                  .flatMap(
                     string -> Optional.ofNullable(decoder.apply(string))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element name: " + string))
                  )
                  .map(arg -> com.mojang.datafixers.util.Pair.of(arg, dynamicOps.empty()));
         }

         @Override
         public String toString() {
            return "StringRepresentable[" + compressedEncoder + "]";
         }
      };
   }

   static Keyable method_28142(final StringIdentifiable[] args) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
            return dynamicOps.compressMaps()
               ? IntStream.range(0, args.length).mapToObj(dynamicOps::createInt)
               : Arrays.stream(args).map(StringIdentifiable::asString).map(dynamicOps::createString);
         }
      };
   }
}
