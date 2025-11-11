import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;

public class alk extends ajv {
   public alk(Schema var1, boolean var2) {
      super(_snowman, _snowman, "Zombie Villager XP rebuild", akn.p, "minecraft:zombie_villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), var0 -> {
         Optional<Number> _snowman = var0.get("Xp").asNumber().result();
         if (!_snowman.isPresent()) {
            int _snowmanx = var0.get("VillagerData").get("level").asInt(1);
            return var0.set("Xp", var0.createInt(alf.a(_snowmanx)));
         } else {
            return var0;
         }
      });
   }
}
