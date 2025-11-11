public abstract class avm extends avv {
   protected aqn d;
   protected fx e = fx.b;
   protected boolean f;
   private boolean a;
   private float b;
   private float c;

   public avm(aqn var1) {
      this.d = _snowman;
      if (!azi.a(_snowman)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean g() {
      if (!this.f) {
         return false;
      } else {
         ceh _snowman = this.d.l.d_(this.e);
         if (!(_snowman.b() instanceof bwb)) {
            this.f = false;
            return false;
         } else {
            return _snowman.c(bwb.b);
         }
      }
   }

   protected void a(boolean var1) {
      if (this.f) {
         ceh _snowman = this.d.l.d_(this.e);
         if (_snowman.b() instanceof bwb) {
            ((bwb)_snowman.b()).a(this.d.l, _snowman, this.e, _snowman);
         }
      }
   }

   @Override
   public boolean a() {
      if (!azi.a(this.d)) {
         return false;
      } else if (!this.d.u) {
         return false;
      } else {
         ayi _snowman = (ayi)this.d.x();
         cxd _snowmanx = _snowman.k();
         if (_snowmanx != null && !_snowmanx.c() && _snowman.f()) {
            for (int _snowmanxx = 0; _snowmanxx < Math.min(_snowmanx.f() + 2, _snowmanx.e()); _snowmanxx++) {
               cxb _snowmanxxx = _snowmanx.a(_snowmanxx);
               this.e = new fx(_snowmanxxx.a, _snowmanxxx.b + 1, _snowmanxxx.c);
               if (!(this.d.h((double)this.e.u(), this.d.cE(), (double)this.e.w()) > 2.25)) {
                  this.f = bwb.a(this.d.l, this.e);
                  if (this.f) {
                     return true;
                  }
               }
            }

            this.e = this.d.cB().b();
            this.f = bwb.a(this.d.l, this.e);
            return this.f;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a;
   }

   @Override
   public void c() {
      this.a = false;
      this.b = (float)((double)this.e.u() + 0.5 - this.d.cD());
      this.c = (float)((double)this.e.w() + 0.5 - this.d.cH());
   }

   @Override
   public void e() {
      float _snowman = (float)((double)this.e.u() + 0.5 - this.d.cD());
      float _snowmanx = (float)((double)this.e.w() + 0.5 - this.d.cH());
      float _snowmanxx = this.b * _snowman + this.c * _snowmanx;
      if (_snowmanxx < 0.0F) {
         this.a = true;
      }
   }
}
