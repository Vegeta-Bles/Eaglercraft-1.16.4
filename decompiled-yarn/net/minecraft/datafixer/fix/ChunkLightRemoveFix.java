package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.datafixer.TypeReferences;

public class ChunkLightRemoveFix extends DataFix {
   public ChunkLightRemoveFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped(
         "ChunkLightRemoveFix",
         _snowman,
         this.getOutputSchema().getType(TypeReferences.CHUNK),
         _snowmanxxx -> _snowmanxxx.updateTyped(_snowman, _snowmanxxxx -> _snowmanxxxx.update(DSL.remainderFinder(), _snowmanxxxxx -> _snowmanxxxxx.remove("isLightOn")))
      );
   }
}
