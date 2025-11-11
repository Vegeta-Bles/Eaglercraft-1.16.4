import java.util.Iterator;

public interface uz<T> {
   default void a(int var1, int var2, int var3, boq<?> var4, Iterator<T> var5, int var6) {
      int _snowman = _snowman;
      int _snowmanx = _snowman;
      if (_snowman instanceof bov) {
         bov _snowmanxx = (bov)_snowman;
         _snowman = _snowmanxx.i();
         _snowmanx = _snowmanxx.j();
      }

      int _snowmanxx = 0;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
         if (_snowmanxx == _snowman) {
            _snowmanxx++;
         }

         boolean _snowmanxxxx = (float)_snowmanx < (float)_snowman / 2.0F;
         int _snowmanxxxxx = afm.d((float)_snowman / 2.0F - (float)_snowmanx / 2.0F);
         if (_snowmanxxxx && _snowmanxxxxx > _snowmanxxx) {
            _snowmanxx += _snowman;
            _snowmanxxx++;
         }

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman; _snowmanxxxxxx++) {
            if (!_snowman.hasNext()) {
               return;
            }

            _snowmanxxxx = (float)_snowman < (float)_snowman / 2.0F;
            _snowmanxxxxx = afm.d((float)_snowman / 2.0F - (float)_snowman / 2.0F);
            int _snowmanxxxxxxx = _snowman;
            boolean _snowmanxxxxxxxx = _snowmanxxxxxx < _snowman;
            if (_snowmanxxxx) {
               _snowmanxxxxxxx = _snowmanxxxxx + _snowman;
               _snowmanxxxxxxxx = _snowmanxxxxx <= _snowmanxxxxxx && _snowmanxxxxxx < _snowmanxxxxx + _snowman;
            }

            if (_snowmanxxxxxxxx) {
               this.a(_snowman, _snowmanxx, _snowman, _snowmanxxx, _snowmanxxxxxx);
            } else if (_snowmanxxxxxxx == _snowmanxxxxxx) {
               _snowmanxx += _snowman - _snowmanxxxxxx;
               break;
            }

            _snowmanxx++;
         }
      }
   }

   void a(Iterator<T> var1, int var2, int var3, int var4, int var5);
}
