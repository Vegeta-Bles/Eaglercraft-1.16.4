import java.util.Random;

public class bpq extends bps {
   protected bpq(bps.a var1, aqf... var2) {
      super(_snowman, bpt.j, _snowman);
   }

   @Override
   public int a(int var1) {
      return 5 + (_snowman - 1) * 8;
   }

   @Override
   public int b(int var1) {
      return super.a(_snowman) + 50;
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(bmb var1) {
      return _snowman.e() ? true : super.a(_snowman);
   }

   public static boolean a(bmb var0, int var1, Random var2) {
      return _snowman.b() instanceof bjy && _snowman.nextFloat() < 0.6F ? false : _snowman.nextInt(_snowman + 1) > 0;
   }
}
