import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class efo implements aci {
   public static final vk a = new vk("textures/misc/enchanted_item_glint.png");
   private static final Set<blx> c = Sets.newHashSet(new blx[]{bmd.a});
   public float b;
   private final ead d;
   private final ekd e;
   private final dks f;

   public efo(ekd var1, elt var2, dks var3) {
      this.e = _snowman;
      this.d = new ead(_snowman);

      for (blx _snowman : gm.T) {
         if (!c.contains(_snowman)) {
            this.d.a(_snowman, new elu(gm.T.b(_snowman), "inventory"));
         }
      }

      this.f = _snowman;
   }

   public ead a() {
      return this.d;
   }

   private void a(elo var1, bmb var2, int var3, int var4, dfm var5, dfq var6) {
      Random _snowman = new Random();
      long _snowmanx = 42L;

      for (gc _snowmanxx : gc.values()) {
         _snowman.setSeed(42L);
         this.a(_snowman, _snowman, _snowman.a(null, _snowmanxx, _snowman), _snowman, _snowman, _snowman);
      }

      _snowman.setSeed(42L);
      this.a(_snowman, _snowman, _snowman.a(null, null, _snowman), _snowman, _snowman, _snowman);
   }

   public void a(bmb var1, ebm.b var2, boolean var3, dfm var4, eag var5, int var6, int var7, elo var8) {
      if (!_snowman.a()) {
         _snowman.a();
         boolean _snowman = _snowman == ebm.b.g || _snowman == ebm.b.h || _snowman == ebm.b.i;
         if (_snowman.b() == bmd.qM && _snowman) {
            _snowman = this.d.a().a(new elu("minecraft:trident#inventory"));
         }

         _snowman.f().a(_snowman).a(_snowman, _snowman);
         _snowman.a(-0.5, -0.5, -0.5);
         if (!_snowman.d() && (_snowman.b() != bmd.qM || _snowman)) {
            boolean _snowmanx;
            if (_snowman != ebm.b.g && !_snowman.a() && _snowman.b() instanceof bkh) {
               buo _snowmanxx = ((bkh)_snowman.b()).e();
               _snowmanx = !(_snowmanxx instanceof bxi) && !(_snowmanxx instanceof caj);
            } else {
               _snowmanx = true;
            }

            eao _snowmanxx = eab.a(_snowman, _snowmanx);
            dfq _snowmanxxx;
            if (_snowman.b() == bmd.mh && _snowman.u()) {
               _snowman.a();
               dfm.a _snowmanxxxx = _snowman.c();
               if (_snowman == ebm.b.g) {
                  _snowmanxxxx.a().a(0.5F);
               } else if (_snowman.a()) {
                  _snowmanxxxx.a().a(0.75F);
               }

               if (_snowmanx) {
                  _snowmanxxx = b(_snowman, _snowmanxx, _snowmanxxxx);
               } else {
                  _snowmanxxx = a(_snowman, _snowmanxx, _snowmanxxxx);
               }

               _snowman.b();
            } else if (_snowmanx) {
               _snowmanxxx = c(_snowman, _snowmanxx, true, _snowman.u());
            } else {
               _snowmanxxx = b(_snowman, _snowmanxx, true, _snowman.u());
            }

            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxx);
         } else {
            dzs.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         }

         _snowman.b();
      }
   }

   public static dfq a(eag var0, eao var1, boolean var2, boolean var3) {
      return _snowman ? dft.a(_snowman.getBuffer(_snowman ? eao.k() : eao.l()), _snowman.getBuffer(_snowman)) : _snowman.getBuffer(_snowman);
   }

   public static dfq a(eag var0, eao var1, dfm.a var2) {
      return dft.a(new dfn(_snowman.getBuffer(eao.n()), _snowman.a(), _snowman.b()), _snowman.getBuffer(_snowman));
   }

   public static dfq b(eag var0, eao var1, dfm.a var2) {
      return dft.a(new dfn(_snowman.getBuffer(eao.o()), _snowman.a(), _snowman.b()), _snowman.getBuffer(_snowman));
   }

   public static dfq b(eag var0, eao var1, boolean var2, boolean var3) {
      if (_snowman) {
         return djz.A() && _snowman == ear.i() ? dft.a(_snowman.getBuffer(eao.m()), _snowman.getBuffer(_snowman)) : dft.a(_snowman.getBuffer(_snowman ? eao.n() : eao.p()), _snowman.getBuffer(_snowman));
      } else {
         return _snowman.getBuffer(_snowman);
      }
   }

   public static dfq c(eag var0, eao var1, boolean var2, boolean var3) {
      return _snowman ? dft.a(_snowman.getBuffer(_snowman ? eao.o() : eao.q()), _snowman.getBuffer(_snowman)) : _snowman.getBuffer(_snowman);
   }

   private void a(dfm var1, dfq var2, List<eba> var3, bmb var4, int var5, int var6) {
      boolean _snowman = !_snowman.a();
      dfm.a _snowmanx = _snowman.c();

      for (eba _snowmanxx : _snowman) {
         int _snowmanxxx = -1;
         if (_snowman && _snowmanxx.c()) {
            _snowmanxxx = this.f.a(_snowman, _snowmanxx.d());
         }

         float _snowmanxxxx = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
         float _snowmanxxxxx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
         float _snowmanxxxxxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
         _snowman.a(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowman, _snowman);
      }
   }

   public elo a(bmb var1, @Nullable brx var2, @Nullable aqm var3) {
      blx _snowman = _snowman.b();
      elo _snowmanx;
      if (_snowman == bmd.qM) {
         _snowmanx = this.d.a().a(new elu("minecraft:trident_in_hand#inventory"));
      } else {
         _snowmanx = this.d.b(_snowman);
      }

      dwt _snowmanxx = _snowman instanceof dwt ? (dwt)_snowman : null;
      elo _snowmanxxx = _snowmanx.g().a(_snowmanx, _snowman, _snowmanxx, _snowman);
      return _snowmanxxx == null ? this.d.a().a() : _snowmanxxx;
   }

   public void a(bmb var1, ebm.b var2, int var3, int var4, dfm var5, eag var6) {
      this.a(null, _snowman, _snowman, false, _snowman, _snowman, null, _snowman, _snowman);
   }

   public void a(@Nullable aqm var1, bmb var2, ebm.b var3, boolean var4, dfm var5, eag var6, @Nullable brx var7, int var8, int var9) {
      if (!_snowman.a()) {
         elo _snowman = this.a(_snowman, _snowman, _snowman);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(bmb var1, int var2, int var3) {
      this.a(_snowman, _snowman, _snowman, this.a(_snowman, null, null));
   }

   protected void a(bmb var1, int var2, int var3, elo var4) {
      RenderSystem.pushMatrix();
      this.e.a(ekb.d);
      this.e.b(ekb.d).a(false, false);
      RenderSystem.enableRescaleNormal();
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(dem.r.l, dem.j.j);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.translatef((float)_snowman, (float)_snowman, 100.0F + this.b);
      RenderSystem.translatef(8.0F, 8.0F, 0.0F);
      RenderSystem.scalef(1.0F, -1.0F, 1.0F);
      RenderSystem.scalef(16.0F, 16.0F, 16.0F);
      dfm _snowman = new dfm();
      eag.a _snowmanx = djz.C().aE().b();
      boolean _snowmanxx = !_snowman.c();
      if (_snowmanxx) {
         dep.c();
      }

      this.a(_snowman, ebm.b.g, false, _snowman, _snowmanx, 15728880, ejw.a, _snowman);
      _snowmanx.a();
      RenderSystem.enableDepthTest();
      if (_snowmanxx) {
         dep.d();
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableRescaleNormal();
      RenderSystem.popMatrix();
   }

   public void b(bmb var1, int var2, int var3) {
      this.b(djz.C().s, _snowman, _snowman, _snowman);
   }

   public void c(bmb var1, int var2, int var3) {
      this.b(null, _snowman, _snowman, _snowman);
   }

   public void a(aqm var1, bmb var2, int var3, int var4) {
      this.b(_snowman, _snowman, _snowman, _snowman);
   }

   private void b(@Nullable aqm var1, bmb var2, int var3, int var4) {
      if (!_snowman.a()) {
         this.b += 50.0F;

         try {
            this.a(_snowman, _snowman, _snowman, this.a(_snowman, null, _snowman));
         } catch (Throwable var8) {
            l _snowman = l.a(var8, "Rendering item");
            m _snowmanx = _snowman.a("Item being rendered");
            _snowmanx.a("Item Type", () -> String.valueOf(_snowman.b()));
            _snowmanx.a("Item Damage", () -> String.valueOf(_snowman.g()));
            _snowmanx.a("Item NBT", () -> String.valueOf(_snowman.o()));
            _snowmanx.a("Item Foil", () -> String.valueOf(_snowman.u()));
            throw new u(_snowman);
         }

         this.b -= 50.0F;
      }
   }

   public void a(dku var1, bmb var2, int var3, int var4) {
      this.a(_snowman, _snowman, _snowman, _snowman, null);
   }

   public void a(dku var1, bmb var2, int var3, int var4, @Nullable String var5) {
      if (!_snowman.a()) {
         dfm _snowman = new dfm();
         if (_snowman.E() != 1 || _snowman != null) {
            String _snowmanx = _snowman == null ? String.valueOf(_snowman.E()) : _snowman;
            _snowman.a(0.0, 0.0, (double)(this.b + 200.0F));
            eag.a _snowmanxx = eag.a(dfo.a().c());
            _snowman.a(_snowmanx, (float)(_snowman + 19 - 2 - _snowman.b(_snowmanx)), (float)(_snowman + 6 + 3), 16777215, true, _snowman.c().a(), _snowmanxx, false, 0, 15728880);
            _snowmanxx.a();
         }

         if (_snowman.f()) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            dfo _snowmanx = dfo.a();
            dfh _snowmanxx = _snowmanx.c();
            float _snowmanxxx = (float)_snowman.g();
            float _snowmanxxxx = (float)_snowman.h();
            float _snowmanxxxxx = Math.max(0.0F, (_snowmanxxxx - _snowmanxxx) / _snowmanxxxx);
            int _snowmanxxxxxx = Math.round(13.0F - _snowmanxxx * 13.0F / _snowmanxxxx);
            int _snowmanxxxxxxx = afm.f(_snowmanxxxxx / 3.0F, 1.0F, 1.0F);
            this.a(_snowmanxx, _snowman + 2, _snowman + 13, 13, 2, 0, 0, 0, 255);
            this.a(_snowmanxx, _snowman + 2, _snowman + 13, _snowmanxxxxxx, 1, _snowmanxxxxxxx >> 16 & 0xFF, _snowmanxxxxxxx >> 8 & 0xFF, _snowmanxxxxxxx & 0xFF, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

         dzm _snowmanx = djz.C().s;
         float _snowmanxx = _snowmanx == null ? 0.0F : _snowmanx.eT().a(_snowman.b(), djz.C().aj());
         if (_snowmanxx > 0.0F) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            dfo _snowmanxxx = dfo.a();
            dfh _snowmanxxxx = _snowmanxxx.c();
            this.a(_snowmanxxxx, _snowman, _snowman + afm.d(16.0F * (1.0F - _snowmanxx)), 16, afm.f(16.0F * _snowmanxx), 255, 255, 255, 127);
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }
      }
   }

   private void a(dfh var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      _snowman.a(7, dfk.l);
      _snowman.a((double)(_snowman + 0), (double)(_snowman + 0), 0.0).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a((double)(_snowman + 0), (double)(_snowman + _snowman), 0.0).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a((double)(_snowman + _snowman), (double)(_snowman + _snowman), 0.0).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a((double)(_snowman + _snowman), (double)(_snowman + 0), 0.0).a(_snowman, _snowman, _snowman, _snowman).d();
      dfo.a().b();
   }

   @Override
   public void a(ach var1) {
      this.d.b();
   }
}
