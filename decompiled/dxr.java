public class dxr extends dzb {
   private final double a;
   private final double b;
   private final double D;

   private dxr(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.D = _snowman;
      this.d = _snowman + _snowman;
      this.e = _snowman + _snowman;
      this.f = _snowman + _snowman;
      this.g = this.d;
      this.h = this.e;
      this.i = this.f;
      this.B = 0.1F * (this.r.nextFloat() * 0.5F + 0.2F);
      float _snowman = this.r.nextFloat() * 0.6F + 0.4F;
      this.v = 0.9F * _snowman;
      this.w = 0.9F * _snowman;
      this.x = _snowman;
      this.n = false;
      this.t = (int)(Math.random() * 10.0) + 30;
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
         _snowman = 1.0F - _snowman;
         float _snowmanx = 1.0F - _snowman;
         _snowmanx *= _snowmanx;
         _snowmanx *= _snowmanx;
         this.g = this.a + this.j * (double)_snowman;
         this.h = this.b + this.k * (double)_snowman - (double)(_snowmanx * 1.2F);
         this.i = this.D + this.l * (double)_snowman;
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxr _snowman = new dxr(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxr _snowman = new dxr(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
