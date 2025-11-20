/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.google.common.collect.Iterators
 *  javax.annotation.Nullable
 */
package net.minecraft.util.collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.MathHelper;

public class Int2ObjectBiMap<K>
implements IndexedIterable<K> {
    private static final Object EMPTY = null;
    private K[] values;
    private int[] ids;
    private K[] idToValues;
    private int nextId;
    private int size;

    public Int2ObjectBiMap(int size) {
        size = (int)((float)size / 0.8f);
        this.values = new Object[size];
        this.ids = new int[size];
        this.idToValues = new Object[size];
    }

    @Override
    public int getRawId(@Nullable K entry) {
        return this.getIdFromIndex(this.findIndex(entry, this.getIdealIndex(entry)));
    }

    @Override
    @Nullable
    public K get(int index) {
        if (index < 0 || index >= this.idToValues.length) {
            return null;
        }
        return this.idToValues[index];
    }

    private int getIdFromIndex(int index) {
        if (index == -1) {
            return -1;
        }
        return this.ids[index];
    }

    public int add(K value) {
        int n = this.nextId();
        this.put(value, n);
        return n;
    }

    private int nextId() {
        while (this.nextId < this.idToValues.length && this.idToValues[this.nextId] != null) {
            ++this.nextId;
        }
        return this.nextId;
    }

    private void resize(int newSize) {
        K[] KArray = this.values;
        int[] _snowman2 = this.ids;
        this.values = new Object[newSize];
        this.ids = new int[newSize];
        this.idToValues = new Object[newSize];
        this.nextId = 0;
        this.size = 0;
        for (int i = 0; i < KArray.length; ++i) {
            if (KArray[i] == null) continue;
            this.put(KArray[i], _snowman2[i]);
        }
    }

    public void put(K value, int id) {
        int n = Math.max(id, this.size + 1);
        if ((float)n >= (float)this.values.length * 0.8f) {
            for (_snowman = this.values.length << 1; _snowman < id; _snowman <<= 1) {
            }
            this.resize(_snowman);
        }
        _snowman = this.findFree(this.getIdealIndex(value));
        this.values[_snowman] = value;
        this.ids[_snowman] = id;
        this.idToValues[id] = value;
        ++this.size;
        if (id == this.nextId) {
            ++this.nextId;
        }
    }

    private int getIdealIndex(@Nullable K value) {
        return (MathHelper.idealHash(System.identityHashCode(value)) & Integer.MAX_VALUE) % this.values.length;
    }

    private int findIndex(@Nullable K value, int id) {
        int n;
        for (n = id; n < this.values.length; ++n) {
            if (this.values[n] == value) {
                return n;
            }
            if (this.values[n] != EMPTY) continue;
            return -1;
        }
        for (n = 0; n < id; ++n) {
            if (this.values[n] == value) {
                return n;
            }
            if (this.values[n] != EMPTY) continue;
            return -1;
        }
        return -1;
    }

    private int findFree(int size) {
        int n;
        for (n = size; n < this.values.length; ++n) {
            if (this.values[n] != EMPTY) continue;
            return n;
        }
        for (n = 0; n < size; ++n) {
            if (this.values[n] != EMPTY) continue;
            return n;
        }
        throw new RuntimeException("Overflowed :(");
    }

    @Override
    public Iterator<K> iterator() {
        return Iterators.filter((Iterator)Iterators.forArray((Object[])this.idToValues), (Predicate)Predicates.notNull());
    }

    public void clear() {
        Arrays.fill(this.values, null);
        Arrays.fill(this.idToValues, null);
        this.nextId = 0;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }
}

