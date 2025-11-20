package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v1 extends IdentifierNormalizingSchema {
   public Schema1451v1(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         false,
         TypeReferences.CHUNK,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(TypeReferences.ENTITY_TREE.in(_snowman)),
                  "TileEntities",
                  DSL.list(TypeReferences.BLOCK_ENTITY.in(_snowman)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(_snowman))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.BLOCK_STATE.in(_snowman))))
               )
            )
      );
   }
}
