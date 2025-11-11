import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class aio extends akv {
   public aio(Schema var1, boolean var2) {
      super("EntityZombieSplitFix", _snowman, _snowman);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2) {
      if (Objects.equals("Zombie", _snowman)) {
         String _snowman = "Zombie";
         int _snowmanx = _snowman.get("ZombieType").asInt(0);
         switch (_snowmanx) {
            case 0:
            default:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               _snowman = "ZombieVillager";
               _snowman = _snowman.set("Profession", _snowman.createInt(_snowmanx - 1));
               break;
            case 6:
               _snowman = "Husk";
         }

         _snowman = _snowman.remove("ZombieType");
         return Pair.of(_snowman, _snowman);
      } else {
         return Pair.of(_snowman, _snowman);
      }
   }
}
