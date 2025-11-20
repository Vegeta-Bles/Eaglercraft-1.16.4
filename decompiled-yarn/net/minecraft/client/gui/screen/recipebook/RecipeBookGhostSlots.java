package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.MathHelper;

public class RecipeBookGhostSlots {
   private Recipe<?> recipe;
   private final List<RecipeBookGhostSlots.GhostInputSlot> slots = Lists.newArrayList();
   private float time;

   public RecipeBookGhostSlots() {
   }

   public void reset() {
      this.recipe = null;
      this.slots.clear();
      this.time = 0.0F;
   }

   public void addSlot(Ingredient ingredient, int x, int y) {
      this.slots.add(new RecipeBookGhostSlots.GhostInputSlot(ingredient, x, y));
   }

   public RecipeBookGhostSlots.GhostInputSlot getSlot(int index) {
      return this.slots.get(index);
   }

   public int getSlotCount() {
      return this.slots.size();
   }

   @Nullable
   public Recipe<?> getRecipe() {
      return this.recipe;
   }

   public void setRecipe(Recipe<?> recipe) {
      this.recipe = recipe;
   }

   public void draw(MatrixStack _snowman, MinecraftClient _snowman, int _snowman, int _snowman, boolean _snowman, float _snowman) {
      if (!Screen.hasControlDown()) {
         this.time += _snowman;
      }

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.slots.size(); _snowmanxxxxxx++) {
         RecipeBookGhostSlots.GhostInputSlot _snowmanxxxxxxx = this.slots.get(_snowmanxxxxxx);
         int _snowmanxxxxxxxx = _snowmanxxxxxxx.getX() + _snowman;
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getY() + _snowman;
         if (_snowmanxxxxxx == 0 && _snowman) {
            DrawableHelper.fill(_snowman, _snowmanxxxxxxxx - 4, _snowmanxxxxxxxxx - 4, _snowmanxxxxxxxx + 20, _snowmanxxxxxxxxx + 20, 822018048);
         } else {
            DrawableHelper.fill(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 16, _snowmanxxxxxxxxx + 16, 822018048);
         }

         ItemStack _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getCurrentItemStack();
         ItemRenderer _snowmanxxxxxxxxxxx = _snowman.getItemRenderer();
         _snowmanxxxxxxxxxxx.renderInGui(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         RenderSystem.depthFunc(516);
         DrawableHelper.fill(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 16, _snowmanxxxxxxxxx + 16, 822083583);
         RenderSystem.depthFunc(515);
         if (_snowmanxxxxxx == 0) {
            _snowmanxxxxxxxxxxx.renderGuiItemOverlay(_snowman.textRenderer, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         }
      }
   }

   public class GhostInputSlot {
      private final Ingredient ingredient;
      private final int x;
      private final int y;

      public GhostInputSlot(Ingredient ingredient, int x, int y) {
         this.ingredient = ingredient;
         this.x = x;
         this.y = y;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public ItemStack getCurrentItemStack() {
         ItemStack[] _snowman = this.ingredient.getMatchingStacksClient();
         return _snowman[MathHelper.floor(RecipeBookGhostSlots.this.time / 30.0F) % _snowman.length];
      }
   }
}
