package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema808 extends IdentifierNormalizingSchema {
   public Schema808(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5309(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      method_5309(_snowman, _snowmanx, "minecraft:shulker_box");
      return _snowmanx;
   }
}
