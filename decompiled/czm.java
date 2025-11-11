public class czm extends czj {
   czm(czq[] var1, dbo[] var2) {
      super(_snowman, _snowman);
   }

   @Override
   public czr a() {
      return czo.h;
   }

   @Override
   protected czi a(czi[] var1) {
      switch (_snowman.length) {
         case 0:
            return b;
         case 1:
            return _snowman[0];
         case 2:
            czi _snowman = _snowman[0];
            czi _snowmanx = _snowman[1];
            return (var2x, var3x) -> {
               _snowman.expand(var2x, var3x);
               _snowman.expand(var2x, var3x);
               return true;
            };
         default:
            return (var1x, var2x) -> {
               for (czi _snowmanxx : _snowman) {
                  _snowmanxx.expand(var1x, var2x);
               }

               return true;
            };
      }
   }
}
