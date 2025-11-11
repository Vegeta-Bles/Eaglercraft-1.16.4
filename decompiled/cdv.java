import java.util.Random;
import javax.annotation.Nullable;

public class cdv extends cdq {
   public cdv() {
   }

   @Nullable
   @Override
   protected civ<cmz, ?> a(Random var1, boolean var2) {
      if (_snowman.nextInt(10) == 0) {
         return _snowman ? kh.ce : kh.bO;
      } else {
         return _snowman ? kh.bY : kh.bH;
      }
   }
}
