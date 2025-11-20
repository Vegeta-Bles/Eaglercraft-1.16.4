package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1470 extends IdentifierNormalizingSchema {
   public Schema1470(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5280(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      method_5280(_snowman, _snowmanx, "minecraft:turtle");
      method_5280(_snowman, _snowmanx, "minecraft:cod_mob");
      method_5280(_snowman, _snowmanx, "minecraft:tropical_fish");
      method_5280(_snowman, _snowmanx, "minecraft:salmon_mob");
      method_5280(_snowman, _snowmanx, "minecraft:puffer_fish");
      method_5280(_snowman, _snowmanx, "minecraft:phantom");
      method_5280(_snowman, _snowmanx, "minecraft:dolphin");
      method_5280(_snowman, _snowmanx, "minecraft:drowned");
      _snowman.register(_snowmanx, "minecraft:trident", _snowmanxx -> DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      return _snowmanx;
   }
}
