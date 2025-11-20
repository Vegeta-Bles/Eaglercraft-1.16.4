/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.BlockStateFlattening;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.util.math.WordPackedArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPalettedStorageFix
extends DataFix {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BitSet BLOCKS_NEEDING_SIDE_UPDATE = new BitSet(256);
    private static final BitSet BLOCKS_NEEDING_IN_PLACE_UPDATE = new BitSet(256);
    private static final Dynamic<?> PUMPKIN = BlockStateFlattening.parseState("{Name:'minecraft:pumpkin'}");
    private static final Dynamic<?> PODZOL = BlockStateFlattening.parseState("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
    private static final Dynamic<?> SNOWY_GRASS = BlockStateFlattening.parseState("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
    private static final Dynamic<?> SNOWY_MYCELIUM = BlockStateFlattening.parseState("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
    private static final Dynamic<?> SUNFLOWER_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
    private static final Dynamic<?> LILAC_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
    private static final Dynamic<?> GRASS_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
    private static final Dynamic<?> FERN_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
    private static final Dynamic<?> ROSE_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
    private static final Dynamic<?> PEONY_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:peony',Properties:{half:'upper'}}");
    private static final Map<String, Dynamic<?>> FLOWER_POT = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        hashMap.put("minecraft:air0", BlockStateFlattening.parseState("{Name:'minecraft:flower_pot'}"));
        hashMap.put("minecraft:red_flower0", BlockStateFlattening.parseState("{Name:'minecraft:potted_poppy'}"));
        hashMap.put("minecraft:red_flower1", BlockStateFlattening.parseState("{Name:'minecraft:potted_blue_orchid'}"));
        hashMap.put("minecraft:red_flower2", BlockStateFlattening.parseState("{Name:'minecraft:potted_allium'}"));
        hashMap.put("minecraft:red_flower3", BlockStateFlattening.parseState("{Name:'minecraft:potted_azure_bluet'}"));
        hashMap.put("minecraft:red_flower4", BlockStateFlattening.parseState("{Name:'minecraft:potted_red_tulip'}"));
        hashMap.put("minecraft:red_flower5", BlockStateFlattening.parseState("{Name:'minecraft:potted_orange_tulip'}"));
        hashMap.put("minecraft:red_flower6", BlockStateFlattening.parseState("{Name:'minecraft:potted_white_tulip'}"));
        hashMap.put("minecraft:red_flower7", BlockStateFlattening.parseState("{Name:'minecraft:potted_pink_tulip'}"));
        hashMap.put("minecraft:red_flower8", BlockStateFlattening.parseState("{Name:'minecraft:potted_oxeye_daisy'}"));
        hashMap.put("minecraft:yellow_flower0", BlockStateFlattening.parseState("{Name:'minecraft:potted_dandelion'}"));
        hashMap.put("minecraft:sapling0", BlockStateFlattening.parseState("{Name:'minecraft:potted_oak_sapling'}"));
        hashMap.put("minecraft:sapling1", BlockStateFlattening.parseState("{Name:'minecraft:potted_spruce_sapling'}"));
        hashMap.put("minecraft:sapling2", BlockStateFlattening.parseState("{Name:'minecraft:potted_birch_sapling'}"));
        hashMap.put("minecraft:sapling3", BlockStateFlattening.parseState("{Name:'minecraft:potted_jungle_sapling'}"));
        hashMap.put("minecraft:sapling4", BlockStateFlattening.parseState("{Name:'minecraft:potted_acacia_sapling'}"));
        hashMap.put("minecraft:sapling5", BlockStateFlattening.parseState("{Name:'minecraft:potted_dark_oak_sapling'}"));
        hashMap.put("minecraft:red_mushroom0", BlockStateFlattening.parseState("{Name:'minecraft:potted_red_mushroom'}"));
        hashMap.put("minecraft:brown_mushroom0", BlockStateFlattening.parseState("{Name:'minecraft:potted_brown_mushroom'}"));
        hashMap.put("minecraft:deadbush0", BlockStateFlattening.parseState("{Name:'minecraft:potted_dead_bush'}"));
        hashMap.put("minecraft:tallgrass2", BlockStateFlattening.parseState("{Name:'minecraft:potted_fern'}"));
        hashMap.put("minecraft:cactus0", BlockStateFlattening.lookupState(2240));
    });
    private static final Map<String, Dynamic<?>> SKULL = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        ChunkPalettedStorageFix.buildSkull(hashMap, 0, "skeleton", "skull");
        ChunkPalettedStorageFix.buildSkull(hashMap, 1, "wither_skeleton", "skull");
        ChunkPalettedStorageFix.buildSkull(hashMap, 2, "zombie", "head");
        ChunkPalettedStorageFix.buildSkull(hashMap, 3, "player", "head");
        ChunkPalettedStorageFix.buildSkull(hashMap, 4, "creeper", "head");
        ChunkPalettedStorageFix.buildSkull(hashMap, 5, "dragon", "head");
    });
    private static final Map<String, Dynamic<?>> DOOR = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        ChunkPalettedStorageFix.buildDoor(hashMap, "oak_door", 1024);
        ChunkPalettedStorageFix.buildDoor(hashMap, "iron_door", 1136);
        ChunkPalettedStorageFix.buildDoor(hashMap, "spruce_door", 3088);
        ChunkPalettedStorageFix.buildDoor(hashMap, "birch_door", 3104);
        ChunkPalettedStorageFix.buildDoor(hashMap, "jungle_door", 3120);
        ChunkPalettedStorageFix.buildDoor(hashMap, "acacia_door", 3136);
        ChunkPalettedStorageFix.buildDoor(hashMap, "dark_oak_door", 3152);
    });
    private static final Map<String, Dynamic<?>> NOTE_BLOCK = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        for (int i = 0; i < 26; ++i) {
            hashMap.put("true" + i, BlockStateFlattening.parseState("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + i + "'}}"));
            hashMap.put("false" + i, BlockStateFlattening.parseState("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + i + "'}}"));
        }
    });
    private static final Int2ObjectMap<String> COLORS = (Int2ObjectMap)DataFixUtils.make((Object)new Int2ObjectOpenHashMap(), int2ObjectOpenHashMap -> {
        int2ObjectOpenHashMap.put(0, (Object)"white");
        int2ObjectOpenHashMap.put(1, (Object)"orange");
        int2ObjectOpenHashMap.put(2, (Object)"magenta");
        int2ObjectOpenHashMap.put(3, (Object)"light_blue");
        int2ObjectOpenHashMap.put(4, (Object)"yellow");
        int2ObjectOpenHashMap.put(5, (Object)"lime");
        int2ObjectOpenHashMap.put(6, (Object)"pink");
        int2ObjectOpenHashMap.put(7, (Object)"gray");
        int2ObjectOpenHashMap.put(8, (Object)"light_gray");
        int2ObjectOpenHashMap.put(9, (Object)"cyan");
        int2ObjectOpenHashMap.put(10, (Object)"purple");
        int2ObjectOpenHashMap.put(11, (Object)"blue");
        int2ObjectOpenHashMap.put(12, (Object)"brown");
        int2ObjectOpenHashMap.put(13, (Object)"green");
        int2ObjectOpenHashMap.put(14, (Object)"red");
        int2ObjectOpenHashMap.put(15, (Object)"black");
    });
    private static final Map<String, Dynamic<?>> BED = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        for (Int2ObjectMap.Entry entry : COLORS.int2ObjectEntrySet()) {
            if (Objects.equals(entry.getValue(), "red")) continue;
            ChunkPalettedStorageFix.buildBed(hashMap, entry.getIntKey(), (String)entry.getValue());
        }
    });
    private static final Map<String, Dynamic<?>> BANNER = (Map)DataFixUtils.make((Object)Maps.newHashMap(), hashMap -> {
        for (Int2ObjectMap.Entry entry : COLORS.int2ObjectEntrySet()) {
            if (Objects.equals(entry.getValue(), "white")) continue;
            ChunkPalettedStorageFix.buildBanner(hashMap, 15 - entry.getIntKey(), (String)entry.getValue());
        }
    });
    private static final Dynamic<?> AIR;

    public ChunkPalettedStorageFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private static void buildSkull(Map<String, Dynamic<?>> out, int n, String mob, String block) {
        out.put(n + "north", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'north'}}"));
        out.put(n + "east", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'east'}}"));
        out.put(n + "south", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'south'}}"));
        out.put(n + "west", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'west'}}"));
        for (_snowman = 0; _snowman < 16; ++_snowman) {
            out.put(n + "" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_" + block + "',Properties:{rotation:'" + _snowman + "'}}"));
        }
    }

    private static void buildDoor(Map<String, Dynamic<?>> out, String name, int n) {
        out.put("minecraft:" + name + "eastlowerleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "eastlowerleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "eastlowerlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "eastlowerlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "eastlowerrightfalsefalse", BlockStateFlattening.lookupState(n));
        out.put("minecraft:" + name + "eastlowerrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "eastlowerrighttruefalse", BlockStateFlattening.lookupState(n + 4));
        out.put("minecraft:" + name + "eastlowerrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "eastupperleftfalsefalse", BlockStateFlattening.lookupState(n + 8));
        out.put("minecraft:" + name + "eastupperleftfalsetrue", BlockStateFlattening.lookupState(n + 10));
        out.put("minecraft:" + name + "eastupperlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "eastupperlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "eastupperrightfalsefalse", BlockStateFlattening.lookupState(n + 9));
        out.put("minecraft:" + name + "eastupperrightfalsetrue", BlockStateFlattening.lookupState(n + 11));
        out.put("minecraft:" + name + "eastupperrighttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "eastupperrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "northlowerleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "northlowerleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "northlowerlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "northlowerlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "northlowerrightfalsefalse", BlockStateFlattening.lookupState(n + 3));
        out.put("minecraft:" + name + "northlowerrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "northlowerrighttruefalse", BlockStateFlattening.lookupState(n + 7));
        out.put("minecraft:" + name + "northlowerrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "northupperleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "northupperleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "northupperlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "northupperlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "northupperrightfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "northupperrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "northupperrighttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "northupperrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "southlowerleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "southlowerleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "southlowerlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "southlowerlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "southlowerrightfalsefalse", BlockStateFlattening.lookupState(n + 1));
        out.put("minecraft:" + name + "southlowerrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "southlowerrighttruefalse", BlockStateFlattening.lookupState(n + 5));
        out.put("minecraft:" + name + "southlowerrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "southupperleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "southupperleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "southupperlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "southupperlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "southupperrightfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "southupperrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "southupperrighttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "southupperrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "westlowerleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "westlowerleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "westlowerlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "westlowerlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "westlowerrightfalsefalse", BlockStateFlattening.lookupState(n + 2));
        out.put("minecraft:" + name + "westlowerrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "westlowerrighttruefalse", BlockStateFlattening.lookupState(n + 6));
        out.put("minecraft:" + name + "westlowerrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "westupperleftfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "westupperleftfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "westupperlefttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "westupperlefttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        out.put("minecraft:" + name + "westupperrightfalsefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        out.put("minecraft:" + name + "westupperrightfalsetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        out.put("minecraft:" + name + "westupperrighttruefalse", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        out.put("minecraft:" + name + "westupperrighttruetrue", BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
    }

    private static void buildBed(Map<String, Dynamic<?>> out, int n, String string) {
        out.put("southfalsefoot" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
        out.put("westfalsefoot" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
        out.put("northfalsefoot" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
        out.put("eastfalsefoot" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
        out.put("southfalsehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
        out.put("westfalsehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
        out.put("northfalsehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
        out.put("eastfalsehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
        out.put("southtruehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
        out.put("westtruehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
        out.put("northtruehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
        out.put("easttruehead" + n, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
    }

    private static void buildBanner(Map<String, Dynamic<?>> out, int n2, String string) {
        int n2;
        for (int i = 0; i < 16; ++i) {
            out.put("" + i + "_" + n2, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_banner',Properties:{rotation:'" + i + "'}}"));
        }
        out.put("north_" + n2, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'north'}}"));
        out.put("south_" + n2, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'south'}}"));
        out.put("west_" + n2, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'west'}}"));
        out.put("east_" + n2, BlockStateFlattening.parseState("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'east'}}"));
    }

    public static String getName(Dynamic<?> dynamic) {
        return dynamic.get("Name").asString("");
    }

    public static String getProperty(Dynamic<?> dynamic, String string) {
        return dynamic.get("Properties").get(string).asString("");
    }

    public static int addTo(Int2ObjectBiMap<Dynamic<?>> int2ObjectBiMap, Dynamic<?> dynamic) {
        int n = int2ObjectBiMap.getRawId(dynamic);
        if (n == -1) {
            n = int2ObjectBiMap.add(dynamic);
        }
        return n;
    }

    private Dynamic<?> fixChunk(Dynamic<?> dynamic) {
        Optional optional = dynamic.get("Level").result();
        if (optional.isPresent() && ((Dynamic)optional.get()).get("Sections").asStreamOpt().result().isPresent()) {
            return dynamic.set("Level", new Level((Dynamic)optional.get()).transform());
        }
        return dynamic;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        _snowman = this.getOutputSchema().getType(TypeReferences.CHUNK);
        return this.writeFixAndRead("ChunkPalettedStorageFix", type, _snowman, this::fixChunk);
    }

    public static int getSideToUpgradeFlag(boolean west, boolean east, boolean north, boolean south) {
        int n = 0;
        if (north) {
            n = east ? (n |= 2) : (west ? (n |= 0x80) : (n |= 1));
        } else if (south) {
            n = west ? (n |= 0x20) : (east ? (n |= 8) : (n |= 0x10));
        } else if (east) {
            n |= 4;
        } else if (west) {
            n |= 0x40;
        }
        return n;
    }

    static {
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(2);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(3);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(110);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(140);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(144);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(25);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(86);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(26);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(176);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(177);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(175);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(64);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(71);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(193);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(194);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(195);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(196);
        BLOCKS_NEEDING_IN_PLACE_UPDATE.set(197);
        BLOCKS_NEEDING_SIDE_UPDATE.set(54);
        BLOCKS_NEEDING_SIDE_UPDATE.set(146);
        BLOCKS_NEEDING_SIDE_UPDATE.set(25);
        BLOCKS_NEEDING_SIDE_UPDATE.set(26);
        BLOCKS_NEEDING_SIDE_UPDATE.set(51);
        BLOCKS_NEEDING_SIDE_UPDATE.set(53);
        BLOCKS_NEEDING_SIDE_UPDATE.set(67);
        BLOCKS_NEEDING_SIDE_UPDATE.set(108);
        BLOCKS_NEEDING_SIDE_UPDATE.set(109);
        BLOCKS_NEEDING_SIDE_UPDATE.set(114);
        BLOCKS_NEEDING_SIDE_UPDATE.set(128);
        BLOCKS_NEEDING_SIDE_UPDATE.set(134);
        BLOCKS_NEEDING_SIDE_UPDATE.set(135);
        BLOCKS_NEEDING_SIDE_UPDATE.set(136);
        BLOCKS_NEEDING_SIDE_UPDATE.set(156);
        BLOCKS_NEEDING_SIDE_UPDATE.set(163);
        BLOCKS_NEEDING_SIDE_UPDATE.set(164);
        BLOCKS_NEEDING_SIDE_UPDATE.set(180);
        BLOCKS_NEEDING_SIDE_UPDATE.set(203);
        BLOCKS_NEEDING_SIDE_UPDATE.set(55);
        BLOCKS_NEEDING_SIDE_UPDATE.set(85);
        BLOCKS_NEEDING_SIDE_UPDATE.set(113);
        BLOCKS_NEEDING_SIDE_UPDATE.set(188);
        BLOCKS_NEEDING_SIDE_UPDATE.set(189);
        BLOCKS_NEEDING_SIDE_UPDATE.set(190);
        BLOCKS_NEEDING_SIDE_UPDATE.set(191);
        BLOCKS_NEEDING_SIDE_UPDATE.set(192);
        BLOCKS_NEEDING_SIDE_UPDATE.set(93);
        BLOCKS_NEEDING_SIDE_UPDATE.set(94);
        BLOCKS_NEEDING_SIDE_UPDATE.set(101);
        BLOCKS_NEEDING_SIDE_UPDATE.set(102);
        BLOCKS_NEEDING_SIDE_UPDATE.set(160);
        BLOCKS_NEEDING_SIDE_UPDATE.set(106);
        BLOCKS_NEEDING_SIDE_UPDATE.set(107);
        BLOCKS_NEEDING_SIDE_UPDATE.set(183);
        BLOCKS_NEEDING_SIDE_UPDATE.set(184);
        BLOCKS_NEEDING_SIDE_UPDATE.set(185);
        BLOCKS_NEEDING_SIDE_UPDATE.set(186);
        BLOCKS_NEEDING_SIDE_UPDATE.set(187);
        BLOCKS_NEEDING_SIDE_UPDATE.set(132);
        BLOCKS_NEEDING_SIDE_UPDATE.set(139);
        BLOCKS_NEEDING_SIDE_UPDATE.set(199);
        AIR = BlockStateFlattening.lookupState(0);
    }

    public static enum Facing {
        DOWN(Direction.NEGATIVE, Axis.Y),
        UP(Direction.POSITIVE, Axis.Y),
        NORTH(Direction.NEGATIVE, Axis.Z),
        SOUTH(Direction.POSITIVE, Axis.Z),
        WEST(Direction.NEGATIVE, Axis.X),
        EAST(Direction.POSITIVE, Axis.X);

        private final Axis axis;
        private final Direction direction;

        private Facing(Direction direction, Axis axis) {
            this.axis = axis;
            this.direction = direction;
        }

        public Direction getDirection() {
            return this.direction;
        }

        public Axis getAxis() {
            return this.axis;
        }

        public static enum Direction {
            POSITIVE(1),
            NEGATIVE(-1);

            private final int offset;

            private Direction(int n2) {
                this.offset = n2;
            }

            public int getOffset() {
                return this.offset;
            }
        }

        public static enum Axis {
            X,
            Y,
            Z;

        }
    }

    static class ChunkNibbleArray {
        private final byte[] contents;

        public ChunkNibbleArray() {
            this.contents = new byte[2048];
        }

        public ChunkNibbleArray(byte[] byArray) {
            this.contents = byArray;
            if (byArray.length != 2048) {
                throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + byArray.length);
            }
        }

        public int get(int x, int y, int n) {
            _snowman = this.getRawIndex(y << 8 | n << 4 | x);
            if (this.usesLowNibble(y << 8 | n << 4 | x)) {
                return this.contents[_snowman] & 0xF;
            }
            return this.contents[_snowman] >> 4 & 0xF;
        }

        private boolean usesLowNibble(int index) {
            return (index & 1) == 0;
        }

        private int getRawIndex(int index) {
            return index >> 1;
        }
    }

    static final class Level {
        private int sidesToUpgrade;
        private final Section[] sections = new Section[16];
        private final Dynamic<?> level;
        private final int xPos;
        private final int yPos;
        private final Int2ObjectMap<Dynamic<?>> blockEntities = new Int2ObjectLinkedOpenHashMap(16);

        public Level(Dynamic<?> dynamic) {
            this.level = dynamic;
            this.xPos = dynamic.get("xPos").asInt(0) << 4;
            this.yPos = dynamic.get("zPos").asInt(0) << 4;
            dynamic.get("TileEntities").asStreamOpt().result().ifPresent(stream -> stream.forEach(dynamic -> {
                int n = dynamic.get("x").asInt(0) - this.xPos & 0xF;
                _snowman = dynamic.get("y").asInt(0);
                _snowman = _snowman << 8 | (_snowman = dynamic.get("z").asInt(0) - this.yPos & 0xF) << 4 | n;
                if (this.blockEntities.put(_snowman, dynamic) != null) {
                    LOGGER.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", (Object)this.xPos, (Object)this.yPos, (Object)n, (Object)_snowman, (Object)_snowman);
                }
            }));
            boolean bl = dynamic.get("convertedFromAlphaFormat").asBoolean(false);
            dynamic.get("Sections").asStreamOpt().result().ifPresent(stream -> stream.forEach(dynamic -> {
                Section section = new Section((Dynamic<?>)dynamic);
                this.sidesToUpgrade = section.visit(this.sidesToUpgrade);
                this.sections[section.y] = section;
            }));
            for (Section section : this.sections) {
                if (section == null) continue;
                block14: for (Map.Entry entry2 : section.inPlaceUpdates.entrySet()) {
                    int n = section.y << 12;
                    switch ((Integer)entry2.getKey()) {
                        case 2: {
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!"minecraft:grass_block".equals(ChunkPalettedStorageFix.getName(_snowman2)) || !"minecraft:snow".equals(_snowman3 = ChunkPalettedStorageFix.getName(this.getBlock(Level.adjacentTo(n2, Facing.UP)))) && !"minecraft:snow_layer".equals(_snowman3)) continue;
                                this.setBlock(n2, SNOWY_GRASS);
                            }
                            continue block14;
                        }
                        case 3: {
                            Dynamic<?> _snowman2;
                            Map.Entry entry2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!"minecraft:podzol".equals(ChunkPalettedStorageFix.getName(_snowman2)) || !"minecraft:snow".equals(_snowman3 = ChunkPalettedStorageFix.getName(this.getBlock(Level.adjacentTo(n2, Facing.UP)))) && !"minecraft:snow_layer".equals(_snowman3)) continue;
                                this.setBlock(n2, PODZOL);
                            }
                            continue block14;
                        }
                        case 110: {
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!"minecraft:mycelium".equals(ChunkPalettedStorageFix.getName(_snowman2)) || !"minecraft:snow".equals(_snowman3 = ChunkPalettedStorageFix.getName(this.getBlock(Level.adjacentTo(n2, Facing.UP)))) && !"minecraft:snow_layer".equals(_snowman3)) continue;
                                this.setBlock(n2, SNOWY_MYCELIUM);
                            }
                            continue block14;
                        }
                        case 25: {
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.removeBlockEntity(n2 |= n);
                                if (_snowman2 == null) continue;
                                _snowman3 = Boolean.toString(_snowman2.get("powered").asBoolean(false)) + (byte)Math.min(Math.max(_snowman2.get("note").asInt(0), 0), 24);
                                this.setBlock(n2, (Dynamic)NOTE_BLOCK.getOrDefault(_snowman3, NOTE_BLOCK.get("false0")));
                            }
                            continue block14;
                        }
                        case 26: {
                            Object _snowman4;
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlockEntity(n2 |= n);
                                _snowman3 = this.getBlock(n2);
                                if (_snowman2 == null || (_snowman = _snowman2.get("color").asInt(0)) == 14 || _snowman < 0 || _snowman >= 16) continue;
                                _snowman4 = ChunkPalettedStorageFix.getProperty(_snowman3, "facing") + ChunkPalettedStorageFix.getProperty(_snowman3, "occupied") + ChunkPalettedStorageFix.getProperty(_snowman3, "part") + _snowman;
                                if (!BED.containsKey(_snowman4)) continue;
                                this.setBlock(n2, (Dynamic)BED.get(_snowman4));
                            }
                            continue block14;
                        }
                        case 176: 
                        case 177: {
                            Object _snowman4;
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlockEntity(n2 |= n);
                                _snowman3 = this.getBlock(n2);
                                if (_snowman2 == null || (_snowman = _snowman2.get("Base").asInt(0)) == 15 || _snowman < 0 || _snowman >= 16) continue;
                                _snowman4 = ChunkPalettedStorageFix.getProperty(_snowman3, (Integer)entry2.getKey() == 176 ? "rotation" : "facing") + "_" + _snowman;
                                if (!BANNER.containsKey(_snowman4)) continue;
                                this.setBlock(n2, (Dynamic)BANNER.get(_snowman4));
                            }
                            continue block14;
                        }
                        case 86: {
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!"minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName(_snowman2)) || !"minecraft:grass_block".equals(_snowman3 = ChunkPalettedStorageFix.getName(this.getBlock(Level.adjacentTo(n2, Facing.DOWN)))) && !"minecraft:dirt".equals(_snowman3)) continue;
                                this.setBlock(n2, PUMPKIN);
                            }
                            continue block14;
                        }
                        case 140: {
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.removeBlockEntity(n2 |= n);
                                if (_snowman2 == null) continue;
                                _snowman3 = _snowman2.get("Item").asString("") + _snowman2.get("Data").asInt(0);
                                this.setBlock(n2, (Dynamic)FLOWER_POT.getOrDefault(_snowman3, FLOWER_POT.get("minecraft:air0")));
                            }
                            continue block14;
                        }
                        case 144: {
                            Object _snowman4;
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlockEntity(n2 |= n);
                                if (_snowman2 == null) continue;
                                _snowman3 = String.valueOf(_snowman2.get("SkullType").asInt(0));
                                String _snowman5 = ChunkPalettedStorageFix.getProperty(this.getBlock(n2), "facing");
                                _snowman4 = "up".equals(_snowman5) || "down".equals(_snowman5) ? _snowman3 + String.valueOf(_snowman2.get("Rot").asInt(0)) : _snowman3 + _snowman5;
                                _snowman2.remove("SkullType");
                                _snowman2.remove("facing");
                                _snowman2.remove("Rot");
                                this.setBlock(n2, (Dynamic)SKULL.getOrDefault(_snowman4, SKULL.get("0north")));
                            }
                            continue block14;
                        }
                        case 64: 
                        case 71: 
                        case 193: 
                        case 194: 
                        case 195: 
                        case 196: 
                        case 197: {
                            Object _snowman4;
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!ChunkPalettedStorageFix.getName(_snowman2).endsWith("_door") || !"lower".equals(ChunkPalettedStorageFix.getProperty(_snowman3 = this.getBlock(n2), "half"))) continue;
                                _snowman = Level.adjacentTo(n2, Facing.UP);
                                _snowman4 = this.getBlock(_snowman);
                                String _snowman6 = ChunkPalettedStorageFix.getName(_snowman3);
                                if (!_snowman6.equals(ChunkPalettedStorageFix.getName(_snowman4))) continue;
                                String _snowman7 = ChunkPalettedStorageFix.getProperty(_snowman3, "facing");
                                String _snowman8 = ChunkPalettedStorageFix.getProperty(_snowman3, "open");
                                String _snowman9 = bl ? "left" : ChunkPalettedStorageFix.getProperty(_snowman4, "hinge");
                                String _snowman10 = bl ? "false" : ChunkPalettedStorageFix.getProperty(_snowman4, "powered");
                                this.setBlock(n2, (Dynamic)DOOR.get(_snowman6 + _snowman7 + "lower" + _snowman9 + _snowman8 + _snowman10));
                                this.setBlock(_snowman, (Dynamic)DOOR.get(_snowman6 + _snowman7 + "upper" + _snowman9 + _snowman8 + _snowman10));
                            }
                            continue block14;
                        }
                        case 175: {
                            Object _snowman3;
                            Dynamic<?> _snowman2;
                            IntListIterator intListIterator = ((IntList)entry2.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                _snowman2 = this.getBlock(n2 |= n);
                                if (!"upper".equals(ChunkPalettedStorageFix.getProperty(_snowman2, "half"))) continue;
                                _snowman3 = this.getBlock(Level.adjacentTo(n2, Facing.DOWN));
                                String _snowman11 = ChunkPalettedStorageFix.getName(_snowman3);
                                if ("minecraft:sunflower".equals(_snowman11)) {
                                    this.setBlock(n2, SUNFLOWER_UPPER);
                                    continue;
                                }
                                if ("minecraft:lilac".equals(_snowman11)) {
                                    this.setBlock(n2, LILAC_UPPER);
                                    continue;
                                }
                                if ("minecraft:tall_grass".equals(_snowman11)) {
                                    this.setBlock(n2, GRASS_UPPER);
                                    continue;
                                }
                                if ("minecraft:large_fern".equals(_snowman11)) {
                                    this.setBlock(n2, FERN_UPPER);
                                    continue;
                                }
                                if ("minecraft:rose_bush".equals(_snowman11)) {
                                    this.setBlock(n2, ROSE_UPPER);
                                    continue;
                                }
                                if (!"minecraft:peony".equals(_snowman11)) continue;
                                this.setBlock(n2, PEONY_UPPER);
                            }
                            break;
                        }
                    }
                }
            }
        }

        @Nullable
        private Dynamic<?> getBlockEntity(int n) {
            return (Dynamic)this.blockEntities.get(n);
        }

        @Nullable
        private Dynamic<?> removeBlockEntity(int n) {
            return (Dynamic)this.blockEntities.remove(n);
        }

        public static int adjacentTo(int n, Facing direction) {
            switch (direction.getAxis()) {
                case X: {
                    _snowman = (n & 0xF) + direction.getDirection().getOffset();
                    return _snowman < 0 || _snowman > 15 ? -1 : n & 0xFFFFFFF0 | _snowman;
                }
                case Y: {
                    _snowman = (n >> 8) + direction.getDirection().getOffset();
                    return _snowman < 0 || _snowman > 255 ? -1 : n & 0xFF | _snowman << 8;
                }
                case Z: {
                    _snowman = (n >> 4 & 0xF) + direction.getDirection().getOffset();
                    return _snowman < 0 || _snowman > 15 ? -1 : n & 0xFFFFFF0F | _snowman << 4;
                }
            }
            return -1;
        }

        private void setBlock(int n, Dynamic<?> dynamic) {
            if (n < 0 || n > 65535) {
                return;
            }
            Section section = this.getSection(n);
            if (section == null) {
                return;
            }
            section.setBlock(n & 0xFFF, dynamic);
        }

        @Nullable
        private Section getSection(int n) {
            _snowman = n >> 12;
            return _snowman < this.sections.length ? this.sections[_snowman] : null;
        }

        public Dynamic<?> getBlock(int n) {
            if (n < 0 || n > 65535) {
                return AIR;
            }
            Section section = this.getSection(n);
            if (section == null) {
                return AIR;
            }
            return section.getBlock(n & 0xFFF);
        }

        public Dynamic<?> transform() {
            Dynamic dynamic = this.level;
            dynamic = this.blockEntities.isEmpty() ? dynamic.remove("TileEntities") : dynamic.set("TileEntities", dynamic.createList(this.blockEntities.values().stream()));
            dynamic2 = dynamic.emptyMap();
            ArrayList _snowman2 = Lists.newArrayList();
            for (Section section : this.sections) {
                if (section == null) continue;
                _snowman2.add(section.transform());
                Dynamic dynamic2 = dynamic2.set(String.valueOf(section.y), dynamic2.createIntList(Arrays.stream(section.innerPositions.toIntArray())));
            }
            _snowman = dynamic.emptyMap();
            _snowman = _snowman.set("Sides", _snowman.createByte((byte)this.sidesToUpgrade));
            _snowman = _snowman.set("Indices", dynamic2);
            return dynamic.set("UpgradeData", _snowman).set("Sections", _snowman.createList(_snowman2.stream()));
        }
    }

    static class Section {
        private final Int2ObjectBiMap<Dynamic<?>> paletteMap = new Int2ObjectBiMap(32);
        private final List<Dynamic<?>> paletteData;
        private final Dynamic<?> section;
        private final boolean hasBlocks;
        private final Int2ObjectMap<IntList> inPlaceUpdates = new Int2ObjectLinkedOpenHashMap();
        private final IntList innerPositions = new IntArrayList();
        public final int y;
        private final Set<Dynamic<?>> seenStates = Sets.newIdentityHashSet();
        private final int[] states = new int[4096];

        public Section(Dynamic<?> dynamic) {
            this.paletteData = Lists.newArrayList();
            this.section = dynamic;
            this.y = dynamic.get("Y").asInt(0);
            this.hasBlocks = dynamic.get("Blocks").result().isPresent();
        }

        public Dynamic<?> getBlock(int index) {
            if (index < 0 || index > 4095) {
                return AIR;
            }
            Dynamic<?> dynamic = this.paletteMap.get(this.states[index]);
            return dynamic == null ? AIR : dynamic;
        }

        public void setBlock(int pos, Dynamic<?> dynamic) {
            if (this.seenStates.add(dynamic)) {
                this.paletteData.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName(dynamic)) ? AIR : dynamic);
            }
            this.states[pos] = ChunkPalettedStorageFix.addTo(this.paletteMap, dynamic);
        }

        public int visit(int sidesToUpgrade) {
            if (!this.hasBlocks) {
                return sidesToUpgrade;
            }
            ByteBuffer byteBuffer2 = (ByteBuffer)this.section.get("Blocks").asByteBufferOpt().result().get();
            ChunkNibbleArray _snowman2 = this.section.get("Data").asByteBufferOpt().map(byteBuffer -> new ChunkNibbleArray(DataFixUtils.toArray((ByteBuffer)byteBuffer))).result().orElseGet(ChunkNibbleArray::new);
            ChunkNibbleArray _snowman3 = this.section.get("Add").asByteBufferOpt().map(byteBuffer -> new ChunkNibbleArray(DataFixUtils.toArray((ByteBuffer)byteBuffer))).result().orElseGet(ChunkNibbleArray::new);
            this.seenStates.add(AIR);
            ChunkPalettedStorageFix.addTo(this.paletteMap, AIR);
            this.paletteData.add(AIR);
            for (int i = 0; i < 4096; ++i) {
                _snowman = i & 0xF;
                _snowman = i >> 8 & 0xF;
                _snowman = i >> 4 & 0xF;
                _snowman = _snowman3.get(_snowman, _snowman, _snowman) << 12 | (byteBuffer2.get(i) & 0xFF) << 4 | _snowman2.get(_snowman, _snowman, _snowman);
                if (BLOCKS_NEEDING_IN_PLACE_UPDATE.get(_snowman >> 4)) {
                    this.addInPlaceUpdate(_snowman >> 4, i);
                }
                if (BLOCKS_NEEDING_SIDE_UPDATE.get(_snowman >> 4)) {
                    _snowman = ChunkPalettedStorageFix.getSideToUpgradeFlag(_snowman == 0, _snowman == 15, _snowman == 0, _snowman == 15);
                    if (_snowman == 0) {
                        this.innerPositions.add(i);
                    } else {
                        sidesToUpgrade |= _snowman;
                    }
                }
                this.setBlock(i, BlockStateFlattening.lookupState(_snowman));
            }
            return sidesToUpgrade;
        }

        private void addInPlaceUpdate(int section, int index) {
            IntList intList = (IntList)this.inPlaceUpdates.get(section);
            if (intList == null) {
                intList = new IntArrayList();
                this.inPlaceUpdates.put(section, (Object)intList);
            }
            intList.add(index);
        }

        public Dynamic<?> transform() {
            Dynamic dynamic = this.section;
            if (!this.hasBlocks) {
                return dynamic;
            }
            dynamic = dynamic.set("Palette", dynamic.createList(this.paletteData.stream()));
            int _snowman2 = Math.max(4, DataFixUtils.ceillog2((int)this.seenStates.size()));
            WordPackedArray _snowman3 = new WordPackedArray(_snowman2, 4096);
            for (int i = 0; i < this.states.length; ++i) {
                _snowman3.set(i, this.states[i]);
            }
            dynamic = dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(_snowman3.getAlignedArray())));
            dynamic = dynamic.remove("Blocks");
            dynamic = dynamic.remove("Data");
            dynamic = dynamic.remove("Add");
            return dynamic;
        }
    }
}

