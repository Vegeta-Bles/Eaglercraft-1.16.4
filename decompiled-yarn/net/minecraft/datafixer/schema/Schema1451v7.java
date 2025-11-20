package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v7 extends IdentifierNormalizingSchema {
   public Schema1451v7(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         false,
         TypeReferences.STRUCTURE_FEATURE,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CB",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CC",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CD",
                     TypeReferences.BLOCK_STATE.in(_snowman)
                  )
               )
            )
      );
   }
}
