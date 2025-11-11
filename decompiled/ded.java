import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class ded implements deb {
   private final ByteBuffer a;
   private final STBTTFontinfo b;
   private final float c;
   private final IntSet d = new IntArraySet();
   private final float e;
   private final float f;
   private final float g;
   private final float h;

   public ded(ByteBuffer var1, STBTTFontinfo var2, float var3, float var4, float var5, float var6, String var7) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      _snowman.codePoints().forEach(this.d::add);
      this.e = _snowman * _snowman;
      this.f = _snowman * _snowman;
      this.g = STBTruetype.stbtt_ScaleForPixelHeight(_snowman, _snowman * _snowman);
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var9 = null;

      try {
         IntBuffer _snowmanx = _snowman.mallocInt(1);
         IntBuffer _snowmanxx = _snowman.mallocInt(1);
         IntBuffer _snowmanxxx = _snowman.mallocInt(1);
         STBTruetype.stbtt_GetFontVMetrics(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         this.h = (float)_snowmanx.get(0) * this.g;
      } catch (Throwable var20) {
         var9 = var20;
         throw var20;
      } finally {
         if (_snowman != null) {
            if (var9 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var19) {
                  var9.addSuppressed(var19);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   @Nullable
   public ded.a b(int var1) {
      if (this.d.contains(_snowman)) {
         return null;
      } else {
         MemoryStack _snowman = MemoryStack.stackPush();
         Throwable var3 = null;

         ded.a var13;
         try {
            IntBuffer _snowmanx = _snowman.mallocInt(1);
            IntBuffer _snowmanxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxxx = _snowman.mallocInt(1);
            int _snowmanxxxxx = STBTruetype.stbtt_FindGlyphIndex(this.b, _snowman);
            if (_snowmanxxxxx == 0) {
               return null;
            }

            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(this.b, _snowmanxxxxx, this.g, this.g, this.e, this.f, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
            int _snowmanxxxxxx = _snowmanxxx.get(0) - _snowmanx.get(0);
            int _snowmanxxxxxxx = _snowmanxxxx.get(0) - _snowmanxx.get(0);
            if (_snowmanxxxxxx == 0 || _snowmanxxxxxxx == 0) {
               return null;
            }

            IntBuffer _snowmanxxxxxxxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxxxxxxxx = _snowman.mallocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics(this.b, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            var13 = new ded.a(_snowmanx.get(0), _snowmanxxx.get(0), -_snowmanxx.get(0), -_snowmanxxxx.get(0), (float)_snowmanxxxxxxxx.get(0) * this.g, (float)_snowmanxxxxxxxxx.get(0) * this.g, _snowmanxxxxx);
         } catch (Throwable var24) {
            var3 = var24;
            throw var24;
         } finally {
            if (_snowman != null) {
               if (var3 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var23) {
                     var3.addSuppressed(var23);
                  }
               } else {
                  _snowman.close();
               }
            }
         }

         return var13;
      }
   }

   @Override
   public void close() {
      this.b.free();
      MemoryUtil.memFree(this.a);
   }

   @Override
   public IntSet a() {
      return IntStream.range(0, 65535).filter(var1 -> !this.d.contains(var1)).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
   }

   class a implements dec {
      private final int b;
      private final int c;
      private final float d;
      private final float e;
      private final float f;
      private final int g;

      private a(int var2, int var3, int var4, int var5, float var6, float var7, int var8) {
         this.b = _snowman - _snowman;
         this.c = _snowman - _snowman;
         this.f = _snowman / ded.this.c;
         this.d = (_snowman + (float)_snowman + ded.this.e) / ded.this.c;
         this.e = (ded.this.h - (float)_snowman + ded.this.f) / ded.this.c;
         this.g = _snowman;
      }

      @Override
      public int d() {
         return this.b;
      }

      @Override
      public int e() {
         return this.c;
      }

      @Override
      public float g() {
         return ded.this.c;
      }

      @Override
      public float getAdvance() {
         return this.f;
      }

      @Override
      public float a() {
         return this.d;
      }

      @Override
      public float l() {
         return this.e;
      }

      @Override
      public void a(int var1, int var2) {
         det _snowman = new det(det.a.d, this.b, this.c, false);
         _snowman.a(ded.this.b, this.g, this.b, this.c, ded.this.g, ded.this.g, ded.this.e, ded.this.f, 0, 0);
         _snowman.a(0, _snowman, _snowman, 0, 0, this.b, this.c, false, true);
      }

      @Override
      public boolean f() {
         return false;
      }
   }
}
