import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public abstract class akw extends DataFix {
   private final String a;

   public akw(String var1, Schema var2, boolean var3) {
      super(_snowman, _snowman);
      this.a = _snowman;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(akn.p);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(akn.p);
      Type<Pair<String, String>> _snowmanxx = DSL.named(akn.n.typeName(), aln.a());
      if (!Objects.equals(this.getOutputSchema().getType(akn.n), _snowmanxx)) {
         throw new IllegalStateException("Entity name type is not what was expected.");
      } else {
         return TypeRewriteRule.seq(this.fixTypeEverywhere(this.a, _snowman, _snowmanx, var3x -> var3xx -> var3xx.mapFirst(var3xxx -> {
                  String _snowmanxxx = this.a(var3xxx);
                  Type<?> _snowmanx = (Type<?>)_snowman.types().get(var3xxx);
                  Type<?> _snowmanxx = (Type<?>)_snowman.types().get(_snowmanxxx);
                  if (!_snowmanxx.equals(_snowmanx, true, true)) {
                     throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", _snowmanxx, _snowmanx));
                  } else {
                     return _snowmanxxx;
                  }
               })), this.fixTypeEverywhere(this.a + " for entity name", _snowmanxx, var1x -> var1xx -> var1xx.mapSecond(this::a)));
      }
   }

   protected abstract String a(String var1);
}
