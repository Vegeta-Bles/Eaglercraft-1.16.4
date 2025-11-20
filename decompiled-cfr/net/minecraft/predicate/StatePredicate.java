/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.Nullable
 */
package net.minecraft.predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.StringIdentifiable;

public class StatePredicate {
    public static final StatePredicate ANY = new StatePredicate((List<Condition>)ImmutableList.of());
    private final List<Condition> conditions;

    private static Condition createPredicate(String key, JsonElement json) {
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            return new ExactValueCondition(key, string);
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "value");
        String _snowman2 = jsonObject.has("min") ? StatePredicate.asNullableString(jsonObject.get("min")) : null;
        String _snowman3 = jsonObject.has("max") ? StatePredicate.asNullableString(jsonObject.get("max")) : null;
        return _snowman2 != null && _snowman2.equals(_snowman3) ? new ExactValueCondition(key, _snowman2) : new RangedValueCondition(key, _snowman2, _snowman3);
    }

    @Nullable
    private static String asNullableString(JsonElement json) {
        if (json.isJsonNull()) {
            return null;
        }
        return json.getAsString();
    }

    private StatePredicate(List<Condition> testers) {
        this.conditions = ImmutableList.copyOf(testers);
    }

    public <S extends State<?, S>> boolean test(StateManager<?, S> stateManager, S container) {
        for (Condition condition : this.conditions) {
            if (condition.test(stateManager, container)) continue;
            return false;
        }
        return true;
    }

    public boolean test(BlockState state) {
        return this.test(state.getBlock().getStateManager(), state);
    }

    public boolean test(FluidState state) {
        return this.test(state.getFluid().getStateManager(), state);
    }

    public void check(StateManager<?, ?> factory, Consumer<String> reporter) {
        this.conditions.forEach(condition -> condition.reportMissing(factory, reporter));
    }

    public static StatePredicate fromJson(@Nullable JsonElement json) {
        if (json == null || json.isJsonNull()) {
            return ANY;
        }
        JsonObject jsonObject = JsonHelper.asObject(json, "properties");
        ArrayList _snowman2 = Lists.newArrayList();
        for (Map.Entry entry : jsonObject.entrySet()) {
            _snowman2.add(StatePredicate.createPredicate((String)entry.getKey(), (JsonElement)entry.getValue()));
        }
        return new StatePredicate(_snowman2);
    }

    public JsonElement toJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (!this.conditions.isEmpty()) {
            this.conditions.forEach(condition -> jsonObject.add(condition.getKey(), condition.toJson()));
        }
        return jsonObject;
    }

    public static class Builder {
        private final List<Condition> conditions = Lists.newArrayList();

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder exactMatch(Property<?> property, String valueName) {
            this.conditions.add(new ExactValueCondition(property.getName(), valueName));
            return this;
        }

        public Builder exactMatch(Property<Integer> property, int value) {
            return this.exactMatch((Property)property, (Comparable<T> & StringIdentifiable)Integer.toString(value));
        }

        public Builder exactMatch(Property<Boolean> property, boolean value) {
            return this.exactMatch((Property)property, (Comparable<T> & StringIdentifiable)Boolean.toString(value));
        }

        public <T extends Comparable<T> & StringIdentifiable> Builder exactMatch(Property<T> property, T value) {
            return this.exactMatch(property, (T)((StringIdentifiable)value).asString());
        }

        public StatePredicate build() {
            return new StatePredicate(this.conditions);
        }
    }

    static class RangedValueCondition
    extends Condition {
        @Nullable
        private final String min;
        @Nullable
        private final String max;

        public RangedValueCondition(String key, @Nullable String min, @Nullable String max) {
            super(key);
            this.min = min;
            this.max = max;
        }

        @Override
        protected <T extends Comparable<T>> boolean test(State<?, ?> state, Property<T> property) {
            T t = state.get(property);
            if (!(this.min == null || (_snowman = property.parse(this.min)).isPresent() && t.compareTo(_snowman.get()) >= 0)) {
                return false;
            }
            return this.max == null || (_snowman = property.parse(this.max)).isPresent() && t.compareTo(_snowman.get()) <= 0;
        }

        @Override
        public JsonElement toJson() {
            JsonObject jsonObject = new JsonObject();
            if (this.min != null) {
                jsonObject.addProperty("min", this.min);
            }
            if (this.max != null) {
                jsonObject.addProperty("max", this.max);
            }
            return jsonObject;
        }
    }

    static class ExactValueCondition
    extends Condition {
        private final String value;

        public ExactValueCondition(String key, String value) {
            super(key);
            this.value = value;
        }

        @Override
        protected <T extends Comparable<T>> boolean test(State<?, ?> state, Property<T> property) {
            T t = state.get(property);
            Optional<T> _snowman2 = property.parse(this.value);
            return _snowman2.isPresent() && t.compareTo(_snowman2.get()) == 0;
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(this.value);
        }
    }

    static abstract class Condition {
        private final String key;

        public Condition(String key) {
            this.key = key;
        }

        public <S extends State<?, S>> boolean test(StateManager<?, S> stateManager, S state) {
            Property<?> property = stateManager.getProperty(this.key);
            if (property == null) {
                return false;
            }
            return this.test(state, property);
        }

        protected abstract <T extends Comparable<T>> boolean test(State<?, ?> var1, Property<T> var2);

        public abstract JsonElement toJson();

        public String getKey() {
            return this.key;
        }

        public void reportMissing(StateManager<?, ?> factory, Consumer<String> reporter) {
            Property<?> property = factory.getProperty(this.key);
            if (property == null) {
                reporter.accept(this.key);
            }
        }
    }
}

