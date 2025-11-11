public class dyc extends dzb {
   private dyc(dwt var1, double var2, double var4, double var6) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.j *= 0.8F;
      this.k *= 0.8F;
      this.l *= 0.8F;
      this.k = (double)(this.r.nextFloat() * 0.4F + 0.05F);
      this.B = this.B * (this.r.nextFloat() * 2.0F + 0.2F);
      this.t = (int)(16.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public int a(float var1) {
      int _snowman = super.a(_snowman);
      int _snowmanx = 240;
      int _snowmanxx = _snowman >> 16 & 0xFF;
      return 240 | _snowmanxx << 16;
   }

   @Override
   public float b(float var1) {
      float _snowman = ((float)this.s + _snowman) / (float)this.t;
      return this.B * (1.0F - _snowman * _snowman);
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      float _snowman = (float)this.s / (float)this.t;
      if (this.r.nextFloat() > _snowman) {
         this.c.a(hh.S, this.g, this.h, this.i, this.j, this.k, this.l);
      }

      if (this.s++ >= this.t) {
         this.j();
      } else {
         this.k -= 0.03;
         this.a(this.j, this.k, this.l);
         this.j *= 0.999F;
         this.k *= 0.999F;
         this.l *= 0.999F;
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
         dyc _snowman = new dyc(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
