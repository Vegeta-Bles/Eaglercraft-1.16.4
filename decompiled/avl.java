public class avl extends avz {
   private static final int[] a = new int[]{0, 1, 4, 5, 6, 7};
   private final baf b;
   private final int c;
   private boolean d;

   public avl(baf var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public boolean a() {
      if (this.b.cY().nextInt(this.c) != 0) {
         return false;
      } else {
         gc _snowman = this.b.ca();
         int _snowmanx = _snowman.i();
         int _snowmanxx = _snowman.k();
         fx _snowmanxxx = this.b.cB();

         for (int _snowmanxxxx : a) {
            if (!this.a(_snowmanxxx, _snowmanx, _snowmanxx, _snowmanxxxx) || !this.b(_snowmanxxx, _snowmanx, _snowmanxx, _snowmanxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(fx var1, int var2, int var3, int var4) {
      fx _snowman = _snowman.b(_snowman * _snowman, 0, _snowman * _snowman);
      return this.b.l.b(_snowman).a(aef.b) && !this.b.l.d_(_snowman).c().c();
   }

   private boolean b(fx var1, int var2, int var3, int var4) {
      return this.b.l.d_(_snowman.b(_snowman * _snowman, 1, _snowman * _snowman)).g() && this.b.l.d_(_snowman.b(_snowman * _snowman, 2, _snowman * _snowman)).g();
   }

   @Override
   public boolean b() {
      double _snowman = this.b.cC().c;
      return (!(_snowman * _snowman < 0.03F) || this.b.q == 0.0F || !(Math.abs(this.b.q) < 10.0F) || !this.b.aE()) && !this.b.ao();
   }

   @Override
   public boolean C_() {
      return false;
   }

   @Override
   public void c() {
      gc _snowman = this.b.ca();
      this.b.f(this.b.cC().b((double)_snowman.i() * 0.6, 0.7, (double)_snowman.k() * 0.6));
      this.b.x().o();
   }

   @Override
   public void d() {
      this.b.q = 0.0F;
   }

   @Override
   public void e() {
      boolean _snowman = this.d;
      if (!_snowman) {
         cux _snowmanx = this.b.l.b(this.b.cB());
         this.d = _snowmanx.a(aef.b);
      }

      if (this.d && !_snowman) {
         this.b.a(adq.cJ, 1.0F, 1.0F);
      }

      dcn _snowmanx = this.b.cC();
      if (_snowmanx.c * _snowmanx.c < 0.03F && this.b.q != 0.0F) {
         this.b.q = afm.j(this.b.q, 0.0F, 0.2F);
      } else {
         double _snowmanxx = Math.sqrt(aqa.c(_snowmanx));
         double _snowmanxxx = Math.signum(-_snowmanx.c) * Math.acos(_snowmanxx / _snowmanx.f()) * 180.0F / (float)Math.PI;
         this.b.q = (float)_snowmanxxx;
      }
   }
}
