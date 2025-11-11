public class czu extends czj {
   czu(czq[] var1, dbo[] var2) {
      super(_snowman, _snowman);
   }

   @Override
   public czr a() {
      return czo.g;
   }

   @Override
   protected czi a(czi[] var1) {
      switch (_snowman.length) {
         case 0:
            return b;
         case 1:
            return _snowman[0];
         case 2:
            return _snowman[0].a(_snowman[1]);
         default:
            return (var1x, var2) -> {
               for (czi _snowman : _snowman) {
                  if (!_snowman.expand(var1x, var2)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }
}
