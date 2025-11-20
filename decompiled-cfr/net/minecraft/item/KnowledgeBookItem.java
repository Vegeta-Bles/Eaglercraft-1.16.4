/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowledgeBookItem
extends Item {
    private static final Logger LOGGER = LogManager.getLogger();

    public KnowledgeBookItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        CompoundTag _snowman2 = itemStack.getTag();
        if (!user.abilities.creativeMode) {
            user.setStackInHand(hand, ItemStack.EMPTY);
        }
        if (_snowman2 == null || !_snowman2.contains("Recipes", 9)) {
            LOGGER.error("Tag not valid: {}", (Object)_snowman2);
            return TypedActionResult.fail(itemStack);
        }
        if (!world.isClient) {
            ListTag listTag = _snowman2.getList("Recipes", 8);
            ArrayList _snowman3 = Lists.newArrayList();
            RecipeManager _snowman4 = world.getServer().getRecipeManager();
            for (int i = 0; i < listTag.size(); ++i) {
                String string = listTag.getString(i);
                Optional<Recipe<?>> _snowman5 = _snowman4.get(new Identifier(string));
                if (!_snowman5.isPresent()) {
                    LOGGER.error("Invalid recipe: {}", (Object)string);
                    return TypedActionResult.fail(itemStack);
                }
                _snowman3.add(_snowman5.get());
            }
            user.unlockRecipes(_snowman3);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}

