import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class akb extends DataFix {
   public akb(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsAddTextBackgroundFix",
         this.getInputSchema().getType(akn.e),
         var1 -> var1.update(
               DSL.remainderFinder(),
               var1x -> (Dynamic)DataFixUtils.orElse(
                     var1x.get("chatOpacity").asString().map(var2 -> var1x.set("textBackgroundOpacity", var1x.createDouble(this.a(var2)))).result(), var1x
                  )
            )
      );
   }

   private double a(String var1) {
      try {
         double _snowman = 0.9 * Double.parseDouble(_snowman) + 0.1;
         return _snowman / 2.0;
      } catch (NumberFormatException var4) {
         return 0.5;
      }
   }
}
