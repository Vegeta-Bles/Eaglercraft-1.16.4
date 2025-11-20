package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;

public class EmptyEntry extends LeafEntry {
   private EmptyEntry(int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
      super(weight, quality, conditions, functions);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.EMPTY;
   }

   @Override
   public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
   }

   public static LeafEntry.Builder<?> Serializer() {
      return builder(EmptyEntry::new);
   }

   public static class Serializer extends LeafEntry.Serializer<EmptyEntry> {
      public Serializer() {
      }

      public EmptyEntry fromJson(
         JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int i, int j, LootCondition[] args, LootFunction[] args2
      ) {
         return new EmptyEntry(i, j, args, args2);
      }
   }
}
