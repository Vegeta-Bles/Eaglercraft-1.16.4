package net.minecraft.client.gui.hud.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SpectatorMenu {
   private static final SpectatorMenuCommand CLOSE_COMMAND = new SpectatorMenu.CloseSpectatorMenuCommand();
   private static final SpectatorMenuCommand PREVIOUS_PAGE_COMMAND = new SpectatorMenu.ChangePageSpectatorMenuCommand(-1, true);
   private static final SpectatorMenuCommand NEXT_PAGE_COMMAND = new SpectatorMenu.ChangePageSpectatorMenuCommand(1, true);
   private static final SpectatorMenuCommand DISABLED_NEXT_PAGE_COMMAND = new SpectatorMenu.ChangePageSpectatorMenuCommand(1, false);
   private static final Text field_26613 = new TranslatableText("spectatorMenu.close");
   private static final Text field_26614 = new TranslatableText("spectatorMenu.previous_page");
   private static final Text field_26615 = new TranslatableText("spectatorMenu.next_page");
   public static final SpectatorMenuCommand BLANK_COMMAND = new SpectatorMenuCommand() {
      @Override
      public void use(SpectatorMenu menu) {
      }

      @Override
      public Text getName() {
         return LiteralText.EMPTY;
      }

      @Override
      public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
      }

      @Override
      public boolean isEnabled() {
         return false;
      }
   };
   private final SpectatorMenuCloseCallback closeCallback;
   private SpectatorMenuCommandGroup currentGroup;
   private int selectedSlot = -1;
   private int page;

   public SpectatorMenu(SpectatorMenuCloseCallback closeCallback) {
      this.currentGroup = new RootSpectatorCommandGroup();
      this.closeCallback = closeCallback;
   }

   public SpectatorMenuCommand getCommand(int slot) {
      int _snowman = slot + this.page * 6;
      if (this.page > 0 && slot == 0) {
         return PREVIOUS_PAGE_COMMAND;
      } else if (slot == 7) {
         return _snowman < this.currentGroup.getCommands().size() ? NEXT_PAGE_COMMAND : DISABLED_NEXT_PAGE_COMMAND;
      } else if (slot == 8) {
         return CLOSE_COMMAND;
      } else {
         return _snowman >= 0 && _snowman < this.currentGroup.getCommands().size()
            ? (SpectatorMenuCommand)MoreObjects.firstNonNull(this.currentGroup.getCommands().get(_snowman), BLANK_COMMAND)
            : BLANK_COMMAND;
      }
   }

   public List<SpectatorMenuCommand> getCommands() {
      List<SpectatorMenuCommand> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx <= 8; _snowmanx++) {
         _snowman.add(this.getCommand(_snowmanx));
      }

      return _snowman;
   }

   public SpectatorMenuCommand getSelectedCommand() {
      return this.getCommand(this.selectedSlot);
   }

   public SpectatorMenuCommandGroup getCurrentGroup() {
      return this.currentGroup;
   }

   public void useCommand(int slot) {
      SpectatorMenuCommand _snowman = this.getCommand(slot);
      if (_snowman != BLANK_COMMAND) {
         if (this.selectedSlot == slot && _snowman.isEnabled()) {
            _snowman.use(this);
         } else {
            this.selectedSlot = slot;
         }
      }
   }

   public void close() {
      this.closeCallback.close(this);
   }

   public int getSelectedSlot() {
      return this.selectedSlot;
   }

   public void selectElement(SpectatorMenuCommandGroup group) {
      this.currentGroup = group;
      this.selectedSlot = -1;
      this.page = 0;
   }

   public SpectatorMenuState getCurrentState() {
      return new SpectatorMenuState(this.currentGroup, this.getCommands(), this.selectedSlot);
   }

   static class ChangePageSpectatorMenuCommand implements SpectatorMenuCommand {
      private final int direction;
      private final boolean enabled;

      public ChangePageSpectatorMenuCommand(int direction, boolean enabled) {
         this.direction = direction;
         this.enabled = enabled;
      }

      @Override
      public void use(SpectatorMenu menu) {
         menu.page = menu.page + this.direction;
      }

      @Override
      public Text getName() {
         return this.direction < 0 ? SpectatorMenu.field_26614 : SpectatorMenu.field_26615;
      }

      @Override
      public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
         MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
         if (this.direction < 0) {
            DrawableHelper.drawTexture(_snowman, 0, 0, 144.0F, 0.0F, 16, 16, 256, 256);
         } else {
            DrawableHelper.drawTexture(_snowman, 0, 0, 160.0F, 0.0F, 16, 16, 256, 256);
         }
      }

      @Override
      public boolean isEnabled() {
         return this.enabled;
      }
   }

   static class CloseSpectatorMenuCommand implements SpectatorMenuCommand {
      private CloseSpectatorMenuCommand() {
      }

      @Override
      public void use(SpectatorMenu menu) {
         menu.close();
      }

      @Override
      public Text getName() {
         return SpectatorMenu.field_26613;
      }

      @Override
      public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
         MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
         DrawableHelper.drawTexture(_snowman, 0, 0, 128.0F, 0.0F, 16, 16, 256, 256);
      }

      @Override
      public boolean isEnabled() {
         return true;
      }
   }
}
