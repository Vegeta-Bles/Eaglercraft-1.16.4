public class dxx extends dzb {
   private dxx(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.j *= 0.01F;
      this.k *= 0.01F;
      this.l *= 0.01F;
      this.k += 0.1;
      this.B *= 1.5F;
      this.t = 16;
      this.n = false;
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public float b(float var1) {
      return this.B * afm.a(((float)this.s + _snowman) / (float)this.t * 32.0F, 0.0F, 1.0F);
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
         if (this.h == this.e) {
            this.j *= 1.1;
            this.l *= 1.1;
         }

         this.j *= 0.86F;
         this.k *= 0.86F;
         this.l *= 0.86F;
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
         dxx _snowman = new dxx(_snowman, _snowman, _snowman + 0.5, _snowman);
         _snowman.a(this.a);
         _snowman.a(1.0F, 1.0F, 1.0F);
         return _snowman;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxx _snowman = new dxx(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
