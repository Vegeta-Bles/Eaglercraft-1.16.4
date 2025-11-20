package net.minecraft.client.realms.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.annotation.Nullable;

public class SkinProcessor {
   private int[] pixels;
   private int width;
   private int height;

   public SkinProcessor() {
   }

   @Nullable
   public BufferedImage process(BufferedImage image) {
      if (image == null) {
         return null;
      } else {
         this.width = 64;
         this.height = 64;
         BufferedImage _snowman = new BufferedImage(this.width, this.height, 2);
         Graphics _snowmanx = _snowman.getGraphics();
         _snowmanx.drawImage(image, 0, 0, null);
         boolean _snowmanxx = image.getHeight() == 32;
         if (_snowmanxx) {
            _snowmanx.setColor(new Color(0, 0, 0, 0));
            _snowmanx.fillRect(0, 32, 64, 32);
            _snowmanx.drawImage(_snowman, 24, 48, 20, 52, 4, 16, 8, 20, null);
            _snowmanx.drawImage(_snowman, 28, 48, 24, 52, 8, 16, 12, 20, null);
            _snowmanx.drawImage(_snowman, 20, 52, 16, 64, 8, 20, 12, 32, null);
            _snowmanx.drawImage(_snowman, 24, 52, 20, 64, 4, 20, 8, 32, null);
            _snowmanx.drawImage(_snowman, 28, 52, 24, 64, 0, 20, 4, 32, null);
            _snowmanx.drawImage(_snowman, 32, 52, 28, 64, 12, 20, 16, 32, null);
            _snowmanx.drawImage(_snowman, 40, 48, 36, 52, 44, 16, 48, 20, null);
            _snowmanx.drawImage(_snowman, 44, 48, 40, 52, 48, 16, 52, 20, null);
            _snowmanx.drawImage(_snowman, 36, 52, 32, 64, 48, 20, 52, 32, null);
            _snowmanx.drawImage(_snowman, 40, 52, 36, 64, 44, 20, 48, 32, null);
            _snowmanx.drawImage(_snowman, 44, 52, 40, 64, 40, 20, 44, 32, null);
            _snowmanx.drawImage(_snowman, 48, 52, 44, 64, 52, 20, 56, 32, null);
         }

         _snowmanx.dispose();
         this.pixels = ((DataBufferInt)_snowman.getRaster().getDataBuffer()).getData();
         this.setNoAlpha(0, 0, 32, 16);
         if (_snowmanxx) {
            this.doNotchTransparencyHack(32, 0, 64, 32);
         }

         this.setNoAlpha(0, 16, 64, 32);
         this.setNoAlpha(16, 48, 48, 64);
         return _snowman;
      }
   }

   private void doNotchTransparencyHack(int x0, int y0, int x1, int y1) {
      for (int _snowman = x0; _snowman < x1; _snowman++) {
         for (int _snowmanx = y0; _snowmanx < y1; _snowmanx++) {
            int _snowmanxx = this.pixels[_snowman + _snowmanx * this.width];
            if ((_snowmanxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for (int _snowman = x0; _snowman < x1; _snowman++) {
         for (int _snowmanxx = y0; _snowmanxx < y1; _snowmanxx++) {
            this.pixels[_snowman + _snowmanxx * this.width] = this.pixels[_snowman + _snowmanxx * this.width] & 16777215;
         }
      }
   }

   private void setNoAlpha(int x0, int y0, int x1, int y1) {
      for (int _snowman = x0; _snowman < x1; _snowman++) {
         for (int _snowmanx = y0; _snowmanx < y1; _snowmanx++) {
            this.pixels[_snowman + _snowmanx * this.width] = this.pixels[_snowman + _snowmanx * this.width] | 0xFF000000;
         }
      }
   }
}
