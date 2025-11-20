package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1481 extends IdentifierNormalizingSchema {
   public Schema1481(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      _snowman.registerSimple(_snowmanx, "minecraft:conduit");
      return _snowmanx;
   }
}
