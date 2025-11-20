package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AnimatedResultButton extends AbstractButtonWidget {
   private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
   private static final Text field_26595 = new TranslatableText("gui.recipebook.moreRecipes");
   private AbstractRecipeScreenHandler<?> craftingScreenHandler;
   private RecipeBook recipeBook;
   private RecipeResultCollection results;
   private float time;
   private float bounce;
   private int currentResultIndex;

   public AnimatedResultButton() {
      super(0, 0, 25, 25, LiteralText.EMPTY);
   }

   public void showResultCollection(RecipeResultCollection _snowman, RecipeBookResults _snowman) {
      this.results = _snowman;
      this.craftingScreenHandler = (AbstractRecipeScreenHandler<?>)_snowman.getMinecraftClient().player.currentScreenHandler;
      this.recipeBook = _snowman.getRecipeBook();
      List<Recipe<?>> _snowmanxx = _snowman.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));

      for (Recipe<?> _snowmanxxx : _snowmanxx) {
         if (this.recipeBook.shouldDisplay(_snowmanxxx)) {
            _snowman.onRecipesDisplayed(_snowmanxx);
            this.bounce = 15.0F;
            break;
         }
      }
   }

   public RecipeResultCollection getResultCollection() {
      return this.results;
   }

   public void setPos(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (!Screen.hasControlDown()) {
         this.time += delta;
      }

      MinecraftClient _snowman = MinecraftClient.getInstance();
      _snowman.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int _snowmanx = 29;
      if (!this.results.hasCraftableRecipes()) {
         _snowmanx += 25;
      }

      int _snowmanxx = 206;
      if (this.results.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
         _snowmanxx += 25;
      }

      boolean _snowmanxxx = this.bounce > 0.0F;
      if (_snowmanxxx) {
         float _snowmanxxxx = 1.0F + 0.1F * (float)Math.sin((double)(this.bounce / 15.0F * (float) Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.x + 8), (float)(this.y + 12), 0.0F);
         RenderSystem.scalef(_snowmanxxxx, _snowmanxxxx, 1.0F);
         RenderSystem.translatef((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
         this.bounce -= delta;
      }

      this.drawTexture(matrices, this.x, this.y, _snowmanx, _snowmanxx, this.width, this.height);
      List<Recipe<?>> _snowmanxxxx = this.getResults();
      this.currentResultIndex = MathHelper.floor(this.time / 30.0F) % _snowmanxxxx.size();
      ItemStack _snowmanxxxxx = _snowmanxxxx.get(this.currentResultIndex).getOutput();
      int _snowmanxxxxxx = 4;
      if (this.results.hasSingleOutput() && this.getResults().size() > 1) {
         _snowman.getItemRenderer().renderInGuiWithOverrides(_snowmanxxxxx, this.x + _snowmanxxxxxx + 1, this.y + _snowmanxxxxxx + 1);
         _snowmanxxxxxx--;
      }

      _snowman.getItemRenderer().renderInGui(_snowmanxxxxx, this.x + _snowmanxxxxxx, this.y + _snowmanxxxxxx);
      if (_snowmanxxx) {
         RenderSystem.popMatrix();
      }
   }

   private List<Recipe<?>> getResults() {
      List<Recipe<?>> _snowman = this.results.getRecipes(true);
      if (!this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
         _snowman.addAll(this.results.getRecipes(false));
      }

      return _snowman;
   }

   public boolean hasResults() {
      return this.getResults().size() == 1;
   }

   public Recipe<?> currentRecipe() {
      List<Recipe<?>> _snowman = this.getResults();
      return _snowman.get(this.currentResultIndex);
   }

   public List<Text> getTooltip(Screen screen) {
      ItemStack _snowman = this.getResults().get(this.currentResultIndex).getOutput();
      List<Text> _snowmanx = Lists.newArrayList(screen.getTooltipFromItem(_snowman));
      if (this.results.getResults(this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)).size() > 1) {
         _snowmanx.add(field_26595);
      }

      return _snowmanx;
   }

   @Override
   public int getWidth() {
      return 25;
   }

   @Override
   protected boolean isValidClickButton(int button) {
      return button == 0 || button == 1;
   }
}
