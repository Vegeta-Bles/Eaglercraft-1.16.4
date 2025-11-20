package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema702 extends Schema {
   public Schema702(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   protected static void method_5292(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_5292(_snowman, _snowmanx, "ZombieVillager");
      method_5292(_snowman, _snowmanx, "Husk");
      return _snowmanx;
   }
}
