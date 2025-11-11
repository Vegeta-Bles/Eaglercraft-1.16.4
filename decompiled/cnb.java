import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class cnb {
   public static final Codec<cnb> a = gm.ba.dispatch(cnb::b, cnc::a);
   protected final OptionalInt b;

   protected static <S extends cnb> RecordCodecBuilder<S, OptionalInt> a() {
      return Codec.intRange(0, 80)
         .optionalFieldOf("min_clipped_height")
         .xmap(var0 -> var0.map(OptionalInt::of).orElse(OptionalInt.empty()), var0 -> var0.isPresent() ? Optional.of(var0.getAsInt()) : Optional.empty())
         .forGetter(var0 -> var0.b);
   }

   public cnb(OptionalInt var1) {
      this.b = _snowman;
   }

   protected abstract cnc<?> b();

   public abstract int a(int var1, int var2);

   public OptionalInt c() {
      return this.b;
   }
}
