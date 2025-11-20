/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeDisplayListener;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;

public class RecipeBookResults {
    private final List<AnimatedResultButton> resultButtons = Lists.newArrayListWithCapacity((int)20);
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
        for (int i = 0; i < 20; ++i) {
            this.resultButtons.add(new AnimatedResultButton());
        }
    }

    public void initialize(MinecraftClient minecraftClient, int parentLeft, int parentTop) {
        this.client = minecraftClient;
        this.recipeBook = minecraftClient.player.getRecipeBook();
        for (int i = 0; i < this.resultButtons.size(); ++i) {
            this.resultButtons.get(i).setPos(parentLeft + 11 + 25 * (i % 5), parentTop + 31 + 25 * (i / 5));
        }
        this.nextPageButton = new ToggleButtonWidget(parentLeft + 93, parentTop + 137, 12, 17, false);
        this.nextPageButton.setTextureUV(1, 208, 13, 18, RecipeBookWidget.TEXTURE);
        this.prevPageButton = new ToggleButtonWidget(parentLeft + 38, parentTop + 137, 12, 17, true);
        this.prevPageButton.setTextureUV(1, 208, 13, 18, RecipeBookWidget.TEXTURE);
    }

    public void setGui(RecipeBookWidget recipeBookWidget) {
        this.recipeDisplayListeners.remove(recipeBookWidget);
        this.recipeDisplayListeners.add(recipeBookWidget);
    }

    public void setResults(List<RecipeResultCollection> list, boolean resetCurrentPage) {
        this.resultCollections = list;
        this.pageCount = (int)Math.ceil((double)list.size() / 20.0);
        if (this.pageCount <= this.currentPage || resetCurrentPage) {
            this.currentPage = 0;
        }
        this.refreshResultButtons();
    }

    private void refreshResultButtons() {
        int n = 20 * this.currentPage;
        for (_snowman = 0; _snowman < this.resultButtons.size(); ++_snowman) {
            AnimatedResultButton animatedResultButton = this.resultButtons.get(_snowman);
            if (n + _snowman < this.resultCollections.size()) {
                RecipeResultCollection recipeResultCollection = this.resultCollections.get(n + _snowman);
                animatedResultButton.showResultCollection(recipeResultCollection, this);
                animatedResultButton.visible = true;
                continue;
            }
            animatedResultButton.visible = false;
        }
        this.hideShowPageButtons();
    }

    private void hideShowPageButtons() {
        this.nextPageButton.visible = this.pageCount > 1 && this.currentPage < this.pageCount - 1;
        this.prevPageButton.visible = this.pageCount > 1 && this.currentPage > 0;
    }

    public void draw(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f) {
        if (this.pageCount > 1) {
            String string = this.currentPage + 1 + "/" + this.pageCount;
            int _snowman2 = this.client.textRenderer.getWidth(string);
            this.client.textRenderer.draw(matrixStack, string, (float)(n - _snowman2 / 2 + 73), (float)(n2 + 141), -1);
        }
        this.hoveredResultButton = null;
        for (AnimatedResultButton _snowman3 : this.resultButtons) {
            _snowman3.render(matrixStack, n3, n4, f);
            if (!_snowman3.visible || !_snowman3.isHovered()) continue;
            this.hoveredResultButton = _snowman3;
        }
        this.prevPageButton.render(matrixStack, n3, n4, f);
        this.nextPageButton.render(matrixStack, n3, n4, f);
        this.alternatesWidget.render(matrixStack, n3, n4, f);
    }

    public void drawTooltip(MatrixStack matrixStack, int n, int n2) {
        if (this.client.currentScreen != null && this.hoveredResultButton != null && !this.alternatesWidget.isVisible()) {
            this.client.currentScreen.renderTooltip(matrixStack, this.hoveredResultButton.getTooltip(this.client.currentScreen), n, n2);
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
        }
        if (this.nextPageButton.mouseClicked(mouseX, mouseY, button)) {
            ++this.currentPage;
            this.refreshResultButtons();
            return true;
        }
        if (this.prevPageButton.mouseClicked(mouseX, mouseY, button)) {
            --this.currentPage;
            this.refreshResultButtons();
            return true;
        }
        for (AnimatedResultButton animatedResultButton : this.resultButtons) {
            if (!animatedResultButton.mouseClicked(mouseX, mouseY, button)) continue;
            if (button == 0) {
                this.lastClickedRecipe = animatedResultButton.currentRecipe();
                this.resultCollection = animatedResultButton.getResultCollection();
            } else if (button == 1 && !this.alternatesWidget.isVisible() && !animatedResultButton.hasResults()) {
                this.alternatesWidget.showAlternativesForResult(this.client, animatedResultButton.getResultCollection(), animatedResultButton.x, animatedResultButton.y, areaLeft + areaWidth / 2, areaTop + 13 + areaHeight / 2, animatedResultButton.getWidth());
            }
            return true;
        }
        return false;
    }

    public void onRecipesDisplayed(List<Recipe<?>> list) {
        for (RecipeDisplayListener recipeDisplayListener : this.recipeDisplayListeners) {
            recipeDisplayListener.onRecipesDisplayed(list);
        }
    }

    public MinecraftClient getMinecraftClient() {
        return this.client;
    }

    public RecipeBook getRecipeBook() {
        return this.recipeBook;
    }
}

