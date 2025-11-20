package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema135 extends Schema {
   public Schema135(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         false,
         TypeReferences.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", TypeReferences.ENTITY_TREE.in(_snowman)),
               "Inventory",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "EnderItems",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman))
            )
      );
      _snowman.registerType(
         true, TypeReferences.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(TypeReferences.ENTITY_TREE.in(_snowman)), TypeReferences.ENTITY.in(_snowman))
      );
   }
}
