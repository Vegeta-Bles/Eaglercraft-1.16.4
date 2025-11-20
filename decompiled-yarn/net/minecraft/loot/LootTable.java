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
            int _snowmanx = stack.getCount();

            while (_snowmanx > 0) {
               ItemStack _snowmanx = stack.copy();
               _snowmanx.setCount(Math.min(stack.getMaxCount(), _snowmanx));
               _snowmanx -= _snowmanx.getCount();
               lootConsumer.accept(_snowmanx);
            }
         }
      };
   }

   public void generateUnprocessedLoot(LootContext context, Consumer<ItemStack> lootConsumer) {
      if (context.markActive(this)) {
         Consumer<ItemStack> _snowman = LootFunction.apply(this.combinedFunction, lootConsumer, context);

         for (LootPool _snowmanx : this.pools) {
            _snowmanx.addGeneratedLoot(_snowman, context);
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
      List<ItemStack> _snowman = Lists.newArrayList();
      this.generateLoot(context, _snowman::add);
      return _snowman;
   }

   public LootContextType getType() {
      return this.type;
   }

   public void validate(LootTableReporter reporter) {
      for (int _snowman = 0; _snowman < this.pools.length; _snowman++) {
         this.pools[_snowman].validate(reporter.makeChild(".pools[" + _snowman + "]"));
      }

      for (int _snowman = 0; _snowman < this.functions.length; _snowman++) {
         this.functions[_snowman].validate(reporter.makeChild(".functions[" + _snowman + "]"));
      }
   }

   public void supplyInventory(Inventory inventory, LootContext context) {
      List<ItemStack> _snowman = this.generateLoot(context);
      Random _snowmanx = context.getRandom();
      List<Integer> _snowmanxx = this.getFreeSlots(inventory, _snowmanx);
      this.shuffle(_snowman, _snowmanxx.size(), _snowmanx);

      for (ItemStack _snowmanxxx : _snowman) {
         if (_snowmanxx.isEmpty()) {
            LOGGER.warn("Tried to over-fill a container");
            return;
         }

         if (_snowmanxxx.isEmpty()) {
            inventory.setStack(_snowmanxx.remove(_snowmanxx.size() - 1), ItemStack.EMPTY);
         } else {
            inventory.setStack(_snowmanxx.remove(_snowmanxx.size() - 1), _snowmanxxx);
         }
      }
   }

   private void shuffle(List<ItemStack> drops, int freeSlots, Random random) {
      List<ItemStack> _snowman = Lists.newArrayList();
      Iterator<ItemStack> _snowmanx = drops.iterator();

      while (_snowmanx.hasNext()) {
         ItemStack _snowmanxx = _snowmanx.next();
         if (_snowmanxx.isEmpty()) {
            _snowmanx.remove();
         } else if (_snowmanxx.getCount() > 1) {
            _snowman.add(_snowmanxx);
            _snowmanx.remove();
         }
      }

      while (freeSlots - drops.size() - _snowman.size() > 0 && !_snowman.isEmpty()) {
         ItemStack _snowmanxx = _snowman.remove(MathHelper.nextInt(random, 0, _snowman.size() - 1));
         int _snowmanxxx = MathHelper.nextInt(random, 1, _snowmanxx.getCount() / 2);
         ItemStack _snowmanxxxx = _snowmanxx.split(_snowmanxxx);
         if (_snowmanxx.getCount() > 1 && random.nextBoolean()) {
            _snowman.add(_snowmanxx);
         } else {
            drops.add(_snowmanxx);
         }

         if (_snowmanxxxx.getCount() > 1 && random.nextBoolean()) {
            _snowman.add(_snowmanxxxx);
         } else {
            drops.add(_snowmanxxxx);
         }
      }

      drops.addAll(_snowman);
      Collections.shuffle(drops, random);
   }

   private List<Integer> getFreeSlots(Inventory inventory, Random random) {
      List<Integer> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < inventory.size(); _snowmanx++) {
         if (inventory.getStack(_snowmanx).isEmpty()) {
            _snowman.add(_snowmanx);
         }
      }

      Collections.shuffle(_snowman, random);
      return _snowman;
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

      public LootTable.Builder apply(LootFunction.Builder _snowman) {
         this.functions.add(_snowman.build());
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

      public LootTable deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "loot table");
         LootPool[] _snowmanxxxx = JsonHelper.deserialize(_snowmanxxx, "pools", new LootPool[0], _snowman, LootPool[].class);
         LootContextType _snowmanxxxxx = null;
         if (_snowmanxxx.has("type")) {
            String _snowmanxxxxxx = JsonHelper.getString(_snowmanxxx, "type");
            _snowmanxxxxx = LootContextTypes.get(new Identifier(_snowmanxxxxxx));
         }

         LootFunction[] _snowmanxxxxxx = JsonHelper.deserialize(_snowmanxxx, "functions", new LootFunction[0], _snowman, LootFunction[].class);
         return new LootTable(_snowmanxxxxx != null ? _snowmanxxxxx : LootContextTypes.GENERIC, _snowmanxxxx, _snowmanxxxxxx);
      }

      public JsonElement serialize(LootTable _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         if (_snowman.type != LootTable.GENERIC) {
            Identifier _snowmanxxxx = LootContextTypes.getId(_snowman.type);
            if (_snowmanxxxx != null) {
               _snowmanxxx.addProperty("type", _snowmanxxxx.toString());
            } else {
               LootTable.LOGGER.warn("Failed to find id for param set " + _snowman.type);
            }
         }

         if (_snowman.pools.length > 0) {
            _snowmanxxx.add("pools", _snowman.serialize(_snowman.pools));
         }

         if (!ArrayUtils.isEmpty(_snowman.functions)) {
            _snowmanxxx.add("functions", _snowman.serialize(_snowman.functions));
         }

         return _snowmanxxx;
      }
   }
}
