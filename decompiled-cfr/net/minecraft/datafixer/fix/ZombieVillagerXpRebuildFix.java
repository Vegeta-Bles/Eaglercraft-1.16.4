/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.datafixer.fix.VillagerXpRebuildFix;

public class ZombieVillagerXpRebuildFix
extends ChoiceFix {
    public ZombieVillagerXpRebuildFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "Zombie Villager XP rebuild", TypeReferences.ENTITY, "minecraft:zombie_villager");
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        return inputType.update(DSL.remainderFinder(), dynamic2 -> {
            Dynamic dynamic2;
            Optional optional = dynamic2.get("Xp").asNumber().result();
            if (!optional.isPresent()) {
                int n = dynamic2.get("VillagerData").get("level").asInt(1);
                return dynamic2.set("Xp", dynamic2.createInt(VillagerXpRebuildFix.levelToXp(n)));
            }
            return dynamic2;
        });
    }
}

