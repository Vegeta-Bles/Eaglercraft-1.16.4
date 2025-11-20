package net.minecraft.loot.entry;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import org.apache.commons.lang3.ArrayUtils;

public class AlternativeEntry extends CombinedEntry {
   AlternativeEntry(LootPoolEntry[] _snowman, LootCondition[] _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.ALTERNATIVES;
   }

   @Override
   protected EntryCombiner combine(EntryCombiner[] children) {
      switch (children.length) {
         case 0:
            return ALWAYS_FALSE;
         case 1:
            return children[0];
         case 2:
            return children[0].or(children[1]);
         default:
            return (context, lootChoiceExpander) -> {
               for (EntryCombiner _snowmanx : children) {
                  if (_snowmanx.expand(context, lootChoiceExpander)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }

   @Override
   public void validate(LootTableReporter reporter) {
      super.validate(reporter);

      for (int _snowman = 0; _snowman < this.children.length - 1; _snowman++) {
         if (ArrayUtils.isEmpty(this.children[_snowman].conditions)) {
            reporter.report("Unreachable entry!");
         }
      }
   }

   public static AlternativeEntry.Builder builder(LootPoolEntry.Builder<?>... children) {
      return new AlternativeEntry.Builder(children);
   }

   public static class Builder extends LootPoolEntry.Builder<AlternativeEntry.Builder> {
      private final List<LootPoolEntry> children = Lists.newArrayList();

      public Builder(LootPoolEntry.Builder<?>... children) {
         for (LootPoolEntry.Builder<?> _snowman : children) {
            this.children.add(_snowman.build());
         }
      }

      protected AlternativeEntry.Builder getThisBuilder() {
         return this;
      }

      @Override
      public AlternativeEntry.Builder alternatively(LootPoolEntry.Builder<?> builder) {
         this.children.add(builder.build());
         return this;
      }

      @Override
      public LootPoolEntry build() {
         return new AlternativeEntry(this.children.toArray(new LootPoolEntry[0]), this.getConditions());
      }
   }
}
