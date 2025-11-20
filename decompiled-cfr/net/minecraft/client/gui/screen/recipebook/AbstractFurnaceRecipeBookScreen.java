/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public abstract class AbstractFurnaceRecipeBookScreen
extends RecipeBookWidget {
    private Iterator<Item> fuelIterator;
    private Set<Item> fuels;
    private Slot outputSlot;
    private Item currentItem;
    private float frameTime;

    @Override
    protected void setBookButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 182, 28, 18, TEXTURE);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.id < this.craftingScreenHandler.getCraftingSlotCount()) {
            this.outputSlot = null;
        }
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack itemStack = recipe.getOutput();
        this.ghostSlots.setRecipe(recipe);
        this.ghostSlots.addSlot(Ingredient.ofStacks(itemStack), slots.get((int)2).x, slots.get((int)2).y);
        DefaultedList<Ingredient> _snowman2 = recipe.getPreviewInputs();
        this.outputSlot = slots.get(1);
        if (this.fuels == null) {
            this.fuels = this.getAllowedFuels();
        }
        this.fuelIterator = this.fuels.iterator();
        this.currentItem = null;
        Iterator _snowman3 = _snowman2.iterator();
        for (int i = 0; i < 2; ++i) {
            if (!_snowman3.hasNext()) {
                return;
            }
            Ingredient ingredient = (Ingredient)_snowman3.next();
            if (ingredient.isEmpty()) continue;
            Slot _snowman4 = slots.get(i);
            this.ghostSlots.addSlot(ingredient, _snowman4.x, _snowman4.y);
        }
    }

    protected abstract Set<Item> getAllowedFuels();

    @Override
    public void drawGhostSlots(MatrixStack matrixStack, int n, int n2, boolean bl, float f) {
        super.drawGhostSlots(matrixStack, n, n2, bl, f);
        if (this.outputSlot == null) {
            return;
        }
        if (!Screen.hasControlDown()) {
            this.frameTime += f;
        }
        int n3 = this.outputSlot.x + n;
        _snowman = this.outputSlot.y + n2;
        DrawableHelper.fill(matrixStack, n3, _snowman, n3 + 16, _snowman + 16, 0x30FF0000);
        this.client.getItemRenderer().renderInGuiWithOverrides(this.client.player, this.getItem().getDefaultStack(), n3, _snowman);
        RenderSystem.depthFunc(516);
        DrawableHelper.fill(matrixStack, n3, _snowman, n3 + 16, _snowman + 16, 0x30FFFFFF);
        RenderSystem.depthFunc(515);
    }

    private Item getItem() {
        if (this.currentItem == null || this.frameTime > 30.0f) {
            this.frameTime = 0.0f;
            if (this.fuelIterator == null || !this.fuelIterator.hasNext()) {
                if (this.fuels == null) {
                    this.fuels = this.getAllowedFuels();
                }
                this.fuelIterator = this.fuels.iterator();
            }
            this.currentItem = this.fuelIterator.next();
        }
        return this.currentItem;
    }
}

