import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class hz implements Consumer<BiConsumer<vk, cyy.a>> {
   private static final dbo.a a = dbv.a(bq.a.a().a(new bb(bpw.u, bz.d.b(1))));
   private static final dbo.a b = a.a();
   private static final dbo.a c = dbv.a(bq.a.a().a(bmd.ng));
   private static final dbo.a d = c.a(a);
   private static final dbo.a e = d.a();
   private static final Set<blx> f = Stream.of(
         bup.ef,
         bup.es,
         bup.kW,
         bup.fc,
         bup.fe,
         bup.fi,
         bup.fg,
         bup.fk,
         bup.fm,
         bup.iP,
         bup.jf,
         bup.jb,
         bup.jc,
         bup.iZ,
         bup.iX,
         bup.jd,
         bup.iT,
         bup.iY,
         bup.iV,
         bup.iS,
         bup.iR,
         bup.iW,
         bup.ja,
         bup.je,
         bup.iQ,
         bup.iU
      )
      .map(brw::h)
      .collect(ImmutableSet.toImmutableSet());
   private static final float[] g = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
   private static final float[] h = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
   private final Map<vk, cyy.a> i = Maps.newHashMap();

   public hz() {
   }

   private static <T> T a(brw var0, dag<T> var1) {
      return !f.contains(_snowman.h()) ? _snowman.b(czy.c()) : _snowman.c();
   }

   private static <T> T a(brw var0, dbh<T> var1) {
      return !f.contains(_snowman.h()) ? _snowman.b(dbk.c()) : _snowman.c();
   }

   private static cyy.a a(brw var0) {
      return cyy.b().a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman))));
   }

   private static cyy.a a(buo var0, dbo.a var1, czq.a<?> var2) {
      return cyy.b().a(cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(_snowman).a(_snowman)));
   }

   private static cyy.a a(buo var0, czq.a<?> var1) {
      return a(_snowman, a, _snowman);
   }

   private static cyy.a b(buo var0, czq.a<?> var1) {
      return a(_snowman, c, _snowman);
   }

   private static cyy.a c(buo var0, czq.a<?> var1) {
      return a(_snowman, d, _snowman);
   }

   private static cyy.a b(buo var0, brw var1) {
      return a(_snowman, (czq.a<?>)a((brw)_snowman, czn.a(_snowman)));
   }

   private static cyy.a a(brw var0, czb var1) {
      return cyy.b().a(cyx.a().a(cyr.a(1)).a((czq.a<?>)a(_snowman, czn.a(_snowman).a(daq.a(_snowman)))));
   }

   private static cyy.a a(buo var0, brw var1, czb var2) {
      return a(_snowman, (czq.a<?>)a((brw)_snowman, czn.a(_snowman).a(daq.a(_snowman))));
   }

   private static cyy.a b(brw var0) {
      return cyy.b().a(cyx.a().a(a).a(cyr.a(1)).a(czn.a(_snowman)));
   }

   private static cyy.a c(brw var0) {
      return cyy.b().a(a(bup.ev, cyx.a().a(cyr.a(1)).a(czn.a(bup.ev)))).a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman))));
   }

   private static cyy.a e(buo var0) {
      return cyy.b().a(cyx.a().a(cyr.a(1)).a((czq.a<?>)a((brw)_snowman, czn.a(_snowman).a(daq.a(cyr.a(2)).a(dbn.a(_snowman).a(cm.a.a().a(bzw.a, cfm.c)))))));
   }

   private static <T extends Comparable<T> & afs> cyy.a a(buo var0, cfj<T> var1, T var2) {
      return cyy.b().a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(dbn.a(_snowman).a(cm.a.a().a(_snowman, _snowman))))));
   }

   private static cyy.a f(buo var0) {
      return cyy.b().a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(daa.a(daa.a.d)))));
   }

   private static cyy.a g(buo var0) {
      return cyy.b()
         .a(
            a(
               _snowman,
               cyx.a()
                  .a(cyr.a(1))
                  .a(
                     czn.a(_snowman)
                        .a(daa.a(daa.a.d))
                        .a(
                           dab.a(dab.c.d)
                              .a("Lock", "BlockEntityTag.Lock")
                              .a("LootTable", "BlockEntityTag.LootTable")
                              .a("LootTableSeed", "BlockEntityTag.LootTableSeed")
                        )
                        .a(dao.c().a(czk.a(bzs.b)))
                  )
            )
         );
   }

   private static cyy.a h(buo var0) {
      return cyy.b().a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(daa.a(daa.a.d)).a(dab.a(dab.c.d).a("Patterns", "BlockEntityTag.Patterns")))));
   }

   private static cyy.a i(buo var0) {
      return cyy.b().a(cyx.a().a(a).a(cyr.a(1)).a(czn.a(_snowman).a(dab.a(dab.c.d).a("Bees", "BlockEntityTag.Bees")).a(czz.a(_snowman).a(buk.b))));
   }

   private static cyy.a j(buo var0) {
      return cyy.b().a(cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(a).a(dab.a(dab.c.d).a("Bees", "BlockEntityTag.Bees")).a(czz.a(_snowman).a(buk.b)).a(czn.a(_snowman))));
   }

   private static cyy.a a(buo var0, blx var1) {
      return a(_snowman, (czq.a<?>)a((brw)_snowman, czn.a(_snowman).a(czx.a(bpw.w))));
   }

   private static cyy.a c(buo var0, brw var1) {
      return a(_snowman, (czq.a<?>)a((brw)_snowman, czn.a(_snowman).a(daq.a(czd.a(-6.0F, 2.0F))).a(dah.a(cyu.a(0)))));
   }

   private static cyy.a k(buo var0) {
      return b(_snowman, (czq.a<?>)a((brw)_snowman, czn.a(bmd.kV).a(dbt.a(0.125F)).a(czx.a(bpw.w, 2))));
   }

   private static cyy.a b(buo var0, blx var1) {
      return cyy.b()
         .a(
            a(
               _snowman,
               cyx.a()
                  .a(cyr.a(1))
                  .a(
                     czn.a(_snowman)
                        .a(daq.a(cyp.a(3, 0.06666667F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 0))))
                        .a(daq.a(cyp.a(3, 0.13333334F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 1))))
                        .a(daq.a(cyp.a(3, 0.2F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 2))))
                        .a(daq.a(cyp.a(3, 0.26666668F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 3))))
                        .a(daq.a(cyp.a(3, 0.33333334F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 4))))
                        .a(daq.a(cyp.a(3, 0.4F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 5))))
                        .a(daq.a(cyp.a(3, 0.46666667F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 6))))
                        .a(daq.a(cyp.a(3, 0.53333336F)).a(dbn.a(_snowman).a(cm.a.a().a(cam.a, 7))))
                  )
            )
         );
   }

   private static cyy.a c(buo var0, blx var1) {
      return cyy.b().a(a(_snowman, cyx.a().a(cyr.a(1)).a(czn.a(_snowman).a(daq.a(cyp.a(3, 0.53333336F))))));
   }

   private static cyy.a d(brw var0) {
      return cyy.b().a(cyx.a().a(cyr.a(1)).a(c).a(czn.a(_snowman)));
   }

   private static cyy.a a(buo var0, buo var1, float... var2) {
      return c(_snowman, ((czs.a)a((brw)_snowman, czn.a(_snowman))).a(dbf.a(bpw.w, _snowman)))
         .a(
            cyx.a()
               .a(cyr.a(1))
               .a(e)
               .a(((czs.a)a((brw)_snowman, czn.a(bmd.kP).a(daq.a(czd.a(1.0F, 2.0F))))).a(dbf.a(bpw.w, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
         );
   }

   private static cyy.a b(buo var0, buo var1, float... var2) {
      return a(_snowman, _snowman, _snowman).a(cyx.a().a(cyr.a(1)).a(e).a(((czs.a)a((brw)_snowman, czn.a(bmd.kb))).a(dbf.a(bpw.w, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
   }

   private static cyy.a a(buo var0, blx var1, blx var2, dbo.a var3) {
      return a((brw)_snowman, cyy.b().a(cyx.a().a(czn.a(_snowman).a(_snowman).a(czn.a(_snowman)))).a(cyx.a().a(_snowman).a(czn.a(_snowman).a(czx.a(bpw.w, 0.5714286F, 3)))));
   }

   private static cyy.a l(buo var0) {
      return cyy.b().a(cyx.a().a(c).a(czn.a(_snowman).a(daq.a(cyr.a(2)))));
   }

   private static cyy.a b(buo var0, buo var1) {
      czq.a<?> _snowman = czn.a(_snowman).a(daq.a(cyr.a(2))).a(c).a(((czs.a)a((brw)_snowman, czn.a(bmd.kV))).a(dbt.a(0.125F)));
      return cyy.b()
         .a(cyx.a().a(_snowman).a(dbn.a(_snowman).a(cm.a.a().a(bwd.a, cfd.b))).a(dbm.a(bw.a.a().a(an.a.a().a(_snowman).a(cm.a.a().a(bwd.a, cfd.a).b()).b()), new fx(0, 1, 0))))
         .a(cyx.a().a(_snowman).a(dbn.a(_snowman).a(cm.a.a().a(bwd.a, cfd.a))).a(dbm.a(bw.a.a().a(an.a.a().a(_snowman).a(cm.a.a().a(bwd.a, cfd.b).b()).b()), new fx(0, -1, 0))));
   }

   public static cyy.a a() {
      return cyy.b();
   }

   public void a(BiConsumer<vk, cyy.a> var1) {
      this.d(bup.c);
      this.d(bup.d);
      this.d(bup.e);
      this.d(bup.f);
      this.d(bup.g);
      this.d(bup.h);
      this.d(bup.j);
      this.d(bup.k);
      this.d(bup.m);
      this.d(bup.n);
      this.d(bup.o);
      this.d(bup.p);
      this.d(bup.q);
      this.d(bup.r);
      this.d(bup.s);
      this.d(bup.t);
      this.d(bup.u);
      this.d(bup.v);
      this.d(bup.w);
      this.d(bup.x);
      this.d(bup.y);
      this.d(bup.C);
      this.d(bup.D);
      this.d(bup.F);
      this.d(bup.G);
      this.d(bup.J);
      this.d(bup.K);
      this.d(bup.L);
      this.d(bup.M);
      this.d(bup.N);
      this.d(bup.O);
      this.d(bup.P);
      this.d(bup.Q);
      this.d(bup.R);
      this.d(bup.S);
      this.d(bup.T);
      this.d(bup.U);
      this.d(bup.mi);
      this.d(bup.mr);
      this.d(bup.V);
      this.d(bup.W);
      this.d(bup.X);
      this.d(bup.Y);
      this.d(bup.Z);
      this.d(bup.aa);
      this.d(bup.ab);
      this.d(bup.ac);
      this.d(bup.ad);
      this.d(bup.ae);
      this.d(bup.af);
      this.d(bup.ag);
      this.d(bup.mt);
      this.d(bup.mk);
      this.d(bup.an);
      this.d(bup.ao);
      this.d(bup.ar);
      this.d(bup.at);
      this.d(bup.au);
      this.d(bup.av);
      this.d(bup.aw);
      this.d(bup.aN);
      this.d(bup.aO);
      this.d(bup.aP);
      this.d(bup.aW);
      this.d(bup.aY);
      this.d(bup.aZ);
      this.d(bup.ba);
      this.d(bup.bb);
      this.d(bup.bc);
      this.d(bup.bd);
      this.d(bup.be);
      this.d(bup.bf);
      this.d(bup.bg);
      this.d(bup.bh);
      this.d(bup.bi);
      this.d(bup.bj);
      this.d(bup.bk);
      this.d(bup.bl);
      this.d(bup.bm);
      this.d(bup.bn);
      this.d(bup.bp);
      this.d(bup.bq);
      this.d(bup.br);
      this.d(bup.bs);
      this.d(bup.bt);
      this.d(bup.bu);
      this.d(bup.bv);
      this.d(bup.bw);
      this.d(bup.bx);
      this.d(bup.by);
      this.d(bup.bz);
      this.d(bup.bA);
      this.d(bup.bB);
      this.d(bup.bC);
      this.d(bup.bD);
      this.d(bup.bE);
      this.d(bup.bF);
      this.d(bup.bG);
      this.d(bup.bJ);
      this.d(bup.bK);
      this.d(bup.ni);
      this.d(bup.bL);
      this.d(bup.bQ);
      this.d(bup.bS);
      this.d(bup.bU);
      this.d(bup.bV);
      this.d(bup.bZ);
      this.d(bup.ca);
      this.d(bup.cb);
      this.d(bup.cc);
      this.d(bup.cd);
      this.d(bup.ce);
      this.d(bup.cg);
      this.d(bup.ch);
      this.d(bup.ci);
      this.d(bup.cp);
      this.d(bup.cq);
      this.d(bup.cs);
      this.d(bup.ct);
      this.d(bup.cu);
      this.d(bup.cv);
      this.d(bup.cw);
      this.d(bup.cx);
      this.d(bup.cz);
      this.d(bup.cB);
      this.d(bup.cF);
      this.d(bup.cH);
      this.d(bup.cI);
      this.d(bup.cJ);
      this.d(bup.cK);
      this.d(bup.cL);
      this.d(bup.cM);
      this.d(bup.cN);
      this.d(bup.cO);
      this.d(bup.cP);
      this.d(bup.cQ);
      this.d(bup.cU);
      this.d(bup.cV);
      this.d(bup.cX);
      this.d(bup.do_);
      this.d(bup.dp);
      this.d(bup.dq);
      this.d(bup.dr);
      this.d(bup.ds);
      this.d(bup.dt);
      this.d(bup.du);
      this.d(bup.dv);
      this.d(bup.dw);
      this.d(bup.dx);
      this.d(bup.dH);
      this.d(bup.dQ);
      this.d(bup.dR);
      this.d(bup.dS);
      this.d(bup.dU);
      this.d(bup.dV);
      this.d(bup.dW);
      this.d(bup.dX);
      this.d(bup.eb);
      this.d(bup.ee);
      this.d(bup.eg);
      this.d(bup.ei);
      this.d(bup.el);
      this.d(bup.en);
      this.d(bup.eo);
      this.d(bup.ep);
      this.d(bup.eq);
      this.d(bup.et);
      this.d(bup.eu);
      this.d(bup.ev);
      this.d(bup.eW);
      this.d(bup.eX);
      this.d(bup.eY);
      this.d(bup.eZ);
      this.d(bup.fa);
      this.d(bup.fb);
      this.d(bup.fc);
      this.d(bup.fe);
      this.d(bup.fg);
      this.d(bup.fk);
      this.d(bup.fm);
      this.d(bup.fo);
      this.d(bup.fp);
      this.d(bup.fq);
      this.d(bup.fs);
      this.d(bup.ft);
      this.d(bup.fu);
      this.d(bup.fv);
      this.d(bup.fw);
      this.d(bup.fz);
      this.d(bup.fA);
      this.d(bup.fB);
      this.d(bup.fC);
      this.d(bup.fD);
      this.d(bup.fF);
      this.d(bup.fG);
      this.d(bup.fH);
      this.d(bup.fI);
      this.d(bup.fJ);
      this.d(bup.fK);
      this.d(bup.fL);
      this.d(bup.fM);
      this.d(bup.fN);
      this.d(bup.fO);
      this.d(bup.fP);
      this.d(bup.fQ);
      this.d(bup.fR);
      this.d(bup.fS);
      this.d(bup.fT);
      this.d(bup.fU);
      this.d(bup.gl);
      this.d(bup.gm);
      this.d(bup.gn);
      this.d(bup.gp);
      this.d(bup.gq);
      this.d(bup.gr);
      this.d(bup.gs);
      this.d(bup.gt);
      this.d(bup.gu);
      this.d(bup.gv);
      this.d(bup.gA);
      this.d(bup.gB);
      this.d(bup.gC);
      this.d(bup.gD);
      this.d(bup.gE);
      this.d(bup.gF);
      this.d(bup.gG);
      this.d(bup.gH);
      this.d(bup.gI);
      this.d(bup.gJ);
      this.d(bup.gK);
      this.d(bup.gL);
      this.d(bup.gM);
      this.d(bup.gN);
      this.d(bup.gO);
      this.d(bup.gP);
      this.d(bup.gQ);
      this.d(bup.gR);
      this.d(bup.gS);
      this.d(bup.hG);
      this.d(bup.hH);
      this.d(bup.hI);
      this.d(bup.hJ);
      this.d(bup.id);
      this.d(bup.ie);
      this.d(bup.if_);
      this.d(bup.ig);
      this.d(bup.ih);
      this.d(bup.ii);
      this.d(bup.ij);
      this.d(bup.ik);
      this.d(bup.il);
      this.d(bup.im);
      this.d(bup.in);
      this.d(bup.io);
      this.d(bup.ip);
      this.d(bup.iq);
      this.d(bup.iw);
      this.d(bup.iz);
      this.d(bup.iA);
      this.d(bup.iB);
      this.d(bup.iC);
      this.d(bup.iJ);
      this.d(bup.iK);
      this.d(bup.iL);
      this.d(bup.iM);
      this.d(bup.iO);
      this.d(bup.nb);
      this.d(bup.jg);
      this.d(bup.jh);
      this.d(bup.ji);
      this.d(bup.jj);
      this.d(bup.jk);
      this.d(bup.jl);
      this.d(bup.jm);
      this.d(bup.jn);
      this.d(bup.jo);
      this.d(bup.jp);
      this.d(bup.jq);
      this.d(bup.jr);
      this.d(bup.js);
      this.d(bup.jt);
      this.d(bup.ju);
      this.d(bup.jv);
      this.d(bup.jw);
      this.d(bup.jx);
      this.d(bup.jy);
      this.d(bup.jz);
      this.d(bup.jA);
      this.d(bup.jB);
      this.d(bup.jC);
      this.d(bup.jD);
      this.d(bup.jE);
      this.d(bup.jF);
      this.d(bup.jG);
      this.d(bup.jH);
      this.d(bup.jI);
      this.d(bup.jJ);
      this.d(bup.jK);
      this.d(bup.jL);
      this.d(bup.jM);
      this.d(bup.jN);
      this.d(bup.jO);
      this.d(bup.jP);
      this.d(bup.jQ);
      this.d(bup.jR);
      this.d(bup.jS);
      this.d(bup.jT);
      this.d(bup.jU);
      this.d(bup.jV);
      this.d(bup.jW);
      this.d(bup.jX);
      this.d(bup.jY);
      this.d(bup.jZ);
      this.d(bup.ka);
      this.d(bup.kb);
      this.d(bup.kc);
      this.d(bup.ke);
      this.d(bup.kg);
      this.d(bup.kh);
      this.d(bup.ki);
      this.d(bup.kj);
      this.d(bup.kk);
      this.d(bup.kW);
      this.d(bup.ef);
      this.d(bup.kY);
      this.d(bup.ld);
      this.d(bup.le);
      this.d(bup.lf);
      this.d(bup.lg);
      this.d(bup.lh);
      this.d(bup.li);
      this.d(bup.lj);
      this.d(bup.lk);
      this.d(bup.ll);
      this.d(bup.lm);
      this.d(bup.ln);
      this.d(bup.lo);
      this.d(bup.lp);
      this.d(bup.lq);
      this.d(bup.lE);
      this.d(bup.lF);
      this.d(bup.lG);
      this.d(bup.lH);
      this.d(bup.lI);
      this.d(bup.lJ);
      this.d(bup.lK);
      this.d(bup.lL);
      this.d(bup.lM);
      this.d(bup.lN);
      this.d(bup.lO);
      this.d(bup.lP);
      this.d(bup.lR);
      this.d(bup.lQ);
      this.d(bup.ne);
      this.d(bup.nf);
      this.d(bup.nj);
      this.d(bup.no);
      this.d(bup.mh);
      this.d(bup.mj);
      this.d(bup.mm);
      this.d(bup.mn);
      this.d(bup.mq);
      this.d(bup.ms);
      this.d(bup.mv);
      this.d(bup.mw);
      this.d(bup.mC);
      this.d(bup.mD);
      this.d(bup.mH);
      this.d(bup.mJ);
      this.d(bup.mL);
      this.d(bup.mN);
      this.d(bup.mP);
      this.d(bup.mR);
      this.d(bup.mV);
      this.d(bup.mG);
      this.d(bup.mI);
      this.d(bup.mK);
      this.d(bup.mM);
      this.d(bup.mO);
      this.d(bup.mQ);
      this.d(bup.mU);
      this.d(bup.ng);
      this.d(bup.nh);
      this.d(bup.np);
      this.d(bup.nu);
      this.d(bup.ny);
      this.d(bup.nq);
      this.d(bup.nr);
      this.d(bup.nz);
      this.d(bup.nw);
      this.d(bup.nv);
      this.d(bup.nt);
      this.d(bup.nB);
      this.d(bup.nD);
      this.d(bup.nE);
      this.d(bup.nF);
      this.d(bup.nG);
      this.d(bup.nH);
      this.d(bup.nI);
      this.d(bup.dI);
      this.d(bup.mo);
      this.d(bup.mB);
      this.a(bup.bX, (brw)bup.j);
      this.a(bup.em, (brw)bmd.kS);
      this.a(bup.iE, (brw)bup.j);
      this.a(bup.kd, (brw)bup.kc);
      this.a(bup.kX, (brw)bup.kY);
      this.a(bup.b, var0 -> b(var0, (brw)bup.m));
      this.a(bup.i, var0 -> b(var0, (brw)bup.j));
      this.a(bup.l, var0 -> b(var0, (brw)bup.j));
      this.a(bup.dT, var0 -> b(var0, (brw)bup.j));
      this.a(bup.kl, var0 -> b(var0, (brw)bup.kg));
      this.a(bup.km, var0 -> b(var0, (brw)bup.kh));
      this.a(bup.kn, var0 -> b(var0, (brw)bup.ki));
      this.a(bup.ko, var0 -> b(var0, (brw)bup.kj));
      this.a(bup.kp, var0 -> b(var0, (brw)bup.kk));
      this.a(bup.mu, var0 -> b(var0, (brw)bup.cL));
      this.a(bup.ml, var0 -> b(var0, (brw)bup.cL));
      this.a(bup.bI, var0 -> a(var0, bmd.mc, cyr.a(3)));
      this.a(bup.cG, var0 -> a(var0, bmd.lZ, cyr.a(4)));
      this.a(bup.ek, var0 -> a(var0, bup.bK, cyr.a(8)));
      this.a(bup.cE, var0 -> a(var0, bmd.lQ, cyr.a(4)));
      this.a(bup.ix, a(bmd.qd, czd.a(0.0F, 1.0F)));
      this.b(bup.ew);
      this.b(bup.ex);
      this.b(bup.ey);
      this.b(bup.ez);
      this.b(bup.eA);
      this.b(bup.eB);
      this.b(bup.eC);
      this.b(bup.eD);
      this.b(bup.eE);
      this.b(bup.eF);
      this.b(bup.eG);
      this.b(bup.eH);
      this.b(bup.eI);
      this.b(bup.eJ);
      this.b(bup.eK);
      this.b(bup.eL);
      this.b(bup.eM);
      this.b(bup.eN);
      this.b(bup.eO);
      this.b(bup.eP);
      this.b(bup.eQ);
      this.b(bup.eR);
      this.b(bup.eS);
      this.b(bup.eT);
      this.b(bup.kZ);
      this.b(bup.nk);
      this.b(bup.nl);
      this.b(bup.nm);
      this.b(bup.nn);
      this.a(bup.hO, hz::e);
      this.a(bup.hM, hz::e);
      this.a(bup.hW, hz::e);
      this.a(bup.hV, hz::e);
      this.a(bup.hP, hz::e);
      this.a(bup.gy, hz::e);
      this.a(bup.hN, hz::e);
      this.a(bup.hY, hz::e);
      this.a(bup.hK, hz::e);
      this.a(bup.hU, hz::e);
      this.a(bup.gx, hz::e);
      this.a(bup.gw, hz::e);
      this.a(bup.ic, hz::e);
      this.a(bup.hZ, hz::e);
      this.a(bup.ia, hz::e);
      this.a(bup.hS, hz::e);
      this.a(bup.ib, hz::e);
      this.a(bup.hT, hz::e);
      this.a(bup.hL, hz::e);
      this.a(bup.hX, hz::e);
      this.a(bup.hQ, hz::e);
      this.a(bup.hR, hz::e);
      this.a(bup.lr, hz::e);
      this.a(bup.ls, hz::e);
      this.a(bup.lt, hz::e);
      this.a(bup.lu, hz::e);
      this.a(bup.lv, hz::e);
      this.a(bup.lw, hz::e);
      this.a(bup.lx, hz::e);
      this.a(bup.ly, hz::e);
      this.a(bup.lz, hz::e);
      this.a(bup.lA, hz::e);
      this.a(bup.lB, hz::e);
      this.a(bup.lC, hz::e);
      this.a(bup.lD, hz::e);
      this.a(bup.mE, hz::e);
      this.a(bup.mF, hz::e);
      this.a(bup.ns, hz::e);
      this.a(bup.nx, hz::e);
      this.a(bup.nC, hz::e);
      this.a(bup.iu, hz::a);
      this.a(bup.is, hz::a);
      this.a(bup.iv, hz::a);
      this.a(bup.cr, hz::a);
      this.a(bup.it, hz::a);
      this.a(bup.cf, hz::a);
      this.a(bup.ir, hz::a);
      this.a(bup.mT, hz::a);
      this.a(bup.mS, hz::a);
      this.a(bup.aM, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aI, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aJ, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aG, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aE, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aK, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aA, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aF, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aC, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.az, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aH, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.ay, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aD, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aL, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.ax, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.aB, var0 -> a(var0, buj.a, cev.a));
      this.a(bup.gV, var0 -> a(var0, bwd.a, cfd.b));
      this.a(bup.gU, var0 -> a(var0, bwd.a, cfd.b));
      this.a(bup.gX, var0 -> a(var0, bwd.a, cfd.b));
      this.a(bup.gW, var0 -> a(var0, bwd.a, cfd.b));
      this.a(bup.bH, cyy.b().a(a(bup.bH, cyx.a().a(cyr.a(1)).a(czn.a(bup.bH).a(dbn.a(bup.bH).a(cm.a.a().a(caz.a, false)))))));
      this.a(bup.eh, var0 -> cyy.b().a(cyx.a().a(cyr.a(1)).a((czq.a<?>)a((brw)var0, czn.a(bmd.ms).a(daq.a(cyr.a(3)).a(dbn.a(var0).a(cm.a.a().a(bvh.a, 2))))))));
      this.a(
         bup.kU,
         var0 -> cyy.b()
               .a(
                  cyx.a()
                     .a(cyr.a(1))
                     .a(
                        (czq.a<?>)a(
                           (brw)bup.kU,
                           czn.a(var0)
                              .a(daq.a(cyr.a(2)).a(dbn.a(var0).a(cm.a.a().a(bzq.a, 2))))
                              .a(daq.a(cyr.a(3)).a(dbn.a(var0).a(cm.a.a().a(bzq.a, 3))))
                              .a(daq.a(cyr.a(4)).a(dbn.a(var0).a(cm.a.a().a(bzq.a, 4))))
                        )
                     )
               )
      );
      this.a(bup.na, var0 -> cyy.b().a(cyx.a().a((czq.a<?>)a((brw)var0, czn.a(bmd.qZ)))).a(cyx.a().a(czn.a(bmd.mK)).a(dbn.a(var0).a(cm.a.a().a(bvk.a, 8)))));
      this.a(bup.es, hz::f);
      this.a(bup.ea, hz::f);
      this.a(bup.bR, hz::f);
      this.a(bup.as, hz::f);
      this.a(bup.fE, hz::f);
      this.a(bup.dZ, hz::f);
      this.a(bup.bY, hz::f);
      this.a(bup.fy, hz::f);
      this.a(bup.fr, hz::f);
      this.a(bup.lT, hz::f);
      this.a(bup.lU, hz::f);
      this.a(bup.lS, hz::f);
      this.a(bup.lV, hz::f);
      this.a(bup.lW, hz::f);
      this.a(bup.lX, hz::f);
      this.a(bup.lY, hz::f);
      this.a(bup.lZ, hz::f);
      this.a(bup.ma, hz::f);
      this.a(bup.mb, hz::a);
      this.a(bup.mc, hz::a);
      this.a(bup.md, hz::a);
      this.a(bup.iP, hz::g);
      this.a(bup.jf, hz::g);
      this.a(bup.jb, hz::g);
      this.a(bup.jc, hz::g);
      this.a(bup.iZ, hz::g);
      this.a(bup.iX, hz::g);
      this.a(bup.jd, hz::g);
      this.a(bup.iT, hz::g);
      this.a(bup.iY, hz::g);
      this.a(bup.iV, hz::g);
      this.a(bup.iS, hz::g);
      this.a(bup.iR, hz::g);
      this.a(bup.iW, hz::g);
      this.a(bup.ja, hz::g);
      this.a(bup.je, hz::g);
      this.a(bup.iQ, hz::g);
      this.a(bup.iU, hz::g);
      this.a(bup.hp, hz::h);
      this.a(bup.hl, hz::h);
      this.a(bup.hm, hz::h);
      this.a(bup.hj, hz::h);
      this.a(bup.hh, hz::h);
      this.a(bup.hn, hz::h);
      this.a(bup.hd, hz::h);
      this.a(bup.hi, hz::h);
      this.a(bup.hf, hz::h);
      this.a(bup.hc, hz::h);
      this.a(bup.hb, hz::h);
      this.a(bup.hg, hz::h);
      this.a(bup.hk, hz::h);
      this.a(bup.ho, hz::h);
      this.a(bup.ha, hz::h);
      this.a(bup.he, hz::h);
      this.a(bup.fi, var0 -> cyy.b().a(a(var0, cyx.a().a(cyr.a(1)).a(czn.a(var0).a(dab.a(dab.c.d).a("SkullOwner", "SkullOwner"))))));
      this.a(bup.nc, hz::i);
      this.a(bup.nd, hz::j);
      this.a(bup.aj, var0 -> a(var0, bup.v, g));
      this.a(bup.al, var0 -> a(var0, bup.x, g));
      this.a(bup.ak, var0 -> a(var0, bup.w, h));
      this.a(bup.ai, var0 -> a(var0, bup.u, g));
      this.a(bup.ah, var0 -> b(var0, bup.t, g));
      this.a(bup.am, var0 -> b(var0, bup.y, g));
      dbo.a _snowman = dbn.a(bup.iD).a(cm.a.a().a(bul.a, 3));
      this.a(bup.iD, a(bup.iD, bmd.qf, bmd.qg, _snowman));
      dbo.a _snowmanx = dbn.a(bup.bW).a(cm.a.a().a(bvs.b, 7));
      this.a(bup.bW, a(bup.bW, bmd.kW, bmd.kV, _snowmanx));
      dbo.a _snowmanxx = dbn.a(bup.eU).a(cm.a.a().a(buz.b, 7));
      this.a(bup.eU, a((brw)bup.eU, cyy.b().a(cyx.a().a(czn.a(bmd.oY))).a(cyx.a().a(_snowmanxx).a(czn.a(bmd.oY).a(czx.a(bpw.w, 0.5714286F, 3))))));
      dbo.a _snowmanxxx = dbn.a(bup.eV).a(cm.a.a().a(byv.b, 7));
      this.a(
         bup.eV,
         a(
            (brw)bup.eV,
            cyy.b()
               .a(cyx.a().a(czn.a(bmd.oZ)))
               .a(cyx.a().a(_snowmanxxx).a(czn.a(bmd.oZ).a(czx.a(bpw.w, 0.5714286F, 3))))
               .a(cyx.a().a(_snowmanxxx).a(czn.a(bmd.pb).a(dbt.a(0.02F))))
         )
      );
      this.a(
         bup.mg,
         var0 -> a(
               (brw)var0,
               cyy.b()
                  .a(cyx.a().a(dbn.a(bup.mg).a(cm.a.a().a(cau.a, 3))).a(czn.a(bmd.rm)).a(daq.a(czd.a(2.0F, 3.0F))).a(czx.b(bpw.w)))
                  .a(cyx.a().a(dbn.a(bup.mg).a(cm.a.a().a(cau.a, 2))).a(czn.a(bmd.rm)).a(daq.a(czd.a(1.0F, 2.0F))).a(czx.b(bpw.w)))
            )
      );
      this.a(bup.dE, var0 -> c(var0, (brw)bup.bC));
      this.a(bup.dF, var0 -> c(var0, (brw)bup.bD));
      this.a(bup.H, var0 -> a(var0, bmd.ke));
      this.a(bup.ej, var0 -> a(var0, bmd.oV));
      this.a(bup.fx, var0 -> a(var0, bmd.ps));
      this.a(bup.bT, var0 -> a(var0, bmd.kg));
      this.a(bup.I, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.nt).a(daq.a(czd.a(2.0F, 6.0F))).a(czx.a(bpw.w)))));
      this.a(bup.aq, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.mt).a(daq.a(czd.a(4.0F, 9.0F))).a(czx.a(bpw.w)))));
      this.a(bup.aQ, var0 -> c(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.kS))));
      this.a(bup.aT, var0 -> b(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.kP).a(daq.a(czd.a(0.0F, 2.0F))))));
      this.a(bup.mp, hz::d);
      this.a(bup.aU, hz::d);
      this.a(bup.dP, hz::d);
      this.a(bup.aV, l(bup.aU));
      this.a(bup.gZ, var0 -> b(var0, bup.aS));
      this.a(bup.gY, var0 -> b(var0, bup.aR));
      this.a(bup.dO, var0 -> b(var0, bmd.nk));
      this.a(bup.dM, var0 -> c(var0, bmd.nk));
      this.a(bup.dN, var0 -> b(var0, bmd.nj));
      this.a(bup.dL, var0 -> c(var0, bmd.nj));
      this.a(bup.iy, var0 -> cyy.b().a(cyx.a().a(cyr.a(1)).a(((czs.a)a((brw)var0, czn.a(var0))).a(dbr.a(cyv.c.a)))));
      this.a(bup.aS, hz::k);
      this.a(bup.aR, hz::k);
      this.a(bup.cS, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.mk).a(daq.a(czd.a(2.0F, 4.0F))).a(czx.b(bpw.w)).a(dah.a(cyu.a(1, 4))))));
      this.a(bup.dK, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.nh).a(daq.a(czd.a(3.0F, 7.0F))).a(czx.b(bpw.w)).a(dah.a(cyu.b(9))))));
      this.a(bup.cy, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.lP).a(daq.a(czd.a(4.0F, 5.0F))).a(czx.b(bpw.w)))));
      this.a(bup.gz, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.pw).a(daq.a(czd.a(2.0F, 3.0F))).a(czx.b(bpw.w)).a(dah.a(cyu.a(1, 5))))));
      this.a(
         bup.dY,
         var0 -> cyy.b()
               .a(
                  a(
                     var0,
                     cyx.a()
                        .a(cyr.a(1))
                        .a(
                           czn.a(bmd.nu)
                              .a(daq.a(czd.a(2.0F, 4.0F)).a(dbn.a(var0).a(cm.a.a().a(bym.a, 3))))
                              .a(czx.b(bpw.w).a(dbn.a(var0).a(cm.a.a().a(bym.a, 3))))
                        )
                  )
               )
      );
      this.a(
         bup.cC,
         var0 -> cyy.b()
               .a(
                  cyx.a()
                     .a(dbr.a(cyv.c.a))
                     .a(
                        czh.a(
                           czh.a(
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 1))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 2))).a(daq.a(cyr.a(2))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 3))).a(daq.a(cyr.a(3))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 4))).a(daq.a(cyr.a(4))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 5))).a(daq.a(cyr.a(5))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 6))).a(daq.a(cyr.a(6))),
                                 czn.a(bmd.lQ).a(dbn.a(var0).a(cm.a.a().a(caa.a, 7))).a(daq.a(cyr.a(7))),
                                 czn.a(bmd.lQ).a(daq.a(cyr.a(8)))
                              )
                              .a(b),
                           czh.a(
                              czn.a(bup.cC).a(dbn.a(var0).a(cm.a.a().a(caa.a, 1))),
                              czn.a(bup.cC).a(daq.a(cyr.a(2))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 2))),
                              czn.a(bup.cC).a(daq.a(cyr.a(3))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 3))),
                              czn.a(bup.cC).a(daq.a(cyr.a(4))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 4))),
                              czn.a(bup.cC).a(daq.a(cyr.a(5))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 5))),
                              czn.a(bup.cC).a(daq.a(cyr.a(6))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 6))),
                              czn.a(bup.cC).a(daq.a(cyr.a(7))).a(dbn.a(var0).a(cm.a.a().a(caa.a, 7))),
                              czn.a(bup.cE)
                           )
                        )
                     )
               )
      );
      this.a(bup.E, var0 -> a(var0, a((brw)var0, czn.a(bmd.lw).a(dbf.a(bpw.w, 0.1F, 0.14285715F, 0.25F, 1.0F)).a(czn.a(var0)))));
      this.a(bup.me, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.kf).a(daq.a(cyr.a(2))))));
      this.a(bup.nA, var0 -> a(var0, a((brw)var0, czn.a(bmd.nt).a(daq.a(czd.a(2.0F, 5.0F))).a(dbf.a(bpw.w, 0.1F, 0.14285715F, 0.25F, 1.0F)).a(czn.a(var0)))));
      this.a(bup.mf, var0 -> a(var0, (czq.a<?>)a((brw)var0, czn.a(bmd.dm).a(daq.a(cyr.a(1))))));
      this.c(bup.ap);
      this.c(bup.cY);
      this.c(bup.cZ);
      this.c(bup.da);
      this.c(bup.db);
      this.c(bup.dc);
      this.c(bup.dd);
      this.c(bup.de);
      this.c(bup.df);
      this.c(bup.dg);
      this.c(bup.dh);
      this.c(bup.di);
      this.c(bup.dj);
      this.c(bup.dk);
      this.c(bup.dl);
      this.c(bup.dm);
      this.c(bup.dn);
      this.c(bup.dJ);
      this.c(bup.fV);
      this.c(bup.fW);
      this.c(bup.fX);
      this.c(bup.fY);
      this.c(bup.fZ);
      this.c(bup.ga);
      this.c(bup.gb);
      this.c(bup.gc);
      this.c(bup.gd);
      this.c(bup.ge);
      this.c(bup.gf);
      this.c(bup.gg);
      this.c(bup.gh);
      this.c(bup.gi);
      this.c(bup.gj);
      this.c(bup.gk);
      this.c(bup.cD);
      this.c(bup.gT);
      this.c(bup.kV);
      this.c(bup.kf);
      this.c(bup.dG);
      this.c(bup.kq);
      this.c(bup.kr);
      this.c(bup.ks);
      this.c(bup.kt);
      this.c(bup.ku);
      this.c(bup.kv);
      this.c(bup.kw);
      this.c(bup.kx);
      this.c(bup.ky);
      this.c(bup.kz);
      this.c(bup.kA);
      this.c(bup.kB);
      this.c(bup.kC);
      this.c(bup.kD);
      this.c(bup.kE);
      this.c(bup.kF);
      this.c(bup.kG);
      this.c(bup.kH);
      this.c(bup.kI);
      this.c(bup.kJ);
      this.a(bup.dy, bup.b);
      this.a(bup.dz, bup.m);
      this.a(bup.dA, bup.du);
      this.a(bup.dB, bup.dv);
      this.a(bup.dC, bup.dw);
      this.a(bup.dD, bup.dx);
      this.c(bup.mx, bup.my);
      this.c(bup.mz, bup.mA);
      this.a(bup.cW, a());
      this.a(bup.iI, a());
      this.a(bup.bP, a());
      this.a(bup.bN, a());
      this.a(bup.bO, a());
      this.a(bup.cT, a());
      Set<vk> _snowmanxxxx = Sets.newHashSet();

      for (buo _snowmanxxxxx : gm.Q) {
         vk _snowmanxxxxxx = _snowmanxxxxx.r();
         if (_snowmanxxxxxx != cyq.a && _snowmanxxxx.add(_snowmanxxxxxx)) {
            cyy.a _snowmanxxxxxxx = this.i.remove(_snowmanxxxxxx);
            if (_snowmanxxxxxxx == null) {
               throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", _snowmanxxxxxx, gm.Q.b(_snowmanxxxxx)));
            }

            _snowman.accept(_snowmanxxxxxx, _snowmanxxxxxxx);
         }
      }

      if (!this.i.isEmpty()) {
         throw new IllegalStateException("Created block loot tables for non-blocks: " + this.i.keySet());
      }
   }

   private void c(buo var1, buo var2) {
      cyy.a _snowman = c(_snowman, czn.a(_snowman).a(dbf.a(bpw.w, 0.33F, 0.55F, 0.77F, 1.0F)));
      this.a(_snowman, _snowman);
      this.a(_snowman, _snowman);
   }

   public static cyy.a a(buo var0) {
      return a(_snowman, bwb.e, cfd.b);
   }

   public void b(buo var1) {
      this.a(_snowman, var0 -> c((brw)((bwv)var0).c()));
   }

   public void a(buo var1, buo var2) {
      this.a(_snowman, b((brw)_snowman));
   }

   public void a(buo var1, brw var2) {
      this.a(_snowman, a(_snowman));
   }

   public void c(buo var1) {
      this.a(_snowman, _snowman);
   }

   public void d(buo var1) {
      this.a(_snowman, (brw)_snowman);
   }

   private void a(buo var1, Function<buo, cyy.a> var2) {
      this.a(_snowman, _snowman.apply(_snowman));
   }

   private void a(buo var1, cyy.a var2) {
      this.i.put(_snowman.r(), _snowman);
   }
}
