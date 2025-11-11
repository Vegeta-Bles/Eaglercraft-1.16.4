public class dzf extends dzb {
   private float a;

   private dzf(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.t = (int)(Math.random() * 60.0) + 30;
      this.n = false;
      this.j = 0.0;
      this.k = -0.05;
      this.l = 0.0;
      this.a(0.02F, 0.02F);
      this.B = this.B * (this.r.nextFloat() * 0.6F + 0.2F);
      this.u = 0.002F;
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
         float _snowman = 0.6F;
         this.j = this.j + (double)(0.6F * afm.b(this.a));
         this.l = this.l + (double)(0.6F * afm.a(this.a));
         this.j *= 0.07;
         this.l *= 0.07;
         this.a(this.j, this.k, this.l);
         if (!this.c.b(new fx(this.g, this.h, this.i)).a(aef.b) || this.m) {
            this.j();
         }

         this.a = (float)((double)this.a + 0.08);
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dzf _snowman = new dzf(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
