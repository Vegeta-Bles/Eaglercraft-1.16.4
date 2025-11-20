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

public class GameRenderer implements SynchronousResourceReloadListener, AutoCloseable {
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
   private float zoom = 1.0F;
   private float zoomX;
   private float zoomY;
   @Nullable
   private ItemStack floatingItem;
   private int floatingItemTimeLeft;
   private float floatingItemWidth;
   private float floatingItemHeight;
   @Nullable
   private ShaderEffect shader;
   private static final Identifier[] SHADERS_LOCATIONS = new Identifier[]{
      new Identifier("shaders/post/notch.json"),
      new Identifier("shaders/post/fxaa.json"),
      new Identifier("shaders/post/art.json"),
      new Identifier("shaders/post/bumpy.json"),
      new Identifier("shaders/post/blobs2.json"),
      new Identifier("shaders/post/pencil.json"),
      new Identifier("shaders/post/color_convolve.json"),
      new Identifier("shaders/post/deconverge.json"),
      new Identifier("shaders/post/flip.json"),
      new Identifier("shaders/post/invert.json"),
      new Identifier("shaders/post/ntsc.json"),
      new Identifier("shaders/post/outline.json"),
      new Identifier("shaders/post/phosphor.json"),
      new Identifier("shaders/post/scan_pincushion.json"),
      new Identifier("shaders/post/sobel.json"),
      new Identifier("shaders/post/bits.json"),
      new Identifier("shaders/post/desaturate.json"),
      new Identifier("shaders/post/green.json"),
      new Identifier("shaders/post/blur.json"),
      new Identifier("shaders/post/wobble.json"),
      new Identifier("shaders/post/blobs.json"),
      new Identifier("shaders/post/antialias.json"),
      new Identifier("shaders/post/creeper.json"),
      new Identifier("shaders/post/spider.json")
   };
   public static final int SHADER_COUNT = SHADERS_LOCATIONS.length;
   private int forcedShaderIndex = SHADER_COUNT;
   private boolean shadersEnabled;
   private final Camera camera = new Camera();

   public GameRenderer(MinecraftClient client, ResourceManager _snowman, BufferBuilderStorage _snowman) {
      this.client = client;
      this.resourceContainer = _snowman;
      this.firstPersonRenderer = client.getHeldItemRenderer();
      this.mapRenderer = new MapRenderer(client.getTextureManager());
      this.lightmapTextureManager = new LightmapTextureManager(this, client);
      this.buffers = _snowman;
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

   public void onCameraEntitySet(@Nullable Entity _snowman) {
      if (this.shader != null) {
         this.shader.close();
      }

      this.shader = null;
      if (_snowman instanceof CreeperEntity) {
         this.loadShader(new Identifier("shaders/post/creeper.json"));
      } else if (_snowman instanceof SpiderEntity) {
         this.loadShader(new Identifier("shaders/post/spider.json"));
      } else if (_snowman instanceof EndermanEntity) {
         this.loadShader(new Identifier("shaders/post/invert.json"));
      }
   }

   private void loadShader(Identifier _snowman) {
      if (this.shader != null) {
         this.shader.close();
      }

      try {
         this.shader = new ShaderEffect(this.client.getTextureManager(), this.resourceContainer, this.client.getFramebuffer(), _snowman);
         this.shader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
         this.shadersEnabled = true;
      } catch (IOException var3) {
         LOGGER.warn("Failed to load shader: {}", _snowman, var3);
         this.forcedShaderIndex = SHADER_COUNT;
         this.shadersEnabled = false;
      } catch (JsonSyntaxException var4) {
         LOGGER.warn("Failed to parse shader: {}", _snowman, var4);
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
      this.ticks++;
      this.firstPersonRenderer.updateHeldItems();
      this.client.worldRenderer.tickRainSplashing(this.camera);
      this.lastSkyDarkness = this.skyDarkness;
      if (this.client.inGameHud.getBossBarHud().shouldDarkenSky()) {
         this.skyDarkness += 0.05F;
         if (this.skyDarkness > 1.0F) {
            this.skyDarkness = 1.0F;
         }
      } else if (this.skyDarkness > 0.0F) {
         this.skyDarkness -= 0.0125F;
      }

      if (this.floatingItemTimeLeft > 0) {
         this.floatingItemTimeLeft--;
         if (this.floatingItemTimeLeft == 0) {
            this.floatingItem = null;
         }
      }
   }

   @Nullable
   public ShaderEffect getShader() {
      return this.shader;
   }

   public void onResized(int _snowman, int _snowman) {
      if (this.shader != null) {
         this.shader.setupDimensions(_snowman, _snowman);
      }

      this.client.worldRenderer.onResized(_snowman, _snowman);
   }

   public void updateTargetedEntity(float tickDelta) {
      Entity _snowman = this.client.getCameraEntity();
      if (_snowman != null) {
         if (this.client.world != null) {
            this.client.getProfiler().push("pick");
            this.client.targetedEntity = null;
            double _snowmanx = (double)this.client.interactionManager.getReachDistance();
            this.client.crosshairTarget = _snowman.raycast(_snowmanx, tickDelta, false);
            Vec3d _snowmanxx = _snowman.getCameraPosVec(tickDelta);
            boolean _snowmanxxx = false;
            int _snowmanxxxx = 3;
            double _snowmanxxxxx = _snowmanx;
            if (this.client.interactionManager.hasExtendedReach()) {
               _snowmanxxxxx = 6.0;
               _snowmanx = _snowmanxxxxx;
            } else {
               if (_snowmanx > 3.0) {
                  _snowmanxxx = true;
               }

               _snowmanx = _snowmanx;
            }

            _snowmanxxxxx *= _snowmanxxxxx;
            if (this.client.crosshairTarget != null) {
               _snowmanxxxxx = this.client.crosshairTarget.getPos().squaredDistanceTo(_snowmanxx);
            }

            Vec3d _snowmanxxxxxx = _snowman.getRotationVec(1.0F);
            Vec3d _snowmanxxxxxxx = _snowmanxx.add(_snowmanxxxxxx.x * _snowmanx, _snowmanxxxxxx.y * _snowmanx, _snowmanxxxxxx.z * _snowmanx);
            float _snowmanxxxxxxxx = 1.0F;
            Box _snowmanxxxxxxxxx = _snowman.getBoundingBox().stretch(_snowmanxxxxxx.multiply(_snowmanx)).expand(1.0, 1.0, 1.0);
            EntityHitResult _snowmanxxxxxxxxxx = ProjectileUtil.raycast(
               _snowman, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx -> !_snowmanxxxxxxxxxxx.isSpectator() && _snowmanxxxxxxxxxxx.collides(), _snowmanxxxxx
            );
            if (_snowmanxxxxxxxxxx != null) {
               Entity _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getEntity();
               Vec3d _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getPos();
               double _snowmanxxxxxxxxxxxxx = _snowmanxx.squaredDistanceTo(_snowmanxxxxxxxxxxxx);
               if (_snowmanxxx && _snowmanxxxxxxxxxxxxx > 9.0) {
                  this.client.crosshairTarget = BlockHitResult.createMissed(
                     _snowmanxxxxxxxxxxxx, Direction.getFacing(_snowmanxxxxxx.x, _snowmanxxxxxx.y, _snowmanxxxxxx.z), new BlockPos(_snowmanxxxxxxxxxxxx)
                  );
               } else if (_snowmanxxxxxxxxxxxxx < _snowmanxxxxx || this.client.crosshairTarget == null) {
                  this.client.crosshairTarget = _snowmanxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxx instanceof LivingEntity || _snowmanxxxxxxxxxxx instanceof ItemFrameEntity) {
                     this.client.targetedEntity = _snowmanxxxxxxxxxxx;
                  }
               }
            }

            this.client.getProfiler().pop();
         }
      }
   }

   private void updateMovementFovMultiplier() {
      float _snowman = 1.0F;
      if (this.client.getCameraEntity() instanceof AbstractClientPlayerEntity) {
         AbstractClientPlayerEntity _snowmanx = (AbstractClientPlayerEntity)this.client.getCameraEntity();
         _snowman = _snowmanx.getSpeed();
      }

      this.lastMovementFovMultiplier = this.movementFovMultiplier;
      this.movementFovMultiplier = this.movementFovMultiplier + (_snowman - this.movementFovMultiplier) * 0.5F;
      if (this.movementFovMultiplier > 1.5F) {
         this.movementFovMultiplier = 1.5F;
      }

      if (this.movementFovMultiplier < 0.1F) {
         this.movementFovMultiplier = 0.1F;
      }
   }

   private double getFov(Camera camera, float tickDelta, boolean changingFov) {
      if (this.renderingPanorama) {
         return 90.0;
      } else {
         double _snowman = 70.0;
         if (changingFov) {
            _snowman = this.client.options.fov;
            _snowman *= (double)MathHelper.lerp(tickDelta, this.lastMovementFovMultiplier, this.movementFovMultiplier);
         }

         if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).isDead()) {
            float _snowmanx = Math.min((float)((LivingEntity)camera.getFocusedEntity()).deathTime + tickDelta, 20.0F);
            _snowman /= (double)((1.0F - 500.0F / (_snowmanx + 500.0F)) * 2.0F + 1.0F);
         }

         FluidState _snowmanx = camera.getSubmergedFluidState();
         if (!_snowmanx.isEmpty()) {
            _snowman = _snowman * 60.0 / 70.0;
         }

         return _snowman;
      }
   }

   private void bobViewWhenHurt(MatrixStack _snowman, float _snowman) {
      if (this.client.getCameraEntity() instanceof LivingEntity) {
         LivingEntity _snowmanxx = (LivingEntity)this.client.getCameraEntity();
         float _snowmanxxx = (float)_snowmanxx.hurtTime - _snowman;
         if (_snowmanxx.isDead()) {
            float _snowmanxxxx = Math.min((float)_snowmanxx.deathTime + _snowman, 20.0F);
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(40.0F - 8000.0F / (_snowmanxxxx + 200.0F)));
         }

         if (_snowmanxxx < 0.0F) {
            return;
         }

         _snowmanxxx /= (float)_snowmanxx.maxHurtTime;
         _snowmanxxx = MathHelper.sin(_snowmanxxx * _snowmanxxx * _snowmanxxx * _snowmanxxx * (float) Math.PI);
         float _snowmanxxxx = _snowmanxx.knockbackVelocity;
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowmanxxxx));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowmanxxx * 14.0F));
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxx));
      }
   }

   private void bobView(MatrixStack _snowman, float _snowman) {
      if (this.client.getCameraEntity() instanceof PlayerEntity) {
         PlayerEntity _snowmanxx = (PlayerEntity)this.client.getCameraEntity();
         float _snowmanxxx = _snowmanxx.horizontalSpeed - _snowmanxx.prevHorizontalSpeed;
         float _snowmanxxxx = -(_snowmanxx.horizontalSpeed + _snowmanxxx * _snowman);
         float _snowmanxxxxx = MathHelper.lerp(_snowman, _snowmanxx.prevStrideDistance, _snowmanxx.strideDistance);
         _snowman.translate(
            (double)(MathHelper.sin(_snowmanxxxx * (float) Math.PI) * _snowmanxxxxx * 0.5F), (double)(-Math.abs(MathHelper.cos(_snowmanxxxx * (float) Math.PI) * _snowmanxxxxx)), 0.0
         );
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(_snowmanxxxx * (float) Math.PI) * _snowmanxxxxx * 3.0F));
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(Math.abs(MathHelper.cos(_snowmanxxxx * (float) Math.PI - 0.2F) * _snowmanxxxxx) * 5.0F));
      }
   }

   private void renderHand(MatrixStack matrices, Camera camera, float tickDelta) {
      if (!this.renderingPanorama) {
         this.loadProjectionMatrix(this.getBasicProjectionMatrix(camera, tickDelta, false));
         MatrixStack.Entry _snowman = matrices.peek();
         _snowman.getModel().loadIdentity();
         _snowman.getNormal().loadIdentity();
         matrices.push();
         this.bobViewWhenHurt(matrices, tickDelta);
         if (this.client.options.bobView) {
            this.bobView(matrices, tickDelta);
         }

         boolean _snowmanx = this.client.getCameraEntity() instanceof LivingEntity && ((LivingEntity)this.client.getCameraEntity()).isSleeping();
         if (this.client.options.getPerspective().isFirstPerson()
            && !_snowmanx
            && !this.client.options.hudHidden
            && this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
            this.lightmapTextureManager.enable();
            this.firstPersonRenderer
               .renderItem(
                  tickDelta,
                  matrices,
                  this.buffers.getEntityVertexConsumers(),
                  this.client.player,
                  this.client.getEntityRenderDispatcher().getLight(this.client.player, tickDelta)
               );
            this.lightmapTextureManager.disable();
         }

         matrices.pop();
         if (this.client.options.getPerspective().isFirstPerson() && !_snowmanx) {
            InGameOverlayRenderer.renderOverlays(this.client, matrices);
            this.bobViewWhenHurt(matrices, tickDelta);
         }

         if (this.client.options.bobView) {
            this.bobView(matrices, tickDelta);
         }
      }
   }

   public void loadProjectionMatrix(Matrix4f _snowman) {
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(_snowman);
      RenderSystem.matrixMode(5888);
   }

   public Matrix4f getBasicProjectionMatrix(Camera _snowman, float _snowman, boolean _snowman) {
      MatrixStack _snowmanxxx = new MatrixStack();
      _snowmanxxx.peek().getModel().loadIdentity();
      if (this.zoom != 1.0F) {
         _snowmanxxx.translate((double)this.zoomX, (double)(-this.zoomY), 0.0);
         _snowmanxxx.scale(this.zoom, this.zoom, 1.0F);
      }

      _snowmanxxx.peek()
         .getModel()
         .multiply(
            Matrix4f.viewboxMatrix(
               this.getFov(_snowman, _snowman, _snowman),
               (float)this.client.getWindow().getFramebufferWidth() / (float)this.client.getWindow().getFramebufferHeight(),
               0.05F,
               this.viewDistance * 4.0F
            )
         );
      return _snowmanxxx.peek().getModel();
   }

   public static float getNightVisionStrength(LivingEntity _snowman, float _snowman) {
      int _snowmanxx = _snowman.getStatusEffect(StatusEffects.NIGHT_VISION).getDuration();
      return _snowmanxx > 200 ? 1.0F : 0.7F + MathHelper.sin(((float)_snowmanxx - _snowman) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void render(float tickDelta, long startTime, boolean tick) {
      if (!this.client.isWindowFocused()
         && this.client.options.pauseOnLostFocus
         && (!this.client.options.touchscreen || !this.client.mouse.wasRightButtonClicked())) {
         if (Util.getMeasuringTimeMs() - this.lastWindowFocusedTime > 500L) {
            this.client.openPauseMenu(false);
         }
      } else {
         this.lastWindowFocusedTime = Util.getMeasuringTimeMs();
      }

      if (!this.client.skipGameRender) {
         int _snowman = (int)(this.client.mouse.getX() * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth());
         int _snowmanx = (int)(this.client.mouse.getY() * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight());
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

         Window _snowmanxx = this.client.getWindow();
         RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
         RenderSystem.matrixMode(5889);
         RenderSystem.loadIdentity();
         RenderSystem.ortho(
            0.0, (double)_snowmanxx.getFramebufferWidth() / _snowmanxx.getScaleFactor(), (double)_snowmanxx.getFramebufferHeight() / _snowmanxx.getScaleFactor(), 0.0, 1000.0, 3000.0
         );
         RenderSystem.matrixMode(5888);
         RenderSystem.loadIdentity();
         RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
         DiffuseLighting.enableGuiDepthLighting();
         MatrixStack _snowmanxxx = new MatrixStack();
         if (tick && this.client.world != null) {
            this.client.getProfiler().swap("gui");
            if (this.client.player != null) {
               float _snowmanxxxx = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength);
               if (_snowmanxxxx > 0.0F && this.client.player.hasStatusEffect(StatusEffects.NAUSEA) && this.client.options.distortionEffectScale < 1.0F) {
                  this.method_31136(_snowmanxxxx * (1.0F - this.client.options.distortionEffectScale));
               }
            }

            if (!this.client.options.hudHidden || this.client.currentScreen != null) {
               RenderSystem.defaultAlphaFunc();
               this.renderFloatingItem(this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight(), tickDelta);
               this.client.inGameHud.render(_snowmanxxx, tickDelta);
               RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
            }

            this.client.getProfiler().pop();
         }

         if (this.client.overlay != null) {
            try {
               this.client.overlay.render(_snowmanxxx, _snowman, _snowmanx, this.client.getLastFrameDuration());
            } catch (Throwable var13) {
               CrashReport _snowmanxxxx = CrashReport.create(var13, "Rendering overlay");
               CrashReportSection _snowmanxxxxx = _snowmanxxxx.addElement("Overlay render details");
               _snowmanxxxxx.add("Overlay name", () -> this.client.overlay.getClass().getCanonicalName());
               throw new CrashException(_snowmanxxxx);
            }
         } else if (this.client.currentScreen != null) {
            try {
               this.client.currentScreen.render(_snowmanxxx, _snowman, _snowmanx, this.client.getLastFrameDuration());
            } catch (Throwable var12) {
               CrashReport _snowmanxxxx = CrashReport.create(var12, "Rendering screen");
               CrashReportSection _snowmanxxxxx = _snowmanxxxx.addElement("Screen render details");
               _snowmanxxxxx.add("Screen name", () -> this.client.currentScreen.getClass().getCanonicalName());
               _snowmanxxxxx.add(
                  "Mouse location",
                  () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", _snowman, _snowman, this.client.mouse.getX(), this.client.mouse.getY())
               );
               _snowmanxxxxx.add(
                  "Screen size",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f",
                        this.client.getWindow().getScaledWidth(),
                        this.client.getWindow().getScaledHeight(),
                        this.client.getWindow().getFramebufferWidth(),
                        this.client.getWindow().getFramebufferHeight(),
                        this.client.getWindow().getScaleFactor()
                     )
               );
               throw new CrashException(_snowmanxxxx);
            }
         }
      }
   }

   private void updateWorldIcon() {
      if (this.client.worldRenderer.getCompletedChunkCount() > 10
         && this.client.worldRenderer.isTerrainRenderComplete()
         && !this.client.getServer().hasIconFile()) {
         NativeImage _snowman = ScreenshotUtils.takeScreenshot(
            this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), this.client.getFramebuffer()
         );
         Util.getIoWorkerExecutor().execute(() -> {
            int _snowmanx = _snowman.getWidth();
            int _snowmanx = _snowman.getHeight();
            int _snowmanxx = 0;
            int _snowmanxxx = 0;
            if (_snowmanx > _snowmanx) {
               _snowmanxx = (_snowmanx - _snowmanx) / 2;
               _snowmanx = _snowmanx;
            } else {
               _snowmanxxx = (_snowmanx - _snowmanx) / 2;
               _snowmanx = _snowmanx;
            }

            try (NativeImage _snowmanxxxx = new NativeImage(64, 64, false)) {
               _snowman.resizeSubRectTo(_snowmanxx, _snowmanxxx, _snowmanx, _snowmanx, _snowmanxxxx);
               _snowmanxxxx.writeFile(this.client.getServer().getIconFile());
            } catch (IOException var27) {
               LOGGER.warn("Couldn't save auto screenshot", var27);
            } finally {
               _snowman.close();
            }
         });
      }
   }

   private boolean shouldRenderBlockOutline() {
      if (!this.blockOutlineEnabled) {
         return false;
      } else {
         Entity _snowman = this.client.getCameraEntity();
         boolean _snowmanx = _snowman instanceof PlayerEntity && !this.client.options.hudHidden;
         if (_snowmanx && !((PlayerEntity)_snowman).abilities.allowModifyWorld) {
            ItemStack _snowmanxx = ((LivingEntity)_snowman).getMainHandStack();
            HitResult _snowmanxxx = this.client.crosshairTarget;
            if (_snowmanxxx != null && _snowmanxxx.getType() == HitResult.Type.BLOCK) {
               BlockPos _snowmanxxxx = ((BlockHitResult)_snowmanxxx).getBlockPos();
               BlockState _snowmanxxxxx = this.client.world.getBlockState(_snowmanxxxx);
               if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
                  _snowmanx = _snowmanxxxxx.createScreenHandlerFactory(this.client.world, _snowmanxxxx) != null;
               } else {
                  CachedBlockPosition _snowmanxxxxxx = new CachedBlockPosition(this.client.world, _snowmanxxxx, false);
                  _snowmanx = !_snowmanxx.isEmpty()
                     && (_snowmanxx.canDestroy(this.client.world.getTagManager(), _snowmanxxxxxx) || _snowmanxx.canPlaceOn(this.client.world.getTagManager(), _snowmanxxxxxx));
               }
            }
         }

         return _snowmanx;
      }
   }

   public void renderWorld(float tickDelta, long limitTime, MatrixStack matrix) {
      this.lightmapTextureManager.update(tickDelta);
      if (this.client.getCameraEntity() == null) {
         this.client.setCameraEntity(this.client.player);
      }

      this.updateTargetedEntity(tickDelta);
      this.client.getProfiler().push("center");
      boolean _snowman = this.shouldRenderBlockOutline();
      this.client.getProfiler().swap("camera");
      Camera _snowmanx = this.camera;
      this.viewDistance = (float)(this.client.options.viewDistance * 16);
      MatrixStack _snowmanxx = new MatrixStack();
      _snowmanxx.peek().getModel().multiply(this.getBasicProjectionMatrix(_snowmanx, tickDelta, true));
      this.bobViewWhenHurt(_snowmanxx, tickDelta);
      if (this.client.options.bobView) {
         this.bobView(_snowmanxx, tickDelta);
      }

      float _snowmanxxx = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength)
         * this.client.options.distortionEffectScale
         * this.client.options.distortionEffectScale;
      if (_snowmanxxx > 0.0F) {
         int _snowmanxxxx = this.client.player.hasStatusEffect(StatusEffects.NAUSEA) ? 7 : 20;
         float _snowmanxxxxx = 5.0F / (_snowmanxxx * _snowmanxxx + 5.0F) - _snowmanxxx * 0.04F;
         _snowmanxxxxx *= _snowmanxxxxx;
         Vector3f _snowmanxxxxxx = new Vector3f(0.0F, MathHelper.SQUARE_ROOT_OF_TWO / 2.0F, MathHelper.SQUARE_ROOT_OF_TWO / 2.0F);
         _snowmanxx.multiply(_snowmanxxxxxx.getDegreesQuaternion(((float)this.ticks + tickDelta) * (float)_snowmanxxxx));
         _snowmanxx.scale(1.0F / _snowmanxxxxx, 1.0F, 1.0F);
         float _snowmanxxxxxxx = -((float)this.ticks + tickDelta) * (float)_snowmanxxxx;
         _snowmanxx.multiply(_snowmanxxxxxx.getDegreesQuaternion(_snowmanxxxxxxx));
      }

      Matrix4f _snowmanxxxx = _snowmanxx.peek().getModel();
      this.loadProjectionMatrix(_snowmanxxxx);
      _snowmanx.update(
         this.client.world,
         (Entity)(this.client.getCameraEntity() == null ? this.client.player : this.client.getCameraEntity()),
         !this.client.options.getPerspective().isFirstPerson(),
         this.client.options.getPerspective().isFrontView(),
         tickDelta
      );
      matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanx.getPitch()));
      matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanx.getYaw() + 180.0F));
      this.client.worldRenderer.render(matrix, tickDelta, limitTime, _snowman, _snowmanx, this, this.lightmapTextureManager, _snowmanxxxx);
      this.client.getProfiler().swap("hand");
      if (this.renderHand) {
         RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
         this.renderHand(matrix, _snowmanx, tickDelta);
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
      this.floatingItemWidth = this.random.nextFloat() * 2.0F - 1.0F;
      this.floatingItemHeight = this.random.nextFloat() * 2.0F - 1.0F;
   }

   private void renderFloatingItem(int scaledWidth, int scaledHeight, float tickDelta) {
      if (this.floatingItem != null && this.floatingItemTimeLeft > 0) {
         int _snowman = 40 - this.floatingItemTimeLeft;
         float _snowmanx = ((float)_snowman + tickDelta) / 40.0F;
         float _snowmanxx = _snowmanx * _snowmanx;
         float _snowmanxxx = _snowmanx * _snowmanxx;
         float _snowmanxxxx = 10.25F * _snowmanxxx * _snowmanxx - 24.95F * _snowmanxx * _snowmanxx + 25.5F * _snowmanxxx - 13.8F * _snowmanxx + 4.0F * _snowmanx;
         float _snowmanxxxxx = _snowmanxxxx * (float) Math.PI;
         float _snowmanxxxxxx = this.floatingItemWidth * (float)(scaledWidth / 4);
         float _snowmanxxxxxxx = this.floatingItemHeight * (float)(scaledHeight / 4);
         RenderSystem.enableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.pushLightingAttributes();
         RenderSystem.enableDepthTest();
         RenderSystem.disableCull();
         MatrixStack _snowmanxxxxxxxx = new MatrixStack();
         _snowmanxxxxxxxx.push();
         _snowmanxxxxxxxx.translate(
            (double)((float)(scaledWidth / 2) + _snowmanxxxxxx * MathHelper.abs(MathHelper.sin(_snowmanxxxxx * 2.0F))),
            (double)((float)(scaledHeight / 2) + _snowmanxxxxxxx * MathHelper.abs(MathHelper.sin(_snowmanxxxxx * 2.0F))),
            -50.0
         );
         float _snowmanxxxxxxxxx = 50.0F + 175.0F * MathHelper.sin(_snowmanxxxxx);
         _snowmanxxxxxxxx.scale(_snowmanxxxxxxxxx, -_snowmanxxxxxxxxx, _snowmanxxxxxxxxx);
         _snowmanxxxxxxxx.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(900.0F * MathHelper.abs(MathHelper.sin(_snowmanxxxxx))));
         _snowmanxxxxxxxx.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0F * MathHelper.cos(_snowmanx * 8.0F)));
         _snowmanxxxxxxxx.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.0F * MathHelper.cos(_snowmanx * 8.0F)));
         VertexConsumerProvider.Immediate _snowmanxxxxxxxxxx = this.buffers.getEntityVertexConsumers();
         this.client
            .getItemRenderer()
            .renderItem(this.floatingItem, ModelTransformation.Mode.FIXED, 15728880, OverlayTexture.DEFAULT_UV, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx.pop();
         _snowmanxxxxxxxxxx.draw();
         RenderSystem.popAttributes();
         RenderSystem.popMatrix();
         RenderSystem.enableCull();
         RenderSystem.disableDepthTest();
      }
   }

   private void method_31136(float _snowman) {
      int _snowmanx = this.client.getWindow().getScaledWidth();
      int _snowmanxx = this.client.getWindow().getScaledHeight();
      double _snowmanxxx = MathHelper.lerp((double)_snowman, 2.0, 1.0);
      float _snowmanxxxx = 0.2F * _snowman;
      float _snowmanxxxxx = 0.4F * _snowman;
      float _snowmanxxxxxx = 0.2F * _snowman;
      double _snowmanxxxxxxx = (double)_snowmanx * _snowmanxxx;
      double _snowmanxxxxxxxx = (double)_snowmanxx * _snowmanxxx;
      double _snowmanxxxxxxxxx = ((double)_snowmanx - _snowmanxxxxxxx) / 2.0;
      double _snowmanxxxxxxxxxx = ((double)_snowmanxx - _snowmanxxxxxxxx) / 2.0;
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE);
      RenderSystem.color4f(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, 1.0F);
      this.client.getTextureManager().bindTexture(field_26730);
      Tessellator _snowmanxxxxxxxxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBuffer();
      _snowmanxxxxxxxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE);
      _snowmanxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + _snowmanxxxxxxxx, -90.0).texture(0.0F, 1.0F).next();
      _snowmanxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxx + _snowmanxxxxxxx, _snowmanxxxxxxxxxx + _snowmanxxxxxxxx, -90.0).texture(1.0F, 1.0F).next();
      _snowmanxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxx + _snowmanxxxxxxx, _snowmanxxxxxxxxxx, -90.0).texture(1.0F, 0.0F).next();
      _snowmanxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, -90.0).texture(0.0F, 0.0F).next();
      _snowmanxxxxxxxxxxx.draw();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
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
