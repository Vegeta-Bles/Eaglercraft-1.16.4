import java.util.Random;

public class bur extends bud {
   public static final cey[] a = new cey[]{cex.k, cex.l, cex.m};
   protected static final ddh b = dde.a(buo.a(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), buo.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0));

   public bur(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a[0], Boolean.valueOf(false)).a(a[1], Boolean.valueOf(false)).a(a[2], Boolean.valueOf(false)));
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public ccj a(brc var1) {
      return new ccl();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccl) {
            _snowman.a((ccl)_snowman);
            _snowman.a(aea.Z);
         }

         return aou.b;
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (_snowman.t()) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccl) {
            ((ccl)_snowman).a(_snowman.r());
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      double _snowman = (double)_snowman.u() + 0.4 + (double)_snowman.nextFloat() * 0.2;
      double _snowmanx = (double)_snowman.v() + 0.7 + (double)_snowman.nextFloat() * 0.3;
      double _snowmanxx = (double)_snowman.w() + 0.4 + (double)_snowman.nextFloat() * 0.2;
      _snowman.a(hh.S, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccl) {
            aoq.a(_snowman, _snowman, (ccl)_snowman);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return bic.a(_snowman.c(_snowman));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a[0], a[1], a[2]);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
