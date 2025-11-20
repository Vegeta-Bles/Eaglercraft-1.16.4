package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;

public class ChunkStatusFix2 extends DataFix {
   private static final Map<String, String> statusMap = ImmutableMap.builder()
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

   public ChunkStatusFix2(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("Level", _snowmanx);
      return this.fixTypeEverywhereTyped("ChunkStatusFix2", _snowman, this.getOutputSchema().getType(TypeReferences.CHUNK), _snowmanxxx -> _snowmanxxx.updateTyped(_snowman, _snowmanxxxx -> {
            Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
            String _snowmanxxxxxx = _snowmanx.get("Status").asString("empty");
            String _snowmanxxxxxxx = statusMap.getOrDefault(_snowmanxxxxxx, "empty");
            return Objects.equals(_snowmanxxxxxx, _snowmanxxxxxxx) ? _snowmanxxxx : _snowmanxxxx.set(DSL.remainderFinder(), _snowmanx.set("Status", _snowmanx.createString(_snowmanxxxxxxx)));
         }));
   }
}
