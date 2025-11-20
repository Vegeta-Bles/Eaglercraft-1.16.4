package net.minecraft.loot.entry;

import net.minecraft.loot.condition.LootCondition;

public class SequenceEntry extends CombinedEntry {
   SequenceEntry(LootPoolEntry[] args, LootCondition[] args2) {
      super(args, args2);
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.GROUP;
   }

   @Override
   protected EntryCombiner combine(EntryCombiner[] children) {
      switch (children.length) {
         case 0:
            return ALWAYS_TRUE;
         case 1:
            return children[0];
         case 2:
            EntryCombiner lv = children[0];
            EntryCombiner lv2 = children[1];
            return (arg3, consumer) -> {
               lv.expand(arg3, consumer);
               lv2.expand(arg3, consumer);
               return true;
            };
         default:
            return (context, lootChoiceExpander) -> {
               for (EntryCombiner lvx : children) {
                  lvx.expand(context, lootChoiceExpander);
               }

               return true;
            };
      }
   }
}
