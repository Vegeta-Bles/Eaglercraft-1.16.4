import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aai {
   private static final Logger c = LogManager.getLogger();
   public aag a;
   public aah b;
   private bru d = bru.a;
   private bru e = bru.a;
   private boolean f;
   private int g;
   private fx h = fx.b;
   private int i;
   private boolean j;
   private fx k = fx.b;
   private int l;
   private int m = -1;

   public aai(aag var1) {
      this.a = _snowman;
   }

   public void a(bru var1) {
      this.a(_snowman, _snowman != this.d ? this.d : this.e);
   }

   public void a(bru var1, bru var2) {
      this.e = _snowman;
      this.d = _snowman;
      _snowman.a(this.b.bC);
      this.b.t();
      this.b.c.ae().a(new qi(qi.a.b, this.b));
      this.a.n_();
   }

   public bru b() {
      return this.d;
   }

   public bru c() {
      return this.e;
   }

   public boolean d() {
      return this.d.f();
   }

   public boolean e() {
      return this.d.e();
   }

   public void b(bru var1) {
      if (this.d == bru.a) {
         this.d = _snowman;
      }

      this.a(this.d);
   }

   public void a() {
      this.i++;
      if (this.j) {
         ceh _snowman = this.a.d_(this.k);
         if (_snowman.g()) {
            this.j = false;
         } else {
            float _snowmanx = this.a(_snowman, this.k, this.l);
            if (_snowmanx >= 1.0F) {
               this.j = false;
               this.a(this.k);
            }
         }
      } else if (this.f) {
         ceh _snowman = this.a.d_(this.h);
         if (_snowman.g()) {
            this.a.a(this.b.Y(), this.h, -1);
            this.m = -1;
            this.f = false;
         } else {
            this.a(_snowman, this.h, this.g);
         }
      }
   }

   private float a(ceh var1, fx var2, int var3) {
      int _snowman = this.i - _snowman;
      float _snowmanx = _snowman.a(this.b, this.b.l, _snowman) * (float)(_snowman + 1);
      int _snowmanxx = (int)(_snowmanx * 10.0F);
      if (_snowmanxx != this.m) {
         this.a.a(this.b.Y(), _snowman, _snowmanxx);
         this.m = _snowmanxx;
      }

      return _snowmanx;
   }

   public void a(fx var1, sz.a var2, gc var3, int var4) {
      double _snowman = this.b.cD() - ((double)_snowman.u() + 0.5);
      double _snowmanx = this.b.cE() - ((double)_snowman.v() + 0.5) + 1.5;
      double _snowmanxx = this.b.cH() - ((double)_snowman.w() + 0.5);
      double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
      if (_snowmanxxx > 36.0) {
         this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, false, "too far"));
      } else if (_snowman.v() >= _snowman) {
         this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, false, "too high"));
      } else {
         if (_snowman == sz.a.a) {
            if (!this.a.a(this.b, _snowman)) {
               this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, false, "may not interact"));
               return;
            }

            if (this.e()) {
               this.a(_snowman, _snowman, "creative destroy");
               return;
            }

            if (this.b.a(this.a, _snowman, this.d)) {
               this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, false, "block action restricted"));
               return;
            }

            this.g = this.i;
            float _snowmanxxxx = 1.0F;
            ceh _snowmanxxxxx = this.a.d_(_snowman);
            if (!_snowmanxxxxx.g()) {
               _snowmanxxxxx.a(this.a, _snowman, this.b);
               _snowmanxxxx = _snowmanxxxxx.a(this.b, this.b.l, _snowman);
            }

            if (!_snowmanxxxxx.g() && _snowmanxxxx >= 1.0F) {
               this.a(_snowman, _snowman, "insta mine");
            } else {
               if (this.f) {
                  this.b.b.a(new ou(this.h, this.a.d_(this.h), sz.a.a, false, "abort destroying since another started (client insta mine, server disagreed)"));
               }

               this.f = true;
               this.h = _snowman.h();
               int _snowmanxxxxxx = (int)(_snowmanxxxx * 10.0F);
               this.a.a(this.b.Y(), _snowman, _snowmanxxxxxx);
               this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, true, "actual start of destroying"));
               this.m = _snowmanxxxxxx;
            }
         } else if (_snowman == sz.a.c) {
            if (_snowman.equals(this.h)) {
               int _snowmanxxxxxx = this.i - this.g;
               ceh _snowmanxxxxxxx = this.a.d_(_snowman);
               if (!_snowmanxxxxxxx.g()) {
                  float _snowmanxxxxxxxx = _snowmanxxxxxxx.a(this.b, this.b.l, _snowman) * (float)(_snowmanxxxxxx + 1);
                  if (_snowmanxxxxxxxx >= 0.7F) {
                     this.f = false;
                     this.a.a(this.b.Y(), _snowman, -1);
                     this.a(_snowman, _snowman, "destroyed");
                     return;
                  }

                  if (!this.j) {
                     this.f = false;
                     this.j = true;
                     this.k = _snowman;
                     this.l = this.g;
                  }
               }
            }

            this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, true, "stopped destroying"));
         } else if (_snowman == sz.a.b) {
            this.f = false;
            if (!Objects.equals(this.h, _snowman)) {
               c.warn("Mismatch in destroy block pos: " + this.h + " " + _snowman);
               this.a.a(this.b.Y(), this.h, -1);
               this.b.b.a(new ou(this.h, this.a.d_(this.h), _snowman, true, "aborted mismatched destroying"));
            }

            this.a.a(this.b.Y(), _snowman, -1);
            this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, true, "aborted destroying"));
         }
      }
   }

   public void a(fx var1, sz.a var2, String var3) {
      if (this.a(_snowman)) {
         this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, true, _snowman));
      } else {
         this.b.b.a(new ou(_snowman, this.a.d_(_snowman), _snowman, false, _snowman));
      }
   }

   public boolean a(fx var1) {
      ceh _snowman = this.a.d_(_snowman);
      if (!this.b.dD().b().a(_snowman, this.a, _snowman, this.b)) {
         return false;
      } else {
         ccj _snowmanx = this.a.c(_snowman);
         buo _snowmanxx = _snowman.b();
         if ((_snowmanxx instanceof bvi || _snowmanxx instanceof caq || _snowmanxx instanceof bxr) && !this.b.eV()) {
            this.a.a(_snowman, _snowman, _snowman, 3);
            return false;
         } else if (this.b.a(this.a, _snowman, this.d)) {
            return false;
         } else {
            _snowmanxx.a(this.a, _snowman, _snowman, this.b);
            boolean _snowmanxxx = this.a.a(_snowman, false);
            if (_snowmanxxx) {
               _snowmanxx.a((bry)this.a, _snowman, _snowman);
            }

            if (this.e()) {
               return true;
            } else {
               bmb _snowmanxxxx = this.b.dD();
               bmb _snowmanxxxxx = _snowmanxxxx.i();
               boolean _snowmanxxxxxx = this.b.d(_snowman);
               _snowmanxxxx.a(this.a, _snowman, _snowman, this.b);
               if (_snowmanxxx && _snowmanxxxxxx) {
                  _snowmanxx.a(this.a, this.b, _snowman, _snowman, _snowmanx, _snowmanxxxxx);
               }

               return true;
            }
         }
      }
   }

   public aou a(aah var1, brx var2, bmb var3, aot var4) {
      if (this.d == bru.e) {
         return aou.c;
      } else if (_snowman.eT().a(_snowman.b())) {
         return aou.c;
      } else {
         int _snowman = _snowman.E();
         int _snowmanx = _snowman.g();
         aov<bmb> _snowmanxx = _snowman.a(_snowman, _snowman, _snowman);
         bmb _snowmanxxx = _snowmanxx.b();
         if (_snowmanxxx == _snowman && _snowmanxxx.E() == _snowman && _snowmanxxx.k() <= 0 && _snowmanxxx.g() == _snowmanx) {
            return _snowmanxx.a();
         } else if (_snowmanxx.a() == aou.d && _snowmanxxx.k() > 0 && !_snowman.dW()) {
            return _snowmanxx.a();
         } else {
            _snowman.a(_snowman, _snowmanxxx);
            if (this.e()) {
               _snowmanxxx.e(_snowman);
               if (_snowmanxxx.e() && _snowmanxxx.g() != _snowmanx) {
                  _snowmanxxx.b(_snowmanx);
               }
            }

            if (_snowmanxxx.a()) {
               _snowman.a(_snowman, bmb.b);
            }

            if (!_snowman.dW()) {
               _snowman.a(_snowman.bo);
            }

            return _snowmanxx.a();
         }
      }
   }

   public aou a(aah var1, brx var2, bmb var3, aot var4, dcj var5) {
      fx _snowman = _snowman.a();
      ceh _snowmanx = _snowman.d_(_snowman);
      if (this.d == bru.e) {
         aox _snowmanxx = _snowmanx.b(_snowman, _snowman);
         if (_snowmanxx != null) {
            _snowman.a(_snowmanxx);
            return aou.a;
         } else {
            return aou.c;
         }
      } else {
         boolean _snowmanxx = !_snowman.dD().a() || !_snowman.dE().a();
         boolean _snowmanxxx = _snowman.eq() && _snowmanxx;
         bmb _snowmanxxxx = _snowman.i();
         if (!_snowmanxxx) {
            aou _snowmanxxxxx = _snowmanx.a(_snowman, _snowman, _snowman, _snowman);
            if (_snowmanxxxxx.a()) {
               ac.M.a(_snowman, _snowman, _snowmanxxxx);
               return _snowmanxxxxx;
            }
         }

         if (!_snowman.a() && !_snowman.eT().a(_snowman.b())) {
            boa _snowmanxxxxx = new boa(_snowman, _snowman, _snowman);
            aou _snowmanxxxxxx;
            if (this.e()) {
               int _snowmanxxxxxxx = _snowman.E();
               _snowmanxxxxxx = _snowman.a(_snowmanxxxxx);
               _snowman.e(_snowmanxxxxxxx);
            } else {
               _snowmanxxxxxx = _snowman.a(_snowmanxxxxx);
            }

            if (_snowmanxxxxxx.a()) {
               ac.M.a(_snowman, _snowman, _snowmanxxxx);
            }

            return _snowmanxxxxxx;
         } else {
            return aou.c;
         }
      }
   }

   public void a(aag var1) {
      this.a = _snowman;
   }
}
