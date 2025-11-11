import java.util.List;
import java.util.Random;

public class afz {
   public static int a(List<? extends afz.a> var0) {
      int _snowman = 0;
      int _snowmanx = 0;

      for (int _snowmanxx = _snowman.size(); _snowmanx < _snowmanxx; _snowmanx++) {
         afz.a _snowmanxxx = _snowman.get(_snowmanx);
         _snowman += _snowmanxxx.a;
      }

      return _snowman;
   }

   public static <T extends afz.a> T a(Random var0, List<T> var1, int var2) {
      if (_snowman <= 0) {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException());
      } else {
         int _snowman = _snowman.nextInt(_snowman);
         return a(_snowman, _snowman);
      }
   }

   public static <T extends afz.a> T a(List<T> var0, int var1) {
      int _snowman = 0;

      for (int _snowmanx = _snowman.size(); _snowman < _snowmanx; _snowman++) {
         T _snowmanxx = (T)_snowman.get(_snowman);
         _snowman -= _snowmanxx.a;
         if (_snowman < 0) {
            return _snowmanxx;
         }
      }

      return null;
   }

   public static <T extends afz.a> T a(Random var0, List<T> var1) {
      return a(_snowman, _snowman, a(_snowman));
   }

   public static class a {
      protected final int a;

      public a(int var1) {
         this.a = _snowman;
      }
   }
}
