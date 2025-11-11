public class bpx extends bps {
   protected bpx(bps.a var1, aqf... var2) {
      super(_snowman, bpt.f, _snowman);
   }

   @Override
   public int a(int var1) {
      return 10 + 20 * (_snowman - 1);
   }

   @Override
   public int b(int var1) {
      return super.a(_snowman) + 50;
   }

   @Override
   public int a() {
      return 2;
   }
}
