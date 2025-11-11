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

public class aks extends DataFix {
   public aks(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(akn.j.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI reorganization", _snowman, var0 -> var0x -> var0x.mapSecond(aks::a));
      }
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      Map<Dynamic<T>, Dynamic<T>> _snowman = Maps.newHashMap();

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         String _snowmanxx = String.valueOf(_snowmanx);
         Optional<Dynamic<T>> _snowmanxxx = _snowman.get(_snowmanxx).result();
         if (_snowmanxxx.isPresent()) {
            Dynamic<T> _snowmanxxxx = _snowmanxxx.get();
            Dynamic<T> _snowmanxxxxx = _snowman.createMap(ImmutableMap.of(_snowman.createString("Records"), _snowmanxxxx));
            _snowman.put(_snowman.createInt(_snowmanx), _snowmanxxxxx);
            _snowman = _snowman.remove(_snowmanxx);
         }
      }

      return _snowman.set("Sections", _snowman.createMap(_snowman));
   }
}
