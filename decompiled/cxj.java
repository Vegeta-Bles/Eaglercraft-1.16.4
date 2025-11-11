import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;

public class cxj extends cxc {
   protected float j;
   private final Long2ObjectMap<cwz> k = new Long2ObjectOpenHashMap();
   private final Object2BooleanMap<dci> l = new Object2BooleanOpenHashMap();

   public cxj() {
   }

   @Override
   public void a(bsi var1, aqn var2) {
      super.a(_snowman, _snowman);
      this.j = _snowman.a(cwz.h);
   }

   @Override
   public void a() {
      this.b.a(cwz.h, this.j);
      this.k.clear();
      this.l.clear();
      super.a();
   }

   @Override
   public cxb b() {
      fx.a _snowman = new fx.a();
      int _snowmanx = afm.c(this.b.cE());
      ceh _snowmanxx = this.a.d_(_snowman.c(this.b.cD(), (double)_snowmanx, this.b.cH()));
      if (!this.b.a(_snowmanxx.m().a())) {
         if (this.e() && this.b.aE()) {
            while (true) {
               if (_snowmanxx.b() != bup.A && _snowmanxx.m() != cuy.c.a(false)) {
                  _snowmanx--;
                  break;
               }

               _snowmanxx = this.a.d_(_snowman.c(this.b.cD(), (double)(++_snowmanx), this.b.cH()));
            }
         } else if (this.b.ao()) {
            _snowmanx = afm.c(this.b.cE() + 0.5);
         } else {
            fx _snowmanxxx = this.b.cB();

            while ((this.a.d_(_snowmanxxx).g() || this.a.d_(_snowmanxxx).a(this.a, _snowmanxxx, cxe.a)) && _snowmanxxx.v() > 0) {
               _snowmanxxx = _snowmanxxx.c();
            }

            _snowmanx = _snowmanxxx.b().v();
         }
      } else {
         while (this.b.a(_snowmanxx.m().a())) {
            _snowmanxx = this.a.d_(_snowman.c(this.b.cD(), (double)(++_snowmanx), this.b.cH()));
         }

         _snowmanx--;
      }

      fx _snowmanxxx = this.b.cB();
      cwz _snowmanxxxx = this.a(this.b, _snowmanxxx.u(), _snowmanx, _snowmanxxx.w());
      if (this.b.a(_snowmanxxxx) < 0.0F) {
         dci _snowmanxxxxx = this.b.cc();
         if (this.b(_snowman.c(_snowmanxxxxx.a, (double)_snowmanx, _snowmanxxxxx.c))
            || this.b(_snowman.c(_snowmanxxxxx.a, (double)_snowmanx, _snowmanxxxxx.f))
            || this.b(_snowman.c(_snowmanxxxxx.d, (double)_snowmanx, _snowmanxxxxx.c))
            || this.b(_snowman.c(_snowmanxxxxx.d, (double)_snowmanx, _snowmanxxxxx.f))) {
            cxb _snowmanxxxxxx = this.a(_snowman);
            _snowmanxxxxxx.l = this.a(this.b, _snowmanxxxxxx.a());
            _snowmanxxxxxx.k = this.b.a(_snowmanxxxxxx.l);
            return _snowmanxxxxxx;
         }
      }

      cxb _snowmanxxxxx = this.a(_snowmanxxx.u(), _snowmanx, _snowmanxxx.w());
      _snowmanxxxxx.l = this.a(this.b, _snowmanxxxxx.a());
      _snowmanxxxxx.k = this.b.a(_snowmanxxxxx.l);
      return _snowmanxxxxx;
   }

   private boolean b(fx var1) {
      cwz _snowman = this.a(this.b, _snowman);
      return this.b.a(_snowman) >= 0.0F;
   }

   @Override
   public cxh a(double var1, double var3, double var5) {
      return new cxh(this.a(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman)));
   }

   @Override
   public int a(cxb[] var1, cxb var2) {
      int _snowman = 0;
      int _snowmanx = 0;
      cwz _snowmanxx = this.a(this.b, _snowman.a, _snowman.b + 1, _snowman.c);
      cwz _snowmanxxx = this.a(this.b, _snowman.a, _snowman.b, _snowman.c);
      if (this.b.a(_snowmanxx) >= 0.0F && _snowmanxxx != cwz.w) {
         _snowmanx = afm.d(Math.max(1.0F, this.b.G));
      }

      double _snowmanxxxx = a(this.a, new fx(_snowman.a, _snowman.b, _snowman.c));
      cxb _snowmanxxxxx = this.a(_snowman.a, _snowman.b, _snowman.c + 1, _snowmanx, _snowmanxxxx, gc.d, _snowmanxxx);
      if (this.a(_snowmanxxxxx, _snowman)) {
         _snowman[_snowman++] = _snowmanxxxxx;
      }

      cxb _snowmanxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c, _snowmanx, _snowmanxxxx, gc.e, _snowmanxxx);
      if (this.a(_snowmanxxxxxx, _snowman)) {
         _snowman[_snowman++] = _snowmanxxxxxx;
      }

      cxb _snowmanxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c, _snowmanx, _snowmanxxxx, gc.f, _snowmanxxx);
      if (this.a(_snowmanxxxxxxx, _snowman)) {
         _snowman[_snowman++] = _snowmanxxxxxxx;
      }

      cxb _snowmanxxxxxxxx = this.a(_snowman.a, _snowman.b, _snowman.c - 1, _snowmanx, _snowmanxxxx, gc.c, _snowmanxxx);
      if (this.a(_snowmanxxxxxxxx, _snowman)) {
         _snowman[_snowman++] = _snowmanxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c - 1, _snowmanx, _snowmanxxxx, gc.c, _snowmanxxx);
      if (this.a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c - 1, _snowmanx, _snowmanxxxx, gc.c, _snowmanxxx);
      if (this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c + 1, _snowmanx, _snowmanxxxx, gc.d, _snowmanxxx);
      if (this.a(_snowman, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c + 1, _snowmanx, _snowmanxxxx, gc.d, _snowmanxxx);
      if (this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxx;
      }

      return _snowman;
   }

   private boolean a(cxb var1, cxb var2) {
      return _snowman != null && !_snowman.i && (_snowman.k >= 0.0F || _snowman.k < 0.0F);
   }

   private boolean a(cxb var1, @Nullable cxb var2, @Nullable cxb var3, @Nullable cxb var4) {
      if (_snowman == null || _snowman == null || _snowman == null) {
         return false;
      } else if (_snowman.i) {
         return false;
      } else if (_snowman.b > _snowman.b || _snowman.b > _snowman.b) {
         return false;
      } else if (_snowman.l != cwz.d && _snowman.l != cwz.d && _snowman.l != cwz.d) {
         boolean _snowman = _snowman.l == cwz.f && _snowman.l == cwz.f && (double)this.b.cy() < 0.5;
         return _snowman.k >= 0.0F && (_snowman.b < _snowman.b || _snowman.k >= 0.0F || _snowman) && (_snowman.b < _snowman.b || _snowman.k >= 0.0F || _snowman);
      } else {
         return false;
      }
   }

   private boolean a(cxb var1) {
      dcn _snowman = new dcn((double)_snowman.a - this.b.cD(), (double)_snowman.b - this.b.cE(), (double)_snowman.c - this.b.cH());
      dci _snowmanx = this.b.cc();
      int _snowmanxx = afm.f(_snowman.f() / _snowmanx.a());
      _snowman = _snowman.a((double)(1.0F / (float)_snowmanxx));

      for (int _snowmanxxx = 1; _snowmanxxx <= _snowmanxx; _snowmanxxx++) {
         _snowmanx = _snowmanx.c(_snowman);
         if (this.a(_snowmanx)) {
            return false;
         }
      }

      return true;
   }

   public static double a(brc var0, fx var1) {
      fx _snowman = _snowman.c();
      ddh _snowmanx = _snowman.d_(_snowman).k(_snowman, _snowman);
      return (double)_snowman.v() + (_snowmanx.b() ? 0.0 : _snowmanx.c(gc.a.b));
   }

   @Nullable
   private cxb a(int var1, int var2, int var3, int var4, double var5, gc var7, cwz var8) {
      cxb _snowman = null;
      fx.a _snowmanx = new fx.a();
      double _snowmanxx = a(this.a, (fx)_snowmanx.d(_snowman, _snowman, _snowman));
      if (_snowmanxx - _snowman > 1.125) {
         return null;
      } else {
         cwz _snowmanxxx = this.a(this.b, _snowman, _snowman, _snowman);
         float _snowmanxxxx = this.b.a(_snowmanxxx);
         double _snowmanxxxxx = (double)this.b.cy() / 2.0;
         if (_snowmanxxxx >= 0.0F) {
            _snowman = this.a(_snowman, _snowman, _snowman);
            _snowman.l = _snowmanxxx;
            _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
         }

         if (_snowman == cwz.f && _snowman != null && _snowman.k >= 0.0F && !this.a(_snowman)) {
            _snowman = null;
         }

         if (_snowmanxxx == cwz.c) {
            return _snowman;
         } else {
            if ((_snowman == null || _snowman.k < 0.0F) && _snowman > 0 && _snowmanxxx != cwz.f && _snowmanxxx != cwz.k && _snowmanxxx != cwz.e) {
               _snowman = this.a(_snowman, _snowman + 1, _snowman, _snowman - 1, _snowman, _snowman, _snowman);
               if (_snowman != null && (_snowman.l == cwz.b || _snowman.l == cwz.c) && this.b.cy() < 1.0F) {
                  double _snowmanxxxxxx = (double)(_snowman - _snowman.i()) + 0.5;
                  double _snowmanxxxxxxx = (double)(_snowman - _snowman.k()) + 0.5;
                  dci _snowmanxxxxxxxx = new dci(
                     _snowmanxxxxxx - _snowmanxxxxx,
                     a(this.a, (fx)_snowmanx.c(_snowmanxxxxxx, (double)(_snowman + 1), _snowmanxxxxxxx)) + 0.001,
                     _snowmanxxxxxxx - _snowmanxxxxx,
                     _snowmanxxxxxx + _snowmanxxxxx,
                     (double)this.b.cz() + a(this.a, (fx)_snowmanx.c((double)_snowman.a, (double)_snowman.b, (double)_snowman.c)) - 0.002,
                     _snowmanxxxxxxx + _snowmanxxxxx
                  );
                  if (this.a(_snowmanxxxxxxxx)) {
                     _snowman = null;
                  }
               }
            }

            if (_snowmanxxx == cwz.h && !this.e()) {
               if (this.a(this.b, _snowman, _snowman - 1, _snowman) != cwz.h) {
                  return _snowman;
               }

               while (_snowman > 0) {
                  _snowmanxxx = this.a(this.b, _snowman, --_snowman, _snowman);
                  if (_snowmanxxx != cwz.h) {
                     return _snowman;
                  }

                  _snowman = this.a(_snowman, _snowman, _snowman);
                  _snowman.l = _snowmanxxx;
                  _snowman.k = Math.max(_snowman.k, this.b.a(_snowmanxxx));
               }
            }

            if (_snowmanxxx == cwz.b) {
               int _snowmanxxxxxx = 0;
               int _snowmanxxxxxxx = _snowman;

               while (_snowmanxxx == cwz.b) {
                  if (--_snowman < 0) {
                     cxb _snowmanxxxxxxxx = this.a(_snowman, _snowmanxxxxxxx, _snowman);
                     _snowmanxxxxxxxx.l = cwz.a;
                     _snowmanxxxxxxxx.k = -1.0F;
                     return _snowmanxxxxxxxx;
                  }

                  if (_snowmanxxxxxx++ >= this.b.bP()) {
                     cxb _snowmanxxxxxxxx = this.a(_snowman, _snowman, _snowman);
                     _snowmanxxxxxxxx.l = cwz.a;
                     _snowmanxxxxxxxx.k = -1.0F;
                     return _snowmanxxxxxxxx;
                  }

                  _snowmanxxx = this.a(this.b, _snowman, _snowman, _snowman);
                  _snowmanxxxx = this.b.a(_snowmanxxx);
                  if (_snowmanxxx != cwz.b && _snowmanxxxx >= 0.0F) {
                     _snowman = this.a(_snowman, _snowman, _snowman);
                     _snowman.l = _snowmanxxx;
                     _snowman.k = Math.max(_snowman.k, _snowmanxxxx);
                     break;
                  }

                  if (_snowmanxxxx < 0.0F) {
                     cxb _snowmanxxxxxxxx = this.a(_snowman, _snowman, _snowman);
                     _snowmanxxxxxxxx.l = cwz.a;
                     _snowmanxxxxxxxx.k = -1.0F;
                     return _snowmanxxxxxxxx;
                  }
               }
            }

            if (_snowmanxxx == cwz.f) {
               _snowman = this.a(_snowman, _snowman, _snowman);
               _snowman.i = true;
               _snowman.l = _snowmanxxx;
               _snowman.k = _snowmanxxx.a();
            }

            return _snowman;
         }
      }
   }

   private boolean a(dci var1) {
      return (Boolean)this.l.computeIfAbsent(_snowman, var2 -> !this.a.a_(this.b, _snowman));
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4, aqn var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      EnumSet<cwz> _snowman = EnumSet.noneOf(cwz.class);
      cwz _snowmanx = cwz.a;
      fx _snowmanxx = _snowman.cB();
      _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
      if (_snowman.contains(cwz.f)) {
         return cwz.f;
      } else if (_snowman.contains(cwz.k)) {
         return cwz.k;
      } else {
         cwz _snowmanxxx = cwz.a;

         for (cwz _snowmanxxxx : _snowman) {
            if (_snowman.a(_snowmanxxxx) < 0.0F) {
               return _snowmanxxxx;
            }

            if (_snowman.a(_snowmanxxxx) >= _snowman.a(_snowmanxxx)) {
               _snowmanxxx = _snowmanxxxx;
            }
         }

         return _snowmanx == cwz.b && _snowman.a(_snowmanxxx) == 0.0F && _snowman <= 1 ? cwz.b : _snowmanxxx;
      }
   }

   public cwz a(brc var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, EnumSet<cwz> var10, cwz var11, fx var12) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
               int _snowmanxxx = _snowman + _snowman;
               int _snowmanxxxx = _snowmanx + _snowman;
               int _snowmanxxxxx = _snowmanxx + _snowman;
               cwz _snowmanxxxxxx = this.a(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
               _snowmanxxxxxx = this.a(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxx);
               if (_snowman == 0 && _snowmanx == 0 && _snowmanxx == 0) {
                  _snowman = _snowmanxxxxxx;
               }

               _snowman.add(_snowmanxxxxxx);
            }
         }
      }

      return _snowman;
   }

   protected cwz a(brc var1, boolean var2, boolean var3, fx var4, cwz var5) {
      if (_snowman == cwz.s && _snowman && _snowman) {
         _snowman = cwz.d;
      }

      if (_snowman == cwz.r && !_snowman) {
         _snowman = cwz.a;
      }

      if (_snowman == cwz.j && !(_snowman.d_(_snowman).b() instanceof bug) && !(_snowman.d_(_snowman.c()).b() instanceof bug)) {
         _snowman = cwz.k;
      }

      if (_snowman == cwz.v) {
         _snowman = cwz.a;
      }

      return _snowman;
   }

   private cwz a(aqn var1, fx var2) {
      return this.a(_snowman, _snowman.u(), _snowman.v(), _snowman.w());
   }

   private cwz a(aqn var1, int var2, int var3, int var4) {
      return (cwz)this.k.computeIfAbsent(fx.a(_snowman, _snowman, _snowman), var5 -> this.a(this.a, _snowman, _snowman, _snowman, _snowman, this.d, this.e, this.f, this.d(), this.c()));
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4) {
      return a(_snowman, new fx.a(_snowman, _snowman, _snowman));
   }

   public static cwz a(brc var0, fx.a var1) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();
      cwz _snowmanxxx = b(_snowman, _snowman);
      if (_snowmanxxx == cwz.b && _snowmanx >= 1) {
         cwz _snowmanxxxx = b(_snowman, _snowman.d(_snowman, _snowmanx - 1, _snowmanxx));
         _snowmanxxx = _snowmanxxxx != cwz.c && _snowmanxxxx != cwz.b && _snowmanxxxx != cwz.h && _snowmanxxxx != cwz.g ? cwz.c : cwz.b;
         if (_snowmanxxxx == cwz.m) {
            _snowmanxxx = cwz.m;
         }

         if (_snowmanxxxx == cwz.o) {
            _snowmanxxx = cwz.o;
         }

         if (_snowmanxxxx == cwz.q) {
            _snowmanxxx = cwz.q;
         }

         if (_snowmanxxxx == cwz.w) {
            _snowmanxxx = cwz.w;
         }
      }

      if (_snowmanxxx == cwz.c) {
         _snowmanxxx = a(_snowman, _snowman.d(_snowman, _snowmanx, _snowmanxx), _snowmanxxx);
      }

      return _snowmanxxx;
   }

   public static cwz a(brc var0, fx.a var1, cwz var2) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();

      for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
         for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
            for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
               if (_snowmanxxx != 0 || _snowmanxxxxx != 0) {
                  _snowman.d(_snowman + _snowmanxxx, _snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxxxx);
                  ceh _snowmanxxxxxx = _snowman.d_(_snowman);
                  if (_snowmanxxxxxx.a(bup.cF)) {
                     return cwz.n;
                  }

                  if (_snowmanxxxxxx.a(bup.mg)) {
                     return cwz.p;
                  }

                  if (a(_snowmanxxxxxx)) {
                     return cwz.l;
                  }

                  if (_snowman.b(_snowman).a(aef.b)) {
                     return cwz.i;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   protected static cwz b(brc var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      buo _snowmanx = _snowman.b();
      cva _snowmanxx = _snowman.c();
      if (_snowman.g()) {
         return cwz.b;
      } else if (_snowman.a(aed.J) || _snowman.a(bup.dU)) {
         return cwz.e;
      } else if (_snowman.a(bup.cF)) {
         return cwz.o;
      } else if (_snowman.a(bup.mg)) {
         return cwz.q;
      } else if (_snowman.a(bup.ne)) {
         return cwz.w;
      } else if (_snowman.a(bup.eh)) {
         return cwz.x;
      } else {
         cux _snowmanxxx = _snowman.b(_snowman);
         if (_snowmanxxx.a(aef.b)) {
            return cwz.h;
         } else if (_snowmanxxx.a(aef.c)) {
            return cwz.g;
         } else if (a(_snowman)) {
            return cwz.m;
         } else if (bwb.l(_snowman) && !_snowman.c(bwb.b)) {
            return cwz.s;
         } else if (_snowmanx instanceof bwb && _snowmanxx == cva.J && !_snowman.c(bwb.b)) {
            return cwz.t;
         } else if (_snowmanx instanceof bwb && _snowman.c(bwb.b)) {
            return cwz.r;
         } else if (_snowmanx instanceof bug) {
            return cwz.j;
         } else if (_snowmanx instanceof bxx) {
            return cwz.v;
         } else if (!_snowmanx.a(aed.M) && !_snowmanx.a(aed.F) && (!(_snowmanx instanceof bwr) || _snowman.c(bwr.a))) {
            return !_snowman.a(_snowman, _snowman, cxe.a) ? cwz.a : cwz.b;
         } else {
            return cwz.f;
         }
      }
   }

   private static boolean a(ceh var0) {
      return _snowman.a(aed.an) || _snowman.a(bup.B) || _snowman.a(bup.iJ) || buy.g(_snowman);
   }
}
