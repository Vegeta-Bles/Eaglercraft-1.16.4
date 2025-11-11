import com.mojang.serialization.Codec;

public interface csp<P extends cso> {
   csp<csn> a = a("always_true", csn.a);
   csp<csl> b = a("linear_pos", csl.a);
   csp<csb> c = a("axis_aligned_linear_pos", csb.a);

   Codec<P> codec();

   static <P extends cso> csp<P> a(String var0, Codec<P> var1) {
      return gm.a(gm.ab, _snowman, () -> _snowman);
   }
}
