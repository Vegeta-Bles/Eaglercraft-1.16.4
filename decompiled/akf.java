import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;

public class akf extends DataFix {
   public akf(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsLowerCaseLanguageFix", this.getInputSchema().getType(akn.e), var0 -> var0.update(DSL.remainderFinder(), var0x -> {
               Optional<String> _snowman = var0x.get("lang").asString().result();
               return _snowman.isPresent() ? var0x.set("lang", var0x.createString(_snowman.get().toLowerCase(Locale.ROOT))) : var0x;
            })
      );
   }
}
