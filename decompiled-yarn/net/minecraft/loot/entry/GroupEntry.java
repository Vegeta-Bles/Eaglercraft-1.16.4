package net.minecraft.loot.entry;

import net.minecraft.loot.condition.LootCondition;

public class GroupEntry extends CombinedEntry {
   GroupEntry(LootPoolEntry[] _snowman, LootCondition[] _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.SEQUENCE;
   }

   @Override
   protected EntryCombiner combine(EntryCombiner[] children) {
      switch (children.length) {
         case 0:
            return ALWAYS_TRUE;
         case 1:
            return children[0];
         case 2:
            return children[0].and(children[1]);
         default:
            return (context, lootChoiceExpander) -> {
               for (EntryCombiner _snowmanx : children) {
                  if (!_snowmanx.expand(context, lootChoiceExpander)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }
}
