package net.minecraft.client.realms.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsTextureManager {
   private static final Map<String, RealmsTextureManager.RealmsTexture> textures = Maps.newHashMap();
   private static final Map<String, Boolean> skinFetchStatus = Maps.newHashMap();
   private static final Map<String, String> fetchedSkins = Maps.newHashMap();
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier field_22730 = new Identifier("textures/gui/presets/isles.png");

   public static void bindWorldTemplate(String id, @Nullable String image) {
      if (image == null) {
         MinecraftClient.getInstance().getTextureManager().bindTexture(field_22730);
      } else {
         int _snowman = getTextureId(id, image);
         RenderSystem.bindTexture(_snowman);
      }
   }

   public static void withBoundFace(String uuid, Runnable r) {
      RenderSystem.pushTextureAttributes();

      try {
         bindFace(uuid);
         r.run();
      } finally {
         RenderSystem.popAttributes();
      }
   }

   private static void bindDefaultFace(UUID uuid) {
      MinecraftClient.getInstance().getTextureManager().bindTexture(DefaultSkinHelper.getTexture(uuid));
   }

   private static void bindFace(String uuid) {
      UUID _snowman = UUIDTypeAdapter.fromString(uuid);
      if (textures.containsKey(uuid)) {
         RenderSystem.bindTexture(textures.get(uuid).textureId);
      } else if (skinFetchStatus.containsKey(uuid)) {
         if (!skinFetchStatus.get(uuid)) {
            bindDefaultFace(_snowman);
         } else if (fetchedSkins.containsKey(uuid)) {
            int _snowmanx = getTextureId(uuid, fetchedSkins.get(uuid));
            RenderSystem.bindTexture(_snowmanx);
         } else {
            bindDefaultFace(_snowman);
         }
      } else {
         skinFetchStatus.put(uuid, false);
         bindDefaultFace(_snowman);
         Thread _snowmanx = new Thread("Realms Texture Downloader") {
            @Override
            public void run() {
               Map<Type, MinecraftProfileTexture> _snowman = RealmsUtil.getTextures(uuid);
               if (_snowman.containsKey(Type.SKIN)) {
                  MinecraftProfileTexture _snowmanx = _snowman.get(Type.SKIN);
                  String _snowmanxx = _snowmanx.getUrl();
                  HttpURLConnection _snowmanxxx = null;
                  RealmsTextureManager.LOGGER.debug("Downloading http texture from {}", _snowmanxx);

                  try {
                     try {
                        _snowmanxxx = (HttpURLConnection)new URL(_snowmanxx).openConnection(MinecraftClient.getInstance().getNetworkProxy());
                        _snowmanxxx.setDoInput(true);
                        _snowmanxxx.setDoOutput(false);
                        _snowmanxxx.connect();
                        if (_snowmanxxx.getResponseCode() / 100 != 2) {
                           RealmsTextureManager.skinFetchStatus.remove(uuid);
                           return;
                        }

                        BufferedImage _snowmanxxxx;
                        try {
                           _snowmanxxxx = ImageIO.read(_snowmanxxx.getInputStream());
                        } catch (Exception var17) {
                           RealmsTextureManager.skinFetchStatus.remove(uuid);
                           return;
                        } finally {
                           IOUtils.closeQuietly(_snowmanxxx.getInputStream());
                        }

                        _snowmanxxxx = new SkinProcessor().process(_snowmanxxxx);
                        ByteArrayOutputStream _snowmanxxxxx = new ByteArrayOutputStream();
                        ImageIO.write(_snowmanxxxx, "png", _snowmanxxxxx);
                        RealmsTextureManager.fetchedSkins.put(uuid, new Base64().encodeToString(_snowmanxxxxx.toByteArray()));
                        RealmsTextureManager.skinFetchStatus.put(uuid, true);
                     } catch (Exception var19) {
                        RealmsTextureManager.LOGGER.error("Couldn't download http texture", var19);
                        RealmsTextureManager.skinFetchStatus.remove(uuid);
                     }
                  } finally {
                     if (_snowmanxxx != null) {
                        _snowmanxxx.disconnect();
                     }
                  }
               } else {
                  RealmsTextureManager.skinFetchStatus.put(uuid, true);
               }
            }
         };
         _snowmanx.setDaemon(true);
         _snowmanx.start();
      }
   }

   private static int getTextureId(String id, String image) {
      int _snowman;
      if (textures.containsKey(id)) {
         RealmsTextureManager.RealmsTexture _snowmanx = textures.get(id);
         if (_snowmanx.image.equals(image)) {
            return _snowmanx.textureId;
         }

         RenderSystem.deleteTexture(_snowmanx.textureId);
         _snowman = _snowmanx.textureId;
      } else {
         _snowman = GlStateManager.genTextures();
      }

      IntBuffer _snowmanx = null;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      try {
         InputStream _snowmanxxxx = new ByteArrayInputStream(new Base64().decode(image));

         BufferedImage _snowmanxxxxx;
         try {
            _snowmanxxxxx = ImageIO.read(_snowmanxxxx);
         } finally {
            IOUtils.closeQuietly(_snowmanxxxx);
         }

         _snowmanxx = _snowmanxxxxx.getWidth();
         _snowmanxxx = _snowmanxxxxx.getHeight();
         int[] var8 = new int[_snowmanxx * _snowmanxxx];
         _snowmanxxxxx.getRGB(0, 0, _snowmanxx, _snowmanxxx, var8, 0, _snowmanxx);
         _snowmanx = ByteBuffer.allocateDirect(4 * _snowmanxx * _snowmanxxx).order(ByteOrder.nativeOrder()).asIntBuffer();
         _snowmanx.put(var8);
         ((Buffer)_snowmanx).flip();
      } catch (IOException var12) {
         var12.printStackTrace();
      }

      RenderSystem.activeTexture(33984);
      RenderSystem.bindTexture(_snowman);
      TextureUtil.uploadImage(_snowmanx, _snowmanxx, _snowmanxxx);
      textures.put(id, new RealmsTextureManager.RealmsTexture(image, _snowman));
      return _snowman;
   }

   public static class RealmsTexture {
      private final String image;
      private final int textureId;

      public RealmsTexture(String image, int textureId) {
         this.image = image;
         this.textureId = textureId;
      }
   }
}
