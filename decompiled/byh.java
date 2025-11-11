import java.util.Random;

public class byh extends buu implements buq {
   protected static final ddh a = buo.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

   public byh(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.nextInt(25) == 0) {
         int _snowman = 5;
         int _snowmanx = 4;

         for (fx _snowmanxx : fx.a(_snowman.b(-4, -1, -4), _snowman.b(4, 1, 4))) {
            if (_snowman.d_(_snowmanxx).a(this)) {
               if (--_snowman <= 0) {
                  return;
               }
            }
         }

         fx _snowmanxxx = _snowman.b(_snowman.nextInt(3) - 1, _snowman.nextInt(2) - _snowman.nextInt(2), _snowman.nextInt(3) - 1);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
            if (_snowman.w(_snowmanxxx) && _snowman.a(_snowman, _snowmanxxx)) {
               _snowman = _snowmanxxx;
            }

            _snowmanxxx = _snowman.b(_snowman.nextInt(3) - 1, _snowman.nextInt(2) - _snowman.nextInt(2), _snowman.nextInt(3) - 1);
         }

         if (_snowman.w(_snowmanxxx) && _snowman.a(_snowman, _snowmanxxx)) {
            _snowman.a(_snowmanxxx, _snowman, 2);
         }
      }
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.i(_snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      return _snowmanx.a(aed.aD) ? true : _snowman.b(_snowman, 0) < 13 && this.c(_snowmanx, _snowman, _snowman);
   }

   public boolean a(aag var1, fx var2, ceh var3, Random var4) {
      _snowman.a(_snowman, false);
      civ<?, ?> _snowman;
      if (this == bup.bC) {
         _snowman = kh.bF;
      } else {
         if (this != bup.bD) {
            _snowman.a(_snowman, _snowman, 3);
            return false;
         }

         _snowman = kh.bG;
      }

      if (_snowman.a(_snowman, _snowman.i().g(), _snowman, _snowman)) {
         return true;
      } else {
         _snowman.a(_snowman, _snowman, 3);
         return false;
      }
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return (double)_snowman.nextFloat() < 0.4;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      this.a(_snowman, _snowman, _snowman, _snowman);
   }
}
