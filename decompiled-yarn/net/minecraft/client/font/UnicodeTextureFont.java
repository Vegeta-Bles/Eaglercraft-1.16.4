package net.minecraft.client.font;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnicodeTextureFont implements Font {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ResourceManager resourceManager;
   private final byte[] sizes;
   private final String template;
   private final Map<Identifier, NativeImage> images = Maps.newHashMap();

   public UnicodeTextureFont(ResourceManager resourceManager, byte[] sizes, String template) {
      this.resourceManager = resourceManager;
      this.sizes = sizes;
      this.template = template;

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         int _snowmanx = _snowman * 256;
         Identifier _snowmanxx = this.getImageId(_snowmanx);

         try (
            Resource _snowmanxxx = this.resourceManager.getResource(_snowmanxx);
            NativeImage _snowmanxxxx = NativeImage.read(NativeImage.Format.ABGR, _snowmanxxx.getInputStream());
         ) {
            if (_snowmanxxxx.getWidth() == 256 && _snowmanxxxx.getHeight() == 256) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 256; _snowmanxxxxx++) {
                  byte _snowmanxxxxxx = sizes[_snowmanx + _snowmanxxxxx];
                  if (_snowmanxxxxxx != 0 && getStart(_snowmanxxxxxx) > getEnd(_snowmanxxxxxx)) {
                     sizes[_snowmanx + _snowmanxxxxx] = 0;
                  }
               }
               continue;
            }
         } catch (IOException var43) {
         }

         Arrays.fill(sizes, _snowmanx, _snowmanx + 256, (byte)0);
      }
   }

   @Override
   public void close() {
      this.images.values().forEach(NativeImage::close);
   }

   private Identifier getImageId(int codePoint) {
      Identifier _snowman = new Identifier(String.format(this.template, String.format("%02x", codePoint / 256)));
      return new Identifier(_snowman.getNamespace(), "textures/" + _snowman.getPath());
   }

   @Nullable
   @Override
   public RenderableGlyph getGlyph(int codePoint) {
      if (codePoint >= 0 && codePoint <= 65535) {
         byte _snowman = this.sizes[codePoint];
         if (_snowman != 0) {
            NativeImage _snowmanx = this.images.computeIfAbsent(this.getImageId(codePoint), this::getGlyphImage);
            if (_snowmanx != null) {
               int _snowmanxx = getStart(_snowman);
               return new UnicodeTextureFont.UnicodeTextureGlyph(codePoint % 16 * 16 + _snowmanxx, (codePoint & 0xFF) / 16 * 16, getEnd(_snowman) - _snowmanxx, 16, _snowmanx);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public IntSet getProvidedGlyphs() {
      IntSet _snowman = new IntOpenHashSet();

      for (int _snowmanx = 0; _snowmanx < 65535; _snowmanx++) {
         if (this.sizes[_snowmanx] != 0) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   @Nullable
   private NativeImage getGlyphImage(Identifier glyphId) {
      try (Resource _snowman = this.resourceManager.getResource(glyphId)) {
         return NativeImage.read(NativeImage.Format.ABGR, _snowman.getInputStream());
      } catch (IOException var16) {
         LOGGER.error("Couldn't load texture {}", glyphId, var16);
         return null;
      }
   }

   private static int getStart(byte size) {
      return size >> 4 & 15;
   }

   private static int getEnd(byte size) {
      return (size & 15) + 1;
   }

   public static class Loader implements FontLoader {
      private final Identifier sizes;
      private final String template;

      public Loader(Identifier sizes, String template) {
         this.sizes = sizes;
         this.template = template;
      }

      public static FontLoader fromJson(JsonObject json) {
         return new UnicodeTextureFont.Loader(new Identifier(JsonHelper.getString(json, "sizes")), JsonHelper.getString(json, "template"));
      }

      @Nullable
      @Override
      public Font load(ResourceManager manager) {
         try (Resource _snowman = MinecraftClient.getInstance().getResourceManager().getResource(this.sizes)) {
            byte[] _snowmanx = new byte[65536];
            _snowman.getInputStream().read(_snowmanx);
            return new UnicodeTextureFont(manager, _snowmanx, this.template);
         } catch (IOException var17) {
            UnicodeTextureFont.LOGGER.error("Cannot load {}, unicode glyphs will not render correctly", this.sizes);
            return null;
         }
      }
   }

   static class UnicodeTextureGlyph implements RenderableGlyph {
      private final int width;
      private final int height;
      private final int unpackSkipPixels;
      private final int unpackSkipRows;
      private final NativeImage image;

      private UnicodeTextureGlyph(int x, int y, int width, int height, NativeImage image) {
         this.width = width;
         this.height = height;
         this.unpackSkipPixels = x;
         this.unpackSkipRows = y;
         this.image = image;
      }

      @Override
      public float getOversample() {
         return 2.0F;
      }

      @Override
      public int getWidth() {
         return this.width;
      }

      @Override
      public int getHeight() {
         return this.height;
      }

      @Override
      public float getAdvance() {
         return (float)(this.width / 2 + 1);
      }

      @Override
      public void upload(int x, int y) {
         this.image.upload(0, x, y, this.unpackSkipPixels, this.unpackSkipRows, this.width, this.height, false, false);
      }

      @Override
      public boolean hasColor() {
         return this.image.getFormat().getChannelCount() > 1;
      }

      @Override
      public float getShadowOffset() {
         return 0.5F;
      }

      @Override
      public float getBoldOffset() {
         return 0.5F;
      }
   }
}
