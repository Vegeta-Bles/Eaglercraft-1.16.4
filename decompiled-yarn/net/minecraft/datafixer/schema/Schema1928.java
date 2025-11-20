package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1928 extends IdentifierNormalizingSchema {
   public Schema1928(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static TypeTemplate method_17997(Schema _snowman) {
      return DSL.optionalFields("ArmorItems", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "HandItems", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)));
   }

   protected static void method_17998(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> method_17997(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.remove("minecraft:illager_beast");
      method_17998(_snowman, _snowmanx, "minecraft:ravager");
      return _snowmanx;
   }
}
