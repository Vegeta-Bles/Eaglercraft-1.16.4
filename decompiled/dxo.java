public class dxo extends dzb {
   private boolean a;
   private final dyw b;

   private dxo(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.v = afm.a(this.r, 0.7176471F, 0.8745098F);
      this.w = afm.a(this.r, 0.0F, 0.0F);
      this.x = afm.a(this.r, 0.8235294F, 0.9764706F);
      this.B *= 0.75F;
      this.t = (int)(20.0 / ((double)this.r.nextFloat() * 0.8 + 0.2));
      this.a = false;
      this.n = false;
      this.b = _snowman;
      this.b(_snowman);
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.b(this.b);
         if (this.m) {
            this.k = 0.0;
            this.a = true;
         }

         if (this.a) {
            this.k += 0.002;
         }

         this.a(this.j, this.k, this.l);
         if (this.h == this.e) {
            this.j *= 1.1;
            this.l *= 1.1;
         }

         this.j *= 0.96F;
         this.l *= 0.96F;
         if (this.a) {
            this.k *= 0.96F;
         }
      }
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public float b(float var1) {
      return this.B * afm.a(((float)this.s + _snowman) / (float)this.t * 32.0F, 0.0F, 1.0F);
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxo(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
