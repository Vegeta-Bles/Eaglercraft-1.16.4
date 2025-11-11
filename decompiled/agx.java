import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class agx extends DataFix {
   private final String a;

   public agx(Schema var1, String var2) {
      super(_snowman, false);
      this.a = _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.q);
      Type<Pair<String, String>> _snowmanx = DSL.named(akn.q.typeName(), aln.a());
      if (!Objects.equals(_snowman, _snowmanx)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule _snowmanxx = this.fixTypeEverywhere(this.a + " for block", _snowmanx, var1x -> var1xx -> var1xx.mapSecond(this::a));
         TypeRewriteRule _snowmanxxx = this.fixTypeEverywhereTyped(
            this.a + " for block_state", this.getInputSchema().getType(akn.m), var1x -> var1x.update(DSL.remainderFinder(), var1xx -> {
                  Optional<String> _snowmanxxxx = var1xx.get("Name").asString().result();
                  return _snowmanxxxx.isPresent() ? var1xx.set("Name", var1xx.createString(this.a(_snowmanxxxx.get()))) : var1xx;
               })
         );
         return TypeRewriteRule.seq(_snowmanxx, _snowmanxxx);
      }
   }

   protected abstract String a(String var1);

   public static DataFix a(Schema var0, String var1, final Function<String, String> var2) {
      return new agx(_snowman, _snowman) {
         @Override
         protected String a(String var1) {
            return _snowman.apply(_snowman);
         }
      };
   }
}
