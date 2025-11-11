public abstract class zv extends cuj {
   protected zv(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(long var1) {
      return _snowman == brd.a;
   }

   @Override
   protected void a(long var1, int var3, boolean var4) {
      brd _snowman = new brd(_snowman);
      int _snowmanx = _snowman.b;
      int _snowmanxx = _snowman.c;

      for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
         for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
            long _snowmanxxxxx = brd.a(_snowmanx + _snowmanxxx, _snowmanxx + _snowmanxxxx);
            if (_snowmanxxxxx != _snowman) {
               this.b(_snowman, _snowmanxxxxx, _snowman, _snowman);
            }
         }
      }
   }

   @Override
   protected int a(long var1, long var3, int var5) {
      int _snowman = _snowman;
      brd _snowmanx = new brd(_snowman);
      int _snowmanxx = _snowmanx.b;
      int _snowmanxxx = _snowmanx.c;

      for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
         for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
            long _snowmanxxxxxx = brd.a(_snowmanxx + _snowmanxxxx, _snowmanxxx + _snowmanxxxxx);
            if (_snowmanxxxxxx == _snowman) {
               _snowmanxxxxxx = brd.a;
            }

            if (_snowmanxxxxxx != _snowman) {
               int _snowmanxxxxxxx = this.b(_snowmanxxxxxx, _snowman, this.c(_snowmanxxxxxx));
               if (_snowman > _snowmanxxxxxxx) {
                  _snowman = _snowmanxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected int b(long var1, long var3, int var5) {
      return _snowman == brd.a ? this.b(_snowman) : _snowman + 1;
   }

   protected abstract int b(long var1);

   public void b(long var1, int var3, boolean var4) {
      this.a(brd.a, _snowman, _snowman, _snowman);
   }
}
