import com.mojang.serialization.Codec;

public class cms implements cma {
   public static final Codec<cms> a = Codec.BOOL.fieldOf("is_beached").orElse(false).xmap(cms::new, var0 -> var0.b).codec();
   public final boolean b;

   public cms(boolean var1) {
      this.b = _snowman;
   }
}
