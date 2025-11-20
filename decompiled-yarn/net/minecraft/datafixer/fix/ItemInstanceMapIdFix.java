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
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemInstanceMapIdFix extends DataFix {
   public ItemInstanceMapIdFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", _snowman, _snowmanxxx -> {
         Optional<Pair<String, String>> _snowmanxxxx = _snowmanxxx.getOptional(_snowman);
         if (_snowmanxxxx.isPresent() && Objects.equals(_snowmanxxxx.get().getSecond(), "minecraft:filled_map")) {
            Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
            Typed<?> _snowmanxxx = _snowmanxxx.getOrCreateTyped(_snowman);
            Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
            _snowmanxxxx = _snowmanxxxx.set("map", _snowmanxxxx.createInt(_snowmanx.get("Damage").asInt(0)));
            return _snowmanxxx.set(_snowman, _snowmanxxx.set(DSL.remainderFinder(), _snowmanxxxx));
         } else {
            return _snowmanxxx;
         }
      });
   }
}
