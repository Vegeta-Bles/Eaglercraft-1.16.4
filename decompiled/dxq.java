public class dxq extends dzb {
   private final dyw a;

   private dxq(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, hd var14, dyw var15) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.j *= 0.1F;
      this.k *= 0.1F;
      this.l *= 0.1F;
      float _snowman = (float)Math.random() * 0.4F + 0.6F;
      this.v = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.c() * _snowman;
      this.w = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.d() * _snowman;
      this.x = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.e() * _snowman;
      this.B = this.B * 0.75F * _snowman.f();
      int _snowmanx = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.t = (int)Math.max((float)_snowmanx * _snowman.f(), 1.0F);
      this.b(_snowman);
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

   public static class a implements dyj<hd> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hd var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new dxq(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }
}
