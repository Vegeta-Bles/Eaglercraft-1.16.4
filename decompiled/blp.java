public class blp extends blx implements bno {
   public blp(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.bI != null) {
         if (!_snowman.v) {
            int _snowmanx = _snowman.bI.b(_snowman);
            _snowman.a(_snowmanx, _snowman, var1x -> var1x.d(_snowman));
         }

         _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.el, adr.g, 1.0F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
      } else {
         _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.en, adr.g, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
         if (!_snowman.v) {
            int _snowmanx = bpu.c(_snowman);
            int _snowmanxx = bpu.b(_snowman);
            _snowman.c(new bgi(_snowman, _snowman, _snowmanxx, _snowmanx));
         }

         _snowman.b(aea.c.b(this));
      }

      return aov.a(_snowman, _snowman.s_());
   }

   @Override
   public int c() {
      return 1;
   }
}
