package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v2 extends IdentifierNormalizingSchema {
   public Schema1451v2(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      _snowman.register(_snowmanx, "minecraft:piston", _snowmanxx -> DSL.optionalFields("blockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      return _snowmanx;
   }
}
