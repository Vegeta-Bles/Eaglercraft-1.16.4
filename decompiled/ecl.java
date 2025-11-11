import java.util.Random;

public class ecl extends ece<ced> {
   private final eax a = djz.C().ab();

   public ecl(ecd var1) {
      super(_snowman);
   }

   public void a(ced var1, float var2, dfm var3, eag var4, int var5, int var6) {
      brx _snowman = _snowman.v();
      if (_snowman != null) {
         fx _snowmanx = _snowman.o().a(_snowman.j().f());
         ceh _snowmanxx = _snowman.k();
         if (!_snowmanxx.g()) {
            eaz.a();
            _snowman.a();
            _snowman.a((double)_snowman.b(_snowman), (double)_snowman.c(_snowman), (double)_snowman.d(_snowman));
            if (_snowmanxx.a(bup.aX) && _snowman.a(_snowman) <= 4.0F) {
               _snowmanxx = _snowmanxx.a(ceb.c, Boolean.valueOf(_snowman.a(_snowman) <= 0.5F));
               this.a(_snowmanx, _snowmanxx, _snowman, _snowman, _snowman, false, _snowman);
            } else if (_snowman.h() && !_snowman.d()) {
               cfi _snowmanxxx = _snowmanxx.a(bup.aP) ? cfi.b : cfi.a;
               ceh _snowmanxxxx = bup.aX.n().a(ceb.b, _snowmanxxx).a(ceb.a, _snowmanxx.c(cea.a));
               _snowmanxxxx = _snowmanxxxx.a(ceb.c, Boolean.valueOf(_snowman.a(_snowman) >= 0.5F));
               this.a(_snowmanx, _snowmanxxxx, _snowman, _snowman, _snowman, false, _snowman);
               fx _snowmanxxxxx = _snowmanx.a(_snowman.j());
               _snowman.b();
               _snowman.a();
               _snowmanxx = _snowmanxx.a(cea.b, Boolean.valueOf(true));
               this.a(_snowmanxxxxx, _snowmanxx, _snowman, _snowman, _snowman, true, _snowman);
            } else {
               this.a(_snowmanx, _snowmanxx, _snowman, _snowman, _snowman, false, _snowman);
            }

            _snowman.b();
            eaz.b();
         }
      }
   }

   private void a(fx var1, ceh var2, dfm var3, eag var4, brx var5, boolean var6, int var7) {
      eao _snowman = eab.b(_snowman);
      dfq _snowmanx = _snowman.getBuffer(_snowman);
      this.a.b().a(_snowman, this.a.a(_snowman), _snowman, _snowman, _snowman, _snowmanx, _snowman, new Random(), _snowman.a(_snowman), _snowman);
   }
}
