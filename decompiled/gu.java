public class gu extends gv {
   private final gv b = new gv();
   private final bhn.b c;

   public gu(bhn.b var1) {
      this.c = _snowman;
   }

   @Override
   public bmb a(fy var1, bmb var2) {
      gc _snowman = _snowman.e().c(bwa.a);
      brx _snowmanx = _snowman.h();
      double _snowmanxx = _snowman.a() + (double)((float)_snowman.i() * 1.125F);
      double _snowmanxxx = _snowman.b() + (double)((float)_snowman.j() * 1.125F);
      double _snowmanxxxx = _snowman.c() + (double)((float)_snowman.k() * 1.125F);
      fx _snowmanxxxxx = _snowman.d().a(_snowman);
      double _snowmanxxxxxx;
      if (_snowmanx.b(_snowmanxxxxx).a(aef.b)) {
         _snowmanxxxxxx = 1.0;
      } else {
         if (!_snowmanx.d_(_snowmanxxxxx).g() || !_snowmanx.b(_snowmanxxxxx.c()).a(aef.b)) {
            return this.b.dispense(_snowman, _snowman);
         }

         _snowmanxxxxxx = 0.0;
      }

      bhn _snowmanxxxxxxx = new bhn(_snowmanx, _snowmanxx, _snowmanxxx + _snowmanxxxxxx, _snowmanxxxx);
      _snowmanxxxxxxx.a(this.c);
      _snowmanxxxxxxx.p = _snowman.o();
      _snowmanx.c(_snowmanxxxxxxx);
      _snowman.g(1);
      return _snowman;
   }

   @Override
   protected void a(fy var1) {
      _snowman.h().c(1000, _snowman.d(), 0);
   }
}
