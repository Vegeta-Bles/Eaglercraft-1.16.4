import java.util.Random;

public class btv extends buo implements buq {
   protected static final ddh a = buo.a(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

   public btv(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ceg.b ah_() {
      return ceg.b.b;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      dcn _snowman = _snowman.n(_snowman, _snowman);
      return a.a(_snowman.b, _snowman.c, _snowman.d);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.nextInt(3) == 0 && _snowman.w(_snowman.b()) && _snowman.b(_snowman.b(), 0) >= 9) {
         this.a(_snowman, _snowman);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.c()).a(aed.ac);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         return bup.a.n();
      } else {
         if (_snowman == gc.b && _snowman.a(bup.kY)) {
            _snowman.a(_snowman, bup.kY.n(), 2);
         }

         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(bmd.bF);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.d_(_snowman.b()).g();
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      this.a(_snowman, _snowman);
   }

   @Override
   public float a(ceh var1, bfw var2, brc var3, fx var4) {
      return _snowman.dD().b() instanceof bnf ? 1.0F : super.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected void a(brx var1, fx var2) {
      _snowman.a(_snowman.b(), bup.kY.n().a(btu.e, ceu.b), 3);
   }
}
