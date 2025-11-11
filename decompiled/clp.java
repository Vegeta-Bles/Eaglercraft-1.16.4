import com.mojang.serialization.Codec;
import java.util.Random;

public class clp extends cll {
   public static final Codec<clp> b = Codec.unit(() -> clp.c);
   public static final clp c = new clp();

   public clp() {
   }

   @Override
   protected clm<?> a() {
      return clm.a;
   }

   @Override
   public void a(bry var1, fx var2, ceh var3, Random var4) {
      _snowman.a(_snowman, _snowman, 2);
   }
}
