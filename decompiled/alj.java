import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public class alj extends DataFix {
   private final String a;
   private final TypeReference b;

   public alj(Schema var1, String var2, TypeReference var3) {
      super(_snowman, true);
      this.a = _snowman;
      this.b = _snowman;
   }

   protected TypeRewriteRule makeRule() {
      return this.writeAndRead(this.a, this.getInputSchema().getType(this.b), this.getOutputSchema().getType(this.b));
   }
}
