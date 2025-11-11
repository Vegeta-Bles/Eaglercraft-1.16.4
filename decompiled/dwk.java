public class dwk<T extends bej> extends dum<T> implements dwe {
   private dwn a;

   public dwk(float var1, boolean var2) {
      super(_snowman, 0.0F, 64, _snowman ? 32 : 64);
      if (_snowman) {
         this.f = new dwn(this, 0, 0);
         this.f.a(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman);
         this.h = new dwn(this, 16, 16);
         this.h.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman + 0.1F);
         this.k = new dwn(this, 0, 16);
         this.k.a(-2.0F, 12.0F, 0.0F);
         this.k.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.1F);
         this.l = new dwn(this, 0, 16);
         this.l.g = true;
         this.l.a(2.0F, 12.0F, 0.0F);
         this.l.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.1F);
      } else {
         this.f = new dwn(this, 0, 0);
         this.f.a(0, 0).a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman);
         this.f.a(24, 0).a(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, _snowman);
         this.g = new dwn(this, 32, 0);
         this.g.a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman + 0.5F);
         this.a = new dwn(this);
         this.a.a(30, 47).a(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, _snowman);
         this.a.d = (float) (-Math.PI / 2);
         this.g.b(this.a);
         this.h = new dwn(this, 16, 20);
         this.h.a(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, _snowman);
         this.h.a(0, 38).a(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, _snowman + 0.05F);
         this.i = new dwn(this, 44, 22);
         this.i.a(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
         this.i.a(-5.0F, 2.0F, 0.0F);
         this.j = new dwn(this, 44, 22);
         this.j.g = true;
         this.j.a(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
         this.j.a(5.0F, 2.0F, 0.0F);
         this.k = new dwn(this, 0, 22);
         this.k.a(-2.0F, 12.0F, 0.0F);
         this.k.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
         this.l = new dwn(this, 0, 22);
         this.l.g = true;
         this.l.a(2.0F, 12.0F, 0.0F);
         this.l.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      }
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      dtg.a(this.j, this.i, _snowman.eF(), this.c, _snowman);
   }

   @Override
   public void a(boolean var1) {
      this.f.h = _snowman;
      this.g.h = _snowman;
      this.a.h = _snowman;
   }
}
