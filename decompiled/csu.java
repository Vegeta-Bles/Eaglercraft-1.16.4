import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class csu {
   public static final Codec<csu> c = gm.aa.dispatch("predicate_type", csu::a, csv::codec);

   public csu() {
   }

   public abstract boolean a(ceh var1, Random var2);

   protected abstract csv<?> a();
}
