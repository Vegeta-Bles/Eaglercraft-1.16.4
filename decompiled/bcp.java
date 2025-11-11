import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bcp extends bco {
   private static final Logger e = LogManager.getLogger();
   private static final us<bmb> f = uv.a(bcp.class, uu.g);
   private static final us<Integer> g = uv.a(bcp.class, uu.b);
   private float ag = 1.0F;
   private boolean ah;

   public bcp(aqe<? extends bcp> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bcp(brx var1, fx var2, gc var3) {
      super(aqe.M, _snowman, _snowman);
      this.a(_snowman);
   }

   @Override
   protected float a(aqx var1, aqb var2) {
      return 0.0F;
   }

   @Override
   protected void e() {
      this.ab().a(f, bmb.b);
      this.ab().a(g, 0);
   }

   @Override
   protected void a(gc var1) {
      Validate.notNull(_snowman);
      this.d = _snowman;
      if (_snowman.n().d()) {
         this.q = 0.0F;
         this.p = (float)(this.d.d() * 90);
      } else {
         this.q = (float)(-90 * _snowman.e().a());
         this.p = 0.0F;
      }

      this.s = this.q;
      this.r = this.p;
      this.g();
   }

   @Override
   protected void g() {
      if (this.d != null) {
         double _snowman = 0.46875;
         double _snowmanx = (double)this.c.u() + 0.5 - (double)this.d.i() * 0.46875;
         double _snowmanxx = (double)this.c.v() + 0.5 - (double)this.d.j() * 0.46875;
         double _snowmanxxx = (double)this.c.w() + 0.5 - (double)this.d.k() * 0.46875;
         this.o(_snowmanx, _snowmanxx, _snowmanxxx);
         double _snowmanxxxx = (double)this.i();
         double _snowmanxxxxx = (double)this.k();
         double _snowmanxxxxxx = (double)this.i();
         gc.a _snowmanxxxxxxx = this.d.n();
         switch (_snowmanxxxxxxx) {
            case a:
               _snowmanxxxx = 1.0;
               break;
            case b:
               _snowmanxxxxx = 1.0;
               break;
            case c:
               _snowmanxxxxxx = 1.0;
         }

         _snowmanxxxx /= 32.0;
         _snowmanxxxxx /= 32.0;
         _snowmanxxxxxx /= 32.0;
         this.a(new dci(_snowmanx - _snowmanxxxx, _snowmanxx - _snowmanxxxxx, _snowmanxxx - _snowmanxxxxxx, _snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxxxx, _snowmanxxx + _snowmanxxxxxx));
      }
   }

   @Override
   public boolean h() {
      if (this.ah) {
         return true;
      } else if (!this.l.k(this)) {
         return false;
      } else {
         ceh _snowman = this.l.d_(this.c.a(this.d.f()));
         return _snowman.c().b() || this.d.n().d() && bvy.l(_snowman) ? this.l.a(this, this.cc(), b).isEmpty() : false;
      }
   }

   @Override
   public void a(aqr var1, dcn var2) {
      if (!this.ah) {
         super.a(_snowman, _snowman);
      }
   }

   @Override
   public void i(double var1, double var3, double var5) {
      if (!this.ah) {
         super.i(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public float bg() {
      return 0.0F;
   }

   @Override
   public void aa() {
      this.c(this.o());
      super.aa();
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.ah) {
         return _snowman != apk.m && !_snowman.v() ? false : super.a(_snowman, _snowman);
      } else if (this.b(_snowman)) {
         return false;
      } else if (!_snowman.d() && !this.o().a()) {
         if (!this.l.v) {
            this.b(_snowman.k(), false);
            this.a(adq.gI, 1.0F, 1.0F);
         }

         return true;
      } else {
         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public int i() {
      return 12;
   }

   @Override
   public int k() {
      return 12;
   }

   @Override
   public boolean a(double var1) {
      double _snowman = 16.0;
      _snowman *= 64.0 * bW();
      return _snowman < _snowman * _snowman;
   }

   @Override
   public void a(@Nullable aqa var1) {
      this.a(adq.gG, 1.0F, 1.0F);
      this.b(_snowman, true);
   }

   @Override
   public void m() {
      this.a(adq.gH, 1.0F, 1.0F);
   }

   private void b(@Nullable aqa var1, boolean var2) {
      if (!this.ah) {
         bmb _snowman = this.o();
         this.b(bmb.b);
         if (!this.l.V().b(brt.g)) {
            if (_snowman == null) {
               this.c(_snowman);
            }
         } else {
            if (_snowman instanceof bfw) {
               bfw _snowmanx = (bfw)_snowman;
               if (_snowmanx.bC.d) {
                  this.c(_snowman);
                  return;
               }
            }

            if (_snowman) {
               this.a(bmd.oW);
            }

            if (!_snowman.a()) {
               _snowman = _snowman.i();
               this.c(_snowman);
               if (this.J.nextFloat() < this.ag) {
                  this.a(_snowman);
               }
            }
         }
      }
   }

   private void c(bmb var1) {
      if (_snowman.b() == bmd.nf) {
         cxx _snowman = bmh.b(_snowman, this.l);
         _snowman.a(this.c, this.Y());
         _snowman.a(true);
      }

      _snowman.a(null);
   }

   public bmb o() {
      return this.ab().a(f);
   }

   public void b(bmb var1) {
      this.a(_snowman, true);
   }

   public void a(bmb var1, boolean var2) {
      if (!_snowman.a()) {
         _snowman = _snowman.i();
         _snowman.e(1);
         _snowman.a(this);
      }

      this.ab().b(f, _snowman);
      if (!_snowman.a()) {
         this.a(adq.gF, 1.0F, 1.0F);
      }

      if (_snowman && this.c != null) {
         this.l.c(this.c, bup.a);
      }
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (_snowman == 0) {
         this.b(_snowman);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(us<?> var1) {
      if (_snowman.equals(f)) {
         bmb _snowman = this.o();
         if (!_snowman.a() && _snowman.z() != this) {
            _snowman.a(this);
         }
      }
   }

   public int p() {
      return this.ab().a(g);
   }

   public void a(int var1) {
      this.a(_snowman, true);
   }

   private void a(int var1, boolean var2) {
      this.ab().b(g, _snowman % 8);
      if (_snowman && this.c != null) {
         this.l.c(this.c, bup.a);
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (!this.o().a()) {
         _snowman.a("Item", this.o().b(new md()));
         _snowman.a("ItemRotation", (byte)this.p());
         _snowman.a("ItemDropChance", this.ag);
      }

      _snowman.a("Facing", (byte)this.d.c());
      _snowman.a("Invisible", this.bF());
      _snowman.a("Fixed", this.ah);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      md _snowman = _snowman.p("Item");
      if (_snowman != null && !_snowman.isEmpty()) {
         bmb _snowmanx = bmb.a(_snowman);
         if (_snowmanx.a()) {
            e.warn("Unable to load item from: {}", _snowman);
         }

         bmb _snowmanxx = this.o();
         if (!_snowmanxx.a() && !bmb.b(_snowmanx, _snowmanxx)) {
            this.c(_snowmanxx);
         }

         this.a(_snowmanx, false);
         this.a(_snowman.f("ItemRotation"), false);
         if (_snowman.c("ItemDropChance", 99)) {
            this.ag = _snowman.j("ItemDropChance");
         }
      }

      this.a(gc.a(_snowman.f("Facing")));
      this.j(_snowman.q("Invisible"));
      this.ah = _snowman.q("Fixed");
   }

   @Override
   public aou a(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      boolean _snowmanx = !this.o().a();
      boolean _snowmanxx = !_snowman.a();
      if (this.ah) {
         return aou.c;
      } else if (!this.l.v) {
         if (!_snowmanx) {
            if (_snowmanxx && !this.y) {
               this.b(_snowman);
               if (!_snowman.bC.d) {
                  _snowman.g(1);
               }
            }
         } else {
            this.a(adq.gJ, 1.0F, 1.0F);
            this.a(this.p() + 1);
         }

         return aou.b;
      } else {
         return !_snowmanx && !_snowmanxx ? aou.c : aou.a;
      }
   }

   public int q() {
      return this.o().a() ? 0 : this.p() % 8 + 1;
   }

   @Override
   public oj<?> P() {
      return new on(this, this.X(), this.d.c(), this.n());
   }
}
