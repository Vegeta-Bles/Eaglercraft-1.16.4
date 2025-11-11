import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cop extends cor {
   public static final Codec<cop> a = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(cop::new, var0 -> var0.b).codec();
   private final float b;

   public cop(float var1) {
      this.b = _snowman;
   }

   @Override
   protected cos<?> a() {
      return cos.c;
   }

   @Override
   public void a(bsr var1, Random var2, List<fx> var3, List<fx> var4, Set<fx> var5, cra var6) {
      if (!(_snowman.nextFloat() >= this.b)) {
         int _snowman = _snowman.get(0).v();
         _snowman.stream().filter(var1x -> var1x.v() - _snowman <= 2).forEach(var5x -> {
            for (gc _snowmanx : gc.c.a) {
               if (_snowman.nextFloat() <= 0.25F) {
                  gc _snowmanx = _snowmanx.f();
                  fx _snowmanxx = var5x.b(_snowmanx.i(), 0, _snowmanx.k());
                  if (cjl.b(_snowman, _snowmanxx)) {
                     ceh _snowmanxxx = bup.eh.n().a(bvh.a, Integer.valueOf(_snowman.nextInt(3))).a(bvh.aq, _snowmanx);
                     this.a(_snowman, _snowmanxx, _snowmanxxx, _snowman, _snowman);
                  }
               }
            }
         });
      }
   }
}
