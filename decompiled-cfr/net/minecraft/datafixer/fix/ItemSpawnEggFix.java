/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemSpawnEggFix
extends DataFix {
    private static final String[] DAMAGE_TO_ENTITY_IDS = (String[])DataFixUtils.make((Object)new String[256], stringArray -> {
        stringArray[1] = "Item";
        stringArray[2] = "XPOrb";
        stringArray[7] = "ThrownEgg";
        stringArray[8] = "LeashKnot";
        stringArray[9] = "Painting";
        stringArray[10] = "Arrow";
        stringArray[11] = "Snowball";
        stringArray[12] = "Fireball";
        stringArray[13] = "SmallFireball";
        stringArray[14] = "ThrownEnderpearl";
        stringArray[15] = "EyeOfEnderSignal";
        stringArray[16] = "ThrownPotion";
        stringArray[17] = "ThrownExpBottle";
        stringArray[18] = "ItemFrame";
        stringArray[19] = "WitherSkull";
        stringArray[20] = "PrimedTnt";
        stringArray[21] = "FallingSand";
        stringArray[22] = "FireworksRocketEntity";
        stringArray[23] = "TippedArrow";
        stringArray[24] = "SpectralArrow";
        stringArray[25] = "ShulkerBullet";
        stringArray[26] = "DragonFireball";
        stringArray[30] = "ArmorStand";
        stringArray[41] = "Boat";
        stringArray[42] = "MinecartRideable";
        stringArray[43] = "MinecartChest";
        stringArray[44] = "MinecartFurnace";
        stringArray[45] = "MinecartTNT";
        stringArray[46] = "MinecartHopper";
        stringArray[47] = "MinecartSpawner";
        stringArray[40] = "MinecartCommandBlock";
        stringArray[48] = "Mob";
        stringArray[49] = "Monster";
        stringArray[50] = "Creeper";
        stringArray[51] = "Skeleton";
        stringArray[52] = "Spider";
        stringArray[53] = "Giant";
        stringArray[54] = "Zombie";
        stringArray[55] = "Slime";
        stringArray[56] = "Ghast";
        stringArray[57] = "PigZombie";
        stringArray[58] = "Enderman";
        stringArray[59] = "CaveSpider";
        stringArray[60] = "Silverfish";
        stringArray[61] = "Blaze";
        stringArray[62] = "LavaSlime";
        stringArray[63] = "EnderDragon";
        stringArray[64] = "WitherBoss";
        stringArray[65] = "Bat";
        stringArray[66] = "Witch";
        stringArray[67] = "Endermite";
        stringArray[68] = "Guardian";
        stringArray[69] = "Shulker";
        stringArray[90] = "Pig";
        stringArray[91] = "Sheep";
        stringArray[92] = "Cow";
        stringArray[93] = "Chicken";
        stringArray[94] = "Squid";
        stringArray[95] = "Wolf";
        stringArray[96] = "MushroomCow";
        stringArray[97] = "SnowMan";
        stringArray[98] = "Ozelot";
        stringArray[99] = "VillagerGolem";
        stringArray[100] = "EntityHorse";
        stringArray[101] = "Rabbit";
        stringArray[120] = "Villager";
        stringArray[200] = "EnderCrystal";
    });

    public ItemSpawnEggFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        Type _snowman2 = schema.getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman3 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        OpticFinder _snowman4 = DSL.fieldFinder((String)"id", (Type)DSL.string());
        OpticFinder _snowman5 = _snowman2.findField("tag");
        OpticFinder _snowman6 = _snowman5.type().findField("EntityTag");
        OpticFinder _snowman7 = DSL.typeFinder((Type)schema.getTypeRaw(TypeReferences.ENTITY));
        Type _snowman8 = this.getOutputSchema().getTypeRaw(TypeReferences.ENTITY);
        return this.fixTypeEverywhereTyped("ItemSpawnEggFix", _snowman2, typed2 -> {
            Optional optional = typed2.getOptional(_snowman3);
            if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:spawn_egg")) {
                Dynamic _snowman10 = (Dynamic)typed2.get(DSL.remainderFinder());
                short _snowman2 = _snowman10.get("Damage").asShort((short)0);
                Optional _snowman3 = typed2.getOptionalTyped(_snowman5);
                Optional _snowman4 = _snowman3.flatMap(typed -> typed.getOptionalTyped(_snowman6));
                Optional _snowman5 = _snowman4.flatMap(typed -> typed.getOptionalTyped(_snowman7));
                Optional _snowman6 = _snowman5.flatMap(typed -> typed.getOptional(_snowman4));
                Typed _snowman7 = typed2;
                String _snowman8 = DAMAGE_TO_ENTITY_IDS[_snowman2 & 0xFF];
                if (!(_snowman8 == null || _snowman6.isPresent() && Objects.equals(_snowman6.get(), _snowman8))) {
                    Typed typed3 = typed2.getOrCreateTyped(_snowman5);
                    _snowman = typed3.getOrCreateTyped(_snowman6);
                    _snowman = _snowman.getOrCreateTyped(_snowman7);
                    Dynamic _snowman9 = _snowman10;
                    _snowman = (Typed)((Pair)_snowman.write().flatMap(dynamic2 -> _snowman8.readTyped(dynamic2.set("id", _snowman9.createString(_snowman8)))).result().orElseThrow(() -> new IllegalStateException("Could not parse new entity"))).getFirst();
                    _snowman7 = _snowman7.set(_snowman5, typed3.set(_snowman6, _snowman.set(_snowman7, _snowman)));
                }
                if (_snowman2 != 0) {
                    _snowman10 = _snowman10.set("Damage", _snowman10.createShort((short)0));
                    _snowman7 = _snowman7.set(DSL.remainderFinder(), (Object)_snowman10);
                }
                return _snowman7;
            }
            return typed2;
        });
    }
}

