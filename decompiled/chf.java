import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;

public enum chf {
   a {
      @Override
      public void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5) {
         fx _snowman = new fx(0, 128, 0);

         for (bbq _snowmanx : _snowman) {
            _snowmanx.a(_snowman);
         }

         _snowman.a(b);
      }
   },
   b {
      @Override
      public void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5) {
         if (_snowman < 100) {
            if (_snowman == 0 || _snowman == 50 || _snowman == 51 || _snowman == 52 || _snowman >= 95) {
               _snowman.c(3001, new fx(0, 128, 0), 0);
            }
         } else {
            _snowman.a(c);
         }
      }
   },
   c {
      @Override
      public void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5) {
         int _snowman = 40;
         boolean _snowmanx = _snowman % 40 == 0;
         boolean _snowmanxx = _snowman % 40 == 39;
         if (_snowmanx || _snowmanxx) {
            List<ckx.a> _snowmanxxx = ckx.a(_snowman);
            int _snowmanxxxx = _snowman / 40;
            if (_snowmanxxxx < _snowmanxxx.size()) {
               ckx.a _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
               if (_snowmanx) {
                  for (bbq _snowmanxxxxxx : _snowman) {
                     _snowmanxxxxxx.a(new fx(_snowmanxxxxx.a(), _snowmanxxxxx.d() + 1, _snowmanxxxxx.b()));
                  }
               } else {
                  int _snowmanxxxxxx = 10;

                  for (fx _snowmanxxxxxxx : fx.a(new fx(_snowmanxxxxx.a() - 10, _snowmanxxxxx.d() - 10, _snowmanxxxxx.b() - 10), new fx(_snowmanxxxxx.a() + 10, _snowmanxxxxx.d() + 10, _snowmanxxxxx.b() + 10))) {
                     _snowman.a(_snowmanxxxxxxx, false);
                  }

                  _snowman.a(null, (double)((float)_snowmanxxxxx.a() + 0.5F), (double)_snowmanxxxxx.d(), (double)((float)_snowmanxxxxx.b() + 0.5F), 5.0F, brp.a.c);
                  cmv _snowmanxxxxxxx = new cmv(true, ImmutableList.of(_snowmanxxxxx), new fx(0, 128, 0));
                  cjl.B.b(_snowmanxxxxxxx).a(_snowman, _snowman.i().g(), new Random(), new fx(_snowmanxxxxx.a(), 45, _snowmanxxxxx.b()));
               }
            } else if (_snowmanx) {
               _snowman.a(d);
            }
         }
      }
   },
   d {
      @Override
      public void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5) {
         if (_snowman >= 100) {
            _snowman.a(e);
            _snowman.f();

            for (bbq _snowman : _snowman) {
               _snowman.a(null);
               _snowman.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), 6.0F, brp.a.a);
               _snowman.ad();
            }
         } else if (_snowman >= 80) {
            _snowman.c(3001, new fx(0, 128, 0), 0);
         } else if (_snowman == 0) {
            for (bbq _snowman : _snowman) {
               _snowman.a(new fx(0, 128, 0));
            }
         } else if (_snowman < 5) {
            _snowman.c(3001, new fx(0, 128, 0), 0);
         }
      }
   },
   e {
      @Override
      public void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5) {
      }
   };

   private chf() {
   }

   public abstract void a(aag var1, chg var2, List<bbq> var3, int var4, fx var5);
}
