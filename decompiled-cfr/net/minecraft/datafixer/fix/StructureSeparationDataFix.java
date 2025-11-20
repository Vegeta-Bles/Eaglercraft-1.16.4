/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicLike
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.OptionalDynamic
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class StructureSeparationDataFix
extends DataFix {
    private static final ImmutableMap<String, Information> STRUCTURE_SPACING = ImmutableMap.builder().put((Object)"minecraft:village", (Object)new Information(32, 8, 10387312)).put((Object)"minecraft:desert_pyramid", (Object)new Information(32, 8, 14357617)).put((Object)"minecraft:igloo", (Object)new Information(32, 8, 14357618)).put((Object)"minecraft:jungle_pyramid", (Object)new Information(32, 8, 14357619)).put((Object)"minecraft:swamp_hut", (Object)new Information(32, 8, 14357620)).put((Object)"minecraft:pillager_outpost", (Object)new Information(32, 8, 165745296)).put((Object)"minecraft:monument", (Object)new Information(32, 5, 10387313)).put((Object)"minecraft:endcity", (Object)new Information(20, 11, 10387313)).put((Object)"minecraft:mansion", (Object)new Information(80, 20, 10387319)).build();

    public StructureSeparationDataFix(Schema schema) {
        super(schema, true);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("WorldGenSettings building", this.getInputSchema().getType(TypeReferences.CHUNK_GENERATOR_SETTINGS), typed -> typed.update(DSL.remainderFinder(), StructureSeparationDataFix::method_28271));
    }

    private static <T> Dynamic<T> method_28268(long l, DynamicLike<T> dynamicLike, Dynamic<T> dynamic, Dynamic<T> dynamic2) {
        return dynamicLike.createMap((Map)ImmutableMap.of((Object)dynamicLike.createString("type"), (Object)dynamicLike.createString("minecraft:noise"), (Object)dynamicLike.createString("biome_source"), dynamic2, (Object)dynamicLike.createString("seed"), (Object)dynamicLike.createLong(l), (Object)dynamicLike.createString("settings"), dynamic));
    }

    private static <T> Dynamic<T> method_28272(Dynamic<T> dynamic, long l, boolean bl, boolean bl2) {
        ImmutableMap.Builder builder = ImmutableMap.builder().put((Object)dynamic.createString("type"), (Object)dynamic.createString("minecraft:vanilla_layered")).put((Object)dynamic.createString("seed"), (Object)dynamic.createLong(l)).put((Object)dynamic.createString("large_biomes"), (Object)dynamic.createBoolean(bl2));
        if (bl) {
            builder.put((Object)dynamic.createString("legacy_biome_init_layer"), (Object)dynamic.createBoolean(bl));
        }
        return dynamic.createMap((Map)builder.build());
    }

    private static <T> Dynamic<T> method_28271(Dynamic<T> dynamic22) {
        OptionalDynamic _snowman6;
        Dynamic dynamic22;
        Dynamic<T> _snowman13;
        DynamicOps dynamicOps = dynamic22.getOps();
        long _snowman2 = dynamic22.get("RandomSeed").asLong(0L);
        Optional _snowman3 = dynamic22.get("generatorName").asString().map(string -> string.toLowerCase(Locale.ROOT)).result();
        Optional _snowman4 = dynamic22.get("legacy_custom_options").asString().result().map(Optional::of).orElseGet(() -> {
            if (_snowman3.equals(Optional.of("customized"))) {
                return dynamic22.get("generatorOptions").asString().result();
            }
            return Optional.empty();
        });
        boolean _snowman5 = false;
        if (_snowman3.equals(Optional.of("customized"))) {
            _snowman13 = StructureSeparationDataFix.method_29916(dynamic22, _snowman2);
        } else if (!_snowman3.isPresent()) {
            _snowman13 = StructureSeparationDataFix.method_29916(dynamic22, _snowman2);
        } else {
            switch ((String)_snowman3.get()) {
                case "flat": {
                    _snowman6 = dynamic22.get("generatorOptions");
                    Map<Dynamic<T>, Dynamic<T>> _snowman7 = StructureSeparationDataFix.method_28275(dynamicOps, _snowman6);
                    _snowman13 = dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("type"), (Object)dynamic22.createString("minecraft:flat"), (Object)dynamic22.createString("settings"), (Object)dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("structures"), (Object)dynamic22.createMap(_snowman7), (Object)dynamic22.createString("layers"), (Object)_snowman6.get("layers").result().orElseGet(() -> dynamic22.createList(Stream.of(dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("height"), (Object)dynamic22.createInt(1), (Object)dynamic22.createString("block"), (Object)dynamic22.createString("minecraft:bedrock"))), dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("height"), (Object)dynamic22.createInt(2), (Object)dynamic22.createString("block"), (Object)dynamic22.createString("minecraft:dirt"))), dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("height"), (Object)dynamic22.createInt(1), (Object)dynamic22.createString("block"), (Object)dynamic22.createString("minecraft:grass_block")))))), (Object)dynamic22.createString("biome"), (Object)dynamic22.createString(_snowman6.get("biome").asString("minecraft:plains"))))));
                    break;
                }
                case "debug_all_block_states": {
                    _snowman13 = dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("type"), (Object)dynamic22.createString("minecraft:debug")));
                    break;
                }
                case "buffet": {
                    Dynamic _snowman12;
                    Dynamic _snowman11;
                    OptionalDynamic _snowman8 = dynamic22.get("generatorOptions");
                    OptionalDynamic _snowman9 = _snowman8.get("chunk_generator");
                    Optional _snowman10 = _snowman9.get("type").asString().result();
                    if (Objects.equals(_snowman10, Optional.of("minecraft:caves"))) {
                        _snowman11 = dynamic22.createString("minecraft:caves");
                        _snowman5 = true;
                    } else {
                        _snowman11 = Objects.equals(_snowman10, Optional.of("minecraft:floating_islands")) ? dynamic22.createString("minecraft:floating_islands") : dynamic22.createString("minecraft:overworld");
                    }
                    Dynamic dynamic3 = _snowman8.get("biome_source").result().orElseGet(() -> dynamic22.createMap((Map)ImmutableMap.of((Object)dynamic22.createString("type"), (Object)dynamic22.createString("minecraft:fixed"))));
                    if (dynamic3.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                        String string2 = dynamic3.get("options").get("biomes").asStream().findFirst().flatMap(dynamic -> dynamic.asString().result()).orElse("minecraft:ocean");
                        _snowman12 = dynamic3.remove("options").set("biome", dynamic22.createString(string2));
                    } else {
                        _snowman12 = dynamic3;
                    }
                    _snowman13 = StructureSeparationDataFix.method_28268(_snowman2, dynamic22, _snowman11, _snowman12);
                    break;
                }
                default: {
                    boolean bl = ((String)_snowman3.get()).equals("default");
                    _snowman = ((String)_snowman3.get()).equals("default_1_1") || bl && dynamic22.get("generatorVersion").asInt(0) == 0;
                    _snowman = ((String)_snowman3.get()).equals("amplified");
                    _snowman = ((String)_snowman3.get()).equals("largebiomes");
                    _snowman13 = StructureSeparationDataFix.method_28268(_snowman2, dynamic22, dynamic22.createString(_snowman ? "minecraft:amplified" : "minecraft:overworld"), StructureSeparationDataFix.method_28272(dynamic22, _snowman2, _snowman, _snowman));
                }
            }
        }
        _snowman = dynamic22.get("MapFeatures").asBoolean(true);
        _snowman = dynamic22.get("BonusChest").asBoolean(false);
        _snowman6 = ImmutableMap.builder();
        _snowman6.put(dynamicOps.createString("seed"), dynamicOps.createLong(_snowman2));
        _snowman6.put(dynamicOps.createString("generate_features"), dynamicOps.createBoolean(_snowman));
        _snowman6.put(dynamicOps.createString("bonus_chest"), dynamicOps.createBoolean(_snowman));
        _snowman6.put(dynamicOps.createString("dimensions"), StructureSeparationDataFix.method_29917(dynamic22, _snowman2, _snowman13, _snowman5));
        _snowman4.ifPresent(arg_0 -> StructureSeparationDataFix.method_28269((ImmutableMap.Builder)_snowman6, dynamicOps, arg_0));
        return new Dynamic(dynamicOps, dynamicOps.createMap((Map)_snowman6.build()));
    }

    protected static <T> Dynamic<T> method_29916(Dynamic<T> dynamic, long l) {
        return StructureSeparationDataFix.method_28268(l, dynamic, dynamic.createString("minecraft:overworld"), StructureSeparationDataFix.method_28272(dynamic, l, false, false));
    }

    protected static <T> T method_29917(Dynamic<T> dynamic, long l, Dynamic<T> dynamic2, boolean bl) {
        DynamicOps dynamicOps = dynamic.getOps();
        return (T)dynamicOps.createMap((Map)ImmutableMap.of((Object)dynamicOps.createString("minecraft:overworld"), (Object)dynamicOps.createMap((Map)ImmutableMap.of((Object)dynamicOps.createString("type"), (Object)dynamicOps.createString("minecraft:overworld" + (bl ? "_caves" : "")), (Object)dynamicOps.createString("generator"), (Object)dynamic2.getValue())), (Object)dynamicOps.createString("minecraft:the_nether"), (Object)dynamicOps.createMap((Map)ImmutableMap.of((Object)dynamicOps.createString("type"), (Object)dynamicOps.createString("minecraft:the_nether"), (Object)dynamicOps.createString("generator"), (Object)StructureSeparationDataFix.method_28268(l, dynamic, dynamic.createString("minecraft:nether"), dynamic.createMap((Map)ImmutableMap.of((Object)dynamic.createString("type"), (Object)dynamic.createString("minecraft:multi_noise"), (Object)dynamic.createString("seed"), (Object)dynamic.createLong(l), (Object)dynamic.createString("preset"), (Object)dynamic.createString("minecraft:nether")))).getValue())), (Object)dynamicOps.createString("minecraft:the_end"), (Object)dynamicOps.createMap((Map)ImmutableMap.of((Object)dynamicOps.createString("type"), (Object)dynamicOps.createString("minecraft:the_end"), (Object)dynamicOps.createString("generator"), (Object)StructureSeparationDataFix.method_28268(l, dynamic, dynamic.createString("minecraft:end"), dynamic.createMap((Map)ImmutableMap.of((Object)dynamic.createString("type"), (Object)dynamic.createString("minecraft:the_end"), (Object)dynamic.createString("seed"), (Object)dynamic.createLong(l)))).getValue()))));
    }

    private static <T> Map<Dynamic<T>, Dynamic<T>> method_28275(DynamicOps<T> dynamicOps, OptionalDynamic<T> optionalDynamic) {
        MutableInt mutableInt = new MutableInt(32);
        _snowman = new MutableInt(3);
        _snowman = new MutableInt(128);
        MutableBoolean _snowman2 = new MutableBoolean(false);
        HashMap _snowman3 = Maps.newHashMap();
        if (!optionalDynamic.result().isPresent()) {
            _snowman2.setTrue();
            _snowman3.put("minecraft:village", STRUCTURE_SPACING.get((Object)"minecraft:village"));
        }
        optionalDynamic.get("structures").flatMap(Dynamic::getMapValues).result().ifPresent(map2 -> map2.forEach((dynamic, dynamic2) -> dynamic2.getMapValues().result().ifPresent(map2 -> map2.forEach((dynamic2, dynamic3) -> {
            String string = dynamic.asString("");
            _snowman = dynamic2.asString("");
            _snowman = dynamic3.asString("");
            if ("stronghold".equals(string)) {
                _snowman2.setTrue();
                switch (_snowman) {
                    case "distance": {
                        mutableInt.setValue(StructureSeparationDataFix.method_28280(_snowman, mutableInt.getValue(), 1));
                        return;
                    }
                    case "spread": {
                        _snowman.setValue(StructureSeparationDataFix.method_28280(_snowman, _snowman.getValue(), 1));
                        return;
                    }
                    case "count": {
                        _snowman.setValue(StructureSeparationDataFix.method_28280(_snowman, _snowman.getValue(), 1));
                        return;
                    }
                }
                return;
            }
            switch (_snowman) {
                case "distance": {
                    switch (string) {
                        case "village": {
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:village", _snowman, 9);
                            return;
                        }
                        case "biome_1": {
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:desert_pyramid", _snowman, 9);
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:igloo", _snowman, 9);
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:jungle_pyramid", _snowman, 9);
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:swamp_hut", _snowman, 9);
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:pillager_outpost", _snowman, 9);
                            return;
                        }
                        case "endcity": {
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:endcity", _snowman, 1);
                            return;
                        }
                        case "mansion": {
                            StructureSeparationDataFix.method_28281(_snowman3, "minecraft:mansion", _snowman, 1);
                            return;
                        }
                    }
                    return;
                }
                case "separation": {
                    if ("oceanmonument".equals(string)) {
                        Information information = (Information)_snowman3.getOrDefault("minecraft:monument", STRUCTURE_SPACING.get((Object)"minecraft:monument"));
                        int _snowman2 = StructureSeparationDataFix.method_28280(_snowman, information.separation, 1);
                        _snowman3.put("minecraft:monument", new Information(_snowman2, information.separation, information.salt));
                    }
                    return;
                }
                case "spacing": {
                    if ("oceanmonument".equals(string)) {
                        StructureSeparationDataFix.method_28281(_snowman3, "minecraft:monument", _snowman, 1);
                    }
                    return;
                }
            }
        }))));
        ImmutableMap.Builder _snowman4 = ImmutableMap.builder();
        _snowman4.put((Object)optionalDynamic.createString("structures"), (Object)optionalDynamic.createMap(_snowman3.entrySet().stream().collect(Collectors.toMap(entry -> optionalDynamic.createString((String)entry.getKey()), entry -> ((Information)entry.getValue()).method_28288(dynamicOps)))));
        if (_snowman2.isTrue()) {
            _snowman4.put((Object)optionalDynamic.createString("stronghold"), (Object)optionalDynamic.createMap((Map)ImmutableMap.of((Object)optionalDynamic.createString("distance"), (Object)optionalDynamic.createInt(mutableInt.getValue().intValue()), (Object)optionalDynamic.createString("spread"), (Object)optionalDynamic.createInt(_snowman.getValue().intValue()), (Object)optionalDynamic.createString("count"), (Object)optionalDynamic.createInt(_snowman.getValue().intValue()))));
        }
        return _snowman4.build();
    }

    private static int method_28279(String string, int n) {
        return NumberUtils.toInt((String)string, (int)n);
    }

    private static int method_28280(String string, int n, int n2) {
        return Math.max(n2, StructureSeparationDataFix.method_28279(string, n));
    }

    private static void method_28281(Map<String, Information> map, String string, String string2, int n) {
        Information information = map.getOrDefault(string, (Information)STRUCTURE_SPACING.get((Object)string));
        int _snowman2 = StructureSeparationDataFix.method_28280(string2, information.spacing, n);
        map.put(string, new Information(_snowman2, information.separation, information.salt));
    }

    private static /* synthetic */ void method_28269(ImmutableMap.Builder builder, DynamicOps dynamicOps, String string) {
        builder.put(dynamicOps.createString("legacy_custom_options"), dynamicOps.createString(string));
    }

    static final class Information {
        public static final Codec<Information> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.INT.fieldOf("spacing").forGetter(information -> information.spacing), (App)Codec.INT.fieldOf("separation").forGetter(information -> information.separation), (App)Codec.INT.fieldOf("salt").forGetter(information -> information.salt)).apply((Applicative)instance, Information::new));
        private final int spacing;
        private final int separation;
        private final int salt;

        public Information(int spacing, int separation, int salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
        }

        public <T> Dynamic<T> method_28288(DynamicOps<T> dynamicOps) {
            return new Dynamic(dynamicOps, CODEC.encodeStart(dynamicOps, (Object)this).result().orElse(dynamicOps.emptyMap()));
        }
    }
}

