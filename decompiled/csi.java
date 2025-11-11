import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;

public class csi extends csy {
   public static final Codec<csi> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               chn.a.g.fieldOf("heightmap").orElse(chn.a.a).forGetter(var0x -> var0x.b), Codec.INT.fieldOf("offset").orElse(0).forGetter(var0x -> var0x.c)
            )
            .apply(var0, csi::new)
   );
   private final chn.a b;
   private final int c;

   public csi(chn.a var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      chn.a _snowman;
      if (_snowman instanceof aag) {
         if (this.b == chn.a.a) {
            _snowman = chn.a.b;
         } else if (this.b == chn.a.c) {
            _snowman = chn.a.d;
         } else {
            _snowman = this.b;
         }
      } else {
         _snowman = this.b;
      }

      int _snowmanx = _snowman.a(_snowman, _snowman.a.u(), _snowman.a.w()) + this.c;
      int _snowmanxx = _snowman.a.v();
      return new ctb.c(new fx(_snowman.a.u(), _snowmanx + _snowmanxx, _snowman.a.w()), _snowman.b, _snowman.c);
   }

   @Override
   protected cta<?> a() {
      return cta.c;
   }
}
