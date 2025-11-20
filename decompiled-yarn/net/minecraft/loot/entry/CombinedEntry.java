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

      for (int _snowman = 0; _snowman < this.children.length; _snowman++) {
         this.children[_snowman].validate(reporter.makeChild(".entry[" + _snowman + "]"));
      }
   }

   protected abstract EntryCombiner combine(EntryCombiner[] children);

   @Override
   public final boolean expand(LootContext _snowman, Consumer<LootChoice> _snowman) {
      return !this.test(_snowman) ? false : this.predicate.expand(_snowman, _snowman);
   }

   public static <T extends CombinedEntry> LootPoolEntry.Serializer<T> createSerializer(CombinedEntry.Factory<T> _snowman) {
      return new LootPoolEntry.Serializer<T>() {
         public void addEntryFields(JsonObject _snowman, T _snowman, JsonSerializationContext _snowman) {
            _snowman.add("children", _snowman.serialize(_snowman.children));
         }

         public final T fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
            LootPoolEntry[] _snowmanxxx = JsonHelper.deserialize(_snowman, "children", _snowman, LootPoolEntry[].class);
            return _snowman.create(_snowmanxxx, _snowman);
         }
      };
   }

   @FunctionalInterface
   public interface Factory<T extends CombinedEntry> {
      T create(LootPoolEntry[] children, LootCondition[] conditions);
   }
}
