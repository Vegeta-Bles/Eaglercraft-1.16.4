package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.Ops;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPlayerScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier OP_ICON = new Identifier("realms", "textures/gui/realms/op_icon.png");
   private static final Identifier USER_ICON = new Identifier("realms", "textures/gui/realms/user_icon.png");
   private static final Identifier CROSS_PLAYER_ICON = new Identifier("realms", "textures/gui/realms/cross_player_icon.png");
   private static final Identifier OPTIONS_BACKGROUND = new Identifier("minecraft", "textures/gui/options_background.png");
   private static final Text field_26498 = new TranslatableText("mco.configure.world.invites.normal.tooltip");
   private static final Text field_26499 = new TranslatableText("mco.configure.world.invites.ops.tooltip");
   private static final Text field_26500 = new TranslatableText("mco.configure.world.invites.remove.tooltip");
   private static final Text field_26501 = new TranslatableText("mco.configure.world.invited");
   private Text tooltipText;
   private final RealmsConfigureWorldScreen parent;
   private final RealmsServer serverData;
   private RealmsPlayerScreen.InvitedObjectSelectionList invitedObjectSelectionList;
   private int column1_x;
   private int column_width;
   private int column2_x;
   private ButtonWidget removeButton;
   private ButtonWidget opdeopButton;
   private int selectedInvitedIndex = -1;
   private String selectedInvited;
   private int player = -1;
   private boolean stateChanged;
   private RealmsLabel titleLabel;
   private RealmsPlayerScreen.PlayerOperation operation = RealmsPlayerScreen.PlayerOperation.NONE;

   public RealmsPlayerScreen(RealmsConfigureWorldScreen parent, RealmsServer serverData) {
      this.parent = parent;
      this.serverData = serverData;
   }

   @Override
   public void init() {
      this.column1_x = this.width / 2 - 160;
      this.column_width = 150;
      this.column2_x = this.width / 2 + 12;
      this.client.keyboard.setRepeatEvents(true);
      this.invitedObjectSelectionList = new RealmsPlayerScreen.InvitedObjectSelectionList();
      this.invitedObjectSelectionList.setLeftPos(this.column1_x);
      this.addChild(this.invitedObjectSelectionList);

      for (PlayerInfo _snowman : this.serverData.players) {
         this.invitedObjectSelectionList.addEntry(_snowman);
      }

      this.addButton(
         new ButtonWidget(
            this.column2_x,
            row(1),
            this.column_width + 10,
            20,
            new TranslatableText("mco.configure.world.buttons.invite"),
            _snowman -> this.client.openScreen(new RealmsInviteScreen(this.parent, this, this.serverData))
         )
      );
      this.removeButton = this.addButton(
         new ButtonWidget(
            this.column2_x,
            row(7),
            this.column_width + 10,
            20,
            new TranslatableText("mco.configure.world.invites.remove.tooltip"),
            _snowman -> this.uninvite(this.player)
         )
      );
      this.opdeopButton = this.addButton(
         new ButtonWidget(this.column2_x, row(9), this.column_width + 10, 20, new TranslatableText("mco.configure.world.invites.ops.tooltip"), _snowman -> {
            if (this.serverData.players.get(this.player).isOperator()) {
               this.deop(this.player);
            } else {
               this.op(this.player);
            }
         })
      );
      this.addButton(
         new ButtonWidget(
            this.column2_x + this.column_width / 2 + 2, row(12), this.column_width / 2 + 10 - 2, 20, ScreenTexts.BACK, _snowman -> this.backButtonClicked()
         )
      );
      this.titleLabel = this.addChild(new RealmsLabel(new TranslatableText("mco.configure.world.players.title"), this.width / 2, 17, 16777215));
      this.narrateLabels();
      this.updateButtonStates();
   }

   private void updateButtonStates() {
      this.removeButton.visible = this.shouldRemoveAndOpdeopButtonBeVisible(this.player);
      this.opdeopButton.visible = this.shouldRemoveAndOpdeopButtonBeVisible(this.player);
   }

   private boolean shouldRemoveAndOpdeopButtonBeVisible(int player) {
      return player != -1;
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
         this.client.openScreen(this.parent.getNewScreen());
      } else {
         this.client.openScreen(this.parent);
      }
   }

   private void op(int index) {
      this.updateButtonStates();
      RealmsClient _snowman = RealmsClient.createRealmsClient();
      String _snowmanx = this.serverData.players.get(index).getUuid();

      try {
         this.updateOps(_snowman.op(this.serverData.id, _snowmanx));
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't op the user");
      }
   }

   private void deop(int index) {
      this.updateButtonStates();
      RealmsClient _snowman = RealmsClient.createRealmsClient();
      String _snowmanx = this.serverData.players.get(index).getUuid();

      try {
         this.updateOps(_snowman.deop(this.serverData.id, _snowmanx));
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't deop the user");
      }
   }

   private void updateOps(Ops ops) {
      for (PlayerInfo _snowman : this.serverData.players) {
         _snowman.setOperator(ops.ops.contains(_snowman.getName()));
      }
   }

   private void uninvite(int index) {
      this.updateButtonStates();
      if (index >= 0 && index < this.serverData.players.size()) {
         PlayerInfo _snowman = this.serverData.players.get(index);
         this.selectedInvited = _snowman.getUuid();
         this.selectedInvitedIndex = index;
         RealmsConfirmScreen _snowmanx = new RealmsConfirmScreen(_snowmanxx -> {
            if (_snowmanxx) {
               RealmsClient _snowmanx = RealmsClient.createRealmsClient();

               try {
                  _snowmanx.uninvite(this.serverData.id, this.selectedInvited);
               } catch (RealmsServiceException var4) {
                  LOGGER.error("Couldn't uninvite user");
               }

               this.deleteFromInvitedList(this.selectedInvitedIndex);
               this.player = -1;
               this.updateButtonStates();
            }

            this.stateChanged = true;
            this.client.openScreen(this);
         }, new LiteralText("Question"), new TranslatableText("mco.configure.world.uninvite.question").append(" '").append(_snowman.getName()).append("' ?"));
         this.client.openScreen(_snowmanx);
      }
   }

   private void deleteFromInvitedList(int selectedInvitedIndex) {
      this.serverData.players.remove(selectedInvitedIndex);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.tooltipText = null;
      this.operation = RealmsPlayerScreen.PlayerOperation.NONE;
      this.renderBackground(matrices);
      if (this.invitedObjectSelectionList != null) {
         this.invitedObjectSelectionList.render(matrices, mouseX, mouseY, delta);
      }

      int _snowman = row(12) + 20;
      Tessellator _snowmanx = Tessellator.getInstance();
      BufferBuilder _snowmanxx = _snowmanx.getBuffer();
      this.client.getTextureManager().bindTexture(OPTIONS_BACKGROUND);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxx = 32.0F;
      _snowmanxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
      _snowmanxx.vertex(0.0, (double)this.height, 0.0).texture(0.0F, (float)(this.height - _snowman) / 32.0F + 0.0F).color(64, 64, 64, 255).next();
      _snowmanxx.vertex((double)this.width, (double)this.height, 0.0)
         .texture((float)this.width / 32.0F, (float)(this.height - _snowman) / 32.0F + 0.0F)
         .color(64, 64, 64, 255)
         .next();
      _snowmanxx.vertex((double)this.width, (double)_snowman, 0.0).texture((float)this.width / 32.0F, 0.0F).color(64, 64, 64, 255).next();
      _snowmanxx.vertex(0.0, (double)_snowman, 0.0).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
      _snowmanx.draw();
      this.titleLabel.render(this, matrices);
      if (this.serverData != null && this.serverData.players != null) {
         this.textRenderer
            .draw(
               matrices,
               new LiteralText("").append(field_26501).append(" (").append(Integer.toString(this.serverData.players.size())).append(")"),
               (float)this.column1_x,
               (float)row(0),
               10526880
            );
      } else {
         this.textRenderer.draw(matrices, field_26501, (float)this.column1_x, (float)row(0), 10526880);
      }

      super.render(matrices, mouseX, mouseY, delta);
      if (this.serverData != null) {
         this.renderMousehoverTooltip(matrices, this.tooltipText, mouseX, mouseY);
      }
   }

   protected void renderMousehoverTooltip(MatrixStack matrices, @Nullable Text _snowman, int mouseX, int mouseY) {
      if (_snowman != null) {
         int _snowmanx = mouseX + 12;
         int _snowmanxx = mouseY - 12;
         int _snowmanxxx = this.textRenderer.getWidth(_snowman);
         this.fillGradient(matrices, _snowmanx - 3, _snowmanxx - 3, _snowmanx + _snowmanxxx + 3, _snowmanxx + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(matrices, _snowman, (float)_snowmanx, (float)_snowmanxx, 16777215);
      }
   }

   private void drawRemoveIcon(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < row(12) + 20 && _snowman > row(1);
      this.client.getTextureManager().bindTexture(CROSS_PLAYER_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxx = _snowmanxxxxx ? 7.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, _snowmanxxxxxx, 8, 7, 8, 14);
      if (_snowmanxxxxx) {
         this.tooltipText = field_26500;
         this.operation = RealmsPlayerScreen.PlayerOperation.REMOVE;
      }
   }

   private void drawOpped(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < row(12) + 20 && _snowman > row(1);
      this.client.getTextureManager().bindTexture(OP_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxx = _snowmanxxxxx ? 8.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, _snowmanxxxxxx, 8, 8, 8, 16);
      if (_snowmanxxxxx) {
         this.tooltipText = field_26499;
         this.operation = RealmsPlayerScreen.PlayerOperation.TOGGLE_OP;
      }
   }

   private void drawNormal(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 9 && _snowman < row(12) + 20 && _snowman > row(1);
      this.client.getTextureManager().bindTexture(USER_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxx = _snowmanxxxxx ? 8.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, _snowmanxxxxxx, 8, 8, 8, 16);
      if (_snowmanxxxxx) {
         this.tooltipText = field_26498;
         this.operation = RealmsPlayerScreen.PlayerOperation.TOGGLE_OP;
      }
   }

   class InvitedObjectSelectionList extends RealmsObjectSelectionList<RealmsPlayerScreen.InvitedObjectSelectionListEntry> {
      public InvitedObjectSelectionList() {
         super(RealmsPlayerScreen.this.column_width + 10, RealmsPlayerScreen.row(12) + 20, RealmsPlayerScreen.row(1), RealmsPlayerScreen.row(12) + 20, 13);
      }

      public void addEntry(PlayerInfo playerInfo) {
         this.addEntry(RealmsPlayerScreen.this.new InvitedObjectSelectionListEntry(playerInfo));
      }

      @Override
      public int getRowWidth() {
         return (int)((double)this.width * 1.0);
      }

      @Override
      public boolean isFocused() {
         return RealmsPlayerScreen.this.getFocused() == this;
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button == 0 && mouseX < (double)this.getScrollbarPositionX() && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
            int _snowman = RealmsPlayerScreen.this.column1_x;
            int _snowmanx = RealmsPlayerScreen.this.column1_x + RealmsPlayerScreen.this.column_width;
            int _snowmanxx = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int _snowmanxxx = _snowmanxx / this.itemHeight;
            if (mouseX >= (double)_snowman && mouseX <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.getItemCount()) {
               this.setSelected(_snowmanxxx);
               this.itemClicked(_snowmanxx, _snowmanxxx, mouseX, mouseY, this.width);
            }

            return true;
         } else {
            return super.mouseClicked(mouseX, mouseY, button);
         }
      }

      @Override
      public void itemClicked(int cursorY, int selectionIndex, double mouseX, double mouseY, int listWidth) {
         if (selectionIndex >= 0
            && selectionIndex <= RealmsPlayerScreen.this.serverData.players.size()
            && RealmsPlayerScreen.this.operation != RealmsPlayerScreen.PlayerOperation.NONE) {
            if (RealmsPlayerScreen.this.operation == RealmsPlayerScreen.PlayerOperation.TOGGLE_OP) {
               if (RealmsPlayerScreen.this.serverData.players.get(selectionIndex).isOperator()) {
                  RealmsPlayerScreen.this.deop(selectionIndex);
               } else {
                  RealmsPlayerScreen.this.op(selectionIndex);
               }
            } else if (RealmsPlayerScreen.this.operation == RealmsPlayerScreen.PlayerOperation.REMOVE) {
               RealmsPlayerScreen.this.uninvite(selectionIndex);
            }
         }
      }

      @Override
      public void setSelected(int index) {
         this.setSelectedItem(index);
         if (index != -1) {
            Realms.narrateNow(I18n.translate("narrator.select", RealmsPlayerScreen.this.serverData.players.get(index).getName()));
         }

         this.selectInviteListItem(index);
      }

      public void selectInviteListItem(int item) {
         RealmsPlayerScreen.this.player = item;
         RealmsPlayerScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsPlayerScreen.InvitedObjectSelectionListEntry _snowman) {
         super.setSelected(_snowman);
         RealmsPlayerScreen.this.player = this.children().indexOf(_snowman);
         RealmsPlayerScreen.this.updateButtonStates();
      }

      @Override
      public void renderBackground(MatrixStack matrices) {
         RealmsPlayerScreen.this.renderBackground(matrices);
      }

      @Override
      public int getScrollbarPositionX() {
         return RealmsPlayerScreen.this.column1_x + this.width - 5;
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 13;
      }
   }

   class InvitedObjectSelectionListEntry extends AlwaysSelectedEntryListWidget.Entry<RealmsPlayerScreen.InvitedObjectSelectionListEntry> {
      private final PlayerInfo playerInfo;

      public InvitedObjectSelectionListEntry(PlayerInfo playerInfo) {
         this.playerInfo = playerInfo;
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderInvitedItem(matrices, this.playerInfo, x, y, mouseX, mouseY);
      }

      private void renderInvitedItem(MatrixStack matrices, PlayerInfo playerInfo, int x, int y, int mouseX, int mouseY) {
         int _snowman;
         if (!playerInfo.getAccepted()) {
            _snowman = 10526880;
         } else if (playerInfo.getOnline()) {
            _snowman = 8388479;
         } else {
            _snowman = 16777215;
         }

         RealmsPlayerScreen.this.textRenderer.draw(matrices, playerInfo.getName(), (float)(RealmsPlayerScreen.this.column1_x + 3 + 12), (float)(y + 1), _snowman);
         if (playerInfo.isOperator()) {
            RealmsPlayerScreen.this.drawOpped(matrices, RealmsPlayerScreen.this.column1_x + RealmsPlayerScreen.this.column_width - 10, y + 1, mouseX, mouseY);
         } else {
            RealmsPlayerScreen.this.drawNormal(matrices, RealmsPlayerScreen.this.column1_x + RealmsPlayerScreen.this.column_width - 10, y + 1, mouseX, mouseY);
         }

         RealmsPlayerScreen.this.drawRemoveIcon(matrices, RealmsPlayerScreen.this.column1_x + RealmsPlayerScreen.this.column_width - 22, y + 2, mouseX, mouseY);
         RealmsTextureManager.withBoundFace(playerInfo.getUuid(), () -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            DrawableHelper.drawTexture(matrices, RealmsPlayerScreen.this.column1_x + 2 + 2, y + 1, 8, 8, 8.0F, 8.0F, 8, 8, 64, 64);
            DrawableHelper.drawTexture(matrices, RealmsPlayerScreen.this.column1_x + 2 + 2, y + 1, 8, 8, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }
   }

   static enum PlayerOperation {
      TOGGLE_OP,
      REMOVE,
      NONE;

      private PlayerOperation() {
      }
   }
}
