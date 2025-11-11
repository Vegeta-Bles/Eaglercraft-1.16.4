public class dzg extends dzb {
   protected dzg(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.j *= 0.3F;
      this.k = Math.random() * 0.2F + 0.1F;
      this.l *= 0.3F;
      this.a(0.01F, 0.01F);
      this.u = 0.06F;
      this.t = (int)(8.0 / (Math.random() * 0.8 + 0.2));
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
      if (this.t-- <= 0) {
         this.j();
      } else {
         this.k = this.k - (double)this.u;
         this.a(this.j, this.k, this.l);
         this.j *= 0.98F;
         this.k *= 0.98F;
         this.l *= 0.98F;
         if (this.m) {
            if (Math.random() < 0.5) {
               this.j();
            }

            this.j *= 0.7F;
            this.l *= 0.7F;
         }

         fx _snowman = new fx(this.g, this.h, this.i);
         double _snowmanx = Math.max(this.c.d_(_snowman).k(this.c, _snowman).b(gc.a.b, this.g - (double)_snowman.u(), this.i - (double)_snowman.w()), (double)this.c.b(_snowman).a((brc)this.c, _snowman));
         if (_snowmanx > 0.0 && this.h < (double)_snowman.v() + _snowmanx) {
            this.j();
         }
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dzg _snowman = new dzg(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
