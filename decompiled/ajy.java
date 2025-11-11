import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ajy extends DataFix {
   public ajy(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private static ddq.a a(String var0) {
      return _snowman.equals("health") ? ddq.a.b : ddq.a.a;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(akn.u.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.u))) {
         throw new IllegalStateException("Objective type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("ObjectiveRenderTypeFix", _snowman, var0 -> var0x -> var0x.mapSecond(var0xx -> {
                  Optional<String> _snowmanx = var0xx.get("RenderType").asString().result();
                  if (!_snowmanx.isPresent()) {
                     String _snowmanx = var0xx.get("CriteriaName").asString("");
                     ddq.a _snowmanxx = a(_snowmanx);
                     return var0xx.set("RenderType", var0xx.createString(_snowmanxx.a()));
                  } else {
                     return var0xx;
                  }
               }));
      }
   }
}
