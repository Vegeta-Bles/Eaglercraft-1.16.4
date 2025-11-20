package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class WorldUuidFix extends AbstractUuidFix {
   public WorldUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.LEVEL);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "LevelUUIDFix",
         this.getInputSchema().getType(this.typeReference),
         _snowman -> _snowman.updateTyped(DSL.remainderFinder(), _snowmanx -> _snowmanx.update(DSL.remainderFinder(), _snowmanxx -> {
                  _snowmanxx = this.method_26061(_snowmanxx);
                  _snowmanxx = this.method_26060(_snowmanxx);
                  return this.method_26057(_snowmanxx);
               }))
      );
   }

   private Dynamic<?> method_26057(Dynamic<?> _snowman) {
      return updateStringUuid(_snowman, "WanderingTraderId", "WanderingTraderId").orElse(_snowman);
   }

   private Dynamic<?> method_26060(Dynamic<?> _snowman) {
      return _snowman.update(
         "DimensionData",
         _snowmanx -> _snowmanx.updateMapValues(
               _snowmanxx -> _snowmanxx.mapSecond(_snowmanxxx -> _snowmanxxx.update("DragonFight", _snowmanxxxx -> updateRegularMostLeast(_snowmanxxxx, "DragonUUID", "Dragon").orElse(_snowmanxxxx)))
            )
      );
   }

   private Dynamic<?> method_26061(Dynamic<?> _snowman) {
      return _snowman.update(
         "CustomBossEvents",
         _snowmanx -> _snowmanx.updateMapValues(
               _snowmanxx -> _snowmanxx.mapSecond(
                     _snowmanxxx -> _snowmanxxx.update(
                           "Players",
                           _snowmanxxxxx -> _snowmanxxx.createList(_snowmanxxxxx.asStream().map(_snowmanxxxxxx -> (Dynamic)createArrayFromCompoundUuid(_snowmanxxxxxx).orElseGet(() -> {
                                    LOGGER.warn("CustomBossEvents contains invalid UUIDs.");
                                    return _snowmanxxxxxx;
                                 })))
                        )
                  )
            )
      );
   }
}
