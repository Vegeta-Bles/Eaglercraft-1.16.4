package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema2100 extends IdentifierNormalizingSchema {
   public Schema2100(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_21746(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_21746(_snowman, _snowmanx, "minecraft:bee");
      method_21746(_snowman, _snowmanx, "minecraft:bee_stinger");
      return _snowmanx;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      _snowman.register(
         _snowmanx,
         "minecraft:beehive",
         () -> DSL.optionalFields(
               "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "Bees", DSL.list(DSL.optionalFields("EntityData", TypeReferences.ENTITY_TREE.in(_snowman)))
            )
      );
      return _snowmanx;
   }
}
