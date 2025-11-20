/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;

public class ChunkStatusFix2
extends DataFix {
    private static final Map<String, String> statusMap = ImmutableMap.builder().put((Object)"structure_references", (Object)"empty").put((Object)"biomes", (Object)"empty").put((Object)"base", (Object)"surface").put((Object)"carved", (Object)"carvers").put((Object)"liquid_carved", (Object)"liquid_carvers").put((Object)"decorated", (Object)"features").put((Object)"lighted", (Object)"light").put((Object)"mobs_spawned", (Object)"spawn").put((Object)"finalized", (Object)"heightmaps").put((Object)"fullchunk", (Object)"full").build();

    public ChunkStatusFix2(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"Level", (Type)_snowman);
        return this.fixTypeEverywhereTyped("ChunkStatusFix2", type, this.getOutputSchema().getType(TypeReferences.CHUNK), typed2 -> typed2.updateTyped(_snowman2, typed -> {
            Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
            String _snowman2 = dynamic.get("Status").asString("empty");
            if (Objects.equals(_snowman2, _snowman = statusMap.getOrDefault(_snowman2, "empty"))) {
                return typed;
            }
            return typed.set(DSL.remainderFinder(), (Object)dynamic.set("Status", dynamic.createString(_snowman)));
        }));
    }
}

