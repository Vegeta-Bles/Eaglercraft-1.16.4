import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class csb extends cso {
   public static final Codec<csb> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.b),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.e),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.f),
               gc.a.d.fieldOf("axis").orElse(gc.a.b).forGetter(var0x -> var0x.g)
            )
            .apply(var0, csb::new)
   );
   private final float b;
   private final float d;
   private final int e;
   private final int f;
   private final gc.a g;

   public csb(float var1, float var2, int var3, int var4, gc.a var5) {
      if (_snowman >= _snowman) {
         throw new IllegalArgumentException("Invalid range: [" + _snowman + "," + _snowman + "]");
      } else {
         this.b = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }
   }

   @Override
   public boolean a(fx var1, fx var2, fx var3, Random var4) {
      gc _snowman = gc.a(gc.b.a, this.g);
      float _snowmanx = (float)Math.abs((_snowman.u() - _snowman.u()) * _snowman.i());
      float _snowmanxx = (float)Math.abs((_snowman.v() - _snowman.v()) * _snowman.j());
      float _snowmanxxx = (float)Math.abs((_snowman.w() - _snowman.w()) * _snowman.k());
      int _snowmanxxxx = (int)(_snowmanx + _snowmanxx + _snowmanxxx);
      float _snowmanxxxxx = _snowman.nextFloat();
      return (double)_snowmanxxxxx <= afm.b((double)this.b, (double)this.d, afm.c((double)_snowmanxxxx, (double)this.e, (double)this.f));
   }

   @Override
   protected csp<?> a() {
      return csp.c;
   }
}
