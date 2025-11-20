package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import net.minecraft.datafixer.TypeReferences;

public class EntityRedundantChanceTagsFix extends DataFix {
   private static final Codec<List<Float>> field_25695 = Codec.FLOAT.listOf();

   public EntityRedundantChanceTagsFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityRedundantChanceTagsFix", this.getInputSchema().getType(TypeReferences.ENTITY), _snowman -> _snowman.update(DSL.remainderFinder(), _snowmanx -> {
               if (method_30073(_snowmanx.get("HandDropChances"), 2)) {
                  _snowmanx = _snowmanx.remove("HandDropChances");
               }

               if (method_30073(_snowmanx.get("ArmorDropChances"), 4)) {
                  _snowmanx = _snowmanx.remove("ArmorDropChances");
               }

               return _snowmanx;
            })
      );
   }

   private static boolean method_30073(OptionalDynamic<?> _snowman, int _snowman) {
      return _snowman.flatMap(field_25695::parse).map(_snowmanxxx -> _snowmanxxx.size() == _snowman && _snowmanxxx.stream().allMatch(_snowmanxxxx -> _snowmanxxxx == 0.0F)).result().orElse(false);
   }
}
