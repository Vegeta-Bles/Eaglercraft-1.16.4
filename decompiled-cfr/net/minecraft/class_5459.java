/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.function.Predicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class class_5459 {
    public static class_5460 method_30574(BlockPos blockPos2, Direction.Axis axis, int n, Direction.Axis axis2, int n2, Predicate<BlockPos> predicate) {
        BlockPos blockPos2;
        IntBounds intBounds;
        int _snowman15;
        BlockPos.Mutable mutable = blockPos2.mutableCopy();
        Direction _snowman2 = Direction.get(Direction.AxisDirection.NEGATIVE, axis);
        Direction _snowman3 = _snowman2.getOpposite();
        Direction _snowman4 = Direction.get(Direction.AxisDirection.NEGATIVE, axis2);
        Direction _snowman5 = _snowman4.getOpposite();
        int _snowman6 = class_5459.method_30575(predicate, mutable.set(blockPos2), _snowman2, n);
        int _snowman7 = class_5459.method_30575(predicate, mutable.set(blockPos2), _snowman3, n);
        int _snowman8 = _snowman6;
        IntBounds[] _snowman9 = new IntBounds[_snowman8 + 1 + _snowman7];
        _snowman9[_snowman8] = new IntBounds(class_5459.method_30575(predicate, mutable.set(blockPos2), _snowman4, n2), class_5459.method_30575(predicate, mutable.set(blockPos2), _snowman5, n2));
        int _snowman10 = _snowman9[_snowman8].min;
        for (_snowman15 = 1; _snowman15 <= _snowman6; ++_snowman15) {
            intBounds = _snowman9[_snowman8 - (_snowman15 - 1)];
            _snowman9[_snowman8 - _snowman15] = new IntBounds(class_5459.method_30575(predicate, mutable.set(blockPos2).move(_snowman2, _snowman15), _snowman4, intBounds.min), class_5459.method_30575(predicate, mutable.set(blockPos2).move(_snowman2, _snowman15), _snowman5, intBounds.max));
        }
        for (_snowman15 = 1; _snowman15 <= _snowman7; ++_snowman15) {
            intBounds = _snowman9[_snowman8 + _snowman15 - 1];
            _snowman9[_snowman8 + _snowman15] = new IntBounds(class_5459.method_30575(predicate, mutable.set(blockPos2).move(_snowman3, _snowman15), _snowman4, intBounds.min), class_5459.method_30575(predicate, mutable.set(blockPos2).move(_snowman3, _snowman15), _snowman5, intBounds.max));
        }
        _snowman15 = 0;
        _snowman = 0;
        _snowman = 0;
        _snowman = 0;
        int[] _snowman11 = new int[_snowman9.length];
        for (n3 = _snowman10; n3 >= 0; --n3) {
            int n3;
            int _snowman13;
            int _snowman12;
            IntBounds _snowman14;
            for (_snowman = 0; _snowman < _snowman9.length; ++_snowman) {
                _snowman14 = _snowman9[_snowman];
                _snowman12 = _snowman10 - _snowman14.min;
                _snowman13 = _snowman10 + _snowman14.max;
                _snowman11[_snowman] = n3 >= _snowman12 && n3 <= _snowman13 ? _snowman13 + 1 - n3 : 0;
            }
            Pair<IntBounds, Integer> pair = class_5459.method_30576(_snowman11);
            _snowman14 = (IntBounds)pair.getFirst();
            _snowman12 = 1 + _snowman14.max - _snowman14.min;
            _snowman13 = (Integer)pair.getSecond();
            if (_snowman12 * _snowman13 <= _snowman * _snowman) continue;
            _snowman15 = _snowman14.min;
            _snowman = n3;
            _snowman = _snowman12;
            _snowman = _snowman13;
        }
        return new class_5460(blockPos2.offset(axis, _snowman15 - _snowman8).offset(axis2, _snowman - _snowman10), _snowman, _snowman);
    }

    private static int method_30575(Predicate<BlockPos> predicate, BlockPos.Mutable mutable, Direction direction, int n) {
        for (_snowman = 0; _snowman < n && predicate.test(mutable.move(direction)); ++_snowman) {
        }
        return _snowman;
    }

    @VisibleForTesting
    static Pair<IntBounds, Integer> method_30576(int[] nArray) {
        int n = 0;
        _snowman = 0;
        _snowman = 0;
        IntArrayList _snowman2 = new IntArrayList();
        _snowman2.push(0);
        for (_snowman = 1; _snowman <= nArray.length; ++_snowman) {
            int n2 = _snowman = _snowman == nArray.length ? 0 : nArray[_snowman];
            while (!_snowman2.isEmpty()) {
                _snowman = nArray[_snowman2.topInt()];
                if (_snowman >= _snowman) {
                    _snowman2.push(_snowman);
                    break;
                }
                _snowman2.popInt();
                _snowman = _snowman2.isEmpty() ? 0 : _snowman2.topInt() + 1;
                if (_snowman * (_snowman - _snowman) <= _snowman * (_snowman - n)) continue;
                _snowman = _snowman;
                n = _snowman;
                _snowman = _snowman;
            }
            if (!_snowman2.isEmpty()) continue;
            _snowman2.push(_snowman);
        }
        return new Pair((Object)new IntBounds(n, _snowman - 1), (Object)_snowman);
    }

    public static class class_5460 {
        public final BlockPos field_25936;
        public final int field_25937;
        public final int field_25938;

        public class_5460(BlockPos blockPos, int n, int n2) {
            this.field_25936 = blockPos;
            this.field_25937 = n;
            this.field_25938 = n2;
        }
    }

    public static class IntBounds {
        public final int min;
        public final int max;

        public IntBounds(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public String toString() {
            return "IntBounds{min=" + this.min + ", max=" + this.max + '}';
        }
    }
}

