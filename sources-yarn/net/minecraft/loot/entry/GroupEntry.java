package net.minecraft.loot.entry;

import net.minecraft.loot.condition.LootCondition;

public class GroupEntry extends CombinedEntry {
   GroupEntry(LootPoolEntry[] args, LootCondition[] args2) {
      super(args, args2);
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
               for (EntryCombiner lv : children) {
                  if (!lv.expand(context, lootChoiceExpander)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }
}
