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
import net.minecraft.datafixer.TypeReferences;

public class HangingEntityFix
extends DataFix {
    private static final int[][] OFFSETS = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

    public HangingEntityFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private Dynamic<?> fixDecorationPosition(Dynamic<?> _snowman32, boolean isPainting, boolean isItemFrame) {
        Dynamic _snowman32;
        if ((isPainting || isItemFrame) && !_snowman32.get("Facing").asNumber().result().isPresent()) {
            int n;
            if (_snowman32.get("Direction").asNumber().result().isPresent()) {
                n = _snowman32.get("Direction").asByte((byte)0) % OFFSETS.length;
                int[] _snowman2 = OFFSETS[n];
                _snowman32 = _snowman32.set("TileX", _snowman32.createInt(_snowman32.get("TileX").asInt(0) + _snowman2[0]));
                _snowman32 = _snowman32.set("TileY", _snowman32.createInt(_snowman32.get("TileY").asInt(0) + _snowman2[1]));
                _snowman32 = _snowman32.set("TileZ", _snowman32.createInt(_snowman32.get("TileZ").asInt(0) + _snowman2[2]));
                _snowman32 = _snowman32.remove("Direction");
                if (isItemFrame && _snowman32.get("ItemRotation").asNumber().result().isPresent()) {
                    _snowman32 = _snowman32.set("ItemRotation", _snowman32.createByte((byte)(_snowman32.get("ItemRotation").asByte((byte)0) * 2)));
                }
            } else {
                n = _snowman32.get("Dir").asByte((byte)0) % OFFSETS.length;
                _snowman32 = _snowman32.remove("Dir");
            }
            _snowman32 = _snowman32.set("Facing", _snowman32.createByte((byte)n));
        }
        return _snowman32;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "Painting");
        OpticFinder _snowman2 = DSL.namedChoice((String)"Painting", (Type)type);
        _snowman = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "ItemFrame");
        OpticFinder _snowman3 = DSL.namedChoice((String)"ItemFrame", (Type)_snowman);
        _snowman = this.getInputSchema().getType(TypeReferences.ENTITY);
        TypeRewriteRule _snowman4 = this.fixTypeEverywhereTyped("EntityPaintingFix", _snowman, typed2 -> typed2.updateTyped(_snowman2, type, typed -> typed.update(DSL.remainderFinder(), dynamic -> this.fixDecorationPosition((Dynamic<?>)dynamic, true, false))));
        TypeRewriteRule _snowman5 = this.fixTypeEverywhereTyped("EntityItemFrameFix", _snowman, typed2 -> typed2.updateTyped(_snowman3, _snowman, typed -> typed.update(DSL.remainderFinder(), dynamic -> this.fixDecorationPosition((Dynamic<?>)dynamic, false, true))));
        return TypeRewriteRule.seq((TypeRewriteRule)_snowman4, (TypeRewriteRule)_snowman5);
    }
}

