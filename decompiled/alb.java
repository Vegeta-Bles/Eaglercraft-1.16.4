import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class alb extends DataFix {
   public alb(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(akn.v.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.v))) {
         throw new IllegalStateException("Team type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(
            "TeamDisplayNameFix",
            _snowman,
            var0 -> var0x -> var0x.mapSecond(
                     var0xx -> var0xx.update(
                           "DisplayName",
                           var1x -> (Dynamic)DataFixUtils.orElse(
                                 var1x.asString().map(var0xxx -> nr.a.a(new oe(var0xxx))).map(var0xx::createString).result(), var1x
                              )
                        )
                  )
         );
      }
   }
}
