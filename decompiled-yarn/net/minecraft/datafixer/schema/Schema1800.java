package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1800 extends IdentifierNormalizingSchema {
   public Schema1800(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5285(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_5285(_snowman, _snowmanx, "minecraft:panda");
      _snowman.register(_snowmanx, "minecraft:pillager", _snowmanxx -> DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), Schema100.targetItems(_snowman)));
      return _snowmanx;
   }
}
