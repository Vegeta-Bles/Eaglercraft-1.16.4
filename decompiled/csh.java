import com.mojang.serialization.Codec;
import java.util.Random;

public class csh extends csu {
   public static final Codec<csh> a = ceh.b.fieldOf("block_state").xmap(csh::new, var0 -> var0.b).codec();
   private final ceh b;

   public csh(ceh var1) {
      this.b = _snowman;
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return _snowman == this.b;
   }

   @Override
   protected csv<?> a() {
      return csv.c;
   }
}
