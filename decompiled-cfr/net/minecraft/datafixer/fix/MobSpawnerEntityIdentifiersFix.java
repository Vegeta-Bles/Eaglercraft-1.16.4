/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class MobSpawnerEntityIdentifiersFix
extends DataFix {
    public MobSpawnerEntityIdentifiersFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private Dynamic<?> fixSpawner(Dynamic<?> dynamic3) {
        Optional optional;
        Dynamic dynamic3;
        if (!"MobSpawner".equals(dynamic3.get("id").asString(""))) {
            return dynamic3;
        }
        Optional optional2 = dynamic3.get("EntityId").asString().result();
        if (optional2.isPresent()) {
            optional = (Dynamic)DataFixUtils.orElse((Optional)dynamic3.get("SpawnData").result(), (Object)dynamic3.emptyMap());
            optional = optional.set("id", optional.createString(((String)optional2.get()).isEmpty() ? "Pig" : (String)optional2.get()));
            dynamic3 = dynamic3.set("SpawnData", (Dynamic)optional);
            dynamic3 = dynamic3.remove("EntityId");
        }
        if ((optional = dynamic3.get("SpawnPotentials").asStreamOpt().result()).isPresent()) {
            dynamic3 = dynamic3.set("SpawnPotentials", dynamic3.createList(((Stream)optional.get()).map(dynamic2 -> {
                Dynamic dynamic2;
                Optional optional = dynamic2.get("Type").asString().result();
                if (optional.isPresent()) {
                    Dynamic dynamic3 = ((Dynamic)DataFixUtils.orElse((Optional)dynamic2.get("Properties").result(), (Object)dynamic2.emptyMap())).set("id", dynamic2.createString((String)optional.get()));
                    return dynamic2.set("Entity", dynamic3).remove("Type").remove("Properties");
                }
                return dynamic2;
            })));
        }
        return dynamic3;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getOutputSchema().getType(TypeReferences.UNTAGGED_SPAWNER);
        return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(TypeReferences.UNTAGGED_SPAWNER), type, typed -> {
            Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
            DataResult _snowman2 = type.readTyped(this.fixSpawner(dynamic = dynamic.set("id", dynamic.createString("MobSpawner"))));
            if (!_snowman2.result().isPresent()) {
                return typed;
            }
            return (Typed)((Pair)_snowman2.result().get()).getFirst();
        });
    }
}

