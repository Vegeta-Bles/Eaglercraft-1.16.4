import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;

public class ctg<SC extends ctv> {
   public static final Codec<ctg<?>> a = gm.aA.dispatch(var0 -> var0.c, ctt::d);
   public static final Codec<Supplier<ctg<?>>> b = vf.a(gm.as, a);
   public final ctt<SC> c;
   public final SC d;

   public ctg(ctt<SC> var1, SC var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12) {
      this.c.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.d);
   }

   public void a(long var1) {
      this.c.a(_snowman);
   }

   public SC a() {
      return this.d;
   }
}
