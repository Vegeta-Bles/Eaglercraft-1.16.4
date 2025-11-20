/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.datafixer.fix.EntitySimpleTransformFix;

public class EntityCatSplitFix
extends EntitySimpleTransformFix {
    public EntityCatSplitFix(Schema outputSchema, boolean changesType) {
        super("EntityCatSplitFix", outputSchema, changesType);
    }

    @Override
    protected Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> dynamic2) {
        Dynamic dynamic2;
        if (Objects.equals("minecraft:ocelot", choice)) {
            int n = dynamic2.get("CatType").asInt(0);
            if (n == 0) {
                String string = dynamic2.get("Owner").asString("");
                _snowman = dynamic2.get("OwnerUUID").asString("");
                if (string.length() > 0 || _snowman.length() > 0) {
                    dynamic2.set("Trusting", dynamic2.createBoolean(true));
                }
            } else if (n > 0 && n < 4) {
                dynamic2 = dynamic2.set("CatType", dynamic2.createInt(n));
                dynamic2 = dynamic2.set("OwnerUUID", dynamic2.createString(dynamic2.get("OwnerUUID").asString("")));
                return Pair.of((Object)"minecraft:cat", (Object)dynamic2);
            }
        }
        return Pair.of((Object)choice, dynamic2);
    }
}

