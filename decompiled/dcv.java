import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class dcv implements dcz {
   private final dct a;
   private final int b;
   private final int c;
   private final int d;

   dcv(int var1, int var2) {
      this.a = new dct((int)dde.a(_snowman, _snowman));
      this.b = _snowman;
      this.c = _snowman;
      this.d = IntMath.gcd(_snowman, _snowman);
   }

   @Override
   public boolean a(dcz.a var1) {
      int _snowman = this.b / this.d;
      int _snowmanx = this.c / this.d;

      for (int _snowmanxx = 0; _snowmanxx <= this.a.size(); _snowmanxx++) {
         if (!_snowman.merge(_snowmanxx / _snowmanx, _snowmanxx / _snowman, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList a() {
      return this.a;
   }
}
