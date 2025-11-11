public class bii extends bic {
   private final bim d;
   private long e;
   public final aon c = new apa(2) {
      @Override
      public void X_() {
         bii.this.a(this);
         super.X_();
      }
   };
   private final bjm f = new bjm() {
      @Override
      public void X_() {
         bii.this.a(this);
         super.X_();
      }
   };

   public bii(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bii(int var1, bfv var2, final bim var3) {
      super(bje.w, _snowman);
      this.d = _snowman;
      this.a(new bjr(this.c, 0, 15, 15) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() == bmd.nf;
         }
      });
      this.a(new bjr(this.c, 1, 15, 52) {
         @Override
         public boolean a(bmb var1) {
            blx _snowman = _snowman.b();
            return _snowman == bmd.mb || _snowman == bmd.pc || _snowman == bmd.dP;
         }
      });
      this.a(new bjr(this.f, 2, 145, 39) {
         @Override
         public boolean a(bmb var1) {
            return false;
         }

         @Override
         public bmb a(bfw var1, bmb var2) {
            bii.this.a.get(0).a(1);
            bii.this.a.get(1).a(1);
            _snowman.b().b(_snowman, _snowman.l, _snowman);
            _snowman.a((var1x, var2x) -> {
               long _snowman = var1x.T();
               if (bii.this.e != _snowman) {
                  var1x.a(null, var2x, adq.pI, adr.e, 1.0F, 1.0F);
                  bii.this.e = _snowman;
               }
            });
            return super.a(_snowman, _snowman);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.d, _snowman, bup.lV);
   }

   @Override
   public void a(aon var1) {
      bmb _snowman = this.c.a(0);
      bmb _snowmanx = this.c.a(1);
      bmb _snowmanxx = this.f.a(2);
      if (_snowmanxx.a() || !_snowman.a() && !_snowmanx.a()) {
         if (!_snowman.a() && !_snowmanx.a()) {
            this.a(_snowman, _snowmanx, _snowmanxx);
         }
      } else {
         this.f.b(2);
      }
   }

   private void a(bmb var1, bmb var2, bmb var3) {
      this.d.a((var4, var5) -> {
         blx _snowman = _snowman.b();
         cxx _snowmanx = bmh.a(_snowman, var4);
         if (_snowmanx != null) {
            bmb _snowmanxx;
            if (_snowman == bmd.mb && !_snowmanx.h && _snowmanx.f < 4) {
               _snowmanxx = _snowman.i();
               _snowmanxx.e(1);
               _snowmanxx.p().b("map_scale_direction", 1);
               this.c();
            } else if (_snowman == bmd.dP && !_snowmanx.h) {
               _snowmanxx = _snowman.i();
               _snowmanxx.e(1);
               _snowmanxx.p().a("map_to_lock", true);
               this.c();
            } else {
               if (_snowman != bmd.pc) {
                  this.f.b(2);
                  this.c();
                  return;
               }

               _snowmanxx = _snowman.i();
               _snowmanxx.e(2);
               this.c();
            }

            if (!bmb.b(_snowmanxx, _snowman)) {
               this.f.a(2, _snowmanxx);
               this.c();
            }
         }
      });
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return _snowman.c != this.f && super.a(_snowman, _snowman);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         blx _snowmanxxx = _snowmanxx.b();
         _snowman = _snowmanxx.i();
         if (_snowman == 2) {
            _snowmanxxx.b(_snowmanxx, _snowman.l, _snowman);
            if (!this.a(_snowmanxx, 3, 39, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman != 1 && _snowman != 0) {
            if (_snowmanxxx == bmd.nf) {
               if (!this.a(_snowmanxx, 0, 1, false)) {
                  return bmb.b;
               }
            } else if (_snowmanxxx != bmd.mb && _snowmanxxx != bmd.pc && _snowmanxxx != bmd.dP) {
               if (_snowman >= 3 && _snowman < 30) {
                  if (!this.a(_snowmanxx, 30, 39, false)) {
                     return bmb.b;
                  }
               } else if (_snowman >= 30 && _snowman < 39 && !this.a(_snowmanxx, 3, 30, false)) {
                  return bmb.b;
               }
            } else if (!this.a(_snowmanxx, 1, 2, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 3, 39, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         }

         _snowmanx.d();
         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         _snowmanx.a(_snowman, _snowmanxx);
         this.c();
      }

      return _snowman;
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.f.b(2);
      this.d.a((var2, var3) -> this.a(_snowman, _snowman.l, this.c));
   }
}
