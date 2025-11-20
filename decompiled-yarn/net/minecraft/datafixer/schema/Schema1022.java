package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1022 extends Schema {
   public Schema1022(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(false, TypeReferences.RECIPE, () -> DSL.constType(IdentifierNormalizingSchema.getIdentifierType()));
      _snowman.registerType(
         false,
         TypeReferences.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", TypeReferences.ENTITY_TREE.in(_snowman)),
               "Inventory",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "EnderItems",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  TypeReferences.ENTITY_TREE.in(_snowman),
                  "ShoulderEntityRight",
                  TypeReferences.ENTITY_TREE.in(_snowman),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(TypeReferences.RECIPE.in(_snowman)), "toBeDisplayed", DSL.list(TypeReferences.RECIPE.in(_snowman)))
               )
            )
      );
      _snowman.registerType(false, TypeReferences.HOTBAR, () -> DSL.compoundList(DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }
}
