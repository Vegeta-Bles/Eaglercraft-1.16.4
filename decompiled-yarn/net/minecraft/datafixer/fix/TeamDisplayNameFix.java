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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TeamDisplayNameFix extends DataFix {
   public TeamDisplayNameFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(TypeReferences.TEAM.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(TypeReferences.TEAM))) {
         throw new IllegalStateException("Team type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(
            "TeamDisplayNameFix",
            _snowman,
            _snowmanx -> _snowmanxx -> _snowmanxx.mapSecond(
                     _snowmanxxx -> _snowmanxxx.update(
                           "DisplayName",
                           _snowmanxxxx -> (Dynamic)DataFixUtils.orElse(
                                 _snowmanxxxx.asString().map(_snowmanxxxxx -> Text.Serializer.toJson(new LiteralText(_snowmanxxxxx))).map(_snowmanxx::createString).result(), _snowmanxxxx
                              )
                        )
                  )
         );
      }
   }
}
