/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.recipebook;

import java.util.Set;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SmokerRecipeBookScreen
extends AbstractFurnaceRecipeBookScreen {
    private static final Text field_26597 = new TranslatableText("gui.recipebook.toggleRecipes.smokable");

    @Override
    protected Text getToggleCraftableButtonText() {
        return field_26597;
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}

