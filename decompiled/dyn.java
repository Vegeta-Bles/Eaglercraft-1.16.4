public class dyn extends dym {
   private dyn(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.B = (float)((double)this.B * 1.5);
      this.t = (int)(Math.random() * 2.0) + 60;
   }

   @Override
   public float b(float var1) {
      float _snowman = 1.0F - ((float)this.s + _snowman) / ((float)this.t * 1.5F);
      return this.B * _snowman;
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
         this.g = this.g + this.j * (double)_snowman;
         this.h = this.h + this.k * (double)_snowman;
         this.i = this.i + this.l * (double)_snowman;
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyn _snowman = new dyn(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
