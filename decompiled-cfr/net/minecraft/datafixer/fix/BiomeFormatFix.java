/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.datafixer.TypeReferences;

public class BiomeFormatFix
extends DataFix {
    public BiomeFormatFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder _snowman2 = type.findField("Level");
        return this.fixTypeEverywhereTyped("Leaves fix", type, typed2 -> typed2.updateTyped(_snowman2, typed -> typed.update(DSL.remainderFinder(), dynamic2 -> {
            Dynamic dynamic2;
            int n;
            Optional optional = dynamic2.get("Biomes").asIntStreamOpt().result();
            if (!optional.isPresent()) {
                return dynamic2;
            }
            int[] _snowman2 = ((IntStream)optional.get()).toArray();
            int[] _snowman3 = new int[1024];
            for (n = 0; n < 4; ++n) {
                for (_snowman = 0; _snowman < 4; ++_snowman) {
                    _snowman = (n << 2) + 2;
                    _snowman = (_snowman << 2) + 2;
                    _snowman = _snowman << 4 | _snowman;
                    _snowman3[n << 2 | _snowman] = _snowman < _snowman2.length ? _snowman2[_snowman] : -1;
                }
            }
            for (n = 1; n < 64; ++n) {
                System.arraycopy(_snowman3, 0, _snowman3, n * 16, 16);
            }
            return dynamic2.set("Biomes", dynamic2.createIntList(Arrays.stream(_snowman3)));
        })));
    }
}

