package net.minecraft.datafixer.fix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntitySkeletonSplitFix extends EntitySimpleTransformFix {
   public EntitySkeletonSplitFix(Schema outputSchema, boolean changesType) {
      super("EntitySkeletonSplitFix", outputSchema, changesType);
   }

   @Override
   protected Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> _snowman) {
      if (Objects.equals(choice, "Skeleton")) {
         int _snowmanx = _snowman.get("SkeletonType").asInt(0);
         if (_snowmanx == 1) {
            choice = "WitherSkeleton";
         } else if (_snowmanx == 2) {
            choice = "Stray";
         }
      }

      return Pair.of(choice, _snowman);
   }
}
