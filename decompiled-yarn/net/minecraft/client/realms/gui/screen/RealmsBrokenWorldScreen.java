package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsWorldOptions;
import net.minecraft.client.realms.dto.WorldDownload;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.RealmsWorldSlotButton;
import net.minecraft.client.realms.task.OpenServerTask;
import net.minecraft.client.realms.task.SwitchSlotTask;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBrokenWorldScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Screen parent;
   private final RealmsMainScreen mainScreen;
   private RealmsServer field_20492;
   private final long serverId;
   private final Text field_24204;
   private final Text[] message = new Text[]{new TranslatableText("mco.brokenworld.message.line1"), new TranslatableText("mco.brokenworld.message.line2")};
   private int left_x;
   private int right_x;
   private final List<Integer> slotsThatHasBeenDownloaded = Lists.newArrayList();
   private int animTick;

   public RealmsBrokenWorldScreen(Screen parent, RealmsMainScreen mainScreen, long serverId, boolean _snowman) {
      this.parent = parent;
      this.mainScreen = mainScreen;
      this.serverId = serverId;
      this.field_24204 = _snowman ? new TranslatableText("mco.brokenworld.minigame.title") : new TranslatableText("mco.brokenworld.title");
   }

   @Override
   public void init() {
      this.left_x = this.width / 2 - 150;
      this.right_x = this.width / 2 + 190;
      this.addButton(new ButtonWidget(this.right_x - 80 + 8, row(13) - 5, 70, 20, ScreenTexts.BACK, _snowman -> this.backButtonClicked()));
      if (this.field_20492 == null) {
         this.fetchServerData(this.serverId);
      } else {
         this.addButtons();
      }

      this.client.keyboard.setRepeatEvents(true);
      Realms.narrateNow(Stream.concat(Stream.of(this.field_24204), Stream.of(this.message)).map(Text::getString).collect(Collectors.joining(" ")));
   }

   private void addButtons() {
      for (Entry<Integer, RealmsWorldOptions> _snowman : this.field_20492.slots.entrySet()) {
         int _snowmanx = _snowman.getKey();
         boolean _snowmanxx = _snowmanx != this.field_20492.activeSlot || this.field_20492.worldType == RealmsServer.WorldType.MINIGAME;
         ButtonWidget _snowmanxxx;
         if (_snowmanxx) {
            _snowmanxxx = new ButtonWidget(
               this.getFramePositionX(_snowmanx),
               row(8),
               80,
               20,
               new TranslatableText("mco.brokenworld.play"),
               _snowmanxxxx -> {
                  if (this.field_20492.slots.get(_snowman).empty) {
                     RealmsResetWorldScreen _snowmanxxxxx = new RealmsResetWorldScreen(
                        this,
                        this.field_20492,
                        new TranslatableText("mco.configure.world.switch.slot"),
                        new TranslatableText("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        ScreenTexts.CANCEL,
                        this::method_25123,
                        () -> {
                           this.client.openScreen(this);
                           this.method_25123();
                        }
                     );
                     _snowmanxxxxx.setSlot(_snowman);
                     _snowmanxxxxx.setResetTitle(new TranslatableText("mco.create.world.reset.title"));
                     this.client.openScreen(_snowmanxxxxx);
                  } else {
                     this.client.openScreen(new RealmsLongRunningMcoTaskScreen(this.parent, new SwitchSlotTask(this.field_20492.id, _snowman, this::method_25123)));
                  }
               }
            );
         } else {
            _snowmanxxx = new ButtonWidget(this.getFramePositionX(_snowmanx), row(8), 80, 20, new TranslatableText("mco.brokenworld.download"), _snowmanxxxx -> {
               Text _snowmanxxxxx = new TranslatableText("mco.configure.world.restore.download.question.line1");
               Text _snowmanxx = new TranslatableText("mco.configure.world.restore.download.question.line2");
               this.client.openScreen(new RealmsLongConfirmationScreen(_snowmanxxx -> {
                  if (_snowmanxxx) {
                     this.downloadWorld(_snowman);
                  } else {
                     this.client.openScreen(this);
                  }
               }, RealmsLongConfirmationScreen.Type.Info, _snowmanxxxxx, _snowmanxx, true));
            });
         }

         if (this.slotsThatHasBeenDownloaded.contains(_snowmanx)) {
            _snowmanxxx.active = false;
            _snowmanxxx.setMessage(new TranslatableText("mco.brokenworld.downloaded"));
         }

         this.addButton(_snowmanxxx);
         this.addButton(new ButtonWidget(this.getFramePositionX(_snowmanx), row(10), 80, 20, new TranslatableText("mco.brokenworld.reset"), _snowmanxxxx -> {
            RealmsResetWorldScreen _snowmanxxxxx = new RealmsResetWorldScreen(this, this.field_20492, this::method_25123, () -> {
               this.client.openScreen(this);
               this.method_25123();
            });
            if (_snowman != this.field_20492.activeSlot || this.field_20492.worldType == RealmsServer.WorldType.MINIGAME) {
               _snowmanxxxxx.setSlot(_snowman);
            }

            this.client.openScreen(_snowmanxxxxx);
         }));
      }
   }

   @Override
   public void tick() {
      this.animTick++;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      drawCenteredText(matrices, this.textRenderer, this.field_24204, this.width / 2, 17, 16777215);

      for (int _snowman = 0; _snowman < this.message.length; _snowman++) {
         drawCenteredText(matrices, this.textRenderer, this.message[_snowman], this.width / 2, row(-1) + 3 + _snowman * 12, 10526880);
      }

      if (this.field_20492 != null) {
         for (Entry<Integer, RealmsWorldOptions> _snowman : this.field_20492.slots.entrySet()) {
            if (_snowman.getValue().templateImage != null && _snowman.getValue().templateId != -1L) {
               this.drawSlotFrame(
                  matrices,
                  this.getFramePositionX(_snowman.getKey()),
                  row(1) + 5,
                  mouseX,
                  mouseY,
                  this.field_20492.activeSlot == _snowman.getKey() && !this.isMinigame(),
                  _snowman.getValue().getSlotName(_snowman.getKey()),
                  _snowman.getKey(),
                  _snowman.getValue().templateId,
                  _snowman.getValue().templateImage,
                  _snowman.getValue().empty
               );
            } else {
               this.drawSlotFrame(
                  matrices,
                  this.getFramePositionX(_snowman.getKey()),
                  row(1) + 5,
                  mouseX,
                  mouseY,
                  this.field_20492.activeSlot == _snowman.getKey() && !this.isMinigame(),
                  _snowman.getValue().getSlotName(_snowman.getKey()),
                  _snowman.getKey(),
                  -1L,
                  null,
                  _snowman.getValue().empty
               );
            }
         }
      }
   }

   private int getFramePositionX(int i) {
      return this.left_x + (i - 1) * 110;
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
      this.client.openScreen(this.parent);
   }

   private void fetchServerData(long worldId) {
      new Thread(() -> {
         RealmsClient _snowmanx = RealmsClient.createRealmsClient();

         try {
            this.field_20492 = _snowmanx.getOwnWorld(worldId);
            this.addButtons();
         } catch (RealmsServiceException var5) {
            LOGGER.error("Couldn't get own world");
            this.client.openScreen(new RealmsGenericErrorScreen(Text.of(var5.getMessage()), this.parent));
         }
      }).start();
   }

   public void method_25123() {
      new Thread(
            () -> {
               RealmsClient _snowman = RealmsClient.createRealmsClient();
               if (this.field_20492.state == RealmsServer.State.CLOSED) {
                  this.client
                     .execute(
                        () -> this.client
                              .openScreen(new RealmsLongRunningMcoTaskScreen(this, new OpenServerTask(this.field_20492, this, this.mainScreen, true)))
                     );
               } else {
                  try {
                     this.mainScreen.newScreen().play(_snowman.getOwnWorld(this.serverId), this);
                  } catch (RealmsServiceException var3) {
                     LOGGER.error("Couldn't get own world");
                     this.client.execute(() -> this.client.openScreen(this.parent));
                  }
               }
            }
         )
         .start();
   }

   private void downloadWorld(int slotId) {
      RealmsClient _snowman = RealmsClient.createRealmsClient();

      try {
         WorldDownload _snowmanx = _snowman.download(this.field_20492.id, slotId);
         RealmsDownloadLatestWorldScreen _snowmanxx = new RealmsDownloadLatestWorldScreen(this, _snowmanx, this.field_20492.getWorldName(slotId), _snowmanxxx -> {
            if (_snowmanxxx) {
               this.slotsThatHasBeenDownloaded.add(slotId);
               this.children.clear();
               this.addButtons();
            } else {
               this.client.openScreen(this);
            }
         });
         this.client.openScreen(_snowmanxx);
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't download world data");
         this.client.openScreen(new RealmsGenericErrorScreen(var5, this));
      }
   }

   private boolean isMinigame() {
      return this.field_20492 != null && this.field_20492.worldType == RealmsServer.WorldType.MINIGAME;
   }

   private void drawSlotFrame(MatrixStack _snowman, int y, int xm, int ym, int _snowman, boolean _snowman, String _snowman, int _snowman, long _snowman, String _snowman, boolean _snowman) {
      if (_snowman) {
         this.client.getTextureManager().bindTexture(RealmsWorldSlotButton.EMPTY_FRAME);
      } else if (_snowman != null && _snowman != -1L) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(_snowman), _snowman);
      } else if (_snowman == 1) {
         this.client.getTextureManager().bindTexture(RealmsWorldSlotButton.PANORAMA_0);
      } else if (_snowman == 2) {
         this.client.getTextureManager().bindTexture(RealmsWorldSlotButton.PANORAMA_2);
      } else if (_snowman == 3) {
         this.client.getTextureManager().bindTexture(RealmsWorldSlotButton.PANORAMA_3);
      } else {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(this.field_20492.minigameId), this.field_20492.minigameImage);
      }

      if (!_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else if (_snowman) {
         float _snowmanxxxxxxxx = 0.9F + 0.1F * MathHelper.cos((float)this.animTick * 0.2F);
         RenderSystem.color4f(_snowmanxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxx, 1.0F);
      }

      DrawableHelper.drawTexture(_snowman, y + 3, xm + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      this.client.getTextureManager().bindTexture(RealmsWorldSlotButton.SLOT_FRAME);
      if (_snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      DrawableHelper.drawTexture(_snowman, y, xm, 0.0F, 0.0F, 80, 80, 80, 80);
      drawCenteredString(_snowman, this.textRenderer, _snowman, y + 40, xm + 66, 16777215);
   }
}
