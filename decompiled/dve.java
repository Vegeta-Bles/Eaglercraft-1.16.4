public class dve<T extends bao> extends dvi<T> {
   public dve() {
      super(12, 0.0F, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
      this.r = 128;
      this.s = 64;
      this.a = new dwn(this, 0, 0);
      this.a.a(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F, 0.0F);
      this.a.a(0.0F, 10.0F, -16.0F);
      this.a.a(0, 44).a(-2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F, 0.0F);
      this.a.a(26, 0).a(-4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      dwn _snowman = this.a.a(26, 0);
      _snowman.g = true;
      _snowman.a(2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.b = new dwn(this);
      this.b.a(0, 19).a(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, 0.0F);
      this.b.a(39, 0).a(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F, 0.0F);
      this.b.a(-2.0F, 9.0F, 12.0F);
      int _snowmanx = 10;
      this.f = new dwn(this, 50, 22);
      this.f.a(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, 0.0F);
      this.f.a(-3.5F, 14.0F, 6.0F);
      this.g = new dwn(this, 50, 22);
      this.g.a(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, 0.0F);
      this.g.a(3.5F, 14.0F, 6.0F);
      this.h = new dwn(this, 50, 40);
      this.h.a(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, 0.0F);
      this.h.a(-2.5F, 14.0F, -7.0F);
      this.i = new dwn(this, 50, 40);
      this.i.a(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, 0.0F);
      this.i.a(2.5F, 14.0F, -7.0F);
      this.f.a--;
      this.g.a++;
      this.f.c += 0.0F;
      this.g.c += 0.0F;
      this.h.a--;
      this.i.a++;
      this.h.c--;
      this.i.c--;
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = _snowman - (float)_snowman.K;
      float _snowmanx = _snowman.y(_snowman);
      _snowmanx *= _snowmanx;
      float _snowmanxx = 1.0F - _snowmanx;
      this.b.d = (float) (Math.PI / 2) - _snowmanx * (float) Math.PI * 0.35F;
      this.b.b = 9.0F * _snowmanxx + 11.0F * _snowmanx;
      this.h.b = 14.0F * _snowmanxx - 6.0F * _snowmanx;
      this.h.c = -8.0F * _snowmanxx - 4.0F * _snowmanx;
      this.h.d -= _snowmanx * (float) Math.PI * 0.45F;
      this.i.b = this.h.b;
      this.i.c = this.h.c;
      this.i.d -= _snowmanx * (float) Math.PI * 0.45F;
      if (this.e) {
         this.a.b = 10.0F * _snowmanxx - 9.0F * _snowmanx;
         this.a.c = -16.0F * _snowmanxx - 7.0F * _snowmanx;
      } else {
         this.a.b = 10.0F * _snowmanxx - 14.0F * _snowmanx;
         this.a.c = -16.0F * _snowmanxx - 3.0F * _snowmanx;
      }

      this.a.d += _snowmanx * (float) Math.PI * 0.15F;
   }
}
