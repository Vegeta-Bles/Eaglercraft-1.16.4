import com.google.common.collect.ImmutableList;

public class dva<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;

   public dva() {
      this.r = 64;
      this.s = 64;
      this.a = new dwn(this, 0, 8);
      this.a.a(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F);
      this.i = new dwn(this, 3, 20);
      this.i.a(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F);
      this.i.a(0.0F, -2.0F, 1.0F);
      this.a.b(this.i);
      this.j = new dwn(this, 4, 29);
      this.j.a(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F);
      this.j.a(0.0F, 0.5F, 6.0F);
      this.i.b(this.j);
      this.b = new dwn(this, 23, 12);
      this.b.a(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
      this.b.a(2.0F, -2.0F, -8.0F);
      this.f = new dwn(this, 16, 24);
      this.f.a(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
      this.f.a(6.0F, 0.0F, 0.0F);
      this.b.b(this.f);
      this.g = new dwn(this, 23, 12);
      this.g.g = true;
      this.g.a(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
      this.g.a(-3.0F, -2.0F, -8.0F);
      this.h = new dwn(this, 16, 24);
      this.h.g = true;
      this.h.a(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
      this.h.a(-6.0F, 0.0F, 0.0F);
      this.g.b(this.h);
      this.b.f = 0.1F;
      this.f.f = 0.1F;
      this.g.f = -0.1F;
      this.h.f = -0.1F;
      this.a.d = -0.1F;
      dwn _snowman = new dwn(this, 0, 0);
      _snowman.a(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F);
      _snowman.a(0.0F, 1.0F, -7.0F);
      _snowman.d = 0.2F;
      this.a.b(_snowman);
      this.a.b(this.b);
      this.a.b(this.g);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = ((float)(_snowman.Y() * 3) + _snowman) * 0.13F;
      float _snowmanx = 16.0F;
      this.b.f = afm.b(_snowman) * 16.0F * (float) (Math.PI / 180.0);
      this.f.f = afm.b(_snowman) * 16.0F * (float) (Math.PI / 180.0);
      this.g.f = -this.b.f;
      this.h.f = -this.f.f;
      this.i.d = -(5.0F + afm.b(_snowman * 2.0F) * 5.0F) * (float) (Math.PI / 180.0);
      this.j.d = -(5.0F + afm.b(_snowman * 2.0F) * 5.0F) * (float) (Math.PI / 180.0);
   }
}
