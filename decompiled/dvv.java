import com.google.common.collect.ImmutableList;

public class dvv<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;

   public dvv() {
      float _snowman = 4.0F;
      float _snowmanx = 0.0F;
      this.f = new dwn(this, 0, 0).b(64, 64);
      this.f.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
      this.f.a(0.0F, 4.0F, 0.0F);
      this.g = new dwn(this, 32, 0).b(64, 64);
      this.g.a(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
      this.g.a(0.0F, 6.0F, 0.0F);
      this.h = new dwn(this, 32, 0).b(64, 64);
      this.h.a(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
      this.h.a(0.0F, 6.0F, 0.0F);
      this.a = new dwn(this, 0, 16).b(64, 64);
      this.a.a(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
      this.a.a(0.0F, 13.0F, 0.0F);
      this.b = new dwn(this, 0, 36).b(64, 64);
      this.b.a(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, -0.5F);
      this.b.a(0.0F, 24.0F, 0.0F);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.f.e = _snowman * (float) (Math.PI / 180.0);
      this.f.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0) * 0.25F;
      float _snowman = afm.a(this.a.e);
      float _snowmanx = afm.b(this.a.e);
      this.g.f = 1.0F;
      this.h.f = -1.0F;
      this.g.e = 0.0F + this.a.e;
      this.h.e = (float) Math.PI + this.a.e;
      this.g.a = _snowmanx * 5.0F;
      this.g.c = -_snowman * 5.0F;
      this.h.a = -_snowmanx * 5.0F;
      this.h.c = _snowman * 5.0F;
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h);
   }

   public dwn b() {
      return this.f;
   }
}
