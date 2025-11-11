import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class coq extends cor {
   public static final Codec<coq> a = Codec.unit(() -> coq.b);
   public static final coq b = new coq();

   public coq() {
   }

   @Override
   protected cos<?> a() {
      return cos.b;
   }

   @Override
   public void a(bsr var1, Random var2, List<fx> var3, List<fx> var4, Set<fx> var5, cra var6) {
      _snowman.forEach(var5x -> {
         if (_snowman.nextInt(4) == 0) {
            fx _snowman = var5x.f();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.c, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(4) == 0) {
            fx _snowman = var5x.g();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.e, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(4) == 0) {
            fx _snowman = var5x.d();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.d, _snowman, _snowman);
            }
         }

         if (_snowman.nextInt(4) == 0) {
            fx _snowman = var5x.e();
            if (cjl.b(_snowman, _snowman)) {
               this.a(_snowman, _snowman, cbi.b, _snowman, _snowman);
            }
         }
      });
   }

   private void a(bsb var1, fx var2, cey var3, Set<fx> var4, cra var5) {
      this.a((bse)_snowman, _snowman, _snowman, _snowman, _snowman);
      int _snowman = 4;

      for (fx var7 = _snowman.c(); cjl.b(_snowman, var7) && _snowman > 0; _snowman--) {
         this.a((bse)_snowman, var7, _snowman, _snowman, _snowman);
         var7 = var7.c();
      }
   }
}
