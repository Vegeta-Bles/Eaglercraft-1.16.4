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
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.datafixer.fix.ItemIdFix;
import net.minecraft.datafixer.fix.ItemInstanceTheFlatteningFix;

public class BlockEntityJukeboxFix
extends ChoiceFix {
    public BlockEntityJukeboxFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "BlockEntityJukeboxFix", TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        Type type = this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
        _snowman = type.findFieldType("RecordItem");
        OpticFinder _snowman2 = DSL.fieldFinder((String)"RecordItem", (Type)_snowman);
        Dynamic _snowman3 = (Dynamic)inputType.get(DSL.remainderFinder());
        int _snowman4 = _snowman3.get("Record").asInt(0);
        if (_snowman4 > 0) {
            _snowman3.remove("Record");
            String string = ItemInstanceTheFlatteningFix.getItem(ItemIdFix.fromId(_snowman4), 0);
            if (string != null) {
                Dynamic dynamic = _snowman3.emptyMap();
                dynamic = dynamic.set("id", dynamic.createString(string));
                dynamic = dynamic.set("Count", dynamic.createByte((byte)1));
                return inputType.set(_snowman2, (Typed)((Pair)_snowman.readTyped(dynamic).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()).set(DSL.remainderFinder(), (Object)_snowman3);
            }
        }
        return inputType;
    }
}

