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
   private static final Map<Integer, String> LOOKUP_MAP = make(Maps.newHashMap(), var0 -> {
      var0.put(0, "No error");
      var0.put(1280, "Enum parameter is invalid for this function");
      var0.put(1281, "Parameter is invalid for this function");
      var0.put(1282, "Current state is invalid for this function");
      var0.put(1283, "Stack overflow");
      var0.put(1284, "Stack underflow");
      var0.put(1285, "Out of memory");
      var0.put(1286, "Operation on incomplete framebuffer");
      var0.put(1286, "Operation on incomplete framebuffer");
   });

   public GLX() {
   }

   public static String getOpenGLVersionString() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GLFW.glfwGetCurrentContext() == 0L ? "NO CONTEXT" : dem.B(7937) + " GL version " + dem.B(7938) + ", " + dem.B(7936);
   }

   public static int _getRefreshRate(dez var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      long _snowman = GLFW.glfwGetWindowMonitor(_snowman.i());
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
      dez.a((var0x, var1x) -> {
         throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", var0x, var1x));
      });
      List<String> _snowman = Lists.newArrayList();
      GLFWErrorCallback _snowmanx = GLFW.glfwSetErrorCallback((var1x, var2x) -> _snowman.add(String.format("GLFW error during init: [0x%X]%s", var1x, var2x)));
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

   public static void _setGlfwErrorCallback(GLFWErrorCallbackI var0) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLFWErrorCallback _snowman = GLFW.glfwSetErrorCallback(_snowman);
      if (_snowman != null) {
         _snowman.free();
      }
   }

   public static boolean _shouldClose(dez var0) {
      return GLFW.glfwWindowShouldClose(_snowman.i());
   }

   public static void _setupNvFogDistance() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (GL.getCapabilities().GL_NV_fog_distance) {
         dem.l(34138, 34139);
      }
   }

   public static void _init(int var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLCapabilities _snowman = GL.getCapabilities();
      capsString = "Using framebuffer using " + dem.a(_snowman);

      try {
         Processor[] _snowmanx = new SystemInfo().getHardware().getProcessors();
         cpuInfo = String.format("%dx %s", _snowmanx.length, _snowmanx[0]).replaceAll("\\s+", " ");
      } catch (Throwable var4) {
      }

      del.a(_snowman, _snowman);
   }

   public static String _getCapsString() {
      return capsString;
   }

   public static String _getCpuInfo() {
      return cpuInfo == null ? "<unknown>" : cpuInfo;
   }

   public static void _renderCrosshair(int var0, boolean var1, boolean var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dem.L();
      dem.a(false);
      dfo _snowman = RenderSystem.renderThreadTesselator();
      dfh _snowmanx = _snowman.c();
      GL11.glLineWidth(4.0F);
      _snowmanx.a(1, dfk.l);
      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(0, 0, 0, 255).d();
         _snowmanx.a((double)_snowman, 0.0, 0.0).a(0, 0, 0, 255).d();
      }

      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(0, 0, 0, 255).d();
         _snowmanx.a(0.0, (double)_snowman, 0.0).a(0, 0, 0, 255).d();
      }

      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(0, 0, 0, 255).d();
         _snowmanx.a(0.0, 0.0, (double)_snowman).a(0, 0, 0, 255).d();
      }

      _snowman.b();
      GL11.glLineWidth(2.0F);
      _snowmanx.a(1, dfk.l);
      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(255, 0, 0, 255).d();
         _snowmanx.a((double)_snowman, 0.0, 0.0).a(255, 0, 0, 255).d();
      }

      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(0, 255, 0, 255).d();
         _snowmanx.a(0.0, (double)_snowman, 0.0).a(0, 255, 0, 255).d();
      }

      if (_snowman) {
         _snowmanx.a(0.0, 0.0, 0.0).a(127, 127, 255, 255).d();
         _snowmanx.a(0.0, 0.0, (double)_snowman).a(127, 127, 255, 255).d();
      }

      _snowman.b();
      GL11.glLineWidth(1.0F);
      dem.a(true);
      dem.K();
   }

   public static String getErrorString(int var0) {
      return LOOKUP_MAP.get(_snowman);
   }

   public static <T> T make(Supplier<T> var0) {
      return _snowman.get();
   }

   public static <T> T make(T var0, Consumer<T> var1) {
      _snowman.accept(_snowman);
      return _snowman;
   }
}
