import com.mojang.serialization.Codec;
import java.util.Random;

public class cjs extends cij {
   public cjs(Codec<cmb> var1) {
      super(_snowman);
   }

   @Override
   protected void a(bry var1, Random var2, fx var3, int var4, fx.a var5, cmb var6) {
      for (int _snowman = _snowman - 3; _snowman <= _snowman; _snowman++) {
         int _snowmanx = _snowman < _snowman ? _snowman.d : _snowman.d - 1;
         int _snowmanxx = _snowman.d - 2;

         for (int _snowmanxxx = -_snowmanx; _snowmanxxx <= _snowmanx; _snowmanxxx++) {
            for (int _snowmanxxxx = -_snowmanx; _snowmanxxxx <= _snowmanx; _snowmanxxxx++) {
               boolean _snowmanxxxxx = _snowmanxxx == -_snowmanx;
               boolean _snowmanxxxxxx = _snowmanxxx == _snowmanx;
               boolean _snowmanxxxxxxx = _snowmanxxxx == -_snowmanx;
               boolean _snowmanxxxxxxxx = _snowmanxxxx == _snowmanx;
               boolean _snowmanxxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
               boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxx || _snowmanxxxxxxxx;
               if (_snowman >= _snowman || _snowmanxxxxxxxxx != _snowmanxxxxxxxxxx) {
                  _snowman.a(_snowman, _snowmanxxx, _snowman, _snowmanxxxx);
                  if (!_snowman.d_(_snowman).i(_snowman, _snowman)) {
                     this.a(
                        _snowman,
                        _snowman,
                        _snowman.b
                           .a(_snowman, _snowman)
                           .a(bxn.e, Boolean.valueOf(_snowman >= _snowman - 1))
                           .a(bxn.d, Boolean.valueOf(_snowmanxxx < -_snowmanxx))
                           .a(bxn.b, Boolean.valueOf(_snowmanxxx > _snowmanxx))
                           .a(bxn.a, Boolean.valueOf(_snowmanxxxx < -_snowmanxx))
                           .a(bxn.c, Boolean.valueOf(_snowmanxxxx > _snowmanxx))
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   protected int a(int var1, int var2, int var3, int var4) {
      int _snowman = 0;
      if (_snowman < _snowman && _snowman >= _snowman - 3) {
         _snowman = _snowman;
      } else if (_snowman == _snowman) {
         _snowman = _snowman;
      }

      return _snowman;
   }
}
