import com.mojang.serialization.Codec;
import javax.annotation.Nullable;

public class csk extends csy {
   public static final Codec<csk> a = Codec.unit(() -> csk.b);
   public static final csk b = new csk();

   public csk() {
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      fx _snowman = _snowman.a;
      boolean _snowmanx = _snowman.d_(_snowman).a(bup.B);
      return _snowmanx && !buo.a(_snowman.b.j(_snowman, _snowman)) ? new ctb.c(_snowman, bup.B.n(), _snowman.c) : _snowman;
   }

   @Override
   protected cta<?> a() {
      return cta.i;
   }
}
