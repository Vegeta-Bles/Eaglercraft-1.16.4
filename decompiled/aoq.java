import java.util.Random;

public class aoq {
   private static final Random a = new Random();

   public static void a(brx var0, fx var1, aon var2) {
      a(_snowman, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), _snowman);
   }

   public static void a(brx var0, aqa var1, aon var2) {
      a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman);
   }

   private static void a(brx var0, double var1, double var3, double var5, aon var7) {
      for (int _snowman = 0; _snowman < _snowman.Z_(); _snowman++) {
         a(_snowman, _snowman, _snowman, _snowman, _snowman.a(_snowman));
      }
   }

   public static void a(brx var0, fx var1, gj<bmb> var2) {
      _snowman.forEach(var2x -> a(_snowman, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), var2x));
   }

   public static void a(brx var0, double var1, double var3, double var5, bmb var7) {
      double _snowman = (double)aqe.L.j();
      double _snowmanx = 1.0 - _snowman;
      double _snowmanxx = _snowman / 2.0;
      double _snowmanxxx = Math.floor(_snowman) + a.nextDouble() * _snowmanx + _snowmanxx;
      double _snowmanxxxx = Math.floor(_snowman) + a.nextDouble() * _snowmanx;
      double _snowmanxxxxx = Math.floor(_snowman) + a.nextDouble() * _snowmanx + _snowmanxx;

      while (!_snowman.a()) {
         bcv _snowmanxxxxxx = new bcv(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman.a(a.nextInt(21) + 10));
         float _snowmanxxxxxxx = 0.05F;
         _snowmanxxxxxx.n(a.nextGaussian() * 0.05F, a.nextGaussian() * 0.05F + 0.2F, a.nextGaussian() * 0.05F);
         _snowman.c(_snowmanxxxxxx);
      }
   }
}
