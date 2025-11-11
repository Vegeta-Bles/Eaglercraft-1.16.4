import java.nio.file.Path;
import java.util.function.Function;

public class jv extends jw<blx> {
   private final Function<ael.e<buo>, ael.a> d;

   public jv(hl var1, js var2) {
      super(_snowman, gm.T);
      this.d = _snowman::b;
   }

   @Override
   protected void b() {
      this.a(aed.b, aeg.b);
      this.a(aed.c, aeg.c);
      this.a(aed.d, aeg.d);
      this.a(aed.e, aeg.e);
      this.a(aed.f, aeg.f);
      this.a(aed.g, aeg.g);
      this.a(aed.h, aeg.h);
      this.a(aed.i, aeg.i);
      this.a(aed.j, aeg.j);
      this.a(aed.k, aeg.k);
      this.a(aed.m, aeg.l);
      this.a(aed.p, aeg.n);
      this.a(aed.q, aeg.o);
      this.a(aed.u, aeg.s);
      this.a(aed.t, aeg.r);
      this.a(aed.v, aeg.t);
      this.a(aed.w, aeg.u);
      this.a(aed.y, aeg.w);
      this.a(aed.x, aeg.v);
      this.a(aed.z, aeg.x);
      this.a(aed.A, aeg.y);
      this.a(aed.r, aeg.p);
      this.a(aed.s, aeg.q);
      this.a(aed.C, aeg.A);
      this.a(aed.E, aeg.C);
      this.a(aed.F, aeg.D);
      this.a(aed.D, aeg.B);
      this.a(aed.G, aeg.E);
      this.a(aed.H, aeg.F);
      this.a(aed.I, aeg.G);
      this.a(aed.o, aeg.m);
      this.a(aed.J, aeg.H);
      this.a(aed.K, aeg.I);
      this.a(aed.L, aeg.J);
      this.a(aed.M, aeg.K);
      this.a(aed.N, aeg.L);
      this.a(aed.O, aeg.M);
      this.a(aed.Q, aeg.P);
      this.a(aed.aw, aeg.R);
      this.a(aeg.z).a(bmd.pM, bmd.pN, bmd.pO, bmd.pP, bmd.pQ, bmd.pR, bmd.pS, bmd.pT, bmd.pU, bmd.pV, bmd.pW, bmd.pX, bmd.pY, bmd.pZ, bmd.qa, bmd.qb);
      this.a(aeg.S).a(bmd.lR, bmd.qp, bmd.qq, bmd.qr, bmd.qs, bmd.qt);
      this.a(aeg.T).a(bmd.ml, bmd.mp, bmd.mm, bmd.mq, bmd.mo, bmd.mn);
      this.a(aed.ad, aeg.U);
      this.a(aeg.W).a(bmd.qz, bmd.qA, bmd.qB, bmd.qC, bmd.qD, bmd.qE, bmd.qF, bmd.qG, bmd.qH, bmd.qI, bmd.qJ, bmd.qK);
      this.a(aeg.V).a(aeg.W).a(bmd.qL);
      this.a(aeg.X).a(bmd.ke, bmd.kf);
      this.a(aeg.Y).a(bmd.kd, bmd.ql, bmd.qk);
      this.a(aeg.Z).a(bmd.oU, bmd.oT);
      this.a(aeg.aa).a(bmd.kj, bmd.oV, bmd.kg, bmd.ki, bmd.kh);
      this.a(aeg.N).a(bmd.dp).a(bmd.rl).a(bmd.ro);
      this.a(aeg.O)
         .a(aeg.P)
         .a(
            bmd.bG,
            bmd.rE,
            bmd.fg,
            bmd.ki,
            bmd.rj,
            bmd.mj,
            bmd.pd,
            bmd.nE,
            bmd.lA,
            bmd.lB,
            bmd.lo,
            bmd.lp,
            bmd.lq,
            bmd.lr,
            bmd.pE,
            bmd.kv,
            bmd.kx,
            bmd.kw,
            bmd.ky,
            bmd.kz
         );
      this.a(aeg.Q)
         .a(
            bmd.S,
            bmd.aa,
            bmd.aq,
            bmd.ai,
            bmd.R,
            bmd.Z,
            bmd.ap,
            bmd.ah,
            bmd.v,
            bmd.w,
            bmd.bO,
            bmd.bP,
            bmd.cP,
            bmd.cQ,
            bmd.dg,
            bmd.dh,
            bmd.dy,
            bmd.dz,
            bmd.dY,
            bmd.dZ,
            bmd.ex,
            bmd.ey,
            bmd.eZ,
            bmd.fa,
            bmd.jS,
            bmd.jT,
            bmd.lI,
            bmd.lJ
         );
      this.a(aeg.ab).a(bmd.o, bmd.rB);
      this.a(aeg.ac).a(bmd.o, bmd.rB);
   }

   protected void a(ael.e<buo> var1, ael.e<blx> var2) {
      ael.a _snowman = this.b(_snowman);
      ael.a _snowmanx = this.d.apply(_snowman);
      _snowmanx.b().forEach(_snowman::a);
   }

   @Override
   protected Path a(vk var1) {
      return this.b.b().resolve("data/" + _snowman.b() + "/tags/items/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "Item Tags";
   }
}
