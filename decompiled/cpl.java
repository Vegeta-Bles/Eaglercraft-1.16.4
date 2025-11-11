import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cpl implements clw {
   public static final Codec<cpl> a = RecordCodecBuilder.create(
      var0 -> var0.group(chm.a.c.fieldOf("step").forGetter(var0x -> var0x.c), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.d))
            .apply(var0, cpl::new)
   );
   protected final chm.a c;
   protected final float d;

   public cpl(chm.a var1, float var2) {
      this.c = _snowman;
      this.d = _snowman;
   }
}
