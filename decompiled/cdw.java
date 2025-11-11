import java.util.Random;
import javax.annotation.Nullable;

public class cdw extends cdp {
   public cdw() {
   }

   @Nullable
   @Override
   protected civ<cmz, ?> a(Random var1, boolean var2) {
      return kh.bL;
   }

   @Nullable
   @Override
   protected civ<cmz, ?> a(Random var1) {
      return _snowman.nextBoolean() ? kh.bR : kh.bS;
   }
}
