import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class dpp<T extends bic> extends dot implements dqq<T> {
   public static final vk a = new vk("textures/gui/container/inventory.png");
   protected int b = 176;
   protected int c = 166;
   protected int p;
   protected int q;
   protected int r;
   protected int s;
   protected final T t;
   protected final bfv u;
   @Nullable
   protected bjr v;
   @Nullable
   private bjr A;
   @Nullable
   private bjr B;
   @Nullable
   private bjr C;
   @Nullable
   private bjr D;
   protected int w;
   protected int x;
   private boolean E;
   private bmb F = bmb.b;
   private int G;
   private int H;
   private long I;
   private bmb J = bmb.b;
   private long K;
   protected final Set<bjr> y = Sets.newHashSet();
   protected boolean z;
   private int L;
   private int M;
   private boolean N;
   private int O;
   private long P;
   private int Q;
   private boolean R;
   private bmb S = bmb.b;

   public dpp(T var1, bfv var2, nr var3) {
      super(_snowman);
      this.t = _snowman;
      this.u = _snowman;
      this.N = true;
      this.p = 8;
      this.q = 6;
      this.r = 8;
      this.s = this.c - 94;
   }

   @Override
   protected void b() {
      super.b();
      this.w = (this.k - this.b) / 2;
      this.x = (this.l - this.c) / 2;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.disableRescaleNormal();
      RenderSystem.disableDepthTest();
      super.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)_snowman, (float)_snowmanx, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableRescaleNormal();
      this.v = null;
      int _snowmanxx = 240;
      int _snowmanxxx = 240;
      RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      for (int _snowmanxxxx = 0; _snowmanxxxx < this.t.a.size(); _snowmanxxxx++) {
         bjr _snowmanxxxxx = this.t.a.get(_snowmanxxxx);
         if (_snowmanxxxxx.b()) {
            this.a(_snowman, _snowmanxxxxx);
         }

         if (this.a(_snowmanxxxxx, (double)_snowman, (double)_snowman) && _snowmanxxxxx.b()) {
            this.v = _snowmanxxxxx;
            RenderSystem.disableDepthTest();
            int _snowmanxxxxxx = _snowmanxxxxx.e;
            int _snowmanxxxxxxx = _snowmanxxxxx.f;
            RenderSystem.colorMask(true, true, true, false);
            this.a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx + 16, _snowmanxxxxxxx + 16, -2130706433, -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
         }
      }

      this.b(_snowman, _snowman, _snowman);
      bfv _snowmanxxxx = this.i.s.bm;
      bmb _snowmanxxxxxx = this.F.a() ? _snowmanxxxx.m() : this.F;
      if (!_snowmanxxxxxx.a()) {
         int _snowmanxxxxxxx = 8;
         int _snowmanxxxxxxxx = this.F.a() ? 8 : 16;
         String _snowmanxxxxxxxxx = null;
         if (!this.F.a() && this.E) {
            _snowmanxxxxxx = _snowmanxxxxxx.i();
            _snowmanxxxxxx.e(afm.f((float)_snowmanxxxxxx.E() / 2.0F));
         } else if (this.z && this.y.size() > 1) {
            _snowmanxxxxxx = _snowmanxxxxxx.i();
            _snowmanxxxxxx.e(this.O);
            if (_snowmanxxxxxx.a()) {
               _snowmanxxxxxxxxx = "" + k.o + "0";
            }
         }

         this.a(_snowmanxxxxxx, _snowman - _snowman - 8, _snowman - _snowmanx - _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      }

      if (!this.J.a()) {
         float _snowmanxxxxxxx = (float)(x.b() - this.I) / 100.0F;
         if (_snowmanxxxxxxx >= 1.0F) {
            _snowmanxxxxxxx = 1.0F;
            this.J = bmb.b;
         }

         int _snowmanxxxxxxxx = this.B.e - this.G;
         int _snowmanxxxxxxxxx = this.B.f - this.H;
         int _snowmanxxxxxxxxxx = this.G + (int)((float)_snowmanxxxxxxxx * _snowmanxxxxxxx);
         int _snowmanxxxxxxxxxxx = this.H + (int)((float)_snowmanxxxxxxxxx * _snowmanxxxxxxx);
         this.a(this.J, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, null);
      }

      RenderSystem.popMatrix();
      RenderSystem.enableDepthTest();
   }

   protected void a(dfm var1, int var2, int var3) {
      if (this.i.s.bm.m().a() && this.v != null && this.v.f()) {
         this.a(_snowman, this.v.e(), _snowman, _snowman);
      }
   }

   private void a(bmb var1, int var2, int var3, String var4) {
      RenderSystem.translatef(0.0F, 0.0F, 32.0F);
      this.d(200);
      this.j.b = 200.0F;
      this.j.b(_snowman, _snowman, _snowman);
      this.j.a(this.o, _snowman, _snowman, _snowman - (this.F.a() ? 0 : 8), _snowman);
      this.d(0);
      this.j.b = 0.0F;
   }

   protected void b(dfm var1, int var2, int var3) {
      this.o.b(_snowman, this.d, (float)this.p, (float)this.q, 4210752);
      this.o.b(_snowman, this.u.d(), (float)this.r, (float)this.s, 4210752);
   }

   protected abstract void a(dfm var1, float var2, int var3, int var4);

   private void a(dfm var1, bjr var2) {
      int _snowman = _snowman.e;
      int _snowmanx = _snowman.f;
      bmb _snowmanxx = _snowman.e();
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = _snowman == this.A && !this.F.a() && !this.E;
      bmb _snowmanxxxxx = this.i.s.bm.m();
      String _snowmanxxxxxx = null;
      if (_snowman == this.A && !this.F.a() && this.E && !_snowmanxx.a()) {
         _snowmanxx = _snowmanxx.i();
         _snowmanxx.e(_snowmanxx.E() / 2);
      } else if (this.z && this.y.contains(_snowman) && !_snowmanxxxxx.a()) {
         if (this.y.size() == 1) {
            return;
         }

         if (bic.a(_snowman, _snowmanxxxxx, true) && this.t.b(_snowman)) {
            _snowmanxx = _snowmanxxxxx.i();
            _snowmanxxx = true;
            bic.a(this.y, this.L, _snowmanxx, _snowman.e().a() ? 0 : _snowman.e().E());
            int _snowmanxxxxxxx = Math.min(_snowmanxx.c(), _snowman.b(_snowmanxx));
            if (_snowmanxx.E() > _snowmanxxxxxxx) {
               _snowmanxxxxxx = k.o.toString() + _snowmanxxxxxxx;
               _snowmanxx.e(_snowmanxxxxxxx);
            }
         } else {
            this.y.remove(_snowman);
            this.l();
         }
      }

      this.d(100);
      this.j.b = 100.0F;
      if (_snowmanxx.a() && _snowman.b()) {
         Pair<vk, vk> _snowmanxxxxxxx = _snowman.c();
         if (_snowmanxxxxxxx != null) {
            ekc _snowmanxxxxxxxx = this.i.a((vk)_snowmanxxxxxxx.getFirst()).apply((vk)_snowmanxxxxxxx.getSecond());
            this.i.M().a(_snowmanxxxxxxxx.m().g());
            a(_snowman, _snowman, _snowmanx, this.v(), 16, 16, _snowmanxxxxxxxx);
            _snowmanxxxx = true;
         }
      }

      if (!_snowmanxxxx) {
         if (_snowmanxxx) {
            a(_snowman, _snowman, _snowmanx, _snowman + 16, _snowmanx + 16, -2130706433);
         }

         RenderSystem.enableDepthTest();
         this.j.a(this.i.s, _snowmanxx, _snowman, _snowmanx);
         this.j.a(this.o, _snowmanxx, _snowman, _snowmanx, _snowmanxxxxxx);
      }

      this.j.b = 0.0F;
      this.d(0);
   }

   private void l() {
      bmb _snowman = this.i.s.bm.m();
      if (!_snowman.a() && this.z) {
         if (this.L == 2) {
            this.O = _snowman.c();
         } else {
            this.O = _snowman.E();

            for (bjr _snowmanx : this.y) {
               bmb _snowmanxx = _snowman.i();
               bmb _snowmanxxx = _snowmanx.e();
               int _snowmanxxxx = _snowmanxxx.a() ? 0 : _snowmanxxx.E();
               bic.a(this.y, this.L, _snowmanxx, _snowmanxxxx);
               int _snowmanxxxxx = Math.min(_snowmanxx.c(), _snowmanx.b(_snowmanxx));
               if (_snowmanxx.E() > _snowmanxxxxx) {
                  _snowmanxx.e(_snowmanxxxxx);
               }

               this.O = this.O - (_snowmanxx.E() - _snowmanxxxx);
            }
         }
      }
   }

   @Nullable
   private bjr a(double var1, double var3) {
      for (int _snowman = 0; _snowman < this.t.a.size(); _snowman++) {
         bjr _snowmanx = this.t.a.get(_snowman);
         if (this.a(_snowmanx, _snowman, _snowman) && _snowmanx.b()) {
            return _snowmanx;
         }
      }

      return null;
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else {
         boolean _snowman = this.i.k.ar.a(_snowman);
         bjr _snowmanx = this.a(_snowman, _snowman);
         long _snowmanxx = x.b();
         this.R = this.D == _snowmanx && _snowmanxx - this.P < 250L && this.Q == _snowman;
         this.N = false;
         if (_snowman != 0 && _snowman != 1 && !_snowman) {
            this.a(_snowman);
         } else {
            int _snowmanxxx = this.w;
            int _snowmanxxxx = this.x;
            boolean _snowmanxxxxx = this.a(_snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowman);
            int _snowmanxxxxxx = -1;
            if (_snowmanx != null) {
               _snowmanxxxxxx = _snowmanx.d;
            }

            if (_snowmanxxxxx) {
               _snowmanxxxxxx = -999;
            }

            if (this.i.k.Y && _snowmanxxxxx && this.i.s.bm.m().a()) {
               this.i.a(null);
               return true;
            }

            if (_snowmanxxxxxx != -1) {
               if (this.i.k.Y) {
                  if (_snowmanx != null && _snowmanx.f()) {
                     this.A = _snowmanx;
                     this.F = bmb.b;
                     this.E = _snowman == 1;
                  } else {
                     this.A = null;
                  }
               } else if (!this.z) {
                  if (this.i.s.bm.m().a()) {
                     if (this.i.k.ar.a(_snowman)) {
                        this.a(_snowmanx, _snowmanxxxxxx, _snowman, bik.d);
                     } else {
                        boolean _snowmanxxxxxxx = _snowmanxxxxxx != -999 && (deo.a(djz.C().aD().i(), 340) || deo.a(djz.C().aD().i(), 344));
                        bik _snowmanxxxxxxxx = bik.a;
                        if (_snowmanxxxxxxx) {
                           this.S = _snowmanx != null && _snowmanx.f() ? _snowmanx.e().i() : bmb.b;
                           _snowmanxxxxxxxx = bik.b;
                        } else if (_snowmanxxxxxx == -999) {
                           _snowmanxxxxxxxx = bik.e;
                        }

                        this.a(_snowmanx, _snowmanxxxxxx, _snowman, _snowmanxxxxxxxx);
                     }

                     this.N = true;
                  } else {
                     this.z = true;
                     this.M = _snowman;
                     this.y.clear();
                     if (_snowman == 0) {
                        this.L = 0;
                     } else if (_snowman == 1) {
                        this.L = 1;
                     } else if (this.i.k.ar.a(_snowman)) {
                        this.L = 2;
                     }
                  }
               }
            }
         }

         this.D = _snowmanx;
         this.P = _snowmanxx;
         this.Q = _snowman;
         return true;
      }
   }

   private void a(int var1) {
      if (this.v != null && this.i.s.bm.m().a()) {
         if (this.i.k.an.a(_snowman)) {
            this.a(this.v, this.v.d, 40, bik.c);
            return;
         }

         for (int _snowman = 0; _snowman < 9; _snowman++) {
            if (this.i.k.aC[_snowman].a(_snowman)) {
               this.a(this.v, this.v.d, _snowman, bik.c);
            }
         }
      }
   }

   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      return _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      bjr _snowman = this.a(_snowman, _snowman);
      bmb _snowmanx = this.i.s.bm.m();
      if (this.A != null && this.i.k.Y) {
         if (_snowman == 0 || _snowman == 1) {
            if (this.F.a()) {
               if (_snowman != this.A && !this.A.e().a()) {
                  this.F = this.A.e().i();
               }
            } else if (this.F.E() > 1 && _snowman != null && bic.a(_snowman, this.F, false)) {
               long _snowmanxx = x.b();
               if (this.C == _snowman) {
                  if (_snowmanxx - this.K > 500L) {
                     this.a(this.A, this.A.d, 0, bik.a);
                     this.a(_snowman, _snowman.d, 1, bik.a);
                     this.a(this.A, this.A.d, 0, bik.a);
                     this.K = _snowmanxx + 750L;
                     this.F.g(1);
                  }
               } else {
                  this.C = _snowman;
                  this.K = _snowmanxx;
               }
            }
         }
      } else if (this.z && _snowman != null && !_snowmanx.a() && (_snowmanx.E() > this.y.size() || this.L == 2) && bic.a(_snowman, _snowmanx, true) && _snowman.a(_snowmanx) && this.t.b(_snowman)) {
         this.y.add(_snowman);
         this.l();
      }

      return true;
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      bjr _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.w;
      int _snowmanxx = this.x;
      boolean _snowmanxxx = this.a(_snowman, _snowman, _snowmanx, _snowmanxx, _snowman);
      int _snowmanxxxx = -1;
      if (_snowman != null) {
         _snowmanxxxx = _snowman.d;
      }

      if (_snowmanxxx) {
         _snowmanxxxx = -999;
      }

      if (this.R && _snowman != null && _snowman == 0 && this.t.a(bmb.b, _snowman)) {
         if (y()) {
            if (!this.S.a()) {
               for (bjr _snowmanxxxxx : this.t.a) {
                  if (_snowmanxxxxx != null && _snowmanxxxxx.a(this.i.s) && _snowmanxxxxx.f() && _snowmanxxxxx.c == _snowman.c && bic.a(_snowmanxxxxx, this.S, true)) {
                     this.a(_snowmanxxxxx, _snowmanxxxxx.d, _snowman, bik.b);
                  }
               }
            }
         } else {
            this.a(_snowman, _snowmanxxxx, _snowman, bik.g);
         }

         this.R = false;
         this.P = 0L;
      } else {
         if (this.z && this.M != _snowman) {
            this.z = false;
            this.y.clear();
            this.N = true;
            return true;
         }

         if (this.N) {
            this.N = false;
            return true;
         }

         if (this.A != null && this.i.k.Y) {
            if (_snowman == 0 || _snowman == 1) {
               if (this.F.a() && _snowman != this.A) {
                  this.F = this.A.e();
               }

               boolean _snowmanxxxxxx = bic.a(_snowman, this.F, false);
               if (_snowmanxxxx != -1 && !this.F.a() && _snowmanxxxxxx) {
                  this.a(this.A, this.A.d, _snowman, bik.a);
                  this.a(_snowman, _snowmanxxxx, 0, bik.a);
                  if (this.i.s.bm.m().a()) {
                     this.J = bmb.b;
                  } else {
                     this.a(this.A, this.A.d, _snowman, bik.a);
                     this.G = afm.c(_snowman - (double)_snowmanx);
                     this.H = afm.c(_snowman - (double)_snowmanxx);
                     this.B = this.A;
                     this.J = this.F;
                     this.I = x.b();
                  }
               } else if (!this.F.a()) {
                  this.G = afm.c(_snowman - (double)_snowmanx);
                  this.H = afm.c(_snowman - (double)_snowmanxx);
                  this.B = this.A;
                  this.J = this.F;
                  this.I = x.b();
               }

               this.F = bmb.b;
               this.A = null;
            }
         } else if (this.z && !this.y.isEmpty()) {
            this.a(null, -999, bic.b(0, this.L), bik.f);

            for (bjr _snowmanxxxxxx : this.y) {
               this.a(_snowmanxxxxxx, _snowmanxxxxxx.d, bic.b(1, this.L), bik.f);
            }

            this.a(null, -999, bic.b(2, this.L), bik.f);
         } else if (!this.i.s.bm.m().a()) {
            if (this.i.k.ar.a(_snowman)) {
               this.a(_snowman, _snowmanxxxx, _snowman, bik.d);
            } else {
               boolean _snowmanxxxxxx = _snowmanxxxx != -999 && (deo.a(djz.C().aD().i(), 340) || deo.a(djz.C().aD().i(), 344));
               if (_snowmanxxxxxx) {
                  this.S = _snowman != null && _snowman.f() ? _snowman.e().i() : bmb.b;
               }

               this.a(_snowman, _snowmanxxxx, _snowman, _snowmanxxxxxx ? bik.b : bik.a);
            }
         }
      }

      if (this.i.s.bm.m().a()) {
         this.P = 0L;
      }

      this.z = false;
      return true;
   }

   private boolean a(bjr var1, double var2, double var4) {
      return this.a(_snowman.e, _snowman.f, 16, 16, _snowman, _snowman);
   }

   protected boolean a(int var1, int var2, int var3, int var4, double var5, double var7) {
      int _snowman = this.w;
      int _snowmanx = this.x;
      _snowman -= (double)_snowman;
      _snowman -= (double)_snowmanx;
      return _snowman >= (double)(_snowman - 1) && _snowman < (double)(_snowman + _snowman + 1) && _snowman >= (double)(_snowman - 1) && _snowman < (double)(_snowman + _snowman + 1);
   }

   protected void a(bjr var1, int var2, int var3, bik var4) {
      if (_snowman != null) {
         _snowman = _snowman.d;
      }

      this.i.q.a(this.t.b, _snowman, _snowman, _snowman, this.i.s);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (this.i.k.am.a(_snowman, _snowman)) {
         this.at_();
         return true;
      } else {
         this.b(_snowman, _snowman);
         if (this.v != null && this.v.f()) {
            if (this.i.k.ar.a(_snowman, _snowman)) {
               this.a(this.v, this.v.d, 0, bik.d);
            } else if (this.i.k.ao.a(_snowman, _snowman)) {
               this.a(this.v, this.v.d, x() ? 1 : 0, bik.e);
            }
         }

         return true;
      }
   }

   protected boolean b(int var1, int var2) {
      if (this.i.s.bm.m().a() && this.v != null) {
         if (this.i.k.an.a(_snowman, _snowman)) {
            this.a(this.v, this.v.d, 40, bik.c);
            return true;
         }

         for (int _snowman = 0; _snowman < 9; _snowman++) {
            if (this.i.k.aC[_snowman].a(_snowman, _snowman)) {
               this.a(this.v, this.v.d, _snowman, bik.c);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void e() {
      if (this.i.s != null) {
         this.t.b(this.i.s);
      }
   }

   @Override
   public boolean ay_() {
      return false;
   }

   @Override
   public void d() {
      super.d();
      if (!this.i.s.aX() || this.i.s.y) {
         this.i.s.m();
      }
   }

   @Override
   public T h() {
      return this.t;
   }

   @Override
   public void at_() {
      this.i.s.m();
      super.at_();
   }
}
