public class dyl extends dzb {
   private final dyw a;

   private dyl(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyw var14) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.a = _snowman;
      float _snowman = 2.5F;
      this.j *= 0.1F;
      this.k *= 0.1F;
      this.l *= 0.1F;
      this.j += _snowman;
      this.k += _snowman;
      this.l += _snowman;
      float _snowmanx = 1.0F - (float)(Math.random() * 0.3F);
      this.v = _snowmanx;
      this.w = _snowmanx;
      this.x = _snowmanx;
      this.B *= 1.875F;
      int _snowmanxx = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.t = (int)Math.max((float)_snowmanxx * 2.5F, 1.0F);
      this.n = false;
      this.b(_snowman);
   }

   @Override
   public dyk b() {
      return dyk.c;
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
         this.j *= 0.96F;
         this.k *= 0.96F;
         this.l *= 0.96F;
         bfw _snowman = this.c.a(this.g, this.h, this.i, 2.0, false);
         if (_snowman != null) {
            double _snowmanx = _snowman.cE();
            if (this.h > _snowmanx) {
               this.h = this.h + (_snowmanx - this.h) * 0.2;
               this.k = this.k + (_snowman.cC().c - this.k) * 0.2;
               this.b(this.g, this.h, this.i);
            }
         }

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
         return new dyl(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyg _snowman = new dyl(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a);
         _snowman.a(200.0F, 50.0F, 120.0F);
         _snowman.e(0.4F);
         return _snowman;
      }
   }
}
