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
 *  com.mojang.datafixers.types.templates.List$ListType
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
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class VillagerXpRebuildFix
extends DataFix {
    private static final int[] LEVEL_TO_XP = new int[]{0, 10, 50, 100, 150};

    public static int levelToXp(int level) {
        return LEVEL_TO_XP[MathHelper.clamp(level - 1, 0, LEVEL_TO_XP.length - 1)];
    }

    public VillagerXpRebuildFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "minecraft:villager");
        OpticFinder _snowman2 = DSL.namedChoice((String)"minecraft:villager", (Type)type);
        OpticFinder _snowman3 = type.findField("Offers");
        _snowman = _snowman3.type();
        OpticFinder _snowman4 = _snowman.findField("Recipes");
        List.ListType _snowman5 = (List.ListType)_snowman4.type();
        OpticFinder _snowman6 = _snowman5.getElement().finder();
        return this.fixTypeEverywhereTyped("Villager level and xp rebuild", this.getInputSchema().getType(TypeReferences.ENTITY), typed -> typed.updateTyped(_snowman2, type, typed2 -> {
            Dynamic dynamic = (Dynamic)typed2.get(DSL.remainderFinder());
            int _snowman2 = dynamic.get("VillagerData").get("level").asInt(0);
            Typed<?> _snowman3 = typed2;
            if ((_snowman2 == 0 || _snowman2 == 1) && (_snowman2 = MathHelper.clamp((_snowman = typed2.getOptionalTyped(_snowman3).flatMap(typed -> typed.getOptionalTyped(_snowman4)).map(typed -> typed.getAllTyped(_snowman6).size()).orElse(0).intValue()) / 2, 1, 5)) > 1) {
                _snowman3 = VillagerXpRebuildFix.method_20487(_snowman3, _snowman2);
            }
            if (!(_snowman = dynamic.get("Xp").asNumber().result()).isPresent()) {
                _snowman3 = VillagerXpRebuildFix.method_20490(_snowman3, _snowman2);
            }
            return _snowman3;
        }));
    }

    private static Typed<?> method_20487(Typed<?> typed, int n) {
        return typed.update(DSL.remainderFinder(), dynamic2 -> dynamic2.update("VillagerData", dynamic -> dynamic.set("level", dynamic.createInt(n))));
    }

    private static Typed<?> method_20490(Typed<?> typed, int n) {
        _snowman = VillagerXpRebuildFix.levelToXp(n);
        return typed.update(DSL.remainderFinder(), dynamic -> dynamic.set("Xp", dynamic.createInt(_snowman)));
    }
}

