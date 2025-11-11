public class bvr extends buo {
   private static final nr a = new of("container.crafting");

   protected bvr(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else {
         _snowman.a(_snowman.b(_snowman, _snowman));
         _snowman.a(aea.am);
         return aou.b;
      }
   }

   @Override
   public aox b(ceh var1, brx var2, fx var3) {
      return new apb((var2x, var3x, var4) -> new bip(var2x, var3x, bim.a(_snowman, _snowman)), a);
   }
}
