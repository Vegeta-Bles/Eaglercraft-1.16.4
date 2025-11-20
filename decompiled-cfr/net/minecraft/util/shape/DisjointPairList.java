/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.AbstractDoubleList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.shape.PairList;

public class DisjointPairList
extends AbstractDoubleList
implements PairList {
    private final DoubleList first;
    private final DoubleList second;
    private final boolean inverted;

    public DisjointPairList(DoubleList first, DoubleList second, boolean inverted) {
        this.first = first;
        this.second = second;
        this.inverted = inverted;
    }

    public int size() {
        return this.first.size() + this.second.size();
    }

    @Override
    public boolean forEachPair(PairList.Consumer predicate) {
        if (this.inverted) {
            return this.iterateSections((n, n2, n3) -> predicate.merge(n2, n, n3));
        }
        return this.iterateSections(predicate);
    }

    private boolean iterateSections(PairList.Consumer consumer) {
        int n = this.first.size() - 1;
        for (_snowman = 0; _snowman < n; ++_snowman) {
            if (consumer.merge(_snowman, -1, _snowman)) continue;
            return false;
        }
        if (!consumer.merge(n, -1, n)) {
            return false;
        }
        for (_snowman = 0; _snowman < this.second.size(); ++_snowman) {
            if (consumer.merge(n, _snowman, n + 1 + _snowman)) continue;
            return false;
        }
        return true;
    }

    public double getDouble(int position) {
        if (position < this.first.size()) {
            return this.first.getDouble(position);
        }
        return this.second.getDouble(position - this.first.size());
    }

    @Override
    public DoubleList getPairs() {
        return this;
    }
}

