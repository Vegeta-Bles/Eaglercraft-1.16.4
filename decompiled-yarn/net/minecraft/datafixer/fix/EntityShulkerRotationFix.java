package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.datafixer.TypeReferences;

public class EntityShulkerRotationFix extends ChoiceFix {
   public EntityShulkerRotationFix(Schema outputSchema) {
      super(outputSchema, false, "EntityShulkerRotationFix", TypeReferences.ENTITY, "minecraft:shulker");
   }

   public Dynamic<?> fixRotation(Dynamic<?> _snowman) {
      List<Double> _snowmanx = _snowman.get("Rotation").asList(_snowmanxx -> _snowmanxx.asDouble(180.0));
      if (!_snowmanx.isEmpty()) {
         _snowmanx.set(0, _snowmanx.get(0) - 180.0);
         return _snowman.set("Rotation", _snowman.createList(_snowmanx.stream().map(_snowman::createDouble)));
      } else {
         return _snowman;
      }
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixRotation);
   }
}
