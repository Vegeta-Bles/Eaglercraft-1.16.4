import javax.annotation.Nullable;

public abstract class axx extends avv {
   protected final aqn e;
   protected final boolean f;
   private final boolean a;
   private int b;
   private int c;
   private int d;
   protected aqm g;
   protected int h = 60;

   public axx(aqn var1, boolean var2) {
      this(_snowman, _snowman, false);
   }

   public axx(aqn var1, boolean var2, boolean var3) {
      this.e = _snowman;
      this.f = _snowman;
      this.a = _snowman;
   }

   @Override
   public boolean b() {
      aqm _snowman = this.e.A();
      if (_snowman == null) {
         _snowman = this.g;
      }

      if (_snowman == null) {
         return false;
      } else if (!_snowman.aX()) {
         return false;
      } else {
         ddp _snowmanx = this.e.bG();
         ddp _snowmanxx = _snowman.bG();
         if (_snowmanx != null && _snowmanxx == _snowmanx) {
            return false;
         } else {
            double _snowmanxxx = this.k();
            if (this.e.h((aqa)_snowman) > _snowmanxxx * _snowmanxxx) {
               return false;
            } else {
               if (this.f) {
                  if (this.e.z().a(_snowman)) {
                     this.d = 0;
                  } else if (++this.d > this.h) {
                     return false;
                  }
               }

               if (_snowman instanceof bfw && ((bfw)_snowman).bC.a) {
                  return false;
               } else {
                  this.e.h(_snowman);
                  return true;
               }
            }
         }
      }
   }

   protected double k() {
      return this.e.b(arl.b);
   }

   @Override
   public void c() {
      this.b = 0;
      this.c = 0;
      this.d = 0;
   }

   @Override
   public void d() {
      this.e.h(null);
      this.g = null;
   }

   protected boolean a(@Nullable aqm var1, azg var2) {
      if (_snowman == null) {
         return false;
      } else if (!_snowman.a(this.e, _snowman)) {
         return false;
      } else if (!this.e.a(_snowman.cB())) {
         return false;
      } else {
         if (this.a) {
            if (--this.c <= 0) {
               this.b = 0;
            }

            if (this.b == 0) {
               this.b = this.a(_snowman) ? 1 : 2;
            }

            if (this.b == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(aqm var1) {
      this.c = 10 + this.e.cY().nextInt(5);
      cxd _snowman = this.e.x().a(_snowman, 0);
      if (_snowman == null) {
         return false;
      } else {
         cxb _snowmanx = _snowman.d();
         if (_snowmanx == null) {
            return false;
         } else {
            int _snowmanxx = _snowmanx.a - afm.c(_snowman.cD());
            int _snowmanxxx = _snowmanx.c - afm.c(_snowman.cH());
            return (double)(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx) <= 2.25;
         }
      }
   }

   public axx a(int var1) {
      this.h = _snowman;
      return this;
   }
}
