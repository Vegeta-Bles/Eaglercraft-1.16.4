/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
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
    private final List<GhostInputSlot> slots = Lists.newArrayList();
    private float time;

    public void reset() {
        this.recipe = null;
        this.slots.clear();
        this.time = 0.0f;
    }

    public void addSlot(Ingredient ingredient, int x, int y) {
        this.slots.add(new GhostInputSlot(ingredient, x, y));
    }

    public GhostInputSlot getSlot(int index) {
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

    public void draw(MatrixStack matrixStack, MinecraftClient minecraftClient, int n, int n2, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            this.time += f;
        }
        for (int i = 0; i < this.slots.size(); ++i) {
            GhostInputSlot ghostInputSlot = this.slots.get(i);
            int _snowman2 = ghostInputSlot.getX() + n;
            int _snowman3 = ghostInputSlot.getY() + n2;
            if (i == 0 && bl) {
                DrawableHelper.fill(matrixStack, _snowman2 - 4, _snowman3 - 4, _snowman2 + 20, _snowman3 + 20, 0x30FF0000);
            } else {
                DrawableHelper.fill(matrixStack, _snowman2, _snowman3, _snowman2 + 16, _snowman3 + 16, 0x30FF0000);
            }
            ItemStack _snowman4 = ghostInputSlot.getCurrentItemStack();
            ItemRenderer _snowman5 = minecraftClient.getItemRenderer();
            _snowman5.renderInGui(_snowman4, _snowman2, _snowman3);
            RenderSystem.depthFunc(516);
            DrawableHelper.fill(matrixStack, _snowman2, _snowman3, _snowman2 + 16, _snowman3 + 16, 0x30FFFFFF);
            RenderSystem.depthFunc(515);
            if (i != 0) continue;
            _snowman5.renderGuiItemOverlay(minecraftClient.textRenderer, _snowman4, _snowman2, _snowman3);
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
            ItemStack[] itemStackArray = this.ingredient.getMatchingStacksClient();
            return itemStackArray[MathHelper.floor(RecipeBookGhostSlots.this.time / 30.0f) % itemStackArray.length];
        }
    }
}

