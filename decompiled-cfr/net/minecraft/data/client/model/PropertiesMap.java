/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.state.property.Property;

public final class PropertiesMap {
    private static final PropertiesMap EMPTY = new PropertiesMap((List<Property.Value<?>>)ImmutableList.of());
    private static final Comparator<Property.Value<?>> COMPARATOR = Comparator.comparing(value -> value.getProperty().getName());
    private final List<Property.Value<?>> propertyValues;

    public PropertiesMap method_25819(Property.Value<?> value) {
        return new PropertiesMap((List<Property.Value<?>>)ImmutableList.builder().addAll(this.propertyValues).add(value).build());
    }

    public PropertiesMap with(PropertiesMap propertiesMap) {
        return new PropertiesMap((List<Property.Value<?>>)ImmutableList.builder().addAll(this.propertyValues).addAll(propertiesMap.propertyValues).build());
    }

    private PropertiesMap(List<Property.Value<?>> list) {
        this.propertyValues = list;
    }

    public static PropertiesMap empty() {
        return EMPTY;
    }

    public static PropertiesMap method_25821(Property.Value<?> ... valueArray) {
        return new PropertiesMap((List<Property.Value<?>>)ImmutableList.copyOf((Object[])valueArray));
    }

    public boolean equals(Object object) {
        return this == object || object instanceof PropertiesMap && this.propertyValues.equals(((PropertiesMap)object).propertyValues);
    }

    public int hashCode() {
        return this.propertyValues.hashCode();
    }

    public String asString() {
        return this.propertyValues.stream().sorted(COMPARATOR).map(Property.Value::toString).collect(Collectors.joining(","));
    }

    public String toString() {
        return this.asString();
    }
}

