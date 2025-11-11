import com.mojang.serialization.Codec;
import java.util.Random;

public class cnx extends cnt {
   public static final Codec<cnx> b = ceh.b.fieldOf("state").xmap(ceg.a::b, buo::n).xmap(cnx::new, var0 -> var0.c).codec();
   private final buo c;

   public cnx(buo var1) {
      this.c = _snowman;
   }

   @Override
   protected cnu<?> a() {
      return cnu.e;
   }

   @Override
   public ceh a(Random var1, fx var2) {
      gc.a _snowman = gc.a.a(_snowman);
      return this.c.n().a(bzl.e, _snowman);
   }
}
