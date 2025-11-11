public class dwg<T extends aqa> extends dwf<T> {
   private boolean m;
   private final dwn n = new dwn(this).b(64, 128);

   public dwg(float var1) {
      super(_snowman, 64, 128);
      this.n.a(0.0F, -2.0F, 0.0F);
      this.n.a(0, 0).a(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F);
      this.l.b(this.n);
      this.a = new dwn(this).b(64, 128);
      this.a.a(0.0F, 0.0F, 0.0F);
      this.a.a(0, 0).a(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman);
      this.b = new dwn(this).b(64, 128);
      this.b.a(-5.0F, -10.03125F, -5.0F);
      this.b.a(0, 64).a(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F);
      this.a.b(this.b);
      this.a.b(this.l);
      dwn _snowman = new dwn(this).b(64, 128);
      _snowman.a(1.75F, -4.0F, 2.0F);
      _snowman.a(0, 76).a(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F);
      _snowman.d = -0.05235988F;
      _snowman.f = 0.02617994F;
      this.b.b(_snowman);
      dwn _snowmanx = new dwn(this).b(64, 128);
      _snowmanx.a(1.75F, -4.0F, 2.0F);
      _snowmanx.a(0, 87).a(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F);
      _snowmanx.d = -0.10471976F;
      _snowmanx.f = 0.05235988F;
      _snowman.b(_snowmanx);
      dwn _snowmanxx = new dwn(this).b(64, 128);
      _snowmanxx.a(1.75F, -2.0F, 2.0F);
      _snowmanxx.a(0, 95).a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.25F);
      _snowmanxx.d = (float) (-Math.PI / 15);
      _snowmanxx.f = 0.10471976F;
      _snowmanx.b(_snowmanxx);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.l.a(0.0F, -2.0F, 0.0F);
      float _snowman = 0.01F * (float)(_snowman.Y() % 10);
      this.l.d = afm.a((float)_snowman.K * _snowman) * 4.5F * (float) (Math.PI / 180.0);
      this.l.e = 0.0F;
      this.l.f = afm.b((float)_snowman.K * _snowman) * 2.5F * (float) (Math.PI / 180.0);
      if (this.m) {
         this.l.a(0.0F, 1.0F, -1.5F);
         this.l.d = -0.9F;
      }
   }

   public dwn b() {
      return this.l;
   }

   public void b(boolean var1) {
      this.m = _snowman;
   }
}
