package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class StriderGravityFix extends ChoiceFix {
   public StriderGravityFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "StriderGravityFix", TypeReferences.ENTITY, "minecraft:strider");
   }

   public Dynamic<?> updateNoGravityTag(Dynamic<?> _snowman) {
      return _snowman.get("NoGravity").asBoolean(false) ? _snowman.set("NoGravity", _snowman.createBoolean(false)) : _snowman;
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::updateNoGravityTag);
   }
}
