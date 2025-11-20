/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRenderer
implements SynchronousResourceReloadListener,
AutoCloseable {
    private static final Identifier field_26730 = new Identifier("textures/misc/nausea.png");
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftClient client;
    private final ResourceManager resourceContainer;
    private final Random random = new Random();
    private float viewDistance;
    public final HeldItemRenderer firstPersonRenderer;
    private final MapRenderer mapRenderer;
    private final BufferBuilderStorage buffers;
    private int ticks;
    private float movementFovMultiplier;
    private float lastMovementFovMultiplier;
    private float skyDarkness;
    private float lastSkyDarkness;
    private boolean renderHand = true;
    private boolean blockOutlineEnabled = true;
    private long lastWorldIconUpdate;
    private long lastWindowFocusedTime = Util.getMeasuringTimeMs();
    private final LightmapTextureManager lightmapTextureManager;
    private final OverlayTexture overlayTexture = new OverlayTexture();
    private boolean renderingPanorama;
    private float zoom = 1.0f;
    private float zoomX;
    private float zoomY;
    @Nullable
    private ItemStack floatingItem;
    private int floatingItemTimeLeft;
    private float floatingItemWidth;
    private float floatingItemHeight;
    @Nullable
    private ShaderEffect shader;
    private static final Identifier[] SHADERS_LOCATIONS = new Identifier[]{new Identifier("shaders/post/notch.json"), new Identifier("shaders/post/fxaa.json"), new Identifier("shaders/post/art.json"), new Identifier("shaders/post/bumpy.json"), new Identifier("shaders/post/blobs2.json"), new Identifier("shaders/post/pencil.json"), new Identifier("shaders/post/color_convolve.json"), new Identifier("shaders/post/deconverge.json"), new Identifier("shaders/post/flip.json"), new Identifier("shaders/post/invert.json"), new Identifier("shaders/post/ntsc.json"), new Identifier("shaders/post/outline.json"), new Identifier("shaders/post/phosphor.json"), new Identifier("shaders/post/scan_pincushion.json"), new Identifier("shaders/post/sobel.json"), new Identifier("shaders/post/bits.json"), new Identifier("shaders/post/desaturate.json"), new Identifier("shaders/post/green.json"), new Identifier("shaders/post/blur.json"), new Identifier("shaders/post/wobble.json"), new Identifier("shaders/post/blobs.json"), new Identifier("shaders/post/antialias.json"), new Identifier("shaders/post/creeper.json"), new Identifier("shaders/post/spider.json")};
    public static final int SHADER_COUNT = SHADERS_LOCATIONS.length;
    private int forcedShaderIndex = SHADER_COUNT;
    private boolean shadersEnabled;
    private final Camera camera = new Camera();

    public GameRenderer(MinecraftClient client, ResourceManager resourceManager, BufferBuilderStorage bufferBuilderStorage) {
        this.client = client;
        this.resourceContainer = resourceManager;
        this.firstPersonRenderer = client.getHeldItemRenderer();
        this.mapRenderer = new MapRenderer(client.getTextureManager());
        this.lightmapTextureManager = new LightmapTextureManager(this, client);
        this.buffers = bufferBuilderStorage;
        this.shader = null;
    }

    @Override
    public void close() {
        this.lightmapTextureManager.close();
        this.mapRenderer.close();
        this.overlayTexture.close();
        this.disableShader();
    }

    public void disableShader() {
        if (this.shader != null) {
            this.shader.close();
        }
        this.shader = null;
        this.forcedShaderIndex = SHADER_COUNT;
    }

    public void toggleShadersEnabled() {
        this.shadersEnabled = !this.shadersEnabled;
    }

    public void onCameraEntitySet(@Nullable Entity entity) {
        if (this.shader != null) {
            this.shader.close();
        }
        this.shader = null;
        if (entity instanceof CreeperEntity) {
            this.loadShader(new Identifier("shaders/post/creeper.json"));
        } else if (entity instanceof SpiderEntity) {
            this.loadShader(new Identifier("shaders/post/spider.json"));
        } else if (entity instanceof EndermanEntity) {
            this.loadShader(new Identifier("shaders/post/invert.json"));
        }
    }

    private void loadShader(Identifier identifier) {
        if (this.shader != null) {
            this.shader.close();
        }
        try {
            this.shader = new ShaderEffect(this.client.getTextureManager(), this.resourceContainer, this.client.getFramebuffer(), identifier);
            this.shader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
            this.shadersEnabled = true;
        }
        catch (IOException iOException) {
            LOGGER.warn("Failed to load shader: {}", (Object)identifier, (Object)iOException);
            this.forcedShaderIndex = SHADER_COUNT;
            this.shadersEnabled = false;
        }
        catch (JsonSyntaxException jsonSyntaxException) {
            LOGGER.warn("Failed to parse shader: {}", (Object)identifier, (Object)jsonSyntaxException);
            this.forcedShaderIndex = SHADER_COUNT;
            this.shadersEnabled = false;
        }
    }

    @Override
    public void apply(ResourceManager manager) {
        if (this.shader != null) {
            this.shader.close();
        }
        this.shader = null;
        if (this.forcedShaderIndex == SHADER_COUNT) {
            this.onCameraEntitySet(this.client.getCameraEntity());
        } else {
            this.loadShader(SHADERS_LOCATIONS[this.forcedShaderIndex]);
        }
    }

    public void tick() {
        this.updateMovementFovMultiplier();
        this.lightmapTextureManager.tick();
        if (this.client.getCameraEntity() == null) {
            this.client.setCameraEntity(this.client.player);
        }
        this.camera.updateEyeHeight();
        ++this.ticks;
        this.firstPersonRenderer.updateHeldItems();
        this.client.worldRenderer.tickRainSplashing(this.camera);
        this.lastSkyDarkness = this.skyDarkness;
        if (this.client.inGameHud.getBossBarHud().shouldDarkenSky()) {
            this.skyDarkness += 0.05f;
            if (this.skyDarkness > 1.0f) {
                this.skyDarkness = 1.0f;
            }
        } else if (this.skyDarkness > 0.0f) {
            this.skyDarkness -= 0.0125f;
        }
        if (this.floatingItemTimeLeft > 0) {
            --this.floatingItemTimeLeft;
            if (this.floatingItemTimeLeft == 0) {
                this.floatingItem = null;
            }
        }
    }

    @Nullable
    public ShaderEffect getShader() {
        return this.shader;
    }

    public void onResized(int n, int n2) {
        if (this.shader != null) {
            this.shader.setupDimensions(n, n2);
        }
        this.client.worldRenderer.onResized(n, n2);
    }

    public void updateTargetedEntity(float tickDelta) {
        Entity entity2 = this.client.getCameraEntity();
        if (entity2 == null) {
            return;
        }
        if (this.client.world == null) {
            return;
        }
        this.client.getProfiler().push("pick");
        this.client.targetedEntity = null;
        double _snowman2 = this.client.interactionManager.getReachDistance();
        this.client.crosshairTarget = entity2.raycast(_snowman2, tickDelta, false);
        Vec3d _snowman3 = entity2.getCameraPosVec(tickDelta);
        boolean _snowman4 = false;
        int _snowman5 = 3;
        double _snowman6 = _snowman2;
        if (this.client.interactionManager.hasExtendedReach()) {
            _snowman2 = _snowman6 = 6.0;
        } else {
            if (_snowman6 > 3.0) {
                _snowman4 = true;
            }
            _snowman2 = _snowman6;
        }
        _snowman6 *= _snowman6;
        if (this.client.crosshairTarget != null) {
            _snowman6 = this.client.crosshairTarget.getPos().squaredDistanceTo(_snowman3);
        }
        Vec3d _snowman7 = entity2.getRotationVec(1.0f);
        Vec3d _snowman8 = _snowman3.add(_snowman7.x * _snowman2, _snowman7.y * _snowman2, _snowman7.z * _snowman2);
        float _snowman9 = 1.0f;
        Box _snowman10 = entity2.getBoundingBox().stretch(_snowman7.multiply(_snowman2)).expand(1.0, 1.0, 1.0);
        EntityHitResult _snowman11 = ProjectileUtil.raycast(entity2, _snowman3, _snowman8, _snowman10, entity -> !entity.isSpectator() && entity.collides(), _snowman6);
        if (_snowman11 != null) {
            _snowman = _snowman11.getEntity();
            Vec3d vec3d = _snowman11.getPos();
            double _snowman12 = _snowman3.squaredDistanceTo(vec3d);
            if (_snowman4 && _snowman12 > 9.0) {
                this.client.crosshairTarget = BlockHitResult.createMissed(vec3d, Direction.getFacing(_snowman7.x, _snowman7.y, _snowman7.z), new BlockPos(vec3d));
            } else if (_snowman12 < _snowman6 || this.client.crosshairTarget == null) {
                this.client.crosshairTarget = _snowman11;
                if (_snowman instanceof LivingEntity || _snowman instanceof ItemFrameEntity) {
                    this.client.targetedEntity = _snowman;
                }
            }
        }
        this.client.getProfiler().pop();
    }

    private void updateMovementFovMultiplier() {
        float _snowman2 = 1.0f;
        if (this.client.getCameraEntity() instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)this.client.getCameraEntity();
            _snowman2 = abstractClientPlayerEntity.getSpeed();
        }
        this.lastMovementFovMultiplier = this.movementFovMultiplier;
        this.movementFovMultiplier += (_snowman2 - this.movementFovMultiplier) * 0.5f;
        if (this.movementFovMultiplier > 1.5f) {
            this.movementFovMultiplier = 1.5f;
        }
        if (this.movementFovMultiplier < 0.1f) {
            this.movementFovMultiplier = 0.1f;
        }
    }

    private double getFov(Camera camera, float tickDelta, boolean changingFov) {
        FluidState fluidState;
        if (this.renderingPanorama) {
            return 90.0;
        }
        double d = 70.0;
        if (changingFov) {
            d = this.client.options.fov;
            d *= (double)MathHelper.lerp(tickDelta, this.lastMovementFovMultiplier, this.movementFovMultiplier);
        }
        if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).isDead()) {
            float f = Math.min((float)((LivingEntity)camera.getFocusedEntity()).deathTime + tickDelta, 20.0f);
            d /= (double)((1.0f - 500.0f / (f + 500.0f)) * 2.0f + 1.0f);
        }
        if (!(fluidState = camera.getSubmergedFluidState()).isEmpty()) {
            d = d * 60.0 / 70.0;
        }
        return d;
    }

    private void bobViewWhenHurt(MatrixStack matrixStack, float f) {
        if (this.client.getCameraEntity() instanceof LivingEntity) {
            float _snowman3;
            LivingEntity livingEntity = (LivingEntity)this.client.getCameraEntity();
            float _snowman2 = (float)livingEntity.hurtTime - f;
            if (livingEntity.isDead()) {
                _snowman3 = Math.min((float)livingEntity.deathTime + f, 20.0f);
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(40.0f - 8000.0f / (_snowman3 + 200.0f)));
            }
            if (_snowman2 < 0.0f) {
                return;
            }
            _snowman2 /= (float)livingEntity.maxHurtTime;
            _snowman2 = MathHelper.sin(_snowman2 * _snowman2 * _snowman2 * _snowman2 * (float)Math.PI);
            _snowman3 = livingEntity.knockbackVelocity;
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowman3));
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowman2 * 14.0f));
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman3));
        }
    }

    private void bobView(MatrixStack matrixStack, float f) {
        if (!(this.client.getCameraEntity() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)this.client.getCameraEntity();
        float _snowman2 = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
        float _snowman3 = -(playerEntity.horizontalSpeed + _snowman2 * f);
        float _snowman4 = MathHelper.lerp(f, playerEntity.prevStrideDistance, playerEntity.strideDistance);
        matrixStack.translate(MathHelper.sin(_snowman3 * (float)Math.PI) * _snowman4 * 0.5f, -Math.abs(MathHelper.cos(_snowman3 * (float)Math.PI) * _snowman4), 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(_snowman3 * (float)Math.PI) * _snowman4 * 3.0f));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(Math.abs(MathHelper.cos(_snowman3 * (float)Math.PI - 0.2f) * _snowman4) * 5.0f));
    }

    private void renderHand(MatrixStack matrices, Camera camera, float tickDelta) {
        if (this.renderingPanorama) {
            return;
        }
        this.loadProjectionMatrix(this.getBasicProjectionMatrix(camera, tickDelta, false));
        MatrixStack.Entry entry = matrices.peek();
        entry.getModel().loadIdentity();
        entry.getNormal().loadIdentity();
        matrices.push();
        this.bobViewWhenHurt(matrices, tickDelta);
        if (this.client.options.bobView) {
            this.bobView(matrices, tickDelta);
        }
        boolean bl = _snowman = this.client.getCameraEntity() instanceof LivingEntity && ((LivingEntity)this.client.getCameraEntity()).isSleeping();
        if (this.client.options.getPerspective().isFirstPerson() && !_snowman && !this.client.options.hudHidden && this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
            this.lightmapTextureManager.enable();
            this.firstPersonRenderer.renderItem(tickDelta, matrices, this.buffers.getEntityVertexConsumers(), this.client.player, this.client.getEntityRenderDispatcher().getLight(this.client.player, tickDelta));
            this.lightmapTextureManager.disable();
        }
        matrices.pop();
        if (this.client.options.getPerspective().isFirstPerson() && !_snowman) {
            InGameOverlayRenderer.renderOverlays(this.client, matrices);
            this.bobViewWhenHurt(matrices, tickDelta);
        }
        if (this.client.options.bobView) {
            this.bobView(matrices, tickDelta);
        }
    }

    public void loadProjectionMatrix(Matrix4f matrix4f) {
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matrix4f);
        RenderSystem.matrixMode(5888);
    }

    public Matrix4f getBasicProjectionMatrix(Camera camera, float f, boolean bl) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.peek().getModel().loadIdentity();
        if (this.zoom != 1.0f) {
            matrixStack.translate(this.zoomX, -this.zoomY, 0.0);
            matrixStack.scale(this.zoom, this.zoom, 1.0f);
        }
        matrixStack.peek().getModel().multiply(Matrix4f.viewboxMatrix(this.getFov(camera, f, bl), (float)this.client.getWindow().getFramebufferWidth() / (float)this.client.getWindow().getFramebufferHeight(), 0.05f, this.viewDistance * 4.0f));
        return matrixStack.peek().getModel();
    }

    public static float getNightVisionStrength(LivingEntity livingEntity, float f) {
        int n = livingEntity.getStatusEffect(StatusEffects.NIGHT_VISION).getDuration();
        if (n > 200) {
            return 1.0f;
        }
        return 0.7f + MathHelper.sin(((float)n - f) * (float)Math.PI * 0.2f) * 0.3f;
    }

    public void render(float tickDelta, long startTime, boolean tick) {
        if (this.client.isWindowFocused() || !this.client.options.pauseOnLostFocus || this.client.options.touchscreen && this.client.mouse.wasRightButtonClicked()) {
            this.lastWindowFocusedTime = Util.getMeasuringTimeMs();
        } else if (Util.getMeasuringTimeMs() - this.lastWindowFocusedTime > 500L) {
            this.client.openPauseMenu(false);
        }
        if (this.client.skipGameRender) {
            return;
        }
        int n = (int)(this.client.mouse.getX() * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth());
        _snowman = (int)(this.client.mouse.getY() * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight());
        RenderSystem.viewport(0, 0, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
        if (tick && this.client.world != null) {
            this.client.getProfiler().push("level");
            this.renderWorld(tickDelta, startTime, new MatrixStack());
            if (this.client.isIntegratedServerRunning() && this.lastWorldIconUpdate < Util.getMeasuringTimeMs() - 1000L) {
                this.lastWorldIconUpdate = Util.getMeasuringTimeMs();
                if (!this.client.getServer().hasIconFile()) {
                    this.updateWorldIcon();
                }
            }
            this.client.worldRenderer.drawEntityOutlinesFramebuffer();
            if (this.shader != null && this.shadersEnabled) {
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.disableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.matrixMode(5890);
                RenderSystem.pushMatrix();
                RenderSystem.loadIdentity();
                this.shader.render(tickDelta);
                RenderSystem.popMatrix();
            }
            this.client.getFramebuffer().beginWrite(true);
        }
        Window _snowman2 = this.client.getWindow();
        RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0.0, (double)_snowman2.getFramebufferWidth() / _snowman2.getScaleFactor(), (double)_snowman2.getFramebufferHeight() / _snowman2.getScaleFactor(), 0.0, 1000.0, 3000.0);
        RenderSystem.matrixMode(5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0f, 0.0f, -2000.0f);
        DiffuseLighting.enableGuiDepthLighting();
        MatrixStack _snowman3 = new MatrixStack();
        if (tick && this.client.world != null) {
            this.client.getProfiler().swap("gui");
            if (this.client.player != null && (_snowman = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength)) > 0.0f && this.client.player.hasStatusEffect(StatusEffects.NAUSEA) && this.client.options.distortionEffectScale < 1.0f) {
                this.method_31136(_snowman * (1.0f - this.client.options.distortionEffectScale));
            }
            if (!this.client.options.hudHidden || this.client.currentScreen != null) {
                RenderSystem.defaultAlphaFunc();
                this.renderFloatingItem(this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight(), tickDelta);
                this.client.inGameHud.render(_snowman3, tickDelta);
                RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
            }
            this.client.getProfiler().pop();
        }
        if (this.client.overlay != null) {
            try {
                this.client.overlay.render(_snowman3, n, _snowman, this.client.getLastFrameDuration());
            }
            catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.create(throwable, "Rendering overlay");
                CrashReportSection _snowman4 = crashReport.addElement("Overlay render details");
                _snowman4.add("Overlay name", () -> this.client.overlay.getClass().getCanonicalName());
                throw new CrashException(crashReport);
            }
        }
        if (this.client.currentScreen != null) {
            try {
                this.client.currentScreen.render(_snowman3, n, _snowman, this.client.getLastFrameDuration());
            }
            catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.create(throwable, "Rendering screen");
                CrashReportSection _snowman5 = crashReport.addElement("Screen render details");
                _snowman5.add("Screen name", () -> this.client.currentScreen.getClass().getCanonicalName());
                _snowman5.add("Mouse location", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", n, _snowman, this.client.mouse.getX(), this.client.mouse.getY()));
                _snowman5.add("Screen size", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f", this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight(), this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), this.client.getWindow().getScaleFactor()));
                throw new CrashException(crashReport);
            }
        }
    }

    private void updateWorldIcon() {
        if (this.client.worldRenderer.getCompletedChunkCount() > 10 && this.client.worldRenderer.isTerrainRenderComplete() && !this.client.getServer().hasIconFile()) {
            NativeImage nativeImage = ScreenshotUtils.takeScreenshot(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), this.client.getFramebuffer());
            Util.getIoWorkerExecutor().execute(() -> {
                int n = nativeImage.getWidth();
                _snowman = nativeImage.getHeight();
                _snowman = 0;
                _snowman = 0;
                if (n > _snowman) {
                    _snowman = (n - _snowman) / 2;
                    n = _snowman;
                } else {
                    _snowman = (_snowman - n) / 2;
                    _snowman = n;
                }
                try (NativeImage nativeImage2 = new NativeImage(64, 64, false);){
                    nativeImage.resizeSubRectTo(_snowman, _snowman, n, _snowman, nativeImage2);
                    nativeImage2.writeFile(this.client.getServer().getIconFile());
                }
                catch (IOException iOException) {
                    LOGGER.warn("Couldn't save auto screenshot", (Throwable)iOException);
                }
                finally {
                    nativeImage.close();
                }
            });
        }
    }

    private boolean shouldRenderBlockOutline() {
        boolean _snowman5;
        if (!this.blockOutlineEnabled) {
            return false;
        }
        Entity entity = this.client.getCameraEntity();
        boolean bl = _snowman5 = entity instanceof PlayerEntity && !this.client.options.hudHidden;
        if (_snowman5 && !((PlayerEntity)entity).abilities.allowModifyWorld) {
            ItemStack itemStack = ((LivingEntity)entity).getMainHandStack();
            HitResult _snowman2 = this.client.crosshairTarget;
            if (_snowman2 != null && _snowman2.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult)_snowman2).getBlockPos();
                BlockState _snowman3 = this.client.world.getBlockState(blockPos);
                if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
                    _snowman5 = _snowman3.createScreenHandlerFactory(this.client.world, blockPos) != null;
                } else {
                    CachedBlockPosition _snowman4 = new CachedBlockPosition(this.client.world, blockPos, false);
                    _snowman5 = !itemStack.isEmpty() && (itemStack.canDestroy(this.client.world.getTagManager(), _snowman4) || itemStack.canPlaceOn(this.client.world.getTagManager(), _snowman4));
                }
            }
        }
        return _snowman5;
    }

    public void renderWorld(float tickDelta, long limitTime, MatrixStack matrix) {
        this.lightmapTextureManager.update(tickDelta);
        if (this.client.getCameraEntity() == null) {
            this.client.setCameraEntity(this.client.player);
        }
        this.updateTargetedEntity(tickDelta);
        this.client.getProfiler().push("center");
        boolean bl = this.shouldRenderBlockOutline();
        this.client.getProfiler().swap("camera");
        Camera _snowman2 = this.camera;
        this.viewDistance = this.client.options.viewDistance * 16;
        MatrixStack _snowman3 = new MatrixStack();
        _snowman3.peek().getModel().multiply(this.getBasicProjectionMatrix(_snowman2, tickDelta, true));
        this.bobViewWhenHurt(_snowman3, tickDelta);
        if (this.client.options.bobView) {
            this.bobView(_snowman3, tickDelta);
        }
        if ((_snowman = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength) * (this.client.options.distortionEffectScale * this.client.options.distortionEffectScale)) > 0.0f) {
            int n = this.client.player.hasStatusEffect(StatusEffects.NAUSEA) ? 7 : 20;
            float _snowman4 = 5.0f / (_snowman * _snowman + 5.0f) - _snowman * 0.04f;
            _snowman4 *= _snowman4;
            Vector3f _snowman5 = new Vector3f(0.0f, MathHelper.SQUARE_ROOT_OF_TWO / 2.0f, MathHelper.SQUARE_ROOT_OF_TWO / 2.0f);
            _snowman3.multiply(_snowman5.getDegreesQuaternion(((float)this.ticks + tickDelta) * (float)n));
            _snowman3.scale(1.0f / _snowman4, 1.0f, 1.0f);
            float _snowman6 = -((float)this.ticks + tickDelta) * (float)n;
            _snowman3.multiply(_snowman5.getDegreesQuaternion(_snowman6));
        }
        Matrix4f matrix4f = _snowman3.peek().getModel();
        this.loadProjectionMatrix(matrix4f);
        _snowman2.update(this.client.world, this.client.getCameraEntity() == null ? this.client.player : this.client.getCameraEntity(), !this.client.options.getPerspective().isFirstPerson(), this.client.options.getPerspective().isFrontView(), tickDelta);
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman2.getPitch()));
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2.getYaw() + 180.0f));
        this.client.worldRenderer.render(matrix, tickDelta, limitTime, bl, _snowman2, this, this.lightmapTextureManager, matrix4f);
        this.client.getProfiler().swap("hand");
        if (this.renderHand) {
            RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
            this.renderHand(matrix, _snowman2, tickDelta);
        }
        this.client.getProfiler().pop();
    }

    public void reset() {
        this.floatingItem = null;
        this.mapRenderer.clearStateTextures();
        this.camera.reset();
    }

    public MapRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    public void showFloatingItem(ItemStack floatingItem) {
        this.floatingItem = floatingItem;
        this.floatingItemTimeLeft = 40;
        this.floatingItemWidth = this.random.nextFloat() * 2.0f - 1.0f;
        this.floatingItemHeight = this.random.nextFloat() * 2.0f - 1.0f;
    }

    private void renderFloatingItem(int scaledWidth, int scaledHeight, float tickDelta) {
        if (this.floatingItem == null || this.floatingItemTimeLeft <= 0) {
            return;
        }
        int n = 40 - this.floatingItemTimeLeft;
        float _snowman2 = ((float)n + tickDelta) / 40.0f;
        float _snowman3 = _snowman2 * _snowman2;
        float _snowman4 = _snowman2 * _snowman3;
        float _snowman5 = 10.25f * _snowman4 * _snowman3 - 24.95f * _snowman3 * _snowman3 + 25.5f * _snowman4 - 13.8f * _snowman3 + 4.0f * _snowman2;
        float _snowman6 = _snowman5 * (float)Math.PI;
        float _snowman7 = this.floatingItemWidth * (float)(scaledWidth / 4);
        float _snowman8 = this.floatingItemHeight * (float)(scaledHeight / 4);
        RenderSystem.enableAlphaTest();
        RenderSystem.pushMatrix();
        RenderSystem.pushLightingAttributes();
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        MatrixStack _snowman9 = new MatrixStack();
        _snowman9.push();
        _snowman9.translate((float)(scaledWidth / 2) + _snowman7 * MathHelper.abs(MathHelper.sin(_snowman6 * 2.0f)), (float)(scaledHeight / 2) + _snowman8 * MathHelper.abs(MathHelper.sin(_snowman6 * 2.0f)), -50.0);
        float _snowman10 = 50.0f + 175.0f * MathHelper.sin(_snowman6);
        _snowman9.scale(_snowman10, -_snowman10, _snowman10);
        _snowman9.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(900.0f * MathHelper.abs(MathHelper.sin(_snowman6))));
        _snowman9.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0f * MathHelper.cos(_snowman2 * 8.0f)));
        _snowman9.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.0f * MathHelper.cos(_snowman2 * 8.0f)));
        VertexConsumerProvider.Immediate _snowman11 = this.buffers.getEntityVertexConsumers();
        this.client.getItemRenderer().renderItem(this.floatingItem, ModelTransformation.Mode.FIXED, 0xF000F0, OverlayTexture.DEFAULT_UV, _snowman9, _snowman11);
        _snowman9.pop();
        _snowman11.draw();
        RenderSystem.popAttributes();
        RenderSystem.popMatrix();
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
    }

    private void method_31136(float f) {
        int n = this.client.getWindow().getScaledWidth();
        _snowman = this.client.getWindow().getScaledHeight();
        double _snowman2 = MathHelper.lerp((double)f, 2.0, 1.0);
        float _snowman3 = 0.2f * f;
        float _snowman4 = 0.4f * f;
        float _snowman5 = 0.2f * f;
        double _snowman6 = (double)n * _snowman2;
        double _snowman7 = (double)_snowman * _snowman2;
        double _snowman8 = ((double)n - _snowman6) / 2.0;
        double _snowman9 = ((double)_snowman - _snowman7) / 2.0;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE);
        RenderSystem.color4f(_snowman3, _snowman4, _snowman5, 1.0f);
        this.client.getTextureManager().bindTexture(field_26730);
        Tessellator _snowman10 = Tessellator.getInstance();
        BufferBuilder _snowman11 = _snowman10.getBuffer();
        _snowman11.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman11.vertex(_snowman8, _snowman9 + _snowman7, -90.0).texture(0.0f, 1.0f).next();
        _snowman11.vertex(_snowman8 + _snowman6, _snowman9 + _snowman7, -90.0).texture(1.0f, 1.0f).next();
        _snowman11.vertex(_snowman8 + _snowman6, _snowman9, -90.0).texture(1.0f, 0.0f).next();
        _snowman11.vertex(_snowman8, _snowman9, -90.0).texture(0.0f, 0.0f).next();
        _snowman10.draw();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    public float getSkyDarkness(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastSkyDarkness, this.skyDarkness);
    }

    public float getViewDistance() {
        return this.viewDistance;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public LightmapTextureManager getLightmapTextureManager() {
        return this.lightmapTextureManager;
    }

    public OverlayTexture getOverlayTexture() {
        return this.overlayTexture;
    }
}

