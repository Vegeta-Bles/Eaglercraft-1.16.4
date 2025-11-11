public class dxy extends dzb {
   private final dyw a;

   private dxy(dwt var1, double var2, double var4, double var6, double var8, dyw var10) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.t = 6 + this.r.nextInt(4);
      float _snowman = this.r.nextFloat() * 0.6F + 0.4F;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.B = 2.0F * (1.0F - (float)_snowman * 0.5F);
      this.a = _snowman;
      this.b(_snowman);
   }

   @Override
   public int a(float var1) {
      return 15728880;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.b(this.a);
      }
   }

   @Override
   public dyk b() {
      return dyk.d;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxy(_snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
