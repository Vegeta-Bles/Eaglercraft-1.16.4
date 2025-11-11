public enum bte implements bta {
   a;

   private bte() {
   }

   @Override
   public bsv a(long var1, int var3, int var4, int var5, bsx.a var6) {
      int _snowman = _snowman - 2;
      int _snowmanx = _snowman - 2;
      int _snowmanxx = _snowman - 2;
      int _snowmanxxx = _snowman >> 2;
      int _snowmanxxxx = _snowmanx >> 2;
      int _snowmanxxxxx = _snowmanxx >> 2;
      double _snowmanxxxxxx = (double)(_snowman & 3) / 4.0;
      double _snowmanxxxxxxx = (double)(_snowmanx & 3) / 4.0;
      double _snowmanxxxxxxxx = (double)(_snowmanxx & 3) / 4.0;
      double[] _snowmanxxxxxxxxx = new double[8];

      for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 8; _snowmanxxxxxxxxxx++) {
         boolean _snowmanxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 4) == 0;
         boolean _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 2) == 0;
         boolean _snowmanxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 1) == 0;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? _snowmanxxx : _snowmanxxx + 1;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx ? _snowmanxxxx : _snowmanxxxx + 1;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? _snowmanxxxxx : _snowmanxxxxx + 1;
         double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? _snowmanxxxxxx : _snowmanxxxxxx - 1.0;
         double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxxx - 1.0;
         double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? _snowmanxxxxxxxx : _snowmanxxxxxxxx - 1.0;
         _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx] = a(_snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
      }

      int _snowmanxxxxxxxxxx = 0;
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx[0];

      for (int _snowmanxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxx++) {
         if (_snowmanxxxxxxxxxxx > _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxx]) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxxx];
         }
      }

      int _snowmanxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 4) == 0 ? _snowmanxxx : _snowmanxxx + 1;
      int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 2) == 0 ? _snowmanxxxx : _snowmanxxxx + 1;
      int _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxx & 1) == 0 ? _snowmanxxxxx : _snowmanxxxxx + 1;
      return _snowman.b(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
   }

   private static double a(long var0, int var2, int var3, int var4, double var5, double var7, double var9) {
      long var11 = afk.a(_snowman, (long)_snowman);
      var11 = afk.a(var11, (long)_snowman);
      var11 = afk.a(var11, (long)_snowman);
      var11 = afk.a(var11, (long)_snowman);
      var11 = afk.a(var11, (long)_snowman);
      var11 = afk.a(var11, (long)_snowman);
      double _snowman = a(var11);
      var11 = afk.a(var11, _snowman);
      double _snowmanx = a(var11);
      var11 = afk.a(var11, _snowman);
      double _snowmanxx = a(var11);
      return a(_snowman + _snowmanxx) + a(_snowman + _snowmanx) + a(_snowman + _snowman);
   }

   private static double a(long var0) {
      double _snowman = (double)((int)Math.floorMod(_snowman >> 24, 1024L)) / 1024.0;
      return (_snowman - 0.5) * 0.9;
   }

   private static double a(double var0) {
      return _snowman * _snowman;
   }
}
