import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ahp extends DataFix {
   public ahp(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> _snowman = DSL.fieldFinder("id", aln.a());
      return this.fixTypeEverywhereTyped(
         "EntityCustomNameToComponentFix", this.getInputSchema().getType(akn.p), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> _snowmanx = var1x.getOptional(_snowman);
               return _snowmanx.isPresent() && Objects.equals(_snowmanx.get(), "minecraft:commandblock_minecart") ? var2 : a(var2);
            })
      );
   }

   public static Dynamic<?> a(Dynamic<?> var0) {
      String _snowman = _snowman.get("CustomName").asString("");
      return _snowman.isEmpty() ? _snowman.remove("CustomName") : _snowman.set("CustomName", _snowman.createString(nr.a.a(new oe(_snowman))));
   }
}
