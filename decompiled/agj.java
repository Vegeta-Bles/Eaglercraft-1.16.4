import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class agj extends DataFix {
   public agj(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(akn.l), var1x -> {
         Optional<Pair<String, String>> _snowmanx = var1x.getOptional(_snowman);
         if (_snowmanx.isPresent() && Objects.equals(_snowmanx.get().getSecond(), "minecraft:bed")) {
            Dynamic<?> _snowmanx = (Dynamic<?>)var1x.get(DSL.remainderFinder());
            if (_snowmanx.get("Damage").asInt(0) == 0) {
               return var1x.set(DSL.remainderFinder(), _snowmanx.set("Damage", _snowmanx.createShort((short)14)));
            }
         }

         return var1x;
      });
   }
}
