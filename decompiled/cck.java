import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cck<T extends ccj> {
   private static final Logger H = LogManager.getLogger();
   public static final cck<ccw> a = a("furnace", cck.a.a(ccw::new, bup.bY));
   public static final cck<ccn> b = a("chest", cck.a.a(ccn::new, bup.bR));
   public static final cck<cdn> c = a("trapped_chest", cck.a.a(cdn::new, bup.fr));
   public static final cck<ccv> d = a("ender_chest", cck.a.a(ccv::new, bup.ek));
   public static final cck<cda> e = a("jukebox", cck.a.a(cda::new, bup.cI));
   public static final cck<ccs> f = a("dispenser", cck.a.a(ccs::new, bup.as));
   public static final cck<cct> g = a("dropper", cck.a.a(cct::new, bup.fE));
   public static final cck<cdf> h = a(
      "sign", cck.a.a(cdf::new, bup.bZ, bup.ca, bup.cb, bup.cc, bup.cd, bup.ce, bup.cj, bup.ck, bup.cl, bup.cm, bup.cn, bup.co, bup.mU, bup.mW, bup.mV, bup.mX)
   );
   public static final cck<cdi> i = a("mob_spawner", cck.a.a(cdi::new, bup.bP));
   public static final cck<ced> j = a("piston", cck.a.a(ced::new, bup.bo));
   public static final cck<ccl> k = a("brewing_stand", cck.a.a(ccl::new, bup.ea));
   public static final cck<ccu> l = a("enchanting_table", cck.a.a(ccu::new, bup.dZ));
   public static final cck<cdl> m = a("end_portal", cck.a.a(cdl::new, bup.ec));
   public static final cck<cce> n = a("beacon", cck.a.a(cce::new, bup.es));
   public static final cck<cdg> o = a(
      "skull", cck.a.a(cdg::new, bup.fc, bup.fd, bup.fk, bup.fl, bup.fm, bup.fn, bup.fg, bup.fh, bup.fe, bup.ff, bup.fi, bup.fj)
   );
   public static final cck<ccr> p = a("daylight_detector", cck.a.a(ccr::new, bup.fv));
   public static final cck<ccy> q = a("hopper", cck.a.a(ccy::new, bup.fy));
   public static final cck<ccp> r = a("comparator", cck.a.a(ccp::new, bup.fu));
   public static final cck<cca> s = a(
      "banner",
      cck.a.a(
         cca::new,
         bup.ha,
         bup.hb,
         bup.hc,
         bup.hd,
         bup.he,
         bup.hf,
         bup.hg,
         bup.hh,
         bup.hi,
         bup.hj,
         bup.hk,
         bup.hl,
         bup.hm,
         bup.hn,
         bup.ho,
         bup.hp,
         bup.hq,
         bup.hr,
         bup.hs,
         bup.ht,
         bup.hu,
         bup.hv,
         bup.hw,
         bup.hx,
         bup.hy,
         bup.hz,
         bup.hA,
         bup.hB,
         bup.hC,
         bup.hD,
         bup.hE,
         bup.hF
      )
   );
   public static final cck<cdj> t = a("structure_block", cck.a.a(cdj::new, bup.mY));
   public static final cck<cdk> u = a("end_gateway", cck.a.a(cdk::new, bup.iF));
   public static final cck<cco> v = a("command_block", cck.a.a(cco::new, bup.er, bup.iH, bup.iG));
   public static final cck<cde> w = a(
      "shulker_box",
      cck.a.a(cde::new, bup.iP, bup.jf, bup.jb, bup.jc, bup.iZ, bup.iX, bup.jd, bup.iT, bup.iY, bup.iV, bup.iS, bup.iR, bup.iW, bup.ja, bup.je, bup.iQ, bup.iU)
   );
   public static final cck<ccf> x = a(
      "bed", cck.a.a(ccf::new, bup.aL, bup.aM, bup.aI, bup.aJ, bup.aG, bup.aE, bup.aK, bup.aA, bup.aF, bup.aC, bup.az, bup.ay, bup.aD, bup.aH, bup.ax, bup.aB)
   );
   public static final cck<ccq> y = a("conduit", cck.a.a(ccq::new, bup.kW));
   public static final cck<ccc> z = a("barrel", cck.a.a(ccc::new, bup.lS));
   public static final cck<cdh> A = a("smoker", cck.a.a(cdh::new, bup.lT));
   public static final cck<cci> B = a("blast_furnace", cck.a.a(cci::new, bup.lU));
   public static final cck<cdb> C = a("lectern", cck.a.a(cdb::new, bup.lY));
   public static final cck<cch> D = a("bell", cck.a.a(cch::new, bup.mb));
   public static final cck<ccz> E = a("jigsaw", cck.a.a(ccz::new, bup.mZ));
   public static final cck<ccm> F = a("campfire", cck.a.a(ccm::new, bup.me, bup.mf));
   public static final cck<ccg> G = a("beehive", cck.a.a(ccg::new, bup.nc, bup.nd));
   private final Supplier<? extends T> I;
   private final Set<buo> J;
   private final Type<?> K;

   @Nullable
   public static vk a(cck<?> var0) {
      return gm.W.b(_snowman);
   }

   private static <T extends ccj> cck<T> a(String var0, cck.a<T> var1) {
      if (_snowman.b.isEmpty()) {
         H.warn("Block entity type {} requires at least one valid block to be defined!", _snowman);
      }

      Type<?> _snowman = x.a(akn.k, _snowman);
      return gm.a(gm.W, _snowman, _snowman.a(_snowman));
   }

   public cck(Supplier<? extends T> var1, Set<buo> var2, Type<?> var3) {
      this.I = _snowman;
      this.J = _snowman;
      this.K = _snowman;
   }

   @Nullable
   public T a() {
      return (T)this.I.get();
   }

   public boolean a(buo var1) {
      return this.J.contains(_snowman);
   }

   @Nullable
   public T a(brc var1, fx var2) {
      ccj _snowman = _snowman.c(_snowman);
      return (T)(_snowman != null && _snowman.u() == this ? _snowman : null);
   }

   public static final class a<T extends ccj> {
      private final Supplier<? extends T> a;
      private final Set<buo> b;

      private a(Supplier<? extends T> var1, Set<buo> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public static <T extends ccj> cck.a<T> a(Supplier<? extends T> var0, buo... var1) {
         return new cck.a<>(_snowman, ImmutableSet.copyOf(_snowman));
      }

      public cck<T> a(Type<?> var1) {
         return new cck<>(this.a, this.b, _snowman);
      }
   }
}
