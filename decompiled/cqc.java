import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public abstract class cqc<DC extends clw> {
   public static final cqc<cmg> a = a("nope", new cqm(cmg.a));
   public static final cqc<cpn> b = a("chance", new cpm(cpn.a));
   public static final cqc<clu> c = a("count", new cpp(clu.a));
   public static final cqc<cmf> d = a("count_noise", new cpq(cmf.a));
   public static final cqc<cql> e = a("count_noise_biased", new cqk(cql.a));
   public static final cqc<cqd> f = a("count_extra", new cpr(cqd.a));
   public static final cqc<cmg> g = a("square", new cqq(cmg.a));
   public static final cqc<cmg> h = a("heightmap", new cqf<>(cmg.a));
   public static final cqc<cmg> i = a("heightmap_spread_double", new cqg<>(cmg.a));
   public static final cqc<cmg> j = a("top_solid_heightmap", new cqr(cmg.a));
   public static final cqc<cmg> k = a("heightmap_world_surface", new cqe(cmg.a));
   public static final cqc<cmo> l = a("range", new cqn(cmo.a));
   public static final cqc<cmo> m = a("range_biased", new cpj(cmo.a));
   public static final cqc<cmo> n = a("range_very_biased", new cqs(cmo.a));
   public static final cqc<cpw> o = a("depth_average", new cpx(cpw.a));
   public static final cqc<cmg> p = a("spread_32_above", new cqp(cmg.a));
   public static final cqc<cpl> q = a("carving_mask", new cpk(cpl.a));
   public static final cqc<clu> r = a("fire", new cqu(clu.a));
   public static final cqc<cmg> s = a("magma", new cqw(cmg.a));
   public static final cqc<cmg> t = a("emerald_ore", new cpz(cmg.a));
   public static final cqc<cpn> u = a("lava_lake", new cqi(cpn.a));
   public static final cqc<cpn> v = a("water_lake", new cqj(cpn.a));
   public static final cqc<clu> w = a("glowstone", new cqv(clu.a));
   public static final cqc<cmg> x = a("end_gateway", new cqa(cmg.a));
   public static final cqc<cmg> y = a("dark_oak_tree", new cps(cmg.a));
   public static final cqc<cmg> z = a("iceberg", new cqh(cmg.a));
   public static final cqc<cmg> A = a("end_island", new cqb(cmg.a));
   public static final cqc<cpu> B = a("decorated", new cpt(cpu.a));
   public static final cqc<clu> C = a("count_multilayer", new cqt(clu.a));
   private final Codec<cpo<DC>> D;

   private static <T extends clw, G extends cqc<T>> G a(String var0, G var1) {
      return gm.a(gm.aK, _snowman, _snowman);
   }

   public cqc(Codec<DC> var1) {
      this.D = _snowman.fieldOf("config").xmap(var1x -> new cpo<>(this, (DC)var1x), cpo::b).codec();
   }

   public cpo<DC> b(DC var1) {
      return new cpo<>(this, _snowman);
   }

   public Codec<cpo<DC>> a() {
      return this.D;
   }

   public abstract Stream<fx> a(cpv var1, Random var2, DC var3, fx var4);

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}
