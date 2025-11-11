public class dyf extends dzb {
   private dyf(dwt var1, double var2, double var4, double var6, double var8) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.j *= 0.01F;
      this.k *= 0.01F;
      this.l *= 0.01F;
      this.k += 0.2;
      this.v = Math.max(0.0F, afm.a(((float)_snowman + 0.0F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.w = Math.max(0.0F, afm.a(((float)_snowman + 0.33333334F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.x = Math.max(0.0F, afm.a(((float)_snowman + 0.6666667F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.B *= 1.5F;
      this.t = 6;
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

         this.j *= 0.66F;
         this.k *= 0.66F;
         this.l *= 0.66F;
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
         dyf _snowman = new dyf(_snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
