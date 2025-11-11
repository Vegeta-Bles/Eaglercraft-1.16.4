import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class ahs extends DataFix {
   private static final Set<String> a = Sets.newHashSet(
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

   public ahs(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      Optional<Number> _snowman = _snowman.get("HealF").asNumber().result();
      Optional<Number> _snowmanx = _snowman.get("Health").asNumber().result();
      float _snowmanxx;
      if (_snowman.isPresent()) {
         _snowmanxx = _snowman.get().floatValue();
         _snowman = _snowman.remove("HealF");
      } else {
         if (!_snowmanx.isPresent()) {
            return _snowman;
         }

         _snowmanxx = _snowmanx.get().floatValue();
      }

      return _snowman.set("Health", _snowman.createFloat(_snowmanxx));
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityHealthFix", this.getInputSchema().getType(akn.p), var1 -> var1.update(DSL.remainderFinder(), this::a));
   }
}
