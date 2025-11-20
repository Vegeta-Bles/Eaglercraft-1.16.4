package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.TypeReferences;

public class PlayerUuidFix extends AbstractUuidFix {
   public PlayerUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.PLAYER);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "PlayerUUIDFix",
         this.getInputSchema().getType(this.typeReference),
         _snowman -> {
            OpticFinder<?> _snowmanx = _snowman.getType().findField("RootVehicle");
            return _snowman.updateTyped(_snowmanx, _snowmanx.type(), _snowmanxx -> _snowmanxx.update(DSL.remainderFinder(), _snowmanxxx -> updateRegularMostLeast(_snowmanxxx, "Attach", "Attach").orElse(_snowmanxxx)))
               .update(DSL.remainderFinder(), _snowmanxx -> EntityUuidFix.updateSelfUuid(EntityUuidFix.updateLiving(_snowmanxx)));
         }
      );
   }
}
