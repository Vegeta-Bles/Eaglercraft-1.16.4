package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v4 extends IdentifierNormalizingSchema {
   public Schema1451v4(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(false, TypeReferences.BLOCK_NAME, () -> DSL.constType(getIdentifierType()));
   }
}
