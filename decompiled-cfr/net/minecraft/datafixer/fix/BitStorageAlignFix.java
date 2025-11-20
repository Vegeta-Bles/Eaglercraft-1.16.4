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
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
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
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class BitStorageAlignFix
extends DataFix {
    public BitStorageAlignFix(Schema outputSchema) {
        super(outputSchema, false);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"Level", (Type)_snowman);
        OpticFinder _snowman3 = _snowman2.type().findField("Sections");
        _snowman = ((List.ListType)_snowman3.type()).getElement();
        OpticFinder _snowman4 = DSL.typeFinder((Type)_snowman);
        _snowman = DSL.named((String)TypeReferences.BLOCK_STATE.typeName(), (Type)DSL.remainderType());
        OpticFinder _snowman5 = DSL.fieldFinder((String)"Palette", (Type)DSL.list((Type)_snowman));
        return this.fixTypeEverywhereTyped("BitStorageAlignFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), typed2 -> typed2.updateTyped(_snowman2, typed -> this.method_27775(BitStorageAlignFix.method_27774(_snowman3, _snowman4, _snowman5, typed))));
    }

    private Typed<?> method_27775(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), dynamic -> dynamic.update("Heightmaps", dynamic2 -> dynamic2.updateMapValues(pair -> pair.mapSecond(dynamic2 -> BitStorageAlignFix.method_27772(dynamic, dynamic2, 256, 9)))));
    }

    private static Typed<?> method_27774(OpticFinder<?> opticFinder, OpticFinder<?> opticFinder2, OpticFinder<List<Pair<String, Dynamic<?>>>> opticFinder3, Typed<?> typed) {
        return typed.updateTyped(opticFinder, typed2 -> typed2.updateTyped(opticFinder2, typed -> {
            int n = typed.getOptional(opticFinder3).map(list -> Math.max(4, DataFixUtils.ceillog2((int)list.size()))).orElse(0);
            if (n == 0 || MathHelper.isPowerOfTwo(n)) {
                return typed;
            }
            return typed.update(DSL.remainderFinder(), dynamic -> dynamic.update("BlockStates", dynamic2 -> BitStorageAlignFix.method_27772(dynamic, dynamic2, 4096, n)));
        }));
    }

    private static Dynamic<?> method_27772(Dynamic<?> dynamic, Dynamic<?> dynamic2, int n, int n2) {
        long[] lArray = dynamic2.asLongStream().toArray();
        _snowman = BitStorageAlignFix.method_27288(n, n2, lArray);
        return dynamic.createLongList(LongStream.of(_snowman));
    }

    public static long[] method_27288(int n, int n2, long[] lArray) {
        int n3 = lArray.length;
        if (n3 == 0) {
            return lArray;
        }
        long _snowman2 = (1L << n2) - 1L;
        _snowman = 64 / n2;
        _snowman = (n + _snowman - 1) / _snowman;
        long[] _snowman3 = new long[_snowman];
        _snowman = 0;
        _snowman = 0;
        long _snowman4 = 0L;
        _snowman = 0;
        long _snowman5 = lArray[0];
        long _snowman6 = n3 > 1 ? lArray[1] : 0L;
        for (_snowman = 0; _snowman < n; ++_snowman) {
            long _snowman7;
            _snowman = _snowman * n2;
            _snowman = _snowman >> 6;
            _snowman = (_snowman + 1) * n2 - 1 >> 6;
            n4 = _snowman ^ _snowman << 6;
            if (_snowman != _snowman) {
                _snowman5 = _snowman6;
                _snowman6 = _snowman + 1 < n3 ? lArray[_snowman + 1] : 0L;
                _snowman = _snowman;
            }
            if (_snowman == _snowman) {
                _snowman7 = _snowman5 >>> n4 & _snowman2;
            } else {
                int n4;
                _snowman = 64 - n4;
                _snowman7 = (_snowman5 >>> n4 | _snowman6 << _snowman) & _snowman2;
            }
            _snowman = _snowman + n2;
            if (_snowman >= 64) {
                _snowman3[_snowman++] = _snowman4;
                _snowman4 = _snowman7;
                _snowman = n2;
                continue;
            }
            _snowman4 |= _snowman7 << _snowman;
            _snowman = _snowman;
        }
        if (_snowman4 != 0L) {
            _snowman3[_snowman] = _snowman4;
        }
        return _snowman3;
    }
}

