import com.google.common.collect.ImmutableList;

public class dtl<T extends baa> extends dtf<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;
   private final dwn m;
   private float n;

   public dtl() {
      super(false, 24.0F, 0.0F);
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this);
      this.a.a(0.0F, 19.0F, 0.0F);
      this.b = new dwn(this, 0, 0);
      this.b.a(0.0F, 0.0F, 0.0F);
      this.a.b(this.b);
      this.b.a(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F, 0.0F);
      this.k = new dwn(this, 26, 7);
      this.k.a(0.0F, -1.0F, 5.0F, 0.0F, 1.0F, 2.0F, 0.0F);
      this.b.b(this.k);
      this.l = new dwn(this, 2, 0);
      this.l.a(0.0F, -2.0F, -5.0F);
      this.l.a(1.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, 0.0F);
      this.m = new dwn(this, 2, 3);
      this.m.a(0.0F, -2.0F, -5.0F);
      this.m.a(-2.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, 0.0F);
      this.b.b(this.l);
      this.b.b(this.m);
      this.f = new dwn(this, 0, 18);
      this.f.a(-1.5F, -4.0F, -3.0F);
      this.f.d = 0.0F;
      this.f.e = -0.2618F;
      this.f.f = 0.0F;
      this.a.b(this.f);
      this.f.a(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, 0.001F);
      this.g = new dwn(this, 0, 18);
      this.g.a(1.5F, -4.0F, -3.0F);
      this.g.d = 0.0F;
      this.g.e = 0.2618F;
      this.g.f = 0.0F;
      this.g.g = true;
      this.a.b(this.g);
      this.g.a(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, 0.001F);
      this.h = new dwn(this);
      this.h.a(1.5F, 3.0F, -2.0F);
      this.a.b(this.h);
      this.h.a("frontLegBox", -5.0F, 0.0F, 0.0F, 7, 2, 0, 0.0F, 26, 1);
      this.i = new dwn(this);
      this.i.a(1.5F, 3.0F, 0.0F);
      this.a.b(this.i);
      this.i.a("midLegBox", -5.0F, 0.0F, 0.0F, 7, 2, 0, 0.0F, 26, 3);
      this.j = new dwn(this);
      this.j.a(1.5F, 3.0F, 2.0F);
      this.a.b(this.j);
      this.j.a("backLegBox", -5.0F, 0.0F, 0.0F, 7, 2, 0, 0.0F, 26, 5);
   }

   public void a(T var1, float var2, float var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.n = _snowman.y(_snowman);
      this.k.h = !_snowman.eY();
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.f.d = 0.0F;
      this.l.d = 0.0F;
      this.m.d = 0.0F;
      this.a.d = 0.0F;
      this.a.b = 19.0F;
      boolean _snowman = _snowman.ao() && _snowman.cC().g() < 1.0E-7;
      if (_snowman) {
         this.f.e = -0.2618F;
         this.f.f = 0.0F;
         this.g.d = 0.0F;
         this.g.e = 0.2618F;
         this.g.f = 0.0F;
         this.h.d = 0.0F;
         this.i.d = 0.0F;
         this.j.d = 0.0F;
      } else {
         float _snowmanx = _snowman * 2.1F;
         this.f.e = 0.0F;
         this.f.f = afm.b(_snowmanx) * (float) Math.PI * 0.15F;
         this.g.d = this.f.d;
         this.g.e = this.f.e;
         this.g.f = -this.f.f;
         this.h.d = (float) (Math.PI / 4);
         this.i.d = (float) (Math.PI / 4);
         this.j.d = (float) (Math.PI / 4);
         this.a.d = 0.0F;
         this.a.e = 0.0F;
         this.a.f = 0.0F;
      }

      if (!_snowman.H_()) {
         this.a.d = 0.0F;
         this.a.e = 0.0F;
         this.a.f = 0.0F;
         if (!_snowman) {
            float _snowmanx = afm.b(_snowman * 0.18F);
            this.a.d = 0.1F + _snowmanx * (float) Math.PI * 0.025F;
            this.l.d = _snowmanx * (float) Math.PI * 0.03F;
            this.m.d = _snowmanx * (float) Math.PI * 0.03F;
            this.h.d = -_snowmanx * (float) Math.PI * 0.1F + (float) (Math.PI / 8);
            this.j.d = -_snowmanx * (float) Math.PI * 0.05F + (float) (Math.PI / 4);
            this.a.b = 19.0F - afm.b(_snowman * 0.18F) * 0.9F;
         }
      }

      if (this.n > 0.0F) {
         this.a.d = duw.a(this.a.d, 3.0915928F, this.n);
      }
   }

   @Override
   protected Iterable<dwn> a() {
      return ImmutableList.of();
   }

   @Override
   protected Iterable<dwn> b() {
      return ImmutableList.of(this.a);
   }
}
