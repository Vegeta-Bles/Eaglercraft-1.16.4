/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.validate;

import net.minecraft.data.SnbtProvider;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.Structure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureValidatorProvider
implements SnbtProvider.Tweaker {
    private static final Logger field_24617 = LogManager.getLogger();

    @Override
    public CompoundTag write(String name, CompoundTag nbt) {
        if (name.startsWith("data/minecraft/structures/")) {
            return StructureValidatorProvider.update(name, StructureValidatorProvider.addDataVersion(nbt));
        }
        return nbt;
    }

    private static CompoundTag addDataVersion(CompoundTag nbt) {
        if (!nbt.contains("DataVersion", 99)) {
            nbt.putInt("DataVersion", 500);
        }
        return nbt;
    }

    private static CompoundTag update(String string, CompoundTag compoundTag) {
        Structure structure = new Structure();
        int _snowman2 = compoundTag.getInt("DataVersion");
        int _snowman3 = 2532;
        if (_snowman2 < 2532) {
            field_24617.warn("SNBT Too old, do not forget to update: " + _snowman2 + " < " + 2532 + ": " + string);
        }
        CompoundTag _snowman4 = NbtHelper.update(Schemas.getFixer(), DataFixTypes.STRUCTURE, compoundTag, _snowman2);
        structure.fromTag(_snowman4);
        return structure.toTag(new CompoundTag());
    }
}

