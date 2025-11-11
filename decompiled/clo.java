import com.mojang.serialization.Codec;
import java.util.Random;

public class clo extends cll {
   public static final Codec<clo> b = Codec.unit(() -> clo.c);
   public static final clo c = new clo();

   public clo() {
   }

   @Override
   protected clm<?> a() {
      return clm.b;
   }

   @Override
   public void a(bry var1, fx var2, ceh var3, Random var4) {
      ((bwd)_snowman.b()).a(_snowman, _snowman, 2);
   }
}
