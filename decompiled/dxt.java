public class dxt extends dzb {
   private final dyw a;

   protected dxt(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.j = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.k = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.l = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      float _snowman = this.r.nextFloat() * 0.3F + 0.7F;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.B = 0.1F * (this.r.nextFloat() * this.r.nextFloat() * 6.0F + 1.0F);
      this.t = (int)(16.0 / ((double)this.r.nextFloat() * 0.8 + 0.2)) + 2;
      this.b(_snowman);
   }

   @Override
   public dyk b() {
      return dyk.b;
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
         this.k += 0.004;
         this.a(this.j, this.k, this.l);
         this.j *= 0.9F;
         this.k *= 0.9F;
         this.l *= 0.9F;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxt(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
