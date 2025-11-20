package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class SetContentsLootFunction extends ConditionalLootFunction {
   private final List<LootPoolEntry> entries;

   private SetContentsLootFunction(LootCondition[] conditions, List<LootPoolEntry> entries) {
      super(conditions);
      this.entries = ImmutableList.copyOf(entries);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_CONTENTS;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.isEmpty()) {
         return stack;
      } else {
         DefaultedList<ItemStack> _snowman = DefaultedList.of();
         this.entries.forEach(entry -> entry.expand(context, choice -> choice.generateLoot(LootTable.processStacks(_snowman::add), context)));
         CompoundTag _snowmanx = new CompoundTag();
         Inventories.toTag(_snowmanx, _snowman);
         CompoundTag _snowmanxx = stack.getOrCreateTag();
         _snowmanxx.put("BlockEntityTag", _snowmanx.copyFrom(_snowmanxx.getCompound("BlockEntityTag")));
         return stack;
      }
   }

   @Override
   public void validate(LootTableReporter reporter) {
      super.validate(reporter);

      for (int _snowman = 0; _snowman < this.entries.size(); _snowman++) {
         this.entries.get(_snowman).validate(reporter.makeChild(".entry[" + _snowman + "]"));
      }
   }

   public static SetContentsLootFunction.Builer builder() {
      return new SetContentsLootFunction.Builer();
   }

   public static class Builer extends ConditionalLootFunction.Builder<SetContentsLootFunction.Builer> {
      private final List<LootPoolEntry> entries = Lists.newArrayList();

      public Builer() {
      }

      protected SetContentsLootFunction.Builer getThisBuilder() {
         return this;
      }

      public SetContentsLootFunction.Builer withEntry(LootPoolEntry.Builder<?> entryBuilder) {
         this.entries.add(entryBuilder.build());
         return this;
      }

      @Override
      public LootFunction build() {
         return new SetContentsLootFunction(this.getConditions(), this.entries);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetContentsLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetContentsLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("entries", _snowman.serialize(_snowman.entries));
      }

      public SetContentsLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         LootPoolEntry[] _snowmanxxx = JsonHelper.deserialize(_snowman, "entries", _snowman, LootPoolEntry[].class);
         return new SetContentsLootFunction(_snowman, Arrays.asList(_snowmanxxx));
      }
   }
}
