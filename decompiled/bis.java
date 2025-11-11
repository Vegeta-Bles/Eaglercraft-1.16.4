import java.util.List;
import java.util.Random;

public class bis extends bic {
   private final aon f = new apa(2) {
      @Override
      public void X_() {
         super.X_();
         bis.this.a(this);
      }
   };
   private final bim g;
   private final Random h = new Random();
   private final biq i = biq.a();
   public final int[] c = new int[3];
   public final int[] d = new int[]{-1, -1, -1};
   public final int[] e = new int[]{-1, -1, -1};

   public bis(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bis(int var1, bfv var2, bim var3) {
      super(bje.m, _snowman);
      this.g = _snowman;
      this.a(new bjr(this.f, 0, 15, 47) {
         @Override
         public boolean a(bmb var1) {
            return true;
         }

         @Override
         public int a() {
            return 1;
         }
      });
      this.a(new bjr(this.f, 1, 35, 47) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() == bmd.mt;
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

      this.a(biq.a(this.c, 0));
      this.a(biq.a(this.c, 1));
      this.a(biq.a(this.c, 2));
      this.a(this.i).a(_snowman.e.eG());
      this.a(biq.a(this.d, 0));
      this.a(biq.a(this.d, 1));
      this.a(biq.a(this.d, 2));
      this.a(biq.a(this.e, 0));
      this.a(biq.a(this.e, 1));
      this.a(biq.a(this.e, 2));
   }

   @Override
   public void a(aon var1) {
      if (_snowman == this.f) {
         bmb _snowman = _snowman.a(0);
         if (!_snowman.a() && _snowman.w()) {
            this.g.a((var2x, var3x) -> {
               int _snowmanx = 0;

               for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
                  for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
                     if ((_snowmanx != 0 || _snowmanxx != 0) && var2x.w(var3x.b(_snowmanxx, 0, _snowmanx)) && var2x.w(var3x.b(_snowmanxx, 1, _snowmanx))) {
                        if (var2x.d_(var3x.b(_snowmanxx * 2, 0, _snowmanx * 2)).a(bup.bI)) {
                           _snowmanx++;
                        }

                        if (var2x.d_(var3x.b(_snowmanxx * 2, 1, _snowmanx * 2)).a(bup.bI)) {
                           _snowmanx++;
                        }

                        if (_snowmanxx != 0 && _snowmanx != 0) {
                           if (var2x.d_(var3x.b(_snowmanxx * 2, 0, _snowmanx)).a(bup.bI)) {
                              _snowmanx++;
                           }

                           if (var2x.d_(var3x.b(_snowmanxx * 2, 1, _snowmanx)).a(bup.bI)) {
                              _snowmanx++;
                           }

                           if (var2x.d_(var3x.b(_snowmanxx, 0, _snowmanx * 2)).a(bup.bI)) {
                              _snowmanx++;
                           }

                           if (var2x.d_(var3x.b(_snowmanxx, 1, _snowmanx * 2)).a(bup.bI)) {
                              _snowmanx++;
                           }
                        }
                     }
                  }
               }

               this.h.setSeed((long)this.i.b());

               for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
                  this.c[_snowmanx] = bpu.a(this.h, _snowmanx, _snowmanx, _snowman);
                  this.d[_snowmanx] = -1;
                  this.e[_snowmanx] = -1;
                  if (this.c[_snowmanx] < _snowmanx + 1) {
                     this.c[_snowmanx] = 0;
                  }
               }

               for (int _snowmanxxx = 0; _snowmanxxx < 3; _snowmanxxx++) {
                  if (this.c[_snowmanxxx] > 0) {
                     List<bpv> _snowmanxxxx = this.a(_snowman, _snowmanxxx, this.c[_snowmanxxx]);
                     if (_snowmanxxxx != null && !_snowmanxxxx.isEmpty()) {
                        bpv _snowmanxxxxx = _snowmanxxxx.get(this.h.nextInt(_snowmanxxxx.size()));
                        this.d[_snowmanxxx] = gm.R.a(_snowmanxxxxx.b);
                        this.e[_snowmanxxx] = _snowmanxxxxx.c;
                     }
                  }
               }

               this.c();
            });
         } else {
            for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
               this.c[_snowmanx] = 0;
               this.d[_snowmanx] = -1;
               this.e[_snowmanx] = -1;
            }
         }
      }
   }

   @Override
   public boolean a(bfw var1, int var2) {
      bmb _snowman = this.f.a(0);
      bmb _snowmanx = this.f.a(1);
      int _snowmanxx = _snowman + 1;
      if ((_snowmanx.a() || _snowmanx.E() < _snowmanxx) && !_snowman.bC.d) {
         return false;
      } else if (this.c[_snowman] <= 0 || _snowman.a() || (_snowman.bD < _snowmanxx || _snowman.bD < this.c[_snowman]) && !_snowman.bC.d) {
         return false;
      } else {
         this.g.a((var6, var7) -> {
            bmb _snowmanxxx = _snowman;
            List<bpv> _snowmanx = this.a(_snowman, _snowman, this.c[_snowman]);
            if (!_snowmanx.isEmpty()) {
               _snowman.a(_snowman, _snowman);
               boolean _snowmanxx = _snowman.b() == bmd.mc;
               if (_snowmanxx) {
                  _snowmanxxx = new bmb(bmd.pq);
                  md _snowmanxxx = _snowman.o();
                  if (_snowmanxxx != null) {
                     _snowmanxxx.c(_snowmanxxx.g());
                  }

                  this.f.a(0, _snowmanxxx);
               }

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
                  bpv _snowmanxxxx = _snowmanx.get(_snowmanxxx);
                  if (_snowmanxx) {
                     blf.a(_snowmanxxx, _snowmanxxxx);
                  } else {
                     _snowmanxxx.a(_snowmanxxxx.b, _snowmanxxxx.c);
                  }
               }

               if (!_snowman.bC.d) {
                  _snowman.g(_snowman);
                  if (_snowman.a()) {
                     this.f.a(1, bmb.b);
                  }
               }

               _snowman.a(aea.aj);
               if (_snowman instanceof aah) {
                  ac.i.a((aah)_snowman, _snowmanxxx, _snowman);
               }

               this.f.X_();
               this.i.a(_snowman.eG());
               this.a(this.f);
               var6.a(null, var7, adq.dm, adr.e, 1.0F, var6.t.nextFloat() * 0.1F + 0.9F);
            }
         });
         return true;
      }
   }

   private List<bpv> a(bmb var1, int var2, int var3) {
      this.h.setSeed((long)(this.i.b() + _snowman));
      List<bpv> _snowman = bpu.b(this.h, _snowman, _snowman, false);
      if (_snowman.b() == bmd.mc && _snowman.size() > 1) {
         _snowman.remove(this.h.nextInt(_snowman.size()));
      }

      return _snowman;
   }

   public int e() {
      bmb _snowman = this.f.a(1);
      return _snowman.a() ? 0 : _snowman.E();
   }

   public int f() {
      return this.i.b();
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.g.a((var2, var3) -> this.a(_snowman, _snowman.l, this.f));
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.g, _snowman, bup.dZ);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == 0) {
            if (!this.a(_snowmanxx, 2, 38, true)) {
               return bmb.b;
            }
         } else if (_snowman == 1) {
            if (!this.a(_snowmanxx, 2, 38, true)) {
               return bmb.b;
            }
         } else if (_snowmanxx.b() == bmd.mt) {
            if (!this.a(_snowmanxx, 1, 2, true)) {
               return bmb.b;
            }
         } else {
            if (this.a.get(0).f() || !this.a.get(0).a(_snowmanxx)) {
               return bmb.b;
            }

            bmb _snowmanxxx = _snowmanxx.i();
            _snowmanxxx.e(1);
            _snowmanxx.g(1);
            this.a.get(0).d(_snowmanxxx);
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
}
