public class dua<T extends aqm> extends dum<T> {
   public boolean a;
   public boolean b;

   public dua(float var1) {
      super(0.0F, -14.0F, 64, 32);
      float _snowman = -14.0F;
      this.g = new dwn(this, 0, 16);
      this.g.a(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman - 0.5F);
      this.g.a(0.0F, -14.0F, 0.0F);
      this.h = new dwn(this, 32, 16);
      this.h.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman);
      this.h.a(0.0F, -14.0F, 0.0F);
      this.i = new dwn(this, 56, 0);
      this.i.a(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.i.a(-3.0F, -12.0F, 0.0F);
      this.j = new dwn(this, 56, 0);
      this.j.g = true;
      this.j.a(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.j.a(5.0F, -12.0F, 0.0F);
      this.k = new dwn(this, 56, 0);
      this.k.a(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.k.a(-2.0F, -2.0F, 0.0F);
      this.l = new dwn(this, 56, 0);
      this.l.g = true;
      this.l.a(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.l.a(2.0F, -2.0F, 0.0F);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.f.h = true;
      float _snowman = -14.0F;
      this.h.d = 0.0F;
      this.h.b = -14.0F;
      this.h.c = -0.0F;
      this.k.d -= 0.0F;
      this.l.d -= 0.0F;
      this.i.d = (float)((double)this.i.d * 0.5);
      this.j.d = (float)((double)this.j.d * 0.5);
      this.k.d = (float)((double)this.k.d * 0.5);
      this.l.d = (float)((double)this.l.d * 0.5);
      float _snowmanx = 0.4F;
      if (this.i.d > 0.4F) {
         this.i.d = 0.4F;
      }

      if (this.j.d > 0.4F) {
         this.j.d = 0.4F;
      }

      if (this.i.d < -0.4F) {
         this.i.d = -0.4F;
      }

      if (this.j.d < -0.4F) {
         this.j.d = -0.4F;
      }

      if (this.k.d > 0.4F) {
         this.k.d = 0.4F;
      }

      if (this.l.d > 0.4F) {
         this.l.d = 0.4F;
      }

      if (this.k.d < -0.4F) {
         this.k.d = -0.4F;
      }

      if (this.l.d < -0.4F) {
         this.l.d = -0.4F;
      }

      if (this.a) {
         this.i.d = -0.5F;
         this.j.d = -0.5F;
         this.i.f = 0.05F;
         this.j.f = -0.05F;
      }

      this.i.c = 0.0F;
      this.j.c = 0.0F;
      this.k.c = 0.0F;
      this.l.c = 0.0F;
      this.k.b = -5.0F;
      this.l.b = -5.0F;
      this.f.c = -0.0F;
      this.f.b = -13.0F;
      this.g.a = this.f.a;
      this.g.b = this.f.b;
      this.g.c = this.f.c;
      this.g.d = this.f.d;
      this.g.e = this.f.e;
      this.g.f = this.f.f;
      if (this.b) {
         float _snowmanxx = 1.0F;
         this.f.b -= 5.0F;
      }

      float _snowmanxx = -14.0F;
      this.i.a(-5.0F, -12.0F, 0.0F);
      this.j.a(5.0F, -12.0F, 0.0F);
   }
}
