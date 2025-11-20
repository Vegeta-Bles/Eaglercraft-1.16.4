/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionConsumingBuilder;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final LootTable EMPTY = new LootTable(LootContextTypes.EMPTY, new LootPool[0], new LootFunction[0]);
    public static final LootContextType GENERIC = LootContextTypes.GENERIC;
    private final LootContextType type;
    private final LootPool[] pools;
    private final LootFunction[] functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> combinedFunction;

    private LootTable(LootContextType type, LootPool[] pools, LootFunction[] functions) {
        this.type = type;
        this.pools = pools;
        this.functions = functions;
        this.combinedFunction = LootFunctionTypes.join(functions);
    }

    public static Consumer<ItemStack> processStacks(Consumer<ItemStack> lootConsumer) {
        return stack -> {
            if (stack.getCount() < stack.getMaxCount()) {
                lootConsumer.accept((ItemStack)stack);
            } else {
                for (int i = stack.getCount(); i > 0; i -= itemStack.getCount()) {
                    ItemStack itemStack = stack.copy();
                    itemStack.setCount(Math.min(stack.getMaxCount(), i));
                    lootConsumer.accept(itemStack);
                }
            }
        };
    }

    public void generateUnprocessedLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
        if (context.markActive(this)) {
            Consumer<ItemStack> consumer = LootFunction.apply(this.combinedFunction, lootConsumer, context);
            for (LootPool lootPool : this.pools) {
                lootPool.addGeneratedLoot(consumer, context);
            }
            context.markInactive(this);
        } else {
            LOGGER.warn("Detected infinite loop in loot tables");
        }
    }

    public void generateLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
        this.generateUnprocessedLoot(context, LootTable.processStacks(lootConsumer));
    }

    public List<ItemStack> generateLoot(LootContext context) {
        ArrayList arrayList = Lists.newArrayList();
        this.generateLoot(context, arrayList::add);
        return arrayList;
    }

    public LootContextType getType() {
        return this.type;
    }

    public void validate(LootTableReporter reporter) {
        int n;
        for (n = 0; n < this.pools.length; ++n) {
            this.pools[n].validate(reporter.makeChild(".pools[" + n + "]"));
        }
        for (n = 0; n < this.functions.length; ++n) {
            this.functions[n].validate(reporter.makeChild(".functions[" + n + "]"));
        }
    }

    public void supplyInventory(Inventory inventory, LootContext context) {
        List<ItemStack> list = this.generateLoot(context);
        Random _snowman2 = context.getRandom();
        List<Integer> _snowman3 = this.getFreeSlots(inventory, _snowman2);
        this.shuffle(list, _snowman3.size(), _snowman2);
        for (ItemStack itemStack : list) {
            if (_snowman3.isEmpty()) {
                LOGGER.warn("Tried to over-fill a container");
                return;
            }
            if (itemStack.isEmpty()) {
                inventory.setStack(_snowman3.remove(_snowman3.size() - 1), ItemStack.EMPTY);
                continue;
            }
            inventory.setStack(_snowman3.remove(_snowman3.size() - 1), itemStack);
        }
    }

    private void shuffle(List<ItemStack> drops, int freeSlots, Random random) {
        ArrayList arrayList = Lists.newArrayList();
        Object _snowman2 = drops.iterator();
        while (_snowman2.hasNext()) {
            ItemStack itemStack = _snowman2.next();
            if (itemStack.isEmpty()) {
                _snowman2.remove();
                continue;
            }
            if (itemStack.getCount() <= 1) continue;
            arrayList.add(itemStack);
            _snowman2.remove();
        }
        while (freeSlots - drops.size() - arrayList.size() > 0 && !arrayList.isEmpty()) {
            _snowman2 = (ItemStack)arrayList.remove(MathHelper.nextInt(random, 0, arrayList.size() - 1));
            int n = MathHelper.nextInt(random, 1, ((ItemStack)_snowman2).getCount() / 2);
            ItemStack _snowman3 = ((ItemStack)_snowman2).split(n);
            if (((ItemStack)_snowman2).getCount() > 1 && random.nextBoolean()) {
                arrayList.add(_snowman2);
            } else {
                drops.add((ItemStack)_snowman2);
            }
            if (_snowman3.getCount() > 1 && random.nextBoolean()) {
                arrayList.add(_snowman3);
                continue;
            }
            drops.add(_snowman3);
        }
        drops.addAll(arrayList);
        Collections.shuffle(drops, random);
    }

    private List<Integer> getFreeSlots(Inventory inventory, Random random) {
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            if (!inventory.getStack(i).isEmpty()) continue;
            arrayList.add(i);
        }
        Collections.shuffle(arrayList, random);
        return arrayList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Serializer
    implements JsonDeserializer<LootTable>,
    JsonSerializer<LootTable> {
        public LootTable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            LootFunction[] _snowman4;
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "loot table");
            LootPool[] _snowman2 = JsonHelper.deserialize(jsonObject, "pools", new LootPool[0], jsonDeserializationContext, LootPool[].class);
            LootContextType _snowman3 = null;
            if (jsonObject.has("type")) {
                _snowman4 = JsonHelper.getString(jsonObject, "type");
                _snowman3 = LootContextTypes.get(new Identifier((String)_snowman4));
            }
            _snowman4 = JsonHelper.deserialize(jsonObject, "functions", new LootFunction[0], jsonDeserializationContext, LootFunction[].class);
            return new LootTable(_snowman3 != null ? _snowman3 : LootContextTypes.GENERIC, _snowman2, _snowman4);
        }

        public JsonElement serialize(LootTable lootTable2, Type type, JsonSerializationContext jsonSerializationContext) {
            LootTable lootTable2;
            JsonObject jsonObject = new JsonObject();
            if (lootTable2.type != GENERIC) {
                Identifier identifier = LootContextTypes.getId(lootTable2.type);
                if (identifier != null) {
                    jsonObject.addProperty("type", identifier.toString());
                } else {
                    LOGGER.warn("Failed to find id for param set " + lootTable2.type);
                }
            }
            if (lootTable2.pools.length > 0) {
                jsonObject.add("pools", jsonSerializationContext.serialize((Object)lootTable2.pools));
            }
            if (!ArrayUtils.isEmpty((Object[])lootTable2.functions)) {
                jsonObject.add("functions", jsonSerializationContext.serialize((Object)lootTable2.functions));
            }
            return jsonObject;
        }

        public /* synthetic */ JsonElement serialize(Object supplier, Type unused, JsonSerializationContext context) {
            return this.serialize((LootTable)supplier, unused, context);
        }

        public /* synthetic */ Object deserialize(JsonElement json, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(json, unused, context);
        }
    }

    public static class Builder
    implements LootFunctionConsumingBuilder<Builder> {
        private final List<LootPool> pools = Lists.newArrayList();
        private final List<LootFunction> functions = Lists.newArrayList();
        private LootContextType type = GENERIC;

        public Builder pool(LootPool.Builder poolBuilder) {
            this.pools.add(poolBuilder.build());
            return this;
        }

        public Builder type(LootContextType context) {
            this.type = context;
            return this;
        }

        @Override
        public Builder apply(LootFunction.Builder builder) {
            this.functions.add(builder.build());
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        public LootTable build() {
            return new LootTable(this.type, this.pools.toArray(new LootPool[0]), this.functions.toArray(new LootFunction[0]));
        }

        @Override
        public /* synthetic */ Object getThis() {
            return this.getThis();
        }

        @Override
        public /* synthetic */ Object apply(LootFunction.Builder function) {
            return this.apply(function);
        }
    }
}

