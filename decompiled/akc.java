import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class akc extends DataFix {
   public akc(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsForceVBOFix",
         this.getInputSchema().getType(akn.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.set("useVbo", var0x.createString("true")))
      );
   }
}
