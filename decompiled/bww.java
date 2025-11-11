import java.util.Random;

public class bww extends bxo {
   public static final cfg a = cex.ag;

   public bww(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      this.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if ((_snowman.nextInt(3) == 0 || this.a(_snowman, _snowman, 4)) && _snowman.B(_snowman) > 11 - _snowman.c(a) - _snowman.b(_snowman, _snowman) && this.e(_snowman, _snowman, _snowman)) {
         fx.a _snowman = new fx.a();

         for (gc _snowmanx : gc.values()) {
            _snowman.a(_snowman, _snowmanx);
            ceh _snowmanxx = _snowman.d_(_snowman);
            if (_snowmanxx.a(this) && !this.e(_snowmanxx, _snowman, _snowman)) {
               _snowman.j().a(_snowman, this, afm.a(_snowman, 20, 40));
            }
         }
      } else {
         _snowman.j().a(_snowman, this, afm.a(_snowman, 20, 40));
      }
   }

   private boolean e(ceh var1, brx var2, fx var3) {
      int _snowman = _snowman.c(a);
      if (_snowman < 3) {
         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman + 1)), 2);
         return false;
      } else {
         this.d(_snowman, _snowman, _snowman);
         return true;
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (_snowman == this && this.a(_snowman, _snowman, 2)) {
         this.d(_snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private boolean a(brc var1, fx var2, int var3) {
      int _snowman = 0;
      fx.a _snowmanx = new fx.a();

      for (gc _snowmanxx : gc.values()) {
         _snowmanx.a(_snowman, _snowmanxx);
         if (_snowman.d_(_snowmanx).a(this)) {
            if (++_snowman >= _snowman) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return bmb.b;
   }
}
