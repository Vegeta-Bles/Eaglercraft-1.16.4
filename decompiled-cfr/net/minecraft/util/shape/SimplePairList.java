/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.util.shape.PairList;

public final class SimplePairList
implements PairList {
    private final DoubleArrayList valueIndices;
    private final IntArrayList minValues;
    private final IntArrayList maxValues;

    protected SimplePairList(DoubleList first, DoubleList second, boolean includeFirstOnly, boolean includeSecondOnly) {
        int n = 0;
        _snowman = 0;
        double _snowman2 = Double.NaN;
        n2 = first.size();
        _snowman = second.size();
        _snowman = n2 + _snowman;
        this.valueIndices = new DoubleArrayList(_snowman);
        this.minValues = new IntArrayList(_snowman);
        this.maxValues = new IntArrayList(_snowman);
        while (true) {
            boolean bl = n < n2;
            boolean bl2 = _snowman = _snowman < _snowman;
            if (!bl && !_snowman) break;
            _snowman = bl && (!_snowman || first.getDouble(n) < second.getDouble(_snowman) + 1.0E-7);
            double d = _snowman = _snowman ? first.getDouble(n++) : second.getDouble(_snowman++);
            if ((n == 0 || !bl) && !_snowman && !includeSecondOnly || (_snowman == 0 || !_snowman) && _snowman && !includeFirstOnly) continue;
            if (!(_snowman2 >= _snowman - 1.0E-7)) {
                this.minValues.add(n - 1);
                this.maxValues.add(_snowman - 1);
                this.valueIndices.add(_snowman);
                _snowman2 = _snowman;
                continue;
            }
            if (this.valueIndices.isEmpty()) continue;
            this.minValues.set(this.minValues.size() - 1, n - 1);
            this.maxValues.set(this.maxValues.size() - 1, _snowman - 1);
        }
        if (this.valueIndices.isEmpty()) {
            int n2;
            this.valueIndices.add(Math.min(first.getDouble(n2 - 1), second.getDouble(_snowman - 1)));
        }
    }

    @Override
    public boolean forEachPair(PairList.Consumer predicate) {
        for (int i = 0; i < this.valueIndices.size() - 1; ++i) {
            if (predicate.merge(this.minValues.getInt(i), this.maxValues.getInt(i), i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public DoubleList getPairs() {
        return this.valueIndices;
    }
}

