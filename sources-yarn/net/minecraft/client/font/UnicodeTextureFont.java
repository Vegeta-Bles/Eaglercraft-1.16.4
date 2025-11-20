package net.minecraft.client.font;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
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

      for (int i = 0; i < 256; i++) {
         int j = i * 256;
         Identifier lv = this.getImageId(j);

         try (
            Resource lv2 = this.resourceManager.getResource(lv);
            NativeImage lv3 = NativeImage.read(NativeImage.Format.ABGR, lv2.getInputStream());
         ) {
            if (lv3.getWidth() == 256 && lv3.getHeight() == 256) {
               for (int k = 0; k < 256; k++) {
                  byte b = sizes[j + k];
                  if (b != 0 && getStart(b) > getEnd(b)) {
                     sizes[j + k] = 0;
                  }
               }
               continue;
            }
         } catch (IOException var43) {
         }

         Arrays.fill(sizes, j, j + 256, (byte)0);
      }
   }

   @Override
   public void close() {
      this.images.values().forEach(NativeImage::close);
   }

   private Identifier getImageId(int codePoint) {
      Identifier lv = new Identifier(String.format(this.template, String.format("%02x", codePoint / 256)));
      return new Identifier(lv.getNamespace(), "textures/" + lv.getPath());
   }

   @Nullable
   @Override
   public RenderableGlyph getGlyph(int codePoint) {
      if (codePoint >= 0 && codePoint <= 65535) {
         byte b = this.sizes[codePoint];
         if (b != 0) {
            NativeImage lv = this.images.computeIfAbsent(this.getImageId(codePoint), this::getGlyphImage);
            if (lv != null) {
               int j = getStart(b);
               return new UnicodeTextureFont.UnicodeTextureGlyph(codePoint % 16 * 16 + j, (codePoint & 0xFF) / 16 * 16, getEnd(b) - j, 16, lv);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public IntSet getProvidedGlyphs() {
      IntSet intSet = new IntOpenHashSet();

      for (int i = 0; i < 65535; i++) {
         if (this.sizes[i] != 0) {
            intSet.add(i);
         }
      }

      return intSet;
   }

   @Nullable
   private NativeImage getGlyphImage(Identifier glyphId) {
      try (Resource lv = this.resourceManager.getResource(glyphId)) {
         return NativeImage.read(NativeImage.Format.ABGR, lv.getInputStream());
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

   @Environment(EnvType.CLIENT)
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
         try (Resource lv = MinecraftClient.getInstance().getResourceManager().getResource(this.sizes)) {
            byte[] bs = new byte[65536];
            lv.getInputStream().read(bs);
            return new UnicodeTextureFont(manager, bs, this.template);
         } catch (IOException var17) {
            UnicodeTextureFont.LOGGER.error("Cannot load {}, unicode glyphs will not render correctly", this.sizes);
            return null;
         }
      }
   }

   @Environment(EnvType.CLIENT)
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
