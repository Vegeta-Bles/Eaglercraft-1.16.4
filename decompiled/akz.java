import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class akz extends DataFix {
   public akz(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.t);
      return this.fixTypeEverywhereTyped("Structure Reference Fix", _snowman, var0 -> var0.update(DSL.remainderFinder(), akz::a));
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      return _snowman.update("references", var0x -> var0x.createInt(var0x.asNumber().map(Number::intValue).result().filter(var0xx -> var0xx > 0).orElse(1)));
   }
}
