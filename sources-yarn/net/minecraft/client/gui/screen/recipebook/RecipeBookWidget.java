package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int i = (this.parentWidth - 147) / 2 - this.leftOffset;
      int j = (this.parentHeight - 166) / 2;
      this.recipeFinder.clear();
      this.client.player.inventory.populateRecipeFinder(this.recipeFinder);
      this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
      String string = this.searchField != null ? this.searchField.getText() : "";
      this.searchField = new TextFieldWidget(this.client.textRenderer, i + 25, j + 14, 80, 9 + 5, new TranslatableText("itemGroup.search"));
      this.searchField.setMaxLength(50);
      this.searchField.setHasBorder(false);
      this.searchField.setVisible(true);
      this.searchField.setEditableColor(16777215);
      this.searchField.setText(string);
      this.recipesArea.initialize(this.client, i, j);
      this.recipesArea.setGui(this);
      this.toggleCraftableButton = new ToggleButtonWidget(i + 110, j + 12, 26, 16, this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));
      this.setBookButtonTexture();
      this.tabButtons.clear();

      for (RecipeBookGroup lv : RecipeBookGroup.method_30285(this.craftingScreenHandler.getCategory())) {
         this.tabButtons.add(new RecipeGroupButtonWidget(lv));
      }

      if (this.currentTab != null) {
         this.currentTab = this.tabButtons.stream().filter(arg -> arg.getCategory().equals(this.currentTab.getCategory())).findFirst().orElse(null);
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
      int k;
      if (this.isOpen() && !narrow) {
         k = 177 + (width - parentWidth - 200) / 2;
      } else {
         k = (width - parentWidth) / 2;
      }

      return k;
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
      List<RecipeResultCollection> list = this.recipeBook.getResultsForGroup(this.currentTab.getCategory());
      list.forEach(
         arg -> arg.computeCraftables(
               this.recipeFinder, this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.recipeBook
            )
      );
      List<RecipeResultCollection> list2 = Lists.newArrayList(list);
      list2.removeIf(arg -> !arg.isInitialized());
      list2.removeIf(arg -> !arg.hasFittingRecipes());
      String string = this.searchField.getText();
      if (!string.isEmpty()) {
         ObjectSet<RecipeResultCollection> objectSet = new ObjectLinkedOpenHashSet(
            this.client.getSearchableContainer(SearchManager.RECIPE_OUTPUT).findAll(string.toLowerCase(Locale.ROOT))
         );
         list2.removeIf(arg -> !objectSet.contains(arg));
      }

      if (this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
         list2.removeIf(arg -> !arg.hasCraftableRecipes());
      }

      this.recipesArea.setResults(list2, resetCurrentPage);
   }

   private void refreshTabButtons() {
      int i = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
      int j = (this.parentHeight - 166) / 2 + 3;
      int k = 27;
      int l = 0;

      for (RecipeGroupButtonWidget lv : this.tabButtons) {
         RecipeBookGroup lv2 = lv.getCategory();
         if (lv2 == RecipeBookGroup.CRAFTING_SEARCH || lv2 == RecipeBookGroup.FURNACE_SEARCH) {
            lv.visible = true;
            lv.setPos(i, j + 27 * l++);
         } else if (lv.hasKnownRecipes(this.recipeBook)) {
            lv.setPos(i, j + 27 * l++);
            lv.checkForNewRecipes(this.client);
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
         int k = (this.parentWidth - 147) / 2 - this.leftOffset;
         int l = (this.parentHeight - 166) / 2;
         this.drawTexture(matrices, k, l, 1, 1, 147, 166);
         if (!this.searchField.isFocused() && this.searchField.getText().isEmpty()) {
            drawTextWithShadow(matrices, this.client.textRenderer, field_25711, k + 25, l + 14, -1);
         } else {
            this.searchField.render(matrices, mouseX, mouseY, delta);
         }

         for (RecipeGroupButtonWidget lv : this.tabButtons) {
            lv.render(matrices, mouseX, mouseY, delta);
         }

         this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
         this.recipesArea.draw(matrices, k, l, mouseX, mouseY, delta);
         RenderSystem.popMatrix();
      }
   }

   public void drawTooltip(MatrixStack arg, int i, int j, int k, int l) {
      if (this.isOpen()) {
         this.recipesArea.drawTooltip(arg, k, l);
         if (this.toggleCraftableButton.isHovered()) {
            Text lv = this.getCraftableButtonText();
            if (this.client.currentScreen != null) {
               this.client.currentScreen.renderTooltip(arg, lv, k, l);
            }
         }

         this.drawGhostSlotTooltip(arg, i, j, k, l);
      }
   }

   private Text getCraftableButtonText() {
      return this.toggleCraftableButton.isToggled() ? this.getToggleCraftableButtonText() : field_26594;
   }

   protected Text getToggleCraftableButtonText() {
      return field_26593;
   }

   private void drawGhostSlotTooltip(MatrixStack arg, int i, int j, int k, int l) {
      ItemStack lv = null;

      for (int m = 0; m < this.ghostSlots.getSlotCount(); m++) {
         RecipeBookGhostSlots.GhostInputSlot lv2 = this.ghostSlots.getSlot(m);
         int n = lv2.getX() + i;
         int o = lv2.getY() + j;
         if (k >= n && l >= o && k < n + 16 && l < o + 16) {
            lv = lv2.getCurrentItemStack();
         }
      }

      if (lv != null && this.client.currentScreen != null) {
         this.client.currentScreen.renderTooltip(arg, this.client.currentScreen.getTooltipFromItem(lv), k, l);
      }
   }

   public void drawGhostSlots(MatrixStack arg, int i, int j, boolean bl, float f) {
      this.ghostSlots.draw(arg, this.client, i, j, bl, f);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isOpen() && !this.client.player.isSpectator()) {
         if (this.recipesArea.mouseClicked(mouseX, mouseY, button, (this.parentWidth - 147) / 2 - this.leftOffset, (this.parentHeight - 166) / 2, 147, 166)) {
            Recipe<?> lv = this.recipesArea.getLastClickedRecipe();
            RecipeResultCollection lv2 = this.recipesArea.getLastClickedResults();
            if (lv != null && lv2 != null) {
               if (!lv2.isCraftable(lv) && this.ghostSlots.getRecipe() == lv) {
                  return false;
               }

               this.ghostSlots.reset();
               this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, lv, Screen.hasShiftDown());
               if (!this.isWide()) {
                  this.setOpen(false);
               }
            }

            return true;
         } else if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
            return true;
         } else if (this.toggleCraftableButton.mouseClicked(mouseX, mouseY, button)) {
            boolean bl = this.toggleFilteringCraftable();
            this.toggleCraftableButton.setToggled(bl);
            this.sendBookDataPacket();
            this.refreshResults(false);
            return true;
         } else {
            for (RecipeGroupButtonWidget lv3 : this.tabButtons) {
               if (lv3.mouseClicked(mouseX, mouseY, button)) {
                  if (this.currentTab != lv3) {
                     this.currentTab.setToggled(false);
                     this.currentTab = lv3;
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
      RecipeBookCategory lv = this.craftingScreenHandler.getCategory();
      boolean bl = !this.recipeBook.isFilteringCraftable(lv);
      this.recipeBook.setFilteringCraftable(lv, bl);
      return bl;
   }

   public boolean isClickOutsideBounds(double d, double e, int i, int j, int k, int l, int m) {
      if (!this.isOpen()) {
         return true;
      } else {
         boolean bl = d < (double)i || e < (double)j || d >= (double)(i + k) || e >= (double)(j + l);
         boolean bl2 = (double)(i - 147) < d && d < (double)i && (double)j < e && e < (double)(j + l);
         return bl && !bl2 && !this.currentTab.isHovered();
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
      String string = this.searchField.getText().toLowerCase(Locale.ROOT);
      this.triggerPirateSpeakEasterEgg(string);
      if (!string.equals(this.searchText)) {
         this.refreshResults(false);
         this.searchText = string;
      }
   }

   private void triggerPirateSpeakEasterEgg(String string) {
      if ("excitedze".equals(string)) {
         LanguageManager lv = this.client.getLanguageManager();
         LanguageDefinition lv2 = lv.getLanguage("en_pt");
         if (lv.getLanguage().compareTo(lv2) == 0) {
            return;
         }

         lv.setLanguage(lv2);
         this.client.options.language = lv2.getCode();
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
      for (Recipe<?> lv : recipes) {
         this.client.player.onRecipeDisplayed(lv);
      }
   }

   public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
      ItemStack lv = recipe.getOutput();
      this.ghostSlots.setRecipe(recipe);
      this.ghostSlots.addSlot(Ingredient.ofStacks(lv), slots.get(0).x, slots.get(0).y);
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
      Ingredient lv = inputs.next();
      if (!lv.isEmpty()) {
         Slot lv2 = this.craftingScreenHandler.slots.get(slot);
         this.ghostSlots.addSlot(lv, lv2.x, lv2.y);
      }
   }

   protected void sendBookDataPacket() {
      if (this.client.getNetworkHandler() != null) {
         RecipeBookCategory lv = this.craftingScreenHandler.getCategory();
         boolean bl = this.recipeBook.getOptions().isGuiOpen(lv);
         boolean bl2 = this.recipeBook.getOptions().isFilteringCraftable(lv);
         this.client.getNetworkHandler().sendPacket(new RecipeCategoryOptionsC2SPacket(lv, bl, bl2));
      }
   }
}
