/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
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
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
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

public class RecipeAlternativesWidget
extends DrawableHelper
implements Drawable,
Element {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private final List<AlternativeButtonWidget> alternativeButtons = Lists.newArrayList();
    private boolean visible;
    private int buttonX;
    private int buttonY;
    private MinecraftClient client;
    private RecipeResultCollection resultCollection;
    private Recipe<?> lastClickedRecipe;
    private float time;
    private boolean furnace;

    public void showAlternativesForResult(MinecraftClient client, RecipeResultCollection results, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta) {
        this.client = client;
        this.resultCollection = results;
        if (client.player.currentScreenHandler instanceof AbstractFurnaceScreenHandler) {
            this.furnace = true;
        }
        boolean bl = client.player.getRecipeBook().isFilteringCraftable((AbstractRecipeScreenHandler)client.player.currentScreenHandler);
        List<Recipe<?>> _snowman2 = results.getRecipes(true);
        List _snowman3 = bl ? Collections.emptyList() : results.getRecipes(false);
        int _snowman4 = _snowman2.size();
        int _snowman5 = _snowman4 + _snowman3.size();
        int _snowman6 = _snowman5 <= 16 ? 4 : 5;
        int _snowman7 = (int)Math.ceil((float)_snowman5 / (float)_snowman6);
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        int _snowman8 = 25;
        float _snowman9 = this.buttonX + Math.min(_snowman5, _snowman6) * 25;
        if (_snowman9 > (_snowman = (float)(areaCenterX + 50))) {
            this.buttonX = (int)((float)this.buttonX - delta * (float)((int)((_snowman9 - _snowman) / delta)));
        }
        if ((_snowman = (float)(this.buttonY + _snowman7 * 25)) > (_snowman = (float)(areaCenterY + 50))) {
            this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((_snowman - _snowman) / delta));
        }
        if ((_snowman = (float)this.buttonY) < (_snowman = (float)(areaCenterY - 100))) {
            this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((_snowman - _snowman) / delta));
        }
        this.visible = true;
        this.alternativeButtons.clear();
        for (int i = 0; i < _snowman5; ++i) {
            boolean bl2 = i < _snowman4;
            Recipe _snowman10 = bl2 ? _snowman2.get(i) : (Recipe)_snowman3.get(i - _snowman4);
            int _snowman11 = this.buttonX + 4 + 25 * (i % _snowman6);
            int _snowman12 = this.buttonY + 5 + 25 * (i / _snowman6);
            if (this.furnace) {
                this.alternativeButtons.add(new FurnaceAlternativeButtonWidget(_snowman11, _snowman12, _snowman10, bl2));
                continue;
            }
            this.alternativeButtons.add(new AlternativeButtonWidget(_snowman11, _snowman12, _snowman10, bl2));
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
        }
        for (AlternativeButtonWidget alternativeButtonWidget : this.alternativeButtons) {
            if (!alternativeButtonWidget.mouseClicked(mouseX, mouseY, button)) continue;
            this.lastClickedRecipe = alternativeButtonWidget.recipe;
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) {
            return;
        }
        this.time += delta;
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 170.0f);
        int n = this.alternativeButtons.size() <= 16 ? 4 : 5;
        _snowman = Math.min(this.alternativeButtons.size(), n);
        _snowman = MathHelper.ceil((float)this.alternativeButtons.size() / (float)n);
        _snowman = 24;
        _snowman = 4;
        _snowman = 82;
        _snowman = 208;
        this.renderGrid(matrices, _snowman, _snowman, 24, 4, 82, 208);
        RenderSystem.disableBlend();
        for (AlternativeButtonWidget alternativeButtonWidget : this.alternativeButtons) {
            alternativeButtonWidget.render(matrices, mouseX, mouseY, delta);
        }
        RenderSystem.popMatrix();
    }

    private void renderGrid(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6) {
        this.drawTexture(matrixStack, this.buttonX, this.buttonY, n5, n6, n4, n4);
        this.drawTexture(matrixStack, this.buttonX + n4 * 2 + n * n3, this.buttonY, n5 + n3 + n4, n6, n4, n4);
        this.drawTexture(matrixStack, this.buttonX, this.buttonY + n4 * 2 + n2 * n3, n5, n6 + n3 + n4, n4, n4);
        this.drawTexture(matrixStack, this.buttonX + n4 * 2 + n * n3, this.buttonY + n4 * 2 + n2 * n3, n5 + n3 + n4, n6 + n3 + n4, n4, n4);
        for (_snowman = 0; _snowman < n; ++_snowman) {
            this.drawTexture(matrixStack, this.buttonX + n4 + _snowman * n3, this.buttonY, n5 + n4, n6, n3, n4);
            this.drawTexture(matrixStack, this.buttonX + n4 + (_snowman + 1) * n3, this.buttonY, n5 + n4, n6, n4, n4);
            for (_snowman = 0; _snowman < n2; ++_snowman) {
                if (_snowman == 0) {
                    this.drawTexture(matrixStack, this.buttonX, this.buttonY + n4 + _snowman * n3, n5, n6 + n4, n4, n3);
                    this.drawTexture(matrixStack, this.buttonX, this.buttonY + n4 + (_snowman + 1) * n3, n5, n6 + n4, n4, n4);
                }
                this.drawTexture(matrixStack, this.buttonX + n4 + _snowman * n3, this.buttonY + n4 + _snowman * n3, n5 + n4, n6 + n4, n3, n3);
                this.drawTexture(matrixStack, this.buttonX + n4 + (_snowman + 1) * n3, this.buttonY + n4 + _snowman * n3, n5 + n4, n6 + n4, n4, n3);
                this.drawTexture(matrixStack, this.buttonX + n4 + _snowman * n3, this.buttonY + n4 + (_snowman + 1) * n3, n5 + n4, n6 + n4, n3, n4);
                this.drawTexture(matrixStack, this.buttonX + n4 + (_snowman + 1) * n3 - 1, this.buttonY + n4 + (_snowman + 1) * n3 - 1, n5 + n4, n6 + n4, n4 + 1, n4 + 1);
                if (_snowman != n - 1) continue;
                this.drawTexture(matrixStack, this.buttonX + n4 * 2 + n * n3, this.buttonY + n4 + _snowman * n3, n5 + n3 + n4, n6 + n4, n4, n3);
                this.drawTexture(matrixStack, this.buttonX + n4 * 2 + n * n3, this.buttonY + n4 + (_snowman + 1) * n3, n5 + n3 + n4, n6 + n4, n4, n4);
            }
            this.drawTexture(matrixStack, this.buttonX + n4 + _snowman * n3, this.buttonY + n4 * 2 + n2 * n3, n5 + n4, n6 + n3 + n4, n3, n4);
            this.drawTexture(matrixStack, this.buttonX + n4 + (_snowman + 1) * n3, this.buttonY + n4 * 2 + n2 * n3, n5 + n4, n6 + n3 + n4, n4, n4);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    class AlternativeButtonWidget
    extends AbstractButtonWidget
    implements RecipeGridAligner<Ingredient> {
        private final Recipe<?> recipe;
        private final boolean craftable;
        protected final List<InputSlot> slots;

        public AlternativeButtonWidget(int x, int y, Recipe<?> recipe, boolean craftable) {
            super(x, y, 200, 20, LiteralText.EMPTY);
            this.slots = Lists.newArrayList();
            this.width = 24;
            this.height = 24;
            this.recipe = recipe;
            this.craftable = craftable;
            this.alignRecipe(recipe);
        }

        protected void alignRecipe(Recipe<?> recipe) {
            this.alignRecipeToGrid(3, 3, -1, recipe, recipe.getPreviewInputs().iterator(), 0);
        }

        @Override
        public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
            ItemStack[] itemStackArray = inputs.next().getMatchingStacksClient();
            if (itemStackArray.length != 0) {
                this.slots.add(new InputSlot(3 + gridY * 7, 3 + gridX * 7, itemStackArray));
            }
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RenderSystem.enableAlphaTest();
            RecipeAlternativesWidget.this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
            int n = 152;
            if (!this.craftable) {
                n += 26;
            }
            int n2 = _snowman = RecipeAlternativesWidget.this.furnace ? 130 : 78;
            if (this.isHovered()) {
                _snowman += 26;
            }
            this.drawTexture(matrices, this.x, this.y, n, _snowman, this.width, this.height);
            for (InputSlot inputSlot : this.slots) {
                RenderSystem.pushMatrix();
                float f = 0.42f;
                int _snowman2 = (int)((float)(this.x + inputSlot.y) / 0.42f - 3.0f);
                int _snowman3 = (int)((float)(this.y + inputSlot.x) / 0.42f - 3.0f);
                RenderSystem.scalef(0.42f, 0.42f, 1.0f);
                RecipeAlternativesWidget.this.client.getItemRenderer().renderInGuiWithOverrides(inputSlot.stacks[MathHelper.floor(RecipeAlternativesWidget.this.time / 30.0f) % inputSlot.stacks.length], _snowman2, _snowman3);
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

    class FurnaceAlternativeButtonWidget
    extends AlternativeButtonWidget {
        public FurnaceAlternativeButtonWidget(int n, int n2, Recipe<?> recipe, boolean bl) {
            super(n, n2, recipe, bl);
        }

        @Override
        protected void alignRecipe(Recipe<?> recipe) {
            ItemStack[] itemStackArray = recipe.getPreviewInputs().get(0).getMatchingStacksClient();
            this.slots.add(new AlternativeButtonWidget.InputSlot(10, 10, itemStackArray));
        }
    }
}

