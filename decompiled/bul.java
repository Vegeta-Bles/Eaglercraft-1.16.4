import java.util.Random;

public class bul extends bvs {
   public static final cfg a = cex.ag;
   private static final ddh[] c = new ddh[]{
      buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
   };

   public bul(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public cfg c() {
      return a;
   }

   @Override
   public int d() {
      return 3;
   }

   @Override
   protected brw e() {
      return bmd.qg;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.nextInt(3) != 0) {
         super.b(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   protected int a(brx var1) {
      return super.a(_snowman) / 3;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c[_snowman.c(this.c())];
   }
}
