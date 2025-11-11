import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class bsg {
   private static final Logger a = LogManager.getLogger();
   private static final int b = (int)Math.pow(17.0, 2.0);
   private static final aqo[] c = Stream.of(aqo.values()).filter(var0 -> var0 != aqo.f).toArray(aqo[]::new);

   public static bsg.d a(int var0, Iterable<aqa> var1, bsg.b var2) {
      bsj _snowman = new bsj();
      Object2IntOpenHashMap<aqo> _snowmanx = new Object2IntOpenHashMap();

      for (aqa _snowmanxx : _snowman) {
         if (_snowmanxx instanceof aqn) {
            aqn _snowmanxxx = (aqn)_snowmanxx;
            if (_snowmanxxx.eu() || _snowmanxxx.K()) {
               continue;
            }
         }

         aqo _snowmanxxx = _snowmanxx.X().e();
         if (_snowmanxxx != aqo.f) {
            fx _snowmanxxxx = _snowmanxx.cB();
            long _snowmanxxxxx = brd.a(_snowmanxxxx.u() >> 4, _snowmanxxxx.w() >> 4);
            _snowman.query(_snowmanxxxxx, var5 -> {
               btg.b _snowmanxxxxxx = b(_snowman, var5).b().a(_snowman.X());
               if (_snowmanxxxxxx != null) {
                  _snowman.a(_snowman.cB(), _snowmanxxxxxx.b());
               }

               _snowman.addTo(_snowman, 1);
            });
         }
      }

      return new bsg.d(_snowman, _snowmanx, _snowman);
   }

   private static bsv b(fx var0, cfw var1) {
      return bti.a.a(0L, _snowman.u(), _snowman.v(), _snowman.w(), _snowman.i());
   }

   public static void a(aag var0, cgh var1, bsg.d var2, boolean var3, boolean var4, boolean var5) {
      _snowman.Z().a("spawner");

      for (aqo _snowman : c) {
         if ((_snowman || !_snowman.d()) && (_snowman || _snowman.d()) && (_snowman || !_snowman.e()) && _snowman.a(_snowman)) {
            a(_snowman, _snowman, _snowman, (var1x, var2x, var3x) -> _snowman.a(var1x, var2x, var3x), (var1x, var2x) -> _snowman.a(var1x, var2x));
         }
      }

      _snowman.Z().c();
   }

   public static void a(aqo var0, aag var1, cgh var2, bsg.c var3, bsg.a var4) {
      fx _snowman = a(_snowman, _snowman);
      if (_snowman.v() >= 1) {
         a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public static void a(aqo var0, aag var1, cfw var2, fx var3, bsg.c var4, bsg.a var5) {
      bsn _snowman = _snowman.a();
      cfy _snowmanx = _snowman.i().g();
      int _snowmanxx = _snowman.v();
      ceh _snowmanxxx = _snowman.d_(_snowman);
      if (!_snowmanxxx.g(_snowman, _snowman)) {
         fx.a _snowmanxxxx = new fx.a();
         int _snowmanxxxxx = 0;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = _snowman.u();
            int _snowmanxxxxxxxx = _snowman.w();
            int _snowmanxxxxxxxxx = 6;
            btg.c _snowmanxxxxxxxxxx = null;
            arc _snowmanxxxxxxxxxxx = null;
            int _snowmanxxxxxxxxxxxx = afm.f(_snowman.t.nextFloat() * 4.0F);
            int _snowmanxxxxxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
               _snowmanxxxxxxx += _snowman.t.nextInt(6) - _snowman.t.nextInt(6);
               _snowmanxxxxxxxx += _snowman.t.nextInt(6) - _snowman.t.nextInt(6);
               _snowmanxxxx.d(_snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxx + 0.5;
               double _snowmanxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxx + 0.5;
               bfw _snowmanxxxxxxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx, -1.0, false);
               if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                  double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.h(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx);
                  if (a(_snowman, _snowman, _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxx)) {
                     if (_snowmanxxxxxxxxxx == null) {
                        _snowmanxxxxxxxxxx = a(_snowman, _snowman, _snowmanx, _snowman, _snowman.t, _snowmanxxxx);
                        if (_snowmanxxxxxxxxxx == null) {
                           break;
                        }

                        _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.d + _snowman.t.nextInt(1 + _snowmanxxxxxxxxxx.e - _snowmanxxxxxxxxxx.d);
                     }

                     if (a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxx) && _snowman.test(_snowmanxxxxxxxxxx.c, _snowmanxxxx, _snowman)) {
                        aqn _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowman, _snowmanxxxxxxxxxx.c);
                        if (_snowmanxxxxxxxxxxxxxxxxxxx == null) {
                           return;
                        }

                        _snowmanxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx, _snowman.t.nextFloat() * 360.0F, 0.0F);
                        if (a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowman, _snowman.d(_snowmanxxxxxxxxxxxxxxxxxxx.cB()), aqp.a, _snowmanxxxxxxxxxxx, null);
                           _snowmanxxxxx++;
                           _snowmanxxxxxxxxxxxxx++;
                           _snowman.l(_snowmanxxxxxxxxxxxxxxxxxxx);
                           _snowman.run(_snowmanxxxxxxxxxxxxxxxxxxx, _snowman);
                           if (_snowmanxxxxx >= _snowmanxxxxxxxxxxxxxxxxxxx.eq()) {
                              return;
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxx.c(_snowmanxxxxxxxxxxxxx)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean a(aag var0, cfw var1, fx.a var2, double var3) {
      if (_snowman <= 576.0) {
         return false;
      } else if (_snowman.u().a(new dcn((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5), 24.0)) {
         return false;
      } else {
         brd _snowman = new brd(_snowman);
         return Objects.equals(_snowman, _snowman.g()) || _snowman.i().a(_snowman);
      }
   }

   private static boolean a(aag var0, aqo var1, bsn var2, cfy var3, btg.c var4, fx.a var5, double var6) {
      aqe<?> _snowman = _snowman.c;
      if (_snowman.e() == aqo.f) {
         return false;
      } else if (!_snowman.d() && _snowman > (double)(_snowman.e().f() * _snowman.e().f())) {
         return false;
      } else if (_snowman.b() && a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman)) {
         ard.c _snowmanx = ard.a(_snowman);
         if (!a(_snowmanx, _snowman, _snowman, _snowman)) {
            return false;
         } else {
            return !ard.a(_snowman, _snowman, aqp.a, _snowman, _snowman.t) ? false : _snowman.b(_snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5));
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static aqn a(aag var0, aqe<?> var1) {
      try {
         aqa _snowman = _snowman.a(_snowman);
         if (!(_snowman instanceof aqn)) {
            throw new IllegalStateException("Trying to spawn a non-mob: " + gm.S.b(_snowman));
         } else {
            return (aqn)_snowman;
         }
      } catch (Exception var4) {
         a.warn("Failed to create mob", var4);
         return null;
      }
   }

   private static boolean a(aag var0, aqn var1, double var2) {
      return _snowman > (double)(_snowman.X().e().f() * _snowman.X().e().f()) && _snowman.h(_snowman) ? false : _snowman.a(_snowman, aqp.a) && _snowman.a(_snowman);
   }

   @Nullable
   private static btg.c a(aag var0, bsn var1, cfy var2, aqo var3, Random var4, fx var5) {
      bsv _snowman = _snowman.v(_snowman);
      if (_snowman == aqo.e && _snowman.t() == bsv.b.n && _snowman.nextFloat() < 0.98F) {
         return null;
      } else {
         List<btg.c> _snowmanx = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         return _snowmanx.isEmpty() ? null : afz.a(_snowman, _snowmanx);
      }
   }

   private static boolean a(aag var0, bsn var1, cfy var2, aqo var3, btg.c var4, fx var5) {
      return a(_snowman, _snowman, _snowman, _snowman, _snowman, null).contains(_snowman);
   }

   private static List<btg.c> a(aag var0, bsn var1, cfy var2, aqo var3, fx var4, @Nullable bsv var5) {
      return _snowman == aqo.a && _snowman.d_(_snowman.c()).b() == bup.dV && _snowman.a(_snowman, false, cla.n).e() ? cla.n.c() : _snowman.a(_snowman != null ? _snowman : _snowman.v(_snowman), _snowman, _snowman, _snowman);
   }

   private static fx a(brx var0, cgh var1) {
      brd _snowman = _snowman.g();
      int _snowmanx = _snowman.d() + _snowman.t.nextInt(16);
      int _snowmanxx = _snowman.e() + _snowman.t.nextInt(16);
      int _snowmanxxx = _snowman.a(chn.a.b, _snowmanx, _snowmanxx) + 1;
      int _snowmanxxxx = _snowman.t.nextInt(_snowmanxxx + 1);
      return new fx(_snowmanx, _snowmanxxxx, _snowmanxx);
   }

   public static boolean a(brc var0, fx var1, ceh var2, cux var3, aqe<?> var4) {
      if (_snowman.r(_snowman, _snowman)) {
         return false;
      } else if (_snowman.i()) {
         return false;
      } else if (!_snowman.c()) {
         return false;
      } else {
         return _snowman.a(aed.aA) ? false : !_snowman.a(_snowman);
      }
   }

   public static boolean a(ard.c var0, brz var1, fx var2, @Nullable aqe<?> var3) {
      if (_snowman == ard.c.c) {
         return true;
      } else if (_snowman != null && _snowman.f().a(_snowman)) {
         ceh _snowman = _snowman.d_(_snowman);
         cux _snowmanx = _snowman.b(_snowman);
         fx _snowmanxx = _snowman.b();
         fx _snowmanxxx = _snowman.c();
         switch (_snowman) {
            case b:
               return _snowmanx.a(aef.b) && _snowman.b(_snowmanxxx).a(aef.b) && !_snowman.d_(_snowmanxx).g(_snowman, _snowmanxx);
            case d:
               return _snowmanx.a(aef.c);
            case a:
            default:
               ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
               return !_snowmanxxxx.a(_snowman, _snowmanxxx, _snowman) ? false : a(_snowman, _snowman, _snowman, _snowmanx, _snowman) && a(_snowman, _snowmanxx, _snowman.d_(_snowmanxx), _snowman.b(_snowmanxx), _snowman);
         }
      } else {
         return false;
      }
   }

   public static void a(bsk var0, bsv var1, int var2, int var3, Random var4) {
      btg _snowman = _snowman.b();
      List<btg.c> _snowmanx = _snowman.a(aqo.b);
      if (!_snowmanx.isEmpty()) {
         int _snowmanxx = _snowman << 4;
         int _snowmanxxx = _snowman << 4;

         while (_snowman.nextFloat() < _snowman.a()) {
            btg.c _snowmanxxxx = afz.a(_snowman, _snowmanx);
            int _snowmanxxxxx = _snowmanxxxx.d + _snowman.nextInt(1 + _snowmanxxxx.e - _snowmanxxxx.d);
            arc _snowmanxxxxxx = null;
            int _snowmanxxxxxxx = _snowmanxx + _snowman.nextInt(16);
            int _snowmanxxxxxxxx = _snowmanxxx + _snowman.nextInt(16);
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx;
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;

            for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
               boolean _snowmanxxxxxxxxxxxx = false;

               for (int _snowmanxxxxxxxxxxxxx = 0; !_snowmanxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
                  fx _snowmanxxxxxxxxxxxxxx = a(_snowman, _snowmanxxxx.c, _snowmanxxxxxxx, _snowmanxxxxxxxx);
                  if (_snowmanxxxx.c.b() && a(ard.a(_snowmanxxxx.c), _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxx.c)) {
                     float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx.c.j();
                     double _snowmanxxxxxxxxxxxxxxxx = afm.a((double)_snowmanxxxxxxx, (double)_snowmanxx + (double)_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx + 16.0 - (double)_snowmanxxxxxxxxxxxxxxx);
                     double _snowmanxxxxxxxxxxxxxxxxx = afm.a(
                        (double)_snowmanxxxxxxxx, (double)_snowmanxxx + (double)_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxxx + 16.0 - (double)_snowmanxxxxxxxxxxxxxxx
                     );
                     if (!_snowman.b(_snowmanxxxx.c.a(_snowmanxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx.v(), _snowmanxxxxxxxxxxxxxxxxx))
                        || !ard.a(_snowmanxxxx.c, _snowman, aqp.b, new fx(_snowmanxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx.v(), _snowmanxxxxxxxxxxxxxxxxx), _snowman.u_())) {
                        continue;
                     }

                     aqa _snowmanxxxxxxxxxxxxxxxxxx;
                     try {
                        _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxx.c.a(_snowman.E());
                     } catch (Exception var27) {
                        a.warn("Failed to create mob", var27);
                        continue;
                     }

                     _snowmanxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx.v(), _snowmanxxxxxxxxxxxxxxxxx, _snowman.nextFloat() * 360.0F, 0.0F);
                     if (_snowmanxxxxxxxxxxxxxxxxxx instanceof aqn) {
                        aqn _snowmanxxxxxxxxxxxxxxxxxxx = (aqn)_snowmanxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxxxx.a(_snowman, aqp.b) && _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowman)) {
                           _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowman, _snowman.d(_snowmanxxxxxxxxxxxxxxxxxxx.cB()), aqp.b, _snowmanxxxxxx, null);
                           _snowman.l(_snowmanxxxxxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxx = true;
                        }
                     }
                  }

                  _snowmanxxxxxxx += _snowman.nextInt(5) - _snowman.nextInt(5);

                  for (_snowmanxxxxxxxx += _snowman.nextInt(5) - _snowman.nextInt(5);
                     _snowmanxxxxxxx < _snowmanxx || _snowmanxxxxxxx >= _snowmanxx + 16 || _snowmanxxxxxxxx < _snowmanxxx || _snowmanxxxxxxxx >= _snowmanxxx + 16;
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxx + _snowman.nextInt(5) - _snowman.nextInt(5)
                  ) {
                     _snowmanxxxxxxx = _snowmanxxxxxxxxx + _snowman.nextInt(5) - _snowman.nextInt(5);
                  }
               }
            }
         }
      }
   }

   private static fx a(brz var0, aqe<?> var1, int var2, int var3) {
      int _snowman = _snowman.a(ard.b(_snowman), _snowman, _snowman);
      fx.a _snowmanx = new fx.a(_snowman, _snowman, _snowman);
      if (_snowman.k().c()) {
         do {
            _snowmanx.c(gc.a);
         } while (!_snowman.d_(_snowmanx).g());

         do {
            _snowmanx.c(gc.a);
         } while (_snowman.d_(_snowmanx).g() && _snowmanx.v() > 0);
      }

      if (ard.a(_snowman) == ard.c.a) {
         fx _snowmanxx = _snowmanx.c();
         if (_snowman.d_(_snowmanxx).a(_snowman, _snowmanxx, cxe.a)) {
            return _snowmanxx;
         }
      }

      return _snowmanx.h();
   }

   @FunctionalInterface
   public interface a {
      void run(aqn var1, cfw var2);
   }

   @FunctionalInterface
   public interface b {
      void query(long var1, Consumer<cgh> var3);
   }

   @FunctionalInterface
   public interface c {
      boolean test(aqe<?> var1, fx var2, cfw var3);
   }

   public static class d {
      private final int a;
      private final Object2IntOpenHashMap<aqo> b;
      private final bsj c;
      private final Object2IntMap<aqo> d;
      @Nullable
      private fx e;
      @Nullable
      private aqe<?> f;
      private double g;

      private d(int var1, Object2IntOpenHashMap<aqo> var2, bsj var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = Object2IntMaps.unmodifiable(_snowman);
      }

      private boolean a(aqe<?> var1, fx var2, cfw var3) {
         this.e = _snowman;
         this.f = _snowman;
         btg.b _snowman = bsg.b(_snowman, _snowman).b().a(_snowman);
         if (_snowman == null) {
            this.g = 0.0;
            return true;
         } else {
            double _snowmanx = _snowman.b();
            this.g = _snowmanx;
            double _snowmanxx = this.c.b(_snowman, _snowmanx);
            return _snowmanxx <= _snowman.a();
         }
      }

      private void a(aqn var1, cfw var2) {
         aqe<?> _snowman = _snowman.X();
         fx _snowmanx = _snowman.cB();
         double _snowmanxx;
         if (_snowmanx.equals(this.e) && _snowman == this.f) {
            _snowmanxx = this.g;
         } else {
            btg.b _snowmanxxx = bsg.b(_snowmanx, _snowman).b().a(_snowman);
            if (_snowmanxxx != null) {
               _snowmanxx = _snowmanxxx.b();
            } else {
               _snowmanxx = 0.0;
            }
         }

         this.c.a(_snowmanx, _snowmanxx);
         this.b.addTo(_snowman.e(), 1);
      }

      public int a() {
         return this.a;
      }

      public Object2IntMap<aqo> b() {
         return this.d;
      }

      private boolean a(aqo var1) {
         int _snowman = _snowman.c() * this.a / bsg.b;
         return this.b.getInt(_snowman) < _snowman;
      }
   }
}
