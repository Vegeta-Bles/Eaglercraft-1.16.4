package net.minecraft.datafixer.fix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityZombieSplitFix extends EntitySimpleTransformFix {
   public EntityZombieSplitFix(Schema outputSchema, boolean changesType) {
      super("EntityZombieSplitFix", outputSchema, changesType);
   }

   @Override
   protected Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> _snowman) {
      if (Objects.equals("Zombie", choice)) {
         String _snowmanx = "Zombie";
         int _snowmanxx = _snowman.get("ZombieType").asInt(0);
         switch (_snowmanxx) {
            case 0:
            default:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               _snowmanx = "ZombieVillager";
               _snowman = _snowman.set("Profession", _snowman.createInt(_snowmanxx - 1));
               break;
            case 6:
               _snowmanx = "Husk";
         }

         _snowman = _snowman.remove("ZombieType");
         return Pair.of(_snowmanx, _snowman);
      } else {
         return Pair.of(choice, _snowman);
      }
   }
}
