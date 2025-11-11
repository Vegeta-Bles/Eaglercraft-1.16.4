import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;

public class btc extends bsy {
   public static final Codec<btc> e = RecordCodecBuilder.create(
      var0 -> var0.group(bsv.e.fieldOf("biomes").forGetter(var0x -> var0x.f), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter(var0x -> var0x.h))
            .apply(var0, btc::new)
   );
   private final List<Supplier<bsv>> f;
   private final int g;
   private final int h;

   public btc(List<Supplier<bsv>> var1, int var2) {
      super(_snowman.stream());
      this.f = _snowman;
      this.g = _snowman + 2;
      this.h = _snowman;
   }

   @Override
   protected Codec<? extends bsy> a() {
      return e;
   }

   @Override
   public bsy a(long var1) {
      return this;
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      return this.f.get(Math.floorMod((_snowman >> this.g) + (_snowman >> this.g), this.f.size())).get();
   }
}
