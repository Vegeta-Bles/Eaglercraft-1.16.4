import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ajj extends DataFix {
   public ajj(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWaterPotionFix",
         _snowman,
         var2x -> {
            Optional<Pair<String, String>> _snowmanxxx = var2x.getOptional(_snowman);
            if (_snowmanxxx.isPresent()) {
               String _snowmanx = (String)_snowmanxxx.get().getSecond();
               if ("minecraft:potion".equals(_snowmanx)
                  || "minecraft:splash_potion".equals(_snowmanx)
                  || "minecraft:lingering_potion".equals(_snowmanx)
                  || "minecraft:tipped_arrow".equals(_snowmanx)) {
                  Typed<?> _snowmanxx = var2x.getOrCreateTyped(_snowman);
                  Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowmanxx.get(DSL.remainderFinder());
                  if (!_snowmanxxx.get("Potion").asString().result().isPresent()) {
                     _snowmanxxx = _snowmanxxx.set("Potion", _snowmanxxx.createString("minecraft:water"));
                  }

                  return var2x.set(_snowman, _snowmanxx.set(DSL.remainderFinder(), _snowmanxxx));
               }
            }

            return var2x;
         }
      );
   }
}
