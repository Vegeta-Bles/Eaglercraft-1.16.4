/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.structure;

import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class MarginedStructureStart<C extends FeatureConfig>
extends StructureStart<C> {
    public MarginedStructureStart(StructureFeature<C> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
        super(structureFeature, n, n2, blockBox, n3, l);
    }

    @Override
    protected void setBoundingBoxFromChildren() {
        super.setBoundingBoxFromChildren();
        int n = 12;
        this.boundingBox.minX -= 12;
        this.boundingBox.minY -= 12;
        this.boundingBox.minZ -= 12;
        this.boundingBox.maxX += 12;
        this.boundingBox.maxY += 12;
        this.boundingBox.maxZ += 12;
    }
}

