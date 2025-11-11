public class avk extends awj {
   private final bab g;

   public avk(bab var1, double var2) {
      super(_snowman, _snowman, 8);
      this.g = _snowman;
   }

   @Override
   public boolean a() {
      return this.g.eK() && !this.g.eO() && super.a();
   }

   @Override
   public void c() {
      super.c();
      this.g.v(false);
   }

   @Override
   public void d() {
      super.d();
      this.g.v(false);
   }

   @Override
   public void e() {
      super.e();
      this.g.v(this.l());
   }

   @Override
   protected boolean a(brz var1, fx var2) {
      if (!_snowman.w(_snowman.b())) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman);
         if (_snowman.a(bup.bR)) {
            return ccn.a(_snowman, _snowman) < 1;
         } else {
            return _snowman.a(bup.bY) && _snowman.c(bwy.b) ? true : _snowman.a(aed.L, var0 -> var0.d(buj.a).map(var0x -> var0x != cev.a).orElse(true));
         }
      }
   }
}
