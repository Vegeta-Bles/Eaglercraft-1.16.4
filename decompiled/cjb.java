import com.mojang.serialization.Codec;
import java.util.Random;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cjb extends cjl<clv> {
   public cjb(Codec<clv> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clv var5) {
      MutableBoolean _snowman = new MutableBoolean();
      _snowman.c.a(new cpv(_snowman, _snowman), _snowman, _snowman).forEach(var5x -> {
         if (_snowman.b.get().a(_snowman, _snowman, _snowman, var5x)) {
            _snowman.setTrue();
         }
      });
      return _snowman.isTrue();
   }

   @Override
   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), gm.aE.b(this));
   }
}
