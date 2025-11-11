public class bjc extends bic {
   private final bim c;
   private final biq d = biq.a();
   private Runnable e = () -> {
   };
   private final bjr f;
   private final bjr g;
   private final bjr h;
   private final bjr i;
   private long j;
   private final aon k = new apa(3) {
      @Override
      public void X_() {
         super.X_();
         bjc.this.a(this);
         bjc.this.e.run();
      }
   };
   private final aon l = new apa(1) {
      @Override
      public void X_() {
         super.X_();
         bjc.this.e.run();
      }
   };

   public bjc(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bjc(int var1, bfv var2, final bim var3) {
      super(bje.r, _snowman);
      this.c = _snowman;
      this.f = this.a(new bjr(this.k, 0, 13, 26) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() instanceof bke;
         }
      });
      this.g = this.a(new bjr(this.k, 1, 33, 26) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() instanceof bky;
         }
      });
      this.h = this.a(new bjr(this.k, 2, 23, 45) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() instanceof bkf;
         }
      });
      this.i = this.a(new bjr(this.l, 0, 143, 58) {
         @Override
         public boolean a(bmb var1) {
            return false;
         }

         @Override
         public bmb a(bfw var1, bmb var2) {
            bjc.this.f.a(1);
            bjc.this.g.a(1);
            if (!bjc.this.f.f() || !bjc.this.g.f()) {
               bjc.this.d.a(0);
            }

            _snowman.a((var1x, var2x) -> {
               long _snowman = var1x.T();
               if (bjc.this.j != _snowman) {
                  var1x.a(null, var2x, adq.pH, adr.e, 1.0F, 1.0F);
                  bjc.this.j = _snowman;
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

      this.a(this.d);
   }

   public int e() {
      return this.d.b();
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.c, _snowman, bup.lR);
   }

   @Override
   public boolean a(bfw var1, int var2) {
      if (_snowman > 0 && _snowman <= ccb.R) {
         this.d.a(_snowman);
         this.j();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(aon var1) {
      bmb _snowman = this.f.e();
      bmb _snowmanx = this.g.e();
      bmb _snowmanxx = this.h.e();
      bmb _snowmanxxx = this.i.e();
      if (_snowmanxxx.a() || !_snowman.a() && !_snowmanx.a() && this.d.b() > 0 && (this.d.b() < ccb.P - ccb.Q || !_snowmanxx.a())) {
         if (!_snowmanxx.a() && _snowmanxx.b() instanceof bkf) {
            md _snowmanxxxx = _snowman.a("BlockEntityTag");
            boolean _snowmanxxxxx = _snowmanxxxx.c("Patterns", 9) && !_snowman.a() && _snowmanxxxx.d("Patterns", 10).size() >= 6;
            if (_snowmanxxxxx) {
               this.d.a(0);
            } else {
               this.d.a(((bkf)_snowmanxx.b()).b().ordinal());
            }
         }
      } else {
         this.i.d(bmb.b);
         this.d.a(0);
      }

      this.j();
      this.c();
   }

   public void a(Runnable var1) {
      this.e = _snowman;
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == this.i.d) {
            if (!this.a(_snowmanxx, 4, 40, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman != this.g.d && _snowman != this.f.d && _snowman != this.h.d) {
            if (_snowmanxx.b() instanceof bke) {
               if (!this.a(_snowmanxx, this.f.d, this.f.d + 1, false)) {
                  return bmb.b;
               }
            } else if (_snowmanxx.b() instanceof bky) {
               if (!this.a(_snowmanxx, this.g.d, this.g.d + 1, false)) {
                  return bmb.b;
               }
            } else if (_snowmanxx.b() instanceof bkf) {
               if (!this.a(_snowmanxx, this.h.d, this.h.d + 1, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 4 && _snowman < 31) {
               if (!this.a(_snowmanxx, 31, 40, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 31 && _snowman < 40 && !this.a(_snowmanxx, 4, 31, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 4, 40, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         } else {
            _snowmanx.d();
         }

         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         _snowmanx.a(_snowman, _snowmanxx);
      }

      return _snowman;
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.c.a((var2, var3) -> this.a(_snowman, _snowman.l, this.k));
   }

   private void j() {
      if (this.d.b() > 0) {
         bmb _snowman = this.f.e();
         bmb _snowmanx = this.g.e();
         bmb _snowmanxx = bmb.b;
         if (!_snowman.a() && !_snowmanx.a()) {
            _snowmanxx = _snowman.i();
            _snowmanxx.e(1);
            ccb _snowmanxxx = ccb.values()[this.d.b()];
            bkx _snowmanxxxx = ((bky)_snowmanx.b()).d();
            md _snowmanxxxxx = _snowmanxx.a("BlockEntityTag");
            mj _snowmanxxxxxx;
            if (_snowmanxxxxx.c("Patterns", 9)) {
               _snowmanxxxxxx = _snowmanxxxxx.d("Patterns", 10);
            } else {
               _snowmanxxxxxx = new mj();
               _snowmanxxxxx.a("Patterns", _snowmanxxxxxx);
            }

            md _snowmanxxxxxxx = new md();
            _snowmanxxxxxxx.a("Pattern", _snowmanxxx.b());
            _snowmanxxxxxxx.b("Color", _snowmanxxxx.b());
            _snowmanxxxxxx.add(_snowmanxxxxxxx);
         }

         if (!bmb.b(_snowmanxx, this.i.e())) {
            this.i.d(_snowmanxx);
         }
      }
   }

   public bjr f() {
      return this.f;
   }

   public bjr g() {
      return this.g;
   }

   public bjr h() {
      return this.h;
   }

   public bjr i() {
      return this.i;
   }
}
