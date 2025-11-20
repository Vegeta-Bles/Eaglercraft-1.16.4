package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1906 extends IdentifierNormalizingSchema {
   public Schema1906(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      method_16052(_snowman, _snowmanx, "minecraft:barrel");
      method_16052(_snowman, _snowmanx, "minecraft:smoker");
      method_16052(_snowman, _snowmanx, "minecraft:blast_furnace");
      _snowman.register(_snowmanx, "minecraft:lectern", _snowmanxx -> DSL.optionalFields("Book", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "minecraft:bell");
      return _snowmanx;
   }

   protected static void method_16052(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }
}
