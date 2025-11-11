import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;

public class hd implements hf {
   public static final hd a = new hd(1.0F, 0.0F, 0.0F, 1.0F);
   public static final Codec<hd> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("r").forGetter(var0x -> var0x.d),
               Codec.FLOAT.fieldOf("g").forGetter(var0x -> var0x.e),
               Codec.FLOAT.fieldOf("b").forGetter(var0x -> var0x.f),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.g)
            )
            .apply(var0, hd::new)
   );
   public static final hf.a<hd> c = new hf.a<hd>() {
      public hd a(hg<hd> var1, StringReader var2) throws CommandSyntaxException {
         _snowman.expect(' ');
         float _snowman = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanx = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanxx = (float)_snowman.readDouble();
         _snowman.expect(' ');
         float _snowmanxxx = (float)_snowman.readDouble();
         return new hd(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
      }

      public hd a(hg<hd> var1, nf var2) {
         return new hd(_snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat());
      }
   };
   private final float d;
   private final float e;
   private final float f;
   private final float g;

   public hd(float var1, float var2, float var3, float var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = afm.a(_snowman, 0.01F, 4.0F);
   }

   @Override
   public void a(nf var1) {
      _snowman.writeFloat(this.d);
      _snowman.writeFloat(this.e);
      _snowman.writeFloat(this.f);
      _snowman.writeFloat(this.g);
   }

   @Override
   public String a() {
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", gm.V.b(this.b()), this.d, this.e, this.f, this.g);
   }

   @Override
   public hg<hd> b() {
      return hh.o;
   }

   public float c() {
      return this.d;
   }

   public float d() {
      return this.e;
   }

   public float e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }
}
