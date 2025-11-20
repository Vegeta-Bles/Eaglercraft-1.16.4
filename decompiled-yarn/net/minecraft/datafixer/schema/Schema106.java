package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema106 extends Schema {
   public Schema106(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         true,
         TypeReferences.UNTAGGED_SPAWNER,
         () -> DSL.optionalFields(
               "SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.ENTITY_TREE.in(_snowman))), "SpawnData", TypeReferences.ENTITY_TREE.in(_snowman)
            )
      );
   }
}
