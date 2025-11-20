package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Schema99 extends Schema {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<String, String> field_5748 = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:furnace", "Furnace");
      _snowman.put("minecraft:lit_furnace", "Furnace");
      _snowman.put("minecraft:chest", "Chest");
      _snowman.put("minecraft:trapped_chest", "Chest");
      _snowman.put("minecraft:ender_chest", "EnderChest");
      _snowman.put("minecraft:jukebox", "RecordPlayer");
      _snowman.put("minecraft:dispenser", "Trap");
      _snowman.put("minecraft:dropper", "Dropper");
      _snowman.put("minecraft:sign", "Sign");
      _snowman.put("minecraft:mob_spawner", "MobSpawner");
      _snowman.put("minecraft:noteblock", "Music");
      _snowman.put("minecraft:brewing_stand", "Cauldron");
      _snowman.put("minecraft:enhanting_table", "EnchantTable");
      _snowman.put("minecraft:command_block", "CommandBlock");
      _snowman.put("minecraft:beacon", "Beacon");
      _snowman.put("minecraft:skull", "Skull");
      _snowman.put("minecraft:daylight_detector", "DLDetector");
      _snowman.put("minecraft:hopper", "Hopper");
      _snowman.put("minecraft:banner", "Banner");
      _snowman.put("minecraft:flower_pot", "FlowerPot");
      _snowman.put("minecraft:repeating_command_block", "CommandBlock");
      _snowman.put("minecraft:chain_command_block", "CommandBlock");
      _snowman.put("minecraft:standing_sign", "Sign");
      _snowman.put("minecraft:wall_sign", "Sign");
      _snowman.put("minecraft:piston_head", "Piston");
      _snowman.put("minecraft:daylight_detector_inverted", "DLDetector");
      _snowman.put("minecraft:unpowered_comparator", "Comparator");
      _snowman.put("minecraft:powered_comparator", "Comparator");
      _snowman.put("minecraft:wall_banner", "Banner");
      _snowman.put("minecraft:standing_banner", "Banner");
      _snowman.put("minecraft:structure_block", "Structure");
      _snowman.put("minecraft:end_portal", "Airportal");
      _snowman.put("minecraft:end_gateway", "EndGateway");
      _snowman.put("minecraft:shield", "Banner");
   });
   protected static final HookFunction field_5747 = new HookFunction() {
      public <T> T apply(DynamicOps<T> _snowman, T _snowman) {
         return Schema99.method_5359(new Dynamic(_snowman, _snowman), Schema99.field_5748, "ArmorStand");
      }
   };

   public Schema99(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   protected static TypeTemplate targetEquipment(Schema _snowman) {
      return DSL.optionalFields("Equipment", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)));
   }

   protected static void method_5339(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> targetEquipment(_snowman));
   }

   protected static void method_5368(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
   }

   protected static void method_5377(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman)));
   }

   protected static void method_5346(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      _snowman.register(_snowmanx, "Item", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "XPOrb");
      method_5368(_snowman, _snowmanx, "ThrownEgg");
      _snowman.registerSimple(_snowmanx, "LeashKnot");
      _snowman.registerSimple(_snowmanx, "Painting");
      _snowman.register(_snowmanx, "Arrow", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      _snowman.register(_snowmanx, "TippedArrow", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      _snowman.register(_snowmanx, "SpectralArrow", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman)));
      method_5368(_snowman, _snowmanx, "Snowball");
      method_5368(_snowman, _snowmanx, "Fireball");
      method_5368(_snowman, _snowmanx, "SmallFireball");
      method_5368(_snowman, _snowmanx, "ThrownEnderpearl");
      _snowman.registerSimple(_snowmanx, "EyeOfEnderSignal");
      _snowman.register(_snowmanx, "ThrownPotion", _snowmanxx -> DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(_snowman), "Potion", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5368(_snowman, _snowmanx, "ThrownExpBottle");
      _snowman.register(_snowmanx, "ItemFrame", _snowmanxx -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5368(_snowman, _snowmanx, "WitherSkull");
      _snowman.registerSimple(_snowmanx, "PrimedTnt");
      _snowman.register(_snowmanx, "FallingSand", _snowmanxx -> DSL.optionalFields("Block", TypeReferences.BLOCK_NAME.in(_snowman), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(_snowman)));
      _snowman.register(_snowmanx, "FireworksRocketEntity", _snowmanxx -> DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "Boat");
      _snowman.register(_snowmanx, "Minecart", () -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
      method_5377(_snowman, _snowmanx, "MinecartRideable");
      _snowman.register(
         _snowmanx, "MinecartChest", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      method_5377(_snowman, _snowmanx, "MinecartFurnace");
      method_5377(_snowman, _snowmanx, "MinecartTNT");
      _snowman.register(_snowmanx, "MinecartSpawner", () -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), TypeReferences.UNTAGGED_SPAWNER.in(_snowman)));
      _snowman.register(
         _snowmanx, "MinecartHopper", _snowmanxx -> DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      method_5377(_snowman, _snowmanx, "MinecartCommandBlock");
      method_5339(_snowman, _snowmanx, "ArmorStand");
      method_5339(_snowman, _snowmanx, "Creeper");
      method_5339(_snowman, _snowmanx, "Skeleton");
      method_5339(_snowman, _snowmanx, "Spider");
      method_5339(_snowman, _snowmanx, "Giant");
      method_5339(_snowman, _snowmanx, "Zombie");
      method_5339(_snowman, _snowmanx, "Slime");
      method_5339(_snowman, _snowmanx, "Ghast");
      method_5339(_snowman, _snowmanx, "PigZombie");
      _snowman.register(_snowmanx, "Enderman", _snowmanxx -> DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(_snowman), targetEquipment(_snowman)));
      method_5339(_snowman, _snowmanx, "CaveSpider");
      method_5339(_snowman, _snowmanx, "Silverfish");
      method_5339(_snowman, _snowmanx, "Blaze");
      method_5339(_snowman, _snowmanx, "LavaSlime");
      method_5339(_snowman, _snowmanx, "EnderDragon");
      method_5339(_snowman, _snowmanx, "WitherBoss");
      method_5339(_snowman, _snowmanx, "Bat");
      method_5339(_snowman, _snowmanx, "Witch");
      method_5339(_snowman, _snowmanx, "Endermite");
      method_5339(_snowman, _snowmanx, "Guardian");
      method_5339(_snowman, _snowmanx, "Pig");
      method_5339(_snowman, _snowmanx, "Sheep");
      method_5339(_snowman, _snowmanx, "Cow");
      method_5339(_snowman, _snowmanx, "Chicken");
      method_5339(_snowman, _snowmanx, "Squid");
      method_5339(_snowman, _snowmanx, "Wolf");
      method_5339(_snowman, _snowmanx, "MushroomCow");
      method_5339(_snowman, _snowmanx, "SnowMan");
      method_5339(_snowman, _snowmanx, "Ozelot");
      method_5339(_snowman, _snowmanx, "VillagerGolem");
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
               targetEquipment(_snowman)
            )
      );
      method_5339(_snowman, _snowmanx, "Rabbit");
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
               targetEquipment(_snowman)
            )
      );
      _snowman.registerSimple(_snowmanx, "EnderCrystal");
      _snowman.registerSimple(_snowmanx, "AreaEffectCloud");
      _snowman.registerSimple(_snowmanx, "ShulkerBullet");
      method_5339(_snowman, _snowmanx, "Shulker");
      return _snowmanx;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      method_5346(_snowman, _snowmanx, "Furnace");
      method_5346(_snowman, _snowmanx, "Chest");
      _snowman.registerSimple(_snowmanx, "EnderChest");
      _snowman.register(_snowmanx, "RecordPlayer", _snowmanxx -> DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5346(_snowman, _snowmanx, "Trap");
      method_5346(_snowman, _snowmanx, "Dropper");
      _snowman.registerSimple(_snowmanx, "Sign");
      _snowman.register(_snowmanx, "MobSpawner", _snowmanxx -> TypeReferences.UNTAGGED_SPAWNER.in(_snowman));
      _snowman.registerSimple(_snowmanx, "Music");
      _snowman.registerSimple(_snowmanx, "Piston");
      method_5346(_snowman, _snowmanx, "Cauldron");
      _snowman.registerSimple(_snowmanx, "EnchantTable");
      _snowman.registerSimple(_snowmanx, "Airportal");
      _snowman.registerSimple(_snowmanx, "Control");
      _snowman.registerSimple(_snowmanx, "Beacon");
      _snowman.registerSimple(_snowmanx, "Skull");
      _snowman.registerSimple(_snowmanx, "DLDetector");
      method_5346(_snowman, _snowmanx, "Hopper");
      _snowman.registerSimple(_snowmanx, "Comparator");
      _snowman.register(_snowmanx, "FlowerPot", _snowmanxx -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(_snowman))));
      _snowman.registerSimple(_snowmanx, "Banner");
      _snowman.registerSimple(_snowmanx, "Structure");
      _snowman.registerSimple(_snowmanx, "EndGateway");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      _snowman.registerType(false, TypeReferences.LEVEL, DSL::remainder);
      _snowman.registerType(
         false,
         TypeReferences.PLAYER,
         () -> DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
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
                  DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(_snowman)))
               )
            )
      );
      _snowman.registerType(true, TypeReferences.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), blockEntityTypes));
      _snowman.registerType(true, TypeReferences.ENTITY_TREE, () -> DSL.optionalFields("Riding", TypeReferences.ENTITY_TREE.in(_snowman), TypeReferences.ENTITY.in(_snowman)));
      _snowman.registerType(false, TypeReferences.ENTITY_NAME, () -> DSL.constType(IdentifierNormalizingSchema.getIdentifierType()));
      _snowman.registerType(true, TypeReferences.ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), entityTypes));
      _snowman.registerType(
         true,
         TypeReferences.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(_snowman)),
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
               field_5747,
               HookFunction.IDENTITY
            )
      );
      _snowman.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
      _snowman.registerType(
         false, TypeReferences.BLOCK_NAME, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(IdentifierNormalizingSchema.getIdentifierType()))
      );
      _snowman.registerType(false, TypeReferences.ITEM_NAME, () -> DSL.constType(IdentifierNormalizingSchema.getIdentifierType()));
      _snowman.registerType(false, TypeReferences.STATS, DSL::remainder);
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
      _snowman.registerType(false, TypeReferences.STRUCTURE_FEATURE, DSL::remainder);
      _snowman.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
      _snowman.registerType(false, TypeReferences.TEAM, DSL::remainder);
      _snowman.registerType(true, TypeReferences.UNTAGGED_SPAWNER, DSL::remainder);
      _snowman.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
      _snowman.registerType(true, TypeReferences.CHUNK_GENERATOR_SETTINGS, DSL::remainder);
   }

   protected static <T> T method_5359(Dynamic<T> _snowman, Map<String, String> _snowman, String _snowman) {
      return (T)_snowman.update("tag", _snowmanxxxx -> _snowmanxxxx.update("BlockEntityTag", _snowmanxxxxxxxxxx -> {
            String _snowmanxxx = _snowman.get("id").asString("");
            String _snowmanxxxx = _snowman.get(IdentifierNormalizingSchema.normalize(_snowmanxxx));
            if (_snowmanxxxx == null) {
               LOGGER.warn("Unable to resolve BlockEntity for ItemStack: {}", _snowmanxxx);
               return _snowmanxxxxxxxxxx;
            } else {
               return _snowmanxxxxxxxxxx.set("id", _snowman.createString(_snowmanxxxx));
            }
         }).update("EntityTag", _snowmanxxxxxxxxx -> {
            String _snowmanxxx = _snowman.get("id").asString("");
            return Objects.equals(IdentifierNormalizingSchema.normalize(_snowmanxxx), "minecraft:armor_stand") ? _snowmanxxxxxxxxx.set("id", _snowman.createString(_snowman)) : _snowmanxxxxxxxxx;
         })).getValue();
   }
}
