import java.util.List;
import java.util.Random;

public interface gw {
   gw a = (var0, var1) -> var1;

   bmb dispense(fy var1, bmb var2);

   static void c() {
      bwa.a(bmd.kd, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            bgc _snowman = new bgc(_snowman, _snowman.a(), _snowman.b(), _snowman.c());
            _snowman.d = bga.a.b;
            return _snowman;
         }
      });
      bwa.a(bmd.ql, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            bgc _snowman = new bgc(_snowman, _snowman.a(), _snowman.b(), _snowman.c());
            _snowman.b(_snowman);
            _snowman.d = bga.a.b;
            return _snowman;
         }
      });
      bwa.a(bmd.qk, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            bga _snowman = new bgr(_snowman, _snowman.a(), _snowman.b(), _snowman.c());
            _snowman.d = bga.a.b;
            return _snowman;
         }
      });
      bwa.a(bmd.mg, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            return x.a(new bgu(_snowman, _snowman.a(), _snowman.b(), _snowman.c()), var1x -> var1x.b(_snowman));
         }
      });
      bwa.a(bmd.lQ, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            return x.a(new bgq(_snowman, _snowman.a(), _snowman.b(), _snowman.c()), var1x -> var1x.b(_snowman));
         }
      });
      bwa.a(bmd.oR, new gt() {
         @Override
         protected bgm a(brx var1, gk var2, bmb var3) {
            return x.a(new bgw(_snowman, _snowman.a(), _snowman.b(), _snowman.c()), var1x -> var1x.b(_snowman));
         }

         @Override
         protected float a() {
            return super.a() * 0.5F;
         }

         @Override
         protected float b() {
            return super.b() * 1.25F;
         }
      });
      bwa.a(bmd.qj, new gw() {
         @Override
         public bmb dispense(fy var1, bmb var2) {
            return (new gt() {
               @Override
               protected bgm a(brx var1, gk var2, bmb var3) {
                  return x.a(new bgx(_snowman, _snowman.a(), _snowman.b(), _snowman.c()), var1x -> var1x.b(_snowman));
               }

               @Override
               protected float a() {
                  return super.a() * 0.5F;
               }

               @Override
               protected float b() {
                  return super.b() * 1.25F;
               }
            }).dispense(_snowman, _snowman);
         }
      });
      bwa.a(bmd.qm, new gw() {
         @Override
         public bmb dispense(fy var1, bmb var2) {
            return (new gt() {
               @Override
               protected bgm a(brx var1, gk var2, bmb var3) {
                  return x.a(new bgx(_snowman, _snowman.a(), _snowman.b(), _snowman.c()), var1x -> var1x.b(_snowman));
               }

               @Override
               protected float a() {
                  return super.a() * 0.5F;
               }

               @Override
               protected float b() {
                  return super.b() * 1.25F;
               }
            }).dispense(_snowman, _snowman);
         }
      });
      gv _snowman = new gv() {
         @Override
         public bmb a(fy var1, bmb var2) {
            gc _snowman = _snowman.e().c(bwa.a);
            aqe<?> _snowmanx = ((bna)_snowman.b()).a(_snowman.o());
            _snowmanx.a(_snowman.h(), _snowman, null, _snowman.d().a(_snowman), aqp.o, _snowman != gc.b, false);
            _snowman.g(1);
            return _snowman;
         }
      };

      for (bna _snowmanx : bna.f()) {
         bwa.a(_snowmanx, _snowman);
      }

      bwa.a(bmd.pC, new gv() {
         @Override
         public bmb a(fy var1, bmb var2) {
            gc _snowman = _snowman.e().c(bwa.a);
            fx _snowmanx = _snowman.d().a(_snowman);
            brx _snowmanxx = _snowman.h();
            bcn _snowmanxxx = new bcn(_snowmanxx, (double)_snowmanx.u() + 0.5, (double)_snowmanx.v(), (double)_snowmanx.w() + 0.5);
            aqe.a(_snowmanxx, null, _snowmanxxx, _snowman.o());
            _snowmanxxx.p = _snowman.o();
            _snowmanxx.c(_snowmanxxx);
            _snowman.g(1);
            return _snowman;
         }
      });
      bwa.a(bmd.lO, new gx() {
         @Override
         public bmb a(fy var1, bmb var2) {
            fx _snowman = _snowman.d().a(_snowman.e().c(bwa.a));
            List<aqm> _snowmanx = _snowman.h().a(aqm.class, new dci(_snowman), var0 -> {
               if (!(var0 instanceof ara)) {
                  return false;
               } else {
                  ara _snowmanxx = (ara)var0;
                  return !_snowmanxx.M_() && _snowmanxx.L_();
               }
            });
            if (!_snowmanx.isEmpty()) {
               ((ara)_snowmanx.get(0)).a(adr.e);
               _snowman.g(1);
               this.a(true);
               return _snowman;
            } else {
               return super.a(_snowman, _snowman);
            }
         }
      });
      gv _snowmanx = new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            fx _snowman = _snowman.d().a(_snowman.e().c(bwa.a));

            for (bbb _snowmanx : _snowman.h().a(bbb.class, new dci(_snowman), var0 -> var0.aX() && var0.fs())) {
               if (_snowmanx.l(_snowman) && !_snowmanx.ft() && _snowmanx.eW()) {
                  _snowmanx.a_(401, _snowman.a(1));
                  this.a(true);
                  return _snowman;
               }
            }

            return super.a(_snowman, _snowman);
         }
      };
      bwa.a(bmd.pG, _snowmanx);
      bwa.a(bmd.pD, _snowmanx);
      bwa.a(bmd.pE, _snowmanx);
      bwa.a(bmd.pF, _snowmanx);
      bwa.a(bmd.fM, _snowmanx);
      bwa.a(bmd.fN, _snowmanx);
      bwa.a(bmd.fV, _snowmanx);
      bwa.a(bmd.fX, _snowmanx);
      bwa.a(bmd.fY, _snowmanx);
      bwa.a(bmd.gb, _snowmanx);
      bwa.a(bmd.fT, _snowmanx);
      bwa.a(bmd.fZ, _snowmanx);
      bwa.a(bmd.fP, _snowmanx);
      bwa.a(bmd.fU, _snowmanx);
      bwa.a(bmd.fR, _snowmanx);
      bwa.a(bmd.fO, _snowmanx);
      bwa.a(bmd.fS, _snowmanx);
      bwa.a(bmd.fW, _snowmanx);
      bwa.a(bmd.ga, _snowmanx);
      bwa.a(bmd.fQ, _snowmanx);
      bwa.a(bmd.cy, new gx() {
         @Override
         public bmb a(fy var1, bmb var2) {
            fx _snowman = _snowman.d().a(_snowman.e().c(bwa.a));

            for (bba _snowmanx : _snowman.h().a(bba.class, new dci(_snowman), var0 -> var0.aX() && !var0.eM())) {
               if (_snowmanx.eW() && _snowmanx.a_(499, _snowman)) {
                  _snowman.g(1);
                  this.a(true);
                  return _snowman;
               }
            }

            return super.a(_snowman, _snowman);
         }
      });
      bwa.a(bmd.po, new gv() {
         @Override
         public bmb a(fy var1, bmb var2) {
            gc _snowman = _snowman.e().c(bwa.a);
            bgh _snowmanx = new bgh(_snowman.h(), _snowman, _snowman.a(), _snowman.b(), _snowman.a(), true);
            gw.a(_snowman, _snowmanx, _snowman);
            _snowmanx.c((double)_snowman.i(), (double)_snowman.j(), (double)_snowman.k(), 0.5F, 1.0F);
            _snowman.h().c(_snowmanx);
            _snowman.g(1);
            return _snowman;
         }

         @Override
         protected void a(fy var1) {
            _snowman.h().c(1004, _snowman.d(), 0);
         }
      });
      bwa.a(bmd.oS, new gv() {
         @Override
         public bmb a(fy var1, bmb var2) {
            gc _snowman = _snowman.e().c(bwa.a);
            gk _snowmanx = bwa.a(_snowman);
            double _snowmanxx = _snowmanx.a() + (double)((float)_snowman.i() * 0.3F);
            double _snowmanxxx = _snowmanx.b() + (double)((float)_snowman.j() * 0.3F);
            double _snowmanxxxx = _snowmanx.c() + (double)((float)_snowman.k() * 0.3F);
            brx _snowmanxxxxx = _snowman.h();
            Random _snowmanxxxxxx = _snowmanxxxxx.t;
            double _snowmanxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.i();
            double _snowmanxxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.j();
            double _snowmanxxxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.k();
            _snowmanxxxxx.c(x.a(new bgp(_snowmanxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx), var1x -> var1x.b(_snowman)));
            _snowman.g(1);
            return _snowman;
         }

         @Override
         protected void a(fy var1) {
            _snowman.h().c(1018, _snowman.d(), 0);
         }
      });
      bwa.a(bmd.lR, new gu(bhn.b.a));
      bwa.a(bmd.qp, new gu(bhn.b.b));
      bwa.a(bmd.qq, new gu(bhn.b.c));
      bwa.a(bmd.qr, new gu(bhn.b.d));
      bwa.a(bmd.qt, new gu(bhn.b.f));
      bwa.a(bmd.qs, new gu(bhn.b.e));
      gw _snowmanxx = new gv() {
         private final gv b = new gv();

         @Override
         public bmb a(fy var1, bmb var2) {
            bko _snowman = (bko)_snowman.b();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            brx _snowmanxx = _snowman.h();
            if (_snowman.a(null, _snowmanxx, _snowmanx, null)) {
               _snowman.a(_snowmanxx, _snowman, _snowmanx);
               return new bmb(bmd.lK);
            } else {
               return this.b.dispense(_snowman, _snowman);
            }
         }
      };
      bwa.a(bmd.lM, _snowmanxx);
      bwa.a(bmd.lL, _snowmanxx);
      bwa.a(bmd.lV, _snowmanxx);
      bwa.a(bmd.lW, _snowmanxx);
      bwa.a(bmd.lU, _snowmanxx);
      bwa.a(bmd.lX, _snowmanxx);
      bwa.a(bmd.lK, new gv() {
         private final gv b = new gv();

         @Override
         public bmb a(fy var1, bmb var2) {
            bry _snowman = _snowman.h();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            buo _snowmanxxx = _snowmanxx.b();
            if (_snowmanxxx instanceof but) {
               cuw _snowmanxxxx = ((but)_snowmanxxx).b(_snowman, _snowmanx, _snowmanxx);
               if (!(_snowmanxxxx instanceof cuv)) {
                  return super.a(_snowman, _snowman);
               } else {
                  blx _snowmanxxxxx = _snowmanxxxx.a();
                  _snowman.g(1);
                  if (_snowman.a()) {
                     return new bmb(_snowmanxxxxx);
                  } else {
                     if (_snowman.<ccs>g().a(new bmb(_snowmanxxxxx)) < 0) {
                        this.b.dispense(_snowman, new bmb(_snowmanxxxxx));
                     }

                     return _snowman;
                  }
               }
            } else {
               return super.a(_snowman, _snowman);
            }
         }
      });
      bwa.a(bmd.ka, new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            brx _snowman = _snowman.h();
            this.a(true);
            gc _snowmanx = _snowman.e().c(bwa.a);
            fx _snowmanxx = _snowman.d().a(_snowmanx);
            ceh _snowmanxxx = _snowman.d_(_snowmanxx);
            if (bue.a(_snowman, _snowmanxx, _snowmanx)) {
               _snowman.a(_snowmanxx, bue.a(_snowman, _snowmanxx));
            } else if (buy.h(_snowmanxxx)) {
               _snowman.a(_snowmanxx, _snowmanxxx.a(cex.r, Boolean.valueOf(true)));
            } else if (_snowmanxxx.b() instanceof caz) {
               caz.a(_snowman, _snowmanxx);
               _snowman.a(_snowmanxx, false);
            } else {
               this.a(false);
            }

            if (this.a() && _snowman.a(1, _snowman.t, null)) {
               _snowman.e(0);
            }

            return _snowman;
         }
      });
      bwa.a(bmd.mK, new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            this.a(true);
            brx _snowman = _snowman.h();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            if (!bkj.a(_snowman, _snowman, _snowmanx) && !bkj.a(_snowman, _snowman, _snowmanx, null)) {
               this.a(false);
            } else if (!_snowman.v) {
               _snowman.c(2005, _snowmanx, 0);
            }

            return _snowman;
         }
      });
      bwa.a(bup.bH, new gv() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            brx _snowman = _snowman.h();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            bcw _snowmanxx = new bcw(_snowman, (double)_snowmanx.u() + 0.5, (double)_snowmanx.v(), (double)_snowmanx.w() + 0.5, null);
            _snowman.c(_snowmanxx);
            _snowman.a(null, _snowmanxx.cD(), _snowmanxx.cE(), _snowmanxx.cH(), adq.pb, adr.e, 1.0F, 1.0F);
            _snowman.g(1);
            return _snowman;
         }
      });
      gw _snowmanxxx = new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            this.a(bjy.a(_snowman, _snowman));
            return _snowman;
         }
      };
      bwa.a(bmd.pi, _snowmanxxx);
      bwa.a(bmd.ph, _snowmanxxx);
      bwa.a(bmd.pj, _snowmanxxx);
      bwa.a(bmd.pe, _snowmanxxx);
      bwa.a(bmd.pg, _snowmanxxx);
      bwa.a(bmd.pf, new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            brx _snowman = _snowman.h();
            gc _snowmanx = _snowman.e().c(bwa.a);
            fx _snowmanxx = _snowman.d().a(_snowmanx);
            if (_snowman.w(_snowmanxx) && cbv.b(_snowman, _snowmanxx, _snowman)) {
               _snowman.a(_snowmanxx, bup.fe.n().a(bzv.a, Integer.valueOf(_snowmanx.n() == gc.a.b ? 0 : _snowmanx.f().d() * 4)), 3);
               ccj _snowmanxxx = _snowman.c(_snowmanxx);
               if (_snowmanxxx instanceof cdg) {
                  cbv.a(_snowman, _snowmanxx, (cdg)_snowmanxxx);
               }

               _snowman.g(1);
               this.a(true);
            } else {
               this.a(bjy.a(_snowman, _snowman));
            }

            return _snowman;
         }
      });
      bwa.a(bup.cU, new gx() {
         @Override
         protected bmb a(fy var1, bmb var2) {
            brx _snowman = _snowman.h();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            bvb _snowmanxx = (bvb)bup.cU;
            if (_snowman.w(_snowmanx) && _snowmanxx.a(_snowman, _snowmanx)) {
               if (!_snowman.v) {
                  _snowman.a(_snowmanx, _snowmanxx.n(), 3);
               }

               _snowman.g(1);
               this.a(true);
            } else {
               this.a(bjy.a(_snowman, _snowman));
            }

            return _snowman;
         }
      });
      bwa.a(bup.iP.h(), new gz());

      for (bkx _snowmanxxxx : bkx.values()) {
         bwa.a(bzs.a(_snowmanxxxx).h(), new gz());
      }

      bwa.a(bmd.nw.h(), new gx() {
         private final gv b = new gv();

         private bmb a(fy var1, bmb var2, bmb var3) {
            _snowman.g(1);
            if (_snowman.a()) {
               return _snowman.i();
            } else {
               if (_snowman.<ccs>g().a(_snowman.i()) < 0) {
                  this.b.dispense(_snowman, _snowman.i());
               }

               return _snowman;
            }
         }

         @Override
         public bmb a(fy var1, bmb var2) {
            this.a(false);
            aag _snowman = _snowman.h();
            fx _snowmanx = _snowman.d().a(_snowman.e().c(bwa.a));
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            if (_snowmanxx.a(aed.aj, var0 -> var0.b(buk.b)) && _snowmanxx.c(buk.b) >= 5) {
               ((buk)_snowmanxx.b()).a(_snowman, _snowmanxx, _snowmanx, null, ccg.b.b);
               this.a(true);
               return this.a(_snowman, _snowman, new bmb(bmd.rt));
            } else if (_snowman.b(_snowmanx).a(aef.b)) {
               this.a(true);
               return this.a(_snowman, _snowman, bnv.a(new bmb(bmd.nv), bnw.b));
            } else {
               return super.a(_snowman, _snowman);
            }
         }
      });
      bwa.a(bmd.dq, new gx() {
         @Override
         public bmb a(fy var1, bmb var2) {
            gc _snowman = _snowman.e().c(bwa.a);
            fx _snowmanx = _snowman.d().a(_snowman);
            brx _snowmanxx = _snowman.h();
            ceh _snowmanxxx = _snowmanxx.d_(_snowmanx);
            this.a(true);
            if (_snowmanxxx.a(bup.nj)) {
               if (_snowmanxxx.c(bzj.a) != 4) {
                  bzj.a(_snowmanxx, _snowmanx, _snowmanxxx);
                  _snowman.g(1);
               } else {
                  this.a(false);
               }

               return _snowman;
            } else {
               return super.a(_snowman, _snowman);
            }
         }
      });
      bwa.a(bmd.ng.h(), new gy());
   }

   static void a(fy var0, aqa var1, gc var2) {
      _snowman.d(
         _snowman.a() + (double)_snowman.i() * (0.5000099999997474 - (double)_snowman.cy() / 2.0),
         _snowman.b() + (double)_snowman.j() * (0.5000099999997474 - (double)_snowman.cz() / 2.0) - (double)_snowman.cz() / 2.0,
         _snowman.c() + (double)_snowman.k() * (0.5000099999997474 - (double)_snowman.cy() / 2.0)
      );
   }
}
