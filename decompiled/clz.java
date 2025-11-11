import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class clz implements cma {
   public static final Codec<clz> a = RecordCodecBuilder.create(
      var0 -> var0.group(fx.a.optionalFieldOf("exit").forGetter(var0x -> var0x.b), Codec.BOOL.fieldOf("exact").forGetter(var0x -> var0x.c))
            .apply(var0, clz::new)
   );
   private final Optional<fx> b;
   private final boolean c;

   private clz(Optional<fx> var1, boolean var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public static clz a(fx var0, boolean var1) {
      return new clz(Optional.of(_snowman), _snowman);
   }

   public static clz b() {
      return new clz(Optional.empty(), false);
   }

   public Optional<fx> c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
