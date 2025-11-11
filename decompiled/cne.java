import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class cne extends cnb {
   public static final Codec<cne> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 81).fieldOf("limit").orElse(1).forGetter(var0x -> var0x.d),
               Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter(var0x -> var0x.e),
               Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter(var0x -> var0x.f),
               a()
            )
            .apply(var0, cne::new)
   );
   private final int d;
   private final int e;
   private final int f;

   public cne(int var1, int var2, int var3) {
      this(_snowman, _snowman, _snowman, OptionalInt.empty());
   }

   public cne(int var1, int var2, int var3, OptionalInt var4) {
      super(_snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   protected cnc<?> b() {
      return cnc.a;
   }

   @Override
   public int a(int var1, int var2) {
      return _snowman < this.d ? this.e : this.f;
   }
}
