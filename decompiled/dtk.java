import com.google.common.collect.ImmutableList;

public class dtk extends dur<azu> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;

   public dtk() {
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this, 0, 0);
      this.a.a(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      dwn _snowman = new dwn(this, 24, 0);
      _snowman.a(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F);
      this.a.b(_snowman);
      dwn _snowmanx = new dwn(this, 24, 0);
      _snowmanx.g = true;
      _snowmanx.a(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F);
      this.a.b(_snowmanx);
      this.b = new dwn(this, 0, 16);
      this.b.a(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F);
      this.b.a(0, 34).a(-5.0F, 16.0F, 0.0F, 10.0F, 6.0F, 1.0F);
      this.f = new dwn(this, 42, 0);
      this.f.a(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F);
      this.h = new dwn(this, 24, 16);
      this.h.a(-12.0F, 1.0F, 1.5F);
      this.h.a(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F);
      this.g = new dwn(this, 42, 0);
      this.g.g = true;
      this.g.a(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F);
      this.i = new dwn(this, 24, 16);
      this.i.g = true;
      this.i.a(12.0F, 1.0F, 1.5F);
      this.i.a(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F);
      this.b.b(this.f);
      this.b.b(this.g);
      this.f.b(this.h);
      this.g.b(this.i);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b);
   }

   public void a(azu var1, float var2, float var3, float var4, float var5, float var6) {
      if (_snowman.eI()) {
         this.a.d = _snowman * (float) (Math.PI / 180.0);
         this.a.e = (float) Math.PI - _snowman * (float) (Math.PI / 180.0);
         this.a.f = (float) Math.PI;
         this.a.a(0.0F, -2.0F, 0.0F);
         this.f.a(-3.0F, 0.0F, 3.0F);
         this.g.a(3.0F, 0.0F, 3.0F);
         this.b.d = (float) Math.PI;
         this.f.d = (float) (-Math.PI / 20);
         this.f.e = (float) (-Math.PI * 2.0 / 5.0);
         this.h.e = -1.7278761F;
         this.g.d = this.f.d;
         this.g.e = -this.f.e;
         this.i.e = -this.h.e;
      } else {
         this.a.d = _snowman * (float) (Math.PI / 180.0);
         this.a.e = _snowman * (float) (Math.PI / 180.0);
         this.a.f = 0.0F;
         this.a.a(0.0F, 0.0F, 0.0F);
         this.f.a(0.0F, 0.0F, 0.0F);
         this.g.a(0.0F, 0.0F, 0.0F);
         this.b.d = (float) (Math.PI / 4) + afm.b(_snowman * 0.1F) * 0.15F;
         this.b.e = 0.0F;
         this.f.e = afm.b(_snowman * 1.3F) * (float) Math.PI * 0.25F;
         this.g.e = -this.f.e;
         this.h.e = this.f.e * 0.5F;
         this.i.e = -this.f.e * 0.5F;
      }
   }
}
