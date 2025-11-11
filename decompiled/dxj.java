public class dxj extends dzb {
   private dxj(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a(0.02F, 0.02F);
      this.B = this.B * (this.r.nextFloat() * 0.6F + 0.2F);
      this.j = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.k = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.l = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.t = (int)(40.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      this.k += 0.005;
      if (this.t-- <= 0) {
         this.j();
      } else {
         this.a(this.j, this.k, this.l);
         this.j *= 0.85F;
         this.k *= 0.85F;
         this.l *= 0.85F;
         if (!this.c.b(new fx(this.g, this.h, this.i)).a(aef.b)) {
            this.j();
         }
      }
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxj _snowman = new dxj(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
