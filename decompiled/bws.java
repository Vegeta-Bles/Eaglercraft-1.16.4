import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class bws extends bue {
   public static final cfg b = cex.aj;
   public static final cey c = bys.a;
   public static final cey d = bys.b;
   public static final cey e = bys.c;
   public static final cey f = bys.d;
   public static final cey g = bys.e;
   private static final Map<gc, cey> h = bys.g.entrySet().stream().filter(var0 -> var0.getKey() != gc.a).collect(x.a());
   private static final ddh i = buo.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final ddh j = buo.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final ddh k = buo.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final ddh o = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final ddh p = buo.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<ceh, ddh> q;
   private final Object2IntMap<buo> r = new Object2IntOpenHashMap();
   private final Object2IntMap<buo> s = new Object2IntOpenHashMap();

   public bws(ceg.c var1) {
      super(_snowman, 1.0F);
      this.j(
         this.n
            .b()
            .a(b, Integer.valueOf(0))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
            .a(g, Boolean.valueOf(false))
      );
      this.q = ImmutableMap.copyOf(this.n.a().stream().filter(var0 -> var0.c(b) == 0).collect(Collectors.toMap(Function.identity(), bws::h)));
   }

   private static ddh h(ceh var0) {
      ddh _snowman = dde.a();
      if (_snowman.c(g)) {
         _snowman = i;
      }

      if (_snowman.c(c)) {
         _snowman = dde.a(_snowman, o);
      }

      if (_snowman.c(e)) {
         _snowman = dde.a(_snowman, p);
      }

      if (_snowman.c(d)) {
         _snowman = dde.a(_snowman, k);
      }

      if (_snowman.c(f)) {
         _snowman = dde.a(_snowman, j);
      }

      return _snowman.b() ? a : _snowman;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return this.a(_snowman, _snowman, _snowman) ? this.a(_snowman, _snowman, _snowman.c(b)) : bup.a.n();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.q.get(_snowman.a(b, Integer.valueOf(0)));
   }

   @Override
   public ceh a(bny var1) {
      return this.b(_snowman.p(), _snowman.a());
   }

   protected ceh b(brc var1, fx var2) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      if (!this.e(_snowmanx) && !_snowmanx.d(_snowman, _snowman, gc.b)) {
         ceh _snowmanxx = this.n();

         for (gc _snowmanxxx : gc.values()) {
            cey _snowmanxxxx = h.get(_snowmanxxx);
            if (_snowmanxxxx != null) {
               _snowmanxx = _snowmanxx.a(_snowmanxxxx, Boolean.valueOf(this.e(_snowman.d_(_snowman.a(_snowmanxxx)))));
            }
         }

         return _snowmanxx;
      } else {
         return this.n();
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      return _snowman.d_(_snowman).d(_snowman, _snowman, gc.b) || this.e(_snowman, _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      _snowman.j().a(_snowman, this, a(_snowman.t));
      if (_snowman.V().b(brt.a)) {
         if (!_snowman.a(_snowman, _snowman)) {
            _snowman.a(_snowman, false);
         }

         ceh _snowman = _snowman.d_(_snowman.c());
         boolean _snowmanx = _snowman.a(_snowman.k().o());
         int _snowmanxx = _snowman.c(b);
         if (!_snowmanx && _snowman.X() && this.a((brx)_snowman, _snowman) && _snowman.nextFloat() < 0.2F + (float)_snowmanxx * 0.03F) {
            _snowman.a(_snowman, false);
         } else {
            int _snowmanxxx = Math.min(15, _snowmanxx + _snowman.nextInt(3) / 2);
            if (_snowmanxx != _snowmanxxx) {
               _snowman = _snowman.a(b, Integer.valueOf(_snowmanxxx));
               _snowman.a(_snowman, _snowman, 4);
            }

            if (!_snowmanx) {
               if (!this.e(_snowman, _snowman)) {
                  fx _snowmanxxxx = _snowman.c();
                  if (!_snowman.d_(_snowmanxxxx).d(_snowman, _snowmanxxxx, gc.b) || _snowmanxx > 3) {
                     _snowman.a(_snowman, false);
                  }

                  return;
               }

               if (_snowmanxx == 15 && _snowman.nextInt(4) == 0 && !this.e(_snowman.d_(_snowman.c()))) {
                  _snowman.a(_snowman, false);
                  return;
               }
            }

            boolean _snowmanxxxx = _snowman.u(_snowman);
            int _snowmanxxxxx = _snowmanxxxx ? -50 : 0;
            this.a(_snowman, _snowman.g(), 300 + _snowmanxxxxx, _snowman, _snowmanxx);
            this.a(_snowman, _snowman.f(), 300 + _snowmanxxxxx, _snowman, _snowmanxx);
            this.a(_snowman, _snowman.c(), 250 + _snowmanxxxxx, _snowman, _snowmanxx);
            this.a(_snowman, _snowman.b(), 250 + _snowmanxxxxx, _snowman, _snowmanxx);
            this.a(_snowman, _snowman.d(), 300 + _snowmanxxxxx, _snowman, _snowmanxx);
            this.a(_snowman, _snowman.e(), 300 + _snowmanxxxxx, _snowman, _snowmanxx);
            fx.a _snowmanxxxxxx = new fx.a();

            for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 1; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx <= 1; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxx = -1; _snowmanxxxxxxxxx <= 4; _snowmanxxxxxxxxx++) {
                     if (_snowmanxxxxxxx != 0 || _snowmanxxxxxxxxx != 0 || _snowmanxxxxxxxx != 0) {
                        int _snowmanxxxxxxxxxx = 100;
                        if (_snowmanxxxxxxxxx > 1) {
                           _snowmanxxxxxxxxxx += (_snowmanxxxxxxxxx - 1) * 100;
                        }

                        _snowmanxxxxxx.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx);
                        int _snowmanxxxxxxxxxxx = this.a((brz)_snowman, _snowmanxxxxxx);
                        if (_snowmanxxxxxxxxxxx > 0) {
                           int _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx + 40 + _snowman.ad().a() * 7) / (_snowmanxx + 30);
                           if (_snowmanxxxx) {
                              _snowmanxxxxxxxxxxxx /= 2;
                           }

                           if (_snowmanxxxxxxxxxxxx > 0 && _snowman.nextInt(_snowmanxxxxxxxxxx) <= _snowmanxxxxxxxxxxxx && (!_snowman.X() || !this.a((brx)_snowman, _snowmanxxxxxx))) {
                              int _snowmanxxxxxxxxxxxxx = Math.min(15, _snowmanxx + _snowman.nextInt(5) / 4);
                              _snowman.a(_snowmanxxxxxx, this.a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxx), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean a(brx var1, fx var2) {
      return _snowman.t(_snowman) || _snowman.t(_snowman.f()) || _snowman.t(_snowman.g()) || _snowman.t(_snowman.d()) || _snowman.t(_snowman.e());
   }

   private int l(ceh var1) {
      return _snowman.b(cex.C) && _snowman.c(cex.C) ? 0 : this.s.getInt(_snowman.b());
   }

   private int m(ceh var1) {
      return _snowman.b(cex.C) && _snowman.c(cex.C) ? 0 : this.r.getInt(_snowman.b());
   }

   private void a(brx var1, fx var2, int var3, Random var4, int var5) {
      int _snowman = this.l(_snowman.d_(_snowman));
      if (_snowman.nextInt(_snowman) < _snowman) {
         ceh _snowmanx = _snowman.d_(_snowman);
         if (_snowman.nextInt(_snowman + 10) < 5 && !_snowman.t(_snowman)) {
            int _snowmanxx = Math.min(_snowman + _snowman.nextInt(5) / 4, 15);
            _snowman.a(_snowman, this.a(_snowman, _snowman, _snowmanxx), 3);
         } else {
            _snowman.a(_snowman, false);
         }

         buo _snowmanxx = _snowmanx.b();
         if (_snowmanxx instanceof caz) {
            caz.a(_snowman, _snowman);
         }
      }
   }

   private ceh a(bry var1, fx var2, int var3) {
      ceh _snowman = a(_snowman, _snowman);
      return _snowman.a(bup.bN) ? _snowman.a(b, Integer.valueOf(_snowman)) : _snowman;
   }

   private boolean e(brc var1, fx var2) {
      for (gc _snowman : gc.values()) {
         if (this.e(_snowman.d_(_snowman.a(_snowman)))) {
            return true;
         }
      }

      return false;
   }

   private int a(brz var1, fx var2) {
      if (!_snowman.w(_snowman)) {
         return 0;
      } else {
         int _snowman = 0;

         for (gc _snowmanx : gc.values()) {
            ceh _snowmanxx = _snowman.d_(_snowman.a(_snowmanx));
            _snowman = Math.max(this.m(_snowmanxx), _snowman);
         }

         return _snowman;
      }
   }

   @Override
   protected boolean e(ceh var1) {
      return this.m(_snowman) > 0;
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      super.b(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.J().a(_snowman, this, a(_snowman.t));
   }

   private static int a(Random var0) {
      return 30 + _snowman.nextInt(10);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, c, d, e, f, g);
   }

   private void a(buo var1, int var2, int var3) {
      this.r.put(_snowman, _snowman);
      this.s.put(_snowman, _snowman);
   }

   public static void c() {
      bws _snowman = (bws)bup.bN;
      _snowman.a(bup.n, 5, 20);
      _snowman.a(bup.o, 5, 20);
      _snowman.a(bup.p, 5, 20);
      _snowman.a(bup.q, 5, 20);
      _snowman.a(bup.r, 5, 20);
      _snowman.a(bup.s, 5, 20);
      _snowman.a(bup.hK, 5, 20);
      _snowman.a(bup.hL, 5, 20);
      _snowman.a(bup.hM, 5, 20);
      _snowman.a(bup.hN, 5, 20);
      _snowman.a(bup.hO, 5, 20);
      _snowman.a(bup.hP, 5, 20);
      _snowman.a(bup.dQ, 5, 20);
      _snowman.a(bup.ih, 5, 20);
      _snowman.a(bup.ii, 5, 20);
      _snowman.a(bup.ij, 5, 20);
      _snowman.a(bup.il, 5, 20);
      _snowman.a(bup.ik, 5, 20);
      _snowman.a(bup.cJ, 5, 20);
      _snowman.a(bup.im, 5, 20);
      _snowman.a(bup.in, 5, 20);
      _snowman.a(bup.io, 5, 20);
      _snowman.a(bup.iq, 5, 20);
      _snowman.a(bup.ip, 5, 20);
      _snowman.a(bup.bQ, 5, 20);
      _snowman.a(bup.ep, 5, 20);
      _snowman.a(bup.eo, 5, 20);
      _snowman.a(bup.eq, 5, 20);
      _snowman.a(bup.gl, 5, 20);
      _snowman.a(bup.gm, 5, 20);
      _snowman.a(bup.J, 5, 5);
      _snowman.a(bup.K, 5, 5);
      _snowman.a(bup.L, 5, 5);
      _snowman.a(bup.M, 5, 5);
      _snowman.a(bup.N, 5, 5);
      _snowman.a(bup.O, 5, 5);
      _snowman.a(bup.U, 5, 5);
      _snowman.a(bup.P, 5, 5);
      _snowman.a(bup.Q, 5, 5);
      _snowman.a(bup.R, 5, 5);
      _snowman.a(bup.S, 5, 5);
      _snowman.a(bup.T, 5, 5);
      _snowman.a(bup.ab, 5, 5);
      _snowman.a(bup.ac, 5, 5);
      _snowman.a(bup.ad, 5, 5);
      _snowman.a(bup.ae, 5, 5);
      _snowman.a(bup.af, 5, 5);
      _snowman.a(bup.ag, 5, 5);
      _snowman.a(bup.V, 5, 5);
      _snowman.a(bup.W, 5, 5);
      _snowman.a(bup.X, 5, 5);
      _snowman.a(bup.Y, 5, 5);
      _snowman.a(bup.Z, 5, 5);
      _snowman.a(bup.aa, 5, 5);
      _snowman.a(bup.ah, 30, 60);
      _snowman.a(bup.ai, 30, 60);
      _snowman.a(bup.aj, 30, 60);
      _snowman.a(bup.ak, 30, 60);
      _snowman.a(bup.al, 30, 60);
      _snowman.a(bup.am, 30, 60);
      _snowman.a(bup.bI, 30, 20);
      _snowman.a(bup.bH, 15, 100);
      _snowman.a(bup.aR, 60, 100);
      _snowman.a(bup.aS, 60, 100);
      _snowman.a(bup.aT, 60, 100);
      _snowman.a(bup.gU, 60, 100);
      _snowman.a(bup.gV, 60, 100);
      _snowman.a(bup.gW, 60, 100);
      _snowman.a(bup.gX, 60, 100);
      _snowman.a(bup.gY, 60, 100);
      _snowman.a(bup.gZ, 60, 100);
      _snowman.a(bup.bp, 60, 100);
      _snowman.a(bup.bq, 60, 100);
      _snowman.a(bup.br, 60, 100);
      _snowman.a(bup.bs, 60, 100);
      _snowman.a(bup.bt, 60, 100);
      _snowman.a(bup.bu, 60, 100);
      _snowman.a(bup.bv, 60, 100);
      _snowman.a(bup.bw, 60, 100);
      _snowman.a(bup.bx, 60, 100);
      _snowman.a(bup.by, 60, 100);
      _snowman.a(bup.bz, 60, 100);
      _snowman.a(bup.bB, 60, 100);
      _snowman.a(bup.bA, 60, 100);
      _snowman.a(bup.aY, 30, 60);
      _snowman.a(bup.aZ, 30, 60);
      _snowman.a(bup.ba, 30, 60);
      _snowman.a(bup.bb, 30, 60);
      _snowman.a(bup.bc, 30, 60);
      _snowman.a(bup.bd, 30, 60);
      _snowman.a(bup.be, 30, 60);
      _snowman.a(bup.bf, 30, 60);
      _snowman.a(bup.bg, 30, 60);
      _snowman.a(bup.bh, 30, 60);
      _snowman.a(bup.bi, 30, 60);
      _snowman.a(bup.bj, 30, 60);
      _snowman.a(bup.bk, 30, 60);
      _snowman.a(bup.bl, 30, 60);
      _snowman.a(bup.bm, 30, 60);
      _snowman.a(bup.bn, 30, 60);
      _snowman.a(bup.dP, 15, 100);
      _snowman.a(bup.gS, 5, 5);
      _snowman.a(bup.gA, 60, 20);
      _snowman.a(bup.nb, 15, 20);
      _snowman.a(bup.gB, 60, 20);
      _snowman.a(bup.gC, 60, 20);
      _snowman.a(bup.gD, 60, 20);
      _snowman.a(bup.gE, 60, 20);
      _snowman.a(bup.gF, 60, 20);
      _snowman.a(bup.gG, 60, 20);
      _snowman.a(bup.gH, 60, 20);
      _snowman.a(bup.gI, 60, 20);
      _snowman.a(bup.gJ, 60, 20);
      _snowman.a(bup.gK, 60, 20);
      _snowman.a(bup.gL, 60, 20);
      _snowman.a(bup.gM, 60, 20);
      _snowman.a(bup.gN, 60, 20);
      _snowman.a(bup.gO, 60, 20);
      _snowman.a(bup.gP, 60, 20);
      _snowman.a(bup.gQ, 60, 20);
      _snowman.a(bup.ke, 30, 60);
      _snowman.a(bup.kY, 60, 60);
      _snowman.a(bup.lQ, 60, 60);
      _snowman.a(bup.lY, 30, 20);
      _snowman.a(bup.na, 5, 20);
      _snowman.a(bup.mg, 60, 100);
      _snowman.a(bup.nd, 5, 20);
      _snowman.a(bup.nc, 30, 20);
   }
}
