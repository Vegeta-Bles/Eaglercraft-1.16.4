import javax.annotation.Nullable;

public class bms extends bkh {
   public bms(buo var1, blx.a var2) {
      super(_snowman, _snowman);
   }

   @Nullable
   @Override
   public bny b(bny var1) {
      fx _snowman = _snowman.a();
      brx _snowmanx = _snowman.p();
      ceh _snowmanxx = _snowmanx.d_(_snowman);
      buo _snowmanxxx = this.e();
      if (!_snowmanxx.a(_snowmanxxx)) {
         return bzp.a(_snowmanx, _snowman) == 7 ? null : _snowman;
      } else {
         gc _snowmanxxxx;
         if (_snowman.g()) {
            _snowmanxxxx = _snowman.l() ? _snowman.j().f() : _snowman.j();
         } else {
            _snowmanxxxx = _snowman.j() == gc.b ? _snowman.f() : gc.b;
         }

         int _snowmanxxxxx = 0;
         fx.a _snowmanxxxxxx = _snowman.i().c(_snowmanxxxx);

         while (_snowmanxxxxx < 7) {
            if (!_snowmanx.v && !brx.k(_snowmanxxxxxx)) {
               bfw _snowmanxxxxxxx = _snowman.n();
               int _snowmanxxxxxxxx = _snowmanx.L();
               if (_snowmanxxxxxxx instanceof aah && _snowmanxxxxxx.v() >= _snowmanxxxxxxxx) {
                  pb _snowmanxxxxxxxxx = new pb(new of("build.tooHigh", _snowmanxxxxxxxx).a(k.m), no.c, x.b);
                  ((aah)_snowmanxxxxxxx).b.a(_snowmanxxxxxxxxx);
               }
               break;
            }

            _snowmanxx = _snowmanx.d_(_snowmanxxxxxx);
            if (!_snowmanxx.a(this.e())) {
               if (_snowmanxx.a(_snowman)) {
                  return bny.a(_snowman, _snowmanxxxxxx, _snowmanxxxx);
               }
               break;
            }

            _snowmanxxxxxx.c(_snowmanxxxx);
            if (_snowmanxxxx.n().d()) {
               _snowmanxxxxx++;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean d() {
      return false;
   }
}
