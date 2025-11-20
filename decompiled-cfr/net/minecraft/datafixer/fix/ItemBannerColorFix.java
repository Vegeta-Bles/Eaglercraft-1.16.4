/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
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

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemBannerColorFix
extends DataFix {
    public ItemBannerColorFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman2 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        OpticFinder _snowman3 = type.findField("tag");
        OpticFinder _snowman4 = _snowman3.type().findField("BlockEntityTag");
        return this.fixTypeEverywhereTyped("ItemBannerColorFix", type, typed -> {
            Optional optional = typed.getOptional(_snowman2);
            if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:banner")) {
                Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
                Optional _snowman2 = typed.getOptionalTyped(_snowman3);
                if (_snowman2.isPresent() && (_snowman = (_snowman = (Typed)_snowman2.get()).getOptionalTyped(_snowman4)).isPresent()) {
                    Typed typed2 = (Typed)_snowman.get();
                    Dynamic _snowman3 = (Dynamic)_snowman.get(DSL.remainderFinder());
                    Dynamic _snowman4 = (Dynamic)typed2.getOrCreate(DSL.remainderFinder());
                    if (_snowman4.get("Base").asNumber().result().isPresent()) {
                        dynamic = dynamic.set("Damage", dynamic.createShort((short)(_snowman4.get("Base").asInt(0) & 0xF)));
                        Optional _snowman5 = _snowman3.get("display").result();
                        if (_snowman5.isPresent() && Objects.equals(_snowman = (Dynamic)_snowman5.get(), _snowman = _snowman.createMap((Map)ImmutableMap.of((Object)_snowman.createString("Lore"), (Object)_snowman.createList(Stream.of(_snowman.createString("(+NBT"))))))) {
                            return typed.set(DSL.remainderFinder(), (Object)dynamic);
                        }
                        _snowman4.remove("Base");
                        return typed.set(DSL.remainderFinder(), (Object)dynamic).set(_snowman3, _snowman.set(_snowman4, typed2.set(DSL.remainderFinder(), (Object)_snowman4)));
                    }
                }
                return typed.set(DSL.remainderFinder(), (Object)dynamic);
            }
            return typed;
        });
    }
}

