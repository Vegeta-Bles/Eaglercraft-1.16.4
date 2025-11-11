import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public interface cta<P extends csy> {
   cta<cse> a = a("block_ignore", cse.a);
   cta<csg> b = a("block_rot", csg.a);
   cta<csi> c = a("gravity", csi.a);
   cta<csj> d = a("jigsaw_replacement", csj.a);
   cta<cst> e = a("rule", cst.a);
   cta<csm> f = a("nop", csm.a);
   cta<csd> g = a("block_age", csd.a);
   cta<csc> h = a("blackstone_replace", csc.a);
   cta<csk> i = a("lava_submerged_block", csk.a);
   Codec<csy> j = gm.bd.dispatch("processor_type", csy::a, cta::codec);
   Codec<csz> k = j.listOf().xmap(csz::new, csz::a);
   Codec<csz> l = Codec.either(k.fieldOf("processors").codec(), k).xmap(var0 -> (csz)var0.map(var0x -> var0x, var0x -> var0x), Either::left);
   Codec<Supplier<csz>> m = vf.a(gm.aw, l);

   Codec<P> codec();

   static <P extends csy> cta<P> a(String var0, Codec<P> var1) {
      return gm.a(gm.bd, _snowman, () -> _snowman);
   }
}
