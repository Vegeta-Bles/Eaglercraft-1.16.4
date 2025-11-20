/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class SetAttributesLootFunction
extends ConditionalLootFunction {
    private final List<Attribute> attributes;

    private SetAttributesLootFunction(LootCondition[] conditions, List<Attribute> attributes) {
        super(conditions);
        this.attributes = ImmutableList.copyOf(attributes);
    }

    @Override
    public LootFunctionType getType() {
        return LootFunctionTypes.SET_ATTRIBUTES;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        Random random = context.getRandom();
        for (Attribute attribute : this.attributes) {
            UUID uUID = attribute.id;
            if (uUID == null) {
                uUID = UUID.randomUUID();
            }
            EquipmentSlot _snowman2 = Util.getRandom(attribute.slots, random);
            stack.addAttributeModifier(attribute.attribute, new EntityAttributeModifier(uUID, attribute.name, (double)attribute.amountRange.nextFloat(random), attribute.operation), _snowman2);
        }
        return stack;
    }

    static class Attribute {
        private final String name;
        private final EntityAttribute attribute;
        private final EntityAttributeModifier.Operation operation;
        private final UniformLootTableRange amountRange;
        @Nullable
        private final UUID id;
        private final EquipmentSlot[] slots;

        private Attribute(String name, EntityAttribute entityAttribute, EntityAttributeModifier.Operation operation, UniformLootTableRange amountRange, EquipmentSlot[] slots, @Nullable UUID id) {
            this.name = name;
            this.attribute = entityAttribute;
            this.operation = operation;
            this.amountRange = amountRange;
            this.id = id;
            this.slots = slots;
        }

        public JsonObject serialize(JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", this.name);
            jsonObject.addProperty("attribute", Registry.ATTRIBUTE.getId(this.attribute).toString());
            jsonObject.addProperty("operation", Attribute.getName(this.operation));
            jsonObject.add("amount", context.serialize((Object)this.amountRange));
            if (this.id != null) {
                jsonObject.addProperty("id", this.id.toString());
            }
            if (this.slots.length == 1) {
                jsonObject.addProperty("slot", this.slots[0].getName());
            } else {
                JsonArray jsonArray = new JsonArray();
                for (EquipmentSlot equipmentSlot : this.slots) {
                    jsonArray.add((JsonElement)new JsonPrimitive(equipmentSlot.getName()));
                }
                jsonObject.add("slot", (JsonElement)jsonArray);
            }
            return jsonObject;
        }

        public static Attribute deserialize(JsonObject json, JsonDeserializationContext context) {
            Object object;
            EquipmentSlot[] _snowman7;
            String string = JsonHelper.getString(json, "name");
            Identifier _snowman2 = new Identifier(JsonHelper.getString(json, "attribute"));
            EntityAttribute _snowman3 = Registry.ATTRIBUTE.get(_snowman2);
            if (_snowman3 == null) {
                throw new JsonSyntaxException("Unknown attribute: " + _snowman2);
            }
            EntityAttributeModifier.Operation _snowman4 = Attribute.fromName(JsonHelper.getString(json, "operation"));
            UniformLootTableRange _snowman5 = JsonHelper.deserialize(json, "amount", context, UniformLootTableRange.class);
            UUID _snowman6 = null;
            if (JsonHelper.hasString(json, "slot")) {
                _snowman7 = new EquipmentSlot[]{EquipmentSlot.byName(JsonHelper.getString(json, "slot"))};
            } else if (JsonHelper.hasArray(json, "slot")) {
                object = JsonHelper.getArray(json, "slot");
                _snowman7 = new EquipmentSlot[object.size()];
                int _snowman8 = 0;
                for (JsonElement jsonElement : object) {
                    _snowman7[_snowman8++] = EquipmentSlot.byName(JsonHelper.asString(jsonElement, "slot"));
                }
                if (_snowman7.length == 0) {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            } else {
                throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
            }
            if (json.has("id")) {
                object = JsonHelper.getString(json, "id");
                try {
                    _snowman6 = UUID.fromString((String)object);
                }
                catch (IllegalArgumentException _snowman9) {
                    throw new JsonSyntaxException("Invalid attribute modifier id '" + (String)object + "' (must be UUID format, with dashes)");
                }
            }
            return new Attribute(string, _snowman3, _snowman4, _snowman5, _snowman7, _snowman6);
        }

        private static String getName(EntityAttributeModifier.Operation operation) {
            switch (operation) {
                case ADDITION: {
                    return "addition";
                }
                case MULTIPLY_BASE: {
                    return "multiply_base";
                }
                case MULTIPLY_TOTAL: {
                    return "multiply_total";
                }
            }
            throw new IllegalArgumentException("Unknown operation " + (Object)((Object)operation));
        }

        private static EntityAttributeModifier.Operation fromName(String name) {
            switch (name) {
                case "addition": {
                    return EntityAttributeModifier.Operation.ADDITION;
                }
                case "multiply_base": {
                    return EntityAttributeModifier.Operation.MULTIPLY_BASE;
                }
                case "multiply_total": {
                    return EntityAttributeModifier.Operation.MULTIPLY_TOTAL;
                }
            }
            throw new JsonSyntaxException("Unknown attribute modifier operation " + name);
        }
    }

    public static class Serializer
    extends ConditionalLootFunction.Serializer<SetAttributesLootFunction> {
        @Override
        public void toJson(JsonObject jsonObject2, SetAttributesLootFunction setAttributesLootFunction, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject2;
            super.toJson(jsonObject2, setAttributesLootFunction, jsonSerializationContext);
            JsonArray jsonArray = new JsonArray();
            for (Attribute attribute : setAttributesLootFunction.attributes) {
                jsonArray.add((JsonElement)attribute.serialize(jsonSerializationContext));
            }
            jsonObject2.add("modifiers", (JsonElement)jsonArray);
        }

        @Override
        public SetAttributesLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditionArray2) {
            LootCondition[] lootConditionArray2;
            JsonArray jsonArray = JsonHelper.getArray(jsonObject, "modifiers");
            ArrayList _snowman2 = Lists.newArrayListWithExpectedSize((int)jsonArray.size());
            for (JsonElement jsonElement : jsonArray) {
                _snowman2.add(Attribute.deserialize(JsonHelper.asObject(jsonElement, "modifier"), jsonDeserializationContext));
            }
            if (_snowman2.isEmpty()) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            }
            return new SetAttributesLootFunction(lootConditionArray2, _snowman2);
        }

        @Override
        public /* synthetic */ ConditionalLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return this.fromJson(json, context, conditions);
        }
    }
}

