package net.minecraft.client.util;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.texture.TextureUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public final class Window implements AutoCloseable {
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
      Optional<VideoMode> _snowman = VideoMode.fromString(videoMode);
      if (_snowman.isPresent()) {
         this.videoMode = _snowman;
      } else if (settings.fullscreenWidth.isPresent() && settings.fullscreenHeight.isPresent()) {
         this.videoMode = Optional.of(new VideoMode(settings.fullscreenWidth.getAsInt(), settings.fullscreenHeight.getAsInt(), 8, 8, 8, 60));
      } else {
         this.videoMode = Optional.empty();
      }

      this.currentFullscreen = this.fullscreen = settings.fullscreen;
      Monitor _snowmanx = monitorTracker.getMonitor(GLFW.glfwGetPrimaryMonitor());
      this.windowedWidth = this.width = settings.width > 0 ? settings.width : 1;
      this.windowedHeight = this.height = settings.height > 0 ? settings.height : 1;
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 2);
      GLFW.glfwWindowHint(139267, 0);
      GLFW.glfwWindowHint(139272, 0);
      this.handle = GLFW.glfwCreateWindow(this.width, this.height, title, this.fullscreen && _snowmanx != null ? _snowmanx.getHandle() : 0L, 0L);
      if (_snowmanx != null) {
         VideoMode _snowmanxx = _snowmanx.findClosestVideoMode(this.fullscreen ? this.videoMode : Optional.empty());
         this.windowedX = this.x = _snowmanx.getViewportX() + _snowmanxx.getWidth() / 2 - this.width / 2;
         this.windowedY = this.y = _snowmanx.getViewportY() + _snowmanxx.getHeight() / 2 - this.height / 2;
      } else {
         int[] _snowmanxx = new int[1];
         int[] _snowmanxxx = new int[1];
         GLFW.glfwGetWindowPos(this.handle, _snowmanxx, _snowmanxxx);
         this.windowedX = this.x = _snowmanxx[0];
         this.windowedY = this.y = _snowmanxxx[0];
      }

      GLFW.glfwMakeContextCurrent(this.handle);
      GL.createCapabilities();
      this.updateWindowRegion();
      this.updateFramebufferSize();
      GLFW.glfwSetFramebufferSizeCallback(this.handle, this::onFramebufferSizeChanged);
      GLFW.glfwSetWindowPosCallback(this.handle, this::onWindowPosChanged);
      GLFW.glfwSetWindowSizeCallback(this.handle, this::onWindowSizeChanged);
      GLFW.glfwSetWindowFocusCallback(this.handle, this::onWindowFocusChanged);
      GLFW.glfwSetCursorEnterCallback(this.handle, this::onCursorEnterChanged);
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
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         PointerBuffer _snowmanx = _snowman.mallocPointer(1);
         int _snowmanxx = GLFW.glfwGetError(_snowmanx);
         if (_snowmanxx != 0) {
            long _snowmanxxx = _snowmanx.get();
            String _snowmanxxxx = _snowmanxxx == 0L ? "" : MemoryUtil.memUTF8(_snowmanxxx);
            consumer.accept(_snowmanxx, _snowmanxxxx);
         }
      } catch (Throwable var15) {
         var2 = var15;
         throw var15;
      } finally {
         if (_snowman != null) {
            if (var2 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var14) {
                  var2.addSuppressed(var14);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   public void setIcon(InputStream icon16, InputStream icon32) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);

      try {
         MemoryStack _snowman = MemoryStack.stackPush();
         Throwable var4 = null;

         try {
            if (icon16 == null) {
               throw new FileNotFoundException("icons/icon_16x16.png");
            }

            if (icon32 == null) {
               throw new FileNotFoundException("icons/icon_32x32.png");
            }

            IntBuffer _snowmanx = _snowman.mallocInt(1);
            IntBuffer _snowmanxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxx = _snowman.mallocInt(1);
            Buffer _snowmanxxxx = GLFWImage.mallocStack(2, _snowman);
            ByteBuffer _snowmanxxxxx = this.readImage(icon16, _snowmanx, _snowmanxx, _snowmanxxx);
            if (_snowmanxxxxx == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            _snowmanxxxx.position(0);
            _snowmanxxxx.width(_snowmanx.get(0));
            _snowmanxxxx.height(_snowmanxx.get(0));
            _snowmanxxxx.pixels(_snowmanxxxxx);
            ByteBuffer _snowmanxxxxxx = this.readImage(icon32, _snowmanx, _snowmanxx, _snowmanxxx);
            if (_snowmanxxxxxx == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            _snowmanxxxx.position(1);
            _snowmanxxxx.width(_snowmanx.get(0));
            _snowmanxxxx.height(_snowmanxx.get(0));
            _snowmanxxxx.pixels(_snowmanxxxxxx);
            _snowmanxxxx.position(0);
            GLFW.glfwSetWindowIcon(this.handle, _snowmanxxxx);
            STBImage.stbi_image_free(_snowmanxxxxx);
            STBImage.stbi_image_free(_snowmanxxxxxx);
         } catch (Throwable var19) {
            var4 = var19;
            throw var19;
         } finally {
            if (_snowman != null) {
               if (var4 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  _snowman.close();
               }
            }
         }
      } catch (IOException var21) {
         LOGGER.error("Couldn't set icon", var21);
      }
   }

   @Nullable
   private ByteBuffer readImage(InputStream in, IntBuffer x, IntBuffer y, IntBuffer channels) throws IOException {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      ByteBuffer _snowman = null;

      ByteBuffer var6;
      try {
         _snowman = TextureUtil.readAllToByteBuffer(in);
         ((java.nio.Buffer)_snowman).rewind();
         var6 = STBImage.stbi_load_from_memory(_snowman, x, y, channels, 0);
      } finally {
         if (_snowman != null) {
            MemoryUtil.memFree(_snowman);
         }
      }

      return var6;
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
      String _snowman = "GLFW error " + error + ": " + MemoryUtil.memUTF8(description);
      TinyFileDialogs.tinyfd_messageBox(
         "Minecraft", _snowman + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false
      );
      throw new Window.GlErroredException(_snowman);
   }

   public void logGlError(int error, long description) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      String _snowman = MemoryUtil.memUTF8(description);
      LOGGER.error("########## GL ERROR ##########");
      LOGGER.error("@ {}", this.phase);
      LOGGER.error("{}: {}", error, _snowman);
   }

   public void logOnGlError() {
      GLFWErrorCallback _snowman = GLFW.glfwSetErrorCallback(this.errorCallback);
      if (_snowman != null) {
         _snowman.free();
      }
   }

   public void setVsync(boolean vsync) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.vsync = vsync;
      GLFW.glfwSwapInterval(vsync ? 1 : 0);
   }

   @Override
   public void close() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Callbacks.glfwFreeCallbacks(this.handle);
      this.errorCallback.close();
      GLFW.glfwDestroyWindow(this.handle);
      GLFW.glfwTerminate();
   }

   private void onWindowPosChanged(long window, int x, int y) {
      this.x = x;
      this.y = y;
   }

   private void onFramebufferSizeChanged(long window, int width, int height) {
      if (window == this.handle) {
         int _snowman = this.getFramebufferWidth();
         int _snowmanx = this.getFramebufferHeight();
         if (width != 0 && height != 0) {
            this.framebufferWidth = width;
            this.framebufferHeight = height;
            if (this.getFramebufferWidth() != _snowman || this.getFramebufferHeight() != _snowmanx) {
               this.eventHandler.onResolutionChanged();
            }
         }
      }
   }

   private void updateFramebufferSize() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      int[] _snowman = new int[1];
      int[] _snowmanx = new int[1];
      GLFW.glfwGetFramebufferSize(this.handle, _snowman, _snowmanx);
      this.framebufferWidth = _snowman[0];
      this.framebufferHeight = _snowmanx[0];
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
      boolean _snowman = !videoMode.equals(this.videoMode);
      this.videoMode = videoMode;
      if (_snowman) {
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
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      boolean _snowman = GLFW.glfwGetWindowMonitor(this.handle) != 0L;
      if (this.fullscreen) {
         Monitor _snowmanx = this.monitorTracker.getMonitor(this);
         if (_snowmanx == null) {
            LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
            this.fullscreen = false;
         } else {
            VideoMode _snowmanxx = _snowmanx.findClosestVideoMode(this.videoMode);
            if (!_snowman) {
               this.windowedX = this.x;
               this.windowedY = this.y;
               this.windowedWidth = this.width;
               this.windowedHeight = this.height;
            }

            this.x = 0;
            this.y = 0;
            this.width = _snowmanxx.getWidth();
            this.height = _snowmanxx.getHeight();
            GLFW.glfwSetWindowMonitor(this.handle, _snowmanx.getHandle(), this.x, this.y, this.width, this.height, _snowmanxx.getRefreshRate());
         }
      } else {
         this.x = this.windowedX;
         this.y = this.windowedY;
         this.width = this.windowedWidth;
         this.height = this.windowedHeight;
         GLFW.glfwSetWindowMonitor(this.handle, 0L, this.x, this.y, this.width, this.height, -1);
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
      } catch (Exception var3) {
         LOGGER.error("Couldn't toggle fullscreen", var3);
      }
   }

   public int calculateScaleFactor(int guiScale, boolean forceUnicodeFont) {
      int _snowman = 1;

      while (
         _snowman != guiScale
            && _snowman < this.framebufferWidth
            && _snowman < this.framebufferHeight
            && this.framebufferWidth / (_snowman + 1) >= 320
            && this.framebufferHeight / (_snowman + 1) >= 240
      ) {
         _snowman++;
      }

      if (forceUnicodeFont && _snowman % 2 != 0) {
         _snowman++;
      }

      return _snowman;
   }

   public void setScaleFactor(double scaleFactor) {
      this.scaleFactor = scaleFactor;
      int _snowman = (int)((double)this.framebufferWidth / scaleFactor);
      this.scaledWidth = (double)this.framebufferWidth / scaleFactor > (double)_snowman ? _snowman + 1 : _snowman;
      int _snowmanx = (int)((double)this.framebufferHeight / scaleFactor);
      this.scaledHeight = (double)this.framebufferHeight / scaleFactor > (double)_snowmanx ? _snowmanx + 1 : _snowmanx;
   }

   public void setTitle(String title) {
      GLFW.glfwSetWindowTitle(this.handle, title);
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

   public static class GlErroredException extends GlException {
      private GlErroredException(String _snowman) {
         super(_snowman);
      }
   }
}
