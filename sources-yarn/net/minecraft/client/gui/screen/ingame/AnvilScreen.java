package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int i = (this.width - this.backgroundWidth) / 2;
      int j = (this.height - this.backgroundHeight) / 2;
      this.nameField = new TextFieldWidget(this.textRenderer, i + 62, j + 24, 103, 12, new TranslatableText("container.repair"));
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
      String string = this.nameField.getText();
      this.init(client, width, height);
      this.nameField.setText(string);
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
         String string2 = name;
         Slot lv = this.handler.getSlot(0);
         if (lv != null && lv.hasStack() && !lv.getStack().hasCustomName() && name.equals(lv.getStack().getName().getString())) {
            string2 = "";
         }

         this.handler.setNewItemName(string2);
         this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string2));
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      RenderSystem.disableBlend();
      super.drawForeground(matrices, mouseX, mouseY);
      int k = this.handler.getLevelCost();
      if (k > 0) {
         int l = 8453920;
         Text lv;
         if (k >= 40 && !this.client.player.abilities.creativeMode) {
            lv = field_26559;
            l = 16736352;
         } else if (!this.handler.getSlot(2).hasStack()) {
            lv = null;
         } else {
            lv = new TranslatableText("container.repair.cost", k);
            if (!this.handler.getSlot(2).canTakeItems(this.playerInventory.player)) {
               l = 16736352;
            }
         }

         if (lv != null) {
            int m = this.backgroundWidth - 8 - this.textRenderer.getWidth(lv) - 2;
            int n = 69;
            fill(matrices, m - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
            this.textRenderer.drawWithShadow(matrices, lv, (float)m, 69.0F, l);
         }
      }
   }

   @Override
   public void renderForeground(MatrixStack arg, int mouseY, int j, float f) {
      this.nameField.render(arg, mouseY, j, f);
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
