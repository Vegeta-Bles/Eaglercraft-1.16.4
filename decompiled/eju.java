public class eju {
   private static final float[] a = x.a(new float[256], var0 -> {
      for (int _snowman = 0; _snowman < var0.length; _snowman++) {
         var0[_snowman] = (float)Math.pow((double)((float)_snowman / 255.0F), 2.2);
      }
   });

   public static det[] a(det var0, int var1) {
      det[] _snowman = new det[_snowman + 1];
      _snowman[0] = _snowman;
      if (_snowman > 0) {
         boolean _snowmanx = false;

         label51:
         for (int _snowmanxx = 0; _snowmanxx < _snowman.a(); _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.b(); _snowmanxxx++) {
               if (_snowman.a(_snowmanxx, _snowmanxxx) >> 24 == 0) {
                  _snowmanx = true;
                  break label51;
               }
            }
         }

         for (int _snowmanxx = 1; _snowmanxx <= _snowman; _snowmanxx++) {
            det _snowmanxxxx = _snowman[_snowmanxx - 1];
            det _snowmanxxxxx = new det(_snowmanxxxx.a() >> 1, _snowmanxxxx.b() >> 1, false);
            int _snowmanxxxxxx = _snowmanxxxxx.a();
            int _snowmanxxxxxxx = _snowmanxxxxx.b();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxx++) {
                  _snowmanxxxxx.a(
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     a(
                        _snowmanxxxx.a(_snowmanxxxxxxxx * 2 + 0, _snowmanxxxxxxxxx * 2 + 0),
                        _snowmanxxxx.a(_snowmanxxxxxxxx * 2 + 1, _snowmanxxxxxxxxx * 2 + 0),
                        _snowmanxxxx.a(_snowmanxxxxxxxx * 2 + 0, _snowmanxxxxxxxxx * 2 + 1),
                        _snowmanxxxx.a(_snowmanxxxxxxxx * 2 + 1, _snowmanxxxxxxxxx * 2 + 1),
                        _snowmanx
                     )
                  );
               }
            }

            _snowman[_snowmanxx] = _snowmanxxxxx;
         }
      }

      return _snowman;
   }

   private static int a(int var0, int var1, int var2, int var3, boolean var4) {
      if (_snowman) {
         float _snowman = 0.0F;
         float _snowmanx = 0.0F;
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 0.0F;
         if (_snowman >> 24 != 0) {
            _snowman += a(_snowman >> 24);
            _snowmanx += a(_snowman >> 16);
            _snowmanxx += a(_snowman >> 8);
            _snowmanxxx += a(_snowman >> 0);
         }

         if (_snowman >> 24 != 0) {
            _snowman += a(_snowman >> 24);
            _snowmanx += a(_snowman >> 16);
            _snowmanxx += a(_snowman >> 8);
            _snowmanxxx += a(_snowman >> 0);
         }

         if (_snowman >> 24 != 0) {
            _snowman += a(_snowman >> 24);
            _snowmanx += a(_snowman >> 16);
            _snowmanxx += a(_snowman >> 8);
            _snowmanxxx += a(_snowman >> 0);
         }

         if (_snowman >> 24 != 0) {
            _snowman += a(_snowman >> 24);
            _snowmanx += a(_snowman >> 16);
            _snowmanxx += a(_snowman >> 8);
            _snowmanxxx += a(_snowman >> 0);
         }

         _snowman /= 4.0F;
         _snowmanx /= 4.0F;
         _snowmanxx /= 4.0F;
         _snowmanxxx /= 4.0F;
         int _snowmanxxxx = (int)(Math.pow((double)_snowman, 0.45454545454545453) * 255.0);
         int _snowmanxxxxx = (int)(Math.pow((double)_snowmanx, 0.45454545454545453) * 255.0);
         int _snowmanxxxxxx = (int)(Math.pow((double)_snowmanxx, 0.45454545454545453) * 255.0);
         int _snowmanxxxxxxx = (int)(Math.pow((double)_snowmanxxx, 0.45454545454545453) * 255.0);
         if (_snowmanxxxx < 96) {
            _snowmanxxxx = 0;
         }

         return _snowmanxxxx << 24 | _snowmanxxxxx << 16 | _snowmanxxxxxx << 8 | _snowmanxxxxxxx;
      } else {
         int _snowmanxxxx = a(_snowman, _snowman, _snowman, _snowman, 24);
         int _snowmanxxxxx = a(_snowman, _snowman, _snowman, _snowman, 16);
         int _snowmanxxxxxx = a(_snowman, _snowman, _snowman, _snowman, 8);
         int _snowmanxxxxxxx = a(_snowman, _snowman, _snowman, _snowman, 0);
         return _snowmanxxxx << 24 | _snowmanxxxxx << 16 | _snowmanxxxxxx << 8 | _snowmanxxxxxxx;
      }
   }

   private static int a(int var0, int var1, int var2, int var3, int var4) {
      float _snowman = a(_snowman >> _snowman);
      float _snowmanx = a(_snowman >> _snowman);
      float _snowmanxx = a(_snowman >> _snowman);
      float _snowmanxxx = a(_snowman >> _snowman);
      float _snowmanxxxx = (float)((double)((float)Math.pow((double)(_snowman + _snowmanx + _snowmanxx + _snowmanxxx) * 0.25, 0.45454545454545453)));
      return (int)((double)_snowmanxxxx * 255.0);
   }

   private static float a(int var0) {
      return a[_snowman & 0xFF];
   }
}
