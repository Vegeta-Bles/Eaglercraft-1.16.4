import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class dde {
   private static final ddh b = x.a((Supplier<dcu>)(() -> {
      dcw _snowman = new dcq(1, 1, 1);
      _snowman.a(0, 0, 0, true, true);
      return new dcu(_snowman);
   }));
   public static final ddh a = a(
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY
   );
   private static final ddh c = new dcp(
      new dcq(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0})
   );

   public static ddh a() {
      return c;
   }

   public static ddh b() {
      return b;
   }

   public static ddh a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return a(new dci(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static ddh a(dci var0) {
      int _snowman = a(_snowman.a, _snowman.d);
      int _snowmanx = a(_snowman.b, _snowman.e);
      int _snowmanxx = a(_snowman.c, _snowman.f);
      if (_snowman >= 0 && _snowmanx >= 0 && _snowmanxx >= 0) {
         if (_snowman == 0 && _snowmanx == 0 && _snowmanxx == 0) {
            return _snowman.e(0.5, 0.5, 0.5) ? b() : a();
         } else {
            int _snowmanxxx = 1 << _snowman;
            int _snowmanxxxx = 1 << _snowmanx;
            int _snowmanxxxxx = 1 << _snowmanxx;
            int _snowmanxxxxxx = (int)Math.round(_snowman.a * (double)_snowmanxxx);
            int _snowmanxxxxxxx = (int)Math.round(_snowman.d * (double)_snowmanxxx);
            int _snowmanxxxxxxxx = (int)Math.round(_snowman.b * (double)_snowmanxxxx);
            int _snowmanxxxxxxxxx = (int)Math.round(_snowman.e * (double)_snowmanxxxx);
            int _snowmanxxxxxxxxxx = (int)Math.round(_snowman.c * (double)_snowmanxxxxx);
            int _snowmanxxxxxxxxxxx = (int)Math.round(_snowman.f * (double)_snowmanxxxxx);
            dcq _snowmanxxxxxxxxxxxx = new dcq(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx);

            for (long _snowmanxxxxxxxxxxxxx = (long)_snowmanxxxxxx; _snowmanxxxxxxxxxxxxx < (long)_snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (long _snowmanxxxxxxxxxxxxxx = (long)_snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < (long)_snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (long _snowmanxxxxxxxxxxxxxxx = (long)_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx < (long)_snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                     _snowmanxxxxxxxxxxxx.a((int)_snowmanxxxxxxxxxxxxx, (int)_snowmanxxxxxxxxxxxxxx, (int)_snowmanxxxxxxxxxxxxxxx, false, true);
                  }
               }
            }

            return new dcu(_snowmanxxxxxxxxxxxx);
         }
      } else {
         return new dcp(b.a, new double[]{_snowman.a, _snowman.d}, new double[]{_snowman.b, _snowman.e}, new double[]{_snowman.c, _snowman.f});
      }
   }

   private static int a(double var0, double var2) {
      if (!(_snowman < -1.0E-7) && !(_snowman > 1.0000001)) {
         for (int _snowman = 0; _snowman <= 3; _snowman++) {
            double _snowmanx = _snowman * (double)(1 << _snowman);
            double _snowmanxx = _snowman * (double)(1 << _snowman);
            boolean _snowmanxxx = Math.abs(_snowmanx - Math.floor(_snowmanx)) < 1.0E-7;
            boolean _snowmanxxxx = Math.abs(_snowmanxx - Math.floor(_snowmanxx)) < 1.0E-7;
            if (_snowmanxxx && _snowmanxxxx) {
               return _snowman;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long a(int var0, int var1) {
      return (long)_snowman * (long)(_snowman / IntMath.gcd(_snowman, _snowman));
   }

   public static ddh a(ddh var0, ddh var1) {
      return a(_snowman, _snowman, dcr.o);
   }

   public static ddh a(ddh var0, ddh... var1) {
      return Arrays.stream(_snowman).reduce(_snowman, dde::a);
   }

   public static ddh a(ddh var0, ddh var1, dcr var2) {
      return b(_snowman, _snowman, _snowman).c();
   }

   public static ddh b(ddh var0, ddh var1, dcr var2) {
      if (_snowman.apply(false, false)) {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException());
      } else if (_snowman == _snowman) {
         return _snowman.apply(true, true) ? _snowman : a();
      } else {
         boolean _snowman = _snowman.apply(true, false);
         boolean _snowmanx = _snowman.apply(false, true);
         if (_snowman.b()) {
            return _snowmanx ? _snowman : a();
         } else if (_snowman.b()) {
            return _snowman ? _snowman : a();
         } else {
            dcz _snowmanxx = a(1, _snowman.a(gc.a.a), _snowman.a(gc.a.a), _snowman, _snowmanx);
            dcz _snowmanxxx = a(_snowmanxx.a().size() - 1, _snowman.a(gc.a.b), _snowman.a(gc.a.b), _snowman, _snowmanx);
            dcz _snowmanxxxx = a((_snowmanxx.a().size() - 1) * (_snowmanxxx.a().size() - 1), _snowman.a(gc.a.c), _snowman.a(gc.a.c), _snowman, _snowmanx);
            dcq _snowmanxxxxx = dcq.a(_snowman.a, _snowman.a, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman);
            return (ddh)(_snowmanxx instanceof dcv && _snowmanxxx instanceof dcv && _snowmanxxxx instanceof dcv ? new dcu(_snowmanxxxxx) : new dcp(_snowmanxxxxx, _snowmanxx.a(), _snowmanxxx.a(), _snowmanxxxx.a()));
         }
      }
   }

   public static boolean c(ddh var0, ddh var1, dcr var2) {
      if (_snowman.apply(false, false)) {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException());
      } else if (_snowman == _snowman) {
         return _snowman.apply(true, true);
      } else if (_snowman.b()) {
         return _snowman.apply(false, !_snowman.b());
      } else if (_snowman.b()) {
         return _snowman.apply(!_snowman.b(), false);
      } else {
         boolean _snowman = _snowman.apply(true, false);
         boolean _snowmanx = _snowman.apply(false, true);

         for (gc.a _snowmanxx : fv.d) {
            if (_snowman.c(_snowmanxx) < _snowman.b(_snowmanxx) - 1.0E-7) {
               return _snowman || _snowmanx;
            }

            if (_snowman.c(_snowmanxx) < _snowman.b(_snowmanxx) - 1.0E-7) {
               return _snowman || _snowmanx;
            }
         }

         dcz _snowmanxx = a(1, _snowman.a(gc.a.a), _snowman.a(gc.a.a), _snowman, _snowmanx);
         dcz _snowmanxxx = a(_snowmanxx.a().size() - 1, _snowman.a(gc.a.b), _snowman.a(gc.a.b), _snowman, _snowmanx);
         dcz _snowmanxxxx = a((_snowmanxx.a().size() - 1) * (_snowmanxxx.a().size() - 1), _snowman.a(gc.a.c), _snowman.a(gc.a.c), _snowman, _snowmanx);
         return a(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman.a, _snowman.a, _snowman);
      }
   }

   private static boolean a(dcz var0, dcz var1, dcz var2, dcw var3, dcw var4, dcr var5) {
      return !_snowman.a(
         (var5x, var6, var7) -> _snowman.a((var6x, var7x, var8) -> _snowman.a((var7xx, var8x, var9) -> !_snowman.apply(_snowman.c(var5x, var6x, var7xx), _snowman.c(var6, var7x, var8x))))
      );
   }

   public static double a(gc.a var0, dci var1, Stream<ddh> var2, double var3) {
      Iterator<ddh> _snowman = _snowman.iterator();

      while (_snowman.hasNext()) {
         if (Math.abs(_snowman) < 1.0E-7) {
            return 0.0;
         }

         _snowman = _snowman.next().a(_snowman, _snowman, _snowman);
      }

      return _snowman;
   }

   public static double a(gc.a var0, dci var1, brz var2, double var3, dcs var5, Stream<ddh> var6) {
      return a(_snowman, _snowman, _snowman, _snowman, fv.a(_snowman, gc.a.c), _snowman);
   }

   private static double a(dci var0, brz var1, double var2, dcs var4, fv var5, Stream<ddh> var6) {
      if (_snowman.b() < 1.0E-6 || _snowman.c() < 1.0E-6 || _snowman.d() < 1.0E-6) {
         return _snowman;
      } else if (Math.abs(_snowman) < 1.0E-7) {
         return 0.0;
      } else {
         fv _snowman = _snowman.a();
         gc.a _snowmanx = _snowman.a(gc.a.a);
         gc.a _snowmanxx = _snowman.a(gc.a.b);
         gc.a _snowmanxxx = _snowman.a(gc.a.c);
         fx.a _snowmanxxxx = new fx.a();
         int _snowmanxxxxx = afm.c(_snowman.a(_snowmanx) - 1.0E-7) - 1;
         int _snowmanxxxxxx = afm.c(_snowman.b(_snowmanx) + 1.0E-7) + 1;
         int _snowmanxxxxxxx = afm.c(_snowman.a(_snowmanxx) - 1.0E-7) - 1;
         int _snowmanxxxxxxxx = afm.c(_snowman.b(_snowmanxx) + 1.0E-7) + 1;
         double _snowmanxxxxxxxxx = _snowman.a(_snowmanxxx) - 1.0E-7;
         double _snowmanxxxxxxxxxx = _snowman.b(_snowmanxxx) + 1.0E-7;
         boolean _snowmanxxxxxxxxxxx = _snowman > 0.0;
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? afm.c(_snowman.b(_snowmanxxx) - 1.0E-7) - 1 : afm.c(_snowman.a(_snowmanxxx) + 1.0E-7) + 1;
         int _snowmanxxxxxxxxxxxxx = a(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? 1 : -1;

         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxx ? _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxx
         ) {
            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxxx = 0;
                  if (_snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxx || _snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxx || _snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxx < 3) {
                     _snowmanxxxx.a(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     ceh _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxx);
                     if ((_snowmanxxxxxxxxxxxxxxxxxx != 1 || _snowmanxxxxxxxxxxxxxxxxxxx.d()) && (_snowmanxxxxxxxxxxxxxxxxxx != 2 || _snowmanxxxxxxxxxxxxxxxxxxx.a(bup.bo))) {
                        _snowman = _snowmanxxxxxxxxxxxxxxxxxxx.b(_snowman, _snowmanxxxx, _snowman).a(_snowmanxxx, _snowman.d((double)(-_snowmanxxxx.u()), (double)(-_snowmanxxxx.v()), (double)(-_snowmanxxxx.w())), _snowman);
                        if (Math.abs(_snowman) < 1.0E-7) {
                           return 0.0;
                        }

                        _snowmanxxxxxxxxxxxxx = a(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                     }
                  }
               }
            }
         }

         double[] _snowmanxxxxxxxxxxxxxxx = new double[]{_snowman};
         _snowman.forEach(var3 -> _snowman[0] = var3.a(_snowman, _snowman, _snowman[0]));
         return _snowmanxxxxxxxxxxxxxxx[0];
      }
   }

   private static int a(double var0, double var2, double var4) {
      return _snowman > 0.0 ? afm.c(_snowman + _snowman) + 1 : afm.c(_snowman + _snowman) - 1;
   }

   public static boolean a(ddh var0, ddh var1, gc var2) {
      if (_snowman == b() && _snowman == b()) {
         return true;
      } else if (_snowman.b()) {
         return false;
      } else {
         gc.a _snowman = _snowman.n();
         gc.b _snowmanx = _snowman.e();
         ddh _snowmanxx = _snowmanx == gc.b.a ? _snowman : _snowman;
         ddh _snowmanxxx = _snowmanx == gc.b.a ? _snowman : _snowman;
         dcr _snowmanxxxx = _snowmanx == gc.b.a ? dcr.e : dcr.c;
         return DoubleMath.fuzzyEquals(_snowmanxx.c(_snowman), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(_snowmanxxx.b(_snowman), 0.0, 1.0E-7)
            && !c(new ddf(_snowmanxx, _snowman, _snowmanxx.a.c(_snowman) - 1), new ddf(_snowmanxxx, _snowman, 0), _snowmanxxxx);
      }
   }

   public static ddh a(ddh var0, gc var1) {
      if (_snowman == b()) {
         return b();
      } else {
         gc.a _snowman = _snowman.n();
         boolean _snowmanx;
         int _snowmanxx;
         if (_snowman.e() == gc.b.a) {
            _snowmanx = DoubleMath.fuzzyEquals(_snowman.c(_snowman), 1.0, 1.0E-7);
            _snowmanxx = _snowman.a.c(_snowman) - 1;
         } else {
            _snowmanx = DoubleMath.fuzzyEquals(_snowman.b(_snowman), 0.0, 1.0E-7);
            _snowmanxx = 0;
         }

         return (ddh)(!_snowmanx ? a() : new ddf(_snowman, _snowman, _snowmanxx));
      }
   }

   public static boolean b(ddh var0, ddh var1, gc var2) {
      if (_snowman != b() && _snowman != b()) {
         gc.a _snowman = _snowman.n();
         gc.b _snowmanx = _snowman.e();
         ddh _snowmanxx = _snowmanx == gc.b.a ? _snowman : _snowman;
         ddh _snowmanxxx = _snowmanx == gc.b.a ? _snowman : _snowman;
         if (!DoubleMath.fuzzyEquals(_snowmanxx.c(_snowman), 1.0, 1.0E-7)) {
            _snowmanxx = a();
         }

         if (!DoubleMath.fuzzyEquals(_snowmanxxx.b(_snowman), 0.0, 1.0E-7)) {
            _snowmanxxx = a();
         }

         return !c(b(), b(new ddf(_snowmanxx, _snowman, _snowmanxx.a.c(_snowman) - 1), new ddf(_snowmanxxx, _snowman, 0), dcr.o), dcr.e);
      } else {
         return true;
      }
   }

   public static boolean b(ddh var0, ddh var1) {
      if (_snowman == b() || _snowman == b()) {
         return true;
      } else {
         return _snowman.b() && _snowman.b() ? false : !c(b(), b(_snowman, _snowman, dcr.o), dcr.e);
      }
   }

   @VisibleForTesting
   protected static dcz a(int var0, DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      int _snowman = _snowman.size() - 1;
      int _snowmanx = _snowman.size() - 1;
      if (_snowman instanceof dct && _snowman instanceof dct) {
         long _snowmanxx = a(_snowman, _snowmanx);
         if ((long)_snowman * _snowmanxx <= 256L) {
            return new dcv(_snowman, _snowmanx);
         }
      }

      if (_snowman.getDouble(_snowman) < _snowman.getDouble(0) - 1.0E-7) {
         return new ddc(_snowman, _snowman, false);
      } else if (_snowman.getDouble(_snowmanx) < _snowman.getDouble(0) - 1.0E-7) {
         return new ddc(_snowman, _snowman, true);
      } else if (_snowman != _snowmanx || !Objects.equals(_snowman, _snowman)) {
         return new dda(_snowman, _snowman, _snowman, _snowman);
      } else if (_snowman instanceof dcy) {
         return (dcz)_snowman;
      } else {
         return (dcz)(_snowman instanceof dcy ? (dcz)_snowman : new dcy(_snowman));
      }
   }

   public interface a {
      void consume(double var1, double var3, double var5, double var7, double var9, double var11);
   }
}
