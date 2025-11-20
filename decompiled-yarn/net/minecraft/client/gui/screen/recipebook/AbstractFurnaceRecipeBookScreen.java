package net.minecraft.client.gui.screen.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public abstract class AbstractFurnaceRecipeBookScreen extends RecipeBookWidget {
   private Iterator<Item> fuelIterator;
   private Set<Item> fuels;
   private Slot outputSlot;
   private Item currentItem;
   private float frameTime;

   public AbstractFurnaceRecipeBookScreen() {
   }

   @Override
   protected void setBookButtonTexture() {
      this.toggleCraftableButton.setTextureUV(152, 182, 28, 18, TEXTURE);
   }

   @Override
   public void slotClicked(@Nullable Slot slot) {
      super.slotClicked(slot);
      if (slot != null && slot.id < this.craftingScreenHandler.getCraftingSlotCount()) {
         this.outputSlot = null;
      }
   }

   @Override
   public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
      ItemStack _snowman = recipe.getOutput();
      this.ghostSlots.setRecipe(recipe);
      this.ghostSlots.addSlot(Ingredient.ofStacks(_snowman), slots.get(2).x, slots.get(2).y);
      DefaultedList<Ingredient> _snowmanx = recipe.getPreviewInputs();
      this.outputSlot = slots.get(1);
      if (this.fuels == null) {
         this.fuels = this.getAllowedFuels();
      }

      this.fuelIterator = this.fuels.iterator();
      this.currentItem = null;
      Iterator<Ingredient> _snowmanxx = _snowmanx.iterator();

      for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
         if (!_snowmanxx.hasNext()) {
            return;
         }

         Ingredient _snowmanxxxx = _snowmanxx.next();
         if (!_snowmanxxxx.isEmpty()) {
            Slot _snowmanxxxxx = slots.get(_snowmanxxx);
            this.ghostSlots.addSlot(_snowmanxxxx, _snowmanxxxxx.x, _snowmanxxxxx.y);
         }
      }
   }

   protected abstract Set<Item> getAllowedFuels();

   @Override
   public void drawGhostSlots(MatrixStack _snowman, int _snowman, int _snowman, boolean _snowman, float _snowman) {
      super.drawGhostSlots(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.outputSlot != null) {
         if (!Screen.hasControlDown()) {
            this.frameTime += _snowman;
         }

         int _snowmanxxxxx = this.outputSlot.x + _snowman;
         int _snowmanxxxxxx = this.outputSlot.y + _snowman;
         DrawableHelper.fill(_snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxx + 16, _snowmanxxxxxx + 16, 822018048);
         this.client.getItemRenderer().renderInGuiWithOverrides(this.client.player, this.getItem().getDefaultStack(), _snowmanxxxxx, _snowmanxxxxxx);
         RenderSystem.depthFunc(516);
         DrawableHelper.fill(_snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxx + 16, _snowmanxxxxxx + 16, 822083583);
         RenderSystem.depthFunc(515);
      }
   }

   private Item getItem() {
      if (this.currentItem == null || this.frameTime > 30.0F) {
         this.frameTime = 0.0F;
         if (this.fuelIterator == null || !this.fuelIterator.hasNext()) {
            if (this.fuels == null) {
               this.fuels = this.getAllowedFuels();
            }

            this.fuelIterator = this.fuels.iterator();
         }

         this.currentItem = this.fuelIterator.next();
      }

      return this.currentItem;
   }
}
