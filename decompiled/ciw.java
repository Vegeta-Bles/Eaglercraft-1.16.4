import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;

public class ciw<FC extends cma, F extends cla<FC>> {
   public static final Codec<ciw<?, ?>> a = gm.aG.dispatch(var0 -> var0.d, cla::h);
   public static final Codec<Supplier<ciw<?, ?>>> b = vf.a(gm.av, a);
   public static final Codec<List<Supplier<ciw<?, ?>>>> c = vf.b(gm.av, a);
   public final F d;
   public final FC e;

   public ciw(F var1, FC var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public crv<?> a(gn var1, cfy var2, bsy var3, csw var4, long var5, brd var7, bsv var8, int var9, cmy var10) {
      return this.d.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, new chx(), _snowman, this.e);
   }
}
