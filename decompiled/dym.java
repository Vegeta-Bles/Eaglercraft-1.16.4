public class dym extends dzb {
   private final double a;
   private final double b;
   private final double D;

   protected dym(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.a = this.g;
      this.b = this.h;
      this.D = this.i;
      this.B = 0.1F * (this.r.nextFloat() * 0.2F + 0.5F);
      float _snowman = this.r.nextFloat() * 0.6F + 0.4F;
      this.v = _snowman * 0.9F;
      this.w = _snowman * 0.3F;
      this.x = _snowman;
      this.t = (int)(Math.random() * 10.0) + 40;
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public void a(double var1, double var3, double var5) {
      this.a(this.m().d(_snowman, _snowman, _snowman));
      this.k();
   }

   @Override
   public float b(float var1) {
      float _snowman = ((float)this.s + _snowman) / (float)this.t;
      _snowman = 1.0F - _snowman;
      _snowman *= _snowman;
      _snowman = 1.0F - _snowman;
      return this.B * _snowman;
   }

   @Override
   public int a(float var1) {
      int _snowman = super.a(_snowman);
      float _snowmanx = (float)this.s / (float)this.t;
      _snowmanx *= _snowmanx;
      _snowmanx *= _snowmanx;
      int _snowmanxx = _snowman & 0xFF;
      int _snowmanxxx = _snowman >> 16 & 0xFF;
      _snowmanxxx += (int)(_snowmanx * 15.0F * 16.0F);
      if (_snowmanxxx > 240) {
         _snowmanxxx = 240;
      }

      return _snowmanxx | _snowmanxxx << 16;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         float _snowman = (float)this.s / (float)this.t;
         float var3 = -_snowman + _snowman * _snowman * 2.0F;
         float var4 = 1.0F - var3;
         this.g = this.a + this.j * (double)var4;
         this.h = this.b + this.k * (double)var4 + (double)(1.0F - _snowman);
         this.i = this.D + this.l * (double)var4;
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dym _snowman = new dym(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
