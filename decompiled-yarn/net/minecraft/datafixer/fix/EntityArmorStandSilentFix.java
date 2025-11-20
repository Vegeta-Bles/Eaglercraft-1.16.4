package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class EntityArmorStandSilentFix extends ChoiceFix {
   public EntityArmorStandSilentFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "EntityArmorStandSilentFix", TypeReferences.ENTITY, "ArmorStand");
   }

   public Dynamic<?> fixSilent(Dynamic<?> _snowman) {
      return _snowman.get("Silent").asBoolean(false) && !_snowman.get("Marker").asBoolean(false) ? _snowman.remove("Silent") : _snowman;
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixSilent);
   }
}
