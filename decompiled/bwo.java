import java.util.Random;

public class bwo extends buo {
   public bwo(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      _snowman.J().a(_snowman, this, this.c());
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      _snowman.J().a(_snowman, this, this.c());
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (h(_snowman.d_(_snowman.c())) && _snowman.v() >= 0) {
         bcu _snowman = new bcu(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5, _snowman.d_(_snowman));
         this.a(_snowman);
         _snowman.c(_snowman);
      }
   }

   protected void a(bcu var1) {
   }

   protected int c() {
      return 2;
   }

   public static boolean h(ceh var0) {
      cva _snowman = _snowman.c();
      return _snowman.g() || _snowman.a(aed.an) || _snowman.a() || _snowman.e();
   }

   public void a(brx var1, fx var2, ceh var3, ceh var4, bcu var5) {
   }

   public void a(brx var1, fx var2, bcu var3) {
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.nextInt(16) == 0) {
         fx _snowman = _snowman.c();
         if (h(_snowman.d_(_snowman))) {
            double _snowmanx = (double)_snowman.u() + _snowman.nextDouble();
            double _snowmanxx = (double)_snowman.v() - 0.05;
            double _snowmanxxx = (double)_snowman.w() + _snowman.nextDouble();
            _snowman.a(new hc(hh.x, _snowman), _snowmanx, _snowmanxx, _snowmanxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   public int c(ceh var1, brc var2, fx var3) {
      return -16777216;
   }
}
