package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema100 extends Schema {
   public Schema100(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   protected static TypeTemplate targetItems(Schema _snowman) {
      return DSL.optionalFields("ArmorItems", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "HandItems", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)));
   }

   protected static void targetEntity(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> targetItems(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      targetEntity(_snowman, _snowmanx, "ArmorStand");
      targetEntity(_snowman, _snowmanx, "Creeper");
      targetEntity(_snowman, _snowmanx, "Skeleton");
      targetEntity(_snowman, _snowmanx, "Spider");
      targetEntity(_snowman, _snowmanx, "Giant");
      targetEntity(_snowman, _snowmanx, "Zombie");
      targetEntity(_snowman, _snowmanx, "Slime");
      targetEntity(_snowman, _snowmanx, "Ghast");
      targetEntity(_snowman, _snowmanx, "PigZombie");
      _snowman.register(_snowmanx, "Enderman", _snowmanxx -> DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(_snowman), targetItems(_snowman)));
      targetEntity(_snowman, _snowmanx, "CaveSpider");
      targetEntity(_snowman, _snowmanx, "Silverfish");
      targetEntity(_snowman, _snowmanx, "Blaze");
      targetEntity(_snowman, _snowmanx, "LavaSlime");
      targetEntity(_snowman, _snowmanx, "EnderDragon");
      targetEntity(_snowman, _snowmanx, "WitherBoss");
      targetEntity(_snowman, _snowmanx, "Bat");
      targetEntity(_snowman, _snowmanx, "Witch");
      targetEntity(_snowman, _snowmanx, "Endermite");
      targetEntity(_snowman, _snowmanx, "Guardian");
      targetEntity(_snowman, _snowmanx, "Pig");
      targetEntity(_snowman, _snowmanx, "Sheep");
      targetEntity(_snowman, _snowmanx, "Cow");
      targetEntity(_snowman, _snowmanx, "Chicken");
      targetEntity(_snowman, _snowmanx, "Squid");
      targetEntity(_snowman, _snowmanx, "Wolf");
      targetEntity(_snowman, _snowmanx, "MushroomCow");
      targetEntity(_snowman, _snowmanx, "SnowMan");
      targetEntity(_snowman, _snowmanx, "Ozelot");
      targetEntity(_snowman, _snowmanx, "VillagerGolem");
      _snowman.register(
         _snowmanx,
         "EntityHorse",
         _snowmanxx -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "ArmorItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               "SaddleItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               targetItems(_snowman)
            )
      );
      targetEntity(_snowman, _snowmanx, "Rabbit");
      _snowman.register(
         _snowmanx,
         "Villager",
         _snowmanxx -> DSL.optionalFields(
               "Inventory",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields(
                        "buy", TypeReferences.ITEM_STACK.in(_snowman), "buyB", TypeReferences.ITEM_STACK.in(_snowman), "sell", TypeReferences.ITEM_STACK.in(_snowman)
                     )
                  )
               ),
               targetItems(_snowman)
            )
      );
      targetEntity(_snowman, _snowmanx, "Shulker");
      _snowman.registerSimple(_snowmanx, "AreaEffectCloud");
      _snowman.registerSimple(_snowmanx, "ShulkerBullet");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         false,
         TypeReferences.STRUCTURE,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", TypeReferences.ENTITY_TREE.in(_snowman))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", TypeReferences.BLOCK_ENTITY.in(_snowman))),
               "palette",
               DSL.list(TypeReferences.BLOCK_STATE.in(_snowman))
            )
      );
      _snowman.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
   }
}
