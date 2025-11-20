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

public class OminousBannerItemRenameFix
extends DataFix {
    public OminousBannerItemRenameFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private Dynamic<?> fixBannerName(Dynamic<?> dynamic) {
        Optional optional = dynamic.get("display").result();
        if (optional.isPresent()) {
            Dynamic _snowman3 = (Dynamic)optional.get();
            Optional _snowman2 = _snowman3.get("Name").asString().result();
            if (_snowman2.isPresent()) {
                String string = (String)_snowman2.get();
                string = string.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
                _snowman3 = _snowman3.set("Name", _snowman3.createString(string));
            }
            return dynamic.set("display", _snowman3);
        }
        return dynamic;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman2 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        OpticFinder _snowman3 = type.findField("tag");
        return this.fixTypeEverywhereTyped("OminousBannerRenameFix", type, typed -> {
            Optional optional = typed.getOptional(_snowman2);
            if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:white_banner") && (_snowman = typed.getOptionalTyped(_snowman3)).isPresent()) {
                Typed typed2 = (Typed)_snowman.get();
                Dynamic _snowman2 = (Dynamic)typed2.get(DSL.remainderFinder());
                return typed.set(_snowman3, typed2.set(DSL.remainderFinder(), this.fixBannerName(_snowman2)));
            }
            return typed;
        });
    }
}

