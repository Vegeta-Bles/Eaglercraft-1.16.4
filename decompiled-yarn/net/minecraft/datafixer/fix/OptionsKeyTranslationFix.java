package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.stream.Collectors;
import net.minecraft.datafixer.TypeReferences;

public class OptionsKeyTranslationFix extends DataFix {
   public OptionsKeyTranslationFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyTranslationFix",
         this.getInputSchema().getType(TypeReferences.OPTIONS),
         _snowman -> _snowman.update(DSL.remainderFinder(), _snowmanx -> _snowmanx.getMapValues().map(_snowmanxxx -> _snowmanx.createMap(_snowmanxxx.entrySet().stream().map(_snowmanxxxxxx -> {
                     if (((Dynamic)_snowmanxxxxxx.getKey()).asString("").startsWith("key_")) {
                        String _snowmanxx = ((Dynamic)_snowmanxxxxxx.getValue()).asString("");
                        if (!_snowmanxx.startsWith("key.mouse") && !_snowmanxx.startsWith("scancode.")) {
                           return Pair.of(_snowmanxxxxxx.getKey(), _snowmanx.createString("key.keyboard." + _snowmanxx.substring("key.".length())));
                        }
                     }

                     return Pair.of(_snowmanxxxxxx.getKey(), _snowmanxxxxxx.getValue());
                  }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(_snowmanx))
      );
   }
}
