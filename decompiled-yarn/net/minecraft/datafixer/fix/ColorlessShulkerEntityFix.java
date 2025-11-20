package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.TypeReferences;

public class ColorlessShulkerEntityFix extends ChoiceFix {
   public ColorlessShulkerEntityFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "Colorless shulker entity fix", TypeReferences.ENTITY, "minecraft:shulker");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), _snowman -> _snowman.get("Color").asInt(0) == 10 ? _snowman.set("Color", _snowman.createByte((byte)16)) : _snowman);
   }
}
