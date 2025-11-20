/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Streams
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.loot.function.SetNameLootFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class SetLoreLootFunction
extends ConditionalLootFunction {
    private final boolean replace;
    private final List<Text> lore;
    @Nullable
    private final LootContext.EntityTarget entity;

    public SetLoreLootFunction(LootCondition[] conditions, boolean replace, List<Text> lore, @Nullable LootContext.EntityTarget entity) {
        super(conditions);
        this.replace = replace;
        this.lore = ImmutableList.copyOf(lore);
        this.entity = entity;
    }

    @Override
    public LootFunctionType getType() {
        return LootFunctionTypes.SET_LORE;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return this.entity != null ? ImmutableSet.of(this.entity.getParameter()) : ImmutableSet.of();
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        ListTag listTag = this.getLoreForMerge(stack, !this.lore.isEmpty());
        if (listTag != null) {
            if (this.replace) {
                listTag.clear();
            }
            UnaryOperator<Text> unaryOperator = SetNameLootFunction.applySourceEntity(context, this.entity);
            this.lore.stream().map(unaryOperator).map(Text.Serializer::toJson).map(StringTag::of).forEach(listTag::add);
        }
        return stack;
    }

    @Nullable
    private ListTag getLoreForMerge(ItemStack stack, boolean otherLoreExists) {
        CompoundTag compoundTag;
        if (stack.hasTag()) {
            compoundTag = stack.getTag();
        } else if (otherLoreExists) {
            compoundTag = new CompoundTag();
            stack.setTag(compoundTag);
        } else {
            return null;
        }
        if (compoundTag.contains("display", 10)) {
            _snowman = compoundTag.getCompound("display");
        } else if (otherLoreExists) {
            _snowman = new CompoundTag();
            compoundTag.put("display", _snowman);
        } else {
            return null;
        }
        if (_snowman.contains("Lore", 9)) {
            return _snowman.getList("Lore", 8);
        }
        if (otherLoreExists) {
            ListTag listTag = new ListTag();
            _snowman.put("Lore", listTag);
            return listTag;
        }
        return null;
    }

    public static class Serializer
    extends ConditionalLootFunction.Serializer<SetLoreLootFunction> {
        @Override
        public void toJson(JsonObject jsonObject2, SetLoreLootFunction setLoreLootFunction, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject2;
            super.toJson(jsonObject2, setLoreLootFunction, jsonSerializationContext);
            jsonObject2.addProperty("replace", Boolean.valueOf(setLoreLootFunction.replace));
            JsonArray jsonArray = new JsonArray();
            for (Text text : setLoreLootFunction.lore) {
                jsonArray.add(Text.Serializer.toJsonTree(text));
            }
            jsonObject2.add("lore", (JsonElement)jsonArray);
            if (setLoreLootFunction.entity != null) {
                jsonObject2.add("entity", jsonSerializationContext.serialize((Object)setLoreLootFunction.entity));
            }
        }

        @Override
        public SetLoreLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditionArray) {
            boolean bl = JsonHelper.getBoolean(jsonObject, "replace", false);
            List _snowman2 = (List)Streams.stream((Iterable)JsonHelper.getArray(jsonObject, "lore")).map(Text.Serializer::fromJson).collect(ImmutableList.toImmutableList());
            LootContext.EntityTarget _snowman3 = JsonHelper.deserialize(jsonObject, "entity", null, jsonDeserializationContext, LootContext.EntityTarget.class);
            return new SetLoreLootFunction(lootConditionArray, bl, _snowman2, _snowman3);
        }

        @Override
        public /* synthetic */ ConditionalLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return this.fromJson(json, context, conditions);
        }
    }
}

