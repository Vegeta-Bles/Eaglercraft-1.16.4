package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public class OverlayTexture implements AutoCloseable {
   public static final int DEFAULT_UV = packUv(0, 10);
   private final NativeImageBackedTexture texture = new NativeImageBackedTexture(16, 16, false);

   public OverlayTexture() {
      NativeImage _snowman = this.texture.getImage();

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
            if (_snowmanx < 8) {
               _snowman.setPixelColor(_snowmanxx, _snowmanx, -1308622593);
            } else {
               int _snowmanxxx = (int)((1.0F - (float)_snowmanxx / 15.0F * 0.75F) * 255.0F);
               _snowman.setPixelColor(_snowmanxx, _snowmanx, _snowmanxxx << 24 | 16777215);
            }
         }
      }

      RenderSystem.activeTexture(33985);
      this.texture.bindTexture();
      RenderSystem.matrixMode(5890);
      RenderSystem.loadIdentity();
      float _snowmanx = 0.06666667F;
      RenderSystem.scalef(0.06666667F, 0.06666667F, 0.06666667F);
      RenderSystem.matrixMode(5888);
      this.texture.bindTexture();
      _snowman.upload(0, 0, 0, 0, 0, _snowman.getWidth(), _snowman.getHeight(), false, true, false, false);
      RenderSystem.activeTexture(33984);
   }

   @Override
   public void close() {
      this.texture.close();
   }

   public void setupOverlayColor() {
      RenderSystem.setupOverlayColor(this.texture::getGlId, 16);
   }

   public static int getU(float whiteOverlayProgress) {
      return (int)(whiteOverlayProgress * 15.0F);
   }

   public static int getV(boolean hurt) {
      return hurt ? 3 : 10;
   }

   public static int packUv(int u, int v) {
      return u | v << 16;
   }

   public static int getUv(float _snowman, boolean hurt) {
      return packUv(getU(_snowman), getV(hurt));
   }

   public void teardownOverlayColor() {
      RenderSystem.teardownOverlayColor();
   }
}
