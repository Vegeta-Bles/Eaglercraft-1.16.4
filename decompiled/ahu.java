import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahu extends aie {
   public ahu(Schema var1, boolean var2) {
      super("EntityHorseSplitFix", _snowman, _snowman);
   }

   @Override
   protected Pair<String, Typed<?>> a(String var1, Typed<?> var2) {
      Dynamic<?> _snowman = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      if (Objects.equals("EntityHorse", _snowman)) {
         int _snowmanx = _snowman.get("Type").asInt(0);
         String _snowmanxx;
         switch (_snowmanx) {
            case 0:
            default:
               _snowmanxx = "Horse";
               break;
            case 1:
               _snowmanxx = "Donkey";
               break;
            case 2:
               _snowmanxx = "Mule";
               break;
            case 3:
               _snowmanxx = "ZombieHorse";
               break;
            case 4:
               _snowmanxx = "SkeletonHorse";
         }

         _snowman.remove("Type");
         Type<?> _snowmanx = (Type<?>)this.getOutputSchema().findChoiceType(akn.p).types().get(_snowmanxx);
         return Pair.of(
            _snowmanxx, ((Pair)_snowman.write().flatMap(_snowmanx::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse"))).getFirst()
         );
      } else {
         return Pair.of(_snowman, _snowman);
      }
   }
}
