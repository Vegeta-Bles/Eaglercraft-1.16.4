import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class biw extends bic {
   private final aon c = new bjm();
   private final aon d = new apa(2) {
      @Override
      public void X_() {
         super.X_();
         biw.this.a(this);
      }
   };
   private final bim e;

   public biw(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public biw(int var1, bfv var2, final bim var3) {
      super(bje.o, _snowman);
      this.e = _snowman;
      this.a(new bjr(this.d, 0, 49, 19) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.e() || _snowman.b() == bmd.pq || _snowman.x();
         }
      });
      this.a(new bjr(this.d, 1, 49, 40) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.e() || _snowman.b() == bmd.pq || _snowman.x();
         }
      });
      this.a(new bjr(this.c, 2, 129, 34) {
         @Override
         public boolean a(bmb var1) {
            return false;
         }

         @Override
         public bmb a(bfw var1, bmb var2) {
            _snowman.a((var1x, var2x) -> {
               int _snowman = this.a(var1x);

               while (_snowman > 0) {
                  int _snowmanx = aqg.a(_snowman);
                  _snowman -= _snowmanx;
                  var1x.c(new aqg(var1x, (double)var2x.u(), (double)var2x.v() + 0.5, (double)var2x.w() + 0.5, _snowmanx));
               }

               var1x.c(1042, var2x, 0);
            });
            biw.this.d.a(0, bmb.b);
            biw.this.d.a(1, bmb.b);
            return _snowman;
         }

         private int a(brx var1) {
            int _snowman = 0;
            _snowman += this.e(biw.this.d.a(0));
            _snowman += this.e(biw.this.d.a(1));
            if (_snowman > 0) {
               int _snowmanx = (int)Math.ceil((double)_snowman / 2.0);
               return _snowmanx + _snowman.t.nextInt(_snowmanx);
            } else {
               return 0;
            }
         }

         private int e(bmb var1) {
            int _snowman = 0;
            Map<bps, Integer> _snowmanx = bpu.a(_snowman);

            for (Entry<bps, Integer> _snowmanxx : _snowmanx.entrySet()) {
               bps _snowmanxxx = _snowmanxx.getKey();
               Integer _snowmanxxxx = _snowmanxx.getValue();
               if (!_snowmanxxx.c()) {
                  _snowman += _snowmanxxx.a(_snowmanxxxx);
               }
            }

            return _snowman;
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
   public void a(aon var1) {
      super.a(_snowman);
      if (_snowman == this.d) {
         this.e();
      }
   }

   private void e() {
      bmb _snowman = this.d.a(0);
      bmb _snowmanx = this.d.a(1);
      boolean _snowmanxx = !_snowman.a() || !_snowmanx.a();
      boolean _snowmanxxx = !_snowman.a() && !_snowmanx.a();
      if (!_snowmanxx) {
         this.c.a(0, bmb.b);
      } else {
         boolean _snowmanxxxx = !_snowman.a() && _snowman.b() != bmd.pq && !_snowman.x() || !_snowmanx.a() && _snowmanx.b() != bmd.pq && !_snowmanx.x();
         if (_snowman.E() > 1 || _snowmanx.E() > 1 || !_snowmanxxx && _snowmanxxxx) {
            this.c.a(0, bmb.b);
            this.c();
            return;
         }

         int _snowmanxxxxx = 1;
         int _snowmanxxxxxx;
         bmb _snowmanxxxxxxx;
         if (_snowmanxxx) {
            if (_snowman.b() != _snowmanx.b()) {
               this.c.a(0, bmb.b);
               this.c();
               return;
            }

            blx _snowmanxxxxxxxx = _snowman.b();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.j() - _snowman.g();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.j() - _snowmanx.g();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxx.j() * 5 / 100;
            _snowmanxxxxxx = Math.max(_snowmanxxxxxxxx.j() - _snowmanxxxxxxxxxxx, 0);
            _snowmanxxxxxxx = this.b(_snowman, _snowmanx);
            if (!_snowmanxxxxxxx.e()) {
               if (!bmb.b(_snowman, _snowmanx)) {
                  this.c.a(0, bmb.b);
                  this.c();
                  return;
               }

               _snowmanxxxxx = 2;
            }
         } else {
            boolean _snowmanxxxxxxxx = !_snowman.a();
            _snowmanxxxxxx = _snowmanxxxxxxxx ? _snowman.g() : _snowmanx.g();
            _snowmanxxxxxxx = _snowmanxxxxxxxx ? _snowman : _snowmanx;
         }

         this.c.a(0, this.a(_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx));
      }

      this.c();
   }

   private bmb b(bmb var1, bmb var2) {
      bmb _snowman = _snowman.i();
      Map<bps, Integer> _snowmanx = bpu.a(_snowman);

      for (Entry<bps, Integer> _snowmanxx : _snowmanx.entrySet()) {
         bps _snowmanxxx = _snowmanxx.getKey();
         if (!_snowmanxxx.c() || bpu.a(_snowmanxxx, _snowman) == 0) {
            _snowman.a(_snowmanxxx, _snowmanxx.getValue());
         }
      }

      return _snowman;
   }

   private bmb a(bmb var1, int var2, int var3) {
      bmb _snowman = _snowman.i();
      _snowman.c("Enchantments");
      _snowman.c("StoredEnchantments");
      if (_snowman > 0) {
         _snowman.b(_snowman);
      } else {
         _snowman.c("Damage");
      }

      _snowman.e(_snowman);
      Map<bps, Integer> _snowmanx = bpu.a(_snowman).entrySet().stream().filter(var0 -> var0.getKey().c()).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      bpu.a(_snowmanx, _snowman);
      _snowman.c(0);
      if (_snowman.b() == bmd.pq && _snowmanx.size() == 0) {
         _snowman = new bmb(bmd.mc);
         if (_snowman.t()) {
            _snowman.a(_snowman.r());
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         _snowman.c(bie.d(_snowman.B()));
      }

      return _snowman;
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.e.a((var2, var3) -> this.a(_snowman, var2, this.d));
   }

   @Override
   public boolean a(bfw var1) {
      return a(this.e, _snowman, bup.lX);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         bmb _snowmanxxx = this.d.a(0);
         bmb _snowmanxxxx = this.d.a(1);
         if (_snowman == 2) {
            if (!this.a(_snowmanxx, 3, 39, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman != 0 && _snowman != 1) {
            if (!_snowmanxxx.a() && !_snowmanxxxx.a()) {
               if (_snowman >= 3 && _snowman < 30) {
                  if (!this.a(_snowmanxx, 30, 39, false)) {
                     return bmb.b;
                  }
               } else if (_snowman >= 30 && _snowman < 39 && !this.a(_snowmanxx, 3, 30, false)) {
                  return bmb.b;
               }
            } else if (!this.a(_snowmanxx, 0, 2, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 3, 39, false)) {
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
}
