/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Schema99
extends Schema {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, String> field_5748 = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        hashMap.put("minecraft:furnace", "Furnace");
        hashMap.put("minecraft:lit_furnace", "Furnace");
        hashMap.put("minecraft:chest", "Chest");
        hashMap.put("minecraft:trapped_chest", "Chest");
        hashMap.put("minecraft:ender_chest", "EnderChest");
        hashMap.put("minecraft:jukebox", "RecordPlayer");
        hashMap.put("minecraft:dispenser", "Trap");
        hashMap.put("minecraft:dropper", "Dropper");
        hashMap.put("minecraft:sign", "Sign");
        hashMap.put("minecraft:mob_spawner", "MobSpawner");
        hashMap.put("minecraft:noteblock", "Music");
        hashMap.put("minecraft:brewing_stand", "Cauldron");
        hashMap.put("minecraft:enhanting_table", "EnchantTable");
        hashMap.put("minecraft:command_block", "CommandBlock");
        hashMap.put("minecraft:beacon", "Beacon");
        hashMap.put("minecraft:skull", "Skull");
        hashMap.put("minecraft:daylight_detector", "DLDetector");
        hashMap.put("minecraft:hopper", "Hopper");
        hashMap.put("minecraft:banner", "Banner");
        hashMap.put("minecraft:flower_pot", "FlowerPot");
        hashMap.put("minecraft:repeating_command_block", "CommandBlock");
        hashMap.put("minecraft:chain_command_block", "CommandBlock");
        hashMap.put("minecraft:standing_sign", "Sign");
        hashMap.put("minecraft:wall_sign", "Sign");
        hashMap.put("minecraft:piston_head", "Piston");
        hashMap.put("minecraft:daylight_detector_inverted", "DLDetector");
        hashMap.put("minecraft:unpowered_comparator", "Comparator");
        hashMap.put("minecraft:powered_comparator", "Comparator");
        hashMap.put("minecraft:wall_banner", "Banner");
        hashMap.put("minecraft:standing_banner", "Banner");
        hashMap.put("minecraft:structure_block", "Structure");
        hashMap.put("minecraft:end_portal", "Airportal");
        hashMap.put("minecraft:end_gateway", "EndGateway");
        hashMap.put("minecraft:shield", "Banner");
    });
    protected static final Hook.HookFunction field_5747 = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return Schema99.method_5359(new Dynamic(dynamicOps, t), Schema99.method_5380(), "ArmorStand");
        }
    };

    public Schema99(int versionKey, Schema parent) {
        super(versionKey, parent);
    }

    protected static TypeTemplate targetEquipment(Schema schema) {
        return DSL.optionalFields((String)"Equipment", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
    }

    protected static void method_5339(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> Schema99.targetEquipment(schema));
    }

    protected static void method_5368(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
    }

    protected static void method_5377(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
    }

    protected static void method_5346(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap hashMap = Maps.newHashMap();
        schema.register((Map)hashMap, "Item", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)hashMap, "XPOrb");
        Schema99.method_5368(schema, hashMap, "ThrownEgg");
        schema.registerSimple((Map)hashMap, "LeashKnot");
        schema.registerSimple((Map)hashMap, "Painting");
        schema.register((Map)hashMap, "Arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        schema.register((Map)hashMap, "TippedArrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        schema.register((Map)hashMap, "SpectralArrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema99.method_5368(schema, hashMap, "Snowball");
        Schema99.method_5368(schema, hashMap, "Fireball");
        Schema99.method_5368(schema, hashMap, "SmallFireball");
        Schema99.method_5368(schema, hashMap, "ThrownEnderpearl");
        schema.registerSimple((Map)hashMap, "EyeOfEnderSignal");
        schema.register((Map)hashMap, "ThrownPotion", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Potion", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        Schema99.method_5368(schema, hashMap, "ThrownExpBottle");
        schema.register((Map)hashMap, "ItemFrame", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        Schema99.method_5368(schema, hashMap, "WitherSkull");
        schema.registerSimple((Map)hashMap, "PrimedTnt");
        schema.register((Map)hashMap, "FallingSand", string -> DSL.optionalFields((String)"Block", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"TileEntityData", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema)));
        schema.register((Map)hashMap, "FireworksRocketEntity", string -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)hashMap, "Boat");
        schema.register((Map)hashMap, "Minecart", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        Schema99.method_5377(schema, hashMap, "MinecartRideable");
        schema.register((Map)hashMap, "MinecartChest", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        Schema99.method_5377(schema, hashMap, "MinecartFurnace");
        Schema99.method_5377(schema, hashMap, "MinecartTNT");
        schema.register((Map)hashMap, "MinecartSpawner", () -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (TypeTemplate)TypeReferences.UNTAGGED_SPAWNER.in(schema)));
        schema.register((Map)hashMap, "MinecartHopper", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        Schema99.method_5377(schema, hashMap, "MinecartCommandBlock");
        Schema99.method_5339(schema, hashMap, "ArmorStand");
        Schema99.method_5339(schema, hashMap, "Creeper");
        Schema99.method_5339(schema, hashMap, "Skeleton");
        Schema99.method_5339(schema, hashMap, "Spider");
        Schema99.method_5339(schema, hashMap, "Giant");
        Schema99.method_5339(schema, hashMap, "Zombie");
        Schema99.method_5339(schema, hashMap, "Slime");
        Schema99.method_5339(schema, hashMap, "Ghast");
        Schema99.method_5339(schema, hashMap, "PigZombie");
        schema.register((Map)hashMap, "Enderman", string -> DSL.optionalFields((String)"carried", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (TypeTemplate)Schema99.targetEquipment(schema)));
        Schema99.method_5339(schema, hashMap, "CaveSpider");
        Schema99.method_5339(schema, hashMap, "Silverfish");
        Schema99.method_5339(schema, hashMap, "Blaze");
        Schema99.method_5339(schema, hashMap, "LavaSlime");
        Schema99.method_5339(schema, hashMap, "EnderDragon");
        Schema99.method_5339(schema, hashMap, "WitherBoss");
        Schema99.method_5339(schema, hashMap, "Bat");
        Schema99.method_5339(schema, hashMap, "Witch");
        Schema99.method_5339(schema, hashMap, "Endermite");
        Schema99.method_5339(schema, hashMap, "Guardian");
        Schema99.method_5339(schema, hashMap, "Pig");
        Schema99.method_5339(schema, hashMap, "Sheep");
        Schema99.method_5339(schema, hashMap, "Cow");
        Schema99.method_5339(schema, hashMap, "Chicken");
        Schema99.method_5339(schema, hashMap, "Squid");
        Schema99.method_5339(schema, hashMap, "Wolf");
        Schema99.method_5339(schema, hashMap, "MushroomCow");
        Schema99.method_5339(schema, hashMap, "SnowMan");
        Schema99.method_5339(schema, hashMap, "Ozelot");
        Schema99.method_5339(schema, hashMap, "VillagerGolem");
        schema.register((Map)hashMap, "EntityHorse", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"ArmorItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema99.targetEquipment(schema)));
        Schema99.method_5339(schema, hashMap, "Rabbit");
        schema.register((Map)hashMap, "Villager", string -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"buyB", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"sell", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)))), (TypeTemplate)Schema99.targetEquipment(schema)));
        schema.registerSimple((Map)hashMap, "EnderCrystal");
        schema.registerSimple((Map)hashMap, "AreaEffectCloud");
        schema.registerSimple((Map)hashMap, "ShulkerBullet");
        Schema99.method_5339(schema, hashMap, "Shulker");
        return hashMap;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap hashMap = Maps.newHashMap();
        Schema99.method_5346(schema, hashMap, "Furnace");
        Schema99.method_5346(schema, hashMap, "Chest");
        schema.registerSimple((Map)hashMap, "EnderChest");
        schema.register((Map)hashMap, "RecordPlayer", string -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        Schema99.method_5346(schema, hashMap, "Trap");
        Schema99.method_5346(schema, hashMap, "Dropper");
        schema.registerSimple((Map)hashMap, "Sign");
        schema.register((Map)hashMap, "MobSpawner", string -> TypeReferences.UNTAGGED_SPAWNER.in(schema));
        schema.registerSimple((Map)hashMap, "Music");
        schema.registerSimple((Map)hashMap, "Piston");
        Schema99.method_5346(schema, hashMap, "Cauldron");
        schema.registerSimple((Map)hashMap, "EnchantTable");
        schema.registerSimple((Map)hashMap, "Airportal");
        schema.registerSimple((Map)hashMap, "Control");
        schema.registerSimple((Map)hashMap, "Beacon");
        schema.registerSimple((Map)hashMap, "Skull");
        schema.registerSimple((Map)hashMap, "DLDetector");
        Schema99.method_5346(schema, hashMap, "Hopper");
        schema.registerSimple((Map)hashMap, "Comparator");
        schema.register((Map)hashMap, "FlowerPot", string -> DSL.optionalFields((String)"Item", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)TypeReferences.ITEM_NAME.in(schema))));
        schema.registerSimple((Map)hashMap, "Banner");
        schema.registerSimple((Map)hashMap, "Structure");
        schema.registerSimple((Map)hashMap, "EndGateway");
        return hashMap;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        schema.registerType(false, TypeReferences.LEVEL, DSL::remainder);
        schema.registerType(false, TypeReferences.PLAYER, () -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        schema.registerType(false, TypeReferences.CHUNK, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ENTITY_TREE.in(schema)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema)), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema))))));
        schema.registerType(true, TypeReferences.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)blockEntityTypes));
        schema.registerType(true, TypeReferences.ENTITY_TREE, () -> DSL.optionalFields((String)"Riding", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (TypeTemplate)TypeReferences.ENTITY.in(schema)));
        schema.registerType(false, TypeReferences.ENTITY_NAME, () -> DSL.constType(IdentifierNormalizingSchema.getIdentifierType()));
        schema.registerType(true, TypeReferences.ENTITY, () -> DSL.taggedChoiceLazy((String)"id", (Type)DSL.string(), (Map)entityTypes));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)TypeReferences.ITEM_NAME.in(schema)), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (String)"BlockEntityTag", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)))), (Hook.HookFunction)field_5747, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
        schema.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
        schema.registerType(false, TypeReferences.BLOCK_NAME, () -> DSL.or((TypeTemplate)DSL.constType((Type)DSL.intType()), (TypeTemplate)DSL.constType(IdentifierNormalizingSchema.getIdentifierType())));
        schema.registerType(false, TypeReferences.ITEM_NAME, () -> DSL.constType(IdentifierNormalizingSchema.getIdentifierType()));
        schema.registerType(false, TypeReferences.STATS, DSL::remainder);
        schema.registerType(false, TypeReferences.SAVED_DATA, () -> DSL.optionalFields((String)"data", (TypeTemplate)DSL.optionalFields((String)"Features", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.STRUCTURE_FEATURE.in(schema)), (String)"Objectives", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.OBJECTIVE.in(schema)), (String)"Teams", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.TEAM.in(schema)))));
        schema.registerType(false, TypeReferences.STRUCTURE_FEATURE, DSL::remainder);
        schema.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
        schema.registerType(false, TypeReferences.TEAM, DSL::remainder);
        schema.registerType(true, TypeReferences.UNTAGGED_SPAWNER, DSL::remainder);
        schema.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
        schema.registerType(true, TypeReferences.CHUNK_GENERATOR_SETTINGS, DSL::remainder);
    }

    protected static <T> T method_5359(Dynamic<T> dynamic, Map<String, String> map, String string) {
        return (T)dynamic.update("tag", dynamic3 -> dynamic3.update("BlockEntityTag", dynamic2 -> {
            String string = dynamic.get("id").asString("");
            _snowman = (String)map.get(IdentifierNormalizingSchema.normalize(string));
            if (_snowman != null) {
                return dynamic2.set("id", dynamic.createString(_snowman));
            }
            LOGGER.warn("Unable to resolve BlockEntity for ItemStack: {}", (Object)string);
            return dynamic2;
        }).update("EntityTag", dynamic2 -> {
            String string2 = dynamic.get("id").asString("");
            if (Objects.equals(IdentifierNormalizingSchema.normalize(string2), "minecraft:armor_stand")) {
                return dynamic2.set("id", dynamic.createString(string));
            }
            return dynamic2;
        })).getValue();
    }

    static /* synthetic */ Map method_5380() {
        return field_5748;
    }
}

