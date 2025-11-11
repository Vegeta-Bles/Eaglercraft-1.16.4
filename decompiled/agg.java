import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public class agg extends DataFix {
   private final String a;
   private final Function<String, String> b;

   public agg(Schema var1, boolean var2, String var3, Function<String, String> var4) {
      super(_snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         this.a, this.getInputSchema().getType(akn.i), var1 -> var1.update(DSL.remainderFinder(), var1x -> var1x.updateMapValues(var2 -> {
                  String _snowman = ((Dynamic)var2.getFirst()).asString("");
                  return var2.mapFirst(var3x -> var1x.createString(this.b.apply(_snowman)));
               }))
      );
   }
}
