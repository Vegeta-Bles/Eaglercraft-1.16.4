import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public class akk extends DataFix {
   private final String a;
   private final Function<String, String> b;

   public akk(Schema var1, boolean var2, String var3, Function<String, String> var4) {
      super(_snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> _snowman = DSL.named(akn.w.typeName(), aln.a());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.w))) {
         throw new IllegalStateException("Recipe type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, _snowman, var1x -> var1xx -> var1xx.mapSecond(this.b));
      }
   }
}
