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
         typed -> typed.update(
               DSL.remainderFinder(),
               dynamic -> dynamic.getMapValues().map(map -> {
                        var remapped = map.entrySet()
                           .stream()
                           .map(entry -> {
                              Dynamic<?> key = entry.getKey();
                              Dynamic<?> value = entry.getValue();
                              if (key.asString("").startsWith("key_")) {
                                 String string = value.asString("");
                                 if (!string.startsWith("key.mouse") && !string.startsWith("scancode.")) {
                                    return Pair.of(key, dynamic.createString("key.keyboard." + string.substring("key.".length())));
                                 }
                              }

                              return Pair.of(key, value);
                           })
                           .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
                        @SuppressWarnings("unchecked")
                        var castMap = (java.util.Map<Dynamic<?>, Dynamic<?>>)remapped;
                        return dynamic.createMap((java.util.Map)castMap);
                     })
                     .result()
                     .orElse(dynamic)
            )
      );
   }
}
