package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;

public abstract class CombinedEntry extends LootPoolEntry {
   protected final LootPoolEntry[] children;
   private final EntryCombiner predicate;

   protected CombinedEntry(LootPoolEntry[] children, LootCondition[] conditions) {
      super(conditions);
      this.children = children;
      this.predicate = this.combine(children);
   }

   @Override
   public void validate(LootTableReporter reporter) {
      super.validate(reporter);
      if (this.children.length == 0) {
         reporter.report("Empty children list");
      }

      for (int i = 0; i < this.children.length; i++) {
         this.children[i].validate(reporter.makeChild(".entry[" + i + "]"));
      }
   }

   protected abstract EntryCombiner combine(EntryCombiner[] children);

   @Override
   public final boolean expand(LootContext arg, Consumer<LootChoice> consumer) {
      return !this.test(arg) ? false : this.predicate.expand(arg, consumer);
   }

   public static <T extends CombinedEntry> LootPoolEntry.Serializer<T> createSerializer(final CombinedEntry.Factory<T> arg) {
      return new LootPoolEntry.Serializer<T>() {
         public void addEntryFields(JsonObject jsonObject, T argx, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("children", jsonSerializationContext.serialize(argx.children));
         }

         public final T fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
            LootPoolEntry[] lvs = JsonHelper.deserialize(jsonObject, "children", jsonDeserializationContext, LootPoolEntry[].class);
            return arg.create(lvs, args);
         }
      };
   }

   @FunctionalInterface
   public interface Factory<T extends CombinedEntry> {
      T create(LootPoolEntry[] children, LootCondition[] conditions);
   }
}
