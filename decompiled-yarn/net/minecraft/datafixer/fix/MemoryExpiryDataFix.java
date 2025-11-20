package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class MemoryExpiryDataFix extends ChoiceFix {
   public MemoryExpiryDataFix(Schema outputSchema, String choiceName) {
      super(outputSchema, false, "Memory expiry data fix (" + choiceName + ")", TypeReferences.ENTITY, choiceName);
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::updateBrain);
   }

   public Dynamic<?> updateBrain(Dynamic<?> _snowman) {
      return _snowman.update("Brain", this::updateMemories);
   }

   private Dynamic<?> updateMemories(Dynamic<?> _snowman) {
      return _snowman.update("memories", this::updateMemoryMap);
   }

   private Dynamic<?> updateMemoryMap(Dynamic<?> _snowman) {
      return _snowman.updateMapValues(this::updateMemoryMapValues);
   }

   private Pair<Dynamic<?>, Dynamic<?>> updateMemoryMapValues(Pair<Dynamic<?>, Dynamic<?>> _snowman) {
      return _snowman.mapSecond(this::updateMemoryMapValueEntry);
   }

   private Dynamic<?> updateMemoryMapValueEntry(Dynamic<?> _snowman) {
      return _snowman.createMap(ImmutableMap.of(_snowman.createString("value"), _snowman));
   }
}
