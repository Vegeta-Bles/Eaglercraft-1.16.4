package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.gl.GlDebug;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class GLX {
   private static final Logger LOGGER = LogManager.getLogger();
   private static String capsString = "";
   private static String cpuInfo;
   private static final Map<Integer, String> LOOKUP_MAP = make(Maps.newHashMap(), _snowman -> {
      _snowman.put(0, "No error");
      _snowman.put(1280, "Enum parameter is invalid for this function");
      _snowman.put(1281, "Parameter is invalid for this function");
      _snowman.put(1282, "Current state is invalid for this function");
      _snowman.put(1283, "Stack overflow");
      _snowman.put(1284, "Stack underflow");
      _snowman.put(1285, "Out of memory");
      _snowman.put(1286, "Operation on incomplete framebuffer");
      _snowman.put(1286, "Operation on incomplete framebuffer");
   });

   public GLX() {
   }

   public static String getOpenGLVersionString() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GLFW.glfwGetCurrentContext() == 0L
         ? "NO CONTEXT"
         : GlStateManager.getString(7937) + " GL version " + GlStateManager.getString(7938) + ", " + GlStateManager.getString(7936);
   }

   public static int _getRefreshRate(Window window) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      long _snowman = GLFW.glfwGetWindowMonitor(window.getHandle());
      if (_snowman == 0L) {
         _snowman = GLFW.glfwGetPrimaryMonitor();
      }

      GLFWVidMode _snowmanx = _snowman == 0L ? null : GLFW.glfwGetVideoMode(_snowman);
      return _snowmanx == null ? 0 : _snowmanx.refreshRate();
   }

   public static String _getLWJGLVersion() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return Version.getVersion();
   }

   public static LongSupplier _initGlfw() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      Window.acceptError((_snowman, _snowmanx) -> {
         throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", _snowman, _snowmanx));
      });
      List<String> _snowman = Lists.newArrayList();
      GLFWErrorCallback _snowmanx = GLFW.glfwSetErrorCallback((_snowmanxx, _snowmanxxx) -> _snowman.add(String.format("GLFW error during init: [0x%X]%s", _snowmanxx, _snowmanxxx)));
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(_snowman));
      } else {
         LongSupplier _snowmanxx = () -> (long)(GLFW.glfwGetTime() * 1.0E9);

         for (String _snowmanxxx : _snowman) {
            LOGGER.error("GLFW error collected during initialization: {}", _snowmanxxx);
         }

         RenderSystem.setErrorCallback(_snowmanx);
         return _snowmanxx;
      }
   }

   public static void _setGlfwErrorCallback(GLFWErrorCallbackI callback) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLFWErrorCallback _snowman = GLFW.glfwSetErrorCallback(callback);
      if (_snowman != null) {
         _snowman.free();
      }
   }

   public static boolean _shouldClose(Window window) {
      return GLFW.glfwWindowShouldClose(window.getHandle());
   }

   public static void _setupNvFogDistance() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (GL.getCapabilities().GL_NV_fog_distance) {
         GlStateManager.fogi(34138, 34139);
      }
   }

   public static void _init(int debugVerbosity, boolean debugSync) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLCapabilities _snowman = GL.getCapabilities();
      capsString = "Using framebuffer using " + GlStateManager.initFramebufferSupport(_snowman);

      try {
         Processor[] _snowmanx = new SystemInfo().getHardware().getProcessors();
         cpuInfo = String.format("%dx %s", _snowmanx.length, _snowmanx[0]).replaceAll("\\s+", " ");
      } catch (Throwable var4) {
      }

      GlDebug.enableDebug(debugVerbosity, debugSync);
   }

   public static String _getCapsString() {
      return capsString;
   }

   public static String _getCpuInfo() {
      return cpuInfo == null ? "<unknown>" : cpuInfo;
   }

   public static void _renderCrosshair(int size, boolean drawX, boolean drawY, boolean drawZ) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.disableTexture();
      GlStateManager.depthMask(false);
      Tessellator _snowman = RenderSystem.renderThreadTesselator();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      GL11.glLineWidth(4.0F);
      _snowmanx.begin(1, VertexFormats.POSITION_COLOR);
      if (drawX) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).next();
         _snowmanx.vertex((double)size, 0.0, 0.0).color(0, 0, 0, 255).next();
      }

      if (drawY) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).next();
         _snowmanx.vertex(0.0, (double)size, 0.0).color(0, 0, 0, 255).next();
      }

      if (drawZ) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).next();
         _snowmanx.vertex(0.0, 0.0, (double)size).color(0, 0, 0, 255).next();
      }

      _snowman.draw();
      GL11.glLineWidth(2.0F);
      _snowmanx.begin(1, VertexFormats.POSITION_COLOR);
      if (drawX) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(255, 0, 0, 255).next();
         _snowmanx.vertex((double)size, 0.0, 0.0).color(255, 0, 0, 255).next();
      }

      if (drawY) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(0, 255, 0, 255).next();
         _snowmanx.vertex(0.0, (double)size, 0.0).color(0, 255, 0, 255).next();
      }

      if (drawZ) {
         _snowmanx.vertex(0.0, 0.0, 0.0).color(127, 127, 255, 255).next();
         _snowmanx.vertex(0.0, 0.0, (double)size).color(127, 127, 255, 255).next();
      }

      _snowman.draw();
      GL11.glLineWidth(1.0F);
      GlStateManager.depthMask(true);
      GlStateManager.enableTexture();
   }

   public static String getErrorString(int code) {
      return LOOKUP_MAP.get(code);
   }

   public static <T> T make(Supplier<T> factory) {
      return factory.get();
   }

   public static <T> T make(T object, Consumer<T> initializer) {
      initializer.accept(object);
      return object;
   }
}
