import com.mojang.serialization.Codec;
import java.util.Random;

public class cnv extends cnt {
   public static final Codec<cnv> b = Codec.unit(() -> cnv.c);
   private static final ceh[] d = new ceh[]{
      bup.bp.n(), bup.bq.n(), bup.bs.n(), bup.bt.n(), bup.bu.n(), bup.bv.n(), bup.bw.n(), bup.bx.n(), bup.by.n(), bup.bz.n(), bup.bB.n()
   };
   public static final cnv c = new cnv();

   public cnv() {
   }

   @Override
   protected cnu<?> a() {
      return cnu.d;
   }

   @Override
   public ceh a(Random var1, fx var2) {
      double _snowman = afm.a((1.0 + bsv.f.a((double)_snowman.u() / 48.0, (double)_snowman.w() / 48.0, false)) / 2.0, 0.0, 0.9999);
      return d[(int)(_snowman * (double)d.length)];
   }
}
