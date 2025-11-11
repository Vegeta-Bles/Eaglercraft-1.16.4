import java.util.function.Predicate;

public class bkm extends bmo implements bno {
   public bkm(blx.a var1) {
      super(_snowman);
   }

   @Override
   public void a(bmb var1, brx var2, aqm var3, int var4) {
      if (_snowman instanceof bfw) {
         bfw _snowman = (bfw)_snowman;
         boolean _snowmanx = _snowman.bC.d || bpu.a(bpw.A, _snowman) > 0;
         bmb _snowmanxx = _snowman.f(_snowman);
         if (!_snowmanxx.a() || _snowmanx) {
            if (_snowmanxx.a()) {
               _snowmanxx = new bmb(bmd.kd);
            }

            int _snowmanxxx = this.e_(_snowman) - _snowman;
            float _snowmanxxxx = a(_snowmanxxx);
            if (!((double)_snowmanxxxx < 0.1)) {
               boolean _snowmanxxxxx = _snowmanx && _snowmanxx.b() == bmd.kd;
               if (!_snowman.v) {
                  bkc _snowmanxxxxxx = (bkc)(_snowmanxx.b() instanceof bkc ? _snowmanxx.b() : bmd.kd);
                  bga _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowman, _snowmanxx, _snowman);
                  _snowmanxxxxxxx.a(_snowman, _snowman.q, _snowman.p, 0.0F, _snowmanxxxx * 3.0F, 1.0F);
                  if (_snowmanxxxx == 1.0F) {
                     _snowmanxxxxxxx.a(true);
                  }

                  int _snowmanxxxxxxxx = bpu.a(bpw.x, _snowman);
                  if (_snowmanxxxxxxxx > 0) {
                     _snowmanxxxxxxx.h(_snowmanxxxxxxx.n() + (double)_snowmanxxxxxxxx * 0.5 + 0.5);
                  }

                  int _snowmanxxxxxxxxx = bpu.a(bpw.y, _snowman);
                  if (_snowmanxxxxxxxxx > 0) {
                     _snowmanxxxxxxx.a(_snowmanxxxxxxxxx);
                  }

                  if (bpu.a(bpw.z, _snowman) > 0) {
                     _snowmanxxxxxxx.f(100);
                  }

                  _snowman.a(1, _snowman, var1x -> var1x.d(_snowman.dX()));
                  if (_snowmanxxxxx || _snowman.bC.d && (_snowmanxx.b() == bmd.qk || _snowmanxx.b() == bmd.ql)) {
                     _snowmanxxxxxxx.d = bga.a.c;
                  }

                  _snowman.c(_snowmanxxxxxxx);
               }

               _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.Y, adr.h, 1.0F, 1.0F / (h.nextFloat() * 0.4F + 1.2F) + _snowmanxxxx * 0.5F);
               if (!_snowmanxxxxx && !_snowman.bC.d) {
                  _snowmanxx.g(1);
                  if (_snowmanxx.a()) {
                     _snowman.bm.f(_snowmanxx);
                  }
               }

               _snowman.b(aea.c.b(this));
            }
         }
      }
   }

   public static float a(int var0) {
      float _snowman = (float)_snowman / 20.0F;
      _snowman = (_snowman * _snowman + _snowman * 2.0F) / 3.0F;
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return _snowman;
   }

   @Override
   public int e_(bmb var1) {
      return 72000;
   }

   @Override
   public bnn d_(bmb var1) {
      return bnn.e;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      boolean _snowmanx = !_snowman.f(_snowman).a();
      if (!_snowman.bC.d && !_snowmanx) {
         return aov.d(_snowman);
      } else {
         _snowman.c(_snowman);
         return aov.b(_snowman);
      }
   }

   @Override
   public Predicate<bmb> b() {
      return a;
   }

   @Override
   public int d() {
      return 15;
   }
}
