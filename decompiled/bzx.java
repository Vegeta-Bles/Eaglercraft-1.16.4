public class bzx extends bxi {
   public bzx(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void a(brx var1, fx var2, aqa var3, float var4) {
      if (_snowman.bw()) {
         super.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         _snowman.b(_snowman, 0.0F);
      }
   }

   @Override
   public void a(brc var1, aqa var2) {
      if (_snowman.bw()) {
         super.a(_snowman, _snowman);
      } else {
         this.a(_snowman);
      }
   }

   private void a(aqa var1) {
      dcn _snowman = _snowman.cC();
      if (_snowman.c < 0.0) {
         double _snowmanx = _snowman instanceof aqm ? 1.0 : 0.8;
         _snowman.n(_snowman.b, -_snowman.c * _snowmanx, _snowman.d);
      }
   }

   @Override
   public void a(brx var1, fx var2, aqa var3) {
      double _snowman = Math.abs(_snowman.cC().c);
      if (_snowman < 0.1 && !_snowman.bv()) {
         double _snowmanx = 0.4 + _snowman * 0.2;
         _snowman.f(_snowman.cC().d(_snowmanx, 1.0, _snowmanx));
      }

      super.a(_snowman, _snowman, _snowman);
   }
}
