package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema102 extends Schema {
   public Schema102(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         true,
         TypeReferences.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  TypeReferences.ITEM_NAME.in(_snowman),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     TypeReferences.ENTITY_TREE.in(_snowman),
                     "BlockEntityTag",
                     TypeReferences.BLOCK_ENTITY.in(_snowman),
                     "CanDestroy",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman)),
                     "CanPlaceOn",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman))
                  )
               ),
               Schema99.field_5747,
               HookFunction.IDENTITY
            )
      );
   }
}
