import java.util.Random;

public class byi extends cah {
   public byi(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (_snowman.nextInt(10) == 0) {
         _snowman.a(hh.N, (double)_snowman.u() + _snowman.nextDouble(), (double)_snowman.v() + 1.1, (double)_snowman.w() + _snowman.nextDouble(), 0.0, 0.0, 0.0);
      }
   }
}
