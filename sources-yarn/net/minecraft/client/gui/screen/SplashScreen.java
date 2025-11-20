package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int k = this.client.getWindow().getScaledWidth();
      int l = this.client.getWindow().getScaledHeight();
      long m = Util.getMeasuringTimeMs();
      if (this.reloading && (this.reloadMonitor.isPrepareStageComplete() || this.client.currentScreen != null) && this.prepareCompleteTime == -1L) {
         this.prepareCompleteTime = m;
      }

      float g = this.applyCompleteTime > -1L ? (float)(m - this.applyCompleteTime) / 1000.0F : -1.0F;
      float h = this.prepareCompleteTime > -1L ? (float)(m - this.prepareCompleteTime) / 500.0F : -1.0F;
      float o;
      if (g >= 1.0F) {
         if (this.client.currentScreen != null) {
            this.client.currentScreen.render(matrices, 0, 0, delta);
         }

         int n = MathHelper.ceil((1.0F - MathHelper.clamp(g - 1.0F, 0.0F, 1.0F)) * 255.0F);
         fill(matrices, 0, 0, k, l, BRAND_RGB | n << 24);
         o = 1.0F - MathHelper.clamp(g - 1.0F, 0.0F, 1.0F);
      } else if (this.reloading) {
         if (this.client.currentScreen != null && h < 1.0F) {
            this.client.currentScreen.render(matrices, mouseX, mouseY, delta);
         }

         int p = MathHelper.ceil(MathHelper.clamp((double)h, 0.15, 1.0) * 255.0);
         fill(matrices, 0, 0, k, l, BRAND_RGB | p << 24);
         o = MathHelper.clamp(h, 0.0F, 1.0F);
      } else {
         fill(matrices, 0, 0, k, l, BRAND_ARGB);
         o = 1.0F;
      }

      int s = (int)((double)this.client.getWindow().getScaledWidth() * 0.5);
      int t = (int)((double)this.client.getWindow().getScaledHeight() * 0.5);
      double d = Math.min((double)this.client.getWindow().getScaledWidth() * 0.75, (double)this.client.getWindow().getScaledHeight()) * 0.25;
      int u = (int)(d * 0.5);
      double e = d * 4.0;
      int v = (int)(e * 0.5);
      this.client.getTextureManager().bindTexture(LOGO);
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.alphaFunc(516, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, o);
      drawTexture(matrices, s - v, t - u, v, (int)d, -0.0625F, 0.0F, 120, 60, 120, 120);
      drawTexture(matrices, s, t - u, v, (int)d, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.defaultBlendFunc();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.disableBlend();
      int w = (int)((double)this.client.getWindow().getScaledHeight() * 0.8325);
      float x = this.reloadMonitor.getProgress();
      this.progress = MathHelper.clamp(this.progress * 0.95F + x * 0.050000012F, 0.0F, 1.0F);
      if (g < 1.0F) {
         this.renderProgressBar(matrices, k / 2 - v, w - 5, k / 2 + v, w + 5, 1.0F - MathHelper.clamp(g, 0.0F, 1.0F));
      }

      if (g >= 2.0F) {
         this.client.setOverlay(null);
      }

      if (this.applyCompleteTime == -1L && this.reloadMonitor.isApplyStageComplete() && (!this.reloading || h >= 2.0F)) {
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
      int m = MathHelper.ceil((float)(x2 - x1 - 2) * this.progress);
      int n = Math.round(opacity * 255.0F);
      int o = BackgroundHelper.ColorMixer.getArgb(n, 255, 255, 255);
      fill(matrices, x1 + 1, y1, x2 - 1, y1 + 1, o);
      fill(matrices, x1 + 1, y2, x2 - 1, y2 - 1, o);
      fill(matrices, x1, y1, x1 + 1, y2, o);
      fill(matrices, x2, y1, x2 - 1, y2, o);
      fill(matrices, x1 + 2, y1 + 2, x1 + m, y2 - 2, o);
   }

   @Override
   public boolean pausesGame() {
      return true;
   }

   @Environment(EnvType.CLIENT)
   static class LogoTexture extends ResourceTexture {
      public LogoTexture() {
         super(SplashScreen.LOGO);
      }

      @Override
      protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
         MinecraftClient lv = MinecraftClient.getInstance();
         DefaultResourcePack lv2 = lv.getResourcePackDownloader().getPack();

         try (InputStream inputStream = lv2.open(ResourceType.CLIENT_RESOURCES, SplashScreen.LOGO)) {
            return new ResourceTexture.TextureData(new TextureResourceMetadata(true, true), NativeImage.read(inputStream));
         } catch (IOException var18) {
            return new ResourceTexture.TextureData(var18);
         }
      }
   }
}
