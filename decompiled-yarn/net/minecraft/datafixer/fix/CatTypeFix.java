package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class CatTypeFix extends ChoiceFix {
   public CatTypeFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "CatTypeFix", TypeReferences.ENTITY, "minecraft:cat");
   }

   public Dynamic<?> fixCatTypeData(Dynamic<?> _snowman) {
      return _snowman.get("CatType").asInt(0) == 9 ? _snowman.set("CatType", _snowman.createInt(10)) : _snowman;
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixCatTypeData);
   }
}
