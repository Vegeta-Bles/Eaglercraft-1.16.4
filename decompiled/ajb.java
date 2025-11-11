import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public abstract class ajb extends DataFix {
   private final String a;

   public ajb(Schema var1, String var2) {
      super(_snowman, false);
      this.a = _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<Pair<String, String>> _snowman = DSL.named(akn.r.typeName(), aln.a());
      if (!Objects.equals(this.getInputSchema().getType(akn.r), _snowman)) {
         throw new IllegalStateException("item name type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.a, _snowman, var1x -> var1xx -> var1xx.mapSecond(this::a));
      }
   }

   protected abstract String a(String var1);

   public static DataFix a(Schema var0, String var1, final Function<String, String> var2) {
      return new ajb(_snowman, _snowman) {
         @Override
         protected String a(String var1) {
            return _snowman.apply(_snowman);
         }
      };
   }
}
