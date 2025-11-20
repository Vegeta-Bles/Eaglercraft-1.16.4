/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.system.MemoryUtil
 */
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
import net.minecraft.client.texture.NativeImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class TextureUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    public static int generateId() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (SharedConstants.isDevelopment) {
            int[] nArray = new int[ThreadLocalRandom.current().nextInt(15) + 1];
            GlStateManager.method_30498(nArray);
            int _snowman2 = GlStateManager.genTextures();
            GlStateManager.method_30499(nArray);
            return _snowman2;
        }
        return GlStateManager.genTextures();
    }

    public static void deleteId(int id) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.deleteTexture(id);
    }

    public static void allocate(int id, int width, int height) {
        TextureUtil.allocate(NativeImage.GLFormat.ABGR, id, 0, width, height);
    }

    public static void allocate(NativeImage.GLFormat internalFormat, int id, int width, int height) {
        TextureUtil.allocate(internalFormat, id, 0, width, height);
    }

    public static void allocate(int id, int maxLevel, int width, int height) {
        TextureUtil.allocate(NativeImage.GLFormat.ABGR, id, maxLevel, width, height);
    }

    public static void allocate(NativeImage.GLFormat internalFormat, int id, int maxLevel, int width, int height) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        TextureUtil.bind(id);
        if (maxLevel >= 0) {
            GlStateManager.texParameter(3553, 33085, maxLevel);
            GlStateManager.texParameter(3553, 33082, 0);
            GlStateManager.texParameter(3553, 33083, maxLevel);
            GlStateManager.texParameter(3553, 34049, 0.0f);
        }
        for (int i = 0; i <= maxLevel; ++i) {
            GlStateManager.texImage2D(3553, i, internalFormat.getGlConstant(), width >> i, height >> i, 0, 6408, 5121, null);
        }
    }

    private static void bind(int id) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.bindTexture(id);
    }

    public static ByteBuffer readAllToByteBuffer(InputStream inputStream) throws IOException {
        ByteBuffer _snowman3;
        if (inputStream instanceof FileInputStream) {
            FileInputStream fileInputStream = (FileInputStream)inputStream;
            FileChannel _snowman2 = fileInputStream.getChannel();
            _snowman3 = MemoryUtil.memAlloc((int)((int)_snowman2.size() + 1));
            while (_snowman2.read(_snowman3) != -1) {
            }
        } else {
            _snowman3 = MemoryUtil.memAlloc((int)8192);
            ReadableByteChannel _snowman4 = Channels.newChannel(inputStream);
            while (_snowman4.read(_snowman3) != -1) {
                if (_snowman3.remaining() != 0) continue;
                _snowman3 = MemoryUtil.memRealloc((ByteBuffer)_snowman3, (int)(_snowman3.capacity() * 2));
            }
        }
        return _snowman3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String readAllToString(InputStream inputStream) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ByteBuffer byteBuffer = null;
        try {
            byteBuffer = TextureUtil.readAllToByteBuffer(inputStream);
            int n = byteBuffer.position();
            byteBuffer.rewind();
            String string = MemoryUtil.memASCII((ByteBuffer)byteBuffer, (int)n);
            return string;
        }
        catch (IOException iOException) {
        }
        finally {
            if (byteBuffer != null) {
                MemoryUtil.memFree((Buffer)byteBuffer);
            }
        }
        return null;
    }

    public static void uploadImage(IntBuffer imageData, int width, int height) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPixelStorei((int)3312, (int)0);
        GL11.glPixelStorei((int)3313, (int)0);
        GL11.glPixelStorei((int)3314, (int)0);
        GL11.glPixelStorei((int)3315, (int)0);
        GL11.glPixelStorei((int)3316, (int)0);
        GL11.glPixelStorei((int)3317, (int)4);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)width, (int)height, (int)0, (int)32993, (int)33639, (IntBuffer)imageData);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
    }
}

