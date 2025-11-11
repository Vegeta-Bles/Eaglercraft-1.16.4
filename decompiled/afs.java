import com.mojang.datafixers.util.Pair;
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

public interface afs {
   String a();

   static <E extends Enum<E> & afs> Codec<E> a(Supplier<E[]> var0, Function<? super String, ? extends E> var1) {
      E[] _snowman = (E[])_snowman.get();
      return a(Enum::ordinal, var1x -> _snowman[var1x], _snowman);
   }

   static <E extends afs> Codec<E> a(final ToIntFunction<E> var0, final IntFunction<E> var1, final Function<? super String, ? extends E> var2) {
      return new Codec<E>() {
         public <T> DataResult<T> a(E var1x, DynamicOps<T> var2x, T var3) {
            return _snowman.compressMaps() ? _snowman.mergeToPrimitive(_snowman, _snowman.createInt(_snowman.applyAsInt(_snowman))) : _snowman.mergeToPrimitive(_snowman, _snowman.createString(_snowman.a()));
         }

         public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> var1x, T var2x) {
            return _snowman.compressMaps()
               ? _snowman.getNumberValue(_snowman)
                  .flatMap(
                     var1xx -> Optional.ofNullable(_snowman.apply(var1xx.intValue()))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element id: " + var1xx))
                  )
                  .map(var1xx -> Pair.of(var1xx, _snowman.empty()))
               : _snowman.getStringValue(_snowman)
                  .flatMap(
                     var1xx -> Optional.ofNullable(_snowman.apply(var1xx))
                           .<DataResult>map(DataResult::success)
                           .orElseGet(() -> DataResult.error("Unknown element name: " + var1xx))
                  )
                  .map(var1xx -> Pair.of(var1xx, _snowman.empty()));
         }

         @Override
         public String toString() {
            return "StringRepresentable[" + _snowman + "]";
         }
      };
   }

   static Keyable a(final afs[] var0) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> var1) {
            return _snowman.compressMaps() ? IntStream.range(0, _snowman.length).mapToObj(_snowman::createInt) : Arrays.stream(_snowman).map(afs::a).map(_snowman::createString);
         }
      };
   }
}
