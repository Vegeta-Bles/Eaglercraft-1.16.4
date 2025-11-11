import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class cib<WC extends chz> {
   public static final Codec<cib<?>> a = gm.aC.dispatch(var0 -> var0.d, cig::c);
   public static final Codec<Supplier<cib<?>>> b = vf.a(gm.at, a);
   public static final Codec<List<Supplier<cib<?>>>> c = vf.b(gm.at, a);
   private final cig<WC> d;
   private final WC e;

   public cib(cig<WC> var1, WC var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public WC a() {
      return this.e;
   }

   public boolean a(Random var1, int var2, int var3) {
      return this.d.a(_snowman, _snowman, _snowman, this.e);
   }

   public boolean a(cfw var1, Function<fx, bsv> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9) {
      return this.d.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.e);
   }
}
