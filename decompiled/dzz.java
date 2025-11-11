import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dzz implements aci, AutoCloseable {
   private static final vk c = new vk("textures/misc/nausea.png");
   private static final Logger d = LogManager.getLogger();
   private final djz e;
   private final ach f;
   private final Random g = new Random();
   private float h;
   public final eac a;
   private final dkx i;
   private final eam j;
   private int k;
   private float l;
   private float m;
   private float n;
   private float o;
   private boolean p = true;
   private boolean q = true;
   private long r;
   private long s = x.b();
   private final eaf t;
   private final ejw u = new ejw();
   private boolean v;
   private float w = 1.0F;
   private float x;
   private float y;
   @Nullable
   private bmb z;
   private int A;
   private float B;
   private float C;
   @Nullable
   private eaj D;
   private static final vk[] E = new vk[]{
      new vk("shaders/post/notch.json"),
      new vk("shaders/post/fxaa.json"),
      new vk("shaders/post/art.json"),
      new vk("shaders/post/bumpy.json"),
      new vk("shaders/post/blobs2.json"),
      new vk("shaders/post/pencil.json"),
      new vk("shaders/post/color_convolve.json"),
      new vk("shaders/post/deconverge.json"),
      new vk("shaders/post/flip.json"),
      new vk("shaders/post/invert.json"),
      new vk("shaders/post/ntsc.json"),
      new vk("shaders/post/outline.json"),
      new vk("shaders/post/phosphor.json"),
      new vk("shaders/post/scan_pincushion.json"),
      new vk("shaders/post/sobel.json"),
      new vk("shaders/post/bits.json"),
      new vk("shaders/post/desaturate.json"),
      new vk("shaders/post/green.json"),
      new vk("shaders/post/blur.json"),
      new vk("shaders/post/wobble.json"),
      new vk("shaders/post/blobs.json"),
      new vk("shaders/post/antialias.json"),
      new vk("shaders/post/creeper.json"),
      new vk("shaders/post/spider.json")
   };
   public static final int b = E.length;
   private int F = b;
   private boolean G;
   private final djk H = new djk();

   public dzz(djz var1, ach var2, eam var3) {
      this.e = _snowman;
      this.f = _snowman;
      this.a = _snowman.ae();
      this.i = new dkx(_snowman.M());
      this.t = new eaf(this, _snowman);
      this.j = _snowman;
      this.D = null;
   }

   @Override
   public void close() {
      this.t.close();
      this.i.close();
      this.u.close();
      this.a();
   }

   public void a() {
      if (this.D != null) {
         this.D.close();
      }

      this.D = null;
      this.F = b;
   }

   public void b() {
      this.G = !this.G;
   }

   public void a(@Nullable aqa var1) {
      if (this.D != null) {
         this.D.close();
      }

      this.D = null;
      if (_snowman instanceof bdc) {
         this.a(new vk("shaders/post/creeper.json"));
      } else if (_snowman instanceof beb) {
         this.a(new vk("shaders/post/spider.json"));
      } else if (_snowman instanceof bdg) {
         this.a(new vk("shaders/post/invert.json"));
      }
   }

   private void a(vk var1) {
      if (this.D != null) {
         this.D.close();
      }

      try {
         this.D = new eaj(this.e.M(), this.f, this.e.f(), _snowman);
         this.D.a(this.e.aD().k(), this.e.aD().l());
         this.G = true;
      } catch (IOException var3) {
         d.warn("Failed to load shader: {}", _snowman, var3);
         this.F = b;
         this.G = false;
      } catch (JsonSyntaxException var4) {
         d.warn("Failed to parse shader: {}", _snowman, var4);
         this.F = b;
         this.G = false;
      }
   }

   @Override
   public void a(ach var1) {
      if (this.D != null) {
         this.D.close();
      }

      this.D = null;
      if (this.F == b) {
         this.a(this.e.aa());
      } else {
         this.a(E[this.F]);
      }
   }

   public void e() {
      this.n();
      this.t.a();
      if (this.e.aa() == null) {
         this.e.a(this.e.s);
      }

      this.H.a();
      this.k++;
      this.a.a();
      this.e.e.a(this.H);
      this.o = this.n;
      if (this.e.j.i().d()) {
         this.n += 0.05F;
         if (this.n > 1.0F) {
            this.n = 1.0F;
         }
      } else if (this.n > 0.0F) {
         this.n -= 0.0125F;
      }

      if (this.A > 0) {
         this.A--;
         if (this.A == 0) {
            this.z = null;
         }
      }
   }

   @Nullable
   public eaj f() {
      return this.D;
   }

   public void a(int var1, int var2) {
      if (this.D != null) {
         this.D.a(_snowman, _snowman);
      }

      this.e.e.a(_snowman, _snowman);
   }

   public void a(float var1) {
      aqa _snowman = this.e.aa();
      if (_snowman != null) {
         if (this.e.r != null) {
            this.e.au().a("pick");
            this.e.u = null;
            double _snowmanx = (double)this.e.q.c();
            this.e.v = _snowman.a(_snowmanx, _snowman, false);
            dcn _snowmanxx = _snowman.j(_snowman);
            boolean _snowmanxxx = false;
            int _snowmanxxxx = 3;
            double _snowmanxxxxx = _snowmanx;
            if (this.e.q.h()) {
               _snowmanxxxxx = 6.0;
               _snowmanx = _snowmanxxxxx;
            } else {
               if (_snowmanx > 3.0) {
                  _snowmanxxx = true;
               }

               _snowmanx = _snowmanx;
            }

            _snowmanxxxxx *= _snowmanxxxxx;
            if (this.e.v != null) {
               _snowmanxxxxx = this.e.v.e().g(_snowmanxx);
            }

            dcn _snowmanxxxxxx = _snowman.f(1.0F);
            dcn _snowmanxxxxxxx = _snowmanxx.b(_snowmanxxxxxx.b * _snowmanx, _snowmanxxxxxx.c * _snowmanx, _snowmanxxxxxx.d * _snowmanx);
            float _snowmanxxxxxxxx = 1.0F;
            dci _snowmanxxxxxxxxx = _snowman.cc().b(_snowmanxxxxxx.a(_snowmanx)).c(1.0, 1.0, 1.0);
            dck _snowmanxxxxxxxxxx = bgn.a(_snowman, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, var0 -> !var0.a_() && var0.aT(), _snowmanxxxxx);
            if (_snowmanxxxxxxxxxx != null) {
               aqa _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a();
               dcn _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.e();
               double _snowmanxxxxxxxxxxxxx = _snowmanxx.g(_snowmanxxxxxxxxxxxx);
               if (_snowmanxxx && _snowmanxxxxxxxxxxxxx > 9.0) {
                  this.e.v = dcj.a(_snowmanxxxxxxxxxxxx, gc.a(_snowmanxxxxxx.b, _snowmanxxxxxx.c, _snowmanxxxxxx.d), new fx(_snowmanxxxxxxxxxxxx));
               } else if (_snowmanxxxxxxxxxxxxx < _snowmanxxxxx || this.e.v == null) {
                  this.e.v = _snowmanxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxx instanceof aqm || _snowmanxxxxxxxxxxx instanceof bcp) {
                     this.e.u = _snowmanxxxxxxxxxxx;
                  }
               }
            }

            this.e.au().c();
         }
      }
   }

   private void n() {
      float _snowman = 1.0F;
      if (this.e.aa() instanceof dzj) {
         dzj _snowmanx = (dzj)this.e.aa();
         _snowman = _snowmanx.v();
      }

      this.m = this.l;
      this.l = this.l + (_snowman - this.l) * 0.5F;
      if (this.l > 1.5F) {
         this.l = 1.5F;
      }

      if (this.l < 0.1F) {
         this.l = 0.1F;
      }
   }

   private double b(djk var1, float var2, boolean var3) {
      if (this.v) {
         return 90.0;
      } else {
         double _snowman = 70.0;
         if (_snowman) {
            _snowman = this.e.k.aO;
            _snowman *= (double)afm.g(_snowman, this.m, this.l);
         }

         if (_snowman.g() instanceof aqm && ((aqm)_snowman.g()).dl()) {
            float _snowmanx = Math.min((float)((aqm)_snowman.g()).aq + _snowman, 20.0F);
            _snowman /= (double)((1.0F - 500.0F / (_snowmanx + 500.0F)) * 2.0F + 1.0F);
         }

         cux _snowmanx = _snowman.k();
         if (!_snowmanx.c()) {
            _snowman = _snowman * 60.0 / 70.0;
         }

         return _snowman;
      }
   }

   private void a(dfm var1, float var2) {
      if (this.e.aa() instanceof aqm) {
         aqm _snowman = (aqm)this.e.aa();
         float _snowmanx = (float)_snowman.an - _snowman;
         if (_snowman.dl()) {
            float _snowmanxx = Math.min((float)_snowman.aq + _snowman, 20.0F);
            _snowman.a(g.f.a(40.0F - 8000.0F / (_snowmanxx + 200.0F)));
         }

         if (_snowmanx < 0.0F) {
            return;
         }

         _snowmanx /= (float)_snowman.ao;
         _snowmanx = afm.a(_snowmanx * _snowmanx * _snowmanx * _snowmanx * (float) Math.PI);
         float _snowmanxx = _snowman.ap;
         _snowman.a(g.d.a(-_snowmanxx));
         _snowman.a(g.f.a(-_snowmanx * 14.0F));
         _snowman.a(g.d.a(_snowmanxx));
      }
   }

   private void b(dfm var1, float var2) {
      if (this.e.aa() instanceof bfw) {
         bfw _snowman = (bfw)this.e.aa();
         float _snowmanx = _snowman.A - _snowman.z;
         float _snowmanxx = -(_snowman.A + _snowmanx * _snowman);
         float _snowmanxxx = afm.g(_snowman, _snowman.bs, _snowman.bt);
         _snowman.a((double)(afm.a(_snowmanxx * (float) Math.PI) * _snowmanxxx * 0.5F), (double)(-Math.abs(afm.b(_snowmanxx * (float) Math.PI) * _snowmanxxx)), 0.0);
         _snowman.a(g.f.a(afm.a(_snowmanxx * (float) Math.PI) * _snowmanxxx * 3.0F));
         _snowman.a(g.b.a(Math.abs(afm.b(_snowmanxx * (float) Math.PI - 0.2F) * _snowmanxxx) * 5.0F));
      }
   }

   private void a(dfm var1, djk var2, float var3) {
      if (!this.v) {
         this.a(this.a(_snowman, _snowman, false));
         dfm.a _snowman = _snowman.c();
         _snowman.a().a();
         _snowman.b().c();
         _snowman.a();
         this.a(_snowman, _snowman);
         if (this.e.k.aa) {
            this.b(_snowman, _snowman);
         }

         boolean _snowmanx = this.e.aa() instanceof aqm && ((aqm)this.e.aa()).em();
         if (this.e.k.g().a() && !_snowmanx && !this.e.k.aI && this.e.q.l() != bru.e) {
            this.t.c();
            this.a.a(_snowman, _snowman, this.j.b(), this.e.s, this.e.ac().a(this.e.s, _snowman));
            this.t.b();
         }

         _snowman.b();
         if (this.e.k.g().a() && !_snowmanx) {
            eaq.a(this.e, _snowman);
            this.a(_snowman, _snowman);
         }

         if (this.e.k.aa) {
            this.b(_snowman, _snowman);
         }
      }
   }

   public void a(b var1) {
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(_snowman);
      RenderSystem.matrixMode(5888);
   }

   public b a(djk var1, float var2, boolean var3) {
      dfm _snowman = new dfm();
      _snowman.c().a().a();
      if (this.w != 1.0F) {
         _snowman.a((double)this.x, (double)(-this.y), 0.0);
         _snowman.a(this.w, this.w, 1.0F);
      }

      _snowman.c().a().a(b.a(this.b(_snowman, _snowman, _snowman), (float)this.e.aD().k() / (float)this.e.aD().l(), 0.05F, this.h * 4.0F));
      return _snowman.c().a();
   }

   public static float a(aqm var0, float var1) {
      int _snowman = _snowman.b(apw.p).b();
      return _snowman > 200 ? 1.0F : 0.7F + afm.a(((float)_snowman - _snowman) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void a(float var1, long var2, boolean var4) {
      if (!this.e.ap() && this.e.k.q && (!this.e.k.Y || !this.e.l.d())) {
         if (x.b() - this.s > 500L) {
            this.e.c(false);
         }
      } else {
         this.s = x.b();
      }

      if (!this.e.x) {
         int _snowman = (int)(this.e.l.e() * (double)this.e.aD().o() / (double)this.e.aD().m());
         int _snowmanx = (int)(this.e.l.f() * (double)this.e.aD().p() / (double)this.e.aD().n());
         RenderSystem.viewport(0, 0, this.e.aD().k(), this.e.aD().l());
         if (_snowman && this.e.r != null) {
            this.e.au().a("level");
            this.a(_snowman, _snowman, new dfm());
            if (this.e.G() && this.r < x.b() - 1000L) {
               this.r = x.b();
               if (!this.e.H().z()) {
                  this.o();
               }
            }

            this.e.e.b();
            if (this.D != null && this.G) {
               RenderSystem.disableBlend();
               RenderSystem.disableDepthTest();
               RenderSystem.disableAlphaTest();
               RenderSystem.enableTexture();
               RenderSystem.matrixMode(5890);
               RenderSystem.pushMatrix();
               RenderSystem.loadIdentity();
               this.D.a(_snowman);
               RenderSystem.popMatrix();
            }

            this.e.f().a(true);
         }

         dez _snowmanxx = this.e.aD();
         RenderSystem.clear(256, djz.a);
         RenderSystem.matrixMode(5889);
         RenderSystem.loadIdentity();
         RenderSystem.ortho(0.0, (double)_snowmanxx.k() / _snowmanxx.s(), (double)_snowmanxx.l() / _snowmanxx.s(), 0.0, 1000.0, 3000.0);
         RenderSystem.matrixMode(5888);
         RenderSystem.loadIdentity();
         RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
         dep.d();
         dfm _snowmanxxx = new dfm();
         if (_snowman && this.e.r != null) {
            this.e.au().b("gui");
            if (this.e.s != null) {
               float _snowmanxxxx = afm.g(_snowman, this.e.s.bQ, this.e.s.bP);
               if (_snowmanxxxx > 0.0F && this.e.s.a(apw.i) && this.e.k.aP < 1.0F) {
                  this.c(_snowmanxxxx * (1.0F - this.e.k.aP));
               }
            }

            if (!this.e.k.aI || this.e.y != null) {
               RenderSystem.defaultAlphaFunc();
               this.a(this.e.aD().o(), this.e.aD().p(), _snowman);
               this.e.j.a(_snowmanxxx, _snowman);
               RenderSystem.clear(256, djz.a);
            }

            this.e.au().c();
         }

         if (this.e.z != null) {
            try {
               this.e.z.a(_snowmanxxx, _snowman, _snowmanx, this.e.ak());
            } catch (Throwable var13) {
               l _snowmanxxxx = l.a(var13, "Rendering overlay");
               m _snowmanxxxxx = _snowmanxxxx.a("Overlay render details");
               _snowmanxxxxx.a("Overlay name", () -> this.e.z.getClass().getCanonicalName());
               throw new u(_snowmanxxxx);
            }
         } else if (this.e.y != null) {
            try {
               this.e.y.a(_snowmanxxx, _snowman, _snowmanx, this.e.ak());
            } catch (Throwable var12) {
               l _snowmanxxxx = l.a(var12, "Rendering screen");
               m _snowmanxxxxx = _snowmanxxxx.a("Screen render details");
               _snowmanxxxxx.a("Screen name", () -> this.e.y.getClass().getCanonicalName());
               _snowmanxxxxx.a("Mouse location", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", _snowman, _snowman, this.e.l.e(), this.e.l.f()));
               _snowmanxxxxx.a(
                  "Screen size",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f",
                        this.e.aD().o(),
                        this.e.aD().p(),
                        this.e.aD().k(),
                        this.e.aD().l(),
                        this.e.aD().s()
                     )
               );
               throw new u(_snowmanxxxx);
            }
         }
      }
   }

   private void o() {
      if (this.e.e.h() > 10 && this.e.e.n() && !this.e.H().z()) {
         det _snowman = dkh.a(this.e.aD().k(), this.e.aD().l(), this.e.f());
         x.g().execute(() -> {
            int _snowmanx = _snowman.a();
            int _snowmanx = _snowman.b();
            int _snowmanxx = 0;
            int _snowmanxxx = 0;
            if (_snowmanx > _snowmanx) {
               _snowmanxx = (_snowmanx - _snowmanx) / 2;
               _snowmanx = _snowmanx;
            } else {
               _snowmanxxx = (_snowmanx - _snowmanx) / 2;
               _snowmanx = _snowmanx;
            }

            try (det _snowmanxxxx = new det(64, 64, false)) {
               _snowman.a(_snowmanxx, _snowmanxxx, _snowmanx, _snowmanx, _snowmanxxxx);
               _snowmanxxxx.a(this.e.H().A());
            } catch (IOException var27) {
               d.warn("Couldn't save auto screenshot", var27);
            } finally {
               _snowman.close();
            }
         });
      }
   }

   private boolean p() {
      if (!this.q) {
         return false;
      } else {
         aqa _snowman = this.e.aa();
         boolean _snowmanx = _snowman instanceof bfw && !this.e.k.aI;
         if (_snowmanx && !((bfw)_snowman).bC.e) {
            bmb _snowmanxx = ((aqm)_snowman).dD();
            dcl _snowmanxxx = this.e.v;
            if (_snowmanxxx != null && _snowmanxxx.c() == dcl.a.b) {
               fx _snowmanxxxx = ((dcj)_snowmanxxx).a();
               ceh _snowmanxxxxx = this.e.r.d_(_snowmanxxxx);
               if (this.e.q.l() == bru.e) {
                  _snowmanx = _snowmanxxxxx.b(this.e.r, _snowmanxxxx) != null;
               } else {
                  cel _snowmanxxxxxx = new cel(this.e.r, _snowmanxxxx, false);
                  _snowmanx = !_snowmanxx.a() && (_snowmanxx.a(this.e.r.p(), _snowmanxxxxxx) || _snowmanxx.b(this.e.r.p(), _snowmanxxxxxx));
               }
            }
         }

         return _snowmanx;
      }
   }

   public void a(float var1, long var2, dfm var4) {
      this.t.a(_snowman);
      if (this.e.aa() == null) {
         this.e.a(this.e.s);
      }

      this.a(_snowman);
      this.e.au().a("center");
      boolean _snowman = this.p();
      this.e.au().b("camera");
      djk _snowmanx = this.H;
      this.h = (float)(this.e.k.b * 16);
      dfm _snowmanxx = new dfm();
      _snowmanxx.c().a().a(this.a(_snowmanx, _snowman, true));
      this.a(_snowmanxx, _snowman);
      if (this.e.k.aa) {
         this.b(_snowmanxx, _snowman);
      }

      float _snowmanxxx = afm.g(_snowman, this.e.s.bQ, this.e.s.bP) * this.e.k.aP * this.e.k.aP;
      if (_snowmanxxx > 0.0F) {
         int _snowmanxxxx = this.e.s.a(apw.i) ? 7 : 20;
         float _snowmanxxxxx = 5.0F / (_snowmanxxx * _snowmanxxx + 5.0F) - _snowmanxxx * 0.04F;
         _snowmanxxxxx *= _snowmanxxxxx;
         g _snowmanxxxxxx = new g(0.0F, afm.a / 2.0F, afm.a / 2.0F);
         _snowmanxx.a(_snowmanxxxxxx.a(((float)this.k + _snowman) * (float)_snowmanxxxx));
         _snowmanxx.a(1.0F / _snowmanxxxxx, 1.0F, 1.0F);
         float _snowmanxxxxxxx = -((float)this.k + _snowman) * (float)_snowmanxxxx;
         _snowmanxx.a(_snowmanxxxxxx.a(_snowmanxxxxxxx));
      }

      b _snowmanxxxx = _snowmanxx.c().a();
      this.a(_snowmanxxxx);
      _snowmanx.a(this.e.r, (aqa)(this.e.aa() == null ? this.e.s : this.e.aa()), !this.e.k.g().a(), this.e.k.g().b(), _snowman);
      _snowman.a(g.b.a(_snowmanx.d()));
      _snowman.a(g.d.a(_snowmanx.e() + 180.0F));
      this.e.e.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, this, this.t, _snowmanxxxx);
      this.e.au().b("hand");
      if (this.p) {
         RenderSystem.clear(256, djz.a);
         this.a(_snowman, _snowmanx, _snowman);
      }

      this.e.au().c();
   }

   public void g() {
      this.z = null;
      this.i.a();
      this.H.o();
   }

   public dkx h() {
      return this.i;
   }

   public void a(bmb var1) {
      this.z = _snowman;
      this.A = 40;
      this.B = this.g.nextFloat() * 2.0F - 1.0F;
      this.C = this.g.nextFloat() * 2.0F - 1.0F;
   }

   private void a(int var1, int var2, float var3) {
      if (this.z != null && this.A > 0) {
         int _snowman = 40 - this.A;
         float _snowmanx = ((float)_snowman + _snowman) / 40.0F;
         float _snowmanxx = _snowmanx * _snowmanx;
         float _snowmanxxx = _snowmanx * _snowmanxx;
         float _snowmanxxxx = 10.25F * _snowmanxxx * _snowmanxx - 24.95F * _snowmanxx * _snowmanxx + 25.5F * _snowmanxxx - 13.8F * _snowmanxx + 4.0F * _snowmanx;
         float _snowmanxxxxx = _snowmanxxxx * (float) Math.PI;
         float _snowmanxxxxxx = this.B * (float)(_snowman / 4);
         float _snowmanxxxxxxx = this.C * (float)(_snowman / 4);
         RenderSystem.enableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.pushLightingAttributes();
         RenderSystem.enableDepthTest();
         RenderSystem.disableCull();
         dfm _snowmanxxxxxxxx = new dfm();
         _snowmanxxxxxxxx.a();
         _snowmanxxxxxxxx.a((double)((float)(_snowman / 2) + _snowmanxxxxxx * afm.e(afm.a(_snowmanxxxxx * 2.0F))), (double)((float)(_snowman / 2) + _snowmanxxxxxxx * afm.e(afm.a(_snowmanxxxxx * 2.0F))), -50.0);
         float _snowmanxxxxxxxxx = 50.0F + 175.0F * afm.a(_snowmanxxxxx);
         _snowmanxxxxxxxx.a(_snowmanxxxxxxxxx, -_snowmanxxxxxxxxx, _snowmanxxxxxxxxx);
         _snowmanxxxxxxxx.a(g.d.a(900.0F * afm.e(afm.a(_snowmanxxxxx))));
         _snowmanxxxxxxxx.a(g.b.a(6.0F * afm.b(_snowmanx * 8.0F)));
         _snowmanxxxxxxxx.a(g.f.a(6.0F * afm.b(_snowmanx * 8.0F)));
         eag.a _snowmanxxxxxxxxxx = this.j.b();
         this.e.ad().a(this.z, ebm.b.i, 15728880, ejw.a, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx.b();
         _snowmanxxxxxxxxxx.a();
         RenderSystem.popAttributes();
         RenderSystem.popMatrix();
         RenderSystem.enableCull();
         RenderSystem.disableDepthTest();
      }
   }

   private void c(float var1) {
      int _snowman = this.e.aD().o();
      int _snowmanx = this.e.aD().p();
      double _snowmanxx = afm.d((double)_snowman, 2.0, 1.0);
      float _snowmanxxx = 0.2F * _snowman;
      float _snowmanxxxx = 0.4F * _snowman;
      float _snowmanxxxxx = 0.2F * _snowman;
      double _snowmanxxxxxx = (double)_snowman * _snowmanxx;
      double _snowmanxxxxxxx = (double)_snowmanx * _snowmanxx;
      double _snowmanxxxxxxxx = ((double)_snowman - _snowmanxxxxxx) / 2.0;
      double _snowmanxxxxxxxxx = ((double)_snowmanx - _snowmanxxxxxxx) / 2.0;
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(dem.r.e, dem.j.e, dem.r.e, dem.j.e);
      RenderSystem.color4f(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 1.0F);
      this.e.M().a(c);
      dfo _snowmanxxxxxxxxxx = dfo.a();
      dfh _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.c();
      _snowmanxxxxxxxxxxx.a(7, dfk.n);
      _snowmanxxxxxxxxxxx.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxx, -90.0).a(0.0F, 1.0F).d();
      _snowmanxxxxxxxxxxx.a(_snowmanxxxxxxxx + _snowmanxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxx, -90.0).a(1.0F, 1.0F).d();
      _snowmanxxxxxxxxxxx.a(_snowmanxxxxxxxx + _snowmanxxxxxx, _snowmanxxxxxxxxx, -90.0).a(1.0F, 0.0F).d();
      _snowmanxxxxxxxxxxx.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, -90.0).a(0.0F, 0.0F).d();
      _snowmanxxxxxxxxxx.b();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
   }

   public float b(float var1) {
      return afm.g(_snowman, this.o, this.n);
   }

   public float j() {
      return this.h;
   }

   public djk k() {
      return this.H;
   }

   public eaf l() {
      return this.t;
   }

   public ejw m() {
      return this.u;
   }
}
