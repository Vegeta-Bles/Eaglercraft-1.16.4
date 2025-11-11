public class dxw extends dyo {
   private dxw(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
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
      return this.B * (1.0F - _snowman * _snowman * 0.5F);
   }

   @Override
   public int a(float var1) {
      float _snowman = ((float)this.s + _snowman) / (float)this.t;
      _snowman = afm.a(_snowman, 0.0F, 1.0F);
      int _snowmanx = super.a(_snowman);
      int _snowmanxx = _snowmanx & 0xFF;
      int _snowmanxxx = _snowmanx >> 16 & 0xFF;
      _snowmanxx += (int)(_snowman * 15.0F * 16.0F);
      if (_snowmanxx > 240) {
         _snowmanxx = 240;
      }

      return _snowmanxx | _snowmanxxx << 16;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxw _snowman = new dxw(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
