import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;

public class cfu {
   private final List<cfs> a = Lists.newArrayList();
   private double b = 0.2;
   private double d = 5.0;
   private int e = 15;
   private int f = 5;
   private double g;
   private double h;
   private int i = 29999984;
   private cfu.a j = new cfu.d(6.0E7);
   public static final cfu.c c = new cfu.c(0.0, 0.0, 0.2, 5.0, 5, 15, 6.0E7, 0L, 0.0);

   public cfu() {
   }

   public boolean a(fx var1) {
      return (double)(_snowman.u() + 1) > this.e() && (double)_snowman.u() < this.g() && (double)(_snowman.w() + 1) > this.f() && (double)_snowman.w() < this.h();
   }

   public boolean a(brd var1) {
      return (double)_snowman.f() > this.e() && (double)_snowman.d() < this.g() && (double)_snowman.g() > this.f() && (double)_snowman.e() < this.h();
   }

   public boolean a(dci var1) {
      return _snowman.d > this.e() && _snowman.a < this.g() && _snowman.f > this.f() && _snowman.c < this.h();
   }

   public double a(aqa var1) {
      return this.b(_snowman.cD(), _snowman.cH());
   }

   public ddh c() {
      return this.j.m();
   }

   public double b(double var1, double var3) {
      double _snowman = _snowman - this.f();
      double _snowmanx = this.h() - _snowman;
      double _snowmanxx = _snowman - this.e();
      double _snowmanxxx = this.g() - _snowman;
      double _snowmanxxxx = Math.min(_snowmanxx, _snowmanxxx);
      _snowmanxxxx = Math.min(_snowmanxxxx, _snowman);
      return Math.min(_snowmanxxxx, _snowmanx);
   }

   public cft d() {
      return this.j.i();
   }

   public double e() {
      return this.j.a();
   }

   public double f() {
      return this.j.c();
   }

   public double g() {
      return this.j.b();
   }

   public double h() {
      return this.j.d();
   }

   public double a() {
      return this.g;
   }

   public double b() {
      return this.h;
   }

   public void c(double var1, double var3) {
      this.g = _snowman;
      this.h = _snowman;
      this.j.k();

      for (cfs _snowman : this.l()) {
         _snowman.a(this, _snowman, _snowman);
      }
   }

   public double i() {
      return this.j.e();
   }

   public long j() {
      return this.j.g();
   }

   public double k() {
      return this.j.h();
   }

   public void a(double var1) {
      this.j = new cfu.d(_snowman);

      for (cfs _snowman : this.l()) {
         _snowman.a(this, _snowman);
      }
   }

   public void a(double var1, double var3, long var5) {
      this.j = (cfu.a)(_snowman == _snowman ? new cfu.d(_snowman) : new cfu.b(_snowman, _snowman, _snowman));

      for (cfs _snowman : this.l()) {
         _snowman.a(this, _snowman, _snowman, _snowman);
      }
   }

   protected List<cfs> l() {
      return Lists.newArrayList(this.a);
   }

   public void a(cfs var1) {
      this.a.add(_snowman);
   }

   public void a(int var1) {
      this.i = _snowman;
      this.j.j();
   }

   public int m() {
      return this.i;
   }

   public double n() {
      return this.d;
   }

   public void b(double var1) {
      this.d = _snowman;

      for (cfs _snowman : this.l()) {
         _snowman.c(this, _snowman);
      }
   }

   public double o() {
      return this.b;
   }

   public void c(double var1) {
      this.b = _snowman;

      for (cfs _snowman : this.l()) {
         _snowman.b(this, _snowman);
      }
   }

   public double p() {
      return this.j.f();
   }

   public int q() {
      return this.e;
   }

   public void b(int var1) {
      this.e = _snowman;

      for (cfs _snowman : this.l()) {
         _snowman.a(this, _snowman);
      }
   }

   public int r() {
      return this.f;
   }

   public void c(int var1) {
      this.f = _snowman;

      for (cfs _snowman : this.l()) {
         _snowman.b(this, _snowman);
      }
   }

   public void s() {
      this.j = this.j.l();
   }

   public cfu.c t() {
      return new cfu.c(this);
   }

   public void a(cfu.c var1) {
      this.c(_snowman.a(), _snowman.b());
      this.c(_snowman.c());
      this.b(_snowman.d());
      this.c(_snowman.e());
      this.b(_snowman.f());
      if (_snowman.h() > 0L) {
         this.a(_snowman.g(), _snowman.i(), _snowman.h());
      } else {
         this.a(_snowman.g());
      }
   }

   interface a {
      double a();

      double b();

      double c();

      double d();

      double e();

      double f();

      long g();

      double h();

      cft i();

      void j();

      void k();

      cfu.a l();

      ddh m();
   }

   class b implements cfu.a {
      private final double b;
      private final double c;
      private final long d;
      private final long e;
      private final double f;

      private b(double var2, double var4, long var6) {
         this.b = _snowman;
         this.c = _snowman;
         this.f = (double)_snowman;
         this.e = x.b();
         this.d = this.e + _snowman;
      }

      @Override
      public double a() {
         return Math.max(cfu.this.a() - this.e() / 2.0, (double)(-cfu.this.i));
      }

      @Override
      public double c() {
         return Math.max(cfu.this.b() - this.e() / 2.0, (double)(-cfu.this.i));
      }

      @Override
      public double b() {
         return Math.min(cfu.this.a() + this.e() / 2.0, (double)cfu.this.i);
      }

      @Override
      public double d() {
         return Math.min(cfu.this.b() + this.e() / 2.0, (double)cfu.this.i);
      }

      @Override
      public double e() {
         double _snowman = (double)(x.b() - this.e) / this.f;
         return _snowman < 1.0 ? afm.d(_snowman, this.b, this.c) : this.c;
      }

      @Override
      public double f() {
         return Math.abs(this.b - this.c) / (double)(this.d - this.e);
      }

      @Override
      public long g() {
         return this.d - x.b();
      }

      @Override
      public double h() {
         return this.c;
      }

      @Override
      public cft i() {
         return this.c < this.b ? cft.b : cft.a;
      }

      @Override
      public void k() {
      }

      @Override
      public void j() {
      }

      @Override
      public cfu.a l() {
         return (cfu.a)(this.g() <= 0L ? cfu.this.new d(this.c) : this);
      }

      @Override
      public ddh m() {
         return dde.a(
            dde.a,
            dde.a(Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())),
            dcr.e
         );
      }
   }

   public static class c {
      private final double a;
      private final double b;
      private final double c;
      private final double d;
      private final int e;
      private final int f;
      private final double g;
      private final long h;
      private final double i;

      private c(double var1, double var3, double var5, double var7, int var9, int var10, double var11, long var13, double var15) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
      }

      private c(cfu var1) {
         this.a = _snowman.a();
         this.b = _snowman.b();
         this.c = _snowman.o();
         this.d = _snowman.n();
         this.e = _snowman.r();
         this.f = _snowman.q();
         this.g = _snowman.i();
         this.h = _snowman.j();
         this.i = _snowman.k();
      }

      public double a() {
         return this.a;
      }

      public double b() {
         return this.b;
      }

      public double c() {
         return this.c;
      }

      public double d() {
         return this.d;
      }

      public int e() {
         return this.e;
      }

      public int f() {
         return this.f;
      }

      public double g() {
         return this.g;
      }

      public long h() {
         return this.h;
      }

      public double i() {
         return this.i;
      }

      public static cfu.c a(DynamicLike<?> var0, cfu.c var1) {
         double _snowman = _snowman.get("BorderCenterX").asDouble(_snowman.a);
         double _snowmanx = _snowman.get("BorderCenterZ").asDouble(_snowman.b);
         double _snowmanxx = _snowman.get("BorderSize").asDouble(_snowman.g);
         long _snowmanxxx = _snowman.get("BorderSizeLerpTime").asLong(_snowman.h);
         double _snowmanxxxx = _snowman.get("BorderSizeLerpTarget").asDouble(_snowman.i);
         double _snowmanxxxxx = _snowman.get("BorderSafeZone").asDouble(_snowman.d);
         double _snowmanxxxxxx = _snowman.get("BorderDamagePerBlock").asDouble(_snowman.c);
         int _snowmanxxxxxxx = _snowman.get("BorderWarningBlocks").asInt(_snowman.e);
         int _snowmanxxxxxxxx = _snowman.get("BorderWarningTime").asInt(_snowman.f);
         return new cfu.c(_snowman, _snowmanx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      public void a(md var1) {
         _snowman.a("BorderCenterX", this.a);
         _snowman.a("BorderCenterZ", this.b);
         _snowman.a("BorderSize", this.g);
         _snowman.a("BorderSizeLerpTime", this.h);
         _snowman.a("BorderSafeZone", this.d);
         _snowman.a("BorderDamagePerBlock", this.c);
         _snowman.a("BorderSizeLerpTarget", this.i);
         _snowman.a("BorderWarningBlocks", (double)this.e);
         _snowman.a("BorderWarningTime", (double)this.f);
      }
   }

   class d implements cfu.a {
      private final double b;
      private double c;
      private double d;
      private double e;
      private double f;
      private ddh g;

      public d(double var2) {
         this.b = _snowman;
         this.n();
      }

      @Override
      public double a() {
         return this.c;
      }

      @Override
      public double b() {
         return this.e;
      }

      @Override
      public double c() {
         return this.d;
      }

      @Override
      public double d() {
         return this.f;
      }

      @Override
      public double e() {
         return this.b;
      }

      @Override
      public cft i() {
         return cft.c;
      }

      @Override
      public double f() {
         return 0.0;
      }

      @Override
      public long g() {
         return 0L;
      }

      @Override
      public double h() {
         return this.b;
      }

      private void n() {
         this.c = Math.max(cfu.this.a() - this.b / 2.0, (double)(-cfu.this.i));
         this.d = Math.max(cfu.this.b() - this.b / 2.0, (double)(-cfu.this.i));
         this.e = Math.min(cfu.this.a() + this.b / 2.0, (double)cfu.this.i);
         this.f = Math.min(cfu.this.b() + this.b / 2.0, (double)cfu.this.i);
         this.g = dde.a(
            dde.a,
            dde.a(Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())),
            dcr.e
         );
      }

      @Override
      public void j() {
         this.n();
      }

      @Override
      public void k() {
         this.n();
      }

      @Override
      public cfu.a l() {
         return this;
      }

      @Override
      public ddh m() {
         return this.g;
      }
   }
}
