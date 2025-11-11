import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;

public class ahg extends DataFix {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("structure_references", "empty")
      .put("biomes", "empty")
      .put("base", "surface")
      .put("carved", "carvers")
      .put("liquid_carved", "liquid_carvers")
      .put("decorated", "features")
      .put("lighted", "light")
      .put("mobs_spawned", "spawn")
      .put("finalized", "heightmaps")
      .put("fullchunk", "full")
      .build();

   public ahg(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped("ChunkStatusFix2", _snowman, this.getOutputSchema().getType(akn.c), var1x -> var1x.updateTyped(_snowman, var0x -> {
            Dynamic<?> _snowmanxxx = (Dynamic<?>)var0x.get(DSL.remainderFinder());
            String _snowmanx = _snowmanxxx.get("Status").asString("empty");
            String _snowmanxx = a.getOrDefault(_snowmanx, "empty");
            return Objects.equals(_snowmanx, _snowmanxx) ? var0x : var0x.set(DSL.remainderFinder(), _snowmanxxx.set("Status", _snowmanxxx.createString(_snowmanxx)));
         }));
   }
}
