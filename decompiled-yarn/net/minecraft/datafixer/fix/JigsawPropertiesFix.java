package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class JigsawPropertiesFix extends ChoiceFix {
   public JigsawPropertiesFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "JigsawPropertiesFix", TypeReferences.BLOCK_ENTITY, "minecraft:jigsaw");
   }

   private static Dynamic<?> renameProperties(Dynamic<?> _snowman) {
      String _snowmanx = _snowman.get("attachement_type").asString("minecraft:empty");
      String _snowmanxx = _snowman.get("target_pool").asString("minecraft:empty");
      return _snowman.set("name", _snowman.createString(_snowmanx))
         .set("target", _snowman.createString(_snowmanx))
         .remove("attachement_type")
         .set("pool", _snowman.createString(_snowmanxx))
         .remove("target_pool");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), JigsawPropertiesFix::renameProperties);
   }
}
