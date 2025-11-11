import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class edh {
   public final edn a = new edn();
   public final edh.a b;
   public final edh.a c;
   public final edh.a d;
   public final edh.a e;
   public final edh.a f;
   public final edd g;
   public final edq h;
   public final edh.a i;
   public final edh.a j;
   public final edh.a k;
   public final edh.a l;
   public final edc m;
   public final edr n;
   public final edb o;
   public final edo p;
   public final edj q;
   public final edi r;
   private boolean s;

   public edh(djz var1) {
      this.b = new eds(_snowman);
      this.c = new ede(_snowman);
      this.d = new edk(_snowman);
      this.e = new edg(_snowman);
      this.f = new edm(_snowman);
      this.g = new edd();
      this.h = new edq(_snowman);
      this.i = new edl(_snowman);
      this.j = new edt();
      this.k = new edp(_snowman);
      this.l = new edf(_snowman);
      this.m = new edc(_snowman);
      this.n = new edr();
      this.o = new edb(_snowman);
      this.p = new edo(_snowman);
      this.q = new edj(_snowman);
      this.r = new edi();
   }

   public void a() {
      this.a.a();
      this.b.a();
      this.c.a();
      this.d.a();
      this.e.a();
      this.f.a();
      this.g.a();
      this.h.a();
      this.i.a();
      this.j.a();
      this.k.a();
      this.l.a();
      this.m.a();
      this.n.a();
      this.o.a();
      this.p.a();
      this.q.a();
      this.r.a();
   }

   public boolean b() {
      this.s = !this.s;
      return this.s;
   }

   public void a(dfm var1, eag.a var2, double var3, double var5, double var7) {
      if (this.s && !djz.C().am()) {
         this.c.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }

      this.r.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static Optional<aqa> a(@Nullable aqa var0, int var1) {
      if (_snowman == null) {
         return Optional.empty();
      } else {
         dcn _snowman = _snowman.j(1.0F);
         dcn _snowmanx = _snowman.f(1.0F).a((double)_snowman);
         dcn _snowmanxx = _snowman.e(_snowmanx);
         dci _snowmanxxx = _snowman.cc().b(_snowmanx).g(1.0);
         int _snowmanxxxx = _snowman * _snowman;
         Predicate<aqa> _snowmanxxxxx = var0x -> !var0x.a_() && var0x.aT();
         dck _snowmanxxxxxx = bgn.a(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxxx, (double)_snowmanxxxx);
         if (_snowmanxxxxxx == null) {
            return Optional.empty();
         } else {
            return _snowman.g(_snowmanxxxxxx.e()) > (double)_snowmanxxxx ? Optional.empty() : Optional.of(_snowmanxxxxxx.a());
         }
      }
   }

   public static void a(fx var0, fx var1, float var2, float var3, float var4, float var5) {
      djk _snowman = djz.C().h.k();
      if (_snowman.h()) {
         dcn _snowmanx = _snowman.b().e();
         dci _snowmanxx = new dci(_snowman, _snowman).c(_snowmanx);
         a(_snowmanxx, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public static void a(fx var0, float var1, float var2, float var3, float var4, float var5) {
      djk _snowman = djz.C().h.k();
      if (_snowman.h()) {
         dcn _snowmanx = _snowman.b().e();
         dci _snowmanxx = new dci(_snowman).c(_snowmanx).g((double)_snowman);
         a(_snowmanxx, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public static void a(dci var0, float var1, float var2, float var3, float var4) {
      a(_snowman.a, _snowman.b, _snowman.c, _snowman.d, _snowman.e, _snowman.f, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(double var0, double var2, double var4, double var6, double var8, double var10, float var12, float var13, float var14, float var15) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      _snowmanx.a(5, dfk.l);
      eae.a(_snowmanx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.b();
   }

   public static void a(String var0, int var1, int var2, int var3, int var4) {
      a(_snowman, (double)_snowman + 0.5, (double)_snowman + 0.5, (double)_snowman + 0.5, _snowman);
   }

   public static void a(String var0, double var1, double var3, double var5, int var7) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, 0.02F);
   }

   public static void a(String var0, double var1, double var3, double var5, int var7, float var8) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, true, 0.0F, false);
   }

   public static void a(String var0, double var1, double var3, double var5, int var7, float var8, boolean var9, float var10, boolean var11) {
      djz _snowman = djz.C();
      djk _snowmanx = _snowman.h.k();
      if (_snowmanx.h() && _snowman.ac().d != null) {
         dku _snowmanxx = _snowman.g;
         double _snowmanxxx = _snowmanx.b().b;
         double _snowmanxxxx = _snowmanx.b().c;
         double _snowmanxxxxx = _snowmanx.b().d;
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(_snowman - _snowmanxxx), (float)(_snowman - _snowmanxxxx) + 0.07F, (float)(_snowman - _snowmanxxxxx));
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.multMatrix(new b(_snowmanx.f()));
         RenderSystem.scalef(_snowman, -_snowman, _snowman);
         RenderSystem.enableTexture();
         if (_snowman) {
            RenderSystem.disableDepthTest();
         } else {
            RenderSystem.enableDepthTest();
         }

         RenderSystem.depthMask(true);
         RenderSystem.scalef(-1.0F, 1.0F, 1.0F);
         float _snowmanxxxxxx = _snowman ? (float)(-_snowmanxx.b(_snowman)) / 2.0F : 0.0F;
         _snowmanxxxxxx -= _snowman / _snowman;
         RenderSystem.enableAlphaTest();
         eag.a _snowmanxxxxxxx = eag.a(dfo.a().c());
         _snowmanxx.a(_snowman, _snowmanxxxxxx, 0.0F, _snowman, false, f.a().c(), _snowmanxxxxxxx, _snowman, 0, 15728880);
         _snowmanxxxxxxx.a();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableDepthTest();
         RenderSystem.popMatrix();
      }
   }

   public interface a {
      void a(dfm var1, eag var2, double var3, double var5, double var7);

      default void a() {
      }
   }
}
