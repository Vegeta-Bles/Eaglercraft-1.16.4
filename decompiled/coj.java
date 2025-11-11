import com.mojang.serialization.Codec;

public interface coj<P extends coi> {
   coj<coh> a = a("single_pool_element", coh.b);
   coj<cog> b = a("list_pool_element", cog.a);
   coj<coc> c = a("feature_pool_element", coc.a);
   coj<cob> d = a("empty_pool_element", cob.a);
   coj<cof> e = a("legacy_single_pool_element", cof.a);

   Codec<P> codec();

   static <P extends coi> coj<P> a(String var0, Codec<P> var1) {
      return gm.a(gm.be, _snowman, () -> _snowman);
   }
}
