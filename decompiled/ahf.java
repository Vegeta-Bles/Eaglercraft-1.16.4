import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahf extends DataFix {
   public ahf(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped("ChunkStatusFix", _snowman, this.getOutputSchema().getType(akn.c), var1x -> var1x.updateTyped(_snowman, var0x -> {
            Dynamic<?> _snowmanxxx = (Dynamic<?>)var0x.get(DSL.remainderFinder());
            String _snowmanx = _snowmanxxx.get("Status").asString("empty");
            if (Objects.equals(_snowmanx, "postprocessed")) {
               _snowmanxxx = _snowmanxxx.set("Status", _snowmanxxx.createString("fullchunk"));
            }

            return var0x.set(DSL.remainderFinder(), _snowmanxxx);
         }));
   }
}
