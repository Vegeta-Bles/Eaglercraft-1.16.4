/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;

public class RecipeGroupButtonWidget
extends ToggleButtonWidget {
    private final RecipeBookGroup category;
    private float bounce;

    public RecipeGroupButtonWidget(RecipeBookGroup category) {
        super(0, 0, 35, 27, false);
        this.category = category;
        this.setTextureUV(153, 2, 35, 0, RecipeBookWidget.TEXTURE);
    }

    public void checkForNewRecipes(MinecraftClient client) {
        ClientRecipeBook clientRecipeBook = client.player.getRecipeBook();
        List<RecipeResultCollection> _snowman2 = clientRecipeBook.getResultsForGroup(this.category);
        if (!(client.player.currentScreenHandler instanceof AbstractRecipeScreenHandler)) {
            return;
        }
        for (RecipeResultCollection recipeResultCollection : _snowman2) {
            for (Recipe<?> recipe : recipeResultCollection.getResults(clientRecipeBook.isFilteringCraftable((AbstractRecipeScreenHandler)client.player.currentScreenHandler))) {
                if (!clientRecipeBook.shouldDisplay(recipe)) continue;
                this.bounce = 15.0f;
                return;
            }
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.bounce > 0.0f) {
            float f = 1.0f + 0.1f * (float)Math.sin(this.bounce / 15.0f * (float)Math.PI);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(this.x + 8, this.y + 12, 0.0f);
            RenderSystem.scalef(1.0f, f, 1.0f);
            RenderSystem.translatef(-(this.x + 8), -(this.y + 12), 0.0f);
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(this.texture);
        RenderSystem.disableDepthTest();
        int _snowman2 = this.u;
        int _snowman3 = this.v;
        if (this.toggled) {
            _snowman2 += this.pressedUOffset;
        }
        if (this.isHovered()) {
            _snowman3 += this.hoverVOffset;
        }
        int _snowman4 = this.x;
        if (this.toggled) {
            _snowman4 -= 2;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexture(matrices, _snowman4, this.y, _snowman2, _snowman3, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(minecraftClient.getItemRenderer());
        if (this.bounce > 0.0f) {
            RenderSystem.popMatrix();
            this.bounce -= delta;
        }
    }

    private void renderIcons(ItemRenderer itemRenderer) {
        List<ItemStack> list = this.category.getIcons();
        int n = _snowman = this.toggled ? -2 : 0;
        if (list.size() == 1) {
            itemRenderer.renderInGui(list.get(0), this.x + 9 + _snowman, this.y + 5);
        } else if (list.size() == 2) {
            itemRenderer.renderInGui(list.get(0), this.x + 3 + _snowman, this.y + 5);
            itemRenderer.renderInGui(list.get(1), this.x + 14 + _snowman, this.y + 5);
        }
    }

    public RecipeBookGroup getCategory() {
        return this.category;
    }

    public boolean hasKnownRecipes(ClientRecipeBook recipeBook) {
        List<RecipeResultCollection> list = recipeBook.getResultsForGroup(this.category);
        this.visible = false;
        if (list != null) {
            for (RecipeResultCollection recipeResultCollection : list) {
                if (!recipeResultCollection.isInitialized() || !recipeResultCollection.hasFittingRecipes()) continue;
                this.visible = true;
                break;
            }
        }
        return this.visible;
    }
}

