import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

public class cld extends cjl<cmz> {
   public cld(Codec<cmz> var1) {
      super(_snowman);
   }

   public static boolean c(bsc var0, fx var1) {
      return e(_snowman, _snowman) || _snowman.a(_snowman, var0x -> var0x.a(aed.s));
   }

   private static boolean f(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> var0x.a(bup.dP));
   }

   private static boolean g(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> var0x.a(bup.A));
   }

   public static boolean d(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> var0x.g() || var0x.a(aed.I));
   }

   private static boolean h(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> {
         buo _snowman = var0x.b();
         return b(_snowman) || _snowman == bup.bX;
      });
   }

   private static boolean i(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> {
         cva _snowman = var0x.c();
         return _snowman == cva.g;
      });
   }

   public static void b(bse var0, fx var1, ceh var2) {
      _snowman.a(_snowman, _snowman, 19);
   }

   public static boolean e(bsc var0, fx var1) {
      return d(_snowman, _snowman) || i(_snowman, _snowman) || g(_snowman, _snowman);
   }

   private boolean a(bsb var1, Random var2, fx var3, Set<fx> var4, Set<fx> var5, cra var6, cmz var7) {
      int _snowman = _snowman.g.a(_snowman);
      int _snowmanx = _snowman.f.a(_snowman, _snowman, _snowman);
      int _snowmanxx = _snowman - _snowmanx;
      int _snowmanxxx = _snowman.f.a(_snowman, _snowmanxx);
      fx _snowmanxxxx;
      if (!_snowman.e) {
         int _snowmanxxxxx = _snowman.a(chn.a.d, _snowman).v();
         int _snowmanxxxxxx = _snowman.a(chn.a.b, _snowman).v();
         if (_snowmanxxxxxx - _snowmanxxxxx > _snowman.i) {
            return false;
         }

         int _snowmanxxxxxxx;
         if (_snowman.l == chn.a.d) {
            _snowmanxxxxxxx = _snowmanxxxxx;
         } else if (_snowman.l == chn.a.b) {
            _snowmanxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxxxxxx = _snowman.a(_snowman.l, _snowman).v();
         }

         _snowmanxxxx = new fx(_snowman.u(), _snowmanxxxxxxx, _snowman.w());
      } else {
         _snowmanxxxx = _snowman;
      }

      if (_snowmanxxxx.v() < 1 || _snowmanxxxx.v() + _snowman + 1 > 256) {
         return false;
      } else if (!h(_snowman, _snowmanxxxx.c())) {
         return false;
      } else {
         OptionalInt _snowmanxxxxxxx = _snowman.h.c();
         int _snowmanxxxxxxxx = this.a(_snowman, _snowman, _snowmanxxxx, _snowman);
         if (_snowmanxxxxxxxx >= _snowman || _snowmanxxxxxxx.isPresent() && _snowmanxxxxxxxx >= _snowmanxxxxxxx.getAsInt()) {
            List<cnl.b> _snowmanxxxxxxxxx = _snowman.g.a(_snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxx, _snowman, _snowman, _snowman);
            _snowmanxxxxxxxxx.forEach(var8x -> _snowman.f.a(_snowman, _snowman, _snowman, _snowman, var8x, _snowman, _snowman, _snowman, _snowman));
            return true;
         } else {
            return false;
         }
      }
   }

   private int a(bsc var1, int var2, fx var3, cmz var4) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx <= _snowman + 1; _snowmanx++) {
         int _snowmanxx = _snowman.h.a(_snowman, _snowmanx);

         for (int _snowmanxxx = -_snowmanxx; _snowmanxxx <= _snowmanxx; _snowmanxxx++) {
            for (int _snowmanxxxx = -_snowmanxx; _snowmanxxxx <= _snowmanxx; _snowmanxxxx++) {
               _snowman.a(_snowman, _snowmanxxx, _snowmanx, _snowmanxxxx);
               if (!c(_snowman, _snowman) || !_snowman.j && f(_snowman, _snowman)) {
                  return _snowmanx - 2;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected void a(bse var1, fx var2, ceh var3) {
      b(_snowman, _snowman, _snowman);
   }

   public final boolean a(bsr var1, cfy var2, Random var3, fx var4, cmz var5) {
      Set<fx> _snowman = Sets.newHashSet();
      Set<fx> _snowmanx = Sets.newHashSet();
      Set<fx> _snowmanxx = Sets.newHashSet();
      cra _snowmanxxx = cra.a();
      boolean _snowmanxxxx = this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowman);
      if (_snowmanxxx.a <= _snowmanxxx.d && _snowmanxxxx && !_snowman.isEmpty()) {
         if (!_snowman.d.isEmpty()) {
            List<fx> _snowmanxxxxx = Lists.newArrayList(_snowman);
            List<fx> _snowmanxxxxxx = Lists.newArrayList(_snowmanx);
            _snowmanxxxxx.sort(Comparator.comparingInt(gr::v));
            _snowmanxxxxxx.sort(Comparator.comparingInt(gr::v));
            _snowman.d.forEach(var6x -> var6x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         }

         dcw _snowmanxxxxx = this.a(_snowman, _snowmanxxx, _snowman, _snowmanxx);
         ctb.a(_snowman, 3, _snowmanxxxxx, _snowmanxxx.a, _snowmanxxx.b, _snowmanxxx.c);
         return true;
      } else {
         return false;
      }
   }

   private dcw a(bry var1, cra var2, Set<fx> var3, Set<fx> var4) {
      List<Set<fx>> _snowman = Lists.newArrayList();
      dcw _snowmanx = new dcq(_snowman.d(), _snowman.e(), _snowman.f());
      int _snowmanxx = 6;

      for (int _snowmanxxx = 0; _snowmanxxx < 6; _snowmanxxx++) {
         _snowman.add(Sets.newHashSet());
      }

      fx.a _snowmanxxx = new fx.a();

      for (fx _snowmanxxxx : Lists.newArrayList(_snowman)) {
         if (_snowman.b(_snowmanxxxx)) {
            _snowmanx.a(_snowmanxxxx.u() - _snowman.a, _snowmanxxxx.v() - _snowman.b, _snowmanxxxx.w() - _snowman.c, true, true);
         }
      }

      for (fx _snowmanxxxxx : Lists.newArrayList(_snowman)) {
         if (_snowman.b(_snowmanxxxxx)) {
            _snowmanx.a(_snowmanxxxxx.u() - _snowman.a, _snowmanxxxxx.v() - _snowman.b, _snowmanxxxxx.w() - _snowman.c, true, true);
         }

         for (gc _snowmanxxxxxx : gc.values()) {
            _snowmanxxx.a(_snowmanxxxxx, _snowmanxxxxxx);
            if (!_snowman.contains(_snowmanxxx)) {
               ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxx);
               if (_snowmanxxxxxxx.b(cex.an)) {
                  _snowman.get(0).add(_snowmanxxx.h());
                  b(_snowman, _snowmanxxx, _snowmanxxxxxxx.a(cex.an, Integer.valueOf(1)));
                  if (_snowman.b(_snowmanxxx)) {
                     _snowmanx.a(_snowmanxxx.u() - _snowman.a, _snowmanxxx.v() - _snowman.b, _snowmanxxx.w() - _snowman.c, true, true);
                  }
               }
            }
         }
      }

      for (int _snowmanxxxxx = 1; _snowmanxxxxx < 6; _snowmanxxxxx++) {
         Set<fx> _snowmanxxxxxxx = _snowman.get(_snowmanxxxxx - 1);
         Set<fx> _snowmanxxxxxxxx = _snowman.get(_snowmanxxxxx);

         for (fx _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
            if (_snowman.b(_snowmanxxxxxxxxx)) {
               _snowmanx.a(_snowmanxxxxxxxxx.u() - _snowman.a, _snowmanxxxxxxxxx.v() - _snowman.b, _snowmanxxxxxxxxx.w() - _snowman.c, true, true);
            }

            for (gc _snowmanxxxxxxxxxx : gc.values()) {
               _snowmanxxx.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
               if (!_snowmanxxxxxxx.contains(_snowmanxxx) && !_snowmanxxxxxxxx.contains(_snowmanxxx)) {
                  ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxx);
                  if (_snowmanxxxxxxxxxxx.b(cex.an)) {
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.c(cex.an);
                     if (_snowmanxxxxxxxxxxxx > _snowmanxxxxx + 1) {
                        ceh _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(cex.an, Integer.valueOf(_snowmanxxxxx + 1));
                        b(_snowman, _snowmanxxx, _snowmanxxxxxxxxxxxxx);
                        if (_snowman.b(_snowmanxxx)) {
                           _snowmanx.a(_snowmanxxx.u() - _snowman.a, _snowmanxxx.v() - _snowman.b, _snowmanxxx.w() - _snowman.c, true, true);
                        }

                        _snowmanxxxxxxxx.add(_snowmanxxx.h());
                     }
                  }
               }
            }
         }
      }

      return _snowmanx;
   }
}
