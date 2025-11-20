package net.minecraft.client.font;

import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.texture.NativeImage;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeFont implements Font {
   private final ByteBuffer field_21839;
   private final STBTTFontinfo info;
   private final float oversample;
   private final IntSet excludedCharacters = new IntArraySet();
   private final float shiftX;
   private final float shiftY;
   private final float scaleFactor;
   private final float ascent;

   public TrueTypeFont(ByteBuffer _snowman, STBTTFontinfo _snowman, float _snowman, float _snowman, float _snowman, float _snowman, String _snowman) {
      this.field_21839 = _snowman;
      this.info = _snowman;
      this.oversample = _snowman;
      _snowman.codePoints().forEach(this.excludedCharacters::add);
      this.shiftX = _snowman * _snowman;
      this.shiftY = _snowman * _snowman;
      this.scaleFactor = STBTruetype.stbtt_ScaleForPixelHeight(_snowman, _snowman * _snowman);
      MemoryStack _snowmanxxxxxxx = MemoryStack.stackPush();
      Throwable var9 = null;

      try {
         IntBuffer _snowmanxxxxxxxx = _snowmanxxxxxxx.mallocInt(1);
         IntBuffer _snowmanxxxxxxxxx = _snowmanxxxxxxx.mallocInt(1);
         IntBuffer _snowmanxxxxxxxxxx = _snowmanxxxxxxx.mallocInt(1);
         STBTruetype.stbtt_GetFontVMetrics(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         this.ascent = (float)_snowmanxxxxxxxx.get(0) * this.scaleFactor;
      } catch (Throwable var20) {
         var9 = var20;
         throw var20;
      } finally {
         if (_snowmanxxxxxxx != null) {
            if (var9 != null) {
               try {
                  _snowmanxxxxxxx.close();
               } catch (Throwable var19) {
                  var9.addSuppressed(var19);
               }
            } else {
               _snowmanxxxxxxx.close();
            }
         }
      }
   }

   @Nullable
   public TrueTypeFont.TtfGlyph getGlyph(int _snowman) {
      if (this.excludedCharacters.contains(_snowman)) {
         return null;
      } else {
         MemoryStack _snowmanx = MemoryStack.stackPush();
         Throwable var3 = null;

         TrueTypeFont.TtfGlyph var13;
         try {
            IntBuffer _snowmanxx = _snowmanx.mallocInt(1);
            IntBuffer _snowmanxxx = _snowmanx.mallocInt(1);
            IntBuffer _snowmanxxxx = _snowmanx.mallocInt(1);
            IntBuffer _snowmanxxxxx = _snowmanx.mallocInt(1);
            int _snowmanxxxxxx = STBTruetype.stbtt_FindGlyphIndex(this.info, _snowman);
            if (_snowmanxxxxxx == 0) {
               return null;
            }

            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(
               this.info, _snowmanxxxxxx, this.scaleFactor, this.scaleFactor, this.shiftX, this.shiftY, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx
            );
            int _snowmanxxxxxxx = _snowmanxxxx.get(0) - _snowmanxx.get(0);
            int _snowmanxxxxxxxx = _snowmanxxxxx.get(0) - _snowmanxxx.get(0);
            if (_snowmanxxxxxxx == 0 || _snowmanxxxxxxxx == 0) {
               return null;
            }

            IntBuffer _snowmanxxxxxxxxx = _snowmanx.mallocInt(1);
            IntBuffer _snowmanxxxxxxxxxx = _snowmanx.mallocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics(this.info, _snowmanxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            var13 = new TrueTypeFont.TtfGlyph(
               _snowmanxx.get(0),
               _snowmanxxxx.get(0),
               -_snowmanxxx.get(0),
               -_snowmanxxxxx.get(0),
               (float)_snowmanxxxxxxxxx.get(0) * this.scaleFactor,
               (float)_snowmanxxxxxxxxxx.get(0) * this.scaleFactor,
               _snowmanxxxxxx
            );
         } catch (Throwable var24) {
            var3 = var24;
            throw var24;
         } finally {
            if (_snowmanx != null) {
               if (var3 != null) {
                  try {
                     _snowmanx.close();
                  } catch (Throwable var23) {
                     var3.addSuppressed(var23);
                  }
               } else {
                  _snowmanx.close();
               }
            }
         }

         return var13;
      }
   }

   @Override
   public void close() {
      this.info.free();
      MemoryUtil.memFree(this.field_21839);
   }

   @Override
   public IntSet getProvidedGlyphs() {
      return IntStream.range(0, 65535)
         .filter(_snowman -> !this.excludedCharacters.contains(_snowman))
         .collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
   }

   class TtfGlyph implements RenderableGlyph {
      private final int width;
      private final int height;
      private final float bearingX;
      private final float ascent;
      private final float advance;
      private final int glyphIndex;

      private TtfGlyph(int xMin, int xMax, int yMax, int yMin, float advance, float bearing, int index) {
         this.width = xMax - xMin;
         this.height = yMax - yMin;
         this.advance = advance / TrueTypeFont.this.oversample;
         this.bearingX = (bearing + (float)xMin + TrueTypeFont.this.shiftX) / TrueTypeFont.this.oversample;
         this.ascent = (TrueTypeFont.this.ascent - (float)yMax + TrueTypeFont.this.shiftY) / TrueTypeFont.this.oversample;
         this.glyphIndex = index;
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
      public float getOversample() {
         return TrueTypeFont.this.oversample;
      }

      @Override
      public float getAdvance() {
         return this.advance;
      }

      @Override
      public float getBearingX() {
         return this.bearingX;
      }

      @Override
      public float getAscent() {
         return this.ascent;
      }

      @Override
      public void upload(int x, int y) {
         NativeImage _snowman = new NativeImage(NativeImage.Format.LUMINANCE, this.width, this.height, false);
         _snowman.makeGlyphBitmapSubpixel(
            TrueTypeFont.this.info,
            this.glyphIndex,
            this.width,
            this.height,
            TrueTypeFont.this.scaleFactor,
            TrueTypeFont.this.scaleFactor,
            TrueTypeFont.this.shiftX,
            TrueTypeFont.this.shiftY,
            0,
            0
         );
         _snowman.upload(0, x, y, 0, 0, this.width, this.height, false, true);
      }

      @Override
      public boolean hasColor() {
         return false;
      }
   }
}
