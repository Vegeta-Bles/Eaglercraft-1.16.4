import java.util.Random;

public abstract class bxh extends bxf implements buq {
   public static final cfg d = cex.ak;
   private final double e;

   protected bxh(ceg.c var1, gc var2, ddh var3, boolean var4, double var5) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.e = _snowman;
      this.j(this.n.b().a(d, Integer.valueOf(0)));
   }

   @Override
   public ceh a(bry var1) {
      return this.n().a(d, Integer.valueOf(_snowman.u_().nextInt(25)));
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(d) < 25;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(d) < 25 && _snowman.nextDouble() < this.e) {
         fx _snowman = _snowman.a(this.a);
         if (this.h(_snowman.d_(_snowman))) {
            _snowman.a(_snowman, _snowman.a(d));
         }
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == this.a.f() && !_snowman.a(_snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 1);
      }

      if (_snowman != this.a || !_snowman.a(this) && !_snowman.a(this.d())) {
         if (this.b) {
            _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
         }

         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         return this.d().n();
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(d);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return this.h(_snowman.d_(_snowman.a(this.a)));
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      fx _snowman = _snowman.a(this.a);
      int _snowmanx = Math.min(_snowman.c(d) + 1, 25);
      int _snowmanxx = this.a(_snowman);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx && this.h(_snowman.d_(_snowman)); _snowmanxxx++) {
         _snowman.a(_snowman, _snowman.a(d, Integer.valueOf(_snowmanx)));
         _snowman = _snowman.a(this.a);
         _snowmanx = Math.min(_snowmanx + 1, 25);
      }
   }

   protected abstract int a(Random var1);

   protected abstract boolean h(ceh var1);

   @Override
   protected bxh c() {
      return this;
   }
}
