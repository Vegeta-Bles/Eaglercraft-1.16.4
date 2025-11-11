import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class dot extends dmg implements dmc, dmf {
   private static final Logger a = LogManager.getLogger();
   private static final Set<String> b = Sets.newHashSet(new String[]{"http", "https"});
   protected final nr d;
   protected final List<dmi> e = Lists.newArrayList();
   @Nullable
   protected djz i;
   protected efo j;
   public int k;
   public int l;
   protected final List<dlh> m = Lists.newArrayList();
   public boolean n;
   protected dku o;
   private URI c;

   protected dot(nr var1) {
      this.d = _snowman;
   }

   public nr w() {
      return this.d;
   }

   public String ax_() {
      return this.w().getString();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      for (int _snowman = 0; _snowman < this.m.size(); _snowman++) {
         this.m.get(_snowman).a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256 && this.as_()) {
         this.at_();
         return true;
      } else if (_snowman == 258) {
         boolean _snowman = !y();
         if (!this.c_(_snowman)) {
            this.c_(_snowman);
         }

         return false;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   public boolean as_() {
      return true;
   }

   public void at_() {
      this.i.a(null);
   }

   protected <T extends dlh> T a(T var1) {
      this.m.add(_snowman);
      return this.d(_snowman);
   }

   protected <T extends dmi> T d(T var1) {
      this.e.add(_snowman);
      return _snowman;
   }

   protected void a(dfm var1, bmb var2, int var3, int var4) {
      this.b(_snowman, this.a(_snowman), _snowman, _snowman);
   }

   public List<nr> a(bmb var1) {
      return _snowman.a(this.i.s, this.i.k.p ? bnl.a.b : bnl.a.a);
   }

   public void b(dfm var1, nr var2, int var3, int var4) {
      this.c(_snowman, Arrays.asList(_snowman.f()), _snowman, _snowman);
   }

   public void b(dfm var1, List<nr> var2, int var3, int var4) {
      this.c(_snowman, Lists.transform(_snowman, nr::f), _snowman, _snowman);
   }

   public void c(dfm var1, List<? extends afa> var2, int var3, int var4) {
      if (!_snowman.isEmpty()) {
         int _snowman = 0;

         for (afa _snowmanx : _snowman) {
            int _snowmanxx = this.o.a(_snowmanx);
            if (_snowmanxx > _snowman) {
               _snowman = _snowmanxx;
            }
         }

         int _snowmanxx = _snowman + 12;
         int _snowmanxxx = _snowman - 12;
         int _snowmanxxxx = 8;
         if (_snowman.size() > 1) {
            _snowmanxxxx += 2 + (_snowman.size() - 1) * 10;
         }

         if (_snowmanxx + _snowman > this.k) {
            _snowmanxx -= 28 + _snowman;
         }

         if (_snowmanxxx + _snowmanxxxx + 6 > this.l) {
            _snowmanxxx = this.l - _snowmanxxxx - 6;
         }

         _snowman.a();
         int _snowmanxxxxx = -267386864;
         int _snowmanxxxxxx = 1347420415;
         int _snowmanxxxxxxx = 1344798847;
         int _snowmanxxxxxxxx = 400;
         dfo _snowmanxxxxxxxxx = dfo.a();
         dfh _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.c();
         _snowmanxxxxxxxxxx.a(7, dfk.l);
         b _snowmanxxxxxxxxxxx = _snowman.c().a();
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 4, _snowmanxx + _snowman + 3, _snowmanxxx - 3, 400, -267386864, -267386864);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 3, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 4, 400, -267386864, -267386864);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 4, _snowmanxxx - 3, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx + _snowman + 3, _snowmanxxx - 3, _snowmanxx + _snowman + 4, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3 + 1, _snowmanxx - 3 + 1, _snowmanxxx + _snowmanxxxx + 3 - 1, 400, 1347420415, 1344798847);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx + _snowman + 2, _snowmanxxx - 3 + 1, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3 - 1, 400, 1347420415, 1344798847);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3, _snowmanxx + _snowman + 3, _snowmanxxx - 3 + 1, 400, 1347420415, 1347420415);
         a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 2, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3, 400, 1344798847, 1344798847);
         RenderSystem.enableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.shadeModel(7425);
         _snowmanxxxxxxxxxx.c();
         dfi.a(_snowmanxxxxxxxxxx);
         RenderSystem.shadeModel(7424);
         RenderSystem.disableBlend();
         RenderSystem.enableTexture();
         eag.a _snowmanxxxxxxxxxxxx = eag.a(dfo.a().c());
         _snowman.a(0.0, 0.0, 400.0);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowman.size(); _snowmanxxxxxxxxxxxxx++) {
            afa _snowmanxxxxxxxxxxxxxx = _snowman.get(_snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx != null) {
               this.o.a(_snowmanxxxxxxxxxxxxxx, (float)_snowmanxx, (float)_snowmanxxx, -1, true, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, false, 0, 15728880);
            }

            if (_snowmanxxxxxxxxxxxxx == 0) {
               _snowmanxxx += 2;
            }

            _snowmanxxx += 10;
         }

         _snowmanxxxxxxxxxxxx.a();
         _snowman.b();
      }
   }

   protected void a(dfm var1, @Nullable ob var2, int var3, int var4) {
      if (_snowman != null && _snowman.i() != null) {
         nv _snowman = _snowman.i();
         nv.c _snowmanx = _snowman.a(nv.a.b);
         if (_snowmanx != null) {
            this.a(_snowman, _snowmanx.a(), _snowman, _snowman);
         } else {
            nv.b _snowmanxx = _snowman.a(nv.a.c);
            if (_snowmanxx != null) {
               if (this.i.k.p) {
                  this.b(_snowman, _snowmanxx.b(), _snowman, _snowman);
               }
            } else {
               nr _snowmanxxx = _snowman.a(nv.a.a);
               if (_snowmanxxx != null) {
                  this.c(_snowman, this.i.g.b(_snowmanxxx, Math.max(this.k / 2, 200)), _snowman, _snowman);
               }
            }
         }
      }
   }

   protected void a(String var1, boolean var2) {
   }

   public boolean a(@Nullable ob var1) {
      if (_snowman == null) {
         return false;
      } else {
         np _snowman = _snowman.h();
         if (y()) {
            if (_snowman.j() != null) {
               this.a(_snowman.j(), false);
            }
         } else if (_snowman != null) {
            if (_snowman.a() == np.a.a) {
               if (!this.i.k.M) {
                  return false;
               }

               try {
                  URI _snowmanx = new URI(_snowman.b());
                  String _snowmanxx = _snowmanx.getScheme();
                  if (_snowmanxx == null) {
                     throw new URISyntaxException(_snowman.b(), "Missing protocol");
                  }

                  if (!b.contains(_snowmanxx.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(_snowman.b(), "Unsupported protocol: " + _snowmanxx.toLowerCase(Locale.ROOT));
                  }

                  if (this.i.k.N) {
                     this.c = _snowmanx;
                     this.i.a(new dnr(this::c, _snowman.b(), false));
                  } else {
                     this.a(_snowmanx);
                  }
               } catch (URISyntaxException var5) {
                  a.error("Can't open url for {}", _snowman, var5);
               }
            } else if (_snowman.a() == np.a.b) {
               URI _snowmanxxx = new File(_snowman.b()).toURI();
               this.a(_snowmanxxx);
            } else if (_snowman.a() == np.a.d) {
               this.a(_snowman.b(), true);
            } else if (_snowman.a() == np.a.c) {
               this.b(_snowman.b(), false);
            } else if (_snowman.a() == np.a.f) {
               this.i.m.a(_snowman.b());
            } else {
               a.error("Don't know how to handle {}", _snowman);
            }

            return true;
         }

         return false;
      }
   }

   public void b_(String var1) {
      this.b(_snowman, true);
   }

   public void b(String var1, boolean var2) {
      if (_snowman) {
         this.i.j.c().a(_snowman);
      }

      this.i.s.f(_snowman);
   }

   public void b(djz var1, int var2, int var3) {
      this.i = _snowman;
      this.j = _snowman.ad();
      this.o = _snowman.g;
      this.k = _snowman;
      this.l = _snowman;
      this.m.clear();
      this.e.clear();
      this.a(null);
      this.b();
   }

   @Override
   public List<? extends dmi> au_() {
      return this.e;
   }

   protected void b() {
   }

   @Override
   public void d() {
   }

   public void e() {
   }

   public void a(dfm var1) {
      this.a(_snowman, 0);
   }

   public void a(dfm var1, int var2) {
      if (this.i.r != null) {
         this.a(_snowman, 0, 0, this.k, this.l, -1072689136, -804253680);
      } else {
         this.e(_snowman);
      }
   }

   public void e(int var1) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      this.i.M().a(f);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxx = 32.0F;
      _snowmanx.a(7, dfk.p);
      _snowmanx.a(0.0, (double)this.l, 0.0).a(0.0F, (float)this.l / 32.0F + (float)_snowman).a(64, 64, 64, 255).d();
      _snowmanx.a((double)this.k, (double)this.l, 0.0).a((float)this.k / 32.0F, (float)this.l / 32.0F + (float)_snowman).a(64, 64, 64, 255).d();
      _snowmanx.a((double)this.k, 0.0, 0.0).a((float)this.k / 32.0F, (float)_snowman).a(64, 64, 64, 255).d();
      _snowmanx.a(0.0, 0.0, 0.0).a(0.0F, (float)_snowman).a(64, 64, 64, 255).d();
      _snowman.b();
   }

   public boolean ay_() {
      return true;
   }

   private void c(boolean var1) {
      if (_snowman) {
         this.a(this.c);
      }

      this.c = null;
      this.i.a(this);
   }

   private void a(URI var1) {
      x.i().a(_snowman);
   }

   public static boolean x() {
      return djz.a ? deo.a(djz.C().aD().i(), 343) || deo.a(djz.C().aD().i(), 347) : deo.a(djz.C().aD().i(), 341) || deo.a(djz.C().aD().i(), 345);
   }

   public static boolean y() {
      return deo.a(djz.C().aD().i(), 340) || deo.a(djz.C().aD().i(), 344);
   }

   public static boolean z() {
      return deo.a(djz.C().aD().i(), 342) || deo.a(djz.C().aD().i(), 346);
   }

   public static boolean f(int var0) {
      return _snowman == 88 && x() && !y() && !z();
   }

   public static boolean g(int var0) {
      return _snowman == 86 && x() && !y() && !z();
   }

   public static boolean h(int var0) {
      return _snowman == 67 && x() && !y() && !z();
   }

   public static boolean i(int var0) {
      return _snowman == 65 && x() && !y() && !z();
   }

   public void a(djz var1, int var2, int var3) {
      this.b(_snowman, _snowman, _snowman);
   }

   public static void a(Runnable var0, String var1, String var2) {
      try {
         _snowman.run();
      } catch (Throwable var6) {
         l _snowman = l.a(var6, _snowman);
         m _snowmanx = _snowman.a("Affected screen");
         _snowmanx.a("Screen name", () -> _snowman);
         throw new u(_snowman);
      }
   }

   protected boolean a(String var1, char var2, int var3) {
      int _snowman = _snowman.indexOf(58);
      int _snowmanx = _snowman.indexOf(47);
      if (_snowman == ':') {
         return (_snowmanx == -1 || _snowman <= _snowmanx) && _snowman == -1;
      } else {
         return _snowman == '/' ? _snowman > _snowman : _snowman == '_' || _snowman == '-' || _snowman >= 'a' && _snowman <= 'z' || _snowman >= '0' && _snowman <= '9' || _snowman == '.';
      }
   }

   @Override
   public boolean b(double var1, double var3) {
      return true;
   }

   public void a(List<Path> var1) {
   }
}
