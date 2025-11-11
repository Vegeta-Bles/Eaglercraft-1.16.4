import java.util.Random;

public class bwy extends bto {
   protected bwy(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new ccw();
   }

   @Override
   protected void a(brx var1, fx var2, bfw var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccw) {
         _snowman.a((aox)_snowman);
         _snowman.a(aea.al);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(b)) {
         double _snowman = (double)_snowman.u() + 0.5;
         double _snowmanx = (double)_snowman.v();
         double _snowmanxx = (double)_snowman.w() + 0.5;
         if (_snowman.nextDouble() < 0.1) {
            _snowman.a(_snowman, _snowmanx, _snowmanxx, adq.eF, adr.e, 1.0F, 1.0F, false);
         }

         gc _snowmanxxx = _snowman.c(a);
         gc.a _snowmanxxxx = _snowmanxxx.n();
         double _snowmanxxxxx = 0.52;
         double _snowmanxxxxxx = _snowman.nextDouble() * 0.6 - 0.3;
         double _snowmanxxxxxxx = _snowmanxxxx == gc.a.a ? (double)_snowmanxxx.i() * 0.52 : _snowmanxxxxxx;
         double _snowmanxxxxxxxx = _snowman.nextDouble() * 6.0 / 16.0;
         double _snowmanxxxxxxxxx = _snowmanxxxx == gc.a.c ? (double)_snowmanxxx.k() * 0.52 : _snowmanxxxxxx;
         _snowman.a(hh.S, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx, 0.0, 0.0, 0.0);
         _snowman.a(hh.A, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx, 0.0, 0.0, 0.0);
      }
   }
}
