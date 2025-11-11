import com.google.common.base.Charsets;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public class deh {
   private final ByteBuffer a = BufferUtils.createByteBuffer(8192);

   public deh() {
   }

   public String a(long var1, GLFWErrorCallbackI var3) {
      GLFWErrorCallback _snowman = GLFW.glfwSetErrorCallback(_snowman);
      String _snowmanx = GLFW.glfwGetClipboardString(_snowman);
      _snowmanx = _snowmanx != null ? afr.a(_snowmanx) : "";
      GLFWErrorCallback _snowmanxx = GLFW.glfwSetErrorCallback(_snowman);
      if (_snowmanxx != null) {
         _snowmanxx.free();
      }

      return _snowmanx;
   }

   private static void a(long var0, ByteBuffer var2, byte[] var3) {
      ((Buffer)_snowman).clear();
      _snowman.put(_snowman);
      _snowman.put((byte)0);
      ((Buffer)_snowman).flip();
      GLFW.glfwSetClipboardString(_snowman, _snowman);
   }

   public void a(long var1, String var3) {
      byte[] _snowman = _snowman.getBytes(Charsets.UTF_8);
      int _snowmanx = _snowman.length + 1;
      if (_snowmanx < this.a.capacity()) {
         a(_snowman, this.a, _snowman);
      } else {
         ByteBuffer _snowmanxx = MemoryUtil.memAlloc(_snowmanx);

         try {
            a(_snowman, _snowmanxx, _snowman);
         } finally {
            MemoryUtil.memFree(_snowmanxx);
         }
      }
   }
}
