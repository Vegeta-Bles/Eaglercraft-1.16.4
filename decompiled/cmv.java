import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class cmv implements cma {
   public static final Codec<cmv> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.BOOL.fieldOf("crystal_invulnerable").orElse(false).forGetter(var0x -> var0x.b),
               ckx.a.a.listOf().fieldOf("spikes").forGetter(var0x -> var0x.c),
               fx.a.optionalFieldOf("crystal_beam_target").forGetter(var0x -> Optional.ofNullable(var0x.d))
            )
            .apply(var0, cmv::new)
   );
   private final boolean b;
   private final List<ckx.a> c;
   @Nullable
   private final fx d;

   public cmv(boolean var1, List<ckx.a> var2, @Nullable fx var3) {
      this(_snowman, _snowman, Optional.ofNullable(_snowman));
   }

   private cmv(boolean var1, List<ckx.a> var2, Optional<fx> var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman.orElse(null);
   }

   public boolean b() {
      return this.b;
   }

   public List<ckx.a> c() {
      return this.c;
   }

   @Nullable
   public fx d() {
      return this.d;
   }
}
