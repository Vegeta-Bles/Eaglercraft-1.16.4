package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class PointOfInterestReorganizationFix extends DataFix {
   public PointOfInterestReorganizationFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(TypeReferences.POI_CHUNK.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI reorganization", _snowman, _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(PointOfInterestReorganizationFix::reorganize));
      }
   }

   private static <T> Dynamic<T> reorganize(Dynamic<T> _snowman) {
      Map<Dynamic<T>, Dynamic<T>> _snowmanx = Maps.newHashMap();

      for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
         String _snowmanxxx = String.valueOf(_snowmanxx);
         Optional<Dynamic<T>> _snowmanxxxx = _snowman.get(_snowmanxxx).result();
         if (_snowmanxxxx.isPresent()) {
            Dynamic<T> _snowmanxxxxx = _snowmanxxxx.get();
            Dynamic<T> _snowmanxxxxxx = _snowman.createMap(ImmutableMap.of(_snowman.createString("Records"), _snowmanxxxxx));
            _snowmanx.put(_snowman.createInt(_snowmanxx), _snowmanxxxxxx);
            _snowman = _snowman.remove(_snowmanxxx);
         }
      }

      return _snowman.set("Sections", _snowman.createMap(_snowmanx));
   }
}
