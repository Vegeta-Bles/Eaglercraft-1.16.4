import javax.annotation.Nullable;

public class cxi extends cxj {
   private float k;
   private float l;

   public cxi() {
   }

   @Override
   public void a(bsi var1, aqn var2) {
      super.a(_snowman, _snowman);
      _snowman.a(cwz.h, 0.0F);
      this.k = _snowman.a(cwz.c);
      _snowman.a(cwz.c, 6.0F);
      this.l = _snowman.a(cwz.i);
      _snowman.a(cwz.i, 4.0F);
   }

   @Override
   public void a() {
      this.b.a(cwz.c, this.k);
      this.b.a(cwz.i, this.l);
      super.a();
   }

   @Override
   public cxb b() {
      return this.a(afm.c(this.b.cc().a), afm.c(this.b.cc().b + 0.5), afm.c(this.b.cc().c));
   }

   @Override
   public cxh a(double var1, double var3, double var5) {
      return new cxh(this.a(afm.c(_snowman), afm.c(_snowman + 0.5), afm.c(_snowman)));
   }

   @Override
   public int a(cxb[] var1, cxb var2) {
      int _snowman = 0;
      int _snowmanx = 1;
      fx _snowmanxx = new fx(_snowman.a, _snowman.b, _snowman.c);
      double _snowmanxxx = this.b(_snowmanxx);
      cxb _snowmanxxxx = this.a(_snowman.a, _snowman.b, _snowman.c + 1, 1, _snowmanxxx);
      cxb _snowmanxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c, 1, _snowmanxxx);
      cxb _snowmanxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c, 1, _snowmanxxx);
      cxb _snowmanxxxxxxx = this.a(_snowman.a, _snowman.b, _snowman.c - 1, 1, _snowmanxxx);
      cxb _snowmanxxxxxxxx = this.a(_snowman.a, _snowman.b + 1, _snowman.c, 0, _snowmanxxx);
      cxb _snowmanxxxxxxxxx = this.a(_snowman.a, _snowman.b - 1, _snowman.c, 1, _snowmanxxx);
      if (_snowmanxxxx != null && !_snowmanxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxx;
      }

      if (_snowmanxxxxx != null && !_snowmanxxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxxx;
      }

      if (_snowmanxxxxxx != null && !_snowmanxxxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxxxx;
      }

      if (_snowmanxxxxxxx != null && !_snowmanxxxxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxxxxx;
      }

      if (_snowmanxxxxxxxx != null && !_snowmanxxxxxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxxxxxx;
      }

      if (_snowmanxxxxxxxxx != null && !_snowmanxxxxxxxxx.i) {
         _snowman[_snowman++] = _snowmanxxxxxxxxx;
      }

      boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxx == null || _snowmanxxxxxxx.l == cwz.b || _snowmanxxxxxxx.k != 0.0F;
      boolean _snowmanxxxxxxxxxxx = _snowmanxxxx == null || _snowmanxxxx.l == cwz.b || _snowmanxxxx.k != 0.0F;
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx == null || _snowmanxxxxxx.l == cwz.b || _snowmanxxxxxx.k != 0.0F;
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxx == null || _snowmanxxxxx.l == cwz.b || _snowmanxxxxx.k != 0.0F;
      if (_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxxxx) {
         cxb _snowmanxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c - 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.i) {
            _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         cxb _snowmanxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c - 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.i) {
            _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxxx) {
         cxb _snowmanxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c + 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.i) {
            _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      if (_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         cxb _snowmanxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c + 1, 1, _snowmanxxx);
         if (_snowmanxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxx.i) {
            _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxx;
         }
      }

      return _snowman;
   }

   private double b(fx var1) {
      if (!this.b.aE()) {
         fx _snowman = _snowman.c();
         ddh _snowmanx = this.a.d_(_snowman).k(this.a, _snowman);
         return (double)_snowman.v() + (_snowmanx.b() ? 0.0 : _snowmanx.c(gc.a.b));
      } else {
         return (double)_snowman.v() + 0.5;
      }
   }

   @Nullable
   private cxb a(int var1, int var2, int var3, int var4, double var5) {
      cxb _snowman = null;
      fx _snowmanx = new fx(_snowman, _snowman, _snowman);
      double _snowmanxx = this.b(_snowmanx);
      if (_snowmanxx - _snowman > 1.125) {
         return null;
      } else {
         cwz _snowmanxxx = this.a(this.a, _snowman, _snowman, _snowman, this.b, this.d, this.e, this.f, false, false);
         float _snowmanxxxx = this.b.a(_snowmanxxx);
         double _snowmanxxxxx = (double)this.b.cy() / 2.0;
         if (_snowmanxxxx >= 0.0F) {
            _snowman = this.a(_snowman, _snowman, _snowman);
            _snowman.l = _snowmanxxx;
            _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
         }

         if (_snowmanxxx != cwz.h && _snowmanxxx != cwz.c) {
            if (_snowman == null && _snowman > 0 && _snowmanxxx != cwz.f && _snowmanxxx != cwz.k && _snowmanxxx != cwz.e) {
               _snowman = this.a(_snowman, _snowman + 1, _snowman, _snowman - 1, _snowman);
            }

            if (_snowmanxxx == cwz.b) {
               dci _snowmanxxxxxx = new dci(
                  (double)_snowman - _snowmanxxxxx + 0.5,
                  (double)_snowman + 0.001,
                  (double)_snowman - _snowmanxxxxx + 0.5,
                  (double)_snowman + _snowmanxxxxx + 0.5,
                  (double)((float)_snowman + this.b.cz()),
                  (double)_snowman + _snowmanxxxxx + 0.5
               );
               if (!this.b.l.a_(this.b, _snowmanxxxxxx)) {
                  return null;
               }

               cwz _snowmanxxxxxxx = this.a(this.a, _snowman, _snowman - 1, _snowman, this.b, this.d, this.e, this.f, false, false);
               if (_snowmanxxxxxxx == cwz.a) {
                  _snowman = this.a(_snowman, _snowman, _snowman);
                  _snowman.l = cwz.c;
                  _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
                  return _snowman;
               }

               if (_snowmanxxxxxxx == cwz.h) {
                  _snowman = this.a(_snowman, _snowman, _snowman);
                  _snowman.l = cwz.h;
                  _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
                  return _snowman;
               }

               int _snowmanxxxxxxxx = 0;

               while (_snowman > 0 && _snowmanxxx == cwz.b) {
                  _snowman--;
                  if (_snowmanxxxxxxxx++ >= this.b.bP()) {
                     return null;
                  }

                  _snowmanxxx = this.a(this.a, _snowman, _snowman, _snowman, this.b, this.d, this.e, this.f, false, false);
                  _snowmanxxxx = this.b.a(_snowmanxxx);
                  if (_snowmanxxx != cwz.b && _snowmanxxxx >= 0.0F) {
                     _snowman = this.a(_snowman, _snowman, _snowman);
                     _snowman.l = _snowmanxxx;
                     _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
                     break;
                  }

                  if (_snowmanxxxx < 0.0F) {
                     return null;
                  }
               }
            }

            return _snowman;
         } else {
            if (_snowman < this.b.l.t_() - 10 && _snowman != null) {
               _snowman.k++;
            }

            return _snowman;
         }
      }
   }

   @Override
   protected cwz a(brc var1, boolean var2, boolean var3, fx var4, cwz var5) {
      if (_snowman == cwz.j && !(_snowman.d_(_snowman).b() instanceof bug) && !(_snowman.d_(_snowman.c()).b() instanceof bug)) {
         _snowman = cwz.k;
      }

      if (_snowman == cwz.r || _snowman == cwz.s || _snowman == cwz.t) {
         _snowman = cwz.a;
      }

      if (_snowman == cwz.v) {
         _snowman = cwz.a;
      }

      return _snowman;
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4) {
      fx.a _snowman = new fx.a();
      cwz _snowmanx = b(_snowman, _snowman.d(_snowman, _snowman, _snowman));
      if (_snowmanx == cwz.h) {
         for (gc _snowmanxx : gc.values()) {
            cwz _snowmanxxx = b(_snowman, _snowman.d(_snowman, _snowman, _snowman).c(_snowmanxx));
            if (_snowmanxxx == cwz.a) {
               return cwz.i;
            }
         }

         return cwz.h;
      } else {
         if (_snowmanx == cwz.b && _snowman >= 1) {
            ceh _snowmanxxx = _snowman.d_(new fx(_snowman, _snowman - 1, _snowman));
            cwz _snowmanxxxx = b(_snowman, _snowman.d(_snowman, _snowman - 1, _snowman));
            if (_snowmanxxxx != cwz.c && _snowmanxxxx != cwz.b && _snowmanxxxx != cwz.g) {
               _snowmanx = cwz.c;
            } else {
               _snowmanx = cwz.b;
            }

            if (_snowmanxxxx == cwz.m || _snowmanxxx.a(bup.iJ) || _snowmanxxx.a(aed.ay)) {
               _snowmanx = cwz.m;
            }

            if (_snowmanxxxx == cwz.o) {
               _snowmanx = cwz.o;
            }

            if (_snowmanxxxx == cwz.q) {
               _snowmanx = cwz.q;
            }
         }

         if (_snowmanx == cwz.c) {
            _snowmanx = a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowmanx);
         }

         return _snowmanx;
      }
   }
}
