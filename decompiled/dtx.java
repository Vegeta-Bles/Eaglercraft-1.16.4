import com.google.common.collect.ImmutableList;

public class dtx<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;

   public dtx() {
      this.r = 64;
      this.s = 64;
      float _snowman = 18.0F;
      float _snowmanx = -8.0F;
      this.a = new dwn(this, 22, 0);
      this.a.a(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F);
      this.a.a(0.0F, 22.0F, -5.0F);
      dwn _snowmanxx = new dwn(this, 51, 0);
      _snowmanxx.a(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F);
      _snowmanxx.d = (float) (Math.PI / 3);
      this.a.b(_snowmanxx);
      dwn _snowmanxxx = new dwn(this, 48, 20);
      _snowmanxxx.g = true;
      _snowmanxxx.a(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      _snowmanxxx.a(2.0F, -2.0F, 4.0F);
      _snowmanxxx.d = (float) (Math.PI / 3);
      _snowmanxxx.f = (float) (Math.PI * 2.0 / 3.0);
      this.a.b(_snowmanxxx);
      dwn _snowmanxxxx = new dwn(this, 48, 20);
      _snowmanxxxx.a(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      _snowmanxxxx.a(-2.0F, -2.0F, 4.0F);
      _snowmanxxxx.d = (float) (Math.PI / 3);
      _snowmanxxxx.f = (float) (-Math.PI * 2.0 / 3.0);
      this.a.b(_snowmanxxxx);
      this.b = new dwn(this, 0, 19);
      this.b.a(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F);
      this.b.a(0.0F, -2.5F, 11.0F);
      this.b.d = -0.10471976F;
      this.a.b(this.b);
      this.f = new dwn(this, 19, 20);
      this.f.a(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F);
      this.f.a(0.0F, 0.0F, 9.0F);
      this.f.d = 0.0F;
      this.b.b(this.f);
      dwn _snowmanxxxxx = new dwn(this, 0, 0);
      _snowmanxxxxx.a(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F);
      _snowmanxxxxx.a(0.0F, -4.0F, -3.0F);
      dwn _snowmanxxxxxx = new dwn(this, 0, 13);
      _snowmanxxxxxx.a(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F);
      _snowmanxxxxx.b(_snowmanxxxxxx);
      this.a.b(_snowmanxxxxx);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      if (aqa.c(_snowman.cC()) > 1.0E-7) {
         this.a.d = this.a.d + -0.05F + -0.05F * afm.b(_snowman * 0.3F);
         this.b.d = -0.1F * afm.b(_snowman * 0.3F);
         this.f.d = -0.2F * afm.b(_snowman * 0.3F);
      }
   }
}
