import com.mojang.serialization.Codec;
import javax.annotation.Nullable;

public class csm extends csy {
   public static final Codec<csm> a = Codec.unit(() -> csm.b);
   public static final csm b = new csm();

   private csm() {
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      return _snowman;
   }

   @Override
   protected cta<?> a() {
      return cta.f;
   }
}
