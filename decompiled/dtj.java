import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class dtj extends dti {
   private final dwn a;
   private final dwn b;
   private final dwn t;
   private final dwn u;

   public dtj() {
      this(0.0F);
   }

   public dtj(float var1) {
      super(_snowman, 64, 64);
      this.f = new dwn(this, 0, 0);
      this.f.a(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, _snowman);
      this.f.a(0.0F, 0.0F, 0.0F);
      this.h = new dwn(this, 0, 26);
      this.h.a(-6.0F, 0.0F, -1.5F, 12.0F, 3.0F, 3.0F, _snowman);
      this.h.a(0.0F, 0.0F, 0.0F);
      this.i = new dwn(this, 24, 0);
      this.i.a(-2.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
      this.i.a(-5.0F, 2.0F, 0.0F);
      this.j = new dwn(this, 32, 16);
      this.j.g = true;
      this.j.a(0.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, _snowman);
      this.j.a(5.0F, 2.0F, 0.0F);
      this.k = new dwn(this, 8, 0);
      this.k.a(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, _snowman);
      this.k.a(-1.9F, 12.0F, 0.0F);
      this.l = new dwn(this, 40, 16);
      this.l.g = true;
      this.l.a(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, _snowman);
      this.l.a(1.9F, 12.0F, 0.0F);
      this.a = new dwn(this, 16, 0);
      this.a.a(-3.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F, _snowman);
      this.a.a(0.0F, 0.0F, 0.0F);
      this.a.h = true;
      this.b = new dwn(this, 48, 16);
      this.b.a(1.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F, _snowman);
      this.b.a(0.0F, 0.0F, 0.0F);
      this.t = new dwn(this, 0, 48);
      this.t.a(-4.0F, 10.0F, -1.0F, 8.0F, 2.0F, 2.0F, _snowman);
      this.t.a(0.0F, 0.0F, 0.0F);
      this.u = new dwn(this, 0, 32);
      this.u.a(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F, _snowman);
      this.u.a(0.0F, 12.0F, 0.0F);
      this.g.h = false;
   }

   public void a(bcn var1, float var2, float var3, float var4) {
      this.u.d = 0.0F;
      this.u.e = (float) (Math.PI / 180.0) * -afm.h(_snowman, _snowman.r, _snowman.p);
      this.u.f = 0.0F;
   }

   @Override
   public void a(bcn var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.j.h = _snowman.o();
      this.i.h = _snowman.o();
      this.u.h = !_snowman.p();
      this.l.a(1.9F, 12.0F, 0.0F);
      this.k.a(-1.9F, 12.0F, 0.0F);
      this.a.d = (float) (Math.PI / 180.0) * _snowman.t().b();
      this.a.e = (float) (Math.PI / 180.0) * _snowman.t().c();
      this.a.f = (float) (Math.PI / 180.0) * _snowman.t().d();
      this.b.d = (float) (Math.PI / 180.0) * _snowman.t().b();
      this.b.e = (float) (Math.PI / 180.0) * _snowman.t().c();
      this.b.f = (float) (Math.PI / 180.0) * _snowman.t().d();
      this.t.d = (float) (Math.PI / 180.0) * _snowman.t().b();
      this.t.e = (float) (Math.PI / 180.0) * _snowman.t().c();
      this.t.f = (float) (Math.PI / 180.0) * _snowman.t().d();
   }

   @Override
   protected Iterable<dwn> b() {
      return Iterables.concat(super.b(), ImmutableList.of(this.a, this.b, this.t, this.u));
   }

   @Override
   public void a(aqi var1, dfm var2) {
      dwn _snowman = this.a(_snowman);
      boolean _snowmanx = _snowman.h;
      _snowman.h = true;
      super.a(_snowman, _snowman);
      _snowman.h = _snowmanx;
   }
}
