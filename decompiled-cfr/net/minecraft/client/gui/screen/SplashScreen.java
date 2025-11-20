/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.screen.Overlay;
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

public class SplashScreen
extends Overlay {
    private static final Identifier LOGO = new Identifier("textures/gui/title/mojangstudios.png");
    private static final int BRAND_ARGB = BackgroundHelper.ColorMixer.getArgb(255, 239, 50, 61);
    private static final int BRAND_RGB = BRAND_ARGB & 0xFFFFFF;
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
        client.getTextureManager().registerTexture(LOGO, new LogoTexture());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int _snowman4;
        int n = this.client.getWindow().getScaledWidth();
        _snowman = this.client.getWindow().getScaledHeight();
        long _snowman2 = Util.getMeasuringTimeMs();
        if (this.reloading && (this.reloadMonitor.isPrepareStageComplete() || this.client.currentScreen != null) && this.prepareCompleteTime == -1L) {
            this.prepareCompleteTime = _snowman2;
        }
        float _snowman3 = this.applyCompleteTime > -1L ? (float)(_snowman2 - this.applyCompleteTime) / 1000.0f : -1.0f;
        float f = f3 = this.prepareCompleteTime > -1L ? (float)(_snowman2 - this.prepareCompleteTime) / 500.0f : -1.0f;
        if (_snowman3 >= 1.0f) {
            if (this.client.currentScreen != null) {
                this.client.currentScreen.render(matrices, 0, 0, delta);
            }
            _snowman4 = MathHelper.ceil((1.0f - MathHelper.clamp(_snowman3 - 1.0f, 0.0f, 1.0f)) * 255.0f);
            SplashScreen.fill(matrices, 0, 0, n, _snowman, BRAND_RGB | _snowman4 << 24);
            float f2 = 1.0f - MathHelper.clamp(_snowman3 - 1.0f, 0.0f, 1.0f);
        } else if (this.reloading) {
            float f3;
            if (this.client.currentScreen != null && f3 < 1.0f) {
                this.client.currentScreen.render(matrices, mouseX, mouseY, delta);
            }
            _snowman4 = MathHelper.ceil(MathHelper.clamp((double)f3, 0.15, 1.0) * 255.0);
            SplashScreen.fill(matrices, 0, 0, n, _snowman, BRAND_RGB | _snowman4 << 24);
            f2 = MathHelper.clamp(f3, 0.0f, 1.0f);
        } else {
            SplashScreen.fill(matrices, 0, 0, n, _snowman, BRAND_ARGB);
            f2 = 1.0f;
        }
        _snowman4 = (int)((double)this.client.getWindow().getScaledWidth() * 0.5);
        int n2 = (int)((double)this.client.getWindow().getScaledHeight() * 0.5);
        double _snowman5 = Math.min((double)this.client.getWindow().getScaledWidth() * 0.75, (double)this.client.getWindow().getScaledHeight()) * 0.25;
        _snowman = (int)(_snowman5 * 0.5);
        double _snowman6 = _snowman5 * 4.0;
        _snowman = (int)(_snowman6 * 0.5);
        this.client.getTextureManager().bindTexture(LOGO);
        RenderSystem.enableBlend();
        RenderSystem.blendEquation(32774);
        RenderSystem.blendFunc(770, 1);
        RenderSystem.alphaFunc(516, 0.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f2);
        SplashScreen.drawTexture(matrices, _snowman4 - _snowman, n2 - _snowman, _snowman, (int)_snowman5, -0.0625f, 0.0f, 120, 60, 120, 120);
        SplashScreen.drawTexture(matrices, _snowman4, n2 - _snowman, _snowman, (int)_snowman5, 0.0625f, 60.0f, 120, 60, 120, 120);
        RenderSystem.defaultBlendFunc();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.disableBlend();
        _snowman = (int)((double)this.client.getWindow().getScaledHeight() * 0.8325);
        float _snowman7 = this.reloadMonitor.getProgress();
        this.progress = MathHelper.clamp(this.progress * 0.95f + _snowman7 * 0.050000012f, 0.0f, 1.0f);
        if (_snowman3 < 1.0f) {
            this.renderProgressBar(matrices, n / 2 - _snowman, _snowman - 5, n / 2 + _snowman, _snowman + 5, 1.0f - MathHelper.clamp(_snowman3, 0.0f, 1.0f));
        }
        if (_snowman3 >= 2.0f) {
            this.client.setOverlay(null);
        }
        if (this.applyCompleteTime == -1L && this.reloadMonitor.isApplyStageComplete() && (!this.reloading || f3 >= 2.0f)) {
            try {
                this.reloadMonitor.throwExceptions();
                this.exceptionHandler.accept(Optional.empty());
            }
            catch (Throwable throwable) {
                this.exceptionHandler.accept(Optional.of(throwable));
            }
            this.applyCompleteTime = Util.getMeasuringTimeMs();
            if (this.client.currentScreen != null) {
                this.client.currentScreen.init(this.client, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
            }
        }
    }

    private void renderProgressBar(MatrixStack matrices, int x1, int y1, int x2, int y2, float opacity) {
        int n = MathHelper.ceil((float)(x2 - x1 - 2) * this.progress);
        _snowman = Math.round(opacity * 255.0f);
        _snowman = BackgroundHelper.ColorMixer.getArgb(_snowman, 255, 255, 255);
        SplashScreen.fill(matrices, x1 + 1, y1, x2 - 1, y1 + 1, _snowman);
        SplashScreen.fill(matrices, x1 + 1, y2, x2 - 1, y2 - 1, _snowman);
        SplashScreen.fill(matrices, x1, y1, x1 + 1, y2, _snowman);
        SplashScreen.fill(matrices, x2, y1, x2 - 1, y2, _snowman);
        SplashScreen.fill(matrices, x1 + 2, y1 + 2, x1 + n, y2 - 2, _snowman);
    }

    @Override
    public boolean pausesGame() {
        return true;
    }

    static class LogoTexture
    extends ResourceTexture {
        public LogoTexture() {
            super(LOGO);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            DefaultResourcePack _snowman2 = minecraftClient.getResourcePackDownloader().getPack();
            try (InputStream _snowman3 = _snowman2.open(ResourceType.CLIENT_RESOURCES, LOGO);){
                ResourceTexture.TextureData textureData = new ResourceTexture.TextureData(new TextureResourceMetadata(true, true), NativeImage.read(_snowman3));
                return textureData;
            }
            catch (IOException iOException) {
                return new ResourceTexture.TextureData(iOException);
            }
        }
    }
}

