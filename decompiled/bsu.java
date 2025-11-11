import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class bsu {
   public static final Codec<bsu> a = RecordCodecBuilder.create(
      var0 -> var0.group(hh.au.fieldOf("options").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.c))
            .apply(var0, bsu::new)
   );
   private final hf b;
   private final float c;

   public bsu(hf var1, float var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public hf a() {
      return this.b;
   }

   public boolean a(Random var1) {
      return _snowman.nextFloat() <= this.c;
   }
}
