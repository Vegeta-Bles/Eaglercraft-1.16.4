import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

public class bnm extends blx implements bno {
   private final Multimap<arg, arj> a;

   public bnm(blx.a var1) {
      super(_snowman);
      Builder<arg, arj> _snowman = ImmutableMultimap.builder();
      _snowman.put(arl.f, new arj(f, "Tool modifier", 8.0, arj.a.a));
      _snowman.put(arl.h, new arj(g, "Tool modifier", -2.9F, arj.a.a));
      this.a = _snowman.build();
   }

   @Override
   public boolean a(ceh var1, brx var2, fx var3, bfw var4) {
      return !_snowman.b_();
   }

   @Override
   public bnn d_(bmb var1) {
      return bnn.f;
   }

   @Override
   public int e_(bmb var1) {
      return 72000;
   }

   @Override
   public void a(bmb var1, brx var2, aqm var3, int var4) {
      if (_snowman instanceof bfw) {
         bfw _snowman = (bfw)_snowman;
         int _snowmanx = this.e_(_snowman) - _snowman;
         if (_snowmanx >= 10) {
            int _snowmanxx = bpu.g(_snowman);
            if (_snowmanxx <= 0 || _snowman.aF()) {
               if (!_snowman.v) {
                  _snowman.a(1, _snowman, var1x -> var1x.d(_snowman.dX()));
                  if (_snowmanxx == 0) {
                     bgy _snowmanxxx = new bgy(_snowman, _snowman, _snowman);
                     _snowmanxxx.a(_snowman, _snowman.q, _snowman.p, 0.0F, 2.5F + (float)_snowmanxx * 0.5F, 1.0F);
                     if (_snowman.bC.d) {
                        _snowmanxxx.d = bga.a.c;
                     }

                     _snowman.c(_snowmanxxx);
                     _snowman.a(null, _snowmanxxx, adq.pj, adr.h, 1.0F, 1.0F);
                     if (!_snowman.bC.d) {
                        _snowman.bm.f(_snowman);
                     }
                  }
               }

               _snowman.b(aea.c.b(this));
               if (_snowmanxx > 0) {
                  float _snowmanxxxx = _snowman.p;
                  float _snowmanxxxxx = _snowman.q;
                  float _snowmanxxxxxx = -afm.a(_snowmanxxxx * (float) (Math.PI / 180.0)) * afm.b(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxx = -afm.a(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxx = afm.b(_snowmanxxxx * (float) (Math.PI / 180.0)) * afm.b(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxxx = afm.c(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx);
                  float _snowmanxxxxxxxxxx = 3.0F * ((1.0F + (float)_snowmanxx) / 4.0F);
                  _snowmanxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowmanxxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowmanxxxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowman.i((double)_snowmanxxxxxx, (double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx);
                  _snowman.r(20);
                  if (_snowman.ao()) {
                     float _snowmanxxxxxxxxxxx = 1.1999999F;
                     _snowman.a(aqr.a, new dcn(0.0, 1.1999999F, 0.0));
                  }

                  adp _snowmanxxxxxxxxxxx;
                  if (_snowmanxx >= 3) {
                     _snowmanxxxxxxxxxxx = adq.pi;
                  } else if (_snowmanxx == 2) {
                     _snowmanxxxxxxxxxxx = adq.ph;
                  } else {
                     _snowmanxxxxxxxxxxx = adq.pg;
                  }

                  _snowman.a(null, _snowman, _snowmanxxxxxxxxxxx, adr.h, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.g() >= _snowman.h() - 1) {
         return aov.d(_snowman);
      } else if (bpu.g(_snowman) > 0 && !_snowman.aF()) {
         return aov.d(_snowman);
      } else {
         _snowman.c(_snowman);
         return aov.b(_snowman);
      }
   }

   @Override
   public boolean a(bmb var1, aqm var2, aqm var3) {
      _snowman.a(1, _snowman, var0 -> var0.c(aqf.a));
      return true;
   }

   @Override
   public boolean a(bmb var1, brx var2, ceh var3, fx var4, aqm var5) {
      if ((double)_snowman.h(_snowman, _snowman) != 0.0) {
         _snowman.a(2, _snowman, var0 -> var0.c(aqf.a));
      }

      return true;
   }

   @Override
   public Multimap<arg, arj> a(aqf var1) {
      return _snowman == aqf.a ? this.a : super.a(_snowman);
   }

   @Override
   public int c() {
      return 1;
   }
}
