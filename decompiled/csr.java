import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class csr extends csu {
   public static final Codec<csr> a = RecordCodecBuilder.create(
      var0 -> var0.group(gm.Q.fieldOf("block").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.d))
            .apply(var0, csr::new)
   );
   private final buo b;
   private final float d;

   public csr(buo var1, float var2) {
      this.b = _snowman;
      this.d = _snowman;
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return _snowman.a(this.b) && _snowman.nextFloat() < this.d;
   }

   @Override
   protected csv<?> a() {
      return csv.e;
   }
}
