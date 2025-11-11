public class dze extends dzb {
   private final dyw a;

   private dze(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.a = _snowman;
      this.j *= 0.3F;
      this.k = Math.random() * 0.2F + 0.1F;
      this.l *= 0.3F;
      this.a(0.01F, 0.01F);
      this.t = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.b(_snowman);
      this.u = 0.0F;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
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
      int _snowman = 60 - this.t;
      if (this.t-- <= 0) {
         this.j();
      } else {
         this.k = this.k - (double)this.u;
         this.a(this.j, this.k, this.l);
         this.j *= 0.98F;
         this.k *= 0.98F;
         this.l *= 0.98F;
         float _snowmanx = (float)_snowman * 0.001F;
         this.a(_snowmanx, _snowmanx);
         this.a(this.a.a(_snowman % 4, 4));
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dze(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
