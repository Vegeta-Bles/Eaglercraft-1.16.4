package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public abstract class PointOfInterestRenameFix extends DataFix {
   public PointOfInterestRenameFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(TypeReferences.POI_CHUNK.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rename", _snowman, _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(this::method_23299));
      }
   }

   private <T> Dynamic<T> method_23299(Dynamic<T> _snowman) {
      return _snowman.update(
         "Sections",
         _snowmanx -> _snowmanx.updateMapValues(_snowmanxx -> _snowmanxx.mapSecond(_snowmanxxx -> _snowmanxxx.update("Records", _snowmanxxxx -> (Dynamic)DataFixUtils.orElse(this.method_23304(_snowmanxxxx), _snowmanxxxx))))
      );
   }

   private <T> Optional<Dynamic<T>> method_23304(Dynamic<T> _snowman) {
      return _snowman.asStreamOpt()
         .map(
            _snowmanx -> _snowman.createList(
                  _snowmanx.map(
                     _snowmanxxx -> _snowmanxxx.update(
                           "type", _snowmanxxxx -> (Dynamic)DataFixUtils.orElse(_snowmanxxxx.asString().map(this::rename).map(_snowmanxxxx::createString).result(), _snowmanxxxx)
                        )
                  )
               )
         )
         .result();
   }

   protected abstract String rename(String input);
}
