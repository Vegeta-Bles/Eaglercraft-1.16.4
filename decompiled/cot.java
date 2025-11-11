import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cot extends cor {
   public static final Codec<cot> a = Codec.unit(() -> cot.b);
   public static final cot b = new cot();

   public cot() {
   }

   @Override
   protected cos<?> a() {
      return cos.a;
   }

   @Override
   public void a(bsr var1, Random var2, List<fx> var3, List<fx> var4, Set<fx> var5, cra var6) {
      _snowman.forEach(var5x -> {
         if (_snowman.nextInt(3) > 0) {
            fx _snowman = var5x.f();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.c, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(3) > 0) {
            fx _snowman = var5x.g();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.e, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(3) > 0) {
            fx _snowman = var5x.d();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.d, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(3) > 0) {
            fx _snowman = var5x.e();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.b, _snowman, _snowman);
            }
         }
      });
   }
}
