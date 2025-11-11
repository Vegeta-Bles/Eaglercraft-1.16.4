import com.mojang.serialization.Codec;
import java.util.Random;

public class csn extends cso {
   public static final Codec<csn> a = Codec.unit(() -> csn.b);
   public static final csn b = new csn();

   private csn() {
   }

   @Override
   public boolean a(fx var1, fx var2, fx var3, Random var4) {
      return true;
   }

   @Override
   protected csp<?> a() {
      return csp.a;
   }
}
