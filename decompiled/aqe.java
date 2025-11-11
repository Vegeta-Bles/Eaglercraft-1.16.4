import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aqe<T extends aqa> {
   private static final Logger be = LogManager.getLogger();
   public static final aqe<apz> a = a("area_effect_cloud", aqe.a.<apz>a(apz::new, aqo.f).c().a(6.0F, 0.5F).a(10).b(Integer.MAX_VALUE));
   public static final aqe<bcn> b = a("armor_stand", aqe.a.<bcn>a(bcn::new, aqo.f).a(0.5F, 1.975F).a(10));
   public static final aqe<bgc> c = a("arrow", aqe.a.<bgc>a(bgc::new, aqo.f).a(0.5F, 0.5F).a(4).b(20));
   public static final aqe<azu> d = a("bat", aqe.a.a(azu::new, aqo.c).a(0.5F, 0.9F).a(5));
   public static final aqe<baa> e = a("bee", aqe.a.a(baa::new, aqo.b).a(0.7F, 0.6F).a(8));
   public static final aqe<bda> f = a("blaze", aqe.a.a(bda::new, aqo.a).c().a(0.6F, 1.8F).a(8));
   public static final aqe<bhn> g = a("boat", aqe.a.<bhn>a(bhn::new, aqo.f).a(1.375F, 0.5625F).a(10));
   public static final aqe<bab> h = a("cat", aqe.a.a(bab::new, aqo.b).a(0.6F, 0.7F).a(8));
   public static final aqe<bdb> i = a("cave_spider", aqe.a.a(bdb::new, aqo.a).a(0.7F, 0.5F).a(8));
   public static final aqe<bac> j = a("chicken", aqe.a.a(bac::new, aqo.b).a(0.4F, 0.7F).a(10));
   public static final aqe<bad> k = a("cod", aqe.a.a(bad::new, aqo.e).a(0.5F, 0.3F).a(4));
   public static final aqe<bae> l = a("cow", aqe.a.a(bae::new, aqo.b).a(0.9F, 1.4F).a(10));
   public static final aqe<bdc> m = a("creeper", aqe.a.a(bdc::new, aqo.a).a(0.6F, 1.7F).a(8));
   public static final aqe<baf> n = a("dolphin", aqe.a.a(baf::new, aqo.d).a(0.9F, 0.6F));
   public static final aqe<bbc> o = a("donkey", aqe.a.a(bbc::new, aqo.b).a(1.3964844F, 1.5F).a(10));
   public static final aqe<bgd> p = a("dragon_fireball", aqe.a.<bgd>a(bgd::new, aqo.f).a(1.0F, 1.0F).a(4).b(10));
   public static final aqe<bde> q = a("drowned", aqe.a.a(bde::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bdf> r = a("elder_guardian", aqe.a.a(bdf::new, aqo.a).a(1.9975F, 1.9975F).a(10));
   public static final aqe<bbq> s = a("end_crystal", aqe.a.<bbq>a(bbq::new, aqo.f).a(2.0F, 2.0F).a(16).b(Integer.MAX_VALUE));
   public static final aqe<bbr> t = a("ender_dragon", aqe.a.a(bbr::new, aqo.a).c().a(16.0F, 8.0F).a(10));
   public static final aqe<bdg> u = a("enderman", aqe.a.a(bdg::new, aqo.a).a(0.6F, 2.9F).a(8));
   public static final aqe<bdh> v = a("endermite", aqe.a.a(bdh::new, aqo.a).a(0.4F, 0.3F).a(8));
   public static final aqe<bdj> w = a("evoker", aqe.a.a(bdj::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bge> x = a("evoker_fangs", aqe.a.<bge>a(bge::new, aqo.f).a(0.5F, 0.8F).a(6).b(2));
   public static final aqe<aqg> y = a("experience_orb", aqe.a.<aqg>a(aqg::new, aqo.f).a(0.5F, 0.5F).a(6).b(20));
   public static final aqe<bgf> z = a("eye_of_ender", aqe.a.<bgf>a(bgf::new, aqo.f).a(0.25F, 0.25F).a(4).b(4));
   public static final aqe<bcu> A = a("falling_block", aqe.a.<bcu>a(bcu::new, aqo.f).a(0.98F, 0.98F).a(10).b(20));
   public static final aqe<bgh> B = a("firework_rocket", aqe.a.<bgh>a(bgh::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bah> C = a("fox", aqe.a.a(bah::new, aqo.b).a(0.6F, 0.7F).a(8).a(bup.mg));
   public static final aqe<bdk> D = a("ghast", aqe.a.a(bdk::new, aqo.a).c().a(4.0F, 4.0F).a(10));
   public static final aqe<bdl> E = a("giant", aqe.a.a(bdl::new, aqo.a).a(3.6F, 12.0F).a(10));
   public static final aqe<bdm> F = a("guardian", aqe.a.a(bdm::new, aqo.a).a(0.85F, 0.85F).a(8));
   public static final aqe<bem> G = a("hoglin", aqe.a.a(bem::new, aqo.a).a(1.3964844F, 1.4F).a(8));
   public static final aqe<bbd> H = a("horse", aqe.a.a(bbd::new, aqo.b).a(1.3964844F, 1.6F).a(10));
   public static final aqe<bdn> I = a("husk", aqe.a.a(bdn::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bdo> J = a("illusioner", aqe.a.a(bdo::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bai> K = a("iron_golem", aqe.a.a(bai::new, aqo.f).a(1.4F, 2.7F).a(10));
   public static final aqe<bcv> L = a("item", aqe.a.<bcv>a(bcv::new, aqo.f).a(0.25F, 0.25F).a(6).b(20));
   public static final aqe<bcp> M = a("item_frame", aqe.a.<bcp>a(bcp::new, aqo.f).a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE));
   public static final aqe<bgk> N = a("fireball", aqe.a.<bgk>a(bgk::new, aqo.f).a(1.0F, 1.0F).a(4).b(10));
   public static final aqe<bcq> O = a("leash_knot", aqe.a.<bcq>a(bcq::new, aqo.f).b().a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE));
   public static final aqe<aql> P = a("lightning_bolt", aqe.a.a(aql::new, aqo.f).b().a(0.0F, 0.0F).a(16).b(Integer.MAX_VALUE));
   public static final aqe<bbe> Q = a("llama", aqe.a.a(bbe::new, aqo.b).a(0.9F, 1.87F).a(10));
   public static final aqe<bgl> R = a("llama_spit", aqe.a.<bgl>a(bgl::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bdp> S = a("magma_cube", aqe.a.a(bdp::new, aqo.a).c().a(2.04F, 2.04F).a(8));
   public static final aqe<bhp> T = a("minecart", aqe.a.<bhp>a(bhp::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bhq> U = a("chest_minecart", aqe.a.<bhq>a(bhq::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bhr> V = a("command_block_minecart", aqe.a.<bhr>a(bhr::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bhs> W = a("furnace_minecart", aqe.a.<bhs>a(bhs::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bht> X = a("hopper_minecart", aqe.a.<bht>a(bht::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bhu> Y = a("spawner_minecart", aqe.a.<bhu>a(bhu::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bhv> Z = a("tnt_minecart", aqe.a.<bhv>a(bhv::new, aqo.f).a(0.98F, 0.7F).a(8));
   public static final aqe<bbg> aa = a("mule", aqe.a.a(bbg::new, aqo.b).a(1.3964844F, 1.6F).a(8));
   public static final aqe<baj> ab = a("mooshroom", aqe.a.a(baj::new, aqo.b).a(0.9F, 1.4F).a(10));
   public static final aqe<bak> ac = a("ocelot", aqe.a.a(bak::new, aqo.b).a(0.6F, 0.7F).a(10));
   public static final aqe<bcs> ad = a("painting", aqe.a.<bcs>a(bcs::new, aqo.f).a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE));
   public static final aqe<bal> ae = a("panda", aqe.a.a(bal::new, aqo.b).a(1.3F, 1.25F).a(10));
   public static final aqe<bam> af = a("parrot", aqe.a.a(bam::new, aqo.b).a(0.5F, 0.9F).a(8));
   public static final aqe<bds> ag = a("phantom", aqe.a.a(bds::new, aqo.a).a(0.9F, 0.5F).a(8));
   public static final aqe<ban> ah = a("pig", aqe.a.a(ban::new, aqo.b).a(0.9F, 0.9F).a(10));
   public static final aqe<bes> ai = a("piglin", aqe.a.a(bes::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bev> aj = a("piglin_brute", aqe.a.a(bev::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bdt> ak = a("pillager", aqe.a.a(bdt::new, aqo.a).d().a(0.6F, 1.95F).a(8));
   public static final aqe<bao> al = a("polar_bear", aqe.a.a(bao::new, aqo.b).a(1.4F, 1.4F).a(10));
   public static final aqe<bcw> am = a("tnt", aqe.a.<bcw>a(bcw::new, aqo.f).c().a(0.98F, 0.98F).a(10).b(10));
   public static final aqe<bap> an = a("pufferfish", aqe.a.a(bap::new, aqo.e).a(0.7F, 0.7F).a(4));
   public static final aqe<baq> ao = a("rabbit", aqe.a.a(baq::new, aqo.b).a(0.4F, 0.5F).a(8));
   public static final aqe<bdv> ap = a("ravager", aqe.a.a(bdv::new, aqo.a).a(1.95F, 2.2F).a(10));
   public static final aqe<bar> aq = a("salmon", aqe.a.a(bar::new, aqo.e).a(0.7F, 0.4F).a(4));
   public static final aqe<bas> ar = a("sheep", aqe.a.a(bas::new, aqo.b).a(0.9F, 1.3F).a(10));
   public static final aqe<bdw> as = a("shulker", aqe.a.a(bdw::new, aqo.a).c().d().a(1.0F, 1.0F).a(10));
   public static final aqe<bgo> at = a("shulker_bullet", aqe.a.<bgo>a(bgo::new, aqo.f).a(0.3125F, 0.3125F).a(8));
   public static final aqe<bdx> au = a("silverfish", aqe.a.a(bdx::new, aqo.a).a(0.4F, 0.3F).a(8));
   public static final aqe<bdy> av = a("skeleton", aqe.a.a(bdy::new, aqo.a).a(0.6F, 1.99F).a(8));
   public static final aqe<bbh> aw = a("skeleton_horse", aqe.a.a(bbh::new, aqo.b).a(1.3964844F, 1.6F).a(10));
   public static final aqe<bdz> ax = a("slime", aqe.a.a(bdz::new, aqo.a).a(2.04F, 2.04F).a(10));
   public static final aqe<bgp> ay = a("small_fireball", aqe.a.<bgp>a(bgp::new, aqo.f).a(0.3125F, 0.3125F).a(4).b(10));
   public static final aqe<bau> az = a("snow_golem", aqe.a.a(bau::new, aqo.f).a(0.7F, 1.9F).a(8));
   public static final aqe<bgq> aA = a("snowball", aqe.a.<bgq>a(bgq::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bgr> aB = a("spectral_arrow", aqe.a.<bgr>a(bgr::new, aqo.f).a(0.5F, 0.5F).a(4).b(20));
   public static final aqe<beb> aC = a("spider", aqe.a.a(beb::new, aqo.a).a(1.4F, 0.9F).a(8));
   public static final aqe<bav> aD = a("squid", aqe.a.a(bav::new, aqo.d).a(0.8F, 0.8F).a(8));
   public static final aqe<bec> aE = a("stray", aqe.a.a(bec::new, aqo.a).a(0.6F, 1.99F).a(8));
   public static final aqe<bed> aF = a("strider", aqe.a.a(bed::new, aqo.b).c().a(0.9F, 1.7F).a(10));
   public static final aqe<bgu> aG = a("egg", aqe.a.<bgu>a(bgu::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bgv> aH = a("ender_pearl", aqe.a.<bgv>a(bgv::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bgw> aI = a("experience_bottle", aqe.a.<bgw>a(bgw::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bgx> aJ = a("potion", aqe.a.<bgx>a(bgx::new, aqo.f).a(0.25F, 0.25F).a(4).b(10));
   public static final aqe<bgy> aK = a("trident", aqe.a.<bgy>a(bgy::new, aqo.f).a(0.5F, 0.5F).a(4).b(20));
   public static final aqe<bbj> aL = a("trader_llama", aqe.a.a(bbj::new, aqo.b).a(0.9F, 1.87F).a(10));
   public static final aqe<baw> aM = a("tropical_fish", aqe.a.a(baw::new, aqo.e).a(0.5F, 0.4F).a(4));
   public static final aqe<bax> aN = a("turtle", aqe.a.a(bax::new, aqo.b).a(1.2F, 0.4F).a(10));
   public static final aqe<bee> aO = a("vex", aqe.a.a(bee::new, aqo.a).c().a(0.4F, 0.8F).a(8));
   public static final aqe<bfj> aP = a("villager", aqe.a.<bfj>a(bfj::new, aqo.f).a(0.6F, 1.95F).a(10));
   public static final aqe<bef> aQ = a("vindicator", aqe.a.a(bef::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bfp> aR = a("wandering_trader", aqe.a.a(bfp::new, aqo.b).a(0.6F, 1.95F).a(10));
   public static final aqe<beg> aS = a("witch", aqe.a.a(beg::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bcl> aT = a("wither", aqe.a.a(bcl::new, aqo.a).c().a(bup.bA).a(0.9F, 3.5F).a(10));
   public static final aqe<beh> aU = a("wither_skeleton", aqe.a.a(beh::new, aqo.a).c().a(bup.bA).a(0.7F, 2.4F).a(8));
   public static final aqe<bgz> aV = a("wither_skull", aqe.a.<bgz>a(bgz::new, aqo.f).a(0.3125F, 0.3125F).a(4).b(10));
   public static final aqe<baz> aW = a("wolf", aqe.a.a(baz::new, aqo.b).a(0.6F, 0.85F).a(10));
   public static final aqe<bei> aX = a("zoglin", aqe.a.a(bei::new, aqo.a).c().a(1.3964844F, 1.4F).a(8));
   public static final aqe<bej> aY = a("zombie", aqe.a.<bej>a(bej::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bbl> aZ = a("zombie_horse", aqe.a.a(bbl::new, aqo.b).a(1.3964844F, 1.6F).a(10));
   public static final aqe<bek> ba = a("zombie_villager", aqe.a.a(bek::new, aqo.a).a(0.6F, 1.95F).a(8));
   public static final aqe<bel> bb = a("zombified_piglin", aqe.a.a(bel::new, aqo.a).c().a(0.6F, 1.95F).a(8));
   public static final aqe<bfw> bc = a("player", aqe.a.<bfw>a(aqo.f).b().a().a(0.6F, 1.8F).a(32).b(2));
   public static final aqe<bgi> bd = a("fishing_bobber", aqe.a.<bgi>a(aqo.f).b().a().a(0.25F, 0.25F).a(4).b(5));
   private final aqe.b<T> bf;
   private final aqo bg;
   private final ImmutableSet<buo> bh;
   private final boolean bi;
   private final boolean bj;
   private final boolean bk;
   private final boolean bl;
   private final int bm;
   private final int bn;
   @Nullable
   private String bo;
   @Nullable
   private nr bp;
   @Nullable
   private vk bq;
   private final aqb br;

   private static <T extends aqa> aqe<T> a(String var0, aqe.a<T> var1) {
      return gm.a(gm.S, _snowman, _snowman.a(_snowman));
   }

   public static vk a(aqe<?> var0) {
      return gm.S.b(_snowman);
   }

   public static Optional<aqe<?>> a(String var0) {
      return gm.S.b(vk.a(_snowman));
   }

   public aqe(aqe.b<T> var1, aqo var2, boolean var3, boolean var4, boolean var5, boolean var6, ImmutableSet<buo> var7, aqb var8, int var9, int var10) {
      this.bf = _snowman;
      this.bg = _snowman;
      this.bl = _snowman;
      this.bi = _snowman;
      this.bj = _snowman;
      this.bk = _snowman;
      this.bh = _snowman;
      this.br = _snowman;
      this.bm = _snowman;
      this.bn = _snowman;
   }

   @Nullable
   public aqa a(aag var1, @Nullable bmb var2, @Nullable bfw var3, fx var4, aqp var5, boolean var6, boolean var7) {
      return this.a(_snowman, _snowman == null ? null : _snowman.o(), _snowman != null && _snowman.t() ? _snowman.r() : null, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   public T a(aag var1, @Nullable md var2, @Nullable nr var3, @Nullable bfw var4, fx var5, aqp var6, boolean var7, boolean var8) {
      T _snowman = this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman != null) {
         _snowman.l(_snowman);
      }

      return _snowman;
   }

   @Nullable
   public T b(aag var1, @Nullable md var2, @Nullable nr var3, @Nullable bfw var4, fx var5, aqp var6, boolean var7, boolean var8) {
      T _snowman = this.a(_snowman);
      if (_snowman == null) {
         return null;
      } else {
         double _snowmanx;
         if (_snowman) {
            _snowman.d((double)_snowman.u() + 0.5, (double)(_snowman.v() + 1), (double)_snowman.w() + 0.5);
            _snowmanx = a(_snowman, _snowman, _snowman, _snowman.cc());
         } else {
            _snowmanx = 0.0;
         }

         _snowman.b((double)_snowman.u() + 0.5, (double)_snowman.v() + _snowmanx, (double)_snowman.w() + 0.5, afm.g(_snowman.t.nextFloat() * 360.0F), 0.0F);
         if (_snowman instanceof aqn) {
            aqn _snowmanxx = (aqn)_snowman;
            _snowmanxx.aC = _snowmanxx.p;
            _snowmanxx.aA = _snowmanxx.p;
            _snowmanxx.a(_snowman, _snowman.d(_snowmanxx.cB()), _snowman, null, _snowman);
            _snowmanxx.F();
         }

         if (_snowman != null && _snowman instanceof aqm) {
            _snowman.a(_snowman);
         }

         a(_snowman, _snowman, _snowman, _snowman);
         return _snowman;
      }
   }

   protected static double a(brz var0, fx var1, boolean var2, dci var3) {
      dci _snowman = new dci(_snowman);
      if (_snowman) {
         _snowman = _snowman.b(0.0, -1.0, 0.0);
      }

      Stream<ddh> _snowmanx = _snowman.d(null, _snowman, var0x -> true);
      return 1.0 + dde.a(gc.a.b, _snowman, _snowmanx, _snowman ? -2.0 : -1.0);
   }

   public static void a(brx var0, @Nullable bfw var1, @Nullable aqa var2, @Nullable md var3) {
      if (_snowman != null && _snowman.c("EntityTag", 10)) {
         MinecraftServer _snowman = _snowman.l();
         if (_snowman != null && _snowman != null) {
            if (_snowman.v || !_snowman.cj() || _snowman != null && _snowman.ae().h(_snowman.eA())) {
               md _snowmanx = _snowman.e(new md());
               UUID _snowmanxx = _snowman.bS();
               _snowmanx.a(_snowman.p("EntityTag"));
               _snowman.a_(_snowmanxx);
               _snowman.f(_snowmanx);
            }
         }
      }
   }

   public boolean a() {
      return this.bi;
   }

   public boolean b() {
      return this.bj;
   }

   public boolean c() {
      return this.bk;
   }

   public boolean d() {
      return this.bl;
   }

   public aqo e() {
      return this.bg;
   }

   public String f() {
      if (this.bo == null) {
         this.bo = x.a("entity", gm.S.b(this));
      }

      return this.bo;
   }

   public nr g() {
      if (this.bp == null) {
         this.bp = new of(this.f());
      }

      return this.bp;
   }

   @Override
   public String toString() {
      return this.f();
   }

   public vk i() {
      if (this.bq == null) {
         vk _snowman = gm.S.b(this);
         this.bq = new vk(_snowman.b(), "entities/" + _snowman.a());
      }

      return this.bq;
   }

   public float j() {
      return this.br.a;
   }

   public float k() {
      return this.br.b;
   }

   @Nullable
   public T a(brx var1) {
      return this.bf.create(this, _snowman);
   }

   @Nullable
   public static aqa a(int var0, brx var1) {
      return a(_snowman, gm.S.a(_snowman));
   }

   public static Optional<aqa> a(md var0, brx var1) {
      return x.a(a(_snowman).map(var1x -> var1x.a(_snowman)), var1x -> var1x.f(_snowman), () -> be.warn("Skipping Entity with id {}", _snowman.l("id")));
   }

   @Nullable
   private static aqa a(brx var0, @Nullable aqe<?> var1) {
      return _snowman == null ? null : _snowman.a(_snowman);
   }

   public dci a(double var1, double var3, double var5) {
      float _snowman = this.j() / 2.0F;
      return new dci(_snowman - (double)_snowman, _snowman, _snowman - (double)_snowman, _snowman + (double)_snowman, _snowman + (double)this.k(), _snowman + (double)_snowman);
   }

   public boolean a(ceh var1) {
      if (this.bh.contains(_snowman.b())) {
         return false;
      } else {
         return this.bk || !_snowman.a(aed.an) && !_snowman.a(bup.iJ) && !buy.g(_snowman) && !_snowman.a(bup.B) ? _snowman.a(bup.bA) || _snowman.a(bup.mg) || _snowman.a(bup.cF) : true;
      }
   }

   public aqb l() {
      return this.br;
   }

   public static Optional<aqe<?>> a(md var0) {
      return gm.S.b(new vk(_snowman.l("id")));
   }

   @Nullable
   public static aqa a(md var0, brx var1, Function<aqa, aqa> var2) {
      return b(_snowman, _snowman).map(_snowman).map(var3 -> {
         if (_snowman.c("Passengers", 9)) {
            mj _snowman = _snowman.d("Passengers", 10);

            for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
               aqa _snowmanxx = a(_snowman.a(_snowmanx), _snowman, _snowman);
               if (_snowmanxx != null) {
                  _snowmanxx.a(var3, true);
               }
            }
         }

         return (aqa)var3;
      }).orElse(null);
   }

   private static Optional<aqa> b(md var0, brx var1) {
      try {
         return a(_snowman, _snowman);
      } catch (RuntimeException var3) {
         be.warn("Exception loading entity: ", var3);
         return Optional.empty();
      }
   }

   public int m() {
      return this.bm;
   }

   public int n() {
      return this.bn;
   }

   public boolean o() {
      return this != bc && this != R && this != aT && this != d && this != M && this != O && this != ad && this != s && this != x;
   }

   public boolean a(ael<aqe<?>> var1) {
      return _snowman.a(this);
   }

   public static class a<T extends aqa> {
      private final aqe.b<T> a;
      private final aqo b;
      private ImmutableSet<buo> c = ImmutableSet.of();
      private boolean d = true;
      private boolean e = true;
      private boolean f;
      private boolean g;
      private int h = 5;
      private int i = 3;
      private aqb j = aqb.b(0.6F, 1.8F);

      private a(aqe.b<T> var1, aqo var2) {
         this.a = _snowman;
         this.b = _snowman;
         this.g = _snowman == aqo.b || _snowman == aqo.f;
      }

      public static <T extends aqa> aqe.a<T> a(aqe.b<T> var0, aqo var1) {
         return new aqe.a<>(_snowman, _snowman);
      }

      public static <T extends aqa> aqe.a<T> a(aqo var0) {
         return new aqe.a<>((var0x, var1) -> null, _snowman);
      }

      public aqe.a<T> a(float var1, float var2) {
         this.j = aqb.b(_snowman, _snowman);
         return this;
      }

      public aqe.a<T> a() {
         this.e = false;
         return this;
      }

      public aqe.a<T> b() {
         this.d = false;
         return this;
      }

      public aqe.a<T> c() {
         this.f = true;
         return this;
      }

      public aqe.a<T> a(buo... var1) {
         this.c = ImmutableSet.copyOf(_snowman);
         return this;
      }

      public aqe.a<T> d() {
         this.g = true;
         return this;
      }

      public aqe.a<T> a(int var1) {
         this.h = _snowman;
         return this;
      }

      public aqe.a<T> b(int var1) {
         this.i = _snowman;
         return this;
      }

      public aqe<T> a(String var1) {
         if (this.d) {
            x.a(akn.o, _snowman);
         }

         return new aqe<>(this.a, this.b, this.d, this.e, this.f, this.g, this.c, this.j, this.h, this.i);
      }
   }

   public interface b<T extends aqa> {
      T create(aqe<T> var1, brx var2);
   }
}
