public class bmf extends blx {
   public bmf(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      buo _snowmanxx = _snowman.d_(_snowmanx).b();
      if (_snowmanxx.a(aed.M)) {
         bfw _snowmanxxx = _snowman.n();
         if (!_snowman.v && _snowmanxxx != null) {
            a(_snowmanxxx, _snowman, _snowmanx);
         }

         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   public static aou a(bfw var0, brx var1, fx var2) {
      bcq _snowman = null;
      boolean _snowmanx = false;
      double _snowmanxx = 7.0;
      int _snowmanxxx = _snowman.u();
      int _snowmanxxxx = _snowman.v();
      int _snowmanxxxxx = _snowman.w();

      for (aqn _snowmanxxxxxx : _snowman.a(
         aqn.class, new dci((double)_snowmanxxx - 7.0, (double)_snowmanxxxx - 7.0, (double)_snowmanxxxxx - 7.0, (double)_snowmanxxx + 7.0, (double)_snowmanxxxx + 7.0, (double)_snowmanxxxxx + 7.0)
      )) {
         if (_snowmanxxxxxx.eC() == _snowman) {
            if (_snowman == null) {
               _snowman = bcq.a(_snowman, _snowman);
            }

            _snowmanxxxxxx.b(_snowman, true);
            _snowmanx = true;
         }
      }

      return _snowmanx ? aou.a : aou.c;
   }
}
