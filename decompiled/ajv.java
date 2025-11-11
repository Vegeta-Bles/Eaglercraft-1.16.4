import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public abstract class ajv extends DataFix {
   private final String a;
   private final String b;
   private final TypeReference c;

   public ajv(Schema var1, boolean var2, String var3, TypeReference var4, String var5) {
      super(_snowman, _snowman);
      this.a = _snowman;
      this.c = _snowman;
      this.b = _snowman;
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<?> _snowman = DSL.namedChoice(this.b, this.getInputSchema().getChoiceType(this.c, this.b));
      return this.fixTypeEverywhereTyped(
         this.a,
         this.getInputSchema().getType(this.c),
         this.getOutputSchema().getType(this.c),
         var2 -> var2.updateTyped(_snowman, this.getOutputSchema().getChoiceType(this.c, this.b), this::a)
      );
   }

   protected abstract Typed<?> a(Typed<?> var1);
}
