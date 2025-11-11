import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public class agw extends DataFix {
   public agw(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.q);
      Type<?> _snowmanx = this.getOutputSchema().getType(akn.q);
      Type<Pair<String, Either<Integer, String>>> _snowmanxx = DSL.named(akn.q.typeName(), DSL.or(DSL.intType(), aln.a()));
      Type<Pair<String, String>> _snowmanxxx = DSL.named(akn.q.typeName(), aln.a());
      if (Objects.equals(_snowman, _snowmanxx) && Objects.equals(_snowmanx, _snowmanxxx)) {
         return this.fixTypeEverywhere(
            "BlockNameFlatteningFix", _snowmanxx, _snowmanxxx, var0 -> var0x -> var0x.mapSecond(var0xx -> (String)var0xx.map(agz::a, var0xxx -> agz.a(aln.a(var0xxx))))
         );
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}
