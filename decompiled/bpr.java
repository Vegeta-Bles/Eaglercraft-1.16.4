public class bpr extends bps {
   protected bpr(bps.a var1, aqf... var2) {
      super(_snowman, bpt.g, _snowman);
   }

   @Override
   public int a(int var1) {
      return 1 + 10 * (_snowman - 1);
   }

   @Override
   public int b(int var1) {
      return super.a(_snowman) + 50;
   }

   @Override
   public int a() {
      return 5;
   }

   @Override
   public boolean a(bmb var1) {
      return _snowman.b() == bmd.ng ? true : super.a(_snowman);
   }
}
