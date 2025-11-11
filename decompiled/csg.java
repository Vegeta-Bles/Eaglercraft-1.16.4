import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class csg extends csy {
   public static final Codec<csg> a = Codec.FLOAT.fieldOf("integrity").orElse(1.0F).xmap(csg::new, var0 -> var0.b).codec();
   private final float b;

   public csg(float var1) {
      this.b = _snowman;
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      Random _snowman = _snowman.b(_snowman.a);
      return !(this.b >= 1.0F) && !(_snowman.nextFloat() <= this.b) ? null : _snowman;
   }

   @Override
   protected cta<?> a() {
      return cta.b;
   }
}
