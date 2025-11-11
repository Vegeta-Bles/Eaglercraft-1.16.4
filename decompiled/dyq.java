public abstract class dyq extends dyg {
   protected float B = 0.1F * (this.r.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected dyq(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   protected dyq(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(dfq var1, djk var2, float var3) {
      dcn _snowman = _snowman.b();
      float _snowmanx = (float)(afm.d((double)_snowman, this.d, this.g) - _snowman.a());
      float _snowmanxx = (float)(afm.d((double)_snowman, this.e, this.h) - _snowman.b());
      float _snowmanxxx = (float)(afm.d((double)_snowman, this.f, this.i) - _snowman.c());
      d _snowmanxxxx;
      if (this.z == 0.0F) {
         _snowmanxxxx = _snowman.f();
      } else {
         _snowmanxxxx = new d(_snowman.f());
         float _snowmanxxxxx = afm.g(_snowman, this.A, this.z);
         _snowmanxxxx.a(g.f.c(_snowmanxxxxx));
      }

      g _snowmanxxxxx = new g(-1.0F, -1.0F, 0.0F);
      _snowmanxxxxx.a(_snowmanxxxx);
      g[] _snowmanxxxxxx = new g[]{new g(-1.0F, -1.0F, 0.0F), new g(-1.0F, 1.0F, 0.0F), new g(1.0F, 1.0F, 0.0F), new g(1.0F, -1.0F, 0.0F)};
      float _snowmanxxxxxxx = this.b(_snowman);

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
         g _snowmanxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxx];
         _snowmanxxxxxxxxx.a(_snowmanxxxx);
         _snowmanxxxxxxxxx.b(_snowmanxxxxxxx);
         _snowmanxxxxxxxxx.c(_snowmanx, _snowmanxx, _snowmanxxx);
      }

      float _snowmanxxxxxxxx = this.c();
      float _snowmanxxxxxxxxx = this.d();
      float _snowmanxxxxxxxxxx = this.e();
      float _snowmanxxxxxxxxxxx = this.f();
      int _snowmanxxxxxxxxxxxx = this.a(_snowman);
      _snowman.a((double)_snowmanxxxxxx[0].a(), (double)_snowmanxxxxxx[0].b(), (double)_snowmanxxxxxx[0].c())
         .a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx)
         .a(this.v, this.w, this.x, this.y)
         .a(_snowmanxxxxxxxxxxxx)
         .d();
      _snowman.a((double)_snowmanxxxxxx[1].a(), (double)_snowmanxxxxxx[1].b(), (double)_snowmanxxxxxx[1].c())
         .a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx)
         .a(this.v, this.w, this.x, this.y)
         .a(_snowmanxxxxxxxxxxxx)
         .d();
      _snowman.a((double)_snowmanxxxxxx[2].a(), (double)_snowmanxxxxxx[2].b(), (double)_snowmanxxxxxx[2].c())
         .a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx)
         .a(this.v, this.w, this.x, this.y)
         .a(_snowmanxxxxxxxxxxxx)
         .d();
      _snowman.a((double)_snowmanxxxxxx[3].a(), (double)_snowmanxxxxxx[3].b(), (double)_snowmanxxxxxx[3].c())
         .a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx)
         .a(this.v, this.w, this.x, this.y)
         .a(_snowmanxxxxxxxxxxxx)
         .d();
   }

   public float b(float var1) {
      return this.B;
   }

   @Override
   public dyg d(float var1) {
      this.B *= _snowman;
      return super.d(_snowman);
   }

   protected abstract float c();

   protected abstract float d();

   protected abstract float e();

   protected abstract float f();
}
