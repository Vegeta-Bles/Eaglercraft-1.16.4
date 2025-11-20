/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 */
package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.LootFunctionTypes;

public class ExplosionDecayLootFunction
extends ConditionalLootFunction {
    private ExplosionDecayLootFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    public LootFunctionType getType() {
        return LootFunctionTypes.EXPLOSION_DECAY;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        Float f = context.get(LootContextParameters.EXPLOSION_RADIUS);
        if (f != null) {
            Random random = context.getRandom();
            float _snowman2 = 1.0f / f.floatValue();
            int _snowman3 = stack.getCount();
            int _snowman4 = 0;
            for (int i = 0; i < _snowman3; ++i) {
                if (!(random.nextFloat() <= _snowman2)) continue;
                ++_snowman4;
            }
            stack.setCount(_snowman4);
        }
        return stack;
    }

    public static ConditionalLootFunction.Builder<?> builder() {
        return ExplosionDecayLootFunction.builder(ExplosionDecayLootFunction::new);
    }

    public static class Serializer
    extends ConditionalLootFunction.Serializer<ExplosionDecayLootFunction> {
        @Override
        public ExplosionDecayLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditionArray) {
            return new ExplosionDecayLootFunction(lootConditionArray);
        }

        @Override
        public /* synthetic */ ConditionalLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return this.fromJson(json, context, conditions);
        }
    }
}

