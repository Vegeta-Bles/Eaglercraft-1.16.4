package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.RecipeCategoryOptionsC2SPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class RecipeBookWidget extends DrawableHelper implements Drawable, Element, RecipeDisplayListener, RecipeGridAligner<Ingredient> {
   protected static final Identifier TEXTURE = new Identifier("textures/gui/recipe_book.png");
   private static final Text field_25711 = new TranslatableText("gui.recipebook.search_hint").formatted(Formatting.ITALIC).formatted(Formatting.GRAY);
   private static final Text field_26593 = new TranslatableText("gui.recipebook.toggleRecipes.craftable");
   private static final Text field_26594 = new TranslatableText("gui.recipebook.toggleRecipes.all");
   private int leftOffset;
   private int parentWidth;
   private int parentHeight;
   protected final RecipeBookGhostSlots ghostSlots = new RecipeBookGhostSlots();
   private final List<RecipeGroupButtonWidget> tabButtons = Lists.newArrayList();
   private RecipeGroupButtonWidget currentTab;
   protected ToggleButtonWidget toggleCraftableButton;
   protected AbstractRecipeScreenHandler<?> craftingScreenHandler;
   protected MinecraftClient client;
   private TextFieldWidget searchField;
   private String searchText = "";
   private ClientRecipeBook recipeBook;
   private final RecipeBookResults recipesArea = new RecipeBookResults();
   private final RecipeFinder recipeFinder = new RecipeFinder();
   private int cachedInvChangeCount;
   private boolean searching;

   public RecipeBookWidget() {
   }

   public void initialize(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, AbstractRecipeScreenHandler<?> craftingScreenHandler) {
      this.client = client;
      this.parentWidth = parentWidth;
      this.parentHeight = parentHeight;
      this.craftingScreenHandler = craftingScreenHandler;
      client.player.currentScreenHandler = craftingScreenHandler;
      this.recipeBook = client.player.getRecipeBook();
      this.cachedInvChangeCount = client.player.inventory.getChangeCount();
      if (this.isOpen()) {
         this.reset(narrow);
      }

      client.keyboard.setRepeatEvents(true);
   }

   public void reset(boolean narrow) {
      this.leftOffset = narrow ? 0 : 86;
      int _snowman = (this.parentWidth - 147) / 2 - this.leftOffset;
      int _snowmanx = (this.parentHeight - 166) / 2;
      this.recipeFinder.clear();
      this.client.player.inventory.populateRecipeFinder(this.recipeFinder);
      this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
      String _snowmanxx = this.searchField != null ? this.searchField.getText() : "";
      this.searchField = new TextFieldWidget(this.client.textRenderer, _snowman + 25, _snowmanx + 14, 80, 9 + 5, new TranslatableText("itemGroup.search"));
      this.searchField.setMaxLength(50);
      this.searchField.setHasBorder(false);
      this.searchField.setVisible(true);
      this.searchField.setEditableColor(16777215);
      this.searchField.setText(_snowmanxx);
      this.recipesArea.initialize(this.client, _snowman, _snowmanx);
      this.recipesArea.setGui(this);
      this.toggleCraftableButton = new ToggleButtonWidget(_snowman + 110, _snowmanx + 12, 26, 16, this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));
      this.setBookButtonTexture();
      this.tabButtons.clear();

      for (RecipeBookGroup _snowmanxxx : RecipeBookGroup.method_30285(this.craftingScreenHandler.getCategory())) {
         this.tabButtons.add(new RecipeGroupButtonWidget(_snowmanxxx));
      }

      if (this.currentTab != null) {
         this.currentTab = this.tabButtons.stream().filter(_snowmanxxx -> _snowmanxxx.getCategory().equals(this.currentTab.getCategory())).findFirst().orElse(null);
      }

      if (this.currentTab == null) {
         this.currentTab = this.tabButtons.get(0);
      }

      this.currentTab.setToggled(true);
      this.refreshResults(false);
      this.refreshTabButtons();
   }

   @Override
   public boolean changeFocus(boolean lookForwards) {
      return false;
   }

   protected void setBookButtonTexture() {
      this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE);
   }

   public void close() {
      this.searchField = null;
      this.currentTab = null;
      this.client.keyboard.setRepeatEvents(false);
   }

   public int findLeftEdge(boolean narrow, int width, int parentWidth) {
      int _snowman;
      if (this.isOpen() && !narrow) {
         _snowman = 177 + (width - parentWidth - 200) / 2;
      } else {
         _snowman = (width - parentWidth) / 2;
      }

      return _snowman;
   }

   public void toggleOpen() {
      this.setOpen(!this.isOpen());
   }

   public boolean isOpen() {
      return this.recipeBook.isGuiOpen(this.craftingScreenHandler.getCategory());
   }

   protected void setOpen(boolean opened) {
      this.recipeBook.setGuiOpen(this.craftingScreenHandler.getCategory(), opened);
      if (!opened) {
         this.recipesArea.hideAlternates();
      }

      this.sendBookDataPacket();
   }

   public void slotClicked(@Nullable Slot slot) {
      if (slot != null && slot.id < this.craftingScreenHandler.getCraftingSlotCount()) {
         this.ghostSlots.reset();
         if (this.isOpen()) {
            this.refreshInputs();
         }
      }
   }

   private void refreshResults(boolean resetCurrentPage) {
      List<RecipeResultCollection> _snowman = this.recipeBook.getResultsForGroup(this.currentTab.getCategory());
      _snowman.forEach(
         _snowmanx -> _snowmanx.computeCraftables(
               this.recipeFinder, this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.recipeBook
            )
      );
      List<RecipeResultCollection> _snowmanx = Lists.newArrayList(_snowman);
      _snowmanx.removeIf(_snowmanxx -> !_snowmanxx.isInitialized());
      _snowmanx.removeIf(_snowmanxx -> !_snowmanxx.hasFittingRecipes());
      String _snowmanxx = this.searchField.getText();
      if (!_snowmanxx.isEmpty()) {
         ObjectSet<RecipeResultCollection> _snowmanxxx = new ObjectLinkedOpenHashSet(
            this.client.getSearchableContainer(SearchManager.RECIPE_OUTPUT).findAll(_snowmanxx.toLowerCase(Locale.ROOT))
         );
         _snowmanx.removeIf(_snowmanxxxx -> !_snowman.contains(_snowmanxxxx));
      }

      if (this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
         _snowmanx.removeIf(_snowmanxxx -> !_snowmanxxx.hasCraftableRecipes());
      }

      this.recipesArea.setResults(_snowmanx, resetCurrentPage);
   }

   private void refreshTabButtons() {
      int _snowman = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
      int _snowmanx = (this.parentHeight - 166) / 2 + 3;
      int _snowmanxx = 27;
      int _snowmanxxx = 0;

      for (RecipeGroupButtonWidget _snowmanxxxx : this.tabButtons) {
         RecipeBookGroup _snowmanxxxxx = _snowmanxxxx.getCategory();
         if (_snowmanxxxxx == RecipeBookGroup.CRAFTING_SEARCH || _snowmanxxxxx == RecipeBookGroup.FURNACE_SEARCH) {
            _snowmanxxxx.visible = true;
            _snowmanxxxx.setPos(_snowman, _snowmanx + 27 * _snowmanxxx++);
         } else if (_snowmanxxxx.hasKnownRecipes(this.recipeBook)) {
            _snowmanxxxx.setPos(_snowman, _snowmanx + 27 * _snowmanxxx++);
            _snowmanxxxx.checkForNewRecipes(this.client);
         }
      }
   }

   public void update() {
      if (this.isOpen()) {
         if (this.cachedInvChangeCount != this.client.player.inventory.getChangeCount()) {
            this.refreshInputs();
            this.cachedInvChangeCount = this.client.player.inventory.getChangeCount();
         }

         this.searchField.tick();
      }
   }

   private void refreshInputs() {
      this.recipeFinder.clear();
      this.client.player.inventory.populateRecipeFinder(this.recipeFinder);
      this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
      this.refreshResults(false);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.isOpen()) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 100.0F);
         this.client.getTextureManager().bindTexture(TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowman = (this.parentWidth - 147) / 2 - this.leftOffset;
         int _snowmanx = (this.parentHeight - 166) / 2;
         this.drawTexture(matrices, _snowman, _snowmanx, 1, 1, 147, 166);
         if (!this.searchField.isFocused() && this.searchField.getText().isEmpty()) {
            drawTextWithShadow(matrices, this.client.textRenderer, field_25711, _snowman + 25, _snowmanx + 14, -1);
         } else {
            this.searchField.render(matrices, mouseX, mouseY, delta);
         }

         for (RecipeGroupButtonWidget _snowmanxx : this.tabButtons) {
            _snowmanxx.render(matrices, mouseX, mouseY, delta);
         }

         this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
         this.recipesArea.draw(matrices, _snowman, _snowmanx, mouseX, mouseY, delta);
         RenderSystem.popMatrix();
      }
   }

   public void drawTooltip(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      if (this.isOpen()) {
         this.recipesArea.drawTooltip(_snowman, _snowman, _snowman);
         if (this.toggleCraftableButton.isHovered()) {
            Text _snowmanxxxxx = this.getCraftableButtonText();
            if (this.client.currentScreen != null) {
               this.client.currentScreen.renderTooltip(_snowman, _snowmanxxxxx, _snowman, _snowman);
            }
         }

         this.drawGhostSlotTooltip(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private Text getCraftableButtonText() {
      return this.toggleCraftableButton.isToggled() ? this.getToggleCraftableButtonText() : field_26594;
   }

   protected Text getToggleCraftableButtonText() {
      return field_26593;
   }

   private void drawGhostSlotTooltip(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      ItemStack _snowmanxxxxx = null;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.ghostSlots.getSlotCount(); _snowmanxxxxxx++) {
         RecipeBookGhostSlots.GhostInputSlot _snowmanxxxxxxx = this.ghostSlots.getSlot(_snowmanxxxxxx);
         int _snowmanxxxxxxxx = _snowmanxxxxxxx.getX() + _snowman;
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getY() + _snowman;
         if (_snowman >= _snowmanxxxxxxxx && _snowman >= _snowmanxxxxxxxxx && _snowman < _snowmanxxxxxxxx + 16 && _snowman < _snowmanxxxxxxxxx + 16) {
            _snowmanxxxxx = _snowmanxxxxxxx.getCurrentItemStack();
         }
      }

      if (_snowmanxxxxx != null && this.client.currentScreen != null) {
         this.client.currentScreen.renderTooltip(_snowman, this.client.currentScreen.getTooltipFromItem(_snowmanxxxxx), _snowman, _snowman);
      }
   }

   public void drawGhostSlots(MatrixStack _snowman, int _snowman, int _snowman, boolean _snowman, float _snowman) {
      this.ghostSlots.draw(_snowman, this.client, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isOpen() && !this.client.player.isSpectator()) {
         if (this.recipesArea.mouseClicked(mouseX, mouseY, button, (this.parentWidth - 147) / 2 - this.leftOffset, (this.parentHeight - 166) / 2, 147, 166)) {
            Recipe<?> _snowman = this.recipesArea.getLastClickedRecipe();
            RecipeResultCollection _snowmanx = this.recipesArea.getLastClickedResults();
            if (_snowman != null && _snowmanx != null) {
               if (!_snowmanx.isCraftable(_snowman) && this.ghostSlots.getRecipe() == _snowman) {
                  return false;
               }

               this.ghostSlots.reset();
               this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, _snowman, Screen.hasShiftDown());
               if (!this.isWide()) {
                  this.setOpen(false);
               }
            }

            return true;
         } else if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
            return true;
         } else if (this.toggleCraftableButton.mouseClicked(mouseX, mouseY, button)) {
            boolean _snowman = this.toggleFilteringCraftable();
            this.toggleCraftableButton.setToggled(_snowman);
            this.sendBookDataPacket();
            this.refreshResults(false);
            return true;
         } else {
            for (RecipeGroupButtonWidget _snowman : this.tabButtons) {
               if (_snowman.mouseClicked(mouseX, mouseY, button)) {
                  if (this.currentTab != _snowman) {
                     this.currentTab.setToggled(false);
                     this.currentTab = _snowman;
                     this.currentTab.setToggled(true);
                     this.refreshResults(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean toggleFilteringCraftable() {
      RecipeBookCategory _snowman = this.craftingScreenHandler.getCategory();
      boolean _snowmanx = !this.recipeBook.isFilteringCraftable(_snowman);
      this.recipeBook.setFilteringCraftable(_snowman, _snowmanx);
      return _snowmanx;
   }

   public boolean isClickOutsideBounds(double _snowman, double _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      if (!this.isOpen()) {
         return true;
      } else {
         boolean _snowmanxxxxxxx = _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + _snowman) || _snowman >= (double)(_snowman + _snowman);
         boolean _snowmanxxxxxxxx = (double)(_snowman - 147) < _snowman && _snowman < (double)_snowman && (double)_snowman < _snowman && _snowman < (double)(_snowman + _snowman);
         return _snowmanxxxxxxx && !_snowmanxxxxxxxx && !this.currentTab.isHovered();
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      this.searching = false;
      if (!this.isOpen() || this.client.player.isSpectator()) {
         return false;
      } else if (keyCode == 256 && !this.isWide()) {
         this.setOpen(false);
         return true;
      } else if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         this.refreshSearchResults();
         return true;
      } else if (this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256) {
         return true;
      } else if (this.client.options.keyChat.matchesKey(keyCode, scanCode) && !this.searchField.isFocused()) {
         this.searching = true;
         this.searchField.setSelected(true);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      this.searching = false;
      return Element.super.keyReleased(keyCode, scanCode, modifiers);
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      if (this.searching) {
         return false;
      } else if (!this.isOpen() || this.client.player.isSpectator()) {
         return false;
      } else if (this.searchField.charTyped(chr, keyCode)) {
         this.refreshSearchResults();
         return true;
      } else {
         return Element.super.charTyped(chr, keyCode);
      }
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return false;
   }

   private void refreshSearchResults() {
      String _snowman = this.searchField.getText().toLowerCase(Locale.ROOT);
      this.triggerPirateSpeakEasterEgg(_snowman);
      if (!_snowman.equals(this.searchText)) {
         this.refreshResults(false);
         this.searchText = _snowman;
      }
   }

   private void triggerPirateSpeakEasterEgg(String _snowman) {
      if ("excitedze".equals(_snowman)) {
         LanguageManager _snowmanx = this.client.getLanguageManager();
         LanguageDefinition _snowmanxx = _snowmanx.getLanguage("en_pt");
         if (_snowmanx.getLanguage().compareTo(_snowmanxx) == 0) {
            return;
         }

         _snowmanx.setLanguage(_snowmanxx);
         this.client.options.language = _snowmanxx.getCode();
         this.client.reloadResources();
         this.client.options.write();
      }
   }

   private boolean isWide() {
      return this.leftOffset == 86;
   }

   public void refresh() {
      this.refreshTabButtons();
      if (this.isOpen()) {
         this.refreshResults(false);
      }
   }

   @Override
   public void onRecipesDisplayed(List<Recipe<?>> recipes) {
      for (Recipe<?> _snowman : recipes) {
         this.client.player.onRecipeDisplayed(_snowman);
      }
   }

   public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
      ItemStack _snowman = recipe.getOutput();
      this.ghostSlots.setRecipe(recipe);
      this.ghostSlots.addSlot(Ingredient.ofStacks(_snowman), slots.get(0).x, slots.get(0).y);
      this.alignRecipeToGrid(
         this.craftingScreenHandler.getCraftingWidth(),
         this.craftingScreenHandler.getCraftingHeight(),
         this.craftingScreenHandler.getCraftingResultSlotIndex(),
         recipe,
         recipe.getPreviewInputs().iterator(),
         0
      );
   }

   @Override
   public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
      Ingredient _snowman = inputs.next();
      if (!_snowman.isEmpty()) {
         Slot _snowmanx = this.craftingScreenHandler.slots.get(slot);
         this.ghostSlots.addSlot(_snowman, _snowmanx.x, _snowmanx.y);
      }
   }

   protected void sendBookDataPacket() {
      if (this.client.getNetworkHandler() != null) {
         RecipeBookCategory _snowman = this.craftingScreenHandler.getCategory();
         boolean _snowmanx = this.recipeBook.getOptions().isGuiOpen(_snowman);
         boolean _snowmanxx = this.recipeBook.getOptions().isFilteringCraftable(_snowman);
         this.client.getNetworkHandler().sendPacket(new RecipeCategoryOptionsC2SPacket(_snowman, _snowmanx, _snowmanxx));
      }
   }
}
