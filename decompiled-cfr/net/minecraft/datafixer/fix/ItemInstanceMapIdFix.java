/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemInstanceMapIdFix
extends DataFix {
    public ItemInstanceMapIdFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman2 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        OpticFinder _snowman3 = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", type, typed -> {
            Optional optional = typed.getOptional(_snowman2);
            if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:filled_map")) {
                Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
                Typed _snowman2 = typed.getOrCreateTyped(_snowman3);
                _snowman = (Dynamic)_snowman2.get(DSL.remainderFinder());
                _snowman = _snowman.set("map", _snowman.createInt(dynamic.get("Damage").asInt(0)));
                return typed.set(_snowman3, _snowman2.set(DSL.remainderFinder(), (Object)_snowman));
            }
            return typed;
        });
    }
}

