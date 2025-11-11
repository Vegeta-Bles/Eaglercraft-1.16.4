import com.mojang.serialization.Codec;
import java.util.Random;

public class csf extends csu {
   public static final Codec<csf> a = gm.Q.fieldOf("block").xmap(csf::new, var0 -> var0.b).codec();
   private final buo b;

   public csf(buo var1) {
      this.b = _snowman;
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return _snowman.a(this.b);
   }

   @Override
   protected csv<?> a() {
      return csv.b;
   }
}
