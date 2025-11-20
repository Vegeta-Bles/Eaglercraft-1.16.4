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

public class HeightmapRenamingFix
extends DataFix {
    public HeightmapRenamingFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder _snowman2 = type.findField("Level");
        return this.fixTypeEverywhereTyped("HeightmapRenamingFix", type, typed2 -> typed2.updateTyped(_snowman2, typed -> typed.update(DSL.remainderFinder(), this::renameHeightmapTags)));
    }

    private Dynamic<?> renameHeightmapTags(Dynamic<?> dynamic) {
        Optional optional = dynamic.get("Heightmaps").result();
        if (!optional.isPresent()) {
            return dynamic;
        }
        Dynamic _snowman2 = (Dynamic)optional.get();
        _snowman = _snowman2.get("LIQUID").result();
        if (_snowman.isPresent()) {
            _snowman2 = _snowman2.remove("LIQUID");
            _snowman2 = _snowman2.set("WORLD_SURFACE_WG", (Dynamic)_snowman.get());
        }
        if ((_snowman = _snowman2.get("SOLID").result()).isPresent()) {
            _snowman2 = _snowman2.remove("SOLID");
            _snowman2 = _snowman2.set("OCEAN_FLOOR_WG", (Dynamic)_snowman.get());
            _snowman2 = _snowman2.set("OCEAN_FLOOR", (Dynamic)_snowman.get());
        }
        if ((_snowman = _snowman2.get("LIGHT").result()).isPresent()) {
            _snowman2 = _snowman2.remove("LIGHT");
            _snowman2 = _snowman2.set("LIGHT_BLOCKING", (Dynamic)_snowman.get());
        }
        if ((_snowman = _snowman2.get("RAIN").result()).isPresent()) {
            _snowman2 = _snowman2.remove("RAIN");
            _snowman2 = _snowman2.set("MOTION_BLOCKING", (Dynamic)_snowman.get());
            _snowman2 = _snowman2.set("MOTION_BLOCKING_NO_LEAVES", (Dynamic)_snowman.get());
        }
        return dynamic.set("Heightmaps", _snowman2);
    }
}

