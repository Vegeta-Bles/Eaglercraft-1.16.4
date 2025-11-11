public class dyx extends dyp {
   private dyx(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, 0.0F);
      this.B = 0.5F;
      this.e(1.0F);
      this.a(0.0F, 0.0F, 0.0F);
      this.t = (int)((double)(this.B * 12.0F) / (Math.random() * 0.8F + 0.2F));
      this.b(_snowman);
      this.n = false;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.f(0.0F);
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
         if (this.s > this.t / 2) {
            this.e(1.0F - ((float)this.s - (float)(this.t / 2)) / (float)this.t);
         }

         this.a(this.j, this.k, this.l);
         if (this.c.d_(new fx(this.g, this.h, this.i)).g()) {
            this.k -= 0.008F;
         }

         this.j *= 0.92F;
         this.k *= 0.92F;
         this.l *= 0.92F;
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
         return new dyx(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
