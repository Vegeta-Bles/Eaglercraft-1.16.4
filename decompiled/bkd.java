import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;

public class bkd extends bkv {
   private static final Set<cva> c = Sets.newHashSet(new cva[]{cva.y, cva.z, cva.e, cva.g, cva.B, cva.P});
   private static final Set<buo> d = Sets.newHashSet(new buo[]{bup.cg, bup.lQ, bup.eW, bup.eX, bup.eY, bup.eZ, bup.fb, bup.fa, bup.mQ, bup.mR});
   protected static final Map<buo, buo> a = new Builder()
      .put(bup.V, bup.ab)
      .put(bup.J, bup.U)
      .put(bup.aa, bup.ag)
      .put(bup.O, bup.T)
      .put(bup.Z, bup.af)
      .put(bup.N, bup.S)
      .put(bup.X, bup.ad)
      .put(bup.L, bup.Q)
      .put(bup.Y, bup.ae)
      .put(bup.M, bup.R)
      .put(bup.W, bup.ac)
      .put(bup.K, bup.P)
      .put(bup.mh, bup.mi)
      .put(bup.mj, bup.mk)
      .put(bup.mq, bup.mr)
      .put(bup.ms, bup.mt)
      .build();

   protected bkd(bnh var1, float var2, float var3, blx.a var4) {
      super(_snowman, _snowman, _snowman, d, _snowman);
   }

   @Override
   public float a(bmb var1, ceh var2) {
      cva _snowman = _snowman.c();
      return c.contains(_snowman) ? this.b : super.a(_snowman, _snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      buo _snowmanxxx = a.get(_snowmanxx.b());
      if (_snowmanxxx != null) {
         bfw _snowmanxxxx = _snowman.n();
         _snowman.a(_snowmanxxxx, _snowmanx, adq.Z, adr.e, 1.0F, 1.0F);
         if (!_snowman.v) {
            _snowman.a(_snowmanx, _snowmanxxx.n().a(bzl.e, _snowmanxx.c(bzl.e)), 11);
            if (_snowmanxxxx != null) {
               _snowman.m().a(1, _snowmanxxxx, var1x -> var1x.d(_snowman.o()));
            }
         }

         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }
}
