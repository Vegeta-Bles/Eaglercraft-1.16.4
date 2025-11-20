package net.minecraft.client.util;

import com.google.common.base.Charsets;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.font.TextVisitFactory;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public class Clipboard {
   private final ByteBuffer clipboardBuffer = BufferUtils.createByteBuffer(8192);

   public Clipboard() {
   }

   public String getClipboard(long window, GLFWErrorCallbackI _snowman) {
      GLFWErrorCallback _snowmanx = GLFW.glfwSetErrorCallback(_snowman);
      String _snowmanxx = GLFW.glfwGetClipboardString(window);
      _snowmanxx = _snowmanxx != null ? TextVisitFactory.validateSurrogates(_snowmanxx) : "";
      GLFWErrorCallback _snowmanxxx = GLFW.glfwSetErrorCallback(_snowmanx);
      if (_snowmanxxx != null) {
         _snowmanxxx.free();
      }

      return _snowmanxx;
   }

   private static void setClipboard(long _snowman, ByteBuffer _snowman, byte[] _snowman) {
      ((Buffer)_snowman).clear();
      _snowman.put(_snowman);
      _snowman.put((byte)0);
      ((Buffer)_snowman).flip();
      GLFW.glfwSetClipboardString(_snowman, _snowman);
   }

   public void setClipboard(long window, String _snowman) {
      byte[] _snowmanx = _snowman.getBytes(Charsets.UTF_8);
      int _snowmanxx = _snowmanx.length + 1;
      if (_snowmanxx < this.clipboardBuffer.capacity()) {
         setClipboard(window, this.clipboardBuffer, _snowmanx);
      } else {
         ByteBuffer _snowmanxxx = MemoryUtil.memAlloc(_snowmanxx);

         try {
            setClipboard(window, _snowmanxxx, _snowmanx);
         } finally {
            MemoryUtil.memFree(_snowmanxxx);
         }
      }
   }
}
