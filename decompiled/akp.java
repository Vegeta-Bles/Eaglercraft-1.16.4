import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;

public class akp extends DataFix {
   private final String a;
   private final Map<String, String> b;

   public akp(Schema var1, boolean var2, String var3, Map<String, String> var4) {
      super(_snowman, _snowman);
      this.b = _snowman;
      this.a = _snowman;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> _snowman = DSL.named(akn.x.typeName(), aln.a());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.x))) {
         throw new IllegalStateException("Biome type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, _snowman, var1x -> var1xx -> var1xx.mapSecond(var1xxx -> this.b.getOrDefault(var1xxx, var1xxx)));
      }
   }
}
