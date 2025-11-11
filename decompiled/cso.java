import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cso {
   public static final Codec<cso> c = gm.ab.dispatch("predicate_type", cso::a, csp::codec);

   public cso() {
   }

   public abstract boolean a(fx var1, fx var2, fx var3, Random var4);

   protected abstract csp<?> a();
}
