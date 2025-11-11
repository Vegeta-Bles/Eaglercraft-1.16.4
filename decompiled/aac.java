public abstract class aac extends cuj {
   protected aac(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(long var1) {
      return _snowman == Long.MAX_VALUE;
   }

   @Override
   protected void a(long var1, int var3, boolean var4) {
      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
               long _snowmanxxx = gp.a(_snowman, _snowman, _snowmanx, _snowmanxx);
               if (_snowmanxxx != _snowman) {
                  this.b(_snowman, _snowmanxxx, _snowman, _snowman);
               }
            }
         }
      }
   }

   @Override
   protected int a(long var1, long var3, int var5) {
      int _snowman = _snowman;

      for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
         for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
            for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
               long _snowmanxxxx = gp.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
               if (_snowmanxxxx == _snowman) {
                  _snowmanxxxx = Long.MAX_VALUE;
               }

               if (_snowmanxxxx != _snowman) {
                  int _snowmanxxxxx = this.b(_snowmanxxxx, _snowman, this.c(_snowmanxxxx));
                  if (_snowman > _snowmanxxxxx) {
                     _snowman = _snowmanxxxxx;
                  }

                  if (_snowman == 0) {
                     return _snowman;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected int b(long var1, long var3, int var5) {
      return _snowman == Long.MAX_VALUE ? this.b(_snowman) : _snowman + 1;
   }

   protected abstract int b(long var1);

   public void b(long var1, int var3, boolean var4) {
      this.a(Long.MAX_VALUE, _snowman, _snowman, _snowman);
   }
}
