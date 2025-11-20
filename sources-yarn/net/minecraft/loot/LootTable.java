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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
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
            lootConsumer.accept(stack);
         } else {
            int i = stack.getCount();

            while (i > 0) {
               ItemStack lv = stack.copy();
               lv.setCount(Math.min(stack.getMaxCount(), i));
               i -= lv.getCount();
               lootConsumer.accept(lv);
            }
         }
      };
   }

   public void generateUnprocessedLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
      if (context.markActive(this)) {
         Consumer<ItemStack> consumer2 = LootFunction.apply(this.combinedFunction, lootConsumer, context);

         for (LootPool lv : this.pools) {
            lv.addGeneratedLoot(consumer2, context);
         }

         context.markInactive(this);
      } else {
         LOGGER.warn("Detected infinite loop in loot tables");
      }
   }

   public void generateLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
      this.generateUnprocessedLoot(context, processStacks(lootConsumer));
   }

   public List<ItemStack> generateLoot(LootContext context) {
      List<ItemStack> list = Lists.newArrayList();
      this.generateLoot(context, list::add);
      return list;
   }

   public LootContextType getType() {
      return this.type;
   }

   public void validate(LootTableReporter reporter) {
      for (int i = 0; i < this.pools.length; i++) {
         this.pools[i].validate(reporter.makeChild(".pools[" + i + "]"));
      }

      for (int j = 0; j < this.functions.length; j++) {
         this.functions[j].validate(reporter.makeChild(".functions[" + j + "]"));
      }
   }

   public void supplyInventory(Inventory inventory, LootContext context) {
      List<ItemStack> list = this.generateLoot(context);
      Random random = context.getRandom();
      List<Integer> list2 = this.getFreeSlots(inventory, random);
      this.shuffle(list, list2.size(), random);

      for (ItemStack lv : list) {
         if (list2.isEmpty()) {
            LOGGER.warn("Tried to over-fill a container");
            return;
         }

         if (lv.isEmpty()) {
            inventory.setStack(list2.remove(list2.size() - 1), ItemStack.EMPTY);
         } else {
            inventory.setStack(list2.remove(list2.size() - 1), lv);
         }
      }
   }

   private void shuffle(List<ItemStack> drops, int freeSlots, Random random) {
      List<ItemStack> list2 = Lists.newArrayList();
      Iterator<ItemStack> iterator = drops.iterator();

      while (iterator.hasNext()) {
         ItemStack lv = iterator.next();
         if (lv.isEmpty()) {
            iterator.remove();
         } else if (lv.getCount() > 1) {
            list2.add(lv);
            iterator.remove();
         }
      }

      while (freeSlots - drops.size() - list2.size() > 0 && !list2.isEmpty()) {
         ItemStack lv2 = list2.remove(MathHelper.nextInt(random, 0, list2.size() - 1));
         int j = MathHelper.nextInt(random, 1, lv2.getCount() / 2);
         ItemStack lv3 = lv2.split(j);
         if (lv2.getCount() > 1 && random.nextBoolean()) {
            list2.add(lv2);
         } else {
            drops.add(lv2);
         }

         if (lv3.getCount() > 1 && random.nextBoolean()) {
            list2.add(lv3);
         } else {
            drops.add(lv3);
         }
      }

      drops.addAll(list2);
      Collections.shuffle(drops, random);
   }

   private List<Integer> getFreeSlots(Inventory inventory, Random random) {
      List<Integer> list = Lists.newArrayList();

      for (int i = 0; i < inventory.size(); i++) {
         if (inventory.getStack(i).isEmpty()) {
            list.add(i);
         }
      }

      Collections.shuffle(list, random);
      return list;
   }

   public static LootTable.Builder builder() {
      return new LootTable.Builder();
   }

   public static class Builder implements LootFunctionConsumingBuilder<LootTable.Builder> {
      private final List<LootPool> pools = Lists.newArrayList();
      private final List<LootFunction> functions = Lists.newArrayList();
      private LootContextType type = LootTable.GENERIC;

      public Builder() {
      }

      public LootTable.Builder pool(LootPool.Builder poolBuilder) {
         this.pools.add(poolBuilder.build());
         return this;
      }

      public LootTable.Builder type(LootContextType context) {
         this.type = context;
         return this;
      }

      public LootTable.Builder apply(LootFunction.Builder arg) {
         this.functions.add(arg.build());
         return this;
      }

      public LootTable.Builder getThis() {
         return this;
      }

      public LootTable build() {
         return new LootTable(this.type, this.pools.toArray(new LootPool[0]), this.functions.toArray(new LootFunction[0]));
      }
   }

   public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public Serializer() {
      }

      public LootTable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = JsonHelper.asObject(jsonElement, "loot table");
         LootPool[] lvs = JsonHelper.deserialize(jsonObject, "pools", new LootPool[0], jsonDeserializationContext, LootPool[].class);
         LootContextType lv = null;
         if (jsonObject.has("type")) {
            String string = JsonHelper.getString(jsonObject, "type");
            lv = LootContextTypes.get(new Identifier(string));
         }

         LootFunction[] lvs2 = JsonHelper.deserialize(jsonObject, "functions", new LootFunction[0], jsonDeserializationContext, LootFunction[].class);
         return new LootTable(lv != null ? lv : LootContextTypes.GENERIC, lvs, lvs2);
      }

      public JsonElement serialize(LootTable arg, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject jsonObject = new JsonObject();
         if (arg.type != LootTable.GENERIC) {
            Identifier lv = LootContextTypes.getId(arg.type);
            if (lv != null) {
               jsonObject.addProperty("type", lv.toString());
            } else {
               LootTable.LOGGER.warn("Failed to find id for param set " + arg.type);
            }
         }

         if (arg.pools.length > 0) {
            jsonObject.add("pools", jsonSerializationContext.serialize(arg.pools));
         }

         if (!ArrayUtils.isEmpty(arg.functions)) {
            jsonObject.add("functions", jsonSerializationContext.serialize(arg.functions));
         }

         return jsonObject;
      }
   }
}
