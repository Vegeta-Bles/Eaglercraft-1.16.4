package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadMonitor;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class SplashScreen extends Overlay {
   private static final Identifier LOGO = new Identifier("textures/gui/title/mojangstudios.png");
   private static final int BRAND_ARGB = BackgroundHelper.ColorMixer.getArgb(255, 239, 50, 61);
   private static final int BRAND_RGB = BRAND_ARGB & 16777215;
   private final MinecraftClient client;
   private final ResourceReloadMonitor reloadMonitor;
   private final Consumer<Optional<Throwable>> exceptionHandler;
   private final boolean reloading;
   private float progress;
   private long applyCompleteTime = -1L;
   private long prepareCompleteTime = -1L;

   public SplashScreen(MinecraftClient client, ResourceReloadMonitor monitor, Consumer<Optional<Throwable>> exceptionHandler, boolean reloading) {
      this.client = client;
      this.reloadMonitor = monitor;
      this.exceptionHandler = exceptionHandler;
      this.reloading = reloading;
   }

   public static void init(MinecraftClient client) {
      client.getTextureManager().registerTexture(LOGO, new SplashScreen.LogoTexture());
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      int _snowman = this.client.getWindow().getScaledWidth();
      int _snowmanx = this.client.getWindow().getScaledHeight();
      long _snowmanxx = Util.getMeasuringTimeMs();
      if (this.reloading && (this.reloadMonitor.isPrepareStageComplete() || this.client.currentScreen != null) && this.prepareCompleteTime == -1L) {
         this.prepareCompleteTime = _snowmanxx;
      }

      float _snowmanxxx = this.applyCompleteTime > -1L ? (float)(_snowmanxx - this.applyCompleteTime) / 1000.0F : -1.0F;
      float _snowmanxxxx = this.prepareCompleteTime > -1L ? (float)(_snowmanxx - this.prepareCompleteTime) / 500.0F : -1.0F;
      float _snowmanxxxxx;
      if (_snowmanxxx >= 1.0F) {
         if (this.client.currentScreen != null) {
            this.client.currentScreen.render(matrices, 0, 0, delta);
         }

         int _snowmanxxxxxx = MathHelper.ceil((1.0F - MathHelper.clamp(_snowmanxxx - 1.0F, 0.0F, 1.0F)) * 255.0F);
         fill(matrices, 0, 0, _snowman, _snowmanx, BRAND_RGB | _snowmanxxxxxx << 24);
         _snowmanxxxxx = 1.0F - MathHelper.clamp(_snowmanxxx - 1.0F, 0.0F, 1.0F);
      } else if (this.reloading) {
         if (this.client.currentScreen != null && _snowmanxxxx < 1.0F) {
            this.client.currentScreen.render(matrices, mouseX, mouseY, delta);
         }

         int _snowmanxxxxxx = MathHelper.ceil(MathHelper.clamp((double)_snowmanxxxx, 0.15, 1.0) * 255.0);
         fill(matrices, 0, 0, _snowman, _snowmanx, BRAND_RGB | _snowmanxxxxxx << 24);
         _snowmanxxxxx = MathHelper.clamp(_snowmanxxxx, 0.0F, 1.0F);
      } else {
         fill(matrices, 0, 0, _snowman, _snowmanx, BRAND_ARGB);
         _snowmanxxxxx = 1.0F;
      }

      int _snowmanxxxxxx = (int)((double)this.client.getWindow().getScaledWidth() * 0.5);
      int _snowmanxxxxxxx = (int)((double)this.client.getWindow().getScaledHeight() * 0.5);
      double _snowmanxxxxxxxx = Math.min((double)this.client.getWindow().getScaledWidth() * 0.75, (double)this.client.getWindow().getScaledHeight()) * 0.25;
      int _snowmanxxxxxxxxx = (int)(_snowmanxxxxxxxx * 0.5);
      double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 4.0;
      int _snowmanxxxxxxxxxxx = (int)(_snowmanxxxxxxxxxx * 0.5);
      this.client.getTextureManager().bindTexture(LOGO);
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.alphaFunc(516, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxxx);
      drawTexture(matrices, _snowmanxxxxxx - _snowmanxxxxxxxxxxx, _snowmanxxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, (int)_snowmanxxxxxxxx, -0.0625F, 0.0F, 120, 60, 120, 120);
      drawTexture(matrices, _snowmanxxxxxx, _snowmanxxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, (int)_snowmanxxxxxxxx, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.defaultBlendFunc();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.disableBlend();
      int _snowmanxxxxxxxxxxxx = (int)((double)this.client.getWindow().getScaledHeight() * 0.8325);
      float _snowmanxxxxxxxxxxxxx = this.reloadMonitor.getProgress();
      this.progress = MathHelper.clamp(this.progress * 0.95F + _snowmanxxxxxxxxxxxxx * 0.050000012F, 0.0F, 1.0F);
      if (_snowmanxxx < 1.0F) {
         this.renderProgressBar(
            matrices, _snowman / 2 - _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx - 5, _snowman / 2 + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx + 5, 1.0F - MathHelper.clamp(_snowmanxxx, 0.0F, 1.0F)
         );
      }

      if (_snowmanxxx >= 2.0F) {
         this.client.setOverlay(null);
      }

      if (this.applyCompleteTime == -1L && this.reloadMonitor.isApplyStageComplete() && (!this.reloading || _snowmanxxxx >= 2.0F)) {
         try {
            this.reloadMonitor.throwExceptions();
            this.exceptionHandler.accept(Optional.empty());
         } catch (Throwable var23) {
            this.exceptionHandler.accept(Optional.of(var23));
         }

         this.applyCompleteTime = Util.getMeasuringTimeMs();
         if (this.client.currentScreen != null) {
            this.client.currentScreen.init(this.client, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
         }
      }
   }

   private void renderProgressBar(MatrixStack matrices, int x1, int y1, int x2, int y2, float opacity) {
      int _snowman = MathHelper.ceil((float)(x2 - x1 - 2) * this.progress);
      int _snowmanx = Math.round(opacity * 255.0F);
      int _snowmanxx = BackgroundHelper.ColorMixer.getArgb(_snowmanx, 255, 255, 255);
      fill(matrices, x1 + 1, y1, x2 - 1, y1 + 1, _snowmanxx);
      fill(matrices, x1 + 1, y2, x2 - 1, y2 - 1, _snowmanxx);
      fill(matrices, x1, y1, x1 + 1, y2, _snowmanxx);
      fill(matrices, x2, y1, x2 - 1, y2, _snowmanxx);
      fill(matrices, x1 + 2, y1 + 2, x1 + _snowman, y2 - 2, _snowmanxx);
   }

   @Override
   public boolean pausesGame() {
      return true;
   }

   static class LogoTexture extends ResourceTexture {
      public LogoTexture() {
         super(SplashScreen.LOGO);
      }

      @Override
      protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
         MinecraftClient _snowman = MinecraftClient.getInstance();
         DefaultResourcePack _snowmanx = _snowman.getResourcePackDownloader().getPack();

         try (InputStream _snowmanxx = _snowmanx.open(ResourceType.CLIENT_RESOURCES, SplashScreen.LOGO)) {
            return new ResourceTexture.TextureData(new TextureResourceMetadata(true, true), NativeImage.read(_snowmanxx));
         } catch (IOException var18) {
            return new ResourceTexture.TextureData(var18);
         }
      }
   }
}
