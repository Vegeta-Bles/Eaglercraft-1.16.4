import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alo extends Schema {
   public alo(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(akn.l.in(_snowman)), "HandItems", DSL.list(akn.l.in(_snowman)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> a(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      a(_snowman, _snowman, "ArmorStand");
      a(_snowman, _snowman, "Creeper");
      a(_snowman, _snowman, "Skeleton");
      a(_snowman, _snowman, "Spider");
      a(_snowman, _snowman, "Giant");
      a(_snowman, _snowman, "Zombie");
      a(_snowman, _snowman, "Slime");
      a(_snowman, _snowman, "Ghast");
      a(_snowman, _snowman, "PigZombie");
      _snowman.register(_snowman, "Enderman", var1x -> DSL.optionalFields("carried", akn.q.in(_snowman), a(_snowman)));
      a(_snowman, _snowman, "CaveSpider");
      a(_snowman, _snowman, "Silverfish");
      a(_snowman, _snowman, "Blaze");
      a(_snowman, _snowman, "LavaSlime");
      a(_snowman, _snowman, "EnderDragon");
      a(_snowman, _snowman, "WitherBoss");
      a(_snowman, _snowman, "Bat");
      a(_snowman, _snowman, "Witch");
      a(_snowman, _snowman, "Endermite");
      a(_snowman, _snowman, "Guardian");
      a(_snowman, _snowman, "Pig");
      a(_snowman, _snowman, "Sheep");
      a(_snowman, _snowman, "Cow");
      a(_snowman, _snowman, "Chicken");
      a(_snowman, _snowman, "Squid");
      a(_snowman, _snowman, "Wolf");
      a(_snowman, _snowman, "MushroomCow");
      a(_snowman, _snowman, "SnowMan");
      a(_snowman, _snowman, "Ozelot");
      a(_snowman, _snowman, "VillagerGolem");
      _snowman.register(_snowman, "EntityHorse", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "ArmorItem", akn.l.in(_snowman), "SaddleItem", akn.l.in(_snowman), a(_snowman)));
      a(_snowman, _snowman, "Rabbit");
      _snowman.register(
         _snowman,
         "Villager",
         var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "Offers",
               DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", akn.l.in(_snowman), "buyB", akn.l.in(_snowman), "sell", akn.l.in(_snowman)))),
               a(_snowman)
            )
      );
      a(_snowman, _snowman, "Shulker");
      _snowman.registerSimple(_snowman, "AreaEffectCloud");
      _snowman.registerSimple(_snowman, "ShulkerBullet");
      return _snowman;
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(
         false,
         akn.f,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", akn.o.in(_snowman))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", akn.k.in(_snowman))),
               "palette",
               DSL.list(akn.m.in(_snowman))
            )
      );
      _snowman.registerType(false, akn.m, DSL::remainder);
   }
}
