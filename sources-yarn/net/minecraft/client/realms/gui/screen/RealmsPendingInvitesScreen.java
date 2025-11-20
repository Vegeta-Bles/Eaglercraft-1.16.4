package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.PendingInvite;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.realms.util.RealmsUtil;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsPendingInvitesScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier ACCEPT_ICON = new Identifier("realms", "textures/gui/realms/accept_icon.png");
   private static final Identifier REJECT_ICON = new Identifier("realms", "textures/gui/realms/reject_icon.png");
   private static final Text field_26493 = new TranslatableText("mco.invites.nopending");
   private static final Text field_26494 = new TranslatableText("mco.invites.button.accept");
   private static final Text field_26495 = new TranslatableText("mco.invites.button.reject");
   private final Screen parent;
   @Nullable
   private Text toolTip;
   private boolean loaded;
   private RealmsPendingInvitesScreen.PendingInvitationSelectionList pendingInvitationSelectionList;
   private RealmsLabel titleLabel;
   private int selectedInvite = -1;
   private ButtonWidget acceptButton;
   private ButtonWidget rejectButton;

   public RealmsPendingInvitesScreen(Screen parent) {
      this.parent = parent;
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.pendingInvitationSelectionList = new RealmsPendingInvitesScreen.PendingInvitationSelectionList();
      (new Thread("Realms-pending-invitations-fetcher") {
            @Override
            public void run() {
               RealmsClient lv = RealmsClient.createRealmsClient();

               try {
                  List<PendingInvite> list = lv.pendingInvites().pendingInvites;
                  List<RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry> list2 = list.stream()
                     .map(arg -> RealmsPendingInvitesScreen.this.new PendingInvitationSelectionListEntry(arg))
                     .collect(Collectors.toList());
                  RealmsPendingInvitesScreen.this.client.execute(() -> RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.replaceEntries(list2));
               } catch (RealmsServiceException var7) {
                  RealmsPendingInvitesScreen.LOGGER.error("Couldn't list invites");
               } finally {
                  RealmsPendingInvitesScreen.this.loaded = true;
               }
            }
         })
         .start();
      this.addChild(this.pendingInvitationSelectionList);
      this.acceptButton = this.addButton(
         new ButtonWidget(this.width / 2 - 174, this.height - 32, 100, 20, new TranslatableText("mco.invites.button.accept"), arg -> {
            this.accept(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
         })
      );
      this.addButton(
         new ButtonWidget(this.width / 2 - 50, this.height - 32, 100, 20, ScreenTexts.DONE, arg -> this.client.openScreen(new RealmsMainScreen(this.parent)))
      );
      this.rejectButton = this.addButton(
         new ButtonWidget(this.width / 2 + 74, this.height - 32, 100, 20, new TranslatableText("mco.invites.button.reject"), arg -> {
            this.reject(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
         })
      );
      this.titleLabel = new RealmsLabel(new TranslatableText("mco.invites.title"), this.width / 2, 12, 16777215);
      this.addChild(this.titleLabel);
      this.narrateLabels();
      this.updateButtonStates();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.client.openScreen(new RealmsMainScreen(this.parent));
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void updateList(int slot) {
      this.pendingInvitationSelectionList.removeAtIndex(slot);
   }

   private void reject(final int slot) {
      if (slot < this.pendingInvitationSelectionList.getItemCount()) {
         (new Thread("Realms-reject-invitation") {
            @Override
            public void run() {
               try {
                  RealmsClient lv = RealmsClient.createRealmsClient();
                  lv.rejectInvitation(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.children().get(slot).mPendingInvite.invitationId);
                  RealmsPendingInvitesScreen.this.client.execute(() -> RealmsPendingInvitesScreen.this.updateList(slot));
               } catch (RealmsServiceException var2) {
                  RealmsPendingInvitesScreen.LOGGER.error("Couldn't reject invite");
               }
            }
         }).start();
      }
   }

   private void accept(final int slot) {
      if (slot < this.pendingInvitationSelectionList.getItemCount()) {
         (new Thread("Realms-accept-invitation") {
            @Override
            public void run() {
               try {
                  RealmsClient lv = RealmsClient.createRealmsClient();
                  lv.acceptInvitation(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.children().get(slot).mPendingInvite.invitationId);
                  RealmsPendingInvitesScreen.this.client.execute(() -> RealmsPendingInvitesScreen.this.updateList(slot));
               } catch (RealmsServiceException var2) {
                  RealmsPendingInvitesScreen.LOGGER.error("Couldn't accept invite");
               }
            }
         }).start();
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.toolTip = null;
      this.renderBackground(matrices);
      this.pendingInvitationSelectionList.render(matrices, mouseX, mouseY, delta);
      this.titleLabel.render(this, matrices);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
      }

      if (this.pendingInvitationSelectionList.getItemCount() == 0 && this.loaded) {
         drawCenteredText(matrices, this.textRenderer, field_26493, this.width / 2, this.height / 2 - 20, 16777215);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   protected void renderMousehoverTooltip(MatrixStack arg, @Nullable Text arg2, int i, int j) {
      if (arg2 != null) {
         int k = i + 12;
         int l = j - 12;
         int m = this.textRenderer.getWidth(arg2);
         this.fillGradient(arg, k - 3, l - 3, k + m + 3, l + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(arg, arg2, (float)k, (float)l, 16777215);
      }
   }

   private void updateButtonStates() {
      this.acceptButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
      this.rejectButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
   }

   private boolean shouldAcceptAndRejectButtonBeVisible(int invite) {
      return invite != -1;
   }

   @Environment(EnvType.CLIENT)
   class PendingInvitationSelectionList extends RealmsObjectSelectionList<RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry> {
      public PendingInvitationSelectionList() {
         super(RealmsPendingInvitesScreen.this.width, RealmsPendingInvitesScreen.this.height, 32, RealmsPendingInvitesScreen.this.height - 40, 36);
      }

      public void removeAtIndex(int index) {
         this.remove(index);
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public int getRowWidth() {
         return 260;
      }

      @Override
      public boolean isFocused() {
         return RealmsPendingInvitesScreen.this.getFocused() == this;
      }

      @Override
      public void renderBackground(MatrixStack matrices) {
         RealmsPendingInvitesScreen.this.renderBackground(matrices);
      }

      @Override
      public void setSelected(int index) {
         this.setSelectedItem(index);
         if (index != -1) {
            List<RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry> list = RealmsPendingInvitesScreen.this.pendingInvitationSelectionList
               .children();
            PendingInvite lv = list.get(index).mPendingInvite;
            String string = I18n.translate("narrator.select.list.position", index + 1, list.size());
            String string2 = Realms.joinNarrations(Arrays.asList(lv.worldName, lv.worldOwnerName, RealmsUtil.method_25282(lv.date), string));
            Realms.narrateNow(I18n.translate("narrator.select", string2));
         }

         this.selectInviteListItem(index);
      }

      public void selectInviteListItem(int item) {
         RealmsPendingInvitesScreen.this.selectedInvite = item;
         RealmsPendingInvitesScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry arg) {
         super.setSelected(arg);
         RealmsPendingInvitesScreen.this.selectedInvite = this.children().indexOf(arg);
         RealmsPendingInvitesScreen.this.updateButtonStates();
      }
   }

   @Environment(EnvType.CLIENT)
   class PendingInvitationSelectionListEntry extends AlwaysSelectedEntryListWidget.Entry<RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry> {
      private final PendingInvite mPendingInvite;
      private final List<RealmsAcceptRejectButton> buttons;

      PendingInvitationSelectionListEntry(PendingInvite pendingInvite) {
         this.mPendingInvite = pendingInvite;
         this.buttons = Arrays.asList(
            new RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry.AcceptButton(),
            new RealmsPendingInvitesScreen.PendingInvitationSelectionListEntry.RejectButton()
         );
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderPendingInvitationItem(matrices, this.mPendingInvite, x, y, mouseX, mouseY);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         RealmsAcceptRejectButton.handleClick(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, this, this.buttons, button, mouseX, mouseY);
         return true;
      }

      private void renderPendingInvitationItem(MatrixStack arg, PendingInvite arg2, int i, int j, int k, int l) {
         RealmsPendingInvitesScreen.this.textRenderer.draw(arg, arg2.worldName, (float)(i + 38), (float)(j + 1), 16777215);
         RealmsPendingInvitesScreen.this.textRenderer.draw(arg, arg2.worldOwnerName, (float)(i + 38), (float)(j + 12), 7105644);
         RealmsPendingInvitesScreen.this.textRenderer.draw(arg, RealmsUtil.method_25282(arg2.date), (float)(i + 38), (float)(j + 24), 7105644);
         RealmsAcceptRejectButton.render(arg, this.buttons, RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, i, j, k, l);
         RealmsTextureManager.withBoundFace(arg2.worldOwnerUuid, () -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            DrawableHelper.drawTexture(arg, i, j, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
            DrawableHelper.drawTexture(arg, i, j, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      @Environment(EnvType.CLIENT)
      class AcceptButton extends RealmsAcceptRejectButton {
         AcceptButton() {
            super(15, 15, 215, 5);
         }

         @Override
         protected void render(MatrixStack arg, int y, int j, boolean bl) {
            RealmsPendingInvitesScreen.this.client.getTextureManager().bindTexture(RealmsPendingInvitesScreen.ACCEPT_ICON);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float f = bl ? 19.0F : 0.0F;
            DrawableHelper.drawTexture(arg, y, j, f, 0.0F, 18, 18, 37, 18);
            if (bl) {
               RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.field_26494;
            }
         }

         @Override
         public void handleClick(int index) {
            RealmsPendingInvitesScreen.this.accept(index);
         }
      }

      @Environment(EnvType.CLIENT)
      class RejectButton extends RealmsAcceptRejectButton {
         RejectButton() {
            super(15, 15, 235, 5);
         }

         @Override
         protected void render(MatrixStack arg, int y, int j, boolean bl) {
            RealmsPendingInvitesScreen.this.client.getTextureManager().bindTexture(RealmsPendingInvitesScreen.REJECT_ICON);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float f = bl ? 19.0F : 0.0F;
            DrawableHelper.drawTexture(arg, y, j, f, 0.0F, 18, 18, 37, 18);
            if (bl) {
               RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.field_26495;
            }
         }

         @Override
         public void handleClick(int index) {
            RealmsPendingInvitesScreen.this.reject(index);
         }
      }
   }
}
