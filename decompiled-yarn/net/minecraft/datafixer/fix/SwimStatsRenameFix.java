package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class SwimStatsRenameFix extends DataFix {
   public SwimStatsRenameFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.STATS);
      Type<?> _snowmanx = this.getInputSchema().getType(TypeReferences.STATS);
      OpticFinder<?> _snowmanxx = _snowmanx.findField("stats");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("minecraft:custom");
      OpticFinder<String> _snowmanxxxx = IdentifierNormalizingSchema.getIdentifierType().finder();
      return this.fixTypeEverywhereTyped(
         "SwimStatsRenameFix", _snowmanx, _snowman, _snowmanxxxxx -> _snowmanxxxxx.updateTyped(_snowman, _snowmanxxxxxx -> _snowmanxxxxxx.updateTyped(_snowman, _snowmanxxxxxxxx -> _snowmanxxxxxxxx.update(_snowman, _snowmanxxxxxxxxx -> {
                     if (_snowmanxxxxxxxxx.equals("minecraft:swim_one_cm")) {
                        return "minecraft:walk_on_water_one_cm";
                     } else {
                        return _snowmanxxxxxxxxx.equals("minecraft:dive_one_cm") ? "minecraft:walk_under_water_one_cm" : _snowmanxxxxxxxxx;
                     }
                  })))
      );
   }
}
