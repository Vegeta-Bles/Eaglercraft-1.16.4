import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cjl<FC extends cma> {
   public static final cjl<cmh> b = a("no_op", new ckf(cmh.a));
   public static final cjl<cmz> c = a("tree", new cld(cmz.a));
   public static final cii<cmn> d = a("flower", new cjc(cmn.a));
   public static final cii<cmn> e = a("no_bonemeal_flower", new cjc(cmn.a));
   public static final cjl<cmn> f = a("random_patch", new ckl(cmn.a));
   public static final cjl<clr> g = a("block_pile", new ciq(clr.a));
   public static final cjl<cmw> h = a("spring_feature", new cky(cmw.a));
   public static final cjl<cmh> i = a("chorus_plant", new ciu(cmh.a));
   public static final cjl<cmp> j = a("emerald_ore", new cko(cmp.a));
   public static final cjl<cmh> k = a("void_start_platform", new clh(cmh.a));
   public static final cjl<cmh> l = a("desert_well", new cjf(cmh.a));
   public static final cjl<cmh> m = a("fossil", new cjn(cmh.a));
   public static final cjl<cmb> n = a("huge_red_mushroom", new cjs(cmb.a));
   public static final cjl<cmb> o = a("huge_brown_mushroom", new cjp(cmb.a));
   public static final cjl<cmh> p = a("ice_spike", new cju(cmh.a));
   public static final cjl<cmh> q = a("glowstone_blob", new cjo(cmh.a));
   public static final cjl<cmh> r = a("freeze_top_layer", new ckw(cmh.a));
   public static final cjl<cmh> s = a("vines", new clg(cmh.a));
   public static final cjl<cmh> t = a("monster_room", new ckc(cmh.a));
   public static final cjl<cmh> u = a("blue_ice", new cir(cmh.a));
   public static final cjl<cls> v = a("iceberg", new cjv(cls.a));
   public static final cjl<cls> w = a("forest_rock", new cip(cls.a));
   public static final cjl<cly> x = a("disk", new cjg(cly.a));
   public static final cjl<cly> y = a("ice_patch", new cjt(cly.a));
   public static final cjl<cls> z = a("lake", new cka(cls.a));
   public static final cjl<cmj> A = a("ore", new cki(cmj.a));
   public static final cjl<cmv> B = a("end_spike", new ckx(cmv.a));
   public static final cjl<cmh> C = a("end_island", new cjj(cmh.a));
   public static final cjl<clz> D = a("end_gateway", new cji(clz.a));
   public static final ckr E = a("seagrass", new ckr(cmk.b));
   public static final cjl<cmh> F = a("kelp", new cjz(cmh.a));
   public static final cjl<cmh> G = a("coral_tree", new cja(cmh.a));
   public static final cjl<cmh> H = a("coral_mushroom", new ciz(cmh.a));
   public static final cjl<cmh> I = a("coral_claw", new cix(cmh.a));
   public static final cjl<clu> J = a("sea_pickle", new ckq(clu.a));
   public static final cjl<cmt> K = a("simple_block", new ckt(cmt.a));
   public static final cjl<cmk> L = a("bamboo", new cik(cmk.b));
   public static final cjl<cjq> M = a("huge_fungus", new cjr(cjq.a));
   public static final cjl<clr> N = a("nether_forest_vegetation", new ckd(clr.a));
   public static final cjl<cmh> O = a("weeping_vines", new cli(cmh.a));
   public static final cjl<cmh> P = a("twisting_vines", new cle(cmh.a));
   public static final cjl<clt> Q = a("basalt_columns", new cil(clt.a));
   public static final cjl<clx> R = a("delta_feature", new cjd(clx.a));
   public static final cjl<cmq> S = a("netherrack_replace_blobs", new ckn(cmq.a));
   public static final cjl<cmd> T = a("fill_layer", new cjm(cmd.a));
   public static final cis U = a("bonus_chest", new cis(cmh.a));
   public static final cjl<cmh> V = a("basalt_pillar", new cim(cmh.a));
   public static final cjl<cmj> W = a("no_surface_ore", new ckg(cmj.a));
   public static final cjl<cmm> X = a("random_selector", new ckm(cmm.a));
   public static final cjl<cmu> Y = a("simple_random_selector", new cku(cmu.a));
   public static final cjl<cml> Z = a("random_boolean_selector", new ckk(cml.a));
   public static final cjl<clv> aa = a("decorated", new cjb(clv.a));
   private final Codec<civ<FC, cjl<FC>>> a;

   private static <C extends cma, F extends cjl<C>> F a(String var0, F var1) {
      return gm.a(gm.aE, _snowman, _snowman);
   }

   public cjl(Codec<FC> var1) {
      this.a = _snowman.fieldOf("config").xmap(var1x -> new civ<>(this, var1x), var0 -> var0.f).codec();
   }

   public Codec<civ<FC, cjl<FC>>> a() {
      return this.a;
   }

   public civ<FC, ?> b(FC var1) {
      return new civ<>(this, _snowman);
   }

   protected void a(bse var1, fx var2, ceh var3) {
      _snowman.a(_snowman, _snowman, 3);
   }

   public abstract boolean a(bsr var1, cfy var2, Random var3, fx var4, FC var5);

   protected static boolean a(buo var0) {
      return _snowman == bup.b || _snowman == bup.c || _snowman == bup.e || _snowman == bup.g;
   }

   public static boolean b(buo var0) {
      return _snowman == bup.j || _snowman == bup.i || _snowman == bup.l || _snowman == bup.k || _snowman == bup.dT;
   }

   public static boolean a(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> b(var0x.b()));
   }

   public static boolean b(bsc var0, fx var1) {
      return _snowman.a(_snowman, ceg.a::g);
   }
}
