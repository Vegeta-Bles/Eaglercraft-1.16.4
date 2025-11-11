import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cll {
   public static final Codec<cll> a = gm.aW.dispatch(cll::a, clm::a);

   public cll() {
   }

   public abstract void a(bry var1, fx var2, ceh var3, Random var4);

   protected abstract clm<?> a();
}
