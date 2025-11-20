package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;

public class ChunkStatusFix extends DataFix {
   public ChunkStatusFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped("ChunkStatusFix", _snowman, this.getOutputSchema().getType(TypeReferences.CHUNK), _snowmanxxx -> _snowmanxxx.updateTyped(_snowman, _snowmanxxxx -> {
            Dynamic<?> _snowmanxxxxx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
            String _snowmanxxxxxx = _snowmanxxxxx.get("Status").asString("empty");
            if (Objects.equals(_snowmanxxxxxx, "postprocessed")) {
               _snowmanxxxxx = _snowmanxxxxx.set("Status", _snowmanxxxxx.createString("fullchunk"));
            }

            return _snowmanxxxx.set(DSL.remainderFinder(), _snowmanxxxxx);
         }));
   }
}
