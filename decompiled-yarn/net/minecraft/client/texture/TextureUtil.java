package net.minecraft.client.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class TextureUtil {
   private static final Logger LOGGER = LogManager.getLogger();

   public static int generateId() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (SharedConstants.isDevelopment) {
         int[] _snowman = new int[ThreadLocalRandom.current().nextInt(15) + 1];
         GlStateManager.method_30498(_snowman);
         int _snowmanx = GlStateManager.genTextures();
         GlStateManager.method_30499(_snowman);
         return _snowmanx;
      } else {
         return GlStateManager.genTextures();
      }
   }

   public static void deleteId(int id) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.deleteTexture(id);
   }

   public static void allocate(int id, int width, int height) {
      allocate(NativeImage.GLFormat.ABGR, id, 0, width, height);
   }

   public static void allocate(NativeImage.GLFormat internalFormat, int id, int width, int height) {
      allocate(internalFormat, id, 0, width, height);
   }

   public static void allocate(int id, int maxLevel, int width, int height) {
      allocate(NativeImage.GLFormat.ABGR, id, maxLevel, width, height);
   }

   public static void allocate(NativeImage.GLFormat internalFormat, int id, int maxLevel, int width, int height) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      bind(id);
      if (maxLevel >= 0) {
         GlStateManager.texParameter(3553, 33085, maxLevel);
         GlStateManager.texParameter(3553, 33082, 0);
         GlStateManager.texParameter(3553, 33083, maxLevel);
         GlStateManager.texParameter(3553, 34049, 0.0F);
      }

      for (int _snowman = 0; _snowman <= maxLevel; _snowman++) {
         GlStateManager.texImage2D(3553, _snowman, internalFormat.getGlConstant(), width >> _snowman, height >> _snowman, 0, 6408, 5121, null);
      }
   }

   private static void bind(int id) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.bindTexture(id);
   }

   public static ByteBuffer readAllToByteBuffer(InputStream inputStream) throws IOException {
      ByteBuffer _snowman;
      if (inputStream instanceof FileInputStream) {
         FileInputStream _snowmanx = (FileInputStream)inputStream;
         FileChannel _snowmanxx = _snowmanx.getChannel();
         _snowman = MemoryUtil.memAlloc((int)_snowmanxx.size() + 1);

         while (_snowmanxx.read(_snowman) != -1) {
         }
      } else {
         _snowman = MemoryUtil.memAlloc(8192);
         ReadableByteChannel _snowmanx = Channels.newChannel(inputStream);

         while (_snowmanx.read(_snowman) != -1) {
            if (_snowman.remaining() == 0) {
               _snowman = MemoryUtil.memRealloc(_snowman, _snowman.capacity() * 2);
            }
         }
      }

      return _snowman;
   }

   public static String readAllToString(InputStream inputStream) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ByteBuffer _snowman = null;

      try {
         _snowman = readAllToByteBuffer(inputStream);
         int _snowmanx = _snowman.position();
         ((Buffer)_snowman).rewind();
         return MemoryUtil.memASCII(_snowman, _snowmanx);
      } catch (IOException var7) {
      } finally {
         if (_snowman != null) {
            MemoryUtil.memFree(_snowman);
         }
      }

      return null;
   }

   public static void uploadImage(IntBuffer imageData, int width, int height) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, imageData);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}
