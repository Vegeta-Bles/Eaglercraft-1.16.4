import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class csl extends cso {
   public static final Codec<csl> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.b),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.e),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.f)
            )
            .apply(var0, csl::new)
   );
   private final float b;
   private final float d;
   private final int e;
   private final int f;

   public csl(float var1, float var2, int var3, int var4) {
      if (_snowman >= _snowman) {
         throw new IllegalArgumentException("Invalid range: [" + _snowman + "," + _snowman + "]");
      } else {
         this.b = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }
   }

   @Override
   public boolean a(fx var1, fx var2, fx var3, Random var4) {
      int _snowman = _snowman.k(_snowman);
      float _snowmanx = _snowman.nextFloat();
      return (double)_snowmanx <= afm.b((double)this.b, (double)this.d, afm.c((double)_snowman, (double)this.e, (double)this.f));
   }

   @Override
   protected csp<?> a() {
      return csp.b;
   }
}
