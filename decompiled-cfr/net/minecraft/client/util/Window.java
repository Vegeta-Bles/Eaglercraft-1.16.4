/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.glfw.Callbacks
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.glfw.GLFWImage
 *  org.lwjgl.glfw.GLFWImage$Buffer
 *  org.lwjgl.opengl.GL
 *  org.lwjgl.stb.STBImage
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 */
package net.minecraft.client.util;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.client.util.GlException;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.VideoMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public final class Window
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final GLFWErrorCallback errorCallback = GLFWErrorCallback.create(this::logGlError);
    private final WindowEventHandler eventHandler;
    private final MonitorTracker monitorTracker;
    private final long handle;
    private int windowedX;
    private int windowedY;
    private int windowedWidth;
    private int windowedHeight;
    private Optional<VideoMode> videoMode;
    private boolean fullscreen;
    private boolean currentFullscreen;
    private int x;
    private int y;
    private int width;
    private int height;
    private int framebufferWidth;
    private int framebufferHeight;
    private int scaledWidth;
    private int scaledHeight;
    private double scaleFactor;
    private String phase = "";
    private boolean videoModeDirty;
    private int framerateLimit;
    private boolean vsync;

    public Window(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, @Nullable String videoMode, String title) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.monitorTracker = monitorTracker;
        this.throwOnGlError();
        this.setPhase("Pre startup");
        this.eventHandler = eventHandler;
        Optional<VideoMode> optional = VideoMode.fromString(videoMode);
        this.videoMode = optional.isPresent() ? optional : (settings.fullscreenWidth.isPresent() && settings.fullscreenHeight.isPresent() ? Optional.of(new VideoMode(settings.fullscreenWidth.getAsInt(), settings.fullscreenHeight.getAsInt(), 8, 8, 8, 60)) : Optional.empty());
        this.currentFullscreen = this.fullscreen = settings.fullscreen;
        Monitor _snowman2 = monitorTracker.getMonitor(GLFW.glfwGetPrimaryMonitor());
        this.width = settings.width > 0 ? settings.width : 1;
        this.windowedWidth = this.width;
        this.height = settings.height > 0 ? settings.height : 1;
        this.windowedHeight = this.height;
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint((int)139265, (int)196609);
        GLFW.glfwWindowHint((int)139275, (int)221185);
        GLFW.glfwWindowHint((int)139266, (int)2);
        GLFW.glfwWindowHint((int)139267, (int)0);
        GLFW.glfwWindowHint((int)139272, (int)0);
        this.handle = GLFW.glfwCreateWindow((int)this.width, (int)this.height, (CharSequence)title, (long)(this.fullscreen && _snowman2 != null ? _snowman2.getHandle() : 0L), (long)0L);
        if (_snowman2 != null) {
            VideoMode videoMode2 = _snowman2.findClosestVideoMode(this.fullscreen ? this.videoMode : Optional.empty());
            this.windowedX = this.x = _snowman2.getViewportX() + videoMode2.getWidth() / 2 - this.width / 2;
            this.windowedY = this.y = _snowman2.getViewportY() + videoMode2.getHeight() / 2 - this.height / 2;
        } else {
            int[] nArray = new int[1];
            _snowman = new int[1];
            GLFW.glfwGetWindowPos((long)this.handle, (int[])nArray, (int[])_snowman);
            this.windowedX = this.x = nArray[0];
            this.windowedY = this.y = _snowman[0];
        }
        GLFW.glfwMakeContextCurrent((long)this.handle);
        GL.createCapabilities();
        this.updateWindowRegion();
        this.updateFramebufferSize();
        GLFW.glfwSetFramebufferSizeCallback((long)this.handle, this::onFramebufferSizeChanged);
        GLFW.glfwSetWindowPosCallback((long)this.handle, this::onWindowPosChanged);
        GLFW.glfwSetWindowSizeCallback((long)this.handle, this::onWindowSizeChanged);
        GLFW.glfwSetWindowFocusCallback((long)this.handle, this::onWindowFocusChanged);
        GLFW.glfwSetCursorEnterCallback((long)this.handle, this::onCursorEnterChanged);
    }

    public int getRefreshRate() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GLX._getRefreshRate(this);
    }

    public boolean shouldClose() {
        return GLX._shouldClose(this);
    }

    public static void acceptError(BiConsumer<Integer, String> consumer) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            PointerBuffer pointerBuffer = memoryStack.mallocPointer(1);
            int _snowman2 = GLFW.glfwGetError((PointerBuffer)pointerBuffer);
            if (_snowman2 != 0) {
                long l = pointerBuffer.get();
                String _snowman3 = l == 0L ? "" : MemoryUtil.memUTF8((long)l);
                consumer.accept(_snowman2, _snowman3);
            }
        }
    }

    public void setIcon(InputStream icon16, InputStream icon32) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            if (icon16 == null) {
                throw new FileNotFoundException("icons/icon_16x16.png");
            }
            if (icon32 == null) {
                throw new FileNotFoundException("icons/icon_32x32.png");
            }
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            GLFWImage.Buffer _snowman2 = GLFWImage.mallocStack((int)2, (MemoryStack)memoryStack);
            ByteBuffer _snowman3 = this.readImage(icon16, intBuffer, _snowman, _snowman);
            if (_snowman3 == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            _snowman2.position(0);
            _snowman2.width(intBuffer.get(0));
            _snowman2.height(_snowman.get(0));
            _snowman2.pixels(_snowman3);
            ByteBuffer _snowman4 = this.readImage(icon32, intBuffer, _snowman, _snowman);
            if (_snowman4 == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }
            _snowman2.position(1);
            _snowman2.width(intBuffer.get(0));
            _snowman2.height(_snowman.get(0));
            _snowman2.pixels(_snowman4);
            _snowman2.position(0);
            GLFW.glfwSetWindowIcon((long)this.handle, (GLFWImage.Buffer)_snowman2);
            STBImage.stbi_image_free((ByteBuffer)_snowman3);
            STBImage.stbi_image_free((ByteBuffer)_snowman4);
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't set icon", (Throwable)iOException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private ByteBuffer readImage(InputStream in, IntBuffer x, IntBuffer y, IntBuffer channels) throws IOException {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        ByteBuffer byteBuffer = null;
        try {
            byteBuffer = TextureUtil.readAllToByteBuffer(in);
            byteBuffer.rewind();
            ByteBuffer byteBuffer2 = STBImage.stbi_load_from_memory((ByteBuffer)byteBuffer, (IntBuffer)x, (IntBuffer)y, (IntBuffer)channels, (int)0);
            return byteBuffer2;
        }
        finally {
            if (byteBuffer != null) {
                MemoryUtil.memFree((Buffer)byteBuffer);
            }
        }
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    private void throwOnGlError() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLFW.glfwSetErrorCallback(Window::throwGlError);
    }

    private static void throwGlError(int error, long description) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        String string = "GLFW error " + error + ": " + MemoryUtil.memUTF8((long)description);
        TinyFileDialogs.tinyfd_messageBox((CharSequence)"Minecraft", (CharSequence)(string + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions)."), (CharSequence)"ok", (CharSequence)"error", (boolean)false);
        throw new GlErroredException(string);
    }

    public void logGlError(int error, long description) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        String string = MemoryUtil.memUTF8((long)description);
        LOGGER.error("########## GL ERROR ##########");
        LOGGER.error("@ {}", (Object)this.phase);
        LOGGER.error("{}: {}", (Object)error, (Object)string);
    }

    public void logOnGlError() {
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)this.errorCallback);
        if (gLFWErrorCallback != null) {
            gLFWErrorCallback.free();
        }
    }

    public void setVsync(boolean vsync) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.vsync = vsync;
        GLFW.glfwSwapInterval((int)(vsync ? 1 : 0));
    }

    @Override
    public void close() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        Callbacks.glfwFreeCallbacks((long)this.handle);
        this.errorCallback.close();
        GLFW.glfwDestroyWindow((long)this.handle);
        GLFW.glfwTerminate();
    }

    private void onWindowPosChanged(long window, int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void onFramebufferSizeChanged(long window, int width, int height) {
        if (window != this.handle) {
            return;
        }
        int n = this.getFramebufferWidth();
        _snowman = this.getFramebufferHeight();
        if (width == 0 || height == 0) {
            return;
        }
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        if (this.getFramebufferWidth() != n || this.getFramebufferHeight() != _snowman) {
            this.eventHandler.onResolutionChanged();
        }
    }

    private void updateFramebufferSize() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        int[] nArray = new int[1];
        _snowman = new int[1];
        GLFW.glfwGetFramebufferSize((long)this.handle, (int[])nArray, (int[])_snowman);
        this.framebufferWidth = nArray[0];
        this.framebufferHeight = _snowman[0];
    }

    private void onWindowSizeChanged(long window, int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void onWindowFocusChanged(long window, boolean focused) {
        if (window == this.handle) {
            this.eventHandler.onWindowFocusChanged(focused);
        }
    }

    private void onCursorEnterChanged(long window, boolean entered) {
        if (entered) {
            this.eventHandler.onCursorEnterChanged();
        }
    }

    public void setFramerateLimit(int framerateLimit) {
        this.framerateLimit = framerateLimit;
    }

    public int getFramerateLimit() {
        return this.framerateLimit;
    }

    public void swapBuffers() {
        RenderSystem.flipFrame(this.handle);
        if (this.fullscreen != this.currentFullscreen) {
            this.currentFullscreen = this.fullscreen;
            this.updateFullscreen(this.vsync);
        }
    }

    public Optional<VideoMode> getVideoMode() {
        return this.videoMode;
    }

    public void setVideoMode(Optional<VideoMode> videoMode) {
        boolean bl = !videoMode.equals(this.videoMode);
        this.videoMode = videoMode;
        if (bl) {
            this.videoModeDirty = true;
        }
    }

    public void applyVideoMode() {
        if (this.fullscreen && this.videoModeDirty) {
            this.videoModeDirty = false;
            this.updateWindowRegion();
            this.eventHandler.onResolutionChanged();
        }
    }

    private void updateWindowRegion() {
        boolean bl;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        boolean bl2 = bl = GLFW.glfwGetWindowMonitor((long)this.handle) != 0L;
        if (this.fullscreen) {
            Monitor monitor = this.monitorTracker.getMonitor(this);
            if (monitor == null) {
                LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
                this.fullscreen = false;
            } else {
                VideoMode videoMode = monitor.findClosestVideoMode(this.videoMode);
                if (!bl) {
                    this.windowedX = this.x;
                    this.windowedY = this.y;
                    this.windowedWidth = this.width;
                    this.windowedHeight = this.height;
                }
                this.x = 0;
                this.y = 0;
                this.width = videoMode.getWidth();
                this.height = videoMode.getHeight();
                GLFW.glfwSetWindowMonitor((long)this.handle, (long)monitor.getHandle(), (int)this.x, (int)this.y, (int)this.width, (int)this.height, (int)videoMode.getRefreshRate());
            }
        } else {
            this.x = this.windowedX;
            this.y = this.windowedY;
            this.width = this.windowedWidth;
            this.height = this.windowedHeight;
            GLFW.glfwSetWindowMonitor((long)this.handle, (long)0L, (int)this.x, (int)this.y, (int)this.width, (int)this.height, (int)-1);
        }
    }

    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
    }

    private void updateFullscreen(boolean vsync) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        try {
            this.updateWindowRegion();
            this.eventHandler.onResolutionChanged();
            this.setVsync(vsync);
            this.swapBuffers();
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't toggle fullscreen", (Throwable)exception);
        }
    }

    public int calculateScaleFactor(int guiScale, boolean forceUnicodeFont) {
        int n;
        for (n = 1; n != guiScale && n < this.framebufferWidth && n < this.framebufferHeight && this.framebufferWidth / (n + 1) >= 320 && this.framebufferHeight / (n + 1) >= 240; ++n) {
        }
        if (forceUnicodeFont && n % 2 != 0) {
            ++n;
        }
        return n;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        int n = (int)((double)this.framebufferWidth / scaleFactor);
        this.scaledWidth = (double)this.framebufferWidth / scaleFactor > (double)n ? n + 1 : n;
        _snowman = (int)((double)this.framebufferHeight / scaleFactor);
        this.scaledHeight = (double)this.framebufferHeight / scaleFactor > (double)_snowman ? _snowman + 1 : _snowman;
    }

    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle((long)this.handle, (CharSequence)title);
    }

    public long getHandle() {
        return this.handle;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public int getFramebufferWidth() {
        return this.framebufferWidth;
    }

    public int getFramebufferHeight() {
        return this.framebufferHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getScaleFactor() {
        return this.scaleFactor;
    }

    @Nullable
    public Monitor getMonitor() {
        return this.monitorTracker.getMonitor(this);
    }

    public void setRawMouseMotion(boolean rawMouseMotion) {
        InputUtil.setRawMouseMotionMode(this.handle, rawMouseMotion);
    }

    public static class GlErroredException
    extends GlException {
        private GlErroredException(String string) {
            super(string);
        }
    }
}

