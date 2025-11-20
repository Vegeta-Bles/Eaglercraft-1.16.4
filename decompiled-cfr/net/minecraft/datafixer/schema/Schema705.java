/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.datafixer.schema.Schema100;
import net.minecraft.datafixer.schema.Schema704;
import net.minecraft.datafixer.schema.Schema99;

public class Schema705
extends IdentifierNormalizingSchema {
    protected static final Hook.HookFunction field_5746 = new Hook.HookFunction(){

        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return Schema99.method_5359(new Dynamic(dynamicOps, t), Schema704.BLOCK_RENAMES, "minecraft:armor_stand");
        }
    };

    public Schema705(int n, Schema schema) {
        super(n, schema);
    }

    protected static void method_5311(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> Schema100.targetItems(schema));
    }

    protected static void method_5330(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap hashMap = Maps.newHashMap();
        schema.registerSimple((Map)hashMap, "minecraft:area_effect_cloud");
        Schema705.method_5311(schema, hashMap, "minecraft:armor_stand");
        schema.register((Map)hashMap, "minecraft:arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:bat");
        Schema705.method_5311(schema, hashMap, "minecraft:blaze");
        schema.registerSimple((Map)hashMap, "minecraft:boat");
        Schema705.method_5311(schema, hashMap, "minecraft:cave_spider");
        schema.register((Map)hashMap, "minecraft:chest_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        Schema705.method_5311(schema, hashMap, "minecraft:chicken");
        schema.register((Map)hashMap, "minecraft:commandblock_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:cow");
        Schema705.method_5311(schema, hashMap, "minecraft:creeper");
        schema.register((Map)hashMap, "minecraft:donkey", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:dragon_fireball");
        Schema705.method_5330(schema, hashMap, "minecraft:egg");
        Schema705.method_5311(schema, hashMap, "minecraft:elder_guardian");
        schema.registerSimple((Map)hashMap, "minecraft:ender_crystal");
        Schema705.method_5311(schema, hashMap, "minecraft:ender_dragon");
        schema.register((Map)hashMap, "minecraft:enderman", string -> DSL.optionalFields((String)"carried", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:endermite");
        Schema705.method_5330(schema, hashMap, "minecraft:ender_pearl");
        schema.registerSimple((Map)hashMap, "minecraft:eye_of_ender_signal");
        schema.register((Map)hashMap, "minecraft:falling_block", string -> DSL.optionalFields((String)"Block", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"TileEntityData", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema)));
        Schema705.method_5330(schema, hashMap, "minecraft:fireball");
        schema.register((Map)hashMap, "minecraft:fireworks_rocket", string -> DSL.optionalFields((String)"FireworksItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.register((Map)hashMap, "minecraft:furnace_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:ghast");
        Schema705.method_5311(schema, hashMap, "minecraft:giant");
        Schema705.method_5311(schema, hashMap, "minecraft:guardian");
        schema.register((Map)hashMap, "minecraft:hopper_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema))));
        schema.register((Map)hashMap, "minecraft:horse", string -> DSL.optionalFields((String)"ArmorItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:husk");
        schema.register((Map)hashMap, "minecraft:item", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.register((Map)hashMap, "minecraft:item_frame", string -> DSL.optionalFields((String)"Item", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:leash_knot");
        Schema705.method_5311(schema, hashMap, "minecraft:magma_cube");
        schema.register((Map)hashMap, "minecraft:minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:mooshroom");
        schema.register((Map)hashMap, "minecraft:mule", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:ocelot");
        schema.registerSimple((Map)hashMap, "minecraft:painting");
        schema.registerSimple((Map)hashMap, "minecraft:parrot");
        Schema705.method_5311(schema, hashMap, "minecraft:pig");
        Schema705.method_5311(schema, hashMap, "minecraft:polar_bear");
        schema.register((Map)hashMap, "minecraft:potion", string -> DSL.optionalFields((String)"Potion", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:rabbit");
        Schema705.method_5311(schema, hashMap, "minecraft:sheep");
        Schema705.method_5311(schema, hashMap, "minecraft:shulker");
        schema.registerSimple((Map)hashMap, "minecraft:shulker_bullet");
        Schema705.method_5311(schema, hashMap, "minecraft:silverfish");
        Schema705.method_5311(schema, hashMap, "minecraft:skeleton");
        schema.register((Map)hashMap, "minecraft:skeleton_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:slime");
        Schema705.method_5330(schema, hashMap, "minecraft:small_fireball");
        Schema705.method_5330(schema, hashMap, "minecraft:snowball");
        Schema705.method_5311(schema, hashMap, "minecraft:snowman");
        schema.register((Map)hashMap, "minecraft:spawner_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema), (TypeTemplate)TypeReferences.UNTAGGED_SPAWNER.in(schema)));
        schema.register((Map)hashMap, "minecraft:spectral_arrow", string -> DSL.optionalFields((String)"inTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:spider");
        Schema705.method_5311(schema, hashMap, "minecraft:squid");
        Schema705.method_5311(schema, hashMap, "minecraft:stray");
        schema.registerSimple((Map)hashMap, "minecraft:tnt");
        schema.register((Map)hashMap, "minecraft:tnt_minecart", string -> DSL.optionalFields((String)"DisplayTile", (TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)));
        schema.register((Map)hashMap, "minecraft:villager", string -> DSL.optionalFields((String)"Inventory", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"Offers", (TypeTemplate)DSL.optionalFields((String)"Recipes", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"buy", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"buyB", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"sell", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema)))), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:villager_golem");
        Schema705.method_5311(schema, hashMap, "minecraft:witch");
        Schema705.method_5311(schema, hashMap, "minecraft:wither");
        Schema705.method_5311(schema, hashMap, "minecraft:wither_skeleton");
        Schema705.method_5330(schema, hashMap, "minecraft:wither_skull");
        Schema705.method_5311(schema, hashMap, "minecraft:wolf");
        Schema705.method_5330(schema, hashMap, "minecraft:xp_bottle");
        schema.registerSimple((Map)hashMap, "minecraft:xp_orb");
        Schema705.method_5311(schema, hashMap, "minecraft:zombie");
        schema.register((Map)hashMap, "minecraft:zombie_horse", string -> DSL.optionalFields((String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        Schema705.method_5311(schema, hashMap, "minecraft:zombie_pigman");
        Schema705.method_5311(schema, hashMap, "minecraft:zombie_villager");
        schema.registerSimple((Map)hashMap, "minecraft:evocation_fangs");
        Schema705.method_5311(schema, hashMap, "minecraft:evocation_illager");
        schema.registerSimple((Map)hashMap, "minecraft:illusion_illager");
        schema.register((Map)hashMap, "minecraft:llama", string -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.ITEM_STACK.in(schema)), (String)"SaddleItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (String)"DecorItem", (TypeTemplate)TypeReferences.ITEM_STACK.in(schema), (TypeTemplate)Schema100.targetItems(schema)));
        schema.registerSimple((Map)hashMap, "minecraft:llama_spit");
        Schema705.method_5311(schema, hashMap, "minecraft:vex");
        Schema705.method_5311(schema, hashMap, "minecraft:vindication_illager");
        return hashMap;
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        super.registerTypes(schema, entityTypes, blockEntityTypes);
        schema.registerType(true, TypeReferences.ENTITY, () -> DSL.taggedChoiceLazy((String)"id", Schema705.getIdentifierType(), (Map)entityTypes));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)TypeReferences.ITEM_NAME.in(schema), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)TypeReferences.ENTITY_TREE.in(schema), (String)"BlockEntityTag", (TypeTemplate)TypeReferences.BLOCK_ENTITY.in(schema), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)TypeReferences.BLOCK_NAME.in(schema)))), (Hook.HookFunction)field_5746, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
    }
}

