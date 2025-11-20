package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1483 extends IdentifierNormalizingSchema {
   public Schema1483(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.put("minecraft:pufferfish", _snowmanx.remove("minecraft:puffer_fish"));
      return _snowmanx;
   }
}
