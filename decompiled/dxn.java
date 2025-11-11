public class dxn extends dzb {
   private dxn(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.j *= 0.1F;
      this.k *= 0.1F;
      this.l *= 0.1F;
      this.j += _snowman * 0.4;
      this.k += _snowman * 0.4;
      this.l += _snowman * 0.4;
      float _snowman = (float)(Math.random() * 0.3F + 0.6F);
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.B *= 0.75F;
      this.t = Math.max((int)(6.0 / (Math.random() * 0.8 + 0.6)), 1);
      this.n = false;
      this.a();
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
         this.w = (float)((double)this.w * 0.96);
         this.x = (float)((double)this.x * 0.9);
         this.j *= 0.7F;
         this.k *= 0.7F;
         this.l *= 0.7F;
         this.k -= 0.02F;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxn _snowman = new dxn(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 1.0, _snowman);
         _snowman.a(20);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxn _snowman = new dxn(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.v *= 0.3F;
         _snowman.w *= 0.8F;
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class c implements dyj<hi> {
      private final dyw a;

      public c(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxn _snowman = new dxn(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
