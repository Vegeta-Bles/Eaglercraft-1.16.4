package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class VillagerGossipFix extends ChoiceFix {
   public VillagerGossipFix(Schema outputSchema, String choiceType) {
      super(outputSchema, false, "Gossip for for " + choiceType, TypeReferences.ENTITY, choiceType);
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(
         DSL.remainderFinder(),
         _snowman -> _snowman.update(
               "Gossips",
               _snowmanx -> (Dynamic)DataFixUtils.orElse(
                     _snowmanx.asStreamOpt()
                        .result()
                        .map(_snowmanxx -> _snowmanxx.map(_snowmanxxx -> (Dynamic)AbstractUuidFix.updateRegularMostLeast(_snowmanxxx, "Target", "Target").orElse(_snowmanxxx)))
                        .map(_snowmanx::createList),
                     _snowmanx
                  )
            )
      );
   }
}
