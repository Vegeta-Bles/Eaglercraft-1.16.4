import com.mojang.serialization.Codec;
import java.util.Random;

public class cjp extends cij {
   public cjp(Codec<cmb> var1) {
      super(_snowman);
   }

   @Override
   protected void a(bry var1, Random var2, fx var3, int var4, fx.a var5, cmb var6) {
      int _snowman = _snowman.d;

      for (int _snowmanx = -_snowman; _snowmanx <= _snowman; _snowmanx++) {
         for (int _snowmanxx = -_snowman; _snowmanxx <= _snowman; _snowmanxx++) {
            boolean _snowmanxxx = _snowmanx == -_snowman;
            boolean _snowmanxxxx = _snowmanx == _snowman;
            boolean _snowmanxxxxx = _snowmanxx == -_snowman;
            boolean _snowmanxxxxxx = _snowmanxx == _snowman;
            boolean _snowmanxxxxxxx = _snowmanxxx || _snowmanxxxx;
            boolean _snowmanxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
            if (!_snowmanxxxxxxx || !_snowmanxxxxxxxx) {
               _snowman.a(_snowman, _snowmanx, _snowman, _snowmanxx);
               if (!_snowman.d_(_snowman).i(_snowman, _snowman)) {
                  boolean _snowmanxxxxxxxxx = _snowmanxxx || _snowmanxxxxxxxx && _snowmanx == 1 - _snowman;
                  boolean _snowmanxxxxxxxxxx = _snowmanxxxx || _snowmanxxxxxxxx && _snowmanx == _snowman - 1;
                  boolean _snowmanxxxxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxxx && _snowmanxx == 1 - _snowman;
                  boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx || _snowmanxxxxxxx && _snowmanxx == _snowman - 1;
                  this.a(
                     _snowman,
                     _snowman,
                     _snowman.b
                        .a(_snowman, _snowman)
                        .a(bxn.d, Boolean.valueOf(_snowmanxxxxxxxxx))
                        .a(bxn.b, Boolean.valueOf(_snowmanxxxxxxxxxx))
                        .a(bxn.a, Boolean.valueOf(_snowmanxxxxxxxxxxx))
                        .a(bxn.c, Boolean.valueOf(_snowmanxxxxxxxxxxxx))
                  );
               }
            }
         }
      }
   }

   @Override
   protected int a(int var1, int var2, int var3, int var4) {
      return _snowman <= 3 ? 0 : _snowman;
   }
}
