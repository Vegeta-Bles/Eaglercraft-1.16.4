import java.util.Random;

public class bye extends buo {
   public bye(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void a(brx var1, fx var2, aqa var3) {
      if (!_snowman.aD() && _snowman instanceof aqm && !bpu.i((aqm)_snowman)) {
         _snowman.a(apk.e, 1.0F);
      }

      super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      bus.a(_snowman, _snowman.b(), true);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == gc.b && _snowman.a(bup.A)) {
         _snowman.J().a(_snowman, this, 20);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      fx _snowman = _snowman.b();
      if (_snowman.b(_snowman).a(aef.b)) {
         _snowman.a(null, _snowman, adq.ej, adr.e, 0.5F, 2.6F + (_snowman.t.nextFloat() - _snowman.t.nextFloat()) * 0.8F);
         _snowman.a(hh.L, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.25, (double)_snowman.w() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      _snowman.J().a(_snowman, this, 20);
   }
}
