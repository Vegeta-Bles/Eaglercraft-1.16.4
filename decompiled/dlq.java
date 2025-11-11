import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class dlq extends dlh implements dmf, dmi {
   private final dku a;
   private String b = "";
   private int c = 32;
   private int d;
   private boolean e = true;
   private boolean s = true;
   private boolean t = true;
   private boolean u;
   private int v;
   private int w;
   private int x;
   private int y = 14737632;
   private int z = 7368816;
   private String A;
   private Consumer<String> B;
   private Predicate<String> C = Objects::nonNull;
   private BiFunction<String, Integer, afa> D = (var0, var1x) -> afa.a(var0, ob.a);

   public dlq(dku var1, int var2, int var3, int var4, int var5, nr var6) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, null, _snowman);
   }

   public dlq(dku var1, int var2, int var3, int var4, int var5, @Nullable dlq var6, nr var7) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      if (_snowman != null) {
         this.a(_snowman.b());
      }
   }

   public void a(Consumer<String> var1) {
      this.B = _snowman;
   }

   public void a(BiFunction<String, Integer, afa> var1) {
      this.D = _snowman;
   }

   public void a() {
      this.d++;
   }

   @Override
   protected nx c() {
      nr _snowman = this.i();
      return new of("gui.narrate.editBox", _snowman, this.b);
   }

   public void a(String var1) {
      if (this.C.test(_snowman)) {
         if (_snowman.length() > this.c) {
            this.b = _snowman.substring(0, this.c);
         } else {
            this.b = _snowman;
         }

         this.l();
         this.n(this.w);
         this.d(_snowman);
      }
   }

   public String b() {
      return this.b;
   }

   public String d() {
      int _snowman = this.w < this.x ? this.w : this.x;
      int _snowmanx = this.w < this.x ? this.x : this.w;
      return this.b.substring(_snowman, _snowmanx);
   }

   public void a(Predicate<String> var1) {
      this.C = _snowman;
   }

   public void b(String var1) {
      int _snowman = this.w < this.x ? this.w : this.x;
      int _snowmanx = this.w < this.x ? this.x : this.w;
      int _snowmanxx = this.c - this.b.length() - (_snowman - _snowmanx);
      String _snowmanxxx = w.a(_snowman);
      int _snowmanxxxx = _snowmanxxx.length();
      if (_snowmanxx < _snowmanxxxx) {
         _snowmanxxx = _snowmanxxx.substring(0, _snowmanxx);
         _snowmanxxxx = _snowmanxx;
      }

      String _snowmanxxxxx = new StringBuilder(this.b).replace(_snowman, _snowmanx, _snowmanxxx).toString();
      if (this.C.test(_snowmanxxxxx)) {
         this.b = _snowmanxxxxx;
         this.j(_snowman + _snowmanxxxx);
         this.n(this.w);
         this.d(this.b);
      }
   }

   private void d(String var1) {
      if (this.B != null) {
         this.B.accept(_snowman);
      }

      this.r = x.b() + 500L;
   }

   private void q(int var1) {
      if (dot.x()) {
         this.e(_snowman);
      } else {
         this.f(_snowman);
      }
   }

   public void e(int var1) {
      if (!this.b.isEmpty()) {
         if (this.x != this.w) {
            this.b("");
         } else {
            this.f(this.g(_snowman) - this.w);
         }
      }
   }

   public void f(int var1) {
      if (!this.b.isEmpty()) {
         if (this.x != this.w) {
            this.b("");
         } else {
            int _snowman = this.r(_snowman);
            int _snowmanx = Math.min(_snowman, this.w);
            int _snowmanxx = Math.max(_snowman, this.w);
            if (_snowmanx != _snowmanxx) {
               String _snowmanxxx = new StringBuilder(this.b).delete(_snowmanx, _snowmanxx).toString();
               if (this.C.test(_snowmanxxx)) {
                  this.b = _snowmanxxx;
                  this.i(_snowmanx);
               }
            }
         }
      }
   }

   public int g(int var1) {
      return this.a(_snowman, this.n());
   }

   private int a(int var1, int var2) {
      return this.a(_snowman, _snowman, true);
   }

   private int a(int var1, int var2, boolean var3) {
      int _snowman = _snowman;
      boolean _snowmanx = _snowman < 0;
      int _snowmanxx = Math.abs(_snowman);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         if (!_snowmanx) {
            int _snowmanxxxx = this.b.length();
            _snowman = this.b.indexOf(32, _snowman);
            if (_snowman == -1) {
               _snowman = _snowmanxxxx;
            } else {
               while (_snowman && _snowman < _snowmanxxxx && this.b.charAt(_snowman) == ' ') {
                  _snowman++;
               }
            }
         } else {
            while (_snowman && _snowman > 0 && this.b.charAt(_snowman - 1) == ' ') {
               _snowman--;
            }

            while (_snowman > 0 && this.b.charAt(_snowman - 1) != ' ') {
               _snowman--;
            }
         }
      }

      return _snowman;
   }

   public void h(int var1) {
      this.i(this.r(_snowman));
   }

   private int r(int var1) {
      return x.a(this.b, this.w, _snowman);
   }

   public void i(int var1) {
      this.j(_snowman);
      if (!this.u) {
         this.n(this.w);
      }

      this.d(this.b);
   }

   public void j(int var1) {
      this.w = afm.a(_snowman, 0, this.b.length());
   }

   public void k() {
      this.i(0);
   }

   public void l() {
      this.i(this.b.length());
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (!this.m()) {
         return false;
      } else {
         this.u = dot.y();
         if (dot.i(_snowman)) {
            this.l();
            this.n(0);
            return true;
         } else if (dot.h(_snowman)) {
            djz.C().m.a(this.d());
            return true;
         } else if (dot.g(_snowman)) {
            if (this.t) {
               this.b(djz.C().m.a());
            }

            return true;
         } else if (dot.f(_snowman)) {
            djz.C().m.a(this.d());
            if (this.t) {
               this.b("");
            }

            return true;
         } else {
            switch (_snowman) {
               case 259:
                  if (this.t) {
                     this.u = false;
                     this.q(-1);
                     this.u = dot.y();
                  }

                  return true;
               case 260:
               case 264:
               case 265:
               case 266:
               case 267:
               default:
                  return false;
               case 261:
                  if (this.t) {
                     this.u = false;
                     this.q(1);
                     this.u = dot.y();
                  }

                  return true;
               case 262:
                  if (dot.x()) {
                     this.i(this.g(1));
                  } else {
                     this.h(1);
                  }

                  return true;
               case 263:
                  if (dot.x()) {
                     this.i(this.g(-1));
                  } else {
                     this.h(-1);
                  }

                  return true;
               case 268:
                  this.k();
                  return true;
               case 269:
                  this.l();
                  return true;
            }
         }
      }
   }

   public boolean m() {
      return this.p() && this.j() && this.s();
   }

   @Override
   public boolean a(char var1, int var2) {
      if (!this.m()) {
         return false;
      } else if (w.a(_snowman)) {
         if (this.t) {
            this.b(Character.toString(_snowman));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (!this.p()) {
         return false;
      } else {
         boolean _snowman = _snowman >= (double)this.l && _snowman < (double)(this.l + this.j) && _snowman >= (double)this.m && _snowman < (double)(this.m + this.k);
         if (this.s) {
            this.e(_snowman);
         }

         if (this.j() && _snowman && _snowman == 0) {
            int _snowmanx = afm.c(_snowman) - this.l;
            if (this.e) {
               _snowmanx -= 4;
            }

            String _snowmanxx = this.a.a(this.b.substring(this.v), this.o());
            this.i(this.a.a(_snowmanxx, _snowmanx).length() + this.v);
            return true;
         } else {
            return false;
         }
      }
   }

   public void e(boolean var1) {
      super.d(_snowman);
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      if (this.p()) {
         if (this.r()) {
            int _snowman = this.j() ? -1 : -6250336;
            a(_snowman, this.l - 1, this.m - 1, this.l + this.j + 1, this.m + this.k + 1, _snowman);
            a(_snowman, this.l, this.m, this.l + this.j, this.m + this.k, -16777216);
         }

         int _snowman = this.t ? this.y : this.z;
         int _snowmanx = this.w - this.v;
         int _snowmanxx = this.x - this.v;
         String _snowmanxxx = this.a.a(this.b.substring(this.v), this.o());
         boolean _snowmanxxxx = _snowmanx >= 0 && _snowmanx <= _snowmanxxx.length();
         boolean _snowmanxxxxx = this.j() && this.d / 6 % 2 == 0 && _snowmanxxxx;
         int _snowmanxxxxxx = this.e ? this.l + 4 : this.l;
         int _snowmanxxxxxxx = this.e ? this.m + (this.k - 8) / 2 : this.m;
         int _snowmanxxxxxxxx = _snowmanxxxxxx;
         if (_snowmanxx > _snowmanxxx.length()) {
            _snowmanxx = _snowmanxxx.length();
         }

         if (!_snowmanxxx.isEmpty()) {
            String _snowmanxxxxxxxxx = _snowmanxxxx ? _snowmanxxx.substring(0, _snowmanx) : _snowmanxxx;
            _snowmanxxxxxxxx = this.a.a(_snowman, this.D.apply(_snowmanxxxxxxxxx, this.v), (float)_snowmanxxxxxx, (float)_snowmanxxxxxxx, _snowman);
         }

         boolean _snowmanxxxxxxxxx = this.w < this.b.length() || this.b.length() >= this.q();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;
         if (!_snowmanxxxx) {
            _snowmanxxxxxxxxxx = _snowmanx > 0 ? _snowmanxxxxxx + this.j : _snowmanxxxxxx;
         } else if (_snowmanxxxxxxxxx) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - 1;
            _snowmanxxxxxxxx--;
         }

         if (!_snowmanxxx.isEmpty() && _snowmanxxxx && _snowmanx < _snowmanxxx.length()) {
            this.a.a(_snowman, this.D.apply(_snowmanxxx.substring(_snowmanx), this.w), (float)_snowmanxxxxxxxx, (float)_snowmanxxxxxxx, _snowman);
         }

         if (!_snowmanxxxxxxxxx && this.A != null) {
            this.a.a(_snowman, this.A, (float)(_snowmanxxxxxxxxxx - 1), (float)_snowmanxxxxxxx, -8355712);
         }

         if (_snowmanxxxxx) {
            if (_snowmanxxxxxxxxx) {
               dkw.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxx - 1, _snowmanxxxxxxxxxx + 1, _snowmanxxxxxxx + 1 + 9, -3092272);
            } else {
               this.a.a(_snowman, "_", (float)_snowmanxxxxxxxxxx, (float)_snowmanxxxxxxx, _snowman);
            }
         }

         if (_snowmanxx != _snowmanx) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxx + this.a.b(_snowmanxxx.substring(0, _snowmanxx));
            this.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxx - 1, _snowmanxxxxxxxxxxx - 1, _snowmanxxxxxxx + 1 + 9);
         }
      }
   }

   private void a(int var1, int var2, int var3, int var4) {
      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      if (_snowman < _snowman) {
         int _snowman = _snowman;
         _snowman = _snowman;
         _snowman = _snowman;
      }

      if (_snowman > this.l + this.j) {
         _snowman = this.l + this.j;
      }

      if (_snowman > this.l + this.j) {
         _snowman = this.l + this.j;
      }

      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(dem.o.n);
      _snowmanx.a(7, dfk.k);
      _snowmanx.a((double)_snowman, (double)_snowman, 0.0).d();
      _snowmanx.a((double)_snowman, (double)_snowman, 0.0).d();
      _snowmanx.a((double)_snowman, (double)_snowman, 0.0).d();
      _snowmanx.a((double)_snowman, (double)_snowman, 0.0).d();
      _snowman.b();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   public void k(int var1) {
      this.c = _snowman;
      if (this.b.length() > _snowman) {
         this.b = this.b.substring(0, _snowman);
         this.d(this.b);
      }
   }

   private int q() {
      return this.c;
   }

   public int n() {
      return this.w;
   }

   private boolean r() {
      return this.e;
   }

   public void f(boolean var1) {
      this.e = _snowman;
   }

   public void l(int var1) {
      this.y = _snowman;
   }

   public void m(int var1) {
      this.z = _snowman;
   }

   @Override
   public boolean c_(boolean var1) {
      return this.p && this.t ? super.c_(_snowman) : false;
   }

   @Override
   public boolean b(double var1, double var3) {
      return this.p && _snowman >= (double)this.l && _snowman < (double)(this.l + this.j) && _snowman >= (double)this.m && _snowman < (double)(this.m + this.k);
   }

   @Override
   protected void c(boolean var1) {
      if (_snowman) {
         this.d = 0;
      }
   }

   private boolean s() {
      return this.t;
   }

   public void g(boolean var1) {
      this.t = _snowman;
   }

   public int o() {
      return this.r() ? this.j - 8 : this.j;
   }

   public void n(int var1) {
      int _snowman = this.b.length();
      this.x = afm.a(_snowman, 0, _snowman);
      if (this.a != null) {
         if (this.v > _snowman) {
            this.v = _snowman;
         }

         int _snowmanx = this.o();
         String _snowmanxx = this.a.a(this.b.substring(this.v), _snowmanx);
         int _snowmanxxx = _snowmanxx.length() + this.v;
         if (this.x == this.v) {
            this.v = this.v - this.a.a(this.b, _snowmanx, true).length();
         }

         if (this.x > _snowmanxxx) {
            this.v = this.v + (this.x - _snowmanxxx);
         } else if (this.x <= this.v) {
            this.v = this.v - (this.v - this.x);
         }

         this.v = afm.a(this.v, 0, _snowman);
      }
   }

   public void h(boolean var1) {
      this.s = _snowman;
   }

   public boolean p() {
      return this.p;
   }

   public void i(boolean var1) {
      this.p = _snowman;
   }

   public void c(@Nullable String var1) {
      this.A = _snowman;
   }

   public int o(int var1) {
      return _snowman > this.b.length() ? this.l : this.l + this.a.b(this.b.substring(0, _snowman));
   }

   public void p(int var1) {
      this.l = _snowman;
   }
}
