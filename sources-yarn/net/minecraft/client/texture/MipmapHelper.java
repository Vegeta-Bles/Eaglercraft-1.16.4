package net.minecraft.client.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
public class MipmapHelper {
   private static final float[] COLOR_FRACTIONS = Util.make(new float[256], fs -> {
      for (int i = 0; i < fs.length; i++) {
         fs[i] = (float)Math.pow((double)((float)i / 255.0F), 2.2);
      }
   });

   public static NativeImage[] getMipmapLevelsImages(NativeImage image, int mipmap) {
      NativeImage[] lvs = new NativeImage[mipmap + 1];
      lvs[0] = image;
      if (mipmap > 0) {
         boolean bl = false;

         label51:
         for (int j = 0; j < image.getWidth(); j++) {
            for (int k = 0; k < image.getHeight(); k++) {
               if (image.getPixelColor(j, k) >> 24 == 0) {
                  bl = true;
                  break label51;
               }
            }
         }

         for (int l = 1; l <= mipmap; l++) {
            NativeImage lv = lvs[l - 1];
            NativeImage lv2 = new NativeImage(lv.getWidth() >> 1, lv.getHeight() >> 1, false);
            int m = lv2.getWidth();
            int n = lv2.getHeight();

            for (int o = 0; o < m; o++) {
               for (int p = 0; p < n; p++) {
                  lv2.setPixelColor(
                     o,
                     p,
                     blend(
                        lv.getPixelColor(o * 2 + 0, p * 2 + 0),
                        lv.getPixelColor(o * 2 + 1, p * 2 + 0),
                        lv.getPixelColor(o * 2 + 0, p * 2 + 1),
                        lv.getPixelColor(o * 2 + 1, p * 2 + 1),
                        bl
                     )
                  );
               }
            }

            lvs[l] = lv2;
         }
      }

      return lvs;
   }

   private static int blend(int one, int two, int three, int four, boolean checkAlpha) {
      if (checkAlpha) {
         float f = 0.0F;
         float g = 0.0F;
         float h = 0.0F;
         float m = 0.0F;
         if (one >> 24 != 0) {
            f += getColorFraction(one >> 24);
            g += getColorFraction(one >> 16);
            h += getColorFraction(one >> 8);
            m += getColorFraction(one >> 0);
         }

         if (two >> 24 != 0) {
            f += getColorFraction(two >> 24);
            g += getColorFraction(two >> 16);
            h += getColorFraction(two >> 8);
            m += getColorFraction(two >> 0);
         }

         if (three >> 24 != 0) {
            f += getColorFraction(three >> 24);
            g += getColorFraction(three >> 16);
            h += getColorFraction(three >> 8);
            m += getColorFraction(three >> 0);
         }

         if (four >> 24 != 0) {
            f += getColorFraction(four >> 24);
            g += getColorFraction(four >> 16);
            h += getColorFraction(four >> 8);
            m += getColorFraction(four >> 0);
         }

         f /= 4.0F;
         g /= 4.0F;
         h /= 4.0F;
         m /= 4.0F;
         int n = (int)(Math.pow((double)f, 0.45454545454545453) * 255.0);
         int o = (int)(Math.pow((double)g, 0.45454545454545453) * 255.0);
         int p = (int)(Math.pow((double)h, 0.45454545454545453) * 255.0);
         int q = (int)(Math.pow((double)m, 0.45454545454545453) * 255.0);
         if (n < 96) {
            n = 0;
         }

         return n << 24 | o << 16 | p << 8 | q;
      } else {
         int r = getColorComponent(one, two, three, four, 24);
         int s = getColorComponent(one, two, three, four, 16);
         int t = getColorComponent(one, two, three, four, 8);
         int u = getColorComponent(one, two, three, four, 0);
         return r << 24 | s << 16 | t << 8 | u;
      }
   }

   private static int getColorComponent(int one, int two, int three, int four, int bits) {
      float f = getColorFraction(one >> bits);
      float g = getColorFraction(two >> bits);
      float h = getColorFraction(three >> bits);
      float n = getColorFraction(four >> bits);
      float o = (float)((double)((float)Math.pow((double)(f + g + h + n) * 0.25, 0.45454545454545453)));
      return (int)((double)o * 255.0);
   }

   private static float getColorFraction(int value) {
      return COLOR_FRACTIONS[value & 0xFF];
   }
}
