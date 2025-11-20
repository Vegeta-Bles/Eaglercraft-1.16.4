/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeDisplayListener;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
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

public class RecipeBookWidget
extends DrawableHelper
implements Drawable,
Element,
RecipeDisplayListener,
RecipeGridAligner<Ingredient> {
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
        int n = (this.parentWidth - 147) / 2 - this.leftOffset;
        _snowman = (this.parentHeight - 166) / 2;
        this.recipeFinder.clear();
        this.client.player.inventory.populateRecipeFinder(this.recipeFinder);
        this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
        String _snowman2 = this.searchField != null ? this.searchField.getText() : "";
        this.searchField = new TextFieldWidget(this.client.textRenderer, n + 25, _snowman + 14, 80, this.client.textRenderer.fontHeight + 5, new TranslatableText("itemGroup.search"));
        this.searchField.setMaxLength(50);
        this.searchField.setHasBorder(false);
        this.searchField.setVisible(true);
        this.searchField.setEditableColor(0xFFFFFF);
        this.searchField.setText(_snowman2);
        this.recipesArea.initialize(this.client, n, _snowman);
        this.recipesArea.setGui(this);
        this.toggleCraftableButton = new ToggleButtonWidget(n + 110, _snowman + 12, 26, 16, this.recipeBook.isFilteringCraftable(this.craftingScreenHandler));
        this.setBookButtonTexture();
        this.tabButtons.clear();
        for (RecipeBookGroup recipeBookGroup : RecipeBookGroup.method_30285(this.craftingScreenHandler.getCategory())) {
            this.tabButtons.add(new RecipeGroupButtonWidget(recipeBookGroup));
        }
        if (this.currentTab != null) {
            this.currentTab = this.tabButtons.stream().filter(recipeGroupButtonWidget -> recipeGroupButtonWidget.getCategory().equals((Object)this.currentTab.getCategory())).findFirst().orElse(null);
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
        int n = this.isOpen() && !narrow ? 177 + (width - parentWidth - 200) / 2 : (width - parentWidth) / 2;
        return n;
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
        list.forEach(recipeResultCollection -> recipeResultCollection.computeCraftables(this.recipeFinder, this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.recipeBook));
        ArrayList _snowman2 = Lists.newArrayList(list);
        _snowman2.removeIf(recipeResultCollection -> !recipeResultCollection.isInitialized());
        _snowman2.removeIf(recipeResultCollection -> !recipeResultCollection.hasFittingRecipes());
        String _snowman3 = this.searchField.getText();
        if (!_snowman3.isEmpty()) {
            ObjectLinkedOpenHashSet objectLinkedOpenHashSet = new ObjectLinkedOpenHashSet(this.client.getSearchableContainer(SearchManager.RECIPE_OUTPUT).findAll(_snowman3.toLowerCase(Locale.ROOT)));
            _snowman2.removeIf(arg_0 -> RecipeBookWidget.method_2594((ObjectSet)objectLinkedOpenHashSet, arg_0));
        }
        if (this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
            _snowman2.removeIf(recipeResultCollection -> !recipeResultCollection.hasCraftableRecipes());
        }
        this.recipesArea.setResults(_snowman2, resetCurrentPage);
    }

    private void refreshTabButtons() {
        int n = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
        _snowman = (this.parentHeight - 166) / 2 + 3;
        _snowman = 27;
        _snowman = 0;
        for (RecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
            RecipeBookGroup recipeBookGroup = recipeGroupButtonWidget.getCategory();
            if (recipeBookGroup == RecipeBookGroup.CRAFTING_SEARCH || recipeBookGroup == RecipeBookGroup.FURNACE_SEARCH) {
                recipeGroupButtonWidget.visible = true;
                recipeGroupButtonWidget.setPos(n, _snowman + 27 * _snowman++);
                continue;
            }
            if (!recipeGroupButtonWidget.hasKnownRecipes(this.recipeBook)) continue;
            recipeGroupButtonWidget.setPos(n, _snowman + 27 * _snowman++);
            recipeGroupButtonWidget.checkForNewRecipes(this.client);
        }
    }

    public void update() {
        if (!this.isOpen()) {
            return;
        }
        if (this.cachedInvChangeCount != this.client.player.inventory.getChangeCount()) {
            this.refreshInputs();
            this.cachedInvChangeCount = this.client.player.inventory.getChangeCount();
        }
        this.searchField.tick();
    }

    private void refreshInputs() {
        this.recipeFinder.clear();
        this.client.player.inventory.populateRecipeFinder(this.recipeFinder);
        this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
        this.refreshResults(false);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.isOpen()) {
            return;
        }
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 100.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int n = (this.parentWidth - 147) / 2 - this.leftOffset;
        _snowman = (this.parentHeight - 166) / 2;
        this.drawTexture(matrices, n, _snowman, 1, 1, 147, 166);
        if (!this.searchField.isFocused() && this.searchField.getText().isEmpty()) {
            RecipeBookWidget.drawTextWithShadow(matrices, this.client.textRenderer, field_25711, n + 25, _snowman + 14, -1);
        } else {
            this.searchField.render(matrices, mouseX, mouseY, delta);
        }
        for (RecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
            recipeGroupButtonWidget.render(matrices, mouseX, mouseY, delta);
        }
        this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
        this.recipesArea.draw(matrices, n, _snowman, mouseX, mouseY, delta);
        RenderSystem.popMatrix();
    }

    public void drawTooltip(MatrixStack matrixStack2, int n, int n2, int n3, int n4) {
        MatrixStack matrixStack2;
        if (!this.isOpen()) {
            return;
        }
        this.recipesArea.drawTooltip(matrixStack2, n3, n4);
        if (this.toggleCraftableButton.isHovered()) {
            Text text = this.getCraftableButtonText();
            if (this.client.currentScreen != null) {
                this.client.currentScreen.renderTooltip(matrixStack2, text, n3, n4);
            }
        }
        this.drawGhostSlotTooltip(matrixStack2, n, n2, n3, n4);
    }

    private Text getCraftableButtonText() {
        return this.toggleCraftableButton.isToggled() ? this.getToggleCraftableButtonText() : field_26594;
    }

    protected Text getToggleCraftableButtonText() {
        return field_26593;
    }

    private void drawGhostSlotTooltip(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        ItemStack _snowman4 = null;
        for (int i = 0; i < this.ghostSlots.getSlotCount(); ++i) {
            RecipeBookGhostSlots.GhostInputSlot ghostInputSlot = this.ghostSlots.getSlot(i);
            int _snowman2 = ghostInputSlot.getX() + n;
            int _snowman3 = ghostInputSlot.getY() + n2;
            if (n3 < _snowman2 || n4 < _snowman3 || n3 >= _snowman2 + 16 || n4 >= _snowman3 + 16) continue;
            _snowman4 = ghostInputSlot.getCurrentItemStack();
        }
        if (_snowman4 != null && this.client.currentScreen != null) {
            this.client.currentScreen.renderTooltip(matrixStack, this.client.currentScreen.getTooltipFromItem(_snowman4), n3, n4);
        }
    }

    public void drawGhostSlots(MatrixStack matrixStack, int n, int n2, boolean bl, float f) {
        this.ghostSlots.draw(matrixStack, this.client, n, n2, bl, f);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isOpen() || this.client.player.isSpectator()) {
            return false;
        }
        if (this.recipesArea.mouseClicked(mouseX, mouseY, button, (this.parentWidth - 147) / 2 - this.leftOffset, (this.parentHeight - 166) / 2, 147, 166)) {
            Recipe<?> recipe = this.recipesArea.getLastClickedRecipe();
            RecipeResultCollection _snowman2 = this.recipesArea.getLastClickedResults();
            if (recipe != null && _snowman2 != null) {
                if (!_snowman2.isCraftable(recipe) && this.ghostSlots.getRecipe() == recipe) {
                    return false;
                }
                this.ghostSlots.reset();
                this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, recipe, Screen.hasShiftDown());
                if (!this.isWide()) {
                    this.setOpen(false);
                }
            }
            return true;
        }
        if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (this.toggleCraftableButton.mouseClicked(mouseX, mouseY, button)) {
            boolean _snowman3 = this.toggleFilteringCraftable();
            this.toggleCraftableButton.setToggled(_snowman3);
            this.sendBookDataPacket();
            this.refreshResults(false);
            return true;
        }
        for (RecipeGroupButtonWidget _snowman4 : this.tabButtons) {
            if (!_snowman4.mouseClicked(mouseX, mouseY, button)) continue;
            if (this.currentTab != _snowman4) {
                this.currentTab.setToggled(false);
                this.currentTab = _snowman4;
                this.currentTab.setToggled(true);
                this.refreshResults(true);
            }
            return true;
        }
        return false;
    }

    private boolean toggleFilteringCraftable() {
        RecipeBookCategory recipeBookCategory = this.craftingScreenHandler.getCategory();
        boolean _snowman2 = !this.recipeBook.isFilteringCraftable(recipeBookCategory);
        this.recipeBook.setFilteringCraftable(recipeBookCategory, _snowman2);
        return _snowman2;
    }

    public boolean isClickOutsideBounds(double d, double d2, int n, int n2, int n3, int n4, int n5) {
        if (!this.isOpen()) {
            return true;
        }
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + n3) || d2 >= (double)(n2 + n4);
        _snowman = (double)(n - 147) < d && d < (double)n && (double)n2 < d2 && d2 < (double)(n2 + n4);
        return bl && !_snowman && !this.currentTab.isHovered();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.searching = false;
        if (!this.isOpen() || this.client.player.isSpectator()) {
            return false;
        }
        if (keyCode == 256 && !this.isWide()) {
            this.setOpen(false);
            return true;
        }
        if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
            this.refreshSearchResults();
            return true;
        }
        if (this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256) {
            return true;
        }
        if (this.client.options.keyChat.matchesKey(keyCode, scanCode) && !this.searchField.isFocused()) {
            this.searching = true;
            this.searchField.setSelected(true);
            return true;
        }
        return false;
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
        }
        if (!this.isOpen() || this.client.player.isSpectator()) {
            return false;
        }
        if (this.searchField.charTyped(chr, keyCode)) {
            this.refreshSearchResults();
            return true;
        }
        return Element.super.charTyped(chr, keyCode);
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
            LanguageManager languageManager = this.client.getLanguageManager();
            LanguageDefinition _snowman2 = languageManager.getLanguage("en_pt");
            if (languageManager.getLanguage().compareTo(_snowman2) == 0) {
                return;
            }
            languageManager.setLanguage(_snowman2);
            this.client.options.language = _snowman2.getCode();
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
        for (Recipe<?> recipe : recipes) {
            this.client.player.onRecipeDisplayed(recipe);
        }
    }

    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack itemStack = recipe.getOutput();
        this.ghostSlots.setRecipe(recipe);
        this.ghostSlots.addSlot(Ingredient.ofStacks(itemStack), slots.get((int)0).x, slots.get((int)0).y);
        this.alignRecipeToGrid(this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.craftingScreenHandler.getCraftingResultSlotIndex(), recipe, recipe.getPreviewInputs().iterator(), 0);
    }

    @Override
    public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
        Ingredient ingredient = inputs.next();
        if (!ingredient.isEmpty()) {
            Slot slot2 = (Slot)this.craftingScreenHandler.slots.get(slot);
            this.ghostSlots.addSlot(ingredient, slot2.x, slot2.y);
        }
    }

    protected void sendBookDataPacket() {
        if (this.client.getNetworkHandler() != null) {
            RecipeBookCategory recipeBookCategory = this.craftingScreenHandler.getCategory();
            boolean _snowman2 = this.recipeBook.getOptions().isGuiOpen(recipeBookCategory);
            boolean _snowman3 = this.recipeBook.getOptions().isFilteringCraftable(recipeBookCategory);
            this.client.getNetworkHandler().sendPacket(new RecipeCategoryOptionsC2SPacket(recipeBookCategory, _snowman2, _snowman3));
        }
    }

    private static /* synthetic */ boolean method_2594(ObjectSet objectSet, RecipeResultCollection recipeResultCollection) {
        return !objectSet.contains((Object)recipeResultCollection);
    }
}

