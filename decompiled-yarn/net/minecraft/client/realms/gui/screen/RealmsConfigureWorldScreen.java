package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
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
            _snowman -> this.client.openScreen(new RealmsPlayerScreen(this, this.server))
         )
      );
      this.settingsButton = this.addButton(
         new ButtonWidget(
            this.buttonCenter(1, 3),
            row(0),
            100,
            20,
            new TranslatableText("mco.configure.world.buttons.settings"),
            _snowman -> this.client.openScreen(new RealmsSettingsScreen(this, this.server.clone()))
         )
      );
      this.subscriptionButton = this.addButton(
         new ButtonWidget(
            this.buttonCenter(2, 3),
            row(0),
            100,
            20,
            new TranslatableText("mco.configure.world.buttons.subscription"),
            _snowman -> this.client.openScreen(new RealmsSubscriptionInfoScreen(this, this.server.clone(), this.parent))
         )
      );

      for (int _snowman = 1; _snowman < 5; _snowman++) {
         this.addSlotButton(_snowman);
      }

      this.switchMinigameButton = this.addButton(
         new ButtonWidget(this.buttonLeft(0), row(13) - 5, 100, 20, new TranslatableText("mco.configure.world.buttons.switchminigame"), _snowman -> {
            RealmsSelectWorldTemplateScreen _snowmanx = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.MINIGAME);
            _snowmanx.setTitle(new TranslatableText("mco.template.title.minigame"));
            this.client.openScreen(_snowmanx);
         })
      );
      this.optionsButton = this.addButton(
         new ButtonWidget(
            this.buttonLeft(0),
            row(13) - 5,
            90,
            20,
            new TranslatableText("mco.configure.world.buttons.options"),
            _snowman -> this.client
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
            _snowman -> this.client.openScreen(new RealmsBackupScreen(this, this.server.clone(), this.server.activeSlot))
         )
      );
      this.resetWorldButton = this.addButton(
         new ButtonWidget(
            this.buttonLeft(2),
            row(13) - 5,
            90,
            20,
            new TranslatableText("mco.configure.world.buttons.resetworld"),
            _snowman -> this.client
                  .openScreen(
                     new RealmsResetWorldScreen(
                        this, this.server.clone(), () -> this.client.openScreen(this.getNewScreen()), () -> this.client.openScreen(this.getNewScreen())
                     )
                  )
         )
      );
      this.addButton(new ButtonWidget(this.right_x - 80 + 8, row(13) - 5, 70, 20, ScreenTexts.BACK, _snowman -> this.backButtonClicked()));
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
      int _snowman = this.frame(slotIndex);
      int _snowmanx = row(5) + 5;
      RealmsWorldSlotButton _snowmanxx = new RealmsWorldSlotButton(_snowman, _snowmanx, 80, 80, () -> this.server, _snowmanxxx -> this.toolTip = _snowmanxxx, slotIndex, _snowmanxxx -> {
         RealmsWorldSlotButton.State _snowmanxxxx = ((RealmsWorldSlotButton)_snowmanxxx).getState();
         if (_snowmanxxxx != null) {
            switch (_snowmanxxxx.action) {
               case NOTHING:
                  break;
               case JOIN:
                  this.joinRealm(this.server);
                  break;
               case SWITCH_SLOT:
                  if (_snowmanxxxx.minigame) {
                     this.switchToMinigame();
                  } else if (_snowmanxxxx.empty) {
                     this.switchToEmptySlot(slotIndex, this.server);
                  } else {
                     this.switchToFullSlot(slotIndex, this.server);
                  }
                  break;
               default:
                  throw new IllegalStateException("Unknown action " + _snowmanxxxx.action);
            }
         }
      });
      this.addButton(_snowmanxx);
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
         String _snowman = this.server.getName();
         int _snowmanx = this.textRenderer.getWidth(_snowman);
         int _snowmanxx = this.server.state == RealmsServer.State.CLOSED ? 10526880 : 8388479;
         int _snowmanxxx = this.textRenderer.getWidth(field_26480);
         drawCenteredText(matrices, this.textRenderer, field_26480, this.width / 2, 12, 16777215);
         drawCenteredString(matrices, this.textRenderer, _snowman, this.width / 2, 24, _snowmanxx);
         int _snowmanxxxx = Math.min(this.buttonCenter(2, 3) + 80 - 11, this.width / 2 + _snowmanx / 2 + _snowmanxxx / 2 + 10);
         this.drawServerStatus(matrices, _snowmanxxxx, 7, mouseX, mouseY);
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
         RealmsClient _snowmanx = RealmsClient.createRealmsClient();

         try {
            this.server = _snowmanx.getOwnWorld(worldId);
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
      RealmsSelectWorldTemplateScreen _snowman = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.MINIGAME);
      _snowman.setTitle(new TranslatableText("mco.template.title.minigame"));
      _snowman.setWarning(new TranslatableText("mco.minigame.world.info.line1"), new TranslatableText("mco.minigame.world.info.line2"));
      this.client.openScreen(_snowman);
   }

   private void switchToFullSlot(int selectedSlot, RealmsServer serverData) {
      Text _snowman = new TranslatableText("mco.configure.world.slot.switch.question.line1");
      Text _snowmanx = new TranslatableText("mco.configure.world.slot.switch.question.line2");
      this.client
         .openScreen(
            new RealmsLongConfirmationScreen(
               _snowmanxx -> {
                  if (_snowmanxx) {
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
               _snowman,
               _snowmanx,
               true
            )
         );
   }

   private void switchToEmptySlot(int selectedSlot, RealmsServer serverData) {
      Text _snowman = new TranslatableText("mco.configure.world.slot.switch.question.line1");
      Text _snowmanx = new TranslatableText("mco.configure.world.slot.switch.question.line2");
      this.client
         .openScreen(
            new RealmsLongConfirmationScreen(
               _snowmanxx -> {
                  if (_snowmanxx) {
                     RealmsResetWorldScreen _snowmanxx = new RealmsResetWorldScreen(
                        this,
                        serverData,
                        new TranslatableText("mco.configure.world.switch.slot"),
                        new TranslatableText("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        ScreenTexts.CANCEL,
                        () -> this.client.openScreen(this.getNewScreen()),
                        () -> this.client.openScreen(this.getNewScreen())
                     );
                     _snowmanxx.setSlot(selectedSlot);
                     _snowmanxx.setResetTitle(new TranslatableText("mco.create.world.reset.title"));
                     this.client.openScreen(_snowmanxx);
                  } else {
                     this.client.openScreen(this);
                  }
               },
               RealmsLongConfirmationScreen.Type.Info,
               _snowman,
               _snowmanx,
               true
            )
         );
   }

   protected void renderMousehoverTooltip(MatrixStack _snowman, @Nullable Text _snowman, int _snowman, int _snowman) {
      int _snowmanxxxx = _snowman + 12;
      int _snowmanxxxxx = _snowman - 12;
      int _snowmanxxxxxx = this.textRenderer.getWidth(_snowman);
      if (_snowmanxxxx + _snowmanxxxxxx + 3 > this.right_x) {
         _snowmanxxxx = _snowmanxxxx - _snowmanxxxxxx - 20;
      }

      this.fillGradient(_snowman, _snowmanxxxx - 3, _snowmanxxxxx - 3, _snowmanxxxx + _snowmanxxxxxx + 3, _snowmanxxxxx + 8 + 3, -1073741824, -1073741824);
      this.textRenderer.drawWithShadow(_snowman, _snowman, (float)_snowmanxxxx, (float)_snowmanxxxxx, 16777215);
   }

   private void drawServerStatus(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      if (this.server.expired) {
         this.drawExpired(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (this.server.state == RealmsServer.State.CLOSED) {
         this.drawClosed(_snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (this.server.state == RealmsServer.State.OPEN) {
         if (this.server.daysLeft < 7) {
            this.drawExpiring(_snowman, _snowman, _snowman, _snowman, _snowman, this.server.daysLeft);
         } else {
            this.drawOpen(_snowman, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   private void drawExpired(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(EXPIRED_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         this.toolTip = field_26482;
      }
   }

   private void drawExpiring(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(EXPIRES_SOON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.animTick % 20 < 10) {
         DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         if (_snowman <= 0) {
            this.toolTip = field_26483;
         } else if (_snowman == 1) {
            this.toolTip = field_26484;
         } else {
            this.toolTip = new TranslatableText("mco.selectServer.expires.days", _snowman);
         }
      }
   }

   private void drawOpen(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(ON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
         this.toolTip = field_26485;
      }
   }

   private void drawClosed(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(OFF_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27) {
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
      RealmsWorldOptions _snowman = this.server.slots.get(this.server.activeSlot);
      options.templateId = _snowman.templateId;
      options.templateImage = _snowman.templateImage;
      RealmsClient _snowmanx = RealmsClient.createRealmsClient();

      try {
         _snowmanx.updateSlot(this.server.id, this.server.activeSlot, options);
         this.server.slots.put(this.server.activeSlot, options);
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't save slot settings");
         this.client.openScreen(new RealmsGenericErrorScreen(var5, this));
         return;
      }

      this.client.openScreen(this);
   }

   public void saveSettings(String name, String desc) {
      String _snowman = desc.trim().isEmpty() ? null : desc;
      RealmsClient _snowmanx = RealmsClient.createRealmsClient();

      try {
         _snowmanx.update(this.server.id, name, _snowman);
         this.server.setName(name);
         this.server.setDescription(_snowman);
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
