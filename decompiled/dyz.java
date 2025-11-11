public class dyz extends dzb {
   private dyz(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = this.r.nextFloat() * 0.1F + 0.2F;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.a(0.02F, 0.02F);
      this.B = this.B * (this.r.nextFloat() * 0.6F + 0.5F);
      this.j *= 0.02F;
      this.k *= 0.02F;
      this.l *= 0.02F;
      this.t = (int)(20.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public void a(double var1, double var3, double var5) {
      this.a(this.m().d(_snowman, _snowman, _snowman));
      this.k();
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.t-- <= 0) {
         this.j();
      } else {
         this.a(this.j, this.k, this.l);
         this.j *= 0.99;
         this.k *= 0.99;
         this.l *= 0.99;
      }
   }

   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyz _snowman = new dyz(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         _snowman.a(1.0F, 1.0F, 1.0F);
         _snowman.a(3 + _snowman.u_().nextInt(5));
         return _snowman;
      }
   }

   public static class b implements dyj<hi> {
      private final dyw a;

      public b(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyz _snowman = new dyz(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(0.3F, 0.5F, 1.0F);
         _snowman.a(this.a);
         _snowman.e(1.0F - _snowman.t.nextFloat() * 0.7F);
         _snowman.a(_snowman.i() / 2);
         return _snowman;
      }
   }

   public static class c implements dyj<hi> {
      private final dyw a;

      public c(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyz _snowman = new dyz(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         _snowman.a(1.0F, 1.0F, 1.0F);
         return _snowman;
      }
   }

   public static class d implements dyj<hi> {
      private final dyw a;

      public d(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dyz _snowman = new dyz(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
