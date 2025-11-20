package net.minecraft.client.font;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BitmapFont implements Font {
   private static final Logger LOGGER = LogManager.getLogger();
   private final NativeImage image;
   private final Int2ObjectMap<BitmapFont.BitmapFontGlyph> glyphs;

   private BitmapFont(NativeImage image, Int2ObjectMap<BitmapFont.BitmapFontGlyph> glyphs) {
      this.image = image;
      this.glyphs = glyphs;
   }

   @Override
   public void close() {
      this.image.close();
   }

   @Nullable
   @Override
   public RenderableGlyph getGlyph(int codePoint) {
      return (RenderableGlyph)this.glyphs.get(codePoint);
   }

   @Override
   public IntSet getProvidedGlyphs() {
      return IntSets.unmodifiable(this.glyphs.keySet());
   }

   static final class BitmapFontGlyph implements RenderableGlyph {
      private final float scaleFactor;
      private final NativeImage image;
      private final int x;
      private final int y;
      private final int width;
      private final int height;
      private final int advance;
      private final int ascent;

      private BitmapFontGlyph(float scaleFactor, NativeImage image, int x, int y, int width, int height, int advance, int ascent) {
         this.scaleFactor = scaleFactor;
         this.image = image;
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
         this.advance = advance;
         this.ascent = ascent;
      }

      @Override
      public float getOversample() {
         return 1.0F / this.scaleFactor;
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
         return (float)this.advance;
      }

      @Override
      public float getAscent() {
         return RenderableGlyph.super.getAscent() + 7.0F - (float)this.ascent;
      }

      @Override
      public void upload(int x, int y) {
         this.image.upload(0, x, y, this.x, this.y, this.width, this.height, false, false);
      }

      @Override
      public boolean hasColor() {
         return this.image.getFormat().getChannelCount() > 1;
      }
   }

   public static class Loader implements FontLoader {
      private final Identifier filename;
      private final List<int[]> chars;
      private final int height;
      private final int ascent;

      public Loader(Identifier id, int height, int ascent, List<int[]> chars) {
         this.filename = new Identifier(id.getNamespace(), "textures/" + id.getPath());
         this.chars = chars;
         this.height = height;
         this.ascent = ascent;
      }

      public static BitmapFont.Loader fromJson(JsonObject json) {
         int _snowman = JsonHelper.getInt(json, "height", 8);
         int _snowmanx = JsonHelper.getInt(json, "ascent");
         if (_snowmanx > _snowman) {
            throw new JsonParseException("Ascent " + _snowmanx + " higher than height " + _snowman);
         } else {
            List<int[]> _snowmanxx = Lists.newArrayList();
            JsonArray _snowmanxxx = JsonHelper.getArray(json, "chars");

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
               String _snowmanxxxxx = JsonHelper.asString(_snowmanxxx.get(_snowmanxxxx), "chars[" + _snowmanxxxx + "]");
               int[] _snowmanxxxxxx = _snowmanxxxxx.codePoints().toArray();
               if (_snowmanxxxx > 0) {
                  int _snowmanxxxxxxx = _snowmanxx.get(0).length;
                  if (_snowmanxxxxxx.length != _snowmanxxxxxxx) {
                     throw new JsonParseException(
                        "Elements of chars have to be the same length (found: " + _snowmanxxxxxx.length + ", expected: " + _snowmanxxxxxxx + "), pad with space or \\u0000"
                     );
                  }
               }

               _snowmanxx.add(_snowmanxxxxxx);
            }

            if (!_snowmanxx.isEmpty() && _snowmanxx.get(0).length != 0) {
               return new BitmapFont.Loader(new Identifier(JsonHelper.getString(json, "file")), _snowman, _snowmanx, _snowmanxx);
            } else {
               throw new JsonParseException("Expected to find data in chars, found none.");
            }
         }
      }

      @Nullable
      @Override
      public Font load(ResourceManager manager) {
         try (Resource _snowman = manager.getResource(this.filename)) {
            NativeImage _snowmanx = NativeImage.read(NativeImage.Format.ABGR, _snowman.getInputStream());
            int _snowmanxx = _snowmanx.getWidth();
            int _snowmanxxx = _snowmanx.getHeight();
            int _snowmanxxxx = _snowmanxx / this.chars.get(0).length;
            int _snowmanxxxxx = _snowmanxxx / this.chars.size();
            float _snowmanxxxxxx = (float)this.height / (float)_snowmanxxxxx;
            Int2ObjectMap<BitmapFont.BitmapFontGlyph> _snowmanxxxxxxx = new Int2ObjectOpenHashMap();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.chars.size(); _snowmanxxxxxxxx++) {
               int _snowmanxxxxxxxxx = 0;

               for (int _snowmanxxxxxxxxxx : this.chars.get(_snowmanxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx++;
                  if (_snowmanxxxxxxxxxx != 0 && _snowmanxxxxxxxxxx != 32) {
                     int _snowmanxxxxxxxxxxxx = this.findCharacterStartX(_snowmanx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx);
                     BitmapFont.BitmapFontGlyph _snowmanxxxxxxxxxxxxx = (BitmapFont.BitmapFontGlyph)_snowmanxxxxxxx.put(
                        _snowmanxxxxxxxxxx,
                        new BitmapFont.BitmapFontGlyph(
                           _snowmanxxxxxx,
                           _snowmanx,
                           _snowmanxxxxxxxxxxx * _snowmanxxxx,
                           _snowmanxxxxxxxx * _snowmanxxxxx,
                           _snowmanxxxx,
                           _snowmanxxxxx,
                           (int)(0.5 + (double)((float)_snowmanxxxxxxxxxxxx * _snowmanxxxxxx)) + 1,
                           this.ascent
                        )
                     );
                     if (_snowmanxxxxxxxxxxxxx != null) {
                        BitmapFont.LOGGER.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(_snowmanxxxxxxxxxx), this.filename);
                     }
                  }
               }
            }

            return new BitmapFont(_snowmanx, _snowmanxxxxxxx);
         } catch (IOException var30) {
            throw new RuntimeException(var30.getMessage());
         }
      }

      private int findCharacterStartX(NativeImage image, int characterWidth, int characterHeight, int charPosX, int charPosY) {
         int _snowman;
         for (_snowman = characterWidth - 1; _snowman >= 0; _snowman--) {
            int _snowmanx = charPosX * characterWidth + _snowman;

            for (int _snowmanxx = 0; _snowmanxx < characterHeight; _snowmanxx++) {
               int _snowmanxxx = charPosY * characterHeight + _snowmanxx;
               if (image.getPixelOpacity(_snowmanx, _snowmanxxx) != 0) {
                  return _snowman + 1;
               }
            }
         }

         return _snowman + 1;
      }
   }
}
