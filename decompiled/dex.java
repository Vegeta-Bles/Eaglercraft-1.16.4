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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class dex {
   private static final Logger a = LogManager.getLogger();

   public static int a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (w.d) {
         int[] _snowman = new int[ThreadLocalRandom.current().nextInt(15) + 1];
         dem.a(_snowman);
         int _snowmanx = dem.M();
         dem.b(_snowman);
         return _snowmanx;
      } else {
         return dem.M();
      }
   }

   public static void a(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.r(_snowman);
   }

   public static void a(int var0, int var1, int var2) {
      a(det.b.a, _snowman, 0, _snowman, _snowman);
   }

   public static void a(det.b var0, int var1, int var2, int var3) {
      a(_snowman, _snowman, 0, _snowman, _snowman);
   }

   public static void a(int var0, int var1, int var2, int var3) {
      a(det.b.a, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(det.b var0, int var1, int var2, int var3, int var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      b(_snowman);
      if (_snowman >= 0) {
         dem.b(3553, 33085, _snowman);
         dem.b(3553, 33082, 0);
         dem.b(3553, 33083, _snowman);
         dem.a(3553, 34049, 0.0F);
      }

      for (int _snowman = 0; _snowman <= _snowman; _snowman++) {
         dem.a(3553, _snowman, _snowman.a(), _snowman >> _snowman, _snowman >> _snowman, 0, 6408, 5121, null);
      }
   }

   private static void b(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.s(_snowman);
   }

   public static ByteBuffer a(InputStream var0) throws IOException {
      ByteBuffer _snowman;
      if (_snowman instanceof FileInputStream) {
         FileInputStream _snowmanx = (FileInputStream)_snowman;
         FileChannel _snowmanxx = _snowmanx.getChannel();
         _snowman = MemoryUtil.memAlloc((int)_snowmanxx.size() + 1);

         while (_snowmanxx.read(_snowman) != -1) {
         }
      } else {
         _snowman = MemoryUtil.memAlloc(8192);
         ReadableByteChannel _snowmanx = Channels.newChannel(_snowman);

         while (_snowmanx.read(_snowman) != -1) {
            if (_snowman.remaining() == 0) {
               _snowman = MemoryUtil.memRealloc(_snowman, _snowman.capacity() * 2);
            }
         }
      }

      return _snowman;
   }

   public static String b(InputStream var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ByteBuffer _snowman = null;

      try {
         _snowman = a(_snowman);
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

   public static void a(IntBuffer var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, _snowman, _snowman, 0, 32993, 33639, _snowman);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}
