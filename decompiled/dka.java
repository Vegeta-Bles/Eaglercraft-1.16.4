import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.glfw.GLFWDropCallback;

public class dka {
   private final djz a;
   private boolean b;
   private boolean c;
   private boolean d;
   private double e;
   private double f;
   private int g;
   private int h = -1;
   private boolean i = true;
   private int j;
   private double k;
   private final afp l = new afp();
   private final afp m = new afp();
   private double n;
   private double o;
   private double p;
   private double q = Double.MIN_VALUE;
   private boolean r;

   public dka(djz var1) {
      this.a = _snowman;
   }

   private void a(long var1, int var3, int var4, int var5) {
      if (_snowman == this.a.aD().i()) {
         boolean _snowman = _snowman == 1;
         if (djz.a && _snowman == 0) {
            if (_snowman) {
               if ((_snowman & 2) == 2) {
                  _snowman = 1;
                  this.g++;
               }
            } else if (this.g > 0) {
               _snowman = 1;
               this.g--;
            }
         }

         int _snowmanx = _snowman;
         if (_snowman) {
            if (this.a.k.Y && this.j++ > 0) {
               return;
            }

            this.h = _snowmanx;
            this.k = ddt.b();
         } else if (this.h != -1) {
            if (this.a.k.Y && --this.j > 0) {
               return;
            }

            this.h = -1;
         }

         boolean[] _snowmanxx = new boolean[]{false};
         if (this.a.z == null) {
            if (this.a.y == null) {
               if (!this.r && _snowman) {
                  this.i();
               }
            } else {
               double _snowmanxxx = this.e * (double)this.a.aD().o() / (double)this.a.aD().m();
               double _snowmanxxxx = this.f * (double)this.a.aD().p() / (double)this.a.aD().n();
               if (_snowman) {
                  dot.a(() -> _snowman[0] = this.a.y.a(_snowman, _snowman, _snowman), "mouseClicked event handler", this.a.y.getClass().getCanonicalName());
               } else {
                  dot.a(() -> _snowman[0] = this.a.y.c(_snowman, _snowman, _snowman), "mouseReleased event handler", this.a.y.getClass().getCanonicalName());
               }
            }
         }

         if (!_snowmanxx[0] && (this.a.y == null || this.a.y.n) && this.a.z == null) {
            if (_snowmanx == 0) {
               this.b = _snowman;
            } else if (_snowmanx == 2) {
               this.c = _snowman;
            } else if (_snowmanx == 1) {
               this.d = _snowman;
            }

            djw.a(deo.b.c.a(_snowmanx), _snowman);
            if (_snowman) {
               if (this.a.s.a_() && _snowmanx == 2) {
                  this.a.j.f().b();
               } else {
                  djw.a(deo.b.c.a(_snowmanx));
               }
            }
         }
      }
   }

   private void a(long var1, double var3, double var5) {
      if (_snowman == djz.C().aD().i()) {
         double _snowman = (this.a.k.S ? Math.signum(_snowman) : _snowman) * this.a.k.G;
         if (this.a.z == null) {
            if (this.a.y != null) {
               double _snowmanx = this.e * (double)this.a.aD().o() / (double)this.a.aD().m();
               double _snowmanxx = this.f * (double)this.a.aD().p() / (double)this.a.aD().n();
               this.a.y.a(_snowmanx, _snowmanxx, _snowman);
            } else if (this.a.s != null) {
               if (this.p != 0.0 && Math.signum(_snowman) != Math.signum(this.p)) {
                  this.p = 0.0;
               }

               this.p += _snowman;
               float _snowmanx = (float)((int)this.p);
               if (_snowmanx == 0.0F) {
                  return;
               }

               this.p -= (double)_snowmanx;
               if (this.a.s.a_()) {
                  if (this.a.j.f().a()) {
                     this.a.j.f().a((double)(-_snowmanx));
                  } else {
                     float _snowmanxx = afm.a(this.a.s.bC.a() + _snowmanx * 0.005F, 0.0F, 0.2F);
                     this.a.s.bC.a(_snowmanxx);
                  }
               } else {
                  this.a.s.bm.a((double)_snowmanx);
               }
            }
         }
      }
   }

   private void a(long var1, List<Path> var3) {
      if (this.a.y != null) {
         this.a.y.a(_snowman);
      }
   }

   public void a(long var1) {
      deo.a(
         _snowman,
         (var1x, var3, var5) -> this.a.execute(() -> this.b(var1x, var3, var5)),
         (var1x, var3, var4, var5) -> this.a.execute(() -> this.a(var1x, var3, var4, var5)),
         (var1x, var3, var5) -> this.a.execute(() -> this.a(var1x, var3, var5)),
         (var1x, var3, var4) -> {
            Path[] _snowman = new Path[var3];

            for (int _snowmanx = 0; _snowmanx < var3; _snowmanx++) {
               _snowman[_snowmanx] = Paths.get(GLFWDropCallback.getName(var4, _snowmanx));
            }

            this.a.execute(() -> this.a(var1x, Arrays.asList(_snowman)));
         }
      );
   }

   private void b(long var1, double var3, double var5) {
      if (_snowman == djz.C().aD().i()) {
         if (this.i) {
            this.e = _snowman;
            this.f = _snowman;
            this.i = false;
         }

         dmi _snowman = this.a.y;
         if (_snowman != null && this.a.z == null) {
            double _snowmanx = _snowman * (double)this.a.aD().o() / (double)this.a.aD().m();
            double _snowmanxx = _snowman * (double)this.a.aD().p() / (double)this.a.aD().n();
            dot.a(() -> _snowman.e(_snowman, _snowman), "mouseMoved event handler", _snowman.getClass().getCanonicalName());
            if (this.h != -1 && this.k > 0.0) {
               double _snowmanxxx = (_snowman - this.e) * (double)this.a.aD().o() / (double)this.a.aD().m();
               double _snowmanxxxx = (_snowman - this.f) * (double)this.a.aD().p() / (double)this.a.aD().n();
               dot.a(() -> _snowman.a(_snowman, _snowman, this.h, _snowman, _snowman), "mouseDragged event handler", _snowman.getClass().getCanonicalName());
            }
         }

         this.a.au().a("mouse");
         if (this.h() && this.a.ap()) {
            this.n = this.n + (_snowman - this.e);
            this.o = this.o + (_snowman - this.f);
         }

         this.a();
         this.e = _snowman;
         this.f = _snowman;
         this.a.au().c();
      }
   }

   public void a() {
      double _snowman = ddt.b();
      double _snowmanx = _snowman - this.q;
      this.q = _snowman;
      if (this.h() && this.a.ap()) {
         double _snowmanxx = this.a.k.a * 0.6F + 0.2F;
         double _snowmanxxx = _snowmanxx * _snowmanxx * _snowmanxx * 8.0;
         double _snowmanxxxx;
         double _snowmanxxxxx;
         if (this.a.k.aN) {
            double _snowmanxxxxxx = this.l.a(this.n * _snowmanxxx, _snowmanx * _snowmanxxx);
            double _snowmanxxxxxxx = this.m.a(this.o * _snowmanxxx, _snowmanx * _snowmanxxx);
            _snowmanxxxx = _snowmanxxxxxx;
            _snowmanxxxxx = _snowmanxxxxxxx;
         } else {
            this.l.a();
            this.m.a();
            _snowmanxxxx = this.n * _snowmanxxx;
            _snowmanxxxxx = this.o * _snowmanxxx;
         }

         this.n = 0.0;
         this.o = 0.0;
         int _snowmanxxxxxx = 1;
         if (this.a.k.R) {
            _snowmanxxxxxx = -1;
         }

         this.a.ao().a(_snowmanxxxx, _snowmanxxxxx);
         if (this.a.s != null) {
            this.a.s.a(_snowmanxxxx, _snowmanxxxxx * (double)_snowmanxxxxxx);
         }
      } else {
         this.n = 0.0;
         this.o = 0.0;
      }
   }

   public boolean b() {
      return this.b;
   }

   public boolean d() {
      return this.d;
   }

   public double e() {
      return this.e;
   }

   public double f() {
      return this.f;
   }

   public void g() {
      this.i = true;
   }

   public boolean h() {
      return this.r;
   }

   public void i() {
      if (this.a.ap()) {
         if (!this.r) {
            if (!djz.a) {
               djw.a();
            }

            this.r = true;
            this.e = (double)(this.a.aD().m() / 2);
            this.f = (double)(this.a.aD().n() / 2);
            deo.a(this.a.aD().i(), 212995, this.e, this.f);
            this.a.a(null);
            this.a.w = 10000;
            this.i = true;
         }
      }
   }

   public void j() {
      if (this.r) {
         this.r = false;
         this.e = (double)(this.a.aD().m() / 2);
         this.f = (double)(this.a.aD().n() / 2);
         deo.a(this.a.aD().i(), 212993, this.e, this.f);
      }
   }

   public void k() {
      this.i = true;
   }
}
