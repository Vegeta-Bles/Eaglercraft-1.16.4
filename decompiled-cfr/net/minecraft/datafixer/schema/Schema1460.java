/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.datafixer.schema.Schema100;
import net.minecraft.datafixer.schema.Schema705;

public class Schema1460
extends IdentifierNormalizingSchema {
    public Schema1460(int n, Schema schema) {
        super(n, schema);
    }

    protected static void method_5232(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> Schema100.targetItems(schema));
    }

    protected static void method_5273(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap hashMap = Maps.newHashMap();
        schema.registerSimple((Map)hashMap, "minecraft:area_effect_cloud");
        Schema1460.method_5232(schema, hashMap, "minecraft:armor_stand");
        schema.register((Map)hashMap, "minecraft:arrow", string -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:bat");
        Schema1460.method_5232(schema, hashMap, "minecraft:blaze");
        schema.registerSimple((Map)hashMap, "minecraft:boat");
        Schema1460.method_5232(schema, hashMap, "minecraft:cave_spider");
        schema.register((Map)hashMap, "minecraft:chest_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        Schema1460.method_5232(schema, hashMap, "minecraft:chicken");
        schema.register((Map)hashMap, "minecraft:commandblock_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:cow");
        Schema1460.method_5232(schema, hashMap, "minecraft:creeper");
        schema.register((Map)hashMap, "minecraft:donkey", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:dragon_fireball");
        schema.registerSimple((Map)hashMap, "minecraft:egg");
        Schema1460.method_5232(schema, hashMap, "minecraft:elder_guardian");
        schema.registerSimple((Map)hashMap, "minecraft:ender_crystal");
        Schema1460.method_5232(schema, hashMap, "minecraft:ender_dragon");
        schema.register((Map)hashMap, "minecraft:enderman", string -> DSL.optionalFields((String)"carriedBlockState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:endermite");
        schema.registerSimple((Map)hashMap, "minecraft:ender_pearl");
        schema.registerSimple((Map)hashMap, "minecraft:evocation_fangs");
        Schema1460.method_5232(schema, hashMap, "minecraft:evocation_illager");
        schema.registerSimple((Map)hashMap, "minecraft:eye_of_ender_signal");
        schema.register((Map)hashMap, "minecraft:falling_block", string -> DSL.optionalFields((String)"BlockState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"TileEntityData", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:fireball");
        schema.register((Map)hashMap, "minecraft:fireworks_rocket", string -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.register((Map)hashMap, "minecraft:furnace_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:ghast");
        Schema1460.method_5232(schema, hashMap, "minecraft:giant");
        Schema1460.method_5232(schema, hashMap, "minecraft:guardian");
        schema.register((Map)hashMap, "minecraft:hopper_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        schema.register((Map)hashMap, "minecraft:horse", string -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:husk");
        schema.registerSimple((Map)hashMap, "minecraft:illusion_illager");
        schema.register((Map)hashMap, "minecraft:item", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.register((Map)hashMap, "minecraft:item_frame", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:leash_knot");
        schema.register((Map)hashMap, "minecraft:llama", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"DecorItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:llama_spit");
        Schema1460.method_5232(schema, hashMap, "minecraft:magma_cube");
        schema.register((Map)hashMap, "minecraft:minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:mooshroom");
        schema.register((Map)hashMap, "minecraft:mule", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:ocelot");
        schema.registerSimple((Map)hashMap, "minecraft:painting");
        schema.registerSimple((Map)hashMap, "minecraft:parrot");
        Schema1460.method_5232(schema, hashMap, "minecraft:pig");
        Schema1460.method_5232(schema, hashMap, "minecraft:polar_bear");
        schema.register((Map)hashMap, "minecraft:potion", string -> DSL.optionalFields((String)"Potion", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:rabbit");
        Schema1460.method_5232(schema, hashMap, "minecraft:sheep");
        Schema1460.method_5232(schema, hashMap, "minecraft:shulker");
        schema.registerSimple((Map)hashMap, "minecraft:shulker_bullet");
        Schema1460.method_5232(schema, hashMap, "minecraft:silverfish");
        Schema1460.method_5232(schema, hashMap, "minecraft:skeleton");
        schema.register((Map)hashMap, "minecraft:skeleton_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:slime");
        schema.registerSimple((Map)hashMap, "minecraft:small_fireball");
        schema.registerSimple((Map)hashMap, "minecraft:snowball");
        Schema1460.method_5232(schema, hashMap, "minecraft:snowman");
        schema.register((Map)hashMap, "minecraft:spawner_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (TypeTemplate)TypeReferences.UNTAGGED_SPAWNER.in(schema)));
        schema.register((Map)hashMap, "minecraft:spectral_arrow", string -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:spider");
        Schema1460.method_5232(schema, hashMap, "minecraft:squid");
        Schema1460.method_5232(schema, hashMap, "minecraft:stray");
        schema.registerSimple((Map)hashMap, "minecraft:tnt");
        schema.register((Map)hashMap, "minecraft:tnt_minecart", string -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:vex");
        schema.register((Map)hashMap, "minecraft:villager", string -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"buyB", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"sell", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)))), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:villager_golem");
        Schema1460.method_5232(schema, hashMap, "minecraft:vindication_illager");
        Schema1460.method_5232(schema, hashMap, "minecraft:witch");
        Schema1460.method_5232(schema, hashMap, "minecraft:wither");
        Schema1460.method_5232(schema, hashMap, "minecraft:wither_skeleton");
        schema.registerSimple((Map)hashMap, "minecraft:wither_skull");
        Schema1460.method_5232(schema, hashMap, "minecraft:wolf");
        schema.registerSimple((Map)hashMap, "minecraft:xp_bottle");
        schema.registerSimple((Map)hashMap, "minecraft:xp_orb");
        Schema1460.method_5232(schema, hashMap, "minecraft:zombie");
        schema.register((Map)hashMap, "minecraft:zombie_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema1460.method_5232(schema, hashMap, "minecraft:zombie_pigman");
        Schema1460.method_5232(schema, hashMap, "minecraft:zombie_villager");
        return hashMap;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap hashMap = Maps.newHashMap();
        Schema1460.method_5273(schema, hashMap, "minecraft:furnace");
        Schema1460.method_5273(schema, hashMap, "minecraft:chest");
        Schema1460.method_5273(schema, hashMap, "minecraft:trapped_chest");
        schema.registerSimple((Map)hashMap, "minecraft:ender_chest");
        schema.register((Map)hashMap, "minecraft:jukebox", string -> DSL.optionalFields((String)"RecordItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        Schema1460.method_5273(schema, hashMap, "minecraft:dispenser");
        Schema1460.method_5273(schema, hashMap, "minecraft:dropper");
        schema.registerSimple((Map)hashMap, "minecraft:sign");
        schema.register((Map)hashMap, "minecraft:mob_spawner", string -> TypeReferences.UNTAGGED_SPAWNER.in(schema));
        schema.register((Map)hashMap, "minecraft:piston", string -> DSL.optionalFields((String)"blockState", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)));
        Schema1460.method_5273(schema, hashMap, "minecraft:brewing_stand");
        schema.registerSimple((Map)hashMap, "minecraft:enchanting_table");
        schema.registerSimple((Map)hashMap, "minecraft:end_portal");
        schema.registerSimple((Map)hashMap, "minecraft:beacon");
        schema.registerSimple((Map)hashMap, "minecraft:skull");
        schema.registerSimple((Map)hashMap, "minecraft:daylight_detector");
        Schema1460.method_5273(schema, hashMap, "minecraft:hopper");
        schema.registerSimple((Map)hashMap, "minecraft:comparator");
        schema.registerSimple((Map)hashMap, "minecraft:banner");
        schema.registerSimple((Map)hashMap, "minecraft:structure_block");
        schema.registerSimple((Map)hashMap, "minecraft:end_gateway");
        schema.registerSimple((Map)hashMap, "minecraft:command_block");
        Schema1460.method_5273(schema, hashMap, "minecraft:shulker_box");
        schema.registerSimple((Map)hashMap, "minecraft:bed");
        return hashMap;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        schema.registerType(false, TypeReferences.LEVEL, DSL::remainder);
        schema.registerType(false, TypeReferences.RECIPE, () -> DSL.constType(Schema1460.getIdentifierType()));
        schema.registerType(false, TypeReferences.PLAYER, () -> DSL.optionalFields((String)"RootVehicle", (TypeTemplate)DSL.optionalFields((String)"Entity", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema)), (String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"EnderItems", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (TypeTemplate)DSL.optionalFields((String)"ShoulderEntityLeft", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (String)"ShoulderEntityRight", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (String)"recipeBook", (TypeTemplate)DSL.optionalFields((String)"recipes", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.RECIPE.in(schema)), (String)"toBeDisplayed", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.RECIPE.in(schema))))));
        schema.registerType(false, TypeReferences.CHUNK, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ENTITY_TREE.in(schema)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema)), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema))), (String)"Sections", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"Palette", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)))))));
        schema.registerType(true, TypeReferences.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy((String)"id", Schema1460.getIdentifierType(), (Map)blockEntityTypes));
        schema.registerType(true, TypeReferences.ENTITY_TREE, () -> DSL.optionalFields((String)"Passengers", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ENTITY_TREE.in(schema)), (TypeTemplate)TypeReferences.ENTITY.in(schema)));
        schema.registerType(true, TypeReferences.ENTITY, () -> DSL.taggedChoiceLazy((String)"id", Schema1460.getIdentifierType(), (Map)entityTypes));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)TypeReferences.ITEM_NAME.in(schema), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (String)"BlockEntityTag", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)))), (Hook.HookFunction)Schema705.field_5746, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
        schema.registerType(false, TypeReferences.HOTBAR, () -> DSL.compoundList((TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        schema.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
        schema.registerType(false, TypeReferences.STRUCTURE, () -> DSL.optionalFields((String)"entities", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema))), (String)"blocks", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"nbt", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema))), (String)"palette", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_STATE.in(schema))));
        schema.registerType(false, TypeReferences.BLOCK_NAME, () -> DSL.constType(Schema1460.getIdentifierType()));
        schema.registerType(false, TypeReferences.ITEM_NAME, () -> DSL.constType(Schema1460.getIdentifierType()));
        schema.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
        Supplier<TypeTemplate> supplier = () -> DSL.compoundList((TypeTemplate)TypeReferences.ITEM_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.intType()));
        schema.registerType(false, TypeReferences.STATS, () -> DSL.optionalFields((String)"stats", (TypeTemplate)DSL.optionalFields((String)"minecraft:mined", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:crafted", (TypeTemplate)((TypeTemplate)supplier.get()), (String)"minecraft:used", (TypeTemplate)((TypeTemplate)supplier.get()), (String)"minecraft:broken", (TypeTemplate)((TypeTemplate)supplier.get()), (String)"minecraft:picked_up", (TypeTemplate)((TypeTemplate)supplier.get()), (TypeTemplate)DSL.optionalFields((String)"minecraft:dropped", (TypeTemplate)((TypeTemplate)supplier.get()), (String)"minecraft:killed", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.ENTITY_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:killed_by", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.ENTITY_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.intType())), (String)"minecraft:custom", (TypeTemplate)DSL.compoundList((TypeTemplate)DSL.constType(Schema1460.getIdentifierType()), (TypeTemplate)DSL.constType((Type)DSL.intType()))))));
        schema.registerType(false, TypeReferences.SAVED_DATA, () -> DSL.optionalFields((String)"data", (TypeTemplate)DSL.optionalFields((String)"Features", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.STRUCTURE_FEATURE.in(schema)), (String)"Objectives", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.OBJECTIVE.in(schema)), (String)"Teams", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.TEAM.in(schema)))));
        schema.registerType(false, TypeReferences.STRUCTURE_FEATURE, () -> DSL.optionalFields((String)"Children", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"CA", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"CB", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"CC", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema), (String)"CD", (TypeTemplate)TypeReferences.BLOCK_STATE.in(schema)))));
        schema.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
        schema.registerType(false, TypeReferences.TEAM, DSL::remainder);
        schema.registerType(true, TypeReferences.UNTAGGED_SPAWNER, () -> DSL.optionalFields((String)"SpawnPotentials", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"Entity", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema))), (String)"SpawnData", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema)));
        schema.registerType(false, TypeReferences.ADVANCEMENTS, () -> DSL.optionalFields((String)"minecraft:adventure/adventuring_time", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.BIOME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_a_mob", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.ENTITY_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:adventure/kill_all_mobs", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.ENTITY_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.string()))), (String)"minecraft:husbandry/bred_all_animals", (TypeTemplate)DSL.optionalFields((String)"criteria", (TypeTemplate)DSL.compoundList((TypeTemplate)TypeReferences.ENTITY_NAME.in(schema), (TypeTemplate)DSL.constType((Type)DSL.string())))));
        schema.registerType(false, TypeReferences.BIOME, () -> DSL.constType(Schema1460.getIdentifierType()));
        schema.registerType(false, TypeReferences.ENTITY_NAME, () -> DSL.constType(Schema1460.getIdentifierType()));
        schema.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
        schema.registerType(true, TypeReferences.CHUNK_GENERATOR_SETTINGS, DSL::remainder);
    }
}

