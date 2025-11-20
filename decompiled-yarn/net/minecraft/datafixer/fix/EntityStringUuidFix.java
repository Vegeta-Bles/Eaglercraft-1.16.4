package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.datafixer.TypeReferences;

public class EntityStringUuidFix extends DataFix {
   public EntityStringUuidFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityStringUuidFix",
         this.getInputSchema().getType(TypeReferences.ENTITY),
         _snowman -> _snowman.update(
               DSL.remainderFinder(),
               _snowmanx -> {
                  Optional<String> _snowmanx = _snowmanx.get("UUID").asString().result();
                  if (_snowmanx.isPresent()) {
                     UUID _snowmanxx = UUID.fromString(_snowmanx.get());
                     return _snowmanx.remove("UUID")
                        .set("UUIDMost", _snowmanx.createLong(_snowmanxx.getMostSignificantBits()))
                        .set("UUIDLeast", _snowmanx.createLong(_snowmanxx.getLeastSignificantBits()));
                  } else {
                     return _snowmanx;
                  }
               }
            )
      );
   }
}
