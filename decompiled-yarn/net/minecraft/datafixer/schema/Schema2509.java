package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema2509 extends IdentifierNormalizingSchema {
   public Schema2509(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void renameZombifiedPiglin(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.remove("minecraft:zombie_pigman");
      renameZombifiedPiglin(_snowman, _snowmanx, "minecraft:zombified_piglin");
      return _snowmanx;
   }
}
