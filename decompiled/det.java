import com.google.common.base.Charsets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class det implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private static final Set<StandardOpenOption> b = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
   private final det.a c;
   private final int d;
   private final int e;
   private final boolean f;
   private long g;
   private final long h;

   public det(int var1, int var2, boolean var3) {
      this(det.a.a, _snowman, _snowman, _snowman);
   }

   public det(det.a var1, int var2, int var3, boolean var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.h = (long)_snowman * (long)_snowman * (long)_snowman.a();
      this.f = false;
      if (_snowman) {
         this.g = MemoryUtil.nmemCalloc(1L, this.h);
      } else {
         this.g = MemoryUtil.nmemAlloc(this.h);
      }
   }

   private det(det.a var1, int var2, int var3, boolean var4, long var5) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = (long)(_snowman * _snowman * _snowman.a());
   }

   @Override
   public String toString() {
      return "NativeImage[" + this.c + " " + this.d + "x" + this.e + "@" + this.g + (this.f ? "S" : "N") + "]";
   }

   public static det a(InputStream var0) throws IOException {
      return a(det.a.a, _snowman);
   }

   public static det a(@Nullable det.a var0, InputStream var1) throws IOException {
      ByteBuffer _snowman = null;

      det var3;
      try {
         _snowman = dex.a(_snowman);
         ((Buffer)_snowman).rewind();
         var3 = a(_snowman, _snowman);
      } finally {
         MemoryUtil.memFree(_snowman);
         IOUtils.closeQuietly(_snowman);
      }

      return var3;
   }

   public static det a(ByteBuffer var0) throws IOException {
      return a(det.a.a, _snowman);
   }

   public static det a(@Nullable det.a var0, ByteBuffer var1) throws IOException {
      if (_snowman != null && !_snowman.i()) {
         throw new UnsupportedOperationException("Don't know how to read format " + _snowman);
      } else if (MemoryUtil.memAddress(_snowman) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         MemoryStack _snowman = MemoryStack.stackPush();
         Throwable var3 = null;

         det var8;
         try {
            IntBuffer _snowmanx = _snowman.mallocInt(1);
            IntBuffer _snowmanxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxx = _snowman.mallocInt(1);
            ByteBuffer _snowmanxxxx = STBImage.stbi_load_from_memory(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowman == null ? 0 : _snowman.e);
            if (_snowmanxxxx == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            var8 = new det(_snowman == null ? det.a.b(_snowmanxxx.get(0)) : _snowman, _snowmanx.get(0), _snowmanxx.get(0), true, MemoryUtil.memAddress(_snowmanxxxx));
         } catch (Throwable var17) {
            var3 = var17;
            throw var17;
         } finally {
            if (_snowman != null) {
               if (var3 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var16) {
                     var3.addSuppressed(var16);
                  }
               } else {
                  _snowman.close();
               }
            }
         }

         return var8;
      }
   }

   private static void a(boolean var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman) {
         dem.b(3553, 10242, 10496);
         dem.b(3553, 10243, 10496);
      } else {
         dem.b(3553, 10242, 10497);
         dem.b(3553, 10243, 10497);
      }
   }

   private static void a(boolean var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman) {
         dem.b(3553, 10241, _snowman ? 9987 : 9729);
         dem.b(3553, 10240, 9729);
      } else {
         dem.b(3553, 10241, _snowman ? 9986 : 9728);
         dem.b(3553, 10240, 9728);
      }
   }

   private void h() {
      if (this.g == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   @Override
   public void close() {
      if (this.g != 0L) {
         if (this.f) {
            STBImage.nstbi_image_free(this.g);
         } else {
            MemoryUtil.nmemFree(this.g);
         }
      }

      this.g = 0L;
   }

   public int a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public det.a c() {
      return this.c;
   }

   public int a(int var1, int var2) {
      if (this.c != det.a.a) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.c));
      } else if (_snowman <= this.d && _snowman <= this.e) {
         this.h();
         long _snowman = (long)((_snowman + _snowman * this.d) * 4);
         return MemoryUtil.memGetInt(this.g + _snowman);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", _snowman, _snowman, this.d, this.e));
      }
   }

   public void a(int var1, int var2, int var3) {
      if (this.c != det.a.a) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.c));
      } else if (_snowman <= this.d && _snowman <= this.e) {
         this.h();
         long _snowman = (long)((_snowman + _snowman * this.d) * 4);
         MemoryUtil.memPutInt(this.g + _snowman, _snowman);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", _snowman, _snowman, this.d, this.e));
      }
   }

   public byte b(int var1, int var2) {
      if (!this.c.g()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.c));
      } else if (_snowman <= this.d && _snowman <= this.e) {
         int _snowman = (_snowman + _snowman * this.d) * this.c.a() + this.c.h() / 8;
         return MemoryUtil.memGetByte(this.g + (long)_snowman);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", _snowman, _snowman, this.d, this.e));
      }
   }

   @Deprecated
   public int[] d() {
      if (this.c != det.a.a) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.h();
         int[] _snowman = new int[this.a() * this.b()];

         for (int _snowmanx = 0; _snowmanx < this.b(); _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < this.a(); _snowmanxx++) {
               int _snowmanxxx = this.a(_snowmanxx, _snowmanx);
               int _snowmanxxxx = a(_snowmanxxx);
               int _snowmanxxxxx = d(_snowmanxxx);
               int _snowmanxxxxxx = c(_snowmanxxx);
               int _snowmanxxxxxxx = b(_snowmanxxx);
               int _snowmanxxxxxxxx = _snowmanxxxx << 24 | _snowmanxxxxxxx << 16 | _snowmanxxxxxx << 8 | _snowmanxxxxx;
               _snowman[_snowmanxx + _snowmanx * this.a()] = _snowmanxxxxxxxx;
            }
         }

         return _snowman;
      }
   }

   public void a(int var1, int var2, int var3, boolean var4) {
      this.a(_snowman, _snowman, _snowman, 0, 0, this.d, this.e, false, _snowman);
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9) {
      this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, false, _snowman, _snowman);
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10, boolean var11) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      } else {
         this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private void b(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10, boolean var11) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.h();
      a(_snowman, _snowman);
      a(_snowman);
      if (_snowman == this.a()) {
         dem.n(3314, 0);
      } else {
         dem.n(3314, this.a());
      }

      dem.n(3316, _snowman);
      dem.n(3315, _snowman);
      this.c.c();
      dem.a(3553, _snowman, _snowman, _snowman, _snowman, _snowman, this.c.d(), 5121, this.g);
      if (_snowman) {
         this.close();
      }
   }

   public void a(int var1, boolean var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.h();
      this.c.b();
      dem.a(3553, _snowman, this.c.d(), 5121, this.g);
      if (_snowman && this.c.e()) {
         for (int _snowman = 0; _snowman < this.b(); _snowman++) {
            for (int _snowmanx = 0; _snowmanx < this.a(); _snowmanx++) {
               this.a(_snowmanx, _snowman, this.a(_snowmanx, _snowman) | 255 << this.c.f());
            }
         }
      }
   }

   public void a(File var1) throws IOException {
      this.a(_snowman.toPath());
   }

   public void a(STBTTFontinfo var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9, int var10) {
      if (_snowman < 0 || _snowman + _snowman > this.a() || _snowman < 0 || _snowman + _snowman > this.b()) {
         throw new IllegalArgumentException(String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", _snowman, _snowman, _snowman, _snowman, this.a(), this.b()));
      } else if (this.c.a() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else {
         STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(_snowman.address(), this.g + (long)_snowman + (long)(_snowman * this.a()), _snowman, _snowman, this.a(), _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(Path var1) throws IOException {
      if (!this.c.i()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.c);
      } else {
         this.h();

         try (WritableByteChannel _snowman = Files.newByteChannel(_snowman, b)) {
            if (!this.a(_snowman)) {
               throw new IOException("Could not write image to the PNG file \"" + _snowman.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
         }
      }
   }

   public byte[] e() throws IOException {
      byte[] var5;
      try (
         ByteArrayOutputStream _snowman = new ByteArrayOutputStream();
         WritableByteChannel _snowmanx = Channels.newChannel(_snowman);
      ) {
         if (!this.a(_snowmanx)) {
            throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
         }

         var5 = _snowman.toByteArray();
      }

      return var5;
   }

   private boolean a(WritableByteChannel var1) throws IOException {
      det.c _snowman = new det.c(_snowman);

      boolean var4;
      try {
         int _snowmanx = Math.min(this.b(), Integer.MAX_VALUE / this.a() / this.c.a());
         if (_snowmanx < this.b()) {
            a.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.b(), _snowmanx);
         }

         if (STBImageWrite.nstbi_write_png_to_func(_snowman.address(), 0L, this.a(), _snowmanx, this.c.a(), this.g, 0) != 0) {
            _snowman.a();
            return true;
         }

         var4 = false;
      } finally {
         _snowman.free();
      }

      return var4;
   }

   public void a(det var1) {
      if (_snowman.c() != this.c) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int _snowman = this.c.a();
         this.h();
         _snowman.h();
         if (this.d == _snowman.d) {
            MemoryUtil.memCopy(_snowman.g, this.g, Math.min(this.h, _snowman.h));
         } else {
            int _snowmanx = Math.min(this.a(), _snowman.a());
            int _snowmanxx = Math.min(this.b(), _snowman.b());

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx * _snowman.a() * _snowman;
               int _snowmanxxxxx = _snowmanxxx * this.a() * _snowman;
               MemoryUtil.memCopy(_snowman.g + (long)_snowmanxxxx, this.g + (long)_snowmanxxxxx, (long)_snowmanx);
            }
         }
      }
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      for (int _snowman = _snowman; _snowman < _snowman + _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx < _snowman + _snowman; _snowmanx++) {
            this.a(_snowmanx, _snowman, _snowman);
         }
      }
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            int _snowmanxx = _snowman ? _snowman - 1 - _snowmanx : _snowmanx;
            int _snowmanxxx = _snowman ? _snowman - 1 - _snowman : _snowman;
            int _snowmanxxxx = this.a(_snowman + _snowmanx, _snowman + _snowman);
            this.a(_snowman + _snowman + _snowmanxx, _snowman + _snowman + _snowmanxxx, _snowmanxxxx);
         }
      }
   }

   public void f() {
      this.h();
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         int _snowmanx = this.c.a();
         int _snowmanxx = this.a() * _snowmanx;
         long _snowmanxxx = _snowman.nmalloc(_snowmanxx);

         for (int _snowmanxxxx = 0; _snowmanxxxx < this.b() / 2; _snowmanxxxx++) {
            int _snowmanxxxxx = _snowmanxxxx * this.a() * _snowmanx;
            int _snowmanxxxxxx = (this.b() - 1 - _snowmanxxxx) * this.a() * _snowmanx;
            MemoryUtil.memCopy(this.g + (long)_snowmanxxxxx, _snowmanxxx, (long)_snowmanxx);
            MemoryUtil.memCopy(this.g + (long)_snowmanxxxxxx, this.g + (long)_snowmanxxxxx, (long)_snowmanxx);
            MemoryUtil.memCopy(_snowmanxxx, this.g + (long)_snowmanxxxxxx, (long)_snowmanxx);
         }
      } catch (Throwable var17) {
         var2 = var17;
         throw var17;
      } finally {
         if (_snowman != null) {
            if (var2 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var16) {
                  var2.addSuppressed(var16);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   public void a(int var1, int var2, int var3, int var4, det var5) {
      this.h();
      if (_snowman.c() != this.c) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int _snowman = this.c.a();
         STBImageResize.nstbir_resize_uint8(this.g + (long)((_snowman + _snowman * this.a()) * _snowman), _snowman, _snowman, this.a() * _snowman, _snowman.g, _snowman.a(), _snowman.b(), 0, _snowman);
      }
   }

   public void g() {
      dei.a(this.g);
   }

   public static det a(String var0) throws IOException {
      byte[] _snowman = Base64.getDecoder().decode(_snowman.replaceAll("\n", "").getBytes(Charsets.UTF_8));
      MemoryStack _snowmanx = MemoryStack.stackPush();
      Throwable var3 = null;

      det var5;
      try {
         ByteBuffer _snowmanxx = _snowmanx.malloc(_snowman.length);
         _snowmanxx.put(_snowman);
         ((Buffer)_snowmanxx).rewind();
         var5 = a(_snowmanxx);
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (_snowmanx != null) {
            if (var3 != null) {
               try {
                  _snowmanx.close();
               } catch (Throwable var13) {
                  var3.addSuppressed(var13);
               }
            } else {
               _snowmanx.close();
            }
         }
      }

      return var5;
   }

   public static int a(int var0) {
      return _snowman >> 24 & 0xFF;
   }

   public static int b(int var0) {
      return _snowman >> 0 & 0xFF;
   }

   public static int c(int var0) {
      return _snowman >> 8 & 0xFF;
   }

   public static int d(int var0) {
      return _snowman >> 16 & 0xFF;
   }

   public static int a(int var0, int var1, int var2, int var3) {
      return (_snowman & 0xFF) << 24 | (_snowman & 0xFF) << 16 | (_snowman & 0xFF) << 8 | (_snowman & 0xFF) << 0;
   }

   public static enum a {
      a(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      b(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      c(2, 6410, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      d(1, 6409, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      private final int e;
      private final int f;
      private final boolean g;
      private final boolean h;
      private final boolean i;
      private final boolean j;
      private final boolean k;
      private final int l;
      private final int m;
      private final int n;
      private final int o;
      private final int p;
      private final boolean q;

      private a(
         int var3,
         int var4,
         boolean var5,
         boolean var6,
         boolean var7,
         boolean var8,
         boolean var9,
         int var10,
         int var11,
         int var12,
         int var13,
         int var14,
         boolean var15
      ) {
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
         this.l = _snowman;
         this.m = _snowman;
         this.n = _snowman;
         this.o = _snowman;
         this.p = _snowman;
         this.q = _snowman;
      }

      public int a() {
         return this.e;
      }

      public void b() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         dem.n(3333, this.a());
      }

      public void c() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         dem.n(3317, this.a());
      }

      public int d() {
         return this.f;
      }

      public boolean e() {
         return this.k;
      }

      public int f() {
         return this.p;
      }

      public boolean g() {
         return this.j || this.k;
      }

      public int h() {
         return this.j ? this.o : this.p;
      }

      public boolean i() {
         return this.q;
      }

      private static det.a b(int var0) {
         switch (_snowman) {
            case 1:
               return d;
            case 2:
               return c;
            case 3:
               return b;
            case 4:
            default:
               return a;
         }
      }
   }

   public static enum b {
      a(6408),
      b(6407),
      c(6410),
      d(6409),
      e(32841);

      private final int f;

      private b(int var3) {
         this.f = _snowman;
      }

      int a() {
         return this.f;
      }
   }

   static class c extends STBIWriteCallback {
      private final WritableByteChannel a;
      @Nullable
      private IOException b;

      private c(WritableByteChannel var1) {
         this.a = _snowman;
      }

      public void invoke(long var1, long var3, int var5) {
         ByteBuffer _snowman = getData(_snowman, _snowman);

         try {
            this.a.write(_snowman);
         } catch (IOException var8) {
            this.b = var8;
         }
      }

      public void a() throws IOException {
         if (this.b != null) {
            throw this.b;
         }
      }
   }
}
