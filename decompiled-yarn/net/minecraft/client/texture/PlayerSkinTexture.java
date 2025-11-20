package net.minecraft.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerSkinTexture extends ResourceTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   private final File cacheFile;
   private final String url;
   private final boolean convertLegacy;
   @Nullable
   private final Runnable loadedCallback;
   @Nullable
   private CompletableFuture<?> loader;
   private boolean loaded;

   public PlayerSkinTexture(@Nullable File cacheFile, String url, Identifier fallbackSkin, boolean convertLegacy, @Nullable Runnable callback) {
      super(fallbackSkin);
      this.cacheFile = cacheFile;
      this.url = url;
      this.convertLegacy = convertLegacy;
      this.loadedCallback = callback;
   }

   private void onTextureLoaded(NativeImage image) {
      if (this.loadedCallback != null) {
         this.loadedCallback.run();
      }

      MinecraftClient.getInstance().execute(() -> {
         this.loaded = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.uploadTexture(image));
         } else {
            this.uploadTexture(image);
         }
      });
   }

   private void uploadTexture(NativeImage image) {
      TextureUtil.allocate(this.getGlId(), image.getWidth(), image.getHeight());
      image.upload(0, 0, 0, true);
   }

   @Override
   public void load(ResourceManager manager) throws IOException {
      MinecraftClient.getInstance().execute(() -> {
         if (!this.loaded) {
            try {
               super.load(manager);
            } catch (IOException var3x) {
               LOGGER.warn("Failed to load texture: {}", this.location, var3x);
            }

            this.loaded = true;
         }
      });
      if (this.loader == null) {
         NativeImage _snowman;
         if (this.cacheFile != null && this.cacheFile.isFile()) {
            LOGGER.debug("Loading http texture from local cache ({})", this.cacheFile);
            FileInputStream _snowmanx = new FileInputStream(this.cacheFile);
            _snowman = this.loadTexture(_snowmanx);
         } else {
            _snowman = null;
         }

         if (_snowman != null) {
            this.onTextureLoaded(_snowman);
         } else {
            this.loader = CompletableFuture.runAsync(() -> {
               HttpURLConnection _snowmanx = null;
               LOGGER.debug("Downloading http texture from {} to {}", this.url, this.cacheFile);

               try {
                  _snowmanx = (HttpURLConnection)new URL(this.url).openConnection(MinecraftClient.getInstance().getNetworkProxy());
                  _snowmanx.setDoInput(true);
                  _snowmanx.setDoOutput(false);
                  _snowmanx.connect();
                  if (_snowmanx.getResponseCode() / 100 == 2) {
                     InputStream _snowmanx;
                     if (this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(_snowmanx.getInputStream(), this.cacheFile);
                        _snowmanx = new FileInputStream(this.cacheFile);
                     } else {
                        _snowmanx = _snowmanx.getInputStream();
                     }

                     MinecraftClient.getInstance().execute(() -> {
                        NativeImage _snowmanxx = this.loadTexture(_snowman);
                        if (_snowmanxx != null) {
                           this.onTextureLoaded(_snowmanxx);
                        }
                     });
                     return;
                  }
               } catch (Exception var6) {
                  LOGGER.error("Couldn't download http texture", var6);
                  return;
               } finally {
                  if (_snowmanx != null) {
                     _snowmanx.disconnect();
                  }
               }
            }, Util.getMainWorkerExecutor());
         }
      }
   }

   @Nullable
   private NativeImage loadTexture(InputStream stream) {
      NativeImage _snowman = null;

      try {
         _snowman = NativeImage.read(stream);
         if (this.convertLegacy) {
            _snowman = remapTexture(_snowman);
         }
      } catch (IOException var4) {
         LOGGER.warn("Error while loading the skin texture", var4);
      }

      return _snowman;
   }

   private static NativeImage remapTexture(NativeImage image) {
      boolean _snowman = image.getHeight() == 32;
      if (_snowman) {
         NativeImage _snowmanx = new NativeImage(64, 64, true);
         _snowmanx.copyFrom(image);
         image.close();
         image = _snowmanx;
         _snowmanx.fillRect(0, 32, 64, 32, 0);
         _snowmanx.copyRect(4, 16, 16, 32, 4, 4, true, false);
         _snowmanx.copyRect(8, 16, 16, 32, 4, 4, true, false);
         _snowmanx.copyRect(0, 20, 24, 32, 4, 12, true, false);
         _snowmanx.copyRect(4, 20, 16, 32, 4, 12, true, false);
         _snowmanx.copyRect(8, 20, 8, 32, 4, 12, true, false);
         _snowmanx.copyRect(12, 20, 16, 32, 4, 12, true, false);
         _snowmanx.copyRect(44, 16, -8, 32, 4, 4, true, false);
         _snowmanx.copyRect(48, 16, -8, 32, 4, 4, true, false);
         _snowmanx.copyRect(40, 20, 0, 32, 4, 12, true, false);
         _snowmanx.copyRect(44, 20, -8, 32, 4, 12, true, false);
         _snowmanx.copyRect(48, 20, -16, 32, 4, 12, true, false);
         _snowmanx.copyRect(52, 20, -8, 32, 4, 12, true, false);
      }

      stripAlpha(image, 0, 0, 32, 16);
      if (_snowman) {
         stripColor(image, 32, 0, 64, 32);
      }

      stripAlpha(image, 0, 16, 64, 32);
      stripAlpha(image, 16, 48, 48, 64);
      return image;
   }

   private static void stripColor(NativeImage image, int x1, int y1, int x2, int y2) {
      for (int _snowman = x1; _snowman < x2; _snowman++) {
         for (int _snowmanx = y1; _snowmanx < y2; _snowmanx++) {
            int _snowmanxx = image.getPixelColor(_snowman, _snowmanx);
            if ((_snowmanxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for (int _snowman = x1; _snowman < x2; _snowman++) {
         for (int _snowmanxx = y1; _snowmanxx < y2; _snowmanxx++) {
            image.setPixelColor(_snowman, _snowmanxx, image.getPixelColor(_snowman, _snowmanxx) & 16777215);
         }
      }
   }

   private static void stripAlpha(NativeImage image, int x1, int y1, int x2, int y2) {
      for (int _snowman = x1; _snowman < x2; _snowman++) {
         for (int _snowmanx = y1; _snowmanx < y2; _snowmanx++) {
            image.setPixelColor(_snowman, _snowmanx, image.getPixelColor(_snowman, _snowmanx) | 0xFF000000);
         }
      }
   }
}
