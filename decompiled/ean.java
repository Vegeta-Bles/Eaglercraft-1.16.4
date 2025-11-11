import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.annotation.Nullable;

public abstract class ean {
   protected final String a;
   private final Runnable V;
   private final Runnable W;
   protected static final ean.q b = new ean.q("no_transparency", () -> RenderSystem.disableBlend(), () -> {
   });
   protected static final ean.q c = new ean.q("additive_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(dem.r.e, dem.j.e);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ean.q d = new ean.q("lightning_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(dem.r.l, dem.j.e);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ean.q e = new ean.q("glint_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(dem.r.n, dem.j.e, dem.r.o, dem.j.e);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ean.q f = new ean.q("crumbling_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(dem.r.d, dem.j.m, dem.r.e, dem.j.n);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ean.q g = new ean.q("translucent_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.e, dem.j.j);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ean.a h = new ean.a(0.0F);
   protected static final ean.a i = new ean.a(0.003921569F);
   protected static final ean.a j = new ean.a(0.5F);
   protected static final ean.n k = new ean.n(false);
   protected static final ean.n l = new ean.n(true);
   protected static final ean.o m = new ean.o(ekb.d, false, true);
   protected static final ean.o n = new ean.o(ekb.d, false, false);
   protected static final ean.o o = new ean.o();
   protected static final ean.p p = new ean.p("default_texturing", () -> {
   }, () -> {
   });
   protected static final ean.p q = new ean.p("outline_texturing", () -> RenderSystem.setupOutline(), () -> RenderSystem.teardownOutline());
   protected static final ean.p r = new ean.p("glint_texturing", () -> a(8.0F), () -> {
      RenderSystem.matrixMode(5890);
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
   });
   protected static final ean.p s = new ean.p("entity_glint_texturing", () -> a(0.16F), () -> {
      RenderSystem.matrixMode(5890);
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
   });
   protected static final ean.h t = new ean.h(true);
   protected static final ean.h u = new ean.h(false);
   protected static final ean.l v = new ean.l(true);
   protected static final ean.l w = new ean.l(false);
   protected static final ean.e x = new ean.e(true);
   protected static final ean.e y = new ean.e(false);
   protected static final ean.c z = new ean.c(true);
   protected static final ean.c A = new ean.c(false);
   protected static final ean.d B = new ean.d("always", 519);
   protected static final ean.d C = new ean.d("==", 514);
   protected static final ean.d D = new ean.d("<=", 515);
   protected static final ean.r E = new ean.r(true, true);
   protected static final ean.r F = new ean.r(true, false);
   protected static final ean.r G = new ean.r(false, true);
   protected static final ean.g H = new ean.g("no_layering", () -> {
   }, () -> {
   });
   protected static final ean.g I = new ean.g("polygon_offset_layering", () -> {
      RenderSystem.polygonOffset(-1.0F, -10.0F);
      RenderSystem.enablePolygonOffset();
   }, () -> {
      RenderSystem.polygonOffset(0.0F, 0.0F);
      RenderSystem.disablePolygonOffset();
   });
   protected static final ean.g J = new ean.g("view_offset_z_layering", () -> {
      RenderSystem.pushMatrix();
      RenderSystem.scalef(0.99975586F, 0.99975586F, 0.99975586F);
   }, RenderSystem::popMatrix);
   protected static final ean.f K = new ean.f("no_fog", () -> {
   }, () -> {
   });
   protected static final ean.f L = new ean.f("fog", () -> {
      dzy.b();
      RenderSystem.enableFog();
   }, () -> RenderSystem.disableFog());
   protected static final ean.f M = new ean.f("black_fog", () -> {
      RenderSystem.fog(2918, 0.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.enableFog();
   }, () -> {
      dzy.b();
      RenderSystem.disableFog();
   });
   protected static final ean.k N = new ean.k("main_target", () -> {
   }, () -> {
   });
   protected static final ean.k O = new ean.k("outline_target", () -> djz.C().e.p().a(false), () -> djz.C().f().a(false));
   protected static final ean.k P = new ean.k("translucent_target", () -> {
      if (djz.A()) {
         djz.C().e.q().a(false);
      }
   }, () -> {
      if (djz.A()) {
         djz.C().f().a(false);
      }
   });
   protected static final ean.k Q = new ean.k("particles_target", () -> {
      if (djz.A()) {
         djz.C().e.s().a(false);
      }
   }, () -> {
      if (djz.A()) {
         djz.C().f().a(false);
      }
   });
   protected static final ean.k R = new ean.k("weather_target", () -> {
      if (djz.A()) {
         djz.C().e.t().a(false);
      }
   }, () -> {
      if (djz.A()) {
         djz.C().f().a(false);
      }
   });
   protected static final ean.k S = new ean.k("clouds_target", () -> {
      if (djz.A()) {
         djz.C().e.u().a(false);
      }
   }, () -> {
      if (djz.A()) {
         djz.C().f().a(false);
      }
   });
   protected static final ean.k T = new ean.k("item_entity_target", () -> {
      if (djz.A()) {
         djz.C().e.r().a(false);
      }
   }, () -> {
      if (djz.A()) {
         djz.C().f().a(false);
      }
   });
   protected static final ean.i U = new ean.i(OptionalDouble.of(1.0));

   public ean(String var1, Runnable var2, Runnable var3) {
      this.a = _snowman;
      this.V = _snowman;
      this.W = _snowman;
   }

   public void a() {
      this.V.run();
   }

   public void b() {
      this.W.run();
   }

   @Override
   public boolean equals(@Nullable Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         ean _snowman = (ean)_snowman;
         return this.a.equals(_snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   @Override
   public String toString() {
      return this.a;
   }

   private static void a(float var0) {
      RenderSystem.matrixMode(5890);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      long _snowman = x.b() * 8L;
      float _snowmanx = (float)(_snowman % 110000L) / 110000.0F;
      float _snowmanxx = (float)(_snowman % 30000L) / 30000.0F;
      RenderSystem.translatef(-_snowmanx, _snowmanxx, 0.0F);
      RenderSystem.rotatef(10.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(_snowman, _snowman, _snowman);
      RenderSystem.matrixMode(5888);
   }

   public static class a extends ean {
      private final float V;

      public a(float var1) {
         super("alpha", () -> {
            if (_snowman > 0.0F) {
               RenderSystem.enableAlphaTest();
               RenderSystem.alphaFunc(516, _snowman);
            } else {
               RenderSystem.disableAlphaTest();
            }
         }, () -> {
            RenderSystem.disableAlphaTest();
            RenderSystem.defaultAlphaFunc();
         });
         this.V = _snowman;
      }

      @Override
      public boolean equals(@Nullable Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman == null || this.getClass() != _snowman.getClass()) {
            return false;
         } else {
            return !super.equals(_snowman) ? false : this.V == ((ean.a)_snowman).V;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(super.hashCode(), this.V);
      }

      @Override
      public String toString() {
         return this.a + '[' + this.V + ']';
      }
   }

   static class b extends ean {
      private final boolean V;

      public b(String var1, Runnable var2, Runnable var3, boolean var4) {
         super(_snowman, _snowman, _snowman);
         this.V = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.b _snowman = (ean.b)_snowman;
            return this.V == _snowman.V;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Boolean.hashCode(this.V);
      }

      @Override
      public String toString() {
         return this.a + '[' + this.V + ']';
      }
   }

   public static class c extends ean.b {
      public c(boolean var1) {
         super("cull", () -> {
            if (!_snowman) {
               RenderSystem.disableCull();
            }
         }, () -> {
            if (!_snowman) {
               RenderSystem.enableCull();
            }
         }, _snowman);
      }
   }

   public static class d extends ean {
      private final String V;
      private final int W;

      public d(String var1, int var2) {
         super("depth_test", () -> {
            if (_snowman != 519) {
               RenderSystem.enableDepthTest();
               RenderSystem.depthFunc(_snowman);
            }
         }, () -> {
            if (_snowman != 519) {
               RenderSystem.disableDepthTest();
               RenderSystem.depthFunc(515);
            }
         });
         this.V = _snowman;
         this.W = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.d _snowman = (ean.d)_snowman;
            return this.W == _snowman.W;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Integer.hashCode(this.W);
      }

      @Override
      public String toString() {
         return this.a + '[' + this.V + ']';
      }
   }

   public static class e extends ean.b {
      public e(boolean var1) {
         super("diffuse_lighting", () -> {
            if (_snowman) {
               dep.a();
            }
         }, () -> {
            if (_snowman) {
               dep.b();
            }
         }, _snowman);
      }
   }

   public static class f extends ean {
      public f(String var1, Runnable var2, Runnable var3) {
         super(_snowman, _snowman, _snowman);
      }
   }

   public static class g extends ean {
      public g(String var1, Runnable var2, Runnable var3) {
         super(_snowman, _snowman, _snowman);
      }
   }

   public static class h extends ean.b {
      public h(boolean var1) {
         super("lightmap", () -> {
            if (_snowman) {
               djz.C().h.l().c();
            }
         }, () -> {
            if (_snowman) {
               djz.C().h.l().b();
            }
         }, _snowman);
      }
   }

   public static class i extends ean {
      private final OptionalDouble V;

      public i(OptionalDouble var1) {
         super("line_width", () -> {
            if (!Objects.equals(_snowman, OptionalDouble.of(1.0))) {
               if (_snowman.isPresent()) {
                  RenderSystem.lineWidth((float)_snowman.getAsDouble());
               } else {
                  RenderSystem.lineWidth(Math.max(2.5F, (float)djz.C().aD().k() / 1920.0F * 2.5F));
               }
            }
         }, () -> {
            if (!Objects.equals(_snowman, OptionalDouble.of(1.0))) {
               RenderSystem.lineWidth(1.0F);
            }
         });
         this.V = _snowman;
      }

      @Override
      public boolean equals(@Nullable Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman == null || this.getClass() != _snowman.getClass()) {
            return false;
         } else {
            return !super.equals(_snowman) ? false : Objects.equals(this.V, ((ean.i)_snowman).V);
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(super.hashCode(), this.V);
      }

      @Override
      public String toString() {
         return this.a + '[' + (this.V.isPresent() ? this.V.getAsDouble() : "window_scale") + ']';
      }
   }

   public static final class j extends ean.p {
      private final float V;
      private final float W;

      public j(float var1, float var2) {
         super("offset_texturing", () -> {
            RenderSystem.matrixMode(5890);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            RenderSystem.translatef(_snowman, _snowman, 0.0F);
            RenderSystem.matrixMode(5888);
         }, () -> {
            RenderSystem.matrixMode(5890);
            RenderSystem.popMatrix();
            RenderSystem.matrixMode(5888);
         });
         this.V = _snowman;
         this.W = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.j _snowman = (ean.j)_snowman;
            return Float.compare(_snowman.V, this.V) == 0 && Float.compare(_snowman.W, this.W) == 0;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.V, this.W);
      }
   }

   public static class k extends ean {
      public k(String var1, Runnable var2, Runnable var3) {
         super(_snowman, _snowman, _snowman);
      }
   }

   public static class l extends ean.b {
      public l(boolean var1) {
         super("overlay", () -> {
            if (_snowman) {
               djz.C().h.m().a();
            }
         }, () -> {
            if (_snowman) {
               djz.C().h.m().b();
            }
         }, _snowman);
      }
   }

   public static final class m extends ean.p {
      private final int V;

      public m(int var1) {
         super("portal_texturing", () -> {
            RenderSystem.matrixMode(5890);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            RenderSystem.translatef(0.5F, 0.5F, 0.0F);
            RenderSystem.scalef(0.5F, 0.5F, 1.0F);
            RenderSystem.translatef(17.0F / (float)_snowman, (2.0F + (float)_snowman / 1.5F) * ((float)(x.b() % 800000L) / 800000.0F), 0.0F);
            RenderSystem.rotatef(((float)(_snowman * _snowman) * 4321.0F + (float)_snowman * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
            RenderSystem.scalef(4.5F - (float)_snowman / 4.0F, 4.5F - (float)_snowman / 4.0F, 1.0F);
            RenderSystem.mulTextureByProjModelView();
            RenderSystem.matrixMode(5888);
            RenderSystem.setupEndPortalTexGen();
         }, () -> {
            RenderSystem.matrixMode(5890);
            RenderSystem.popMatrix();
            RenderSystem.matrixMode(5888);
            RenderSystem.clearTexGen();
         });
         this.V = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.m _snowman = (ean.m)_snowman;
            return this.V == _snowman.V;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Integer.hashCode(this.V);
      }
   }

   public static class n extends ean {
      private final boolean V;

      public n(boolean var1) {
         super("shade_model", () -> RenderSystem.shadeModel(_snowman ? 7425 : 7424), () -> RenderSystem.shadeModel(7424));
         this.V = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.n _snowman = (ean.n)_snowman;
            return this.V == _snowman.V;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Boolean.hashCode(this.V);
      }

      @Override
      public String toString() {
         return this.a + '[' + (this.V ? "smooth" : "flat") + ']';
      }
   }

   public static class o extends ean {
      private final Optional<vk> V;
      private final boolean W;
      private final boolean X;

      public o(vk var1, boolean var2, boolean var3) {
         super("texture", () -> {
            RenderSystem.enableTexture();
            ekd _snowman = djz.C().M();
            _snowman.a(_snowman);
            _snowman.b(_snowman).a(_snowman, _snowman);
         }, () -> {
         });
         this.V = Optional.of(_snowman);
         this.W = _snowman;
         this.X = _snowman;
      }

      public o() {
         super("texture", () -> RenderSystem.disableTexture(), () -> RenderSystem.enableTexture());
         this.V = Optional.empty();
         this.W = false;
         this.X = false;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.o _snowman = (ean.o)_snowman;
            return this.V.equals(_snowman.V) && this.W == _snowman.W && this.X == _snowman.X;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return this.V.hashCode();
      }

      @Override
      public String toString() {
         return this.a + '[' + this.V + "(blur=" + this.W + ", mipmap=" + this.X + ")]";
      }

      protected Optional<vk> c() {
         return this.V;
      }
   }

   public static class p extends ean {
      public p(String var1, Runnable var2, Runnable var3) {
         super(_snowman, _snowman, _snowman);
      }
   }

   public static class q extends ean {
      public q(String var1, Runnable var2, Runnable var3) {
         super(_snowman, _snowman, _snowman);
      }
   }

   public static class r extends ean {
      private final boolean V;
      private final boolean W;

      public r(boolean var1, boolean var2) {
         super("write_mask_state", () -> {
            if (!_snowman) {
               RenderSystem.depthMask(_snowman);
            }

            if (!_snowman) {
               RenderSystem.colorMask(_snowman, _snowman, _snowman, _snowman);
            }
         }, () -> {
            if (!_snowman) {
               RenderSystem.depthMask(true);
            }

            if (!_snowman) {
               RenderSystem.colorMask(true, true, true, true);
            }
         });
         this.V = _snowman;
         this.W = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            ean.r _snowman = (ean.r)_snowman;
            return this.V == _snowman.V && this.W == _snowman.W;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.V, this.W);
      }

      @Override
      public String toString() {
         return this.a + "[writeColor=" + this.V + ", writeDepth=" + this.W + ']';
      }
   }
}
