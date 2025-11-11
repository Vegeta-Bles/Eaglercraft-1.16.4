import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class brk {
   public static final brk a = new brk(ImmutableList.of("vanilla"), ImmutableList.of());
   public static final Codec<brk> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.listOf().fieldOf("Enabled").forGetter(var0x -> var0x.c), Codec.STRING.listOf().fieldOf("Disabled").forGetter(var0x -> var0x.d)
            )
            .apply(var0, brk::new)
   );
   private final List<String> c;
   private final List<String> d;

   public brk(List<String> var1, List<String> var2) {
      this.c = ImmutableList.copyOf(_snowman);
      this.d = ImmutableList.copyOf(_snowman);
   }

   public List<String> a() {
      return this.c;
   }

   public List<String> b() {
      return this.d;
   }
}
