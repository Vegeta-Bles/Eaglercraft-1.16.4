/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.s2c.play.UnlockRecipesS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook
extends RecipeBook {
    private static final Logger LOGGER = LogManager.getLogger();

    public int unlockRecipes(Collection<Recipe<?>> recipes, ServerPlayerEntity player) {
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        for (Recipe<?> recipe : recipes) {
            Identifier identifier = recipe.getId();
            if (this.recipes.contains(identifier) || recipe.isIgnoredInRecipeBook()) continue;
            this.add(identifier);
            this.display(identifier);
            arrayList.add(identifier);
            Criteria.RECIPE_UNLOCKED.trigger(player, recipe);
            ++_snowman2;
        }
        this.sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action.ADD, player, arrayList);
        return _snowman2;
    }

    public int lockRecipes(Collection<Recipe<?>> recipes, ServerPlayerEntity player) {
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        for (Recipe<?> recipe : recipes) {
            Identifier identifier = recipe.getId();
            if (!this.recipes.contains(identifier)) continue;
            this.remove(identifier);
            arrayList.add(identifier);
            ++_snowman2;
        }
        this.sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action.REMOVE, player, arrayList);
        return _snowman2;
    }

    private void sendUnlockRecipesPacket(UnlockRecipesS2CPacket.Action action, ServerPlayerEntity player, List<Identifier> recipeIds) {
        player.networkHandler.sendPacket(new UnlockRecipesS2CPacket(action, recipeIds, Collections.emptyList(), this.getOptions()));
    }

    public CompoundTag toTag() {
        CompoundTag compoundTag = new CompoundTag();
        this.getOptions().toTag(compoundTag);
        ListTag _snowman2 = new ListTag();
        for (Identifier identifier : this.recipes) {
            _snowman2.add(StringTag.of(identifier.toString()));
        }
        compoundTag.put("recipes", _snowman2);
        ListTag _snowman3 = new ListTag();
        for (Identifier identifier : this.toBeDisplayed) {
            _snowman3.add(StringTag.of(identifier.toString()));
        }
        compoundTag.put("toBeDisplayed", _snowman3);
        return compoundTag;
    }

    public void fromTag(CompoundTag tag, RecipeManager recipeManager) {
        this.setOptions(RecipeBookOptions.fromTag(tag));
        ListTag listTag = tag.getList("recipes", 8);
        this.handleList(listTag, this::add, recipeManager);
        _snowman = tag.getList("toBeDisplayed", 8);
        this.handleList(_snowman, this::display, recipeManager);
    }

    private void handleList(ListTag list, Consumer<Recipe<?>> handler, RecipeManager recipeManager) {
        for (int i = 0; i < list.size(); ++i) {
            String string = list.getString(i);
            try {
                Identifier identifier = new Identifier(string);
                Optional<Recipe<?>> _snowman2 = recipeManager.get(identifier);
                if (!_snowman2.isPresent()) {
                    LOGGER.error("Tried to load unrecognized recipe: {} removed now.", (Object)identifier);
                    continue;
                }
                handler.accept(_snowman2.get());
                continue;
            }
            catch (InvalidIdentifierException invalidIdentifierException) {
                LOGGER.error("Tried to load improperly formatted recipe: {} removed now.", (Object)string);
            }
        }
    }

    public void sendInitRecipesPacket(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new UnlockRecipesS2CPacket(UnlockRecipesS2CPacket.Action.INIT, this.recipes, this.toBeDisplayed, this.getOptions()));
    }
}

