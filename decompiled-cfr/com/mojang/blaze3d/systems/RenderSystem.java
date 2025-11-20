/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 */
package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderCall;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class RenderSystem {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConcurrentLinkedQueue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
    private static final Tessellator RENDER_THREAD_TESSELATOR = new Tessellator();
    public static final float DEFAULTALPHACUTOFF = 0.1f;
    private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
    private static boolean isReplayingQueue;
    private static Thread gameThread;
    private static Thread renderThread;
    private static int MAX_SUPPORTED_TEXTURE_SIZE;
    private static boolean isInInit;
    private static double lastDrawTime;

    public static void initRenderThread() {
        if (renderThread != null || gameThread == Thread.currentThread()) {
            throw new IllegalStateException("Could not initialize render thread");
        }
        renderThread = Thread.currentThread();
    }

    public static boolean isOnRenderThread() {
        return Thread.currentThread() == renderThread;
    }

    public static boolean isOnRenderThreadOrInit() {
        return isInInit || RenderSystem.isOnRenderThread();
    }

    public static void initGameThread(boolean assertNotRenderThread) {
        boolean bl;
        boolean bl2 = bl = renderThread == Thread.currentThread();
        if (gameThread != null || renderThread == null || bl == assertNotRenderThread) {
            throw new IllegalStateException("Could not initialize tick thread");
        }
        gameThread = Thread.currentThread();
    }

    public static boolean isOnGameThread() {
        return true;
    }

    public static boolean isOnGameThreadOrInit() {
        return isInInit || RenderSystem.isOnGameThread();
    }

    public static void assertThread(Supplier<Boolean> check) {
        if (!check.get().booleanValue()) {
            throw new IllegalStateException("Rendersystem called from wrong thread");
        }
    }

    public static boolean isInInitPhase() {
        return true;
    }

    public static void recordRenderCall(RenderCall renderCall) {
        recordingQueue.add(renderCall);
    }

    public static void flipFrame(long window) {
        GLFW.glfwPollEvents();
        RenderSystem.replayQueue();
        Tessellator.getInstance().getBuffer().clear();
        GLFW.glfwSwapBuffers((long)window);
        GLFW.glfwPollEvents();
    }

    public static void replayQueue() {
        isReplayingQueue = true;
        while (!recordingQueue.isEmpty()) {
            RenderCall renderCall = recordingQueue.poll();
            renderCall.execute();
        }
        isReplayingQueue = false;
    }

    public static void limitDisplayFPS(int fps) {
        double d = lastDrawTime + 1.0 / (double)fps;
        _snowman = GLFW.glfwGetTime();
        while (_snowman < d) {
            GLFW.glfwWaitEventsTimeout((double)(d - _snowman));
            _snowman = GLFW.glfwGetTime();
        }
        lastDrawTime = _snowman;
    }

    @Deprecated
    public static void pushLightingAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushLightingAttributes();
    }

    @Deprecated
    public static void pushTextureAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushTextureAttributes();
    }

    @Deprecated
    public static void popAttributes() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.popAttributes();
    }

    @Deprecated
    public static void disableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableAlphaTest();
    }

    @Deprecated
    public static void enableAlphaTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableAlphaTest();
    }

    @Deprecated
    public static void alphaFunc(int func, float ref) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.alphaFunc(func, ref);
    }

    @Deprecated
    public static void enableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableLighting();
    }

    @Deprecated
    public static void disableLighting() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableLighting();
    }

    @Deprecated
    public static void enableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableColorMaterial();
    }

    @Deprecated
    public static void disableColorMaterial() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableColorMaterial();
    }

    @Deprecated
    public static void colorMaterial(int face, int mode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.colorMaterial(face, mode);
    }

    @Deprecated
    public static void normal3f(float nx, float ny, float nz) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.normal3f(nx, ny, nz);
    }

    public static void disableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableDepthTest();
    }

    public static void enableDepthTest() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.enableDepthTest();
    }

    public static void enableScissor(int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.method_31319();
        GlStateManager.method_31317(n, n2, n3, n4);
    }

    public static void disableScissor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.method_31318();
    }

    public static void depthFunc(int func) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.depthFunc(func);
    }

    public static void depthMask(boolean mask) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.depthMask(mask);
    }

    public static void enableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableBlend();
    }

    public static void disableBlend() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableBlend();
    }

    public static void blendFunc(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFunc(srcFactor.field_22545, dstFactor.field_22528);
    }

    public static void blendFunc(int srcFactor, int dstFactor) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFunc(srcFactor, dstFactor);
    }

    public static void blendFuncSeparate(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor, GlStateManager.SrcFactor srcAlpha, GlStateManager.DstFactor dstAlpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFuncSeparate(srcFactor.field_22545, dstFactor.field_22528, srcAlpha.field_22545, dstAlpha.field_22528);
    }

    public static void blendFuncSeparate(int srcFactorRGB, int dstFactorRGB, int srcFactorAlpha, int dstFactorAlpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
    }

    public static void blendEquation(int mode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendEquation(mode);
    }

    public static void blendColor(float red, float green, float blue, float alpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.blendColor(red, green, blue, alpha);
    }

    @Deprecated
    public static void enableFog() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableFog();
    }

    @Deprecated
    public static void disableFog() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableFog();
    }

    @Deprecated
    public static void fogMode(GlStateManager.FogMode mode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogMode(mode.value);
    }

    @Deprecated
    public static void fogMode(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogMode(n);
    }

    @Deprecated
    public static void fogDensity(float density) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogDensity(density);
    }

    @Deprecated
    public static void fogStart(float start) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogStart(start);
    }

    @Deprecated
    public static void fogEnd(float end) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogEnd(end);
    }

    @Deprecated
    public static void fog(int pname, float red, float green, float blue, float alpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fog(pname, new float[]{red, green, blue, alpha});
    }

    @Deprecated
    public static void fogi(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.fogi(n, n2);
    }

    public static void enableCull() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableCull();
    }

    public static void disableCull() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableCull();
    }

    public static void polygonMode(int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.polygonMode(n, n2);
    }

    public static void enablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enablePolygonOffset();
    }

    public static void disablePolygonOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disablePolygonOffset();
    }

    public static void enableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableLineOffset();
    }

    public static void disableLineOffset() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableLineOffset();
    }

    public static void polygonOffset(float factor, float units) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.polygonOffset(factor, units);
    }

    public static void enableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableColorLogicOp();
    }

    public static void disableColorLogicOp() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableColorLogicOp();
    }

    public static void logicOp(GlStateManager.LogicOp op) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.logicOp(op.value);
    }

    public static void activeTexture(int texture) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.activeTexture(texture);
    }

    public static void enableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableTexture();
    }

    public static void disableTexture() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableTexture();
    }

    public static void texParameter(int target, int pname, int param) {
        GlStateManager.texParameter(target, pname, param);
    }

    public static void deleteTexture(int texture) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.deleteTexture(texture);
    }

    public static void bindTexture(int texture) {
        GlStateManager.bindTexture(texture);
    }

    @Deprecated
    public static void shadeModel(int mode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.shadeModel(mode);
    }

    @Deprecated
    public static void enableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.enableRescaleNormal();
    }

    @Deprecated
    public static void disableRescaleNormal() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.disableRescaleNormal();
    }

    public static void viewport(int x, int y, int width, int height) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.viewport(x, y, width, height);
    }

    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.colorMask(red, green, blue, alpha);
    }

    public static void stencilFunc(int func, int ref, int mask) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilFunc(func, ref, mask);
    }

    public static void stencilMask(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilMask(n);
    }

    public static void stencilOp(int sfail, int dpfail, int dppass) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.stencilOp(sfail, dpfail, dppass);
    }

    public static void clearDepth(double depth) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clearDepth(depth);
    }

    public static void clearColor(float red, float green, float blue, float alpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clearColor(red, green, blue, alpha);
    }

    public static void clearStencil(int n) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearStencil(n);
    }

    public static void clear(int mask, boolean getError) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.clear(mask, getError);
    }

    @Deprecated
    public static void matrixMode(int mode) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.matrixMode(mode);
    }

    @Deprecated
    public static void loadIdentity() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.loadIdentity();
    }

    @Deprecated
    public static void pushMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.pushMatrix();
    }

    @Deprecated
    public static void popMatrix() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.popMatrix();
    }

    @Deprecated
    public static void ortho(double l, double r, double b, double t, double n, double f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.ortho(l, r, b, t, n, f);
    }

    @Deprecated
    public static void rotatef(float angle, float x, float y, float z) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.rotatef(angle, x, y, z);
    }

    @Deprecated
    public static void scalef(float x, float y, float z) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.scalef(x, y, z);
    }

    @Deprecated
    public static void scaled(double x, double y, double z) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.scaled(x, y, z);
    }

    @Deprecated
    public static void translatef(float x, float y, float z) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.translatef(x, y, z);
    }

    @Deprecated
    public static void translated(double x, double y, double z) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.translated(x, y, z);
    }

    @Deprecated
    public static void multMatrix(Matrix4f matrix) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.multMatrix(matrix);
    }

    @Deprecated
    public static void color4f(float red, float green, float blue, float alpha) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.color4f(red, green, blue, alpha);
    }

    @Deprecated
    public static void color3f(float red, float green, float blue) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.color4f(red, green, blue, 1.0f);
    }

    @Deprecated
    public static void clearCurrentColor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearCurrentColor();
    }

    public static void drawArrays(int mode, int first, int count) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.drawArrays(mode, first, count);
    }

    public static void lineWidth(float width) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.lineWidth(width);
    }

    public static void pixelStore(int pname, int param) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        GlStateManager.pixelStore(pname, param);
    }

    public static void pixelTransfer(int n, float f) {
        GlStateManager.pixelTransfer(n, f);
    }

    public static void readPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.readPixels(x, y, width, height, format, type, pixels);
    }

    public static void getString(int name, Consumer<String> consumer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        consumer.accept(GlStateManager.getString(name));
    }

    public static String getBackendDescription() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return String.format("LWJGL version %s", GLX._getLWJGLVersion());
    }

    public static String getApiDescription() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return GLX.getOpenGLVersionString();
    }

    public static LongSupplier initBackendSystem() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return GLX._initGlfw();
    }

    public static void initRenderer(int debugVerbosity, boolean debugSync) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLX._init(debugVerbosity, debugSync);
    }

    public static void setErrorCallback(GLFWErrorCallbackI callback) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLX._setGlfwErrorCallback(callback);
    }

    public static void renderCrosshair(int size) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GLX._renderCrosshair(size, true, true, true);
    }

    public static void setupNvFogDistance() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GLX._setupNvFogDistance();
    }

    @Deprecated
    public static void glMultiTexCoord2f(int texture, float s, float t) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.multiTexCoords2f(texture, s, t);
    }

    public static String getCapsString() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        return GLX._getCapsString();
    }

    public static void setupDefaultState(int x, int y, int width, int height) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GlStateManager.enableTexture();
        GlStateManager.shadeModel(7425);
        GlStateManager.clearDepth(1.0);
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.viewport(x, y, width, height);
    }

    public static int maxSupportedTextureSize() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
            int n = GlStateManager.getInteger(3379);
            for (_snowman = Math.max(32768, n); _snowman >= 1024; _snowman >>= 1) {
                GlStateManager.texImage2D(32868, 0, 6408, _snowman, _snowman, 0, 6408, 5121, null);
                _snowman = GlStateManager.getTexLevelParameter(32868, 0, 4096);
                if (_snowman == 0) continue;
                MAX_SUPPORTED_TEXTURE_SIZE = _snowman;
                return _snowman;
            }
            MAX_SUPPORTED_TEXTURE_SIZE = Math.max(n, 1024);
            LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", (Object)MAX_SUPPORTED_TEXTURE_SIZE);
        }
        return MAX_SUPPORTED_TEXTURE_SIZE;
    }

    public static void glBindBuffer(int target, Supplier<Integer> buffer) {
        GlStateManager.bindBuffers(target, buffer.get());
    }

    public static void glBufferData(int target, ByteBuffer data, int usage) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.bufferData(target, data, usage);
    }

    public static void glDeleteBuffers(int buffer) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.deleteBuffers(buffer);
    }

    public static void glUniform1i(int location, int value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1(location, value);
    }

    public static void glUniform1(int location, IntBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1(location, value);
    }

    public static void glUniform2(int location, IntBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform2(location, value);
    }

    public static void glUniform3(int location, IntBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform3(location, value);
    }

    public static void glUniform4(int location, IntBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform4(location, value);
    }

    public static void glUniform1(int location, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform1(location, value);
    }

    public static void glUniform2(int location, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform2(location, value);
    }

    public static void glUniform3(int location, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform3(location, value);
    }

    public static void glUniform4(int location, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniform4(location, value);
    }

    public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix2(location, transpose, value);
    }

    public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix3(location, transpose, value);
    }

    public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer value) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.uniformMatrix4(location, transpose, value);
    }

    public static void setupOutline() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupOutline();
    }

    public static void teardownOutline() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.teardownOutline();
    }

    public static void setupOverlayColor(IntSupplier texture, int size) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupOverlayColor(texture.getAsInt(), size);
    }

    public static void teardownOverlayColor() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.teardownOverlayColor();
    }

    public static void setupLevelDiffuseLighting(Vector3f vector3f, Vector3f vector3f2, Matrix4f matrix4f) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupLevelDiffuseLighting(vector3f, vector3f2, matrix4f);
    }

    public static void setupGuiFlatDiffuseLighting(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupGuiFlatDiffuseLighting(vector3f, vector3f2);
    }

    public static void setupGui3DDiffuseLighting(Vector3f vector3f, Vector3f vector3f2) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupGui3dDiffuseLighting(vector3f, vector3f2);
    }

    public static void mulTextureByProjModelView() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.mulTextureByProjModelView();
    }

    public static void setupEndPortalTexGen() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.setupEndPortalTexGen();
    }

    public static void clearTexGen() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlStateManager.clearTexGen();
    }

    public static void beginInitialization() {
        isInInit = true;
    }

    public static void finishInitialization() {
        isInInit = false;
        if (!recordingQueue.isEmpty()) {
            RenderSystem.replayQueue();
        }
        if (!recordingQueue.isEmpty()) {
            throw new IllegalStateException("Recorded to render queue during initialization");
        }
    }

    public static void glGenBuffers(Consumer<Integer> consumer) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> consumer.accept(GlStateManager.genBuffers()));
        } else {
            consumer.accept(GlStateManager.genBuffers());
        }
    }

    public static Tessellator renderThreadTesselator() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return RENDER_THREAD_TESSELATOR;
    }

    public static void defaultBlendFunc() {
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
    }

    public static void defaultAlphaFunc() {
        RenderSystem.alphaFunc(516, 0.1f);
    }

    @Deprecated
    public static void runAsFancy(Runnable runnable) {
        boolean bl = MinecraftClient.isFabulousGraphicsOrBetter();
        if (!bl) {
            runnable.run();
            return;
        }
        GameOptions _snowman2 = MinecraftClient.getInstance().options;
        GraphicsMode _snowman3 = _snowman2.graphicsMode;
        _snowman2.graphicsMode = GraphicsMode.FANCY;
        runnable.run();
        _snowman2.graphicsMode = _snowman3;
    }

    private static /* synthetic */ void lambda$setupGui3DDiffuseLighting$71(Vector3f vector3f, Vector3f vector3f2) {
        GlStateManager.setupGui3dDiffuseLighting(vector3f, vector3f2);
    }

    private static /* synthetic */ void lambda$setupGuiFlatDiffuseLighting$70(Vector3f vector3f, Vector3f vector3f2) {
        GlStateManager.setupGuiFlatDiffuseLighting(vector3f, vector3f2);
    }

    private static /* synthetic */ void lambda$setupLevelDiffuseLighting$69(Vector3f vector3f, Vector3f vector3f2, Matrix4f matrix4f) {
        GlStateManager.setupLevelDiffuseLighting(vector3f, vector3f2, matrix4f);
    }

    private static /* synthetic */ void lambda$setupOverlayColor$68(IntSupplier intSupplier, int n) {
        GlStateManager.setupOverlayColor(intSupplier.getAsInt(), n);
    }

    private static /* synthetic */ void lambda$glUniformMatrix4$67(int n, boolean bl, FloatBuffer floatBuffer) {
        GlStateManager.uniformMatrix4(n, bl, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniformMatrix3$66(int n, boolean bl, FloatBuffer floatBuffer) {
        GlStateManager.uniformMatrix3(n, bl, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniformMatrix2$65(int n, boolean bl, FloatBuffer floatBuffer) {
        GlStateManager.uniformMatrix2(n, bl, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniform4$64(int n, FloatBuffer floatBuffer) {
        GlStateManager.uniform4(n, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniform3$63(int n, FloatBuffer floatBuffer) {
        GlStateManager.uniform3(n, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniform2$62(int n, FloatBuffer floatBuffer) {
        GlStateManager.uniform2(n, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniform1$61(int n, FloatBuffer floatBuffer) {
        GlStateManager.uniform1(n, floatBuffer);
    }

    private static /* synthetic */ void lambda$glUniform4$60(int n, IntBuffer intBuffer) {
        GlStateManager.uniform4(n, intBuffer);
    }

    private static /* synthetic */ void lambda$glUniform3$59(int n, IntBuffer intBuffer) {
        GlStateManager.uniform3(n, intBuffer);
    }

    private static /* synthetic */ void lambda$glUniform2$58(int n, IntBuffer intBuffer) {
        GlStateManager.uniform2(n, intBuffer);
    }

    private static /* synthetic */ void lambda$glUniform1$57(int n, IntBuffer intBuffer) {
        GlStateManager.uniform1(n, intBuffer);
    }

    private static /* synthetic */ void lambda$glUniform1i$56(int n, int n2) {
        GlStateManager.uniform1(n, n2);
    }

    private static /* synthetic */ void lambda$glDeleteBuffers$55(int n) {
        GlStateManager.deleteBuffers(n);
    }

    private static /* synthetic */ void lambda$glBindBuffer$54(int n, Supplier supplier) {
        GlStateManager.bindBuffers(n, (Integer)supplier.get());
    }

    private static /* synthetic */ void lambda$glMultiTexCoord2f$53(int n, float f, float f2) {
        GlStateManager.multiTexCoords2f(n, f, f2);
    }

    private static /* synthetic */ void lambda$renderCrosshair$52(int n) {
        GLX._renderCrosshair(n, true, true, true);
    }

    private static /* synthetic */ void lambda$getString$51(int n, Consumer consumer) {
        String string = GlStateManager.getString(n);
        consumer.accept(string);
    }

    private static /* synthetic */ void lambda$readPixels$50(int n, int n2, int n3, int n4, int n5, int n6, ByteBuffer byteBuffer) {
        GlStateManager.readPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    private static /* synthetic */ void lambda$pixelTransfer$49(int n, float f) {
        GlStateManager.pixelTransfer(n, f);
    }

    private static /* synthetic */ void lambda$pixelStore$48(int n, int n2) {
        GlStateManager.pixelStore(n, n2);
    }

    private static /* synthetic */ void lambda$lineWidth$47(float f) {
        GlStateManager.lineWidth(f);
    }

    private static /* synthetic */ void lambda$drawArrays$46(int n, int n2, int n3) {
        GlStateManager.drawArrays(n, n2, n3);
    }

    private static /* synthetic */ void lambda$color3f$45(float f, float f2, float f3) {
        GlStateManager.color4f(f, f2, f3, 1.0f);
    }

    private static /* synthetic */ void lambda$color4f$44(float f, float f2, float f3, float f4) {
        GlStateManager.color4f(f, f2, f3, f4);
    }

    private static /* synthetic */ void lambda$multMatrix$43(Matrix4f matrix4f) {
        GlStateManager.multMatrix(matrix4f);
    }

    private static /* synthetic */ void lambda$translated$42(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, d3);
    }

    private static /* synthetic */ void lambda$translatef$41(float f, float f2, float f3) {
        GlStateManager.translatef(f, f2, f3);
    }

    private static /* synthetic */ void lambda$scaled$40(double d, double d2, double d3) {
        GlStateManager.scaled(d, d2, d3);
    }

    private static /* synthetic */ void lambda$scalef$39(float f, float f2, float f3) {
        GlStateManager.scalef(f, f2, f3);
    }

    private static /* synthetic */ void lambda$rotatef$38(float f, float f2, float f3, float f4) {
        GlStateManager.rotatef(f, f2, f3, f4);
    }

    private static /* synthetic */ void lambda$ortho$37(double d, double d2, double d3, double d4, double d5, double d6) {
        GlStateManager.ortho(d, d2, d3, d4, d5, d6);
    }

    private static /* synthetic */ void lambda$matrixMode$36(int n) {
        GlStateManager.matrixMode(n);
    }

    private static /* synthetic */ void lambda$clear$35(int n, boolean bl) {
        GlStateManager.clear(n, bl);
    }

    private static /* synthetic */ void lambda$clearStencil$34(int n) {
        GlStateManager.clearStencil(n);
    }

    private static /* synthetic */ void lambda$clearColor$33(float f, float f2, float f3, float f4) {
        GlStateManager.clearColor(f, f2, f3, f4);
    }

    private static /* synthetic */ void lambda$clearDepth$32(double d) {
        GlStateManager.clearDepth(d);
    }

    private static /* synthetic */ void lambda$stencilOp$31(int n, int n2, int n3) {
        GlStateManager.stencilOp(n, n2, n3);
    }

    private static /* synthetic */ void lambda$stencilMask$30(int n) {
        GlStateManager.stencilMask(n);
    }

    private static /* synthetic */ void lambda$stencilFunc$29(int n, int n2, int n3) {
        GlStateManager.stencilFunc(n, n2, n3);
    }

    private static /* synthetic */ void lambda$colorMask$28(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        GlStateManager.colorMask(bl, bl2, bl3, bl4);
    }

    private static /* synthetic */ void lambda$viewport$27(int n, int n2, int n3, int n4) {
        GlStateManager.viewport(n, n2, n3, n4);
    }

    private static /* synthetic */ void lambda$shadeModel$26(int n) {
        GlStateManager.shadeModel(n);
    }

    private static /* synthetic */ void lambda$bindTexture$25(int n) {
        GlStateManager.bindTexture(n);
    }

    private static /* synthetic */ void lambda$deleteTexture$24(int n) {
        GlStateManager.deleteTexture(n);
    }

    private static /* synthetic */ void lambda$texParameter$23(int n, int n2, int n3) {
        GlStateManager.texParameter(n, n2, n3);
    }

    private static /* synthetic */ void lambda$activeTexture$22(int n) {
        GlStateManager.activeTexture(n);
    }

    private static /* synthetic */ void lambda$logicOp$21(GlStateManager.LogicOp logicOp) {
        GlStateManager.logicOp(logicOp.value);
    }

    private static /* synthetic */ void lambda$polygonOffset$20(float f, float f2) {
        GlStateManager.polygonOffset(f, f2);
    }

    private static /* synthetic */ void lambda$polygonMode$19(int n, int n2) {
        GlStateManager.polygonMode(n, n2);
    }

    private static /* synthetic */ void lambda$fogi$18(int n, int n2) {
        GlStateManager.fogi(n, n2);
    }

    private static /* synthetic */ void lambda$fog$17(int n, float f, float f2, float f3, float f4) {
        GlStateManager.fog(n, new float[]{f, f2, f3, f4});
    }

    private static /* synthetic */ void lambda$fogEnd$16(float f) {
        GlStateManager.fogEnd(f);
    }

    private static /* synthetic */ void lambda$fogStart$15(float f) {
        GlStateManager.fogStart(f);
    }

    private static /* synthetic */ void lambda$fogDensity$14(float f) {
        GlStateManager.fogDensity(f);
    }

    private static /* synthetic */ void lambda$fogMode$13(int n) {
        GlStateManager.fogMode(n);
    }

    private static /* synthetic */ void lambda$fogMode$12(GlStateManager.FogMode fogMode) {
        GlStateManager.fogMode(fogMode.value);
    }

    private static /* synthetic */ void lambda$blendColor$11(float f, float f2, float f3, float f4) {
        GlStateManager.blendColor(f, f2, f3, f4);
    }

    private static /* synthetic */ void lambda$blendEquation$10(int n) {
        GlStateManager.blendEquation(n);
    }

    private static /* synthetic */ void lambda$blendFuncSeparate$9(int n, int n2, int n3, int n4) {
        GlStateManager.blendFuncSeparate(n, n2, n3, n4);
    }

    private static /* synthetic */ void lambda$blendFuncSeparate$8(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor, GlStateManager.SrcFactor srcFactor2, GlStateManager.DstFactor dstFactor2) {
        GlStateManager.blendFuncSeparate(srcFactor.field_22545, dstFactor.field_22528, srcFactor2.field_22545, dstFactor2.field_22528);
    }

    private static /* synthetic */ void lambda$blendFunc$7(int n, int n2) {
        GlStateManager.blendFunc(n, n2);
    }

    private static /* synthetic */ void lambda$blendFunc$6(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor) {
        GlStateManager.blendFunc(srcFactor.field_22545, dstFactor.field_22528);
    }

    private static /* synthetic */ void lambda$depthMask$5(boolean bl) {
        GlStateManager.depthMask(bl);
    }

    private static /* synthetic */ void lambda$depthFunc$4(int n) {
        GlStateManager.depthFunc(n);
    }

    private static /* synthetic */ void lambda$enableScissor$3(int n, int n2, int n3, int n4) {
        GlStateManager.method_31319();
        GlStateManager.method_31317(n, n2, n3, n4);
    }

    private static /* synthetic */ void lambda$normal3f$2(float f, float f2, float f3) {
        GlStateManager.normal3f(f, f2, f3);
    }

    private static /* synthetic */ void lambda$colorMaterial$1(int n, int n2) {
        GlStateManager.colorMaterial(n, n2);
    }

    private static /* synthetic */ void lambda$alphaFunc$0(int n, float f) {
        GlStateManager.alphaFunc(n, f);
    }

    static {
        MAX_SUPPORTED_TEXTURE_SIZE = -1;
        lastDrawTime = Double.MIN_VALUE;
    }
}

