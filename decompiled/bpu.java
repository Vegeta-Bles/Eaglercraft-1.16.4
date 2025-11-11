import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class bpu {
   public static int a(bps var0, bmb var1) {
      if (_snowman.a()) {
         return 0;
      } else {
         vk _snowman = gm.R.b(_snowman);
         mj _snowmanx = _snowman.q();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            md _snowmanxxx = _snowmanx.a(_snowmanxx);
            vk _snowmanxxxx = vk.a(_snowmanxxx.l("id"));
            if (_snowmanxxxx != null && _snowmanxxxx.equals(_snowman)) {
               return afm.a(_snowmanxxx.h("lvl"), 0, 255);
            }
         }

         return 0;
      }
   }

   public static Map<bps, Integer> a(bmb var0) {
      mj _snowman = _snowman.b() == bmd.pq ? blf.d(_snowman) : _snowman.q();
      return a(_snowman);
   }

   public static Map<bps, Integer> a(mj var0) {
      Map<bps, Integer> _snowman = Maps.newLinkedHashMap();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         md _snowmanxx = _snowman.a(_snowmanx);
         gm.R.b(vk.a(_snowmanxx.l("id"))).ifPresent(var2x -> {
            Integer var10000 = _snowman.put(var2x, _snowman.h("lvl"));
         });
      }

      return _snowman;
   }

   public static void a(Map<bps, Integer> var0, bmb var1) {
      mj _snowman = new mj();

      for (Entry<bps, Integer> _snowmanx : _snowman.entrySet()) {
         bps _snowmanxx = _snowmanx.getKey();
         if (_snowmanxx != null) {
            int _snowmanxxx = _snowmanx.getValue();
            md _snowmanxxxx = new md();
            _snowmanxxxx.a("id", String.valueOf(gm.R.b(_snowmanxx)));
            _snowmanxxxx.a("lvl", (short)_snowmanxxx);
            _snowman.add(_snowmanxxxx);
            if (_snowman.b() == bmd.pq) {
               blf.a(_snowman, new bpv(_snowmanxx, _snowmanxxx));
            }
         }
      }

      if (_snowman.isEmpty()) {
         _snowman.c("Enchantments");
      } else if (_snowman.b() != bmd.pq) {
         _snowman.a("Enchantments", _snowman);
      }
   }

   private static void a(bpu.a var0, bmb var1) {
      if (!_snowman.a()) {
         mj _snowman = _snowman.q();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            String _snowmanxx = _snowman.a(_snowmanx).l("id");
            int _snowmanxxx = _snowman.a(_snowmanx).h("lvl");
            gm.R.b(vk.a(_snowmanxx)).ifPresent(var2x -> _snowman.accept(var2x, _snowman));
         }
      }
   }

   private static void a(bpu.a var0, Iterable<bmb> var1) {
      for (bmb _snowman : _snowman) {
         a(_snowman, _snowman);
      }
   }

   public static int a(Iterable<bmb> var0, apk var1) {
      MutableInt _snowman = new MutableInt();
      a((var2x, var3) -> _snowman.add(var2x.a(var3, _snowman)), _snowman);
      return _snowman.intValue();
   }

   public static float a(bmb var0, aqq var1) {
      MutableFloat _snowman = new MutableFloat();
      a((var2x, var3) -> _snowman.add(var2x.a(var3, _snowman)), _snowman);
      return _snowman.floatValue();
   }

   public static float a(aqm var0) {
      int _snowman = a(bpw.s, _snowman);
      return _snowman > 0 ? bqi.e(_snowman) : 0.0F;
   }

   public static void a(aqm var0, aqa var1) {
      bpu.a _snowman = (var2x, var3) -> var2x.b(_snowman, _snowman, var3);
      if (_snowman != null) {
         a(_snowman, _snowman.bp());
      }

      if (_snowman instanceof bfw) {
         a(_snowman, _snowman.dD());
      }
   }

   public static void b(aqm var0, aqa var1) {
      bpu.a _snowman = (var2x, var3) -> var2x.a(_snowman, _snowman, var3);
      if (_snowman != null) {
         a(_snowman, _snowman.bp());
      }

      if (_snowman instanceof bfw) {
         a(_snowman, _snowman.dD());
      }
   }

   public static int a(bps var0, aqm var1) {
      Iterable<bmb> _snowman = _snowman.a(_snowman).values();
      if (_snowman == null) {
         return 0;
      } else {
         int _snowmanx = 0;

         for (bmb _snowmanxx : _snowman) {
            int _snowmanxxx = a(_snowman, _snowmanxx);
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }

         return _snowmanx;
      }
   }

   public static int b(aqm var0) {
      return a(bpw.p, _snowman);
   }

   public static int c(aqm var0) {
      return a(bpw.q, _snowman);
   }

   public static int d(aqm var0) {
      return a(bpw.f, _snowman);
   }

   public static int e(aqm var0) {
      return a(bpw.i, _snowman);
   }

   public static int f(aqm var0) {
      return a(bpw.t, _snowman);
   }

   public static int b(bmb var0) {
      return a(bpw.B, _snowman);
   }

   public static int c(bmb var0) {
      return a(bpw.C, _snowman);
   }

   public static int g(aqm var0) {
      return a(bpw.r, _snowman);
   }

   public static boolean h(aqm var0) {
      return a(bpw.g, _snowman) > 0;
   }

   public static boolean i(aqm var0) {
      return a(bpw.j, _snowman) > 0;
   }

   public static boolean j(aqm var0) {
      return a(bpw.l, _snowman) > 0;
   }

   public static boolean d(bmb var0) {
      return a(bpw.k, _snowman) > 0;
   }

   public static boolean e(bmb var0) {
      return a(bpw.L, _snowman) > 0;
   }

   public static int f(bmb var0) {
      return a(bpw.D, _snowman);
   }

   public static int g(bmb var0) {
      return a(bpw.F, _snowman);
   }

   public static boolean h(bmb var0) {
      return a(bpw.G, _snowman) > 0;
   }

   @Nullable
   public static Entry<aqf, bmb> b(bps var0, aqm var1) {
      return a(_snowman, _snowman, var0x -> true);
   }

   @Nullable
   public static Entry<aqf, bmb> a(bps var0, aqm var1, Predicate<bmb> var2) {
      Map<aqf, bmb> _snowman = _snowman.a(_snowman);
      if (_snowman.isEmpty()) {
         return null;
      } else {
         List<Entry<aqf, bmb>> _snowmanx = Lists.newArrayList();

         for (Entry<aqf, bmb> _snowmanxx : _snowman.entrySet()) {
            bmb _snowmanxxx = _snowmanxx.getValue();
            if (!_snowmanxxx.a() && a(_snowman, _snowmanxxx) > 0 && _snowman.test(_snowmanxxx)) {
               _snowmanx.add(_snowmanxx);
            }
         }

         return _snowmanx.isEmpty() ? null : _snowmanx.get(_snowman.cY().nextInt(_snowmanx.size()));
      }
   }

   public static int a(Random var0, int var1, int var2, bmb var3) {
      blx _snowman = _snowman.b();
      int _snowmanx = _snowman.c();
      if (_snowmanx <= 0) {
         return 0;
      } else {
         if (_snowman > 15) {
            _snowman = 15;
         }

         int _snowmanxx = _snowman.nextInt(8) + 1 + (_snowman >> 1) + _snowman.nextInt(_snowman + 1);
         if (_snowman == 0) {
            return Math.max(_snowmanxx / 3, 1);
         } else {
            return _snowman == 1 ? _snowmanxx * 2 / 3 + 1 : Math.max(_snowmanxx, _snowman * 2);
         }
      }
   }

   public static bmb a(Random var0, bmb var1, int var2, boolean var3) {
      List<bpv> _snowman = b(_snowman, _snowman, _snowman, _snowman);
      boolean _snowmanx = _snowman.b() == bmd.mc;
      if (_snowmanx) {
         _snowman = new bmb(bmd.pq);
      }

      for (bpv _snowmanxx : _snowman) {
         if (_snowmanx) {
            blf.a(_snowman, _snowmanxx);
         } else {
            _snowman.a(_snowmanxx.b, _snowmanxx.c);
         }
      }

      return _snowman;
   }

   public static List<bpv> b(Random var0, bmb var1, int var2, boolean var3) {
      List<bpv> _snowman = Lists.newArrayList();
      blx _snowmanx = _snowman.b();
      int _snowmanxx = _snowmanx.c();
      if (_snowmanxx <= 0) {
         return _snowman;
      } else {
         _snowman += 1 + _snowman.nextInt(_snowmanxx / 4 + 1) + _snowman.nextInt(_snowmanxx / 4 + 1);
         float _snowmanxxx = (_snowman.nextFloat() + _snowman.nextFloat() - 1.0F) * 0.15F;
         _snowman = afm.a(Math.round((float)_snowman + (float)_snowman * _snowmanxxx), 1, Integer.MAX_VALUE);
         List<bpv> _snowmanxxxx = a(_snowman, _snowman, _snowman);
         if (!_snowmanxxxx.isEmpty()) {
            _snowman.add(afz.a(_snowman, _snowmanxxxx));

            while (_snowman.nextInt(50) <= _snowman) {
               a(_snowmanxxxx, x.a(_snowman));
               if (_snowmanxxxx.isEmpty()) {
                  break;
               }

               _snowman.add(afz.a(_snowman, _snowmanxxxx));
               _snowman /= 2;
            }
         }

         return _snowman;
      }
   }

   public static void a(List<bpv> var0, bpv var1) {
      Iterator<bpv> _snowman = _snowman.iterator();

      while (_snowman.hasNext()) {
         if (!_snowman.b.b(_snowman.next().b)) {
            _snowman.remove();
         }
      }
   }

   public static boolean a(Collection<bps> var0, bps var1) {
      for (bps _snowman : _snowman) {
         if (!_snowman.b(_snowman)) {
            return false;
         }
      }

      return true;
   }

   public static List<bpv> a(int var0, bmb var1, boolean var2) {
      List<bpv> _snowman = Lists.newArrayList();
      blx _snowmanx = _snowman.b();
      boolean _snowmanxx = _snowman.b() == bmd.mc;

      for (bps _snowmanxxx : gm.R) {
         if ((!_snowmanxxx.b() || _snowman) && _snowmanxxx.i() && (_snowmanxxx.b.a(_snowmanx) || _snowmanxx)) {
            for (int _snowmanxxxx = _snowmanxxx.a(); _snowmanxxxx > _snowmanxxx.e() - 1; _snowmanxxxx--) {
               if (_snowman >= _snowmanxxx.a(_snowmanxxxx) && _snowman <= _snowmanxxx.b(_snowmanxxxx)) {
                  _snowman.add(new bpv(_snowmanxxx, _snowmanxxxx));
                  break;
               }
            }
         }
      }

      return _snowman;
   }

   @FunctionalInterface
   interface a {
      void accept(bps var1, int var2);
   }
}
