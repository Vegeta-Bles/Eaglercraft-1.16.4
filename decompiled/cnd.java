import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class cnd extends cnb {
   public static final Codec<cnd> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 80).fieldOf("limit").orElse(1).forGetter(var0x -> var0x.d),
               Codec.intRange(0, 80).fieldOf("upper_limit").orElse(1).forGetter(var0x -> var0x.e),
               Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter(var0x -> var0x.f),
               Codec.intRange(0, 16).fieldOf("middle_size").orElse(1).forGetter(var0x -> var0x.g),
               Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter(var0x -> var0x.h),
               a()
            )
            .apply(var0, cnd::new)
   );
   private final int d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;

   public cnd(int var1, int var2, int var3, int var4, int var5, OptionalInt var6) {
      super(_snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   protected cnc<?> b() {
      return cnc.b;
   }

   @Override
   public int a(int var1, int var2) {
      if (_snowman < this.d) {
         return this.f;
      } else {
         return _snowman >= _snowman - this.e ? this.h : this.g;
      }
   }
}
