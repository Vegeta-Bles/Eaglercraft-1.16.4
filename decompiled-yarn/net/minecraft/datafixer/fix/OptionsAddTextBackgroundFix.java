package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class OptionsAddTextBackgroundFix extends DataFix {
   public OptionsAddTextBackgroundFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsAddTextBackgroundFix",
         this.getInputSchema().getType(TypeReferences.OPTIONS),
         _snowman -> _snowman.update(
               DSL.remainderFinder(),
               _snowmanx -> (Dynamic)DataFixUtils.orElse(
                     _snowmanx.get("chatOpacity")
                        .asString()
                        .map(_snowmanxx -> _snowmanx.set("textBackgroundOpacity", _snowmanx.createDouble(this.convertToTextBackgroundOpacity(_snowmanxx))))
                        .result(),
                     _snowmanx
                  )
            )
      );
   }

   private double convertToTextBackgroundOpacity(String chatOpacity) {
      try {
         double _snowman = 0.9 * Double.parseDouble(chatOpacity) + 0.1;
         return _snowman / 2.0;
      } catch (NumberFormatException var4) {
         return 0.5;
      }
   }
}
