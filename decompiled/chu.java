import java.util.Random;

public class chu implements brj {
   private int a;

   public chu() {
   }

   @Override
   public int a(aag var1, boolean var2, boolean var3) {
      if (!_snowman) {
         return 0;
      } else if (!_snowman.V().b(brt.y)) {
         return 0;
      } else {
         Random _snowman = _snowman.t;
         this.a--;
         if (this.a > 0) {
            return 0;
         } else {
            this.a = this.a + (60 + _snowman.nextInt(60)) * 20;
            if (_snowman.c() < 5 && _snowman.k().b()) {
               return 0;
            } else {
               int _snowmanx = 0;

               for (bfw _snowmanxx : _snowman.x()) {
                  if (!_snowmanxx.a_()) {
                     fx _snowmanxxx = _snowmanxx.cB();
                     if (!_snowman.k().b() || _snowmanxxx.v() >= _snowman.t_() && _snowman.e(_snowmanxxx)) {
                        aos _snowmanxxxx = _snowman.d(_snowmanxxx);
                        if (_snowmanxxxx.a(_snowman.nextFloat() * 3.0F)) {
                           adw _snowmanxxxxx = ((aah)_snowmanxx).A();
                           int _snowmanxxxxxx = afm.a(_snowmanxxxxx.a(aea.i.b(aea.m)), 1, Integer.MAX_VALUE);
                           int _snowmanxxxxxxx = 24000;
                           if (_snowman.nextInt(_snowmanxxxxxx) >= 72000) {
                              fx _snowmanxxxxxxxx = _snowmanxxx.b(20 + _snowman.nextInt(15)).g(-10 + _snowman.nextInt(21)).e(-10 + _snowman.nextInt(21));
                              ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
                              cux _snowmanxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxx);
                              if (bsg.a(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, aqe.ag)) {
                                 arc _snowmanxxxxxxxxxxx = null;
                                 int _snowmanxxxxxxxxxxxx = 1 + _snowman.nextInt(_snowmanxxxx.a().a() + 1);

                                 for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                                    bds _snowmanxxxxxxxxxxxxxx = aqe.ag.a(_snowman);
                                    _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxx, 0.0F, 0.0F);
                                    _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.a(_snowman, _snowmanxxxx, aqp.a, _snowmanxxxxxxxxxxx, null);
                                    _snowman.l(_snowmanxxxxxxxxxxxxxx);
                                 }

                                 _snowmanx += _snowmanxxxxxxxxxxxx;
                              }
                           }
                        }
                     }
                  }
               }

               return _snowmanx;
            }
         }
      }
   }
}
