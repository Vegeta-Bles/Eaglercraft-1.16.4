public enum cvz implements cwn, cws {
   a;

   private cvz() {
   }

   @Override
   public int a(cvk var1, cvf var2, cvf var3, int var4, int var5) {
      int _snowman = _snowman.a(this.a(_snowman), this.b(_snowman));
      int _snowmanx = _snowman.a(this.a(_snowman), this.b(_snowman));
      if (!cvx.a(_snowman)) {
         return _snowman;
      } else {
         int _snowmanxx = 8;
         int _snowmanxxx = 4;

         for (int _snowmanxxxx = -8; _snowmanxxxx <= 8; _snowmanxxxx += 4) {
            for (int _snowmanxxxxx = -8; _snowmanxxxxx <= 8; _snowmanxxxxx += 4) {
               int _snowmanxxxxxx = _snowman.a(this.a(_snowman + _snowmanxxxx), this.b(_snowman + _snowmanxxxxx));
               if (!cvx.a(_snowmanxxxxxx)) {
                  if (_snowmanx == 44) {
                     return 45;
                  }

                  if (_snowmanx == 10) {
                     return 46;
                  }
               }
            }
         }

         if (_snowman == 24) {
            if (_snowmanx == 45) {
               return 48;
            }

            if (_snowmanx == 0) {
               return 24;
            }

            if (_snowmanx == 46) {
               return 49;
            }

            if (_snowmanx == 10) {
               return 50;
            }
         }

         return _snowmanx;
      }
   }
}
