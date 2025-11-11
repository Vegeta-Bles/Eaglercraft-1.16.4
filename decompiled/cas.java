import java.util.Random;

public class cas extends buo {
   public static final cfg a = cex.aj;
   protected static final ddh b = buo.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   protected cas(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.w(_snowman.b())) {
         int _snowman = 1;

         while (_snowman.d_(_snowman.c(_snowman)).a(this)) {
            _snowman++;
         }

         if (_snowman < 3) {
            int _snowmanx = _snowman.c(a);
            if (_snowmanx == 15) {
               _snowman.a(_snowman.b(), this.n());
               _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(0)), 4);
            } else {
               _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowmanx + 1)), 4);
            }
         }
      }
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
      ceh _snowman = _snowman.d_(_snowman.c());
      if (_snowman.b() == this) {
         return true;
      } else {
         if (_snowman.a(bup.i) || _snowman.a(bup.j) || _snowman.a(bup.k) || _snowman.a(bup.l) || _snowman.a(bup.C) || _snowman.a(bup.D)) {
            fx _snowmanx = _snowman.c();

            for (gc _snowmanxx : gc.c.a) {
               ceh _snowmanxxx = _snowman.d_(_snowmanx.a(_snowmanxx));
               cux _snowmanxxxx = _snowman.b(_snowmanx.a(_snowmanxx));
               if (_snowmanxxxx.a(aef.b) || _snowmanxxx.a(bup.iI)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
