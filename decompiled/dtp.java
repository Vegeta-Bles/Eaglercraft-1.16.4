public class dtp<T extends bab> extends dux<T> {
   private float m;
   private float n;
   private float o;

   public dtp(float var1) {
      super(_snowman);
   }

   public void a(T var1, float var2, float var3, float var4) {
      this.m = _snowman.y(_snowman);
      this.n = _snowman.z(_snowman);
      this.o = _snowman.A(_snowman);
      if (this.m <= 0.0F) {
         this.j.d = 0.0F;
         this.j.f = 0.0F;
         this.f.d = 0.0F;
         this.f.f = 0.0F;
         this.g.d = 0.0F;
         this.g.f = 0.0F;
         this.g.a = -1.2F;
         this.a.d = 0.0F;
         this.b.d = 0.0F;
         this.b.f = 0.0F;
         this.b.a = -1.1F;
         this.b.b = 18.0F;
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
      if (_snowman.eM()) {
         this.k.d = (float) (Math.PI / 4);
         this.k.b += -4.0F;
         this.k.c += 5.0F;
         this.j.b += -3.3F;
         this.j.c++;
         this.h.b += 8.0F;
         this.h.c += -2.0F;
         this.i.b += 2.0F;
         this.i.c += -0.8F;
         this.h.d = 1.7278761F;
         this.i.d = 2.670354F;
         this.f.d = (float) (-Math.PI / 20);
         this.f.b = 16.1F;
         this.f.c = -7.0F;
         this.g.d = (float) (-Math.PI / 20);
         this.g.b = 16.1F;
         this.g.c = -7.0F;
         this.a.d = (float) (-Math.PI / 2);
         this.a.b = 21.0F;
         this.a.c = 1.0F;
         this.b.d = (float) (-Math.PI / 2);
         this.b.b = 21.0F;
         this.b.c = 1.0F;
         this.l = 3;
      }
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.m > 0.0F) {
         this.j.f = duw.a(this.j.f, -1.2707963F, this.m);
         this.j.e = duw.a(this.j.e, 1.2707963F, this.m);
         this.f.d = -1.2707963F;
         this.g.d = -0.47079635F;
         this.g.f = -0.2F;
         this.g.a = -0.2F;
         this.a.d = -0.4F;
         this.b.d = 0.5F;
         this.b.f = -0.5F;
         this.b.a = -0.3F;
         this.b.b = 20.0F;
         this.h.d = duw.a(this.h.d, 0.8F, this.n);
         this.i.d = duw.a(this.i.d, -0.4F, this.n);
      }

      if (this.o > 0.0F) {
         this.j.d = duw.a(this.j.d, -0.58177644F, this.o);
      }
   }
}
