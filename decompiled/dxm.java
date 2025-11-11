public class dxm extends dzb {
   private dxm(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, boolean var14) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.d(3.0F);
      this.a(0.25F, 0.25F);
      if (_snowman) {
         this.t = this.r.nextInt(50) + 280;
      } else {
         this.t = this.r.nextInt(50) + 80;
      }

      this.u = 3.0E-6F;
      this.j = _snowman;
      this.k = _snowman + (double)(this.r.nextFloat() / 500.0F);
      this.l = _snowman;
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ < this.t && !(this.y <= 0.0F)) {
         this.j = this.j + (double)(this.r.nextFloat() / 5000.0F * (float)(this.r.nextBoolean() ? 1 : -1));
         this.l = this.l + (double)(this.r.nextFloat() / 5000.0F * (float)(this.r.nextBoolean() ? 1 : -1));
         this.k = this.k - (double)this.u;
         this.a(this.j, this.k, this.l);
         if (this.s >= this.t - 60 && this.y > 0.01F) {
            this.y -= 0.015F;
         }
      } else {
         this.j();
      }
   }

   @Override
   public dyk b() {
      return dyk.c;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxm _snowman = new dxm(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
         _snowman.e(0.9F);
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
         dxm _snowman = new dxm(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, true);
         _snowman.e(0.95F);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
