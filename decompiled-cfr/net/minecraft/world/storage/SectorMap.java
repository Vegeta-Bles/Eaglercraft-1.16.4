/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.storage;

import java.util.BitSet;

public class SectorMap {
    private final BitSet field_20433 = new BitSet();

    public void allocate(int n, int n2) {
        this.field_20433.set(n, n + n2);
    }

    public void free(int n, int n2) {
        this.field_20433.clear(n, n + n2);
    }

    public int allocate(int n) {
        _snowman = 0;
        while (true) {
            if ((_snowman = this.field_20433.nextSetBit(_snowman = this.field_20433.nextClearBit(_snowman))) == -1 || _snowman - _snowman >= n) {
                this.allocate(_snowman, n);
                return _snowman;
            }
            _snowman = _snowman;
        }
    }
}

