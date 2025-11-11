import com.mojang.serialization.Codec;
import java.util.Random;

public class csa extends csu {
   public static final Codec<csa> a = Codec.unit(() -> csa.b);
   public static final csa b = new csa();

   private csa() {
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return true;
   }

   @Override
   protected csv<?> a() {
      return csv.a;
   }
}
