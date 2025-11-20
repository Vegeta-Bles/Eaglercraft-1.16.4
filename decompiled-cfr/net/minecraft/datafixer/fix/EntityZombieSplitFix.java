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

public class EntityZombieSplitFix
extends EntitySimpleTransformFix {
    public EntityZombieSplitFix(Schema outputSchema, boolean changesType) {
        super("EntityZombieSplitFix", outputSchema, changesType);
    }

    @Override
    protected Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> dynamic2) {
        Dynamic dynamic2;
        if (Objects.equals("Zombie", choice)) {
            String string = "Zombie";
            int _snowman2 = dynamic2.get("ZombieType").asInt(0);
            switch (_snowman2) {
                default: {
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    string = "ZombieVillager";
                    dynamic2 = dynamic2.set("Profession", dynamic2.createInt(_snowman2 - 1));
                    break;
                }
                case 6: {
                    string = "Husk";
                }
            }
            dynamic2 = dynamic2.remove("ZombieType");
            return Pair.of((Object)string, (Object)dynamic2);
        }
        return Pair.of((Object)choice, dynamic2);
    }
}

