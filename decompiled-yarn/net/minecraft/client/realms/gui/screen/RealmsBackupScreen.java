package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.Backup;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.task.DownloadTask;
import net.minecraft.client.realms.task.RestoreTask;
import net.minecraft.client.realms.util.RealmsUtil;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBackupScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier field_22686 = new Identifier("realms", "textures/gui/realms/plus_icon.png");
   private static final Identifier field_22687 = new Identifier("realms", "textures/gui/realms/restore_icon.png");
   private static final Text field_26471 = new TranslatableText("mco.backup.button.restore");
   private static final Text field_26472 = new TranslatableText("mco.backup.changes.tooltip");
   private static final Text field_26473 = new TranslatableText("mco.configure.world.backup");
   private static final Text field_26474 = new TranslatableText("mco.backup.nobackups");
   private static int lastScrollPosition = -1;
   private final RealmsConfigureWorldScreen parent;
   private List<Backup> backups = Collections.emptyList();
   @Nullable
   private Text toolTip;
   private RealmsBackupScreen.BackupObjectSelectionList backupObjectSelectionList;
   private int selectedBackup = -1;
   private final int slotId;
   private ButtonWidget downloadButton;
   private ButtonWidget restoreButton;
   private ButtonWidget changesButton;
   private Boolean noBackups = false;
   private final RealmsServer serverData;
   private RealmsLabel titleLabel;

   public RealmsBackupScreen(RealmsConfigureWorldScreen parent, RealmsServer serverData, int slotId) {
      this.parent = parent;
      this.serverData = serverData;
      this.slotId = slotId;
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.backupObjectSelectionList = new RealmsBackupScreen.BackupObjectSelectionList();
      if (lastScrollPosition != -1) {
         this.backupObjectSelectionList.setScrollAmount((double)lastScrollPosition);
      }

      (new Thread("Realms-fetch-backups") {
         @Override
         public void run() {
            RealmsClient _snowman = RealmsClient.createRealmsClient();

            try {
               List<Backup> _snowmanx = _snowman.backupsFor(RealmsBackupScreen.this.serverData.id).backups;
               RealmsBackupScreen.this.client.execute(() -> {
                  RealmsBackupScreen.this.backups = _snowman;
                  RealmsBackupScreen.this.noBackups = RealmsBackupScreen.this.backups.isEmpty();
                  RealmsBackupScreen.this.backupObjectSelectionList.clear();

                  for (Backup _snowmanxx : RealmsBackupScreen.this.backups) {
                     RealmsBackupScreen.this.backupObjectSelectionList.addEntry(_snowmanxx);
                  }

                  RealmsBackupScreen.this.generateChangeList();
               });
            } catch (RealmsServiceException var3) {
               RealmsBackupScreen.LOGGER.error("Couldn't request backups", var3);
            }
         }
      }).start();
      this.downloadButton = this.addButton(
         new ButtonWidget(this.width - 135, row(1), 120, 20, new TranslatableText("mco.backup.button.download"), _snowman -> this.downloadClicked())
      );
      this.restoreButton = this.addButton(
         new ButtonWidget(this.width - 135, row(3), 120, 20, new TranslatableText("mco.backup.button.restore"), _snowman -> this.restoreClicked(this.selectedBackup))
      );
      this.changesButton = this.addButton(new ButtonWidget(this.width - 135, row(5), 120, 20, new TranslatableText("mco.backup.changes.tooltip"), _snowman -> {
         this.client.openScreen(new RealmsBackupInfoScreen(this, this.backups.get(this.selectedBackup)));
         this.selectedBackup = -1;
      }));
      this.addButton(new ButtonWidget(this.width - 100, this.height - 35, 85, 20, ScreenTexts.BACK, _snowman -> this.client.openScreen(this.parent)));
      this.addChild(this.backupObjectSelectionList);
      this.titleLabel = this.addChild(new RealmsLabel(new TranslatableText("mco.configure.world.backup"), this.width / 2, 12, 16777215));
      this.focusOn(this.backupObjectSelectionList);
      this.updateButtonStates();
      this.narrateLabels();
   }

   private void generateChangeList() {
      if (this.backups.size() > 1) {
         for (int _snowman = 0; _snowman < this.backups.size() - 1; _snowman++) {
            Backup _snowmanx = this.backups.get(_snowman);
            Backup _snowmanxx = this.backups.get(_snowman + 1);
            if (!_snowmanx.metadata.isEmpty() && !_snowmanxx.metadata.isEmpty()) {
               for (String _snowmanxxx : _snowmanx.metadata.keySet()) {
                  if (!_snowmanxxx.contains("Uploaded") && _snowmanxx.metadata.containsKey(_snowmanxxx)) {
                     if (!_snowmanx.metadata.get(_snowmanxxx).equals(_snowmanxx.metadata.get(_snowmanxxx))) {
                        this.addToChangeList(_snowmanx, _snowmanxxx);
                     }
                  } else {
                     this.addToChangeList(_snowmanx, _snowmanxxx);
                  }
               }
            }
         }
      }
   }

   private void addToChangeList(Backup backup, String key) {
      if (key.contains("Uploaded")) {
         String _snowman = DateFormat.getDateTimeInstance(3, 3).format(backup.lastModifiedDate);
         backup.changeList.put(key, _snowman);
         backup.setUploadedVersion(true);
      } else {
         backup.changeList.put(key, backup.metadata.get(key));
      }
   }

   private void updateButtonStates() {
      this.restoreButton.visible = this.shouldRestoreButtonBeVisible();
      this.changesButton.visible = this.shouldChangesButtonBeVisible();
   }

   private boolean shouldChangesButtonBeVisible() {
      return this.selectedBackup == -1 ? false : !this.backups.get(this.selectedBackup).changeList.isEmpty();
   }

   private boolean shouldRestoreButtonBeVisible() {
      return this.selectedBackup == -1 ? false : !this.serverData.expired;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.client.openScreen(this.parent);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void restoreClicked(int selectedBackup) {
      if (selectedBackup >= 0 && selectedBackup < this.backups.size() && !this.serverData.expired) {
         this.selectedBackup = selectedBackup;
         Date _snowman = this.backups.get(selectedBackup).lastModifiedDate;
         String _snowmanx = DateFormat.getDateTimeInstance(3, 3).format(_snowman);
         String _snowmanxx = RealmsUtil.method_25282(_snowman);
         Text _snowmanxxx = new TranslatableText("mco.configure.world.restore.question.line1", _snowmanx, _snowmanxx);
         Text _snowmanxxxx = new TranslatableText("mco.configure.world.restore.question.line2");
         this.client.openScreen(new RealmsLongConfirmationScreen(_snowmanxxxxx -> {
            if (_snowmanxxxxx) {
               this.restore();
            } else {
               this.selectedBackup = -1;
               this.client.openScreen(this);
            }
         }, RealmsLongConfirmationScreen.Type.Warning, _snowmanxxx, _snowmanxxxx, true));
      }
   }

   private void downloadClicked() {
      Text _snowman = new TranslatableText("mco.configure.world.restore.download.question.line1");
      Text _snowmanx = new TranslatableText("mco.configure.world.restore.download.question.line2");
      this.client.openScreen(new RealmsLongConfirmationScreen(_snowmanxx -> {
         if (_snowmanxx) {
            this.downloadWorldData();
         } else {
            this.client.openScreen(this);
         }
      }, RealmsLongConfirmationScreen.Type.Info, _snowman, _snowmanx, true));
   }

   private void downloadWorldData() {
      this.client
         .openScreen(
            new RealmsLongRunningMcoTaskScreen(
               this.parent.getNewScreen(),
               new DownloadTask(
                  this.serverData.id,
                  this.slotId,
                  this.serverData.name + " (" + this.serverData.slots.get(this.serverData.activeSlot).getSlotName(this.serverData.activeSlot) + ")",
                  this
               )
            )
         );
   }

   private void restore() {
      Backup _snowman = this.backups.get(this.selectedBackup);
      this.selectedBackup = -1;
      this.client.openScreen(new RealmsLongRunningMcoTaskScreen(this.parent.getNewScreen(), new RestoreTask(_snowman, this.serverData.id, this.parent)));
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.toolTip = null;
      this.renderBackground(matrices);
      this.backupObjectSelectionList.render(matrices, mouseX, mouseY, delta);
      this.titleLabel.render(this, matrices);
      this.textRenderer.draw(matrices, field_26473, (float)((this.width - 150) / 2 - 90), 20.0F, 10526880);
      if (this.noBackups) {
         this.textRenderer.draw(matrices, field_26474, 20.0F, (float)(this.height / 2 - 10), 16777215);
      }

      this.downloadButton.active = !this.noBackups;
      super.render(matrices, mouseX, mouseY, delta);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
      }
   }

   protected void renderMousehoverTooltip(MatrixStack _snowman, @Nullable Text _snowman, int _snowman, int _snowman) {
      if (_snowman != null) {
         int _snowmanxxxx = _snowman + 12;
         int _snowmanxxxxx = _snowman - 12;
         int _snowmanxxxxxx = this.textRenderer.getWidth(_snowman);
         this.fillGradient(_snowman, _snowmanxxxx - 3, _snowmanxxxxx - 3, _snowmanxxxx + _snowmanxxxxxx + 3, _snowmanxxxxx + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(_snowman, _snowman, (float)_snowmanxxxx, (float)_snowmanxxxxx, 16777215);
      }
   }

   class BackupObjectSelectionList extends RealmsObjectSelectionList<RealmsBackupScreen.BackupObjectSelectionListEntry> {
      public BackupObjectSelectionList() {
         super(RealmsBackupScreen.this.width - 150, RealmsBackupScreen.this.height, 32, RealmsBackupScreen.this.height - 15, 36);
      }

      public void addEntry(Backup backup) {
         this.addEntry(RealmsBackupScreen.this.new BackupObjectSelectionListEntry(backup));
      }

      @Override
      public int getRowWidth() {
         return (int)((double)this.width * 0.93);
      }

      @Override
      public boolean isFocused() {
         return RealmsBackupScreen.this.getFocused() == this;
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public void renderBackground(MatrixStack matrices) {
         RealmsBackupScreen.this.renderBackground(matrices);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button != 0) {
            return false;
         } else if (mouseX < (double)this.getScrollbarPositionX() && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
            int _snowman = this.width / 2 - 92;
            int _snowmanx = this.width;
            int _snowmanxx = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount();
            int _snowmanxxx = _snowmanxx / this.itemHeight;
            if (mouseX >= (double)_snowman && mouseX <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.getItemCount()) {
               this.setSelected(_snowmanxxx);
               this.itemClicked(_snowmanxx, _snowmanxxx, mouseX, mouseY, this.width);
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public int getScrollbarPositionX() {
         return this.width - 5;
      }

      @Override
      public void itemClicked(int cursorY, int selectionIndex, double mouseX, double mouseY, int listWidth) {
         int _snowman = this.width - 35;
         int _snowmanx = selectionIndex * this.itemHeight + 36 - (int)this.getScrollAmount();
         int _snowmanxx = _snowman + 10;
         int _snowmanxxx = _snowmanx - 3;
         if (mouseX >= (double)_snowman && mouseX <= (double)(_snowman + 9) && mouseY >= (double)_snowmanx && mouseY <= (double)(_snowmanx + 9)) {
            if (!RealmsBackupScreen.this.backups.get(selectionIndex).changeList.isEmpty()) {
               RealmsBackupScreen.this.selectedBackup = -1;
               RealmsBackupScreen.lastScrollPosition = (int)this.getScrollAmount();
               this.client.openScreen(new RealmsBackupInfoScreen(RealmsBackupScreen.this, RealmsBackupScreen.this.backups.get(selectionIndex)));
            }
         } else if (mouseX >= (double)_snowmanxx && mouseX < (double)(_snowmanxx + 13) && mouseY >= (double)_snowmanxxx && mouseY < (double)(_snowmanxxx + 15)) {
            RealmsBackupScreen.lastScrollPosition = (int)this.getScrollAmount();
            RealmsBackupScreen.this.restoreClicked(selectionIndex);
         }
      }

      @Override
      public void setSelected(int index) {
         this.setSelectedItem(index);
         if (index != -1) {
            Realms.narrateNow(I18n.translate("narrator.select", RealmsBackupScreen.this.backups.get(index).lastModifiedDate.toString()));
         }

         this.selectInviteListItem(index);
      }

      public void selectInviteListItem(int item) {
         RealmsBackupScreen.this.selectedBackup = item;
         RealmsBackupScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsBackupScreen.BackupObjectSelectionListEntry _snowman) {
         super.setSelected(_snowman);
         RealmsBackupScreen.this.selectedBackup = this.children().indexOf(_snowman);
         RealmsBackupScreen.this.updateButtonStates();
      }
   }

   class BackupObjectSelectionListEntry extends AlwaysSelectedEntryListWidget.Entry<RealmsBackupScreen.BackupObjectSelectionListEntry> {
      private final Backup mBackup;

      public BackupObjectSelectionListEntry(Backup backup) {
         this.mBackup = backup;
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderBackupItem(matrices, this.mBackup, x - 40, y, mouseX, mouseY);
      }

      private void renderBackupItem(MatrixStack _snowman, Backup _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         int _snowmanxxxxxx = _snowman.isUploadedVersion() ? -8388737 : 16777215;
         RealmsBackupScreen.this.textRenderer.draw(_snowman, "Backup (" + RealmsUtil.method_25282(_snowman.lastModifiedDate) + ")", (float)(_snowman + 40), (float)(_snowman + 1), _snowmanxxxxxx);
         RealmsBackupScreen.this.textRenderer.draw(_snowman, this.getMediumDatePresentation(_snowman.lastModifiedDate), (float)(_snowman + 40), (float)(_snowman + 12), 5000268);
         int _snowmanxxxxxxx = RealmsBackupScreen.this.width - 175;
         int _snowmanxxxxxxxx = -3;
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx - 10;
         int _snowmanxxxxxxxxxx = 0;
         if (!RealmsBackupScreen.this.serverData.expired) {
            this.drawRestore(_snowman, _snowmanxxxxxxx, _snowman + -3, _snowman, _snowman);
         }

         if (!_snowman.changeList.isEmpty()) {
            this.drawInfo(_snowman, _snowmanxxxxxxxxx, _snowman + 0, _snowman, _snowman);
         }
      }

      private String getMediumDatePresentation(Date lastModifiedDate) {
         return DateFormat.getDateTimeInstance(3, 3).format(lastModifiedDate);
      }

      private void drawRestore(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         boolean _snowmanxxxxx = _snowman >= _snowman && _snowman <= _snowman + 12 && _snowman >= _snowman && _snowman <= _snowman + 14 && _snowman < RealmsBackupScreen.this.height - 15 && _snowman > 32;
         RealmsBackupScreen.this.client.getTextureManager().bindTexture(RealmsBackupScreen.field_22687);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float _snowmanxxxxxx = _snowmanxxxxx ? 28.0F : 0.0F;
         DrawableHelper.drawTexture(_snowman, _snowman * 2, _snowman * 2, 0.0F, _snowmanxxxxxx, 23, 28, 23, 56);
         RenderSystem.popMatrix();
         if (_snowmanxxxxx) {
            RealmsBackupScreen.this.toolTip = RealmsBackupScreen.field_26471;
         }
      }

      private void drawInfo(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         boolean _snowmanxxxxx = _snowman >= _snowman && _snowman <= _snowman + 8 && _snowman >= _snowman && _snowman <= _snowman + 8 && _snowman < RealmsBackupScreen.this.height - 15 && _snowman > 32;
         RealmsBackupScreen.this.client.getTextureManager().bindTexture(RealmsBackupScreen.field_22686);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float _snowmanxxxxxx = _snowmanxxxxx ? 15.0F : 0.0F;
         DrawableHelper.drawTexture(_snowman, _snowman * 2, _snowman * 2, 0.0F, _snowmanxxxxxx, 15, 15, 15, 30);
         RenderSystem.popMatrix();
         if (_snowmanxxxxx) {
            RealmsBackupScreen.this.toolTip = RealmsBackupScreen.field_26472;
         }
      }
   }
}
