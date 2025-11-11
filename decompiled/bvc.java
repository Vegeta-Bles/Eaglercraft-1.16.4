public class bvc extends buo {
   public static final cfg a = cex.ar;
   private static final ddh c = a(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
   protected static final ddh b = dde.a(
      dde.b(), dde.a(a(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), a(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), a(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), c), dcr.e
   );

   public bvc(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public ddh a_(ceh var1, brc var2, fx var3) {
      return c;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      int _snowman = _snowman.c(a);
      float _snowmanx = (float)_snowman.v() + (6.0F + (float)(3 * _snowman)) / 16.0F;
      if (!_snowman.v && _snowman.bq() && _snowman > 0 && _snowman.cE() <= (double)_snowmanx) {
         _snowman.am();
         this.a(_snowman, _snowman, _snowman, _snowman - 1);
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.a()) {
         return aou.c;
      } else {
         int _snowmanx = _snowman.c(a);
         blx _snowmanxx = _snowman.b();
         if (_snowmanxx == bmd.lL) {
            if (_snowmanx < 3 && !_snowman.v) {
               if (!_snowman.bC.d) {
                  _snowman.a(_snowman, new bmb(bmd.lK));
               }

               _snowman.a(aea.U);
               this.a(_snowman, _snowman, _snowman, 3);
               _snowman.a(null, _snowman, adq.bj, adr.e, 1.0F, 1.0F);
            }

            return aou.a(_snowman.v);
         } else if (_snowmanxx == bmd.lK) {
            if (_snowmanx == 3 && !_snowman.v) {
               if (!_snowman.bC.d) {
                  _snowman.g(1);
                  if (_snowman.a()) {
                     _snowman.a(_snowman, new bmb(bmd.lL));
                  } else if (!_snowman.bm.e(new bmb(bmd.lL))) {
                     _snowman.a(new bmb(bmd.lL), false);
                  }
               }

               _snowman.a(aea.V);
               this.a(_snowman, _snowman, _snowman, 0);
               _snowman.a(null, _snowman, adq.bm, adr.e, 1.0F, 1.0F);
            }

            return aou.a(_snowman.v);
         } else if (_snowmanxx == bmd.nw) {
            if (_snowmanx > 0 && !_snowman.v) {
               if (!_snowman.bC.d) {
                  bmb _snowmanxxx = bnv.a(new bmb(bmd.nv), bnw.b);
                  _snowman.a(aea.V);
                  _snowman.g(1);
                  if (_snowman.a()) {
                     _snowman.a(_snowman, _snowmanxxx);
                  } else if (!_snowman.bm.e(_snowmanxxx)) {
                     _snowman.a(_snowmanxxx, false);
                  } else if (_snowman instanceof aah) {
                     ((aah)_snowman).a(_snowman.bo);
                  }
               }

               _snowman.a(null, _snowman, adq.bb, adr.e, 1.0F, 1.0F);
               this.a(_snowman, _snowman, _snowman, _snowmanx - 1);
            }

            return aou.a(_snowman.v);
         } else if (_snowmanxx == bmd.nv && bnv.d(_snowman) == bnw.b) {
            if (_snowmanx < 3 && !_snowman.v) {
               if (!_snowman.bC.d) {
                  bmb _snowmanxxx = new bmb(bmd.nw);
                  _snowman.a(aea.V);
                  _snowman.a(_snowman, _snowmanxxx);
                  if (_snowman instanceof aah) {
                     ((aah)_snowman).a(_snowman.bo);
                  }
               }

               _snowman.a(null, _snowman, adq.ba, adr.e, 1.0F, 1.0F);
               this.a(_snowman, _snowman, _snowman, _snowmanx + 1);
            }

            return aou.a(_snowman.v);
         } else {
            if (_snowmanx > 0 && _snowmanxx instanceof blb) {
               blb _snowmanxxx = (blb)_snowmanxx;
               if (_snowmanxxx.a(_snowman) && !_snowman.v) {
                  _snowmanxxx.c(_snowman);
                  this.a(_snowman, _snowman, _snowman, _snowmanx - 1);
                  _snowman.a(aea.W);
                  return aou.a;
               }
            }

            if (_snowmanx > 0 && _snowmanxx instanceof bke) {
               if (cca.b(_snowman) > 0 && !_snowman.v) {
                  bmb _snowmanxxx = _snowman.i();
                  _snowmanxxx.e(1);
                  cca.c(_snowmanxxx);
                  _snowman.a(aea.X);
                  if (!_snowman.bC.d) {
                     _snowman.g(1);
                     this.a(_snowman, _snowman, _snowman, _snowmanx - 1);
                  }

                  if (_snowman.a()) {
                     _snowman.a(_snowman, _snowmanxxx);
                  } else if (!_snowman.bm.e(_snowmanxxx)) {
                     _snowman.a(_snowmanxxx, false);
                  } else if (_snowman instanceof aah) {
                     ((aah)_snowman).a(_snowman.bo);
                  }
               }

               return aou.a(_snowman.v);
            } else if (_snowmanx > 0 && _snowmanxx instanceof bkh) {
               buo _snowmanxxxx = ((bkh)_snowmanxx).e();
               if (_snowmanxxxx instanceof bzs && !_snowman.s_()) {
                  bmb _snowmanxxxxx = new bmb(bup.iP, 1);
                  if (_snowman.n()) {
                     _snowmanxxxxx.c(_snowman.o().g());
                  }

                  _snowman.a(_snowman, _snowmanxxxxx);
                  this.a(_snowman, _snowman, _snowman, _snowmanx - 1);
                  _snowman.a(aea.Y);
                  return aou.a;
               } else {
                  return aou.b;
               }
            } else {
               return aou.c;
            }
         }
      }
   }

   public void a(brx var1, fx var2, ceh var3, int var4) {
      _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(afm.a(_snowman, 0, 3))), 2);
      _snowman.c(_snowman, this);
   }

   @Override
   public void c(brx var1, fx var2) {
      if (_snowman.t.nextInt(20) == 1) {
         float _snowman = _snowman.v(_snowman).a(_snowman);
         if (!(_snowman < 0.15F)) {
            ceh _snowmanx = _snowman.d_(_snowman);
            if (_snowmanx.c(a) < 3) {
               _snowman.a(_snowman, _snowmanx.a(a), 2);
            }
         }
      }
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return _snowman.c(a);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
