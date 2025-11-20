/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSortedMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.MapDecoder
 *  com.mojang.serialization.MapEncoder
 *  javax.annotation.Nullable
 */
package net.minecraft.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;

public class StateManager<O, S extends State<O, S>> {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
    private final O owner;
    private final ImmutableSortedMap<String, Property<?>> properties;
    private final ImmutableList<S> states;

    protected StateManager(Function<O, S> function, O o2, Factory<O, S> factory, Map<String, Property<?>> propertiesMap) {
        Object o2;
        Object _snowman32;
        this.owner = o2;
        this.properties = ImmutableSortedMap.copyOf(propertiesMap);
        Supplier<State> supplier = () -> (State)function.apply(o2);
        MapCodec<State> _snowman2 = MapCodec.of((MapEncoder)Encoder.empty(), (MapDecoder)Decoder.unit(supplier));
        for (Object _snowman32 : this.properties.entrySet()) {
            _snowman2 = StateManager.method_30040(_snowman2, supplier, (String)_snowman32.getKey(), (Property)_snowman32.getValue());
        }
        MapCodec<State> mapCodec = _snowman2;
        _snowman32 = Maps.newLinkedHashMap();
        ArrayList _snowman4 = Lists.newArrayList();
        Stream<List<List<Object>>> _snowman5 = Stream.of(Collections.emptyList());
        for (Object object : this.properties.values()) {
            _snowman5 = _snowman5.flatMap(arg_0 -> StateManager.method_11666((Property)object, arg_0));
        }
        _snowman5.forEach(arg_0 -> StateManager.method_28484(factory, o2, mapCodec, (Map)_snowman32, _snowman4, arg_0));
        for (Object object : _snowman4) {
            ((State)object).createWithTable(_snowman32);
        }
        this.states = ImmutableList.copyOf((Collection)_snowman4);
    }

    private static <S extends State<?, S>, T extends Comparable<T>> MapCodec<S> method_30040(MapCodec<S> mapCodec, Supplier<S> supplier, String string, Property<T> property) {
        return Codec.mapPair(mapCodec, (MapCodec)property.getValueCodec().fieldOf(string).setPartial(() -> property.createValue((State)supplier.get()))).xmap(pair -> (State)((State)pair.getFirst()).with(property, ((Property.Value)pair.getSecond()).getValue()), state -> Pair.of((Object)state, property.createValue((State<?, ?>)state)));
    }

    public ImmutableList<S> getStates() {
        return this.states;
    }

    public S getDefaultState() {
        return (S)((State)this.states.get(0));
    }

    public O getOwner() {
        return this.owner;
    }

    public Collection<Property<?>> getProperties() {
        return this.properties.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("block", this.owner).add("properties", this.properties.values().stream().map(Property::getName).collect(Collectors.toList())).toString();
    }

    @Nullable
    public Property<?> getProperty(String name) {
        return (Property)this.properties.get((Object)name);
    }

    private static /* synthetic */ void method_28484(Factory factory, Object object, MapCodec mapCodec, Map map, List list, List list2) {
        ImmutableMap immutableMap = (ImmutableMap)list2.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
        State _snowman2 = (State)factory.create(object, immutableMap, mapCodec);
        map.put(immutableMap, _snowman2);
        list.add(_snowman2);
    }

    private static /* synthetic */ Stream method_11666(Property property, List list) {
        return property.getValues().stream().map(comparable -> {
            ArrayList arrayList = Lists.newArrayList((Iterable)list);
            arrayList.add(Pair.of((Object)property, (Object)comparable));
            return arrayList;
        });
    }

    public static class Builder<O, S extends State<O, S>> {
        private final O owner;
        private final Map<String, Property<?>> namedProperties = Maps.newHashMap();

        public Builder(O owner) {
            this.owner = owner;
        }

        public Builder<O, S> add(Property<?> ... properties) {
            for (Property<?> property : properties) {
                this.validate(property);
                this.namedProperties.put(property.getName(), property);
            }
            return this;
        }

        private <T extends Comparable<T>> void validate(Property<T> property) {
            String string = property.getName();
            if (!VALID_NAME_PATTERN.matcher(string).matches()) {
                throw new IllegalArgumentException(this.owner + " has invalidly named property: " + string);
            }
            Collection<T> _snowman2 = property.getValues();
            if (_snowman2.size() <= 1) {
                throw new IllegalArgumentException(this.owner + " attempted use property " + string + " with <= 1 possible values");
            }
            for (Comparable comparable : _snowman2) {
                String string2 = property.name(comparable);
                if (VALID_NAME_PATTERN.matcher(string2).matches()) continue;
                throw new IllegalArgumentException(this.owner + " has property: " + string + " with invalidly named value: " + string2);
            }
            if (this.namedProperties.containsKey(string)) {
                throw new IllegalArgumentException(this.owner + " has duplicate property: " + string);
            }
        }

        public StateManager<O, S> build(Function<O, S> ownerToStateFunction, Factory<O, S> factory) {
            return new StateManager<O, S>(ownerToStateFunction, this.owner, factory, this.namedProperties);
        }
    }

    public static interface Factory<O, S> {
        public S create(O var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<S> var3);
    }
}

