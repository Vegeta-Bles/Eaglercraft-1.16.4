package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema2522 extends IdentifierNormalizingSchema {
   public Schema2522(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void updateZoglinItems(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, String name) {
      schema.register(entityTypes, name, () -> Schema100.targetItems(schema));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(schema);
      updateZoglinItems(schema, _snowman, "minecraft:zoglin");
      return _snowman;
   }
}
