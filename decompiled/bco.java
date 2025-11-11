import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public abstract class bco extends aqa {
   protected static final Predicate<aqa> b = var0 -> var0 instanceof bco;
   private int e;
   protected fx c;
   protected gc d;

   protected bco(aqe<? extends bco> var1, brx var2) {
      super(_snowman, _snowman);
      this.d = gc.d;
   }

   protected bco(aqe<? extends bco> var1, brx var2, fx var3) {
      this(_snowman, _snowman);
      this.c = _snowman;
   }

   @Override
   protected void e() {
   }

   protected void a(gc var1) {
      Validate.notNull(_snowman);
      Validate.isTrue(_snowman.n().d());
      this.d = _snowman;
      this.p = (float)(this.d.d() * 90);
      this.r = this.p;
      this.g();
   }

   protected void g() {
      if (this.d != null) {
         double _snowman = (double)this.c.u() + 0.5;
         double _snowmanx = (double)this.c.v() + 0.5;
         double _snowmanxx = (double)this.c.w() + 0.5;
         double _snowmanxxx = 0.46875;
         double _snowmanxxxx = this.a(this.i());
         double _snowmanxxxxx = this.a(this.k());
         _snowman -= (double)this.d.i() * 0.46875;
         _snowmanxx -= (double)this.d.k() * 0.46875;
         _snowmanx += _snowmanxxxxx;
         gc _snowmanxxxxxx = this.d.h();
         _snowman += _snowmanxxxx * (double)_snowmanxxxxxx.i();
         _snowmanxx += _snowmanxxxx * (double)_snowmanxxxxxx.k();
         this.o(_snowman, _snowmanx, _snowmanxx);
         double _snowmanxxxxxxx = (double)this.i();
         double _snowmanxxxxxxxx = (double)this.k();
         double _snowmanxxxxxxxxx = (double)this.i();
         if (this.d.n() == gc.a.c) {
            _snowmanxxxxxxxxx = 1.0;
         } else {
            _snowmanxxxxxxx = 1.0;
         }

         _snowmanxxxxxxx /= 32.0;
         _snowmanxxxxxxxx /= 32.0;
         _snowmanxxxxxxxxx /= 32.0;
         this.a(new dci(_snowman - _snowmanxxxxxxx, _snowmanx - _snowmanxxxxxxxx, _snowmanxx - _snowmanxxxxxxxxx, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx));
      }
   }

   private double a(int var1) {
      return _snowman % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void j() {
      if (!this.l.v) {
         if (this.cE() < -64.0) {
            this.an();
         }

         if (this.e++ == 100) {
            this.e = 0;
            if (!this.y && !this.h()) {
               this.ad();
               this.a(null);
            }
         }
      }
   }

   public boolean h() {
      if (!this.l.k(this)) {
         return false;
      } else {
         int _snowman = Math.max(1, this.i() / 16);
         int _snowmanx = Math.max(1, this.k() / 16);
         fx _snowmanxx = this.c.a(this.d.f());
         gc _snowmanxxx = this.d.h();
         fx.a _snowmanxxxx = new fx.a();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanx; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = (_snowman - 1) / -2;
               int _snowmanxxxxxxxx = (_snowmanx - 1) / -2;
               _snowmanxxxx.g(_snowmanxx).c(_snowmanxxx, _snowmanxxxxx + _snowmanxxxxxxx).c(gc.b, _snowmanxxxxxx + _snowmanxxxxxxxx);
               ceh _snowmanxxxxxxxxx = this.l.d_(_snowmanxxxx);
               if (!_snowmanxxxxxxxxx.c().b() && !bvy.l(_snowmanxxxxxxxxx)) {
                  return false;
               }
            }
         }

         return this.l.a(this, this.cc(), b).isEmpty();
      }
   }

   @Override
   public boolean aT() {
      return true;
   }

   @Override
   public boolean t(aqa var1) {
      if (_snowman instanceof bfw) {
         bfw _snowman = (bfw)_snowman;
         return !this.l.a(_snowman, this.c) ? true : this.a(apk.a(_snowman), 0.0F);
      } else {
         return false;
      }
   }

   @Override
   public gc bZ() {
      return this.d;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         if (!this.y && !this.l.v) {
            this.ad();
            this.aS();
            this.a(_snowman.k());
         }

         return true;
      }
   }

   @Override
   public void a(aqr var1, dcn var2) {
      if (!this.l.v && !this.y && _snowman.g() > 0.0) {
         this.ad();
         this.a(null);
      }
   }

   @Override
   public void i(double var1, double var3, double var5) {
      if (!this.l.v && !this.y && _snowman * _snowman + _snowman * _snowman + _snowman * _snowman > 0.0) {
         this.ad();
         this.a(null);
      }
   }

   @Override
   public void b(md var1) {
      fx _snowman = this.n();
      _snowman.b("TileX", _snowman.u());
      _snowman.b("TileY", _snowman.v());
      _snowman.b("TileZ", _snowman.w());
   }

   @Override
   public void a(md var1) {
      this.c = new fx(_snowman.h("TileX"), _snowman.h("TileY"), _snowman.h("TileZ"));
   }

   public abstract int i();

   public abstract int k();

   public abstract void a(@Nullable aqa var1);

   @Override
   public abstract void m();

   @Override
   public bcv a(bmb var1, float var2) {
      bcv _snowman = new bcv(this.l, this.cD() + (double)((float)this.d.i() * 0.15F), this.cE() + (double)_snowman, this.cH() + (double)((float)this.d.k() * 0.15F), _snowman);
      _snowman.m();
      this.l.c(_snowman);
      return _snowman;
   }

   @Override
   protected boolean aV() {
      return false;
   }

   @Override
   public void d(double var1, double var3, double var5) {
      this.c = new fx(_snowman, _snowman, _snowman);
      this.g();
      this.Z = true;
   }

   public fx n() {
      return this.c;
   }

   @Override
   public float a(bzm var1) {
      if (this.d.n() != gc.a.b) {
         switch (_snowman) {
            case c:
               this.d = this.d.f();
               break;
            case d:
               this.d = this.d.h();
               break;
            case b:
               this.d = this.d.g();
         }
      }

      float _snowman = afm.g(this.p);
      switch (_snowman) {
         case c:
            return _snowman + 180.0F;
         case d:
            return _snowman + 90.0F;
         case b:
            return _snowman + 270.0F;
         default:
            return _snowman;
      }
   }

   @Override
   public float a(byg var1) {
      return this.a(_snowman.a(this.d));
   }

   @Override
   public void a(aag var1, aql var2) {
   }

   @Override
   public void x_() {
   }
}
