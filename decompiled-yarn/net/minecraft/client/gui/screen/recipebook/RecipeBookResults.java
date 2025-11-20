package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;

public class RecipeBookResults {
   private final List<AnimatedResultButton> resultButtons = Lists.newArrayListWithCapacity(20);
   private AnimatedResultButton hoveredResultButton;
   private final RecipeAlternativesWidget alternatesWidget = new RecipeAlternativesWidget();
   private MinecraftClient client;
   private final List<RecipeDisplayListener> recipeDisplayListeners = Lists.newArrayList();
   private List<RecipeResultCollection> resultCollections;
   private ToggleButtonWidget nextPageButton;
   private ToggleButtonWidget prevPageButton;
   private int pageCount;
   private int currentPage;
   private RecipeBook recipeBook;
   private Recipe<?> lastClickedRecipe;
   private RecipeResultCollection resultCollection;

   public RecipeBookResults() {
      for (int _snowman = 0; _snowman < 20; _snowman++) {
         this.resultButtons.add(new AnimatedResultButton());
      }
   }

   public void initialize(MinecraftClient _snowman, int parentLeft, int parentTop) {
      this.client = _snowman;
      this.recipeBook = _snowman.player.getRecipeBook();

      for (int _snowmanx = 0; _snowmanx < this.resultButtons.size(); _snowmanx++) {
         this.resultButtons.get(_snowmanx).setPos(parentLeft + 11 + 25 * (_snowmanx % 5), parentTop + 31 + 25 * (_snowmanx / 5));
      }

      this.nextPageButton = new ToggleButtonWidget(parentLeft + 93, parentTop + 137, 12, 17, false);
      this.nextPageButton.setTextureUV(1, 208, 13, 18, RecipeBookWidget.TEXTURE);
      this.prevPageButton = new ToggleButtonWidget(parentLeft + 38, parentTop + 137, 12, 17, true);
      this.prevPageButton.setTextureUV(1, 208, 13, 18, RecipeBookWidget.TEXTURE);
   }

   public void setGui(RecipeBookWidget _snowman) {
      this.recipeDisplayListeners.remove(_snowman);
      this.recipeDisplayListeners.add(_snowman);
   }

   public void setResults(List<RecipeResultCollection> _snowman, boolean resetCurrentPage) {
      this.resultCollections = _snowman;
      this.pageCount = (int)Math.ceil((double)_snowman.size() / 20.0);
      if (this.pageCount <= this.currentPage || resetCurrentPage) {
         this.currentPage = 0;
      }

      this.refreshResultButtons();
   }

   private void refreshResultButtons() {
      int _snowman = 20 * this.currentPage;

      for (int _snowmanx = 0; _snowmanx < this.resultButtons.size(); _snowmanx++) {
         AnimatedResultButton _snowmanxx = this.resultButtons.get(_snowmanx);
         if (_snowman + _snowmanx < this.resultCollections.size()) {
            RecipeResultCollection _snowmanxxx = this.resultCollections.get(_snowman + _snowmanx);
            _snowmanxx.showResultCollection(_snowmanxxx, this);
            _snowmanxx.visible = true;
         } else {
            _snowmanxx.visible = false;
         }
      }

      this.hideShowPageButtons();
   }

   private void hideShowPageButtons() {
      this.nextPageButton.visible = this.pageCount > 1 && this.currentPage < this.pageCount - 1;
      this.prevPageButton.visible = this.pageCount > 1 && this.currentPage > 0;
   }

   public void draw(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, float _snowman) {
      if (this.pageCount > 1) {
         String _snowmanxxxxxx = this.currentPage + 1 + "/" + this.pageCount;
         int _snowmanxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxx);
         this.client.textRenderer.draw(_snowman, _snowmanxxxxxx, (float)(_snowman - _snowmanxxxxxxx / 2 + 73), (float)(_snowman + 141), -1);
      }

      this.hoveredResultButton = null;

      for (AnimatedResultButton _snowmanxxxxxx : this.resultButtons) {
         _snowmanxxxxxx.render(_snowman, _snowman, _snowman, _snowman);
         if (_snowmanxxxxxx.visible && _snowmanxxxxxx.isHovered()) {
            this.hoveredResultButton = _snowmanxxxxxx;
         }
      }

      this.prevPageButton.render(_snowman, _snowman, _snowman, _snowman);
      this.nextPageButton.render(_snowman, _snowman, _snowman, _snowman);
      this.alternatesWidget.render(_snowman, _snowman, _snowman, _snowman);
   }

   public void drawTooltip(MatrixStack _snowman, int _snowman, int _snowman) {
      if (this.client.currentScreen != null && this.hoveredResultButton != null && !this.alternatesWidget.isVisible()) {
         this.client.currentScreen.renderTooltip(_snowman, this.hoveredResultButton.getTooltip(this.client.currentScreen), _snowman, _snowman);
      }
   }

   @Nullable
   public Recipe<?> getLastClickedRecipe() {
      return this.lastClickedRecipe;
   }

   @Nullable
   public RecipeResultCollection getLastClickedResults() {
      return this.resultCollection;
   }

   public void hideAlternates() {
      this.alternatesWidget.setVisible(false);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight) {
      this.lastClickedRecipe = null;
      this.resultCollection = null;
      if (this.alternatesWidget.isVisible()) {
         if (this.alternatesWidget.mouseClicked(mouseX, mouseY, button)) {
            this.lastClickedRecipe = this.alternatesWidget.getLastClickedRecipe();
            this.resultCollection = this.alternatesWidget.getResults();
         } else {
            this.alternatesWidget.setVisible(false);
         }

         return true;
      } else if (this.nextPageButton.mouseClicked(mouseX, mouseY, button)) {
         this.currentPage++;
         this.refreshResultButtons();
         return true;
      } else if (this.prevPageButton.mouseClicked(mouseX, mouseY, button)) {
         this.currentPage--;
         this.refreshResultButtons();
         return true;
      } else {
         for (AnimatedResultButton _snowman : this.resultButtons) {
            if (_snowman.mouseClicked(mouseX, mouseY, button)) {
               if (button == 0) {
                  this.lastClickedRecipe = _snowman.currentRecipe();
                  this.resultCollection = _snowman.getResultCollection();
               } else if (button == 1 && !this.alternatesWidget.isVisible() && !_snowman.hasResults()) {
                  this.alternatesWidget
                     .showAlternativesForResult(
                        this.client, _snowman.getResultCollection(), _snowman.x, _snowman.y, areaLeft + areaWidth / 2, areaTop + 13 + areaHeight / 2, (float)_snowman.getWidth()
                     );
               }

               return true;
            }
         }

         return false;
      }
   }

   public void onRecipesDisplayed(List<Recipe<?>> _snowman) {
      for (RecipeDisplayListener _snowmanx : this.recipeDisplayListeners) {
         _snowmanx.onRecipesDisplayed(_snowman);
      }
   }

   public MinecraftClient getMinecraftClient() {
      return this.client;
   }

   public RecipeBook getRecipeBook() {
      return this.recipeBook;
   }
}
