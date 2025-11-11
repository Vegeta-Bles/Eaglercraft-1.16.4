import com.mojang.serialization.Codec;
import java.util.Random;

public class cny extends cnt {
   public static final Codec<cny> b = ceh.b.fieldOf("state").xmap(cny::new, var0 -> var0.c).codec();
   private final ceh c;

   public cny(ceh var1) {
      this.c = _snowman;
   }

   @Override
   protected cnu<?> a() {
      return cnu.a;
   }

   @Override
   public ceh a(Random var1, fx var2) {
      return this.c;
   }
}
