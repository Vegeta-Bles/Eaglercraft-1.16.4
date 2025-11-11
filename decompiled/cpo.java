import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cpo<DC extends clw> implements chk<cpo<?>> {
   public static final Codec<cpo<?>> a = gm.aK.dispatch("type", var0 -> var0.b, cqc::a);
   private final cqc<DC> b;
   private final DC c;

   public cpo(cqc<DC> var1, DC var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public Stream<fx> a(cpv var1, Random var2, fx var3) {
      return this.b.a(_snowman, _snowman, this.c, _snowman);
   }

   @Override
   public String toString() {
      return String.format("[%s %s]", gm.aK.b(this.b), this.c);
   }

   public cpo<?> b(cpo<?> var1) {
      return new cpo<>(cqc.B, new cpu(_snowman, this));
   }

   public DC b() {
      return this.c;
   }
}
