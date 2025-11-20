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
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.datafixer.TypeReferences;

public class ChunkLightRemoveFix
extends DataFix {
    public ChunkLightRemoveFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"Level", (Type)_snowman);
        return this.fixTypeEverywhereTyped("ChunkLightRemoveFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), typed2 -> typed2.updateTyped(_snowman2, typed -> typed.update(DSL.remainderFinder(), dynamic -> dynamic.remove("isLightOn"))));
    }
}

