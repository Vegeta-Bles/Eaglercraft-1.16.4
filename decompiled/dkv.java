import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class dkv extends dkw {
   private static final vk b = new vk("textures/misc/vignette.png");
   private static final vk c = new vk("textures/gui/widgets.png");
   private static final vk d = new vk("textures/misc/pumpkinblur.png");
   private static final nr e = new of("demo.demoExpired");
   private final Random i = new Random();
   private final djz j;
   private final efo k;
   private final dlk l;
   private int m;
   @Nullable
   private nr n;
   private int o;
   private boolean p;
   public float a = 1.0F;
   private int q;
   private bmb r = bmb.b;
   private final dlp s;
   private final dmb t;
   private final dml u;
   private final dly v;
   private final dli w;
   private int x;
   @Nullable
   private nr y;
   @Nullable
   private nr z;
   private int A;
   private int B;
   private int C;
   private int D;
   private int E;
   private long F;
   private long G;
   private int H;
   private int I;
   private final Map<no, List<dky>> J = Maps.newHashMap();

   public dkv(djz var1) {
      this.j = _snowman;
      this.k = _snowman.ad();
      this.s = new dlp(_snowman);
      this.u = new dml(_snowman);
      this.l = new dlk(_snowman);
      this.v = new dly(_snowman, this);
      this.w = new dli(_snowman);
      this.t = new dmb(_snowman);

      for (no _snowman : no.values()) {
         this.J.put(_snowman, Lists.newArrayList());
      }

      dky _snowman = dkz.b;
      this.J.get(no.a).add(new dlb(_snowman));
      this.J.get(no.a).add(_snowman);
      this.J.get(no.b).add(new dlb(_snowman));
      this.J.get(no.b).add(_snowman);
      this.J.get(no.c).add(new dla(_snowman));
      this.a();
   }

   public void a() {
      this.A = 10;
      this.B = 70;
      this.C = 20;
   }

   public void a(dfm var1, float var2) {
      this.H = this.j.aD().o();
      this.I = this.j.aD().p();
      dku _snowman = this.e();
      RenderSystem.enableBlend();
      if (djz.z()) {
         this.b(this.j.aa());
      } else {
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
      }

      bmb _snowmanx = this.j.s.bm.e(3);
      if (this.j.k.g().a() && _snowmanx.b() == bup.cU.h()) {
         this.m();
      }

      float _snowmanxx = afm.g(_snowman, this.j.s.bQ, this.j.s.bP);
      if (_snowmanxx > 0.0F && !this.j.s.a(apw.i)) {
         this.a(_snowmanxx);
      }

      if (this.j.q.l() == bru.e) {
         this.u.a(_snowman, _snowman);
      } else if (!this.j.k.aI) {
         this.a(_snowman, _snowman);
      }

      if (!this.j.k.aI) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.j.M().a(h);
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         this.d(_snowman);
         RenderSystem.defaultBlendFunc();
         this.j.au().a("bossHealth");
         this.w.a(_snowman);
         this.j.au().c();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.j.M().a(h);
         if (this.j.q.a()) {
            this.e(_snowman);
         }

         this.f(_snowman);
         RenderSystem.disableBlend();
         int _snowmanxxx = this.H / 2 - 91;
         if (this.j.s.H()) {
            this.a(_snowman, _snowmanxxx);
         } else if (this.j.q.e()) {
            this.b(_snowman, _snowmanxxx);
         }

         if (this.j.k.u && this.j.q.l() != bru.e) {
            this.b(_snowman);
         } else if (this.j.s.a_()) {
            this.u.a(_snowman);
         }
      }

      if (this.j.s.eC() > 0) {
         this.j.au().a("sleep");
         RenderSystem.disableDepthTest();
         RenderSystem.disableAlphaTest();
         float _snowmanxxxx = (float)this.j.s.eC();
         float _snowmanxxxxx = _snowmanxxxx / 100.0F;
         if (_snowmanxxxxx > 1.0F) {
            _snowmanxxxxx = 1.0F - (_snowmanxxxx - 100.0F) / 10.0F;
         }

         int _snowmanxxxxxx = (int)(220.0F * _snowmanxxxxx) << 24 | 1052704;
         a(_snowman, 0, 0, this.H, this.I, _snowmanxxxxxx);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         this.j.au().c();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.j.v()) {
         this.c(_snowman);
      }

      this.a(_snowman);
      if (this.j.k.aJ) {
         this.s.a(_snowman);
      }

      if (!this.j.k.aI) {
         if (this.n != null && this.o > 0) {
            this.j.au().a("overlayMessage");
            float _snowmanxxxx = (float)this.o - _snowman;
            int _snowmanxxxxx = (int)(_snowmanxxxx * 255.0F / 20.0F);
            if (_snowmanxxxxx > 255) {
               _snowmanxxxxx = 255;
            }

            if (_snowmanxxxxx > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.H / 2), (float)(this.I - 68), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               int _snowmanxxxxxx = 16777215;
               if (this.p) {
                  _snowmanxxxxxx = afm.f(_snowmanxxxx / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int _snowmanxxxxxxx = _snowmanxxxxx << 24 & 0xFF000000;
               int _snowmanxxxxxxxx = _snowman.a(this.n);
               this.a(_snowman, _snowman, -4, _snowmanxxxxxxxx, 16777215 | _snowmanxxxxxxx);
               _snowman.b(_snowman, this.n, (float)(-_snowmanxxxxxxxx / 2), -4.0F, _snowmanxxxxxx | _snowmanxxxxxxx);
               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.j.au().c();
         }

         if (this.y != null && this.x > 0) {
            this.j.au().a("titleAndSubtitle");
            float _snowmanxxxxxx = (float)this.x - _snowman;
            int _snowmanxxxxxxx = 255;
            if (this.x > this.C + this.B) {
               float _snowmanxxxxxxxx = (float)(this.A + this.B + this.C) - _snowmanxxxxxx;
               _snowmanxxxxxxx = (int)(_snowmanxxxxxxxx * 255.0F / (float)this.A);
            }

            if (this.x <= this.C) {
               _snowmanxxxxxxx = (int)(_snowmanxxxxxx * 255.0F / (float)this.C);
            }

            _snowmanxxxxxxx = afm.a(_snowmanxxxxxxx, 0, 255);
            if (_snowmanxxxxxxx > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.H / 2), (float)(this.I / 2), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.pushMatrix();
               RenderSystem.scalef(4.0F, 4.0F, 4.0F);
               int _snowmanxxxxxxxx = _snowmanxxxxxxx << 24 & 0xFF000000;
               int _snowmanxxxxxxxxx = _snowman.a(this.y);
               this.a(_snowman, _snowman, -10, _snowmanxxxxxxxxx, 16777215 | _snowmanxxxxxxxx);
               _snowman.a(_snowman, this.y, (float)(-_snowmanxxxxxxxxx / 2), -10.0F, 16777215 | _snowmanxxxxxxxx);
               RenderSystem.popMatrix();
               if (this.z != null) {
                  RenderSystem.pushMatrix();
                  RenderSystem.scalef(2.0F, 2.0F, 2.0F);
                  int _snowmanxxxxxxxxxx = _snowman.a(this.z);
                  this.a(_snowman, _snowman, 5, _snowmanxxxxxxxxxx, 16777215 | _snowmanxxxxxxxx);
                  _snowman.a(_snowman, this.z, (float)(-_snowmanxxxxxxxxxx / 2), 5.0F, 16777215 | _snowmanxxxxxxxx);
                  RenderSystem.popMatrix();
               }

               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.j.au().c();
         }

         this.t.a(_snowman);
         ddn _snowmanxxxxxxxx = this.j.r.G();
         ddk _snowmanxxxxxxxxx = null;
         ddl _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.i(this.j.s.bU());
         if (_snowmanxxxxxxxxxx != null) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.n().b();
            if (_snowmanxxxxxxxxxxx >= 0) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a(3 + _snowmanxxxxxxxxxxx);
            }
         }

         ddk _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx != null ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx.a(1);
         if (_snowmanxxxxxxxxxxx != null) {
            this.a(_snowman, _snowmanxxxxxxxxxxx);
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, (float)(this.I - 48), 0.0F);
         this.j.au().a("chat");
         this.l.a(_snowman, this.m);
         this.j.au().c();
         RenderSystem.popMatrix();
         _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.a(0);
         if (!this.j.k.at.d() || this.j.F() && this.j.s.e.e().size() <= 1 && _snowmanxxxxxxxxxxx == null) {
            this.v.a(false);
         } else {
            this.v.a(true);
            this.v.a(_snowman, this.H, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
         }
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
   }

   private void a(dfm var1, dku var2, int var3, int var4, int var5) {
      int _snowman = this.j.k.b(0.0F);
      if (_snowman != 0) {
         int _snowmanx = -_snowman / 2;
         a(_snowman, _snowmanx - 2, _snowman - 2, _snowmanx + _snowman + 2, _snowman + 9 + 2, aez.a.a(_snowman, _snowman));
      }
   }

   private void d(dfm var1) {
      dkd _snowman = this.j.k;
      if (_snowman.g().a()) {
         if (this.j.q.l() != bru.e || this.a(this.j.v)) {
            if (_snowman.aJ && !_snowman.aI && !this.j.s.eO() && !_snowman.U) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.H / 2), (float)(this.I / 2), (float)this.v());
               djk _snowmanx = this.j.h.k();
               RenderSystem.rotatef(_snowmanx.d(), -1.0F, 0.0F, 0.0F);
               RenderSystem.rotatef(_snowmanx.e(), 0.0F, 1.0F, 0.0F);
               RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
               RenderSystem.renderCrosshair(10);
               RenderSystem.popMatrix();
            } else {
               RenderSystem.blendFuncSeparate(dem.r.i, dem.j.k, dem.r.e, dem.j.n);
               int _snowmanx = 15;
               this.b(_snowman, (this.H - 15) / 2, (this.I - 15) / 2, 0, 0, 15, 15);
               if (this.j.k.C == dji.b) {
                  float _snowmanxx = this.j.s.u(0.0F);
                  boolean _snowmanxxx = false;
                  if (this.j.u != null && this.j.u instanceof aqm && _snowmanxx >= 1.0F) {
                     _snowmanxxx = this.j.s.eR() > 5.0F;
                     _snowmanxxx &= this.j.u.aX();
                  }

                  int _snowmanxxxx = this.I / 2 - 7 + 16;
                  int _snowmanxxxxx = this.H / 2 - 8;
                  if (_snowmanxxx) {
                     this.b(_snowman, _snowmanxxxxx, _snowmanxxxx, 68, 94, 16, 16);
                  } else if (_snowmanxx < 1.0F) {
                     int _snowmanxxxxxx = (int)(_snowmanxx * 17.0F);
                     this.b(_snowman, _snowmanxxxxx, _snowmanxxxx, 36, 94, 16, 4);
                     this.b(_snowman, _snowmanxxxxx, _snowmanxxxx, 52, 94, _snowmanxxxxxx, 4);
                  }
               }
            }
         }
      }
   }

   private boolean a(dcl var1) {
      if (_snowman == null) {
         return false;
      } else if (_snowman.c() == dcl.a.c) {
         return ((dck)_snowman).a() instanceof aox;
      } else if (_snowman.c() == dcl.a.b) {
         fx _snowman = ((dcj)_snowman).a();
         brx _snowmanx = this.j.r;
         return _snowmanx.d_(_snowman).b(_snowmanx, _snowman) != null;
      } else {
         return false;
      }
   }

   protected void a(dfm var1) {
      Collection<apu> _snowman = this.j.s.dh();
      if (!_snowman.isEmpty()) {
         RenderSystem.enableBlend();
         int _snowmanx = 0;
         int _snowmanxx = 0;
         ekp _snowmanxxx = this.j.at();
         List<Runnable> _snowmanxxxx = Lists.newArrayListWithExpectedSize(_snowman.size());
         this.j.M().a(dpp.a);

         for (apu _snowmanxxxxx : Ordering.natural().reverse().sortedCopy(_snowman)) {
            aps _snowmanxxxxxx = _snowmanxxxxx.a();
            if (_snowmanxxxxx.f()) {
               int _snowmanxxxxxxx = this.H;
               int _snowmanxxxxxxxx = 1;
               if (this.j.v()) {
                  _snowmanxxxxxxxx += 15;
               }

               if (_snowmanxxxxxx.h()) {
                  _snowmanx++;
                  _snowmanxxxxxxx -= 25 * _snowmanx;
               } else {
                  _snowmanxx++;
                  _snowmanxxxxxxx -= 25 * _snowmanxx;
                  _snowmanxxxxxxxx += 26;
               }

               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               float _snowmanxxxxxxxxx = 1.0F;
               if (_snowmanxxxxx.d()) {
                  this.b(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, 165, 166, 24, 24);
               } else {
                  this.b(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, 141, 166, 24, 24);
                  if (_snowmanxxxxx.b() <= 200) {
                     int _snowmanxxxxxxxxxx = 10 - _snowmanxxxxx.b() / 20;
                     _snowmanxxxxxxxxx = afm.a((float)_snowmanxxxxx.b() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + afm.b((float)_snowmanxxxxx.b() * (float) Math.PI / 5.0F) * afm.a((float)_snowmanxxxxxxxxxx / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               ekc _snowmanxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxx);
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxx;
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx;
               float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxx.add(() -> {
                  this.j.M().a(_snowman.m().g());
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowman);
                  a(_snowman, _snowman + 3, _snowman + 3, this.v(), 18, 18, _snowman);
               });
            }
         }

         _snowmanxxxx.forEach(Runnable::run);
      }
   }

   protected void a(float var1, dfm var2) {
      bfw _snowman = this.k();
      if (_snowman != null) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.j.M().a(c);
         bmb _snowmanx = _snowman.dE();
         aqi _snowmanxx = _snowman.dV().a();
         int _snowmanxxx = this.H / 2;
         int _snowmanxxxx = this.v();
         int _snowmanxxxxx = 182;
         int _snowmanxxxxxx = 91;
         this.d(-90);
         this.b(_snowman, _snowmanxxx - 91, this.I - 22, 0, 0, 182, 22);
         this.b(_snowman, _snowmanxxx - 91 - 1 + _snowman.bm.d * 20, this.I - 22 - 1, 0, 22, 24, 22);
         if (!_snowmanx.a()) {
            if (_snowmanxx == aqi.a) {
               this.b(_snowman, _snowmanxxx - 91 - 29, this.I - 23, 24, 22, 29, 24);
            } else {
               this.b(_snowman, _snowmanxxx + 91, this.I - 23, 53, 22, 29, 24);
            }
         }

         this.d(_snowmanxxxx);
         RenderSystem.enableRescaleNormal();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 9; _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = _snowmanxxx - 90 + _snowmanxxxxxxx * 20 + 2;
            int _snowmanxxxxxxxxx = this.I - 16 - 3;
            this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman, _snowman, _snowman.bm.a.get(_snowmanxxxxxxx));
         }

         if (!_snowmanx.a()) {
            int _snowmanxxxxxxx = this.I - 16 - 3;
            if (_snowmanxx == aqi.a) {
               this.a(_snowmanxxx - 91 - 26, _snowmanxxxxxxx, _snowman, _snowman, _snowmanx);
            } else {
               this.a(_snowmanxxx + 91 + 10, _snowmanxxxxxxx, _snowman, _snowman, _snowmanx);
            }
         }

         if (this.j.k.C == dji.c) {
            float _snowmanxxxxxxx = this.j.s.u(0.0F);
            if (_snowmanxxxxxxx < 1.0F) {
               int _snowmanxxxxxxxx = this.I - 20;
               int _snowmanxxxxxxxxx = _snowmanxxx + 91 + 6;
               if (_snowmanxx == aqi.b) {
                  _snowmanxxxxxxxxx = _snowmanxxx - 91 - 22;
               }

               this.j.M().a(dkw.h);
               int _snowmanxxxxxxxxxx = (int)(_snowmanxxxxxxx * 19.0F);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.b(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, 0, 94, 18, 18);
               this.b(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 18 - _snowmanxxxxxxxxxx, 18, 112 - _snowmanxxxxxxxxxx, 18, _snowmanxxxxxxxxxx);
            }
         }

         RenderSystem.disableRescaleNormal();
         RenderSystem.disableBlend();
      }
   }

   public void a(dfm var1, int var2) {
      this.j.au().a("jumpBar");
      this.j.M().a(dkw.h);
      float _snowman = this.j.s.I();
      int _snowmanx = 182;
      int _snowmanxx = (int)(_snowman * 183.0F);
      int _snowmanxxx = this.I - 32 + 3;
      this.b(_snowman, _snowman, _snowmanxxx, 0, 84, 182, 5);
      if (_snowmanxx > 0) {
         this.b(_snowman, _snowman, _snowmanxxx, 0, 89, _snowmanxx, 5);
      }

      this.j.au().c();
   }

   public void b(dfm var1, int var2) {
      this.j.au().a("expBar");
      this.j.M().a(dkw.h);
      int _snowman = this.j.s.eH();
      if (_snowman > 0) {
         int _snowmanx = 182;
         int _snowmanxx = (int)(this.j.s.bF * 183.0F);
         int _snowmanxxx = this.I - 32 + 3;
         this.b(_snowman, _snowman, _snowmanxxx, 0, 64, 182, 5);
         if (_snowmanxx > 0) {
            this.b(_snowman, _snowman, _snowmanxxx, 0, 69, _snowmanxx, 5);
         }
      }

      this.j.au().c();
      if (this.j.s.bD > 0) {
         this.j.au().a("expLevel");
         String _snowmanx = "" + this.j.s.bD;
         int _snowmanxx = (this.H - this.e().b(_snowmanx)) / 2;
         int _snowmanxxx = this.I - 31 - 4;
         this.e().b(_snowman, _snowmanx, (float)(_snowmanxx + 1), (float)_snowmanxxx, 0);
         this.e().b(_snowman, _snowmanx, (float)(_snowmanxx - 1), (float)_snowmanxxx, 0);
         this.e().b(_snowman, _snowmanx, (float)_snowmanxx, (float)(_snowmanxxx + 1), 0);
         this.e().b(_snowman, _snowmanx, (float)_snowmanxx, (float)(_snowmanxxx - 1), 0);
         this.e().b(_snowman, _snowmanx, (float)_snowmanxx, (float)_snowmanxxx, 8453920);
         this.j.au().c();
      }
   }

   public void b(dfm var1) {
      this.j.au().a("selectedItemName");
      if (this.q > 0 && !this.r.a()) {
         nx _snowman = new oe("").a(this.r.r()).a(this.r.v().e);
         if (this.r.t()) {
            _snowman.a(k.u);
         }

         int _snowmanx = this.e().a(_snowman);
         int _snowmanxx = (this.H - _snowmanx) / 2;
         int _snowmanxxx = this.I - 59;
         if (!this.j.q.a()) {
            _snowmanxxx += 14;
         }

         int _snowmanxxxx = (int)((float)this.q * 256.0F / 10.0F);
         if (_snowmanxxxx > 255) {
            _snowmanxxxx = 255;
         }

         if (_snowmanxxxx > 0) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            a(_snowman, _snowmanxx - 2, _snowmanxxx - 2, _snowmanxx + _snowmanx + 2, _snowmanxxx + 9 + 2, this.j.k.a(0));
            this.e().a(_snowman, _snowman, (float)_snowmanxx, (float)_snowmanxxx, 16777215 + (_snowmanxxxx << 24));
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }

      this.j.au().c();
   }

   public void c(dfm var1) {
      this.j.au().a("demo");
      nr _snowman;
      if (this.j.r.T() >= 120500L) {
         _snowman = e;
      } else {
         _snowman = new of("demo.remainingTime", aft.a((int)(120500L - this.j.r.T())));
      }

      int _snowmanx = this.e().a(_snowman);
      this.e().a(_snowman, _snowman, (float)(this.H - _snowmanx - 10), 5.0F, 16777215);
      this.j.au().c();
   }

   private void a(dfm var1, ddk var2) {
      ddn _snowman = _snowman.a();
      Collection<ddm> _snowmanx = _snowman.i(_snowman);
      List<ddm> _snowmanxx = _snowmanx.stream().filter(var0 -> var0.e() != null && !var0.e().startsWith("#")).collect(Collectors.toList());
      if (_snowmanxx.size() > 15) {
         _snowmanx = Lists.newArrayList(Iterables.skip(_snowmanxx, _snowmanx.size() - 15));
      } else {
         _snowmanx = _snowmanxx;
      }

      List<Pair<ddm, nr>> _snowmanxxx = Lists.newArrayListWithCapacity(_snowmanx.size());
      nr _snowmanxxxx = _snowman.d();
      int _snowmanxxxxx = this.e().a(_snowmanxxxx);
      int _snowmanxxxxxx = _snowmanxxxxx;
      int _snowmanxxxxxxx = this.e().b(": ");

      for (ddm _snowmanxxxxxxxx : _snowmanx) {
         ddl _snowmanxxxxxxxxx = _snowman.i(_snowmanxxxxxxxx.e());
         nr _snowmanxxxxxxxxxx = ddl.a(_snowmanxxxxxxxxx, new oe(_snowmanxxxxxxxx.e()));
         _snowmanxxx.add(Pair.of(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx));
         _snowmanxxxxxx = Math.max(_snowmanxxxxxx, this.e().a(_snowmanxxxxxxxxxx) + _snowmanxxxxxxx + this.e().b(Integer.toString(_snowmanxxxxxxxx.b())));
      }

      int _snowmanxxxxxxxx = _snowmanx.size() * 9;
      int _snowmanxxxxxxxxx = this.I / 2 + _snowmanxxxxxxxx / 3;
      int _snowmanxxxxxxxxxx = 3;
      int _snowmanxxxxxxxxxxx = this.H - _snowmanxxxxxx - 3;
      int _snowmanxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxx = this.j.k.b(0.3F);
      int _snowmanxxxxxxxxxxxxxx = this.j.k.b(0.4F);

      for (Pair<ddm, nr> _snowmanxxxxxxxxxxxxxxx : _snowmanxxx) {
         _snowmanxxxxxxxxxxxx++;
         ddm _snowmanxxxxxxxxxxxxxxxx = (ddm)_snowmanxxxxxxxxxxxxxxx.getFirst();
         nr _snowmanxxxxxxxxxxxxxxxxx = (nr)_snowmanxxxxxxxxxxxxxxx.getSecond();
         String _snowmanxxxxxxxxxxxxxxxxxx = k.m + "" + _snowmanxxxxxxxxxxxxxxxx.b();
         int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx * 9;
         int _snowmanxxxxxxxxxxxxxxxxxxxx = this.H - 3 + 2;
         a(_snowman, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx + 9, _snowmanxxxxxxxxxxxxx);
         this.e().b(_snowman, _snowmanxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxx, -1);
         this.e().b(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxx - this.e().b(_snowmanxxxxxxxxxxxxxxxxxx)), (float)_snowmanxxxxxxxxxxxxxxxxxxx, -1);
         if (_snowmanxxxxxxxxxxxx == _snowmanx.size()) {
            a(_snowman, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx - 9 - 1, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxx);
            a(_snowman, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            this.e().b(_snowman, _snowmanxxxx, (float)(_snowmanxxxxxxxxxxx + _snowmanxxxxxx / 2 - _snowmanxxxxx / 2), (float)(_snowmanxxxxxxxxxxxxxxxxxxx - 9), -1);
         }
      }
   }

   private bfw k() {
      return !(this.j.aa() instanceof bfw) ? null : (bfw)this.j.aa();
   }

   private aqm l() {
      bfw _snowman = this.k();
      if (_snowman != null) {
         aqa _snowmanx = _snowman.ct();
         if (_snowmanx == null) {
            return null;
         }

         if (_snowmanx instanceof aqm) {
            return (aqm)_snowmanx;
         }
      }

      return null;
   }

   private int a(aqm var1) {
      if (_snowman != null && _snowman.bd()) {
         float _snowman = _snowman.dx();
         int _snowmanx = (int)(_snowman + 0.5F) / 2;
         if (_snowmanx > 30) {
            _snowmanx = 30;
         }

         return _snowmanx;
      } else {
         return 0;
      }
   }

   private int a(int var1) {
      return (int)Math.ceil((double)_snowman / 10.0);
   }

   private void e(dfm var1) {
      bfw _snowman = this.k();
      if (_snowman != null) {
         int _snowmanx = afm.f(_snowman.dk());
         boolean _snowmanxx = this.G > (long)this.m && (this.G - (long)this.m) / 3L % 2L == 1L;
         long _snowmanxxx = x.b();
         if (_snowmanx < this.D && _snowman.P > 0) {
            this.F = _snowmanxxx;
            this.G = (long)(this.m + 20);
         } else if (_snowmanx > this.D && _snowman.P > 0) {
            this.F = _snowmanxxx;
            this.G = (long)(this.m + 10);
         }

         if (_snowmanxxx - this.F > 1000L) {
            this.D = _snowmanx;
            this.E = _snowmanx;
            this.F = _snowmanxxx;
         }

         this.D = _snowmanx;
         int _snowmanxxxx = this.E;
         this.i.setSeed((long)(this.m * 312871));
         bhy _snowmanxxxxx = _snowman.eI();
         int _snowmanxxxxxx = _snowmanxxxxx.a();
         int _snowmanxxxxxxx = this.H / 2 - 91;
         int _snowmanxxxxxxxx = this.H / 2 + 91;
         int _snowmanxxxxxxxxx = this.I - 39;
         float _snowmanxxxxxxxxxx = (float)_snowman.b(arl.a);
         int _snowmanxxxxxxxxxxx = afm.f(_snowman.dT());
         int _snowmanxxxxxxxxxxxx = afm.f((_snowmanxxxxxxxxxx + (float)_snowmanxxxxxxxxxxx) / 2.0F / 10.0F);
         int _snowmanxxxxxxxxxxxxx = Math.max(10 - (_snowmanxxxxxxxxxxxx - 2), 3);
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - (_snowmanxxxxxxxxxxxx - 1) * _snowmanxxxxxxxxxxxxx - 10;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - 10;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxx = _snowman.du();
         int _snowmanxxxxxxxxxxxxxxxxxx = -1;
         if (_snowman.a(apw.j)) {
            _snowmanxxxxxxxxxxxxxxxxxx = this.m % afm.f(_snowmanxxxxxxxxxx + 5.0F);
         }

         this.j.au().a("armor");

         for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < 10; _snowmanxxxxxxxxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxxxxxxxxxx > 0) {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx * 8;
               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxxxxxxxxxxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 34, 9, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxxxxxxxxxxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 25, 9, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 > _snowmanxxxxxxxxxxxxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 16, 9, 9, 9);
               }
            }
         }

         this.j.au().b("health");

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = afm.f((_snowmanxxxxxxxxxx + (float)_snowmanxxxxxxxxxxx) / 2.0F) - 1; _snowmanxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxx--) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 16;
            if (_snowman.a(apw.s)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxx += 36;
            } else if (_snowman.a(apw.t)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxx += 72;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0;
            if (_snowmanxx) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 1;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = afm.f((float)(_snowmanxxxxxxxxxxxxxxxxxxxxx + 1) / 10.0F) - 1;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx % 10 * 8;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
            if (_snowmanx <= 4) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx += this.i.nextInt(2);
            }

            if (_snowmanxxxxxxxxxxxxxxxx <= 0 && _snowmanxxxxxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx -= 2;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            if (_snowman.l.h().n()) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 5;
            }

            this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, 16 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 9, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
            if (_snowmanxx) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 54, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 63, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
               }
            }

            if (_snowmanxxxxxxxxxxxxxxxx > 0) {
               if (_snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxx % 2 == 1) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 153, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
                  _snowmanxxxxxxxxxxxxxxxx--;
               } else {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 144, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
                  _snowmanxxxxxxxxxxxxxxxx -= 2;
               }
            } else {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 36, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 45, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9);
               }
            }
         }

         aqm _snowmanxxxxxxxxxxxxxxxxxxxxx = this.l();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 0) {
            this.j.au().b("food");

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 10; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 16;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               if (_snowman.a(apw.q)) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += 36;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 13;
               }

               if (_snowman.eI().e() <= 0.0F && this.m % (_snowmanxxxxxx * 3 + 1) == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + (this.i.nextInt(3) - 1);
               }

               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9;
               this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 16 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 9, 27, 9, 9);
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 36, 27, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 45, 27, 9, 9);
               }
            }

            _snowmanxxxxxxxxxxxxxxx -= 10;
         }

         this.j.au().b("air");
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.bH();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.min(_snowman.bI(), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         if (_snowman.a(aef.b) || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx) - 1;
            _snowmanxxxxxxxxxxxxxxx -= _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 10;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.f((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - 2) * 10.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.f((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 10.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  this.b(_snowman, _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9, _snowmanxxxxxxxxxxxxxxx, 16, 18, 9, 9);
               } else {
                  this.b(_snowman, _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9, _snowmanxxxxxxxxxxxxxxx, 25, 18, 9, 9);
               }
            }
         }

         this.j.au().c();
      }
   }

   private void f(dfm var1) {
      aqm _snowman = this.l();
      if (_snowman != null) {
         int _snowmanx = this.a(_snowman);
         if (_snowmanx != 0) {
            int _snowmanxx = (int)Math.ceil((double)_snowman.dk());
            this.j.au().b("mountHealth");
            int _snowmanxxx = this.I - 39;
            int _snowmanxxxx = this.H / 2 + 91;
            int _snowmanxxxxx = _snowmanxxx;
            int _snowmanxxxxxx = 0;

            for (boolean _snowmanxxxxxxx = false; _snowmanx > 0; _snowmanxxxxxx += 20) {
               int _snowmanxxxxxxxx = Math.min(_snowmanx, 10);
               _snowmanx -= _snowmanxxxxxxxx;

               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxx = 52;
                  int _snowmanxxxxxxxxxxx = 0;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxxxxxx * 8 - 9;
                  this.b(_snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 52 + _snowmanxxxxxxxxxxx * 9, 9, 9, 9);
                  if (_snowmanxxxxxxxxx * 2 + 1 + _snowmanxxxxxx < _snowmanxx) {
                     this.b(_snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 88, 9, 9, 9);
                  }

                  if (_snowmanxxxxxxxxx * 2 + 1 + _snowmanxxxxxx == _snowmanxx) {
                     this.b(_snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 97, 9, 9, 9);
                  }
               }

               _snowmanxxxxx -= 10;
            }
         }
      }
   }

   private void m() {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableAlphaTest();
      this.j.M().a(d);
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      _snowmanx.a(7, dfk.n);
      _snowmanx.a(0.0, (double)this.I, -90.0).a(0.0F, 1.0F).d();
      _snowmanx.a((double)this.H, (double)this.I, -90.0).a(1.0F, 1.0F).d();
      _snowmanx.a((double)this.H, 0.0, -90.0).a(1.0F, 0.0F).d();
      _snowmanx.a(0.0, 0.0, -90.0).a(0.0F, 0.0F).d();
      _snowman.b();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(aqa var1) {
      if (_snowman != null) {
         float _snowman = afm.a(1.0F - _snowman.aR(), 0.0F, 1.0F);
         this.a = (float)((double)this.a + (double)(_snowman - this.a) * 0.01);
      }
   }

   private void b(aqa var1) {
      cfu _snowman = this.j.r.f();
      float _snowmanx = (float)_snowman.a(_snowman);
      double _snowmanxx = Math.min(_snowman.p() * (double)_snowman.q() * 1000.0, Math.abs(_snowman.k() - _snowman.i()));
      double _snowmanxxx = Math.max((double)_snowman.r(), _snowmanxx);
      if ((double)_snowmanx < _snowmanxxx) {
         _snowmanx = 1.0F - (float)((double)_snowmanx / _snowmanxxx);
      } else {
         _snowmanx = 0.0F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(dem.r.o, dem.j.k, dem.r.e, dem.j.n);
      if (_snowmanx > 0.0F) {
         RenderSystem.color4f(0.0F, _snowmanx, _snowmanx, 1.0F);
      } else {
         RenderSystem.color4f(this.a, this.a, this.a, 1.0F);
      }

      this.j.M().a(b);
      dfo _snowmanxxxx = dfo.a();
      dfh _snowmanxxxxx = _snowmanxxxx.c();
      _snowmanxxxxx.a(7, dfk.n);
      _snowmanxxxxx.a(0.0, (double)this.I, -90.0).a(0.0F, 1.0F).d();
      _snowmanxxxxx.a((double)this.H, (double)this.I, -90.0).a(1.0F, 1.0F).d();
      _snowmanxxxxx.a((double)this.H, 0.0, -90.0).a(1.0F, 0.0F).d();
      _snowmanxxxxx.a(0.0, 0.0, -90.0).a(0.0F, 0.0F).d();
      _snowmanxxxx.b();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
   }

   private void a(float var1) {
      if (_snowman < 1.0F) {
         _snowman *= _snowman;
         _snowman *= _snowman;
         _snowman = _snowman * 0.8F + 0.2F;
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowman);
      this.j.M().a(ekb.d);
      ekc _snowman = this.j.ab().a().a(bup.cT.n());
      float _snowmanx = _snowman.h();
      float _snowmanxx = _snowman.j();
      float _snowmanxxx = _snowman.i();
      float _snowmanxxxx = _snowman.k();
      dfo _snowmanxxxxx = dfo.a();
      dfh _snowmanxxxxxx = _snowmanxxxxx.c();
      _snowmanxxxxxx.a(7, dfk.n);
      _snowmanxxxxxx.a(0.0, (double)this.I, -90.0).a(_snowmanx, _snowmanxxxx).d();
      _snowmanxxxxxx.a((double)this.H, (double)this.I, -90.0).a(_snowmanxxx, _snowmanxxxx).d();
      _snowmanxxxxxx.a((double)this.H, 0.0, -90.0).a(_snowmanxxx, _snowmanxx).d();
      _snowmanxxxxxx.a(0.0, 0.0, -90.0).a(_snowmanx, _snowmanxx).d();
      _snowmanxxxxx.b();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(int var1, int var2, float var3, bfw var4, bmb var5) {
      if (!_snowman.a()) {
         float _snowman = (float)_snowman.D() - _snowman;
         if (_snowman > 0.0F) {
            RenderSystem.pushMatrix();
            float _snowmanx = 1.0F + _snowman / 5.0F;
            RenderSystem.translatef((float)(_snowman + 8), (float)(_snowman + 12), 0.0F);
            RenderSystem.scalef(1.0F / _snowmanx, (_snowmanx + 1.0F) / 2.0F, 1.0F);
            RenderSystem.translatef((float)(-(_snowman + 8)), (float)(-(_snowman + 12)), 0.0F);
         }

         this.k.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowman > 0.0F) {
            RenderSystem.popMatrix();
         }

         this.k.a(this.j.g, _snowman, _snowman, _snowman);
      }
   }

   public void b() {
      if (this.o > 0) {
         this.o--;
      }

      if (this.x > 0) {
         this.x--;
         if (this.x <= 0) {
            this.y = null;
            this.z = null;
         }
      }

      this.m++;
      aqa _snowman = this.j.aa();
      if (_snowman != null) {
         this.a(_snowman);
      }

      if (this.j.s != null) {
         bmb _snowmanx = this.j.s.bm.f();
         if (_snowmanx.a()) {
            this.q = 0;
         } else if (this.r.a() || _snowmanx.b() != this.r.b() || !_snowmanx.r().equals(this.r.r())) {
            this.q = 40;
         } else if (this.q > 0) {
            this.q--;
         }

         this.r = _snowmanx;
      }
   }

   public void a(nr var1) {
      this.a(new of("record.nowPlaying", _snowman), true);
   }

   public void a(nr var1, boolean var2) {
      this.n = _snowman;
      this.o = 60;
      this.p = _snowman;
   }

   public void a(@Nullable nr var1, @Nullable nr var2, int var3, int var4, int var5) {
      if (_snowman == null && _snowman == null && _snowman < 0 && _snowman < 0 && _snowman < 0) {
         this.y = null;
         this.z = null;
         this.x = 0;
      } else if (_snowman != null) {
         this.y = _snowman;
         this.x = this.A + this.B + this.C;
      } else if (_snowman != null) {
         this.z = _snowman;
      } else {
         if (_snowman >= 0) {
            this.A = _snowman;
         }

         if (_snowman >= 0) {
            this.B = _snowman;
         }

         if (_snowman >= 0) {
            this.C = _snowman;
         }

         if (this.x > 0) {
            this.x = this.A + this.B + this.C;
         }
      }
   }

   public UUID b(nr var1) {
      String _snowman = afr.a(_snowman);
      String _snowmanx = StringUtils.substringBetween(_snowman, "<", ">");
      return _snowmanx == null ? x.b : this.j.aB().a(_snowmanx);
   }

   public void a(no var1, nr var2, UUID var3) {
      if (!this.j.a(_snowman)) {
         if (!this.j.k.ae || !this.j.a(this.b(_snowman))) {
            for (dky _snowman : this.J.get(_snowman)) {
               _snowman.a(_snowman, _snowman, _snowman);
            }
         }
      }
   }

   public dlk c() {
      return this.l;
   }

   public int d() {
      return this.m;
   }

   public dku e() {
      return this.j.g;
   }

   public dml f() {
      return this.u;
   }

   public dly g() {
      return this.v;
   }

   public void h() {
      this.v.a();
      this.w.a();
      this.j.an().a();
   }

   public dli i() {
      return this.w;
   }

   public void j() {
      this.s.a();
   }
}
