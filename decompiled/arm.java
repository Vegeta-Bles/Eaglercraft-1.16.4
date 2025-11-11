import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class arm {
   private static final Logger a = LogManager.getLogger();
   private static final Map<aqe<? extends aqm>, ark> b = ImmutableMap.builder()
      .put(aqe.b, aqm.cL().a())
      .put(aqe.d, azu.m().a())
      .put(aqe.e, baa.eZ().a())
      .put(aqe.f, bda.m().a())
      .put(aqe.h, bab.fa().a())
      .put(aqe.i, bdb.m().a())
      .put(aqe.j, bac.eK().a())
      .put(aqe.k, azw.m().a())
      .put(aqe.l, bae.eK().a())
      .put(aqe.m, bdc.m().a())
      .put(aqe.n, baf.eM().a())
      .put(aqe.o, bba.eL().a())
      .put(aqe.q, bej.eS().a())
      .put(aqe.r, bdf.m().a())
      .put(aqe.u, bdg.m().a())
      .put(aqe.v, bdh.m().a())
      .put(aqe.t, bbr.m().a())
      .put(aqe.w, bdj.eK().a())
      .put(aqe.C, bah.eK().a())
      .put(aqe.D, bdk.eJ().a())
      .put(aqe.E, bdl.m().a())
      .put(aqe.F, bdm.eM().a())
      .put(aqe.G, bem.eK().a())
      .put(aqe.H, bbb.fi().a())
      .put(aqe.I, bej.eS().a())
      .put(aqe.J, bdo.eK().a())
      .put(aqe.K, bai.m().a())
      .put(aqe.Q, bbe.fw().a())
      .put(aqe.S, bdp.m().a())
      .put(aqe.ab, bae.eK().a())
      .put(aqe.aa, bba.eL().a())
      .put(aqe.ac, bak.eK().a())
      .put(aqe.ae, bal.eY().a())
      .put(aqe.af, bam.eU().a())
      .put(aqe.ag, bdq.eR().a())
      .put(aqe.ah, ban.eK().a())
      .put(aqe.ai, bes.eT().a())
      .put(aqe.aj, bev.eS().a())
      .put(aqe.ak, bdt.eK().a())
      .put(aqe.bc, bfw.ep().a())
      .put(aqe.al, bao.eK().a())
      .put(aqe.an, azw.m().a())
      .put(aqe.ao, baq.eL().a())
      .put(aqe.ap, bdv.m().a())
      .put(aqe.aq, azw.m().a())
      .put(aqe.ar, bas.eK().a())
      .put(aqe.as, bdw.m().a())
      .put(aqe.au, bdx.m().a())
      .put(aqe.av, bcz.m().a())
      .put(aqe.aw, bbh.eL().a())
      .put(aqe.ax, bdq.eR().a())
      .put(aqe.az, bau.m().a())
      .put(aqe.aC, beb.eK().a())
      .put(aqe.aD, bav.m().a())
      .put(aqe.aE, bcz.m().a())
      .put(aqe.aF, bed.eM().a())
      .put(aqe.aL, bbe.fw().a())
      .put(aqe.aM, azw.m().a())
      .put(aqe.aN, bax.eM().a())
      .put(aqe.aO, bee.m().a())
      .put(aqe.aP, bfj.eY().a())
      .put(aqe.aQ, bef.eK().a())
      .put(aqe.aR, aqn.p().a())
      .put(aqe.aS, beg.eK().a())
      .put(aqe.aT, bcl.eK().a())
      .put(aqe.aU, bcz.m().a())
      .put(aqe.aW, baz.eU().a())
      .put(aqe.aX, bei.m().a())
      .put(aqe.aY, bej.eS().a())
      .put(aqe.aZ, bbl.eL().a())
      .put(aqe.ba, bej.eS().a())
      .put(aqe.bb, bel.eW().a())
      .build();

   public static ark a(aqe<? extends aqm> var0) {
      return b.get(_snowman);
   }

   public static boolean b(aqe<?> var0) {
      return b.containsKey(_snowman);
   }

   public static void a() {
      gm.S.g().filter(var0 -> var0.e() != aqo.f).filter(var0 -> !b((aqe<?>)var0)).map(gm.S::b).forEach(var0 -> {
         if (w.d) {
            throw new IllegalStateException("Entity " + var0 + " has no attributes");
         } else {
            a.error("Entity {} has no attributes", var0);
         }
      });
   }
}
