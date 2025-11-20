package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RecipeAlternativesWidget extends DrawableHelper implements Drawable, Element {
   private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
   private final List<RecipeAlternativesWidget.AlternativeButtonWidget> alternativeButtons = Lists.newArrayList();
   private boolean visible;
   private int buttonX;
   private int buttonY;
   private MinecraftClient client;
   private RecipeResultCollection resultCollection;
   private Recipe<?> lastClickedRecipe;
   private float time;
   private boolean furnace;

   public RecipeAlternativesWidget() {
   }

   public void showAlternativesForResult(
      MinecraftClient client, RecipeResultCollection results, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta
   ) {
      this.client = client;
      this.resultCollection = results;
      if (client.player.currentScreenHandler instanceof AbstractFurnaceScreenHandler) {
         this.furnace = true;
      }

      boolean _snowman = client.player.getRecipeBook().isFilteringCraftable((AbstractRecipeScreenHandler<?>)client.player.currentScreenHandler);
      List<Recipe<?>> _snowmanx = results.getRecipes(true);
      List<Recipe<?>> _snowmanxx = _snowman ? Collections.emptyList() : results.getRecipes(false);
      int _snowmanxxx = _snowmanx.size();
      int _snowmanxxxx = _snowmanxxx + _snowmanxx.size();
      int _snowmanxxxxx = _snowmanxxxx <= 16 ? 4 : 5;
      int _snowmanxxxxxx = (int)Math.ceil((double)((float)_snowmanxxxx / (float)_snowmanxxxxx));
      this.buttonX = buttonX;
      this.buttonY = buttonY;
      int _snowmanxxxxxxx = 25;
      float _snowmanxxxxxxxx = (float)(this.buttonX + Math.min(_snowmanxxxx, _snowmanxxxxx) * 25);
      float _snowmanxxxxxxxxx = (float)(areaCenterX + 50);
      if (_snowmanxxxxxxxx > _snowmanxxxxxxxxx) {
         this.buttonX = (int)((float)this.buttonX - delta * (float)((int)((_snowmanxxxxxxxx - _snowmanxxxxxxxxx) / delta)));
      }

      float _snowmanxxxxxxxxxx = (float)(this.buttonY + _snowmanxxxxxx * 25);
      float _snowmanxxxxxxxxxxx = (float)(areaCenterY + 50);
      if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxx) {
         this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((_snowmanxxxxxxxxxx - _snowmanxxxxxxxxxxx) / delta));
      }

      float _snowmanxxxxxxxxxxxx = (float)this.buttonY;
      float _snowmanxxxxxxxxxxxxx = (float)(areaCenterY - 100);
      if (_snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx) {
         this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((_snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx) / delta));
      }

      this.visible = true;
      this.alternativeButtons.clear();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxxxx++) {
         boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx < _snowmanxxx;
         Recipe<?> _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx ? _snowmanx.get(_snowmanxxxxxxxxxxxxxx) : _snowmanxx.get(_snowmanxxxxxxxxxxxxxx - _snowmanxxx);
         int _snowmanxxxxxxxxxxxxxxxxx = this.buttonX + 4 + 25 * (_snowmanxxxxxxxxxxxxxx % _snowmanxxxxx);
         int _snowmanxxxxxxxxxxxxxxxxxx = this.buttonY + 5 + 25 * (_snowmanxxxxxxxxxxxxxx / _snowmanxxxxx);
         if (this.furnace) {
            this.alternativeButtons
               .add(new RecipeAlternativesWidget.FurnaceAlternativeButtonWidget(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         } else {
            this.alternativeButtons
               .add(new RecipeAlternativesWidget.AlternativeButtonWidget(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         }
      }

      this.lastClickedRecipe = null;
   }

   @Override
   public boolean changeFocus(boolean lookForwards) {
      return false;
   }

   public RecipeResultCollection getResults() {
      return this.resultCollection;
   }

   public Recipe<?> getLastClickedRecipe() {
      return this.lastClickedRecipe;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return false;
      } else {
         for (RecipeAlternativesWidget.AlternativeButtonWidget _snowman : this.alternativeButtons) {
            if (_snowman.mouseClicked(mouseX, mouseY, button)) {
               this.lastClickedRecipe = _snowman.recipe;
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return false;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.visible) {
         this.time += delta;
         RenderSystem.enableBlend();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 170.0F);
         int _snowman = this.alternativeButtons.size() <= 16 ? 4 : 5;
         int _snowmanx = Math.min(this.alternativeButtons.size(), _snowman);
         int _snowmanxx = MathHelper.ceil((float)this.alternativeButtons.size() / (float)_snowman);
         int _snowmanxxx = 24;
         int _snowmanxxxx = 4;
         int _snowmanxxxxx = 82;
         int _snowmanxxxxxx = 208;
         this.renderGrid(matrices, _snowmanx, _snowmanxx, 24, 4, 82, 208);
         RenderSystem.disableBlend();

         for (RecipeAlternativesWidget.AlternativeButtonWidget _snowmanxxxxxxx : this.alternativeButtons) {
            _snowmanxxxxxxx.render(matrices, mouseX, mouseY, delta);
         }

         RenderSystem.popMatrix();
      }
   }

   private void renderGrid(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.drawTexture(_snowman, this.buttonX, this.buttonY, _snowman, _snowman, _snowman, _snowman);
      this.drawTexture(_snowman, this.buttonX + _snowman * 2 + _snowman * _snowman, this.buttonY, _snowman + _snowman + _snowman, _snowman, _snowman, _snowman);
      this.drawTexture(_snowman, this.buttonX, this.buttonY + _snowman * 2 + _snowman * _snowman, _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
      this.drawTexture(_snowman, this.buttonX + _snowman * 2 + _snowman * _snowman, this.buttonY + _snowman * 2 + _snowman * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman; _snowmanxxxxxxx++) {
         this.drawTexture(_snowman, this.buttonX + _snowman + _snowmanxxxxxxx * _snowman, this.buttonY, _snowman + _snowman, _snowman, _snowman, _snowman);
         this.drawTexture(_snowman, this.buttonX + _snowman + (_snowmanxxxxxxx + 1) * _snowman, this.buttonY, _snowman + _snowman, _snowman, _snowman, _snowman);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowman; _snowmanxxxxxxxx++) {
            if (_snowmanxxxxxxx == 0) {
               this.drawTexture(_snowman, this.buttonX, this.buttonY + _snowman + _snowmanxxxxxxxx * _snowman, _snowman, _snowman + _snowman, _snowman, _snowman);
               this.drawTexture(_snowman, this.buttonX, this.buttonY + _snowman + (_snowmanxxxxxxxx + 1) * _snowman, _snowman, _snowman + _snowman, _snowman, _snowman);
            }

            this.drawTexture(_snowman, this.buttonX + _snowman + _snowmanxxxxxxx * _snowman, this.buttonY + _snowman + _snowmanxxxxxxxx * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.drawTexture(_snowman, this.buttonX + _snowman + (_snowmanxxxxxxx + 1) * _snowman, this.buttonY + _snowman + _snowmanxxxxxxxx * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.drawTexture(_snowman, this.buttonX + _snowman + _snowmanxxxxxxx * _snowman, this.buttonY + _snowman + (_snowmanxxxxxxxx + 1) * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.drawTexture(_snowman, this.buttonX + _snowman + (_snowmanxxxxxxx + 1) * _snowman - 1, this.buttonY + _snowman + (_snowmanxxxxxxxx + 1) * _snowman - 1, _snowman + _snowman, _snowman + _snowman, _snowman + 1, _snowman + 1);
            if (_snowmanxxxxxxx == _snowman - 1) {
               this.drawTexture(_snowman, this.buttonX + _snowman * 2 + _snowman * _snowman, this.buttonY + _snowman + _snowmanxxxxxxxx * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
               this.drawTexture(_snowman, this.buttonX + _snowman * 2 + _snowman * _snowman, this.buttonY + _snowman + (_snowmanxxxxxxxx + 1) * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            }
         }

         this.drawTexture(_snowman, this.buttonX + _snowman + _snowmanxxxxxxx * _snowman, this.buttonY + _snowman * 2 + _snowman * _snowman, _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
         this.drawTexture(_snowman, this.buttonX + _snowman + (_snowmanxxxxxxx + 1) * _snowman, this.buttonY + _snowman * 2 + _snowman * _snowman, _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
      }
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public boolean isVisible() {
      return this.visible;
   }

   class AlternativeButtonWidget extends AbstractButtonWidget implements RecipeGridAligner<Ingredient> {
      private final Recipe<?> recipe;
      private final boolean craftable;
      protected final List<RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot> slots = Lists.newArrayList();

      public AlternativeButtonWidget(int x, int y, Recipe<?> recipe, boolean craftable) {
         super(x, y, 200, 20, LiteralText.EMPTY);
         this.width = 24;
         this.height = 24;
         this.recipe = recipe;
         this.craftable = craftable;
         this.alignRecipe(recipe);
      }

      protected void alignRecipe(Recipe<?> _snowman) {
         this.alignRecipeToGrid(3, 3, -1, _snowman, _snowman.getPreviewInputs().iterator(), 0);
      }

      @Override
      public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
         ItemStack[] _snowman = inputs.next().getMatchingStacksClient();
         if (_snowman.length != 0) {
            this.slots.add(new RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot(3 + gridY * 7, 3 + gridX * 7, _snowman));
         }
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RenderSystem.enableAlphaTest();
         RecipeAlternativesWidget.this.client.getTextureManager().bindTexture(RecipeAlternativesWidget.BACKGROUND_TEXTURE);
         int _snowman = 152;
         if (!this.craftable) {
            _snowman += 26;
         }

         int _snowmanx = RecipeAlternativesWidget.this.furnace ? 130 : 78;
         if (this.isHovered()) {
            _snowmanx += 26;
         }

         this.drawTexture(matrices, this.x, this.y, _snowman, _snowmanx, this.width, this.height);

         for (RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot _snowmanxx : this.slots) {
            RenderSystem.pushMatrix();
            float _snowmanxxx = 0.42F;
            int _snowmanxxxx = (int)((float)(this.x + _snowmanxx.y) / 0.42F - 3.0F);
            int _snowmanxxxxx = (int)((float)(this.y + _snowmanxx.x) / 0.42F - 3.0F);
            RenderSystem.scalef(0.42F, 0.42F, 1.0F);
            RecipeAlternativesWidget.this.client
               .getItemRenderer()
               .renderInGuiWithOverrides(_snowmanxx.stacks[MathHelper.floor(RecipeAlternativesWidget.this.time / 30.0F) % _snowmanxx.stacks.length], _snowmanxxxx, _snowmanxxxxx);
            RenderSystem.popMatrix();
         }

         RenderSystem.disableAlphaTest();
      }

      public class InputSlot {
         public final ItemStack[] stacks;
         public final int y;
         public final int x;

         public InputSlot(int y, int x, ItemStack[] stacks) {
            this.y = y;
            this.x = x;
            this.stacks = stacks;
         }
      }
   }

   class FurnaceAlternativeButtonWidget extends RecipeAlternativesWidget.AlternativeButtonWidget {
      public FurnaceAlternativeButtonWidget(int var2, int var3, Recipe<?> var4, boolean var5) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      protected void alignRecipe(Recipe<?> _snowman) {
         ItemStack[] _snowmanx = _snowman.getPreviewInputs().get(0).getMatchingStacksClient();
         this.slots.add(new RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot(10, 10, _snowmanx));
      }
   }
}
