import com.mojang.serialization.Codec;
import java.util.Random;

public class ckm extends cjl<cmm> {
   public ckm(Codec<cmm> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmm var5) {
      for (clj _snowman : _snowman.b) {
         if (_snowman.nextFloat() < _snowman.c) {
            return _snowman.a(_snowman, _snowman, _snowman, _snowman);
         }
      }

      return _snowman.c.get().a(_snowman, _snowman, _snowman, _snowman);
   }
}
