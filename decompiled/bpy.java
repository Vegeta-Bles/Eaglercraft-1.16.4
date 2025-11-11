public class bpy extends bps {
   protected bpy(bps.a var1, bpt var2, aqf... var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public int a(int var1) {
      return 15 + (_snowman - 1) * 9;
   }

   @Override
   public int b(int var1) {
      return super.a(_snowman) + 50;
   }

   @Override
   public int a() {
      return 3;
   }
}
