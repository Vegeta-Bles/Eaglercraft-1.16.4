package net.minecraft.loot.entry;

import net.minecraft.loot.condition.LootCondition;

public class SequenceEntry extends CombinedEntry {
   SequenceEntry(LootPoolEntry[] _snowman, LootCondition[] _snowman) {
      super(_snowman, _snowman);
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
            EntryCombiner _snowman = children[0];
            EntryCombiner _snowmanx = children[1];
            return (_snowmanxx, _snowmanxxx) -> {
               _snowman.expand(_snowmanxx, _snowmanxxx);
               _snowman.expand(_snowmanxx, _snowmanxxx);
               return true;
            };
         default:
            return (context, lootChoiceExpander) -> {
               for (EntryCombiner _snowmanxx : children) {
                  _snowmanxx.expand(context, lootChoiceExpander);
               }

               return true;
            };
      }
   }
}
