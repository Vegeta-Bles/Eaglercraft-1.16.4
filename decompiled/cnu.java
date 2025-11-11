import com.mojang.serialization.Codec;

public class cnu<P extends cnt> {
   public static final cnu<cny> a = a("simple_state_provider", cny.b);
   public static final cnu<cnz> b = a("weighted_state_provider", cnz.b);
   public static final cnu<cnw> c = a("plain_flower_provider", cnw.b);
   public static final cnu<cnv> d = a("forest_flower_provider", cnv.b);
   public static final cnu<cnx> e = a("rotated_block_provider", cnx.b);
   private final Codec<P> f;

   private static <P extends cnt> cnu<P> a(String var0, Codec<P> var1) {
      return gm.a(gm.aV, _snowman, new cnu<>(_snowman));
   }

   private cnu(Codec<P> var1) {
      this.f = _snowman;
   }

   public Codec<P> a() {
      return this.f;
   }
}
