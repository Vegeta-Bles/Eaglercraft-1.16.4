package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;

public class RemovePoiValidTagFix extends DataFix {
   public RemovePoiValidTagFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(TypeReferences.POI_CHUNK.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rebuild", _snowman, _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(RemovePoiValidTagFix::removeValidTag));
      }
   }

   private static <T> Dynamic<T> removeValidTag(Dynamic<T> _snowman) {
      return _snowman.update("Sections", _snowmanx -> _snowmanx.updateMapValues(_snowmanxx -> _snowmanxx.mapSecond(_snowmanxxx -> _snowmanxxx.remove("Valid"))));
   }
}
