public class dxh extends dzb {
   private final dyw a;
   private final double b;

   protected dxh(
      dwt var1,
      double var2,
      double var4,
      double var6,
      float var8,
      float var9,
      float var10,
      double var11,
      double var13,
      double var15,
      float var17,
      dyw var18,
      float var19,
      int var20,
      double var21,
      boolean var23
   ) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.b = _snowman;
      this.a = _snowman;
      this.j *= (double)_snowman;
      this.k *= (double)_snowman;
      this.l *= (double)_snowman;
      this.j += _snowman;
      this.k += _snowman;
      this.l += _snowman;
      float _snowman = _snowman.t.nextFloat() * _snowman;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.B *= 0.75F * _snowman;
      this.t = (int)((double)_snowman / ((double)_snowman.t.nextFloat() * 0.8 + 0.2));
      this.t = (int)((float)this.t * _snowman);
      this.t = Math.max(this.t, 1);
      this.b(_snowman);
      this.n = _snowman;
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
         this.b(this.a);
         this.k = this.k + this.b;
         this.a(this.j, this.k, this.l);
         if (this.h == this.e) {
            this.j *= 1.1;
            this.l *= 1.1;
         }

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
