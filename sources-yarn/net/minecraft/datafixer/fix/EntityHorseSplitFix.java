package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;

public class EntityHorseSplitFix extends EntityTransformFix {
   public EntityHorseSplitFix(Schema outputSchema, boolean changesType) {
      super("EntityHorseSplitFix", outputSchema, changesType);
   }

   @Override
   protected Pair<String, Typed<?>> transform(String choice, Typed<?> typed) {
      Dynamic<?> dynamic = (Dynamic<?>)typed.get(DSL.remainderFinder());
      if (!Objects.equals("EntityHorse", choice)) {
         return Pair.of(choice, typed);
      }

      int typeId = dynamic.get("Type").asInt(0);
      String newId;
      switch (typeId) {
         case 1:
            newId = "Donkey";
            break;
         case 2:
            newId = "Mule";
            break;
         case 3:
            newId = "ZombieHorse";
            break;
         case 4:
            newId = "SkeletonHorse";
            break;
         case 0:
         default:
            newId = "Horse";
      }

      Typed<?> stripped = typed.update(DSL.remainderFinder(), dyn -> dyn.remove("Type"));
      Type<?> targetType = this.getOutputSchema().getChoiceType(TypeReferences.ENTITY, newId);
      Pair<?, ?> result = stripped.write().flatMap(targetType::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse"));
      @SuppressWarnings("unchecked")
      Typed<?> converted = (Typed<?>)result.getFirst();
      return Pair.of(newId, converted);
   }
}
