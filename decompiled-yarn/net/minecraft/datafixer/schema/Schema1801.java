package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1801 extends IdentifierNormalizingSchema {
   public Schema1801(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5283(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_5283(_snowman, _snowmanx, "minecraft:illager_beast");
      return _snowmanx;
   }
}
