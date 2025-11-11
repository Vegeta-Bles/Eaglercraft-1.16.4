import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;

public class csj extends csy {
   public static final Codec<csj> a = Codec.unit(() -> csj.b);
   public static final csj b = new csj();

   private csj() {
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      ceh _snowman = _snowman.b;
      if (_snowman.a(bup.mZ)) {
         String _snowmanx = _snowman.c.l("final_state");
         ei _snowmanxx = new ei(new StringReader(_snowmanx), false);

         try {
            _snowmanxx.a(true);
         } catch (CommandSyntaxException var11) {
            throw new RuntimeException(var11);
         }

         return _snowmanxx.b().a(bup.iN) ? null : new ctb.c(_snowman.a, _snowmanxx.b(), null);
      } else {
         return _snowman;
      }
   }

   @Override
   protected cta<?> a() {
      return cta.d;
   }
}
