import java.util.Random;

public class efq extends eeu<aql> {
   public efq(eet var1) {
      super(_snowman);
   }

   public void a(aql var1, float var2, float var3, dfm var4, eag var5, int var6) {
      float[] _snowman = new float[8];
      float[] _snowmanx = new float[8];
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;
      Random _snowmanxxxx = new Random(_snowman.b);

      for (int _snowmanxxxxx = 7; _snowmanxxxxx >= 0; _snowmanxxxxx--) {
         _snowman[_snowmanxxxxx] = _snowmanxx;
         _snowmanx[_snowmanxxxxx] = _snowmanxxx;
         _snowmanxx += (float)(_snowmanxxxx.nextInt(11) - 5);
         _snowmanxxx += (float)(_snowmanxxxx.nextInt(11) - 5);
      }

      dfq _snowmanxxxxx = _snowman.getBuffer(eao.r());
      b _snowmanxxxxxx = _snowman.c().a();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
         Random _snowmanxxxxxxxx = new Random(_snowman.b);

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 3; _snowmanxxxxxxxxx++) {
            int _snowmanxxxxxxxxxx = 7;
            int _snowmanxxxxxxxxxxx = 0;
            if (_snowmanxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxx = 7 - _snowmanxxxxxxxxx;
            }

            if (_snowmanxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx - 2;
            }

            float _snowmanxxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxxx] - _snowmanxx;
            float _snowmanxxxxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxxx] - _snowmanxxx;

            for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx--) {
               float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxx += (float)(_snowmanxxxxxxxx.nextInt(11) - 5);
                  _snowmanxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxx.nextInt(11) - 5);
               } else {
                  _snowmanxxxxxxxxxxxx += (float)(_snowmanxxxxxxxx.nextInt(31) - 15);
                  _snowmanxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxx.nextInt(31) - 15);
               }

               float _snowmanxxxxxxxxxxxxxxxxx = 0.5F;
               float _snowmanxxxxxxxxxxxxxxxxxx = 0.45F;
               float _snowmanxxxxxxxxxxxxxxxxxxx = 0.45F;
               float _snowmanxxxxxxxxxxxxxxxxxxxx = 0.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxx = 0.1F + (float)_snowmanxxxxxxx * 0.2F;
               if (_snowmanxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxxxxxxxx * ((double)_snowmanxxxxxxxxxxxxxx * 0.1 + 1.0));
               }

               float _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0.1F + (float)_snowmanxxxxxxx * 0.2F;
               if (_snowmanxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx *= (float)(_snowmanxxxxxxxxxxxxxx - 1) * 0.1F + 1.0F;
               }

               a(
                  _snowmanxxxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  false,
                  false,
                  true,
                  false
               );
               a(
                  _snowmanxxxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  true,
                  false,
                  true,
                  true
               );
               a(
                  _snowmanxxxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  true,
                  true,
                  false,
                  true
               );
               a(
                  _snowmanxxxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  false,
                  true,
                  false,
                  false
               );
            }
         }
      }
   }

   private static void a(
      b var0,
      dfq var1,
      float var2,
      float var3,
      int var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      boolean var12,
      boolean var13,
      boolean var14,
      boolean var15
   ) {
      _snowman.a(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)(_snowman * 16), _snowman + (_snowman ? _snowman : -_snowman)).a(_snowman, _snowman, _snowman, 0.3F).d();
      _snowman.a(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)((_snowman + 1) * 16), _snowman + (_snowman ? _snowman : -_snowman)).a(_snowman, _snowman, _snowman, 0.3F).d();
      _snowman.a(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)((_snowman + 1) * 16), _snowman + (_snowman ? _snowman : -_snowman)).a(_snowman, _snowman, _snowman, 0.3F).d();
      _snowman.a(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)(_snowman * 16), _snowman + (_snowman ? _snowman : -_snowman)).a(_snowman, _snowman, _snowman, 0.3F).d();
   }

   public vk a(aql var1) {
      return ekb.d;
   }
}
