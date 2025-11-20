/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.shorts.ShortArrayList
 *  it.unimi.dsi.fastutil.shorts.ShortList
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class ChunkToProtoChunkFix
extends DataFix {
    public ChunkToProtoChunkFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        _snowman = this.getOutputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        _snowman = _snowman.findFieldType("Level");
        _snowman = _snowman.findFieldType("TileTicks");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"Level", (Type)_snowman);
        OpticFinder _snowman3 = DSL.fieldFinder((String)"TileTicks", (Type)_snowman);
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhereTyped("ChunkToProtoChunkFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), typed -> typed.updateTyped(_snowman2, _snowman, typed2 -> {
            Dynamic _snowman4;
            Optional optional = typed2.getOptionalTyped(_snowman3).flatMap(typed -> typed.write().result()).flatMap(dynamic -> dynamic.asStreamOpt().result());
            Dynamic _snowman2 = (Dynamic)typed2.get(DSL.remainderFinder());
            boolean _snowman3 = _snowman2.get("TerrainPopulated").asBoolean(false) && (!_snowman2.get("LightPopulated").asNumber().result().isPresent() || _snowman2.get("LightPopulated").asBoolean(false));
            _snowman2 = _snowman2.set("Status", _snowman2.createString(_snowman3 ? "mobs_spawned" : "empty"));
            _snowman2 = _snowman2.set("hasLegacyStructureData", _snowman2.createBoolean(true));
            if (_snowman3) {
                Object object;
                Optional optional2 = _snowman2.get("Biomes").asByteBufferOpt().result();
                if (optional2.isPresent()) {
                    Object object2;
                    object = (ByteBuffer)optional2.get();
                    object2 = new int[256];
                    for (int i = 0; i < ((int[])object2).length; ++i) {
                        if (i >= ((Buffer)object).capacity()) continue;
                        object2[i] = ((ByteBuffer)object).get(i) & 0xFF;
                    }
                    _snowman2 = _snowman2.set("Biomes", _snowman2.createIntList(Arrays.stream((int[])object2)));
                }
                object = _snowman2;
                object2 = IntStream.range(0, 16).mapToObj(n -> new ShortArrayList()).collect(Collectors.toList());
                if (optional.isPresent()) {
                    ((Stream)optional.get()).forEach(arg_0 -> ChunkToProtoChunkFix.method_28186((List)object2, arg_0));
                    _snowman2 = _snowman2.set("ToBeTicked", _snowman2.createList(object2.stream().map(arg_0 -> ChunkToProtoChunkFix.method_28185((Dynamic)object, arg_0))));
                }
                _snowman4 = (Dynamic)DataFixUtils.orElse((Optional)typed2.set(DSL.remainderFinder(), (Object)_snowman2).write().result(), (Object)_snowman2);
            } else {
                _snowman4 = _snowman2;
            }
            return (Typed)((Pair)_snowman.readTyped(_snowman4).result().orElseThrow(() -> new IllegalStateException("Could not read the new chunk"))).getFirst();
        })), (TypeRewriteRule)this.writeAndRead("Structure biome inject", this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE), this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE)));
    }

    private static short method_15675(int n, int n2, int n3) {
        return (short)(n & 0xF | (n2 & 0xF) << 4 | (n3 & 0xF) << 8);
    }

    private static /* synthetic */ Dynamic method_28185(Dynamic dynamic, ShortList shortList) {
        return dynamic.createList(shortList.stream().map(arg_0 -> ((Dynamic)dynamic).createShort(arg_0)));
    }

    private static /* synthetic */ void method_28186(List list, Dynamic dynamic) {
        int n = dynamic.get("x").asInt(0);
        _snowman = dynamic.get("y").asInt(0);
        _snowman = dynamic.get("z").asInt(0);
        short _snowman2 = ChunkToProtoChunkFix.method_15675(n, _snowman, _snowman);
        ((ShortList)list.get(_snowman >> 4)).add(_snowman2);
    }
}

