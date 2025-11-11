import java.util.Random;

public class cjk extends cjl<cmh> {
   public static final fx a = fx.b;
   private final boolean ab;

   public cjk(boolean var1) {
      super(cmh.a);
      this.ab = _snowman;
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      for (fx _snowman : fx.a(new fx(_snowman.u() - 4, _snowman.v() - 1, _snowman.w() - 4), new fx(_snowman.u() + 4, _snowman.v() + 32, _snowman.w() + 4))) {
         boolean _snowmanx = _snowman.a(_snowman, 2.5);
         if (_snowmanx || _snowman.a(_snowman, 3.5)) {
            if (_snowman.v() < _snowman.v()) {
               if (_snowmanx) {
                  this.a(_snowman, _snowman, bup.z.n());
               } else if (_snowman.v() < _snowman.v()) {
                  this.a(_snowman, _snowman, bup.ee.n());
               }
            } else if (_snowman.v() > _snowman.v()) {
               this.a(_snowman, _snowman, bup.a.n());
            } else if (!_snowmanx) {
               this.a(_snowman, _snowman, bup.z.n());
            } else if (this.ab) {
               this.a(_snowman, new fx(_snowman), bup.ec.n());
            } else {
               this.a(_snowman, new fx(_snowman), bup.a.n());
            }
         }
      }

      for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
         this.a(_snowman, _snowman.b(_snowmanx), bup.z.n());
      }

      fx _snowmanx = _snowman.b(2);

      for (gc _snowmanxx : gc.c.a) {
         this.a(_snowman, _snowmanx.a(_snowmanxx), bup.bM.n().a(cbn.a, _snowmanxx));
      }

      return true;
   }
}
