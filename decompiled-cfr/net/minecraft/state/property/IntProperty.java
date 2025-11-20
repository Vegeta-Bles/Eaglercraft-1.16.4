/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 */
package net.minecraft.state.property;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import net.minecraft.state.property.Property;

public class IntProperty
extends Property<Integer> {
    private final ImmutableSet<Integer> values;

    protected IntProperty(String name, int min, int max) {
        super(name, Integer.class);
        if (min < 0) {
            throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
        }
        if (max <= min) {
            throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
        }
        HashSet hashSet = Sets.newHashSet();
        for (int i = min; i <= max; ++i) {
            hashSet.add(i);
        }
        this.values = ImmutableSet.copyOf((Collection)hashSet);
    }

    @Override
    public Collection<Integer> getValues() {
        return this.values;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof IntProperty && super.equals(object)) {
            IntProperty intProperty = (IntProperty)object;
            return this.values.equals(intProperty.values);
        }
        return false;
    }

    @Override
    public int computeHashCode() {
        return 31 * super.computeHashCode() + this.values.hashCode();
    }

    public static IntProperty of(String name, int min, int max) {
        return new IntProperty(name, min, max);
    }

    @Override
    public Optional<Integer> parse(String name) {
        try {
            Integer n = Integer.valueOf(name);
            return this.values.contains((Object)n) ? Optional.of(n) : Optional.empty();
        }
        catch (NumberFormatException numberFormatException) {
            return Optional.empty();
        }
    }

    @Override
    public String name(Integer n) {
        return n.toString();
    }
}

