package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemWaterPotionFix extends DataFix {
   public ItemWaterPotionFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWaterPotionFix",
         _snowman,
         _snowmanxxx -> {
            Optional<Pair<String, String>> _snowmanxxxx = _snowmanxxx.getOptional(_snowman);
            if (_snowmanxxxx.isPresent()) {
               String _snowmanx = (String)_snowmanxxxx.get().getSecond();
               if ("minecraft:potion".equals(_snowmanx)
                  || "minecraft:splash_potion".equals(_snowmanx)
                  || "minecraft:lingering_potion".equals(_snowmanx)
                  || "minecraft:tipped_arrow".equals(_snowmanx)) {
                  Typed<?> _snowmanxxx = _snowmanxxx.getOrCreateTyped(_snowman);
                  Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
                  if (!_snowmanxxxx.get("Potion").asString().result().isPresent()) {
                     _snowmanxxxx = _snowmanxxxx.set("Potion", _snowmanxxxx.createString("minecraft:water"));
                  }

                  return _snowmanxxx.set(_snowman, _snowmanxxx.set(DSL.remainderFinder(), _snowmanxxxx));
               }
            }

            return _snowmanxxx;
         }
      );
   }
}
