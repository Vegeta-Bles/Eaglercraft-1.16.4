package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class BedItemColorFix extends DataFix {
   public BedItemColorFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(TypeReferences.ITEM_STACK), _snowmanx -> {
         Optional<Pair<String, String>> _snowmanx = _snowmanx.getOptional(_snowman);
         if (_snowmanx.isPresent() && Objects.equals(_snowmanx.get().getSecond(), "minecraft:bed")) {
            Dynamic<?> _snowmanxx = (Dynamic<?>)_snowmanx.get(DSL.remainderFinder());
            if (_snowmanxx.get("Damage").asInt(0) == 0) {
               return _snowmanx.set(DSL.remainderFinder(), _snowmanxx.set("Damage", _snowmanxx.createShort((short)14)));
            }
         }

         return _snowmanx;
      });
   }
}
