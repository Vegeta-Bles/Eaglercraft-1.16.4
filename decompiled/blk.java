public class blk extends blx {
   public blk(blx.a var1) {
      super(_snowman);
   }

   @Override
   public boolean e(bmb var1) {
      return true;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.dV, adr.g, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
      if (!_snowman.v) {
         bgw _snowmanx = new bgw(_snowman, _snowman);
         _snowmanx.b(_snowman);
         _snowmanx.a(_snowman, _snowman.q, _snowman.p, -20.0F, 0.7F, 1.0F);
         _snowman.c(_snowmanx);
      }

      _snowman.b(aea.c.b(this));
      if (!_snowman.bC.d) {
         _snowman.g(1);
      }

      return aov.a(_snowman, _snowman.s_());
   }
}
