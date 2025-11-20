/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class PointOfInterestReorganizationFix
extends DataFix {
    public PointOfInterestReorganizationFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = DSL.named((String)TypeReferences.POI_CHUNK.typeName(), (Type)DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere("POI reorganization", type, dynamicOps -> pair -> pair.mapSecond(PointOfInterestReorganizationFix::reorganize));
    }

    private static <T> Dynamic<T> reorganize(Dynamic<T> _snowman52) {
        Dynamic _snowman52;
        HashMap hashMap = Maps.newHashMap();
        for (int i = 0; i < 16; ++i) {
            String string = String.valueOf(i);
            Optional _snowman2 = _snowman52.get(string).result();
            if (!_snowman2.isPresent()) continue;
            Dynamic _snowman3 = (Dynamic)_snowman2.get();
            Dynamic _snowman4 = _snowman52.createMap((Map)ImmutableMap.of((Object)_snowman52.createString("Records"), (Object)_snowman3));
            hashMap.put(_snowman52.createInt(i), _snowman4);
            _snowman52 = _snowman52.remove(string);
        }
        return _snowman52.set("Sections", _snowman52.createMap((Map)hashMap));
    }
}

