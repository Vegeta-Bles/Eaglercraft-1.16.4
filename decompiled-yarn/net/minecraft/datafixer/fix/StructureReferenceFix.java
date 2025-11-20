package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class StructureReferenceFix extends DataFix {
   public StructureReferenceFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
      return this.fixTypeEverywhereTyped("Structure Reference Fix", _snowman, _snowmanx -> _snowmanx.update(DSL.remainderFinder(), StructureReferenceFix::updateReferences));
   }

   private static <T> Dynamic<T> updateReferences(Dynamic<T> _snowman) {
      return _snowman.update("references", _snowmanx -> _snowmanx.createInt(_snowmanx.asNumber().map(Number::intValue).result().filter(_snowmanxx -> _snowmanxx > 0).orElse(1)));
   }
}
