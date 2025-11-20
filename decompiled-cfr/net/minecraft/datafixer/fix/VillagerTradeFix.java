/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class VillagerTradeFix
extends ChoiceFix {
    public VillagerTradeFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "Villager trade fix", TypeReferences.ENTITY, "minecraft:villager");
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        OpticFinder opticFinder = inputType.getType().findField("Offers");
        _snowman = opticFinder.type().findField("Recipes");
        Type _snowman2 = _snowman.type();
        if (!(_snowman2 instanceof List.ListType)) {
            throw new IllegalStateException("Recipes are expected to be a list.");
        }
        List.ListType _snowman3 = (List.ListType)_snowman2;
        Type _snowman4 = _snowman3.getElement();
        _snowman = DSL.typeFinder((Type)_snowman4);
        _snowman = _snowman4.findField("buy");
        _snowman = _snowman4.findField("buyB");
        _snowman = _snowman4.findField("sell");
        _snowman = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        Function<Typed, Typed> _snowman5 = typed -> this.fixPumpkinTrade((OpticFinder<Pair<String, String>>)_snowman, (Typed<?>)typed);
        return inputType.updateTyped(opticFinder, typed -> typed.updateTyped(_snowman, typed2 -> typed2.updateTyped(_snowman, typed -> typed.updateTyped(_snowman, _snowman5).updateTyped(_snowman, _snowman5).updateTyped(_snowman, _snowman5))));
    }

    private Typed<?> fixPumpkinTrade(OpticFinder<Pair<String, String>> opticFinder, Typed<?> typed) {
        return typed.update(opticFinder, pair -> pair.mapSecond(string -> Objects.equals(string, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : string));
    }
}

