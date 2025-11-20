package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import net.minecraft.datafixer.TypeReferences;

public class EntityZombieVillagerTypeFix extends ChoiceFix {
   private static final Random RANDOM = new Random();

   public EntityZombieVillagerTypeFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "EntityZombieVillagerTypeFix", TypeReferences.ENTITY, "Zombie");
   }

   public Dynamic<?> fixZombieType(Dynamic<?> _snowman) {
      if (_snowman.get("IsVillager").asBoolean(false)) {
         if (!_snowman.get("ZombieType").result().isPresent()) {
            int _snowmanx = this.clampType(_snowman.get("VillagerProfession").asInt(-1));
            if (_snowmanx == -1) {
               _snowmanx = this.clampType(RANDOM.nextInt(6));
            }

            _snowman = _snowman.set("ZombieType", _snowman.createInt(_snowmanx));
         }

         _snowman = _snowman.remove("IsVillager");
      }

      return _snowman;
   }

   private int clampType(int type) {
      return type >= 0 && type < 6 ? type : -1;
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixZombieType);
   }
}
