import java.util.Random;

public class bwh extends bud {
   protected bwh(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new cdk();
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdk) {
         int _snowmanx = ((cdk)_snowman).j();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble();
            double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble();
            double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble();
            double _snowmanxxxxxx = (_snowman.nextDouble() - 0.5) * 0.5;
            double _snowmanxxxxxxx = (_snowman.nextDouble() - 0.5) * 0.5;
            double _snowmanxxxxxxxx = (_snowman.nextDouble() - 0.5) * 0.5;
            int _snowmanxxxxxxxxx = _snowman.nextInt(2) * 2 - 1;
            if (_snowman.nextBoolean()) {
               _snowmanxxxxx = (double)_snowman.w() + 0.5 + 0.25 * (double)_snowmanxxxxxxxxx;
               _snowmanxxxxxxxx = (double)(_snowman.nextFloat() * 2.0F * (float)_snowmanxxxxxxxxx);
            } else {
               _snowmanxxx = (double)_snowman.u() + 0.5 + 0.25 * (double)_snowmanxxxxxxxxx;
               _snowmanxxxxxx = (double)(_snowman.nextFloat() * 2.0F * (float)_snowmanxxxxxxxxx);
            }

            _snowman.a(hh.Q, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
         }
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return bmb.b;
   }

   @Override
   public boolean a(ceh var1, cuw var2) {
      return false;
   }
}
