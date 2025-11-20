/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.advancement.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class InventoryChangedCriterion
extends AbstractCriterion<Conditions> {
    private static final Identifier ID = new Identifier("inventory_changed");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        JsonObject jsonObject2 = JsonHelper.getObject(jsonObject, "slots", new JsonObject());
        NumberRange.IntRange _snowman2 = NumberRange.IntRange.fromJson(jsonObject2.get("occupied"));
        NumberRange.IntRange _snowman3 = NumberRange.IntRange.fromJson(jsonObject2.get("full"));
        NumberRange.IntRange _snowman4 = NumberRange.IntRange.fromJson(jsonObject2.get("empty"));
        ItemPredicate[] _snowman5 = ItemPredicate.deserializeAll(jsonObject.get("items"));
        return new Conditions(extended, _snowman2, _snowman3, _snowman4, _snowman5);
    }

    public void trigger(ServerPlayerEntity player, PlayerInventory inventory, ItemStack stack) {
        int n = 0;
        _snowman = 0;
        _snowman = 0;
        for (_snowman = 0; _snowman < inventory.size(); ++_snowman) {
            ItemStack itemStack = inventory.getStack(_snowman);
            if (itemStack.isEmpty()) {
                ++_snowman;
                continue;
            }
            ++_snowman;
            if (itemStack.getCount() < itemStack.getMaxCount()) continue;
            ++n;
        }
        this.trigger(player, inventory, stack, n, _snowman, _snowman);
    }

    private void trigger(ServerPlayerEntity player, PlayerInventory inventory, ItemStack stack, int full, int empty, int occupied) {
        this.test(player, conditions -> conditions.matches(inventory, stack, full, empty, occupied));
    }

    @Override
    public /* synthetic */ AbstractCriterionConditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return this.conditionsFromJson(obj, playerPredicate, predicateDeserializer);
    }

    public static class Conditions
    extends AbstractCriterionConditions {
        private final NumberRange.IntRange occupied;
        private final NumberRange.IntRange full;
        private final NumberRange.IntRange empty;
        private final ItemPredicate[] items;

        public Conditions(EntityPredicate.Extended player, NumberRange.IntRange occupied, NumberRange.IntRange full, NumberRange.IntRange empty, ItemPredicate[] items) {
            super(ID, player);
            this.occupied = occupied;
            this.full = full;
            this.empty = empty;
            this.items = items;
        }

        public static Conditions items(ItemPredicate ... items) {
            return new Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, items);
        }

        public static Conditions items(ItemConvertible ... items) {
            ItemPredicate[] itemPredicateArray = new ItemPredicate[items.length];
            for (int i = 0; i < items.length; ++i) {
                itemPredicateArray[i] = new ItemPredicate(null, items[i].asItem(), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
            }
            return Conditions.items(itemPredicateArray);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            if (!(this.occupied.isDummy() && this.full.isDummy() && this.empty.isDummy())) {
                _snowman = new JsonObject();
                _snowman.add("occupied", this.occupied.toJson());
                _snowman.add("full", this.full.toJson());
                _snowman.add("empty", this.empty.toJson());
                jsonObject.add("slots", (JsonElement)_snowman);
            }
            if (this.items.length > 0) {
                _snowman = new JsonArray();
                for (ItemPredicate itemPredicate : this.items) {
                    _snowman.add(itemPredicate.toJson());
                }
                jsonObject.add("items", (JsonElement)_snowman);
            }
            return jsonObject;
        }

        public boolean matches(PlayerInventory inventory, ItemStack stack, int full, int empty, int occupied) {
            if (!this.full.test(full)) {
                return false;
            }
            if (!this.empty.test(empty)) {
                return false;
            }
            if (!this.occupied.test(occupied)) {
                return false;
            }
            int n = this.items.length;
            if (n == 0) {
                return true;
            }
            if (n == 1) {
                return !stack.isEmpty() && this.items[0].test(stack);
            }
            ObjectArrayList _snowman2 = new ObjectArrayList((Object[])this.items);
            _snowman = inventory.size();
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                if (_snowman2.isEmpty()) {
                    return true;
                }
                ItemStack itemStack = inventory.getStack(_snowman);
                if (itemStack.isEmpty()) continue;
                _snowman2.removeIf(itemPredicate -> itemPredicate.test(itemStack));
            }
            return _snowman2.isEmpty();
        }
    }
}

