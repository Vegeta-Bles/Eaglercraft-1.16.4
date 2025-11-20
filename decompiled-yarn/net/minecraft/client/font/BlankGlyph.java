package net.minecraft.client.font;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Util;

public enum BlankGlyph implements RenderableGlyph {
   INSTANCE;

   private static final NativeImage IMAGE = Util.make(new NativeImage(NativeImage.Format.ABGR, 5, 8, false), _snowman -> {
      for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            boolean _snowmanxxx = _snowmanxx == 0 || _snowmanxx + 1 == 5 || _snowmanx == 0 || _snowmanx + 1 == 8;
            _snowman.setPixelColor(_snowmanxx, _snowmanx, _snowmanxxx ? -1 : 0);
         }
      }

      _snowman.untrack();
   });

   private BlankGlyph() {
   }

   @Override
   public int getWidth() {
      return 5;
   }

   @Override
   public int getHeight() {
      return 8;
   }

   @Override
   public float getAdvance() {
      return 6.0F;
   }

   @Override
   public float getOversample() {
      return 1.0F;
   }

   @Override
   public void upload(int x, int y) {
      IMAGE.upload(0, x, y, false);
   }

   @Override
   public boolean hasColor() {
      return true;
   }
}
