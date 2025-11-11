import javax.annotation.Nullable;

public class aab {
   @Nullable
   protected static fx a(aag var0, int var1, int var2, boolean var3) {
      fx.a _snowman = new fx.a(_snowman, 0, _snowman);
      bsv _snowmanx = _snowman.v(_snowman);
      boolean _snowmanxx = _snowman.k().c();
      ceh _snowmanxxx = _snowmanx.e().e().a();
      if (_snowman && !_snowmanxxx.b().a(aed.V)) {
         return null;
      } else {
         cgh _snowmanxxxx = _snowman.d(_snowman >> 4, _snowman >> 4);
         int _snowmanxxxxx = _snowmanxx ? _snowman.i().g().c() : _snowmanxxxx.a(chn.a.e, _snowman & 15, _snowman & 15);
         if (_snowmanxxxxx < 0) {
            return null;
         } else {
            int _snowmanxxxxxx = _snowmanxxxx.a(chn.a.b, _snowman & 15, _snowman & 15);
            if (_snowmanxxxxxx <= _snowmanxxxxx && _snowmanxxxxxx > _snowmanxxxx.a(chn.a.d, _snowman & 15, _snowman & 15)) {
               return null;
            } else {
               for (int _snowmanxxxxxxx = _snowmanxxxxx + 1; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
                  _snowman.d(_snowman, _snowmanxxxxxxx, _snowman);
                  ceh _snowmanxxxxxxxx = _snowman.d_(_snowman);
                  if (!_snowmanxxxxxxxx.m().c()) {
                     break;
                  }

                  if (_snowmanxxxxxxxx.equals(_snowmanxxx)) {
                     return _snowman.b().h();
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public static fx a(aag var0, brd var1, boolean var2) {
      for (int _snowman = _snowman.d(); _snowman <= _snowman.f(); _snowman++) {
         for (int _snowmanx = _snowman.e(); _snowmanx <= _snowman.g(); _snowmanx++) {
            fx _snowmanxx = a(_snowman, _snowman, _snowmanx, _snowman);
            if (_snowmanxx != null) {
               return _snowmanxx;
            }
         }
      }

      return null;
   }
}
