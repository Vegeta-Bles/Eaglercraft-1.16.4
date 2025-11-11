import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class bnv {
   private static final nx a = new of("effect.none").a(k.h);

   public static List<apu> a(bmb var0) {
      return a(_snowman.o());
   }

   public static List<apu> a(bnt var0, Collection<apu> var1) {
      List<apu> _snowman = Lists.newArrayList();
      _snowman.addAll(_snowman.a());
      _snowman.addAll(_snowman);
      return _snowman;
   }

   public static List<apu> a(@Nullable md var0) {
      List<apu> _snowman = Lists.newArrayList();
      _snowman.addAll(c(_snowman).a());
      a(_snowman, _snowman);
      return _snowman;
   }

   public static List<apu> b(bmb var0) {
      return b(_snowman.o());
   }

   public static List<apu> b(@Nullable md var0) {
      List<apu> _snowman = Lists.newArrayList();
      a(_snowman, _snowman);
      return _snowman;
   }

   public static void a(@Nullable md var0, List<apu> var1) {
      if (_snowman != null && _snowman.c("CustomPotionEffects", 9)) {
         mj _snowman = _snowman.d("CustomPotionEffects", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            md _snowmanxx = _snowman.a(_snowmanx);
            apu _snowmanxxx = apu.b(_snowmanxx);
            if (_snowmanxxx != null) {
               _snowman.add(_snowmanxxx);
            }
         }
      }
   }

   public static int c(bmb var0) {
      md _snowman = _snowman.o();
      if (_snowman != null && _snowman.c("CustomPotionColor", 99)) {
         return _snowman.h("CustomPotionColor");
      } else {
         return d(_snowman) == bnw.a ? 16253176 : a(a(_snowman));
      }
   }

   public static int a(bnt var0) {
      return _snowman == bnw.a ? 16253176 : a(_snowman.a());
   }

   public static int a(Collection<apu> var0) {
      int _snowman = 3694022;
      if (_snowman.isEmpty()) {
         return 3694022;
      } else {
         float _snowmanx = 0.0F;
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 0.0F;
         int _snowmanxxxx = 0;

         for (apu _snowmanxxxxx : _snowman) {
            if (_snowmanxxxxx.e()) {
               int _snowmanxxxxxx = _snowmanxxxxx.a().f();
               int _snowmanxxxxxxx = _snowmanxxxxx.c() + 1;
               _snowmanx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 16 & 0xFF)) / 255.0F;
               _snowmanxx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 8 & 0xFF)) / 255.0F;
               _snowmanxxx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 0 & 0xFF)) / 255.0F;
               _snowmanxxxx += _snowmanxxxxxxx;
            }
         }

         if (_snowmanxxxx == 0) {
            return 0;
         } else {
            _snowmanx = _snowmanx / (float)_snowmanxxxx * 255.0F;
            _snowmanxx = _snowmanxx / (float)_snowmanxxxx * 255.0F;
            _snowmanxxx = _snowmanxxx / (float)_snowmanxxxx * 255.0F;
            return (int)_snowmanx << 16 | (int)_snowmanxx << 8 | (int)_snowmanxxx;
         }
      }
   }

   public static bnt d(bmb var0) {
      return c(_snowman.o());
   }

   public static bnt c(@Nullable md var0) {
      return _snowman == null ? bnw.a : bnt.a(_snowman.l("Potion"));
   }

   public static bmb a(bmb var0, bnt var1) {
      vk _snowman = gm.U.b(_snowman);
      if (_snowman == bnw.a) {
         _snowman.c("Potion");
      } else {
         _snowman.p().a("Potion", _snowman.toString());
      }

      return _snowman;
   }

   public static bmb a(bmb var0, Collection<apu> var1) {
      if (_snowman.isEmpty()) {
         return _snowman;
      } else {
         md _snowman = _snowman.p();
         mj _snowmanx = _snowman.d("CustomPotionEffects", 9);

         for (apu _snowmanxx : _snowman) {
            _snowmanx.add(_snowmanxx.a(new md()));
         }

         _snowman.a("CustomPotionEffects", _snowmanx);
         return _snowman;
      }
   }

   public static void a(bmb var0, List<nr> var1, float var2) {
      List<apu> _snowman = a(_snowman);
      List<Pair<arg, arj>> _snowmanx = Lists.newArrayList();
      if (_snowman.isEmpty()) {
         _snowman.add(a);
      } else {
         for (apu _snowmanxx : _snowman) {
            nx _snowmanxxx = new of(_snowmanxx.g());
            aps _snowmanxxxx = _snowmanxx.a();
            Map<arg, arj> _snowmanxxxxx = _snowmanxxxx.g();
            if (!_snowmanxxxxx.isEmpty()) {
               for (Entry<arg, arj> _snowmanxxxxxx : _snowmanxxxxx.entrySet()) {
                  arj _snowmanxxxxxxx = _snowmanxxxxxx.getValue();
                  arj _snowmanxxxxxxxx = new arj(_snowmanxxxxxxx.b(), _snowmanxxxx.a(_snowmanxx.c(), _snowmanxxxxxxx), _snowmanxxxxxxx.c());
                  _snowmanx.add(new Pair(_snowmanxxxxxx.getKey(), _snowmanxxxxxxxx));
               }
            }

            if (_snowmanxx.c() > 0) {
               _snowmanxxx = new of("potion.withAmplifier", _snowmanxxx, new of("potion.potency." + _snowmanxx.c()));
            }

            if (_snowmanxx.b() > 20) {
               _snowmanxxx = new of("potion.withDuration", _snowmanxxx, apv.a(_snowmanxx, _snowman));
            }

            _snowman.add(_snowmanxxx.a(_snowmanxxxx.e().a()));
         }
      }

      if (!_snowmanx.isEmpty()) {
         _snowman.add(oe.d);
         _snowman.add(new of("potion.whenDrank").a(k.f));

         for (Pair<arg, arj> _snowmanxx : _snowmanx) {
            arj _snowmanxxxxxx = (arj)_snowmanxx.getSecond();
            double _snowmanxxxxxxx = _snowmanxxxxxx.d();
            double _snowmanxxxxxxxx;
            if (_snowmanxxxxxx.c() != arj.a.b && _snowmanxxxxxx.c() != arj.a.c) {
               _snowmanxxxxxxxx = _snowmanxxxxxx.d();
            } else {
               _snowmanxxxxxxxx = _snowmanxxxxxx.d() * 100.0;
            }

            if (_snowmanxxxxxxx > 0.0) {
               _snowman.add(new of("attribute.modifier.plus." + _snowmanxxxxxx.c().a(), bmb.c.format(_snowmanxxxxxxxx), new of(((arg)_snowmanxx.getFirst()).c())).a(k.j));
            } else if (_snowmanxxxxxxx < 0.0) {
               _snowmanxxxxxxxx *= -1.0;
               _snowman.add(new of("attribute.modifier.take." + _snowmanxxxxxx.c().a(), bmb.c.format(_snowmanxxxxxxxx), new of(((arg)_snowmanxx.getFirst()).c())).a(k.m));
            }
         }
      }
   }
}
