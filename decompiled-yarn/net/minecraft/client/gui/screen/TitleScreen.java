package net.minecraft.client.gui.screen;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
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

public class TitleScreen extends Screen {
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

   public static CompletableFuture<Void> loadTexturesAsync(TextureManager _snowman, Executor _snowman) {
      return CompletableFuture.allOf(
         _snowman.loadTextureAsync(MINECRAFT_TITLE_TEXTURE, _snowman),
         _snowman.loadTextureAsync(EDITION_TITLE_TEXTURE, _snowman),
         _snowman.loadTextureAsync(PANORAMA_OVERLAY, _snowman),
         PANORAMA_CUBE_MAP.loadTexturesAsync(_snowman, _snowman)
      );
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
      int _snowman = 24;
      int _snowmanx = this.height / 4 + 48;
      if (this.client.isDemo()) {
         this.initWidgetsDemo(_snowmanx, 24);
      } else {
         this.initWidgetsNormal(_snowmanx, 24);
      }

      this.addButton(
         new TexturedButtonWidget(
            this.width / 2 - 124,
            _snowmanx + 72 + 12,
            20,
            20,
            0,
            106,
            20,
            ButtonWidget.WIDGETS_LOCATION,
            256,
            256,
            _snowmanxx -> this.client.openScreen(new LanguageOptionsScreen(this, this.client.options, this.client.getLanguageManager())),
            new TranslatableText("narrator.button.language")
         )
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            _snowmanx + 72 + 12,
            98,
            20,
            new TranslatableText("menu.options"),
            _snowmanxx -> this.client.openScreen(new OptionsScreen(this, this.client.options))
         )
      );
      this.addButton(new ButtonWidget(this.width / 2 + 2, _snowmanx + 72 + 12, 98, 20, new TranslatableText("menu.quit"), _snowmanxx -> this.client.scheduleStop()));
      this.addButton(
         new TexturedButtonWidget(
            this.width / 2 + 104,
            _snowmanx + 72 + 12,
            20,
            20,
            0,
            0,
            20,
            ACCESSIBILITY_ICON_TEXTURE,
            32,
            64,
            _snowmanxx -> this.client.openScreen(new AccessibilityOptionsScreen(this, this.client.options)),
            new TranslatableText("narrator.button.accessibility")
         )
      );
      this.client.setConnectedToRealms(false);
      if (this.client.options.realmsNotifications && !this.realmsNotificationsInitialized) {
         RealmsBridgeScreen _snowmanxx = new RealmsBridgeScreen();
         this.realmsNotificationGui = _snowmanxx.getNotificationScreen(this);
         this.realmsNotificationsInitialized = true;
      }

      if (this.areRealmsNotificationsEnabled()) {
         this.realmsNotificationGui.init(this.client, this.width, this.height);
      }
   }

   private void initWidgetsNormal(int y, int spacingY) {
      this.addButton(
         new ButtonWidget(this.width / 2 - 100, y, 200, 20, new TranslatableText("menu.singleplayer"), _snowman -> this.client.openScreen(new SelectWorldScreen(this)))
      );
      boolean _snowman = this.client.isMultiplayerEnabled();
      ButtonWidget.TooltipSupplier _snowmanx = _snowman
         ? ButtonWidget.EMPTY
         : (_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx) -> {
            if (!_snowmanxx.active) {
               this.renderOrderedTooltip(
                  _snowmanxxx,
                  this.client.textRenderer.wrapLines(new TranslatableText("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)),
                  _snowmanxxxx,
                  _snowmanxxxxx
               );
            }
         };
      this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 1, 200, 20, new TranslatableText("menu.multiplayer"), _snowmanxx -> {
         Screen _snowmanx = (Screen)(this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
         this.client.openScreen(_snowmanx);
      }, _snowmanx)).active = _snowman;
      this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 2, 200, 20, new TranslatableText("menu.online"), _snowmanxx -> this.switchToRealms(), _snowmanx)).active = _snowman;
   }

   private void initWidgetsDemo(int y, int spacingY) {
      boolean _snowman = this.method_31129();
      this.addButton(new ButtonWidget(this.width / 2 - 100, y, 200, 20, new TranslatableText("menu.playdemo"), _snowmanx -> {
         if (_snowman) {
            this.client.startIntegratedServer("Demo_World");
         } else {
            DynamicRegistryManager.Impl _snowmanx = DynamicRegistryManager.create();
            this.client.method_29607("Demo_World", MinecraftServer.DEMO_LEVEL_INFO, _snowmanx, GeneratorOptions.method_31112(_snowmanx));
         }
      }));
      this.buttonResetDemo = this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            y + spacingY * 1,
            200,
            20,
            new TranslatableText("menu.resetdemo"),
            _snowmanx -> {
               LevelStorage _snowmanx = this.client.getLevelStorage();

               try (LevelStorage.Session _snowmanxx = _snowmanx.createSession("Demo_World")) {
                  LevelSummary _snowmanxxx = _snowmanxx.getLevelSummary();
                  if (_snowmanxxx != null) {
                     this.client
                        .openScreen(
                           new ConfirmScreen(
                              this::onDemoDeletionConfirmed,
                              new TranslatableText("selectWorld.deleteQuestion"),
                              new TranslatableText("selectWorld.deleteWarning", _snowmanxxx.getDisplayName()),
                              new TranslatableText("selectWorld.deleteButton"),
                              ScreenTexts.CANCEL
                           )
                        );
                  }
               } catch (IOException var16) {
                  SystemToast.addWorldAccessFailureToast(this.client, "Demo_World");
                  field_23775.warn("Failed to access demo world", var16);
               }
            }
         )
      );
      this.buttonResetDemo.active = _snowman;
   }

   private boolean method_31129() {
      try (LevelStorage.Session _snowman = this.client.getLevelStorage().createSession("Demo_World")) {
         return _snowman.getLevelSummary() != null;
      } catch (IOException var15) {
         SystemToast.addWorldAccessFailureToast(this.client, "Demo_World");
         field_23775.warn("Failed to read demo world data", var15);
         return false;
      }
   }

   private void switchToRealms() {
      RealmsBridgeScreen _snowman = new RealmsBridgeScreen();
      _snowman.switchToRealms(this);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.backgroundFadeStart == 0L && this.doBackgroundFade) {
         this.backgroundFadeStart = Util.getMeasuringTimeMs();
      }

      float _snowman = this.doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F : 1.0F;
      fill(matrices, 0, 0, this.width, this.height, -1);
      this.backgroundRenderer.render(delta, MathHelper.clamp(_snowman, 0.0F, 1.0F));
      int _snowmanx = 274;
      int _snowmanxx = this.width / 2 - 137;
      int _snowmanxxx = 30;
      this.client.getTextureManager().bindTexture(PANORAMA_OVERLAY);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.doBackgroundFade ? (float)MathHelper.ceil(MathHelper.clamp(_snowman, 0.0F, 1.0F)) : 1.0F);
      drawTexture(matrices, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
      float _snowmanxxxx = this.doBackgroundFade ? MathHelper.clamp(_snowman - 1.0F, 0.0F, 1.0F) : 1.0F;
      int _snowmanxxxxx = MathHelper.ceil(_snowmanxxxx * 255.0F) << 24;
      if ((_snowmanxxxxx & -67108864) != 0) {
         this.client.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxx);
         if (this.isMinceraft) {
            this.method_29343(_snowmanxx, 30, (_snowmanxxxxxx, _snowmanxxxxxxx) -> {
               this.drawTexture(matrices, _snowmanxxxxxx + 0, _snowmanxxxxxxx, 0, 0, 99, 44);
               this.drawTexture(matrices, _snowmanxxxxxx + 99, _snowmanxxxxxxx, 129, 0, 27, 44);
               this.drawTexture(matrices, _snowmanxxxxxx + 99 + 26, _snowmanxxxxxxx, 126, 0, 3, 44);
               this.drawTexture(matrices, _snowmanxxxxxx + 99 + 26 + 3, _snowmanxxxxxxx, 99, 0, 26, 44);
               this.drawTexture(matrices, _snowmanxxxxxx + 155, _snowmanxxxxxxx, 0, 45, 155, 44);
            });
         } else {
            this.method_29343(_snowmanxx, 30, (_snowmanxxxxxx, _snowmanxxxxxxx) -> {
               this.drawTexture(matrices, _snowmanxxxxxx + 0, _snowmanxxxxxxx, 0, 0, 155, 44);
               this.drawTexture(matrices, _snowmanxxxxxx + 155, _snowmanxxxxxxx, 0, 45, 155, 44);
            });
         }

         this.client.getTextureManager().bindTexture(EDITION_TITLE_TEXTURE);
         drawTexture(matrices, _snowmanxx + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
         if (this.splashText != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
            RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            float _snowmanxxxxxx = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
            _snowmanxxxxxx = _snowmanxxxxxx * 100.0F / (float)(this.textRenderer.getWidth(this.splashText) + 32);
            RenderSystem.scalef(_snowmanxxxxxx, _snowmanxxxxxx, _snowmanxxxxxx);
            drawCenteredString(matrices, this.textRenderer, this.splashText, 0, -8, 16776960 | _snowmanxxxxx);
            RenderSystem.popMatrix();
         }

         String _snowmanxxxxxx = "Minecraft " + SharedConstants.getGameVersion().getName();
         if (this.client.isDemo()) {
            _snowmanxxxxxx = _snowmanxxxxxx + " Demo";
         } else {
            _snowmanxxxxxx = _snowmanxxxxxx + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType());
         }

         if (this.client.isModded()) {
            _snowmanxxxxxx = _snowmanxxxxxx + I18n.translate("menu.modded");
         }

         drawStringWithShadow(matrices, this.textRenderer, _snowmanxxxxxx, 2, this.height - 10, 16777215 | _snowmanxxxxx);
         drawStringWithShadow(matrices, this.textRenderer, "Copyright Mojang AB. Do not distribute!", this.copyrightTextX, this.height - 10, 16777215 | _snowmanxxxxx);
         if (mouseX > this.copyrightTextX && mouseX < this.copyrightTextX + this.copyrightTextWidth && mouseY > this.height - 10 && mouseY < this.height) {
            fill(matrices, this.copyrightTextX, this.height - 1, this.copyrightTextX + this.copyrightTextWidth, this.height, 16777215 | _snowmanxxxxx);
         }

         for (AbstractButtonWidget _snowmanxxxxxxx : this.buttons) {
            _snowmanxxxxxxx.setAlpha(_snowmanxxxx);
         }

         super.render(matrices, mouseX, mouseY, delta);
         if (this.areRealmsNotificationsEnabled() && _snowmanxxxx >= 1.0F) {
            this.realmsNotificationGui.render(matrices, mouseX, mouseY, delta);
         }
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else if (this.areRealmsNotificationsEnabled() && this.realmsNotificationGui.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         if (mouseX > (double)this.copyrightTextX
            && mouseX < (double)(this.copyrightTextX + this.copyrightTextWidth)
            && mouseY > (double)(this.height - 10)
            && mouseY < (double)this.height) {
            this.client.openScreen(new CreditsScreen(false, Runnables.doNothing()));
         }

         return false;
      }
   }

   @Override
   public void removed() {
      if (this.realmsNotificationGui != null) {
         this.realmsNotificationGui.removed();
      }
   }

   private void onDemoDeletionConfirmed(boolean delete) {
      if (delete) {
         try (LevelStorage.Session _snowman = this.client.getLevelStorage().createSession("Demo_World")) {
            _snowman.deleteSessionLock();
         } catch (IOException var15) {
            SystemToast.addWorldDeleteFailureToast(this.client, "Demo_World");
            field_23775.warn("Failed to delete demo world", var15);
         }
      }

      this.client.openScreen(this);
   }
}
