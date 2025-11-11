import java.util.List;

public class avu extends avv {
   private final azz a;
   private azz b;
   private final double c;
   private int d;

   public avu(azz var1, double var2) {
      this.a = _snowman;
      this.c = _snowman;
   }

   @Override
   public boolean a() {
      if (this.a.i() >= 0) {
         return false;
      } else {
         List<azz> _snowman = this.a.l.a((Class<? extends azz>)this.a.getClass(), this.a.cc().c(8.0, 4.0, 8.0));
         azz _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (azz _snowmanxxx : _snowman) {
            if (_snowmanxxx.i() >= 0) {
               double _snowmanxxxx = this.a.h(_snowmanxxx);
               if (!(_snowmanxxxx > _snowmanxx)) {
                  _snowmanxx = _snowmanxxxx;
                  _snowmanx = _snowmanxxx;
               }
            }
         }

         if (_snowmanx == null) {
            return false;
         } else if (_snowmanxx < 9.0) {
            return false;
         } else {
            this.b = _snowmanx;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      if (this.a.i() >= 0) {
         return false;
      } else if (!this.b.aX()) {
         return false;
      } else {
         double _snowman = this.a.h(this.b);
         return !(_snowman < 9.0) && !(_snowman > 256.0);
      }
   }

   @Override
   public void c() {
      this.d = 0;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (--this.d <= 0) {
         this.d = 10;
         this.a.x().a(this.b, this.c);
      }
   }
}
