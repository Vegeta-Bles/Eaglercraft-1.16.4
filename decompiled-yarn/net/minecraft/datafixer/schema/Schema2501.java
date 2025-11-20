package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema2501 extends IdentifierNormalizingSchema {
   public Schema2501(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   private static void targetRecipeUsedField(Schema schema, Map<String, Supplier<TypeTemplate>> map, String name) {
      schema.register(
         map,
         name,
         () -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.ITEM_STACK.in(schema)),
               "RecipesUsed",
               DSL.compoundList(TypeReferences.RECIPE.in(schema), DSL.constType(DSL.intType()))
            )
      );
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      targetRecipeUsedField(_snowman, _snowmanx, "minecraft:furnace");
      targetRecipeUsedField(_snowman, _snowmanx, "minecraft:smoker");
      targetRecipeUsedField(_snowman, _snowmanx, "minecraft:blast_furnace");
      return _snowmanx;
   }
}
