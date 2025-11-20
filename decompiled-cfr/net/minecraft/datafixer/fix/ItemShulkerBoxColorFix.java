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
 *  com.mojang.datafixers.util.Pair
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
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemShulkerBoxColorFix
extends DataFix {
    public static final String[] COLORED_SHULKER_BOX_IDS = new String[]{"minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:silver_shulker_box", "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box", "minecraft:red_shulker_box", "minecraft:black_shulker_box"};

    public ItemShulkerBoxColorFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder _snowman2 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        OpticFinder _snowman3 = type.findField("tag");
        OpticFinder _snowman4 = _snowman3.type().findField("BlockEntityTag");
        return this.fixTypeEverywhereTyped("ItemShulkerBoxColorFix", type, typed -> {
            Optional optional = typed.getOptional(_snowman2);
            if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:shulker_box") && (_snowman = typed.getOptionalTyped(_snowman3)).isPresent() && (_snowman = (_snowman = (Typed)_snowman.get()).getOptionalTyped(_snowman4)).isPresent()) {
                Typed typed2 = (Typed)_snowman.get();
                Dynamic _snowman2 = (Dynamic)typed2.get(DSL.remainderFinder());
                int _snowman3 = _snowman2.get("Color").asInt(0);
                _snowman2.remove("Color");
                return typed.set(_snowman3, _snowman.set(_snowman4, typed2.set(DSL.remainderFinder(), (Object)_snowman2))).set(_snowman2, (Object)Pair.of((Object)TypeReferences.ITEM_NAME.typeName(), (Object)COLORED_SHULKER_BOX_IDS[_snowman3 % 16]));
            }
            return typed;
        });
    }
}

