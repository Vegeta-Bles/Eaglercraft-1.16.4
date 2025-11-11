public class gv implements gw {
   public gv() {
   }

   @Override
   public final bmb dispense(fy var1, bmb var2) {
      bmb _snowman = this.a(_snowman, _snowman);
      this.a(_snowman);
      this.a(_snowman, _snowman.e().c(bwa.a));
      return _snowman;
   }

   protected bmb a(fy var1, bmb var2) {
      gc _snowman = _snowman.e().c(bwa.a);
      gk _snowmanx = bwa.a(_snowman);
      bmb _snowmanxx = _snowman.a(1);
      a(_snowman.h(), _snowmanxx, 6, _snowman, _snowmanx);
      return _snowman;
   }

   public static void a(brx var0, bmb var1, int var2, gc var3, gk var4) {
      double _snowman = _snowman.a();
      double _snowmanx = _snowman.b();
      double _snowmanxx = _snowman.c();
      if (_snowman.n() == gc.a.b) {
         _snowmanx -= 0.125;
      } else {
         _snowmanx -= 0.15625;
      }

      bcv _snowmanxxx = new bcv(_snowman, _snowman, _snowmanx, _snowmanxx, _snowman);
      double _snowmanxxxx = _snowman.t.nextDouble() * 0.1 + 0.2;
      _snowmanxxx.n(
         _snowman.t.nextGaussian() * 0.0075F * (double)_snowman + (double)_snowman.i() * _snowmanxxxx,
         _snowman.t.nextGaussian() * 0.0075F * (double)_snowman + 0.2F,
         _snowman.t.nextGaussian() * 0.0075F * (double)_snowman + (double)_snowman.k() * _snowmanxxxx
      );
      _snowman.c(_snowmanxxx);
   }

   protected void a(fy var1) {
      _snowman.h().c(1000, _snowman.d(), 0);
   }

   protected void a(fy var1, gc var2) {
      _snowman.h().c(2000, _snowman.d(), _snowman.c());
   }
}
