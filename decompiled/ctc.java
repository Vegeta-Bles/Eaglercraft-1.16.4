import com.mojang.serialization.Codec;
import java.util.Random;

public class ctc extends csu {
   public static final Codec<ctc> a = ael.a(() -> aeh.a().a()).fieldOf("tag").xmap(ctc::new, var0 -> var0.b).codec();
   private final ael<buo> b;

   public ctc(ael<buo> var1) {
      this.b = _snowman;
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return _snowman.a(this.b);
   }

   @Override
   protected csv<?> a() {
      return csv.d;
   }
}
