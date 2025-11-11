import com.mojang.serialization.Codec;
import java.util.Random;

public class cjc extends cii<cmn> {
   public cjc(Codec<cmn> var1) {
      super(_snowman);
   }

   public boolean a(bry var1, fx var2, cmn var3) {
      return !_snowman.e.contains(_snowman.d_(_snowman));
   }

   public int a(cmn var1) {
      return _snowman.f;
   }

   public fx a(Random var1, fx var2, cmn var3) {
      return _snowman.b(_snowman.nextInt(_snowman.g) - _snowman.nextInt(_snowman.g), _snowman.nextInt(_snowman.h) - _snowman.nextInt(_snowman.h), _snowman.nextInt(_snowman.i) - _snowman.nextInt(_snowman.i));
   }

   public ceh b(Random var1, fx var2, cmn var3) {
      return _snowman.b.a(_snowman, _snowman);
   }
}
