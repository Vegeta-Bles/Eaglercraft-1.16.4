import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cln extends cll {
   public static final Codec<cln> b = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("min_size").forGetter(var0x -> var0x.c), Codec.INT.fieldOf("extra_size").forGetter(var0x -> var0x.d))
            .apply(var0, cln::new)
   );
   private final int c;
   private final int d;

   public cln(int var1, int var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   protected clm<?> a() {
      return clm.c;
   }

   @Override
   public void a(bry var1, fx var2, ceh var3, Random var4) {
      fx.a _snowman = _snowman.i();
      int _snowmanx = this.c + _snowman.nextInt(_snowman.nextInt(this.d + 1) + 1);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         _snowman.a(_snowman, _snowman, 2);
         _snowman.c(gc.b);
      }
   }
}
