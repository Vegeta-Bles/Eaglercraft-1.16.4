package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.scoreboard.ScoreboardCriterion;

public class ObjectiveRenderTypeFix extends DataFix {
   public ObjectiveRenderTypeFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private static ScoreboardCriterion.RenderType parseLegacyRenderType(String oldName) {
      return oldName.equals("health") ? ScoreboardCriterion.RenderType.HEARTS : ScoreboardCriterion.RenderType.INTEGER;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(TypeReferences.OBJECTIVE.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(TypeReferences.OBJECTIVE))) {
         throw new IllegalStateException("Objective type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("ObjectiveRenderTypeFix", _snowman, _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(_snowmanxxx -> {
                  Optional<String> _snowmanx = _snowmanxxx.get("RenderType").asString().result();
                  if (!_snowmanx.isPresent()) {
                     String _snowmanxx = _snowmanxxx.get("CriteriaName").asString("");
                     ScoreboardCriterion.RenderType _snowmanxxxxxx = parseLegacyRenderType(_snowmanxx);
                     return _snowmanxxx.set("RenderType", _snowmanxxx.createString(_snowmanxxxxxx.getName()));
                  } else {
                     return _snowmanxxx;
                  }
               }));
      }
   }
}
