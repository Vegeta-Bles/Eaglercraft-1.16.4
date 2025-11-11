import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class com extends cor {
   public static final Codec<com> a = cnt.a.fieldOf("provider").xmap(com::new, var0 -> var0.b).codec();
   private final cnt b;

   public com(cnt var1) {
      this.b = _snowman;
   }

   @Override
   protected cos<?> a() {
      return cos.e;
   }

   @Override
   public void a(bsr var1, Random var2, List<fx> var3, List<fx> var4, Set<fx> var5, cra var6) {
      int _snowman = _snowman.get(0).v();
      _snowman.stream().filter(var1x -> var1x.v() == _snowman).forEach(var3x -> {
         this.a((bsb)_snowman, _snowman, var3x.f().d());
         this.a((bsb)_snowman, _snowman, var3x.g(2).d());
         this.a((bsb)_snowman, _snowman, var3x.f().e(2));
         this.a((bsb)_snowman, _snowman, var3x.g(2).e(2));

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            int _snowmanx = _snowman.nextInt(64);
            int _snowmanxx = _snowmanx % 8;
            int _snowmanxxx = _snowmanx / 8;
            if (_snowmanxx == 0 || _snowmanxx == 7 || _snowmanxxx == 0 || _snowmanxxx == 7) {
               this.a((bsb)_snowman, _snowman, var3x.b(-3 + _snowmanxx, 0, -3 + _snowmanxxx));
            }
         }
      });
   }

   private void a(bsb var1, Random var2, fx var3) {
      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            if (Math.abs(_snowman) != 2 || Math.abs(_snowmanx) != 2) {
               this.b(_snowman, _snowman, _snowman.b(_snowman, 0, _snowmanx));
            }
         }
      }
   }

   private void b(bsb var1, Random var2, fx var3) {
      for (int _snowman = 2; _snowman >= -3; _snowman--) {
         fx _snowmanx = _snowman.b(_snowman);
         if (cjl.a(_snowman, _snowmanx)) {
            _snowman.a(_snowmanx, this.b.a(_snowman, _snowman), 19);
            break;
         }

         if (!cjl.b(_snowman, _snowmanx) && _snowman < 0) {
            break;
         }
      }
   }
}
