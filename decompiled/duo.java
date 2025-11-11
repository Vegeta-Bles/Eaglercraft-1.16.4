import com.google.common.collect.ImmutableList;

public class duo<T extends bai> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;

   public duo() {
      int _snowman = 128;
      int _snowmanx = 128;
      this.a = new dwn(this).b(128, 128);
      this.a.a(0.0F, -7.0F, -2.0F);
      this.a.a(0, 0).a(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F);
      this.a.a(24, 0).a(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, 0.0F);
      this.b = new dwn(this).b(128, 128);
      this.b.a(0.0F, -7.0F, 0.0F);
      this.b.a(0, 40).a(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F);
      this.b.a(0, 70).a(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F);
      this.f = new dwn(this).b(128, 128);
      this.f.a(0.0F, -7.0F, 0.0F);
      this.f.a(60, 21).a(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
      this.g = new dwn(this).b(128, 128);
      this.g.a(0.0F, -7.0F, 0.0F);
      this.g.a(60, 58).a(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
      this.h = new dwn(this, 0, 22).b(128, 128);
      this.h.a(-4.0F, 11.0F, 0.0F);
      this.h.a(37, 0).a(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
      this.i = new dwn(this, 0, 22).b(128, 128);
      this.i.g = true;
      this.i.a(60, 0).a(5.0F, 11.0F, 0.0F);
      this.i.a(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.h, this.i, this.f, this.g);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
      this.h.d = -1.5F * afm.e(_snowman, 13.0F) * _snowman;
      this.i.d = 1.5F * afm.e(_snowman, 13.0F) * _snowman;
      this.h.e = 0.0F;
      this.i.e = 0.0F;
   }

   public void a(T var1, float var2, float var3, float var4) {
      int _snowman = _snowman.eL();
      if (_snowman > 0) {
         this.f.d = -2.0F + 1.5F * afm.e((float)_snowman - _snowman, 10.0F);
         this.g.d = -2.0F + 1.5F * afm.e((float)_snowman - _snowman, 10.0F);
      } else {
         int _snowmanx = _snowman.eM();
         if (_snowmanx > 0) {
            this.f.d = -0.8F + 0.025F * afm.e((float)_snowmanx, 70.0F);
            this.g.d = 0.0F;
         } else {
            this.f.d = (-0.2F + 1.5F * afm.e(_snowman, 13.0F)) * _snowman;
            this.g.d = (-0.2F - 1.5F * afm.e(_snowman, 13.0F)) * _snowman;
         }
      }
   }

   public dwn b() {
      return this.f;
   }
}
