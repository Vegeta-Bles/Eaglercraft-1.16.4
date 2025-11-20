/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class EntityHorseSaddleFix
extends ChoiceFix {
    public EntityHorseSaddleFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "EntityHorseSaddleFix", TypeReferences.ENTITY, "EntityHorse");
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        OpticFinder opticFinder = DSL.fieldFinder((String)"id", (Type)DSL.named((String)TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType()));
        Type _snowman2 = this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK);
        _snowman = DSL.fieldFinder((String)"SaddleItem", (Type)_snowman2);
        Optional _snowman3 = inputType.getOptionalTyped(_snowman);
        Dynamic _snowman4 = (Dynamic)inputType.get(DSL.remainderFinder());
        if (!_snowman3.isPresent() && _snowman4.get("Saddle").asBoolean(false)) {
            Typed typed = (Typed)_snowman2.pointTyped(inputType.getOps()).orElseThrow(IllegalStateException::new);
            typed = typed.set(opticFinder, (Object)Pair.of((Object)TypeReferences.ITEM_NAME.typeName(), (Object)"minecraft:saddle"));
            Dynamic _snowman5 = _snowman4.emptyMap();
            _snowman5 = _snowman5.set("Count", _snowman5.createByte((byte)1));
            _snowman5 = _snowman5.set("Damage", _snowman5.createShort((short)0));
            typed = typed.set(DSL.remainderFinder(), (Object)_snowman5);
            _snowman4.remove("Saddle");
            return inputType.set(_snowman, typed).set(DSL.remainderFinder(), (Object)_snowman4);
        }
        return inputType;
    }
}

