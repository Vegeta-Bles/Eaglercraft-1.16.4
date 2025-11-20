/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.Runnables
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.options.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen
extends Screen {
    private static final Logger field_23775 = LogManager.getLogger();
    public static final CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier("textures/gui/title/background/panorama"));
    private static final Identifier PANORAMA_OVERLAY = new Identifier("textures/gui/title/background/panorama_overlay.png");
    private static final Identifier ACCESSIBILITY_ICON_TEXTURE = new Identifier("textures/gui/accessibility.png");
    private final boolean isMinceraft;
    @Nullable
    private String splashText;
    private ButtonWidget buttonResetDemo;
    private static final Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");
    private static final Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
    private boolean realmsNotificationsInitialized;
    private Screen realmsNotificationGui;
    private int copyrightTextWidth;
    private int copyrightTextX;
    private final RotatingCubeMapRenderer backgroundRenderer = new RotatingCubeMapRenderer(PANORAMA_CUBE_MAP);
    private final boolean doBackgroundFade;
    private long backgroundFadeStart;

    public TitleScreen() {
        this(false);
    }

    public TitleScreen(boolean doBackgroundFade) {
        super(new TranslatableText("narrator.screen.title"));
        this.doBackgroundFade = doBackgroundFade;
        this.isMinceraft = (double)new Random().nextFloat() < 1.0E-4;
    }

    private boolean areRealmsNotificationsEnabled() {
        return this.client.options.realmsNotifications && this.realmsNotificationGui != null;
    }

    @Override
    public void tick() {
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotificationGui.tick();
        }
    }

    public static CompletableFuture<Void> loadTexturesAsync(TextureManager textureManager, Executor executor) {
        return CompletableFuture.allOf(textureManager.loadTextureAsync(MINECRAFT_TITLE_TEXTURE, executor), textureManager.loadTextureAsync(EDITION_TITLE_TEXTURE, executor), textureManager.loadTextureAsync(PANORAMA_OVERLAY, executor), PANORAMA_CUBE_MAP.loadTexturesAsync(textureManager, executor));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        if (this.splashText == null) {
            this.splashText = this.client.getSplashTextLoader().get();
        }
        this.copyrightTextWidth = this.textRenderer.getWidth("Copyright Mojang AB. Do not distribute!");
        this.copyrightTextX = this.width - this.copyrightTextWidth - 2;
        int n = 24;
        _snowman = this.height / 4 + 48;
        if (this.client.isDemo()) {
            this.initWidgetsDemo(_snowman, 24);
        } else {
            this.initWidgetsNormal(_snowman, 24);
        }
        this.addButton(new TexturedButtonWidget(this.width / 2 - 124, _snowman + 72 + 12, 20, 20, 0, 106, 20, ButtonWidget.WIDGETS_LOCATION, 256, 256, buttonWidget -> this.client.openScreen(new LanguageOptionsScreen((Screen)this, this.client.options, this.client.getLanguageManager())), new TranslatableText("narrator.button.language")));
        this.addButton(new ButtonWidget(this.width / 2 - 100, _snowman + 72 + 12, 98, 20, new TranslatableText("menu.options"), buttonWidget -> this.client.openScreen(new OptionsScreen(this, this.client.options))));
        this.addButton(new ButtonWidget(this.width / 2 + 2, _snowman + 72 + 12, 98, 20, new TranslatableText("menu.quit"), buttonWidget -> this.client.scheduleStop()));
        this.addButton(new TexturedButtonWidget(this.width / 2 + 104, _snowman + 72 + 12, 20, 20, 0, 0, 20, ACCESSIBILITY_ICON_TEXTURE, 32, 64, buttonWidget -> this.client.openScreen(new AccessibilityOptionsScreen(this, this.client.options)), new TranslatableText("narrator.button.accessibility")));
        this.client.setConnectedToRealms(false);
        if (this.client.options.realmsNotifications && !this.realmsNotificationsInitialized) {
            RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
            this.realmsNotificationGui = realmsBridgeScreen.getNotificationScreen(this);
            this.realmsNotificationsInitialized = true;
        }
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotificationGui.init(this.client, this.width, this.height);
        }
    }

    private void initWidgetsNormal(int y, int spacingY) {
        this.addButton(new ButtonWidget(this.width / 2 - 100, y, 200, 20, new TranslatableText("menu.singleplayer"), buttonWidget -> this.client.openScreen(new SelectWorldScreen(this))));
        boolean bl = this.client.isMultiplayerEnabled();
        ButtonWidget.TooltipSupplier _snowman2 = bl ? ButtonWidget.EMPTY : (buttonWidget, matrixStack, n, n2) -> {
            if (!buttonWidget.active) {
                this.renderOrderedTooltip(matrixStack, this.client.textRenderer.wrapLines(new TranslatableText("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), n, n2);
            }
        };
        this.addButton(new ButtonWidget((int)(this.width / 2 - 100), (int)(y + spacingY * 1), (int)200, (int)20, (Text)new TranslatableText((String)"menu.multiplayer"), (ButtonWidget.PressAction)(ButtonWidget.PressAction)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/widget/ButtonWidget;)V, method_19860(net.minecraft.client.gui.widget.ButtonWidget ), (Lnet/minecraft/client/gui/widget/ButtonWidget;)V)((TitleScreen)this), (ButtonWidget.TooltipSupplier)_snowman2)).active = bl;
        this.addButton(new ButtonWidget((int)(this.width / 2 - 100), (int)(y + spacingY * 2), (int)200, (int)20, (Text)new TranslatableText((String)"menu.online"), (ButtonWidget.PressAction)(ButtonWidget.PressAction)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/widget/ButtonWidget;)V, method_19859(net.minecraft.client.gui.widget.ButtonWidget ), (Lnet/minecraft/client/gui/widget/ButtonWidget;)V)((TitleScreen)this), (ButtonWidget.TooltipSupplier)_snowman2)).active = bl;
    }

    private void initWidgetsDemo(int y, int spacingY) {
        boolean bl = this.method_31129();
        this.addButton(new ButtonWidget(this.width / 2 - 100, y, 200, 20, new TranslatableText("menu.playdemo"), buttonWidget -> {
            if (bl) {
                this.client.startIntegratedServer("Demo_World");
            } else {
                DynamicRegistryManager.Impl impl = DynamicRegistryManager.create();
                this.client.method_29607("Demo_World", MinecraftServer.DEMO_LEVEL_INFO, impl, GeneratorOptions.method_31112(impl));
            }
        }));
        this.buttonResetDemo = this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 1, 200, 20, new TranslatableText("menu.resetdemo"), buttonWidget -> {
            LevelStorage levelStorage = this.client.getLevelStorage();
            try (LevelStorage.Session session = levelStorage.createSession("Demo_World");){
                LevelSummary levelSummary = session.getLevelSummary();
                if (levelSummary != null) {
                    this.client.openScreen(new ConfirmScreen(this::onDemoDeletionConfirmed, new TranslatableText("selectWorld.deleteQuestion"), new TranslatableText("selectWorld.deleteWarning", levelSummary.getDisplayName()), new TranslatableText("selectWorld.deleteButton"), ScreenTexts.CANCEL));
                }
            }
            catch (IOException iOException) {
                SystemToast.addWorldAccessFailureToast(this.client, "Demo_World");
                field_23775.warn("Failed to access demo world", (Throwable)iOException);
            }
        }));
        this.buttonResetDemo.active = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean method_31129() {
        try (LevelStorage.Session session = this.client.getLevelStorage().createSession("Demo_World");){
            boolean bl = session.getLevelSummary() != null;
            return bl;
        }
        catch (IOException iOException) {
            SystemToast.addWorldAccessFailureToast(this.client, "Demo_World");
            field_23775.warn("Failed to read demo world data", (Throwable)iOException);
            return false;
        }
    }

    private void switchToRealms() {
        RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
        realmsBridgeScreen.switchToRealms(this);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float f;
        if (this.backgroundFadeStart == 0L && this.doBackgroundFade) {
            this.backgroundFadeStart = Util.getMeasuringTimeMs();
        }
        float f2 = this.doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0f : 1.0f;
        TitleScreen.fill(matrices, 0, 0, this.width, this.height, -1);
        this.backgroundRenderer.render(delta, MathHelper.clamp(f2, 0.0f, 1.0f));
        int _snowman2 = 274;
        int _snowman3 = this.width / 2 - 137;
        int _snowman4 = 30;
        this.client.getTextureManager().bindTexture(PANORAMA_OVERLAY);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, this.doBackgroundFade ? (float)MathHelper.ceil(MathHelper.clamp(f2, 0.0f, 1.0f)) : 1.0f);
        TitleScreen.drawTexture(matrices, 0, 0, this.width, this.height, 0.0f, 0.0f, 16, 128, 16, 128);
        f = this.doBackgroundFade ? MathHelper.clamp(f2 - 1.0f, 0.0f, 1.0f) : 1.0f;
        int _snowman5 = MathHelper.ceil(f * 255.0f) << 24;
        if ((_snowman5 & 0xFC000000) == 0) {
            return;
        }
        this.client.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        if (this.isMinceraft) {
            this.method_29343(_snowman3, 30, (n, n2) -> {
                this.drawTexture(matrices, n + 0, (int)n2, 0, 0, 99, 44);
                this.drawTexture(matrices, n + 99, (int)n2, 129, 0, 27, 44);
                this.drawTexture(matrices, n + 99 + 26, (int)n2, 126, 0, 3, 44);
                this.drawTexture(matrices, n + 99 + 26 + 3, (int)n2, 99, 0, 26, 44);
                this.drawTexture(matrices, n + 155, (int)n2, 0, 45, 155, 44);
            });
        } else {
            this.method_29343(_snowman3, 30, (n, n2) -> {
                this.drawTexture(matrices, n + 0, (int)n2, 0, 0, 155, 44);
                this.drawTexture(matrices, n + 155, (int)n2, 0, 45, 155, 44);
            });
        }
        this.client.getTextureManager().bindTexture(EDITION_TITLE_TEXTURE);
        TitleScreen.drawTexture(matrices, _snowman3 + 88, 67, 0.0f, 0.0f, 98, 14, 128, 16);
        if (this.splashText != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(this.width / 2 + 90, 70.0f, 0.0f);
            RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
            _snowman = 1.8f - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0f * ((float)Math.PI * 2)) * 0.1f);
            _snowman = _snowman * 100.0f / (float)(this.textRenderer.getWidth(this.splashText) + 32);
            RenderSystem.scalef(_snowman, _snowman, _snowman);
            TitleScreen.drawCenteredString(matrices, this.textRenderer, this.splashText, 0, -8, 0xFFFF00 | _snowman5);
            RenderSystem.popMatrix();
        }
        String _snowman6 = "Minecraft " + SharedConstants.getGameVersion().getName();
        _snowman6 = this.client.isDemo() ? _snowman6 + " Demo" : _snowman6 + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType());
        if (this.client.isModded()) {
            _snowman6 = _snowman6 + I18n.translate("menu.modded", new Object[0]);
        }
        TitleScreen.drawStringWithShadow(matrices, this.textRenderer, _snowman6, 2, this.height - 10, 0xFFFFFF | _snowman5);
        TitleScreen.drawStringWithShadow(matrices, this.textRenderer, "Copyright Mojang AB. Do not distribute!", this.copyrightTextX, this.height - 10, 0xFFFFFF | _snowman5);
        if (mouseX > this.copyrightTextX && mouseX < this.copyrightTextX + this.copyrightTextWidth && mouseY > this.height - 10 && mouseY < this.height) {
            TitleScreen.fill(matrices, this.copyrightTextX, this.height - 1, this.copyrightTextX + this.copyrightTextWidth, this.height, 0xFFFFFF | _snowman5);
        }
        for (AbstractButtonWidget abstractButtonWidget : this.buttons) {
            abstractButtonWidget.setAlpha(f);
        }
        super.render(matrices, mouseX, mouseY, delta);
        if (this.areRealmsNotificationsEnabled() && f >= 1.0f) {
            this.realmsNotificationGui.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (this.areRealmsNotificationsEnabled() && this.realmsNotificationGui.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (mouseX > (double)this.copyrightTextX && mouseX < (double)(this.copyrightTextX + this.copyrightTextWidth) && mouseY > (double)(this.height - 10) && mouseY < (double)this.height) {
            this.client.openScreen(new CreditsScreen(false, Runnables.doNothing()));
        }
        return false;
    }

    @Override
    public void removed() {
        if (this.realmsNotificationGui != null) {
            this.realmsNotificationGui.removed();
        }
    }

    private void onDemoDeletionConfirmed(boolean delete) {
        if (delete) {
            try (LevelStorage.Session session = this.client.getLevelStorage().createSession("Demo_World");){
                session.deleteSessionLock();
            }
            catch (IOException iOException) {
                SystemToast.addWorldDeleteFailureToast(this.client, "Demo_World");
                field_23775.warn("Failed to delete demo world", (Throwable)iOException);
            }
        }
        this.client.openScreen(this);
    }

    private /* synthetic */ void method_19859(ButtonWidget buttonWidget) {
        this.switchToRealms();
    }

    private /* synthetic */ void method_19860(ButtonWidget buttonWidget) {
        Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
        this.client.openScreen(screen);
    }
}

