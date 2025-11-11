public interface beo {
   int eM();

   static boolean a(aqm var0, aqm var1) {
      float _snowman = (float)_snowman.b(arl.f);
      float _snowmanx;
      if (!_snowman.w_() && (int)_snowman > 0) {
         _snowmanx = _snowman / 2.0F + (float)_snowman.l.t.nextInt((int)_snowman);
      } else {
         _snowmanx = _snowman;
      }

      boolean _snowmanxx = _snowman.a(apk.c(_snowman), _snowmanx);
      if (_snowmanxx) {
         _snowman.a(_snowman, _snowman);
         if (!_snowman.w_()) {
            b(_snowman, _snowman);
         }
      }

      return _snowmanxx;
   }

   static void b(aqm var0, aqm var1) {
      double _snowman = _snowman.b(arl.g);
      double _snowmanx = _snowman.b(arl.c);
      double _snowmanxx = _snowman - _snowmanx;
      if (!(_snowmanxx <= 0.0)) {
         double _snowmanxxx = _snowman.cD() - _snowman.cD();
         double _snowmanxxxx = _snowman.cH() - _snowman.cH();
         float _snowmanxxxxx = (float)(_snowman.l.t.nextInt(21) - 10);
         double _snowmanxxxxxx = _snowmanxx * (double)(_snowman.l.t.nextFloat() * 0.5F + 0.2F);
         dcn _snowmanxxxxxxx = new dcn(_snowmanxxx, 0.0, _snowmanxxxx).d().a(_snowmanxxxxxx).b(_snowmanxxxxx);
         double _snowmanxxxxxxxx = _snowmanxx * (double)_snowman.l.t.nextFloat() * 0.5;
         _snowman.i(_snowmanxxxxxxx.b, _snowmanxxxxxxxx, _snowmanxxxxxxx.d);
         _snowman.w = true;
      }
   }
}
