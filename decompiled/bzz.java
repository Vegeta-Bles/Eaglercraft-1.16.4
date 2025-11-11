import java.util.Random;

public class bzz extends bto {
   protected bzz(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new cdh();
   }

   @Override
   protected void a(brx var1, fx var2, bfw var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdh) {
         _snowman.a((aox)_snowman);
         _snowman.a(aea.as);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(b)) {
         double _snowman = (double)_snowman.u() + 0.5;
         double _snowmanx = (double)_snowman.v();
         double _snowmanxx = (double)_snowman.w() + 0.5;
         if (_snowman.nextDouble() < 0.1) {
            _snowman.a(_snowman, _snowmanx, _snowmanxx, adq.oo, adr.e, 1.0F, 1.0F, false);
         }

         _snowman.a(hh.S, _snowman, _snowmanx + 1.1, _snowmanxx, 0.0, 0.0, 0.0);
      }
   }
}
