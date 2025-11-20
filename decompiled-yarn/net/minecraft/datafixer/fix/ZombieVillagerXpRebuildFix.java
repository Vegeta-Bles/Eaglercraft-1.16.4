package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class ZombieVillagerXpRebuildFix extends ChoiceFix {
   public ZombieVillagerXpRebuildFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "Zombie Villager XP rebuild", TypeReferences.ENTITY, "minecraft:zombie_villager");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), _snowman -> {
         Optional<Number> _snowmanx = _snowman.get("Xp").asNumber().result();
         if (!_snowmanx.isPresent()) {
            int _snowmanxx = _snowman.get("VillagerData").get("level").asInt(1);
            return _snowman.set("Xp", _snowman.createInt(VillagerXpRebuildFix.levelToXp(_snowmanxx)));
         } else {
            return _snowman;
         }
      });
   }
}
