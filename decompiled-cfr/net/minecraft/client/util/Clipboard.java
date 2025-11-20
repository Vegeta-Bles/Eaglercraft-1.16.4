/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.system.MemoryUtil
 */
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
    private final ByteBuffer clipboardBuffer = BufferUtils.createByteBuffer((int)8192);

    public String getClipboard(long window, GLFWErrorCallbackI gLFWErrorCallbackI) {
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)gLFWErrorCallbackI);
        String _snowman2 = GLFW.glfwGetClipboardString((long)window);
        _snowman2 = _snowman2 != null ? TextVisitFactory.validateSurrogates(_snowman2) : "";
        _snowman = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)gLFWErrorCallback);
        if (_snowman != null) {
            _snowman.free();
        }
        return _snowman2;
    }

    private static void setClipboard(long l, ByteBuffer byteBuffer, byte[] byArray) {
        byteBuffer.clear();
        byteBuffer.put(byArray);
        byteBuffer.put((byte)0);
        byteBuffer.flip();
        GLFW.glfwSetClipboardString((long)l, (ByteBuffer)byteBuffer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setClipboard(long window, String string) {
        byte[] byArray = string.getBytes(Charsets.UTF_8);
        int _snowman2 = byArray.length + 1;
        if (_snowman2 < this.clipboardBuffer.capacity()) {
            Clipboard.setClipboard(window, this.clipboardBuffer, byArray);
        } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc((int)_snowman2);
            try {
                Clipboard.setClipboard(window, byteBuffer, byArray);
            }
            finally {
                MemoryUtil.memFree((Buffer)byteBuffer);
            }
        }
    }
}

