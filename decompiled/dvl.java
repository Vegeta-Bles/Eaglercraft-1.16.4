import com.google.common.collect.ImmutableList;

public class dvl<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;

   public dvl() {
      this.r = 32;
      this.s = 32;
      int _snowman = 20;
      this.a = new dwn(this, 0, 0);
      this.a.a(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
      this.a.a(0.0F, 20.0F, 0.0F);
      this.b = new dwn(this, 0, 13);
      this.b.a(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
      this.b.a(0.0F, 20.0F, 8.0F);
      this.f = new dwn(this, 22, 0);
      this.f.a(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F);
      this.f.a(0.0F, 20.0F, 0.0F);
      dwn _snowmanx = new dwn(this, 20, 10);
      _snowmanx.a(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F);
      _snowmanx.a(0.0F, 0.0F, 8.0F);
      this.b.b(_snowmanx);
      dwn _snowmanxx = new dwn(this, 2, 1);
      _snowmanxx.a(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F);
      _snowmanxx.a(0.0F, -4.5F, 5.0F);
      this.a.b(_snowmanxx);
      dwn _snowmanxxx = new dwn(this, 0, 2);
      _snowmanxxx.a(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F);
      _snowmanxxx.a(0.0F, -4.5F, -1.0F);
      this.b.b(_snowmanxxx);
      this.g = new dwn(this, -4, 0);
      this.g.a(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.g.a(-1.5F, 21.5F, 0.0F);
      this.g.f = (float) (-Math.PI / 4);
      this.h = new dwn(this, 0, 0);
      this.h.a(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.h.a(1.5F, 21.5F, 0.0F);
      this.h.f = (float) (Math.PI / 4);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g, this.h);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = 1.0F;
      float _snowmanx = 1.0F;
      if (!_snowman.aE()) {
         _snowman = 1.3F;
         _snowmanx = 1.7F;
      }

      this.b.e = -_snowman * 0.25F * afm.a(_snowmanx * 0.6F * _snowman);
   }
}
