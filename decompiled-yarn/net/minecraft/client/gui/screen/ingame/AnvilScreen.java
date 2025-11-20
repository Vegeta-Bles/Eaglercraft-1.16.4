package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class AnvilScreen extends ForgingScreen<AnvilScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/anvil.png");
   private static final Text field_26559 = new TranslatableText("container.repair.expensive");
   private TextFieldWidget nameField;

   public AnvilScreen(AnvilScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title, TEXTURE);
      this.titleX = 60;
   }

   @Override
   public void tick() {
      super.tick();
      this.nameField.tick();
   }

   @Override
   protected void setup() {
      this.client.keyboard.setRepeatEvents(true);
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      this.nameField = new TextFieldWidget(this.textRenderer, _snowman + 62, _snowmanx + 24, 103, 12, new TranslatableText("container.repair"));
      this.nameField.setFocusUnlocked(false);
      this.nameField.setEditableColor(-1);
      this.nameField.setUneditableColor(-1);
      this.nameField.setHasBorder(false);
      this.nameField.setMaxLength(35);
      this.nameField.setChangedListener(this::onRenamed);
      this.children.add(this.nameField);
      this.setInitialFocus(this.nameField);
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String _snowman = this.nameField.getText();
      this.init(client, width, height);
      this.nameField.setText(_snowman);
   }

   @Override
   public void removed() {
      super.removed();
      this.client.keyboard.setRepeatEvents(false);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.client.player.closeHandledScreen();
      }

      return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.isActive() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
   }

   private void onRenamed(String name) {
      if (!name.isEmpty()) {
         String _snowman = name;
         Slot _snowmanx = this.handler.getSlot(0);
         if (_snowmanx != null && _snowmanx.hasStack() && !_snowmanx.getStack().hasCustomName() && name.equals(_snowmanx.getStack().getName().getString())) {
            _snowman = "";
         }

         this.handler.setNewItemName(_snowman);
         this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(_snowman));
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      RenderSystem.disableBlend();
      super.drawForeground(matrices, mouseX, mouseY);
      int _snowman = this.handler.getLevelCost();
      if (_snowman > 0) {
         int _snowmanx = 8453920;
         Text _snowmanxx;
         if (_snowman >= 40 && !this.client.player.abilities.creativeMode) {
            _snowmanxx = field_26559;
            _snowmanx = 16736352;
         } else if (!this.handler.getSlot(2).hasStack()) {
            _snowmanxx = null;
         } else {
            _snowmanxx = new TranslatableText("container.repair.cost", _snowman);
            if (!this.handler.getSlot(2).canTakeItems(this.playerInventory.player)) {
               _snowmanx = 16736352;
            }
         }

         if (_snowmanxx != null) {
            int _snowmanxxx = this.backgroundWidth - 8 - this.textRenderer.getWidth(_snowmanxx) - 2;
            int _snowmanxxxx = 69;
            fill(matrices, _snowmanxxx - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
            this.textRenderer.drawWithShadow(matrices, _snowmanxx, (float)_snowmanxxx, 69.0F, _snowmanx);
         }
      }
   }

   @Override
   public void renderForeground(MatrixStack _snowman, int mouseY, int _snowman, float _snowman) {
      this.nameField.render(_snowman, mouseY, _snowman, _snowman);
   }

   @Override
   public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
      if (slotId == 0) {
         this.nameField.setText(stack.isEmpty() ? "" : stack.getName().getString());
         this.nameField.setEditable(!stack.isEmpty());
         this.setFocused(this.nameField);
      }
   }
}
