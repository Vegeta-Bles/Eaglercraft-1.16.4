import java.util.Random;

public class bkb extends blx {
   public bkb(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      gc _snowman = _snowman.j();
      if (_snowman == gc.a) {
         return aou.d;
      } else {
         brx _snowmanx = _snowman.p();
         bny _snowmanxx = new bny(_snowman);
         fx _snowmanxxx = _snowmanxx.a();
         bmb _snowmanxxxx = _snowman.m();
         dcn _snowmanxxxxx = dcn.c(_snowmanxxx);
         dci _snowmanxxxxxx = aqe.b.l().a(_snowmanxxxxx.a(), _snowmanxxxxx.b(), _snowmanxxxxx.c());
         if (_snowmanx.b(null, _snowmanxxxxxx, var0 -> true) && _snowmanx.a(null, _snowmanxxxxxx).isEmpty()) {
            if (_snowmanx instanceof aag) {
               aag _snowmanxxxxxxx = (aag)_snowmanx;
               bcn _snowmanxxxxxxxx = aqe.b.b(_snowmanxxxxxxx, _snowmanxxxx.o(), null, _snowman.n(), _snowmanxxx, aqp.m, true, true);
               if (_snowmanxxxxxxxx == null) {
                  return aou.d;
               }

               _snowmanxxxxxxx.l(_snowmanxxxxxxxx);
               float _snowmanxxxxxxxxx = (float)afm.d((afm.g(_snowman.h() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               _snowmanxxxxxxxx.b(_snowmanxxxxxxxx.cD(), _snowmanxxxxxxxx.cE(), _snowmanxxxxxxxx.cH(), _snowmanxxxxxxxxx, 0.0F);
               this.a(_snowmanxxxxxxxx, _snowmanx.t);
               _snowmanx.c(_snowmanxxxxxxxx);
               _snowmanx.a(null, _snowmanxxxxxxxx.cD(), _snowmanxxxxxxxx.cE(), _snowmanxxxxxxxx.cH(), adq.V, adr.e, 0.75F, 0.8F);
            }

            _snowmanxxxx.g(1);
            return aou.a(_snowmanx.v);
         } else {
            return aou.d;
         }
      }
   }

   private void a(bcn var1, Random var2) {
      go _snowman = _snowman.r();
      float _snowmanx = _snowman.nextFloat() * 5.0F;
      float _snowmanxx = _snowman.nextFloat() * 20.0F - 10.0F;
      go _snowmanxxx = new go(_snowman.b() + _snowmanx, _snowman.c() + _snowmanxx, _snowman.d());
      _snowman.a(_snowmanxxx);
      _snowman = _snowman.t();
      _snowmanx = _snowman.nextFloat() * 10.0F - 5.0F;
      _snowmanxxx = new go(_snowman.b(), _snowman.c() + _snowmanx, _snowman.d());
      _snowman.b(_snowmanxxx);
   }
}
