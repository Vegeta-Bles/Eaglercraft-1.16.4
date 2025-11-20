/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class SwimStatsRenameFix
extends DataFix {
    public SwimStatsRenameFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getOutputSchema().getType(TypeReferences.STATS);
        _snowman = this.getInputSchema().getType(TypeReferences.STATS);
        OpticFinder _snowman2 = _snowman.findField("stats");
        OpticFinder _snowman3 = _snowman2.type().findField("minecraft:custom");
        OpticFinder _snowman4 = IdentifierNormalizingSchema.getIdentifierType().finder();
        return this.fixTypeEverywhereTyped("SwimStatsRenameFix", _snowman, type, typed -> typed.updateTyped(_snowman2, typed2 -> typed2.updateTyped(_snowman3, typed -> typed.update(_snowman4, string -> {
            if (string.equals("minecraft:swim_one_cm")) {
                return "minecraft:walk_on_water_one_cm";
            }
            if (string.equals("minecraft:dive_one_cm")) {
                return "minecraft:walk_under_water_one_cm";
            }
            return string;
        }))));
    }
}

