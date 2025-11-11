import java.util.Random;

public class bzo extends buu implements buq {
   public static final cfg a = cex.aA;
   protected static final ddh b = buo.a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   private final cdq c;

   protected bzo(cdq var1, ceg.c var2) {
      super(_snowman);
      this.c = _snowman;
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.B(_snowman.b()) >= 9 && _snowman.nextInt(7) == 0) {
         this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(aag var1, fx var2, ceh var3, Random var4) {
      if (_snowman.c(a) == 0) {
         _snowman.a(_snowman, _snowman.a(a), 4);
      } else {
         this.c.a(_snowman, _snowman.i().g(), _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return (double)_snowman.t.nextFloat() < 0.45;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      this.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
