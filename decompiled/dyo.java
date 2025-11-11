public abstract class dyo extends dzb {
   protected dyo(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.j = this.j * 0.01F + _snowman;
      this.k = this.k * 0.01F + _snowman;
      this.l = this.l * 0.01F + _snowman;
      this.g = this.g + (double)((this.r.nextFloat() - this.r.nextFloat()) * 0.05F);
      this.h = this.h + (double)((this.r.nextFloat() - this.r.nextFloat()) * 0.05F);
      this.i = this.i + (double)((this.r.nextFloat() - this.r.nextFloat()) * 0.05F);
      this.t = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.a(this.j, this.k, this.l);
         this.j *= 0.96F;
         this.k *= 0.96F;
         this.l *= 0.96F;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }
}
