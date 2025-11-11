import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bfn {
   public static final Map<bfm, Int2ObjectMap<bfn.f[]>> a = x.a(
      Maps.newHashMap(),
      var0 -> {
         var0.put(
            bfm.f,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{
                     new bfn.b(bmd.kW, 20, 16, 2),
                     new bfn.b(bmd.oZ, 26, 16, 2),
                     new bfn.b(bmd.oY, 22, 16, 2),
                     new bfn.b(bmd.qf, 15, 16, 2),
                     new bfn.h(bmd.kX, 1, 6, 16, 1)
                  },
                  2,
                  new bfn.f[]{new bfn.b(bup.cK, 6, 12, 10), new bfn.h(bmd.pn, 1, 4, 5), new bfn.h(bmd.kb, 1, 4, 16, 5)},
                  3,
                  new bfn.f[]{new bfn.h(bmd.ne, 3, 18, 10), new bfn.b(bup.dK, 4, 12, 20)},
                  4,
                  new bfn.f[]{
                     new bfn.h(bup.cW, 1, 1, 12, 15),
                     new bfn.i(apw.p, 100, 15),
                     new bfn.i(apw.h, 160, 15),
                     new bfn.i(apw.r, 140, 15),
                     new bfn.i(apw.o, 120, 15),
                     new bfn.i(apw.s, 280, 15),
                     new bfn.i(apw.w, 7, 15)
                  },
                  5,
                  new bfn.f[]{new bfn.h(bmd.pd, 3, 3, 30), new bfn.h(bmd.nE, 4, 3, 30)}
               )
            )
         );
         var0.put(
            bfm.g,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{
                     new bfn.b(bmd.kS, 20, 16, 2), new bfn.b(bmd.ke, 10, 16, 2), new bfn.g(bmd.ml, 6, bmd.mp, 6, 16, 1), new bfn.h(bmd.lW, 3, 1, 16, 1)
                  },
                  2,
                  new bfn.f[]{new bfn.b(bmd.ml, 15, 16, 10), new bfn.g(bmd.mm, 6, bmd.mq, 6, 16, 5), new bfn.h(bmd.rn, 2, 1, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.mm, 13, 16, 20), new bfn.e(bmd.mi, 3, 3, 10, 0.2F)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.mn, 6, 12, 30)},
                  5,
                  new bfn.f[]{
                     new bfn.b(bmd.mo, 4, 12, 30),
                     new bfn.c(
                        1,
                        12,
                        30,
                        ImmutableMap.builder()
                           .put(bfo.c, bmd.lR)
                           .put(bfo.g, bmd.qp)
                           .put(bfo.e, bmd.qp)
                           .put(bfo.a, bmd.qr)
                           .put(bfo.b, bmd.qr)
                           .put(bfo.d, bmd.qs)
                           .put(bfo.f, bmd.qt)
                           .build()
                     )
                  }
               )
            )
         );
         var0.put(
            bfm.m,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{
                     new bfn.b(bup.aY, 18, 16, 2),
                     new bfn.b(bup.bk, 18, 16, 2),
                     new bfn.b(bup.bn, 18, 16, 2),
                     new bfn.b(bup.bf, 18, 16, 2),
                     new bfn.h(bmd.ng, 2, 1, 1)
                  },
                  2,
                  new bfn.f[]{
                     new bfn.b(bmd.mu, 12, 16, 10),
                     new bfn.b(bmd.mB, 12, 16, 10),
                     new bfn.b(bmd.mJ, 12, 16, 10),
                     new bfn.b(bmd.mx, 12, 16, 10),
                     new bfn.b(bmd.mz, 12, 16, 10),
                     new bfn.h(bup.aY, 1, 1, 16, 5),
                     new bfn.h(bup.aZ, 1, 1, 16, 5),
                     new bfn.h(bup.ba, 1, 1, 16, 5),
                     new bfn.h(bup.bb, 1, 1, 16, 5),
                     new bfn.h(bup.bc, 1, 1, 16, 5),
                     new bfn.h(bup.bd, 1, 1, 16, 5),
                     new bfn.h(bup.be, 1, 1, 16, 5),
                     new bfn.h(bup.bf, 1, 1, 16, 5),
                     new bfn.h(bup.bg, 1, 1, 16, 5),
                     new bfn.h(bup.bh, 1, 1, 16, 5),
                     new bfn.h(bup.bi, 1, 1, 16, 5),
                     new bfn.h(bup.bj, 1, 1, 16, 5),
                     new bfn.h(bup.bk, 1, 1, 16, 5),
                     new bfn.h(bup.bl, 1, 1, 16, 5),
                     new bfn.h(bup.bm, 1, 1, 16, 5),
                     new bfn.h(bup.bn, 1, 1, 16, 5),
                     new bfn.h(bup.gB, 1, 4, 16, 5),
                     new bfn.h(bup.gC, 1, 4, 16, 5),
                     new bfn.h(bup.gD, 1, 4, 16, 5),
                     new bfn.h(bup.gE, 1, 4, 16, 5),
                     new bfn.h(bup.gF, 1, 4, 16, 5),
                     new bfn.h(bup.gG, 1, 4, 16, 5),
                     new bfn.h(bup.gH, 1, 4, 16, 5),
                     new bfn.h(bup.gI, 1, 4, 16, 5),
                     new bfn.h(bup.gJ, 1, 4, 16, 5),
                     new bfn.h(bup.gK, 1, 4, 16, 5),
                     new bfn.h(bup.gL, 1, 4, 16, 5),
                     new bfn.h(bup.gM, 1, 4, 16, 5),
                     new bfn.h(bup.gN, 1, 4, 16, 5),
                     new bfn.h(bup.gO, 1, 4, 16, 5),
                     new bfn.h(bup.gP, 1, 4, 16, 5),
                     new bfn.h(bup.gQ, 1, 4, 16, 5)
                  },
                  3,
                  new bfn.f[]{
                     new bfn.b(bmd.my, 12, 16, 20),
                     new bfn.b(bmd.mC, 12, 16, 20),
                     new bfn.b(bmd.mv, 12, 16, 20),
                     new bfn.b(bmd.mI, 12, 16, 20),
                     new bfn.b(bmd.mA, 12, 16, 20),
                     new bfn.h(bup.ax, 3, 1, 12, 10),
                     new bfn.h(bup.aB, 3, 1, 12, 10),
                     new bfn.h(bup.aL, 3, 1, 12, 10),
                     new bfn.h(bup.aM, 3, 1, 12, 10),
                     new bfn.h(bup.aI, 3, 1, 12, 10),
                     new bfn.h(bup.aJ, 3, 1, 12, 10),
                     new bfn.h(bup.aG, 3, 1, 12, 10),
                     new bfn.h(bup.aE, 3, 1, 12, 10),
                     new bfn.h(bup.aK, 3, 1, 12, 10),
                     new bfn.h(bup.aA, 3, 1, 12, 10),
                     new bfn.h(bup.aF, 3, 1, 12, 10),
                     new bfn.h(bup.aC, 3, 1, 12, 10),
                     new bfn.h(bup.az, 3, 1, 12, 10),
                     new bfn.h(bup.ay, 3, 1, 12, 10),
                     new bfn.h(bup.aD, 3, 1, 12, 10),
                     new bfn.h(bup.aH, 3, 1, 12, 10)
                  },
                  4,
                  new bfn.f[]{
                     new bfn.b(bmd.mG, 12, 16, 30),
                     new bfn.b(bmd.mE, 12, 16, 30),
                     new bfn.b(bmd.mF, 12, 16, 30),
                     new bfn.b(bmd.mH, 12, 16, 30),
                     new bfn.b(bmd.mw, 12, 16, 30),
                     new bfn.b(bmd.mD, 12, 16, 30),
                     new bfn.h(bmd.pM, 3, 1, 12, 15),
                     new bfn.h(bmd.pX, 3, 1, 12, 15),
                     new bfn.h(bmd.pP, 3, 1, 12, 15),
                     new bfn.h(bmd.qa, 3, 1, 12, 15),
                     new bfn.h(bmd.pS, 3, 1, 12, 15),
                     new bfn.h(bmd.pZ, 3, 1, 12, 15),
                     new bfn.h(bmd.pR, 3, 1, 12, 15),
                     new bfn.h(bmd.pT, 3, 1, 12, 15),
                     new bfn.h(bmd.qb, 3, 1, 12, 15),
                     new bfn.h(bmd.pW, 3, 1, 12, 15),
                     new bfn.h(bmd.pO, 3, 1, 12, 15),
                     new bfn.h(bmd.pV, 3, 1, 12, 15),
                     new bfn.h(bmd.pY, 3, 1, 12, 15),
                     new bfn.h(bmd.pQ, 3, 1, 12, 15),
                     new bfn.h(bmd.pN, 3, 1, 12, 15),
                     new bfn.h(bmd.pU, 3, 1, 12, 15)
                  },
                  5,
                  new bfn.f[]{new bfn.h(bmd.lz, 2, 3, 30)}
               )
            )
         );
         var0.put(
            bfm.h,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.kP, 32, 16, 2), new bfn.h(bmd.kd, 1, 16, 1), new bfn.g(bup.E, 10, bmd.lw, 10, 12, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.lw, 26, 12, 10), new bfn.h(bmd.kc, 2, 1, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.kS, 14, 16, 20), new bfn.h(bmd.qQ, 3, 1, 10)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.kT, 24, 16, 30), new bfn.e(bmd.kc, 2, 3, 15)},
                  5,
                  new bfn.f[]{new bfn.b(bmd.es, 8, 12, 30), new bfn.e(bmd.qQ, 3, 3, 15), new bfn.j(bmd.kd, 5, bmd.ql, 5, 2, 12, 30)}
               )
            )
         );
         var0.put(
            bfm.j,
            a(
               ImmutableMap.builder()
                  .put(1, new bfn.f[]{new bfn.b(bmd.mb, 24, 16, 2), new bfn.d(1), new bfn.h(bup.bI, 9, 1, 12, 1)})
                  .put(2, new bfn.f[]{new bfn.b(bmd.mc, 4, 12, 10), new bfn.d(5), new bfn.h(bmd.rk, 1, 1, 5)})
                  .put(3, new bfn.f[]{new bfn.b(bmd.mr, 5, 12, 20), new bfn.d(10), new bfn.h(bmd.az, 1, 4, 10)})
                  .put(4, new bfn.f[]{new bfn.b(bmd.oT, 2, 12, 30), new bfn.d(15), new bfn.h(bmd.mj, 5, 1, 15), new bfn.h(bmd.mh, 4, 1, 15)})
                  .put(5, new bfn.f[]{new bfn.h(bmd.pI, 20, 1, 30)})
                  .build()
            )
         );
         var0.put(
            bfm.d,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.mb, 24, 16, 2), new bfn.h(bmd.pc, 7, 1, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.dP, 11, 16, 10), new bfn.k(13, cla.l, cxu.a.j, 12, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.mh, 1, 12, 20), new bfn.k(14, cla.d, cxu.a.i, 12, 10)},
                  4,
                  new bfn.f[]{
                     new bfn.h(bmd.oW, 7, 1, 15),
                     new bfn.h(bmd.pM, 3, 1, 15),
                     new bfn.h(bmd.pX, 3, 1, 15),
                     new bfn.h(bmd.pP, 3, 1, 15),
                     new bfn.h(bmd.qa, 3, 1, 15),
                     new bfn.h(bmd.pS, 3, 1, 15),
                     new bfn.h(bmd.pZ, 3, 1, 15),
                     new bfn.h(bmd.pR, 3, 1, 15),
                     new bfn.h(bmd.pT, 3, 1, 15),
                     new bfn.h(bmd.qb, 3, 1, 15),
                     new bfn.h(bmd.pW, 3, 1, 15),
                     new bfn.h(bmd.pO, 3, 1, 15),
                     new bfn.h(bmd.pV, 3, 1, 15),
                     new bfn.h(bmd.pY, 3, 1, 15),
                     new bfn.h(bmd.pQ, 3, 1, 15),
                     new bfn.h(bmd.pN, 3, 1, 15),
                     new bfn.h(bmd.pU, 3, 1, 15)
                  },
                  5,
                  new bfn.f[]{new bfn.h(bmd.qX, 8, 1, 30)}
               )
            )
         );
         var0.put(
            bfm.e,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.np, 32, 16, 2), new bfn.h(bmd.lP, 1, 2, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.ki, 3, 12, 10), new bfn.h(bmd.mt, 1, 1, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.pA, 2, 12, 20), new bfn.h(bup.cS, 4, 1, 12, 10)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.jZ, 4, 12, 30), new bfn.b(bmd.nw, 9, 12, 30), new bfn.h(bmd.nq, 5, 1, 15)},
                  5,
                  new bfn.f[]{new bfn.b(bmd.nu, 22, 12, 30), new bfn.h(bmd.oR, 3, 1, 30)}
               )
            )
         );
         var0.put(
            bfm.b,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{
                     new bfn.b(bmd.ke, 15, 16, 2),
                     new bfn.h(new bmb(bmd.li), 7, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.lj), 4, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.lg), 5, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.lh), 9, 1, 12, 1, 0.2F)
                  },
                  2,
                  new bfn.f[]{
                     new bfn.b(bmd.kh, 4, 12, 10),
                     new bfn.h(new bmb(bmd.rj), 36, 1, 12, 5, 0.2F),
                     new bfn.h(new bmb(bmd.lf), 1, 1, 12, 5, 0.2F),
                     new bfn.h(new bmb(bmd.le), 3, 1, 12, 5, 0.2F)
                  },
                  3,
                  new bfn.f[]{
                     new bfn.b(bmd.lM, 1, 12, 20),
                     new bfn.b(bmd.kg, 1, 12, 20),
                     new bfn.h(new bmb(bmd.lc), 1, 1, 12, 10, 0.2F),
                     new bfn.h(new bmb(bmd.ld), 4, 1, 12, 10, 0.2F),
                     new bfn.h(new bmb(bmd.qn), 5, 1, 12, 10, 0.2F)
                  },
                  4,
                  new bfn.f[]{new bfn.e(bmd.lm, 14, 3, 15, 0.2F), new bfn.e(bmd.ln, 8, 3, 15, 0.2F)},
                  5,
                  new bfn.f[]{new bfn.e(bmd.lk, 8, 3, 30, 0.2F), new bfn.e(bmd.ll, 16, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            bfm.o,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.ke, 15, 16, 2), new bfn.h(new bmb(bmd.kD), 3, 1, 12, 1, 0.2F), new bfn.e(bmd.kA, 2, 3, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.kh, 4, 12, 10), new bfn.h(new bmb(bmd.rj), 36, 1, 12, 5, 0.2F)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.lw, 24, 12, 20)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.kg, 1, 12, 30), new bfn.e(bmd.kI, 12, 3, 15, 0.2F)},
                  5,
                  new bfn.f[]{new bfn.e(bmd.kF, 8, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            bfm.n,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{
                     new bfn.b(bmd.ke, 15, 16, 2),
                     new bfn.h(new bmb(bmd.kt), 1, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.kr), 1, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.ks), 1, 1, 12, 1, 0.2F),
                     new bfn.h(new bmb(bmd.ku), 1, 1, 12, 1, 0.2F)
                  },
                  2,
                  new bfn.f[]{new bfn.b(bmd.kh, 4, 12, 10), new bfn.h(new bmb(bmd.rj), 36, 1, 12, 5, 0.2F)},
                  3,
                  new bfn.f[]{
                     new bfn.b(bmd.lw, 30, 12, 20),
                     new bfn.e(bmd.kD, 1, 3, 10, 0.2F),
                     new bfn.e(bmd.kB, 2, 3, 10, 0.2F),
                     new bfn.e(bmd.kC, 3, 3, 10, 0.2F),
                     new bfn.h(new bmb(bmd.kJ), 4, 1, 3, 10, 0.2F)
                  },
                  4,
                  new bfn.f[]{new bfn.b(bmd.kg, 1, 12, 30), new bfn.e(bmd.kI, 12, 3, 15, 0.2F), new bfn.e(bmd.kG, 5, 3, 15, 0.2F)},
                  5,
                  new bfn.f[]{new bfn.e(bmd.kH, 13, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            bfm.c,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.nn, 14, 16, 2), new bfn.b(bmd.lx, 7, 16, 2), new bfn.b(bmd.px, 4, 16, 2), new bfn.h(bmd.pz, 1, 1, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.ke, 15, 16, 2), new bfn.h(bmd.ly, 1, 5, 16, 5), new bfn.h(bmd.no, 1, 8, 16, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.pK, 7, 16, 20), new bfn.b(bmd.nl, 10, 16, 20)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.ma, 10, 12, 30)},
                  5,
                  new bfn.f[]{new bfn.b(bmd.rm, 10, 12, 30)}
               )
            )
         );
         var0.put(
            bfm.i,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.lS, 6, 16, 2), new bfn.a(bmd.la, 3), new bfn.a(bmd.kZ, 7)},
                  2,
                  new bfn.f[]{new bfn.b(bmd.lw, 26, 12, 10), new bfn.a(bmd.kY, 5, 12, 5), new bfn.a(bmd.lb, 4, 12, 5)},
                  3,
                  new bfn.f[]{new bfn.b(bmd.pB, 9, 12, 20), new bfn.a(bmd.kZ, 7)},
                  4,
                  new bfn.f[]{new bfn.b(bmd.jZ, 4, 12, 30), new bfn.a(bmd.pG, 6, 12, 15)},
                  5,
                  new bfn.f[]{new bfn.h(new bmb(bmd.lO), 6, 1, 12, 30, 0.2F), new bfn.a(bmd.kY, 5, 12, 30)}
               )
            )
         );
         var0.put(
            bfm.k,
            a(
               ImmutableMap.of(
                  1,
                  new bfn.f[]{new bfn.b(bmd.lZ, 10, 16, 2), new bfn.h(bmd.lY, 1, 10, 16, 1)},
                  2,
                  new bfn.f[]{new bfn.b(bup.b, 20, 16, 10), new bfn.h(bup.dx, 1, 4, 16, 5)},
                  3,
                  new bfn.f[]{
                     new bfn.b(bup.c, 16, 16, 20),
                     new bfn.b(bup.g, 16, 16, 20),
                     new bfn.b(bup.e, 16, 16, 20),
                     new bfn.h(bup.h, 1, 4, 16, 10),
                     new bfn.h(bup.f, 1, 4, 16, 10),
                     new bfn.h(bup.d, 1, 4, 16, 10)
                  },
                  4,
                  new bfn.f[]{
                     new bfn.b(bmd.ps, 12, 12, 30),
                     new bfn.h(bup.fG, 1, 1, 12, 15),
                     new bfn.h(bup.fF, 1, 1, 12, 15),
                     new bfn.h(bup.fQ, 1, 1, 12, 15),
                     new bfn.h(bup.fI, 1, 1, 12, 15),
                     new bfn.h(bup.fM, 1, 1, 12, 15),
                     new bfn.h(bup.fN, 1, 1, 12, 15),
                     new bfn.h(bup.fU, 1, 1, 12, 15),
                     new bfn.h(bup.fT, 1, 1, 12, 15),
                     new bfn.h(bup.fL, 1, 1, 12, 15),
                     new bfn.h(bup.fH, 1, 1, 12, 15),
                     new bfn.h(bup.fK, 1, 1, 12, 15),
                     new bfn.h(bup.fS, 1, 1, 12, 15),
                     new bfn.h(bup.fO, 1, 1, 12, 15),
                     new bfn.h(bup.fP, 1, 1, 12, 15),
                     new bfn.h(bup.fJ, 1, 1, 12, 15),
                     new bfn.h(bup.fR, 1, 1, 12, 15),
                     new bfn.h(bup.jh, 1, 1, 12, 15),
                     new bfn.h(bup.jg, 1, 1, 12, 15),
                     new bfn.h(bup.jr, 1, 1, 12, 15),
                     new bfn.h(bup.jj, 1, 1, 12, 15),
                     new bfn.h(bup.jn, 1, 1, 12, 15),
                     new bfn.h(bup.jo, 1, 1, 12, 15),
                     new bfn.h(bup.jv, 1, 1, 12, 15),
                     new bfn.h(bup.ju, 1, 1, 12, 15),
                     new bfn.h(bup.jm, 1, 1, 12, 15),
                     new bfn.h(bup.ji, 1, 1, 12, 15),
                     new bfn.h(bup.jl, 1, 1, 12, 15),
                     new bfn.h(bup.jt, 1, 1, 12, 15),
                     new bfn.h(bup.jp, 1, 1, 12, 15),
                     new bfn.h(bup.jq, 1, 1, 12, 15),
                     new bfn.h(bup.jk, 1, 1, 12, 15),
                     new bfn.h(bup.js, 1, 1, 12, 15)
                  },
                  5,
                  new bfn.f[]{new bfn.h(bup.fB, 1, 1, 12, 30), new bfn.h(bup.fz, 1, 1, 12, 30)}
               )
            )
         );
      }
   );
   public static final Int2ObjectMap<bfn.f[]> b = a(
      ImmutableMap.of(
         1,
         new bfn.f[]{
            new bfn.h(bmd.aP, 2, 1, 5, 1),
            new bfn.h(bmd.md, 4, 1, 5, 1),
            new bfn.h(bmd.dq, 2, 1, 5, 1),
            new bfn.h(bmd.qO, 5, 1, 5, 1),
            new bfn.h(bmd.aM, 1, 1, 12, 1),
            new bfn.h(bmd.bD, 1, 1, 8, 1),
            new bfn.h(bmd.di, 1, 1, 4, 1),
            new bfn.h(bmd.bE, 3, 1, 12, 1),
            new bfn.h(bmd.cX, 3, 1, 8, 1),
            new bfn.h(bmd.bh, 1, 1, 12, 1),
            new bfn.h(bmd.bi, 1, 1, 12, 1),
            new bfn.h(bmd.bj, 1, 1, 8, 1),
            new bfn.h(bmd.bk, 1, 1, 12, 1),
            new bfn.h(bmd.bl, 1, 1, 12, 1),
            new bfn.h(bmd.bm, 1, 1, 12, 1),
            new bfn.h(bmd.bn, 1, 1, 12, 1),
            new bfn.h(bmd.bo, 1, 1, 12, 1),
            new bfn.h(bmd.bp, 1, 1, 12, 1),
            new bfn.h(bmd.bq, 1, 1, 12, 1),
            new bfn.h(bmd.br, 1, 1, 12, 1),
            new bfn.h(bmd.bs, 1, 1, 7, 1),
            new bfn.h(bmd.kV, 1, 1, 12, 1),
            new bfn.h(bmd.qg, 1, 1, 12, 1),
            new bfn.h(bmd.nj, 1, 1, 12, 1),
            new bfn.h(bmd.nk, 1, 1, 12, 1),
            new bfn.h(bmd.B, 5, 1, 8, 1),
            new bfn.h(bmd.z, 5, 1, 8, 1),
            new bfn.h(bmd.C, 5, 1, 8, 1),
            new bfn.h(bmd.A, 5, 1, 8, 1),
            new bfn.h(bmd.x, 5, 1, 8, 1),
            new bfn.h(bmd.y, 5, 1, 8, 1),
            new bfn.h(bmd.mI, 1, 3, 12, 1),
            new bfn.h(bmd.mu, 1, 3, 12, 1),
            new bfn.h(bmd.mF, 1, 3, 12, 1),
            new bfn.h(bmd.mA, 1, 3, 12, 1),
            new bfn.h(bmd.mJ, 1, 3, 12, 1),
            new bfn.h(bmd.mH, 1, 3, 12, 1),
            new bfn.h(bmd.mC, 1, 3, 12, 1),
            new bfn.h(bmd.mw, 1, 3, 12, 1),
            new bfn.h(bmd.my, 1, 3, 12, 1),
            new bfn.h(bmd.mB, 1, 3, 12, 1),
            new bfn.h(bmd.mE, 1, 3, 12, 1),
            new bfn.h(bmd.mx, 1, 3, 12, 1),
            new bfn.h(bmd.mz, 1, 3, 12, 1),
            new bfn.h(bmd.mv, 1, 3, 12, 1),
            new bfn.h(bmd.mG, 1, 3, 12, 1),
            new bfn.h(bmd.mD, 1, 3, 12, 1),
            new bfn.h(bmd.iJ, 3, 1, 8, 1),
            new bfn.h(bmd.iK, 3, 1, 8, 1),
            new bfn.h(bmd.iL, 3, 1, 8, 1),
            new bfn.h(bmd.iM, 3, 1, 8, 1),
            new bfn.h(bmd.iI, 3, 1, 8, 1),
            new bfn.h(bmd.dR, 1, 1, 12, 1),
            new bfn.h(bmd.bu, 1, 1, 12, 1),
            new bfn.h(bmd.bv, 1, 1, 12, 1),
            new bfn.h(bmd.ed, 1, 2, 5, 1),
            new bfn.h(bmd.E, 1, 8, 8, 1),
            new bfn.h(bmd.F, 1, 4, 6, 1)
         },
         2,
         new bfn.f[]{
            new bfn.h(bmd.lX, 5, 1, 4, 1),
            new bfn.h(bmd.lU, 5, 1, 4, 1),
            new bfn.h(bmd.ge, 3, 1, 6, 1),
            new bfn.h(bmd.jh, 6, 1, 6, 1),
            new bfn.h(bmd.kU, 1, 1, 8, 1),
            new bfn.h(bmd.l, 3, 3, 6, 1)
         }
      )
   );

   private static Int2ObjectMap<bfn.f[]> a(ImmutableMap<Integer, bfn.f[]> var0) {
      return new Int2ObjectOpenHashMap(_snowman);
   }

   static class a implements bfn.f {
      private final blx a;
      private final int b;
      private final int c;
      private final int d;

      public a(blx var1, int var2) {
         this(_snowman, _snowman, 12, 1);
      }

      public a(blx var1, int var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         bmb _snowman = new bmb(bmd.oV, this.b);
         bmb _snowmanx = new bmb(this.a);
         if (this.a instanceof bkz) {
            List<bky> _snowmanxx = Lists.newArrayList();
            _snowmanxx.add(a(_snowman));
            if (_snowman.nextFloat() > 0.7F) {
               _snowmanxx.add(a(_snowman));
            }

            if (_snowman.nextFloat() > 0.8F) {
               _snowmanxx.add(a(_snowman));
            }

            _snowmanx = blb.a(_snowmanx, _snowmanxx);
         }

         return new bqv(_snowman, _snowmanx, this.c, this.d, 0.2F);
      }

      private static bky a(Random var0) {
         return bky.a(bkx.a(_snowman.nextInt(16)));
      }
   }

   static class b implements bfn.f {
      private final blx a;
      private final int b;
      private final int c;
      private final int d;
      private final float e;

      public b(brw var1, int var2, int var3, int var4) {
         this.a = _snowman.h();
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = 0.05F;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         bmb _snowman = new bmb(this.a, this.b);
         return new bqv(_snowman, new bmb(bmd.oV), this.c, this.d, this.e);
      }
   }

   static class c implements bfn.f {
      private final Map<bfo, blx> a;
      private final int b;
      private final int c;
      private final int d;

      public c(int var1, int var2, int var3, Map<bfo, blx> var4) {
         gm.ah.g().filter(var1x -> !_snowman.containsKey(var1x)).findAny().ifPresent(var0 -> {
            throw new IllegalStateException("Missing trade for villager type: " + gm.ah.b(var0));
         });
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Nullable
      @Override
      public bqv a(aqa var1, Random var2) {
         if (_snowman instanceof bfl) {
            bmb _snowman = new bmb(this.a.get(((bfl)_snowman).eX().a()), this.b);
            return new bqv(_snowman, new bmb(bmd.oV), this.c, this.d, 0.05F);
         } else {
            return null;
         }
      }
   }

   static class d implements bfn.f {
      private final int a;

      public d(int var1) {
         this.a = _snowman;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         List<bps> _snowman = gm.R.g().filter(bps::h).collect(Collectors.toList());
         bps _snowmanx = _snowman.get(_snowman.nextInt(_snowman.size()));
         int _snowmanxx = afm.a(_snowman, _snowmanx.e(), _snowmanx.a());
         bmb _snowmanxxx = blf.a(new bpv(_snowmanx, _snowmanxx));
         int _snowmanxxxx = 2 + _snowman.nextInt(5 + _snowmanxx * 10) + 3 * _snowmanxx;
         if (_snowmanx.b()) {
            _snowmanxxxx *= 2;
         }

         if (_snowmanxxxx > 64) {
            _snowmanxxxx = 64;
         }

         return new bqv(new bmb(bmd.oV, _snowmanxxxx), new bmb(bmd.mc), _snowmanxxx, 12, this.a, 0.2F);
      }
   }

   static class e implements bfn.f {
      private final bmb a;
      private final int b;
      private final int c;
      private final int d;
      private final float e;

      public e(blx var1, int var2, int var3, int var4) {
         this(_snowman, _snowman, _snowman, _snowman, 0.05F);
      }

      public e(blx var1, int var2, int var3, int var4, float var5) {
         this.a = new bmb(_snowman);
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         int _snowman = 5 + _snowman.nextInt(15);
         bmb _snowmanx = bpu.a(_snowman, new bmb(this.a.b()), _snowman, false);
         int _snowmanxx = Math.min(this.b + _snowman, 64);
         bmb _snowmanxxx = new bmb(bmd.oV, _snowmanxx);
         return new bqv(_snowmanxxx, _snowmanx, this.c, this.d, this.e);
      }
   }

   public interface f {
      @Nullable
      bqv a(aqa var1, Random var2);
   }

   static class g implements bfn.f {
      private final bmb a;
      private final int b;
      private final int c;
      private final bmb d;
      private final int e;
      private final int f;
      private final int g;
      private final float h;

      public g(brw var1, int var2, blx var3, int var4, int var5, int var6) {
         this(_snowman, _snowman, 1, _snowman, _snowman, _snowman, _snowman);
      }

      public g(brw var1, int var2, int var3, blx var4, int var5, int var6, int var7) {
         this.a = new bmb(_snowman);
         this.b = _snowman;
         this.c = _snowman;
         this.d = new bmb(_snowman);
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = 0.05F;
      }

      @Nullable
      @Override
      public bqv a(aqa var1, Random var2) {
         return new bqv(new bmb(bmd.oV, this.c), new bmb(this.a.b(), this.b), new bmb(this.d.b(), this.e), this.f, this.g, this.h);
      }
   }

   static class h implements bfn.f {
      private final bmb a;
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final float f;

      public h(buo var1, int var2, int var3, int var4, int var5) {
         this(new bmb(_snowman), _snowman, _snowman, _snowman, _snowman);
      }

      public h(blx var1, int var2, int var3, int var4) {
         this(new bmb(_snowman), _snowman, _snowman, 12, _snowman);
      }

      public h(blx var1, int var2, int var3, int var4, int var5) {
         this(new bmb(_snowman), _snowman, _snowman, _snowman, _snowman);
      }

      public h(bmb var1, int var2, int var3, int var4, int var5) {
         this(_snowman, _snowman, _snowman, _snowman, _snowman, 0.05F);
      }

      public h(bmb var1, int var2, int var3, int var4, int var5, float var6) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         return new bqv(new bmb(bmd.oV, this.b), new bmb(this.a.b(), this.c), this.d, this.e, this.f);
      }
   }

   static class i implements bfn.f {
      final aps a;
      final int b;
      final int c;
      private final float d;

      public i(aps var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = 0.05F;
      }

      @Nullable
      @Override
      public bqv a(aqa var1, Random var2) {
         bmb _snowman = new bmb(bmd.qR, 1);
         bne.a(_snowman, this.a, this.b);
         return new bqv(new bmb(bmd.oV, 1), _snowman, 12, this.c, this.d);
      }
   }

   static class j implements bfn.f {
      private final bmb a;
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final blx f;
      private final int g;
      private final float h;

      public j(blx var1, int var2, blx var3, int var4, int var5, int var6, int var7) {
         this.a = new bmb(_snowman);
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.b = _snowman;
         this.h = 0.05F;
      }

      @Override
      public bqv a(aqa var1, Random var2) {
         bmb _snowman = new bmb(bmd.oV, this.c);
         List<bnt> _snowmanx = gm.U.g().filter(var0 -> !var0.a().isEmpty() && bnu.a(var0)).collect(Collectors.toList());
         bnt _snowmanxx = _snowmanx.get(_snowman.nextInt(_snowmanx.size()));
         bmb _snowmanxxx = bnv.a(new bmb(this.a.b(), this.b), _snowmanxx);
         return new bqv(_snowman, new bmb(this.f, this.g), _snowmanxxx, this.d, this.e, this.h);
      }
   }

   static class k implements bfn.f {
      private final int a;
      private final cla<?> b;
      private final cxu.a c;
      private final int d;
      private final int e;

      public k(int var1, cla<?> var2, cxu.a var3, int var4, int var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Nullable
      @Override
      public bqv a(aqa var1, Random var2) {
         if (!(_snowman.l instanceof aag)) {
            return null;
         } else {
            aag _snowman = (aag)_snowman.l;
            fx _snowmanx = _snowman.a(this.b, _snowman.cB(), 100, true);
            if (_snowmanx != null) {
               bmb _snowmanxx = bmh.a(_snowman, _snowmanx.u(), _snowmanx.w(), (byte)2, true, true);
               bmh.a(_snowman, _snowmanxx);
               cxx.a(_snowmanxx, _snowmanx, "+", this.c);
               _snowmanxx.a(new of("filled_map." + this.b.i().toLowerCase(Locale.ROOT)));
               return new bqv(new bmb(bmd.oV, this.a), new bmb(bmd.mh), _snowmanxx, this.d, this.e, 0.2F);
            } else {
               return null;
            }
         }
      }
   }
}
