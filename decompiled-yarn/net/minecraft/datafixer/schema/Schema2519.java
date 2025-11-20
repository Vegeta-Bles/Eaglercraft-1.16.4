package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema2519 extends IdentifierNormalizingSchema {
   public Schema2519(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void updateStriderItems(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, String name) {
      schema.register(entityTypes, name, () -> Schema100.targetItems(schema));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      updateStriderItems(_snowman, _snowmanx, "minecraft:strider");
      return _snowmanx;
   }
}
