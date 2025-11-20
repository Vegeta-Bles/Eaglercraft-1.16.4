package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.util.Identifier;

public class HorseScreen extends HandledScreen<HorseScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/horse.png");
   private final HorseBaseEntity entity;
   private float mouseX;
   private float mouseY;

   public HorseScreen(HorseScreenHandler handler, PlayerInventory inventory, HorseBaseEntity entity) {
      super(handler, inventory, entity.getDisplayName());
      this.entity = entity;
      this.passEvents = false;
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      if (this.entity instanceof AbstractDonkeyEntity) {
         AbstractDonkeyEntity _snowmanxx = (AbstractDonkeyEntity)this.entity;
         if (_snowmanxx.hasChest()) {
            this.drawTexture(matrices, _snowman + 79, _snowmanx + 17, 0, this.backgroundHeight, _snowmanxx.getInventoryColumns() * 18, 54);
         }
      }

      if (this.entity.canBeSaddled()) {
         this.drawTexture(matrices, _snowman + 7, _snowmanx + 35 - 18, 18, this.backgroundHeight + 54, 18, 18);
      }

      if (this.entity.hasArmorSlot()) {
         if (this.entity instanceof LlamaEntity) {
            this.drawTexture(matrices, _snowman + 7, _snowmanx + 35, 36, this.backgroundHeight + 54, 18, 18);
         } else {
            this.drawTexture(matrices, _snowman + 7, _snowmanx + 35, 0, this.backgroundHeight + 54, 18, 18);
         }
      }

      InventoryScreen.drawEntity(_snowman + 51, _snowmanx + 60, 17, (float)(_snowman + 51) - this.mouseX, (float)(_snowmanx + 75 - 50) - this.mouseY, this.entity);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.mouseX = (float)mouseX;
      this.mouseY = (float)mouseY;
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }
}
