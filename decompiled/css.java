import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class css extends csu {
   public static final Codec<css> a = RecordCodecBuilder.create(
      var0 -> var0.group(ceh.b.fieldOf("block_state").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.d))
            .apply(var0, css::new)
   );
   private final ceh b;
   private final float d;

   public css(ceh var1, float var2) {
      this.b = _snowman;
      this.d = _snowman;
   }

   @Override
   public boolean a(ceh var1, Random var2) {
      return _snowman == this.b && _snowman.nextFloat() < this.d;
   }

   @Override
   protected csv<?> a() {
      return csv.f;
   }
}
