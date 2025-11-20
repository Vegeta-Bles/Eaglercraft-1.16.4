package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsWorldOptions;
import net.minecraft.client.realms.dto.WorldTemplate;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.RealmsWorldSlotButton;
import net.minecraft.client.realms.task.CloseServerTask;
import net.minecraft.client.realms.task.OpenServerTask;
import net.minecraft.client.realms.task.SwitchMinigameTask;
import net.minecraft.client.realms.task.SwitchSlotTask;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsConfigureWorldScreen extends RealmsScreenWithCallback {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier ON_ICON = new Identifier("realms", "textures/gui/realms/on_icon.png");
   private static final Identifier OFF_ICON = new Identifier("realms", "textures/gui/realms/off_icon.png");
   private static final Identifier EXPIRED_ICON = new Identifier("realms", "textures/gui/realms/expired_icon.png");
   private static final Identifier EXPIRES_SOON_ICON = new Identifier("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final Text field_26479 = new TranslatableText("mco.configure.worlds.title");
   private static final Text field_26480 = new TranslatableText("mco.configure.world.title");
   private static final Text field_26481 = new TranslatableText("mco.configure.current.minigame").append(": ");
   private static final Text field_26482 = new TranslatableText("mco.selectServer.expired");
   private static final Text field_26483 = new TranslatableText("mco.selectServer.expires.soon");
   private static final Text field_26484 = new TranslatableText("mco.selectServer.expires.day");
   private static final Text field_26485 = new TranslatableText("mco.selectServer.open");
   private static final Text field_26486 = new TranslatableText("mco.selectServer.closed");
   @Nullable
   private Text toolTip;
   private final RealmsMainScreen parent;
   @Nullable
   private RealmsServer server;
   private final long serverId;
   private int left_x;
   private int right_x;
   private ButtonWidget playersButton;
   private ButtonWidget settingsButton;
   private ButtonWidget subscriptionButton;
   private ButtonWidget optionsButton;
   private ButtonWidget backupButton;
   private ButtonWidget resetWorldButton;
   private ButtonWidget switchMinigameButton;
   private boolean stateChanged;
   private int animTick;
   private int clicks;

   public RealmsConfigureWorldScreen(RealmsMainScreen parent, long serverId) {
      this.parent = parent;
      this.serverId = serverId;
   }

   @Override
   public void init() {
      if (this.server == null) {
         this.fetchServerData(this.serverId);
      }

      this.left_x = this.width / 2 - 187;
      this.right_x = this.width / 2 + 190;
      this.client.keyboard.setRepeatEvents(true);
      this.playersButton = this.addButton(
         new ButtonWidget(
            this.buttonCenter(0, 3),
            row(0),
            100,
            20,
            new TranslatableText("mco.configure.world.buttons.players"),
            arg -> this.client.openScreen(new RealmsPlayerScreen(this, this.server))
         )
      );
      this.settingsButton = this.addButton(
         new ButtonWidget(
            this.buttonCenter(1, 3),
            row(0),
            100,
            20,
            new TranslatableText("mco.configure.world.buttons.settings"),
            arg -> this.client.openScreen(new RealmsSettingsScreen(this, this.server.clone()))
         )
      );
      this.subscriptionButton = this.addButton(
         new ButtonWidget(
            this.buttonCenter(2, 3),
            row(0),
            100,
            20,
            new TranslatableText("mco.configure.world.buttons.subscription"),
            arg -> this.client.openScreen(new RealmsSubscriptionInfoScreen(this, this.server.clone(), this.parent))
         )
      );

      for (int i = 1; i < 5; i++) {
         this.addSlotButton(i);
      }

      this.switchMinigameButton = this.addButton(
         new ButtonWidget(this.buttonLeft(0), row(13) - 5, 100, 20, new TranslatableText("mco.configure.world.buttons.switchminigame"), arg -> {
            RealmsSelectWorldTemplateScreen lv = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.MINIGAME);
            lv.setTitle(new TranslatableText("mco.template.title.minigame"));
            this.client.openScreen(lv);
         })
      );
      this.optionsButton = this.addButton(
         new ButtonWidget(
            this.buttonLeft(0),
            row(13) - 5,
            90,
            20,
            new TranslatableText("mco.configure.world.buttons.options"),
            arg -> this.client
                  .openScreen(
                     new RealmsSlotOptionsScreen(this, this.server.slots.get(this.server.activeSlot).clone(), this.server.worldType, this.server.activeSlot)
                  )
         )
      );
      this.backupButton = this.addButton(
         new ButtonWidget(
            this.buttonLeft(1),
            row(13) - 5,
            90,
            20,
            new TranslatableText("mco.configure.world.backup"),
            arg -> this.client.openScreen(new RealmsBackupScreen(this, this.server.clone(), this.server.activeSlot))
         )
      );
      this.resetWorldButton = this.addButton(
         new ButtonWidget(
            this.buttonLeft(2),
            row(13) - 5,
            90,
            20,
            new TranslatableText("mco.configure.world.buttons.resetworld"),
            arg -> this.client
                  .openScreen(
                     new RealmsResetWorldScreen(
                        this, this.server.clone(), () -> this.client.openScreen(this.getNewScreen()), () -> this.client.openScreen(this.getNewScreen())
                     )
                  )
         )
      );
      this.addButton(new ButtonWidget(this.right_x - 80 + 8, row(13) - 5, 70, 20, ScreenTexts.BACK, arg -> this.backButtonClicked()));
      this.backupButton.active = true;
      if (this.server == null) {
         this.hideMinigameButtons();
         this.hideRegularButtons();
         this.playersButton.active = false;
         this.settingsButton.active = false;
         this.subscriptionButton.active = false;
      } else {
         this.disableButtons();
         if (this.isMinigame()) {
            this.hideRegularButtons();
         } else {
            this.hideMinigameButtons();
         }
      }
   }

   private void addSlotButton(int slotIndex) {
      int j = this.frame(slotIndex);
      int k = row(5) + 5;
      RealmsWorldSlotButton lv = new RealmsWorldSlotButton(j, k, 80, 80, () -> this.server, arg -> this.toolTip = arg, slotIndex, arg -> {
         RealmsWorldSlotButton.State lvx = ((RealmsWorldSlotButton)arg).getState();
         if (lvx != null) {
            switch (lvx.action) {
               case NOTHING:
                  break;
               case JOIN:
                  this.joinRealm(this.server);
                  break;
               case SWITCH_SLOT:
                  if (lvx.minigame) {
                     this.switchToMinigame();
                  } else if (lvx.empty) {
                     this.switchToEmptySlot(slotIndex, this.server);
                  } else {
                     this.switchToFullSlot(slotIndex, this.server);
                  }
                  break;
               default:
                  throw new IllegalStateException("Unknown action " + lvx.action);
            }
         }
      });
      this.addButton(lv);
   }

   private int buttonLeft(int i) {
      return this.left_x + i * 95;
   }

   private int buttonCenter(int i, int total) {
      return this.width / 2 - (total * 105 - 5) / 2 + i * 105;
   }

   @Override
   public void tick() {
      super.tick();
      this.animTick++;
      this.clicks--;
      if (this.clicks < 0) {
         this.clicks = 0;
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.toolTip = null;
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, field_26479, this.width / 2, row(4), 16777215);
      super.render(matrices, mouseX, mouseY, delta);
      if (this.server == null) {
         drawCenteredText(matrices, this.textRenderer, field_26480, this.width / 2, 17, 16777215);
      } else {
         String string = this.server.getName();
         int k = this.textRenderer.getWidth(string);
         int l = this.server.state == RealmsServer.State.CLOSED ? 10526880 : 8388479;
         int m = this.textRenderer.getWidth(field_26480);
         drawCenteredText(matrices, this.textRenderer, field_26480, this.width / 2, 12, 16777215);
         drawCenteredString(matrices, this.textRenderer, string, this.width / 2, 24, l);
         int n = Math.min(this.buttonCenter(2, 3) + 80 - 11, this.width / 2 + k / 2 + m / 2 + 10);
         this.drawServerStatus(matrices, n, 7, mouseX, mouseY);
         if (this.isMinigame()) {
            this.textRenderer
               .draw(matrices, field_26481.shallowCopy().append(this.server.getMinigameName()), (float)(this.left_x + 80 + 20 + 10), (float)row(13), 16777215);
         }

         if (this.toolTip != null) {
            this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
         }
      }
   }

   private int frame(int ordinal) {
      return this.left_x + (ordinal - 1) * 98;
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.backButtonClicked();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void backButtonClicked() {
      if (this.stateChanged) {
         this.parent.removeSelection();
      }

      this.client.openScreen(this.parent);
   }

   private void fetchServerData(long worldId) {
      new Thread(() -> {
         RealmsClient lv = RealmsClient.createRealmsClient();

         try {
            this.server = lv.getOwnWorld(worldId);
            this.disableButtons();
            if (this.isMinigame()) {
               this.addButton(this.switchMinigameButton);
            } else {
               this.addButton(this.optionsButton);
               this.addButton(this.backupButton);
               this.addButton(this.resetWorldButton);
            }
         } catch (RealmsServiceException var5) {
            LOGGER.error("Couldn't get own world");
            this.client.execute(() -> this.client.openScreen(new RealmsGenericErrorScreen(Text.of(var5.getMessage()), this.parent)));
         }
      }).start();
   }

   private void disableButtons() {
      this.playersButton.active = !this.server.expired;
      this.settingsButton.active = !this.server.expired;
      this.subscriptionButton.active = true;
      this.switchMinigameButton.active = !this.server.expired;
      this.optionsButton.active = !this.server.expired;
      this.resetWorldButton.active = !this.server.expired;
   }

   private void joinRealm(RealmsServer serverData) {
      if (this.server.state == RealmsServer.State.OPEN) {
         this.parent.play(serverData, new RealmsConfigureWorldScreen(this.parent.newScreen(), this.serverId));
      } else {
         this.openTheWorld(true, new RealmsConfigureWorldScreen(this.parent.newScreen(), this.serverId));
      }
   }

   private void switchToMinigame() {
      RealmsSelectWorldTemplateScreen lv = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.MINIGAME);
      lv.setTitle(new TranslatableText("mco.template.title.minigame"));
      lv.setWarning(new TranslatableText("mco.minigame.world.info.line1"), new TranslatableText("mco.minigame.world.info.line2"));
      this.client.openScreen(lv);
   }

   private void switchToFullSlot(int selectedSlot, RealmsServer serverData) {
      Text lv = new TranslatableText("mco.configure.world.slot.switch.question.line1");
      Text lv2 = new TranslatableText("mco.configure.world.slot.switch.question.line2");
      this.client
         .openScreen(
            new RealmsLongConfirmationScreen(
               bl -> {
                  if (bl) {
                     this.client
                        .openScreen(
                           new RealmsLongRunningMcoTaskScreen(
                              this.parent, new SwitchSlotTask(serverData.id, selectedSlot, () -> this.client.openScreen(this.getNewScreen()))
                           )
                        );
                  } else {
                     this.client.openScreen(this);
                  }
               },
               RealmsLongConfirmationScreen.Type.Info,
               lv,
               lv2,
               true
            )
         );
   }

   private void switchToEmptySlot(int selectedSlot, RealmsServer serverData) {
      Text lv = new TranslatableText("mco.configure.world.slot.switch.question.line1");
      Text lv2 = new TranslatableText("mco.configure.world.slot.switch.question.line2");
      this.client
         .openScreen(
            new RealmsLongConfirmationScreen(
               bl -> {
                  if (bl) {
                     RealmsResetWorldScreen lvx = new RealmsResetWorldScreen(
                        this,
                        serverData,
                        new TranslatableText("mco.configure.world.switch.slot"),
                        new TranslatableText("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        ScreenTexts.CANCEL,
                        () -> this.client.openScreen(this.getNewScreen()),
                        () -> this.client.openScreen(this.getNewScreen())
                     );
                     lvx.setSlot(selectedSlot);
                     lvx.setResetTitle(new TranslatableText("mco.create.world.reset.title"));
                     this.client.openScreen(lvx);
                  } else {
                     this.client.openScreen(this);
                  }
               },
               RealmsLongConfirmationScreen.Type.Info,
               lv,
               lv2,
               true
            )
         );
   }

   protected void renderMousehoverTooltip(MatrixStack arg, @Nullable Text arg2, int i, int j) {
      int k = i + 12;
      int l = j - 12;
      int m = this.textRenderer.getWidth(arg2);
      if (k + m + 3 > this.right_x) {
         k = k - m - 20;
      }

      this.fillGradient(arg, k - 3, l - 3, k + m + 3, l + 8 + 3, -1073741824, -1073741824);
      this.textRenderer.drawWithShadow(arg, arg2, (float)k, (float)l, 16777215);
   }

   private void drawServerStatus(MatrixStack arg, int i, int j, int k, int l) {
      if (this.server.expired) {
         this.drawExpired(arg, i, j, k, l);
      } else if (this.server.state == RealmsServer.State.CLOSED) {
         this.drawClosed(arg, i, j, k, l);
      } else if (this.server.state == RealmsServer.State.OPEN) {
         if (this.server.daysLeft < 7) {
            this.drawExpiring(arg, i, j, k, l, this.server.daysLeft);
         } else {
            this.drawOpen(arg, i, j, k, l);
         }
      }
   }

   private void drawExpired(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(EXPIRED_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27) {
         this.toolTip = field_26482;
      }
   }

   private void drawExpiring(MatrixStack arg, int i, int j, int k, int l, int m) {
      this.client.getTextureManager().bindTexture(EXPIRES_SOON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.animTick % 20 < 10) {
         DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         DrawableHelper.drawTexture(arg, i, j, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (k >= i && k <= i + 9 && l >= j && l <= j + 27) {
         if (m <= 0) {
            this.toolTip = field_26483;
         } else if (m == 1) {
            this.toolTip = field_26484;
         } else {
            this.toolTip = new TranslatableText("mco.selectServer.expires.days", m);
         }
      }
   }

   private void drawOpen(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(ON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27) {
         this.toolTip = field_26485;
      }
   }

   private void drawClosed(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(OFF_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27) {
         this.toolTip = field_26486;
      }
   }

   private boolean isMinigame() {
      return this.server != null && this.server.worldType == RealmsServer.WorldType.MINIGAME;
   }

   private void hideRegularButtons() {
      this.removeButton(this.optionsButton);
      this.removeButton(this.backupButton);
      this.removeButton(this.resetWorldButton);
   }

   private void removeButton(ButtonWidget button) {
      button.visible = false;
      this.children.remove(button);
      this.buttons.remove(button);
   }

   private void addButton(ButtonWidget button) {
      button.visible = true;
      this.addButton(button);
   }

   private void hideMinigameButtons() {
      this.removeButton(this.switchMinigameButton);
   }

   public void saveSlotSettings(RealmsWorldOptions options) {
      RealmsWorldOptions lv = this.server.slots.get(this.server.activeSlot);
      options.templateId = lv.templateId;
      options.templateImage = lv.templateImage;
      RealmsClient lv2 = RealmsClient.createRealmsClient();

      try {
         lv2.updateSlot(this.server.id, this.server.activeSlot, options);
         this.server.slots.put(this.server.activeSlot, options);
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't save slot settings");
         this.client.openScreen(new RealmsGenericErrorScreen(var5, this));
         return;
      }

      this.client.openScreen(this);
   }

   public void saveSettings(String name, String desc) {
      String string3 = desc.trim().isEmpty() ? null : desc;
      RealmsClient lv = RealmsClient.createRealmsClient();

      try {
         lv.update(this.server.id, name, string3);
         this.server.setName(name);
         this.server.setDescription(string3);
      } catch (RealmsServiceException var6) {
         LOGGER.error("Couldn't save settings");
         this.client.openScreen(new RealmsGenericErrorScreen(var6, this));
         return;
      }

      this.client.openScreen(this);
   }

   public void openTheWorld(boolean join, Screen screen) {
      this.client.openScreen(new RealmsLongRunningMcoTaskScreen(screen, new OpenServerTask(this.server, this, this.parent, join)));
   }

   public void closeTheWorld(Screen screen) {
      this.client.openScreen(new RealmsLongRunningMcoTaskScreen(screen, new CloseServerTask(this.server, this)));
   }

   public void stateChanged() {
      this.stateChanged = true;
   }

   @Override
   protected void callback(@Nullable WorldTemplate template) {
      if (template != null) {
         if (WorldTemplate.WorldTemplateType.MINIGAME == template.type) {
            this.client.openScreen(new RealmsLongRunningMcoTaskScreen(this.parent, new SwitchMinigameTask(this.server.id, template, this.getNewScreen())));
         }
      }
   }

   public RealmsConfigureWorldScreen getNewScreen() {
      return new RealmsConfigureWorldScreen(this.parent, this.serverId);
   }
}
