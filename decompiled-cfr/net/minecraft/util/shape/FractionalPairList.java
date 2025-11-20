/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.math.IntMath
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.util.shape;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.shape.FractionalDoubleList;
import net.minecraft.util.shape.PairList;
import net.minecraft.util.shape.VoxelShapes;

public final class FractionalPairList
implements PairList {
    private final FractionalDoubleList mergedList;
    private final int firstSectionCount;
    private final int secondSectionCount;
    private final int gcd;

    FractionalPairList(int firstSectionCount, int secondSectionCount) {
        this.mergedList = new FractionalDoubleList((int)VoxelShapes.lcm(firstSectionCount, secondSectionCount));
        this.firstSectionCount = firstSectionCount;
        this.secondSectionCount = secondSectionCount;
        this.gcd = IntMath.gcd((int)firstSectionCount, (int)secondSectionCount);
    }

    @Override
    public boolean forEachPair(PairList.Consumer predicate) {
        int n = this.firstSectionCount / this.gcd;
        _snowman = this.secondSectionCount / this.gcd;
        for (_snowman = 0; _snowman <= this.mergedList.size(); ++_snowman) {
            if (predicate.merge(_snowman / _snowman, _snowman / n, _snowman)) continue;
            return false;
        }
        return true;
    }

    @Override
    public DoubleList getPairs() {
        return this.mergedList;
    }
}

