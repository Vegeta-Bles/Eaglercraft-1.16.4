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
import java.util.Random;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;

public class EntityZombieVillagerTypeFix
extends ChoiceFix {
    private static final Random RANDOM = new Random();

    public EntityZombieVillagerTypeFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "EntityZombieVillagerTypeFix", TypeReferences.ENTITY, "Zombie");
    }

    public Dynamic<?> fixZombieType(Dynamic<?> _snowman22) {
        Dynamic _snowman22;
        if (_snowman22.get("IsVillager").asBoolean(false)) {
            if (!_snowman22.get("ZombieType").result().isPresent()) {
                int n = this.clampType(_snowman22.get("VillagerProfession").asInt(-1));
                if (n == -1) {
                    n = this.clampType(RANDOM.nextInt(6));
                }
                _snowman22 = _snowman22.set("ZombieType", _snowman22.createInt(n));
            }
            _snowman22 = _snowman22.remove("IsVillager");
        }
        return _snowman22;
    }

    private int clampType(int type) {
        if (type < 0 || type >= 6) {
            return -1;
        }
        return type;
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        return inputType.update(DSL.remainderFinder(), this::fixZombieType);
    }
}

