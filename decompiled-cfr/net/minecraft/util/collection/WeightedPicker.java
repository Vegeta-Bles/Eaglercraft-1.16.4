/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.collection;

import java.util.List;
import java.util.Random;
import net.minecraft.util.Util;

public class WeightedPicker {
    public static int getWeightSum(List<? extends Entry> list) {
        int n = 0;
        _snowman = list.size();
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            Entry entry = list.get(_snowman);
            n += entry.weight;
        }
        return n;
    }

    public static <T extends Entry> T getRandom(Random random, List<T> list, int weightSum) {
        if (weightSum <= 0) {
            throw Util.throwOrPause(new IllegalArgumentException());
        }
        int n = random.nextInt(weightSum);
        return WeightedPicker.getAt(list, n);
    }

    public static <T extends Entry> T getAt(List<T> list, int weightMark) {
        int n = list.size();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            Entry entry = (Entry)list.get(_snowman);
            if ((weightMark -= entry.weight) >= 0) continue;
            return (T)entry;
        }
        return null;
    }

    public static <T extends Entry> T getRandom(Random random, List<T> list) {
        return WeightedPicker.getRandom(random, list, WeightedPicker.getWeightSum(list));
    }

    public static class Entry {
        protected final int weight;

        public Entry(int n) {
            this.weight = n;
        }
    }
}

