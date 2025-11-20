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
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ItemCustomNameToComponentFix
extends DataFix {
    public ItemCustomNameToComponentFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private Dynamic<?> fixCustomName(Dynamic<?> dynamic2) {
        Dynamic<?> dynamic2;
        Optional optional = dynamic2.get("display").result();
        if (optional.isPresent()) {
            Dynamic dynamic3 = (Dynamic)optional.get();
            Optional _snowman2 = dynamic3.get("Name").asString().result();
            if (_snowman2.isPresent()) {
                dynamic3 = dynamic3.set("Name", dynamic3.createString(Text.Serializer.toJson(new LiteralText((String)_snowman2.get()))));
            } else {
                Optional optional2 = dynamic3.get("LocName").asString().result();
                if (optional2.isPresent()) {
                    dynamic3 = dynamic3.set("Name", dynamic3.createString(Text.Serializer.toJson(new TranslatableText((String)optional2.get()))));
                    dynamic3 = dynamic3.remove("LocName");
                }
            }
            return dynamic2.set("display", dynamic3);
        }
        return dynamic2;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman2 = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemCustomNameToComponentFix", type, typed2 -> typed2.updateTyped(_snowman2, typed -> typed.update(DSL.remainderFinder(), this::fixCustomName)));
    }
}

