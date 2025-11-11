import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;

public class age extends DataFix {
   private final String a;
   private final TypeReference b;

   public age(Schema var1, String var2, TypeReference var3) {
      super(_snowman, true);
      this.a = _snowman;
      this.b = _snowman;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<?> _snowman = this.getInputSchema().findChoiceType(this.b);
      TaggedChoiceType<?> _snowmanx = this.getOutputSchema().findChoiceType(this.b);
      return this.a(this.a, _snowman, _snowmanx);
   }

   protected final <K> TypeRewriteRule a(String var1, TaggedChoiceType<K> var2, TaggedChoiceType<?> var3) {
      if (_snowman.getKeyType() != _snowman.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(_snowman, _snowman, _snowman, var2x -> var2xx -> {
               if (!_snowman.hasType(var2xx.getFirst())) {
                  throw new IllegalArgumentException(String.format("Unknown type %s in %s ", var2xx.getFirst(), this.b));
               } else {
                  return var2xx;
               }
            });
      }
   }
}
