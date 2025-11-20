package net.minecraft.datafixer.fix;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.datafixer.TypeReferences;

public class EntityHealthFix extends DataFix {
   private static final Set<String> ENTITIES = Sets.newHashSet(
      new String[]{
         "ArmorStand",
         "Bat",
         "Blaze",
         "CaveSpider",
         "Chicken",
         "Cow",
         "Creeper",
         "EnderDragon",
         "Enderman",
         "Endermite",
         "EntityHorse",
         "Ghast",
         "Giant",
         "Guardian",
         "LavaSlime",
         "MushroomCow",
         "Ozelot",
         "Pig",
         "PigZombie",
         "Rabbit",
         "Sheep",
         "Shulker",
         "Silverfish",
         "Skeleton",
         "Slime",
         "SnowMan",
         "Spider",
         "Squid",
         "Villager",
         "VillagerGolem",
         "Witch",
         "WitherBoss",
         "Wolf",
         "Zombie"
      }
   );

   public EntityHealthFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public Dynamic<?> fixHealth(Dynamic<?> _snowman) {
      Optional<Number> _snowmanx = _snowman.get("HealF").asNumber().result();
      Optional<Number> _snowmanxx = _snowman.get("Health").asNumber().result();
      float _snowmanxxx;
      if (_snowmanx.isPresent()) {
         _snowmanxxx = _snowmanx.get().floatValue();
         _snowman = _snowman.remove("HealF");
      } else {
         if (!_snowmanxx.isPresent()) {
            return _snowman;
         }

         _snowmanxxx = _snowmanxx.get().floatValue();
      }

      return _snowman.set("Health", _snowman.createFloat(_snowmanxxx));
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityHealthFix", this.getInputSchema().getType(TypeReferences.ENTITY), _snowman -> _snowman.update(DSL.remainderFinder(), this::fixHealth)
      );
   }
}
