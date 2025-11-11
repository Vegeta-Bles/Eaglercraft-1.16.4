import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class afw {
   public static final Codec<afw> a = Codec.either(
         Codec.INT,
         RecordCodecBuilder.create(
               var0 -> var0.group(Codec.INT.fieldOf("base").forGetter(var0x -> var0x.b), Codec.INT.fieldOf("spread").forGetter(var0x -> var0x.c))
                     .apply(var0, afw::new)
            )
            .comapFlatMap(var0 -> var0.c < 0 ? DataResult.error("Spread must be non-negative, got: " + var0.c) : DataResult.success(var0), Function.identity())
      )
      .xmap(var0 -> (afw)var0.map(afw::a, var0x -> var0x), var0 -> var0.c == 0 ? Either.left(var0.b) : Either.right(var0));
   private final int b;
   private final int c;

   public static Codec<afw> a(int var0, int var1, int var2) {
      Function<afw, DataResult<afw>> _snowman = var3x -> {
         if (var3x.b < _snowman || var3x.b > _snowman) {
            return DataResult.error("Base value out of range: " + var3x.b + " [" + _snowman + "-" + _snowman + "]");
         } else {
            return var3x.c <= _snowman ? DataResult.success(var3x) : DataResult.error("Spread too big: " + var3x.c + " > " + _snowman);
         }
      };
      return a.flatXmap(_snowman, _snowman);
   }

   private afw(int var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public static afw a(int var0) {
      return new afw(_snowman, 0);
   }

   public static afw a(int var0, int var1) {
      return new afw(_snowman, _snowman);
   }

   public int a(Random var1) {
      return this.c == 0 ? this.b : this.b + _snowman.nextInt(this.c + 1);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         afw _snowman = (afw)_snowman;
         return this.b == _snowman.b && this.c == _snowman.c;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.b, this.c);
   }

   @Override
   public String toString() {
      return "[" + this.b + '-' + (this.b + this.c) + ']';
   }
}
