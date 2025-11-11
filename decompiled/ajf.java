import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ajf extends DataFix {
   public ajf(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", _snowman, var2x -> {
         Optional<Pair<String, String>> _snowmanxxx = var2x.getOptional(_snowman);
         if (_snowmanxxx.isPresent() && Objects.equals(_snowmanxxx.get().getSecond(), "minecraft:filled_map")) {
            Dynamic<?> _snowmanx = (Dynamic<?>)var2x.get(DSL.remainderFinder());
            Typed<?> _snowmanxx = var2x.getOrCreateTyped(_snowman);
            Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowmanxx.get(DSL.remainderFinder());
            _snowmanxxx = _snowmanxxx.set("map", _snowmanxxx.createInt(_snowmanx.get("Damage").asInt(0)));
            return var2x.set(_snowman, _snowmanxx.set(DSL.remainderFinder(), _snowmanxxx));
         } else {
            return var2x;
         }
      });
   }
}
