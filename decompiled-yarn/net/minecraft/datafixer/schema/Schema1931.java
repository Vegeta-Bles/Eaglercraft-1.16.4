package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1931 extends IdentifierNormalizingSchema {
   public Schema1931(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_18247(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_18247(_snowman, _snowmanx, "minecraft:fox");
      return _snowmanx;
   }
}
