import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;

public class dpe extends dkw {
   private final djz a;
   private final dpi b;
   private final dpf c;
   private final int d;
   private final y e;
   private final ah i;
   private final bmb j;
   private final nr k;
   private final dpg l;
   private final Map<y, dpg> m = Maps.newLinkedHashMap();
   private double n;
   private double o;
   private int p = Integer.MAX_VALUE;
   private int q = Integer.MAX_VALUE;
   private int r = Integer.MIN_VALUE;
   private int s = Integer.MIN_VALUE;
   private float t;
   private boolean u;

   public dpe(djz var1, dpi var2, dpf var3, int var4, y var5, ah var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.i = _snowman;
      this.j = _snowman.c();
      this.k = _snowman.a();
      this.l = new dpg(this, _snowman, _snowman, _snowman);
      this.a(this.l, _snowman);
   }

   public y c() {
      return this.e;
   }

   public nr d() {
      return this.k;
   }

   public void a(dfm var1, int var2, int var3, boolean var4) {
      this.c.a(_snowman, this, _snowman, _snowman, _snowman, this.d);
   }

   public void a(int var1, int var2, efo var3) {
      this.c.a(_snowman, _snowman, this.d, _snowman, this.j);
   }

   public void a(dfm var1) {
      if (!this.u) {
         this.n = (double)(117 - (this.r + this.p) / 2);
         this.o = (double)(56 - (this.s + this.q) / 2);
         this.u = true;
      }

      RenderSystem.pushMatrix();
      RenderSystem.enableDepthTest();
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.colorMask(false, false, false, false);
      a(_snowman, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.depthFunc(518);
      a(_snowman, 234, 113, 0, 0, -16777216);
      RenderSystem.depthFunc(515);
      vk _snowman = this.i.d();
      if (_snowman != null) {
         this.a.M().a(_snowman);
      } else {
         this.a.M().a(ekd.a);
      }

      int _snowmanx = afm.c(this.n);
      int _snowmanxx = afm.c(this.o);
      int _snowmanxxx = _snowmanx % 16;
      int _snowmanxxxx = _snowmanxx % 16;

      for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 15; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 8; _snowmanxxxxxx++) {
            a(_snowman, _snowmanxxx + 16 * _snowmanxxxxx, _snowmanxxxx + 16 * _snowmanxxxxxx, 0.0F, 0.0F, 16, 16, 16, 16);
         }
      }

      this.l.a(_snowman, _snowmanx, _snowmanxx, true);
      this.l.a(_snowman, _snowmanx, _snowmanxx, false);
      this.l.a(_snowman, _snowmanx, _snowmanxx);
      RenderSystem.depthFunc(518);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.colorMask(false, false, false, false);
      a(_snowman, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.depthFunc(515);
      RenderSystem.popMatrix();
   }

   public void c(dfm var1, int var2, int var3, int var4, int var5) {
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 200.0F);
      a(_snowman, 0, 0, 234, 113, afm.d(this.t * 255.0F) << 24);
      boolean _snowman = false;
      int _snowmanx = afm.c(this.n);
      int _snowmanxx = afm.c(this.o);
      if (_snowman > 0 && _snowman < 234 && _snowman > 0 && _snowman < 113) {
         for (dpg _snowmanxxx : this.m.values()) {
            if (_snowmanxxx.a(_snowmanx, _snowmanxx, _snowman, _snowman)) {
               _snowman = true;
               _snowmanxxx.a(_snowman, _snowmanx, _snowmanxx, this.t, _snowman, _snowman);
               break;
            }
         }
      }

      RenderSystem.popMatrix();
      if (_snowman) {
         this.t = afm.a(this.t + 0.02F, 0.0F, 0.3F);
      } else {
         this.t = afm.a(this.t - 0.04F, 0.0F, 1.0F);
      }
   }

   public boolean a(int var1, int var2, double var3, double var5) {
      return this.c.a(_snowman, _snowman, this.d, _snowman, _snowman);
   }

   @Nullable
   public static dpe a(djz var0, dpi var1, int var2, y var3) {
      if (_snowman.c() == null) {
         return null;
      } else {
         for (dpf _snowman : dpf.values()) {
            if (_snowman < _snowman.a()) {
               return new dpe(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.c());
            }

            _snowman -= _snowman.a();
         }

         return null;
      }
   }

   public void a(double var1, double var3) {
      if (this.r - this.p > 234) {
         this.n = afm.a(this.n + _snowman, (double)(-(this.r - 234)), 0.0);
      }

      if (this.s - this.q > 113) {
         this.o = afm.a(this.o + _snowman, (double)(-(this.s - 113)), 0.0);
      }
   }

   public void a(y var1) {
      if (_snowman.c() != null) {
         dpg _snowman = new dpg(this, this.a, _snowman, _snowman.c());
         this.a(_snowman, _snowman);
      }
   }

   private void a(dpg var1, y var2) {
      this.m.put(_snowman, _snowman);
      int _snowman = _snowman.d();
      int _snowmanx = _snowman + 28;
      int _snowmanxx = _snowman.c();
      int _snowmanxxx = _snowmanxx + 27;
      this.p = Math.min(this.p, _snowman);
      this.r = Math.max(this.r, _snowmanx);
      this.q = Math.min(this.q, _snowmanxx);
      this.s = Math.max(this.s, _snowmanxxx);

      for (dpg _snowmanxxxx : this.m.values()) {
         _snowmanxxxx.b();
      }
   }

   @Nullable
   public dpg b(y var1) {
      return this.m.get(_snowman);
   }

   public dpi f() {
      return this.b;
   }
}
