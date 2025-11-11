import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;

public class dpi extends dot implements dwq.a {
   private static final vk a = new vk("textures/gui/advancements/window.png");
   private static final vk b = new vk("textures/gui/advancements/tabs.png");
   private static final nr c = new of("advancements.sad_label");
   private static final nr p = new of("advancements.empty");
   private static final nr q = new of("gui.advancements");
   private final dwq r;
   private final Map<y, dpe> s = Maps.newLinkedHashMap();
   private dpe t;
   private boolean u;

   public dpi(dwq var1) {
      super(dkz.a);
      this.r = _snowman;
   }

   @Override
   protected void b() {
      this.s.clear();
      this.t = null;
      this.r.a(this);
      if (this.t == null && !this.s.isEmpty()) {
         this.r.a(this.s.values().iterator().next().c(), true);
      } else {
         this.r.a(this.t == null ? null : this.t.c(), true);
      }
   }

   @Override
   public void e() {
      this.r.a(null);
      dwu _snowman = this.i.w();
      if (_snowman != null) {
         _snowman.a(tg.b());
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (_snowman == 0) {
         int _snowman = (this.k - 252) / 2;
         int _snowmanx = (this.l - 140) / 2;

         for (dpe _snowmanxx : this.s.values()) {
            if (_snowmanxx.a(_snowman, _snowmanx, _snowman, _snowman)) {
               this.r.a(_snowmanxx.c(), true);
               break;
            }
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (this.i.k.aB.a(_snowman, _snowman)) {
         this.i.a(null);
         this.i.l.i();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      int _snowman = (this.k - 252) / 2;
      int _snowmanx = (this.l - 140) / 2;
      this.a(_snowman);
      this.c(_snowman, _snowman, _snowman, _snowman, _snowmanx);
      this.a(_snowman, _snowman, _snowmanx);
      this.d(_snowman, _snowman, _snowman, _snowman, _snowmanx);
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (_snowman != 0) {
         this.u = false;
         return false;
      } else {
         if (!this.u) {
            this.u = true;
         } else if (this.t != null) {
            this.t.a(_snowman, _snowman);
         }

         return true;
      }
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5) {
      dpe _snowman = this.t;
      if (_snowman == null) {
         a(_snowman, _snowman + 9, _snowman + 18, _snowman + 9 + 234, _snowman + 18 + 113, -16777216);
         int _snowmanx = _snowman + 9 + 117;
         a(_snowman, this.o, p, _snowmanx, _snowman + 18 + 56 - 9 / 2, -1);
         a(_snowman, this.o, c, _snowmanx, _snowman + 18 + 113 - 9, -1);
      } else {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(_snowman + 9), (float)(_snowman + 18), 0.0F);
         _snowman.a(_snowman);
         RenderSystem.popMatrix();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
      }
   }

   public void a(dfm var1, int var2, int var3) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      this.i.M().a(a);
      this.b(_snowman, _snowman, _snowman, 0, 0, 252, 140);
      if (this.s.size() > 1) {
         this.i.M().a(b);

         for (dpe _snowman : this.s.values()) {
            _snowman.a(_snowman, _snowman, _snowman, _snowman == this.t);
         }

         RenderSystem.enableRescaleNormal();
         RenderSystem.defaultBlendFunc();

         for (dpe _snowman : this.s.values()) {
            _snowman.a(_snowman, _snowman, this.j);
         }

         RenderSystem.disableBlend();
      }

      this.o.b(_snowman, q, (float)(_snowman + 8), (float)(_snowman + 6), 4210752);
   }

   private void d(dfm var1, int var2, int var3, int var4, int var5) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.t != null) {
         RenderSystem.pushMatrix();
         RenderSystem.enableDepthTest();
         RenderSystem.translatef((float)(_snowman + 9), (float)(_snowman + 18), 400.0F);
         this.t.c(_snowman, _snowman - _snowman - 9, _snowman - _snowman - 18, _snowman, _snowman);
         RenderSystem.disableDepthTest();
         RenderSystem.popMatrix();
      }

      if (this.s.size() > 1) {
         for (dpe _snowman : this.s.values()) {
            if (_snowman.a(_snowman, _snowman, (double)_snowman, (double)_snowman)) {
               this.b(_snowman, _snowman.d(), _snowman, _snowman);
            }
         }
      }
   }

   @Override
   public void a(y var1) {
      dpe _snowman = dpe.a(this.i, this, this.s.size(), _snowman);
      if (_snowman != null) {
         this.s.put(_snowman, _snowman);
      }
   }

   @Override
   public void b(y var1) {
   }

   @Override
   public void c(y var1) {
      dpe _snowman = this.g(_snowman);
      if (_snowman != null) {
         _snowman.a(_snowman);
      }
   }

   @Override
   public void d(y var1) {
   }

   @Override
   public void a(y var1, aa var2) {
      dpg _snowman = this.f(_snowman);
      if (_snowman != null) {
         _snowman.a(_snowman);
      }
   }

   @Override
   public void e(@Nullable y var1) {
      this.t = this.s.get(_snowman);
   }

   @Override
   public void a() {
      this.s.clear();
      this.t = null;
   }

   @Nullable
   public dpg f(y var1) {
      dpe _snowman = this.g(_snowman);
      return _snowman == null ? null : _snowman.b(_snowman);
   }

   @Nullable
   private dpe g(y var1) {
      while (_snowman.b() != null) {
         _snowman = _snowman.b();
      }

      return this.s.get(_snowman);
   }
}
