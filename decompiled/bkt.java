import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bkt extends bmo implements bno {
   private boolean c = false;
   private boolean d = false;

   public bkt(blx.a var1) {
      super(_snowman);
   }

   @Override
   public Predicate<bmb> e() {
      return b;
   }

   @Override
   public Predicate<bmb> b() {
      return a;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      if (d(_snowman)) {
         a(_snowman, _snowman, _snowman, _snowman, m(_snowman), 1.0F);
         a(_snowman, false);
         return aov.b(_snowman);
      } else if (!_snowman.f(_snowman).a()) {
         if (!d(_snowman)) {
            this.c = false;
            this.d = false;
            _snowman.c(_snowman);
         }

         return aov.b(_snowman);
      } else {
         return aov.d(_snowman);
      }
   }

   @Override
   public void a(bmb var1, brx var2, aqm var3, int var4) {
      int _snowman = this.e_(_snowman) - _snowman;
      float _snowmanx = a(_snowman, _snowman);
      if (_snowmanx >= 1.0F && !d(_snowman) && a(_snowman, _snowman)) {
         a(_snowman, true);
         adr _snowmanxx = _snowman instanceof bfw ? adr.h : adr.f;
         _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.ct, _snowmanxx, 1.0F, 1.0F / (h.nextFloat() * 0.5F + 1.0F) + 0.2F);
      }
   }

   private static boolean a(aqm var0, bmb var1) {
      int _snowman = bpu.a(bpw.H, _snowman);
      int _snowmanx = _snowman == 0 ? 1 : 3;
      boolean _snowmanxx = _snowman instanceof bfw && ((bfw)_snowman).bC.d;
      bmb _snowmanxxx = _snowman.f(_snowman);
      bmb _snowmanxxxx = _snowmanxxx.i();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanx; _snowmanxxxxx++) {
         if (_snowmanxxxxx > 0) {
            _snowmanxxx = _snowmanxxxx.i();
         }

         if (_snowmanxxx.a() && _snowmanxx) {
            _snowmanxxx = new bmb(bmd.kd);
            _snowmanxxxx = _snowmanxxx.i();
         }

         if (!a(_snowman, _snowman, _snowmanxxx, _snowmanxxxxx > 0, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   private static boolean a(aqm var0, bmb var1, bmb var2, boolean var3, boolean var4) {
      if (_snowman.a()) {
         return false;
      } else {
         boolean _snowman = _snowman && _snowman.b() instanceof bkc;
         bmb _snowmanx;
         if (!_snowman && !_snowman && !_snowman) {
            _snowmanx = _snowman.a(1);
            if (_snowman.a() && _snowman instanceof bfw) {
               ((bfw)_snowman).bm.f(_snowman);
            }
         } else {
            _snowmanx = _snowman.i();
         }

         b(_snowman, _snowmanx);
         return true;
      }
   }

   public static boolean d(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null && _snowman.q("Charged");
   }

   public static void a(bmb var0, boolean var1) {
      md _snowman = _snowman.p();
      _snowman.a("Charged", _snowman);
   }

   private static void b(bmb var0, bmb var1) {
      md _snowman = _snowman.p();
      mj _snowmanx;
      if (_snowman.c("ChargedProjectiles", 9)) {
         _snowmanx = _snowman.d("ChargedProjectiles", 10);
      } else {
         _snowmanx = new mj();
      }

      md _snowmanxx = new md();
      _snowman.b(_snowmanxx);
      _snowmanx.add(_snowmanxx);
      _snowman.a("ChargedProjectiles", _snowmanx);
   }

   private static List<bmb> k(bmb var0) {
      List<bmb> _snowman = Lists.newArrayList();
      md _snowmanx = _snowman.o();
      if (_snowmanx != null && _snowmanx.c("ChargedProjectiles", 9)) {
         mj _snowmanxx = _snowmanx.d("ChargedProjectiles", 10);
         if (_snowmanxx != null) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
               md _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
               _snowman.add(bmb.a(_snowmanxxxx));
            }
         }
      }

      return _snowman;
   }

   private static void l(bmb var0) {
      md _snowman = _snowman.o();
      if (_snowman != null) {
         mj _snowmanx = _snowman.d("ChargedProjectiles", 9);
         _snowmanx.clear();
         _snowman.a("ChargedProjectiles", _snowmanx);
      }
   }

   public static boolean a(bmb var0, blx var1) {
      return k(_snowman).stream().anyMatch(var1x -> var1x.b() == _snowman);
   }

   private static void a(brx var0, aqm var1, aot var2, bmb var3, bmb var4, float var5, boolean var6, float var7, float var8, float var9) {
      if (!_snowman.v) {
         boolean _snowman = _snowman.b() == bmd.po;
         bgm _snowmanx;
         if (_snowman) {
            _snowmanx = new bgh(_snowman, _snowman, _snowman, _snowman.cD(), _snowman.cG() - 0.15F, _snowman.cH(), true);
         } else {
            _snowmanx = a(_snowman, _snowman, _snowman, _snowman);
            if (_snowman || _snowman != 0.0F) {
               ((bga)_snowmanx).d = bga.a.c;
            }
         }

         if (_snowman instanceof bdd) {
            bdd _snowmanxx = (bdd)_snowman;
            _snowmanxx.a(_snowmanxx.A(), _snowman, _snowmanx, _snowman);
         } else {
            dcn _snowmanxx = _snowman.i(1.0F);
            d _snowmanxxx = new d(new g(_snowmanxx), _snowman, true);
            dcn _snowmanxxxx = _snowman.f(1.0F);
            g _snowmanxxxxx = new g(_snowmanxxxx);
            _snowmanxxxxx.a(_snowmanxxx);
            _snowmanx.c((double)_snowmanxxxxx.a(), (double)_snowmanxxxxx.b(), (double)_snowmanxxxxx.c(), _snowman, _snowman);
         }

         _snowman.a(_snowman ? 3 : 1, _snowman, var1x -> var1x.d(_snowman));
         _snowman.c(_snowmanx);
         _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.cz, adr.h, 1.0F, _snowman);
      }
   }

   private static bga a(brx var0, aqm var1, bmb var2, bmb var3) {
      bkc _snowman = (bkc)(_snowman.b() instanceof bkc ? _snowman.b() : bmd.kd);
      bga _snowmanx = _snowman.a(_snowman, _snowman, _snowman);
      if (_snowman instanceof bfw) {
         _snowmanx.a(true);
      }

      _snowmanx.a(adq.cs);
      _snowmanx.p(true);
      int _snowmanxx = bpu.a(bpw.J, _snowman);
      if (_snowmanxx > 0) {
         _snowmanx.b((byte)_snowmanxx);
      }

      return _snowmanx;
   }

   public static void a(brx var0, aqm var1, aot var2, bmb var3, float var4, float var5) {
      List<bmb> _snowman = k(_snowman);
      float[] _snowmanx = a(_snowman.cY());

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.get(_snowmanxx);
         boolean _snowmanxxxx = _snowman instanceof bfw && ((bfw)_snowman).bC.d;
         if (!_snowmanxxx.a()) {
            if (_snowmanxx == 0) {
               a(_snowman, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, _snowman, _snowman, 0.0F);
            } else if (_snowmanxx == 1) {
               a(_snowman, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, _snowman, _snowman, -10.0F);
            } else if (_snowmanxx == 2) {
               a(_snowman, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, _snowman, _snowman, 10.0F);
            }
         }
      }

      a(_snowman, _snowman, _snowman);
   }

   private static float[] a(Random var0) {
      boolean _snowman = _snowman.nextBoolean();
      return new float[]{1.0F, a(_snowman), a(!_snowman)};
   }

   private static float a(boolean var0) {
      float _snowman = _snowman ? 0.63F : 0.43F;
      return 1.0F / (h.nextFloat() * 0.5F + 1.8F) + _snowman;
   }

   private static void a(brx var0, aqm var1, bmb var2) {
      if (_snowman instanceof aah) {
         aah _snowman = (aah)_snowman;
         if (!_snowman.v) {
            ac.F.a(_snowman, _snowman);
         }

         _snowman.b(aea.c.b(_snowman.b()));
      }

      l(_snowman);
   }

   @Override
   public void a(brx var1, aqm var2, bmb var3, int var4) {
      if (!_snowman.v) {
         int _snowman = bpu.a(bpw.I, _snowman);
         adp _snowmanx = this.a(_snowman);
         adp _snowmanxx = _snowman == 0 ? adq.cu : null;
         float _snowmanxxx = (float)(_snowman.k() - _snowman) / (float)g(_snowman);
         if (_snowmanxxx < 0.2F) {
            this.c = false;
            this.d = false;
         }

         if (_snowmanxxx >= 0.2F && !this.c) {
            this.c = true;
            _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowmanx, adr.h, 0.5F, 1.0F);
         }

         if (_snowmanxxx >= 0.5F && _snowmanxx != null && !this.d) {
            this.d = true;
            _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowmanxx, adr.h, 0.5F, 1.0F);
         }
      }
   }

   @Override
   public int e_(bmb var1) {
      return g(_snowman) + 3;
   }

   public static int g(bmb var0) {
      int _snowman = bpu.a(bpw.I, _snowman);
      return _snowman == 0 ? 25 : 25 - 5 * _snowman;
   }

   @Override
   public bnn d_(bmb var1) {
      return bnn.g;
   }

   private adp a(int var1) {
      switch (_snowman) {
         case 1:
            return adq.cw;
         case 2:
            return adq.cx;
         case 3:
            return adq.cy;
         default:
            return adq.cv;
      }
   }

   private static float a(int var0, bmb var1) {
      float _snowman = (float)_snowman / (float)g(_snowman);
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return _snowman;
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      List<bmb> _snowman = k(_snowman);
      if (d(_snowman) && !_snowman.isEmpty()) {
         bmb _snowmanx = _snowman.get(0);
         _snowman.add(new of("item.minecraft.crossbow.projectile").c(" ").a(_snowmanx.C()));
         if (_snowman.a() && _snowmanx.b() == bmd.po) {
            List<nr> _snowmanxx = Lists.newArrayList();
            bmd.po.a(_snowmanx, _snowman, _snowmanxx, _snowman);
            if (!_snowmanxx.isEmpty()) {
               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
                  _snowmanxx.set(_snowmanxxx, new oe("  ").a(_snowmanxx.get(_snowmanxxx)).a(k.h));
               }

               _snowman.addAll(_snowmanxx);
            }
         }
      }
   }

   private static float m(bmb var0) {
      return _snowman.b() == bmd.qQ && a(_snowman, bmd.po) ? 1.6F : 3.15F;
   }

   @Override
   public int d() {
      return 8;
   }
}
