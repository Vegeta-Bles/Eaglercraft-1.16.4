package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class StonecutterScreen extends HandledScreen<StonecutterScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");
   private float scrollAmount;
   private boolean mouseClicked;
   private int scrollOffset;
   private boolean canCraft;

   public StonecutterScreen(StonecutterScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      handler.setContentsChangedListener(this::onInventoryChange);
      this.titleY--;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      this.renderBackground(matrices);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = this.x;
      int _snowmanx = this.y;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      int _snowmanxx = (int)(41.0F * this.scrollAmount);
      this.drawTexture(matrices, _snowman + 119, _snowmanx + 15 + _snowmanxx, 176 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
      int _snowmanxxx = this.x + 52;
      int _snowmanxxxx = this.y + 14;
      int _snowmanxxxxx = this.scrollOffset + 12;
      this.renderRecipeBackground(matrices, mouseX, mouseY, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      this.renderRecipeIcons(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   @Override
   protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
      super.drawMouseoverTooltip(matrices, x, y);
      if (this.canCraft) {
         int _snowman = this.x + 52;
         int _snowmanx = this.y + 14;
         int _snowmanxx = this.scrollOffset + 12;
         List<StonecuttingRecipe> _snowmanxxx = this.handler.getAvailableRecipes();

         for (int _snowmanxxxx = this.scrollOffset; _snowmanxxxx < _snowmanxx && _snowmanxxxx < this.handler.getAvailableRecipeCount(); _snowmanxxxx++) {
            int _snowmanxxxxx = _snowmanxxxx - this.scrollOffset;
            int _snowmanxxxxxx = _snowman + _snowmanxxxxx % 4 * 16;
            int _snowmanxxxxxxx = _snowmanx + _snowmanxxxxx / 4 * 18 + 2;
            if (x >= _snowmanxxxxxx && x < _snowmanxxxxxx + 16 && y >= _snowmanxxxxxxx && y < _snowmanxxxxxxx + 18) {
               this.renderTooltip(matrices, _snowmanxxx.get(_snowmanxxxx).getOutput(), x, y);
            }
         }
      }
   }

   private void renderRecipeBackground(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      for (int _snowmanxxxxxx = this.scrollOffset; _snowmanxxxxxx < _snowman && _snowmanxxxxxx < this.handler.getAvailableRecipeCount(); _snowmanxxxxxx++) {
         int _snowmanxxxxxxx = _snowmanxxxxxx - this.scrollOffset;
         int _snowmanxxxxxxxx = _snowman + _snowmanxxxxxxx % 4 * 16;
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx / 4;
         int _snowmanxxxxxxxxxx = _snowman + _snowmanxxxxxxxxx * 18 + 2;
         int _snowmanxxxxxxxxxxx = this.backgroundHeight;
         if (_snowmanxxxxxx == this.handler.getSelectedRecipe()) {
            _snowmanxxxxxxxxxxx += 18;
         } else if (_snowman >= _snowmanxxxxxxxx && _snowman >= _snowmanxxxxxxxxxx && _snowman < _snowmanxxxxxxxx + 16 && _snowman < _snowmanxxxxxxxxxx + 18) {
            _snowmanxxxxxxxxxxx += 36;
         }

         this.drawTexture(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx - 1, 0, _snowmanxxxxxxxxxxx, 16, 18);
      }
   }

   private void renderRecipeIcons(int x, int y, int scrollOffset) {
      List<StonecuttingRecipe> _snowman = this.handler.getAvailableRecipes();

      for (int _snowmanx = this.scrollOffset; _snowmanx < scrollOffset && _snowmanx < this.handler.getAvailableRecipeCount(); _snowmanx++) {
         int _snowmanxx = _snowmanx - this.scrollOffset;
         int _snowmanxxx = x + _snowmanxx % 4 * 16;
         int _snowmanxxxx = _snowmanxx / 4;
         int _snowmanxxxxx = y + _snowmanxxxx * 18 + 2;
         this.client.getItemRenderer().renderInGuiWithOverrides(_snowman.get(_snowmanx).getOutput(), _snowmanxxx, _snowmanxxxxx);
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.mouseClicked = false;
      if (this.canCraft) {
         int _snowman = this.x + 52;
         int _snowmanx = this.y + 14;
         int _snowmanxx = this.scrollOffset + 12;

         for (int _snowmanxxx = this.scrollOffset; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxxx - this.scrollOffset;
            double _snowmanxxxxx = mouseX - (double)(_snowman + _snowmanxxxx % 4 * 16);
            double _snowmanxxxxxx = mouseY - (double)(_snowmanx + _snowmanxxxx / 4 * 18);
            if (_snowmanxxxxx >= 0.0 && _snowmanxxxxxx >= 0.0 && _snowmanxxxxx < 16.0 && _snowmanxxxxxx < 18.0 && this.handler.onButtonClick(this.client.player, _snowmanxxx)) {
               MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
               this.client.interactionManager.clickButton(this.handler.syncId, _snowmanxxx);
               return true;
            }
         }

         _snowman = this.x + 119;
         _snowmanx = this.y + 9;
         if (mouseX >= (double)_snowman && mouseX < (double)(_snowman + 12) && mouseY >= (double)_snowmanx && mouseY < (double)(_snowmanx + 54)) {
            this.mouseClicked = true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.mouseClicked && this.shouldScroll()) {
         int _snowman = this.y + 14;
         int _snowmanx = _snowman + 54;
         this.scrollAmount = ((float)mouseY - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
         this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * 4;
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      if (this.shouldScroll()) {
         int _snowman = this.getMaxScroll();
         this.scrollAmount = (float)((double)this.scrollAmount - amount / (double)_snowman);
         this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
         this.scrollOffset = (int)((double)(this.scrollAmount * (float)_snowman) + 0.5) * 4;
      }

      return true;
   }

   private boolean shouldScroll() {
      return this.canCraft && this.handler.getAvailableRecipeCount() > 12;
   }

   protected int getMaxScroll() {
      return (this.handler.getAvailableRecipeCount() + 4 - 1) / 4 - 3;
   }

   private void onInventoryChange() {
      this.canCraft = this.handler.canCraft();
      if (!this.canCraft) {
         this.scrollAmount = 0.0F;
         this.scrollOffset = 0;
      }
   }
}
