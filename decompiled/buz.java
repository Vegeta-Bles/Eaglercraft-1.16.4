public class buz extends bvs {
   private static final ddh[] a = new ddh[]{
      buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
   };

   public buz(ceg.c var1) {
      super(_snowman);
   }

   @Override
   protected brw e() {
      return bmd.oY;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a[_snowman.c(this.c())];
   }
}
