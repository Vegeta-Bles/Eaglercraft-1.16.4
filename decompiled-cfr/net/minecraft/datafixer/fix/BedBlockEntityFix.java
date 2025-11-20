/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class BedBlockEntityFix
extends DataFix {
    public BedBlockEntityFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getOutputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        _snowman = _snowman.findFieldType("TileEntities");
        if (!(_snowman instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType _snowman2 = (List.ListType)_snowman;
        return this.method_15506(_snowman, _snowman2);
    }

    private <TE> TypeRewriteRule method_15506(Type<?> type, List.ListType<TE> listType) {
        Type type2 = listType.getElement();
        OpticFinder _snowman2 = DSL.fieldFinder((String)"Level", type);
        OpticFinder _snowman3 = DSL.fieldFinder((String)"TileEntities", listType);
        int _snowman4 = 416;
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhere("InjectBedBlockEntityType", (Type)this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY), (Type)this.getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY), dynamicOps -> pair -> pair), (TypeRewriteRule)this.fixTypeEverywhereTyped("BedBlockEntityInjecter", this.getOutputSchema().getType(TypeReferences.CHUNK), typed2 -> {
            Typed typed2;
            _snowman = typed2.getTyped(_snowman2);
            Dynamic dynamic2 = (Dynamic)_snowman.get(DSL.remainderFinder());
            int _snowman2 = dynamic2.get("xPos").asInt(0);
            int _snowman3 = dynamic2.get("zPos").asInt(0);
            ArrayList _snowman4 = Lists.newArrayList((Iterable)((Iterable)_snowman.getOrCreate(_snowman3)));
            List _snowman5 = dynamic2.get("Sections").asList(Function.identity());
            for (int i = 0; i < _snowman5.size(); ++i) {
                Dynamic dynamic3 = (Dynamic)_snowman5.get(i);
                int _snowman6 = dynamic3.get("Y").asInt(0);
                Stream<Integer> _snowman7 = dynamic3.get("Blocks").asStream().map(dynamic -> dynamic.asInt(0));
                int _snowman8 = 0;
                Iterator iterator = ((Iterable)_snowman7::iterator).iterator();
                while (iterator.hasNext()) {
                    int n = (Integer)iterator.next();
                    if (416 == (n & 0xFF) << 4) {
                        _snowman = _snowman8 & 0xF;
                        _snowman = _snowman8 >> 8 & 0xF;
                        _snowman = _snowman8 >> 4 & 0xF;
                        HashMap hashMap = Maps.newHashMap();
                        hashMap.put(dynamic3.createString("id"), dynamic3.createString("minecraft:bed"));
                        hashMap.put(dynamic3.createString("x"), dynamic3.createInt(_snowman + (_snowman2 << 4)));
                        hashMap.put(dynamic3.createString("y"), dynamic3.createInt(_snowman + (_snowman6 << 4)));
                        hashMap.put(dynamic3.createString("z"), dynamic3.createInt(_snowman + (_snowman3 << 4)));
                        hashMap.put(dynamic3.createString("color"), dynamic3.createShort((short)14));
                        _snowman4.add(((Pair)type2.read(dynamic3.createMap((Map)hashMap)).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity."))).getFirst());
                    }
                    ++_snowman8;
                }
            }
            if (!_snowman4.isEmpty()) {
                return typed2.set(_snowman2, _snowman.set(_snowman3, (Object)_snowman4));
            }
            return typed2;
        }));
    }
}

