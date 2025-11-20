package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class PersistentStateUuidFix extends AbstractUuidFix {
   public PersistentStateUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.SAVED_DATA);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "SavedDataUUIDFix",
         this.getInputSchema().getType(this.typeReference),
         _snowman -> _snowman.updateTyped(
               _snowman.getType().findField("data"),
               _snowmanx -> _snowmanx.update(
                     DSL.remainderFinder(),
                     _snowmanxx -> _snowmanxx.update(
                           "Raids",
                           _snowmanxxx -> _snowmanxxx.createList(
                                 _snowmanxxx.asStream()
                                    .map(
                                       _snowmanxxxx -> _snowmanxxxx.update(
                                             "HeroesOfTheVillage",
                                             _snowmanxxxxx -> _snowmanxxxxx.createList(
                                                   _snowmanxxxxx.asStream()
                                                      .map(_snowmanxxxxxx -> (Dynamic)createArrayFromMostLeastTags(_snowmanxxxxxx, "UUIDMost", "UUIDLeast").orElseGet(() -> {
                                                            LOGGER.warn("HeroesOfTheVillage contained invalid UUIDs.");
                                                            return _snowmanxxxxxx;
                                                         }))
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }
}
