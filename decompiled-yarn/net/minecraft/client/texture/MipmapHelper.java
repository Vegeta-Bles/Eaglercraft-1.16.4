package net.minecraft.client.texture;

import net.minecraft.util.Util;

public class MipmapHelper {
   private static final float[] COLOR_FRACTIONS = Util.make(new float[256], _snowman -> {
      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = (float)Math.pow((double)((float)_snowmanx / 255.0F), 2.2);
      }
   });

   public static NativeImage[] getMipmapLevelsImages(NativeImage image, int mipmap) {
      NativeImage[] _snowman = new NativeImage[mipmap + 1];
      _snowman[0] = image;
      if (mipmap > 0) {
         boolean _snowmanx = false;

         label51:
         for (int _snowmanxx = 0; _snowmanxx < image.getWidth(); _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < image.getHeight(); _snowmanxxx++) {
               if (image.getPixelColor(_snowmanxx, _snowmanxxx) >> 24 == 0) {
                  _snowmanx = true;
                  break label51;
               }
            }
         }

         for (int _snowmanxx = 1; _snowmanxx <= mipmap; _snowmanxx++) {
            NativeImage _snowmanxxxx = _snowman[_snowmanxx - 1];
            NativeImage _snowmanxxxxx = new NativeImage(_snowmanxxxx.getWidth() >> 1, _snowmanxxxx.getHeight() >> 1, false);
            int _snowmanxxxxxx = _snowmanxxxxx.getWidth();
            int _snowmanxxxxxxx = _snowmanxxxxx.getHeight();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxx++) {
                  _snowmanxxxxx.setPixelColor(
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     blend(
                        _snowmanxxxx.getPixelColor(_snowmanxxxxxxxx * 2 + 0, _snowmanxxxxxxxxx * 2 + 0),
                        _snowmanxxxx.getPixelColor(_snowmanxxxxxxxx * 2 + 1, _snowmanxxxxxxxxx * 2 + 0),
                        _snowmanxxxx.getPixelColor(_snowmanxxxxxxxx * 2 + 0, _snowmanxxxxxxxxx * 2 + 1),
                        _snowmanxxxx.getPixelColor(_snowmanxxxxxxxx * 2 + 1, _snowmanxxxxxxxxx * 2 + 1),
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

   private static int blend(int one, int two, int three, int four, boolean checkAlpha) {
      if (checkAlpha) {
         float _snowman = 0.0F;
         float _snowmanx = 0.0F;
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 0.0F;
         if (one >> 24 != 0) {
            _snowman += getColorFraction(one >> 24);
            _snowmanx += getColorFraction(one >> 16);
            _snowmanxx += getColorFraction(one >> 8);
            _snowmanxxx += getColorFraction(one >> 0);
         }

         if (two >> 24 != 0) {
            _snowman += getColorFraction(two >> 24);
            _snowmanx += getColorFraction(two >> 16);
            _snowmanxx += getColorFraction(two >> 8);
            _snowmanxxx += getColorFraction(two >> 0);
         }

         if (three >> 24 != 0) {
            _snowman += getColorFraction(three >> 24);
            _snowmanx += getColorFraction(three >> 16);
            _snowmanxx += getColorFraction(three >> 8);
            _snowmanxxx += getColorFraction(three >> 0);
         }

         if (four >> 24 != 0) {
            _snowman += getColorFraction(four >> 24);
            _snowmanx += getColorFraction(four >> 16);
            _snowmanxx += getColorFraction(four >> 8);
            _snowmanxxx += getColorFraction(four >> 0);
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
         int _snowmanxxxx = getColorComponent(one, two, three, four, 24);
         int _snowmanxxxxx = getColorComponent(one, two, three, four, 16);
         int _snowmanxxxxxx = getColorComponent(one, two, three, four, 8);
         int _snowmanxxxxxxx = getColorComponent(one, two, three, four, 0);
         return _snowmanxxxx << 24 | _snowmanxxxxx << 16 | _snowmanxxxxxx << 8 | _snowmanxxxxxxx;
      }
   }

   private static int getColorComponent(int one, int two, int three, int four, int bits) {
      float _snowman = getColorFraction(one >> bits);
      float _snowmanx = getColorFraction(two >> bits);
      float _snowmanxx = getColorFraction(three >> bits);
      float _snowmanxxx = getColorFraction(four >> bits);
      float _snowmanxxxx = (float)((double)((float)Math.pow((double)(_snowman + _snowmanx + _snowmanxx + _snowmanxxx) * 0.25, 0.45454545454545453)));
      return (int)((double)_snowmanxxxx * 255.0);
   }

   private static float getColorFraction(int value) {
      return COLOR_FRACTIONS[value & 0xFF];
   }
}
