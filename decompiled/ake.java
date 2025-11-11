import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.stream.Collectors;

public class ake extends DataFix {
   public ake(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyTranslationFix",
         this.getInputSchema().getType(akn.e),
         var0 -> var0.update(DSL.remainderFinder(), var0x -> var0x.getMapValues().map(var1 -> var0x.createMap(var1.entrySet().stream().map(var1x -> {
                     if (((Dynamic)var1x.getKey()).asString("").startsWith("key_")) {
                        String _snowman = ((Dynamic)var1x.getValue()).asString("");
                        if (!_snowman.startsWith("key.mouse") && !_snowman.startsWith("scancode.")) {
                           return Pair.of(var1x.getKey(), var0x.createString("key.keyboard." + _snowman.substring("key.".length())));
                        }
                     }

                     return Pair.of(var1x.getKey(), var1x.getValue());
                  }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(var0x))
      );
   }
}
