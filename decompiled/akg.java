import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class akg extends DataFix {
   private final String a;
   private final String b;
   private final String c;

   public akg(Schema var1, boolean var2, String var3, String var4, String var5) {
      super(_snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         this.a,
         this.getInputSchema().getType(akn.e),
         var1 -> var1.update(
               DSL.remainderFinder(),
               var1x -> (Dynamic)DataFixUtils.orElse(var1x.get(this.b).result().map(var2 -> var1x.set(this.c, var2).remove(this.b)), var1x)
            )
      );
   }
}
