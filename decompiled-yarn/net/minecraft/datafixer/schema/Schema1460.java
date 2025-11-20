package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1460 extends IdentifierNormalizingSchema {
   public Schema1460(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   protected static void method_5232(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> Schema100.targetItems(_snowman));
   }

   protected static void method_5273(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      _snowman.registerSimple(_snowmanx, "minecraft:area_effect_cloud");
      method_5232(_snowman, _snowmanx, "minecraft:armor_stand");
      _snowman.register(_snowmanx, "minecraft:arrow", _snowmanxx -> DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:bat");
      method_5232(_snowman, _snowmanx, "minecraft:blaze");
      _snowman.registerSimple(_snowmanx, "minecraft:boat");
      method_5232(_snowman, _snowmanx, "minecraft:cave_spider");
      _snowman.register(
         _snowmanx,
         "minecraft:chest_minecart",
         _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      method_5232(_snowman, _snowmanx, "minecraft:chicken");
      _snowman.register(_snowmanx, "minecraft:commandblock_minecart", _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:cow");
      method_5232(_snowman, _snowmanx, "minecraft:creeper");
      _snowman.register(
         _snowmanx,
         "minecraft:donkey",
         _snowmanxx -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      _snowman.registerSimple(_snowmanx, "minecraft:dragon_fireball");
      _snowman.registerSimple(_snowmanx, "minecraft:egg");
      method_5232(_snowman, _snowmanx, "minecraft:elder_guardian");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_crystal");
      method_5232(_snowman, _snowmanx, "minecraft:ender_dragon");
      _snowman.register(_snowmanx, "minecraft:enderman", _snowmanxx -> DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(_snowman), Schema100.targetItems(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:endermite");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowmanx, "minecraft:evocation_fangs");
      method_5232(_snowman, _snowmanx, "minecraft:evocation_illager");
      _snowman.registerSimple(_snowmanx, "minecraft:eye_of_ender_signal");
      _snowman.register(
         _snowmanx,
         "minecraft:falling_block",
         _snowmanxx -> DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(_snowman), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(_snowman))
      );
      _snowman.registerSimple(_snowmanx, "minecraft:fireball");
      _snowman.register(_snowmanx, "minecraft:fireworks_rocket", _snowmanxx -> DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:furnace_minecart", _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:ghast");
      method_5232(_snowman, _snowmanx, "minecraft:giant");
      method_5232(_snowman, _snowmanx, "minecraft:guardian");
      _snowman.register(
         _snowmanx,
         "minecraft:hopper_minecart",
         _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      _snowman.register(
         _snowmanx,
         "minecraft:horse",
         _snowmanxx -> DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(_snowman), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      method_5232(_snowman, _snowmanx, "minecraft:husk");
      _snowman.registerSimple(_snowmanx, "minecraft:illusion_illager");
      _snowman.register(_snowmanx, "minecraft:item", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:item_frame", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "minecraft:leash_knot");
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
      method_5232(_snowman, _snowmanx, "minecraft:magma_cube");
      _snowman.register(_snowmanx, "minecraft:minecart", _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:mooshroom");
      _snowman.register(
         _snowmanx,
         "minecraft:mule",
         _snowmanxx -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      method_5232(_snowman, _snowmanx, "minecraft:ocelot");
      _snowman.registerSimple(_snowmanx, "minecraft:painting");
      _snowman.registerSimple(_snowmanx, "minecraft:parrot");
      method_5232(_snowman, _snowmanx, "minecraft:pig");
      method_5232(_snowman, _snowmanx, "minecraft:polar_bear");
      _snowman.register(_snowmanx, "minecraft:potion", _snowmanxx -> DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:rabbit");
      method_5232(_snowman, _snowmanx, "minecraft:sheep");
      method_5232(_snowman, _snowmanx, "minecraft:shulker");
      _snowman.registerSimple(_snowmanx, "minecraft:shulker_bullet");
      method_5232(_snowman, _snowmanx, "minecraft:silverfish");
      method_5232(_snowman, _snowmanx, "minecraft:skeleton");
      _snowman.register(_snowmanx, "minecraft:skeleton_horse", _snowmanxx -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:slime");
      _snowman.registerSimple(_snowmanx, "minecraft:small_fireball");
      _snowman.registerSimple(_snowmanx, "minecraft:snowball");
      method_5232(_snowman, _snowmanx, "minecraft:snowman");
      _snowman.register(
         _snowmanx, "minecraft:spawner_minecart", _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), TypeReferences.UNTAGGED_SPAWNER.in(_snowman))
      );
      _snowman.register(_snowmanx, "minecraft:spectral_arrow", _snowmanxx -> DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:spider");
      method_5232(_snowman, _snowmanx, "minecraft:squid");
      method_5232(_snowman, _snowmanx, "minecraft:stray");
      _snowman.registerSimple(_snowmanx, "minecraft:tnt");
      _snowman.register(_snowmanx, "minecraft:tnt_minecart", _snowmanxx -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:vex");
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
      method_5232(_snowman, _snowmanx, "minecraft:villager_golem");
      method_5232(_snowman, _snowmanx, "minecraft:vindication_illager");
      method_5232(_snowman, _snowmanx, "minecraft:witch");
      method_5232(_snowman, _snowmanx, "minecraft:wither");
      method_5232(_snowman, _snowmanx, "minecraft:wither_skeleton");
      _snowman.registerSimple(_snowmanx, "minecraft:wither_skull");
      method_5232(_snowman, _snowmanx, "minecraft:wolf");
      _snowman.registerSimple(_snowmanx, "minecraft:xp_bottle");
      _snowman.registerSimple(_snowmanx, "minecraft:xp_orb");
      method_5232(_snowman, _snowmanx, "minecraft:zombie");
      _snowman.register(_snowmanx, "minecraft:zombie_horse", _snowmanxx -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      method_5232(_snowman, _snowmanx, "minecraft:zombie_pigman");
      method_5232(_snowman, _snowmanx, "minecraft:zombie_villager");
      return _snowmanx;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      method_5273(_snowman, _snowmanx, "minecraft:furnace");
      method_5273(_snowman, _snowmanx, "minecraft:chest");
      method_5273(_snowman, _snowmanx, "minecraft:trapped_chest");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_chest");
      _snowman.register(_snowmanx, "minecraft:jukebox", _snowmanxx -> DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5273(_snowman, _snowmanx, "minecraft:dispenser");
      method_5273(_snowman, _snowmanx, "minecraft:dropper");
      _snowman.registerSimple(_snowmanx, "minecraft:sign");
      _snowman.register(_snowmanx, "minecraft:mob_spawner", _snowmanxx -> TypeReferences.UNTAGGED_SPAWNER.in(_snowman));
      _snowman.register(_snowmanx, "minecraft:piston", _snowmanxx -> DSL.optionalFields("blockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      method_5273(_snowman, _snowmanx, "minecraft:brewing_stand");
      _snowman.registerSimple(_snowmanx, "minecraft:enchanting_table");
      _snowman.registerSimple(_snowmanx, "minecraft:end_portal");
      _snowman.registerSimple(_snowmanx, "minecraft:beacon");
      _snowman.registerSimple(_snowmanx, "minecraft:skull");
      _snowman.registerSimple(_snowmanx, "minecraft:daylight_detector");
      method_5273(_snowman, _snowmanx, "minecraft:hopper");
      _snowman.registerSimple(_snowmanx, "minecraft:comparator");
      _snowman.registerSimple(_snowmanx, "minecraft:banner");
      _snowman.registerSimple(_snowmanx, "minecraft:structure_block");
      _snowman.registerSimple(_snowmanx, "minecraft:end_gateway");
      _snowman.registerSimple(_snowmanx, "minecraft:command_block");
      method_5273(_snowman, _snowmanx, "minecraft:shulker_box");
      _snowman.registerSimple(_snowmanx, "minecraft:bed");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      _snowman.registerType(false, TypeReferences.LEVEL, DSL::remainder);
      _snowman.registerType(false, TypeReferences.RECIPE, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(
         false,
         TypeReferences.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", TypeReferences.ENTITY_TREE.in(_snowman)),
               "Inventory",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "EnderItems",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  TypeReferences.ENTITY_TREE.in(_snowman),
                  "ShoulderEntityRight",
                  TypeReferences.ENTITY_TREE.in(_snowman),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(TypeReferences.RECIPE.in(_snowman)), "toBeDisplayed", DSL.list(TypeReferences.RECIPE.in(_snowman)))
               )
            )
      );
      _snowman.registerType(
         false,
         TypeReferences.CHUNK,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(TypeReferences.ENTITY_TREE.in(_snowman)),
                  "TileEntities",
                  DSL.list(TypeReferences.BLOCK_ENTITY.in(_snowman)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(_snowman))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.BLOCK_STATE.in(_snowman))))
               )
            )
      );
      _snowman.registerType(true, TypeReferences.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", getIdentifierType(), blockEntityTypes));
      _snowman.registerType(
         true, TypeReferences.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(TypeReferences.ENTITY_TREE.in(_snowman)), TypeReferences.ENTITY.in(_snowman))
      );
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
               Schema705.field_5746,
               HookFunction.IDENTITY
            )
      );
      _snowman.registerType(false, TypeReferences.HOTBAR, () -> DSL.compoundList(DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
      _snowman.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
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
      _snowman.registerType(false, TypeReferences.BLOCK_NAME, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(false, TypeReferences.ITEM_NAME, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
      Supplier<TypeTemplate> _snowmanx = () -> DSL.compoundList(TypeReferences.ITEM_NAME.in(_snowman), DSL.constType(DSL.intType()));
      _snowman.registerType(
         false,
         TypeReferences.STATS,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(TypeReferences.BLOCK_NAME.in(_snowman), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  _snowman.get(),
                  "minecraft:used",
                  _snowman.get(),
                  "minecraft:broken",
                  _snowman.get(),
                  "minecraft:picked_up",
                  _snowman.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     _snowman.get(),
                     "minecraft:killed",
                     DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(getIdentifierType()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      _snowman.registerType(
         false,
         TypeReferences.SAVED_DATA,
         () -> DSL.optionalFields(
               "data",
               DSL.optionalFields(
                  "Features",
                  DSL.compoundList(TypeReferences.STRUCTURE_FEATURE.in(_snowman)),
                  "Objectives",
                  DSL.list(TypeReferences.OBJECTIVE.in(_snowman)),
                  "Teams",
                  DSL.list(TypeReferences.TEAM.in(_snowman))
               )
            )
      );
      _snowman.registerType(
         false,
         TypeReferences.STRUCTURE_FEATURE,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CB",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CC",
                     TypeReferences.BLOCK_STATE.in(_snowman),
                     "CD",
                     TypeReferences.BLOCK_STATE.in(_snowman)
                  )
               )
            )
      );
      _snowman.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
      _snowman.registerType(false, TypeReferences.TEAM, DSL::remainder);
      _snowman.registerType(
         true,
         TypeReferences.UNTAGGED_SPAWNER,
         () -> DSL.optionalFields(
               "SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.ENTITY_TREE.in(_snowman))), "SpawnData", TypeReferences.ENTITY_TREE.in(_snowman)
            )
      );
      _snowman.registerType(
         false,
         TypeReferences.ADVANCEMENTS,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.BIOME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string())))
            )
      );
      _snowman.registerType(false, TypeReferences.BIOME, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(false, TypeReferences.ENTITY_NAME, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
      _snowman.registerType(true, TypeReferences.CHUNK_GENERATOR_SETTINGS, DSL::remainder);
   }
}
