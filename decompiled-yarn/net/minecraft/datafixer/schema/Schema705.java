package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema705 extends IdentifierNormalizingSchema {
   protected static final HookFunction field_5746 = new HookFunction() {
      public <T> T apply(DynamicOps<T> _snowman, T _snowman) {
         return Schema99.method_5359(new Dynamic(_snowman, _snowman), Schema704.BLOCK_RENAMES, "minecraft:armor_stand");
      }
   };

   public Schema705(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5311(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   protected static void method_5330(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      _snowman.registerSimple(_snowmanx, "minecraft:area_effect_cloud");
      method_5311(_snowman, _snowmanx, "minecraft:armor_stand");
      _snowman.register(_snowmanx, "minecraft:arrow", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:bat");
      method_5311(_snowman, _snowmanx, "minecraft:blaze");
      _snowman.registerSimple(_snowmanx, "minecraft:boat");
      method_5311(_snowman, _snowmanx, "minecraft:cave_spider");
      _snowman.register(
         _snowmanx,
         "minecraft:chest_minecart",
         _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      method_5311(_snowman, _snowmanx, "minecraft:chicken");
      _snowman.register(_snowmanx, "minecraft:commandblock_minecart", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:cow");
      method_5311(_snowman, _snowmanx, "minecraft:creeper");
      _snowman.register(
         _snowmanx,
         "minecraft:donkey",
         _snowmanxx -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      _snowman.registerSimple(_snowmanx, "minecraft:dragon_fireball");
      method_5330(_snowman, _snowmanx, "minecraft:egg");
      method_5311(_snowman, _snowmanx, "minecraft:elder_guardian");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_crystal");
      method_5311(_snowman, _snowmanx, "minecraft:ender_dragon");
      _snowman.register(_snowmanx, "minecraft:enderman", _snowmanxx -> DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(_snowman), Schema100.targetItems(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:endermite");
      method_5330(_snowman, _snowmanx, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowmanx, "minecraft:eye_of_ender_signal");
      _snowman.register(
         _snowmanx,
         "minecraft:falling_block",
         _snowmanxx -> DSL.optionalFields("Block", TypeReferences.BLOCK_NAME.in(_snowman), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(_snowman))
      );
      method_5330(_snowman, _snowmanx, "minecraft:fireball");
      _snowman.register(_snowmanx, "minecraft:fireworks_rocket", _snowmanxx -> DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:furnace_minecart", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:ghast");
      method_5311(_snowman, _snowmanx, "minecraft:giant");
      method_5311(_snowman, _snowmanx, "minecraft:guardian");
      _snowman.register(
         _snowmanx,
         "minecraft:hopper_minecart",
         _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      _snowman.register(
         _snowmanx,
         "minecraft:horse",
         _snowmanxx -> DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(_snowman), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      method_5311(_snowman, _snowmanx, "minecraft:husk");
      _snowman.register(_snowmanx, "minecraft:item", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:item_frame", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "minecraft:leash_knot");
      method_5311(_snowman, _snowmanx, "minecraft:magma_cube");
      _snowman.register(_snowmanx, "minecraft:minecart", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:mooshroom");
      _snowman.register(
         _snowmanx,
         "minecraft:mule",
         _snowmanxx -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      method_5311(_snowman, _snowmanx, "minecraft:ocelot");
      _snowman.registerSimple(_snowmanx, "minecraft:painting");
      _snowman.registerSimple(_snowmanx, "minecraft:parrot");
      method_5311(_snowman, _snowmanx, "minecraft:pig");
      method_5311(_snowman, _snowmanx, "minecraft:polar_bear");
      _snowman.register(_snowmanx, "minecraft:potion", _snowmanxx -> DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(_snowman), "inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:rabbit");
      method_5311(_snowman, _snowmanx, "minecraft:sheep");
      method_5311(_snowman, _snowmanx, "minecraft:shulker");
      _snowman.registerSimple(_snowmanx, "minecraft:shulker_bullet");
      method_5311(_snowman, _snowmanx, "minecraft:silverfish");
      method_5311(_snowman, _snowmanx, "minecraft:skeleton");
      _snowman.register(_snowmanx, "minecraft:skeleton_horse", _snowmanxx -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:slime");
      method_5330(_snowman, _snowmanx, "minecraft:small_fireball");
      method_5330(_snowman, _snowmanx, "minecraft:snowball");
      method_5311(_snowman, _snowmanx, "minecraft:snowman");
      _snowman.register(
         _snowmanx, "minecraft:spawner_minecart", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), TypeReferences.UNTAGGED_SPAWNER.in(_snowman))
      );
      _snowman.register(_snowmanx, "minecraft:spectral_arrow", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:spider");
      method_5311(_snowman, _snowmanx, "minecraft:squid");
      method_5311(_snowman, _snowmanx, "minecraft:stray");
      _snowman.registerSimple(_snowmanx, "minecraft:tnt");
      _snowman.register(_snowmanx, "minecraft:tnt_minecart", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      _snowman.register(
         _snowmanx,
         "minecraft:villager",
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
               Schema100.targetItems(_snowman)
            )
      );
      method_5311(_snowman, _snowmanx, "minecraft:villager_golem");
      method_5311(_snowman, _snowmanx, "minecraft:witch");
      method_5311(_snowman, _snowmanx, "minecraft:wither");
      method_5311(_snowman, _snowmanx, "minecraft:wither_skeleton");
      method_5330(_snowman, _snowmanx, "minecraft:wither_skull");
      method_5311(_snowman, _snowmanx, "minecraft:wolf");
      method_5330(_snowman, _snowmanx, "minecraft:xp_bottle");
      _snowman.registerSimple(_snowmanx, "minecraft:xp_orb");
      method_5311(_snowman, _snowmanx, "minecraft:zombie");
      _snowman.register(_snowmanx, "minecraft:zombie_horse", _snowmanxx -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      method_5311(_snowman, _snowmanx, "minecraft:zombie_pigman");
      method_5311(_snowman, _snowmanx, "minecraft:zombie_villager");
      _snowman.registerSimple(_snowmanx, "minecraft:evocation_fangs");
      method_5311(_snowman, _snowmanx, "minecraft:evocation_illager");
      _snowman.registerSimple(_snowmanx, "minecraft:illusion_illager");
      _snowman.register(
         _snowmanx,
         "minecraft:llama",
         _snowmanxx -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "SaddleItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               "DecorItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               Schema100.targetItems(_snowman)
            )
      );
      _snowman.registerSimple(_snowmanx, "minecraft:llama_spit");
      method_5311(_snowman, _snowmanx, "minecraft:vex");
      method_5311(_snowman, _snowmanx, "minecraft:vindication_illager");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(true, TypeReferences.ENTITY, () -> DSL.taggedChoiceLazy("id", getIdentifierType(), entityTypes));
      _snowman.registerType(
         true,
         TypeReferences.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  TypeReferences.ITEM_NAME.in(_snowman),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     TypeReferences.ENTITY_TREE.in(_snowman),
                     "BlockEntityTag",
                     TypeReferences.BLOCK_ENTITY.in(_snowman),
                     "CanDestroy",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman)),
                     "CanPlaceOn",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman))
                  )
               ),
               field_5746,
               HookFunction.IDENTITY
            )
      );
   }
}
