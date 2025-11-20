package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class VillagerFollowRangeFix extends ChoiceFix {
   public VillagerFollowRangeFix(Schema _snowman) {
      super(_snowman, false, "Villager Follow Range Fix", TypeReferences.ENTITY, "minecraft:villager");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), VillagerFollowRangeFix::method_27914);
   }

   private static Dynamic<?> method_27914(Dynamic<?> _snowman) {
      return _snowman.update(
         "Attributes",
         _snowmanxx -> _snowman.createList(
               _snowmanxx.asStream()
                  .map(
                     _snowmanxxx -> _snowmanxxx.get("Name").asString("").equals("generic.follow_range") && _snowmanxxx.get("Base").asDouble(0.0) == 16.0
                           ? _snowmanxxx.set("Base", _snowmanxxx.createDouble(48.0))
                           : _snowmanxxx
                  )
            )
      );
   }
}
