package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class EntityShulkerColorFix extends ChoiceFix {
   public EntityShulkerColorFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "EntityShulkerColorFix", TypeReferences.ENTITY, "minecraft:shulker");
   }

   public Dynamic<?> fixShulkerColor(Dynamic<?> _snowman) {
      return !_snowman.get("Color").map(Dynamic::asNumber).result().isPresent() ? _snowman.set("Color", _snowman.createByte((byte)10)) : _snowman;
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixShulkerColor);
   }
}
