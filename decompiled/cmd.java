import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmd implements cma {
   public static final Codec<cmd> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.intRange(0, 255).fieldOf("height").forGetter(var0x -> var0x.b), ceh.b.fieldOf("state").forGetter(var0x -> var0x.c))
            .apply(var0, cmd::new)
   );
   public final int b;
   public final ceh c;

   public cmd(int var1, ceh var2) {
      this.b = _snowman;
      this.c = _snowman;
   }
}
