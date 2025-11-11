package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GLX;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class RenderSystem {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ConcurrentLinkedQueue<dee> recordingQueue = Queues.newConcurrentLinkedQueue();
   private static final dfo RENDER_THREAD_TESSELATOR = new dfo();
   public static final float DEFAULTALPHACUTOFF = 0.1F;
   private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
   private static boolean isReplayingQueue;
   private static Thread gameThread;
   private static Thread renderThread;
   private static int MAX_SUPPORTED_TEXTURE_SIZE = -1;
   private static boolean isInInit;
   private static double lastDrawTime = Double.MIN_VALUE;

   public RenderSystem() {
   }

   public static void initRenderThread() {
      if (renderThread == null && gameThread != Thread.currentThread()) {
         renderThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize render thread");
      }
   }

   public static boolean isOnRenderThread() {
      return Thread.currentThread() == renderThread;
   }

   public static boolean isOnRenderThreadOrInit() {
      return isInInit || isOnRenderThread();
   }

   public static void initGameThread(boolean var0) {
      boolean _snowman = renderThread == Thread.currentThread();
      if (gameThread == null && renderThread != null && _snowman != _snowman) {
         gameThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize tick thread");
      }
   }

   public static boolean isOnGameThread() {
      return true;
   }

   public static boolean isOnGameThreadOrInit() {
      return isInInit || isOnGameThread();
   }

   public static void assertThread(Supplier<Boolean> var0) {
      if (!_snowman.get()) {
         throw new IllegalStateException("Rendersystem called from wrong thread");
      }
   }

   public static boolean isInInitPhase() {
      return true;
   }

   public static void recordRenderCall(dee var0) {
      recordingQueue.add(_snowman);
   }

   public static void flipFrame(long var0) {
      GLFW.glfwPollEvents();
      replayQueue();
      dfo.a().c().g();
      GLFW.glfwSwapBuffers(_snowman);
      GLFW.glfwPollEvents();
   }

   public static void replayQueue() {
      isReplayingQueue = true;

      while (!recordingQueue.isEmpty()) {
         dee _snowman = recordingQueue.poll();
         _snowman.execute();
      }

      isReplayingQueue = false;
   }

   public static void limitDisplayFPS(int var0) {
      double _snowman = lastDrawTime + 1.0 / (double)_snowman;

      double _snowmanx;
      for (_snowmanx = GLFW.glfwGetTime(); _snowmanx < _snowman; _snowmanx = GLFW.glfwGetTime()) {
         GLFW.glfwWaitEventsTimeout(_snowman - _snowmanx);
      }

      lastDrawTime = _snowmanx;
   }

   @Deprecated
   public static void pushLightingAttributes() {
      assertThread(RenderSystem::isOnGameThread);
      dem.a();
   }

   @Deprecated
   public static void pushTextureAttributes() {
      assertThread(RenderSystem::isOnGameThread);
      dem.b();
   }

   @Deprecated
   public static void popAttributes() {
      assertThread(RenderSystem::isOnGameThread);
      dem.c();
   }

   @Deprecated
   public static void disableAlphaTest() {
      assertThread(RenderSystem::isOnGameThread);
      dem.d();
   }

   @Deprecated
   public static void enableAlphaTest() {
      assertThread(RenderSystem::isOnGameThread);
      dem.e();
   }

   @Deprecated
   public static void alphaFunc(int var0, float var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman);
   }

   @Deprecated
   public static void enableLighting() {
      assertThread(RenderSystem::isOnGameThread);
      dem.f();
   }

   @Deprecated
   public static void disableLighting() {
      assertThread(RenderSystem::isOnGameThread);
      dem.g();
   }

   @Deprecated
   public static void enableColorMaterial() {
      assertThread(RenderSystem::isOnGameThread);
      dem.h();
   }

   @Deprecated
   public static void disableColorMaterial() {
      assertThread(RenderSystem::isOnGameThread);
      dem.i();
   }

   @Deprecated
   public static void colorMaterial(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman);
   }

   @Deprecated
   public static void normal3f(float var0, float var1, float var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman);
   }

   public static void disableDepthTest() {
      assertThread(RenderSystem::isOnGameThread);
      dem.l();
   }

   public static void enableDepthTest() {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.m();
   }

   public static void enableScissor(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.k();
      dem.a(_snowman, _snowman, _snowman, _snowman);
   }

   public static void disableScissor() {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.j();
   }

   public static void depthFunc(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman);
   }

   public static void depthMask(boolean var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman);
   }

   public static void enableBlend() {
      assertThread(RenderSystem::isOnGameThread);
      dem.o();
   }

   public static void disableBlend() {
      assertThread(RenderSystem::isOnGameThread);
      dem.n();
   }

   public static void blendFunc(dem.r var0, dem.j var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman.p, _snowman.o);
   }

   public static void blendFunc(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman);
   }

   public static void blendFuncSeparate(dem.r var0, dem.j var1, dem.r var2, dem.j var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman.p, _snowman.o, _snowman.p, _snowman.o);
   }

   public static void blendFuncSeparate(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman, _snowman, _snowman);
   }

   public static void blendEquation(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman);
   }

   public static void blendColor(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void enableFog() {
      assertThread(RenderSystem::isOnGameThread);
      dem.A();
   }

   @Deprecated
   public static void disableFog() {
      assertThread(RenderSystem::isOnGameThread);
      dem.B();
   }

   @Deprecated
   public static void fogMode(dem.m var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.o(_snowman.d);
   }

   @Deprecated
   public static void fogMode(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.o(_snowman);
   }

   @Deprecated
   public static void fogDensity(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman);
   }

   @Deprecated
   public static void fogStart(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman);
   }

   @Deprecated
   public static void fogEnd(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman);
   }

   @Deprecated
   public static void fog(int var0, float var1, float var2, float var3, float var4) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, new float[]{_snowman, _snowman, _snowman, _snowman});
   }

   @Deprecated
   public static void fogi(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.l(_snowman, _snowman);
   }

   public static void enableCull() {
      assertThread(RenderSystem::isOnGameThread);
      dem.C();
   }

   public static void disableCull() {
      assertThread(RenderSystem::isOnGameThread);
      dem.D();
   }

   public static void polygonMode(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.m(_snowman, _snowman);
   }

   public static void enablePolygonOffset() {
      assertThread(RenderSystem::isOnGameThread);
      dem.E();
   }

   public static void disablePolygonOffset() {
      assertThread(RenderSystem::isOnGameThread);
      dem.F();
   }

   public static void enableLineOffset() {
      assertThread(RenderSystem::isOnGameThread);
      dem.G();
   }

   public static void disableLineOffset() {
      assertThread(RenderSystem::isOnGameThread);
      dem.H();
   }

   public static void polygonOffset(float var0, float var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman);
   }

   public static void enableColorLogicOp() {
      assertThread(RenderSystem::isOnGameThread);
      dem.I();
   }

   public static void disableColorLogicOp() {
      assertThread(RenderSystem::isOnGameThread);
      dem.J();
   }

   public static void logicOp(dem.o var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.p(_snowman.q);
   }

   public static void activeTexture(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.q(_snowman);
   }

   public static void enableTexture() {
      assertThread(RenderSystem::isOnGameThread);
      dem.K();
   }

   public static void disableTexture() {
      assertThread(RenderSystem::isOnGameThread);
      dem.L();
   }

   public static void texParameter(int var0, int var1, int var2) {
      dem.b(_snowman, _snowman, _snowman);
   }

   public static void deleteTexture(int var0) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.r(_snowman);
   }

   public static void bindTexture(int var0) {
      dem.s(_snowman);
   }

   @Deprecated
   public static void shadeModel(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.t(_snowman);
   }

   @Deprecated
   public static void enableRescaleNormal() {
      assertThread(RenderSystem::isOnGameThread);
      dem.N();
   }

   @Deprecated
   public static void disableRescaleNormal() {
      assertThread(RenderSystem::isOnGameThread);
      dem.O();
   }

   public static void viewport(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.d(_snowman, _snowman, _snowman, _snowman);
   }

   public static void colorMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman, _snowman);
   }

   public static void stencilFunc(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman, _snowman, _snowman);
   }

   public static void stencilMask(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.u(_snowman);
   }

   public static void stencilOp(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.e(_snowman, _snowman, _snowman);
   }

   public static void clearDepth(double var0) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.a(_snowman);
   }

   public static void clearColor(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.b(_snowman, _snowman, _snowman, _snowman);
   }

   public static void clearStencil(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.v(_snowman);
   }

   public static void clear(int var0, boolean var1) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.a(_snowman, _snowman);
   }

   @Deprecated
   public static void matrixMode(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.w(_snowman);
   }

   @Deprecated
   public static void loadIdentity() {
      assertThread(RenderSystem::isOnGameThread);
      dem.P();
   }

   @Deprecated
   public static void pushMatrix() {
      assertThread(RenderSystem::isOnGameThread);
      dem.Q();
   }

   @Deprecated
   public static void popMatrix() {
      assertThread(RenderSystem::isOnGameThread);
      dem.R();
   }

   @Deprecated
   public static void ortho(double var0, double var2, double var4, double var6, double var8, double var10) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void rotatef(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void scalef(float var0, float var1, float var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void scaled(double var0, double var2, double var4) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void translatef(float var0, float var1, float var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void translated(double var0, double var2, double var4) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void multMatrix(b var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman);
   }

   @Deprecated
   public static void color4f(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void color3f(float var0, float var1, float var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman, _snowman, _snowman, 1.0F);
   }

   @Deprecated
   public static void clearCurrentColor() {
      assertThread(RenderSystem::isOnGameThread);
      dem.S();
   }

   public static void drawArrays(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.f(_snowman, _snowman, _snowman);
   }

   public static void lineWidth(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman);
   }

   public static void pixelStore(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      dem.n(_snowman, _snowman);
   }

   public static void pixelTransfer(int var0, float var1) {
      dem.b(_snowman, _snowman);
   }

   public static void readPixels(int var0, int var1, int var2, int var3, int var4, int var5, ByteBuffer var6) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void getString(int var0, Consumer<String> var1) {
      assertThread(RenderSystem::isOnGameThread);
      _snowman.accept(dem.B(_snowman));
   }

   public static String getBackendDescription() {
      assertThread(RenderSystem::isInInitPhase);
      return String.format("LWJGL version %s", GLX._getLWJGLVersion());
   }

   public static String getApiDescription() {
      assertThread(RenderSystem::isInInitPhase);
      return GLX.getOpenGLVersionString();
   }

   public static LongSupplier initBackendSystem() {
      assertThread(RenderSystem::isInInitPhase);
      return GLX._initGlfw();
   }

   public static void initRenderer(int var0, boolean var1) {
      assertThread(RenderSystem::isInInitPhase);
      GLX._init(_snowman, _snowman);
   }

   public static void setErrorCallback(GLFWErrorCallbackI var0) {
      assertThread(RenderSystem::isInInitPhase);
      GLX._setGlfwErrorCallback(_snowman);
   }

   public static void renderCrosshair(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GLX._renderCrosshair(_snowman, true, true, true);
   }

   public static void setupNvFogDistance() {
      assertThread(RenderSystem::isOnGameThread);
      GLX._setupNvFogDistance();
   }

   @Deprecated
   public static void glMultiTexCoord2f(int var0, float var1, float var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman);
   }

   public static String getCapsString() {
      assertThread(RenderSystem::isOnGameThread);
      return GLX._getCapsString();
   }

   public static void setupDefaultState(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isInInitPhase);
      dem.K();
      dem.t(7425);
      dem.a(1.0);
      dem.m();
      dem.b(515);
      dem.e();
      dem.a(516, 0.1F);
      dem.w(5889);
      dem.P();
      dem.w(5888);
      dem.d(_snowman, _snowman, _snowman, _snowman);
   }

   public static int maxSupportedTextureSize() {
      assertThread(RenderSystem::isInInitPhase);
      if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
         int _snowman = dem.C(3379);

         for (int _snowmanx = Math.max(32768, _snowman); _snowmanx >= 1024; _snowmanx >>= 1) {
            dem.a(32868, 0, 6408, _snowmanx, _snowmanx, 0, 6408, 5121, null);
            int _snowmanxx = dem.c(32868, 0, 4096);
            if (_snowmanxx != 0) {
               MAX_SUPPORTED_TEXTURE_SIZE = _snowmanx;
               return _snowmanx;
            }
         }

         MAX_SUPPORTED_TEXTURE_SIZE = Math.max(_snowman, 1024);
         LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", MAX_SUPPORTED_TEXTURE_SIZE);
      }

      return MAX_SUPPORTED_TEXTURE_SIZE;
   }

   public static void glBindBuffer(int var0, Supplier<Integer> var1) {
      dem.g(_snowman, _snowman.get());
   }

   public static void glBufferData(int var0, ByteBuffer var1, int var2) {
      assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.a(_snowman, _snowman, _snowman);
   }

   public static void glDeleteBuffers(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      dem.j(_snowman);
   }

   public static void glUniform1i(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.f(_snowman, _snowman);
   }

   public static void glUniform1(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman);
   }

   public static void glUniform2(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman);
   }

   public static void glUniform3(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman, _snowman);
   }

   public static void glUniform4(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman, _snowman);
   }

   public static void glUniform1(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman);
   }

   public static void glUniform2(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman, _snowman);
   }

   public static void glUniform3(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.d(_snowman, _snowman);
   }

   public static void glUniform4(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.e(_snowman, _snowman);
   }

   public static void glUniformMatrix2(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman);
   }

   public static void glUniformMatrix3(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman, _snowman);
   }

   public static void glUniformMatrix4(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.c(_snowman, _snowman, _snowman);
   }

   public static void setupOutline() {
      assertThread(RenderSystem::isOnGameThread);
      dem.u();
   }

   public static void teardownOutline() {
      assertThread(RenderSystem::isOnGameThread);
      dem.v();
   }

   public static void setupOverlayColor(IntSupplier var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.k(_snowman.getAsInt(), _snowman);
   }

   public static void teardownOverlayColor() {
      assertThread(RenderSystem::isOnGameThread);
      dem.w();
   }

   public static void setupLevelDiffuseLighting(g var0, g var1, b var2) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman, _snowman);
   }

   public static void setupGuiFlatDiffuseLighting(g var0, g var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.a(_snowman, _snowman);
   }

   public static void setupGui3DDiffuseLighting(g var0, g var1) {
      assertThread(RenderSystem::isOnGameThread);
      dem.b(_snowman, _snowman);
   }

   public static void mulTextureByProjModelView() {
      assertThread(RenderSystem::isOnGameThread);
      dem.z();
   }

   public static void setupEndPortalTexGen() {
      assertThread(RenderSystem::isOnGameThread);
      dem.x();
   }

   public static void clearTexGen() {
      assertThread(RenderSystem::isOnGameThread);
      dem.y();
   }

   public static void beginInitialization() {
      isInInit = true;
   }

   public static void finishInitialization() {
      isInInit = false;
      if (!recordingQueue.isEmpty()) {
         replayQueue();
      }

      if (!recordingQueue.isEmpty()) {
         throw new IllegalStateException("Recorded to render queue during initialization");
      }
   }

   public static void glGenBuffers(Consumer<Integer> var0) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _snowman.accept(dem.q()));
      } else {
         _snowman.accept(dem.q());
      }
   }

   public static dfo renderThreadTesselator() {
      assertThread(RenderSystem::isOnRenderThread);
      return RENDER_THREAD_TESSELATOR;
   }

   public static void defaultBlendFunc() {
      blendFuncSeparate(dem.r.l, dem.j.j, dem.r.e, dem.j.n);
   }

   public static void defaultAlphaFunc() {
      alphaFunc(516, 0.1F);
   }

   @Deprecated
   public static void runAsFancy(Runnable var0) {
      boolean _snowman = djz.A();
      if (!_snowman) {
         _snowman.run();
      } else {
         dkd _snowmanx = djz.C().k;
         djt _snowmanxx = _snowmanx.f;
         _snowmanx.f = djt.b;
         _snowman.run();
         _snowmanx.f = _snowmanxx;
      }
   }
}
