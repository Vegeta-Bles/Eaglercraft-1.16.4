import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class ayj {
   protected final aqn a;
   protected final brx b;
   @Nullable
   protected cxd c;
   protected double d;
   protected int e;
   protected int f;
   protected dcn g = dcn.a;
   protected gr h = gr.d;
   protected long i;
   protected long j;
   protected double k;
   protected float l = 0.5F;
   protected boolean m;
   protected long n;
   protected cxc o;
   private fx p;
   private int q;
   private float r = 1.0F;
   private final cxf s;
   private boolean t;

   public ayj(aqn var1, brx var2) {
      this.a = _snowman;
      this.b = _snowman;
      int _snowman = afm.c(_snowman.b(arl.b) * 16.0);
      this.s = this.a(_snowman);
   }

   public void g() {
      this.r = 1.0F;
   }

   public void a(float var1) {
      this.r = _snowman;
   }

   public fx h() {
      return this.p;
   }

   protected abstract cxf a(int var1);

   public void a(double var1) {
      this.d = _snowman;
   }

   public boolean i() {
      return this.m;
   }

   public void j() {
      if (this.b.T() - this.n > 20L) {
         if (this.p != null) {
            this.c = null;
            this.c = this.a(this.p, this.q);
            this.n = this.b.T();
            this.m = false;
         }
      } else {
         this.m = true;
      }
   }

   @Nullable
   public final cxd a(double var1, double var3, double var5, int var7) {
      return this.a(new fx(_snowman, _snowman, _snowman), _snowman);
   }

   @Nullable
   public cxd a(Stream<fx> var1, int var2) {
      return this.a(_snowman.collect(Collectors.toSet()), 8, false, _snowman);
   }

   @Nullable
   public cxd a(Set<fx> var1, int var2) {
      return this.a(_snowman, 8, false, _snowman);
   }

   @Nullable
   public cxd a(fx var1, int var2) {
      return this.a(ImmutableSet.of(_snowman), 8, false, _snowman);
   }

   @Nullable
   public cxd a(aqa var1, int var2) {
      return this.a(ImmutableSet.of(_snowman.cB()), 16, true, _snowman);
   }

   @Nullable
   protected cxd a(Set<fx> var1, int var2, boolean var3, int var4) {
      if (_snowman.isEmpty()) {
         return null;
      } else if (this.a.cE() < 0.0) {
         return null;
      } else if (!this.a()) {
         return null;
      } else if (this.c != null && !this.c.c() && _snowman.contains(this.p)) {
         return this.c;
      } else {
         this.b.Z().a("pathfind");
         float _snowman = (float)this.a.b(arl.b);
         fx _snowmanx = _snowman ? this.a.cB().b() : this.a.cB();
         int _snowmanxx = (int)(_snowman + (float)_snowman);
         bsi _snowmanxxx = new bsi(this.b, _snowmanx.b(-_snowmanxx, -_snowmanxx, -_snowmanxx), _snowmanx.b(_snowmanxx, _snowmanxx, _snowmanxx));
         cxd _snowmanxxxx = this.s.a(_snowmanxxx, this.a, _snowman, _snowman, _snowman, this.r);
         this.b.Z().c();
         if (_snowmanxxxx != null && _snowmanxxxx.m() != null) {
            this.p = _snowmanxxxx.m();
            this.q = _snowman;
            this.f();
         }

         return _snowmanxxxx;
      }
   }

   public boolean a(double var1, double var3, double var5, double var7) {
      return this.a(this.a(_snowman, _snowman, _snowman, 1), _snowman);
   }

   public boolean a(aqa var1, double var2) {
      cxd _snowman = this.a(_snowman, 1);
      return _snowman != null && this.a(_snowman, _snowman);
   }

   public boolean a(@Nullable cxd var1, double var2) {
      if (_snowman == null) {
         this.c = null;
         return false;
      } else {
         if (!_snowman.a(this.c)) {
            this.c = _snowman;
         }

         if (this.m()) {
            return false;
         } else {
            this.D_();
            if (this.c.e() <= 0) {
               return false;
            } else {
               this.d = _snowman;
               dcn _snowman = this.b();
               this.f = this.e;
               this.g = _snowman;
               return true;
            }
         }
      }
   }

   @Nullable
   public cxd k() {
      return this.c;
   }

   public void c() {
      this.e++;
      if (this.m) {
         this.j();
      }

      if (!this.m()) {
         if (this.a()) {
            this.l();
         } else if (this.c != null && !this.c.c()) {
            dcn _snowman = this.b();
            dcn _snowmanx = this.c.a(this.a);
            if (_snowman.c > _snowmanx.c && !this.a.ao() && afm.c(_snowman.b) == afm.c(_snowmanx.b) && afm.c(_snowman.d) == afm.c(_snowmanx.d)) {
               this.c.a();
            }
         }

         rz.a(this.b, this.a, this.c, this.l);
         if (!this.m()) {
            dcn _snowman = this.c.a(this.a);
            fx _snowmanx = new fx(_snowman);
            this.a.u().a(_snowman.b, this.b.d_(_snowmanx.c()).g() ? _snowman.c : cxj.a(this.b, _snowmanx), _snowman.d, this.d);
         }
      }
   }

   protected void l() {
      dcn _snowman = this.b();
      this.l = this.a.cy() > 0.75F ? this.a.cy() / 2.0F : 0.75F - this.a.cy() / 2.0F;
      gr _snowmanx = this.c.g();
      double _snowmanxx = Math.abs(this.a.cD() - ((double)_snowmanx.u() + 0.5));
      double _snowmanxxx = Math.abs(this.a.cE() - (double)_snowmanx.v());
      double _snowmanxxxx = Math.abs(this.a.cH() - ((double)_snowmanx.w() + 0.5));
      boolean _snowmanxxxxx = _snowmanxx < (double)this.l && _snowmanxxxx < (double)this.l && _snowmanxxx < 1.0;
      if (_snowmanxxxxx || this.a.b(this.c.h().l) && this.b(_snowman)) {
         this.c.a();
      }

      this.a(_snowman);
   }

   private boolean b(dcn var1) {
      if (this.c.f() + 1 >= this.c.e()) {
         return false;
      } else {
         dcn _snowman = dcn.c(this.c.g());
         if (!_snowman.a(_snowman, 2.0)) {
            return false;
         } else {
            dcn _snowmanx = dcn.c(this.c.d(this.c.f() + 1));
            dcn _snowmanxx = _snowmanx.d(_snowman);
            dcn _snowmanxxx = _snowman.d(_snowman);
            return _snowmanxx.b(_snowmanxxx) > 0.0;
         }
      }
   }

   protected void a(dcn var1) {
      if (this.e - this.f > 100) {
         if (_snowman.g(this.g) < 2.25) {
            this.t = true;
            this.o();
         } else {
            this.t = false;
         }

         this.f = this.e;
         this.g = _snowman;
      }

      if (this.c != null && !this.c.c()) {
         gr _snowman = this.c.g();
         if (_snowman.equals(this.h)) {
            this.i = this.i + (x.b() - this.j);
         } else {
            this.h = _snowman;
            double _snowmanx = _snowman.f(dcn.c(this.h));
            this.k = this.a.dN() > 0.0F ? _snowmanx / (double)this.a.dN() * 1000.0 : 0.0;
         }

         if (this.k > 0.0 && (double)this.i > this.k * 3.0) {
            this.e();
         }

         this.j = x.b();
      }
   }

   private void e() {
      this.f();
      this.o();
   }

   private void f() {
      this.h = gr.d;
      this.i = 0L;
      this.k = 0.0;
      this.t = false;
   }

   public boolean m() {
      return this.c == null || this.c.c();
   }

   public boolean n() {
      return !this.m();
   }

   public void o() {
      this.c = null;
   }

   protected abstract dcn b();

   protected abstract boolean a();

   protected boolean p() {
      return this.a.aH() || this.a.aQ();
   }

   protected void D_() {
      if (this.c != null) {
         for (int _snowman = 0; _snowman < this.c.e(); _snowman++) {
            cxb _snowmanx = this.c.a(_snowman);
            cxb _snowmanxx = _snowman + 1 < this.c.e() ? this.c.a(_snowman + 1) : null;
            ceh _snowmanxxx = this.b.d_(new fx(_snowmanx.a, _snowmanx.b, _snowmanx.c));
            if (_snowmanxxx.a(bup.eb)) {
               this.c.a(_snowman, _snowmanx.a(_snowmanx.a, _snowmanx.b + 1, _snowmanx.c));
               if (_snowmanxx != null && _snowmanx.b >= _snowmanxx.b) {
                  this.c.a(_snowman + 1, _snowmanx.a(_snowmanxx.a, _snowmanx.b + 1, _snowmanxx.c));
               }
            }
         }
      }
   }

   protected abstract boolean a(dcn var1, dcn var2, int var3, int var4, int var5);

   public boolean a(fx var1) {
      fx _snowman = _snowman.c();
      return this.b.d_(_snowman).i(this.b, _snowman);
   }

   public cxc q() {
      return this.o;
   }

   public void d(boolean var1) {
      this.o.c(_snowman);
   }

   public boolean r() {
      return this.o.e();
   }

   public void b(fx var1) {
      if (this.c != null && !this.c.c() && this.c.e() != 0) {
         cxb _snowman = this.c.d();
         dcn _snowmanx = new dcn(((double)_snowman.a + this.a.cD()) / 2.0, ((double)_snowman.b + this.a.cE()) / 2.0, ((double)_snowman.c + this.a.cH()) / 2.0);
         if (_snowman.a(_snowmanx, (double)(this.c.e() - this.c.f()))) {
            this.j();
         }
      }
   }

   public boolean t() {
      return this.t;
   }
}
