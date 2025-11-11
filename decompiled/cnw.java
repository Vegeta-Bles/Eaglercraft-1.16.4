import com.mojang.serialization.Codec;
import java.util.Random;

public class cnw extends cnt {
   public static final Codec<cnw> b = Codec.unit(() -> cnw.c);
   public static final cnw c = new cnw();
   private static final ceh[] d = new ceh[]{bup.bv.n(), bup.bu.n(), bup.bx.n(), bup.bw.n()};
   private static final ceh[] e = new ceh[]{bup.bq.n(), bup.bt.n(), bup.by.n(), bup.bz.n()};

   public cnw() {
   }

   @Override
   protected cnu<?> a() {
      return cnu.c;
   }

   @Override
   public ceh a(Random var1, fx var2) {
      double _snowman = bsv.f.a((double)_snowman.u() / 200.0, (double)_snowman.w() / 200.0, false);
      if (_snowman < -0.8) {
         return x.a(d, _snowman);
      } else {
         return _snowman.nextInt(3) > 0 ? x.a(e, _snowman) : bup.bp.n();
      }
   }
}
