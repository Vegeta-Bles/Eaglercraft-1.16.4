import java.util.Random;

public class buw extends buo {
   public static final cfg a = cex.aj;
   protected static final ddh b = buo.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final ddh c = buo.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   protected buw(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      fx _snowman = _snowman.b();
      if (_snowman.w(_snowman)) {
         int _snowmanx = 1;

         while (_snowman.d_(_snowman.c(_snowmanx)).a(this)) {
            _snowmanx++;
         }

         if (_snowmanx < 3) {
            int _snowmanxx = _snowman.c(a);
            if (_snowmanxx == 15) {
               _snowman.a(_snowman, this.n());
               ceh _snowmanxxx = _snowman.a(a, Integer.valueOf(0));
               _snowman.a(_snowman, _snowmanxxx, 4);
               _snowmanxxx.a(_snowman, _snowman, this, _snowman, false);
            } else {
               _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowmanxx + 1)), 4);
            }
         }
      }
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      for (gc _snowman : gc.c.a) {
         ceh _snowmanx = _snowman.d_(_snowman.a(_snowman));
         cva _snowmanxx = _snowmanx.c();
         if (_snowmanxx.b() || _snowman.b(_snowman.a(_snowman)).a(aef.c)) {
            return false;
         }
      }

      ceh _snowmanx = _snowman.d_(_snowman.c());
      return (_snowmanx.a(bup.cF) || _snowmanx.a(bup.C) || _snowmanx.a(bup.D)) && !_snowman.d_(_snowman.b()).c().a();
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      _snowman.a(apk.j, 1.0F);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
