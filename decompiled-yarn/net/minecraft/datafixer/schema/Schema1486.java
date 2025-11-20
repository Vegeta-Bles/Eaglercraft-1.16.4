package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1486 extends IdentifierNormalizingSchema {
   public Schema1486(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.put("minecraft:cod", _snowmanx.remove("minecraft:cod_mob"));
      _snowmanx.put("minecraft:salmon", _snowmanx.remove("minecraft:salmon_mob"));
      return _snowmanx;
   }
}
