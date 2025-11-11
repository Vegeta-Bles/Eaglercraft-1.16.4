import java.util.Random;

public class cad extends buo {
   protected static final ddh a = buo.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

   public cad(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public ddh e(ceh var1, brc var2, fx var3) {
      return dde.b();
   }

   @Override
   public ddh a(ceh var1, brc var2, fx var3, dcs var4) {
      return dde.b();
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      bus.a(_snowman, _snowman.b(), false);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman == gc.b && _snowman.a(bup.A)) {
         _snowman.J().a(_snowman, this, 20);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      _snowman.J().a(_snowman, this, 20);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
