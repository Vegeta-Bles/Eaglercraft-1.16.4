import java.util.Random;
import javax.annotation.Nullable;

public class cam extends buu implements buq {
   public static final cfg a = cex.ai;
   protected static final ddh[] b = new ddh[]{
      buo.a(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
      buo.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
   };
   private final can c;

   protected cam(can var1, ceg.c var2) {
      super(_snowman);
      this.c = _snowman;
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a)];
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.a(bup.bX);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.b(_snowman, 0) >= 9) {
         float _snowman = bvs.a(this, _snowman, _snowman);
         if (_snowman.nextInt((int)(25.0F / _snowman) + 1) == 0) {
            int _snowmanx = _snowman.c(a);
            if (_snowmanx < 7) {
               _snowman = _snowman.a(a, Integer.valueOf(_snowmanx + 1));
               _snowman.a(_snowman, _snowman, 2);
            } else {
               gc _snowmanxx = gc.c.a.a(_snowman);
               fx _snowmanxxx = _snowman.a(_snowmanxx);
               ceh _snowmanxxxx = _snowman.d_(_snowmanxxx.c());
               if (_snowman.d_(_snowmanxxx).g() && (_snowmanxxxx.a(bup.bX) || _snowmanxxxx.a(bup.j) || _snowmanxxxx.a(bup.k) || _snowmanxxxx.a(bup.l) || _snowmanxxxx.a(bup.i))) {
                  _snowman.a(_snowmanxxx, this.c.n());
                  _snowman.a(_snowman, this.c.d().n().a(bxm.aq, _snowmanxx));
               }
            }
         }
      }
   }

   @Nullable
   protected blx c() {
      if (this.c == bup.cK) {
         return bmd.nj;
      } else {
         return this.c == bup.dK ? bmd.nk : null;
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      blx _snowman = this.c();
      return _snowman == null ? bmb.b : new bmb(_snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.c(a) != 7;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      int _snowman = Math.min(7, _snowman.c(a) + afm.a(_snowman.t, 2, 5));
      ceh _snowmanx = _snowman.a(a, Integer.valueOf(_snowman));
      _snowman.a(_snowman, _snowmanx, 2);
      if (_snowman == 7) {
         _snowmanx.b(_snowman, _snowman, _snowman.t);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   public can d() {
      return this.c;
   }
}
