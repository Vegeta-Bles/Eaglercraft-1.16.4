package net.minecraft.client.texture;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.platform.GlStateManager;
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
import net.minecraft.client.util.Untracker;
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

public final class NativeImage implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<StandardOpenOption> WRITE_TO_FILE_OPEN_OPTIONS = EnumSet.of(
      StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
   );
   private final NativeImage.Format format;
   private final int width;
   private final int height;
   private final boolean isStbImage;
   private long pointer;
   private final long sizeBytes;

   public NativeImage(int width, int height, boolean useStb) {
      this(NativeImage.Format.ABGR, width, height, useStb);
   }

   public NativeImage(NativeImage.Format format, int width, int height, boolean useStb) {
      this.format = format;
      this.width = width;
      this.height = height;
      this.sizeBytes = (long)width * (long)height * (long)format.getChannelCount();
      this.isStbImage = false;
      if (useStb) {
         this.pointer = MemoryUtil.nmemCalloc(1L, this.sizeBytes);
      } else {
         this.pointer = MemoryUtil.nmemAlloc(this.sizeBytes);
      }
   }

   private NativeImage(NativeImage.Format format, int width, int height, boolean useStb, long pointer) {
      this.format = format;
      this.width = width;
      this.height = height;
      this.isStbImage = useStb;
      this.pointer = pointer;
      this.sizeBytes = (long)(width * height * format.getChannelCount());
   }

   @Override
   public String toString() {
      return "NativeImage[" + this.format + " " + this.width + "x" + this.height + "@" + this.pointer + (this.isStbImage ? "S" : "N") + "]";
   }

   public static NativeImage read(InputStream _snowman) throws IOException {
      return read(NativeImage.Format.ABGR, _snowman);
   }

   public static NativeImage read(@Nullable NativeImage.Format _snowman, InputStream _snowman) throws IOException {
      ByteBuffer _snowmanxx = null;

      NativeImage var3;
      try {
         _snowmanxx = TextureUtil.readAllToByteBuffer(_snowman);
         ((Buffer)_snowmanxx).rewind();
         var3 = read(_snowman, _snowmanxx);
      } finally {
         MemoryUtil.memFree(_snowmanxx);
         IOUtils.closeQuietly(_snowman);
      }

      return var3;
   }

   public static NativeImage read(ByteBuffer _snowman) throws IOException {
      return read(NativeImage.Format.ABGR, _snowman);
   }

   public static NativeImage read(@Nullable NativeImage.Format _snowman, ByteBuffer _snowman) throws IOException {
      if (_snowman != null && !_snowman.isWriteable()) {
         throw new UnsupportedOperationException("Don't know how to read format " + _snowman);
      } else if (MemoryUtil.memAddress(_snowman) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         MemoryStack _snowmanxx = MemoryStack.stackPush();
         Throwable var3 = null;

         NativeImage var8;
         try {
            IntBuffer _snowmanxxx = _snowmanxx.mallocInt(1);
            IntBuffer _snowmanxxxx = _snowmanxx.mallocInt(1);
            IntBuffer _snowmanxxxxx = _snowmanxx.mallocInt(1);
            ByteBuffer _snowmanxxxxxx = STBImage.stbi_load_from_memory(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman == null ? 0 : _snowman.channelCount);
            if (_snowmanxxxxxx == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            var8 = new NativeImage(_snowman == null ? NativeImage.Format.getFormat(_snowmanxxxxx.get(0)) : _snowman, _snowmanxxx.get(0), _snowmanxxxx.get(0), true, MemoryUtil.memAddress(_snowmanxxxxxx));
         } catch (Throwable var17) {
            var3 = var17;
            throw var17;
         } finally {
            if (_snowmanxx != null) {
               if (var3 != null) {
                  try {
                     _snowmanxx.close();
                  } catch (Throwable var16) {
                     var3.addSuppressed(var16);
                  }
               } else {
                  _snowmanxx.close();
               }
            }
         }

         return var8;
      }
   }

   private static void setTextureClamp(boolean clamp) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (clamp) {
         GlStateManager.texParameter(3553, 10242, 10496);
         GlStateManager.texParameter(3553, 10243, 10496);
      } else {
         GlStateManager.texParameter(3553, 10242, 10497);
         GlStateManager.texParameter(3553, 10243, 10497);
      }
   }

   private static void setTextureFilter(boolean blur, boolean mipmap) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (blur) {
         GlStateManager.texParameter(3553, 10241, mipmap ? 9987 : 9729);
         GlStateManager.texParameter(3553, 10240, 9729);
      } else {
         GlStateManager.texParameter(3553, 10241, mipmap ? 9986 : 9728);
         GlStateManager.texParameter(3553, 10240, 9728);
      }
   }

   private void checkAllocated() {
      if (this.pointer == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   @Override
   public void close() {
      if (this.pointer != 0L) {
         if (this.isStbImage) {
            STBImage.nstbi_image_free(this.pointer);
         } else {
            MemoryUtil.nmemFree(this.pointer);
         }
      }

      this.pointer = 0L;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public NativeImage.Format getFormat() {
      return this.format;
   }

   public int getPixelColor(int x, int y) {
      if (this.format != NativeImage.Format.ABGR) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (x <= this.width && y <= this.height) {
         this.checkAllocated();
         long _snowman = (long)((x + y * this.width) * 4);
         return MemoryUtil.memGetInt(this.pointer + _snowman);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", x, y, this.width, this.height));
      }
   }

   public void setPixelColor(int x, int y, int color) {
      if (this.format != NativeImage.Format.ABGR) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (x <= this.width && y <= this.height) {
         this.checkAllocated();
         long _snowman = (long)((x + y * this.width) * 4);
         MemoryUtil.memPutInt(this.pointer + _snowman, color);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", x, y, this.width, this.height));
      }
   }

   public byte getPixelOpacity(int x, int y) {
      if (!this.format.hasOpacityChannel()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.format));
      } else if (x <= this.width && y <= this.height) {
         int _snowman = (x + y * this.width) * this.format.getChannelCount() + this.format.getOpacityOffset() / 8;
         return MemoryUtil.memGetByte(this.pointer + (long)_snowman);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", x, y, this.width, this.height));
      }
   }

   @Deprecated
   public int[] makePixelArray() {
      if (this.format != NativeImage.Format.ABGR) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.checkAllocated();
         int[] _snowman = new int[this.getWidth() * this.getHeight()];

         for (int _snowmanx = 0; _snowmanx < this.getHeight(); _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < this.getWidth(); _snowmanxx++) {
               int _snowmanxxx = this.getPixelColor(_snowmanxx, _snowmanx);
               int _snowmanxxxx = getAlpha(_snowmanxxx);
               int _snowmanxxxxx = getBlue(_snowmanxxx);
               int _snowmanxxxxxx = getGreen(_snowmanxxx);
               int _snowmanxxxxxxx = getRed(_snowmanxxx);
               int _snowmanxxxxxxxx = _snowmanxxxx << 24 | _snowmanxxxxxxx << 16 | _snowmanxxxxxx << 8 | _snowmanxxxxx;
               _snowman[_snowmanxx + _snowmanx * this.getWidth()] = _snowmanxxxxxxxx;
            }
         }

         return _snowman;
      }
   }

   public void upload(int level, int offsetX, int offsetY, boolean close) {
      this.upload(level, offsetX, offsetY, 0, 0, this.width, this.height, false, close);
   }

   public void upload(int level, int offsetX, int offsetY, int unpackSkipPixels, int unpackSkipRows, int width, int height, boolean mipmap, boolean close) {
      this.upload(level, offsetX, offsetY, unpackSkipPixels, unpackSkipRows, width, height, false, false, mipmap, close);
   }

   public void upload(
      int level,
      int offsetX,
      int offsetY,
      int unpackSkipPixels,
      int unpackSkipRows,
      int width,
      int height,
      boolean blur,
      boolean clamp,
      boolean mipmap,
      boolean close
   ) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(
            () -> this.uploadInternal(level, offsetX, offsetY, unpackSkipPixels, unpackSkipRows, width, height, blur, clamp, mipmap, close)
         );
      } else {
         this.uploadInternal(level, offsetX, offsetY, unpackSkipPixels, unpackSkipRows, width, height, blur, clamp, mipmap, close);
      }
   }

   private void uploadInternal(
      int level,
      int xOffset,
      int yOffset,
      int unpackSkipPixels,
      int unpackSkipRows,
      int width,
      int height,
      boolean blur,
      boolean clamp,
      boolean mipmap,
      boolean close
   ) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.checkAllocated();
      setTextureFilter(blur, mipmap);
      setTextureClamp(clamp);
      if (width == this.getWidth()) {
         GlStateManager.pixelStore(3314, 0);
      } else {
         GlStateManager.pixelStore(3314, this.getWidth());
      }

      GlStateManager.pixelStore(3316, unpackSkipPixels);
      GlStateManager.pixelStore(3315, unpackSkipRows);
      this.format.setUnpackAlignment();
      GlStateManager.texSubImage2D(3553, level, xOffset, yOffset, width, height, this.format.getPixelDataFormat(), 5121, this.pointer);
      if (close) {
         this.close();
      }
   }

   public void loadFromTextureImage(int level, boolean removeAlpha) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.checkAllocated();
      this.format.setPackAlignment();
      GlStateManager.getTexImage(3553, level, this.format.getPixelDataFormat(), 5121, this.pointer);
      if (removeAlpha && this.format.hasAlphaChannel()) {
         for (int _snowman = 0; _snowman < this.getHeight(); _snowman++) {
            for (int _snowmanx = 0; _snowmanx < this.getWidth(); _snowmanx++) {
               this.setPixelColor(_snowmanx, _snowman, this.getPixelColor(_snowmanx, _snowman) | 255 << this.format.getAlphaChannelOffset());
            }
         }
      }
   }

   public void writeFile(File _snowman) throws IOException {
      this.writeFile(_snowman.toPath());
   }

   public void makeGlyphBitmapSubpixel(
      STBTTFontinfo fontInfo, int glyphIndex, int width, int height, float scaleX, float scaleY, float shiftX, float shiftY, int startX, int startY
   ) {
      if (startX < 0 || startX + width > this.getWidth() || startY < 0 || startY + height > this.getHeight()) {
         throw new IllegalArgumentException(
            String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", startX, startY, width, height, this.getWidth(), this.getHeight())
         );
      } else if (this.format.getChannelCount() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else {
         STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(
            fontInfo.address(),
            this.pointer + (long)startX + (long)(startY * this.getWidth()),
            width,
            height,
            this.getWidth(),
            scaleX,
            scaleY,
            shiftX,
            shiftY,
            glyphIndex
         );
      }
   }

   public void writeFile(Path path) throws IOException {
      if (!this.format.isWriteable()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.format);
      } else {
         this.checkAllocated();

         try (WritableByteChannel _snowman = Files.newByteChannel(path, WRITE_TO_FILE_OPEN_OPTIONS)) {
            if (!this.write(_snowman)) {
               throw new IOException("Could not write image to the PNG file \"" + path.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
         }
      }
   }

   public byte[] getBytes() throws IOException {
      byte[] var5;
      try (
         ByteArrayOutputStream _snowman = new ByteArrayOutputStream();
         WritableByteChannel _snowmanx = Channels.newChannel(_snowman);
      ) {
         if (!this.write(_snowmanx)) {
            throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
         }

         var5 = _snowman.toByteArray();
      }

      return var5;
   }

   private boolean write(WritableByteChannel _snowman) throws IOException {
      NativeImage.WriteCallback _snowmanx = new NativeImage.WriteCallback(_snowman);

      boolean var4;
      try {
         int _snowmanxx = Math.min(this.getHeight(), Integer.MAX_VALUE / this.getWidth() / this.format.getChannelCount());
         if (_snowmanxx < this.getHeight()) {
            LOGGER.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.getHeight(), _snowmanxx);
         }

         if (STBImageWrite.nstbi_write_png_to_func(_snowmanx.address(), 0L, this.getWidth(), _snowmanxx, this.format.getChannelCount(), this.pointer, 0) != 0) {
            _snowmanx.throwStoredException();
            return true;
         }

         var4 = false;
      } finally {
         _snowmanx.free();
      }

      return var4;
   }

   public void copyFrom(NativeImage image) {
      if (image.getFormat() != this.format) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int _snowman = this.format.getChannelCount();
         this.checkAllocated();
         image.checkAllocated();
         if (this.width == image.width) {
            MemoryUtil.memCopy(image.pointer, this.pointer, Math.min(this.sizeBytes, image.sizeBytes));
         } else {
            int _snowmanx = Math.min(this.getWidth(), image.getWidth());
            int _snowmanxx = Math.min(this.getHeight(), image.getHeight());

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx * image.getWidth() * _snowman;
               int _snowmanxxxxx = _snowmanxxx * this.getWidth() * _snowman;
               MemoryUtil.memCopy(image.pointer + (long)_snowmanxxxx, this.pointer + (long)_snowmanxxxxx, (long)_snowmanx);
            }
         }
      }
   }

   public void fillRect(int x, int y, int width, int height, int color) {
      for (int _snowman = y; _snowman < y + height; _snowman++) {
         for (int _snowmanx = x; _snowmanx < x + width; _snowmanx++) {
            this.setPixelColor(_snowmanx, _snowman, color);
         }
      }
   }

   public void copyRect(int x, int y, int translateX, int translateY, int width, int height, boolean flipX, boolean flipY) {
      for (int _snowman = 0; _snowman < height; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < width; _snowmanx++) {
            int _snowmanxx = flipX ? width - 1 - _snowmanx : _snowmanx;
            int _snowmanxxx = flipY ? height - 1 - _snowman : _snowman;
            int _snowmanxxxx = this.getPixelColor(x + _snowmanx, y + _snowman);
            this.setPixelColor(x + translateX + _snowmanxx, y + translateY + _snowmanxxx, _snowmanxxxx);
         }
      }
   }

   public void mirrorVertically() {
      this.checkAllocated();
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         int _snowmanx = this.format.getChannelCount();
         int _snowmanxx = this.getWidth() * _snowmanx;
         long _snowmanxxx = _snowman.nmalloc(_snowmanxx);

         for (int _snowmanxxxx = 0; _snowmanxxxx < this.getHeight() / 2; _snowmanxxxx++) {
            int _snowmanxxxxx = _snowmanxxxx * this.getWidth() * _snowmanx;
            int _snowmanxxxxxx = (this.getHeight() - 1 - _snowmanxxxx) * this.getWidth() * _snowmanx;
            MemoryUtil.memCopy(this.pointer + (long)_snowmanxxxxx, _snowmanxxx, (long)_snowmanxx);
            MemoryUtil.memCopy(this.pointer + (long)_snowmanxxxxxx, this.pointer + (long)_snowmanxxxxx, (long)_snowmanxx);
            MemoryUtil.memCopy(_snowmanxxx, this.pointer + (long)_snowmanxxxxxx, (long)_snowmanxx);
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

   public void resizeSubRectTo(int x, int y, int width, int height, NativeImage targetImage) {
      this.checkAllocated();
      if (targetImage.getFormat() != this.format) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int _snowman = this.format.getChannelCount();
         STBImageResize.nstbir_resize_uint8(
            this.pointer + (long)((x + y * this.getWidth()) * _snowman),
            width,
            height,
            this.getWidth() * _snowman,
            targetImage.pointer,
            targetImage.getWidth(),
            targetImage.getHeight(),
            0,
            _snowman
         );
      }
   }

   public void untrack() {
      Untracker.untrack(this.pointer);
   }

   public static NativeImage read(String dataUri) throws IOException {
      byte[] _snowman = Base64.getDecoder().decode(dataUri.replaceAll("\n", "").getBytes(Charsets.UTF_8));
      MemoryStack _snowmanx = MemoryStack.stackPush();
      Throwable var3 = null;

      NativeImage var5;
      try {
         ByteBuffer _snowmanxx = _snowmanx.malloc(_snowman.length);
         _snowmanxx.put(_snowman);
         ((Buffer)_snowmanxx).rewind();
         var5 = read(_snowmanxx);
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

   public static int getAlpha(int color) {
      return color >> 24 & 0xFF;
   }

   public static int getRed(int color) {
      return color >> 0 & 0xFF;
   }

   public static int getGreen(int color) {
      return color >> 8 & 0xFF;
   }

   public static int getBlue(int color) {
      return color >> 16 & 0xFF;
   }

   public static int getAbgrColor(int alpha, int blue, int green, int red) {
      return (alpha & 0xFF) << 24 | (blue & 0xFF) << 16 | (green & 0xFF) << 8 | (red & 0xFF) << 0;
   }

   public static enum Format {
      ABGR(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      BGR(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 6410, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6409, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      private final int channelCount;
      private final int pixelDataFormat;
      private final boolean hasRed;
      private final boolean hasGreen;
      private final boolean hasBlue;
      private final boolean hasLuminance;
      private final boolean hasAlpha;
      private final int redOffset;
      private final int greenOffset;
      private final int blueOffset;
      private final int luminanceChannelOffset;
      private final int alphaChannelOffset;
      private final boolean writeable;

      private Format(
         int channels,
         int glFormat,
         boolean hasRed,
         boolean hasGreen,
         boolean hasBlue,
         boolean hasLuminance,
         boolean hasAlpha,
         int redOffset,
         int greenOffset,
         int blueOffset,
         int luminanceOffset,
         int alphaOffset,
         boolean writeable
      ) {
         this.channelCount = channels;
         this.pixelDataFormat = glFormat;
         this.hasRed = hasRed;
         this.hasGreen = hasGreen;
         this.hasBlue = hasBlue;
         this.hasLuminance = hasLuminance;
         this.hasAlpha = hasAlpha;
         this.redOffset = redOffset;
         this.greenOffset = greenOffset;
         this.blueOffset = blueOffset;
         this.luminanceChannelOffset = luminanceOffset;
         this.alphaChannelOffset = alphaOffset;
         this.writeable = writeable;
      }

      public int getChannelCount() {
         return this.channelCount;
      }

      public void setPackAlignment() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         GlStateManager.pixelStore(3333, this.getChannelCount());
      }

      public void setUnpackAlignment() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         GlStateManager.pixelStore(3317, this.getChannelCount());
      }

      public int getPixelDataFormat() {
         return this.pixelDataFormat;
      }

      public boolean hasAlphaChannel() {
         return this.hasAlpha;
      }

      public int getAlphaChannelOffset() {
         return this.alphaChannelOffset;
      }

      public boolean hasOpacityChannel() {
         return this.hasLuminance || this.hasAlpha;
      }

      public int getOpacityOffset() {
         return this.hasLuminance ? this.luminanceChannelOffset : this.alphaChannelOffset;
      }

      public boolean isWriteable() {
         return this.writeable;
      }

      private static NativeImage.Format getFormat(int glFormat) {
         switch (glFormat) {
            case 1:
               return LUMINANCE;
            case 2:
               return LUMINANCE_ALPHA;
            case 3:
               return BGR;
            case 4:
            default:
               return ABGR;
         }
      }
   }

   public static enum GLFormat {
      ABGR(6408),
      BGR(6407),
      LUMINANCE_ALPHA(6410),
      LUMINANCE(6409),
      INTENSITY(32841);

      private final int glConstant;

      private GLFormat(int glConstant) {
         this.glConstant = glConstant;
      }

      int getGlConstant() {
         return this.glConstant;
      }
   }

   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel channel;
      @Nullable
      private IOException exception;

      private WriteCallback(WritableByteChannel channel) {
         this.channel = channel;
      }

      public void invoke(long context, long data, int size) {
         ByteBuffer _snowman = getData(data, size);

         try {
            this.channel.write(_snowman);
         } catch (IOException var8) {
            this.exception = var8;
         }
      }

      public void throwStoredException() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         }
      }
   }
}
