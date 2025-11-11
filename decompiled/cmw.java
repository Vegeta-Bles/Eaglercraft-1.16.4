import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;

public class cmw implements cma {
   public static final Codec<cmw> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               cux.a.fieldOf("state").forGetter(var0x -> var0x.b),
               Codec.BOOL.fieldOf("requires_block_below").orElse(true).forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("rock_count").orElse(4).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("hole_count").orElse(1).forGetter(var0x -> var0x.e),
               gm.Q.listOf().fieldOf("valid_blocks").xmap(ImmutableSet::copyOf, ImmutableList::copyOf).forGetter(var0x -> var0x.f)
            )
            .apply(var0, cmw::new)
   );
   public final cux b;
   public final boolean c;
   public final int d;
   public final int e;
   public final Set<buo> f;

   public cmw(cux var1, boolean var2, int var3, int var4, Set<buo> var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }
}
