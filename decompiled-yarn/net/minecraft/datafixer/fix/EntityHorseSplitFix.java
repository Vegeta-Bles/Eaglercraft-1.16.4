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
   protected Pair<String, Typed<?>> transform(String choice, Typed<?> _snowman) {
      Dynamic<?> _snowmanx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      if (Objects.equals("EntityHorse", choice)) {
         int _snowmanxx = _snowmanx.get("Type").asInt(0);
         String _snowmanxxx;
         switch (_snowmanxx) {
            case 0:
            default:
               _snowmanxxx = "Horse";
               break;
            case 1:
               _snowmanxxx = "Donkey";
               break;
            case 2:
               _snowmanxxx = "Mule";
               break;
            case 3:
               _snowmanxxx = "ZombieHorse";
               break;
            case 4:
               _snowmanxxx = "SkeletonHorse";
         }

         _snowmanx.remove("Type");
         Type<?> _snowmanxx = (Type<?>)this.getOutputSchema().findChoiceType(TypeReferences.ENTITY).types().get(_snowmanxxx);
         return Pair.of(
            _snowmanxxx, ((Pair)_snowman.write().flatMap(_snowmanxx::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse"))).getFirst()
         );
      } else {
         return Pair.of(choice, _snowman);
      }
   }
}
