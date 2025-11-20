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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.BlockStateFlattening;
import net.minecraft.datafixer.fix.ChoiceFix;

public class BlockEntityBlockStateFix
extends ChoiceFix {
    public BlockEntityBlockStateFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "BlockEntityBlockStateFix", TypeReferences.BLOCK_ENTITY, "minecraft:piston");
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        Type type = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:piston");
        _snowman = type.findFieldType("blockState");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"blockState", (Type)_snowman);
        Dynamic _snowman3 = (Dynamic)inputType.get(DSL.remainderFinder());
        int _snowman4 = _snowman3.get("blockId").asInt(0);
        _snowman3 = _snowman3.remove("blockId");
        int _snowman5 = _snowman3.get("blockData").asInt(0) & 0xF;
        _snowman3 = _snowman3.remove("blockData");
        Dynamic<?> _snowman6 = BlockStateFlattening.lookupState(_snowman4 << 4 | _snowman5);
        Typed _snowman7 = (Typed)type.pointTyped(inputType.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
        return _snowman7.set(DSL.remainderFinder(), (Object)_snowman3).set(_snowman2, (Typed)((Pair)_snowman.readTyped(_snowman6).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag."))).getFirst());
    }
}

