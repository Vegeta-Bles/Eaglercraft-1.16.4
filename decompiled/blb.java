import java.util.List;

public interface blb {
   default boolean a(bmb var1) {
      md _snowman = _snowman.b("display");
      return _snowman != null && _snowman.c("color", 99);
   }

   default int b(bmb var1) {
      md _snowman = _snowman.b("display");
      return _snowman != null && _snowman.c("color", 99) ? _snowman.h("color") : 10511680;
   }

   default void c(bmb var1) {
      md _snowman = _snowman.b("display");
      if (_snowman != null && _snowman.e("color")) {
         _snowman.r("color");
      }
   }

   default void a(bmb var1, int var2) {
      _snowman.a("display").b("color", _snowman);
   }

   static bmb a(bmb var0, List<bky> var1) {
      bmb _snowman = bmb.b;
      int[] _snowmanx = new int[3];
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      blb _snowmanxxxx = null;
      blx _snowmanxxxxx = _snowman.b();
      if (_snowmanxxxxx instanceof blb) {
         _snowmanxxxx = (blb)_snowmanxxxxx;
         _snowman = _snowman.i();
         _snowman.e(1);
         if (_snowmanxxxx.a(_snowman)) {
            int _snowmanxxxxxx = _snowmanxxxx.b(_snowman);
            float _snowmanxxxxxxx = (float)(_snowmanxxxxxx >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxx = (float)(_snowmanxxxxxx >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx & 0xFF) / 255.0F;
            _snowmanxx = (int)((float)_snowmanxx + Math.max(_snowmanxxxxxxx, Math.max(_snowmanxxxxxxxx, _snowmanxxxxxxxxx)) * 255.0F);
            _snowmanx[0] = (int)((float)_snowmanx[0] + _snowmanxxxxxxx * 255.0F);
            _snowmanx[1] = (int)((float)_snowmanx[1] + _snowmanxxxxxxxx * 255.0F);
            _snowmanx[2] = (int)((float)_snowmanx[2] + _snowmanxxxxxxxxx * 255.0F);
            _snowmanxxx++;
         }

         for (bky _snowmanxxxxxx : _snowman) {
            float[] _snowmanxxxxxxx = _snowmanxxxxxx.d().e();
            int _snowmanxxxxxxxx = (int)(_snowmanxxxxxxx[0] * 255.0F);
            int _snowmanxxxxxxxxx = (int)(_snowmanxxxxxxx[1] * 255.0F);
            int _snowmanxxxxxxxxxx = (int)(_snowmanxxxxxxx[2] * 255.0F);
            _snowmanxx += Math.max(_snowmanxxxxxxxx, Math.max(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx));
            _snowmanx[0] += _snowmanxxxxxxxx;
            _snowmanx[1] += _snowmanxxxxxxxxx;
            _snowmanx[2] += _snowmanxxxxxxxxxx;
            _snowmanxxx++;
         }
      }

      if (_snowmanxxxx == null) {
         return bmb.b;
      } else {
         int _snowmanxxxxxx = _snowmanx[0] / _snowmanxxx;
         int _snowmanxxxxxxx = _snowmanx[1] / _snowmanxxx;
         int _snowmanxxxxxxxx = _snowmanx[2] / _snowmanxxx;
         float _snowmanxxxxxxxxx = (float)_snowmanxx / (float)_snowmanxxx;
         float _snowmanxxxxxxxxxx = (float)Math.max(_snowmanxxxxxx, Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxx));
         _snowmanxxxxxx = (int)((float)_snowmanxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         _snowmanxxxxxxx = (int)((float)_snowmanxxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx = (int)((float)_snowmanxxxxxxxx * _snowmanxxxxxxxxx / _snowmanxxxxxxxxxx);
         int var26 = (_snowmanxxxxxx << 8) + _snowmanxxxxxxx;
         var26 = (var26 << 8) + _snowmanxxxxxxxx;
         _snowmanxxxx.a(_snowman, var26);
         return _snowman;
      }
   }
}
