import java.util.Random;

public class bym extends buu {
   public static final cfg a = cex.ag;
   private static final ddh[] b = new ddh[]{
      buo.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
   };

   protected bym(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a)];
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return _snowman.a(bup.cM);
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a) < 3;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      int _snowman = _snowman.c(a);
      if (_snowman < 3 && _snowman.nextInt(10) == 0) {
         _snowman = _snowman.a(a, Integer.valueOf(_snowman + 1));
         _snowman.a(_snowman, _snowman, 2);
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(bmd.nu);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
